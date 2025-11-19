package iit.postalapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficeRepo extends JpaRepository<PostOffice,String> {
}
