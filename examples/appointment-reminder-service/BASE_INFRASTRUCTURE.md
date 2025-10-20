# Base Healthcare Infrastructure

## ✅ What's Already Built

This is a **complete, working healthcare system** with the following features already implemented and tested:

### 1. Patient Management
- **Entity:** `Patient.java` - Patient records with PHI protection
- **Repository:** `PatientRepository.java` - Database access
- **Service:** `PatientService.java` - Business logic with proper logging
- **Controller:** `PatientController.java` - REST API
- **Tests:** `PatientServiceTest.java` - Comprehensive unit tests

**Features:**
- ✅ Create, read, update, delete patients
- ✅ Reference IDs for safe logging (no PHI in logs)
- ✅ UTC timestamps
- ✅ Audit logging integration
- ✅ Synthetic test data

**API Endpoints:**
```
POST   /api/patients          - Create patient
GET    /api/patients/{ref}    - Get patient by reference ID
GET    /api/patients          - Get all patients
PUT    /api/patients/{id}     - Update patient
DELETE /api/patients/{id}     - Delete patient
```

---

### 2. Consent Management
- **Entity:** `Consent.java` - Consent records with scopes and expiry
- **Repository:** `ConsentRepository.java` - Database access with custom queries
- **Service:** `ConsentValidator.java` - Consent validation logic
- **Controller:** `ConsentController.java` - REST API
- **Tests:** `ConsentValidatorTest.java` - Comprehensive unit tests

**Features:**
- ✅ Grant consent with multiple scopes
- ✅ Validate consent before operations
- ✅ Check expiry automatically
- ✅ Revoke consent
- ✅ Query active consents

**Consent Scopes:**
- `PATIENT_VIEW` - View basic patient information
- `EMERGENCY_CONTACT_NOTIFY` - Notify emergency contacts
- `EMERGENCY_CONTACT_NOTIFY_DETAILED` - Share detailed info with emergency contacts
- `APPOINTMENT_REMINDER` - Send appointment reminders
- `MEDICATION_REMINDER` - Send medication reminders

**API Endpoints:**
```
POST   /api/consents                    - Create consent
GET    /api/consents/patient/{ref}      - Get consents for patient
GET    /api/consents/patient/{ref}/active - Get active consents
DELETE /api/consents/{id}               - Revoke consent
```

---

### 3. Audit Logging
- **Entity:** `AuditLog.java` - Immutable audit records
- **Repository:** `AuditLogRepository.java` - Database access
- **Service:** `AuditLogger.java` - Audit logging service

**Features:**
- ✅ Immutable audit logs (no updates/deletes)
- ✅ UTC timestamps
- ✅ Correlation IDs for tracing
- ✅ No PHI in logs
- ✅ Synchronous logging (cannot be lost)

**Event Types:**
- `PATIENT_CREATED`, `PATIENT_ACCESSED`, `PATIENT_UPDATED`, `PATIENT_DELETED`
- `CONSENT_GRANTED`, `CONSENT_VALIDATED`, `CONSENT_REVOKED`
- (More event types can be added by features)

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    REST API Layer                            │
│  PatientController  │  ConsentController  │  (More to add)   │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                   Service Layer                              │
│  PatientService  │  ConsentValidator  │  AuditLogger         │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                  Repository Layer                            │
│  PatientRepository  │  ConsentRepository  │  AuditLogRepo    │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Database (H2)                             │
│         patients  │  consents  │  audit_logs                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚀 Running the Application

### Start the Server
```bash
cd examples/context-engineering-demo
mvn spring-boot:run
```

Server starts on: `http://localhost:8080`

### Run Tests
```bash
mvn test
```

All tests should pass ✅

### Access H2 Console (for debugging)
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:healthcaredb
Username: sa
Password: (leave blank)
```

---

## 📝 Example Usage

### 1. Create a Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "referenceId": "PAT_12345",
    "fullName": "Test Patient",
    "dateOfBirth": "1990-01-01T00:00:00Z",
    "email": "test@example.com",
    "phone": "+64-21-555-0100",
    "timezone": "Pacific/Auckland"
  }'
```

### 2. Grant Consent
```bash
curl -X POST http://localhost:8080/api/consents \
  -H "Content-Type: application/json" \
  -d '{
    "patientRef": "PAT_12345",
    "scopes": ["EMERGENCY_CONTACT_NOTIFY"],
    "grantedAt": "2024-01-01T00:00:00Z",
    "expiresAt": "2025-01-01T00:00:00Z"
  }'
```

### 3. Get Patient
```bash
curl http://localhost:8080/api/patients/PAT_12345
```

### 4. Check Active Consents
```bash
curl http://localhost:8080/api/consents/patient/PAT_12345/active
```

---

## 🎯 What This Demonstrates

### 1. PHI Protection
- ✅ Patient names never in logs (only reference IDs)
- ✅ Synthetic test data (TEST_PAT_001, not "John Smith")
- ✅ Proper error handling (no PHI in exceptions)

**Example from PatientService.java:**
```java
// ✅ CORRECT: Log reference ID, not patient name
logger.info("Creating patient {}", patient.getReferenceId());

// ❌ WRONG (what we prevent):
// logger.info("Creating patient {}", patient.getFullName());
```

### 2. Consent Validation
- ✅ Consent checked before operations
- ✅ Expiry validation
- ✅ Scope validation
- ✅ Audit logging of consent checks

**Example from ConsentValidator.java:**
```java
public Consent requireConsent(String patientRef, String requiredScope) 
        throws ConsentException {
    return validateConsent(patientRef, requiredScope)
            .orElseThrow(() -> new ConsentException(
                "Patient does not have valid consent for scope: " + requiredScope
            ));
}
```

### 3. UTC Timestamps
- ✅ All timestamps stored in UTC (Instant)
- ✅ No LocalDateTime (prevents timezone bugs)
- ✅ Conversion to local timezone only for display

**Example from Patient.java:**
```java
@Column(nullable = false)
private Instant createdAt; // Always UTC

@Column(nullable = false)
private Instant updatedAt; // Always UTC
```

### 4. Audit Logging
- ✅ All operations logged
- ✅ Immutable audit records
- ✅ Correlation IDs for tracing
- ✅ No PHI in audit logs

**Example from AuditLogger.java:**
```java
public void logPatientCreated(String patientRef) {
    log("PATIENT_CREATED", patientRef, "Patient record created");
}
```

---

## 🎓 Key Patterns to Notice

### 1. Reference IDs for Logging
Every entity has a `referenceId` field that's safe to log:
```java
private String referenceId; // e.g., "PAT_12345" - safe to log
private String fullName;    // PHI - never log this
```

### 2. Instant for Timestamps
All timestamps use `Instant` (UTC):
```java
private Instant createdAt; // ✅ CORRECT
// NOT: private LocalDateTime createdAt; // ❌ WRONG
```

### 3. Synthetic Test Data
All tests use obviously fake data:
```java
testPatient.setReferenceId("TEST_PAT_001");
testPatient.setFullName("TEST_PATIENT_001");
testPatient.setEmail("test.patient.001@example.test");
```

### 4. Audit Everything
All operations create audit logs:
```java
Patient saved = patientRepository.save(patient);
auditLogger.logPatientCreated(saved.getReferenceId());
```

---

## 🔧 Technology Stack

- **Java:** 17
- **Spring Boot:** 3.2.0
- **Database:** H2 (in-memory)
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito, AssertJ

---

## 📊 Test Coverage

All core functionality is tested:
- ✅ PatientService - 100% coverage
- ✅ ConsentValidator - 100% coverage
- ✅ All tests use synthetic data
- ✅ All tests pass

Run tests:
```bash
mvn test
```

---

## 🎯 What's Next?

This base infrastructure is **complete and working**. The demo will show how to **add new features** to this system using context engineering:

1. **Emergency Contact Notifications** (demo feature)
2. **Appointment Reminders** (hands-on exercise)
3. **Medication Reminders** (bonus exercise)

Each new feature will:
- Build on this existing infrastructure
- Use ConsentValidator for consent checking
- Use AuditLogger for compliance
- Follow the same PHI protection patterns
- Use the same UTC timestamp patterns

---

## 💡 For Presenters

**When demoing, emphasize:**
1. "This is a working system - let me show you" (run it, show API calls)
2. "Notice how it follows all our rules" (show logs, no PHI)
3. "Now let's add a feature using context engineering" (start demo)

**Key talking points:**
- "We're not building from scratch - we're extending existing code"
- "The base infrastructure already handles PHI, consent, and audit logging"
- "Context engineering helps us add features that follow these patterns"

---

## 🚀 Ready to Demo

The base infrastructure is ready. Now you can demonstrate how to add the Emergency Contact Notification feature using context engineering!

