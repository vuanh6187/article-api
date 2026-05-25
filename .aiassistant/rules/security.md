---
apply: always
---

# Security Rules

- Never log passwords, tokens, secrets, API keys, or personal sensitive data.
- Never hardcode secrets.
- Validate authorization before accessing protected resources.
- Do not trust user input.
- Use parameterized queries only.
- Do not expose stack traces to API response.
- Passwords must be hashed, never encrypted or stored as plain text.
- Check ownership before updating/deleting user-owned resources.