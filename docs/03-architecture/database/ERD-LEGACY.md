---
document_id: RE-DB-ERD-LEGACY-001
title: LEGACY Entity Relationship Model
version: 0.2.0
status: draft
language: en
source_references:
  - SRC-008-PDF
---

# ERD — LEGACY (2023 PDF)

Sources: entity tables pp. **6–21**; diagram image pp. **22–23** (raster; relationships partially **inferred**).

**Do not merge** with [ERD-AS-IS.md](ERD-AS-IS.md) — compare side by side.

## Explicit entities (text on PDF pages)

Advert, Category, CategoryPropertyKey, CategoryPropertyValue, Favorite, User, Role, UserRole, TourRequest, Image, AdvertType, Country, City, District, Log, Contact.

## LEGACY logical diagram (text + inference)

```mermaid
erDiagram
  USER ||--o{ ADVERT : owns
  USER }o--o{ ROLE : UserRole
  ADVERT }o--|| ADVERT_TYPE : typed
  ADVERT }o--|| CATEGORY : in
  ADVERT }o--|| COUNTRY : in
  ADVERT }o--|| CITY : in
  ADVERT }o--|| DISTRICT : in
  ADVERT ||--o{ IMAGE : has
  ADVERT ||--o{ CATEGORY_PROPERTY_VALUE : values
  CATEGORY ||--o{ CATEGORY_PROPERTY_KEY : defines
  CATEGORY_PROPERTY_KEY ||--o{ CATEGORY_PROPERTY_VALUE : template
  USER ||--o{ FAVORITE : marks
  ADVERT ||--o{ FAVORITE : target
  USER ||--o{ TOUR_REQUEST : guest
  USER ||--o{ TOUR_REQUEST : owner
  ADVERT ||--o{ TOUR_REQUEST : for
  USER ||--o{ LOG : actor
  ADVERT ||--o{ LOG : subject
```

**Figure ERD-LEG-001** — LEGACY model (explicit FKs from pp. 6–21; UserRole M:N inferred).

## Explicit vs inferred

| Relationship | Evidence |
|--------------|----------|
| Advert → Country/City/District/AdvertType/Category/User | **Explicit** (p. 6) |
| CategoryPropertyValue → Advert + Key | **Explicit** (p. 9) |
| Favorite user/advert | **Explicit** (p. 10) |
| UserRole | **Explicit** (p. 13) |
| Tour owner/guest/advert | **Explicit** (p. 14) |
| Message entity | **Not in PDF** | AS-IS only |

## Uncertain / unreadable (p. 23 image)

- Cardinality on diagram image not machine-traced; use text tables as authority.
- **TBD:** re-digitize LEG-VIS-002 into editable SVG without copying slide pixels.

## LEGACY vs AS-IS comparison

```mermaid
flowchart LR
  subgraph LEGACY
    L1[Slug-first URLs]
    L2[Status int 0-2]
    L3[Image data OID]
  end
  subgraph ASIS
    A1[id + language paths]
    A2[AdvertStatus enum]
    A3[Image entity TBD REST]
  end
  L1 -.->|gap| A1
  L2 -->|partial| A2
  L3 -.->|TBD| A3
```

**Figure ERD-LEG-002** — Model drift highlights.

| Aspect | LEGACY (PDF) | AS-IS (code) |
|--------|----------------|--------------|
| API i18n | Not specified | `{language}` path |
| Message | Absent | `Message` entity + controller |
| Geo CRUD | GET-only seed (p. 4) | Full CRUD controllers |
| Advert status | 0/1/2 int | `AdvertStatus` enum |
| Image storage | `data Oid` | **TBD** persistence review |

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.2.0 | 2026-11-13 | TASK-008 LEGACY ERD |
