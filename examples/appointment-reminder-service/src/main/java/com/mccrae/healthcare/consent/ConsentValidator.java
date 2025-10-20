package com.mccrae.healthcare.consent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/**
 * Consent Validator - validates consent before operations
 * 
 * This is a core service that other features will use.
 * Example: Emergency Contact notifications will use this to validate consent.
 */
@Service
public class ConsentValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsentValidator.class);
    
    @Autowired
    private ConsentRepository consentRepository;
    
    /**
     * Validate that patient has active consent for the given scope
     * 
     * @param patientRef Patient reference ID
     * @param requiredScope Required consent scope
     * @return Consent if valid, empty if not found or expired
     */
    public Optional<Consent> validateConsent(String patientRef, String requiredScope) {
        // ✅ CORRECT: Log reference ID and scope, not patient details
        logger.info("Validating consent for patient {} with scope {}", patientRef, requiredScope);
        
        Optional<Consent> consent = consentRepository.findActiveConsentByPatientRefAndScope(
                patientRef,
                requiredScope,
                Instant.now()
        );
        
        if (consent.isEmpty()) {
            // ✅ CORRECT: Generic log message
            logger.warn("No active consent found for patient {} with scope {}", patientRef, requiredScope);
        }
        
        return consent;
    }
    
    /**
     * Check if patient has active consent for the given scope
     * 
     * @param patientRef Patient reference ID
     * @param requiredScope Required consent scope
     * @return true if consent is valid, false otherwise
     */
    public boolean hasValidConsent(String patientRef, String requiredScope) {
        return validateConsent(patientRef, requiredScope).isPresent();
    }
    
    /**
     * Require consent - throws exception if not valid
     * 
     * @param patientRef Patient reference ID
     * @param requiredScope Required consent scope
     * @return Valid consent
     * @throws ConsentException if consent is not valid
     */
    public Consent requireConsent(String patientRef, String requiredScope) throws ConsentException {
        return validateConsent(patientRef, requiredScope)
                .orElseThrow(() -> new ConsentException(
                        "Patient " + patientRef + " does not have valid consent for scope: " + requiredScope
                ));
    }
}

