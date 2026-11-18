---
document_id: RE-HLD-001
title: High-Level Design — Containers
version: 0.2.1
status: draft
language: en
owner: RealEstate Project
updated_at: 2026-11-18
---

# HLD — Container Architecture

**Physical deployment / CI / Docker:** [INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md](../infrastructure/INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE.md).

## AS-IS (layered monolith)

```mermaid
flowchart TB
  subgraph Presentation
    CTRL[REST Controllers]
    OAS[OpenAPI springdoc]
  end
  subgraph Application
    SVC[Service Managers]
    MAP[MapStruct Mappers]
  end
  subgraph Domain_Persistence
    ENT[JPA Entities]
    REPO[Spring Data Repositories]
  end
  subgraph CrossCutting
    SEC[Spring Security JWT]
    EX[Global Exception Handler]
    I18N[Messages / Language]
  end
  CTRL --> SVC --> REPO --> DB[(PostgreSQL)]
  SVC --> MAP
  SEC --> CTRL
  EX --> CTRL
```

**Figure RE-FIG-HLD-001** — AS-IS logical layering inside a single deployable (not modular packages).

**Caption:** Historical package-by-layer structure in one JVM.

**Explanation:** Controllers and `*Manager` services share one classpath; persistence entities are used across business areas without module fences.

![Infrastructure diagram](../../assets/screenshots/Infrastructure.png)

**Figure RE-FIG-HLD-002** — Historical infrastructure diagram from project README (AS-IS reference).

## TO-BE — Modular monolith (one deployable)

**Approved:** One Spring Boot application, one Maven artifact — **not** six separate services.

```mermaid
flowchart TB
  subgraph deployable [Single Spring Boot application - TO-BE]
    direction TB
    subgraph mod_identity [identity module]
      IAPI[api]
      IAPP[application]
    end
    subgraph mod_catalog [catalog module]
      CAPI[api]
      CAPP[application]
    end
    subgraph mod_listing [listing module]
      LAPI[api]
      LAPP[application]
    end
    subgraph mod_engagement [engagement module]
      EAPI[api]
      EAPP[application]
    end
    subgraph mod_communication [communication module]
      COAPI[api]
      COAPP[application]
    end
    subgraph mod_platform [platform module]
      PL[technical capabilities]
    end
  end
  DB[(PostgreSQL shared database)]
  deployable --> DB
```

**Figure RE-FIG-HLD-005** — Logical module boundaries inside one backend container.

**Caption:** Six modules coexist in one process; PostgreSQL is shared infrastructure.

**Explanation:** The diagram shows **ownership boundaries**, not network boundaries. Inter-module calls are in-process ports/adapters unless a **FUTURE** outbox/broker is introduced (see [HLD-003-event-driven-architecture.md](HLD-003-event-driven-architecture.md)).

### Initial modules (approved responsibilities)

| Module | Responsibility |
|--------|----------------|
| `identity` | User identity, profiles, application roles and identity-related capabilities |
| `catalog` | Reference data, categories, classifications, geo reference data |
| `listing` | Advertisements, listing lifecycle, property values (`category_property_values`) |
| `engagement` | Favorites, following, user interactions |
| `communication` | Conversations, messages, communication capabilities |
| `platform` | Minimal shared **technical** infrastructure (i18n registry, API errors, etc.) |

**Future (not initial):** `notification`, `subscription`, `ai`, `partner`, `audit` — see [ADR-001-modular-monolith.md](../ADR/ADR-001-modular-monolith.md).

### Approved module dependencies (technical)

```mermaid
flowchart LR
  listing --> identity
  listing --> catalog
  engagement --> identity
  engagement --> listing
  communication --> identity
  listing --> platform
  catalog --> platform
  engagement --> platform
  communication --> platform
  identity --> platform
```

**Figure RE-FIG-HLD-006** — Approved module dependency direction (ports, not JPA).

**Caption:** Arrows are allowed consumer → provider relationships implemented via ports.

**Explanation:** No arrow from catalog to listing or identity to engagement “back” — **no circular** module dependencies.

| Consumer | Provider | Port |
|----------|----------|------|
| listing | identity | `IdentityPort` |
| listing | catalog | `CatalogPort` |
| engagement | identity | `IdentityPort` |
| engagement | listing | `ListingQueryPort` |
| communication | identity | `IdentityPort` |
| business modules | platform | technical APIs only |

## TO-BE — Hexagonal view (per module)

```mermaid
flowchart LR
  subgraph Adapters_In
    REST[REST Adapter]
    SECA[Security Adapter]
  end
  subgraph Application_Core
    UC[Use Cases]
    DOM[Domain Model]
  end
  subgraph Adapters_Out
    JPA[JPA Adapter]
    PORTS[Module ports]
  end
  REST --> UC --> DOM
  UC --> PORTS
  UC --> JPA
```

**Figure RE-FIG-HLD-003** — Target dependency inversion within each module (TO-BE).

Dependency rules and layers: [HLD-002-clean-architecture.md](HLD-002-clean-architecture.md).

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | AS-IS layering; draft five-module table |
| 0.2.0 | 2026-11-18 | Six-module modular monolith container; dependency diagram; listing/engagement split corrected |
| 0.2.1 | 2026-11-18 | Link INFRASTRUCTURE-DEPLOYMENT-ARCHITECTURE (Group 10) |