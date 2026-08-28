# Daily Goals

This document is the active working tracker for project days. It should turn the V1 release plan into concrete,
measurable work that can be completed or reviewed in a single day.

Daily goals should assume roughly 10 hours of committed project work, including reading, thinking, implementation,
debugging, testing, cleanup, and review.

The user works at a human pace. Goals should be sized for careful understanding, not for AI-speed implementation.

## How To Use This Document

At the start of a project day:

1. Read `docs/SESSION_START_PROMPT.md`.
2. Read `docs/V1_RELEASE_PLAN.md`.
3. Read `docs/PROJECT_STATE.md`.
4. Read this file.
5. Inspect only the relevant code/docs needed to understand the current focus.
6. Generate 2-4 concrete goals for the day.
7. Include clear "done when" criteria for each goal.

At the end of a project day:

1. Mark completed goals.
2. Record blockers or unresolved questions.
3. Record any V1 release-plan requirements that appear ready for user confirmation.
4. Update `docs/PROJECT_STATE.md` if the current focus, open questions, or last known verification changed.
5. Write the recommended starting point for the next session.

The assistant should not silently mark V1 release requirements complete. If a requirement appears ready, the assistant
should tell the user and ask whether it should be checked off.

## Standing Update Permission

The assistant has standing permission to update this file and `docs/PROJECT_STATE.md` during a work session when:

- a daily goal's "done when" criteria are clearly met
- a blocker or unresolved question is discovered
- last known verification changes
- the next recommended starting point changes
- the current implementation focus materially changes

This standing permission does not apply to code files or broad roadmap changes.

The assistant must still ask before marking any requirement in `docs/V1_RELEASE_PLAN.md` complete.

## Goal Sizing Rules

A good project day usually has:

- one major implementation or design goal
- one verification or test goal
- one review, cleanup, or documentation goal
- optional stretch work

Avoid plans that contain many unrelated implementation goals. ROTDB's risk is correctness, not raw feature throughput.

Good daily goals are measurable:

- "Define the ordered timeline action model and identify all current call sites that need to dispatch through it."
- "Add regression tests for conjure removal after invalid necromancy offhand state."
- "Review melee special attacks and record which ones need calculator changes."

Weak daily goals are vague:

- "Work on conjures."
- "Improve tests."
- "Clean up rotation stuff."

## Daily Goal Template

```md
## YYYY-MM-DD

### Goal 1: <major goal>

Done when:
- <measurable condition>
- <measurable condition>
- <measurable condition>

Notes:
- <optional context>

### Goal 2: <verification goal>

Done when:
- <measurable condition>
- <measurable condition>

### Goal 3: <cleanup/review/doc goal>

Done when:
- <measurable condition>
- <measurable condition>

### Stretch

- <optional stretch goal>

### End-Of-Day Notes

Completed:
- <completed work>

Blocked:
- <blockers>

Ready For User Check-Off:
- <V1 requirements that may be ready>

Next Starting Point:
- <recommended next focus>
```

## Active Focus

Current focus comes from `docs/V1_RELEASE_PLAN.md`:

1. finish necromancy offhand creation and maintenance rules
2. finish hard conjure removal behavior
3. add command validation for absent conjures
4. add regression tests for conjure lifecycle behavior
5. define the ordered same-tick action model
6. implement equipment preset and slot swaps
7. return to broader style and special attack coverage

## 2026-07-22

### Goal 1: Re-Establish The Current Simulation Flow

Done when:

- `git status --short` has been reviewed so the current worktree state is understood
- the current user buff placement block in `RotationTimelineService` has been inspected
- the current ability placement/cast block in `RotationTimelineService` has been inspected
- system-driven events are identified and intentionally left outside the first ordered-action slice

Notes:

- This is not a code-prettification pass.
- The purpose is to make the next V1 foundation change without accidentally moving release, hit landing, expiry, or
  conjure recurrence behavior.

### Goal 2: Create The First TimelineAction Model Slice

Done when:

- the timeline action package exists in the simulation domain
- the shared action contract includes tick and order
- ability cast, ability release, and buff placement action variants are designed
- the first implemented action variants are intentionally chosen
- each action wraps exactly one payload
- no equipment or preset swap behavior is implemented yet

Notes:

- Current preferred package: `com.rotdb.simulation.domain.model.timeline`
- Current preferred model: sealed `TimelineAction` interface with concrete records.
- Ability casts and releases should be separate concepts because users can stall one ability and release it later.

### Goal 3: Route Existing Ability And Buff Placements Through Ordered User Actions

Done when:

- existing ability placements and buff placements are converted into `Map<Integer, List<TimelineAction>>`
- each tick processes user-authored actions in order
- buff actions reuse the existing buff processor behavior
- ability cast and release actions reuse the existing ability processing behavior where appropriate
- system-driven events remain in their existing lifecycle positions unless a test proves they must move

Notes:

- The goal is to change orchestration, not rewrite processors.
- Preserve current behavior first, then use tests to expose where same-tick ordering should intentionally differ.

### Goal 4: Add Or Precisely Define One Same-Tick Ordering Regression Test

Done when:

- a concrete same-tick ordering scenario is chosen
- the test explains why order matters
- the expected observable result is defined
- the test either passes or the failure is understood and recorded

Good candidate:

- a user-placed buff before an ability changes that ability's result
- the same ability before the buff does not receive the buff's effect

Stretch:

- add placeholder action variants for future preset and equipment slot swaps if the first slice is already stable

### End-Of-Day Notes

Completed:
- Not recorded yet.

Blocked:
- Not recorded yet.

Ready For User Check-Off:
- Not recorded yet.

Next Starting Point:
- Not recorded yet.

## 2026-07-20

### Goal 1: Define The Ordered Timeline Action Model

Done when:

- the intended package for timeline actions is decided
- the initial action variants are named
- the shared ordering contract is decided
- the conversion path from existing ability and buff placements is understood

Notes:

- Current preferred package: `com.rotdb.simulation.domain.model.timeline`
- Current preferred model: sealed `TimelineAction` interface with concrete action records.
- Initial variants should likely cover ability placements and buff placements first.

### Goal 2: Identify RotationTimelineService User-Action Refactor Points

Done when:

- the current user buff placement block is identified
- the current ability placement/cast block is identified
- system-driven tick events are separated from user-ordered actions
- the smallest safe orchestration change is clear

Notes:

- Scheduled hit landings, expiries, conjure recurrence, and post-hit effects should stay system-driven unless a specific
  ordering rule requires moving them.

### Goal 3: Implement The First Ordered-Action Slice

Done when:

- existing ability placements and buff placements can be wrapped into ordered timeline actions
- each tick can process user-authored actions in order
- existing processors are reused where possible
- equipment swaps are not implemented yet
- focused simulation tests pass or failures are understood

### Goal 4: Plan Or Add One Same-Tick Ordering Regression Test

Done when:

- there is a specific test scenario where same-tick order changes behavior
- the test states which order should win and why
- the expected observable result is defined

Stretch:

- add placeholder action variants for preset swaps and equipment slot swaps if they do not complicate the first slice

### End-Of-Day Notes

Completed:
- Not recorded yet.

Blocked:
- Not recorded yet.

Ready For User Check-Off:
- Not recorded yet.

Next Starting Point:
- Not recorded yet.
