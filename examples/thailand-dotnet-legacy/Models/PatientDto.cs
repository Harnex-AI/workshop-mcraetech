namespace ThailandLegacy;

/// <summary>
/// Patient data transfer object.
/// Workshop 1 example - intentionally has PHI logging issues.
/// </summary>
public class PatientDto
{
    public string Name { get; set; } = string.Empty;
    public DateTime Dob { get; set; }
    public string? Address { get; set; }
}

