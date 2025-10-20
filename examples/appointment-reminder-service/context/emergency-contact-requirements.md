# Emergency Contact Notification Requirements

## Business Context
When a patient is admitted to the emergency department or experiences a critical health event, the clinical team must notify the patient's designated emergency contact(s).

## Functional Requirements

### 1. Emergency Contact Management
- Patients can designate 1-3 emergency contacts
- Each contact must have:
  - Full name
  - Relationship to patient (spouse, parent, child, sibling, friend, other)
  - Primary phone number
  - Secondary phone number (optional)
  - Email address (optional)
  - Preferred contact method (SMS, email, phone call)
  - Timezone (for proper notification timing)
  - Language preference

### 2. Notification Triggers
Notifications must be sent when:
- Patient admitted to emergency department
- Patient admitted to ICU
- Critical lab result received
- Surgery scheduled or completed
- Patient condition changes significantly
- Patient requests contact notification

### 3. Notification Content
Emergency contacts may receive:
- **Always Allowed:**
  - Patient reference ID (not name)
  - Facility name and location
  - General status ("stable", "critical", "recovering")
  - Contact information for care team
  - Visiting hours and policies

- **Allowed with Consent:**
  - Patient name
  - Reason for admission (general)
  - Expected length of stay
  - Specific condition updates

- **Never Allowed:**
  - Detailed diagnosis
  - Specific lab results
  - Treatment details
  - Medical history
  - Insurance information
  - Social security number

### 4. Consent Requirements
- **Emergency Contact Consent Scope:** `EMERGENCY_CONTACT_NOTIFY`
- Consent must be:
  - Active (not expired)
  - Specific to emergency contact notifications
  - Documented in audit log
- If no consent exists:
  - Send minimal notification (facility contact info only)
  - Do not include patient name or condition

### 5. Timezone Handling
- Store all timestamps in UTC
- Convert notification times to contact's local timezone
- Display times in contact's timezone in messages
- Example: Patient admitted at 2024-01-15T03:00:00Z (Auckland)
  - Contact in London sees: "Admitted at 2024-01-15 03:00 GMT"
  - Contact in New York sees: "Admitted at 2024-01-14 22:00 EST"

### 6. Notification Delivery
- **SMS:** Use for urgent notifications (admission, critical status)
- **Email:** Use for non-urgent updates (surgery scheduled, discharge planning)
- **Phone Call:** Reserved for critical events only
- Retry logic:
  - SMS: Retry once after 5 minutes if delivery fails
  - Email: Retry twice (5 min, 30 min intervals)
  - Phone: Do not retry (requires human intervention)

### 7. Audit Logging
Every notification must create an audit log entry with:
- Timestamp (UTC)
- Patient reference ID
- Emergency contact ID
- Notification type (admission, status_change, etc.)
- Consent scope used
- PHI shared (list of fields)
- Delivery method (SMS, email, phone)
- Delivery status (sent, failed, retrying)
- User who triggered notification (if manual)

## Non-Functional Requirements

### Performance
- Notifications must be sent within 5 minutes of trigger event
- System must handle 100 concurrent notifications
- Audit logs must be written synchronously (cannot be lost)

### Security
- All PHI must be encrypted in transit (TLS 1.3)
- Emergency contact data must be encrypted at rest
- Access to notification history requires `AUDIT_VIEW` permission
- Failed delivery attempts must not expose PHI in error messages

### Compliance
- HIPAA: Minimum necessary standard applies
- GDPR: Right to be forgotten (delete contact data on request)
- Audit logs must be retained for 7 years
- Consent must be re-validated every 12 months

## Edge Cases

### 1. Multiple Emergency Contacts
- Notify all contacts simultaneously
- Do not wait for one to succeed before sending to others
- Log each notification separately

### 2. Contact Unreachable
- If primary method fails, try secondary method
- If all methods fail, create alert for care team
- Do not retry indefinitely (max 3 attempts total)

### 3. Consent Expired During Notification
- If consent expires between trigger and delivery:
  - Send minimal notification only
  - Log consent expiry in audit trail
  - Alert care team to update consent

### 4. Patient Opts Out Mid-Treatment
- If patient revokes consent during admission:
  - Stop all future notifications immediately
  - Do not retract already-sent notifications
  - Log opt-out event

### 5. Contact in Different Country
- Respect international phone number formats
- Handle timezone conversions correctly
- Consider language preferences
- Comply with local data protection laws (GDPR, etc.)

## API Design

### Create Emergency Contact
```
POST /api/patients/{patientRef}/emergency-contacts
Headers:
  X-Consent-Scope: EMERGENCY_CONTACT_MANAGE
Body:
  {
    "name": "Jane Doe",
    "relationship": "spouse",
    "primaryPhone": "+64-21-555-0123",
    "email": "jane.doe@example.com",
    "preferredMethod": "SMS",
    "timezone": "Pacific/Auckland",
    "language": "en"
  }
```

### Send Notification
```
POST /api/notifications/emergency-contact
Headers:
  X-Consent-Scope: EMERGENCY_CONTACT_NOTIFY
Body:
  {
    "patientRef": "PAT_12345",
    "notificationType": "ADMISSION",
    "severity": "URGENT",
    "message": "Patient admitted to Emergency Department at Auckland City Hospital. Please contact 09-555-0100 for information."
  }
```

### Get Notification History
```
GET /api/patients/{patientRef}/notifications
Headers:
  X-Consent-Scope: AUDIT_VIEW
Response:
  [
    {
      "id": "NOTIF_001",
      "timestamp": "2024-01-15T03:00:00Z",
      "type": "ADMISSION",
      "contactId": "EC_001",
      "deliveryMethod": "SMS",
      "status": "SENT",
      "phiShared": ["patientName", "facilityName", "generalStatus"]
    }
  ]
```

## Testing Requirements

### Test Data
- Use synthetic data only (see `.cursor/rules/testing-patterns.mdc`)
- Example emergency contact:
  ```
  Name: TEST_CONTACT_001
  Phone: +64-21-555-9999
  Email: test.contact.001@example.test
  Relationship: spouse
  ```

### Test Scenarios
1. **Happy Path:** Valid consent, contact reachable, notification sent
2. **No Consent:** Send minimal notification only
3. **Expired Consent:** Detect and send minimal notification
4. **Timezone Conversion:** Verify correct local time in message
5. **Delivery Failure:** Verify retry logic and audit logging
6. **PHI Protection:** Verify no PHI in logs or error messages
7. **Multiple Contacts:** Verify all contacts notified
8. **Audit Logging:** Verify all fields captured correctly

## Success Metrics
- 95% of notifications delivered within 5 minutes
- 0 PHI violations in logs or error messages
- 100% of notifications have audit log entries
- 0 consent validation failures in production

