package com.victormoraes.service;

import org.springframework.stereotype.Service;

import com.victormoraes.dto.request.ReportRequest;
import com.victormoraes.dto.response.ReportResponse;
import com.victormoraes.model.entity.ReportEntity;
import com.victormoraes.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportResponse generateReport(ReportRequest request) {

        var entity = new ReportEntity(
                request.getProjectName(),
                request.getClientName(),
                request.getStatus(),
                request.getBudget(),
                request.getActualCost());

        var savedEntity = this.reportRepository.save(entity);

        return new ReportResponse(
                savedEntity.getId(),
                savedEntity.getProjectName(),
                savedEntity.getClientName(),
                savedEntity.getStatus(),
                savedEntity.getBudget(),
                savedEntity.getActualCost(),
                savedEntity.getCompletionDate(),
                savedEntity.getCreatedAt(),
                savedEntity.getUpdatedAt());
    }

    public ReportResponse getReportById(Long id) {

        var savedEntity = this.reportRepository.getReferenceById(id);

        return new ReportResponse(
                savedEntity.getId(),
                savedEntity.getProjectName(),
                savedEntity.getClientName(),
                savedEntity.getStatus(),
                savedEntity.getBudget(),
                savedEntity.getActualCost(),
                savedEntity.getCompletionDate(),
                savedEntity.getCreatedAt(),
                savedEntity.getUpdatedAt());
    }
}
