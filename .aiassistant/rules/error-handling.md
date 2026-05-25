---
apply: always
---

# Error Handling Rules

## General Rules

- All API errors must use `ApiErrorResponse`.
- All business exceptions must extend `ApiException`.
- Never throw generic `RuntimeException` directly in business code.
- Use typed exceptions only.
- All exceptions must map to a valid HTTP status code.
- Error responses must contain:
    - `code`
    - `message`
- Validation errors may additionally contain:
    - `errors`

---

# HTTP Error Codes

Use `ErrorCode` enum and typed exceptions extending `ApiException`.

REST responses use `ApiErrorResponse`.

| Code | HTTP | Use case |
|---|---|---|
| 200 | OK | Success (GET, PUT) |
| 201 | Created | Resource created |
| 204 | No Content | Delete success |
| 400 | Bad Request | Validation / malformed request |
| 401 | Unauthorized | Authentication failed |
| 403 | Forbidden | Permission denied |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Duplicate resource / business conflict |
| 500 | Internal Server Error | Unexpected server error |

---

# Exception Mapping

| Exception | HTTP |
|---|---|
| BadRequestException | 400 |
| UnauthorizedException | 401 |
| ForbiddenException | 403 |
| NotFoundException | 404 |
| ConflictException | 409 |

---

# ErrorCode Enum Rules

- Every business error should have a dedicated `ErrorCode`.
- ErrorCode names must use UPPER_SNAKE_CASE.
- ErrorCode should include:
    - numeric code
    - default message

Example:

```java
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(40901, "Email already exists"),
    USER_NOT_FOUND(40401, "User not found");

    private final int code;
    private final String message;
}
```

---

# ApiErrorResponse Format

## Standard Error

```json
{
  "code": 40901,
  "message": "Email already exists"
}
```

## Validation Error

```json
{
  "code": 40000,
  "message": "Validation failed",
  "errors": {
    "email": "Email must be valid"
  }
}
```

---

# Validation Rules

- Use Bean Validation annotations.
- Validation errors must be handled globally.
- Do not manually validate request fields in controllers unless necessary.
- Validation field names must match request DTO fields.

---

# Global Exception Handler Rules

- Use `@RestControllerAdvice`.
- Centralize all exception handling.
- Never expose stack traces to API clients.
- Log unexpected exceptions internally.
- Return generic message for internal server errors.

---

# Logging Rules

- Log unexpected exceptions at ERROR level.
- Do not log sensitive data:
    - password
    - token
    - secret
    - personal sensitive data
- Business exceptions may use WARN level.