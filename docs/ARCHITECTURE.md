# Architecture

ROTDB should currently be treated as a modular monolith.

The application may eventually evolve toward service extraction, but microservices are not the current goal.

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

## Current Architectural Direction

The desired architecture is domain/feature-oriented.

Likely domains:

- calculator
- rotation builder
- shared combat model
- frontend/UI
- API layer

Avoid splitting packages only by technical role if it weakens domain clarity.

## Modular Monolith Goal

The project should be structured so that major domains are understandable and independently maintainable without needing
separate deployable services.

A good modular monolith should have:

- clear package boundaries
- clear ownership of state
- limited cross-domain leakage
- stable APIs between major modules
- simple local development
- low deployment complexity

## Why Not Microservices Yet

Microservices should be rejected unless there is a clear technical need.

Reasons to avoid microservices for now:

- added deployment complexity
- distributed debugging
- API/versioning overhead
- harder local development
- unnecessary operational burden
- unclear service boundaries while the domain is still evolving

## Possible Future Extraction Boundaries

If the app grows enough, possible future service boundaries may include:

- calculator service
- rotation simulation service
- ability/combat data service

These should not be extracted until the monolith has clean internal boundaries.

## Preferred Design Bias

Prefer maintainable abstractions when they clarify the domain, reduce coupling, or make future changes safer.

The project does not reject abstraction. It rejects unexplained or performative abstraction.

Interfaces are acceptable when they make sense.

Good reasons for an interface include:

- defining a meaningful domain boundary
- separating a caller from an implementation detail
- supporting multiple implementations
- improving testability
- protecting stable code from volatile code
- making future extension cleaner

Bad reasons for an interface include:

- adding one automatically for every class
- copying enterprise patterns without understanding them
- creating abstraction before the responsibility is clear
- hiding simple logic behind unnecessary indirection

If the user suggests an interface, the AI should not dismiss it automatically. Instead, it should help evaluate whether
the interface is pulling its weight.

## Shared Domain

Shared combat concepts may include:

- abilities
- buffs
- debuffs
- cooldowns
- adrenaline
- target state
- equipment
- perks
- combat style
- damage modifiers

Shared domain objects should be designed carefully because they can easily become dumping grounds.

## Boundary Questions

Before adding or moving logic, ask:

1. Is this calculator logic?
2. Is this rotation simulation logic?
3. Is this shared combat state?
4. Is this presentation/UI concern?
5. Who owns this data?
6. Who is allowed to mutate this data?
7. Does this belong in the stable calculator path?