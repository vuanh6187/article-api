---
apply: always
---

# Architecture Rules

- Follow layered architecture: Controller -> Service -> Repository.
- Controller must not contain business logic.
- Service layer owns business rules and transaction boundaries.
- Repository layer only handles persistence queries.
- Do not call Repository directly from Controller.
- Do not return Entity directly from API.
- Use DTO for request and response.
- Use Mapper class or MapStruct for Entity <> DTO conversion.
- Avoid circular dependencies between modules.
- Keep module boundaries clear.
- Do not modify unrelated modules unless explicitly required.