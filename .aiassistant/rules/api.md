---
apply: always
---

# REST API Rules

- Use RESTful resource naming.
- Use plural nouns for resources.
- Use proper HTTP methods:
    - GET for query
    - POST for create/action
    - PUT/PATCH for update
    - DELETE for delete
- Use consistent response wrapper if project already has one.
- Never expose internal exception messages to clients.
- Request DTO name format: XxxRequest.
- Response DTO name format: XxxResponse.
- Validate all request bodies.
- Return proper HTTP status codes.
- Pagination APIs must support page, size, sort.