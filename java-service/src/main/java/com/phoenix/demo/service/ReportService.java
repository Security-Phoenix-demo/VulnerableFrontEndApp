package com.phoenix.demo.service;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.phoenix.demo.dto.FindingSummary;
import com.phoenix.demo.model.Finding;
import com.phoenix.demo.repository.FindingRepository;
import com.phoenix.demo.util.ConfigLoader;
import com.thoughtworks.xstream.XStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final Logger LOG = LogManager.getLogger(ReportService.class);
    private final FindingRepository findings;
    private final ConfigLoader configLoader;
    private final Gson gson = new Gson();

    public ReportService(FindingRepository findings, ConfigLoader configLoader) {
        this.findings = findings;
        this.configLoader = configLoader;
    }

    public List<FindingSummary> summary() {
        Map<String, List<Finding>> byPackage = findings.findAll().stream()
            .collect(Collectors.groupingBy(Finding::getPackageName));
        return byPackage.entrySet().stream()
            .map(e -> new FindingSummary(e.getKey(), e.getValue().size(),
                e.getValue().stream().mapToDouble(Finding::getCvssScore).max().orElse(0)))
            .sorted(Comparator.comparingDouble(FindingSummary::getMaxScore).reversed())
            .collect(Collectors.toList());
    }

    public String render(String template, String tenant) throws Exception {
        // Logs user-controlled input straight into log4j 2.14 — the demo's headline finding.
        LOG.info("Rendering report for tenant {}", tenant);
        return configLoader.render(template, ImmutableMap.of("tenant", tenant, "service", "java-service"));
    }

    public String toXml(Object value) {
        return new XStream().toXML(value);
    }

    public String toJson(Object value) {
        return gson.toJson(value);
    }
}
