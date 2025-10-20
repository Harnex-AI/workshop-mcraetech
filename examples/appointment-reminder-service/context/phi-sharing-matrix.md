# PHI Sharing Matrix for Emergency Contact Notifications

## Purpose
This document defines exactly what Protected Health Information (PHI) can be shared with emergency contacts under different consent scenarios.

## Consent Scopes

### 1. `EMERGENCY_CONTACT_NOTIFY` (Standard)
**Description:** Patient consents to notify emergency contacts of admission and general status.

**Allowed PHI:**
- ✅ Patient first name and last name
- ✅ Facility name and location
- ✅ General status (stable, critical, recovering, discharged)
- ✅ Admission date and time (in contact's timezone)
- ✅ Expected discharge date (approximate)
- ✅ Care team contact information (phone, email)
- ✅ Visiting hours and policies

**Prohibited PHI:**
- ❌ Date of birth
- ❌ Social security number
- ❌ Medical record number
- ❌ Specific diagnosis or condition
- ❌ Lab results or test results
- ❌ Treatment details or medications
- ❌ Insurance information
- ❌ Billing information
- ❌ Home address
- ❌ Other medical history

**Example Notification:**
```
John Smith has been admitted to Auckland City Hospital Emergency Department.
Current status: Stable
Admitted: 15 Jan 2024, 3:00 PM NZDT
For information, contact the care team at +64-9-555-0100.
Visiting hours: 10 AM - 8 PM daily.
```

---

### 2. `EMERGENCY_CONTACT_NOTIFY_DETAILED` (Enhanced)
**Description:** Patient consents to share more detailed information with emergency contacts.

**Allowed PHI (in addition to standard):**
- ✅ General reason for admission (e.g., "chest pain", "fall", "respiratory issue")
- ✅ Department/unit (Emergency, ICU, Surgery, etc.)
- ✅ Scheduled procedures (general description, not details)
- ✅ Expected length of stay (specific dates)
- ✅ Discharge planning status

**Still Prohibited:**
- ❌ Specific diagnosis codes (ICD-10)
- ❌ Lab values or test results
- ❌ Medication names or dosages
- ❌ Detailed treatment plans
- ❌ Prognosis or medical opinions

**Example Notification:**
```
John Smith has been admitted to Auckland City Hospital Emergency Department.
Reason: Chest pain evaluation
Current status: Stable, transferred to Cardiology unit
Admitted: 15 Jan 2024, 3:00 PM NZDT
Expected stay: 2-3 days
Scheduled: Cardiac stress test on 16 Jan 2024
For information, contact Cardiology at +64-9-555-0150.
```

---

### 3. No Consent or Expired Consent
**Description:** Patient has not provided consent or consent has expired.

**Allowed Information (NOT PHI):**
- ✅ Facility name and general contact number
- ✅ Visiting hours (general policy)
- ✅ Instructions to contact facility for information

**Prohibited:**
- ❌ Patient name
- ❌ Any admission details
- ❌ Any status information
- ❌ Any dates or times

**Example Notification:**
```
A patient has listed you as an emergency contact.
For information, please contact Auckland City Hospital at +64-9-555-0100.
Please provide the patient's name and date of birth when calling.
Visiting hours: 10 AM - 8 PM daily.
```

---

## Implementation Guidelines

### Code Example: Consent-Based PHI Filtering

```java
public class EmergencyContactNotification {
    
    public NotificationContent buildNotification(
        Patient patient,
        EmergencyContact contact,
        AdmissionEvent event,
        ConsentContext consent
    ) {
        NotificationContent content = new NotificationContent();
        
        // Always allowed (non-PHI)
        content.setFacilityName("Auckland City Hospital");
        content.setFacilityPhone("+64-9-555-0100");
        content.setVisitingHours("10 AM - 8 PM daily");
        
        // Check consent scope
        if (consent == null || consent.isExpired()) {
            // No consent: minimal notification
            content.setMessage(buildMinimalMessage());
            return content;
        }
        
        if (consent.hasScope("EMERGENCY_CONTACT_NOTIFY")) {
            // Standard consent: basic PHI
            content.setPatientName(patient.getFullName());
            content.setGeneralStatus(event.getGeneralStatus());
            content.setAdmissionTime(
                convertToContactTimezone(event.getAdmissionTime(), contact.getTimezone())
            );
            content.setCareTeamContact(event.getCareTeamPhone());
        }
        
        if (consent.hasScope("EMERGENCY_CONTACT_NOTIFY_DETAILED")) {
            // Enhanced consent: additional PHI
            content.setAdmissionReason(event.getGeneralReason());
            content.setDepartment(event.getDepartment());
            content.setExpectedStay(event.getExpectedLengthOfStay());
            
            if (event.hasScheduledProcedures()) {
                content.setScheduledProcedures(
                    event.getScheduledProcedures()
                         .stream()
                         .map(p -> p.getGeneralDescription()) // Not detailed
                         .collect(Collectors.toList())
                );
            }
        }
        
        return content;
    }
    
    private String buildMinimalMessage() {
        return "A patient has listed you as an emergency contact. " +
               "For information, please contact Auckland City Hospital at +64-9-555-0100. " +
               "Please provide the patient's name and date of birth when calling.";
    }
}
```

---

## Audit Logging Requirements

Every notification must log exactly what PHI was shared:

```java
public class NotificationAuditLog {
    private String notificationId;
    private Instant timestamp;
    private String patientRef;
    private String emergencyContactId;
    private String consentScope;
    private List<String> phiFieldsShared; // CRITICAL: Log what was shared
    private String deliveryMethod;
    private String deliveryStatus;
    
    // Example PHI fields shared
    // ["patientName", "generalStatus", "admissionTime", "facilityName"]
}
```

**Why This Matters:**
- HIPAA requires tracking of PHI disclosures
- Auditors need to verify minimum necessary standard
- Patients have right to access disclosure history
- Compliance investigations require detailed logs

---

## Testing Matrix

### Test Case 1: Standard Consent
```java
@Test
public void testStandardConsent_SharesBasicPHI() {
    // Given
    Consent consent = createConsent("EMERGENCY_CONTACT_NOTIFY");
    Patient patient = createTestPatient("TEST_PAT_001");
    EmergencyContact contact = createTestContact("TEST_CONTACT_001");
    
    // When
    NotificationContent content = service.buildNotification(patient, contact, event, consent);
    
    // Then
    assertThat(content.getPatientName()).isEqualTo("TEST_PAT_001");
    assertThat(content.getGeneralStatus()).isNotNull();
    assertThat(content.getAdmissionReason()).isNull(); // Not in standard scope
    assertThat(content.getDiagnosis()).isNull(); // Never allowed
}
```

### Test Case 2: No Consent
```java
@Test
public void testNoConsent_SharesMinimalInfo() {
    // Given
    Consent consent = null; // No consent
    
    // When
    NotificationContent content = service.buildNotification(patient, contact, event, consent);
    
    // Then
    assertThat(content.getPatientName()).isNull();
    assertThat(content.getGeneralStatus()).isNull();
    assertThat(content.getMessage()).contains("A patient has listed you as an emergency contact");
}
```

### Test Case 3: Expired Consent
```java
@Test
public void testExpiredConsent_SharesMinimalInfo() {
    // Given
    Consent consent = createExpiredConsent("EMERGENCY_CONTACT_NOTIFY");
    
    // When
    NotificationContent content = service.buildNotification(patient, contact, event, consent);
    
    // Then
    assertThat(content.getPatientName()).isNull();
    assertThat(content.getMessage()).contains("A patient has listed you as an emergency contact");
}
```

---

## Common Mistakes to Avoid

### ❌ Mistake 1: Logging Full Patient Object
```java
// WRONG - Logs all PHI
_logger.LogInformation("Notifying contact for patient {@patient}", patient);
```

### ✅ Correct: Log Reference ID Only
```java
// CORRECT - Logs reference only
_logger.LogInformation("Notifying contact for patient {PatientRef}", patient.getReferenceId());
```

---

### ❌ Mistake 2: Sharing PHI in Error Messages
```java
// WRONG - PHI in exception message
throw new NotificationException(
    "Failed to notify contact for patient " + patient.getName()
);
```

### ✅ Correct: Generic Error, PHI in Audit Log
```java
// CORRECT - Generic error, detailed audit log
_auditLogger.logFailure(patient.getReferenceId(), contact.getId(), "Delivery failed");
throw new NotificationException("Failed to deliver notification. See audit log for details.");
```

---

### ❌ Mistake 3: Not Checking Consent Expiry
```java
// WRONG - Assumes consent is valid
if (consent.hasScope("EMERGENCY_CONTACT_NOTIFY")) {
    content.setPatientName(patient.getName());
}
```

### ✅ Correct: Always Check Expiry
```java
// CORRECT - Check both scope and expiry
if (consent != null && 
    !consent.isExpired() && 
    consent.hasScope("EMERGENCY_CONTACT_NOTIFY")) {
    content.setPatientName(patient.getName());
}
```

---

## Quick Reference Table

| PHI Field | No Consent | Standard Consent | Detailed Consent |
|-----------|------------|------------------|------------------|
| Patient Name | ❌ | ✅ | ✅ |
| Facility Name | ✅ | ✅ | ✅ |
| General Status | ❌ | ✅ | ✅ |
| Admission Time | ❌ | ✅ | ✅ |
| Admission Reason | ❌ | ❌ | ✅ |
| Department/Unit | ❌ | ❌ | ✅ |
| Expected Stay | ❌ | ✅ (approx) | ✅ (specific) |
| Scheduled Procedures | ❌ | ❌ | ✅ (general) |
| Diagnosis | ❌ | ❌ | ❌ |
| Lab Results | ❌ | ❌ | ❌ |
| Medications | ❌ | ❌ | ❌ |
| Medical History | ❌ | ❌ | ❌ |

---

## Compliance Notes

- **HIPAA Minimum Necessary:** Only share PHI required for the purpose
- **GDPR Data Minimization:** Collect and share only what's needed
- **Consent Specificity:** Separate consents for different levels of sharing
- **Audit Trail:** Log every disclosure with timestamp and scope
- **Right to Revoke:** Patient can revoke consent at any time
- **Retention:** Audit logs must be kept for 7 years

