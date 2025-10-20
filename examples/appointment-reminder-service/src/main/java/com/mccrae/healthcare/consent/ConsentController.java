package com.mccrae.healthcare.consent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Consent REST API
 * 
 * Endpoints:
 * - POST   /api/consents                    - Create consent
 * - GET    /api/consents/patient/{ref}      - Get consents for patient
 * - GET    /api/consents/patient/{ref}/active - Get active consents for patient
 * - DELETE /api/consents/{id}               - Revoke consent
 */
@RestController
@RequestMapping("/api/consents")
public class ConsentController {
    
    @Autowired
    private ConsentRepository consentRepository;
    
    @PostMapping
    public ResponseEntity<Consent> createConsent(@Valid @RequestBody Consent consent) {
        Consent created = consentRepository.save(consent);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/patient/{patientRef}")
    public ResponseEntity<List<Consent>> getConsentsForPatient(@PathVariable String patientRef) {
        List<Consent> consents = consentRepository.findByPatientRef(patientRef);
        return ResponseEntity.ok(consents);
    }
    
    @GetMapping("/patient/{patientRef}/active")
    public ResponseEntity<List<Consent>> getActiveConsentsForPatient(@PathVariable String patientRef) {
        List<Consent> consents = consentRepository.findActiveConsentsByPatientRef(
                patientRef,
                Instant.now()
        );
        return ResponseEntity.ok(consents);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revokeConsent(@PathVariable UUID id) {
        consentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

