using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace ThailandLegacy.Controllers;

[ApiController]
[Route("legacy/patients")]
public class LegacyPatientsController : ControllerBase
{
    private readonly ILogger<LegacyPatientsController> _logger;
    private readonly LegacyRepo _repo;

    public LegacyPatientsController(ILogger<LegacyPatientsController> logger, LegacyRepo repo)
    {
        _logger = logger;
        _repo = repo;
    }

    // ISSUES FOR WORKSHOP:
    // - Logs full DTO (PHI risk)
    // - No validation or UTC normalization
    [HttpPost]
    public IActionResult Create([FromBody] PatientDto dto)
    {
        _logger.LogInformation("Creating patient {@dto}", dto); // TODO: avoid PHI logging
        var id = _repo.Save(dto.Name, dto.Dob, dto.Address);     // TODO: validate + UTC
        return Ok(new { id });
    }
}

