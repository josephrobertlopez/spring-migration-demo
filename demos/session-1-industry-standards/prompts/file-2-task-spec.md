# Task Specification: UserController Migration

**Pattern Applied:** Template Pattern (structured output format for AI)
**Maps to:** GitHub issue acceptance criteria, Jira ticket requirements, task checklist
**Alternative:** Could be part of ADR "Implementation Requirements" section

---

## Current State (Spring 2.7)

**File:** `spring-migration-demo/src/main/java/com/example/demo/controller/UserController.java`

**Current implementation characteristics:**
- Uses javax.* imports (javax.servlet, javax.validation)
- May use @RequestMapping with method parameters
- May use @Autowired dependency injection
- Basic exception handling (try-catch with ResponseEntity)

**Note:** The spring-migration-demo repository main branch already uses modern Spring 3 patterns. For learning purposes, imagine the "before" state had javax imports and older patterns.

---

## Target State (Spring 3.2)

**Required changes:**
- jakarta.* imports (jakarta.servlet, jakarta.validation)
- Specific HTTP method annotations (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping)
- Constructor injection with @RequiredArgsConstructor (no @Autowired needed)
- Modern exception handling (keep current ResponseEntity pattern unless enhancing)

---

## Success Criteria (Template Pattern)

### Compilation & Build
- ✓ Compiles with Spring Boot 3.2.x
- ✓ No compiler errors
- ✓ No @Deprecated warnings

### Testing
- ✓ All unit tests pass
- ✓ All integration tests pass
- ✓ Test coverage maintained or improved

### API Contract Preservation
- ✓ API endpoints unchanged (same URLs: /api/users, /api/users/{id}, etc.)
- ✓ Request formats unchanged
- ✓ Response formats unchanged
- ✓ HTTP status codes unchanged

### Code Quality
- ✓ Follows Spring Boot 3 conventions
- ✓ No javax.* imports remain
- ✓ Dependency injection uses modern patterns
- ✓ Code is readable and maintainable

---

## API Endpoints to Preserve

### GET /api/users
- **Returns:** List<User>
- **Status:** 200 OK
- **Description:** Get all users

### GET /api/users/active
- **Returns:** List<User>
- **Status:** 200 OK
- **Description:** Get active users only

### GET /api/users/{id}
- **Returns:** User
- **Status:** 200 OK or 404 Not Found
- **Description:** Get user by ID

### GET /api/users/username/{username}
- **Returns:** User
- **Status:** 200 OK or 404 Not Found
- **Description:** Get user by username

### POST /api/users
- **Request:** User (with validation)
- **Returns:** User
- **Status:** 201 Created or 400 Bad Request
- **Description:** Create new user

### PUT /api/users/{id}
- **Request:** User (with validation)
- **Returns:** User
- **Status:** 200 OK or 404 Not Found
- **Description:** Update existing user

### DELETE /api/users/{id}
- **Returns:** Void
- **Status:** 204 No Content or 404 Not Found
- **Description:** Delete user

---

## Migration Checklist (Structured Template)

When migrating a Spring 2.7 controller to Spring 3.2, verify each item:

### Imports
- [ ] All javax.validation.* replaced with jakarta.validation.*
- [ ] All javax.servlet.* replaced with jakarta.servlet.*
- [ ] All javax.* packages replaced with jakarta.* equivalents
- [ ] No javax.* imports remain

### Annotations
- [ ] @RequestMapping(method=GET) replaced with @GetMapping
- [ ] @RequestMapping(method=POST) replaced with @PostMapping
- [ ] @RequestMapping(method=PUT) replaced with @PutMapping
- [ ] @RequestMapping(method=DELETE) replaced with @DeleteMapping
- [ ] @RequestMapping(method=PATCH) replaced with @PatchMapping (if used)
- [ ] Class-level @RequestMapping preserved (for base path)

### Dependency Injection
- [ ] @RequiredArgsConstructor present on class (or explicit constructor)
- [ ] All dependencies are final fields
- [ ] No @Autowired annotations on constructor
- [ ] No @Autowired annotations on fields

### Exception Handling
- [ ] Exception handling logic preserved
- [ ] try-catch blocks maintained
- [ ] ResponseEntity error responses unchanged
- [ ] (Optional) ProblemDetail considered for future enhancement

### Endpoints
- [ ] GET /api/users works
- [ ] GET /api/users/active works
- [ ] GET /api/users/{id} works
- [ ] GET /api/users/username/{username} works
- [ ] POST /api/users works
- [ ] PUT /api/users/{id} works
- [ ] DELETE /api/users/{id} works

### Code Quality
- [ ] Code compiles cleanly
- [ ] No compiler warnings
- [ ] All tests pass
- [ ] Code follows Spring Boot 3 conventions

---

## Current Code (Reference)

**This is the target Spring 3.2 pattern from the repository:**

```java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;  // jakarta (not javax)
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor  // No @Autowired needed
public class UserController {

    private final UserService userService;  // final field

    @GetMapping  // Specific HTTP method annotation
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();  // Basic error handling
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

---

## Expected Transformations (Few-shot Examples)

### Import Changes
```java
// Before (Spring 2.7)
import javax.validation.Valid;
import javax.servlet.http.*;

// After (Spring 3.2)
import jakarta.validation.Valid;
import jakarta.servlet.http.*;
```

### Annotation Changes
```java
// Before (Spring 2.7)
@RequestMapping(value = "/users", method = RequestMethod.GET)
public ResponseEntity<List<User>> getAllUsers() { ... }

// After (Spring 3.2)
@GetMapping("/users")
public ResponseEntity<List<User>> getAllUsers() { ... }
```

### Dependency Injection Changes
```java
// Before (Spring 2.7)
@Autowired
private UserService userService;

// After (Spring 3.2) - with @RequiredArgsConstructor
private final UserService userService;
// Constructor generated by Lombok @RequiredArgsConstructor
```

---

## Alternative Formats

This task specification could also be:

**Option A: GitHub Issue**
```markdown
## Acceptance Criteria
- [ ] Compiles with Spring Boot 3.2
- [ ] All tests pass
- [ ] API endpoints unchanged
[etc.]
```

**Option B: Jira Ticket**
```
Story Points: 3
Requirements:
- Update imports to jakarta.*
- Modernize annotations
- Verify tests pass
```

**Option C: ADR Section**
```markdown
## Implementation Requirements
See checklist in docs/adr/0002-usercontroller-migration.md
```

**Choose the format your team already uses.** The structured checklist pattern works regardless of tool.

---

## Notes

- **Repository Status:** The spring-migration-demo repository already demonstrates Spring 3 patterns
- **Learning Approach:** Use this specification as a template for migrating controllers in your own projects
- **System Prompt Reference:** All transformations must follow rules in file-1-system-prompt.md
- **Validation:** Compare your AI-generated code against the patterns shown above
