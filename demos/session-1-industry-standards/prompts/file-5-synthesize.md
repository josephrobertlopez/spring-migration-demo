# Code Generation: Complete Pattern Synthesis

**Pattern Applied:** Meta-prompting (orchestrating multiple patterns)
**Combines:** Persona + Few-shot + Template + ReAct + Tree of Thoughts + Chain-of-Thought
**Alternative:** Simple prompt "migrate this following ADR 0001" if using ADR-driven approach

---

## Context Synthesis (Meta-prompting)

This generation prompt synthesizes multiple prompt engineering patterns:

**From System Prompt (file-1-system-prompt.md):**
- ✅ Persona Pattern: AI role as Spring migration specialist
- ✅ Few-shot Pattern: Before/after transformation examples
- ✅ Quality standards and constraints

**From Task Specification (file-2-task-spec.md):**
- ✅ Template Pattern: Structured checklist of required changes
- ✅ Success criteria with verification steps
- ✅ All 7 API endpoints documented

**From ReAct Execution Plan (file-3-react-plan.md):**
- ✅ Phase-by-phase execution order
- ✅ THINK → ACT → OBSERVE validation at each phase
- ✅ Clear dependency chain (imports before annotations)

**From Tree of Thoughts Decisions (file-4-tree-decisions.md):**
- ✅ Exception handling: Branch A (keep current - minimal changes)
- ✅ Validation: Branch A (keep current @Valid)
- ✅ Strategy: Conservative, migration-focused approach

---

## Code Input (Current State)

**Note:** spring-migration-demo main branch already uses jakarta. For learning purposes, imagine this "before" state with javax imports:

```java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;  // BEFORE: javax namespace
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 7 methods with Spring 2.7 patterns...
    @RequestMapping(method = RequestMethod.GET)
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

## Generation Instructions (Pattern Orchestration)

Generate Spring Boot 3.2 compatible code following this multi-pattern approach:

### Phase 1: THINK (ReAct Pattern)
**Reasoning:**
- What are the dependencies? Imports must compile before annotations can be updated
- What are the decisions? Keep exception handling as-is (Tree of Thoughts: Branch A)
- What transformations apply? Few-shot examples from system prompt
- What are success criteria? Template pattern checklist from task specification

### Phase 2: ACT (ReAct Pattern + Few-shot Pattern)
**Execute transformations following ReAct plan:**

1. **Update imports first** (Phase 1 from ReAct plan)
   - Apply few-shot rule: `javax.validation.Valid` → `jakarta.validation.Valid`
   - Validate: Code compiles

2. **Modernize annotations second** (Phase 2 from ReAct plan)
   - Apply few-shot rules:
     - `@RequestMapping(method=RequestMethod.GET)` → `@GetMapping`
     - `@RequestMapping(method=RequestMethod.POST)` → `@PostMapping`
     - `@RequestMapping(method=RequestMethod.PUT)` → `@PutMapping`
     - `@RequestMapping(method=RequestMethod.DELETE)` → `@DeleteMapping`
   - Validate: No deprecated warnings

3. **Verify dependency injection third** (Phase 3 from ReAct plan)
   - Confirm: `@RequiredArgsConstructor` present
   - Confirm: No `@Autowired` annotations
   - Confirm: `userService` is final field
   - Validate: Follows best practices

4. **Preserve exception handling fourth** (Phase 4 from ReAct plan)
   - Decision from Tree of Thoughts: Keep current pattern (Branch A)
   - Do NOT change try-catch logic
   - Do NOT adopt ProblemDetail (decision: future enhancement)
   - Validate: Exception handling unchanged

### Phase 3: OBSERVE (ReAct Pattern + Chain-of-Thought)
**Verify against success criteria:**

Show your reasoning for each checkpoint:
- ✓ Imports updated to jakarta.* (Phase 1 complete)
- ✓ Annotations modernized (Phase 2 complete)
- ✓ Dependency injection verified (Phase 3 complete)
- ✓ Exception handling preserved (Phase 4 complete)
- ✓ All 7 endpoints present with same URLs
- ✓ Code compiles with Spring Boot 3.2
- ✓ No deprecated warnings

---

## Expected Output

Generate complete UserController.java that:
1. ✅ Uses jakarta.validation.Valid (not javax)
2. ✅ Uses @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
3. ✅ Uses @RequiredArgsConstructor (no @Autowired)
4. ✅ Maintains all 7 endpoints with identical URLs
5. ✅ Preserves exception handling exactly as-is
6. ✅ Compiles with Spring Boot 3.2.x
7. ✅ Has no @Deprecated warnings

**Include inline comments showing your Chain-of-Thought reasoning for key changes.**

**Please generate the complete migrated UserController.java now.**

---

## Alternative Approaches for Code Generation

**Option A: Simple Prompt (Faster)**
```
Context: .github/copilot-instructions.md + ADR 0001
Prompt: "Migrate this controller to Spring 3, follow the ADR"
```

**Advantages:**
- Much faster (single prompt)
- Less overhead
- Good for experienced teams

**When to use:** Simple migrations, team has good ADRs and config files

---

**Option B: IDE-Native (Tool-Assisted)**
```
GitHub Copilot: Reference ADR in inline comment, use Copilot suggestions
Cursor: Add files to context, use Composer with "migrate following patterns"
```

**Advantages:**
- Built into workflow
- Real-time feedback
- No context switching

**When to use:** Team already committed to specific IDE/tool

---

**Option C: Structured Files (This Example)**
```
5 files explicitly mapping to patterns, complete orchestration
```

**Advantages:**
- Explicit pattern application
- Great for learning
- Clear reasoning trail
- Handles complex migrations

**When to use:** Complex tasks, learning prompt patterns, need auditability

---

**All approaches work. Choose based on task complexity and team preference.**

For simple controller migration: Use Option A (ADR + simple prompt)
For complex multi-file refactoring: Use Option C (structured files)
For daily development: Use Option B (IDE-native)
