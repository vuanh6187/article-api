---
apply: always
---

# Spring Boot Rules

- Use constructor injection only.
- Do not use field injection with @Autowired.
- Prefer @Service, @Repository, @RestController with clear responsibility.
- Put @Transactional on service methods, not controller methods.
- Use @Transactional(readOnly = true) for query-only service methods.
- Validate input using Bean Validation annotations.
- Centralize exception handling with @RestControllerAdvice.
- Use configuration properties instead of hardcoded config values.
- Do not put business logic in Entity, DTO, Mapper, or Controller.