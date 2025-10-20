# Context Engineering Quick Reference

## 🎯 Core Principle
**Context is Code** - Your rules, docs, and specs are as important as your source code.

---

## 📚 Context Layers

### 1. Rules (`.cursor/rules/*.mdc`)
**Purpose:** Domain-specific enforcement (automatic)
**When to use:** Always active for matching file types
**Example:**
```yaml
---
description: Prevent PHI exposure in logs
globs: ["**/*.java", "**/*.cs"]
---
# PHI Protection Rules
## NEVER DO
- Log patient names, DOB, addresses
```

### 2. Docs (Cursor Docs Panel)
**Purpose:** Business requirements and specifications
**When to use:** Add when implementing related features
**Example:** `emergency-contact-requirements.md`, `phi-sharing-matrix.md`

### 3. Notepads
**Purpose:** Persistent agent memory across sessions
**When to use:** Track decisions, blockers, next steps
**Example:**
```markdown
# Feature Context
## Decisions
- Using SMS for urgent notifications
## Blockers
- Waiting for SMS gateway credentials
```

### 4. Terminal Output
**Purpose:** Build errors, test results
**When to use:** When debugging or fixing errors
**Example:** Add terminal to context, ask "Fix these compilation errors"

### 5. Linter Errors
**Purpose:** Code quality issues
**When to use:** When fixing code quality problems
**Example:** Add linter output, ask "Fix these linting errors"

---

## 🔄 Spec-Driven Development Workflow

```
1. Create spec in /spec/todo/
   ↓
2. Add context (rules, docs, notepads)
   ↓
3. Implement tasks iteratively
   ↓
4. Check off tasks as you complete them
   ↓
5. Move spec to /spec/done/
   ↓
6. Generate docs in /docs/
```

---

## 📊 Progressive Context Loading

### Early Stage (Tasks 1-3)
```
Context Window:
├── Rules (always)
├── 1-2 context docs
├── Current spec
└── Current file
```
**Why:** Keep focused on immediate task

### Mid Stage (Tasks 4-6)
```
Context Window:
├── Rules (always)
├── 2-3 context docs
├── Current spec
├── Notepad (decisions)
└── Terminal (if debugging)
```
**Why:** More complexity requires more context

### Late Stage (Tasks 7+)
```
Context Window:
├── Rules (always)
├── Relevant docs only
├── Notepad (decisions)
└── Linter errors (if fixing)
```
**Why:** Spec mostly done, focus on quality

---

## 🛠️ Common Workflows

### Starting a New Feature
1. Create spec in `/spec/todo/feature-name.md`
2. Add relevant context docs to Cursor Docs panel
3. Create notepad for tracking
4. Ask: "Implement [first task] from the spec"

### Debugging Build Errors
1. Run build: `mvn clean test` or `npm test`
2. Add terminal output to context
3. Ask: "Fix the errors shown in the terminal"
4. Clear terminal output when done

### Fixing Linter Errors
1. Run linter: `mvn checkstyle:check` or `npm run lint`
2. Add linter output to context
3. Ask: "Fix these linting errors"
4. Clear linter output when done

### Completing a Feature
1. Check off all tasks in spec
2. Run all tests: `mvn test`
3. Move spec: `/spec/todo/` → `/spec/done/`
4. Generate docs: Ask "Create API documentation"

---

## 🎯 Best Practices

### DO ✅
- Start with minimal context, add as needed
- Use synthetic test data (TEST_PAT_001, not "John Smith")
- Store timestamps in UTC, convert for display only
- Validate consent before all PHI operations
- Log reference IDs, not patient names
- Check off spec tasks as you complete them
- Move completed specs to `/spec/done/`

### DON'T ❌
- Dump all context at once (overwhelming)
- Use realistic PHI in tests (compliance violation)
- Use LocalDateTime (timezone bugs)
- Skip consent validation (security violation)
- Log full patient objects (PHI exposure)
- Leave completed specs in `/spec/todo/` (clutters context)

---

## 🔍 Troubleshooting

### AI Not Following Rules
**Problem:** AI suggests code that violates rules
**Solution:**
1. Check rule glob pattern matches file type
2. Point AI to specific rule: "Follow security-phi-protection.mdc"
3. Show before/after example from rule

### AI Not Using Context Docs
**Problem:** AI doesn't reference your docs
**Solution:**
1. Verify docs added to Cursor Docs panel
2. Ask explicitly: "According to emergency-contact-requirements.md, what..."
3. Reference doc in prompt: "Follow the PHI sharing matrix"

### Context Window Too Large
**Problem:** Too much context, AI gets confused
**Solution:**
1. Remove old terminal output
2. Keep only relevant docs in panel
3. Move completed specs to `/spec/done/`
4. Clear chat history if needed

### AI Forgets Decisions
**Problem:** AI doesn't remember previous decisions
**Solution:**
1. Add decisions to notepad
2. Reference notepad in prompts
3. Keep notepad updated as you progress

---

## 📋 Cheat Sheet

### Cursor Shortcuts
| Action | Shortcut |
|--------|----------|
| Open Chat | `Cmd+L` (Mac) / `Ctrl+L` (Win) |
| Add to Docs | Right sidebar → Docs → Add |
| Create Notepad | Chat → Notepad icon |
| Run Command | Type `/` in chat |

### Common Prompts
```
"Implement [task] from the spec following all rules"
"Create tests for [component] using synthetic data"
"Fix the errors shown in the terminal"
"What PHI can I share with [consent scope]?"
"Generate API documentation for [component]"
```

### File Locations
```
.cursor/rules/          # Domain enforcement rules
context/                # Business requirements
spec/todo/              # Active specs (in context)
spec/done/              # Completed specs (out of context)
docs/                   # Generated documentation
src/main/               # Implementation code
src/test/               # Test code
```

---

## 🎓 Key Concepts

### Context is Code
Your rules, docs, and specs guide the AI to write compliant code. Treat them as first-class artifacts.

### Progressive Loading
Add context as complexity grows. Start minimal, expand as needed.

### Multi-Modal Context
Use files, folders, docs, terminal, linter, and notepads together.

### Spec-Driven Development
todo → implement → done → docs. Keep context focused on current work.

### Same AI, Different Results
The AI model doesn't change. Your context does. **Context engineering is the skill.**

---

## 🚀 Next Steps

1. **Practice:** Build a feature using this workflow
2. **Create Rules:** Identify pain points in your domain
3. **Document:** Write context docs for your requirements
4. **Iterate:** Refine your context based on results
5. **Share:** Teach your team these patterns

---

## 📖 Additional Resources

- **Full Demo:** `DEMO_SCRIPT.md`
- **Presenter Guide:** `PRESENTER_GUIDE.md`
- **Example Rules:** `.cursor/rules/`
- **Example Docs:** `context/`
- **Example Spec:** `spec/todo/emergency-contact-notification.md`

---

## 💡 Remember

> "The best AI developers aren't the ones who write the most code.
> They're the ones who provide the best context."

**Context Engineering = Better Code + Faster Development + Fewer Bugs**

