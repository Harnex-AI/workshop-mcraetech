---
inclusion: fileMatch
fileMatchPattern: ['**/Services/**', '**/Repositories/**', '**/Controllers/**', '**/services/**', '**/repositories/**', '**/controllers/**']
---
# Performance & Scalability Patterns

## N+1 QUERY PREVENTION

### Problem: N+1 Queries
```java
// ❌ WRONG - N+1 queries
public List<PatientDto> getPatients() {
    List<Patient> patients = patientRepo.findAll();
    return patients.stream()
        .map(p -> {
            // This causes N additional queries!
            List<Consent> consents = consentRepo.findByPatientId(p.getId());
            List<Appointment> appointments = appointmentRepo.findByPatientId(p.getId());
            return toDto(p, consents, appointments);
        })
        .collect(toList());
}
```

### Solution: Batch Loading
```java
// ✅ CORRECT - Batch loading
public List<PatientDto> getPatients() {
    List<Patient> patients = patientRepo.findAll();
    Set<Long> patientIds = patients.stream()
        .map(Patient::getId)
        .collect(toSet());
    
    // Single query for all consents
    Map<Long, List<Consent>> consentsByPatient = 
        consentRepo.findByPatientIdIn(patientIds).stream()
            .collect(groupingBy(Consent::getPatientId));
    
    // Single query for all appointments
    Map<Long, List<Appointment>> appointmentsByPatient =
        appointmentRepo.findByPatientIdIn(patientIds).stream()
            .collect(groupingBy(Appointment::getPatientId));
    
    return patients.stream()
        .map(p -> toDto(
            p, 
            consentsByPatient.getOrDefault(p.getId(), emptyList()),
            appointmentsByPatient.getOrDefault(p.getId(), emptyList())
        ))
        .collect(toList());
}
```

### C# Entity Framework
```csharp
// ❌ WRONG - N+1 queries
public async Task<List<PatientDto>> GetPatientsAsync()
{
    var patients = await _context.Patients.ToListAsync();
    
    return patients.Select(p => new PatientDto {
        ReferenceId = p.ReferenceId,
        // Lazy loading triggers N queries
        Consents = p.Consents.Select(c => new ConsentDto { ... }).ToList(),
        Appointments = p.Appointments.Select(a => new AppointmentDto { ... }).ToList()
    }).ToList();
}

// ✅ CORRECT - Eager loading
public async Task<List<PatientDto>> GetPatientsAsync()
{
    var patients = await _context.Patients
        .Include(p => p.Consents)
        .Include(p => p.Appointments)
        .ToListAsync();
    
    return patients.Select(p => new PatientDto {
        ReferenceId = p.ReferenceId,
        Consents = p.Consents.Select(c => new ConsentDto { ... }).ToList(),
        Appointments = p.Appointments.Select(a => new AppointmentDto { ... }).ToList()
    }).ToList();
}
```

## ASYNC PATTERNS FOR SLOW OPERATIONS

### Background Processing
```csharp
// For operations >100ms, use async
[HttpPost("documents/process")]
public async Task<IActionResult> ProcessDocument(IFormFile file)
{
    // Validate file size
    if (file.Length > 10 * 1024 * 1024) // 10MB
        return BadRequest("File size exceeds 10MB limit");
    
    // Return immediately with tracking ID
    var trackingId = Guid.NewGuid().ToString();
    
    // Queue for background processing
    await _messageQueue.PublishAsync(new ProcessDocumentCommand {
        TrackingId = trackingId,
        FileName = file.FileName,
        FileData = await file.GetBytesAsync(),
        UploadedAt = DateTime.UtcNow
    });
    
    return Accepted(new {
        trackingId,
        statusUrl = $"/api/documents/status/{trackingId}",
        message = "Document processing started"
    });
}

[HttpGet("documents/status/{trackingId}")]
public async Task<IActionResult> GetProcessingStatus(string trackingId)
{
    var status = await _statusService.GetStatusAsync(trackingId);
    
    if (status == null)
        return NotFound();
    
    return Ok(new {
        trackingId,
        status = status.State, // "pending", "processing", "completed", "failed"
        progress = status.Progress,
        completedAt = status.CompletedAt,
        resultUrl = status.State == "completed" ? $"/api/documents/{trackingId}" : null
    });
}
```

### Java Async Processing
```java
@PostMapping("/clinical-records/import")
public ResponseEntity<?> importClinicalRecords(
    @RequestBody ImportRequest request,
    @RequestHeader("X-Correlation-Id") String correlationId) {
    
    // Validate request
    if (request.getRecords().size() > 1000) {
        return ResponseEntity.badRequest()
            .body("Cannot import more than 1000 records at once");
    }
    
    // Start async processing
    String jobId = UUID.randomUUID().toString();
    
    CompletableFuture.runAsync(() -> {
        try {
            importService.processImport(jobId, request, correlationId);
        } catch (Exception ex) {
            log.error("Import failed [{}]", jobId, ex);
            jobStatusService.markFailed(jobId, ex.getMessage());
        }
    }, asyncExecutor);
    
    return ResponseEntity.accepted()
        .body(Map.of(
            "jobId", jobId,
            "statusUrl", "/api/jobs/" + jobId,
            "estimatedDuration", "2-5 minutes"
        ));
}
```

## CACHING STRATEGIES

### In-Memory Caching
```csharp
public class ConsentService
{
    private readonly IMemoryCache _cache;
    private readonly IConsentRepository _repository;
    
    public async Task<Consent> GetActiveConsentAsync(string patientRef, string scope)
    {
        var cacheKey = $"consent:{patientRef}:{scope}";
        
        // Try cache first
        if (_cache.TryGetValue(cacheKey, out Consent cachedConsent))
        {
            return cachedConsent;
        }
        
        // Fetch from database
        var consent = await _repository.GetActiveConsentAsync(patientRef, scope);
        
        if (consent != null && !consent.IsExpired())
        {
            // Cache for 5 minutes or until expiry
            var cacheExpiry = consent.ExpiresAt < DateTime.UtcNow.AddMinutes(5)
                ? consent.ExpiresAt
                : DateTime.UtcNow.AddMinutes(5);
            
            _cache.Set(cacheKey, consent, cacheExpiry);
        }
        
        return consent;
    }
}
```

### Distributed Caching (Redis)
```java
@Service
public class PatientCacheService {
    
    private final RedisTemplate<String, PatientDto> redisTemplate;
    private final PatientRepository repository;
    
    public PatientDto getPatient(String patientRef) {
        String cacheKey = "patient:" + patientRef;
        
        // Try cache first
        PatientDto cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        // Fetch from database
        Patient patient = repository.findByRef(patientRef)
            .orElseThrow(() -> new NotFoundException("Patient not found"));
        
        PatientDto dto = toDto(patient);
        
        // Cache for 10 minutes
        redisTemplate.opsForValue().set(cacheKey, dto, 10, TimeUnit.MINUTES);
        
        return dto;
    }
    
    public void invalidatePatient(String patientRef) {
        String cacheKey = "patient:" + patientRef;
        redisTemplate.delete(cacheKey);
    }
}
```

## DATABASE QUERY OPTIMIZATION

### Projection (Select Only Needed Fields)
```csharp
// ❌ WRONG - Fetches all columns
public async Task<List<PatientSummaryDto>> GetPatientSummariesAsync()
{
    var patients = await _context.Patients.ToListAsync();
    return patients.Select(p => new PatientSummaryDto {
        ReferenceId = p.ReferenceId,
        Name = p.Name
    }).ToList();
}

// ✅ CORRECT - Projects only needed fields
public async Task<List<PatientSummaryDto>> GetPatientSummariesAsync()
{
    return await _context.Patients
        .Select(p => new PatientSummaryDto {
            ReferenceId = p.ReferenceId,
            Name = p.Name
        })
        .ToListAsync();
}
```

### Pagination at Database Level
```java
// ✅ CORRECT - Pagination in database
@Query("SELECT p FROM Patient p WHERE p.status = :status")
Page<Patient> findByStatus(
    @Param("status") String status, 
    Pageable pageable);

// Usage
Page<Patient> patients = repository.findByStatus(
    "active", 
    PageRequest.of(0, 20, Sort.by("createdAt").descending())
);
```

### Indexed Queries
```sql
-- Create indexes for frequently queried fields
CREATE INDEX idx_patient_reference_id ON patients(reference_id);
CREATE INDEX idx_consent_patient_scope ON consents(patient_id, scope, expires_at);
CREATE INDEX idx_appointment_patient_date ON appointments(patient_id, scheduled_at);

-- Composite index for common query patterns
CREATE INDEX idx_clinical_records_lookup 
ON clinical_records(patient_id, record_type, created_at DESC);
```

## BULK OPERATIONS

### Batch Insert
```csharp
public async Task<int> ImportPatientsAsync(List<PatientDto> patients)
{
    // Validate batch size
    if (patients.Count > 1000)
        throw new ArgumentException("Batch size cannot exceed 1000");
    
    var entities = patients.Select(dto => new Patient {
        ReferenceId = dto.ReferenceId,
        Name = dto.Name,
        DateOfBirth = dto.DateOfBirth,
        CreatedAt = DateTime.UtcNow
    }).ToList();
    
    // Bulk insert
    await _context.Patients.AddRangeAsync(entities);
    await _context.SaveChangesAsync();
    
    return entities.Count;
}
```

### Batch Update
```java
public int updatePatientStatuses(List<String> patientRefs, String newStatus) {
    // Use batch update instead of individual updates
    return jdbcTemplate.batchUpdate(
        "UPDATE patients SET status = ?, updated_at = ? WHERE reference_id = ?",
        new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, newStatus);
                ps.setTimestamp(2, Timestamp.from(Instant.now()));
                ps.setString(3, patientRefs.get(i));
            }
            
            @Override
            public int getBatchSize() {
                return patientRefs.size();
            }
        }
    ).length;
}
```

## CONNECTION POOLING

### C# Configuration
```csharp
services.AddDbContext<AppDbContext>(options =>
    options.UseSqlServer(connectionString, sqlOptions => {
        sqlOptions.EnableRetryOnFailure(
            maxRetryCount: 3,
            maxRetryDelay: TimeSpan.FromSeconds(5),
            errorNumbersToAdd: null);
        sqlOptions.CommandTimeout(30);
    }));

// Connection pool settings in connection string
"Server=...;Database=...;Min Pool Size=10;Max Pool Size=100;Connection Timeout=30;"
```

### Java Configuration
```java
@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        
        // Pool settings
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(50);
        config.setConnectionTimeout(30000); // 30 seconds
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(1800000); // 30 minutes
        
        return new HikariDataSource(config);
    }
}
```

## MONITORING & METRICS

### Performance Logging
```csharp
public async Task<PatientData> GetPatientDataAsync(string patientRef)
{
    var stopwatch = Stopwatch.StartNew();
    
    try {
        var data = await _repository.GetByRefAsync(patientRef);
        return data;
    } finally {
        stopwatch.Stop();
        
        if (stopwatch.ElapsedMilliseconds > 100) {
            _logger.LogWarning(
                "Slow query detected: GetPatientData took {ElapsedMs}ms for {PatientRef}",
                stopwatch.ElapsedMilliseconds, patientRef);
        }
        
        _metrics.RecordQueryDuration("GetPatientData", stopwatch.ElapsedMilliseconds);
    }
}
```

## COMPLIANCE NOTES
- Batch operations must respect consent boundaries
- Cache invalidation required on data updates
- Performance monitoring must not log PHI
- Connection pools must be sized for peak load
- Async operations must maintain audit trail

