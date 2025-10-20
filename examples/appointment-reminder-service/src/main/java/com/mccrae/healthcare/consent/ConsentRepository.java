package com.mccrae.healthcare.consent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, UUID> {
    
    List<Consent> findByPatientRef(String patientRef);
    
    @Query("SELECT c FROM Consent c WHERE c.patientRef = :patientRef AND c.expiresAt > :now")
    List<Consent> findActiveConsentsByPatientRef(
            @Param("patientRef") String patientRef,
            @Param("now") Instant now
    );
    
    @Query("SELECT c FROM Consent c WHERE c.patientRef = :patientRef AND :scope MEMBER OF c.scopes AND c.expiresAt > :now")
    Optional<Consent> findActiveConsentByPatientRefAndScope(
            @Param("patientRef") String patientRef,
            @Param("scope") String scope,
            @Param("now") Instant now
    );
}

