# Auckland: Consent Service (Workshop 1)
Use this minimal service to practice advanced prompting, context grounding, model selection, and docs generation.

## Setup
```bash
mvn clean install
```

## Known Issues (Intentional for Workshop)
1. **ConsentService.java**: Expired consents are not filtered (see TODO comment)
2. Missing scope validation in controller

## Workshop Tasks
1. Fix the consent expiry bug using `prompts/enterprise_code.pattern.md`
2. Add scope validation (EHR_VIEW required)
3. Generate SDK_README.md using the docs pattern
4. Log your model routing decisions in `/ops/run-report.md`

