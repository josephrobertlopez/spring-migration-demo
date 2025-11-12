# MODE 5: IMPLEMENTATION - Generate Migrated UserController

## Context Loaded

This file synthesizes all previous modes to generate Spring 3.2 compatible code.

### From Constitution (file-0):
✓ Package rules: javax.* → jakarta.*
✓ Annotation rules: @RequestMapping → specific HTTP methods (@GetMapping, @PostMapping, etc.)
✓ Dependency injection: Constructor injection with @RequiredArgsConstructor (no @Autowired)
✓ Quality standards: Must compile with Spring 3.2, pass tests, maintain API contracts

### From Specification (file-1):
✓ File: UserController.java
✓ Target: Spring Boot 3.2 compatible
✓ Success criteria: Compiles, tests pass, APIs unchanged, no deprecated warnings
✓ Endpoints: /api/users (GET, POST), /api/users/{id} (GET, PUT, DELETE), /api/users/active (GET), /api/users/username/{username} (GET)

### From Planning (file-2):
✓ Phase 1: Package updates (javax → jakarta)
✓ Phase 2: Annotation modernization (@RequestMapping → @GetMapping/@PostMapping/@PutMapping/@DeleteMapping)
✓ Phase 3: Dependency injection (verify @RequiredArgsConstructor, no @Autowired)
✓ Phase 4: Exception handling (keep current pattern per ABCD decision)
✓ Phase 5: Testing and validation

### From ABCD Decisions (file-3):
✓ Exception handling: Keep current ResponseEntity pattern (Option B)
✓ Validation: Keep current @Valid pattern (Option A)
✓ Approach: Conservative migration focusing on framework upgrade, minimal business logic changes

---

## Input Code (Spring 2.7 - Hypothetical Before State)

**Note:** The spring-migration-demo repository main branch already uses jakarta imports. For learning purposes, imagine the "before" state as shown below with javax imports:

```java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;  // Before: javax
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)  // Before: verbose @RequestMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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

## Instructions

Generate Spring Boot 3.2 compatible code for UserController following these requirements:

1. **Apply all Constitution rules:**
   - Update javax.validation.* → jakarta.validation.*
   - Replace all @RequestMapping annotations with specific HTTP method annotations
   - Verify constructor injection with @RequiredArgsConstructor (no @Autowired needed)

2. **Meet all Specification criteria:**
   - Target: Spring Boot 3.2.x compatibility
   - Preserve all 7 API endpoints with same URLs
   - Maintain request/response formats
   - Ensure code compiles without errors or warnings

3. **Follow the Planning phases:**
   - Phase 1: Package updates first
   - Phase 2: Annotation modernization second
   - Phase 3: Verify dependency injection third
   - Phase 4: Maintain current exception handling (per ABCD decision)
   - Phase 5: Ensure validation and testing readiness

4. **Implement ABCD decisions:**
   - Keep current exception handling pattern (try-catch with ResponseEntity)
   - Keep current validation pattern (@Valid annotation)
   - Focus on framework upgrade, not feature enhancements

5. **Generate complete, working code:**
   - Include all imports
   - Include all 7 methods
   - Include all annotations
   - Ensure proper formatting
   - Add brief comments where Spring 3 patterns differ significantly from Spring 2

---

## Expected Output

Complete UserController.java class that:
- ✅ Compiles with Spring Boot 3.2.x
- ✅ Uses jakarta.validation.Valid (not javax)
- ✅ Uses @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
- ✅ Uses @RequiredArgsConstructor for dependency injection
- ✅ Maintains all 7 endpoints with same behavior
- ✅ Has no @Deprecated warnings
- ✅ Follows modern Spring 3 patterns

**Please generate the complete migrated code now.**
