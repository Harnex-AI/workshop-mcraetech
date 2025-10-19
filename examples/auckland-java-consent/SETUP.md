# Setup Instructions

## Expected IDE Errors Before Setup
You will see Spring Boot import errors until Maven downloads dependencies. This is normal.

## Run Setup
```bash
# From this directory (examples/auckland-java-consent)
mvn clean install
```

This will:
1. Download Spring Boot dependencies
2. Compile the code
3. Resolve all import errors in your IDE

## Verify Setup
After running Maven, the following errors should disappear:
- "The import org.springframework cannot be resolved"
- "RestController cannot be resolved to a type"
- "RequestMapping cannot be resolved to a type"
- etc.

## Expected Warnings (Intentional)
These are part of the workshop exercises:
- "The value of the local variable now is not used" - This is the bug you'll fix!
- "TODO: filter expired consents" - This is your workshop task!

