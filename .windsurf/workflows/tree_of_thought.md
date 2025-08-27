---
description: tree of thought workflow
auto_execution_mode: 1
---

# Tree of Thoughts (ToT) Reasoning Framework

This workflow implements the Tree of Thoughts (ToT) framework for deliberate problem solving.  
ToT extends Chain of Thought prompting by enabling:

- **Thought decompositions**: Breaking reasoning into coherent units  
- **Thought generations**: Creating multiple candidates at each step  
- **State evaluations**: Evaluating progress toward solving the problem  
- **Search algorithms**: BFS/DFS with lookahead and backtracking  

---

## 🧠 Workflow Phases

1. **Problem analysis and thought decomposition**  
2. **Knowledge acquisition and research**  
3. **Iteration with thought generation and evaluations**  
4. **Search, synthesis, and presentation**  
5. **Review, refinement, and knowledge updates**  

---

## 🗂️ Context Block

> ⚠️ Must be maintained and updated throughout all reasoning steps

```yaml
############################################
question: <user_question>
question_type: <domain/topic>
reasoning_approach: <analytical/creative/systems>
knowledge_required: <key_areas>
current_step: start
next_step: problem_analysis
knowledge_base: {}
branch_paths: ["main"]
active_thoughts: ["main"]
thought_decomposition: <word/line/paragraph>
search_algorithm: <bfs/dfs>
max_depth: <integer>
beam_width: <integer>
############################################
```

---

## ⚙️ Workflow Definition

```yaml
trigger:
  event: "problem_request"
  parameters:
    question: <user_question>
    question_domain: <domain_of_question>
    reasoning_type: <analytical/creative/systems/etc>
    knowledge_required: <key_knowledge_areas>
    knowledge_base: {}
    branches: 3

workflow:
  steps:

    - name: "json_persistence"
      type: "file_operations"
      functions:
        read_json: "load knowledge base from file if exists"
        write_json: "save knowledge base to file after updates"
      path: "./reasoning_kb.json"

    - name: "problem_analysis"
      type: "thought_decomposer"
      input: "{problem_json}"
      extract:
        - problem_type
        - constraints
        - requirements
        - thought_granularity
      instructions:
        - "Classify question by domain and type"
        - "Identify key terms and break into sub-questions"
        - "Determine thought decomposition level (word/line/paragraph)"
        - "Select search algorithm (BFS/DFS) based on problem structure"

    - name: "thought_generator"
      type: "divergent_thinking"
      strategies:
        independent_sampling: "Generate diverse thoughts independently"
        sequential_proposals: "Propose thoughts sequentially with alternatives"
      parameters:
        k: 5
        temperature: 0.7
      instructions:
        - "Generate k diverse thoughts for the current state"
        - "Use independent sampling for paragraph-level"
        - "Use sequential proposal for word/equation-level"
        - "Include confidence level for each thought"

    - name: "state_evaluator"
      type: "heuristic_evaluator"
      methods:
        independent_evaluation: "Evaluate states independently"
        comparative_voting: "Vote across states to select the best"
      parameters:
        voting_samples: 3
        scoring_scheme: ["sure", "maybe", "impossible"]
      instructions:
        - "Evaluate progress toward solving the problem"
        - "Use deliberate reasoning as heuristic"
        - "Use lookahead for viability"
        - "Eliminate impossible states"

    - name: "tree_search"
      type: "search_algorithm"
      algorithms:
        bfs:
          max_depth: 3
          beam_width: 5
        dfs:
          max_depth: 10
          pruning_threshold: 0.4
      instructions:
        - "Use BFS for problems with limited depth (≤3)"
        - "Use DFS for deep exploration and backtracking"
        - "Prune impossible subtrees"

    - name: "solution_synthesis"
      type: "synthesizer"
      action: "extract_best_solution"
      output: "candidate_solution"
      instructions:
        - "Extract best solution from tree exploration"
        - "For BFS: pick from final frontier"
        - "For DFS: choose deepest successful path"
        - "Document reasoning path"

    - name: "actor_critic_refinement"
      type: "iterative_improvement"
      depends_on: "solution_synthesis"
      max_iterations: 3
      actor:
        role: "solution_improver"
        action: "refine_candidate_solution"
        focus: ["fix_issues", "optimize_logic", "improve_readability"]
      critic:
        role: "solution_evaluator"
        action: "identify_problems"
        checks: ["correctness", "edge_cases", "performance", "clarity"]
      actor_proposes: "improved_solution"
      critic_evaluates: "solution_quality"
      instructions:
        - "Actor improves the solution"
        - "Critic evaluates each iteration"
        - "Exit loop when threshold met or iterations maxed"
      if: critic_score > 0.9
      then: "accept_solution"
      else: "iterate_with_feedback"

    - name: "code_generator"
      type: "code_generator"
      depends_on: "actor_critic_refinement"
      parameters:
        language: "target_language"
        style: "code_style"
        documentation: "comprehensive"
      output: "final_implementation"
      instructions:
        - "Turn solution into executable code"
        - "Add comments and error handling"
        - "Ensure readability and structure"

    - name: "knowledge_update"
      type: "memory_manager"
      action: "persist_knowledge"
      target: "knowledge_base"
      instructions:
        - "Log new knowledge from the process"
        - "Update existing concepts"
        - "Save knowledge base"
        - "Update context block"
```

---

## 🧭 Step-by-Step Process Guidelines

### 1. Problem Analysis and Thought Decomposition
- Determine problem structure and complexity
- Choose decomposition level:
  - **Word-level**: Crosswords, constrained logic
  - **Line-level**: Equations, mathematical steps
  - **Paragraph-level**: Planning, creative tasks
- Select search strategy:
  - **BFS**: Shallow trees (≤3)
  - **DFS**: Deep trees with backtracking
- Identify knowledge gaps

### 2. Knowledge Acquisition
- Gather facts using tools or research
- Resolve contradictions
- Structure findings clearly

### 3. Thought Generation & Evaluation
- Generate k thoughts at each state
- Score with “sure”, “maybe”, “impossible”
- Use comparative voting and pruning
- Explore tree of thoughts with selected algorithm

### 4. Solution Synthesis
- Extract promising path or node
- Reconstruct full reasoning path
- Present clearly based on problem type

### 5. Review, Refinement, Knowledge Update
- Use actor-critic loop for refinement
- Improve logic, fix issues, clarify
- Log final learnings to knowledge base

---

## 🧪 Practical Examples

### Example 1: Game of 24 (Line-Level, BFS)
**Input**: 4 9 10 13  
**Goal**: Make 24

```text
# Step 1: Initial Thoughts
T1: 13 - 9 = 4    → [4, 4, 10]
T2: 4 × 9 = 36    → [10, 13, 36]
T3: 10 - 4 = 6    → [6, 9, 13]

# Step 2: Evaluation
T1: sure
T2: maybe
T3: likely

# Step 3: Continue Best Paths
From T1: 4 × 10 = 40 → [4, 40]
From T3: 13 - 6 = 7  → [7, 9]

# Final Solution
(13 - 9) × (10 - 4) = 4 × 6 = 24
```

---

### Example 2: Creative Writing (Paragraph-Level, BFS)
**Prompt**: *Write a story ending with “The door slowly closed behind them.”*

```text
# Plans
P1: Mystery - escape from vault
P2: Romance - moving into house
P3: Horror - enter haunted place

# Chosen: Plan 3

# Generated Passages → Best Selected
# Final Output: Full story ending with prompt
```

---

### Example 3: Mini Crosswords (Word-Level, DFS)
```text
# Start: Fill constrained
h1: "SNOW" (high confidence)

# Try h2: "VIBRA" → causes v3 conflict

# Backtrack
Try h2: "WINCH" → fits

# Continue solving
```

---