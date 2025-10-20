# McRae Tech AI Training

**Learn to build healthcare software faster and better with AI-assisted development.**

Master Cursor IDE's context engineering capabilities through hands-on workshops that teach prompt engineering, context management, model selection, and security-critical coding patterns.

---

## 🎯 What You'll Learn

- **Prompt Engineering** - How to guide AI effectively with clear, specific requests
- **Context Engineering** - Managing AI context for optimal code generation quality
- **Model Selection** - Choosing the right AI model for different tasks
- **Healthcare Patterns** - PHI protection, consent management, UTC timestamps
- **Security First** - Building compliance into your AI-assisted workflow
- **Production Readiness** - Generating code that's audit-ready and tested

---

## 🚀 Quick Start

### Prerequisites
- Java 17+ (for Java workshops)
- .NET 8.0+ (for .NET workshop)
- Cursor IDE
- Basic familiarity with your tech stack

### Choose Your Path

```
START HERE
    ↓
    └─→ 🟢 Beginner (45 min)
    │   └─→ Auckland Java Consent Service
    │
    └─→ 🟡 Intermediate (60 min)
    │   └─→ Thailand .NET Legacy Patients
    │
    └─→ 🔴 Advanced (2-3 hours)
        └─→ Build Appointment Reminder Service
```

**See [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md) for detailed step-by-step instructions.**

---

## 📚 Workshop Overview

### 🟢 Workshop 1: Auckland Java Consent Service (Beginner)

**What:** Fix bugs and add validation to a consent management microservice.

**Tech Stack:** Java 17 + Spring Boot 3.1.5

**Learning Focus:**
- Basic bug fixing with AI assistance
- Input validation patterns
- Documentation generation
- Model selection for quick fixes

**Path:** `examples/auckland-java-consent/`

**Start Here:** [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md#-workshop-1-auckland-java-consent-beginner)

---

### 🟡 Workshop 2: Thailand .NET Legacy Patients (Intermediate)

**What:** Secure a legacy patient API by fixing PHI logging, adding validation, and normalizing timestamps to UTC.

**Tech Stack:** C# .NET 8 + ASP.NET Core

**Learning Focus:**
- Healthcare security concerns (PHI protection)
- Input validation and error handling
- UTC timestamp normalization
- Security review with AI
- Model selection for security-critical tasks

**Path:** `examples/thailand-dotnet-legacy/`

**Start Here:** [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md#-workshop-2-thailand-net-legacy-intermediate)

---

### 🔴 Workshop 3: Build Appointment Reminder Service (Advanced)

**What:** Build a complete appointment reminder feature using context engineering patterns in an existing healthcare system.

**Tech Stack:** Java 17 + Spring Boot 3.2 + JPA + H2

**Learning Focus:**
- Progressive context loading
- Multi-modal context engineering
- Full feature development (entity → repository → service → controller)
- Integration testing
- Documentation generation
- Production-ready code patterns

**Path:** `examples/appointment-reminder-service/`

**Features You'll Build:**
- Appointment entity with UTC timestamps
- JPA repository with query methods
- Reminder service with consent validation
- REST API controller
- Comprehensive tests
- API documentation

**Start Here:** [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md#-workshop-3-build-appointment-reminder-service-advanced)

---

## 📖 Documentation

### Getting Started
- **[QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md)** - Step-by-step guides for all workshops
- **[workshop-guides/MODEL_SELECTION_GUIDE.md](./workshop-guides/MODEL_SELECTION_GUIDE.md)** - How to choose the right model for your task

### Creating Your Own Resources
- **[workshop-guides/HOW_TO_CREATE_RULES.md](./workshop-guides/HOW_TO_CREATE_RULES.md)** - Build domain-specific Cursor rules
- **[workshop-guides/HOW_TO_CREATE_COMMANDS.md](./workshop-guides/HOW_TO_CREATE_COMMANDS.md)** - Create custom Cursor commands for workflows

### Workshop-Specific Documentation
Each workshop contains detailed guides:
- **Setup instructions** - Get the project building
- **Known issues** - Intentional bugs for learning
- **Troubleshooting** - Common problems and solutions

---

## 🏗️ Repository Structure

```
mcraetech-training/
├── README.md                          # This file
├── QUICK_START_GUIDES.md             # Step-by-step workshop guides
│
├── examples/                          # Workshop implementations
│   ├── auckland-java-consent/        # Beginner: Java + consent bugs
│   │   ├── README.md                 # Workshop overview
│   │   ├── SETUP.md                  # Setup instructions
│   │   ├── pom.xml                   # Maven configuration
│   │   └── src/                      # Source code with intentional bugs
│   │
│   ├── thailand-dotnet-legacy/       # Intermediate: .NET + security fixes
│   │   ├── README.md                 # Workshop overview
│   │   ├── SETUP.md                  # Setup instructions
│   │   ├── ThailandLegacy.csproj     # Project configuration
│   │   ├── Program.cs                # ASP.NET Core setup
│   │   ├── Controllers/              # API controllers with PHI logging bugs
│   │   ├── Models/                   # Data models
│   │   └── Services/                 # Business logic
│   │
│   └── appointment-reminder-service/ # Advanced: Full feature development
│       ├── README.md                 # Project overview
│       ├── HANDS_ON_APPOINTMENT_REMINDERS.md # Your exercise
│       ├── BASE_INFRASTRUCTURE.md    # What's already built
│       ├── DEMO_FEATURE_SPEC.md      # Demo script
│       ├── pom.xml                   # Maven configuration
│       ├── src/                      # Complete Spring Boot application
│       ├── context/                  # Context docs for AI
│       ├── spec/                     # Feature specifications
│       └── .cursor/                  # Cursor IDE configuration
│
├── workshop-guides/                   # Reusable guides
│   ├── HOW_TO_CREATE_RULES.md        # Create custom Cursor rules
│   ├── HOW_TO_CREATE_COMMANDS.md     # Create custom Cursor commands
│   └── MODEL_SELECTION_GUIDE.md      # Model selection strategies
│
└── spec/                              # Repository-level specs
    ├── todo/                         # Planned work
    └── done/                         # Completed work
```

---

## 🎓 Learning Objectives by Workshop

### Auckland (Beginner)
- ✅ Understand how to use Cursor context effectively
- ✅ Practice focused prompting for bug fixes
- ✅ Learn to generate API documentation
- ✅ Experience rapid iteration with AI
- ✅ Make your first model selection decision

### Thailand (Intermediate)
- ✅ Master security-first context management
- ✅ Learn healthcare compliance patterns (PHI, UTC)
- ✅ Add validation comprehensively
- ✅ Perform security review with AI
- ✅ Handle complex, multi-step refactoring
- ✅ Understand verification passes

### Appointment Reminder (Advanced)
- ✅ Build complete features with AI assistance
- ✅ Apply progressive context loading
- ✅ Follow established code patterns
- ✅ Use existing services (ConsentValidator, AuditLogger)
- ✅ Create comprehensive tests
- ✅ Generate production-ready documentation
- ✅ Experience the full AI-assisted development lifecycle

---

## 💻 Running the Workshops

### Auckland (Java)
```bash
cd examples/auckland-java-consent
mvn clean install
mvn spring-boot:run
```
Test: `curl http://localhost:8080/api/consents`

### Thailand (.NET)
```bash
cd examples/thailand-dotnet-legacy
dotnet restore
dotnet build
dotnet run
```
Test: `curl http://localhost:5000/api/patients`

### Appointment Reminder (Java)
```bash
cd examples/appointment-reminder-service
mvn clean install
mvn spring-boot:run
```
Test: `curl http://localhost:8080/api/patients`

---

## 🔑 Key Concepts

### Context is Code
Your rules, documentation, and specifications are as important as your source code. Treat them with the same care and version control.

### Progressive Context Loading
Don't overload the AI with unnecessary information. Add context as you need it:
1. Start with rules only
2. Add 1-2 source files at a time
3. Add examples and patterns
4. Reference existing code as templates

### Security First (Healthcare)
For healthcare projects, ALWAYS:
1. Add security rules before any coding
2. Use reference IDs in logs, never names or PHI
3. Use UTC timestamps consistently
4. Validate all inputs
5. Include audit logging

### Model Selection Matters
- **Quick fixes** (linting, formatting): Use small models (gemini-2.5-flash)
- **Code generation** (features, APIs): Use medium models (gemini-2.5-pro, grok-4)
- **Planning/review** (architecture, verification): Use complex models (claude-4.5-sonnet)

### Verification Pass
Critical code should be verified by a capable model:
1. Generate code with medium model
2. Verify security with claude-4.5-sonnet
3. Run tests to confirm functionality
4. Document your decisions

---

## 🎯 Recommended Learning Path

### For Individuals (4-6 hours)
```
1. Read this README (10 min)
   ↓
2. Complete Auckland workshop (60 min)
   ↓
3. Complete Thailand workshop (90 min)
   ↓
4. Review MODEL_SELECTION_GUIDE (20 min)
   ↓
5. Skim appointment reminder setup (20 min)
   ↓
6. Create your first custom rule (60 min)
```

### For Teams (2-day workshop)
**Day 1 - Delivery (3 hours)**
```
- Kickoff: AI-assisted development intro (15 min)
- Auckland demo & walkthrough (30 min)
- Thailand demo & security focus (30 min)
- Break (15 min)
- Attendees start apartments reminder service (90 min)
```

**Day 2 - Deep Dive (4 hours)**
```
- Review attendee progress (30 min)
- Context engineering patterns (45 min)
- Custom rules workshop (60 min)
- Q&A and refinement (90 min)
- Next steps & resources (15 min)
```

---

## 🛠️ Creating Your Own Resources

### Create Custom Cursor Rules
Build domain-specific rules for your technology stack or business domain.

**Guide:** [workshop-guides/HOW_TO_CREATE_RULES.md](./workshop-guides/HOW_TO_CREATE_RULES.md)

**Example: Build a rule that enforces authorization on all delete endpoints**

### Create Custom Commands
Create reusable prompts for common workflows (code review, feature setup, etc.).

**Guide:** [workshop-guides/HOW_TO_CREATE_COMMANDS.md](./workshop-guides/HOW_TO_CREATE_COMMANDS.md)

**Example: Build a command that runs security review on healthcare code**

---

## 🚨 Troubleshooting

### Java Projects Won't Build
```bash
# Check Java version (needs 17+)
java -version

# Download all dependencies
mvn clean install -U

# Clear maven cache if issues persist
rm -rf ~/.m2/repository
mvn clean install
```

### .NET Projects Won't Build
```bash
# Check .NET version (needs 8.0+)
dotnet --version

# Restore packages
dotnet restore --force

# Clean and rebuild
dotnet clean
dotnet build
```

### Cursor Rules Not Applying
1. Check `.cursor/rules/*.mdc` files exist
2. Verify glob patterns match your files (e.g., `**/*.java`)
3. Restart Cursor IDE
4. Add rules to Docs panel manually

### AI Not Following Patterns
1. Explicitly reference the pattern in your prompt
2. Add the specific rule to context
3. Use Claude Sonnet for complex decisions
4. Be specific: "following the PatientService pattern"

---

## 📊 Success Criteria

### Workshop Completion Checklist

**Auckland (Beginner)**
- [ ] Expired consents filtered correctly
- [ ] Scope validation prevents invalid requests
- [ ] SDK documentation generated and clear
- [ ] All tests passing
- [ ] No PHI in logs

**Thailand (Intermediate)**
- [ ] **NO PHI in application logs** (CRITICAL)
- [ ] All inputs validated with error messages
- [ ] All DateTime values using UTC
- [ ] API documentation includes security notes
- [ ] Security review completed
- [ ] All tests passing

**Appointment Reminder (Advanced)**
- [ ] Complete CRUD API for appointments
- [ ] Consent validation working (APPOINTMENT_REMINDER scope)
- [ ] No PHI in logs (reference IDs only)
- [ ] UTC timestamps throughout
- [ ] Audit logging on all operations
- [ ] Comprehensive tests covering all cases
- [ ] Integration tests passing
- [ ] API documentation complete

---

## 💡 Pro Tips

1. **Use Notepads** - Track progress across sessions with Cursor notepads
2. **Start Small** - Begin with Auckland, don't skip to advanced
3. **Read the Guides** - Each workshop README has valuable troubleshooting
4. **Verify Code** - Use verification passes for security-critical changes
5. **Save Decisions** - Document your model selection choices
6. **Share Knowledge** - These patterns apply to your real projects too

---

## 🎓 After the Workshop

### Apply to Real Projects
- Use healthcare patterns in your daily work
- Reference these examples when building similar features
- Follow the context engineering approach for your codebase

### Build Team Resources
1. Create custom rules for your tech stack
2. Build commands for your common workflows
3. Document your domain-specific patterns
4. Share with your team

### Measure Impact
- Track time saved vs. manual development
- Monitor code quality improvements
- Measure security review completeness
- Collect team feedback

---

## 📞 Support & Resources

### Getting Help
1. Check the workshop README for your chosen example
2. Review QUICK_START_GUIDES.md
3. Check workshop-guides for deeper topics
4. Verify all prerequisite software is installed

### Common Issues
- **Build failures** → Check SETUP.md in workshop directory
- **Cursor rules not applying** → See Troubleshooting section
- **AI suggesting wrong code** → Add more specific context
- **Security concerns** → Use verification pass with Claude Sonnet

---

## 🤝 Contributing & Feedback

These workshops are designed to evolve. If you find issues or have suggestions:

1. Complete the workshop
2. Note what worked well and what didn't
3. Share feedback with your team
4. Suggest improvements or new workshop topics

---

## 📋 Quick Links

| Resource | Purpose |
|----------|---------|
| [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md) | Step-by-step workshop instructions |
| [MODEL_SELECTION_GUIDE.md](./workshop-guides/MODEL_SELECTION_GUIDE.md) | Choose the right AI model |
| [HOW_TO_CREATE_RULES.md](./workshop-guides/HOW_TO_CREATE_RULES.md) | Build custom Cursor rules |
| [HOW_TO_CREATE_COMMANDS.md](./workshop-guides/HOW_TO_CREATE_COMMANDS.md) | Create custom Cursor commands |
| [Auckland README](./examples/auckland-java-consent/README.md) | Beginner workshop details |
| [Thailand README](./examples/thailand-dotnet-legacy/README.md) | Intermediate workshop details |
| [Appointment Reminder README](./examples/appointment-reminder-service/README.md) | Advanced workshop details |

---

## 🎉 Let's Get Started!

Pick your skill level and dive in:

- **🟢 Beginner?** Start with [Auckland Java Consent](./examples/auckland-java-consent/)
- **🟡 Intermediate?** Try [Thailand .NET Legacy](./examples/thailand-dotnet-legacy/)
- **🔴 Advanced?** Build [Appointment Reminder Service](./examples/appointment-reminder-service/)

**See [QUICK_START_GUIDES.md](./QUICK_START_GUIDES.md) for detailed instructions.**

---

**Ready to build healthcare software faster with AI? Let's go! 🚀**