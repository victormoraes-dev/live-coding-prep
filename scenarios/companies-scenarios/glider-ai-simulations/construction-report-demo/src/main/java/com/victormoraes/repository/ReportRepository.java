package com.victormoraes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victormoraes.model.entity.ReportEntity;
import com.victormoraes.model.enums.ReportStatus;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    
    List<ReportEntity> findByStatus(ReportStatus status);
}
