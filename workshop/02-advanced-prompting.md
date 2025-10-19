# Advanced Prompting (2:20–2:40)

## Overview
Learn to use structured prompts with the Instruction → Constraints → Inputs → Steps → Self-Critique pattern.

## Instructor Demo
- **Java Track**: ConsentService.java (expiry filter bug)
- **.NET Track**: LegacyPatientsController.cs (PHI logging issue)

## Hands-On Exercise

### Step 1: Locate Your Track's Example Code
- **Java Track**: Open `examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentService.java`
- **.NET Track**: Open `examples/thailand-dotnet-legacy/Controllers/LegacyPatientsController.cs`

### Step 2: Open the Prompt Pattern
1. Open `prompts/enterprise_code.pattern.md` in Cursor
2. Copy the entire contents

### Step 3: Fill in the Placeholders
Paste into Cursor Chat and replace the placeholders:

**For Java Track (ConsentService.java):**
```
{{FILE_PATHS}}
examples/auckland-java-consent/src/main/java/com/mccrae/consent/ConsentService.java

{{OBSERVED}}
The getActiveConsents method returns all consents including expired ones. Line 20 has a TODO comment indicating the expiry filter is missing.

{{EXPECTED}}
Filter out expired consents by comparing expiresAt with the current time from the Clock service.
```

**For .NET Track (LegacyPatientsController.cs):**
```
{{FILE_PATHS}}
examples/thailand-dotnet-legacy/Controllers/LegacyPatientsController.cs

{{OBSERVED}}
Line 25 logs the entire PatientDto object which contains PHI (Name, DOB, Address). No input validation is performed. Timestamps are not normalized to UTC.

{{EXPECTED}}
Remove PHI from logs, add input validation, and ensure all timestamps are stored in UTC.
```

### Step 4: Review AI Output
Check the AI's response against `prompts/self_critique.rubric.md`:
- ✅ Domain Safety (PHI, Consent, UTC)
- ✅ Minimality (only necessary changes)
- ✅ Readability (clear, maintainable code)
- ✅ Testability (can be unit tested)
- ✅ Standards (follows Java/C# conventions)

### Step 5: Apply and Commit
1. Review the suggested changes
2. Accept the changes in Cursor
3. Commit with message: `fix: [describe the fix based on your track]`

## Key Takeaways
- Structured prompts produce better, more consistent results
- Self-critique rubrics help validate AI output
- Domain-specific constraints (PHI, UTC) must be explicit

