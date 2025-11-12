# Implementation Plan: UserController Migration

**Location**: spec/implementation-plan.md
**Related**: specification.md (what to build), knowledge-base.md (domain context)
**Created**: 2025-11-12
**Last Updated**: 2025-11-12

## Objective

Migrate UserController from Spring Boot 2.7 (javax namespace) to Spring Boot 3.2 (jakarta namespace) while preserving all API contracts and business logic. Total estimated time: 20 minutes.

## Approach

**Pattern: ReAct (Think → Act → Observe)**

Each phase follows a deliberate cycle:
- **Think**: Analyze current state, identify changes needed
- **Act**: Execute specific transformations
- **Observe**: Validate changes work before proceeding

This ensures compilation errors are caught immediately and dependencies between phases are respected.

## Phases

### Phase 1: Package Updates (Est: 2 min)

**Why first:** Imports must compile before changing annotations

**Think:**
- Review all import statements in UserController
- Identify javax.servlet.* and javax.validation.* imports
- Plan replacement mapping (javax → jakarta)

**Act:**
- Task 1.1: Replace javax.servlet.* with jakarta.servlet.*
- Task 1.2: Replace javax.validation.* with jakarta.validation.*

**Observe:**
- Code compiles without import errors
- No unresolved references
- IDE shows no red squiggly lines

**Dependencies:** None (this is first phase)

---

### Phase 2: Annotation Modernization (Est: 5 min)

**Why second:** Annotations build on updated imports

**Think:**
- Scan controller for @RequestMapping annotations
- Identify HTTP method parameters (method=GET/POST/PUT/DELETE)
- Map to specific annotations (@GetMapping, @PostMapping, etc.)

**Act:**
- Task 2.1: Identify all @RequestMapping annotations
- Task 2.2: Replace @RequestMapping(method=GET) with @GetMapping
- Task 2.3: Replace @RequestMapping(method=POST) with @PostMapping
- Task 2.4: Replace @RequestMapping(method=PUT) with @PutMapping
- Task 2.5: Replace @RequestMapping(method=DELETE) with @DeleteMapping

**Observe:**
- All HTTP method annotations are specific
- Code compiles successfully
- No @RequestMapping with method parameter remains
- URL paths preserved

**Dependencies:** Phase 1 complete (imports updated)

---

### Phase 3: Dependency Injection (Est: 3 min)

**Why third:** Clean up after framework changes

**Think:**
- Examine constructor injection pattern
- Check for @Autowired annotations (redundant in Spring 3)
- Verify @RequiredArgsConstructor usage (Lombok)

**Act:**
- Task 3.1: Verify @RequiredArgsConstructor is present
- Task 3.2: Confirm no @Autowired annotations on constructor
- Task 3.3: Ensure all dependencies are final fields

**Observe:**
- Constructor injection working
- No @Autowired warnings
- Spring successfully injects dependencies
- No compilation errors

**Dependencies:** Phase 2 complete (annotations modernized)

---

### Phase 4: Exception Handling Review (Est: 5 min)

**Why fourth:** Depends on updated Spring framework patterns

**Think:**
- Review current exception handling approach
- Recall ABCD decision: Keep current ResponseEntity pattern (no ProblemDetail)
- Verify error handling is appropriate for Spring 3

**Act:**
- Task 4.1: Review exception handling in createUser method
- Task 4.2: Review exception handling in updateUser method
- Task 4.3: Review exception handling in deleteUser method
- Task 4.4: Keep current pattern per ABCD decision (no ProblemDetail changes)

**Observe:**
- Exception handling appropriate for Spring 3
- Errors handled gracefully
- ResponseEntity patterns preserved
- No deprecated error handling methods used

**Dependencies:** Phase 3 complete (injection updated)

---

### Phase 5: Testing & Validation (Est: 5 min)

**Why last:** Validates all changes work together

**Think:**
- Review entire controller for completeness
- Check for any missed deprecations
- Compare against target state from specification

**Act:**
- Task 5.1: Review code for compilation errors
- Task 5.2: Check for @Deprecated warnings
- Task 5.3: Compare with spring-migration-demo feature branch
- Task 5.4: Verify API endpoints unchanged

**Observe:**
- Code compiles successfully
- Patterns match target architecture
- APIs unchanged (same URLs, same contracts)
- No warnings in console
- All acceptance criteria met

**Dependencies:** All previous phases complete

---

## Testing Strategy

**Per-Phase Validation:**
- Each phase includes "Observe" step for immediate validation
- Compilation must succeed before moving to next phase
- No warnings tolerated (fix immediately)

**Final Validation:**
- Compare migrated controller to specification requirements
- Verify all 7 API endpoints preserved
- Check for @Deprecated warnings
- Ensure business logic unchanged

**Rollback Strategy:**
If any phase fails:
1. Identify which task in the phase failed
2. Review knowledge-base.md for architectural principles
3. Check specification.md acceptance criteria
4. Fix issue before proceeding to next phase

## Rollout Strategy

**Migration Approach: Single File Migration**
- Focus: UserController only (isolated change)
- Risk: Low (minimal API surface)
- Testing: Existing unit tests must pass

**Deployment:**
- No deployment required (this is a workshop demonstration)
- In production: Would deploy to staging first, run regression tests

## Timeline

| Phase | Duration | Cumulative |
|-------|----------|------------|
| Phase 1: Package Updates | 2 min | 2 min |
| Phase 2: Annotation Modernization | 5 min | 7 min |
| Phase 3: Dependency Injection | 3 min | 10 min |
| Phase 4: Exception Handling Review | 5 min | 15 min |
| Phase 5: Testing & Validation | 5 min | 20 min |

**Total: 20 minutes**

## Notes

- Keep tasks small (1-2 changes per task)
- Validate after each phase (ReAct pattern)
- Don't skip phases (dependencies matter)
- Document any deviations from plan
- Conservative approach: Preserve patterns unless Spring 3 requires change
