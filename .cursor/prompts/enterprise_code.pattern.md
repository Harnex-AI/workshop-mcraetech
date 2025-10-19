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

