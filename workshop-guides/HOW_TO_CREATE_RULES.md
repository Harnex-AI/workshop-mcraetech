# How to Create Domain-Specific Cursor Rules

## Exercise: Create Your Own Rule

This guide walks you through creating a custom Cursor rule for your specific domain needs.

## Step 1: Identify Your Pain Point

Ask yourself:
- What mistake do developers keep making?
- What pattern should be enforced but isn't?
- What security/compliance requirement is often missed?

**Example Pain Points:**
- Forgetting to validate email formats
- Not checking null references before use
- Missing authorization checks on endpoints
- Hardcoding configuration values
- Not disposing database connections

## Step 2: Define the Rule Structure

Every `.mdc` rule file has this structure:

```yaml
---
description: One-line description of what this rule does
globs: ["**/*.ext", "**/path/**"]
---
# Rule Name

## WHEN TO APPLY
- Specific condition 1
- Specific condition 2

## DETECTION PATTERNS
- Pattern to detect 1
- Pattern to detect 2

## AUTOMATIC FIXES
Replace:
```code
bad pattern
```
With:
```code
good pattern
```

## COMPLIANCE NOTES (optional)
- Requirement 1
- Requirement 2
```

## Step 3: Fill in the Template

### 3.1 Choose Your Glob Patterns

Glob patterns determine which files the rule applies to:

```yaml
# Apply to specific file types
globs: ["**/*.cs"]                    # All C# files
globs: ["**/*.java"]                  # All Java files
globs: ["**/*.ts", "**/*.js"]         # TypeScript and JavaScript

# Apply to specific directories
globs: ["**/Controllers/**"]          # Only controller files
globs: ["**/Services/**/*.cs"]        # C# files in Services folder
globs: ["**/api/**", "**/routes/**"]  # Multiple directories

# Combine patterns
globs: ["**/*.cs", "**/*.java", "**/Controllers/**"]
```

### 3.2 Write Detection Patterns

Be specific about what triggers the rule:

```markdown
## DETECTION PATTERNS
When you see these patterns, apply this rule:
- Methods named `Delete*` or `Remove*`
- Variables containing `password`, `secret`, `apiKey`
- HTTP endpoints without `[Authorize]` attribute
- Database queries without parameterization
- File operations without try-catch blocks
```

### 3.3 Provide Automatic Fixes

Show the **before** and **after** code:

````markdown
## AUTOMATIC FIXES

### Fix 1: Add Authorization
Replace:
```csharp
[HttpGet]
public IActionResult GetSensitiveData()
```
With:
```csharp
[HttpGet]
[Authorize(Roles = "Admin")]
public IActionResult GetSensitiveData()
```

### Fix 2: Parameterize Query
Replace:
```java
String sql = "SELECT * FROM users WHERE id = " + userId;
```
With:
```java
String sql = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, userId);
```
````

## Step 4: Real-World Examples

### Example 1: Authorization Check Rule

**Pain Point:** Developers forget to add authorization to sensitive endpoints

```yaml
---
description: Enforce authorization checks on all HTTP endpoints
globs: ["**/Controllers/**/*.cs", "**/controllers/**/*.ts"]
---
# Authorization Enforcement Rule

## WHEN TO APPLY
- Any HTTP endpoint (GET, POST, PUT, DELETE)
- Controllers handling sensitive data
- Admin-only operations

## DETECTION PATTERNS
When you see these patterns, apply this rule:
- HTTP method attributes without `[Authorize]`
- Endpoints with names containing: Admin, Delete, Sensitive, Private
- Methods accessing user data without authorization check

## AUTOMATIC FIXES

### C# Controllers
Replace:
```csharp
[HttpDelete("{id}")]
public async Task<IActionResult> DeleteUser(string id)
{
    await _service.DeleteUser(id);
    return NoContent();
}
```
With:
```csharp
[HttpDelete("{id}")]
[Authorize(Roles = "Admin")]
public async Task<IActionResult> DeleteUser(string id)
{
    await _service.DeleteUser(id);
    return NoContent();
}
```

### TypeScript/Express
Replace:
```typescript
app.delete('/users/:id', async (req, res) => {
    await service.deleteUser(req.params.id);
    res.status(204).send();
});
```
With:
```typescript
app.delete('/users/:id', requireAuth, requireRole('admin'), async (req, res) => {
    await service.deleteUser(req.params.id);
    res.status(204).send();
});
```

## COMPLIANCE NOTES
- OWASP: Broken Access Control (A01:2021)
- Principle of Least Privilege
- All destructive operations require authorization
```

### Example 2: SQL Injection Prevention Rule

**Pain Point:** String concatenation in SQL queries

```yaml
---
description: Prevent SQL injection by enforcing parameterized queries
globs: ["**/*.java", "**/*.cs", "**/*.py"]
---
# SQL Injection Prevention

## WHEN TO APPLY
- Any code executing SQL queries
- Database access layers
- Repository methods

## DETECTION PATTERNS
When you see these patterns, apply this rule:
- String concatenation with SQL keywords (SELECT, INSERT, UPDATE, DELETE)
- Variables in SQL strings without parameterization
- User input directly in SQL queries

## AUTOMATIC FIXES

### Java
Replace:
```java
String sql = "SELECT * FROM users WHERE email = '" + email + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
```
With:
```java
String sql = "SELECT * FROM users WHERE email = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, email);
ResultSet rs = stmt.executeQuery();
```

### C#
Replace:
```csharp
var sql = $"SELECT * FROM users WHERE email = '{email}'";
var result = _context.Database.ExecuteSqlRaw(sql);
```
With:
```csharp
var sql = "SELECT * FROM users WHERE email = {0}";
var result = _context.Database.ExecuteSqlRaw(sql, email);
```

### Python
Replace:
```python
cursor.execute(f"SELECT * FROM users WHERE email = '{email}'")
```
With:
```python
cursor.execute("SELECT * FROM users WHERE email = %s", (email,))
```

## COMPLIANCE NOTES
- OWASP: Injection (A03:2021)
- CWE-89: SQL Injection
- Always use parameterized queries or ORM
```

### Example 3: Configuration Hardcoding Rule

**Pain Point:** Developers hardcode configuration values

```yaml
---
description: Prevent hardcoded configuration values
globs: ["**/*.cs", "**/*.java", "**/*.ts"]
---
# Configuration Hardcoding Prevention

## WHEN TO APPLY
- Connection strings
- API keys and secrets
- Environment-specific URLs
- Feature flags

## DETECTION PATTERNS
When you see these patterns, apply this rule:
- String literals containing: `http://`, `https://`, `Server=`, `Password=`
- Variables named: `apiKey`, `secret`, `connectionString`, `apiUrl`
- Hardcoded port numbers (except 80, 443)

## AUTOMATIC FIXES

### C#
Replace:
```csharp
private const string ApiUrl = "https://api.production.com";
private const string ApiKey = "sk_live_abc123";
```
With:
```csharp
private readonly string _apiUrl;
private readonly string _apiKey;

public MyService(IConfiguration config)
{
    _apiUrl = config["ApiSettings:Url"];
    _apiKey = config["ApiSettings:Key"];
}
```

### Java
Replace:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
private static final String DB_PASSWORD = "password123";
```
With:
```java
@Value("${database.url}")
private String dbUrl;

@Value("${database.password}")
private String dbPassword;
```

### TypeScript
Replace:
```typescript
const API_KEY = 'sk_live_abc123';
const API_URL = 'https://api.production.com';
```
With:
```typescript
const API_KEY = process.env.API_KEY;
const API_URL = process.env.API_URL;

if (!API_KEY || !API_URL) {
    throw new Error('Missing required environment variables');
}
```

## COMPLIANCE NOTES
- 12-Factor App: Store config in environment
- Never commit secrets to version control
- Use secret management systems (Azure Key Vault, AWS Secrets Manager)
```

## Step 5: Test Your Rule

1. **Create the `.mdc` file** in `.cursor/rules/`
2. **Open a test file** that should trigger the rule
3. **Type the "bad" pattern** and see if Cursor suggests the fix
4. **Refine** the rule based on results

### Testing Checklist

- [ ] Rule triggers on the correct file types (globs work)
- [ ] Detection patterns catch the bad code
- [ ] Suggested fixes are correct and complete
- [ ] Rule doesn't trigger false positives
- [ ] Multiple languages supported (if applicable)

## Step 6: Document Your Rule

Add a brief comment at the top explaining:
- Why this rule exists
- What problem it solves
- When to use it

## Exercise Template

Use this template to create your own rule:

```yaml
---
description: [What does this rule enforce?]
globs: ["[Which files does it apply to?]"]
---
# [Rule Name]

## WHEN TO APPLY
- [Condition 1]
- [Condition 2]

## DETECTION PATTERNS
When you see these patterns, apply this rule:
- [Pattern 1]
- [Pattern 2]

## AUTOMATIC FIXES

### [Language 1]
Replace:
```[language]
[bad code]
```
With:
```[language]
[good code]
```

### [Language 2]
Replace:
```[language]
[bad code]
```
With:
```[language]
[good code]
```

## COMPLIANCE NOTES
- [Requirement 1]
- [Requirement 2]
```

## Common Patterns to Enforce

Here are ideas for rules you might create:

### Security
- Input validation on all user inputs
- HTTPS-only URLs
- Secure cookie settings
- CORS configuration
- Rate limiting on public endpoints

### Performance
- Async/await for I/O operations
- Connection pooling
- Caching for expensive operations
- Lazy loading for large collections

### Code Quality
- Null checking before dereferencing
- Disposing IDisposable objects
- Logging exceptions with context
- Meaningful variable names

### Domain-Specific
- Business rule validation
- Domain event publishing
- Aggregate boundary enforcement
- Value object immutability

## Tips for Effective Rules

1. **Be Specific**: Narrow scope = better suggestions
2. **Show Examples**: Multiple before/after examples help
3. **Explain Why**: Include compliance/security notes
4. **Test Thoroughly**: Try edge cases
5. **Iterate**: Refine based on real usage

## Next Steps

1. Identify your top 3 pain points
2. Create rules for each using this guide
3. Test them on real code
4. Share with your team
5. Refine based on feedback

---

**Now create your first rule!** 🚀
