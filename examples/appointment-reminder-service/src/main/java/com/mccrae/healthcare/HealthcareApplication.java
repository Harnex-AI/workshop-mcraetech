package com.mccrae.healthcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Healthcare System - Base Infrastructure
 * 
 * This is a working healthcare system with:
 * - Patient management
 * - Consent validation
 * - Audit logging
 * 
 * The demo will show how to ADD features to this existing system
 * using context engineering.
 */
@SpringBootApplication
public class HealthcareApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthcareApplication.class, args);
    }
}

