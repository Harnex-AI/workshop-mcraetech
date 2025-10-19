---
inclusion: fileMatch
fileMatchPattern: ['**/*.java', '**/*.cs', '**/*.ts', '**/*.py']
---
# PHI Protection Rules

## NEVER DO
- Log patient names, DOB, addresses, or medical record numbers
- Include PHI in error messages or stack traces
- Store PHI in local storage, cookies, or session storage
- Use PHI in URLs or query parameters
- Serialize entire patient/person objects in logs
- Return PHI in error responses to clients
- Include PHI in correlation IDs or tracking tokens

## ALWAYS DO
- Use patient reference IDs instead of identifiable information
- Redact PHI before logging: `_logger.LogInformation("Processing patient {PatientRef}", patient.ReferenceId)`
- Return generic error messages to clients, log details server-side
- Use synthetic data in tests and examples
- Mask PHI in debug output: `patient.Name = "***REDACTED***"`
- Use audit logs for PHI access tracking (separate from application logs)
- Encrypt PHI at rest and in transit

## DETECTION PATTERNS
When you see these patterns, apply PHI rules:
- Variables named: patient*, person*, member*, client*, subscriber*
- DTOs containing: name, dob, ssn, address, phone, email, mrn (medical record number)
- Logging statements with @dto or object serialisation
- Test data with realistic-looking personal information
- API responses containing full patient objects
- Exception messages with patient details

## AUTOMATIC FIXES

### C# Logging
Replace:
```csharp
_logger.LogInformation("Creating patient {@dto}", dto);
```
With:
```csharp
_logger.LogInformation("Creating patient {PatientRef}", dto.GetReferenceId());
```

### Java Logging
Replace:
```java
log.info("Patient data: {}", patient);
```
With:
```java
log.info("Processing patient ref: {}", patient.getReferenceId());
```

### TypeScript/JavaScript
Replace:
```typescript
console.log('Patient:', patient);
```
With:
```typescript
console.log('Patient ref:', patient.referenceId);
```

### Python Logging
Replace:
```python
logger.info(f"Patient: {patient}")
```
With:
```python
logger.info(f"Patient ref: {patient.reference_id}")
```

## ERROR HANDLING
Replace:
```csharp
catch (Exception ex) {
    return BadRequest($"Failed to process patient {patient.Name}: {ex.Message}");
}
```
With:
```csharp
catch (Exception ex) {
    _logger.LogError(ex, "Failed to process patient {PatientRef}", patient.ReferenceId);
    return BadRequest(new ProblemDetails {
        Title = "Patient Processing Failed",
        Detail = "Unable to process patient record. Reference: " + correlationId
    });
}
```

## TEST DATA RULES
Use obviously synthetic data:
```csharp
// ✅ CORRECT
var testPatient = new Patient {
    ReferenceId = "TEST_PAT_001",
    Name = "TEST_PATIENT_001",
    DateOfBirth = new DateTime(1900, 1, 1),
    Address = "123 Test Street, Test City, TS1 1ST"
};

// ❌ WRONG
var testPatient = new Patient {
    Name = "John Smith",
    DateOfBirth = new DateTime(1985, 6, 15),
    Address = "456 Main St, Auckland"
};
```

## API RESPONSE SANITIZATION
Replace:
```java
@GetMapping("/patients/{id}")
public Patient getPatient(@PathVariable String id) {
    return patientRepository.findById(id);
}
```
With:
```java
@GetMapping("/patients/{id}")
public PatientSummaryDto getPatient(@PathVariable String id) {
    Patient patient = patientRepository.findById(id);
    return PatientSummaryDto.fromEntity(patient); // DTO excludes sensitive fields
}
```

## COMPLIANCE NOTES
- HIPAA requires PHI protection in all forms
- GDPR requires data minimization
- Audit all PHI access with user ID, timestamp, and purpose
- PHI exposure incidents must be reported within 72 hours

