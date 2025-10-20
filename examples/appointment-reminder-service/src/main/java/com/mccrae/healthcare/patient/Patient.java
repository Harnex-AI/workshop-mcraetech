package com.mccrae.healthcare.patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Patient entity - core domain model
 * 
 * Note: This follows all our rules:
 * - Uses Instant for timestamps (UTC)
 * - Has reference ID for logging (not full patient object)
 * - Validation annotations
 */
@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    private UUID id;
    
    @Column(nullable = false, unique = true)
    @NotBlank
    private String referenceId; // e.g., "PAT_12345" - safe to log
    
    @Column(nullable = false)
    @NotBlank
    private String fullName; // PHI - never log this
    
    @Column(nullable = false)
    @NotNull
    private Instant dateOfBirth; // PHI - never log this
    
    @Column(nullable = false)
    @NotBlank
    private String email; // PHI - never log this
    
    @Column(nullable = false)
    @NotBlank
    private String phone; // PHI - never log this
    
    @Column(nullable = false)
    private String timezone; // e.g., "Pacific/Auckland"
    
    @Column(nullable = false)
    private Instant createdAt; // Always UTC
    
    @Column(nullable = false)
    private Instant updatedAt; // Always UTC
    
    // Constructors
    public Patient() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getReferenceId() {
        return referenceId;
    }
    
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Instant getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getTimezone() {
        return timezone;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

