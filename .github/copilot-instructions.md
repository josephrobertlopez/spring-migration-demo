# GitHub Copilot Instructions - Spring Boot 3 Migration (Day 1)

## Project Context

This is a demo repository for Prompt Engineering Bootcamp - **Day 1: Industry Standards**

**Current State:**
- Spring Boot 3.2.0 (migrated)
- Java 17
- Jakarta EE namespace (jakarta.*)

**Purpose:** Learn foundational prompt engineering patterns through practical Spring Boot migration

## Migration Standards

### Package Transformations

**BEFORE (Spring Boot 2.7.x):**
```java
import javax.persistence.*;
import javax.validation.Valid;
```

**AFTER (Spring Boot 3.x):**
```java
import jakarta.persistence.*;
import jakarta.validation.Valid;
```

### Annotation Modernization

**BEFORE:**
```java
@RequestMapping(value = "/users", method = RequestMethod.GET)
```

**AFTER:**
```java
@GetMapping("/users")
```

### Dependency Injection

**Preferred approach:**
```java
@RestController
@RequiredArgsConstructor  // Lombok
public class UserController {
    private final UserService userService;  // Constructor injection
}
```

**Avoid:**
```java
@Autowired
private UserService userService;  // Field injection
```

## Coding Standards

### REST Controllers

- Use specific HTTP method annotations (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping)
- Use constructor injection with @RequiredArgsConstructor
- Preserve API contracts (URLs must remain unchanged)
- Use @Valid for request body validation

### Services

- Keep business logic in service layer
- Use transactions where appropriate (@Transactional)
- Constructor injection for dependencies

### Testing

- Unit tests: Mockito for mocking
- Integration tests: Cucumber for BDD scenarios
- Maintain test coverage during migrations

## Quality Requirements

✅ **Code must:**
- Compile with Spring Boot 3.2.0
- Pass all unit tests
- Pass all integration tests
- Have no @Deprecated warnings
- Preserve all API endpoints and behavior

❌ **Do NOT:**
- Change business logic during framework migration
- Modify database schemas
- Alter API endpoint URLs
- Remove error handling

## Decision Documentation

See `demos/session-1-industry-standards/adr/` for architecture decisions.

**Example usage:**
```
"Follow ADR 0001, migrate UserController to Spring Boot 3 patterns"
```

## Day 1 Learning Materials

Workshop demo files in `demos/session-1-industry-standards/`:
- **ADR example:** Shows decision documentation
- **5-file workflow:** Demonstrates Persona, Few-shot, Template, Chain-of-Thought patterns
- **README:** How to use these materials

## Related Resources

- Workshop repo: https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- Spring Boot 3 Migration Guide: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- Day 2 materials: Check `demo-day-2` branch for advanced patterns
