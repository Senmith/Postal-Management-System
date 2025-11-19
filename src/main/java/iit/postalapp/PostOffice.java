package iit.postalapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "post_office_data")
public class PostOffice {
    @Id
    @Column(name = "office_id")
    private String officeId;
    private String officeName;
    private String district;
    private String province;
    private String contactNo;
    private boolean isActive;

    @OneToMany(mappedBy = "assignedOffice")
    @JsonIgnore 
    private List<Parcel> parcels;

    //ID Generation
    @PrePersist //runs before saving the entity to database
    public void generateOfficeId() {
        if (this.officeId == null) {
            //auto generation logic
            this.officeId = "POF" + String.format("%03d", (int)(Math.random() * 900 + 100)); // POF100-POF999
        }
    }

    public PostOffice() {
    }

    public PostOffice(String officeId, String officeName, String district, String contactNo, boolean isActive, List<Parcel> parcels) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.district = district;
        this.contactNo = contactNo;
        this.isActive = isActive;
        this.parcels = parcels;
    }

    public String getOfficeId() {

        return officeId;
    }

    public void setOfficeId(String officeId) {

        this.officeId = officeId;
    }

    public String getOfficeName() {

        return officeName;
    }

    public void setOfficeName(String officeName) {

        this.officeName = officeName;
    }

    public String getDistrict() {

        return district;
    }

    public void setDistrict(String district) {

        this.district = district;
    }

    public String getProvince(){

        return province;
    }

    public void setProvince(String province){

        this.province=province;
    }

    public String getContactNo() {

        return contactNo;
    }

    public void setContactNo(String contactNo) {

        this.contactNo = contactNo;
    }

    public boolean isActive() {

        return isActive;
    }

    public void setActive(boolean isActive) {

        this.isActive = isActive;
    }

    public List<Parcel> getParcels() {

        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {

        this.parcels = parcels;
    }
}
