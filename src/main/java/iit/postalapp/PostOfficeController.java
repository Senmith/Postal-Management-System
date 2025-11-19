package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/postOffice")
public class PostOfficeController {
    @Autowired
    PostOfficeService postOfficeService;

    //View Post Office page
    @GetMapping("/view")
    public String showPostOfficesPage(@RequestParam(required = false) String editOfficeId,
                                     Model model) {
        // Load all post offices
        List<PostOffice> postOffices = postOfficeService.getAllPostOffices();
        model.addAttribute("postOffices", postOffices);
        
        // Handle edit modal
        if (editOfficeId != null) {
            try {
                PostOffice editOffice = postOfficeService.getPostOfficeById(editOfficeId);
                model.addAttribute("editOffice", editOffice);
            } catch (Exception e) {
                // Handle error silently
            }
        }
        
        return "post-offices";
    }

    //Add Post Office
    @PostMapping("/add")
    public String addPostOffice(@RequestParam String officeName,
                               @RequestParam String district,
                               @RequestParam String province,
                               @RequestParam String contactNo) {
        try {
            PostOffice postOffice = new PostOffice();
            postOffice.setOfficeName(officeName);
            postOffice.setDistrict(district);
            postOffice.setProvince(province);
            postOffice.setContactNo(contactNo);
            postOffice.setActive(true);
            
            postOfficeService.addPostOffice(postOffice);
            return "redirect:/postOffice/view";
        } catch (Exception e) {
            return "redirect:/postOffice/view";
        }
    }

    //Update Post Office
    @PostMapping("/update")
    public String updatePostOffice(@RequestParam String officeId,
                                  @RequestParam String officeName,
                                  @RequestParam String district,
                                  @RequestParam String province,
                                  @RequestParam String contactNo) {
        try {
            PostOffice existingOffice = postOfficeService.getPostOfficeById(officeId);
            existingOffice.setOfficeName(officeName);
            existingOffice.setDistrict(district);
            existingOffice.setProvince(province);
            existingOffice.setContactNo(contactNo);
            
            postOfficeService.updatePostOffice(existingOffice);
            return "redirect:/postOffice/view";
        } catch (Exception e) {
            return "redirect:/postOffice/view";
        }
    }

    //Delete Post Office
    @PostMapping("/delete")
    public String deletePostOffice(@RequestParam String officeId) {
        try {
            postOfficeService.deletePostOfficeById(officeId);
            return "redirect:/postOffice/view";
        } catch (Exception e) {
            return "redirect:/postOffice/view";
        }
    }

    //Toggle Post Office Status
    @PostMapping("/toggle-status")
    public String togglePostOfficeStatus(@RequestParam String officeId) {
        try {
            PostOffice postOffice = postOfficeService.getPostOfficeById(officeId);
            postOffice.setActive(!postOffice.isActive());
            postOfficeService.updatePostOffice(postOffice);
            return "redirect:/postOffice/view";
        } catch (Exception e) {
            return "redirect:/postOffice/view";
        }
    }
}
