package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

    public SearchController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");


        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());
    }

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        ArrayList<Job> jobs;
        if (searchTerm.equals("all") || searchTerm.equals("")){
            jobs = JobData.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType,searchTerm);
        }
        model.addAttribute("selectedSearchType", searchType);
        model.addAttribute("jobs",jobs);
        model.addAttribute("columns", columnChoices);
        return "search";
    }
}
