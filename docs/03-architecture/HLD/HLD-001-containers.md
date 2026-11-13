---
document_id: RE-HLD-001
title: High-Level Design — Containers
version: 0.1.0
status: draft
language: en
---

# HLD — Container Architecture

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

**Figure RE-FIG-HLD-001** — AS-IS logical containers inside single deployable.

![Infrastructure diagram](../../assets/screenshots/Infrastructure.png)

**Figure RE-FIG-HLD-002** — Historical infrastructure diagram from project README (AS-IS reference).

## TO-BE — Hexagonal / Clean (proposal)

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
    MAIL[Mail Adapter TBD]
  end
  REST --> UC --> DOM
  UC --> JPA
```

**Figure RE-FIG-HLD-003** — Target dependency inversion (TO-BE draft).

Dependency rules: [HLD-002-clean-architecture.md](HLD-002-clean-architecture.md).

## Modular monolith modules (proposal)

| Module | Responsibility |
|--------|----------------|
| `identity` | Auth, users, roles |
| `catalog` | Geo, categories, advert types |
| `listing` | Adverts, images, favorites |
| `engagement` | Tours, contact, messages |
| `platform` | Logging, config, i18n |

Inter-module: in-process calls via application services; **no circular deps** (TO-BE ADR-002).
