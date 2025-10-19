# Workshop Demo Preparation Guide

## Workshop 1: Demo Examples to Prepare

### Demo 1: Advanced Prompt Engineering (20 min)

**Feature to Demonstrate:** Patient Appointment Validation Service

**What to Prepare:**

1. **`AGENTS.md` file with McCraeTech standards (in project root):**
```markdown
# McCraeTech AI Development Standards

## Healthcare Context
- All code must be auditable and traceable
- Patient data privacy is paramount (HIPAA/GDPR compliance)
- Error handling must be explicit and logged
- All changes must be spec-driven (TODO → DONE → DOCS)

## Code Quality Standards
- TypeScript strict mode enabled
- 100% test coverage for critical paths
- No any types without explicit justification
- All functions must have JSDoc comments
- Error messages must be user-friendly and actionable

## Spec-Driven Development
- Always check /specs/*/TODO.md before starting work
- Never mark work as DONE without passing tests
- Auto-generate docs from DONE specs
- Use verification prompts before committing
```

2. **Prompt Pattern Templates:**

**`/prompts/planning.pattern.md`:**
```markdown
# Planning Prompt Pattern

## Structure
I need to implement [FEATURE]. Before writing any code:

1. Read the TODO spec at /specs/[feature]/TODO.md
2. Break down the work into discrete tasks
3. Identify dependencies and order of implementation
4. List potential edge cases and error scenarios
5. Estimate complexity for each task
6. Recommend model selection for each task

## Example
"I need to implement patient appointment validation. Before writing code, read /specs/appointment-validation/TODO.md and create a task breakdown with model recommendations."
```

**`/prompts/reasoning.pattern.md`:**
```markdown
# Reasoning Prompt Pattern

## Structure
For [TASK], explain your reasoning:

1. What are the requirements from the TODO spec?
2. What are the alternative approaches?
3. What are the tradeoffs of each approach?
4. Which approach best meets our McCraeTech standards?
5. What are the potential risks?
6. How will we verify correctness?

## Example
"For appointment validation, explain your reasoning for the validation approach, considering our healthcare compliance requirements."
```

**`/prompts/verification.pattern.md`:**
```markdown
# Verification Prompt Pattern

## Structure
Before marking this as DONE, verify:

1. All acceptance criteria from TODO spec are met
2. All tests pass (unit + integration)
3. Code follows McCraeTech standards
4. Error handling is comprehensive
5. Documentation is complete
6. No security vulnerabilities introduced
7. Performance is acceptable

## Example
"Verify that the appointment validation implementation meets all TODO criteria, passes all tests, and follows McCraeTech standards."
```

3. **Example TODO Spec:**

**`/specs/appointment-validation/TODO.md`:**
```markdown
# Appointment Validation Service - TODO

## Requirements
Validate patient appointment requests before booking.

## Acceptance Criteria
1. Validate appointment date is in the future
2. Validate appointment time is within business hours (9 AM - 5 PM)
3. Validate patient exists in system
4. Validate doctor is available at requested time
5. Return clear error messages for each validation failure
6. Log all validation attempts for audit trail

## Non-Functional Requirements
- Response time < 100ms
- 100% test coverage
- HIPAA-compliant logging (no PII in logs)

## Edge Cases to Handle
- Timezone differences
- Public holidays
- Doctor emergency unavailability
- Concurrent booking attempts
```

**Live Demo Flow:**
1. Show the TODO spec
2. Use planning prompt to break down work
3. Use reasoning prompt to validate approach
4. Show how `AGENTS.md` enforces standards
5. Generate initial implementation
6. Use verification prompt to check completeness

---

### Demo 2: Spec-Driven Context Engineering (30 min)

**Feature to Demonstrate:** Patient Data Export Service

**What to Prepare:**

1. **`/context/Agent.md`:**
```markdown
# Agent Instructions for McCraeTech Development

## Always Start Here
1. Check if there's a TODO spec in /specs/[feature]/TODO.md
2. If no TODO exists, ask the developer to create one
3. Read the TODO spec completely before starting work
4. Follow the McCraeTech standards in /context/standards.md

## Workflow
1. TODO Phase: Read spec, use planning prompts
2. Implementation Phase: Generate code with appropriate model
3. Verification Phase: Use verification prompts, run tests
4. DONE Phase: Move TODO → DONE only when verified
5. DOCS Phase: Auto-generate documentation from DONE spec

## Model Selection Guidelines
- Small models (Haiku): Linting, formatting, simple edits
- Medium models (Sonnet): Code generation, refactoring
- Large models (Opus): Architecture, verification, complex reasoning

## Never
- Skip tests
- Mark as DONE without verification
- Generate code without reading TODO spec
- Ignore McCraeTech standards
```

2. **`/context/standards.md`:**
```markdown
# McCraeTech Coding Standards

## TypeScript Standards
- Strict mode enabled
- No `any` types without justification
- Explicit return types on all functions
- Comprehensive error handling

## Testing Standards
- 100% coverage for critical paths
- Unit tests for all business logic
- Integration tests for API endpoints
- E2E tests for critical user flows

## Healthcare Compliance
- No PII in logs
- All data access audited
- Encryption at rest and in transit
- HIPAA/GDPR compliance checks

## Documentation Standards
- JSDoc for all public APIs
- README for each service
- Architecture decision records (ADRs)
- Auto-generated from DONE specs
```

3. **Example TODO Spec:**

**`/specs/patient-data-export/TODO.md`:**
```markdown
# Patient Data Export Service - TODO

## Requirements
Allow patients to export their medical data in multiple formats.

## Acceptance Criteria
1. Support export formats: PDF, JSON, CSV
2. Include all patient data: demographics, appointments, prescriptions, lab results
3. Redact sensitive information based on user role
4. Generate audit log entry for each export
5. Support date range filtering
6. Email export file to patient (encrypted)

## Non-Functional Requirements
- Export generation < 5 seconds for typical patient
- Files encrypted with patient-specific key
- Audit log includes: who, what, when, IP address
- HIPAA-compliant data handling

## Security Requirements
- Verify patient identity before export
- Rate limit: max 5 exports per day per patient
- Automatic deletion of export files after 24 hours
- No export of other patients' data (strict isolation)
```

**Live Demo Flow:**
1. Show TODO spec
2. Use **Opus** (large model) with planning prompt to break down work
3. Use **Sonnet** (medium model) to generate core export logic
4. Use **Haiku** (small model) to fix linting issues
5. Use **Opus** (large model) with verification prompt to check against spec
6. Show tests passing
7. Move TODO → DONE
8. Auto-generate `/docs/patient-data-export.md` from DONE spec

**Key Points to Demonstrate:**
- How context files guide the AI
- When to use which model (cost vs. quality)
- How verification catches missing requirements
- How docs are auto-generated (never stale)

---

### Demo 3: Effective Model Selection (15 min)

**Feature to Demonstrate:** Same Patient Data Export, Different Models

**What to Prepare:**

1. **Cost/Quality Comparison Matrix:**

| Task | Small Model (Haiku) | Medium Model (Sonnet) | Large Model (Opus) |
|------|---------------------|----------------------|-------------------|
| **Linting/Formatting** | ✅ Perfect, $0.01 | ⚠️ Overkill, $0.10 | ❌ Wasteful, $1.00 |
| **Simple CRUD** | ⚠️ Good enough, $0.05 | ✅ Excellent, $0.25 | ⚠️ Overkill, $2.50 |
| **Complex Business Logic** | ❌ Misses edge cases, $0.10 | ✅ Good, $0.50 | ✅ Excellent, $5.00 |
| **Architecture/Planning** | ❌ Inadequate, $0.15 | ⚠️ Decent, $0.75 | ✅ Excellent, $7.50 |
| **Verification** | ❌ Misses issues, $0.10 | ⚠️ Catches most, $0.50 | ✅ Catches all, $5.00 |

2. **Live Comparison:**
   - Show same task (e.g., "generate export validation logic") with different models
   - Compare output quality
   - Show cost difference
   - Demonstrate when to escalate to larger model

3. **Spec Quality Impact:**
   - Show vague TODO spec → requires Opus to clarify
   - Show detailed TODO spec → Sonnet can implement directly
   - **Key insight:** Better specs = cheaper models = lower costs

**Live Demo Flow:**
1. Show vague TODO spec, try with Sonnet → struggles
2. Escalate to Opus → succeeds but expensive
3. Refine TODO spec with Opus's insights
4. Re-run with Sonnet using refined spec → succeeds at lower cost
5. Show cost savings: $5 (Opus) vs $0.50 (Sonnet) = 10x savings
6. Extrapolate: 100 features × $4.50 savings = $450 saved

---

### Hands-On Lab: Participant Exercise (25 min)

**Feature for Participants:** Prescription Refill Request Validation

**Provide to Participants:**

**`/specs/prescription-refill/TODO.md`:**
```markdown
# Prescription Refill Request Validation - TODO

## Requirements
Validate prescription refill requests before processing.

## Acceptance Criteria
1. Verify prescription exists and is active
2. Check refill is not too early (must be 80% through current supply)
3. Verify patient is the prescription holder
4. Check doctor has not marked "no refills"
5. Validate pharmacy is in network
6. Return clear error messages for each validation failure

## Non-Functional Requirements
- Response time < 50ms
- 100% test coverage
- Audit log for all validation attempts

## Edge Cases
- Expired prescriptions
- Controlled substances (special rules)
- Out-of-network pharmacies
- Multiple concurrent refill requests
```

**Participant Tasks:**
1. Read the TODO spec
2. Use planning prompt to break down work
3. Select appropriate model for each task
4. Generate implementation
5. Write tests
6. Use verification prompt to check completeness
7. Move TODO → DONE
8. Auto-generate docs

**Success Criteria:**
- All acceptance criteria met
- Tests pass
- Follows McCraeTech standards
- Documentation generated
- Can explain model selection choices

---

## Preparation Checklist

### Before Workshop 1:
- [ ] Create all prompt pattern templates
- [ ] Set up `AGENTS.md` in project root with McCraeTech standards
- [ ] Create `/context/Agent.md` and `/context/standards.md`
- [ ] Prepare all TODO specs for demos
- [ ] Test all demos end-to-end
- [ ] Set up cost tracking for model usage
- [ ] Prepare participant exercise materials
- [ ] Create evaluation rubric for hands-on lab

### Demo Environment Setup:
- [ ] Clean repository with proper structure
- [ ] `/specs/` directory with TODO examples
- [ ] `/prompts/` directory with pattern templates
- [ ] `/context/` directory with Agent.md and standards.md
- [ ] `AGENTS.md` file in project root configured
- [ ] Test framework set up and working
- [ ] Cost tracking dashboard ready

### Metrics to Track:
- [ ] Time from TODO to DONE (per feature)
- [ ] Model usage costs (per task)
- [ ] Verification failures (quality indicator)
- [ ] Test coverage percentage
- [ ] Documentation completeness score
- [ ] Participant satisfaction scores

