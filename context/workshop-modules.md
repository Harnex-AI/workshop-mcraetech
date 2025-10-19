## ✅ Stage 3 — Synthesize Workshop Focus Areas

Below is the focused bridge from **what the teams need** → **what we’ll teach** → **how they’ll practice** → **how complexity ramps from AI‑assisted to agentic**. This builds directly on the Stage 1 interview synthesis and the Stage 2 convergence + thought‑flow analyses.    

---

### A. Core Problems (evidence‑based)

1. **No standardized AI practices** across teams → results vary, confidence uneven. 
2. **Spec → code accuracy gaps**, especially on complex microservices. 
3. **Severe time pressure** (Thailand: 12 devs maintaining 15–20M LOC) → need quick wins.  
4. **Strict coding standards** must be preserved (Thailand) + Java integration pain (Auckland). 
5. **Context engineering is ad‑hoc** (few templates; agent files exist but not systematized).  
6. **Different readiness**: Auckland interested in **multi‑agent orchestration**; Thailand needs **automation triage** first. 
7. **Model selection isn’t deliberate** (cost/quality trade‑offs unclear). 
8. **Research mode integration** (Perplexity/ChatGPT Pro) wanted for design/verification (Auckland). 
9. **Healthcare context & compliance** require domain‑fit artifacts (docs, tests, auditability). 

---

### B. Focused Learning Modules → Hands‑on Exercises

> **How to read this map:** Each module names the **problem(s) solved**, the **skills** we teach, the **exercise** they’ll do, and the **artifact** they keep. Track weighting guides time/effort emphasis for **Auckland (AKL)** vs **Thailand (THA)**.

| ID | Module (Goal)                                              | Solves | Key Skills                                                                 | Hands‑on Exercise                                                                                                                  | Artifact Produced                                               | Track Weight          |
| -- | ---------------------------------------------------------- | ------ | -------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------- | --------------------- |
| M1 | **Standardized Prompt Patterns** (make results consistent) | 1      | Reusable prompt frames; instruction vs. examples; critique→revise loop     | Convert an unstructured request into a 3‑part prompt (instruction, constraints, examples) for a bug fix + run a self‑critique pass | `prompts/bug_fix.pattern.md` + “critique rubric”                | AKL: ●●○ / THA: ●●●   |
| M2 | **Spec‑Driven Dev** (raise spec→code accuracy)             | 2,4    | Turning **OpenAPI v3** specs into scaffolds; spec linting; ambiguity hunts | Given an OpenAPI v3 spec, generate server/client stubs, enumerate ambiguities, propose spec diffs, regenerate                      | `specs/<svc>/openapi.yaml` (cleaned), scaffolds, `spec-diff.md` | AKL: ●●● / THA: ●●●   |
| M3 | **Context Engineering Basics** (reduce hallucinations)     | 5      | Repo context templates; **Agent.md** (TODO→DONE); retrieval boundaries     | Add `/context/` folder + `Agent.md` for one service; wire the file into your IDE agent; rerun M2 with context                      | `/context/Agent.md`, `/context/standards.md`                    | AKL: ●●● / THA: ●●○   |
| M4 | **Model–Task Fit & Cost Control** (be intentional)         | 7      | Small/Medium/Large model routing; cost guards; “verify with bigger”        | Implement a tiny router: lint with small, generate with medium, verify with large; compare quality/cost                            | `ops/model-routing.json`, `run-report.md`                       | AKL: ●●○ / THA: ●●●   |
| M5 | **Shift‑Left Verification** (trust but verify)             | 2,4,9  | Checklists, static checks, “AI reviewer” diffs; ReAct‑style verify passes  | Write a **verification prompt** that red‑teams the code from M2, reporting contract, security, and style violations                | `verification/checklist.md`, `ai-review.md`                     | AKL: ●●● / THA: ●●○   |
| M6 | **Test Generation at Scale** (time wins)                   | 3,4,9  | Unit/integration test prompts; coverage targeting; flaky‑test triage       | Auto‑generate tests for a legacy module; add coverage check; fix 2 flakies with AI suggestions                                     | `tests/auto/*.spec`, `coverage-diff.md`                         | AKL: ●●○ / THA: ●●●   |
| M7 | **Documentation Automation** (audit & handover)            | 3,5,9  | SDK docs, changelogs, API examples; “living docs” from `Agent.md`          | Generate an **SDK README** + **changelog** from the spec + diffs; link to standards                                                | `/docs/SDK_README.md`, `/docs/CHANGELOG.md`                     | AKL: ●●○ / THA: ●●●   |
| M8 | **Two‑Agent Orchestration** (first step to agentic)        | 6,5,2  | Planner ↔ Verifier handoff; ReAct chains; tool calls                       | Wire a **Planner agent** (decompose) feeding a **Verifier agent** (gate) on the same task from M2                                  | `/agents/pipeline.yaml`, `runbook-orchestration.md`             | AKL: ●●● / THA: ●○○   |
| M9 | **Research Mode Integration** (design & due diligence)     | 8,5,9  | Research prompts, source triage, decision logs                             | Use research mode to evaluate two library options; produce a decision record the Verifier can check                                | `/decisions/ADR-XXX.md`, `sources.csv`                          | AKL: ●●● / THA: ●○○   |

> **Why the artifacts matter:** they create a **repeatable, auditable** trail aligned to McCrae Tech’s healthcare mission (traceable changes, safety, compliance). 

---

### C. Progression of Complexity (AI‑assisted → Agentic)

We’ll teach the modules so teams **climb** from simple, reliable wins to orchestrated autonomy:

1. **Level 0 – Baseline Standards:** M1 (prompt patterns) → the shared language that stops variance. 
2. **Level 1 – Assisted & Reliable:** M2 (spec‑driven) + M3 (context) + M4 (model routing) → accurate scaffolds with predictable cost.  
3. **Level 2 – Self‑Checking:** M5 (shift‑left verify) + M6 (tests) + M7 (docs) → production‑ready outputs, audit‑safe.   
4. **Level 3 – Agentic Orchestration:** M8 (Planner↔Verifier) + M9 (research) → the first durable multi‑agent loop, setting up Stage 5’s advanced agentic workshop.  

---

### D. Track Emphasis (apply the same modules with different weight)

* **Auckland (Advanced)**: Prioritize **M2 → M3 → M5 → M8 → M9**, keep M1/M4 short refreshers. Outcome target: **Multi‑agent pipeline in production** + standard practice adoption.  
* **Thailand (Pragmatic)**: Prioritize **M1 → M4 → M2 → M6 → M7**, introduce M3 lightly, preview M8 only. Outcome target: **+30% test automation, −50% doc time, 1 hr/day saved/dev**.  

---

### E. How this maps back to your Stage 2 direction

* **“Go Smart, then Go Hard”**: M1–M4 deliver immediate stability/ROI; M5–M9 escalate to agentic without overwhelming. 
* **Four Agent Modes honored**: Planning (M8, M9), Verification (M5), Execution (M2/M4), Testing (M6). 
* **Model selection & context as backbone**: M3 + M4 operationalize your small/medium/large strategy and context accumulation (Agent.md, repo templates). 
* **Research a first‑class citizen**: M9 builds the habit of decision logs & sources to feed Verifier gates. 

---

### F. Success criteria per module (short list)

* **M1**: 90% of prompts use the shared pattern in code reviews (THA focus). 
* **M2**: Spec→scaffold diffs drop by ≥30% in first week. 
* **M3**: Repo contains `/context` with Agent.md + standards referenced in 2 services. 
* **M4**: Cost per successful generation down ≥20% while quality holds (router in place). 
* **M5**: AI‑review checklists enforced in PR template. 
* **M6**: +30% automated coverage (THA), flakies reduced on the targeted module. 
* **M7**: SDK README + CHANGELOG auto‑generated in CI for one service.  
* **M8**: Planner↔Verifier YAML runs end‑to‑end on one backlog item (AKL). 
* **M9**: One ADR produced from research mode and verified by M5. 

---

## What you get out of Stage 3

* A **clear module map** that addresses each team’s top problems with **targeted exercises** and **kept artifacts**.
* A **stepwise complexity path** that upgrades teams from **assisted** to **agentic** without overload.
* Concrete **track weightings** to tune delivery for Auckland and Thailand.
