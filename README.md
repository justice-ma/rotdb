# RuneScape Ability Damage Simulator

A deterministic damage calculator and simulation engine for RuneScape
abilities.\
Built to model real in‑game combat math, modifier stacking, and
multi‑hit abilities with high accuracy.

This project now includes:

-   A **Spring Boot backend**
-   A **React frontend prototype**
-   A **PostgreSQL database populated with RuneScape data scraped from
    the RuneScape Wiki**

The goal is to provide an **accurate, explainable, and extensible combat
simulation engine** for RuneScape 3.

------------------------------------------------------------------------

# Overview

The simulator calculates the damage output of RuneScape abilities by
executing a structured **damage pipeline** on a clean input model
(`DamageRequest`).

It supports:

-   Multi‑hit abilities
-   Style specific formulas (Melee / Ranged / Magic / Necromancy)
-   Additive and multiplicative modifier stacking
-   Critical strike modeling
-   Buffs, prayers, relics, perks, and gear effects
-   Proc effects and injected hits
-   Familiar and target modifiers
-   Equipment‑driven rule logic

Unlike simple calculators, this project attempts to replicate
**RuneScape's internal combat math deterministically**.

------------------------------------------------------------------------

# Tech Stack

## Backend

-   Java
-   Spring Boot
-   Maven

## Frontend

-   React
-   Vite

## Database

-   PostgreSQL
-   RuneScape Wiki scraped data

## Deployment

-   Railway (backend hosting)
-   PostgreSQL production database

------------------------------------------------------------------------

# System Architecture

The system is separated into **three major layers**.

## 1. Input Layer

User configuration is converted into a structured model:

    DamageRequest

Inputs include:

-   Equipment
-   Ability
-   Combat style
-   Player skill levels
-   Buffs
-   Prayers
-   Perks
-   Relics
-   Familiar
-   Target
-   Potions

This ensures the calculation engine **never interacts directly with UI
state**.

------------------------------------------------------------------------

## 2. Engine Layer

The engine converts the request into a working context and executes the
**damage pipeline**.

``` java
DamageResult result = engine.calculateAbilityDamage(request);
```

Pipeline stages include:

1.  Base damage calculation
2.  Additive modifiers
3.  Multiplicative modifiers
4.  Hit‑specific adjustments
5.  Critical strike modeling
6.  Proc injections
7.  Final hit resolution

Each stage is modular and independently testable.

------------------------------------------------------------------------

## 3. Output Layer

Results are returned as:

    DamageResult

Output contains:

-   Individual hit values
-   Min / Max / Avg damage
-   Crit vs non‑crit breakdown
-   Total damage
-   Optional debug trace

------------------------------------------------------------------------

# Architecture Diagram

                     ┌──────────────┐
                     │   React UI   │
                     │ (Frontend)   │
                     └──────┬───────┘
                            │ REST API
                            ▼
                   ┌─────────────────┐
                   │ Spring Boot API │
                   │  /calculate     │
                   │  /batch         │
                   └──────┬──────────┘
                          │
                          ▼
                 ┌────────────────────┐
                 │ Damage Calculation │
                 │      Engine        │
                 │  Ability Pipeline  │
                 └──────┬─────────────┘
                        │
                        ▼
               ┌───────────────────────┐
               │ PostgreSQL Database   │
               │ Equipment / Targets   │
               └───────────────────────┘

------------------------------------------------------------------------

# Backend API

## Calculate Single Ability

    POST /damage/calculate

Returns detailed damage information for a single ability.

------------------------------------------------------------------------

## Batch Ability Calculation

    POST /damage/calculate/batch

Used by the frontend ability browser to calculate damage for multiple
abilities simultaneously.

------------------------------------------------------------------------

# Example API Payload

Example request body sent from the frontend:

``` json
{
  "style": "MELEE",
  "abilityId": "ASSAULT",
  "skills": {
    "attack": 99,
    "strength": 99
  },
  "equipment": {
    "mainhandId": 12345,
    "headId": 9876
  },
  "selectedPrayers": ["PIETY"],
  "selectedFamiliar": "KALGERION_DEMON"
}
```

Example response:

``` json
{
  "totalDamage": 13450,
  "averageHit": 1921,
  "minHit": 1450,
  "maxHit": 2380,
  "hits": [1800, 1900, 2000, 2100]
}
```

------------------------------------------------------------------------

# Frontend Prototype

The React frontend currently provides a **combat configuration
interface**.

Users can configure:

-   Equipment
-   Skill levels
-   Buffs
-   Prayers
-   Familiar
-   Target
-   Ability selection

The frontend constructs a `DamageRequest` payload and sends it to the
backend API.

The UI is currently **prototype stage**, focused on functionality rather
than final visual polish.

------------------------------------------------------------------------

# Frontend Screenshots

*(Add screenshots here once UI stabilizes)*

Example placeholders:

    docs/screenshots/ability-browser.png
    docs/screenshots/combat-settings.png
    docs/screenshots/damage-results.png

------------------------------------------------------------------------

# Database

PostgreSQL stores RuneScape data including:

-   Equipment
-   Abilities
-   Stats
-   Requirements
-   Metadata

Data is scraped from the **RuneScape Wiki** and normalized for use by
the simulator.

------------------------------------------------------------------------

# Project Structure (Conceptual)

    backend/
      domain/
      engine/
      model/
      rules/
      abilities/

    frontend/
      pages/
      components/
      panels/
      api/

    database/
      schema/
      seed_data/

------------------------------------------------------------------------

# Running the Project

## Backend

Requirements:

-   Java 21+
-   Maven
-   PostgreSQL

Run:

    mvn clean install
    mvn spring-boot:run

------------------------------------------------------------------------

## Frontend

    npm install
    npm run dev

------------------------------------------------------------------------

# Why This Exists

RuneScape combat math is:

-   Complex
-   Poorly documented
-   Full of layered edge cases

This project provides a **deterministic and extensible implementation of
RuneScape ability damage calculations**.

------------------------------------------------------------------------

# Author

Justice Mazerolle
