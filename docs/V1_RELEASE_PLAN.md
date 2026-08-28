# V1 Release Plan

This document defines the intended public V1 target for ROTDB. It exists to keep implementation decisions, review feedback,
and future session context aligned around the same release bar.

## Release Definition

V1 is a trust-grade RuneScape 3 rotation simulator for serious PvM players.

It should answer two questions:

1. Is this rotation viable in terms of adrenaline, cooldowns, equipment state, and required combat state?
2. What damage does this rotation produce, both cumulatively and per tick?

The user should be able to build a planned rotation, take it to a boss, and reasonably expect the simulation to match the
planned combat behavior for all supported mechanics.

Accuracy and UX are the highest priorities. Maintainability matters because the project has broad combat coverage, but
maintainability should not come at the cost of knowingly incorrect combat behavior.

## Target Audience

V1 is for high-level PvMers who already understand RuneScape combat and want a precise visual tool for dissecting and
sharing rotations.

The product should assume the user knows what abilities, buffs, presets, and swaps are. It should not hide complexity, but
it should make the rotation state easy to inspect.

## V1 Scope

V1 should support:

- all combat styles
- hybridding and mid-rotation style swaps
- all abilities and special attacks
- ammo, spell, prayer, perk, enchantment, passive, and equipment interactions that affect supported damage or viability
- tick-by-tick rotation simulation
- cumulative and per-tick damage output
- adrenaline and cooldown validation
- ordered same-tick actions
- full equipment presets and individual slot swaps
- user-placed buffs, target debuffs, and account-level passive settings
- supported familiar-provided buffs
- static boss target selection from the database
- account-saved rotations
- shareable rotations for users without accounts
- manual database migrations

For V1, boss support means selectable target definitions, not full encounter scripts. Bosses with enrage scaling, hard mode
variants, normal mode variants, or special target forms may need additional target records or target metadata.

## Out Of Scope For V1

These should not block V1 unless the release bar changes:

- live phase tracking
- full boss encounter scripting
- phase-cap enforcement
- active boss HP tracking
- boss healing simulation
- multi-target encounter routing for bosses like Arch-Glacor, Amascut, Zamorak, or Telos
- familiar damage output
- hit chance UX and expected damage from hitchance
- Discord bot integration
- public profile browsing of user rotations

V1 may still include explicit warnings or known limitation notes for these areas.

## Simulation Rules

The simulator should be tick-accurate.

Each tick should preserve enough state for the frontend to inspect:

- equipment active at the relevant point in the tick
- ordered actions taken on the tick
- abilities released
- hits scheduled
- hits landed
- buffs gained, consumed, expired, or removed
- target debuffs gained, consumed, expired, or removed
- adrenaline changes
- cooldown changes
- warnings and hard invalidations
- damage per hit
- total damage per tick
- cumulative damage after the tick

Same-tick action order must follow user input order. For example, `ranged auto + melee preset` should calculate the auto
with the ranged state, while `melee preset + melee auto` should calculate the auto with the melee state.

The simulator should prefer hard state correction where fake damage would mislead the user. For example, if a necromancy
offhand is removed for more than zero ticks, conjures should be removed rather than allowed to continue producing damage.

## Damage Reporting

V1 should assume 100% hit chance unless a later decision explicitly brings hitchance into scope.

Damage reporting should include:

- minimum damage
- average or expected damage
- maximum damage
- cumulative damage
- per-tick damage
- probability-based views where supported by the calculation model

The desired probability UX is for the user to be able to inspect rotations by percentile or mode, such as:

- expected average rotation
- top 90% outcome
- bottom 10% outcome

If full probability views are not ready before other V1 foundations, they should be tracked as a visible release risk
rather than silently dropped.

## Frontend Requirements

The frontend should be desktop-first and responsive.

V1 should provide:

- a grid-based rotation display
- drag/search ability insertion
- setup and equipment display
- named presets
- individual equipment slot swaps
- account/global passive configuration
- tick inspection
- warnings and invalid-state display
- saved rotations for authenticated users
- shareable rotation links or exports

An ideal V1 or near-V1 workflow includes typed rotation entry, such as:

```text
s ezk -> r ezk + meteor -> divert -> zerk -> barge -> op -> gflurry -> preset_ranged
```

The typed format should support ability placement and preset or equipment swaps without requiring the user to manually
populate every timeline cell.

## Data Strategy

The database should be authoritative for V1 equipment and target data.

Reference data means app-owned combat data such as:

- abilities
- ability hits
- equipment
- equipment effects
- target definitions
- boss tags
- passives
- supported buffs and effects

The likely long-term process is hybrid:

1. scrape base data from the RuneScape Wiki
2. review or normalize enum mappings
3. add ROTDB-specific effects and tags
4. ship changes through manual migrations

Manual migrations are acceptable for now. Once a migration is applied anywhere shared, it should not be edited; follow-up
fixes should be made with a new migration.

## Testing Strategy

The test strategy should optimize for trust, not percentage coverage.

Highest-priority tests:

- damage calculation golden tests
- style-specific modifier tests
- special attack tests
- buff interaction tests
- proc and probability behavior tests
- rotation simulation regression tests
- tick-ordering tests
- equipment swap tests
- invalid-state and warning tests
- conjure and command lifecycle tests

Tests should focus on known combat truths, high-risk interactions, and bug-prone timing rules. Broad coverage is useful,
but only if the assertions prove behavior that matters.

## Release Stages

V1 should not go directly from local development to public release.

Recommended stages:

1. Internal correctness pass
2. Private alpha with trusted PvMers
3. Data correction and UX pass
4. Wider beta
5. Public V1

Each stage should produce specific bug reports, known limitations, and test cases where possible.

## Risk Register

Highest risks:

- incorrect damage values
- incorrect tick timing
- incorrect buff or debuff lifecycle
- incorrect equipment state during swaps
- missing style-specific interactions
- stale or incorrectly tagged reference data
- frontend hiding important simulator state from users
- trying to model boss encounter mechanics before the core simulator is trusted

The biggest product risk is trust loss. If a user finds a large incorrect value, they may reasonably doubt the entire
application. V1 should therefore be conservative about claiming support for mechanics that are not verified.

## Requirement Checklist

Use these statuses:

- `[ ]` Not started
- `[~]` In progress
- `[?]` Needs review or domain confirmation
- `[x]` Complete

When work appears ready to mark complete, the assistant should explicitly notify the user instead of silently updating the
status. The user remains responsible for deciding whether a requirement is actually checked off.

## Daily Planning

Daily goals are tracked in `docs/DAILY_GOALS.md`.

The daily plan should be derived from this release plan, but it should not simply copy broad V1 requirements. Each day
should contain concrete, measurable goals sized for roughly 10 hours of careful project work.

A good daily plan usually contains:

- one major implementation or design goal
- one verification or regression-test goal
- one review, cleanup, or documentation goal
- optional stretch work

If a daily goal appears to complete one of the V1 checklist items below, the assistant should explicitly call that out and
ask the user whether the requirement should be checked off.

### Core Simulation

- `[~]` Tick-based rotation timeline
- `[~]` Ability release and hit landing pipeline
- `[~]` Buff and debuff lifecycle tracking
- `[~]` Adrenaline viability
- `[~]` Cooldown viability
- `[ ]` Ordered same-tick action processing
- `[ ]` Mid-rotation equipment preset swaps
- `[ ]` Mid-rotation individual equipment slot swaps
- `[ ]` User-visible tick inspection model

### Combat Coverage

- `[~]` Necromancy support
- `[ ]` Melee support review
- `[ ]` Ranged support review
- `[ ]` Magic support review
- `[ ]` Defensive and utility ability review
- `[ ]` Special attack coverage review
- `[ ]` Prayer interaction review
- `[ ]` Ammo interaction review
- `[ ]` Spell interaction review
- `[ ]` Perk and enchantment interaction review

### Necromancy And Conjures

- `[~]` Skeleton warrior recurring damage
- `[~]` Putrid zombie split hit sources
- `[~]` Vengeful ghost damage and Haunted behavior
- `[ ]` Necromancy offhand creation requirements
- `[ ]` Necromancy offhand maintenance requirements
- `[ ]` Hard conjure removal on invalid offhand state
- `[ ]` Command validation when conjure is absent
- `[ ]` Conjure lifecycle regression tests

### Damage Output

- `[~]` Per-hit damage
- `[~]` Per-tick damage
- `[~]` Cumulative damage
- `[ ]` Min damage reporting
- `[ ]` Average damage reporting
- `[ ]` Max damage reporting
- `[ ]` Probability percentile views

### Data And Migrations

- `[~]` Manual Flyway migration setup
- `[ ]` Production-to-local reference data alignment
- `[ ]` Boss display tags
- `[ ]` Enrage or scalable boss target metadata
- `[ ]` Normal mode and hard mode target modeling
- `[ ]` Reference data update workflow

### Frontend

- `[ ]` Grid-based rotation builder
- `[ ]` Ability drag/search insertion
- `[ ]` Setup and preset display
- `[ ]` Equipment swap controls
- `[ ]` Account/global passive controls
- `[ ]` Tick inspection UI
- `[ ]` Warning and invalid-state UI
- `[ ]` Typed rotation parser
- `[ ]` Rotation sharing
- `[ ]` Account-saved rotations

### Release Readiness

- `[ ]` Golden damage test suite
- `[ ]` Rotation regression suite
- `[ ]` Known limitations page or section
- `[ ]` Private alpha feedback loop
- `[ ]` Wider beta feedback loop
- `[ ]` Public V1 readiness review

## Current Recommended Focus

The next implementation focus should be finishing the conjure and equipment-state foundation before broadening to the
remaining conjures, commands, and style coverage.

Recommended order:

1. finish necromancy offhand creation and maintenance rules
2. finish hard conjure removal behavior
3. add command validation for absent conjures
4. add regression tests for conjure lifecycle behavior
5. define the ordered same-tick action model
6. implement equipment preset and slot swaps
7. return to broader style and special attack coverage
