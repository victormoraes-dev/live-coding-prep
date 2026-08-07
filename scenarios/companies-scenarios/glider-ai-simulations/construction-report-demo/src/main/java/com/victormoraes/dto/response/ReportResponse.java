package com.victormoraes.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.victormoraes.model.enums.ReportStatus;

public class ReportResponse {

    private Long id;
    private String projectName;
    private String clientName;
    private ReportStatus status;
    private Double budget;
    private Double actualCost;
    private LocalDate completionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReportResponse(Long id, String projectName, String clientName, ReportStatus status, Double budget,
            Double actualCost, LocalDate completionDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.projectName = projectName;
        this.clientName = clientName;
        this.status = status;
        this.budget = budget;
        this.actualCost = actualCost;
        this.completionDate = completionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public Double getBudget() {
        return budget;
    }

    public Double getActualCost() {
        return actualCost;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
