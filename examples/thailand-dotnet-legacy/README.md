# Thailand: Legacy Patients (Workshop 1)
Practice minimal-change PHI-safe logging and UTC normalization using patterns + context + model routing.

## Setup
```bash
dotnet restore
dotnet build
```

## Known Issues (Intentional for Workshop)
1. **LegacyPatientsController.cs**: Logs full DTO with PHI (see TODO comment)
2. No input validation
3. No UTC normalization for DateTime fields

## Workshop Tasks
1. Fix PHI logging using `prompts/enterprise_code.pattern.md`
2. Add input validation
3. Ensure DateTime is stored/returned as UTC
4. Generate SDK_README.md using the docs pattern
5. Log your model routing decisions in `/ops/run-report.md`

