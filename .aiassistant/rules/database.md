---
apply: always
---

# Database Rules

- Table names use snake_case.
- Column names use snake_case.
- Primary key should be named id.
- Foreign key should be named xxx_id.
- Use Flyway or Liquibase migration if the project uses migration.
- Do not change existing schema without migration.
- Avoid eager fetching by default.
- Avoid N+1 queries.
- Soft delete should use deleted_at if existing convention supports it.
- Audit fields should follow project convention:
    - created_at
    - updated_at
    - created_by
    - updated_by