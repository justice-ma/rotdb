# RuneScape Ability Damage Simulator

A deterministic damage calculator and simulation engine for RuneScape abilities.  
Built to model real in-game combat math, modifier stacking, and multi-hit abilities with high accuracy.

## Overview

This project calculates the damage output of RuneScape abilities by running a structured **damage pipeline** over a clean input model (`DamageRequest`).

It supports:

- Multi-hit abilities
- Style-specific formulas (Melee / Ranged / Magic / Necromancy)
- Additive + multiplicative modifier layers
- Critical strike modeling
- Buffs, perks, prayers, relics, and gear effects
- Proc effects and injected hits (e.g., Perfect Equilibrium)

The goal is **accurate, explainable, and modular damage calculation** — not just rough estimates.

## Tech Stack

- **Backend:** Java (Spring Boot integration planned/in progress)
- **Build Tool:** Maven
- **Frontend (planned):** React
- **Database (planned):** PostgreSQL

## Architecture

The system is split into **three major layers**:

### 1) Input Layer

User inputs are captured in a clean input model, e.g. `DamageRequest`.

This includes:

- Equipment
- Ability
- Skills
- Buffs
- Prayers
- Perks
- Relics
- Target
- Ammo/Familiar/etc

This ensures the calculation engine **never touches raw user input directly**.

### 2) Engine Layer

The engine converts the request into a working context and runs the **damage pipeline**:

```java
DamageResult result = engine.calculateAbilityDamage(request);
```

Pipeline stages typically include:

1. Base damage calculation
2. Additive modifiers
3. Multiplicative modifiers
4. Hit-specific adjustments
5. Crit calculation
6. Proc injections
7. Final damage resolution

Each stage is modular and composable.

### 3) Output Layer

The final output is returned as `DamageResult`.

This contains:

- Final hit values (per hit)
- Min / Max / Avg damage
- Crit / Non-crit breakdown
- Total damage
- Optional debug trace (if enabled)

## Project Structure (Conceptual)

```
domain/
  DamageRequest
  DamageResult

engine/
  CalculationEngine
  AbilityDamagePipeline

model/
  context/
    CalculationContext
    AbilityContext
    HitContext
    PrayerContext
    BuffContext
    ...

rules/
  modifiers/
  injectors/
  calculators/

abilities/
  factories/
```

## Example Usage

```java
DamageRequest request = new DamageRequestBuilder()
    .equipment(equipment)
    .ability(AbilityId.ASSAULT)
    .skills(skills)
    .buffs(buffs)
    .prayers(prayers)
    .build();

DamageResult result = engine.calculateAbilityDamage(request);

System.out.println(result.getTotalDamage());
```

## Key Design Principles

### Deterministic pipeline
Every modifier runs in a strict order to match in-game behavior.

### Separation of concerns
- Input models never leak into calculation logic
- Rules are isolated into their own classes
- Each modifier is independently testable

### Hit-level simulation
Each ability hit is modeled independently, allowing:
- Per-hit crit chance
- Proc injection
- Hit-specific scaling

## Current Features

- Base damage formulas for all styles
- Multi-hit ability support
- Prayer + buff handling
- Crit calculation per hit
- Modifier layering system
- Debug output support
- Proc injection framework

## Planned Features

- Spring Boot REST API
- React frontend UI
- Preset saving via PostgreSQL
- Rotation simulation (multi-ability timelines)
- DPS graphing + comparisons
- Export/share builds

## Running the Project

### Prerequisites

- Java 17+
- Maven

### Run

```bash
mvn clean install
mvn spring-boot:run   # once backend is integrated
```

## Debugging Output

The engine supports:

- Always printing final damage
- Optional detailed modifier trace
- File output for long logs

## Contributing

To add a new modifier:

1. Create a class implementing `Modifier`
2. Insert it into the appropriate pipeline stage
3. Add any required context fields

## Why This Exists

RuneScape damage math is:

- Non-trivial
- Poorly documented in one place
- Full of layered edge cases

This project exists to provide a **clear, correct, and extensible implementation** of that system.

## Author

Justice Mazerolle
