package com.mccrae.consent;

import java.time.Instant;

/**
 * Represents a patient consent record.
 * Workshop 1 example - minimal domain model.
 */
public class Consent {
  private String id;
  private String patientRef;
  private String scope;
  private Instant grantedAt;
  private Instant expiresAt;

  public Consent() {}

  public Consent(String id, String patientRef, String scope, Instant grantedAt, Instant expiresAt) {
    this.id = id;
    this.patientRef = patientRef;
    this.scope = scope;
    this.grantedAt = grantedAt;
    this.expiresAt = expiresAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPatientRef() {
    return patientRef;
  }

  public void setPatientRef(String patientRef) {
    this.patientRef = patientRef;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Instant getGrantedAt() {
    return grantedAt;
  }

  public void setGrantedAt(Instant grantedAt) {
    this.grantedAt = grantedAt;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Instant expiresAt) {
    this.expiresAt = expiresAt;
  }
}

