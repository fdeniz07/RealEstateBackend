---
document_id: RE-DDD-BC-001
title: Bounded Context Map
version: 0.1.0
status: draft
language: en
---

# Bounded Contexts (candidates)

```mermaid
flowchart LR
  IAM[Identity & Access]
  LIST[Listing Management]
  CAT[Catalog & Location]
  ENG[Engagement]
  COM[Communication]
  subgraph Shared
    SK[Shared Kernel TBD]
  end
  IAM --> LIST
  CAT --> LIST
  LIST --> ENG
  ENG --> COM
  IAM --> COM
```

**Figure RE-FIG-DDD-001** — Context map (draft).

| Context | Responsibility | Key aggregates (candidates) | Open questions |
|---------|----------------|----------------------------|----------------|
| Identity & Access | Users, roles, JWT | User, Role | OAuth2 migration |
| Catalog & Location | Countries, cities, categories | Country, Category | i18n of catalog labels |
| Listing | Adverts, types, images | Advert, AdvertType | Moderation workflow |
| Engagement | Favorites, tours | Favorite, TourRequest | Expose REST for tours |
| Communication | Messages, contact | Message, Contact | Anonymous contact policy |

Per-context detail: [domain/DOMAIN-OVERVIEW.md](../domain/DOMAIN-OVERVIEW.md).
