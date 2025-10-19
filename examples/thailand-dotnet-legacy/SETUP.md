# Setup Instructions

## Expected IDE Errors Before Setup
You may see some errors until NuGet packages are restored. This is normal.

## Run Setup
```bash
# From this directory (examples/thailand-dotnet-legacy)
dotnet restore
dotnet build
```

This will:
1. Download NuGet packages (ASP.NET Core, etc.)
2. Compile the code
3. Resolve any dependency errors in your IDE

## Verify Setup
After running `dotnet restore`, all dependency errors should disappear.

## Expected Warnings (Intentional)
These are part of the workshop exercises:
- PHI logging in LegacyPatientsController - This is the issue you'll fix!
- Missing validation - This is your workshop task!
- No UTC normalization - This is your workshop task!

