# GitHub Copilot Instructions - Spring Boot 3 Migration

## Project Context

This is a demo repository for **Prompt Engineering Bootcamp** - Day 1 & Day 2

**Current State:**
- Spring Boot 3.2.0 (migrated)
- Java 17
- Jakarta EE namespace (jakarta.*)

**Purpose:** Learn prompt engineering patterns (foundational & advanced) through practical Spring Boot migration

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

## Workshop Materials

### Day 1: Industry Standards (Foundational Patterns)

Workshop demo files in `demos/session-1-industry-standards/`:
- **ADR example:** Shows decision documentation
- **5-file workflow:** Demonstrates Persona, Few-shot, Template, Chain-of-Thought patterns
- **README:** How to use these materials

**Example usage:**
```
"Follow ADR 0001, migrate UserController to Spring Boot 3 patterns"
```

### Day 2: Advanced Patterns (Orchestration)

Workshop demo files in `demos/session-2-advanced-patterns/`:
- **spec/ folder:** knowledge-base.md, specification.md, implementation-plan.md
- **prompts/ folder:** 5-file workflow (alternative structure)
- **ReAct Pattern:** Think→Act→Observe reasoning cycles
- **Tree of Thoughts:** Multiple solution path evaluation
- **Meta-prompting:** Orchestrating specialized prompts
- **README:** How to use these advanced materials

**Focus:** Combining multiple patterns for complex workflows
- Multi-component migrations
- Architectural decision-making
- Structured problem-solving
- Audit trail creation

## Related Resources

- Workshop repo: https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- Spring Boot 3 Migration Guide: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- ReAct paper: https://arxiv.org/abs/2210.03629
- Tree of Thoughts paper: https://arxiv.org/abs/2305.10601
