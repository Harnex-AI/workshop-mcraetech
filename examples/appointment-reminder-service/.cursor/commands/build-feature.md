# Build Feature: Spec-Driven Development

This command guides you through building a feature using spec-driven development with proper context management.

## Purpose
Build features systematically using the todo→done→docs workflow while managing context effectively.

## Constraints
- Follow all rules in `.cursor/rules/`
- Reference context docs in `/context/` for domain requirements
- Follow the spec in `/spec/todo/`
- Mark tasks as complete in the spec as you progress
- Move spec to `/spec/done/` when complete
- Generate documentation in `/docs/` at the end

## Steps

### 1) Load Context
- Add relevant context docs from `/context/` to Cursor Docs panel
- Review the spec in `/spec/todo/`
- Identify which rules apply (PHI protection, consent validation, UTC temporal)
- Create a notepad for tracking decisions and blockers

### 2) Plan the Work
- Review all tasks in the spec
- Identify dependencies between tasks
- Determine the order of implementation
- Note any questions or clarifications needed

### 3) Implement Iteratively
For each task in the spec:
- Read the task requirements
- Check relevant context docs
- Implement the code following all rules
- Write tests (use synthetic data)
- Run tests and fix failures
- Mark task as complete in spec

### 4) Manage Context Window
As you progress:
- Keep only relevant context docs in Cursor Docs panel
- Add terminal output when debugging build/test errors
- Add linter errors to context when fixing issues
- Remove old terminal output when no longer needed
- Update notepad with decisions and progress

### 5) Verify Compliance
Before marking complete:
- [ ] All tests passing
- [ ] No PHI in logs (check with grep)
- [ ] All timestamps in UTC
- [ ] Consent validated before operations
- [ ] Audit logs created
- [ ] Error messages sanitized (no PHI)

### 6) Complete the Feature
- Move spec from `/spec/todo/` to `/spec/done/`
- Generate API documentation
- Create SDK_README.md with usage examples
- Update main README.md if needed

## Output Format

After each task completion, provide:
```markdown
## Task: [Task Name]

### What was implemented
- [Implementation detail 1]
- [Implementation detail 2]

### Tests added
- [Test 1]
- [Test 2]

### Compliance checks
- [x] No PHI in logs
- [x] UTC timestamps
- [x] Consent validated
- [x] Audit logged

### Next task
[Name of next task from spec]
```

## Context Management Tips

### Early Stage (Tasks 1-3)
**Context:**
- Rules (always active)
- 1-2 context docs
- Current spec
- Current file

**Why:** Keep context focused on immediate task

### Mid Stage (Tasks 4-6)
**Context:**
- Rules (always active)
- 2-3 context docs
- Current spec
- Notepad (decisions)
- Terminal output (if debugging)

**Why:** More complexity requires more context

### Late Stage (Tasks 7+)
**Context:**
- Rules (always active)
- Relevant context docs only
- Notepad (decisions)
- Linter errors (if fixing)

**Why:** Spec tasks mostly done, focus on quality

## Example Usage

**User:** `/build-feature`

**AI Response:**
```
I'll help you build the Emergency Contact Notification feature using spec-driven development.

## Context Loaded
- ✅ Rules: PHI protection, consent validation, UTC temporal
- ✅ Docs: emergency-contact-requirements.md, phi-sharing-matrix.md, audit-log-spec.md
- ✅ Spec: /spec/todo/emergency-contact-notification.md
- ✅ Notepad: Created "Emergency Contact Feature Context"

## Plan
I see 9 phases with 30+ tasks. I'll start with Phase 1: Core Entities & Repository.

## First Task: Create EmergencyContact Entity
Let me implement this following:
- UTC temporal rules (Instant for timestamps)
- Validation annotations
- Synthetic test data in tests

[Proceeds to implement...]
```

## Checklist

Before marking feature complete:
- [ ] All spec tasks completed
- [ ] All tests passing (>80% coverage)
- [ ] No PHI violations (grep logs for patient names)
- [ ] All timestamps in UTC
- [ ] Consent validated before all operations
- [ ] Audit logs created for all actions
- [ ] Error messages sanitized
- [ ] API documentation generated
- [ ] SDK_README.md created
- [ ] Spec moved to /spec/done/

## Next Steps

After feature is complete:
1. Demo the feature to stakeholders
2. Deploy to test environment
3. Run integration tests
4. Create deployment checklist
5. Plan next feature

