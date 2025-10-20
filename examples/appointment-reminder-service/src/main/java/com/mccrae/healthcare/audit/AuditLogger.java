package com.mccrae.healthcare.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Audit Logger - creates audit log entries for compliance
 * 
 * This is a core service that other features will use.
 * Example: Emergency Contact notifications will use this to log notifications.
 */
@Service
public class AuditLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditLogger.class);
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    // Patient events
    public void logPatientCreated(String patientRef) {
        log("PATIENT_CREATED", patientRef, "Patient record created");
    }
    
    public void logPatientAccessed(String patientRef) {
        log("PATIENT_ACCESSED", patientRef, "Patient record accessed");
    }
    
    public void logPatientUpdated(String patientRef) {
        log("PATIENT_UPDATED", patientRef, "Patient record updated");
    }
    
    public void logPatientDeleted(String patientRef) {
        log("PATIENT_DELETED", patientRef, "Patient record deleted");
    }
    
    // Consent events
    public void logConsentGranted(String patientRef, String scope) {
        log("CONSENT_GRANTED", patientRef, "Consent granted for scope: " + scope);
    }
    
    public void logConsentValidated(String patientRef, String scope, boolean valid) {
        String details = valid ? "Consent valid for scope: " + scope : "Consent invalid for scope: " + scope;
        log("CONSENT_VALIDATED", patientRef, details);
    }
    
    public void logConsentRevoked(String patientRef) {
        log("CONSENT_REVOKED", patientRef, "Consent revoked");
    }
    
    // Generic log method
    public void log(String eventType, String patientRef, String details) {
        // ✅ CORRECT: Log to application log (reference ID only)
        logger.info("Audit: {} for patient {} - {}", eventType, patientRef, details);
        
        // Save to database (synchronously - cannot be lost!)
        AuditLog auditLog = new AuditLog(eventType, patientRef, details);
        auditLogRepository.save(auditLog);
    }
    
    // Log with correlation ID (for tracing across services)
    public void log(String eventType, String patientRef, String details, String correlationId) {
        logger.info("Audit: {} for patient {} - {} [correlation: {}]", 
                eventType, patientRef, details, correlationId);
        
        AuditLog auditLog = new AuditLog(eventType, patientRef, details);
        auditLog.setCorrelationId(correlationId);
        auditLogRepository.save(auditLog);
    }
}

