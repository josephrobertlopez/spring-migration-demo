# MODE 2: SPECIFICATION - Migrate UserController

## Current State (Spring 2.7)
File: `spring-migration-demo/src/main/java/com/example/demo/controller/UserController.java`

**Current implementation characteristics:**
- Uses javax.* imports (javax.servlet, javax.validation)
- May use @RequestMapping with method parameters
- May use @Autowired dependency injection
- Basic exception handling (try-catch with ResponseEntity)

**Note:** The spring-migration-demo repository main branch already uses modern Spring 3 patterns. For learning purposes, imagine the "before" state had javax imports and older patterns.

---

## Target State (Spring 3.2)
- jakarta.* imports (jakarta.servlet, jakarta.validation)
- Specific HTTP method annotations (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping)
- Constructor injection with @RequiredArgsConstructor (no @Autowired needed)
- Modern exception handling (optional: ProblemDetail for enhanced error responses)

---

## Success Criteria
✓ Compiles with Spring Boot 3.2.x
✓ All unit tests pass
✓ All integration tests pass
✓ API endpoints unchanged (same URLs: /api/users, /api/users/{id}, etc.)
✓ Request/response formats unchanged
✓ No @Deprecated warnings

---

## API Endpoints to Preserve

### GET /api/users
- Returns: List<User>
- Status: 200 OK
- Description: Get all users

### GET /api/users/active
- Returns: List<User>
- Status: 200 OK
- Description: Get active users only

### GET /api/users/{id}
- Returns: User
- Status: 200 OK or 404 Not Found
- Description: Get user by ID

### GET /api/users/username/{username}
- Returns: User
- Status: 200 OK or 404 Not Found
- Description: Get user by username

### POST /api/users
- Request: User (with validation)
- Returns: User
- Status: 201 Created or 400 Bad Request
- Description: Create new user

### PUT /api/users/{id}
- Request: User (with validation)
- Returns: User
- Status: 200 OK or 404 Not Found
- Description: Update existing user

### DELETE /api/users/{id}
- Returns: Void
- Status: 204 No Content or 404 Not Found
- Description: Delete user

---

## Current Code (Reference)

```java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
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
            return ResponseEntity.badRequest().build();
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

## Migration Checklist

When migrating a Spring 2.7 controller to Spring 3.2, verify:

- [ ] Import statements use jakarta.* instead of javax.*
- [ ] HTTP method annotations are specific (@GetMapping vs @RequestMapping(method=GET))
- [ ] Constructor injection uses @RequiredArgsConstructor or explicit constructor (no @Autowired)
- [ ] Validation annotations use jakarta.validation.Valid
- [ ] Exception handling is appropriate (basic ResponseEntity or enhanced ProblemDetail)
- [ ] All endpoint URLs remain unchanged
- [ ] All request/response formats remain unchanged
- [ ] Code compiles with Spring Boot 3.2.x
- [ ] All tests pass

---

## Expected Transformations

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

## Notes

- **Repository Status:** The spring-migration-demo repository already demonstrates Spring 3 patterns
- **Learning Approach:** Use this specification as a template for migrating controllers in your own projects
- **Constitution Reference:** All transformations must follow rules in file-0-constitution.md
- **Validation:** Compare your AI-generated code against the patterns shown above
