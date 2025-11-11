# MODE 3: PLANNING - UserController Migration Execution Plan

## Phase 1: Package Updates (Est: 2 min)

**Why first:** Imports must compile before changing annotations

**Tasks:**
- Task 1.1: Replace javax.servlet.* with jakarta.servlet.*
- Task 1.2: Replace javax.validation.* with jakarta.validation.*

**Validation:** Code compiles without import errors

**Dependencies:** None (this is first phase)

---

## Phase 2: Annotation Modernization (Est: 5 min)

**Why second:** Annotations build on updated imports

**Tasks:**
- Task 2.1: Identify all @RequestMapping annotations
- Task 2.2: Replace @RequestMapping(method=GET) with @GetMapping
- Task 2.3: Replace @RequestMapping(method=POST) with @PostMapping
- Task 2.4: Replace @RequestMapping(method=PUT) with @PutMapping
- Task 2.5: Replace @RequestMapping(method=DELETE) with @DeleteMapping

**Validation:** All HTTP method annotations are specific, code compiles

**Dependencies:** Phase 1 complete (imports updated)

---

## Phase 3: Dependency Injection (Est: 3 min)

**Why third:** Clean up after framework changes

**Tasks:**
- Task 3.1: Verify @RequiredArgsConstructor is present
- Task 3.2: Confirm no @Autowired annotations on constructor
- Task 3.3: Ensure all dependencies are final fields

**Validation:** Constructor injection working, no @Autowired warnings

**Dependencies:** Phase 2 complete (annotations modernized)

---

## Phase 4: Exception Handling Review (Est: 5 min)

**Why fourth:** Depends on updated Spring framework patterns

**Tasks:**
- Task 4.1: Review exception handling in createUser method
- Task 4.2: Review exception handling in updateUser method
- Task 4.3: Review exception handling in deleteUser method
- Task 4.4: Keep current pattern per ABCD decision (no ProblemDetail changes for this migration)

**Validation:** Exception handling appropriate for Spring 3, errors handled gracefully

**Dependencies:** Phase 3 complete (injection updated)

---

## Phase 5: Testing & Validation (Est: 5 min)

**Why last:** Validates all changes work together

**Tasks:**
- Task 5.1: Review code for compilation errors
- Task 5.2: Check for @Deprecated warnings
- Task 5.3: Compare with spring-migration-demo feature branch
- Task 5.4: Verify API endpoints unchanged

**Validation:** Code compiles, patterns match target, APIs unchanged

**Dependencies:** All previous phases complete

---

## Total Estimated Time: 20 minutes

## Rollback Strategy

If any phase fails:
1. Identify which task in the phase failed
2. Review Constitution rules for that area
3. Check Specification success criteria
4. Fix issue before proceeding to next phase

## Notes

- Keep tasks small (1-2 changes per task)
- Validate after each phase
- Don't skip phases (dependencies matter)
- Document any deviations from plan
