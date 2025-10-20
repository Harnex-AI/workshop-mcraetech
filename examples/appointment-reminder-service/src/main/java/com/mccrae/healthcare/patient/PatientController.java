package com.mccrae.healthcare.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Patient REST API
 * 
 * Endpoints:
 * - POST   /api/patients          - Create patient
 * - GET    /api/patients/{ref}    - Get patient by reference ID
 * - GET    /api/patients          - Get all patients
 * - PUT    /api/patients/{id}     - Update patient
 * - DELETE /api/patients/{id}     - Delete patient
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient created = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{referenceId}")
    public ResponseEntity<Patient> getPatient(@PathVariable String referenceId) {
        return patientService.getPatientByReferenceId(referenceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable UUID id,
            @Valid @RequestBody Patient patient) {
        patient.setId(id);
        Patient updated = patientService.updatePatient(patient);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}

