# McRae Tech AI Training - Quick Start Guides

Simple step-by-step guides for developers to complete the workshop exercises.

---

## 🚀 Workshop 1: Auckland Java Consent

### Prerequisites
- Java 17+ installed
- Maven installed
- Cursor IDE

### Step-by-Step Guide

#### 1. Setup
```bash
cd examples/auckland-java-consent
mvn clean install
```

#### 2. Add Context to Cursor
1. Open Cursor IDE
2. Open the **Docs panel** (right sidebar)
3. Click **"Add Docs"**
4. Add these files:
   - `.cursor/rules/security-phi-protection.mdc`
   - `context/standards.md`
   - `prompts/enterprise_code.pattern.md`

#### 3. Fix the Expired Consents Bug
1. Open `ConsentService.java`
2. Find the TODO comment about expired consents
3. **Prompt Cursor**: "Fix the expired consent filtering bug following the enterprise code pattern"
4. **Verify**: The method now filters out expired consents using `Instant.now()`

#### 4. Add Scope Validation
1. Open `ConsentController.java`
2. **Prompt Cursor**: "Add scope validation to require EHR_VIEW scope for consent requests"
3. **Verify**: Controller validates required scopes before processing

#### 5. Generate Documentation
1. **Prompt Cursor**: "Generate SDK_README.md for this consent service following the docs pattern"
2. **Verify**: Clear API documentation is created

#### 6. Test Everything
```bash
mvn test
mvn spring-boot:run
```
Test endpoint: `curl http://localhost:8080/api/consents`

#### ✅ Success Criteria
- [ ] Expired consents are filtered correctly
- [ ] Scope validation prevents invalid requests  
- [ ] SDK documentation is clear and complete
- [ ] All tests pass
- [ ] No PHI in logs

---

## 🔒 Thailand .NET Legacy

### Prerequisites
- .NET 8.0+ installed  
- Cursor IDE

### Step-by-Step Guide

#### 1. Setup
```bash
cd examples/thailand-dotnet-legacy
dotnet restore
dotnet build
```

#### 2. Add Security Context First
**CRITICAL: Always add security rules first for healthcare projects**
1. Open Cursor **Docs panel**
2. Add these files in order:
   - `.cursor/rules/security-phi-protection.mdc` (**FIRST!**)
   - `.cursor/rules/temporal-utc-standard.mdc` (**SECOND!**)
   - `context/standards.md`
   - `prompts/enterprise_code.pattern.md`

#### 3. Fix PHI Logging
1. Open `LegacyPatientsController.cs`
2. Find the TODO about PHI logging
3. **Prompt Cursor**: "Fix the PHI logging issue - log only reference IDs, never patient names or sensitive data"
4. **Verify**: Logs show only patient references, no names or PHI

#### 4. Add Input Validation
1. **Prompt Cursor**: "Add comprehensive input validation to all controller endpoints following healthcare standards"
2. **Verify**: All inputs are validated, with proper error messages

#### 5. Fix UTC DateTime Handling
1. **Prompt Cursor**: "Ensure all DateTime fields use UTC - convert inputs to UTC and return UTC timestamps"
2. **Verify**: All timestamps use `DateTime.UtcNow` and `.ToUniversalTime()`

#### 6. Generate API Documentation
1. **Prompt Cursor**: "Generate SDK_README.md with security notes and UTC timestamp requirements"
2. **Verify**: Documentation includes security warnings and UTC requirements

#### 7. Security Review
1. **Prompt Cursor with Claude Sonnet**: "Perform a security review of this healthcare API focusing on PHI protection"
2. Fix any issues found

#### 8. Test Everything
```bash
dotnet test
dotnet run
```
Test endpoint: `curl http://localhost:5000/api/patients`

#### ✅ Success Criteria
- [ ] **NO PHI in application logs (CRITICAL)**
- [ ] All inputs validated properly
- [ ] All DateTime values are UTC  
- [ ] API documentation includes security notes
- [ ] Security review passed
- [ ] All tests pass

---

## 🧠 Workshop 3: Build Appointment Reminder Service (Advanced)

### Prerequisites
- Java 17+ installed
- Maven installed
- Understanding of Spring Boot

### Step-by-Step Guide

#### 1. Setup Base Context
```bash
cd examples/appointment-reminder-service
mvn clean install
```

#### 2. Progressive Context Loading
Add to Cursor in this **exact order**:
1. `.cursor/rules/security-phi-protection.mdc`
2. `.cursor/rules/temporal-utc-standard.mdc`  
3. `BASE_INFRASTRUCTURE.md`
4. `DEMO_FEATURE_SPEC.md`

#### 3. Create Notepad for Tracking
Create a new notepad in Cursor:
```
Appointment Reminder Feature Progress
⏳ Appointment entity
⏳ AppointmentRepository  
⏳ ReminderService
⏳ AppointmentController
⏳ Tests
⏳ Documentation
```

#### 4. Build Appointment Entity
1. Look at existing `Patient.java` as reference
2. **Prompt Cursor**: "Create Appointment entity following the Patient pattern with Instant timestamps and PHI comments"
3. **Verify**: Uses Instant, has patientRef, marks PHI fields in comments
4. Update notepad: ✅ Appointment entity

#### 5. Build Repository Layer
1. Look at existing `PatientRepository.java` as reference
2. **Prompt Cursor**: "Create AppointmentRepository following the PatientRepository pattern"
3. **Verify**: Extends JpaRepository, has proper query methods
4. Update notepad: ✅ AppointmentRepository

#### 6. Build Service Layer
1. Look at existing `ConsentValidator.java` and `AuditLogger.java`
2. **Prompt Cursor**: "Create ReminderService that validates APPOINTMENT_REMINDER consent and logs audit events"
3. **Key requirements**:
   - Uses `consentValidator.requireConsent(patientRef, "APPOINTMENT_REMINDER")`
   - Uses `auditLogger.log("REMINDER_SENT", patientRef, details)`
   - Logs reference IDs only, never names
4. **Verify**: Service validates consent before sending reminders
5. Update notepad: ✅ ReminderService

#### 7. Build Controller Layer
1. Look at existing `PatientController.java` as reference
2. **Prompt Cursor**: "Create AppointmentController following PatientController REST patterns"
3. **Verify**: Follows REST conventions, handles exceptions properly
4. Update notepad: ✅ AppointmentController

#### 8. Create Tests
1. **Prompt Cursor**: "Create comprehensive tests for all appointment components"
2. **Verify**: Tests cover consent validation, PHI protection, UTC handling
3. Update notepad: ✅ Tests

#### 9. Generate Documentation
1. **Prompt Cursor**: "Generate complete API documentation for the appointment reminder feature"
2. Update notepad: ✅ Documentation

#### 10. Integration Testing
```bash
mvn test
mvn spring-boot:run

# Create appointment
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientRef": "PAT_12345",
    "appointmentType": "Checkup", 
    "scheduledTime": "2024-12-01T10:00:00Z",
    "location": "Room 101",
    "providerName": "Dr. Smith"
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

#### ✅ Success Criteria
- [ ] Complete appointment management system
- [ ] Consent validation working
- [ ] No PHI in logs
- [ ] UTC timestamps throughout  
- [ ] Comprehensive tests passing
- [ ] API documentation complete
- [ ] Integration tests working

---

## 🛠️ Creating Custom Cursor Rules

### Step-by-Step Guide

#### 1. Identify Your Pain Point
Ask yourself:
- What mistake do developers keep making?
- What pattern should be enforced?
- What security requirement is often missed?

#### 2. Create the Rule File
Create `.cursor/rules/your-rule-name.mdc`:

```yaml
---
description: One-line description of what this rule does
globs: ["**/*.java", "**/*.cs"]  # File patterns to apply to
---
# Your Rule Name

## WHEN TO APPLY
- Specific condition 1
- Specific condition 2

## DETECTION PATTERNS
- Pattern to detect 1  
- Pattern to detect 2

## AUTOMATIC FIXES
Replace:
```java
// Bad pattern
String sql = "SELECT * FROM users WHERE id = " + userId;
```

With:
```java  
// Good pattern
String sql = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, userId);
```
```

#### 3. Test Your Rule
1. Save the file in `.cursor/rules/`
2. Restart Cursor IDE
3. Open a file matching your glob pattern
4. Write code that should trigger the rule
5. Verify Cursor suggests the fix

#### 4. Refine
- Adjust glob patterns if needed
- Add more detection patterns
- Improve fix examples
- Test with different code scenarios

### Example: Authorization Rule
```yaml
---
description: Enforce authorization checks on HTTP endpoints
globs: ["**/Controllers/**/*.cs", "**/controllers/**/*.ts"]
---
# Authorization Enforcement

## WHEN TO APPLY
- Any HTTP endpoint (GET, POST, PUT, DELETE)
- Methods handling sensitive data
- Admin-only operations

## DETECTION PATTERNS  
- HTTP method attributes without [Authorize]
- Endpoints with names containing: Admin, Delete, Sensitive

## AUTOMATIC FIXES
Replace:
```csharp
[HttpDelete("{id}")]
public async Task<IActionResult> DeleteUser(string id)
```

With:
```csharp
[HttpDelete("{id}")]
[Authorize(Roles = "Admin")]
public async Task<IActionResult> DeleteUser(string id)  
```
```

---

## 📝 Creating Custom Commands

### Step-by-Step Guide

#### 1. Identify Repeated Workflow
What do you do repeatedly?
- Code reviews
- Feature setup  
- Bug fix procedures
- Security audits

#### 2. Create Command File
Create `.cursor/commands/your-command.md`:

```markdown
# Code Review: Security Check

Perform security-focused code review for healthcare applications.

## Constraints
- Follow security-phi-protection.mdc rules
- Check for PHI exposure  
- Verify authorization on endpoints
- Ensure UTC timestamps

## Steps

### 1) PHI Protection Check
- No patient names in logs
- Only reference IDs logged
- No PHI in error messages

### 2) Authorization Check  
- All endpoints have [Authorize] attributes
- Admin operations require admin role
- Consent checks present

### 3) UTC Timestamp Check
- All DateTime fields use UTC
- No local time conversions
- Consistent timestamp format

## Output Format
```markdown
# Security Review Results

## ✅ Passed
- Authorization properly implemented
- No PHI exposure found  

## ⚠️ Issues Found
- Missing [Authorize] on DELETE endpoint
- Patient name logged in line 45

## 💡 Recommendations  
- Add role-based authorization
- Use reference IDs in logging
```
```

#### 3. Test Command
1. Save file in `.cursor/commands/`
2. Open Cursor chat (Cmd+L)
3. Type `/` to see your command
4. Run `/your-command` 
5. Verify output matches expectations

### Example: Feature Setup Command
```markdown
# Setup Feature: New Healthcare Feature

Set up a new feature following healthcare patterns.

## Constraints
- Follow security-phi-protection.mdc
- Use UTC timestamps
- Include consent validation
- Add audit logging

## Steps

### 1) Create Entity
- Use Instant for timestamps  
- Add patientRef field
- Mark PHI fields in comments

### 2) Create Repository
- Extend JpaRepository
- Add query methods
- Include patient reference queries

### 3) Create Service  
- Add consent validation
- Include audit logging
- Use reference IDs only

### 4) Create Controller
- Add authorization attributes
- Include input validation
- Follow REST patterns

## Output Format
Complete feature structure with all required components.
```

---

## 💡 Pro Tips for All Workshops

### Model Selection Strategy
- **Quick fixes**: Use `gemini-2.5-flash` 
- **Code generation**: Use `gemini-2.5-pro` or `grok-4`
- **Planning/review**: Use `claude-4.5-sonnet`

### Context Management
1. **Start small** - Add 1-2 files at a time
2. **Security first** - Always add security rules before coding in healthcare
3. **Progressive loading** - Add context as you need it
4. **Use notepads** - Track progress across sessions

### Common Issues
- **Rules not applying**: Restart Cursor, check glob patterns
- **Wrong code generated**: Add more specific context files
- **PHI in logs**: Always use reference IDs, never names
- **UTC issues**: Use Instant/DateTime.UtcNow consistently

### Verification Workflow
1. Generate code with medium model
2. Verify security with Claude Sonnet  
3. Test functionality
4. Document decisions in notepad

---

## 🎯 Next Steps After Workshops

1. **Apply to real projects** - Use these patterns daily
2. **Create team rules** - Build domain-specific rule library
3. **Train team members** - Share knowledge and patterns
4. **Measure impact** - Track code quality improvements
5. **Iterate and improve** - Refine rules and commands

**Ready to start? Pick your workshop level and dive in! 🚀**