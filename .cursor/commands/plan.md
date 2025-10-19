# Planning Mode: Create Specification

You are a technical architect creating a **simple, effective specification** for a feature or change. The spec should be practical, not over-engineered, and focused on what needs to be built.

## Constraints
- Respect domain rules in `.cursor/rules`
- Keep the spec **simple and actionable** - avoid over-engineering
- Output a single markdown file in `/spec/todo/` directory
- Use the filename format: `YYYY-MM-DD-feature-name.md`

## Steps

### 1) Requirements Analysis
- **What**: Clear description of what needs to be built (2-3 sentences)
- **Why**: Business value and user impact (2-3 bullets)
- **Scope**: What's included and explicitly what's NOT included
- **Success Criteria**: How we know it's done (3-5 measurable criteria)

### 2) Design Decisions
- **Approach**: High-level approach (1 paragraph)
- **Alternatives Considered**: What else was considered and why rejected (2-3 bullets)
- **Trade-offs**: Key trade-offs being made
- **Dependencies**: External dependencies or prerequisites

### 3) Architecture
- **Components**: List of components/files to create or modify
- **Data Flow**: Simple description or diagram of how data flows
- **Integration Points**: Where this touches existing code
- **Security Considerations**: PHI protection, consent checks, authorization
- **Error Handling**: How errors will be handled

### 4) Implementation Tasks
Break down into **small, sequential tasks** (each task should take 15-30 minutes):

```markdown
- [ ] Task 1: Brief description (files: file1.cs, file2.cs)
- [ ] Task 2: Brief description (files: file3.cs)
- [ ] Task 3: Brief description (files: file4.cs)
...
```

Each task should:
- Be independently reviewable
- Have clear input/output
- List specific files to modify
- Be ordered by dependency (prerequisites first)

### 5) Testing Strategy
- **Unit Tests**: What needs unit test coverage
- **Integration Tests**: What integration scenarios to test
- **Test Data**: Synthetic test data requirements (follow testing-patterns.mdc)
- **Edge Cases**: Key edge cases to validate

### 6) Risks & Mitigations
- **Technical Risks**: What could go wrong technically (2-4 items)
- **Mitigation**: How to reduce each risk
- **Rollback Plan**: How to undo if needed

## Output Format

Create `/spec/todo/YYYY-MM-DD-feature-name.md` with this structure:

```markdown
# [Feature Name]

**Created**: YYYY-MM-DD
**Status**: Planning
**Owner**: [Developer Name]

## 1. Requirements

### What
[2-3 sentence description]

### Why
- Business value point 1
- Business value point 2

### Scope
**In Scope:**
- Item 1
- Item 2

**Out of Scope:**
- Item 1
- Item 2

### Success Criteria
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3

## 2. Design

### Approach
[1 paragraph describing the approach]

### Alternatives Considered
- **Alternative 1**: Why rejected
- **Alternative 2**: Why rejected

### Trade-offs
- Trade-off 1
- Trade-off 2

### Dependencies
- Dependency 1
- Dependency 2

## 3. Architecture

### Components
- **Component 1** (`path/to/file.cs`): Purpose
- **Component 2** (`path/to/file.cs`): Purpose

### Data Flow
[Simple description or ASCII diagram]

### Integration Points
- Integration point 1: How it connects
- Integration point 2: How it connects

### Security Considerations
- PHI protection: [approach]
- Consent validation: [approach]
- Authorization: [approach]

### Error Handling
- Error scenario 1: [handling approach]
- Error scenario 2: [handling approach]

## 4. Implementation Tasks

- [ ] Task 1: Description (files: file1.cs, file2.cs)
- [ ] Task 2: Description (files: file3.cs)
- [ ] Task 3: Description (files: file4.cs)
- [ ] Task 4: Description (files: file5.cs)
...

## 5. Testing Strategy

### Unit Tests
- Test 1: What to test
- Test 2: What to test

### Integration Tests
- Scenario 1: What to test
- Scenario 2: What to test

### Test Data
- Synthetic data requirements (follow testing-patterns.mdc)

### Edge Cases
- Edge case 1
- Edge case 2

## 6. Risks & Mitigations

| Risk | Impact | Mitigation |
|------|--------|------------|
| Risk 1 | High/Med/Low | Mitigation approach |
| Risk 2 | High/Med/Low | Mitigation approach |

### Rollback Plan
[How to undo this change if needed]

## 7. Notes
[Any additional context, links, or references]
```

## Self-Critique Checklist

Before finalizing the spec, verify:
- [ ] **Simplicity**: Is this the simplest approach that could work?
- [ ] **Completeness**: Are all requirements captured?
- [ ] **Actionable**: Can a developer start implementing immediately?
- [ ] **Testable**: Are success criteria measurable?
- [ ] **Safe**: Are PHI, consent, and UTC rules addressed?
- [ ] **Scoped**: Is scope clearly bounded (in/out)?
- [ ] **Tasks**: Are tasks small enough (15-30 min each)?
- [ ] **Dependencies**: Are task dependencies clear and ordered?
- [ ] **Rollback**: Is there a clear rollback plan?

## Next Steps

After creating the spec:
1. Save to `/spec/todo/YYYY-MM-DD-feature-name.md`
2. Run `/verify-spec` command to validate against codebase
3. Review with lead developer
4. Once approved, use `/execute` command to implement tasks