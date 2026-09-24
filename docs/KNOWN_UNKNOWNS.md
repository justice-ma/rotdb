# Known Unknowns

This document tracks unresolved, decision-relevant uncertainty.

Keep entries precise. Archive or update resolved entries so the list remains useful.

## Entry Template

```md
## <question>

Why it matters:
- <implementation, calculation, tick order, state behavior, tests, API behavior, or UX risk>

Evidence checked:
- <repository investigation, external research, user confirmation, or in-game testing>

Conflicting evidence:
- <if any>

Would resolve with:
- <external research, repository investigation, in-game testing, or user confirmation>

Status:
- unresolved
```

## Active Questions

### Exact same-tick lifecycle ordering between user actions and system events

Why it matters:
- This affects ordered action processing, releases, hit landings, expiries, state snapshots, and regression tests.

Evidence checked:
- Captured in `docs/PROJECT_STATE.md` as a known open question.

Conflicting evidence:
- None recorded yet.

Would resolve with:
- RuneScape mechanics research, repository investigation, and user domain confirmation.

Status:
- unresolved

### Frontend-visible representation of multiple ordered actions on one tick

Why it matters:
- This affects the tick inspection UI, typed rotation entry, and how warnings are attributed to user-authored actions.

Evidence checked:
- Captured in `docs/PROJECT_STATE.md` as a known open question.

Conflicting evidence:
- None recorded yet.

Would resolve with:
- Design analysis and user UX preference.

Status:
- unresolved

### Melee base damage when weapon tier exceeds Strength level

Why it matters:
- `MeleeBaseDamageResolver` produces low base ability damage when the weapon's damage tier is greater than the player's Strength level. The resolvers currently assume tier ≤ Strength, so under-levelled or low-level setups may produce wrong base damage without any error.

Evidence checked:
- Recorded as a code comment in `MeleeBaseDamageResolver` ("ASSUME TIER IS ALWAYS LESS THAN OR EQUAL TO STR"). The comment was removed during the 2026-09-24 ranged no-ammo work and moved here.
- No fixture in `CalculationBaseAbilityDamageTests` covers tier > Strength.

Conflicting evidence:
- None recorded yet.

Would resolve with:
- In-game readings of displayed base ability damage with Strength held below the weapon tier (one factor changed at a time), added as fixtures. The original note suggests contacting Akritia if the readings don't settle it.

Status:
- unresolved
