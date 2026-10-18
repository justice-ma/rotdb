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

### Where the Arisen Flesh flat add sits in the damage formula

Why it matters:
- The add currently lives in the on-NPC `FlatAddResolver`: after the on-NPC multipliers (including Vulnerability via
  `TargetStatusMultiplierResolver`) and stored Haunted damage, before `PostHauntedMultiplierResolver` and
  `AbilityMultiplierResolver`. If the game applies it earlier, the on-NPC multipliers should scale it and ROTDB
  under-reports Putrid Zombie damage under Arisen Flesh.

Evidence checked:
- External research (2026-10-18): no source places Arisen Flesh in the damage order, and the RS Wiki ability damage
  order page does not list it. Released 12 Oct 2026; no published testing yet.
- The wiki places comparable "bonus damage is added" effects (Frostblades, Am-zi, Searing Winds, Crimson Salvo) at the
  end of the PvM-only multiplier section, before Core and On-NPC. ROTDB models Crimson Salvo in `CoreFlatAddResolver`.

Conflicting evidence:
- The current on-NPC placement is a placeholder chosen without evidence; the wiki analogy points to the core stage.

Would resolve with:
- In-game testing: if the add appears as its own hitsplat, compare it against a target with and without Vulnerability.

Status:
- unresolved
