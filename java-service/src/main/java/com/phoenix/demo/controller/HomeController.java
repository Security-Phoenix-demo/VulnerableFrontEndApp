package com.phoenix.demo.controller;

import com.phoenix.demo.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ReportService reports;

    public HomeController(ReportService reports) {
        this.reports = reports;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("summary", reports.summary());
        return "index";
    }
}
