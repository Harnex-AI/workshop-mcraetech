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

## Troubleshooting

### Build Fails with "Cannot resolve symbol"
**Cause:** Maven dependencies not downloaded
**Fix:** Run `mvn clean install` to download all dependencies

### Build Fails with "Unsupported class file major version"
**Cause:** Wrong Java version (requires JDK 17+)
**Fix:**
```bash
# Check your Java version
java -version

# Should show version 17 or higher
# If not, install JDK 17+ and set JAVA_HOME
```

### Tests Fail After Fixing ConsentService
**Expected:** This is normal - the workshop focuses on fixing the service logic, not the tests
**Note:** In a real project, you would update tests to match the new behavior

### Cursor Not Applying Context Files
**Cause:** Context files not added to Cursor Docs
**Fix:**
1. Open Cursor's Docs panel (right sidebar)
2. Click "Add Docs"
3. Select all files in `/context` folder
4. Verify they appear in the Docs panel

### AI Suggestions Don't Follow Standards
**Cause:** Cursor rules may not be active
**Fix:**
1. Check that `.cursor/rules/*.mdc` files exist
2. Verify the `globs` pattern matches your file (e.g., `**/*.java`)
3. Restart Cursor IDE if rules were just added

