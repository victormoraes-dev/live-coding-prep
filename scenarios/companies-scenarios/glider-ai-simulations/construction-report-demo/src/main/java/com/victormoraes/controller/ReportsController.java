package com.victormoraes.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victormoraes.dto.request.ReportRequest;
import com.victormoraes.dto.response.ReportResponse;
import com.victormoraes.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ReportsController {

    private final ReportService reportService;

    public ReportsController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Example request body:
     * 
     * <pre>
     * {
     *   "projectName": "Downtown Office Tower",
     *   "clientName": "Acme Construction",
     *   "status": "DRAFT",
     *   "budget": 2500000.00,
     *   "actualCost": 1750000.00
     * }
     * </pre>
     */
    @PostMapping("/reports")
    public ResponseEntity<ReportResponse> getReport(@RequestBody @Valid ReportRequest body) {
        var response = reportService.generateReport(body);
        return ResponseEntity.created(URI.create(String.format("/api/v1/reports/%s", response.getId()))).body(response);
    }

    @GetMapping("/reports/{id}")
    public ReportResponse getReportById(@PathVariable("id") Long id) {
        return reportService.getReportById(id);
    }
}
