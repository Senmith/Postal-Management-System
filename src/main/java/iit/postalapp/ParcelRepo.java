package iit.postalapp;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ParcelRepo extends JpaRepository<Parcel,String> {
}
