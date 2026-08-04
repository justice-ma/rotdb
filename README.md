# rot_db

A RuneScape 3 ability damage calculator focused on deterministic combat math,
modifier ordering, and explainable hit breakdowns.

The live app is available at [rotdb.com](https://www.rotdb.com).

This project is in active development. Some newer mechanics are based on limited
testing or pre-release information and may change as better data becomes
available.

## What It Does

rot_db lets users build a combat setup and compare calculated ability damage
across RuneScape combat styles. The calculator models equipment, skills,
prayers, potions, perks, buffs, target state, familiars, spell selection, hit
caps, and conditional effects.

The backend runs combat calculations through a structured pipeline rather than
mixing UI state directly into the calculation logic. That keeps individual
mechanics easier to isolate, test, and adjust as game behavior is confirmed.

## Features

- Ability damage calculation for melee, ranged, magic, and necromancy
- Batch calculation for ability list comparisons
- Detailed single-ability breakdowns with hit-level results
- Crit and non-crit damage ranges
- Multi-hit abilities, channels, injected hits, and proc effects
- Equipment, prayer, perk, potion, familiar, target, and buff modifiers
- Saved local presets in the frontend
- Lightweight activity analytics for active client/session tracking
- Database migrations through Flyway

## Tech Stack

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- React
- Vite
- Railway

## Architecture

The application is split into a React frontend, a Spring Boot API, and a
PostgreSQL database.

```text
React UI
  |
  | REST API
  v
Spring Boot API
  |
  | calculation context
  v
Damage calculation engine
  |
  | equipment / target / ability data
  v
PostgreSQL
```

At a high level, calculation requests move through:

1. Request mapping
2. Player, equipment, ability, and target context construction
3. Base damage calculation
4. Modifier resolution
5. Hit-specific effects
6. Critical strike modeling
7. Proc and injected-hit handling
8. Final result mapping

## API Surface

Common calculation endpoints:

```text
POST /damage/calculate
POST /damage/calculate/batch
POST /damage/derived-stats
```

Activity endpoints:

```text
POST /analytics/heartbeat
GET  /analytics/active
```

The public frontend uses these APIs to calculate ability results and maintain
lightweight active-session metrics.

## Project Structure

```text
frontend/                     React frontend
src/main/java/com/rotdb/       Spring Boot backend
src/main/resources/            application config and Flyway migrations
src/test/                      backend tests
database/                      database snapshots and migration support
```

## Running Locally

Backend requirements:

- Java 21+
- Maven
- PostgreSQL

Frontend requirements:

- Node.js
- npm

Backend:

```bash
./mvnw spring-boot:run
```

Frontend:

```bash
cd frontend
npm install
npm run dev
```

Configuration is environment-specific. Local database credentials and production
secrets are intentionally not committed.

## Development Notes

RuneScape combat has many layered exceptions, so the implementation favors small
resolver classes and explicit pipeline stages over large conditional blocks. The
goal is not just to produce a final number, but to make incorrect modifier
ordering easier to find and correct.

Where mechanics are uncertain, the project generally keeps the implementation
isolated so behavior can be updated without reshaping unrelated calculations.
