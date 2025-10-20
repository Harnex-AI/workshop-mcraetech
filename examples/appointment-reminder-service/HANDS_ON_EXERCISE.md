# Hands-On Exercise: Appointment Reminder Service

## 🎯 Objective
Practice context engineering by building an Appointment Reminder Service using the same techniques demonstrated in the Emergency Contact Notification demo.

---

## 📖 The Scenario

### Business Problem
Patients frequently miss medical appointments, leading to:
- Wasted clinical resources
- Delayed care
- Revenue loss for the practice

### Your Task
Build an Appointment Reminder Service that:
- Sends reminders 24 hours before appointments
- Validates consent before sending
- Protects PHI (only shares permitted information)
- Handles timezones correctly
- Creates audit logs for compliance

---

## 📋 Requirements

### Functional Requirements

#### 1. Appointment Entity
- id (UUID)
- patientRef (String)
- appointmentType (Enum: CONSULTATION, FOLLOW_UP, PROCEDURE, LAB_WORK)
- scheduledAt (Instant, UTC)
- duration (Integer, minutes)
- location (String, e.g., "Room 301, Auckland Clinic")
- providerName (String)
- status (Enum: SCHEDULED, REMINDED, COMPLETED, CANCELLED)
- createdAt (Instant, UTC)
- updatedAt (Instant, UTC)

#### 2. Reminder Preferences
- patientRef (String)
- preferredMethod (Enum: SMS, EMAIL, PHONE)
- reminderTiming (Integer, hours before appointment, default 24)
- timezone (String, e.g., "Pacific/Auckland")
- language (String, default "en")

#### 3. Reminder Content
Based on consent scope:

**No Consent or Expired:**
- Facility name and phone number only
- Generic message: "You have an upcoming appointment"

**Standard Consent (`APPOINTMENT_REMINDER`):**
- Appointment date and time (in patient's timezone)
- Facility name and location
- General appointment type (not specific details)
- Contact number to confirm/cancel

**Detailed Consent (`APPOINTMENT_REMINDER_DETAILED`):**
- All standard information PLUS:
- Provider name
- Specific appointment type
- Preparation instructions (if any)

#### 4. Delivery Rules
- **SMS:** For appointments within 48 hours
- **Email:** For appointments more than 48 hours away
- **Retry:** Once after 1 hour if delivery fails
- **Timing:** Send exactly 24 hours before (or custom timing from preferences)

#### 5. Audit Logging
Log every reminder with:
- Timestamp (UTC)
- Patient reference
- Appointment ID
- Consent scope used
- PHI fields shared
- Delivery method and status
- Retry attempts

---

## 🛠️ Setup

### 1. Create Project Structure
```bash
cd examples/context-engineering-demo
mkdir -p src/main/java/com/mccrae/appointments
mkdir -p src/test/java/com/mccrae/appointments
```

### 2. Create Spec
Create `spec/todo/appointment-reminder-service.md` with:
- Requirements (copy from above)
- Design (entities, services, architecture)
- Tasks (broken down into phases)
- Constraints (reference existing rules and docs)

### 3. Create Context Docs
Create `context/appointment-reminder-requirements.md` with:
- Detailed business rules
- PHI sharing matrix for appointments
- Reminder timing logic
- Edge cases

---

## 📝 Exercise Steps

### Phase 1: Context Setup (15 min)

#### Step 1.1: Review Existing Rules
1. Open `.cursor/rules/security-phi-protection.mdc`
2. Open `.cursor/rules/consent-validation.mdc`
3. Open `.cursor/rules/temporal-utc-standard.mdc`
4. **Question:** Do these rules apply to appointment reminders? How?

#### Step 1.2: Create Context Doc
1. Create `context/appointment-reminder-requirements.md`
2. Include:
   - What PHI can be shared (based on consent)
   - Reminder timing rules
   - Delivery method selection logic
   - Edge cases (cancelled appointments, timezone changes)

#### Step 1.3: Add to Cursor Docs
1. Open Cursor Docs panel
2. Add your new context doc
3. Add existing PHI protection and audit log specs

#### Step 1.4: Create Notepad
1. Create notepad: "Appointment Reminder Context"
2. Add:
   - Decision: SMS for urgent (< 48 hrs), email for non-urgent
   - Decision: Retry once after 1 hour
   - Blocker: None currently

---

### Phase 2: Build Core Entities (20 min)

#### Step 2.1: Create Appointment Entity
**Prompt:** "Create an Appointment entity following the spec. Use UTC for timestamps and include all required fields."

**Verify:**
- [ ] Uses `Instant` for timestamps (not LocalDateTime)
- [ ] Has all required fields
- [ ] Has proper validation annotations
- [ ] Follows Spring Boot best practices

#### Step 2.2: Create ReminderPreferences Entity
**Prompt:** "Create a ReminderPreferences entity for storing patient reminder preferences."

**Verify:**
- [ ] Includes timezone field
- [ ] Has default values (24 hours, SMS)
- [ ] Uses enums for preferredMethod

#### Step 2.3: Create Tests
**Prompt:** "Create tests for Appointment entity using synthetic data."

**Verify:**
- [ ] Test data is synthetic (TEST_PAT_001, not "John Smith")
- [ ] Tests cover validation
- [ ] Tests cover all fields

---

### Phase 3: Build Reminder Service (25 min)

#### Step 3.1: Create ConsentValidator
**Prompt:** "Create a ConsentValidator service that validates consent for appointment reminders. Check for APPOINTMENT_REMINDER and APPOINTMENT_REMINDER_DETAILED scopes."

**Verify:**
- [ ] Checks consent expiry
- [ ] Checks required scopes
- [ ] Throws exception if invalid

#### Step 3.2: Create ReminderContentBuilder
**Prompt:** "Create a ReminderContentBuilder that builds reminder content based on consent scope. Follow the PHI sharing rules in the context doc."

**Verify:**
- [ ] No consent → minimal message
- [ ] Standard consent → basic appointment info
- [ ] Detailed consent → additional info (provider, type)
- [ ] Converts time to patient's timezone

#### Step 3.3: Create ReminderService
**Prompt:** "Create a ReminderService that orchestrates sending reminders. It should validate consent, build content, deliver, and audit log."

**Verify:**
- [ ] Validates consent first
- [ ] Builds content based on consent
- [ ] Selects delivery method (SMS < 48hrs, email otherwise)
- [ ] Creates audit log entry
- [ ] Handles errors gracefully

---

### Phase 4: Testing & Validation (20 min)

#### Step 4.1: Unit Tests
**Prompt:** "Create comprehensive unit tests for ReminderContentBuilder covering all consent scenarios."

**Tests should cover:**
- [ ] No consent → minimal message
- [ ] Expired consent → minimal message
- [ ] Standard consent → basic info
- [ ] Detailed consent → full info
- [ ] Timezone conversion (Auckland → London)

#### Step 4.2: Integration Test
**Prompt:** "Create an integration test for the full reminder flow: validate consent → build content → deliver → audit log."

**Verify:**
- [ ] End-to-end flow works
- [ ] Audit log created
- [ ] No PHI in logs
- [ ] Consent validated

#### Step 4.3: Run Tests
```bash
mvn test
```

**Fix any failures:**
- Add terminal output to context
- Ask: "Fix the test failures shown in the terminal"

---

### Phase 5: Context Management (10 min)

#### Step 5.1: Review Context Window
**Check what's in your context:**
- Rules (always active)
- Context docs (how many?)
- Notepad
- Terminal output
- Current files

**Question:** Is this too much? Too little? Just right?

#### Step 5.2: Clean Up Context
1. Check off completed tasks in spec
2. Clear old terminal output
3. Update notepad with progress
4. Remove irrelevant docs from panel

#### Step 5.3: Move Spec to Done
```bash
mv spec/todo/appointment-reminder-service.md spec/done/
```

---

## ✅ Success Criteria

### Code Quality
- [ ] All tests passing
- [ ] No PHI in logs (grep for "TEST_PAT")
- [ ] All timestamps in UTC
- [ ] Consent validated before operations
- [ ] Audit logs created

### Context Engineering
- [ ] Used existing rules effectively
- [ ] Created helpful context docs
- [ ] Managed context window (added/removed as needed)
- [ ] Used notepad for decisions
- [ ] Followed spec-driven workflow

### Learning Objectives
- [ ] Understand how rules guide AI behavior
- [ ] Know when to add/remove context
- [ ] Can create effective context docs
- [ ] Can use spec-driven development
- [ ] Can manage context window effectively

---

## 🎓 Reflection Questions

After completing the exercise, reflect on:

### 1. Context Impact
- How did the rules change the AI's code generation?
- What would the code look like without the PHI protection rule?
- Did the context docs help the AI understand requirements?

### 2. Context Management
- When did you add context? When did you remove it?
- Did you have too much context at any point?
- How did notepads help (or not help)?

### 3. Workflow
- Was spec-driven development helpful?
- Did checking off tasks help you stay focused?
- Would you use this workflow in your daily work?

### 4. Challenges
- What was hardest about context engineering?
- Where did the AI struggle despite good context?
- What would you do differently next time?

---

## 🚀 Bonus Challenges

If you finish early, try these:

### Challenge 1: Add a New Rule
Create `.cursor/rules/appointment-validation.mdc` that enforces:
- Appointments must be in the future
- Duration must be 15, 30, 45, or 60 minutes
- No appointments on weekends (for this clinic)

### Challenge 2: Handle Edge Cases
Add logic for:
- Appointment cancelled after reminder sent
- Patient changes timezone preference
- Multiple reminders for same appointment (shouldn't happen)

### Challenge 3: Add Scheduling
Create a scheduled job that:
- Runs every hour
- Finds appointments 24 hours away
- Sends reminders automatically
- Logs all actions

### Challenge 4: Create API Documentation
**Prompt:** "Generate API documentation for the Appointment Reminder Service including endpoints, request/response examples, and consent requirements."

---

## 📚 Resources

### Reference Materials
- **Demo:** `DEMO_SCRIPT.md` - See how it was done
- **Quick Reference:** `QUICK_REFERENCE.md` - Context engineering patterns
- **Rules:** `.cursor/rules/` - Existing domain rules
- **Context Docs:** `context/` - Example business requirements

### Help
If you get stuck:
1. Review the Emergency Contact demo
2. Check the Quick Reference
3. Ask the instructor
4. Pair with another attendee

---

## 🎯 Deliverables

By the end of this exercise, you should have:

1. **Code:**
   - Appointment entity
   - ReminderPreferences entity
   - ConsentValidator service
   - ReminderContentBuilder service
   - ReminderService (orchestrator)
   - Comprehensive tests

2. **Context:**
   - Context doc: `appointment-reminder-requirements.md`
   - Spec: `spec/done/appointment-reminder-service.md` (moved from todo)
   - Notepad: Decisions and progress

3. **Understanding:**
   - How rules guide AI behavior
   - When to add/remove context
   - How to use spec-driven development
   - How to manage context window

---

## 💡 Tips for Success

1. **Start Small:** Don't try to build everything at once
2. **Use Context:** Reference rules and docs explicitly in prompts
3. **Test Often:** Run tests after each component
4. **Manage Context:** Add/remove as needed, don't accumulate
5. **Ask Questions:** If stuck, ask the AI to explain its reasoning

---

## 🏆 Completion

When you're done:
1. Run all tests: `mvn test`
2. Check for PHI violations: `grep -r "John\|Jane\|Smith" src/`
3. Verify all timestamps are UTC
4. Review audit logs
5. Share your experience with the group

**Congratulations! You've practiced context engineering! 🎉**

---

## 📝 Feedback

Please provide feedback on:
- What worked well?
- What was confusing?
- What would you change?
- Will you use this in your work?

Your feedback helps improve the workshop for future attendees.

