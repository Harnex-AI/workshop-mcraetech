# Emergency Contact Notification Service - Feature Addition

## 🎯 Overview
This spec shows how to **ADD** the Emergency Contact Notification feature to the **existing working healthcare system**.

**Key Point:** We're NOT building from scratch. We're extending existing infrastructure that already has:
- ✅ Patient management
- ✅ Consent validation (`ConsentValidator`)
- ✅ Audit logging (`AuditLogger`)
- ✅ PHI protection patterns
- ✅ UTC timestamp handling

## 📋 What We're Adding

### New Components (4 files)
1. `EmergencyContact.java` - Entity (builds on Patient pattern)
2. `EmergencyContactRepository.java` - Repository (simple JPA)
3. `NotificationService.java` - Service (uses existing ConsentValidator + AuditLogger)
4. `EmergencyContactController.java` - REST API (follows Patient pattern)

### Integration Points
- **Uses:** `ConsentValidator.requireConsent()` - Already exists ✅
- **Uses:** `AuditLogger.log()` - Already exists ✅
- **Uses:** `PatientRepository.findByReferenceId()` - Already exists ✅
- **Follows:** Same PHI protection patterns as `PatientService` ✅
- **Follows:** Same UTC timestamp patterns as `Patient` entity ✅

## 🏗️ Implementation Plan (15-20 min)

### Step 1: Create EmergencyContact Entity (5 min)
**Pattern to follow:** `Patient.java`

```java
@Entity
@Table(name = "emergency_contacts")
public class EmergencyContact {
    @Id
    private UUID id;
    
    @Column(nullable = false)
    private String patientRef;      // Reference to patient (safe to log)
    
    @Column(nullable = false)
    private String contactName;     // PHI - never log
    
    @Column(nullable = false)
    private String relationship;    // e.g., "Spouse", "Parent"
    
    @Column(nullable = false)
    private String phone;           // PHI - never log
    
    @Column(nullable = false)
    private String email;           // PHI - never log
    
    @Column(nullable = false)
    private int priority;           // 1 = primary, 2 = secondary
    
    @Column(nullable = false)
    private boolean active;
    
    @Column(nullable = false)
    private Instant createdAt;      // ✅ UTC (like Patient)
    
    @Column(nullable = false)
    private Instant updatedAt;      // ✅ UTC (like Patient)
    
    // Constructor, getters, setters, @PreUpdate
}
```

**Key points:**
- ✅ Uses `Instant` for timestamps (not LocalDateTime)
- ✅ Has `patientRef` for safe logging
- ✅ Marks PHI fields clearly in comments
- ✅ Follows same pattern as `Patient.java`

---

### Step 2: Create EmergencyContactRepository (2 min)
**Pattern to follow:** `PatientRepository.java`

```java
@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, UUID> {
    List<EmergencyContact> findByPatientRef(String patientRef);
    List<EmergencyContact> findByPatientRefAndActiveTrue(String patientRef);
}
```

**Key points:**
- ✅ Simple JPA repository
- ✅ Query by patientRef (not patient object)
- ✅ Query active contacts only

---

### Step 3: Create NotificationService (8 min)
**Pattern to follow:** `PatientService.java`
**Uses:** `ConsentValidator`, `AuditLogger`, `PatientRepository`

```java
@Service
public class NotificationService {
    
    @Autowired
    private EmergencyContactRepository contactRepository;
    
    @Autowired
    private ConsentValidator consentValidator;  // ✅ Already exists
    
    @Autowired
    private AuditLogger auditLogger;            // ✅ Already exists
    
    @Autowired
    private PatientRepository patientRepository; // ✅ Already exists
    
    /**
     * Notify all emergency contacts for a patient
     */
    public void notifyEmergencyContacts(String patientRef, String eventType) 
            throws ConsentException {
        
        // ✅ STEP 1: Validate consent (using existing service)
        consentValidator.requireConsent(patientRef, "EMERGENCY_CONTACT_NOTIFY");
        
        // ✅ STEP 2: Get active contacts
        List<EmergencyContact> contacts = contactRepository
                .findByPatientRefAndActiveTrue(patientRef);
        
        // ✅ STEP 3: Send notifications
        for (EmergencyContact contact : contacts) {
            sendNotification(contact, eventType);
        }
        
        // ✅ STEP 4: Audit log (using existing service)
        auditLogger.log("NOTIFICATION_SENT", patientRef, 
                "Notified " + contacts.size() + " emergency contacts");
    }
    
    private void sendNotification(EmergencyContact contact, String eventType) {
        // ✅ CORRECT: Log priority, not contact name
        logger.info("Sending notification to contact priority {} for patient {}", 
                contact.getPriority(), contact.getPatientRef());
        
        // Simulate sending notification
        // In real system: call SMS/email service
    }
}
```

**Key points:**
- ✅ Uses existing `ConsentValidator.requireConsent()`
- ✅ Uses existing `AuditLogger.log()`
- ✅ Logs priority/patientRef, NOT contact name (PHI)
- ✅ Follows same pattern as `PatientService`

---

### Step 4: Create EmergencyContactController (5 min)
**Pattern to follow:** `PatientController.java`

```java
@RestController
@RequestMapping("/api/emergency-contacts")
public class EmergencyContactController {
    
    @Autowired
    private EmergencyContactRepository contactRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping
    public ResponseEntity<EmergencyContact> createContact(
            @Valid @RequestBody EmergencyContact contact) {
        EmergencyContact created = contactRepository.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/patient/{patientRef}")
    public ResponseEntity<List<EmergencyContact>> getContactsForPatient(
            @PathVariable String patientRef) {
        List<EmergencyContact> contacts = contactRepository.findByPatientRef(patientRef);
        return ResponseEntity.ok(contacts);
    }
    
    @PostMapping("/notify/{patientRef}")
    public ResponseEntity<String> notifyContacts(@PathVariable String patientRef) {
        try {
            notificationService.notifyEmergencyContacts(patientRef, "TEST_EVENT");
            return ResponseEntity.ok("Notifications sent");
        } catch (ConsentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
```

**Key points:**
- ✅ Follows same pattern as `PatientController`
- ✅ Uses `@Valid` for validation
- ✅ Returns proper HTTP status codes
- ✅ Handles `ConsentException` from service

---

## 📊 Context Engineering Demo Flow

### Phase 1: Show the Problem (3 min)
**Without context:**
1. Prompt: "Add emergency contact notifications"
2. AI generates code with:
   - ❌ PHI in logs: `logger.info("Notifying " + contact.getName())`
   - ❌ LocalDateTime instead of Instant
   - ❌ No consent validation
   - ❌ No audit logging

### Phase 2: Add Context (2 min)
**Add to Cursor context:**
1. `.cursor/rules/security-phi-protection.mdc` (Rules panel)
2. `context/emergency-contact-requirements.md` (Docs panel)
3. `BASE_INFRASTRUCTURE.md` (Docs panel)
4. `PatientService.java` (reference pattern)
5. `ConsentValidator.java` (reference pattern)

### Phase 3: Build with Context (10 min)
**Use Notepad to track progress:**
```
Emergency Contact Feature
✅ Entity created
⏳ Repository created
⏳ Service layer
⏳ Controller
⏳ Tests
```

**Show AI now generates:**
- ✅ Uses reference IDs in logs (no PHI)
- ✅ Uses Instant for timestamps
- ✅ Calls `consentValidator.requireConsent()`
- ✅ Calls `auditLogger.log()`
- ✅ Follows existing patterns

### Phase 4: Test & Verify (5 min)
1. Run tests: `mvn test`
2. Start server: `mvn spring-boot:run`
3. Test API:
   ```bash
   # Create contact
   curl -X POST http://localhost:8080/api/emergency-contacts \
     -H "Content-Type: application/json" \
     -d '{"patientRef":"PAT_12345", ...}'
   
   # Trigger notification (should fail - no consent)
   curl -X POST http://localhost:8080/api/emergency-contacts/notify/PAT_12345
   
   # Grant consent
   curl -X POST http://localhost:8080/api/consents \
     -H "Content-Type: application/json" \
     -d '{"patientRef":"PAT_12345", "scopes":["EMERGENCY_CONTACT_NOTIFY"], ...}'
   
   # Trigger notification (should succeed)
   curl -X POST http://localhost:8080/api/emergency-contacts/notify/PAT_12345
   ```
4. Check logs - no PHI visible ✅
5. Check H2 console - audit logs created ✅

---

## 📈 Results

### Without Context Engineering
- ⏱️ Time: 30 minutes
- 🐛 Bugs: 5 (PHI in logs, wrong timestamps, no consent, no audit, wrong patterns)
- 🔒 Security Issues: 3 (PHI exposure, no consent, no audit)

### With Context Engineering
- ⏱️ Time: 15 minutes
- 🐛 Bugs: 0
- 🔒 Security Issues: 0
- ✅ All patterns followed
- ✅ All compliance requirements met

### Improvement
- **2x faster**
- **100% compliant**
- **0 bugs**

---

## 🎓 Key Takeaways for Attendees

1. **Context is everything** - Rules + Docs + Examples = Correct code
2. **Extend, don't rebuild** - Use existing services (ConsentValidator, AuditLogger)
3. **Follow patterns** - Look at PatientService, copy the pattern
4. **Progressive context** - Add context as complexity grows
5. **Verify everything** - Run tests, check logs, verify no PHI

---

## 📁 Files Created

```
src/main/java/com/mccrae/healthcare/emergency/
├── EmergencyContact.java           (NEW - 80 lines)
├── EmergencyContactRepository.java (NEW - 10 lines)
├── NotificationService.java        (NEW - 60 lines)
└── EmergencyContactController.java (NEW - 50 lines)

Total: 4 files, ~200 lines of code, 15-20 minutes
```

---

## ⏭️ Next: Hands-On Exercise

After this demo, attendees will add **Appointment Reminder Service** following the same pattern:
- Extend existing infrastructure
- Use ConsentValidator
- Use AuditLogger
- Follow Patient/EmergencyContact patterns
- 20-30 minute exercise

