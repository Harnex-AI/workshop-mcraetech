---
inclusion: fileMatch
fileMatchPattern: ['**/Controllers/**', '**/Services/**', '**/Repositories/**', '**/controllers/**', '**/services/**', '**/repositories/**']
---
# Consent Validation Rules

## WHEN TO APPLY
- Any endpoint accessing clinical data
- Any service method querying patient records
- Any repository method fetching health information
- Any operation that reads/writes EHR data
- Any data sharing or export functionality

## REQUIRED PATTERNS

### Controller Level (C#)
```csharp
[HttpGet]
public async Task<IActionResult> GetPatientData(
    [FromHeader(Name = "X-Consent-Scope")] string consentScope,
    [FromRoute] string patientRef)
{
    // REQUIRED: Validate consent scope
    if (string.IsNullOrEmpty(consentScope) || !consentScope.Contains("EHR_VIEW"))
        return Forbid("Missing required consent scope: EHR_VIEW");
    
    // REQUIRED: Validate consent expiry (if applicable)
    var consent = await _consentService.GetActiveConsent(patientRef, consentScope);
    if (consent == null || consent.IsExpired())
        return Forbid("Consent expired or not found");
    
    // Proceed with data access
    var data = await _service.GetPatientData(patientRef, consent);
    return Ok(data);
}
```

### Service Level (Java)
```java
public List<ClinicalRecord> getRecords(String patientRef, ConsentContext consent) {
    // ALWAYS validate consent first
    consentValidator.requireScope(consent, "EHR_VIEW");
    
    // Check consent expiry
    if (consent.isExpired()) {
        throw new ConsentExpiredException(patientRef);
    }
    
    // Check consent revocation
    if (consent.isRevoked()) {
        throw new ConsentRevokedException(patientRef);
    }
    
    // Audit consent usage
    auditLog.recordConsentUsage(consent, "EHR_VIEW", patientRef);
    
    return repository.findByPatientRef(patientRef);
}
```

### TypeScript/Node.js
```typescript
async function getPatientRecords(
    patientRef: string, 
    consentScope: string
): Promise<ClinicalRecord[]> {
    // REQUIRED: Validate consent
    if (!consentScope?.includes('EHR_VIEW')) {
        throw new ForbiddenError('Missing required consent scope: EHR_VIEW');
    }
    
    const consent = await consentService.getActiveConsent(patientRef, consentScope);
    if (!consent || consent.isExpired()) {
        throw new ForbiddenError('Consent expired or not found');
    }
    
    // Audit before access
    await auditLog.recordAccess(patientRef, consentScope, 'READ');
    
    return repository.findByPatientRef(patientRef);
}
```

## CONSENT SCOPES
Standard scopes for healthcare:
- `EHR_VIEW` - Read electronic health records
- `EHR_WRITE` - Modify health records
- `LAB_RESULTS` - Access laboratory results
- `PRESCRIPTIONS` - Access prescription data
- `APPOINTMENTS` - Access appointment data
- `BILLING` - Access billing information
- `SHARE_EXTERNAL` - Share data with external parties

## RED FLAGS
Generate warnings when:
- Clinical data access without consent parameter
- Direct repository calls bypassing service layer
- Missing consent expiry checks
- Hardcoded consent values
- No audit logging for consent usage
- Consent validation in wrong layer (should be service layer)
- Missing consent revocation checks

## AUTOMATIC FIXES

### Add Missing Consent Parameter
Replace:
```csharp
public async Task<PatientData> GetData(string patientRef)
```
With:
```csharp
public async Task<PatientData> GetData(string patientRef, ConsentContext consent)
{
    // Validate consent first
    _consentValidator.RequireScope(consent, "EHR_VIEW");
    if (consent.IsExpired())
        throw new ConsentExpiredException(patientRef);
```

### Add Consent Validation to Existing Method
Replace:
```java
public ClinicalRecord getRecord(String recordId) {
    return repository.findById(recordId);
}
```
With:
```java
public ClinicalRecord getRecord(String recordId, ConsentContext consent) {
    // REQUIRED: Validate consent
    consentValidator.requireScope(consent, "EHR_VIEW");
    if (consent.isExpired()) {
        throw new ConsentExpiredException();
    }
    
    // Audit access
    auditLog.recordAccess(recordId, consent.getUserId(), "READ");
    
    return repository.findById(recordId);
}
```

## TESTING REQUIREMENTS
Every test accessing clinical data must setup consent:

```csharp
[Test]
public async Task GetPatientData_WithValidConsent_ReturnsData()
{
    // Arrange
    var consent = new ConsentBuilder()
        .WithScope("EHR_VIEW")
        .WithExpiry(DateTime.UtcNow.AddHours(1))
        .WithPatientRef("TEST_PAT_001")
        .Build();
    
    // Act
    var result = await _service.GetPatientData("TEST_PAT_001", consent);
    
    // Assert
    Assert.NotNull(result);
}

[Test]
public async Task GetPatientData_WithExpiredConsent_ThrowsException()
{
    // REQUIRED: Test consent expiry handling
    var expiredConsent = new ConsentBuilder()
        .WithScope("EHR_VIEW")
        .WithExpiry(DateTime.UtcNow.AddHours(-1)) // Expired
        .Build();
    
    // Act & Assert
    Assert.ThrowsAsync<ConsentExpiredException>(
        () => _service.GetPatientData("TEST_PAT_001", expiredConsent)
    );
}

[Test]
public async Task GetPatientData_WithInsufficientScope_ThrowsException()
{
    // REQUIRED: Test insufficient scope
    var consent = new ConsentBuilder()
        .WithScope("APPOINTMENTS") // Wrong scope
        .WithExpiry(DateTime.UtcNow.AddHours(1))
        .Build();
    
    Assert.ThrowsAsync<InsufficientConsentException>(
        () => _service.GetPatientData("TEST_PAT_001", consent)
    );
}
```

## AUDIT REQUIREMENTS
All consent usage must be audited:
```java
auditLog.record(AuditEvent.builder()
    .eventType("CONSENT_USAGE")
    .userId(consent.getUserId())
    .patientRef(patientRef)
    .consentScope(consent.getScope())
    .action("EHR_VIEW")
    .timestamp(Instant.now())
    .correlationId(requestContext.getCorrelationId())
    .build());
```

## COMPLIANCE NOTES
- HIPAA requires patient consent for data access
- GDPR requires explicit consent with right to withdraw
- Consent must be granular (specific purposes)
- Consent expiry must be enforced
- Consent revocation must be immediate
- All consent usage must be auditable

