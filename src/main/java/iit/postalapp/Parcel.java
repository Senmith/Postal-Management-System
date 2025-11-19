package iit.postalapp;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parcel_data")
public class    Parcel {

    @Id
    private String parcelId;
    private String senderName;
    private String senderAddress;
    private String receiverName;
    private String receiverAddress;
    private double weight;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status current_status;

    private LocalDateTime createdDate;
    private String qrCodePath;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private PostOffice assignedOffice;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL)
    private List<ParcelHistory> history;

    public enum Category{
        DOCUMENT,
        PACKAGE,
        REGISTERED,
        EMS
    }

    public enum Status{
        REGISTERED,
        AT_ORIGIN_POST_OFFICE,
        IN_TRANSIT,
        AT_DESTINATION_POST_OFFICE,
        OUT_FOR_DELIVERY,
        DELIVERED,
        DELIVERY_ATTEMPT_FAILED,
        RETURNED_TO_ORIGIN,
        ON_HOLD
    }

    @PrePersist //runs before saving the entity to the database
    public void generateParcelId() {
        if (this.parcelId == null) {
            this.parcelId = "PCL" + String.format("%05d", (int)(Math.random() * 90000 + 10000)); // PCL10000-PCL99999
        }
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
    }

    public Parcel() {
        this.history = new ArrayList<>();
    }

    public Parcel(String parcelId, String senderName, String senderAddress, String receiverName, String receiverAddress, double weight, Category category, Status current_status, LocalDateTime createdDate, String qrCodePath, PostOffice assignedOffice, List<ParcelHistory> history) {
        this.parcelId = parcelId;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.weight = weight;
        this.category = category;
        this.current_status = current_status;
        this.createdDate = createdDate;
        this.qrCodePath = qrCodePath;
        this.assignedOffice = assignedOffice;
        this.history = history;
    }

    public String getParcelId() {
        return parcelId;
    }

    public void setParcelId(String parcelId) {
        this.parcelId = parcelId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(Status current_status) {
        this.current_status = current_status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public PostOffice getAssignedOffice() {
        return assignedOffice;
    }

    public void setAssignedOffice(PostOffice assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public List<ParcelHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ParcelHistory> history) {
        this.history = history;
    }
}
