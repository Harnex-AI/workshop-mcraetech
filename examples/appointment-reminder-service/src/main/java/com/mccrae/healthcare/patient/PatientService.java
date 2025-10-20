package com.mccrae.healthcare.patient;

import com.mccrae.healthcare.audit.AuditLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Patient Service - demonstrates proper PHI handling
 * 
 * Note how we:
 * - Log reference IDs, not patient names
 * - Use audit logger for PHI access
 * - Return Optional for not found cases
 */
@Service
public class PatientService {
    
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private AuditLogger auditLogger;
    
    public Patient createPatient(Patient patient) {
        // ✅ CORRECT: Log reference ID, not patient name
        logger.info("Creating patient {}", patient.getReferenceId());
        
        Patient saved = patientRepository.save(patient);
        
        // Audit log the creation
        auditLogger.logPatientCreated(saved.getReferenceId());
        
        return saved;
    }
    
    public Optional<Patient> getPatientByReferenceId(String referenceId) {
        // ✅ CORRECT: Log reference ID
        logger.info("Retrieving patient {}", referenceId);
        
        Optional<Patient> patient = patientRepository.findByReferenceId(referenceId);
        
        if (patient.isPresent()) {
            // Audit log the access
            auditLogger.logPatientAccessed(referenceId);
        }
        
        return patient;
    }
    
    public List<Patient> getAllPatients() {
        // ✅ CORRECT: Generic log message, no PHI
        logger.info("Retrieving all patients");
        return patientRepository.findAll();
    }
    
    public Patient updatePatient(Patient patient) {
        // ✅ CORRECT: Log reference ID
        logger.info("Updating patient {}", patient.getReferenceId());
        
        Patient updated = patientRepository.save(patient);
        
        // Audit log the update
        auditLogger.logPatientUpdated(updated.getReferenceId());
        
        return updated;
    }
    
    public void deletePatient(UUID id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            // ✅ CORRECT: Log reference ID
            logger.info("Deleting patient {}", patient.get().getReferenceId());
            
            // Audit log the deletion
            auditLogger.logPatientDeleted(patient.get().getReferenceId());
            
            patientRepository.deleteById(id);
        }
    }
}

