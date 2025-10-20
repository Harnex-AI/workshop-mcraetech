# Audit Log Specification for Emergency Contact Notifications

## Purpose
Define the audit logging requirements for emergency contact notifications to ensure compliance with HIPAA, GDPR, and internal security policies.

## Regulatory Requirements

### HIPAA (Health Insurance Portability and Accountability Act)
- **§164.528 Accounting of Disclosures:** Must track all PHI disclosures
- **§164.308(a)(1)(ii)(D) Information System Activity Review:** Must log access and modifications
- **Retention:** Minimum 6 years from creation or last use

### GDPR (General Data Protection Regulation)
- **Article 30 Records of Processing:** Must maintain records of processing activities
- **Article 33 Breach Notification:** Must detect and report breaches within 72 hours
- **Retention:** As long as necessary for the purpose, minimum 7 years for healthcare

### Internal Policy
- **Retention:** 7 years (exceeds both HIPAA and GDPR)
- **Access:** Restricted to authorized personnel with `AUDIT_VIEW` permission
- **Immutability:** Audit logs cannot be modified or deleted
- **Encryption:** At rest and in transit

---

## Audit Log Schema

### Required Fields

```java
public class NotificationAuditLog {
    
    // Unique identifier for this audit entry
    @Id
    private String auditId;
    
    // When the notification was triggered (UTC)
    @Column(nullable = false)
    private Instant timestamp;
    
    // Patient reference (not full patient object)
    @Column(nullable = false)
    private String patientRef;
    
    // Emergency contact reference
    @Column(nullable = false)
    private String emergencyContactId;
    
    // Type of notification (ADMISSION, STATUS_CHANGE, DISCHARGE, etc.)
    @Column(nullable = false)
    private NotificationType notificationType;
    
    // Consent scope used for this notification
    @Column(nullable = false)
    private String consentScope;
    
    // Was consent valid at time of notification?
    @Column(nullable = false)
    private Boolean consentValid;
    
    // Consent expiry date (if applicable)
    private Instant consentExpiryDate;
    
    // List of PHI fields shared in this notification
    @Column(nullable = false)
    private List<String> phiFieldsShared;
    
    // Delivery method (SMS, EMAIL, PHONE)
    @Column(nullable = false)
    private DeliveryMethod deliveryMethod;
    
    // Delivery status (SENT, FAILED, RETRYING, CANCELLED)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;
    
    // Error message if delivery failed (no PHI!)
    private String errorMessage;
    
    // User who triggered the notification (if manual)
    private String triggeredByUserId;
    
    // System component that triggered (if automatic)
    private String triggeredBySystem;
    
    // Correlation ID for tracing across systems
    @Column(nullable = false)
    private String correlationId;
    
    // IP address of the system that triggered (for security)
    private String sourceIpAddress;
    
    // Retry attempt number (0 for first attempt)
    @Column(nullable = false)
    private Integer retryAttempt;
    
    // Notification content hash (for integrity verification)
    private String contentHash;
}
```

---

## Logging Events

### Event 1: Notification Triggered
**When:** Notification process starts
**Log:**
```java
auditLogger.logNotificationTriggered(
    patientRef: "PAT_12345",
    contactId: "EC_001",
    notificationType: NotificationType.ADMISSION,
    triggeredBy: "SYSTEM_ADMISSION_MONITOR",
    correlationId: "CORR_ABC123"
);
```

### Event 2: Consent Validated
**When:** Consent check completes
**Log:**
```java
auditLogger.logConsentValidation(
    patientRef: "PAT_12345",
    consentScope: "EMERGENCY_CONTACT_NOTIFY",
    consentValid: true,
    consentExpiryDate: Instant.parse("2025-01-15T00:00:00Z"),
    correlationId: "CORR_ABC123"
);
```

### Event 3: PHI Shared
**When:** Notification content is built
**Log:**
```java
auditLogger.logPhiShared(
    patientRef: "PAT_12345",
    contactId: "EC_001",
    phiFields: List.of("patientName", "generalStatus", "admissionTime", "facilityName"),
    consentScope: "EMERGENCY_CONTACT_NOTIFY",
    correlationId: "CORR_ABC123"
);
```

### Event 4: Notification Sent
**When:** Delivery attempt completes
**Log:**
```java
auditLogger.logNotificationSent(
    patientRef: "PAT_12345",
    contactId: "EC_001",
    deliveryMethod: DeliveryMethod.SMS,
    deliveryStatus: DeliveryStatus.SENT,
    retryAttempt: 0,
    correlationId: "CORR_ABC123"
);
```

### Event 5: Delivery Failed
**When:** Delivery fails
**Log:**
```java
auditLogger.logDeliveryFailure(
    patientRef: "PAT_12345",
    contactId: "EC_001",
    deliveryMethod: DeliveryMethod.SMS,
    errorMessage: "SMS gateway timeout", // No PHI in error!
    retryAttempt: 0,
    willRetry: true,
    correlationId: "CORR_ABC123"
);
```

---

## Implementation Example

```java
@Service
public class NotificationAuditLogger {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private SecurityContext securityContext;
    
    public void logNotificationSent(
        String patientRef,
        String contactId,
        NotificationType type,
        String consentScope,
        List<String> phiFieldsShared,
        DeliveryMethod deliveryMethod,
        DeliveryStatus status,
        String correlationId
    ) {
        NotificationAuditLog log = new NotificationAuditLog();
        
        // Required fields
        log.setAuditId(UUID.randomUUID().toString());
        log.setTimestamp(Instant.now()); // Always UTC
        log.setPatientRef(patientRef);
        log.setEmergencyContactId(contactId);
        log.setNotificationType(type);
        log.setConsentScope(consentScope);
        log.setPhiFieldsShared(phiFieldsShared);
        log.setDeliveryMethod(deliveryMethod);
        log.setDeliveryStatus(status);
        log.setCorrelationId(correlationId);
        
        // Context fields
        log.setTriggeredByUserId(securityContext.getCurrentUserId());
        log.setSourceIpAddress(securityContext.getSourceIp());
        log.setRetryAttempt(0);
        
        // Save synchronously (cannot be lost!)
        auditLogRepository.save(log);
        
        // Also log to security monitoring system
        securityMonitor.recordPhiDisclosure(log);
    }
    
    public void logDeliveryFailure(
        String patientRef,
        String contactId,
        DeliveryMethod deliveryMethod,
        String errorMessage,
        int retryAttempt,
        String correlationId
    ) {
        NotificationAuditLog log = new NotificationAuditLog();
        
        log.setAuditId(UUID.randomUUID().toString());
        log.setTimestamp(Instant.now());
        log.setPatientRef(patientRef);
        log.setEmergencyContactId(contactId);
        log.setDeliveryMethod(deliveryMethod);
        log.setDeliveryStatus(DeliveryStatus.FAILED);
        log.setRetryAttempt(retryAttempt);
        log.setCorrelationId(correlationId);
        
        // CRITICAL: Sanitize error message (no PHI!)
        log.setErrorMessage(sanitizeErrorMessage(errorMessage));
        
        auditLogRepository.save(log);
        
        // Alert if multiple failures
        if (retryAttempt >= 2) {
            alertingService.notifyDeliveryFailure(patientRef, contactId);
        }
    }
    
    private String sanitizeErrorMessage(String errorMessage) {
        // Remove any potential PHI from error messages
        // Keep only technical error codes and generic messages
        return errorMessage
            .replaceAll("\\b[A-Z]{2,}\\d{4,}\\b", "[REDACTED]") // Patient IDs
            .replaceAll("\\b\\d{3}-\\d{2}-\\d{4}\\b", "[REDACTED]") // SSN
            .replaceAll("\\b[A-Za-z]+\\s[A-Za-z]+\\b", "[REDACTED]"); // Names
    }
}
```

---

## Query Examples

### Get All Notifications for a Patient
```java
@Query("SELECT a FROM NotificationAuditLog a WHERE a.patientRef = :patientRef ORDER BY a.timestamp DESC")
List<NotificationAuditLog> findByPatientRef(@Param("patientRef") String patientRef);
```

### Get Failed Notifications Requiring Retry
```java
@Query("SELECT a FROM NotificationAuditLog a WHERE a.deliveryStatus = 'FAILED' AND a.retryAttempt < 3 AND a.timestamp > :since")
List<NotificationAuditLog> findFailedNotificationsForRetry(@Param("since") Instant since);
```

### Get PHI Disclosures for Compliance Report
```java
@Query("SELECT a FROM NotificationAuditLog a WHERE a.timestamp BETWEEN :start AND :end AND SIZE(a.phiFieldsShared) > 0")
List<NotificationAuditLog> findPhiDisclosures(
    @Param("start") Instant start,
    @Param("end") Instant end
);
```

---

## Security Requirements

### 1. Access Control
- Only users with `AUDIT_VIEW` permission can query audit logs
- Queries must be logged themselves (audit the auditors)
- No bulk export without approval

### 2. Immutability
- Audit logs cannot be updated or deleted
- Use append-only database or write-once storage
- Implement database triggers to prevent modifications

### 3. Encryption
- Encrypt at rest using AES-256
- Encrypt in transit using TLS 1.3
- Encrypt backups

### 4. Integrity
- Calculate hash of notification content
- Store hash in audit log
- Verify hash on retrieval to detect tampering

---

## Monitoring & Alerting

### Alert Conditions

1. **High Failure Rate**
   - Trigger: >10% of notifications fail in 1 hour
   - Action: Alert operations team

2. **Consent Violations**
   - Trigger: Notification sent with expired consent
   - Action: Alert security team immediately

3. **Unusual Volume**
   - Trigger: >100 notifications in 5 minutes
   - Action: Alert for potential system issue

4. **PHI Exposure Risk**
   - Trigger: Error message contains potential PHI
   - Action: Alert security team, quarantine log

---

## Compliance Reporting

### Monthly Report: PHI Disclosures
```sql
SELECT 
    DATE_TRUNC('day', timestamp) as disclosure_date,
    COUNT(*) as total_notifications,
    COUNT(DISTINCT patient_ref) as unique_patients,
    COUNT(DISTINCT emergency_contact_id) as unique_contacts,
    consent_scope,
    delivery_method
FROM notification_audit_log
WHERE timestamp >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '1 month')
  AND timestamp < DATE_TRUNC('month', CURRENT_DATE)
  AND delivery_status = 'SENT'
GROUP BY disclosure_date, consent_scope, delivery_method
ORDER BY disclosure_date DESC;
```

### Quarterly Report: Consent Compliance
```sql
SELECT 
    consent_valid,
    COUNT(*) as notification_count,
    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) as percentage
FROM notification_audit_log
WHERE timestamp >= CURRENT_DATE - INTERVAL '3 months'
GROUP BY consent_valid;
```

---

## Testing Requirements

### Test Case 1: Audit Log Created
```java
@Test
public void testNotificationSent_CreatesAuditLog() {
    // When
    service.sendNotification(patient, contact, event, consent);
    
    // Then
    List<NotificationAuditLog> logs = auditLogRepository.findByPatientRef(patient.getRef());
    assertThat(logs).hasSize(1);
    assertThat(logs.get(0).getDeliveryStatus()).isEqualTo(DeliveryStatus.SENT);
    assertThat(logs.get(0).getPhiFieldsShared()).contains("patientName");
}
```

### Test Case 2: Failed Delivery Logged
```java
@Test
public void testDeliveryFailure_LogsError() {
    // Given
    when(smsGateway.send(any())).thenThrow(new GatewayException("Timeout"));
    
    // When
    service.sendNotification(patient, contact, event, consent);
    
    // Then
    List<NotificationAuditLog> logs = auditLogRepository.findByPatientRef(patient.getRef());
    assertThat(logs.get(0).getDeliveryStatus()).isEqualTo(DeliveryStatus.FAILED);
    assertThat(logs.get(0).getErrorMessage()).doesNotContain(patient.getName()); // No PHI!
}
```

### Test Case 3: Audit Log Immutable
```java
@Test
public void testAuditLog_CannotBeModified() {
    // Given
    NotificationAuditLog log = createAuditLog();
    auditLogRepository.save(log);
    
    // When/Then
    assertThatThrownBy(() -> {
        log.setDeliveryStatus(DeliveryStatus.SENT);
        auditLogRepository.save(log);
    }).isInstanceOf(ImmutableEntityException.class);
}
```

---

## Best Practices

1. **Log Synchronously:** Never lose audit logs due to async failures
2. **No PHI in Errors:** Sanitize all error messages before logging
3. **Use Correlation IDs:** Trace notifications across systems
4. **Hash Content:** Verify integrity of notification content
5. **Monitor Failures:** Alert on unusual patterns
6. **Encrypt Everything:** At rest, in transit, in backups
7. **Restrict Access:** Audit the auditors
8. **Retain Long-Term:** 7 years minimum

