package com.mccrae.healthcare.consent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Consent entity - tracks patient consent for various operations
 * 
 * Scopes:
 * - PATIENT_VIEW: View basic patient information
 * - EMERGENCY_CONTACT_NOTIFY: Notify emergency contacts
 * - EMERGENCY_CONTACT_NOTIFY_DETAILED: Share detailed info with emergency contacts
 * - APPOINTMENT_REMINDER: Send appointment reminders
 * - MEDICATION_REMINDER: Send medication reminders
 */
@Entity
@Table(name = "consents")
public class Consent {
    
    @Id
    private UUID id;
    
    @Column(nullable = false)
    @NotBlank
    private String patientRef; // Reference to patient
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "consent_scopes", joinColumns = @JoinColumn(name = "consent_id"))
    @Column(name = "scope")
    private Set<String> scopes = new HashSet<>();
    
    @Column(nullable = false)
    @NotNull
    private Instant grantedAt; // When consent was granted (UTC)
    
    @Column(nullable = false)
    @NotNull
    private Instant expiresAt; // When consent expires (UTC)
    
    @Column(nullable = false)
    private Instant createdAt; // Always UTC
    
    @Column(nullable = false)
    private Instant updatedAt; // Always UTC
    
    // Constructors
    public Consent() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Business logic
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
    
    public boolean hasScope(String scope) {
        return scopes.contains(scope);
    }
    
    public boolean isValid() {
        return !isExpired();
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getPatientRef() {
        return patientRef;
    }
    
    public void setPatientRef(String patientRef) {
        this.patientRef = patientRef;
    }
    
    public Set<String> getScopes() {
        return scopes;
    }
    
    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
    
    public void addScope(String scope) {
        this.scopes.add(scope);
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
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}

