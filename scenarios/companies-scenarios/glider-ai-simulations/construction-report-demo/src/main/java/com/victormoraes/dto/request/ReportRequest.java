package com.victormoraes.dto.request;

import com.victormoraes.model.enums.ReportStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ReportRequest {

    @Size(max = 200)
    @NotBlank
    private String projectName;

    @NotBlank
    private String clientName;

    private ReportStatus status = ReportStatus.DRAFT;

    @PositiveOrZero
    private Double budget;

    private Double actualCost;

    public ReportRequest(
            String projectName,
            String clientName,
            ReportStatus status,
            Double budget,
            Double actualCost) {
        this.projectName = projectName;
        this.clientName = clientName;
        this.status = status;
        this.budget = budget;
        this.actualCost = actualCost;
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
}
