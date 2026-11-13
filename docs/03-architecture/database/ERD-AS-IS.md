---
document_id: RE-DB-ERD-001
title: AS-IS Entity Relationship Diagram
version: 0.1.0
status: draft
language: en
---

# Database — AS-IS ERD

Schema evolved via Hibernate `ddl-auto: update` (no Flyway in repo).

```mermaid
erDiagram
  USER ||--o{ ADVERT : owns
  USER }o--|| ROLE : has
  ADVERT }o--|| ADVERT_TYPE : typed
  ADVERT }o--|| COUNTRY : located
  ADVERT }o--|| CITY : located
  ADVERT }o--|| DISTRICT : located
  USER ||--o{ FAVORITE : marks
  ADVERT ||--o{ FAVORITE : target
  USER ||--o{ TOUR_REQUEST : requests
  ADVERT ||--o{ TOUR_REQUEST : for
  CATEGORY ||--o{ CATEGORY_PROPERTY_KEY : defines
  CATEGORY_PROPERTY_KEY ||--o{ CATEGORY_PROPERTY_VALUE : values
  USER ||--o{ MESSAGE : sends
  USER ||--o{ CONTACT : submits
```

**Figure RE-FIG-DB-001** — Logical ERD (from entity relationships; physical columns TBD full audit).

## Integrity notes

| Topic | AS-IS |
|-------|-------|
| PK strategy | `BaseEntity` IDs (review for `@GeneratedValue`) |
| Soft delete | Patterns in services **TBD** |
| Indexes | **TBD** — no migration files |
| Transactions | Service-layer default Spring `@Transactional` usage **TBD** audit |

## TO-BE migration strategy (proposal)

1. Introduce Flyway baseline from live schema snapshot.
2. Freeze `ddl-auto` to `validate`.
3. Separate reference data seeds from tenant data.

## Search implications (TO-BE)

Localized `title`/`desc` columns — consider `tsvector` or external search (Elasticsearch LEGACY README plan).
