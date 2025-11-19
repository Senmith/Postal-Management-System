package iit.postalapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parcel_history_data")
public class ParcelHistory {
    @SequenceGenerator(name = "historyId", sequenceName ="history_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_sequence")
    @Id
    private int historyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parcelId")
    @JsonIgnore  // Prevent circular reference
    private Parcel parcel;

    private LocalDateTime updateTime;
    private String location;

    @Enumerated(EnumType.STRING)
    private Parcel.Status status;

    private String remarks;

    public ParcelHistory() {
    }

    public ParcelHistory(int historyId, Parcel parcel, LocalDateTime updateTime, String location, Parcel.Status status, String remarks) {
        this.historyId = historyId;
        this.parcel = parcel;
        this.updateTime = updateTime;
        this.location = location;
        this.status = status;
        this.remarks = remarks;
    }

    public int getHistoryId() {

        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public Parcel getParcel() {

        return parcel;
    }

    public void setParcel(Parcel parcel) {

        this.parcel = parcel;
    }

    public LocalDateTime getUpdateTime() {

        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {

        this.updateTime = updateTime;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public Parcel.Status getStatus() {

        return status;
    }

    public void setStatus(Parcel.Status status) {

        this.status = status;
    }

    public String getRemarks() {

        return remarks;
    }

    public void setRemarks(String remarks) {

        this.remarks = remarks;
    }

   
    public String getParcelId() {
        return parcel != null ? parcel.getParcelId() : null;
    }
}
