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

### End-Of-Day Notes (executed 2026-09-03)

Completed:

- **Goal 1 (provenance), met with one deliberate exception.** The fixture record carries `provenance` (free text:
  gear, permanent unlocks, stat state) and `observedOn` (`LocalDate`). `1924` is confirmed as an in-game observation,
  not calculator output. The VERIFIED / CHARACTERIZATION split was considered and **declined by the user**: every
  fixture in this suite is an in-game observation, so the distinction carries no information here. Revisit only if a
  characterization fixture is ever added.
- **Goal 2 (parameterized fixture table), met.** `@ParameterizedTest` + `@MethodSource("fixtures")` over a nested
  `BaseAbilityDamageFixture` record. `toString()` returns observation date + provenance, so the test report names rows.
  The assertion reads `fixture.expected`, `fixture.tolerance`, and `fixture.provenance`. `AttackHandedness`
  (`MAINHAND_ONLY` / `TWO_HANDED` / `DUAL_WIELD`) replaced the three booleans that could contradict each other.
- **Goal 3 (verify what runs), partially met.** Surefire 3.5.4 is confirmed configured and running the class — the
  earlier suspicion that no tests execute is ruled out for this suite. Still only one fixture row; style coverage did
  not start.
- **Goal 4 (cleanup), met.** `engine`, `copier`, `sampleRequest(...)`, the three unused `sample*Context()` methods and
  the `SimulationStateSnapshotCopier` import are gone. The `tier_100_...` name is moot — the method is now
  `baseAbilityDamageTests` and effective tier is described in the row's provenance.
- **New, unplanned: `BaseCombatState` extracted** (`src/test/java/com/rotdb/calculation/BaseCombatState.java`). Holds
  permanent account state only, returns a fresh graph per call. Ability and spell were both deliberately *excluded* as
  per-fixture axes — see the decision note below.

Verified:

- `mvn test -Dtest=CalculationBaseAbilityDamageTests` passes. The melee dual-wield fixture holds at `1924 ± 5` when
  built from `BaseCombatState.baseState()` rather than the old inline context, confirming the two were equivalent on
  the base-damage path.

Decisions worth remembering:

- These context classes are DTOs with **no constructor invariants** — `new XContext()` leaves collections and boxed
  fields null (`BuffContext.buffSet`, `PerkContext.perk`/`genocidalRank`, `PrayerContext.selected`,
  `TargetContext.tags`). Anything building one owns initializing all of them. Four separate NPE-on-use defects came
  from assuming otherwise.
- Base state holds what is permanent; the fixture holds what varies. Ability and spell are axes, not state — leaving
  them unset makes a malformed row fail loudly instead of silently computing a plausible wrong number.
- Boosted stats are *derived* (`StatBoostModifier` resets to base, then applies `potionBuffs`). The axis is therefore
  the potion, never a hand-set boosted stat, which would test a state the engine cannot produce.

Blocked:
- Nothing.

Ready For User Check-Off:
- Nothing on the V1 checklist.

Next Starting Point:
- Add the `shardable = false` melee row. Cheapest row that can actually fail, and it proves the Shard of Genesis +5 is
  applied rather than inherited from row one.

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

### End-Of-Day Notes (executed 2026-09-03)

Completed:

- **Goal 1 (provenance), met with one deliberate exception.** The fixture record carries `provenance` (free text:
  gear, permanent unlocks, stat state) and `observedOn` (`LocalDate`). `1924` is confirmed as an in-game observation,
  not calculator output. The VERIFIED / CHARACTERIZATION split was considered and **declined by the user**: every
  fixture in this suite is an in-game observation, so the distinction carries no information here. Revisit only if a
  characterization fixture is ever added.
- **Goal 2 (parameterized fixture table), met.** `@ParameterizedTest` + `@MethodSource("fixtures")` over a nested
  `BaseAbilityDamageFixture` record. `toString()` returns observation date + provenance, so the test report names rows.
  The assertion reads `fixture.expected`, `fixture.tolerance`, and `fixture.provenance`. `AttackHandedness`
  (`MAINHAND_ONLY` / `TWO_HANDED` / `DUAL_WIELD`) replaced the three booleans that could contradict each other.
- **Goal 3 (verify what runs), partially met.** Surefire 3.5.4 is confirmed configured and running the class — the
  earlier suspicion that no tests execute is ruled out for this suite. Still only one fixture row; style coverage did
  not start.
- **Goal 4 (cleanup), met.** `engine`, `copier`, `sampleRequest(...)`, the three unused `sample*Context()` methods and
  the `SimulationStateSnapshotCopier` import are gone. The `tier_100_...` name is moot — the method is now
  `baseAbilityDamageTests` and effective tier is described in the row's provenance.
- **New, unplanned: `BaseCombatState` extracted** (`src/test/java/com/rotdb/calculation/BaseCombatState.java`). Holds
  permanent account state only, returns a fresh graph per call. Ability and spell were both deliberately *excluded* as
  per-fixture axes — see the decision note below.

Verified:

- `mvn test -Dtest=CalculationBaseAbilityDamageTests` passes. The melee dual-wield fixture holds at `1924 ± 5` when
  built from `BaseCombatState.baseState()` rather than the old inline context, confirming the two were equivalent on
  the base-damage path.

Decisions worth remembering:

- These context classes are DTOs with **no constructor invariants** — `new XContext()` leaves collections and boxed
  fields null (`BuffContext.buffSet`, `PerkContext.perk`/`genocidalRank`, `PrayerContext.selected`,
  `TargetContext.tags`). Anything building one owns initializing all of them. Four separate NPE-on-use defects came
  from assuming otherwise.
- Base state holds what is permanent; the fixture holds what varies. Ability and spell are axes, not state — leaving
  them unset makes a malformed row fail loudly instead of silently computing a plausible wrong number.
- Boosted stats are *derived* (`StatBoostModifier` resets to base, then applies `potionBuffs`). The axis is therefore
  the potion, never a hand-set boosted stat, which would test a state the engine cannot produce.

Blocked:
- Nothing.

Ready For User Check-Off:
- Nothing on the V1 checklist.

Next Starting Point:
- Add the `shardable = false` melee row. Cheapest row that can actually fail, and it proves the Shard of Genesis +5 is
  applied rather than inherited from row one.

## 2026-09-01

Carried forward unchanged from the 2026-08-29 plan, which was written but not started. Verified at the
start of this session: the `main` merge is still `19b9025`, `CalculationBaseAbilityDamageTests` is still
the single-fixture one-method shape with the `engine`, `copier`, and `sampleRequest(...)` leftovers and
the `SimulationStateSnapshotCopier` import still present. Calculation-domain regression testing remains
the active track; simulation timeline work is intentionally parked.

`src/test/java/com/rotdb/calculation/CalculationBaseAbilityDamageTests.java` currently holds one melee
dual-wield fixture asserting `1924` within a tolerance of `5`. That number has no recorded source yet,
which is the first thing to settle.

### Goal 1: Settle Expected-Value Provenance For Base Ability Damage

Done when:

- it is decided and written down whether fixtures represent real observed in-game states or synthetic isolation cases
- the melee dual-wield fixture's `1924` is either confirmed against an in-game observation or relabelled as characterization
- every fixture carries the account state it was observed under (permanent buffs active, gear, stats, date)
- the distinction between a VERIFIED fixture and a CHARACTERIZATION fixture is visible in the file, not implied

Notes:

- Expected values come from in-game observation; the calculator is the approximation under test.
- Tolerance is therefore absolute (points), not relative (percent) — the deviation is roughly constant, not proportional.
- `sampleBuffs()` bakes in REAPERSCREW and SHARDOFGENESIS as permanent account unlocks. This is correct, but it means
  adding a future permanent buff silently invalidates every expected value recorded before it. That is the specific
  hazard the provenance record exists to catch.

### Goal 2: Convert The Suite To A Parameterized Fixture Table

Done when:

- `@ParameterizedTest` + `@MethodSource` replaces the one-method-per-case shape
- a fixture record carries inputs, expected value, tolerance, and provenance as constructor arguments
- provenance cannot be omitted when adding a case
- the assertion failure message reports the observed delta and the fixture's conditions
- the per-case setter mutation is consolidated into a single context builder

Notes:

- `junit-jupiter-params` 6.0.3 is already on the test classpath via `spring-boot-starter-test`; no pom change needed.
- Override `toString()` on the fixture record so the test report shows names rather than `[1]`, `[2]`.
- Doing this at one fixture is much cheaper than at twelve.

### Goal 3: Verify What Actually Runs, Then Extend Coverage

Done when:

- `mvn test` has been run and the actual executed test count is known
- the `DamageControllerTest.java` / `class DamageControllerIT` naming mismatch is confirmed or ruled out
  (Surefire matches `*Test`, Failsafe matches `*IT`; the pom configures neither)
- a decision is recorded on whether those four controller tests should run, be renamed, or be replaced
- base damage fixtures exist for more than one style

Notes:

- Style coverage is really style x weapon configuration: magic alone has `twoHand`, `dualWield`, and `mainhandOnly`.
- Base damage resolvers are static methods on primitives — no Spring, no database, fast.

### Goal 4: Cleanup And Record Open Questions

Done when:

- unused leftovers are removed from the test: `engine`, `copier`, `sampleRequest(...)`, and the three unused `sample*Context()` methods
- the `com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier` import is gone (calculation suite should not reach into simulation)
- the `tier_100_...` test name is reconciled with the tier-95 setters plus the Shard of Genesis +5, or the effective tier is explained in the file
- `docs/KNOWN_UNKNOWNS.md` records that the true base-damage formula is unpublished, the calculator approximates it,
  and the per-style deviation is an open measurement

### Stretch

- Extract the context builder far enough that a second modifier tier (crit, additive, multiplicative) could reuse it.

### End-Of-Day Notes (executed 2026-09-03)

Completed:

- **Goal 1 (provenance), met with one deliberate exception.** The fixture record carries `provenance` (free text:
  gear, permanent unlocks, stat state) and `observedOn` (`LocalDate`). `1924` is confirmed as an in-game observation,
  not calculator output. The VERIFIED / CHARACTERIZATION split was considered and **declined by the user**: every
  fixture in this suite is an in-game observation, so the distinction carries no information here. Revisit only if a
  characterization fixture is ever added.
- **Goal 2 (parameterized fixture table), met.** `@ParameterizedTest` + `@MethodSource("fixtures")` over a nested
  `BaseAbilityDamageFixture` record. `toString()` returns observation date + provenance, so the test report names rows.
  The assertion reads `fixture.expected`, `fixture.tolerance`, and `fixture.provenance`. `AttackHandedness`
  (`MAINHAND_ONLY` / `TWO_HANDED` / `DUAL_WIELD`) replaced the three booleans that could contradict each other.
- **Goal 3 (verify what runs), partially met.** Surefire 3.5.4 is confirmed configured and running the class — the
  earlier suspicion that no tests execute is ruled out for this suite. Still only one fixture row; style coverage did
  not start.
- **Goal 4 (cleanup), met.** `engine`, `copier`, `sampleRequest(...)`, the three unused `sample*Context()` methods and
  the `SimulationStateSnapshotCopier` import are gone. The `tier_100_...` name is moot — the method is now
  `baseAbilityDamageTests` and effective tier is described in the row's provenance.
- **New, unplanned: `BaseCombatState` extracted** (`src/test/java/com/rotdb/calculation/BaseCombatState.java`). Holds
  permanent account state only, returns a fresh graph per call. Ability and spell were both deliberately *excluded* as
  per-fixture axes — see the decision note below.

Verified:

- `mvn test -Dtest=CalculationBaseAbilityDamageTests` passes. The melee dual-wield fixture holds at `1924 ± 5` when
  built from `BaseCombatState.baseState()` rather than the old inline context, confirming the two were equivalent on
  the base-damage path.

Decisions worth remembering:

- These context classes are DTOs with **no constructor invariants** — `new XContext()` leaves collections and boxed
  fields null (`BuffContext.buffSet`, `PerkContext.perk`/`genocidalRank`, `PrayerContext.selected`,
  `TargetContext.tags`). Anything building one owns initializing all of them. Four separate NPE-on-use defects came
  from assuming otherwise.
- Base state holds what is permanent; the fixture holds what varies. Ability and spell are axes, not state — leaving
  them unset makes a malformed row fail loudly instead of silently computing a plausible wrong number.
- Boosted stats are *derived* (`StatBoostModifier` resets to base, then applies `potionBuffs`). The axis is therefore
  the potion, never a hand-set boosted stat, which would test a state the engine cannot produce.

Blocked:
- Nothing.

Ready For User Check-Off:
- Nothing on the V1 checklist.

Next Starting Point:
- Add the `shardable = false` melee row. Cheapest row that can actually fail, and it proves the Shard of Genesis +5 is
  applied rather than inherited from row one.

## 2026-09-04

Continues the calculation-domain regression testing track. The fixture shape is settled and verified at one row; today
is about proving the axes actually work and getting real coverage into the table. Simulation timeline work stays parked.

Entry state: `CalculationBaseAbilityDamageTests` holds one melee dual-wield row passing at `1924 ± 5`.
`BaseCombatState` supplies permanent account state; ability and spell are unset by design.

### Goal 1: Prove The Axes Before Trusting Them

Every axis added yesterday is exercised by exactly one value, so none of them are known to work. A field that silently
does nothing still passes.

Done when:

- a `shardable = false` melee row exists and its expected value differs from row one by the Shard of Genesis margin
- `MAINHAND_ONLY` and `TWO_HANDED` melee rows exist, and each demonstrably routes to a different resolver branch
- `armourBonus` is exercised by at least one row with a non-zero value
- any axis that turns out not to change the result is either fixed or removed — not left in place

Notes:

- `armourBonus` is applied to the body slot as a lump sum. `getTotalStrength()` sums slots, so this is arithmetically
  equivalent, but it means the row describes a total, not a real gear set. Keep that in the provenance text.
- A row whose expected value is unchanged when an axis changes is evidence the axis is not wired, not evidence the
  mechanic does nothing.

### Goal 2: Guard Malformed Rows Before Writing Magic

Done when:

- a magic row without a spell fails with a message naming the row, not an NPE inside `BaseAbilityDamageModifier:57`
- the `ammoTier`-ignored-for-magic overlap is either rejected by the same guard or the field is restructured
- it is decided whether `NECROMANCY` + `TWO_HANDED` should be expressible at all

Notes:

- `resolveBase` has no two-handed branch for necromancy (`dw ? dualWield : mainhandOnly`), so such a row silently
  routes to mainhand-only. If necromancy has no two-handed weapons in game, the table should not be able to express it.
- Guards belong at the point the fixture is applied, before the modifier runs.

### Goal 3: Style Coverage

Done when:

- ranged, magic, and necromancy rows exist alongside melee
- each style covers its real weapon configurations (magic has all three; necromancy likely two)
- every row carries its own in-game observation and date

Notes:

- Ranged has a wrinkle: `effectiveAmmoTier` substitutes `max(mainhand, offhand)` when ammo tier is 0 and the weapon is
  thrown or a chargebow. Worth a row that lands on that path deliberately.
- This is the goal most likely to overrun. Observing values in game is the slow part, not writing rows.

### Goal 4: Close The Remaining Documentation Debt

Done when:

- `docs/KNOWN_UNKNOWNS.md` records that the true base-damage formula is unpublished, that the calculator approximates
  it, and that per-style deviation is an open measurement
- a decision is recorded on the `DamageControllerTest.java` / `class DamageControllerIT` mismatch — Surefire is
  confirmed working, so the question is now only whether those four tests are named correctly

### Stretch

- Start a second modifier suite (crit is the natural next tier) reusing `BaseCombatState`. This is the real test of
  whether base state generalizes; if the crit suite needs a different base, the split is wrong somewhere.
- If it does generalize, that is the point to reconsider moving `BaseCombatState` to a shared test package.

### End-Of-Day Notes

Completed:
- Not recorded yet.

Blocked:
- Not recorded yet.

Ready For User Check-Off:
- Not recorded yet.

Next Starting Point:
- Not recorded yet.
