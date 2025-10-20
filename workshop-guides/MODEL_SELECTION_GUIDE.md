# Model Selection Guide

## Overview
Choosing the right AI model is crucial for optimal performance and efficiency. This guide helps you select the appropriate model based on your task requirements.

**Key Principle:** Prompt engineering is vital - the effectiveness depends on context.

---

## Model Categories & Use Cases

### 🚀 Small Models - Quick Operations
**Best for:** Fast, focused tasks that require speed over complexity

**Models:**
- `gemini-2.5-flash`
- `grok-code-fast-1`

**Use Cases:**
- Quick fixes and edits
- Code linting
- Simple refactoring
- Syntax corrections
- Formatting adjustments
- Minor bug fixes
- Fast iterations

**When to Choose:**
- You need rapid responses
- The task is well-defined and narrow in scope
- You're making incremental changes
- Speed is more important than deep reasoning

---

### 🎯 Medium Models - Code Generation
**Best for:** Balanced performance for standard development tasks

**Models:**
- `gemini-2.5-pro`
- `grok-4`
- `gpt-5-high`

**Use Cases:**
- Code generation
- Feature implementation
- API integration
- Database queries
- Component creation
- Standard refactoring
- Documentation generation

**When to Choose:**
- You need to generate new code
- The task requires understanding context
- You're implementing features with moderate complexity
- You need a balance between speed and quality

---

### 🧠 Complex Models - Planning & Verification
**Best for:** Deep reasoning, architecture, and quality assurance

**Models:**
- `claude-4.5-sonnet` (with extended thinking)
- `claude-4.5-sonnet` (standard)
- `claude-4.5-haiku` (with extended thinking)
- `claude-4.5-haiku` (standard)

**Use Cases:**
- Planning and architecture design
- Verification and validation
- Code reviews
- Security analysis
- Complex refactoring
- System design
- Debugging complex issues
- Technical debt assessment

**When to Choose:**
- You need strategic planning
- The task requires deep analysis
- You're validating code quality
- You need to understand system-wide implications
- Security and correctness are critical

---

## Decision Tree

```
START
  │
  ├─ Need quick fix/edit/linting?
  │  └─ YES → Use Small Model (gemini-2.5-flash, grok-code-fast-1)
  │
  ├─ Need to generate new code/features?
  │  └─ YES → Use Medium Model (gemini-2.5-pro, grok-4, gpt-5-high)
  │
  └─ Need planning/verification/validation?
     └─ YES → Use Complex Model (claude-4.5-sonnet, claude-4.5-haiku)
```

---

## Model-Specific Notes

### Claude Models
- **claude-4.5-sonnet**: Highest reasoning capability, best for complex planning
- **claude-4.5-haiku**: Faster than Sonnet, still excellent for verification
- **Extended thinking (🧠 icon)**: Enhanced reasoning for particularly complex tasks

### Gemini Models
- **gemini-2.5-flash**: Extremely fast, great for quick iterations
- **gemini-2.5-pro**: Strong general-purpose model for generation

### Grok Models
- **grok-code-fast-1**: Optimized for speed in code-related tasks
- **grok-4**: Balanced performance for standard development

### GPT Models
- **gpt-5-high**: Strong reasoning and generation capabilities

---

## Best Practices

1. **Start Small, Scale Up**: Begin with a smaller model and move to larger ones if needed
2. **Context Matters**: The same task might need different models depending on codebase complexity
3. **Iterate Efficiently**: Use small models for rapid iteration, then verify with complex models
4. **Cost Awareness**: Larger models consume more resources - use appropriately
5. **Prompt Quality**: Better prompts can make smaller models perform like larger ones

---

## Example Workflows

### Quick Fix Workflow
1. Use `gemini-2.5-flash` for the fix
2. Use `claude-4.5-haiku` for verification (optional)

### Feature Development Workflow
1. Use `claude-4.5-sonnet` for planning
2. Use `gemini-2.5-pro` or `grok-4` for implementation
3. Use `claude-4.5-sonnet` for final verification

### Refactoring Workflow
1. Use `claude-4.5-sonnet` for analysis and planning
2. Use `grok-4` or `gemini-2.5-pro` for implementation
3. Use `gemini-2.5-flash` for linting and formatting
4. Use `claude-4.5-haiku` for verification

---

## Summary Table

| Task Type | Model Size | Recommended Models | Speed | Quality |
|-----------|------------|-------------------|-------|---------|
| Quick fixes, linting | Small | gemini-2.5-flash, grok-code-fast-1 | ⚡⚡⚡ | ⭐⭐ |
| Code generation | Medium | gemini-2.5-pro, grok-4, gpt-5-high | ⚡⚡ | ⭐⭐⭐ |
| Planning, verification | Complex | claude-4.5-sonnet, claude-4.5-haiku | ⚡ | ⭐⭐⭐⭐ |

---

## Remember

> **The right model for the job makes all the difference. When in doubt, consider the complexity of reasoning required, not just the amount of code to generate.**

