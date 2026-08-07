package com.victormoraes.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.victormoraes.model.enums.ReportStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reports")
@EntityListeners(AuditingEntityListener.class)
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Size(max = 200, message = "Project name must be at most 200 characters long")
    private String projectName;

    @Column(nullable = false)
    @NotBlank(message = "Client Name is required")
    private String clientName;

    @Column
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Column
    @PositiveOrZero(message = "Budget must be greater than or equal to 0")
    private Double budget;

    @Column
    private Double actualCost;

    @Column
    private LocalDate completionDate;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ReportEntity() {
    }

    public ReportEntity(String projectName, String clientName, ReportStatus status, Double budget, Double actualCost) {
        this.projectName = projectName;
        this.clientName = clientName;
        this.status = status;
        this.budget = budget;
        this.actualCost = actualCost;
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

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

}
