---
apply: off
---

# Testing Rules

- Add unit tests for service business logic.
- Add controller tests for request validation and response status.
- Mock external dependencies in unit tests.
- Test success case, validation error, not found, forbidden, and conflict cases when applicable.
- Test method names should describe behavior.
- Do not write tests that depend on execution order.
- Prefer Testcontainers for integration tests if project already uses it.