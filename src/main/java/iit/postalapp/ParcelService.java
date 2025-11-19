package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelService {
    @Autowired
    ParcelRepo parcelRepo;

    @Autowired
    ParcelHistoryRepo parcelHistoryRepo;
    @Autowired
    private PostOfficeRepo postOfficeRepo;

    @Autowired
    private QRCodeService qrCodeService;

    public Parcel addParcel(Parcel parcel){

        //Assigning the post office which the parcel belongs to
        if (parcel.getAssignedOffice() != null && parcel.getAssignedOffice().getOfficeId() != null) {
            String officeId = parcel.getAssignedOffice().getOfficeId();
            PostOffice office = postOfficeRepo.findById(officeId)
                    .orElseThrow(() -> new RuntimeException("Assigned Post Office not found: " + officeId));
            parcel.setAssignedOffice(office);
        } else {
            parcel.setAssignedOffice(null);
        }

        Parcel savedParcel = parcelRepo.save(parcel);

        // Generate QR code for the parcel
        try {
            String qrCodePath = qrCodeService.generateQRCodeForParcel(savedParcel);
            savedParcel.setQrCodePath(qrCodePath);
            savedParcel = parcelRepo.save(savedParcel); // Save again with QR code path
        } catch (Exception e) {
            // Log the error but don't fail the parcel creation
            System.err.println("Failed to generate QR code for parcel " + savedParcel.getParcelId() + ": " + e.getMessage());
        }

        ParcelHistory history = new ParcelHistory();
        history.setParcel(savedParcel);
        history.setStatus(savedParcel.getCurrent_status());
        history.setUpdateTime(LocalDateTime.now());
        history.setLocation(savedParcel.getAssignedOffice() != null
                ? savedParcel.getAssignedOffice().getOfficeName()
                : "Not assigned");
        parcelHistoryRepo.save(history);


        return savedParcel;
    }

    //Get parcel by id
    public Parcel getParcelById(String parcelId){
        return parcelRepo.findById(parcelId)
                .orElseThrow(() -> new RuntimeException("Parcel not found: "+parcelId));
    }

    //Get all parcels
    public List<Parcel> getAllParcel(Parcel parcel){
        return parcelRepo.findAll();
    }

    //update parcel details
    public Parcel updateParcel (Parcel parcel){
        Parcel existingParcel = getParcelById(parcel.getParcelId());

        existingParcel.setSenderName(parcel.getSenderName());
        existingParcel.setSenderAddress(parcel.getSenderAddress());
        existingParcel.setReceiverName(parcel.getReceiverName());
        existingParcel.setReceiverAddress(parcel.getReceiverAddress());
        existingParcel.setWeight(parcel.getWeight());
        existingParcel.setCategory(parcel.getCategory());
        existingParcel.setCurrent_status(parcel.getCurrent_status());

        if (parcel.getAssignedOffice() != null && parcel.getAssignedOffice().getOfficeId() != null) {
            PostOffice office = postOfficeRepo.findById(parcel.getAssignedOffice().getOfficeId())
                    .orElseThrow(() -> new RuntimeException("Post Office not found: " + parcel.getAssignedOffice().getOfficeId()));
            existingParcel.setAssignedOffice(office);
        } else {
            existingParcel.setAssignedOffice(null);
        }

        //Adds a new history entry in the DB
        ParcelHistory history = new ParcelHistory();
        history.setParcel((existingParcel));
        history.setStatus(existingParcel.getCurrent_status());
        history.setUpdateTime(LocalDateTime.now());
        history.setLocation(existingParcel.getAssignedOffice() != null? existingParcel.getAssignedOffice().getOfficeName(): "Not Assigned");

        existingParcel.getHistory().add(history);

        return parcelRepo.save(existingParcel);
    }

    //delete parcel by id
    public void deleteParcelById(String parcelId){
        // Delete QR code file if it exists
        try {
            qrCodeService.deleteQRCodeFile(parcelId);
        } catch (Exception e) {
            // Log error but continue with deletion
            System.err.println("Failed to delete QR code file for parcel " + parcelId + ": " + e.getMessage());
        }
        
        parcelRepo.deleteById(parcelId);
    }

    //get parcel history
    public List<ParcelHistory> getParcelHistory(String parcelId){
        Parcel parcel = getParcelById(parcelId);
        return parcel.getHistory().stream()
                .sorted(Comparator.comparing(ParcelHistory::getUpdateTime).reversed())
                .collect(Collectors.toList());
    }

    //get recent parcel updates
    public List<ParcelHistory>getRecentParcelUpdates(int limit){
        List<ParcelHistory> allHistory = parcelHistoryRepo.findAll();

        return allHistory.stream()
                .sorted(Comparator.comparing(ParcelHistory::getUpdateTime).reversed())
                .limit(limit)
                .collect(Collectors.toList());

    }

    //add parcel history entry
    public ParcelHistory addParcelHistory(ParcelHistory history) {
        return parcelHistoryRepo.save(history);
    }
}
