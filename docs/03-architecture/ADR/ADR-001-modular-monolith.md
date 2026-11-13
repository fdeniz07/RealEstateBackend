---
document_id: ADR-001
title: Prefer Modular Monolith
version: 0.1.0
status: draft
language: en
---

# ADR-001 — Modular Monolith First

## Status

draft

## Context

Single Spring Boot deployable with ~15 controllers and shared database (AS-IS). Strategic prompt targets DDD and Clean Architecture without premature microservices.

## Decision (proposal)

Organize code into **modules** (identity, catalog, listing, engagement, communication) within one artifact; enforce module dependency rules via ArchUnit (future).

## Consequences

- Simpler operations than distributed system
- Requires discipline to avoid big-ball-of-mud
- Migration path to split services later if bounded contexts stabilize

## Alternatives rejected (for now)

Microservices — insufficient domain clarity and operational overhead.
