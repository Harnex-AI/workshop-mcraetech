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

## Troubleshooting

### Build Fails with "Program does not contain a static 'Main' method"
**Cause:** Missing Program.cs file
**Fix:** Ensure `Program.cs` exists in the project root with ASP.NET Core minimal hosting setup
**Note:** This should already be present in the workshop materials

### Build Fails with "The type or namespace name could not be found"
**Cause:** NuGet packages not restored
**Fix:**
```bash
dotnet restore
dotnet build
```

### Build Fails with "The current .NET SDK does not support targeting .NET 8.0"
**Cause:** Wrong .NET SDK version (requires .NET 8.0+)
**Fix:**
```bash
# Check your .NET version
dotnet --version

# Should show 8.0.x or higher
# If not, download .NET 8.0 SDK from https://dotnet.microsoft.com/download
```

### Swagger UI Not Showing Endpoints
**Cause:** Controllers not registered or MapControllers() missing
**Fix:** Verify `Program.cs` contains:
```csharp
builder.Services.AddControllers();
// ...
app.MapControllers();
```

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
2. Verify the `globs` pattern matches your file (e.g., `**/*.cs`)
3. Restart Cursor IDE if rules were just added

### DateTime Not Converting to UTC
**Expected Behavior:** All DateTime values should use `DateTime.UtcNow` and `.ToUniversalTime()`
**Check:** Review the `context/standards.md` file for UTC handling requirements

