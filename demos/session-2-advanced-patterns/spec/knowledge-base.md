# Project Knowledge Base

**Location**: spec/knowledge-base.md
**Created**: 2025-11-12
**Last Updated**: 2025-11-12

## Domain Concepts

**Spring Boot Migration**: Upgrading from Spring Boot 2.x (javax namespace) to Spring Boot 3.x (jakarta namespace)

**Jakarta EE**: Evolution of Java EE, namespace changed from javax.* to jakarta.* in version 9+

**REST Controller**: Spring MVC component handling HTTP requests and returning JSON/XML responses

**Dependency Injection**: Spring pattern for managing object dependencies, constructor injection preferred in Spring 3+

## Architectural Principles

**Pattern: Constructor Injection (No @Autowired)**
- Rationale: Spring 5.0+ supports implicit constructor injection, @Autowired annotation redundant
- Example: `public UserController(UserService service) { this.service = service; }`

**Pattern: Specific HTTP Method Annotations**
- Rationale: @GetMapping/@PostMapping more explicit than @RequestMapping(method=GET/POST)
- Improves code readability and IDE support

**Pattern: SecurityFilterChain Bean**
- Rationale: WebSecurityConfigurerAdapter deprecated in Spring 2.7, removed in Spring 3
- Use @Bean returning SecurityFilterChain instead

**Anti-patterns:**
- Using @Autowired on constructor injection (redundant)
- Extending WebSecurityConfigurerAdapter (deprecated)
- Using .and() chaining in security config (verbose, use lambdas)

## System Context

**Infrastructure:**
- Spring Boot 3.2+
- Java 17+
- Maven build system
- Spring Web MVC framework

**Integrations:**
- Spring Security for authentication/authorization
- Spring Data JPA for persistence
- Jakarta Validation for input validation

## Past Decisions

**Decision: Use jakarta.* namespace**
- Rationale: Required for Spring Boot 3 compatibility, Jakarta EE 9+ standard
- Date: 2025-11-12

**Decision: Modernize HTTP method annotations**
- Rationale: Improve code clarity, follow Spring Boot 3 best practices
- Date: 2025-11-12

**Decision: Use ProblemDetail for error responses**
- Rationale: RFC 7807 standard, built-in Spring 3 support
- Date: 2025-11-12

## Constraints

**Technical:**
- Must use Java 17+ (Spring Boot 3 requirement)
- Must migrate javax.* → jakarta.* (breaking change)
- Must update Spring Security configuration (WebSecurityConfigurerAdapter removed)

**Regulatory:**
- Must preserve all authentication/authorization logic
- Must maintain API contracts (same endpoints, same request/response formats)

**Organizational:**
- Business logic must remain unchanged (migration only, no new features)
- All existing tests must pass after migration
- Code structure preserved unless Spring 3 pattern requires change

## Lessons Learned

**From previous Spring Boot migrations:**
- Import updates cause most compilation errors - fix these first
- Security configuration changes are most complex - allocate extra time
- Test imports often forgotten - update systematically
- Constructor injection simplification improves code readability
