package com.mccrae.healthcare.consent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests for ConsentValidator
 */
@ExtendWith(MockitoExtension.class)
class ConsentValidatorTest {
    
    @Mock
    private ConsentRepository consentRepository;
    
    @InjectMocks
    private ConsentValidator consentValidator;
    
    private Consent validConsent;
    
    @BeforeEach
    void setUp() {
        validConsent = new Consent();
        validConsent.setPatientRef("TEST_PAT_001");
        validConsent.setScopes(Set.of("EMERGENCY_CONTACT_NOTIFY"));
        validConsent.setGrantedAt(Instant.now().minus(1, ChronoUnit.DAYS));
        validConsent.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));
    }
    
    @Test
    void testValidateConsent_Valid() {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("EMERGENCY_CONTACT_NOTIFY"),
                any(Instant.class)
        )).thenReturn(Optional.of(validConsent));
        
        // When
        Optional<Consent> result = consentValidator.validateConsent(
                "TEST_PAT_001",
                "EMERGENCY_CONTACT_NOTIFY"
        );
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getPatientRef()).isEqualTo("TEST_PAT_001");
        assertThat(result.get().hasScope("EMERGENCY_CONTACT_NOTIFY")).isTrue();
    }
    
    @Test
    void testValidateConsent_NotFound() {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("NONEXISTENT_SCOPE"),
                any(Instant.class)
        )).thenReturn(Optional.empty());
        
        // When
        Optional<Consent> result = consentValidator.validateConsent(
                "TEST_PAT_001",
                "NONEXISTENT_SCOPE"
        );
        
        // Then
        assertThat(result).isEmpty();
    }
    
    @Test
    void testHasValidConsent_True() {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("EMERGENCY_CONTACT_NOTIFY"),
                any(Instant.class)
        )).thenReturn(Optional.of(validConsent));
        
        // When
        boolean hasConsent = consentValidator.hasValidConsent(
                "TEST_PAT_001",
                "EMERGENCY_CONTACT_NOTIFY"
        );
        
        // Then
        assertThat(hasConsent).isTrue();
    }
    
    @Test
    void testHasValidConsent_False() {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("NONEXISTENT_SCOPE"),
                any(Instant.class)
        )).thenReturn(Optional.empty());
        
        // When
        boolean hasConsent = consentValidator.hasValidConsent(
                "TEST_PAT_001",
                "NONEXISTENT_SCOPE"
        );
        
        // Then
        assertThat(hasConsent).isFalse();
    }
    
    @Test
    void testRequireConsent_Valid() throws ConsentException {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("EMERGENCY_CONTACT_NOTIFY"),
                any(Instant.class)
        )).thenReturn(Optional.of(validConsent));
        
        // When
        Consent result = consentValidator.requireConsent(
                "TEST_PAT_001",
                "EMERGENCY_CONTACT_NOTIFY"
        );
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPatientRef()).isEqualTo("TEST_PAT_001");
    }
    
    @Test
    void testRequireConsent_ThrowsException() {
        // Given
        when(consentRepository.findActiveConsentByPatientRefAndScope(
                eq("TEST_PAT_001"),
                eq("NONEXISTENT_SCOPE"),
                any(Instant.class)
        )).thenReturn(Optional.empty());
        
        // When/Then
        assertThatThrownBy(() -> consentValidator.requireConsent(
                "TEST_PAT_001",
                "NONEXISTENT_SCOPE"
        ))
        .isInstanceOf(ConsentException.class)
        .hasMessageContaining("does not have valid consent");
    }
}

