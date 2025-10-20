# McRae Tech AI Training - Workshop Examples

## 📚 Overview

This directory contains hands-on workshop examples for learning AI-assisted development with **Cursor agents**, **effective context management**, and **strategic model selection**.

All examples are designed for healthcare software development with a focus on **PHI protection**, **consent management**, and **UTC datetime handling**.

---

## 🎯 Available Workshops

### 1. Auckland Java Consent Service
**Path:** `auckland-java-consent/`  
**Tech Stack:** Java 17 + Spring Boot 3.1.5  
**Complexity:** ⭐⭐ Low (Beginner-friendly)  

**What You'll Learn:**
- Simple bug fixing with AI agents
- Basic validation patterns
- SDK documentation generation
- Model selection fundamentals
- Context management basics

**Best For:**
- First-time AI coding workshop participants
- Java/Spring Boot developers
- Learning prompt engineering basics
- Quick introduction to AI-assisted development

[📖 Start Auckland Workshop →](auckland-java-consent/COMPLETION_GUIDE.md)

---

### 2. Thailand .NET Legacy Patients
**Path:** `thailand-dotnet-legacy/`  
**Tech Stack:** C# .NET 8 + ASP.NET Core  
**Complexity:** ⭐⭐⭐ Medium (Intermediate)  

**What You'll Learn:**
- Security-critical bug fixing (PHI protection)
- Advanced validation with custom attributes
- DateTime/UTC normalization
- Security-first context management
- Advanced model selection strategies

**Best For:**
- Healthcare software developers
- .NET/C# developers
- Security-conscious teams
- Developers handling sensitive data
- Advanced AI coding patterns

[📖 Start Thailand Workshop →](thailand-dotnet-legacy/COMPLETION_GUIDE.md)

---

### 3. Context Engineering Demo
**Path:** `context-engineering-demo/`  
**Tech Stack:** Java 17 + Spring Boot  
**Complexity:** ⭐⭐⭐⭐ Advanced  

**What You'll Learn:**
- Full feature development with AI
- Progressive context loading
- Multi-modal context engineering
- Spec-driven development (todo→done→docs)
- Custom Cursor commands

**Best For:**
- Workshop presenters
- Teams adopting AI-assisted development
- Learning advanced context engineering
- Building complete features with AI

[📖 View Context Engineering Demo →](context-engineering-demo/INDEX.md)

---

## 📖 Documentation Guide

### Getting Started
- **[QUICK_START_GUIDE.md](QUICK_START_GUIDE.md)** - 5-minute express setup for both workshops
- **[WORKSHOP_COMPARISON_GUIDE.md](WORKSHOP_COMPARISON_GUIDE.md)** - Detailed comparison to choose the right workshop

### Workshop-Specific Guides
- **[Auckland COMPLETION_GUIDE.md](auckland-java-consent/COMPLETION_GUIDE.md)** - Step-by-step Java workshop
- **[Thailand COMPLETION_GUIDE.md](thailand-dotnet-legacy/COMPLETION_GUIDE.md)** - Step-by-step .NET workshop
- **[Context Engineering INDEX.md](context-engineering-demo/INDEX.md)** - Advanced demo overview

### Supporting Resources
- **[HOW_TO_CREATE_RULES.md](../workshop-guides/HOW_TO_CREATE_RULES.md)** - Creating custom Cursor rules
- **[MODEL_SELECTION_GUIDE.md](../workshop-guides/MODEL_SELECTION_GUIDE.md)** - Choosing the right AI model
- **[HOW_TO_CREATE_COMMANDS.md](../workshop-guides/HOW_TO_CREATE_COMMANDS.md)** - Custom Cursor commands

---

## 📚 Context Management Patterns

### Progressive Context Loading
```
Phase 1 (Setup):        Rules only
Phase 2 (Implement):    + Source files (1-2 at a time)
Phase 3 (Debug):        + Terminal output
Phase 4 (Verify):       + Related files
Phase 5 (Document):     + All files
Phase 6 (Cleanup):      Remove source, keep docs
```

### Security-First Context (Healthcare)
```
Priority 1: security-phi-protection.mdc (FIRST!)
Priority 2: temporal-utc-standard.mdc (SECOND!)
Priority 3: consent-validation.mdc
Priority 4: Other domain rules
Priority 5: Source files
```

### Context Budget Management
```
Small Context (gemini-2.5-flash, grok-code-fast-1):
- 1-2 source files
- 1-2 rules
- Function/method focus

Medium Context (gemini-2.5-pro, grok-4):
- 3-5 source files
- All relevant rules
- Module-level focus

Large Context (claude-4.5-sonnet):
- Entire package
- All rules and docs
- Architecture-level focus
```

---

## ✅ Workshop Completion Checklist

### Before Starting
- [ ] Read workshop README.md
- [ ] Review SETUP.md for prerequisites
- [ ] Install build tools (Maven or .NET SDK)
- [ ] Open Cursor IDE
- [ ] Review `.cursor/rules/` directory

### During Workshop
- [ ] Add Cursor rules to Docs panel
- [ ] Create notepad for tracking
- [ ] Follow model selection recommendations
- [ ] Verify each change
- [ ] Track decisions in notepad

### After Completion
- [ ] All tasks completed
- [ ] SDK_README.md generated
- [ ] Run report created in `/ops/run-report.md`
- [ ] Code follows domain rules
- [ ] Security review passed (if applicable)

---

## 🎓 Learning Paths

### Beginner Path (4-6 hours)
```
1. Read QUICK_START_GUIDE.md (15 min)
   ↓
2. Auckland Java Consent (60 min)
   ↓
3. Thailand .NET Legacy (90 min)
   ↓
4. Review model selection patterns (30 min)
   ↓
5. Create your first custom rule (60 min)
```

### Advanced Path (6-8 hours)
```
1. Thailand .NET Legacy (90 min)
   ↓
2. Context Engineering Demo (3 hours)
   ↓
3. Create domain-specific rules (2 hours)
   ↓
4. Apply to real project (ongoing)
```

### Team Training Path (2 days)
```
Day 1:
- Complete both workshops yourself (3 hours)
- Review presenter guides (1 hour)
- Customize for your tech stack (2 hours)

Day 2:
- Run workshop with team (4 hours)
- Q&A and practice (2 hours)
- Create team rule library (2 hours)
```

---

## 🔑 Key Concepts

### 1. Context is Code
Your rules, docs, and specs are as important as your source code. Treat them with the same care.

### 2. Progressive Loading
Add context as needed. Don't overload the AI with unnecessary information.

### 3. Security First
For healthcare projects, ALWAYS add security rules before any code changes.

### 4. Model Selection Matters
Use fast models for simple tasks, reasoning models for complex/critical ones.

### 5. Verification Pass
Always verify critical changes with a second model pass (preferably Claude Sonnet 3.5).

### 6. Track Decisions
Use Cursor notepads to maintain context across sessions and track important decisions.

---

## 🚨 Common Issues

### Build Failures

**Java:**
```bash
# Maven dependency issues
mvn clean install -U

# Java version issues
java -version  # Should be 17+
```

**C#:**
```bash
# NuGet package issues
dotnet restore --force
dotnet clean
dotnet build

# .NET version issues
dotnet --version  # Should be 8.0+
```

### Cursor Rules Not Applying
1. Check `.cursor/rules/*.mdc` files exist
2. Verify glob patterns match your files
3. Add rules to Docs panel manually
4. Restart Cursor IDE

### AI Not Following Patterns
**Solution:** Add the specific rule to context explicitly
```
Example: "@.cursor/rules/security-phi-protection.mdc"
```

---

## 📊 Workshop Comparison

| Aspect | Auckland Java | Thailand .NET |
|--------|--------------|---------------|
| **Complexity** | ⭐⭐ Low | ⭐⭐⭐ Medium |
| **Time** | 45-60 min | 60-90 min |
| **Focus** | Bug fixing | Security + validation |
| **Security Level** | Medium | High (PHI critical) |
| **Best For** | Beginners | Healthcare devs |

[📖 Full Comparison →](WORKSHOP_COMPARISON_GUIDE.md)

---

## 🎯 Success Criteria

### Auckland Java Consent
✅ Expired consents filtered correctly  
✅ Scope validation prevents invalid requests  
✅ SDK documentation is clear and complete  
✅ Model selection decisions documented  

### Thailand .NET Legacy
✅ No PHI in application logs (CRITICAL)  
✅ All inputs validated  
✅ All DateTime values are UTC  
✅ API documentation includes security notes  
✅ Security review passed  

---

## 📚 Additional Resources

### Cursor Rules Library
- `.cursor/rules/security-phi-protection.mdc` - PHI protection patterns
- `.cursor/rules/consent-validation.mdc` - Consent checking patterns
- `.cursor/rules/temporal-utc-standard.mdc` - UTC datetime handling
- `.cursor/rules/api-design-patterns.mdc` - RESTful API patterns
- `.cursor/rules/testing-patterns.mdc` - Healthcare testing patterns

### Workshop Guides
- `workshop-guides/HOW_TO_CREATE_RULES.md` - Rule creation guide
- `workshop-guides/MODEL_SELECTION_GUIDE.md` - Model selection strategies
- `workshop-guides/HOW_TO_CREATE_COMMANDS.md` - Custom command creation

---

## 💡 Pro Tips

1. **Read First, Code Second** - Don't skip the completion guides
2. **Verify Everything** - Always do a verification pass for critical changes
3. **Use Notepads** - They persist across chat sessions
4. **Learn the Patterns** - These workshops teach reusable patterns
5. **Security First** - For healthcare, add security rules before coding

---

## 🤝 Contributing

Found an issue or have a suggestion? These workshops are designed to be improved over time.

**How to contribute:**
1. Complete the workshop
2. Note any issues or improvements
3. Share feedback with your team lead
4. Suggest new workshop topics

---

## 📞 Support

### Getting Help
1. Check the COMPLETION_GUIDE.md for your workshop
2. Review the troubleshooting section
3. Check common issues in this README
4. Ask your team lead or workshop facilitator

### Reporting Issues
- Build failures: Check SETUP.md first
- Cursor issues: Restart IDE, check rules
- AI not following patterns: Add rules to context explicitly

---

## 🎉 Next Steps

After completing these workshops:

1. **Apply to Real Projects** - Use these patterns in your daily work
2. **Create Custom Rules** - Build rules for your specific domain
3. **Train Your Team** - Share what you've learned
4. **Build Rule Library** - Create a shared team resource
5. **Measure Impact** - Track code quality and velocity improvements

---

**Ready to start? Choose your workshop and dive in! 🚀**

- [🟢 Auckland Java Consent (Beginner)](auckland-java-consent/COMPLETION_GUIDE.md)
- [🟡 Thailand .NET Legacy (Intermediate)](thailand-dotnet-legacy/COMPLETION_GUIDE.md)
- [🔴 Context Engineering Demo (Advanced)](context-engineering-demo/INDEX.md)

