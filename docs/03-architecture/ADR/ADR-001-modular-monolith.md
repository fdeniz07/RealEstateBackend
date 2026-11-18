---
document_id: ADR-001
title: Prefer Modular Monolith
version: 0.2.0
status: draft
language: en
owner: RealEstate Project
updated_at: 2026-11-18
---

# ADR-001 — Modular Monolith First

## Status

draft

## Context

Single Spring Boot deployable with ~15 controllers and shared PostgreSQL database (**AS-IS**). Code is organized **package-by-layer** today (`controller`, `service`, `repository`, `entity`). Strategic direction adopts DDD, Clean / Hexagonal Architecture, and explicit module boundaries **without** premature microservices.

**AS-IS:** One Maven project, one artifact, one JVM process. **TO-BE:** Same deployment shape; **six business/technical modules** inside one application with controlled dependencies.

## Decision

Adopt a **Modular Monolith** with:

| Property | Decision |
|----------|----------|
| Runtime | **Single** Spring Boot application |
| Build | **Single** Maven project, **single** deployable backend JAR |
| Database | **Single** PostgreSQL instance (schema ownership per module — **TO-BE** evolution) |
| Code layout | **Package-by-feature** under module roots (`identity`, `catalog`, …) |
| Cross-module access | **Ports and module APIs only** — no foreign JPA entities or repositories |
| Microservices | **Not** in initial scope; extraction remains possible later |

### Initial modules (in scope)

| Module | Responsibility |
|--------|----------------|
| **identity** | User identity, profiles, application roles and identity-related capabilities |
| **catalog** | Reference data, property classifications, categories and catalog-related data |
| **listing** | Advertisements, listing lifecycle, **category property values** (`category_property_values`), listing business rules |
| **engagement** | Favorites, following and user interactions |
| **communication** | Conversations, messages and communication-related capabilities |
| **platform** | **Minimal** shared technical infrastructure (for example i18n registry, API error envelope) — **not** a general business module |

### Future modules (out of initial implementation scope)

Documented for roadmap only — **not implemented**, **not** part of initial module set:

`notification`, `subscription`, `ai`, `partner`, `audit`

**TBD:** `Log` entity / audit trail ownership remains unresolved in product documentation; do not assign logs to platform business ownership until decided.

### Module dependency rules (approved)

**Allowed (consumer → provider via port):**

| Consumer | Provider | Port (examples) |
|----------|----------|-----------------|
| listing | identity | `IdentityPort` |
| listing | catalog | `CatalogPort` |
| engagement | identity | `IdentityPort` |
| engagement | listing | `ListingQueryPort` |
| communication | identity | `IdentityPort` |
| Business modules | platform | Approved technical capabilities only (i18n, cross-cutting infra) |

**Forbidden:**

- Circular module dependencies
- Cross-module JPA entity imports
- Direct use of another module's Spring Data repositories
- Database access through another module's infrastructure layer

**Port design:**

- Create ports only for **actual** use cases
- The **consuming** module owns the **outbound** port (`application/port/out`)
- The **providing** module supplies the **adapter**

Detail: [HLD-002-clean-architecture.md](../HLD/HLD-002-clean-architecture.md), [bounded-contexts/BOUNDED-CONTEXT-MAP.md](../bounded-contexts/BOUNDED-CONTEXT-MAP.md). **Flyway, schema ownership, persistence patterns:** [database/PERSISTENCE-ARCHITECTURE.md](../database/PERSISTENCE-ARCHITECTURE.md).

### Enforcement (TO-BE)

Module dependency rules via **ArchUnit** (or equivalent) — **FUTURE**; not enforced in code **AS-IS**.

## Consequences

- Simpler operations than a distributed system (one deployable, one DB connection pool)
- Requires discipline to avoid a big-ball-of-mud during migration from layered packages
- Clear ports enable eventual service extraction **if** a bounded context stabilizes
- **platform** must stay small — avoid dumping business rules into shared packages

## Alternatives rejected (for now)

| Alternative | Reason |
|-------------|--------|
| Microservices now | Insufficient bounded-context stability and operational overhead |
| Multiple Maven modules / repos immediately | Higher build complexity before package boundaries are proven |
| Shared “god” `common` module for business logic | Violates ownership; use `platform` for technical cross-cutting only |

## Related documents

- Containers: [HLD-001-containers.md](../HLD/HLD-001-containers.md)
- Layers and mappers: [HLD-002-clean-architecture.md](../HLD/HLD-002-clean-architecture.md)
- Events: [HLD-003-event-driven-architecture.md](../HLD/HLD-003-event-driven-architecture.md)

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Initial modular monolith proposal (five modules named) |
| 0.2.0 | 2026-11-18 | Six initial modules; platform/communication; dependency ports; future modules separated |