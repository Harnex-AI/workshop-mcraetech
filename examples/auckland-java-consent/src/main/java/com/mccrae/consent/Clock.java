package com.mccrae.consent;

import java.time.Instant;

/**
 * Clock abstraction for testability.
 * Workshop 1 example - allows mocking time in tests.
 */
public interface Clock {
  /**
   * Returns the current instant in UTC.
   * @return current instant
   */
  Instant instant();
}

