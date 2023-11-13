package ru.example.testTask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.example.testTask.dao.ServiceDAO;

import java.time.LocalTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceDAO serviceDAO;
    private AdminController(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    @GetMapping
    public String viewPage() {
        return "adminPage.html";
    }

    @PostMapping("/addService")
    public String addService(@RequestParam String name, @RequestParam LocalTime time,  @RequestParam Integer identifier, ModelMap model) {
      String result = serviceDAO.addService(name, time, identifier);
      if(result.equals("1")) return  "redirect:/admin";
      result = result.split("Подробности: ")[1];
      model.addAttribute("message", result);
      return "errorPage.html";
    }

    @PostMapping("/removeService")
    public String removeService(@RequestParam Integer removeService, ModelMap model) {
        String result = serviceDAO.removeService(removeService);
        if(result.equals("1")) return  "redirect:/admin";
        model.addAttribute("message", "Удаление не удалось");
        return "errorPage.html";
    }

    @GetMapping("/viewAllService")
    public String viewAllService(ModelMap model) {
        model.addAttribute("services", serviceDAO.showAll());
        return "servicesPage.html";
    }

}
