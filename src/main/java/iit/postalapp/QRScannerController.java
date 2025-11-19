package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qr")
public class QRScannerController {

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private ParcelHistoryRepo parcelHistoryRepo;

    //Display QR scanner page
    @GetMapping("/scanner")
    public String showQRScanner(Model model) {
        return "qr-scanner";
    }

    //Display parcel checkpoint information after QR scan
    @GetMapping("/checkpoint/{parcelId}")
    public String showParcelCheckpoint(@PathVariable String parcelId, Model model,
                                     @RequestParam(required = false) String success) {
        try {
            Parcel parcel = parcelService.getParcelById(parcelId);
            if (parcel == null) {
                model.addAttribute("error", "Parcel not found: " + parcelId);
                return "qr-checkpoint";
            }
            
            // Fetch parcel history using service
            List<ParcelHistory> history = parcelService.getParcelHistory(parcelId);
            parcel.setHistory(history);
            
            model.addAttribute("parcel", parcel);
            model.addAttribute("parcelId", parcelId);
            
            if (success != null) {
                model.addAttribute("success", "Status updated successfully!");
            }
            
            return "qr-checkpoint";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving parcel information: " + e.getMessage());
            return "qr-checkpoint";
        }
    }

    //Process scanned QR code data
    @PostMapping("/scan")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processQRScan(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String qrContent = request.get("qrContent");
            
            if (qrContent == null || qrContent.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "No QR content provided");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate parcel qr code
            if (!qrCodeService.isValidParcelQRCode(qrContent)) {
                response.put("success", false);
                response.put("message", "Invalid parcel QR code");
                return ResponseEntity.badRequest().body(response);
            }

            // Extract parcel ID from QR content
            String parcelId = qrCodeService.parseParcelIdFromQRContent(qrContent);
            
            if (parcelId == null) {
                response.put("success", false);
                response.put("message", "Could not extract parcel ID from QR code");
                return ResponseEntity.badRequest().body(response);
            }

            // Fetch parcel details
            Parcel parcel = parcelService.getParcelById(parcelId);
            
            if (parcel == null) {
                response.put("success", false);
                response.put("message", "Parcel not found: " + parcelId);
                return ResponseEntity.badRequest().body(response);
            }

            // Return parcel information
            response.put("success", true);
            response.put("parcel", createParcelResponse(parcel));
            response.put("message", "Parcel information retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error processing QR code: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Update parcel through qr scan
    @PostMapping("/update-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateParcelStatus(
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String parcelId = request.get("parcelId");
            String newStatus = request.get("status");
            String location = request.get("location");
            String notes = request.get("notes");

            if (parcelId == null || newStatus == null) {
                response.put("success", false);
                response.put("message", "Parcel ID and status are required");
                return ResponseEntity.badRequest().body(response);
            }

            // Fetch and update parcel
            Parcel parcel = parcelService.getParcelById(parcelId);
            Parcel.Status status = Parcel.Status.valueOf(newStatus.toUpperCase());
            
            parcel.setCurrent_status(status);
            
            // Create history entry
            ParcelHistory history = new ParcelHistory();
            history.setParcel(parcel);
            history.setStatus(status);
            history.setUpdateTime(LocalDateTime.now());
            history.setLocation(location != null ? location : 
                (parcel.getAssignedOffice() != null ? parcel.getAssignedOffice().getOfficeName() : "Unknown"));
            history.setRemarks(notes != null ? notes : "Status updated via QR scan");
            
            // Save history
            parcelHistoryRepo.save(history);
            
            // Update parcel
            parcelService.updateParcel(parcel);

            response.put("success", true);
            response.put("message", "Parcel status updated successfully");
            response.put("parcel", createParcelResponse(parcel));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating parcel status: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Update parcel status via form submission from checkpoint page
    @PostMapping("/checkpoint/update-status")
    public String updateParcelStatusForm(
            @RequestParam String parcelId,
            @RequestParam String status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String notes,
            Model model) {
        
        try {
            // Fetch and update parcel
            Parcel parcel = parcelService.getParcelById(parcelId);
            if (parcel == null) {
                model.addAttribute("error", "Parcel not found: " + parcelId);
                return "qr-checkpoint";
            }
            
            Parcel.Status newStatus = Parcel.Status.valueOf(status.toUpperCase());
            parcel.setCurrent_status(newStatus);
            
            // Create history entry
            ParcelHistory history = new ParcelHistory();
            history.setParcel(parcel);
            history.setStatus(newStatus);
            history.setUpdateTime(LocalDateTime.now());
            history.setLocation(location != null && !location.trim().isEmpty() ? location : 
                (parcel.getAssignedOffice() != null ? parcel.getAssignedOffice().getOfficeName() : "Unknown"));
            history.setRemarks(notes != null && !notes.trim().isEmpty() ? notes : "Status updated via checkpoint");
            
            // Save history and update parcel
            parcelHistoryRepo.save(history);
            parcelService.updateParcel(parcel);
            
            // Redirect back to checkpoint page with success message
            return "redirect:/qr/checkpoint/" + parcelId + "?success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error updating parcel status: " + e.getMessage());
            model.addAttribute("parcel", parcelService.getParcelById(parcelId));
            return "qr-checkpoint";
        }
    }

    //Get parcel information by QR scan
    @GetMapping("/parcel/{parcelId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getParcelInfo(@PathVariable String parcelId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Parcel parcel = parcelService.getParcelById(parcelId);
            
            response.put("success", true);
            response.put("parcel", createParcelResponse(parcel));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Parcel not found: " + parcelId);
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Create a response object for parcel data
    private Map<String, Object> createParcelResponse(Parcel parcel) {
        Map<String, Object> parcelData = new HashMap<>();
        parcelData.put("parcelId", parcel.getParcelId());
        parcelData.put("senderName", parcel.getSenderName());
        parcelData.put("senderAddress", parcel.getSenderAddress());
        parcelData.put("receiverName", parcel.getReceiverName());
        parcelData.put("receiverAddress", parcel.getReceiverAddress());
        parcelData.put("weight", parcel.getWeight());
        parcelData.put("category", parcel.getCategory().toString());
        parcelData.put("currentStatus", parcel.getCurrent_status().toString());
        parcelData.put("createdDate", parcel.getCreatedDate().toString());
        parcelData.put("qrCodePath", parcel.getQrCodePath());
        
        if (parcel.getAssignedOffice() != null) {
            parcelData.put("assignedOffice", parcel.getAssignedOffice().getOfficeName());
            parcelData.put("officeId", parcel.getAssignedOffice().getOfficeId());
        }
        
        return parcelData;
    }
}