package iit.postalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    ParcelService parcelService;

    @Autowired
    PostOfficeService postOfficeService;

    @GetMapping
    public String showDashboard(Model model){

        //Active post office count
        long activePostOffices = postOfficeService.getAllPostOffices()
                .stream()
                .filter(PostOffice::isActive)
                .count();
        System.out.println("this is what"+activePostOffices);
        model.addAttribute("activePostOfficeCount",activePostOffices);

        //Parcels grouped by status
        List<Parcel> allParcels = parcelService.getAllParcel(null);
        Map<String, Long> parcelStatusCount = allParcels
                .stream()
                .collect(Collectors.groupingBy(
                        parcel -> parcel.getCurrent_status().name(),
                        Collectors.counting()
                ));
        model.addAttribute("parcelStatusCount", parcelStatusCount);
        
        //Total parcels count
        model.addAttribute("totalParcels", allParcels.size());

        //Recent parcel updates
        List<ParcelHistory> recentUpdates = parcelService.getRecentParcelUpdates(10);
        model.addAttribute("recentUpdates", recentUpdates);

        return "dashboard";
    }

}
