# Session 2: Advanced Patterns Demo

**Workshop:** Prompt Engineering Bootcamp - Session 2 (Day 2)
**Topic:** Orchestrating multiple patterns for complex workflows

## What's Here

This folder contains advanced prompt orchestration materials showing how to combine multiple patterns for end-to-end Spring Boot migration workflows.

### Folder Structure

```
session-2-advanced-patterns/
├── README.md (this file)
└── prompts/
    ├── file-0-constitution.md (Project-wide principles)
    ├── file-1-specification.md (What to build)
    ├── file-2-planning.md (Execution approach)
    ├── file-3-abcd.md (Decision points)
    └── file-4-implementation.md (Code generation)
```

## How to Use These Files

### 5-Mode Workflow (Spec-Kit Inspired)

**Mode 0: Constitution** (file-0)
- Load project-wide standards and principles
- Establishes context for all subsequent prompts

**Mode 1: Specification** (file-1)
- Define what needs to be built/changed
- Clear acceptance criteria

**Mode 2: Planning** (file-2)
- Break work into ordered phases
- Identify dependencies

**Mode 3: ABCD Clarification** (file-3)
- Document key decision points
- Evaluate alternatives

**Mode 4: Implementation** (file-4)
- Synthesize all context
- Generate final code

## When to Use This Approach

✅ **Use for:**
- Complex multi-file migrations
- Architectural changes requiring decisions
- Learning prompt orchestration
- Tasks requiring audit trail

❌ **Don't use for:**
- Simple single-file changes (use Day 1 approach)
- Quick iterations (overhead too high)
- Well-understood patterns (ADR + config file is simpler)

## Patterns Demonstrated

- **Meta-prompting:** Orchestrating multiple specialized prompts
- **ReAct Pattern:** Reasoning cycles in planning phase
- **Tree of Thoughts:** Multiple solution paths in ABCD
- **Iterative refinement:** Building context across modes

## Comparison to Day 1

| Aspect | Day 1 | Day 2 |
|--------|-------|-------|
| Complexity | Single controller migration | Multi-component workflow |
| Files | 5 (optional 3) | 5 (all recommended) |
| Time | 5-20 min | 30-60 min |
| Best for | Learning basics, simple tasks | Complex tasks, orchestration |

## Alternative Approaches

**Simpler alternatives that achieve same results:**
- ADRs + `.github/copilot-instructions.md` (recommended for production)
- GitHub Copilot Workspace (platform-native)
- Windsurf Cascade (if using Windsurf)

**Key insight:** The patterns matter, not the file format.

## Grounding to Real Code

These prompts reference actual Spring Boot migration scenarios:
- Service layer migration
- Controller pattern updates
- Security configuration changes
- Database schema migrations

## Related Documentation

- **Workshop repo:** https://github.com/josephrobertlopez/prompt-engineering-bootcamp
- **Day 1 materials:** Check `demo-day-1` branch for foundational patterns
- **ReAct paper:** Yao et al. (2022) - https://arxiv.org/abs/2210.03629
- **Tree of Thoughts:** Yao et al. (2023) - https://arxiv.org/abs/2305.10601

## Next Steps

1. Complete Day 1 demos first (check `demo-day-1` branch)
2. Understand when structured workflows add value vs overhead
3. Experiment with hybrid approaches (ADRs + selective structured files)
4. Choose approach based on task complexity, not dogma
