# Healthcare Domain Rules
- No PHI in code, logs, or examples; use synthetic data.
- Consent: operations on clinical data require EHR_VIEW scope; fail safe if missing.
- Time: store/return UTC; annotate boundaries where conversion occurs.

