package com.mccrae.consent;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ConsentService {
  private final ConsentRepository repo;
  private final Clock clock;

  public ConsentService(ConsentRepository repo, Clock clock) {
    this.repo = repo;
    this.clock = clock;
  }

  // BUG FOR WORKSHOP: expired consents are not filtered
  public List<Consent> findActiveByPatient(String patientRef) {
    Instant now = clock.instant();
    return repo.findByPatientRef(patientRef).stream()
      // TODO: filter expired consents: c.getExpiresAt().isAfter(now)
      .collect(Collectors.toList());
  }
}

