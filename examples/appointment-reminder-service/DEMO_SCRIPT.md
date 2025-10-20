# Context Engineering Demo: Emergency Contact Notification Feature

## 🎯 Demo Objective
Showcase how effective context management in Cursor enables developers to build complex, compliant healthcare features efficiently.

## 📖 Story & Pain Point

**The Problem:**
A patient is admitted to the emergency department. The clinical team needs to notify the patient's emergency contact, but:
- ❌ Current system has no emergency contact notification capability
- ❌ Developers don't know what PHI can be shared with emergency contacts
- ❌ Consent requirements are unclear
- ❌ Timezone handling causes missed notifications
- ❌ No audit trail for compliance

**The Solution:**
Build an Emergency Contact Notification Service that:
- ✅ Validates consent before sharing patient information
- ✅ Protects PHI (only shares what's permitted)
- ✅ Handles timezones correctly (patient in Auckland, contact in London)
- ✅ Creates audit logs for compliance
- ✅ Follows McRae Tech coding standards

**Why This Resonates:**
Every developer has experienced the pain of building features in regulated domains without clear guidance. This demo shows how context engineering solves that.

---

## 🎬 Demo Flow (30-40 minutes)

### **Phase 1: The Problem (5 min)**
**Show the pain without context engineering**

1. Open empty Java project
2. Ask Cursor: "Create an emergency contact notification service"
3. **Watch it fail:**
   - Logs full patient names (PHI violation)
   - Uses `LocalDateTime` (timezone bugs)
   - No consent checking
   - Realistic test data (compliance violation)

**Key Message:** "Without context, even the best AI makes dangerous mistakes in healthcare."

---

### **Phase 2: Context Engineering Setup (10 min)**
**Build the context layer by layer**

#### 2.1 Add Domain Rules (Cursor Rules)
1. **Add `.cursor/rules/security-phi-protection.mdc`**
   - Show how it prevents PHI logging
   - Demonstrate auto-suggestions changing

2. **Add `.cursor/rules/consent-validation.mdc`**
   - Show consent requirement enforcement
   - Watch Cursor suggest consent checks

3. **Add `.cursor/rules/temporal-utc-standard.mdc`**
   - Show UTC enforcement
   - Watch `LocalDateTime` get flagged

**Demo Technique:**
- Split screen: Code editor + Rules panel
- Type bad code, watch Cursor correct it in real-time
- Show the "why" in rule descriptions

#### 2.2 Add Context Docs
1. **Add to Cursor Docs panel:**
   - `context/emergency-contact-requirements.md` (business rules)
   - `context/phi-sharing-matrix.md` (what PHI is allowed)
   - `context/audit-log-spec.md` (compliance requirements)

**Demo Technique:**
- Show Docs panel in sidebar
- Ask: "What PHI can I share with emergency contacts?"
- Watch Cursor reference the exact doc

#### 2.3 Add Notepads (Agent Memory)
1. **Create notepad: "Emergency Contact Feature Context"**
   - Add: Current task, decisions made, blockers
   - Show how it persists across chat sessions

**Demo Technique:**
- Make a decision: "Use SMS for notifications"
- Add to notepad
- Clear chat, ask "How are we notifying contacts?"
- Watch Cursor reference notepad

---

### **Phase 3: Building with Context (15 min)**
**Show the full context engineering workflow**

#### 3.1 Spec-Driven Development
1. **Create spec in `spec/todo/emergency-contact-notification.md`**
   ```markdown
   # Emergency Contact Notification Feature
   
   ## Requirements
   - Validate consent before notification
   - Share only permitted PHI
   - Handle timezone conversions
   - Create audit log entries
   
   ## Tasks
   - [ ] Create EmergencyContact entity
   - [ ] Create NotificationService
   - [ ] Add consent validation
   - [ ] Add audit logging
   - [ ] Write tests
   ```

2. **Use Cursor Task Management**
   - Show task list in sidebar
   - Check off tasks as you build
   - Watch context window stay clean

#### 3.2 Iterative Development with Context
**Task 1: Create EmergencyContact Entity**
- Ask Cursor: "Create EmergencyContact entity following our standards"
- **Show context in action:**
  - Uses UTC for timestamps (temporal rule)
  - Synthetic test data (PHI rule)
  - Proper field validation

**Task 2: Create NotificationService**
- Ask: "Create notification service that validates consent"
- **Show context in action:**
  - Adds consent parameter (consent rule)
  - Redacts PHI in logs (PHI rule)
  - References emergency-contact-requirements.md

**Task 3: Handle Timezone Conversion**
- Ask: "Convert notification time to contact's timezone"
- **Show context in action:**
  - Stores in UTC, converts for display (temporal rule)
  - References audit-log-spec.md for logging

#### 3.3 Terminal & Linter Integration
1. **Run build: `mvn clean install`**
   - Show terminal output in Cursor
   - Add terminal output to context
   - Ask: "Fix the compilation errors"
   - Watch Cursor reference terminal output

2. **Run linter/tests**
   - Show test failures
   - Add linter errors to context
   - Ask: "Fix these test failures"
   - Watch Cursor fix them systematically

#### 3.4 Progressive Context Management
**Show context window management:**
1. **Early stage:** Rules + 1 doc + current file
2. **Mid stage:** Rules + 3 docs + notepad + terminal
3. **Late stage:** Mark spec tasks done, remove from context

**Demo Technique:**
- Show context panel (what's in the window)
- Explain: "As code grows, we mark tasks done and move specs to /done"
- Move completed spec: `spec/todo/` → `spec/done/`
- Show how this keeps context focused

---

### **Phase 4: The Payoff (5 min)**
**Show the final result**

1. **Review generated code:**
   - Compliant with all rules
   - Follows domain requirements
   - Properly tested
   - Audit logged

2. **Compare to Phase 1:**
   - Before: Dangerous, non-compliant code
   - After: Production-ready, compliant code
   - **Same AI, different context**

3. **Show the artifacts:**
   - ✅ Spec moved to `spec/done/`
   - ✅ Code in `src/main/java/`
   - ✅ Tests passing
   - ✅ Audit logs working
   - ✅ Documentation generated

---

## 🎓 Key Teaching Points

### 1. **Context is Code**
"Your rules, docs, and specs are as important as your source code. They guide the AI to write compliant code."

### 2. **Layer Context Progressively**
"Don't dump everything at once. Add context as complexity grows:
- Start: Rules only
- Add: Domain docs when needed
- Add: Terminal output when debugging
- Add: Linter errors when fixing"

### 3. **Context Window Management**
"As your codebase grows, manage context like memory:
- Mark tasks done → move specs to /done
- Remove old terminal output
- Keep only relevant docs in panel"

### 4. **Spec-Driven Development**
"The todo→done→docs workflow keeps the AI focused:
- `/spec/todo/` = What we're building now (in context)
- `/spec/done/` = What we built (out of context)
- `/docs/` = Generated documentation (reference)"

### 5. **Multi-Modal Context**
"Cursor can use:
- Files (code, specs, docs)
- Folders (entire modules)
- Notepads (decisions, context)
- Docs (domain knowledge)
- Terminal (build output, errors)
- Linter (code quality issues)"

---

## 📁 Demo Repository Structure

```
examples/context-engineering-demo/
├── DEMO_SCRIPT.md (this file)
├── README.md (setup instructions)
├── .cursor/
│   ├── rules/
│   │   ├── security-phi-protection.mdc
│   │   ├── consent-validation.mdc
│   │   └── temporal-utc-standard.mdc
│   └── commands/
│       └── build-feature.md
├── context/
│   ├── emergency-contact-requirements.md
│   ├── phi-sharing-matrix.md
│   └── audit-log-spec.md
├── spec/
│   ├── todo/
│   │   └── emergency-contact-notification.md (start here)
│   └── done/
│       └── (move here when complete)
├── src/main/java/com/mccrae/emergency/
│   └── (generated during demo)
└── src/test/java/com/mccrae/emergency/
    └── (generated during demo)
```

---

## 🎯 Success Metrics

By the end of this demo, developers should understand:
1. ✅ How to create and use Cursor rules for domain enforcement
2. ✅ How to add context docs to guide AI behavior
3. ✅ How to use notepads for persistent context
4. ✅ How to manage context window as code grows
5. ✅ How to use spec-driven development (todo→done→docs)
6. ✅ How to integrate terminal output and linter errors into context

---

## 🚀 Next Steps After Demo

1. **Hands-on Exercise:** Developers build a similar feature (e.g., "Appointment Reminder Service")
2. **Create Their Own Rules:** Use the pain points from their domain
3. **Build Their Context Library:** Document their domain requirements
4. **Practice Context Management:** Build a feature using todo→done→docs workflow

