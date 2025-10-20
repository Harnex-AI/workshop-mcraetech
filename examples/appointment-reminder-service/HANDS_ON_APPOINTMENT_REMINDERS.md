# Hands-On Exercise: Appointment Reminder Service

## 🎯 Your Task
Add an **Appointment Reminder Service** to the existing healthcare system using context engineering.

## 📋 Requirements

### Business Requirements
- Patients can schedule appointments
- System sends reminders 24 hours before appointment
- Reminders respect patient timezone
- Requires consent scope: `APPOINTMENT_REMINDER`
- All reminders must be audit logged

### Technical Requirements
- Follow the same patterns as Emergency Contact feature
- Use existing `ConsentValidator` service
- Use existing `AuditLogger` service
- Use `Instant` for all timestamps (UTC)
- No PHI in logs

## 🏗️ What to Build

### 1. Appointment Entity
```java
@Entity
@Table(name = "appointments")
public class Appointment {
    UUID id;
    String patientRef;           // Reference to patient (safe to log)
    String appointmentType;      // e.g., "Checkup", "Follow-up", "Consultation"
    Instant scheduledTime;       // UTC
    String location;             // e.g., "Room 101", "Building A"
    String providerName;         // PHI - never log
    boolean reminderSent;
    Instant createdAt;           // UTC
    Instant updatedAt;           // UTC
}
```

### 2. AppointmentRepository
```java
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientRef(String patientRef);
    List<Appointment> findByScheduledTimeBetween(Instant start, Instant end);
    List<Appointment> findByReminderSentFalse();
}
```

### 3. ReminderService
```java
@Service
public class ReminderService {
    
    // Send reminder for a specific appointment
    void sendReminder(UUID appointmentId) throws ConsentException;
    
    // Send reminders for all appointments in next 24 hours
    void sendUpcomingReminders();
    
    // Check if reminder should be sent (24 hours before)
    boolean shouldSendReminder(Appointment appointment);
}
```

**Key methods:**
- Validate consent using `consentValidator.requireConsent(patientRef, "APPOINTMENT_REMINDER")`
- Log using `auditLogger.log("REMINDER_SENT", patientRef, details)`
- Log reference IDs only, never patient/provider names

### 4. AppointmentController
```java
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    
    // POST   /api/appointments              - Create appointment
    // GET    /api/appointments/patient/{ref} - Get appointments for patient
    // PUT    /api/appointments/{id}         - Update appointment
    // DELETE /api/appointments/{id}         - Delete appointment
    // POST   /api/appointments/send-reminders - Trigger reminders (for testing)
}
```

## 📝 Step-by-Step Guide

### Step 1: Set Up Context (5 min)
**Add to Cursor:**
1. Open `.cursor/rules/security-phi-protection.mdc` (Rules panel)
2. Open `BASE_INFRASTRUCTURE.md` (Docs panel)
3. Open `PatientService.java` (reference pattern)
4. Open `ConsentValidator.java` (reference pattern)
5. Open `EmergencyContact.java` (reference pattern - if demo was completed)

### Step 2: Create Notepad (1 min)
**Track your progress:**
```
Appointment Reminder Feature
⏳ Appointment entity
⏳ AppointmentRepository
⏳ ReminderService
⏳ AppointmentController
⏳ Tests
```

### Step 3: Build the Feature (15-20 min)
**Use AI with context:**

1. **Create Appointment entity**
   - Prompt: "Create Appointment entity following the Patient pattern"
   - Verify: Uses Instant, has patientRef, marks PHI in comments
   - Update notepad: ✅ Appointment entity

2. **Create AppointmentRepository**
   - Prompt: "Create AppointmentRepository following the PatientRepository pattern"
   - Verify: Extends JpaRepository, has query methods
   - Update notepad: ✅ AppointmentRepository

3. **Create ReminderService**
   - Prompt: "Create ReminderService that validates consent using ConsentValidator and logs using AuditLogger"
   - Verify: Uses existing services, logs reference IDs only
   - Update notepad: ✅ ReminderService

4. **Create AppointmentController**
   - Prompt: "Create AppointmentController following the PatientController pattern"
   - Verify: Follows REST conventions, handles exceptions
   - Update notepad: ✅ AppointmentController

### Step 4: Test (5 min)
1. Run tests: `mvn test`
2. Start server: `mvn spring-boot:run`
3. Test API:
   ```bash
   # Create appointment
   curl -X POST http://localhost:8080/api/appointments \
     -H "Content-Type: application/json" \
     -d '{
       "patientRef": "PAT_12345",
       "appointmentType": "Checkup",
       "scheduledTime": "2024-12-01T10:00:00Z",
       "location": "Room 101",
       "providerName": "Dr. Smith",
       "reminderSent": false
     }'
   
   # Grant consent
   curl -X POST http://localhost:8080/api/consents \
     -H "Content-Type: application/json" \
     -d '{
       "patientRef": "PAT_12345",
       "scopes": ["APPOINTMENT_REMINDER"],
       "grantedAt": "2024-01-01T00:00:00Z",
       "expiresAt": "2025-01-01T00:00:00Z"
     }'
   
   # Send reminders
   curl -X POST http://localhost:8080/api/appointments/send-reminders
   ```

4. Verify:
   - ✅ No PHI in logs
   - ✅ Consent validated
   - ✅ Audit logs created
   - ✅ All tests pass

## ✅ Success Criteria

- [ ] Appointment entity created with Instant timestamps
- [ ] AppointmentRepository created with query methods
- [ ] ReminderService validates consent before sending
- [ ] ReminderService logs to audit log
- [ ] No PHI in application logs
- [ ] AppointmentController follows REST conventions
- [ ] All tests pass
- [ ] API endpoints work as expected

## 🎓 Learning Objectives

By completing this exercise, you will:
1. ✅ Understand how to use context engineering to extend existing code
2. ✅ Learn to use existing services (ConsentValidator, AuditLogger)
3. ✅ Practice following established patterns
4. ✅ Experience the speed and quality benefits of context engineering

## 💡 Tips

### If AI generates wrong code:
1. **Check your context** - Did you add the right files?
2. **Be specific** - Reference the pattern: "following the PatientService pattern"
3. **Iterate** - Ask AI to fix: "This logs PHI, use reference ID instead"

### If you get stuck:
1. Look at `PatientService.java` - it's the reference pattern
2. Look at `EmergencyContact.java` - it's a similar feature
3. Ask the instructor for help

### Common mistakes to avoid:
- ❌ Using LocalDateTime instead of Instant
- ❌ Logging patient/provider names (PHI)
- ❌ Forgetting to validate consent
- ❌ Forgetting to audit log
- ❌ Not following existing patterns

## 🚀 Bonus Challenges

If you finish early:

### Challenge 1: Add Tests
Create `ReminderServiceTest.java` following `PatientServiceTest.java` pattern:
- Test consent validation
- Test audit logging
- Use synthetic test data

### Challenge 2: Add Timezone Handling
Enhance `ReminderService` to:
- Get patient timezone from `PatientRepository`
- Convert UTC time to patient local time for display
- Still store everything in UTC

### Challenge 3: Add Medication Reminders
Create a similar feature for medication reminders:
- `Medication` entity
- `MedicationReminderService`
- Consent scope: `MEDICATION_REMINDER`
- Same patterns as Appointment Reminders

## 📊 Expected Results

### Time Breakdown
- Context setup: 5 min
- Entity + Repository: 5 min
- Service layer: 8 min
- Controller: 5 min
- Testing: 5 min
- **Total: ~30 min**

### Quality Metrics
- **Code quality:** Production-ready
- **Compliance:** 100% (no PHI, consent validated, audit logged)
- **Pattern adherence:** 100% (follows existing patterns)
- **Bugs:** 0 (context prevents common mistakes)

---

## 🎉 Completion

When you're done:
1. Mark all items in your notepad as ✅
2. Show your instructor
3. Share your experience with the group

**Questions to reflect on:**
- How did context engineering help you?
- What would have been different without context?
- How will you use this in your daily work?

---

**Good luck! Remember: Context is everything. 🚀**

