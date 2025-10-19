package com.mccrae.consent;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consents")
public class ConsentController {
  private final ConsentService service;
  public ConsentController(ConsentService service) { this.service = service; }

  // NOTE: for Workshop 1 we only demo assisted fixes and docs (no spec→code)
  @GetMapping("/active")
  public List<Consent> active(@RequestHeader("X-Consent-Scope") String scope,
                              @RequestParam String patientRef) {
    // For the demo, assume EHR_VIEW is required—document the assumption in README Limitations.
    return service.findActiveByPatient(patientRef);
  }
}

