package com.mccrae.healthcare.audit;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Audit Log entity - immutable record of all system actions
 * 
 * Note: This entity is IMMUTABLE - no updates or deletes allowed
 */
@Entity
@Table(name = "audit_logs")
public class AuditLog {
    
    @Id
    private UUID id;
    
    @Column(nullable = false)
    private Instant timestamp; // Always UTC
    
    @Column(nullable = false)
    private String eventType; // e.g., "PATIENT_CREATED", "CONSENT_VALIDATED", "NOTIFICATION_SENT"
    
    @Column(nullable = false)
    private String patientRef; // Patient reference ID (safe to log)
    
    @Column(length = 1000)
    private String details; // Additional details (NO PHI!)
    
    @Column
    private String userId; // User who performed the action (if applicable)
    
    @Column
    private String correlationId; // For tracing across services
    
    // Constructors
    public AuditLog() {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
    }
    
    public AuditLog(String eventType, String patientRef, String details) {
        this();
        this.eventType = eventType;
        this.patientRef = patientRef;
        this.details = details;
    }
    
    // Getters only - no setters after creation (immutable)
    public UUID getId() {
        return id;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public String getPatientRef() {
        return patientRef;
    }
    
    public String getDetails() {
        return details;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getCorrelationId() {
        return correlationId;
    }
    
    // Setters only for initial creation
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public void setPatientRef(String patientRef) {
        this.patientRef = patientRef;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}

