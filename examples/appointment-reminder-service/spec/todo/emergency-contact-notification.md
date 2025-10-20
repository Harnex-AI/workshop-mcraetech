# Emergency Contact Notification Feature

## Overview
Build a service to notify emergency contacts when patients are admitted or experience critical health events.

## Requirements
See `/context/emergency-contact-requirements.md` for full business requirements.

## Design

### Entities

#### EmergencyContact
- id (UUID)
- patientRef (String)
- name (String)
- relationship (Enum: SPOUSE, PARENT, CHILD, SIBLING, FRIEND, OTHER)
- primaryPhone (String)
- secondaryPhone (String, optional)
- email (String, optional)
- preferredMethod (Enum: SMS, EMAIL, PHONE)
- timezone (String, e.g., "Pacific/Auckland")
- language (String, default "en")
- createdAt (Instant, UTC)
- updatedAt (Instant, UTC)

#### NotificationEvent
- id (UUID)
- patientRef (String)
- emergencyContactId (UUID)
- notificationType (Enum: ADMISSION, STATUS_CHANGE, DISCHARGE, CRITICAL)
- severity (Enum: URGENT, NORMAL, INFO)
- triggeredAt (Instant, UTC)
- triggeredBy (String, user ID or system name)
- correlationId (String)

#### NotificationContent
- patientName (String, nullable - depends on consent)
- facilityName (String)
- facilityPhone (String)
- generalStatus (String, nullable)
- admissionTime (Instant, nullable)
- admissionReason (String, nullable - requires detailed consent)
- department (String, nullable)
- expectedStay (String, nullable)
- visitingHours (String)
- message (String)

### Services

#### EmergencyContactService
- createContact(patientRef, contactDto, consent) → EmergencyContact
- updateContact(contactId, contactDto, consent) → EmergencyContact
- deleteContact(contactId, consent) → void
- getContactsByPatient(patientRef, consent) → List<EmergencyContact>

#### NotificationService
- sendNotification(patientRef, notificationType, severity, consent) → NotificationResult
- buildNotificationContent(patient, contact, event, consent) → NotificationContent
- deliverNotification(contact, content, deliveryMethod) → DeliveryResult
- retryFailedNotifications() → void

#### ConsentValidator
- validateConsent(patientRef, consentScope) → ConsentContext
- hasScope(consent, requiredScope) → boolean
- isExpired(consent) → boolean

#### AuditLogger
- logNotificationTriggered(patientRef, contactId, type, triggeredBy, correlationId) → void
- logConsentValidation(patientRef, scope, valid, expiryDate, correlationId) → void
- logPhiShared(patientRef, contactId, phiFields, scope, correlationId) → void
- logNotificationSent(patientRef, contactId, method, status, retryAttempt, correlationId) → void
- logDeliveryFailure(patientRef, contactId, method, error, retryAttempt, correlationId) → void

### Architecture

```
Controller Layer
  ↓
Service Layer (NotificationService)
  ↓
├─→ ConsentValidator (validate consent)
├─→ NotificationContentBuilder (build content based on consent)
├─→ DeliveryService (send via SMS/Email/Phone)
└─→ AuditLogger (log all actions)
```

## Tasks

### Phase 1: Core Entities & Repository
- [ ] Create EmergencyContact entity
  - Follow UTC temporal rules
  - Use synthetic test data
  - Add validation annotations
- [ ] Create EmergencyContactRepository
  - findByPatientRef(String patientRef)
  - findByPatientRefAndId(String patientRef, UUID id)
- [ ] Create NotificationEvent entity
  - Follow UTC temporal rules
  - Add correlation ID for tracing
- [ ] Create NotificationAuditLog entity
  - Immutable (no updates/deletes)
  - All required fields from audit-log-spec.md

### Phase 2: Consent Validation
- [ ] Create ConsentValidator service
  - validateConsent(patientRef, scope)
  - Check expiry
  - Check scope
  - Throw ConsentException if invalid
- [ ] Create ConsentContext value object
  - consentId
  - patientRef
  - scopes (List<String>)
  - expiryDate
  - isExpired() method
  - hasScope(String scope) method

### Phase 3: Notification Content Builder
- [ ] Create NotificationContentBuilder service
  - buildContent(patient, contact, event, consent)
  - Apply PHI filtering based on consent scope
  - Handle no consent scenario (minimal notification)
  - Handle standard consent (basic PHI)
  - Handle detailed consent (additional PHI)
  - Convert times to contact's timezone
- [ ] Follow phi-sharing-matrix.md exactly
  - No PHI if no consent
  - Basic PHI for EMERGENCY_CONTACT_NOTIFY
  - Additional PHI for EMERGENCY_CONTACT_NOTIFY_DETAILED

### Phase 4: Delivery Service
- [ ] Create DeliveryService interface
  - send(contact, content) → DeliveryResult
- [ ] Create SmsDeliveryService implementation
  - Format message for SMS (160 char limit)
  - Handle international phone numbers
  - Retry logic (once after 5 min)
- [ ] Create EmailDeliveryService implementation
  - Format message as HTML email
  - Include facility contact info
  - Retry logic (twice: 5 min, 30 min)
- [ ] Create DeliveryResult value object
  - status (SENT, FAILED, RETRYING)
  - errorMessage (sanitized, no PHI)
  - timestamp

### Phase 5: Audit Logging
- [ ] Create AuditLogger service
  - Log all notification events
  - Log consent validation
  - Log PHI shared (list of fields)
  - Log delivery attempts
  - Log failures (sanitize errors)
- [ ] Ensure synchronous logging (cannot be lost)
- [ ] Sanitize error messages (no PHI)
- [ ] Use correlation IDs for tracing

### Phase 6: Main Notification Service
- [ ] Create NotificationService
  - sendNotification(patientRef, type, severity, consent)
  - Orchestrate: validate → build → deliver → audit
  - Handle errors gracefully
  - Return NotificationResult
- [ ] Add retry mechanism for failed deliveries
  - Query audit log for failed notifications
  - Retry up to 3 times total
  - Exponential backoff (5 min, 30 min, 2 hours)

### Phase 7: REST API
- [ ] Create EmergencyContactController
  - POST /api/patients/{patientRef}/emergency-contacts
  - GET /api/patients/{patientRef}/emergency-contacts
  - PUT /api/patients/{patientRef}/emergency-contacts/{id}
  - DELETE /api/patients/{patientRef}/emergency-contacts/{id}
  - Require X-Consent-Scope header
  - Validate consent before operations
- [ ] Create NotificationController
  - POST /api/notifications/emergency-contact
  - GET /api/patients/{patientRef}/notifications (audit view)
  - Require X-Consent-Scope header

### Phase 8: Testing
- [ ] Unit tests for ConsentValidator
  - Valid consent
  - Expired consent
  - Missing scope
  - Null consent
- [ ] Unit tests for NotificationContentBuilder
  - No consent → minimal notification
  - Standard consent → basic PHI
  - Detailed consent → additional PHI
  - Expired consent → minimal notification
  - Timezone conversion
- [ ] Unit tests for DeliveryService
  - Successful delivery
  - Failed delivery
  - Retry logic
- [ ] Unit tests for AuditLogger
  - All events logged
  - Error sanitization
  - No PHI in logs
- [ ] Integration tests
  - End-to-end notification flow
  - Consent validation integration
  - Audit log creation
  - Delivery retry mechanism

### Phase 9: Documentation
- [ ] Generate API documentation
  - Endpoint descriptions
  - Request/response examples
  - Consent requirements
  - Error codes
- [ ] Create SDK_README.md
  - How to use the API
  - Code examples
  - Consent scope requirements
  - Timezone handling

## Constraints

### Security & Compliance
- Follow `.cursor/rules/security-phi-protection.mdc`
  - No PHI in logs
  - No PHI in error messages
  - Use synthetic test data
- Follow `.cursor/rules/consent-validation.mdc`
  - Validate consent before all operations
  - Check expiry
  - Check scope
- Follow `.cursor/rules/temporal-utc-standard.mdc`
  - Store all timestamps in UTC
  - Convert to contact timezone for display only

### Domain Rules
- Follow `/context/emergency-contact-requirements.md`
  - Notification triggers
  - Delivery methods
  - Retry logic
- Follow `/context/phi-sharing-matrix.md`
  - Exactly what PHI to share per consent scope
  - No PHI without consent
- Follow `/context/audit-log-spec.md`
  - All required audit fields
  - Synchronous logging
  - Immutable logs

### Code Quality
- Use Spring Boot best practices
- Dependency injection for all services
- Value objects for DTOs
- Proper exception handling
- Comprehensive test coverage (>80%)

## Success Criteria
- [ ] All tests passing
- [ ] No PHI violations in logs or errors
- [ ] Consent validated before all operations
- [ ] All timestamps in UTC
- [ ] Audit logs created for all notifications
- [ ] Retry logic working correctly
- [ ] API documentation generated
- [ ] Code follows all Cursor rules

## Notes
- Use correlation IDs to trace notifications across services
- Sanitize all error messages before logging
- Test timezone conversion thoroughly (Auckland ↔ London ↔ New York)
- Consider edge cases: multiple contacts, expired consent mid-notification, delivery failures

