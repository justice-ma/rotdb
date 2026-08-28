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
