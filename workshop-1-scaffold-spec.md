Perfect—let’s **lock Workshop 1 to its modules only** (M1 Prompting, M3 Context, M4 Model Selection, M7 Docs, plus a unifying “McCraeTech AI Standard”), and **defer all spec‑driven/agentic pieces** (spec→code generation, PR gates, security scans, tests at scale, Planner↔Verifier) to Workshop 2.

Below you’ll find:

1. **Agenda‑mapped expert demos + hands‑on** (per your 2–4 pm plan)
2. An updated, **Cursor‑compliant** repo scaffold (Workshop 1 only) with **filled file contents**
3. A **machine‑readable scaffold plan** your agent can execute

---

## 1) Agenda → Modules → Demos → Artifacts

> **Only Workshop 1 modules** are used: **M1 Prompting**, **M3 Context**, **M4 Model Selection**, **M7 Documentation**, plus **“McCraeTech AI Standard.”**
> Cursor rule files use **`.mdc`** with YAML front‑matter (`description`, `globs`) + markdown body, placed in `.cursor/rules/`. ([Cursor][1])
> Add project docs to Cursor’s **Docs** panel for grounding during the session. ([Cursor][2])

### 2:00–2:20 — Intro: AI Software Landscape

* **Expert demo:** “From ad‑hoc prompting to **governed assistance**” (why rules, context, and model selection).
* **Artifacts:** `workshop/01-intro.md` (talk track), `.cursor/rules/00-baseline.mdc` seeded.

### 2:20–2:40 — Advanced Prompt Engineering (M1)

* **Expert demo (Auckland):** Fix **consent‑expiry** bug in `ConsentService.java` using **3‑part prompt + self‑critique**.
* **Expert demo (Thailand):** Remove **PHI logging** in `LegacyPatientsController.cs` with **minimal‑change** patch prompt.
* **Hands‑on:** Engineers adapt the pattern to their track.
* **Artifacts:** `prompts/enterprise_code.pattern.md`, `prompts/self_critique.rubric.md`, diffs recorded.

### 2:40–3:00 — Context Engineering (M3)

* **Expert demo:** Add `/context/Agent.md`, `/context/standards.md`, `/context/product_domain.md` to Cursor **Docs**; re‑run the same prompt and compare.
* **Hands‑on:** Each group wires Docs, updates standards bullets for their stack.
* **Artifacts:** Context folder committed; deltas noted in `ops/run-report.md`.

### 3:00–3:15 — Effective Model Selection (M4)

* **Expert demo:** Switch **small→medium→large** routes on the *same* fix; show quality/cost trade‑off.
* **Hands‑on:** Participants run two routes and fill `ops/run-report.md`.
* **Artifacts:** `ops/model-routing.json`, `ops/run-report.md` (at least 2 rows filled).

### 3:15–3:30 — Automated Documentation (M7)

* **Expert demo:** Generate `docs/SDK_README.md` (from current code + context) and add a **CHANGELOG** entry.
* **Hands‑on:** Teams produce both docs for their example.
* **Artifacts:** `docs/SDK_README.md`, `docs/CHANGELOG.md`.

### 3:30–3:45 — The McCraeTech AI Standard (unifier)

* **Expert demo:** Combine rules + context + prompts + routing into **one governing rule** that encodes domain red‑flags (PHI, UTC, consent scope) and style expectations.
* **Hands‑on:** Commit `.cursor/rules/90-mccraetech-ai-standard.mdc`.
* **Artifacts:** The standard rule; a concise “how‑to use” snippet in `README.md`.

### 3:45–4:00 — Q&A & Next Workshop

* **Outcome:** Clear handoff to **Workshop 2** (spec‑driven + agentic). No W2 files live here.

---

## 2) Workshop 1 Repo Scaffold (trimmed to W1 modules)

```
workshop1-cursor-ai/
├─ README.md
├─ .gitignore
├─ .cursor/
│  └─ rules/
│     ├─ 00-baseline.mdc
│     ├─ 10-advanced-prompting.mdc
│     ├─ 20-context-engineering.mdc
│     ├─ 30-model-selection.mdc
│     ├─ 40-docs-automation.mdc
│     └─ 90-mccraetech-ai-standard.mdc
├─ prompts/
│  ├─ enterprise_code.pattern.md
│  ├─ self_critique.rubric.md
│  ├─ sdk_readme.pattern.md
│  └─ changelog_entry.pattern.md
├─ context/
│  ├─ Agent.md
│  ├─ standards.md
│  └─ product_domain.md
├─ ops/
│  ├─ model-routing.json
│  └─ run-report.md
├─ docs/
│  ├─ SDK_README.template.md
│  └─ CHANGELOG.md
├─ examples/
│  ├─ auckland-java-consent/
│  │  ├─ src/main/java/com/mccrae/consent/ConsentService.java
│  │  ├─ src/main/java/com/mccrae/consent/ConsentController.java
│  │  └─ README.md
│  └─ thailand-dotnet-legacy/
│     ├─ Controllers/LegacyPatientsController.cs
│     ├─ Services/LegacyRepo.cs
│     └─ README.md
└─ workshop/
   ├─ 01-intro.md
   ├─ 02-advanced-prompting.md
   ├─ 03-context-engineering.md
   ├─ 04-model-selection.md
   ├─ 05-automated-docs.md
   ├─ 06-mccraetech-standard.md
   └─ quickstart.md
```

### 2.1 Cursor Rules — **exact contents**

**`.cursor/rules/00-baseline.mdc`**

```md
---
description: Baseline behaviours for Workshop 1 (assisted development only).
globs: ["**/*"]
---
# Baseline (Workshop 1)
- Use minimal, testable diffs and clear docstrings.
- Never invent APIs or clinical semantics; if unclear, output a **Questions** block first.
- Treat `/context/*` as authoritative; reference relevant items explicitly.
- Always run the **Self‑Critique** rubric before final output.
- Log model route + token estimates in `/ops/run-report.md`.
```

**`.cursor/rules/10-advanced-prompting.mdc`**

```md
---
description: Advanced prompt patterns for production-grade enterprise code (M1).
globs: ["prompts/**", "examples/**"]
---
# Advanced Prompting Rules
- Structure prompts as: **Instruction → Constraints → Inputs → Steps → Self‑Critique**.
- Constraints must reference `/context/standards.md` and domain rules in `/context/product_domain.md`.
- Prefer "Minimal Change Patch" for fixes. Provide a unified diff and a short rollback plan.
```

**`.cursor/rules/20-context-engineering.mdc`**

```md
---
description: Treat repo docs and standards as grounding context (M3).
globs: ["context/**", "examples/**", "docs/**"]
---
# Context Engineering Rules
- Assume `/context/Agent.md` defines mission, inputs, and outputs.
- Always cross-check health-domain specifics (PHI, consent scope, UTC) from `/context/product_domain.md`.
- When guidance conflicts, defer to `/context/standards.md` and note the decision in output.
```

**`.cursor/rules/30-model-selection.mdc`**

```md
---
description: Deliberate small/medium/large selection and escalation (M4).
globs: ["ops/**", "examples/**"]
---
# Model Selection Rules
- small → lint/edit/refactor; medium → generate code/docs; large → verification/ambiguity hunts.
- If Self‑Critique fails or ambiguity remains, escalate to **large**, revise, and re‑verify.
- Record each run in `/ops/run-report.md`.
```

**`.cursor/rules/40-docs-automation.mdc`**

```md
---
description: Generate SDK README and CHANGELOG from current code and context (M7).
globs: ["docs/**", "prompts/**", "examples/**"]
---
# Docs Automation Rules
- Use `prompts/sdk_readme.pattern.md` to produce `/docs/SDK_README.md` using example code + context.
- Add a bullet under `/docs/CHANGELOG.md` using `prompts/changelog_entry.pattern.md`.
- Limitations must state any remaining ambiguity (e.g., consent header semantics or timezone).
```

**`.cursor/rules/90-mccraetech-ai-standard.mdc`**

```md
---
description: The McCraeTech AI Standard that unifies prompting, context, selection, and docs.
globs: ["**/*"]
---
# McCraeTech AI Standard (Workshop 1)
## Domain Red Flags
- **PHI**: never log or include real patient data; use synthetic examples only.
- **Consent**: require `EHR_VIEW` scope when accessing clinical data; fail safe if absent.
- **UTC**: store/return timestamps as UTC; document conversions at boundaries.

## Coding Expectations
- **Java**: Spring idioms; thin Controller, thick Service; DTOs with Bean Validation; no entity leakage.
- **.NET**: ASP.NET Core; nullable enabled; input validation; `ProblemDetails` for errors.

## Process Expectations
- Use the **Enterprise Code** prompt pattern for changes.
- Choose model route deliberately; log runs; escalate when verification fails.
- Generate/update `SDK_README.md` + `CHANGELOG.md` after meaningful changes.
```

> Why `.mdc` with front‑matter and `globs`: this is the Cursor‑supported format for Rules, enabling scoped, auto‑applied guidance. ([Cursor][1])

---

### 2.2 Prompts — **exact contents**

**`prompts/enterprise_code.pattern.md`**

```md
# Enterprise Code Change (Instruction · Constraints · Inputs · Steps · Self‑Critique)

## Instruction
You are a senior engineer updating production code. Make the **smallest safe change** that fully resolves the issue.

## Constraints
- Follow `/context/standards.md` + `.cursor/rules/90-mccraetech-ai-standard.mdc`.
- Respect domain rules in `/context/product_domain.md` (PHI, consent scope, UTC).
- Output a **unified diff** for changed files and a brief rollback plan.

## Inputs
- Files: {{FILE_PATHS}}
- Observed behaviour: {{OBSERVED}}
- Expected behaviour: {{EXPECTED}}

## Steps
1) Root‑cause analysis (bullets).
2) Minimal patch (diff).
3) Clarifications (if any).
4) Risks & rollback.

## Self‑Critique
Run `prompts/self_critique.rubric.md`. If any criterion fails, revise and show the updated diff.
```

**`prompts/self_critique.rubric.md`**

```md
# Self‑Critique (Workshop 1)
- Spec/Intent alignment clear?
- Domain safety: PHI, consent scope, UTC?
- Minimality: smallest coherent patch?
- Readability: idiomatic style + docstrings?
- Side‑effects: logging, errors, config?
```

**`prompts/sdk_readme.pattern.md`**

```md
# Generate SDK README (from code + context)
Use the example service code and `/context/*` to produce `/docs/SDK_README.md`.

Sections:
1) Overview (what it does)
2) Install
3) Quickstart (10–15 lines of code)
4) API (main endpoints/classes)
5) Examples (2 concise flows)
6) Limitations (note consent/UTC assumptions)
7) Changelog link
```

**`prompts/changelog_entry.pattern.md`**

```md
# CHANGELOG Entry (Keep a Changelog style)
## [Unreleased]
- {{ADDED|CHANGED|FIXED|SECURITY}}: {{WHAT}} (files: {{FILES}})
```

---

### 2.3 Context — **exact contents**

**`context/Agent.md`**

```md
# Agent Contract (Workshop 1)
Mission: help engineers deliver correct, safe, minimal changes quickly.
Inputs: /prompts, /context, /examples
Outputs: diffs, reasoning notes, README, CHANGELOG
```

**`context/standards.md`**

```md
# Coding Standards (Excerpt)
Java: Spring Boot; DTOs + Bean Validation; no entity leakage; @Slf4j; never log secrets/PHI.
.NET: ASP.NET Core; nullable enabled; validate inputs; return ProblemDetails; avoid PHI in logs.
Security: config via env; input validation; output encoding; redact sensitive data.
Time: all external-facing timestamps are UTC; document conversions.
```

**`context/product_domain.md`**

```md
# Healthcare Domain Rules
- No PHI in code, logs, or examples; use synthetic data.
- Consent: operations on clinical data require EHR_VIEW scope; fail safe if missing.
- Time: store/return UTC; annotate boundaries where conversion occurs.
```

---

### 2.4 Ops, Docs — **exact contents**

**`ops/model-routing.json`**

```json
{
  "version": 1,
  "routes": {
    "lint_or_edit": { "model": "small",  "notes": "Fast, cheap for refactors/edits." },
    "generate_code": { "model": "medium", "notes": "Primary for code/docs generation." },
    "verify_changes": { "model": "large", "notes": "Use for self‑critique + ambiguity hunts." }
  },
  "policy": [
    { "if": "task == 'edit' || risk == 'low'", "use": "lint_or_edit" },
    { "if": "task == 'feature' || task == 'docs'", "use": "generate_code" },
    { "if": "critique_failed == true || ambiguity == true", "use": "verify_changes" }
  ]
}
```

**`ops/run-report.md`**

```md
# Run Report
| Time | Track | Task | Route | Input Tokens | Output Tokens | Outcome | Notes |
|------|-------|------|-------|--------------|---------------|---------|-------|
```

**`docs/SDK_README.template.md`**

````md
# {{SERVICE_NAME}} SDK

## Overview
{{ONE_PARAGRAPH}}

## Install
{{STEPS}}

## Quickstart
```{{LANG}}
{{CODE_SNIPPET}}
````

## API

* {{MEMBER}} — {{DESC}}

## Examples

1. {{FLOW_1}}
2. {{FLOW_2}}

## Limitations

* Consent: {{NOTE}}
* Time: {{NOTE}}

## Changelog

See [CHANGELOG.md](./CHANGELOG.md).

````

**`docs/CHANGELOG.md`**
```md
# Changelog
## [Unreleased]
- Initial Workshop 1 scaffolding.
````

---

### 2.5 Tailored Examples (simple, **Workshop‑1‑only**)

> These are intentionally **small** so they fit the 2‑hour session; they are real enough to resonate with each team’s pain points but **do not** include spec‑to‑code or agentic orchestration (that’s Workshop 2).

**`examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentService.java`**

```java
package com.mccrae.consent;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ConsentService {
  private final ConsentRepository repo;
  private final Clock clock;

  public ConsentService(ConsentRepository repo, Clock clock) {
    this.repo = repo;
    this.clock = clock;
  }

  // BUG FOR WORKSHOP: expired consents are not filtered
  public List<Consent> findActiveByPatient(String patientRef) {
    Instant now = clock.instant();
    return repo.findByPatientRef(patientRef).stream()
      // TODO: filter expired consents: c.getExpiresAt().isAfter(now)
      .collect(Collectors.toList());
  }
}
```

**`examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentController.java`**

```java
package com.mccrae.consent;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consents")
public class ConsentController {
  private final ConsentService service;
  public ConsentController(ConsentService service) { this.service = service; }

  // NOTE: for Workshop 1 we only demo assisted fixes and docs (no spec→code)
  @GetMapping("/active")
  public List<Consent> active(@RequestHeader("X-Consent-Scope") String scope,
                              @RequestParam String patientRef) {
    // For the demo, assume EHR_VIEW is required—document the assumption in README Limitations.
    return service.findActiveByPatient(patientRef);
  }
}
```

**`examples/auckland-java-consent/README.md`**

```md
# Auckland: Consent Service (Workshop 1)
Use this minimal service to practice advanced prompting, context grounding, model selection, and docs generation.
```

**`examples/thailand-dotnet-legacy/Controllers/LegacyPatientsController.cs`**

```csharp
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace ThailandLegacy.Controllers;

[ApiController]
[Route("legacy/patients")]
public class LegacyPatientsController : ControllerBase
{
    private readonly ILogger<LegacyPatientsController> _logger;
    private readonly LegacyRepo _repo;

    public LegacyPatientsController(ILogger<LegacyPatientsController> logger, LegacyRepo repo)
    {
        _logger = logger;
        _repo = repo;
    }

    // ISSUES FOR WORKSHOP:
    // - Logs full DTO (PHI risk)
    // - No validation or UTC normalization
    [HttpPost]
    public IActionResult Create([FromBody] PatientDto dto)
    {
        _logger.LogInformation("Creating patient {@dto}", dto); // TODO: avoid PHI logging
        var id = _repo.Save(dto.Name, dto.Dob, dto.Address);     // TODO: validate + UTC
        return Ok(new { id });
    }
}
```

**`examples/thailand-dotnet-legacy/Services/LegacyRepo.cs`**

```csharp
namespace ThailandLegacy;
public class LegacyRepo
{
    public string Save(string name, DateTime dob, string? address)
    {
        // pretend to persist and return an id
        return Guid.NewGuid().ToString();
    }
}
```

**`examples/thailand-dotnet-legacy/README.md`**

```md
# Thailand: Legacy Patients (Workshop 1)
Practice minimal-change PHI-safe logging and UTC normalization using patterns + context + model routing.
```

---

### 2.6 Workshop Docs — **exact contents**

**`workshop/01-intro.md`**

```md
# Intro (2:00–2:20)
- Why governed AI assistance: rules (.mdc), context (/context), and model selection.
- What we will NOT do in W1: spec→code, PR gates, tests at scale, multi-agent.
```

**`workshop/02-advanced-prompting.md`**

```md
# Advanced Prompting (2:20–2:40)
Instructor: demo on ConsentService.java (expiry filter) and LegacyPatientsController.cs (PHI logging).
Participants: run prompts/enterprise_code.pattern.md on your track; commit the diff.
```

**`workshop/03-context-engineering.md`**

```md
# Context Engineering (2:40–3:00)
Add /context files to Cursor Docs. Re-run the same prompt; compare outputs; record notes in ops/run-report.md.
```

**`workshop/04-model-selection.md`**

```md
# Model Selection (3:00–3:15)
Run the same task with 'lint_or_edit' and 'generate_code' routes; fill ops/run-report.md with tokens/outcomes.
```

**`workshop/05-automated-docs.md`**

```md
# Automated Documentation (3:15–3:30)
Use prompts/sdk_readme.pattern.md to create docs/SDK_README.md; add a CHANGELOG bullet.
```

**`workshop/06-mccraetech-standard.md`**

```md
# The McCraeTech AI Standard (3:30–3:45)
Review and commit .cursor/rules/90-mccraetech-ai-standard.mdc. This is your day-to-day guardrail.
```

**`workshop/quickstart.md`**

```md
# Quickstart
1) Open repo in Cursor; add /context to Docs.
2) Use 'enterprise_code.pattern.md' on your track’s example.
3) Switch model routes and log results in ops/run-report.md.
4) Generate SDK_README.md and CHANGELOG.md.
5) Commit .cursor/rules/90-mccraetech-ai-standard.mdc.
```

**`README.md`**

```md
# Workshop 1 — Cursor AI-Accelerated Development
Modules: Advanced Prompting (M1), Context Engineering (M3), Model Selection (M4), Docs Automation (M7), McCraeTech AI Standard.

## Use in the Session
Follow `workshop/*` in order. Only assisted workflows here; spec-driven/agentic are Workshop 2.
```

---

## 3) Machine‑Readable Scaffold Plan (Workshop 1 only)

````yaml
kind: ScaffoldPlan
version: 4
repo_name: workshop1-cursor-ai
steps:
  - mkdir:
      - ".cursor/rules"
      - "prompts"
      - "context"
      - "ops"
      - "docs"
      - "examples/auckland-java-consent/src/main/java/com/mccrae/consent"
      - "examples/thailand-dotnet-legacy/Controllers"
      - "examples/thailand-dotnet-legacy/Services"
      - "workshop"
  - write: { path: README.md, content: "# Workshop 1 — Cursor AI-Accelerated Development\nModules: Advanced Prompting (M1), Context Engineering (M3), Model Selection (M4), Docs Automation (M7), McCraeTech AI Standard.\n\n## Use in the Session\nFollow `workshop/*` in order. Only assisted workflows here; spec-driven/agentic are Workshop 2.\n" }
  - write: { path: .gitignore, content: ".DS_Store\nnode_modules/\ntarget/\nbin/\nobj/\n.env\n*.log\n" }

  # Cursor-compliant rules
  - write: { path: .cursor/rules/00-baseline.mdc, content: "---\ndescription: Baseline behaviours for Workshop 1 (assisted development only).\nglobs: [\"**/*\"]\n---\n# Baseline (Workshop 1)\n- Use minimal, testable diffs and clear docstrings.\n- Never invent APIs or clinical semantics; if unclear, output a **Questions** block first.\n- Treat `/context/*` as authoritative; reference relevant items explicitly.\n- Always run the **Self‑Critique** rubric before final output.\n- Log model route + token estimates in `/ops/run-report.md`.\n" }
  - write: { path: .cursor/rules/10-advanced-prompting.mdc, content: "---\ndescription: Advanced prompt patterns for production-grade enterprise code (M1).\nglobs: [\"prompts/**\", \"examples/**\"]\n---\n# Advanced Prompting Rules\n- Structure prompts as: **Instruction → Constraints → Inputs → Steps → Self‑Critique**.\n- Constraints must reference `/context/standards.md` and domain rules in `/context/product_domain.md`.\n- Prefer \"Minimal Change Patch\" for fixes. Provide a unified diff and a short rollback plan.\n" }
  - write: { path: .cursor/rules/20-context-engineering.mdc, content: "---\ndescription: Treat repo docs and standards as grounding context (M3).\nglobs: [\"context/**\", \"examples/**\", \"docs/**\"]\n---\n# Context Engineering Rules\n- Assume `/context/Agent.md` defines mission, inputs, and outputs.\n- Always cross-check health-domain specifics (PHI, consent scope, UTC) from `/context/product_domain.md`.\n- When guidance conflicts, defer to `/context/standards.md` and note the decision in output.\n" }
  - write: { path: .cursor/rules/30-model-selection.mdc, content: "---\ndescription: Deliberate small/medium/large selection and escalation (M4).\nglobs: [\"ops/**\", \"examples/**\"]\n---\n# Model Selection Rules\n- small → lint/edit/refactor; medium → generate code/docs; large → verification/ambiguity hunts.\n- If Self‑Critique fails or ambiguity remains, escalate to **large**, revise, and re‑verify.\n- Record each run in `/ops/run-report.md`.\n" }
  - write: { path: .cursor/rules/40-docs-automation.mdc, content: "---\ndescription: Generate SDK README and CHANGELOG from current code and context (M7).\nglobs: [\"docs/**\", \"prompts/**\", \"examples/**\"]\n---\n# Docs Automation Rules\n- Use `prompts/sdk_readme.pattern.md` to produce `/docs/SDK_README.md` using example code + context.\n- Add a bullet under `/docs/CHANGELOG.md` using `prompts/changelog_entry.pattern.md`.\n- Limitations must state any remaining ambiguity (e.g., consent/UTC assumptions).\n" }
  - write: { path: .cursor/rules/90-mccraetech-ai-standard.mdc, content: "---\ndescription: The McCraeTech AI Standard that unifies prompting, context, selection, and docs.\nglobs: [\"**/*\"]\n---\n# McCraeTech AI Standard (Workshop 1)\n## Domain Red Flags\n- **PHI**: never log or include real patient data; use synthetic examples only.\n- **Consent**: require `EHR_VIEW` scope when accessing clinical data; fail safe if absent.\n- **UTC**: store/return timestamps as UTC; document conversions at boundaries.\n\n## Coding Expectations\n- **Java**: Spring idioms; thin Controller, thick Service; DTOs with Bean Validation; no entity leakage.\n- **.NET**: ASP.NET Core; nullable enabled; input validation; return ProblemDetails; avoid PHI in logs.\n\n## Process Expectations\n- Use the **Enterprise Code** prompt pattern for changes.\n- Choose model route deliberately; log runs; escalate when verification fails.\n- Generate/update `SDK_README.md` + `CHANGELOG.md` after meaningful changes.\n" }

  # Prompts
  - write: { path: prompts/enterprise_code.pattern.md, content: "# Enterprise Code Change (Instruction · Constraints · Inputs · Steps · Self‑Critique)\n\n## Instruction\nYou are a senior engineer updating production code. Make the **smallest safe change** that fully resolves the issue.\n\n## Constraints\n- Follow `/context/standards.md` + `.cursor/rules/90-mccraetech-ai-standard.mdc`.\n- Respect domain rules in `/context/product_domain.md` (PHI, consent scope, UTC).\n- Output a **unified diff** for changed files and a brief rollback plan.\n\n## Inputs\n- Files: {{FILE_PATHS}}\n- Observed behaviour: {{OBSERVED}}\n- Expected behaviour: {{EXPECTED}}\n\n## Steps\n1) Root‑cause analysis (bullets).\n2) Minimal patch (diff).\n3) Clarifications (if any).\n4) Risks & rollback.\n\n## Self‑Critique\nRun `prompts/self_critique.rubric.md`. If any criterion fails, revise and show the updated diff.\n" }
  - write: { path: prompts/self_critique.rubric.md, content: "# Self‑Critique (Workshop 1)\n- Spec/Intent alignment clear?\n- Domain safety: PHI, consent scope, UTC?\n- Minimality: smallest coherent patch?\n- Readability: idiomatic style + docstrings?\n- Side‑effects: logging, errors, config?\n" }
  - write: { path: prompts/sdk_readme.pattern.md, content: "# Generate SDK README (from code + context)\nUse the example service code and `/context/*` to produce `/docs/SDK_README.md`.\n\nSections:\n1) Overview (what it does)\n2) Install\n3) Quickstart (10–15 lines of code)\n4) API (main endpoints/classes)\n5) Examples (2 concise flows)\n6) Limitations (note consent/UTC assumptions)\n7) Changelog link\n" }
  - write: { path: prompts/changelog_entry.pattern.md, content: "# CHANGELOG Entry (Keep a Changelog style)\n## [Unreleased]\n- {{ADDED|CHANGED|FIXED|SECURITY}}: {{WHAT}} (files: {{FILES}})\n" }

  # Context
  - write: { path: context/Agent.md, content: "# Agent Contract (Workshop 1)\nMission: help engineers deliver correct, safe, minimal changes quickly.\nInputs: /prompts, /context, /examples\nOutputs: diffs, reasoning notes, README, CHANGELOG\n" }
  - write: { path: context/standards.md, content: "# Coding Standards (Excerpt)\nJava: Spring Boot; DTOs + Bean Validation; no entity leakage; @Slf4j; never log secrets/PHI.\n.NET: ASP.NET Core; nullable enabled; validate inputs; return ProblemDetails; avoid PHI in logs.\nSecurity: config via env; input validation; output encoding; redact sensitive data.\nTime: all external-facing timestamps are UTC; document conversions.\n" }
  - write: { path: context/product_domain.md, content: "# Healthcare Domain Rules\n- No PHI in code, logs, or examples; use synthetic data.\n- Consent: operations on clinical data require EHR_VIEW scope; fail safe if missing.\n- Time: store/return UTC; annotate boundaries where conversion occurs.\n" }

  # Ops & Docs
  - write: { path: ops/model-routing.json, content: "{\n  \"version\": 1,\n  \"routes\": {\n    \"lint_or_edit\": { \"model\": \"small\",  \"notes\": \"Fast, cheap for refactors/edits.\" },\n    \"generate_code\": { \"model\": \"medium\", \"notes\": \"Primary for code/docs generation.\" },\n    \"verify_changes\": { \"model\": \"large\", \"notes\": \"Use for self‑critique + ambiguity hunts.\" }\n  },\n  \"policy\": [\n    { \"if\": \"task == 'edit' || risk == 'low'\", \"use\": \"lint_or_edit\" },\n    { \"if\": \"task == 'feature' || task == 'docs'\", \"use\": \"generate_code\" },\n    { \"if\": \"critique_failed == true || ambiguity == true\", \"use\": \"verify_changes\" }\n  ]\n}\n" }
  - write: { path: ops/run-report.md, content: "# Run Report\n| Time | Track | Task | Route | Input Tokens | Output Tokens | Outcome | Notes |\n|------|-------|------|-------|--------------|---------------|---------|-------|\n" }
  - write: { path: docs/SDK_README.template.md, content: "# {{SERVICE_NAME}} SDK\n\n## Overview\n{{ONE_PARAGRAPH}}\n\n## Install\n{{STEPS}}\n\n## Quickstart\n```{{LANG}}\n{{CODE_SNIPPET}}\n```\n\n## API\n- {{MEMBER}} — {{DESC}}\n\n## Examples\n1) {{FLOW_1}}\n2) {{FLOW_2}}\n\n## Limitations\n- Consent: {{NOTE}}\n- Time: {{NOTE}}\n\n## Changelog\nSee [CHANGELOG.md](./CHANGELOG.md).\n" }
  - write: { path: docs/CHANGELOG.md, content: "# Changelog\n## [Unreleased]\n- Initial Workshop 1 scaffolding.\n" }

  # Examples (Auckland + Thailand)
  - write: { path: examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentService.java, content: "package com.mccrae.consent;\n\nimport java.time.Instant;\nimport java.util.List;\nimport java.util.stream.Collectors;\n\npublic class ConsentService {\n  private final ConsentRepository repo;\n  private final Clock clock;\n\n  public ConsentService(ConsentRepository repo, Clock clock) {\n    this.repo = repo;\n    this.clock = clock;\n  }\n\n  // BUG FOR WORKSHOP: expired consents are not filtered\n  public List<Consent> findActiveByPatient(String patientRef) {\n    Instant now = clock.instant();\n    return repo.findByPatientRef(patientRef).stream()\n      // TODO: filter expired consents: c.getExpiresAt().isAfter(now)\n      .collect(Collectors.toList());\n  }\n}\n" }
  - write: { path: examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentController.java, content: "package com.mccrae.consent;\n\nimport org.springframework.web.bind.annotation.*;\nimport java.util.List;\n\n@RestController\n@RequestMapping(\"/consents\")\npublic class ConsentController {\n  private final ConsentService service;\n  public ConsentController(ConsentService service) { this.service = service; }\n\n  // NOTE: for Workshop 1 we only demo assisted fixes and docs (no spec→code)\n  @GetMapping(\"/active\")\n  public List<Consent> active(@RequestHeader(\"X-Consent-Scope\") String scope,\n                              @RequestParam String patientRef) {\n    // For the demo, assume EHR_VIEW is required—document the assumption in README Limitations.\n    return service.findActiveByPatient(patientRef);\n  }\n}\n" }
  - write: { path: examples/auckland-java-consent/README.md, content: "# Auckland: Consent Service (Workshop 1)\nUse this minimal service to practice advanced prompting, context grounding, model selection, and docs generation.\n" }
  - write: { path: examples/thailand-dotnet-legacy/Controllers/LegacyPatientsController.cs, content: "using Microsoft.AspNetCore.Mvc;\nusing Microsoft.Extensions.Logging;\n\nnamespace ThailandLegacy.Controllers;\n\n[ApiController]\n[Route(\"legacy/patients\")]\npublic class LegacyPatientsController : ControllerBase\n{\n    private readonly ILogger<LegacyPatientsController> _logger;\n    private readonly LegacyRepo _repo;\n\n    public LegacyPatientsController(ILogger<LegacyPatientsController> logger, LegacyRepo repo)\n    {\n        _logger = logger;\n        _repo = repo;\n    }\n\n    // ISSUES FOR WORKSHOP:\n    // - Logs full DTO (PHI risk)\n    // - No validation or UTC normalization\n    [HttpPost]\n    public IActionResult Create([FromBody] PatientDto dto)\n    {\n        _logger.LogInformation(\"Creating patient {@dto}\", dto); // TODO: avoid PHI logging\n        var id = _repo.Save(dto.Name, dto.Dob, dto.Address);     // TODO: validate + UTC\n        return Ok(new { id });\n    }\n}\n" }
  - write: { path: examples/thailand-dotnet-legacy/Services/LegacyRepo.cs, content: "namespace ThailandLegacy;\npublic class LegacyRepo\n{\n    public string Save(string name, DateTime dob, string? address)\n    {\n        // pretend to persist and return an id\n        return Guid.NewGuid().ToString();\n    }\n}\n" }
  - write: { path: examples/thailand-dotnet-legacy/README.md, content: "# Thailand: Legacy Patients (Workshop 1)\nPractice minimal-change PHI-safe logging and UTC normalization using patterns + context + model routing.\n" }

  # Workshop docs
  - write: { path: workshop/01-intro.md, content: "# Intro (2:00–2:20)\n- Why governed AI assistance: rules (.mdc), context (/context), and model selection.\n- What we will NOT do in W1: spec→code, PR gates, tests at scale, multi-agent.\n" }
  - write: { path: workshop/02-advanced-prompting.md, content: "# Advanced Prompting (2:20–2:40)\nInstructor: demo on ConsentService.java (expiry filter) and LegacyPatientsController.cs (PHI logging).\nParticipants: run prompts/enterprise_code.pattern.md on your track; commit the diff.\n" }
  - write: { path: workshop/03-context-engineering.md, content: "# Context Engineering (2:40–3:00)\nAdd /context files to Cursor Docs. Re-run the same prompt; compare outputs; record notes in ops/run-report.md.\n" }
  - write: { path: workshop/04-model-selection.md, content: "# Model Selection (3:00–3:15)\nRun the same task with 'lint_or_edit' and 'generate_code' routes; fill ops/run-report.md with tokens/outcomes.\n" }
  - write: { path: workshop/05-automated-docs.md, content: "# Automated Documentation (3:15–3:30)\nUse prompts/sdk_readme.pattern.md to create docs/SDK_README.md; add a CHANGELOG bullet.\n" }
  - write: { path: workshop/06-mccraetech-standard.md, content: "# The McCraeTech AI Standard (3:30–3:45)\nReview and commit .cursor/rules/90-mccraetech-ai-standard.mdc. This is your day-to-day guardrail.\n" }
  - write: { path: workshop/quickstart.md, content: "# Quickstart\n1) Open repo in Cursor; add /context to Docs.\n2) Use 'enterprise_code.pattern.md' on your track’s example.\n3) Switch model routes and log results in ops/run-report.md.\n4) Generate SDK_README.md and CHANGELOG.md.\n5) Commit .cursor/rules/90-mccraetech-ai-standard.mdc.\n" }
````