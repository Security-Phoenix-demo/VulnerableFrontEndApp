package com.phoenix.demo.controller;

import com.phoenix.demo.service.ReportService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/public/reports")
public class ReportController {
    private final ReportService reports;

    public ReportController(ReportService reports) {
        this.reports = reports;
    }

    /** Renders a YAML template uploaded as multipart — commons-fileupload 1.3 and commons-io 2.6 do the parsing. */
    @PostMapping(value = "/render", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String render(HttpServletRequest request, @RequestParam(defaultValue = "acme") String tenant) throws Exception {
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        String template = items.isEmpty() ? "{}" : IOUtils.toString(items.get(0).getInputStream(), StandardCharsets.UTF_8);
        return reports.render(template, tenant);
    }

    @PostMapping(value = "/export", produces = MediaType.APPLICATION_XML_VALUE)
    public String export() {
        return reports.toXml(reports.summary());
    }
}
