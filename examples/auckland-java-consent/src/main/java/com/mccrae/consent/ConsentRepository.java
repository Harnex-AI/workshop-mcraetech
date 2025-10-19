package com.mccrae.consent;

import java.util.List;

/**
 * Repository interface for Consent entities.
 * Workshop 1 example - stub implementation.
 */
public interface ConsentRepository {
  /**
   * Find all consents for a given patient reference.
   * @param patientRef the patient reference ID
   * @return list of consents (may include expired ones - filtering is service layer responsibility)
   */
  List<Consent> findByPatientRef(String patientRef);
}

