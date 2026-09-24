# AI Learning Journal

This is a concise record of transferable engineering lessons from ROTDB work.

Use it for patterns the user can reuse: ownership boundaries, debugging habits, missed clues, architecture instincts,
testing habits, and concepts that may need reinforcement. Avoid patronizing language and personal detail.

## Entry Template

```md
## YYYY-MM-DD - <lesson>

Situation:
- <what kind of issue this came from>

Lesson:
- <transferable pattern or habit>

Earlier signal:
- <clue that could make a similar issue faster next time>
```

## Entries

## 2026-09-24 - The game is the oracle; a verified formula is a measuring instrument

Situation:
- Base ability damage was wrong only for ranged weapons with no ammo. There was no documentation, and a newer
  community formula (User:Akritia/ability_damage, 12 June 2026) didn't reproduce in-game values either.

Lesson:
- When documentation is missing, change one input at a time in-game and read the displayed value. Once part of your
  model is verified (the with-ammo path), use it in reverse: solve for the unknown input that reproduces each reading.
  Four readings back-solved to "effective ammo tier = weapon tier / 3" for two-handed ranged weapons.
- Two implementations agreeing (ROTDB and weirdgloop's rs-rotations) is not corroboration when both come from the
  same source.
- Handedness matters: dual-wield and main-hand-only weapons did not follow the two-handed no-ammo rule. Take a reading
  for each weapon type before generalising a rule across them.

Earlier signal:
- "The value only ever increases with ammo" traced to `Math.min(mt, a)` with `a = 0`. In-game, low-tier ammo
  *lowered* damage, so the game had a non-zero baseline the code didn't.

## 2026-09-24 - Reassigning a parameter is not a side effect

Situation:
- `mhTier += 5` inside `ShardOfGenesisResolver` looked like the resolver was "modifying" state.

Lesson:
- By Fowler's CommandQuerySeparation definition, a query returns a value with no *observable* side effects. Changing a
  local copy of a primitive is invisible to the caller, so the method is a pure query. In this package, resolvers
  compute and return values, and the modifier writes to the context.
- The real smell was the same condition written three times, which is duplication and not a command/query problem.
- Needs reinforcement: SRP as Martin defines it is about *actors*, meaning who requests a change, not class size or
  "one purpose." Reference: Martin, "The Single Responsibility Principle" (Clean Code Blog, 2014).

Earlier signal:
- Before calling a method "mutating," ask who could observe the change.
