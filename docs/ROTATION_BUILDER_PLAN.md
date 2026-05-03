# Rotation Builder Plan

The rotation builder is intended to help users plan RuneScape 3 combat rotations.

It should allow creativity while providing accurate warnings and damage tracking.

## Repository Access Rule

The AI must treat this repository as read-only.

The AI may inspect, explain, critique, and suggest.

The AI must not modify, create, delete, move, rename, format, or commit files unless the user explicitly grants
permission for that specific task.

Permission must be specific, not assumed from the general conversation.

Examples of valid permission:

- "Edit this file."
- "Create the test class."
- "Make the change we discussed."
- "Apply this patch."

Examples that are not permission:

- "What should I do?"
- "How would you implement this?"
- "Can you review this?"
- "What files are relevant?"
- "Let's plan this out."

## Definition of Rotation

A rotation has two main representations:

1. Builder/analyzer representation
2. Share/export representation

### Builder/Analyzer Representation

Inside the app, a rotation should be treated as a tick-based timeline.

The detailed builder view should allow the user to inspect the rotation tick by tick.

Each tick may display or track:

- ability/action
- adrenaline
- cooldowns
- damage
- cumulative damage
- buff/debuff state
- relevant combat state
- warnings

This detailed timeline is used for planning, validation, debugging, and damage analysis.

### Share/Export Representation

When a user shares a rotation with friends or the community, the exported version may be much simpler.

The exported representation can focus on the ability sequence itself rather than exposing every internal tick-level
detail.

The app should support detailed analysis internally while still allowing clean, readable external sharing.

## Validation Philosophy

Validation should guide, not block.

Users should be allowed to create invalid or unusual rotations.

The system should warn users rather than prevent them from continuing.

Warnings may include:

- ability used while on cooldown
- insufficient adrenaline
- buff missing
- ability timing issue
- impossible or suspicious sequence
- ambiguous combat state

Avoid hard errors unless the application cannot interpret the input at all.

## State to Track

The rotation builder should track at least:

- cooldowns
- buff state
- debuff state
- adrenaline
- damage per tick
- cumulative damage
- ability timing
- combat state

Design should leave room for future tracked values.

## Calculator Interaction

The rotation builder should call the calculator API with combat state for each ability/action.

The calculator should produce damage results.

The rotation builder should own:

- timeline construction
- tick advancement
- state mutation
- warnings
- rotation validity
- recalculation of later events

## Recalculation Requirement

If an ability affects later abilities in the timeline, later affected abilities should be updated.

This means the builder needs a model where changes can propagate forward through the timeline.

Important question:

When a user changes tick X, what downstream ticks need recalculation?

## Design Risks

Key risks:

- too much mutable state
- unclear ownership of combat state
- calculator and rotation logic becoming tangled
- validation becoming hard-blocking
- timeline updates becoming expensive or confusing
- premature abstraction
- insufficient tests

## Design Questions to Ask Often

1. Is this a warning or a hard failure?
2. Who owns this state?
3. Is this state derived or directly stored?
4. What happens when a previous tick changes?
5. Does this belong in the calculator or the rotation builder?
6. Can this be recalculated from earlier state?
7. What is the smallest version that proves the design?