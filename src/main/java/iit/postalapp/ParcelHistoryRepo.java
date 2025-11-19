package iit.postalapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelHistoryRepo extends JpaRepository<ParcelHistory,Integer> {
}
