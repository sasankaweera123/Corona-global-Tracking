package io.javabrains.coronavirustracker.controllers;

import io.javabrains.coronavirustracker.models.LocationStat;
import io.javabrains.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class HomeController {

    public static String withLargeIntegers(long value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){

        List <LocationStat> allStat = coronaVirusDataService.getAllStat();

        long totalReportedCases = allStat.stream().mapToInt(stat -> stat.getLatestCases()).sum();

        long totalNewCases = allStat.stream().mapToInt(stat -> stat.getDiffFromPast()).sum();

        model.addAttribute("locationStat", allStat);
        model.addAttribute("totalReportedCases", withLargeIntegers(totalReportedCases));
        model.addAttribute("yesterdayTotalCases", withLargeIntegers(totalNewCases));
        return "home";
    }
}
