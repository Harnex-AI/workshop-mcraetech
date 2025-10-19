# Workshop 1 — AI-Accelerated Development with Spec-Driven Flow (2:00PM – 4:00PM)

**Focus →** Spec-driven development workflow **with AI acceleration at every stage**.
**Maps to modules →** M1 (Prompting), M2 (Spec-Driven), M3 (Context), M4 (Model Selection), M7 (Documentation).

**Core Philosophy:** TODO → DONE → DOCS as the foundation of all AI-accelerated work.

## Agenda (120 min)

**2:00PM – 2:15PM — Intro: The McCraeTech AI Standard**

* Why spec-driven development is non-negotiable for healthcare software
* The TODO → DONE → DOCS workflow as the foundation
* How AI accelerates (not replaces) this disciplined approach
* Overview: We'll build a real feature end-to-end using this standard
* **Outcome:** Everyone understands the workflow we're about to demonstrate

**2:15PM – 2:35PM — Demo 1: Advanced Prompt Engineering**

* **The McCraeTech Standard in Action**
  - Domain-specific rules embedded in `.cursorrules` and `/context/standards.md`
  - Advanced prompting techniques: Planning mode, Reasoning mode, Verification mode
  - 3-part prompt structure: Instruction · Constraints · Examples + self-critique
* **Live Demo:** Set up the standard for a new feature
  - Create TODO spec with acceptance criteria
  - Use planning prompts to break down the work
  - Use reasoning prompts to validate approach
  - Use verification prompts to check completeness
* **Artefacts Created:**
  - `.cursorrules` with McCraeTech standards
  - `/prompts/planning.pattern.md`
  - `/prompts/reasoning.pattern.md`
  - `/prompts/verification.pattern.md`
  - `TODO.md` with feature spec
* **Why:** Establishes the "AI dialect" and ensures agents always follow our standards

**2:35PM – 3:05PM — Demo 2: Spec-Driven Context Engineering**

* **The TODO → DONE → DOCS Flow**
  - Start with a TODO spec (human-readable requirements + acceptance criteria)
  - Generate implementation with spec-aware context
  - Mark as DONE when tests pass and requirements met
  - Auto-generate DOCS from the completed work
* **Live Demo:** Build a feature using spec-driven flow
  - Write `/specs/feature-name/TODO.md` with clear acceptance criteria
  - Create `/context/Agent.md` that instructs agents to always check TODO first
  - Use medium model (Claude Sonnet) to generate implementation
  - Use small model (Claude Haiku) for quick edits and lint fixes
  - Use large model (Claude Opus) for verification against spec
  - Move TODO → DONE when verified
  - Generate `/docs/feature-name.md` from DONE spec
* **Artefacts Created:**
  - `/specs/feature-name/TODO.md` → `/specs/feature-name/DONE.md`
  - `/context/Agent.md` (spec-driven instructions)
  - `/context/standards.md` (McCraeTech coding standards)
  - `/docs/feature-name.md` (auto-generated from DONE)
  - Working feature code with tests
* **Why:** Documentation is never stale—it's generated from verified specs. Context ensures every AI interaction is spec-aware.

**3:05PM – 3:20PM — Demo 3: Effective Model Selection in Practice**

* **Right Model for Right Task**
  - Small models (Haiku, GPT-4o-mini): Quick fixes, linting, formatting, simple edits
  - Medium models (Sonnet, GPT-4o): Code generation, refactoring, implementation
  - Large models (Opus, o1): Planning, architecture, verification, complex reasoning
* **Live Demo:** Same feature, different models
  - Show cost/quality tradeoffs in real-time
  - Demonstrate when to escalate to larger models
  - Show how spec quality affects model selection (better spec = can use cheaper model)
* **Artefacts Created:**
  - `/ops/model-routing.md` (decision matrix)
  - Cost/quality comparison report
* **Why:** Intentional model selection = predictable costs + reliable output

**3:20PM – 3:45PM — Hands-On Lab: Apply the Full Standard**

* **Guided Practice:** Each participant builds a small feature using the complete flow
  1. Write a TODO spec with acceptance criteria
  2. Use planning prompts to break down work
  3. Select appropriate model for each task
  4. Generate implementation with spec-aware context
  5. Use verification prompts to check against spec
  6. Move TODO → DONE
  7. Auto-generate documentation
* **Support:** Instructors circulate to help with:
  - Writing effective TODO specs
  - Choosing the right model
  - Using advanced prompts
  - Verifying completeness
* **Checkpoint Metrics:**
  - Time from TODO to DONE
  - Number of verification failures
  - Documentation completeness score
  - Cost per feature (model usage)

**3:45PM – 4:00PM — Retrospective & Workshop 2 Preview**

* **Review:** What we built today
  - Complete TODO → DONE → DOCS workflow
  - McCraeTech AI Standard embedded in codebase
  - Practical model selection strategy
  - Real features with auto-generated docs
* **Gaps Identified:** Where we still need human oversight
  - Spec ambiguities that need clarification
  - Edge cases not covered in TODO
  - Integration points requiring manual verification
* **Workshop 2 Preview:** Autonomous agentic development
  - Multi-agent orchestration (Planner ↔ Verifier)
  - Automated PR review and security scanning
  - CI/CD integration with AI gates
  - Scaling from individual to team workflows

### Workshop-1 Deliverables

* **McCraeTech AI Standard** embedded in codebase:
  - `.cursorrules` with domain-specific rules
  - `/context/Agent.md` (spec-driven agent instructions)
  - `/context/standards.md` (coding standards)
* **Advanced prompt patterns:**
  - `/prompts/planning.pattern.md`
  - `/prompts/reasoning.pattern.md`
  - `/prompts/verification.pattern.md`
* **Spec-driven workflow artifacts:**
  - Example TODO → DONE → DOCS flow
  - `/specs/*/TODO.md` and `/specs/*/DONE.md`
  - Auto-generated `/docs/*.md`
* **Model selection strategy:**
  - `/ops/model-routing.md` (decision matrix)
  - Cost/quality analysis report
* **Working features** built during workshop with full documentation
* **Baseline metrics** for Workshop 2 comparison

---

# Workshop 2 — Autonomous Agentic Development (2:00PM – 4:00PM)

**Focus →** Multi-agent orchestration with gated autonomy and CI/CD integration.
**Maps to modules →** M5 (PR Review), M6 (Test Generation), M8 (Orchestration), + deployment.

**Prerequisites:** Workshop 1 completed—participants have working TODO → DONE → DOCS flow with McCraeTech AI Standard embedded.

## Agenda (120 min)

**2:00PM – 2:15PM — Bridge: From Assisted to Autonomous**

* **Workshop 1 Recap:** Individual developer with AI acceleration
  - Spec-driven workflow (TODO → DONE → DOCS)
  - Advanced prompting (Planning, Reasoning, Verification)
  - Strategic model selection
* **Workshop 2 Goal:** Team-scale autonomous development
  - Multi-agent orchestration (Planner ↔ Verifier ↔ Tester)
  - Automated quality gates (PR review, security, testing)
  - CI/CD integration with AI checkpoints
* **Mental Model:** Agents as specialized team members with clear hand-offs
* **Outcome:** Everyone understands the shift from "AI helps me code" to "AI team builds with oversight"

**2:15PM – 2:45PM — Advanced Spec-Driven Scaffolding (OpenAPI v3)**

* **Building on Workshop 1's TODO → DONE flow**
  - Extend spec-driven approach to API contracts
  - OpenAPI as the "TODO" for service interfaces
  - Auto-generate server/client stubs from spec
  - Identify spec ambiguities and propose improvements
  - Regenerate with refined spec
* **Live Demo:** API-first development with AI
  - Start with OpenAPI spec in `/specs/<service>/openapi.yaml`
  - Use Planner agent to generate scaffolds
  - Use Verifier agent to check spec compliance
  - Iterate on spec based on implementation learnings
  - Generate final DOCS from validated spec
* **Artefacts Created:**
  - `/specs/<service>/openapi.yaml` (refined through iteration)
  - Generated server/client stubs
  - `/specs/<service>/spec-evolution.md` (ambiguities found and resolved)
  - API documentation auto-generated from spec
* **Why:** Closes the spec→code gap with a repeatable, verifiable loop

**2:45PM – 3:10PM — Automated PR Review + Security Scanning**

* **Quality Gates Before Merge**
  - AI-powered PR review checklist (contract compliance, style, security)
  - Automated security scanning (secrets, vulnerabilities, compliance)
  - Auto-fix suggestions with verification
* **Live Demo:** PR review automation
  - Create PR with intentional issues (security, style, contract violations)
  - Run AI review agent with McCraeTech standards
  - Show automated security scan results
  - Demonstrate auto-fix workflow
  - Verify fixes meet standards
* **Artefacts Created:**
  - `/verification/pr-checklist.md` (McCraeTech-specific checks)
  - `.github/pull_request_template.md` (with AI review integration)
  - `/verification/security-rules.yaml`
  - Example AI review report with auto-fix suggestions
* **Why:** Shift-left quality; healthcare compliance built into workflow

**3:10PM – 3:30PM — Test Generation at Scale**

* **Automated Test Coverage**
  - Auto-generate unit/integration tests from specs
  - Target low-coverage areas
  - Fix flaky tests with AI analysis
  - Verify tests against acceptance criteria
* **Live Demo:** Test generation workflow
  - Identify low-coverage module
  - Generate tests from DONE spec and code
  - Run tests and analyze failures
  - Iterate on tests until passing
  - Update coverage metrics
* **Artefacts Created:**
  - Auto-generated tests in `/tests/auto/`
  - Coverage delta report
  - Flaky test analysis and fixes
* **Why:** Massive time savings for legacy codebases (Thailand team pain point)

**3:30PM – 3:50PM — Multi-Agent Orchestration**

* **The Agentic Team**
  - **Planner Agent:** Decomposes TODO specs into tasks
  - **Generator Agent:** Implements code from tasks
  - **Verifier Agent:** Checks against spec and standards
  - **Tester Agent:** Generates and runs tests
  - **Reviewer Agent:** Final quality gate before merge
* **Live Demo:** End-to-end agentic workflow
  - Feed TODO spec to Planner
  - Planner creates task breakdown
  - Generator implements each task
  - Verifier checks each implementation
  - Tester generates and runs tests
  - Reviewer performs final check
  - Human approves merge
* **Artefacts Created:**
  - `/agents/pipeline.yaml` (orchestration config)
  - `/agents/runbook.md` (how to operate the pipeline)
  - Example run logs showing agent hand-offs
  - Metrics: time saved, quality gates triggered
* **Why:** First durable agentic loop with clear accountability

**3:50PM – 4:00PM — CI/CD Integration & ROI Review**

* **Deployment Hand-off**
  - Connect PR gates to CI/CD pipeline
  - Auto-generate release notes from DONE specs
  - Staged deployment with AI checkpoints
* **ROI Analysis:**
  - Compare Workshop 1 baseline metrics to Workshop 2 results
  - Time saved: TODO to DONE to Deploy
  - Quality improvements: defects caught by AI gates
  - Cost analysis: model usage vs. developer time saved
* **Next Steps:**
  - Path to full multi-agent expansion (Auckland: research mode)
  - Automation emphasis for legacy codebases (Thailand: test generation)
  - Customizing agents for domain-specific needs
* **Artefacts Created:**
  - `.ci/ai-gates.yml` (CI/CD integration example)
  - Release notes auto-generated from DONE specs
  - ROI report comparing Workshop 1 vs Workshop 2 metrics

### Workshop-2 Deliverables

* **Advanced spec-driven scaffolding:**
  - Refined OpenAPI specs with ambiguities resolved
  - Auto-generated server/client stubs
  - Spec evolution documentation
* **Automated quality gates:**
  - PR review checklist and templates
  - Security scanning rules and reports
  - Auto-fix examples
* **Test generation at scale:**
  - Auto-generated test suites
  - Coverage improvement reports
  - Flaky test fixes
* **Multi-agent orchestration:**
  - Working Planner ↔ Generator ↔ Verifier ↔ Tester ↔ Reviewer pipeline
  - Orchestration config and runbook
  - Example run logs and metrics
* **CI/CD integration:**
  - AI gate configuration
  - Auto-generated release notes
  - ROI analysis report

---

## Why This Restructured Split Works

### Workshop 1: Foundation with Immediate Value
* **Spec-driven from day one:** TODO → DONE → DOCS is the foundation, not an afterthought
* **McCraeTech Standard embedded:** Domain-specific rules and prompts ensure consistency
* **Practical demonstrations:** Three live demos showing real features being built
* **Hands-on practice:** Participants build actual features using the complete workflow
* **Immediate ROI:** Working code + auto-generated docs + baseline metrics
* **Natural documentation:** Docs are generated from verified specs, never stale
* **Strategic model selection:** Cost control built into the workflow from the start

### Workshop 2: Scale with Autonomy
* **Builds on Workshop 1:** Assumes participants have working spec-driven flow
* **Team-scale automation:** Multi-agent orchestration for larger projects
* **Quality gates:** Automated PR review, security scanning, test generation
* **CI/CD integration:** Complete pipeline from TODO to production deployment
* **ROI validation:** Measurable comparison to Workshop 1 baseline metrics

### Key Improvements from Original Plan
1. **Spec-driven development is now core to Workshop 1** (not deferred to Workshop 2)
2. **Documentation is embedded throughout** (not a separate section)
3. **McCraeTech Standard leads** (establishes foundation immediately)
4. **Practical demos replace theory** (show, don't just tell)
5. **Model selection is strategic** (right model for right task, demonstrated in practice)
6. **Clear progression:** Individual → Team, Assisted → Autonomous

### Alignment with Team Needs
* **Auckland (research-focused):** Workshop 1 gives them spec-driven discipline; Workshop 2 enables multi-agent research workflows
* **Thailand (legacy codebases):** Workshop 1 establishes standards; Workshop 2 delivers automated test generation at scale
* **Healthcare compliance:** Spec-driven approach with auto-generated docs ensures auditability
* **Cost control:** Strategic model selection prevents runaway AI costs

