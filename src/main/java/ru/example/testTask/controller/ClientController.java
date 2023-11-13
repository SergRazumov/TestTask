package ru.example.testTask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.testTask.dao.RecordDAO;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final RecordDAO recordDAO;

    public ClientController(RecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    @GetMapping
    public String viewPage() {
        return "clientPage.html";
    }

    @PostMapping("/addRecord")
    public String addRecord(@RequestParam String name, @RequestParam LocalDate dateRecord, @RequestParam LocalTime timeRecord, @RequestParam Integer idServices, ModelMap model) {
        String result =  recordDAO.addRecord(name, dateRecord, timeRecord, idServices);
        if(result.equals("1"))  return "redirect:/client";
        model.addAttribute("message", result);
        return "errorPage.html";

    }
    @GetMapping("/timeLeft")
    public String timeLeft(@RequestParam Integer id, ModelMap model) {
        String result = recordDAO.timeLeft(id);
        model.addAttribute("message", result);
        if(result.equals("1")) return "timeLeftPage.html";
        return "errorPage.html";
    }
    @GetMapping("/viewAllRecord")
    public String viewAllRecord(ModelMap model) {
        model.addAttribute("records", recordDAO.showAll());
        return "recordsPage.html";
    }

}
