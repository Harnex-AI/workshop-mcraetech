# How to Create Custom Cursor Commands

## What Are Cursor Commands?

Cursor commands are reusable prompts that you can invoke by typing `/` in the chat. They make it easy to execute common workflows without typing long prompts every time.

**Example**: Instead of typing "Create a specification for adding consent validation to patient endpoints", you can just type `/plan for adding consent validation`.

## When to Create a Command

Create a command when you:
- Have a workflow you repeat frequently
- Want to standardize how your team approaches a task
- Need to enforce specific patterns or constraints
- Want to make complex prompts easier to use

**Example Use Cases:**
- Code review checklists
- Security audits
- Feature setup workflows
- Bug fix procedures
- Documentation generation
- Test creation patterns

---

## Step 1: Identify Your Workflow

Ask yourself:
- What task do I (or my team) do repeatedly?
- What steps are involved in this task?
- What constraints or rules should be followed?
- What output should be produced?

**Example Workflows:**
- "Review code for security issues"
- "Set up a new feature branch with proper structure"
- "Create a pull request with all required information"
- "Run all tests and fix failures systematically"
- "Onboard a new developer to the project"

---

## Step 2: Create the Command File

### File Location

Commands go in `.cursor/commands/` directory:

```
.cursor/
└── commands/
    ├── your-command-name.md
    ├── another-command.md
    └── README.md
```

### File Naming

- Use **lowercase with hyphens**: `code-review.md`, `setup-feature.md`
- The filename becomes the command: `/code-review`, `/setup-feature`
- Keep names short and memorable

---

## Step 3: Write the Command Content

Commands are written in **plain Markdown**. The content describes what the AI should do when the command is invoked.

### Basic Template

```markdown
# [Command Name]: [Brief Description]

[Detailed description of what this command does and when to use it]

## Constraints
- Constraint 1 (e.g., follow specific rules or standards)
- Constraint 2 (e.g., respect domain requirements)
- Constraint 3 (e.g., output format requirements)

## Steps

### 1) [First Step Name]
[Description of what to do in this step]
- Sub-step 1
- Sub-step 2

### 2) [Second Step Name]
[Description of what to do in this step]
- Sub-step 1
- Sub-step 2

### 3) [Third Step Name]
[Description of what to do in this step]

## Output Format

[Describe what the output should look like]

```markdown
[Example output format]
```

## Checklist

Before completing, verify:
- [ ] Checklist item 1
- [ ] Checklist item 2
- [ ] Checklist item 3

## Next Steps

After completing this command:
1. Next action 1
2. Next action 2
```

---

## Step 4: Add Domain-Specific Context

Reference your domain rules and standards:

```markdown
## Constraints
- Follow `/context/standards.md` + `.cursor/rules/90-mccraetech-ai-standard.mdc`
- Respect domain rules in `/context/product_domain.md` (PHI, consent scope, UTC)
- Check for PHI exposure (security-phi-protection.mdc)
- Validate consent checks (consent-validation.mdc)
- Use UTC timestamps (temporal-utc-standard.mdc)
```

This ensures the AI follows your project's specific requirements.

---

## Step 5: Test Your Command

1. **Save the file** in `.cursor/commands/`
2. **Open Cursor chat** (Cmd+L or Ctrl+L)
3. **Type `/`** to see your command in the list
4. **Run the command** with test input
5. **Verify the output** matches your expectations
6. **Iterate** if needed

---

## Example 1: Simple Code Review Command

**File**: `.cursor/commands/review.md`

```markdown
# Code Review: Quick Quality Check

Perform a quick code review focusing on common issues.

## Constraints
- Follow project coding standards
- Check for security vulnerabilities
- Verify error handling
- Ensure tests are updated

## Steps

### 1) Review Code Quality
- Check for code duplication
- Verify naming conventions
- Look for magic numbers
- Check method/class sizes

### 2) Security Check
- No hardcoded secrets
- Input validation present
- No SQL injection risks
- Authorization checks in place

### 3) Testing
- Tests updated for changes
- Edge cases covered
- No skipped tests without reason

## Output Format

```markdown
# Code Review Results

## ✅ Passed
- Item 1
- Item 2

## ⚠️ Issues Found
- Issue 1: Description and fix
- Issue 2: Description and fix

## 💡 Suggestions
- Suggestion 1
- Suggestion 2
```

## Checklist
- [ ] Code quality verified
- [ ] Security checked
- [ ] Tests reviewed
- [ ] Feedback provided
```

**Usage**: `/review` or `/review the PatientController changes`

---

## Example 2: Feature Setup Command

**File**: `.cursor/commands/setup-feature.md`

```markdown
# Setup New Feature: Complete Workflow

Systematically set up a new feature from planning through implementation structure.

## Constraints
- Follow `/context/standards.md`
- Create feature branch from main
- Set up proper directory structure
- Include testing strategy

## Steps

### 1) Define Requirements
- Clarify feature scope and goals
- Identify user stories
- List acceptance criteria
- Plan technical approach

### 2) Create Feature Branch
- Branch from main/develop
- Use naming convention: `feature/[ticket-id]-[description]`
- Set up local development environment
- Configure any new dependencies

### 3) Plan Architecture
- Design data models and APIs
- Plan UI components and flow
- Consider testing strategy
- Identify integration points

### 4) Create Directory Structure
- Create necessary folders
- Add placeholder files
- Set up test structure
- Add README if needed

## Output Format

```markdown
# Feature Setup: [Feature Name]

## Requirements
- Requirement 1
- Requirement 2

## Architecture
- Component 1: Purpose
- Component 2: Purpose

## Files Created
- `path/to/file1.cs`
- `path/to/file2.cs`

## Next Steps
1. Implement component 1
2. Write tests
3. Integration testing
```

## Checklist
- [ ] Requirements documented
- [ ] Branch created
- [ ] Directory structure ready
- [ ] Architecture planned
- [ ] Ready to implement
```

**Usage**: `/setup-feature for patient consent tracking`

---

## Example 3: Security Audit Command

**File**: `.cursor/commands/security-audit.md`

```markdown
# Security Audit: Comprehensive Review

Perform a thorough security review of the codebase.

## Constraints
- Follow OWASP Top 10
- Check for PHI exposure (healthcare projects)
- Verify authentication/authorization
- Review dependency vulnerabilities

## Steps

### 1) Dependency Audit
- Check for known vulnerabilities
- Update outdated packages
- Review third-party dependencies
- Check license compatibility

### 2) Code Security Review
- SQL injection prevention
- XSS prevention
- CSRF protection
- Authentication/authorization checks
- Input validation
- Output encoding

### 3) Data Protection
- No PHI in logs (healthcare)
- No secrets in code
- Proper encryption for sensitive data
- Secure configuration management

### 4) Infrastructure Security
- Environment variables properly used
- Access controls configured
- Network security reviewed
- Secrets management in place

## Output Format

```markdown
# Security Audit Report

**Date**: [date]
**Scope**: [what was reviewed]

## 🔴 Critical Issues
1. Issue: Description
   - Impact: High/Medium/Low
   - Fix: Recommended action

## ⚠️ Warnings
1. Warning: Description
   - Recommendation: Suggested action

## ✅ Passed Checks
- Check 1
- Check 2

## 📋 Recommendations
1. Recommendation 1
2. Recommendation 2
```

## Checklist
- [ ] Dependencies audited
- [ ] Code reviewed for vulnerabilities
- [ ] Data protection verified
- [ ] Infrastructure security checked
- [ ] Report generated
```

**Usage**: `/security-audit` or `/security-audit for the API module`

---

## Best Practices

### DO ✅
- **Keep it focused**: One command = one clear purpose
- **Be specific**: Provide clear steps and constraints
- **Include examples**: Show expected output format
- **Reference standards**: Link to your domain rules
- **Add checklists**: Help ensure completeness
- **Test thoroughly**: Verify the command works as expected

### DON'T ❌
- **Make it too broad**: Avoid commands that try to do everything
- **Be vague**: Don't leave steps open to interpretation
- **Skip constraints**: Always include relevant rules and standards
- **Forget output format**: Always specify what should be produced
- **Ignore domain context**: Reference your project's specific requirements

---

## Advanced: Parameters and Context

Commands can accept additional context from the user:

```markdown
# Example Command

When the user types: `/my-command with additional context here`

Everything after the command name is included in the prompt.
You can reference this in your command instructions.
```

**Example**:
```
/review the authentication changes for security issues
```

The command receives: "the authentication changes for security issues"

---

## Sharing Commands with Your Team

### Project Commands (Recommended)
Commands in `.cursor/commands/` are automatically available to anyone who has the project open.

**Pros**:
- Version controlled with your code
- Easy to update and maintain
- Team members get updates automatically

### Team Commands (Enterprise/Team Plans)
Create commands in the [Cursor Dashboard](https://cursor.com/dashboard?tab=team-content&section=commands) for server-enforced commands.

**Pros**:
- Centrally managed
- Available across all projects
- Admin-controlled

---

## Troubleshooting

**Command doesn't appear in the list**
- Check the file is in `.cursor/commands/`
- Verify the file has `.md` extension
- Restart Cursor if needed

**Command doesn't work as expected**
- Review the command content for clarity
- Add more specific constraints
- Include examples of expected output
- Test with different inputs

**Command is too complex**
- Break it into multiple smaller commands
- Simplify the steps
- Remove unnecessary details

---

## Template: Blank Command

Use this template to create your own command:

```markdown
# [Command Name]: [Brief Description]

[Detailed description of what this command does]

## Constraints
- Constraint 1
- Constraint 2
- Constraint 3

## Steps

### 1) [Step Name]
[Description]
- Sub-step 1
- Sub-step 2

### 2) [Step Name]
[Description]

### 3) [Step Name]
[Description]

## Output Format

```markdown
[Example output]
```

## Checklist
- [ ] Item 1
- [ ] Item 2
- [ ] Item 3

## Next Steps
1. Action 1
2. Action 2
```

---

## Resources

- **Cursor Documentation**: https://cursor.com/docs/agent/chat/commands
- **Example Commands**: `.cursor/commands/` directory
- **Domain Rules**: `.cursor/rules/` directory
- **Context Files**: `/context/` directory

---

## Quick Reference

| Step | Action |
|------|--------|
| 1 | Identify workflow to automate |
| 2 | Create `.cursor/commands/command-name.md` |
| 3 | Write command content in Markdown |
| 4 | Add constraints and domain context |
| 5 | Test with `/command-name` in chat |
| 6 | Iterate and improve |

---

**Ready to create your first command?** Start with a simple workflow you do often, and build from there!

