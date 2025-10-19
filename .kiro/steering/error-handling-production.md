---
inclusion: fileMatch
fileMatchPattern: ['**/*.java', '**/*.cs', '**/Controllers/**', '**/Services/**', '**/controllers/**', '**/services/**']
---
# Production Error Handling

## CLIENT-FACING ERRORS
Never expose:
- Stack traces
- Internal service names
- Database schema details
- File paths
- PHI in error messages
- Internal error codes or IDs
- SQL queries or database errors
- Configuration details

## ALWAYS INCLUDE
- User-friendly error message
- Correlation/tracking ID for support
- HTTP status code
- Error code (public-facing)
- Timestamp
- Request ID

## PATTERN FOR CONTROLLERS

### Java/Spring
```java
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ProblemDetail> handleDataIntegrity(DataIntegrityViolationException ex) {
    // Log full details internally with correlation ID
    String correlationId = MDC.get("correlationId");
    log.error("Data integrity violation [{}]", correlationId, ex);
    
    // Return safe client response
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        HttpStatus.CONFLICT,
        "Unable to process request due to data conflict"
    );
    problem.setProperty("errorCode", "CONFLICT_409");
    problem.setProperty("correlationId", correlationId);
    problem.setProperty("timestamp", Instant.now().toString());
    
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
}

@ExceptionHandler(ConsentExpiredException.class)
public ResponseEntity<ProblemDetail> handleConsentExpired(ConsentExpiredException ex) {
    String correlationId = MDC.get("correlationId");
    log.warn("Consent expired [{}]: {}", correlationId, ex.getMessage());
    
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        HttpStatus.FORBIDDEN,
        "Consent has expired. Please renew consent to access this data."
    );
    problem.setProperty("errorCode", "CONSENT_EXPIRED");
    problem.setProperty("correlationId", correlationId);
    
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
    String correlationId = MDC.get("correlationId");
    log.error("Unexpected error [{}]", correlationId, ex);
    
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "An unexpected error occurred. Please contact support with reference: " + correlationId
    );
    problem.setProperty("errorCode", "INTERNAL_ERROR");
    problem.setProperty("correlationId", correlationId);
    
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
}
```

### C#/.NET
```csharp
[HttpPost]
public IActionResult CreatePatient([FromBody] PatientDto dto)
{
    var correlationId = Activity.Current?.Id ?? Guid.NewGuid().ToString();
    
    try {
        var result = _service.Create(dto);
        return Ok(result);
    }
    catch (ValidationException vex) {
        _logger.LogWarning("Validation failed [{CorrelationId}]: {Details}", 
            correlationId, vex.Details);
        
        return BadRequest(new ProblemDetails {
            Title = "Validation Failed",
            Detail = vex.UserMessage, // Safe, sanitised message
            Status = 400,
            Extensions = { 
                ["errors"] = vex.ValidationErrors,
                ["correlationId"] = correlationId,
                ["timestamp"] = DateTime.UtcNow
            }
        });
    }
    catch (ConsentExpiredException cex) {
        _logger.LogWarning("Consent expired [{CorrelationId}]: {PatientRef}", 
            correlationId, cex.PatientRef);
        
        return StatusCode(403, new ProblemDetails {
            Title = "Consent Expired",
            Detail = "Consent has expired. Please renew consent to access this data.",
            Status = 403,
            Extensions = {
                ["errorCode"] = "CONSENT_EXPIRED",
                ["correlationId"] = correlationId
            }
        });
    }
    catch (DbUpdateException dbEx) {
        _logger.LogError(dbEx, "Database error [{CorrelationId}]", correlationId);
        
        return StatusCode(500, new ProblemDetails {
            Title = "Database Error",
            Detail = "Unable to save data. Reference: " + correlationId,
            Status = 500,
            Extensions = {
                ["errorCode"] = "DB_ERROR",
                ["correlationId"] = correlationId
            }
        });
    }
    catch (Exception ex) {
        _logger.LogError(ex, "Unexpected error in CreatePatient [{CorrelationId}]", correlationId);
        
        return StatusCode(500, new ProblemDetails {
            Title = "Internal Server Error",
            Detail = "An unexpected error occurred. Reference: " + correlationId,
            Status = 500,
            Extensions = {
                ["errorCode"] = "INTERNAL_ERROR",
                ["correlationId"] = correlationId
            }
        });
    }
}
```

### TypeScript/Express
```typescript
app.post('/patients', async (req, res, next) => {
    const correlationId = req.headers['x-correlation-id'] || uuidv4();
    
    try {
        const result = await service.createPatient(req.body);
        res.json(result);
    } catch (error) {
        if (error instanceof ValidationError) {
            logger.warn(`Validation failed [${correlationId}]:`, error.details);
            return res.status(400).json({
                title: 'Validation Failed',
                detail: error.message,
                status: 400,
                errors: error.validationErrors,
                correlationId,
                timestamp: new Date().toISOString()
            });
        }
        
        if (error instanceof ConsentExpiredError) {
            logger.warn(`Consent expired [${correlationId}]:`, error.patientRef);
            return res.status(403).json({
                title: 'Consent Expired',
                detail: 'Consent has expired. Please renew consent.',
                status: 403,
                errorCode: 'CONSENT_EXPIRED',
                correlationId
            });
        }
        
        // Generic error - don't expose details
        logger.error(`Unexpected error [${correlationId}]:`, error);
        res.status(500).json({
            title: 'Internal Server Error',
            detail: `An unexpected error occurred. Reference: ${correlationId}`,
            status: 500,
            errorCode: 'INTERNAL_ERROR',
            correlationId
        });
    }
});
```

## SERVICE LAYER ERROR HANDLING

### Domain Exceptions (Safe to expose)
```csharp
// Create specific domain exceptions with safe messages
public class ConsentExpiredException : Exception
{
    public string PatientRef { get; }
    
    public ConsentExpiredException(string patientRef) 
        : base("Consent has expired")
    {
        PatientRef = patientRef;
    }
}

public class InsufficientConsentException : Exception
{
    public string RequiredScope { get; }
    
    public InsufficientConsentException(string requiredScope)
        : base($"Insufficient consent. Required scope: {requiredScope}")
    {
        RequiredScope = requiredScope;
    }
}
```

### Service Method Pattern
```java
public PatientData getPatientData(String patientRef, ConsentContext consent) {
    try {
        // Validate consent
        if (consent.isExpired()) {
            throw new ConsentExpiredException(patientRef);
        }
        
        // Business logic
        return repository.findByRef(patientRef);
        
    } catch (ConsentExpiredException ex) {
        // Domain exception - safe to propagate
        throw ex;
    } catch (DataAccessException ex) {
        // Infrastructure exception - wrap with safe message
        log.error("Database error accessing patient data: {}", patientRef, ex);
        throw new ServiceException("Unable to retrieve patient data", ex);
    } catch (Exception ex) {
        // Unexpected - log and wrap
        log.error("Unexpected error in getPatientData: {}", patientRef, ex);
        throw new ServiceException("An unexpected error occurred", ex);
    }
}
```

## LOGGING PATTERNS

### Structured Logging
```csharp
// ✅ CORRECT - Structured with correlation ID
_logger.LogError(ex, 
    "Failed to process patient. CorrelationId: {CorrelationId}, PatientRef: {PatientRef}",
    correlationId, patientRef);

// ❌ WRONG - Unstructured, includes PHI
_logger.LogError($"Failed to process patient {patient.Name}: {ex.Message}");
```

### Log Levels
```java
// ERROR - Unexpected errors requiring investigation
log.error("Unexpected error [{}]", correlationId, exception);

// WARN - Expected errors (validation, business rules)
log.warn("Validation failed [{}]: {}", correlationId, validationErrors);

// INFO - Normal operations
log.info("Patient created [{}]: {}", correlationId, patientRef);

// DEBUG - Detailed flow (not in production)
log.debug("Processing step 3 [{}]", correlationId);
```

## VALIDATION ERROR PATTERNS

### Input Validation
```csharp
public class PatientValidator : AbstractValidator<PatientDto>
{
    public PatientValidator()
    {
        RuleFor(x => x.DateOfBirth)
            .NotEmpty().WithMessage("Date of birth is required")
            .LessThan(DateTime.UtcNow).WithMessage("Date of birth must be in the past");
        
        RuleFor(x => x.Email)
            .EmailAddress().WithMessage("Invalid email format")
            .When(x => !string.IsNullOrEmpty(x.Email));
    }
}

// Return validation errors
var validator = new PatientValidator();
var result = validator.Validate(dto);

if (!result.IsValid) {
    return BadRequest(new ValidationProblemDetails {
        Title = "Validation Failed",
        Detail = "One or more validation errors occurred",
        Status = 400,
        Errors = result.Errors.GroupBy(e => e.PropertyName)
            .ToDictionary(g => g.Key, g => g.Select(e => e.ErrorMessage).ToArray())
    });
}
```

## TESTING ERROR HANDLING

```csharp
[Test]
public async Task CreatePatient_WithExpiredConsent_Returns403()
{
    // Arrange
    var expiredConsent = CreateExpiredConsent();
    
    // Act
    var response = await _controller.CreatePatient(dto, expiredConsent);
    
    // Assert
    Assert.IsInstanceOf<ObjectResult>(response);
    var result = (ObjectResult)response;
    Assert.AreEqual(403, result.StatusCode);
    
    var problem = result.Value as ProblemDetails;
    Assert.IsNotNull(problem);
    Assert.AreEqual("Consent Expired", problem.Title);
    Assert.IsTrue(problem.Extensions.ContainsKey("correlationId"));
}

[Test]
public async Task CreatePatient_WithDatabaseError_Returns500WithoutDetails()
{
    // Arrange
    _mockService.Setup(s => s.Create(It.IsAny<PatientDto>()))
        .ThrowsAsync(new DbUpdateException("FK constraint violation"));
    
    // Act
    var response = await _controller.CreatePatient(dto);
    
    // Assert
    var result = (ObjectResult)response;
    Assert.AreEqual(500, result.StatusCode);
    
    var problem = result.Value as ProblemDetails;
    Assert.IsFalse(problem.Detail.Contains("FK constraint")); // No DB details
    Assert.IsTrue(problem.Extensions.ContainsKey("correlationId"));
}
```

## COMPLIANCE NOTES
- Never expose PHI in error messages
- Always log full errors server-side
- Return correlation IDs for support
- Use standard HTTP status codes
- Follow RFC 7807 Problem Details format
- Audit all error occurrences

