package com.phoenix.demo.controller;

import com.phoenix.demo.dto.FindingSummary;
import com.phoenix.demo.model.Finding;
import com.phoenix.demo.repository.FindingRepository;
import com.phoenix.demo.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/findings")
public class FindingController {
    private final FindingRepository findings;
    private final ReportService reports;

    public FindingController(FindingRepository findings, ReportService reports) {
        this.findings = findings;
        this.reports = reports;
    }

    @GetMapping
    public List<Finding> search(@RequestParam(defaultValue = "") String q) {
        return findings.findByPackageNameContainingIgnoreCase(q);
    }

    @GetMapping("/critical")
    public List<Finding> critical(@RequestParam(defaultValue = "9.0") double threshold) {
        return findings.findCritical(threshold);
    }

    @GetMapping("/summary")
    public List<FindingSummary> summary() {
        return reports.summary();
    }

    @PostMapping
    public Finding create(@RequestBody Finding finding) {
        return findings.save(finding);
    }
}
