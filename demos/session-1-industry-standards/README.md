# Session 1: Industry Standards Demo

**Workshop:** Prompt Engineering Bootcamp - Session 1 (Day 1)
**Topic:** Foundational patterns applied to Spring Boot migration

## What's Here

This folder contains reference materials showing how to apply industry-standard prompt engineering patterns to the Spring Boot 2→3 migration in this repository.

### Folder Structure

```
session-1-industry-standards/
├── README.md (this file)
├── adr/
│   └── 0001-migration-strategy.md (example ADR)
└── prompts/
    ├── file-1-system-prompt.md (Persona + Few-shot patterns)
    ├── file-2-task-spec.md (Template pattern)
    ├── file-3-react-plan.md (ReAct pattern - optional)
    ├── file-4-tree-decisions.md (Tree of Thoughts - optional)
    └── file-5-synthesize.md (Chain-of-Thought pattern)
```

## How to Use These Files

### Option A: Simple Approach (Recommended)

1. Read the ADR to understand migration strategy
2. Use `.github/copilot-instructions.md` in repo root
3. Prompt AI: "Follow ADR 0001, migrate UserController.java"

**Time:** 5 minutes

### Option B: Structured Approach (Learning/Complex Tasks)

1. Load `file-1-system-prompt.md` into AI tool
2. Load `file-2-task-spec.md`
3. (Optional) Load `file-3-react-plan.md`
4. (Optional) Load `file-4-tree-decisions.md`
5. Load `file-5-synthesize.md` to generate code

**Time:** 15-20 minutes

## Patterns Demonstrated

- **Persona Pattern** (file-1): Assign AI role as "Spring migration specialist"
- **Few-shot Pattern** (file-1): Provide before/after examples
- **Template Pattern** (file-2): Structured task specification
- **ReAct Pattern** (file-3): Reason → Act → Observe cycle
- **Tree of Thoughts** (file-4): Evaluate multiple approaches
- **Chain-of-Thought** (file-5): Show reasoning steps

## Grounding to Real Code

All examples reference actual files in this repository:

- `src/main/java/com/example/demo/controller/UserController.java`
- `src/main/java/com/example/demo/model/User.java`
- `src/main/java/com/example/demo/service/UserService.java`

## Related Documentation

- **Workshop materials:** https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- **Spring Boot 3 Migration Guide:** https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- **Pattern catalog:** White et al. (2023) arXiv:2302.11382

## Next Steps

After Day 1, check out `demo-day-2` branch for advanced orchestration patterns (Session 2).
