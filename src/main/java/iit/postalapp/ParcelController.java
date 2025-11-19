package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parcel")
public class ParcelController {
    @Autowired
    ParcelService parcelService;

    @Autowired
    PostOfficeService postOfficeService;

    //View Parcels page
    @GetMapping("/view")
    public String showParcelsPage(Model model,
                                 @RequestParam(required = false) String editParcelId,
                                 @RequestParam(required = false) String historyParcelId,
                                 @RequestParam(required = false) String statusParcelId) {
        // Get all parcels for display
        List<Parcel> parcels = parcelService.getAllParcel(null);
        model.addAttribute("parcels", parcels);
        
        // Get all post offices for dropdown
        List<PostOffice> postOffices = postOfficeService.getAllPostOffices();
        model.addAttribute("postOffices", postOffices);
        
        // Handle edit parcel modal
        if (editParcelId != null) {
            try {
                Parcel editParcel = parcelService.getParcelById(editParcelId);
                model.addAttribute("editParcel", editParcel);
                model.addAttribute("showEditModal", true);
            } catch (Exception e) {
                // Handle error silently
            }
        }
        
        // Handle status update modal
        if (statusParcelId != null) {
            try {
                Parcel statusParcel = parcelService.getParcelById(statusParcelId);
                model.addAttribute("statusParcel", statusParcel);
                model.addAttribute("showStatusModal", true);
            } catch (Exception e) {
                // Handle error silently
            }
        }
        
        // Handle history modal
        if (historyParcelId != null) {
            try {
                Parcel historyParcel = parcelService.getParcelById(historyParcelId);
                List<ParcelHistory> history = parcelService.getParcelHistory(historyParcelId);
                model.addAttribute("historyParcel", historyParcel);
                model.addAttribute("parcelHistory", history);
                model.addAttribute("showHistoryModal", true);
            } catch (Exception e) {
                // Handle error silently
            }
        }
        
        return "parcels";
    }

    //Add a parcel
    @PostMapping("/add")
    public String addParcel(@RequestParam String senderName,
                           @RequestParam String senderAddress,
                           @RequestParam String receiverName,
                           @RequestParam String receiverAddress,
                           @RequestParam double weight,
                           @RequestParam String category,
                           @RequestParam String assignedOfficeId,
                           Model model) {
        try {
            Parcel parcel = new Parcel();
            parcel.setSenderName(senderName);
            parcel.setSenderAddress(senderAddress);
            parcel.setReceiverName(receiverName);
            parcel.setReceiverAddress(receiverAddress);
            parcel.setWeight(weight);
            parcel.setCategory(Parcel.Category.valueOf(category));
            parcel.setCurrent_status(Parcel.Status.REGISTERED);

            if (assignedOfficeId != null && !assignedOfficeId.isEmpty()) {
                PostOffice office = postOfficeService.getPostOfficeById(assignedOfficeId);
                if (office != null) {
                    parcel.setAssignedOffice(office);
                }
            }

            parcelService.addParcel(parcel);
            return "redirect:/parcel/view";
        } catch (Exception e) {
            return "redirect:/parcel/view";
        }
    }

    //Update parcel
    @PostMapping("/update")
    public String updateParcel(@RequestParam String parcelId,
                              @RequestParam String senderName,
                              @RequestParam String senderAddress,
                              @RequestParam String receiverName,
                              @RequestParam String receiverAddress,
                              @RequestParam double weight,
                              @RequestParam String category,
                              @RequestParam String assignedOfficeId) {
        try {
            Parcel existingParcel = parcelService.getParcelById(parcelId);
            
            existingParcel.setSenderName(senderName);
            existingParcel.setSenderAddress(senderAddress);
            existingParcel.setReceiverName(receiverName);
            existingParcel.setReceiverAddress(receiverAddress);
            existingParcel.setWeight(weight);
            existingParcel.setCategory(Parcel.Category.valueOf(category));

            if (assignedOfficeId != null && !assignedOfficeId.isEmpty()) {
                PostOffice office = postOfficeService.getPostOfficeById(assignedOfficeId);
                if (office != null) {
                    existingParcel.setAssignedOffice(office);
                }
            }

            parcelService.updateParcel(existingParcel);
            return "redirect:/parcel/view";
        } catch (Exception e) {
            return "redirect:/parcel/view";
        }
    }

    //Update parcel status
    @PostMapping("/update-status")
    public String updateParcelStatus(@RequestParam String parcelId,
                                    @RequestParam String newStatus,
                                    @RequestParam(required = false) String location,
                                    @RequestParam(required = false) String notes) {
        try {
            Parcel existingParcel = parcelService.getParcelById(parcelId);
            existingParcel.setCurrent_status(Parcel.Status.valueOf(newStatus));
            
            parcelService.updateParcel(existingParcel);
            
            // Add to history with additional details if provided
            if (location != null || notes != null) {
                ParcelHistory history = new ParcelHistory();
                history.setParcel(existingParcel);
                history.setStatus(existingParcel.getCurrent_status());
                history.setUpdateTime(java.time.LocalDateTime.now());
                history.setLocation(location != null ? location : 
                    (existingParcel.getAssignedOffice() != null ? 
                     existingParcel.getAssignedOffice().getOfficeName() : "Not assigned"));
                history.setRemarks(notes);
                parcelService.addParcelHistory(history);
            }
            
            return "redirect:/parcel/view";
        } catch (Exception e) {
            return "redirect:/parcel/view";
        }
    }

    //Delete parcel
    @PostMapping("/delete")
    public String deleteParcel(@RequestParam String parcelId) {
        try {
            parcelService.deleteParcelById(parcelId);
            return "redirect:/parcel/view";
        } catch (Exception e) {
            return "redirect:/parcel/view";
        }
    }

    //Tracking page
    @GetMapping("/tracking")
    public String showTrackingPage(@RequestParam(required = false) String parcelId, 
                                   Model model) {
        if (parcelId != null && !parcelId.trim().isEmpty()) {
            try {
                Parcel parcel = parcelService.getParcelById(parcelId.trim());
                List<ParcelHistory> history = parcelService.getParcelHistory(parcelId.trim());
                
                model.addAttribute("parcel", parcel);
                model.addAttribute("parcelHistory", history);
                model.addAttribute("searchedParcelId", parcelId.trim());
                
            } catch (RuntimeException e) {
                // Handle error silently
                model.addAttribute("searchedParcelId", parcelId.trim());
            }
        }
        
        return "tracking";
    }

    //Handle tracking form submission
    @PostMapping("/tracking")
    public String trackParcelForm(@RequestParam String parcelId) {
        if (parcelId == null || parcelId.trim().isEmpty()) {
            return "redirect:/parcel/tracking";
        }
        return "redirect:/parcel/tracking?parcelId=" + parcelId.trim();
    }
}