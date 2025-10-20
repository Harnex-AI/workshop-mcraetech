package com.mccrae.healthcare.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    
    List<AuditLog> findByPatientRef(String patientRef);
    
    List<AuditLog> findByEventType(String eventType);
    
    List<AuditLog> findByTimestampBetween(Instant start, Instant end);
    
    List<AuditLog> findByPatientRefAndEventType(String patientRef, String eventType);
}

