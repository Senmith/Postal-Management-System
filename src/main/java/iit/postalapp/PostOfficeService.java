package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostOfficeService {
    @Autowired
    PostOfficeRepo postOfficeRepo;

    public PostOffice addPostOffice(PostOffice postOffice){

        return postOfficeRepo.save(postOffice);
    }

    public PostOffice getPostOfficeById(String officeId){
        return postOfficeRepo.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Post Office not found: "+officeId));
    }

    public List<PostOffice> getAllPostOffices(){

        return postOfficeRepo.findAll();
    }

    public PostOffice updatePostOffice(PostOffice postOffice){

        return postOfficeRepo.save(postOffice);
    }

    public void deletePostOfficeById(String officeId){

        postOfficeRepo.deleteById(officeId);
    }
}
