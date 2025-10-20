package com.mccrae.healthcare.consent;

/**
 * Exception thrown when consent validation fails
 */
public class ConsentException extends Exception {
    public ConsentException(String message) {
        super(message);
    }
}

