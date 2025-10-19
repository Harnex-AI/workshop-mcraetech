---
inclusion: fileMatch
fileMatchPattern: ['**/*.java', '**/*.cs', '**/api/**', '**/dto/**', '**/*.ts', '**/*.py']
---
# UTC Temporal Rules

## STORAGE RULE
All timestamps in database/API must be UTC:

### Java
```java
// ✅ CORRECT
entity.setCreatedAt(Instant.now()); // Always UTC
entity.setAppointmentTime(ZonedDateTime.now(ZoneOffset.UTC));
entity.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));

// ❌ WRONG
entity.setCreatedAt(LocalDateTime.now()); // Timezone ambiguous
entity.setAppointmentTime(new Date()); // Legacy, timezone-dependent
entity.setCreatedAt(ZonedDateTime.now()); // Uses system timezone
```

### C#
```csharp
// ✅ CORRECT
entity.CreatedAt = DateTime.UtcNow;
entity.AppointmentTime = DateTimeOffset.UtcNow;
entity.UpdatedAt = DateTime.SpecifyKind(dateTime, DateTimeKind.Utc);

// ❌ WRONG
entity.CreatedAt = DateTime.Now; // Local timezone
entity.AppointmentTime = DateTime.Parse(input); // Ambiguous
entity.UpdatedAt = new DateTime(2024, 1, 15, 14, 30, 0); // No timezone
```

### TypeScript/JavaScript
```typescript
// ✅ CORRECT
entity.createdAt = new Date(); // JavaScript Date is always UTC internally
entity.appointmentTime = new Date().toISOString(); // ISO 8601 with Z suffix
entity.updatedAt = Date.now(); // Unix timestamp (UTC)

// ❌ WRONG
entity.createdAt = new Date().toLocaleString(); // Local timezone string
entity.appointmentTime = moment().format('YYYY-MM-DD HH:mm:ss'); // No timezone
```

### Python
```python
# ✅ CORRECT
from datetime import datetime, timezone
entity.created_at = datetime.now(timezone.utc)
entity.appointment_time = datetime.utcnow().replace(tzinfo=timezone.utc)

# ❌ WRONG
entity.created_at = datetime.now()  # Local timezone
entity.appointment_time = datetime.utcnow()  # Naive datetime
```

## API BOUNDARIES

### Input DTOs
```csharp
// Accept timezone-aware or specify timezone
public class AppointmentDto {
    [Required]
    public DateTime ScheduledAt { get; set; } // Must be UTC or specify timezone
    
    public string TimeZone { get; set; } = "UTC"; // Document source TZ
}

// Validation
public void Validate(AppointmentDto dto) {
    if (dto.ScheduledAt.Kind != DateTimeKind.Utc) {
        // Convert to UTC if timezone provided
        if (!string.IsNullOrEmpty(dto.TimeZone)) {
            var tz = TimeZoneInfo.FindSystemTimeZoneById(dto.TimeZone);
            dto.ScheduledAt = TimeZoneInfo.ConvertTimeToUtc(dto.ScheduledAt, tz);
        } else {
            throw new ValidationException("DateTime must be UTC or timezone must be specified");
        }
    }
}
```

### Output DTOs
```csharp
// Always return UTC with 'Z' suffix
public class AppointmentResponseDto {
    public string ScheduledAt { get; set; } // ISO 8601 with Z
    public string TimeZone { get; set; } = "UTC";
}

// Serialization
return new AppointmentResponseDto {
    ScheduledAt = appointment.ScheduledAt.ToString("O"), // 2024-01-15T14:30:00.000Z
    TimeZone = "UTC"
};
```

### Java API
```java
// Input
@PostMapping("/appointments")
public ResponseEntity<?> createAppointment(@RequestBody AppointmentDto dto) {
    // Ensure UTC
    Instant scheduledAt = dto.getScheduledAt().toInstant(); // Convert to UTC
    
    // Store as UTC
    appointment.setScheduledAt(scheduledAt);
}

// Output - Always return ISO 8601 with Z
@GetMapping("/appointments/{id}")
public AppointmentDto getAppointment(@PathVariable String id) {
    Appointment apt = repository.findById(id);
    return AppointmentDto.builder()
        .scheduledAt(apt.getScheduledAt().toString()) // 2024-01-15T14:30:00Z
        .timeZone("UTC")
        .build();
}
```

## CONVERSION POINTS
Document where conversions occur:

### Display Conversion (UI Boundary)
```java
// Convert for display only at UI boundary
public String getLocalDisplayTime(Instant utcTime, String userTimeZone) {
    // DOCUMENT: Converting UTC to user's local timezone for display
    return utcTime.atZone(ZoneId.of(userTimeZone))
                  .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}
```

```csharp
// UI conversion helper
public string ToLocalDisplay(DateTime utcTime, string userTimeZone) {
    // DOCUMENT: Converting UTC to user's timezone for display only
    var tz = TimeZoneInfo.FindSystemTimeZoneById(userTimeZone);
    var localTime = TimeZoneInfo.ConvertTimeFromUtc(utcTime, tz);
    return localTime.ToString("yyyy-MM-dd HH:mm:ss");
}
```

### Input Conversion (API Boundary)
```typescript
// Convert user input to UTC before storage
function convertToUtc(localTime: string, userTimeZone: string): Date {
    // DOCUMENT: Converting user's local time to UTC for storage
    const moment = require('moment-timezone');
    return moment.tz(localTime, userTimeZone).utc().toDate();
}
```

## DATABASE SCHEMA
```sql
-- ✅ CORRECT - Use TIMESTAMP or TIMESTAMPTZ (PostgreSQL)
CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    scheduled_at TIMESTAMPTZ NOT NULL, -- Stores UTC, displays in session timezone
    created_at TIMESTAMPTZ DEFAULT NOW() -- Always UTC
);

-- ❌ WRONG - TIMESTAMP without timezone
CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    scheduled_at TIMESTAMP NOT NULL, -- Ambiguous timezone
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP -- Depends on server timezone
);
```

## TESTING PATTERNS
```csharp
[Test]
public void CreateAppointment_StoresInUtc()
{
    // Arrange
    var localTime = new DateTime(2024, 1, 15, 14, 30, 0, DateTimeKind.Local);
    var expectedUtc = localTime.ToUniversalTime();
    
    // Act
    var appointment = _service.CreateAppointment(localTime);
    
    // Assert
    Assert.AreEqual(DateTimeKind.Utc, appointment.ScheduledAt.Kind);
    Assert.AreEqual(expectedUtc, appointment.ScheduledAt);
}

[Test]
public void GetAppointment_ReturnsUtcWithZSuffix()
{
    // Arrange
    var utcTime = DateTime.UtcNow;
    var appointment = CreateTestAppointment(utcTime);
    
    // Act
    var dto = _service.GetAppointment(appointment.Id);
    
    // Assert
    Assert.IsTrue(dto.ScheduledAt.EndsWith("Z"), "Must have Z suffix for UTC");
    Assert.AreEqual("UTC", dto.TimeZone);
}
```

## COMMON PITFALLS

### Pitfall 1: Comparing Local and UTC Times
```csharp
// ❌ WRONG
if (appointment.ScheduledAt > DateTime.Now) { }

// ✅ CORRECT
if (appointment.ScheduledAt > DateTime.UtcNow) { }
```

### Pitfall 2: Parsing Without Timezone
```java
// ❌ WRONG
LocalDateTime.parse("2024-01-15T14:30:00"); // No timezone

// ✅ CORRECT
Instant.parse("2024-01-15T14:30:00Z"); // Explicit UTC
ZonedDateTime.parse("2024-01-15T14:30:00Z"); // With timezone
```

### Pitfall 3: Serialization Without Timezone
```typescript
// ❌ WRONG
JSON.stringify({ time: new Date().toString() }); // Local string

// ✅ CORRECT
JSON.stringify({ time: new Date().toISOString() }); // ISO 8601 with Z
```

## AUTOMATIC FIXES

Replace:
```csharp
entity.CreatedAt = DateTime.Now;
```
With:
```csharp
entity.CreatedAt = DateTime.UtcNow;
```

Replace:
```java
entity.setCreatedAt(LocalDateTime.now());
```
With:
```java
entity.setCreatedAt(Instant.now());
```

## COMPLIANCE NOTES
- Healthcare appointments must be timezone-aware
- Audit logs require UTC timestamps for compliance
- Cross-timezone coordination requires UTC storage
- Display in user's local timezone, store in UTC
- Document all timezone conversions

