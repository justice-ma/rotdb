# Calculator Guardrails

The calculator is considered stable and mostly complete.

It should be protected from unnecessary changes.

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

## Calculator Role

The calculator is responsible for producing accurate combat calculation results based on provided combat state.

It should not become responsible for full rotation simulation unless there is a strong reason.

## Stability Rule

Do not modify calculator logic unless there is a clear reason.

Acceptable reasons include:

- game update requiring formula changes
- discovered calculation bug
- missing edge case
- required API change for rotation builder integration
- correctness improvement

Unacceptable reasons include:

- making code look cleaner without benefit
- speculative refactoring
- forcing rotation-builder concepts into calculator internals
- changing stable APIs without a migration reason

## Required Explanation Before Change

Before recommending calculator changes, explain:

1. what currently exists
2. what limitation exists
3. why the change is necessary
4. what files/packages may be affected
5. what could break
6. how correctness will be verified

## Calculator API

The calculator API is not frozen forever.

It may change if there is a good reason.

However, changes should be intentional and explained.

## Relationship to Rotation Builder

The rotation builder should call the calculator with combat state for each relevant ability/action.

The calculator should be treated like a calculation engine.

The rotation builder should own timeline state, sequencing, validation, and tick-by-tick simulation.

## Dangerous Areas

All calculator files should be considered dangerous for the AI to modify.

The user may modify them when there is a good reason.

AI should treat calculator-related code as read-only unless explicitly instructed.