# Session 2: Advanced Patterns - UserController Migration

This demo demonstrates **spec-driven development** for migrating UserController from Spring Boot 2.7 to Spring Boot 3.2.

## Two Approaches Provided

This folder contains **two different organizational patterns** for the same content, demonstrating that prompt engineering patterns are **structure-agnostic**.

### Approach 1: 5-File Numbered Pattern (`prompts/`)

```
prompts/
├── file-0-constitution.md    # Reusable project rules
├── file-1-specification.md   # Feature requirements
├── file-2-planning.md        # Execution phases
├── file-3-abcd.md           # Decision documentation
└── file-4-implementation.md  # Synthesis and execution
```

**When to use:**
- Quick prototyping
- Sequential workflow emphasis
- Learning the core patterns
- Simple feature scoping

**Reference:** `../references/planning-mode.md`, `../references/abcd-mode.md`

---

### Approach 2: spec/ Folder Semantic Pattern (`spec/`)

```
spec/
├── knowledge-base.md        # Domain context + architectural principles
├── specification.md         # Requirements + design decisions
└── implementation-plan.md   # Execution phases (ReAct pattern)
```

**When to use:**
- Production projects
- Clear semantic structure
- Scalable multi-feature projects
- Team collaboration

**Reference:** `../references/spec-driven-mode.md`, `../references/spec-folder-setup.md`

---

## Content Mapping

Both approaches contain the **same information**, organized differently:

| Content Type | 5-File Pattern | spec/ Pattern |
|--------------|----------------|---------------|
| Domain knowledge + architectural principles | file-0-constitution.md | spec/knowledge-base.md |
| Feature requirements + API contracts | file-1-specification.md | spec/specification.md (Requirements) |
| Decision documentation (Tree of Thoughts) | file-3-abcd.md | spec/specification.md (Design Decisions) |
| Execution phases (ReAct pattern) | file-2-planning.md | spec/implementation-plan.md |
| Synthesis and execution | file-4-implementation.md | (Workflow in spec-driven-mode.md) |

---

## Which Should You Use?

**For this workshop:** Use the **spec/ folder approach** (demonstrated in Session 2).

**In your projects:** Choose based on your needs:
- **5-file pattern**: Great for learning, prototyping, single-feature projects
- **spec/ folder**: Better for production, team projects, multiple features

**Key insight:** The **patterns** (ReAct, Tree of Thoughts, ABCD clarification) remain the same regardless of file structure. Focus on the patterns, not the folders.

---

## Workshop Usage

**Step 1:** Load spec/ files into your AI assistant context:
```
"Load all files in spec/ folder. We're migrating UserController from Spring Boot 2.7 to 3.2."
```

**Step 2:** Execute the implementation plan:
```
"Execute spec/implementation-plan.md phase by phase. Follow the ReAct pattern (Think → Act → Observe)."
```

**Step 3:** Validate against specification:
```
"Check if the migrated code meets all acceptance criteria in spec/specification.md."
```

---

## Reference Materials

- **spec-driven-mode.md**: Overall spec-driven workflow
- **planning-mode.md**: Plan-first development workflow
- **abcd-mode.md**: Decision documentation with alternatives
- **spec-folder-setup.md**: How to create spec/ structure

All references available at: `../../bootcamp-materials/references/`

---

## Migration Target

**Objective:** Migrate UserController from Spring Boot 2.7 (javax) to Spring Boot 3.2 (jakarta)

**Key Changes:**
- javax.* → jakarta.* namespace
- @RequestMapping → @GetMapping/@PostMapping/@PutMapping/@DeleteMapping
- Remove @Autowired from constructor injection
- Preserve all 7 API endpoints with identical behavior

**Time Estimate:** 20 minutes (5 phases)

**Acceptance Criteria:** See spec/specification.md for complete requirements.

---

## Patterns Demonstrated

- **Meta-prompting:** Orchestrating multiple specialized prompts
- **ReAct Pattern:** Reasoning cycles in implementation-plan.md (Think → Act → Observe)
- **Tree of Thoughts:** Multiple solution paths in specification.md Design Decisions
- **Iterative refinement:** Building context across spec/ files

## Related Documentation

- **Workshop repo:** https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- **Day 1 materials:** Check `demo-day-1` branch for foundational patterns
- **ReAct paper:** Yao et al. (2022) - https://arxiv.org/abs/2210.03629
- **Tree of Thoughts:** Yao et al. (2023) - https://arxiv.org/abs/2305.10601
