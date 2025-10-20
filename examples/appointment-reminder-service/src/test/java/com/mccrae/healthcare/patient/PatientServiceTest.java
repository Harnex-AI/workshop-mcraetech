package com.mccrae.healthcare.patient;

import com.mccrae.healthcare.audit.AuditLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for PatientService
 * 
 * Note: Uses synthetic test data (TEST_PAT_001, not "John Smith")
 */
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    
    @Mock
    private PatientRepository patientRepository;
    
    @Mock
    private AuditLogger auditLogger;
    
    @InjectMocks
    private PatientService patientService;
    
    private Patient testPatient;
    
    @BeforeEach
    void setUp() {
        // ✅ CORRECT: Synthetic test data
        testPatient = new Patient();
        testPatient.setReferenceId("TEST_PAT_001");
        testPatient.setFullName("TEST_PATIENT_001");
        testPatient.setDateOfBirth(Instant.parse("1900-01-01T00:00:00Z"));
        testPatient.setEmail("test.patient.001@example.test");
        testPatient.setPhone("+64-21-555-9999");
        testPatient.setTimezone("Pacific/Auckland");
    }
    
    @Test
    void testCreatePatient() {
        // Given
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        
        // When
        Patient created = patientService.createPatient(testPatient);
        
        // Then
        assertThat(created).isNotNull();
        assertThat(created.getReferenceId()).isEqualTo("TEST_PAT_001");
        verify(patientRepository).save(testPatient);
        verify(auditLogger).logPatientCreated("TEST_PAT_001");
    }
    
    @Test
    void testGetPatientByReferenceId() {
        // Given
        when(patientRepository.findByReferenceId("TEST_PAT_001"))
                .thenReturn(Optional.of(testPatient));
        
        // When
        Optional<Patient> found = patientService.getPatientByReferenceId("TEST_PAT_001");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getReferenceId()).isEqualTo("TEST_PAT_001");
        verify(auditLogger).logPatientAccessed("TEST_PAT_001");
    }
    
    @Test
    void testGetPatientByReferenceId_NotFound() {
        // Given
        when(patientRepository.findByReferenceId("NONEXISTENT"))
                .thenReturn(Optional.empty());
        
        // When
        Optional<Patient> found = patientService.getPatientByReferenceId("NONEXISTENT");
        
        // Then
        assertThat(found).isEmpty();
        verify(auditLogger, never()).logPatientAccessed(any());
    }
    
    @Test
    void testUpdatePatient() {
        // Given
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        
        // When
        Patient updated = patientService.updatePatient(testPatient);
        
        // Then
        assertThat(updated).isNotNull();
        verify(patientRepository).save(testPatient);
        verify(auditLogger).logPatientUpdated("TEST_PAT_001");
    }
    
    @Test
    void testDeletePatient() {
        // Given
        UUID patientId = UUID.randomUUID();
        testPatient.setId(patientId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        
        // When
        patientService.deletePatient(patientId);
        
        // Then
        verify(patientRepository).deleteById(patientId);
        verify(auditLogger).logPatientDeleted("TEST_PAT_001");
    }
}

