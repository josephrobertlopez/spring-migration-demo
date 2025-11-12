# MODE 1: CONSTITUTION - Spring 2→3 Migration Rules

You are assisting with Spring Boot 2.x to Spring Boot 3.x migration for a Java application.

---

## 🎯 NON-NEGOTIABLE RULES

### Package Migration Rules
**Rule 1.1:** All `javax.*` packages MUST be replaced with `jakarta.*`
- javax.servlet.* → jakarta.servlet.*
- javax.persistence.* → jakarta.persistence.*
- javax.validation.* → jakarta.validation.*
- javax.transaction.* → jakarta.transaction.*
- javax.annotation.* → jakarta.annotation.*

**Rule 1.2:** Keep all other imports unchanged unless deprecated

### Annotation Modernization Rules
**Rule 2.1:** Replace verbose @RequestMapping with specific HTTP method annotations
- @RequestMapping(method = RequestMethod.GET) → @GetMapping
- @RequestMapping(method = RequestMethod.POST) → @PostMapping
- @RequestMapping(method = RequestMethod.PUT) → @PutMapping
- @RequestMapping(method = RequestMethod.DELETE) → @DeleteMapping
- @RequestMapping(method = RequestMethod.PATCH) → @PatchMapping

**Rule 2.2:** Remove @Autowired on constructor injection (implicit in Spring 5.0+)

**Rule 2.3:** Update parameter annotations
- Keep @RequestBody, @PathVariable, @RequestParam as-is
- Update validation annotations: javax.validation → jakarta.validation

### Security Configuration Rules
**Rule 3.1:** Remove WebSecurityConfigurerAdapter pattern (deprecated)
- Do NOT extend WebSecurityConfigurerAdapter
- Create @Bean methods returning SecurityFilterChain

**Rule 3.2:** Use lambda DSL for security configuration
- authorizeRequests() → authorizeHttpRequests()
- Remove .and() chaining, use lambdas instead
- Example old: http.authorizeRequests().anyRequest().authenticated().and().csrf()
- Example new: http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

**Rule 3.3:** Update antMatchers to requestMatchers
- antMatchers("/api/**") → requestMatchers("/api/**")

### Exception Handling Rules
**Rule 4.1:** Use ProblemDetail for REST error responses (RFC 7807)
- @ExceptionHandler methods should return ProblemDetail
- Set status, title, detail properties

**Rule 4.2:** Preserve existing error handling logic, just modernize format

---

## 📋 CONSTRAINTS

### Business Logic Constraints
- **MUST preserve** all business logic (no functional changes)
- **MUST preserve** API contracts (same URLs, same request/response formats)
- **MUST preserve** authentication and authorization logic

### Code Quality Constraints
- **MUST maintain** existing code structure unless modernizing Spring patterns
- **MUST keep** variable names and method names unless they conflict with new patterns
- **MUST add** comments where Spring 3 pattern differs significantly from Spring 2

### Testing Constraints
- **MUST keep** all existing test cases
- **MUST update** test imports (javax → jakarta)
- **MUST ensure** all tests pass after migration

---

## 🏗️ PATTERNS TO FOLLOW

### Dependency Injection Pattern
**Spring 3 Standard:**
```java
// Constructor injection - no @Autowired needed
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

### Security Configuration Pattern
**Spring 3 Standard:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
```

### Exception Handling Pattern
**Spring 3 Standard:**
```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ProblemDetail> handleNotFound(ResourceNotFoundException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND, 
        ex.getMessage()
    );
    problem.setTitle("Resource Not Found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
}
```

---

## ⚠️ MIGRATION WARNINGS

### Do NOT Change These
- Business logic (calculations, algorithms, workflows)
- Database queries (JPA/Hibernate logic)
- External API integrations
- DTO/Entity structures (unless imports change)

### Pay Special Attention To
- SecurityFilterChain configuration (common source of errors)
- Test assertions (may need updates for new error formats)
- CORS configuration (syntax changed slightly)
- WebSocket configuration (if present, has new patterns)

---

## 🎓 QUALITY STANDARDS

### Code must:
- ✅ Compile with Spring Boot 3.2.x
- ✅ Use Java 17+ features where appropriate
- ✅ Follow Spring Boot 3 conventions
- ✅ Pass all existing tests
- ✅ Maintain API backward compatibility

### Code must NOT:
- ❌ Use deprecated Spring 2 patterns
- ❌ Mix javax and jakarta imports
- ❌ Break existing API contracts
- ❌ Remove business logic
- ❌ Introduce new dependencies without justification

---

## 📚 REFERENCE

**Target Versions:**
- Spring Boot: 3.2.x
- Spring Framework: 6.1.x
- Java: 17+
- Jakarta EE: 10

**Key Migration Docs:**
- https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- https://docs.spring.io/spring-security/reference/6.0/migration/index.html

---

**Constitution Active**
These rules apply to ALL files in this migration project.
Reference this constitution in every implementation.
