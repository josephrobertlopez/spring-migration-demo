# GitHub Copilot Instructions - Spring Boot 3 Migration (Day 2)

## Project Context

This is a demo repository for Prompt Engineering Bootcamp - **Day 2: Advanced Patterns**

**Current State:**
- Spring Boot 3.2.0 (migrated)
- Java 17
- Jakarta EE namespace (jakarta.*)

**Purpose:** Learn advanced prompt orchestration through practical Spring Boot migration workflows

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

## Day 2 Learning Materials

Workshop demo files in `demos/session-2-advanced-patterns/`:
- **5-mode workflow:** Constitution → Specification → Planning → ABCD → Implementation
- **ReAct Pattern:** Reasoning cycles
- **Tree of Thoughts:** Multiple solution evaluation
- **Meta-prompting:** Orchestrating specialized prompts
- **README:** How to use these advanced materials

## Advanced Orchestration

Day 2 focuses on combining multiple patterns for complex workflows:
- Multi-component migrations
- Architectural decision-making
- Structured problem-solving
- Audit trail creation

## Related Resources

- Workshop repo: https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- Spring Boot 3 Migration Guide: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- Day 1 materials: Check `demo-day-1` branch for foundational patterns
- ReAct: https://arxiv.org/abs/2210.03629
- Tree of Thoughts: https://arxiv.org/abs/2305.10601
