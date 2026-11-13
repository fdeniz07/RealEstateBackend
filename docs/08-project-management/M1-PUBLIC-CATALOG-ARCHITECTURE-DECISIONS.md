---
document_id: RE-PM-M1-ARCH-001
title: M1 Public Catalog — Architecture Decision Package
version: 0.2.0
status: approved
language: en
prepared_by: TASK-016
owner: Product Owner
created_at: 2026-11-13
---

# M1 Public Catalog — Architecture Decision Package (TASK-016)

Consolidates **TASK-013** … **TASK-016** evidence. **TASK-001** recorded PO approval for the M1 baseline (see §0.1). Field-level tables in §3 remain **engineering reference** unless marked approved.

Turkish: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md](M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md).

**ADR:** [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) — **status: approved** (v0.5.0).

---

## 0. Existing approvals (do not change)

| ID | Statement |
|----|-----------|
| **DEC-001-J02** | Contact POST **authenticated only** |
| **DEC-001-M1-A02-A04** | A02, A03, A04 **not M1** |
| **DEC-001-ANONYMOUS-CATALOG** | Anonymous **listing + detail** read-only required |

### 0.1 PO approved M1 baseline (TASK-001)

| # | Decision |
|---|----------|
| 1 | **P1** listing endpoint |
| 2 | **DTO Option B** (no JPA on public JSON) |
| 3 | **Visibility V3** (DEC-006 §7.5) |
| 4 | **Image Model 2** |
| 5 | No public `id`/`viewCount`; slug; allow-listed `propertyValues` |
| 6 | Three-path anonymous GET whitelist |
| 7 | Mandatory IP rate limiting before production |

Authoritative wording: [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md).

---

## 1. How to read this package

| Layer | Meaning |
|-------|---------|
| **PO approved (TASK-001)** | §0.1 and ADR-002 |
| **Engineering reference** | §3 field tables — default allow-list for implementers, not per-field PO sign-off |
| **Engineering detail** | Thresholds, migration, storage, unspecified property keys |

---

## 2. Endpoint strategy — P1 vs P1b

### Comparison

| Criterion | **P1** `GET .../catalog` | **P1b** `getListWithActive` + public DTO |
|-----------|---------------------------|------------------------------------------|
| API clarity | Dedicated public contract | Legacy admin-oriented name |
| Security whitelist | Single intentional path | Same path used pre-public DTO confusion |
| FE impact | New URL (greenfield FE) | Name implies “active” not “public” |
| Maintainability | Clear split vs `getAll` | Risk JWT clients expect old shape |
| Effort | New mapping + deprecate policy | Re-type existing endpoint |

### Architecture recommendation

**Adopt P1** for anonymous catalog listing. Keep `getListWithActive` **JWT-only** until PO approves migration/deprecation.

### PO decision required

Confirm **P1** vs **P1b** and whether authenticated users may also call P1.

### Engineering detail

Pagination params mirror AS-IS (`page`, `size`, `sort`, `type`). Detail remains **P2** `GET .../slug/{slug}` (**TASK-014**).

---

## 3. Public DTO — Option B allow-lists

**Strategy:** Dedicated types — **no JPA entities in JSON** ([TASK-014-DATA](DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md)).

### 3.1 `PublicAdvertListItem` (default M1 — no CONDITIONAL fields)

| Field | Class | Include | Reason | Evidence | PO? |
|-------|-------|---------|--------|----------|-----|
| `slug` | PUBLIC | Yes | A07 navigation | `Advert.slug` entity; absent in `AdvertResponse` | Align product |
| `title` | PUBLIC | Yes | Card headline | FR-ADV-001 | No |
| `price` | PUBLIC | Yes | Search/sort | AS-IS | No |
| `location` | PUBLIC | Yes | Free-text geo | AS-IS | No |
| `advertType` | PUBLIC | Yes | `PublicAdvertTypeRef` | Nested entity risk in AS-IS | No |
| `category` | PUBLIC | Yes | `PublicCategoryRef` | Category in list cards | No |
| `country`, `city`, `district` | PUBLIC | Yes | `PublicGeoRef` name only | Exclude `plateNumber` | No |
| `featuredImage` | PUBLIC | Yes | `PublicImageRef` | Gallery teaser | No |
| `id` | CONDITIONAL | **No** | Slug-first URLs | TASK-015 | **Yes** if analytics |
| `viewCount` | CONDITIONAL | **No** | Scraping metric | Entity field | **Yes** |
| `shortDescription` | CONDITIONAL | **No** | Duplicates title | Optional UX | **Yes** |
| `desc`, `images[]`, `propertyValues` | — | **No** on list | Detail only | Design | No |

### 3.2 `PublicAdvertDetail` (default M1)

All list fields **except** `featuredImage` may be replaced by full `images[]`. Additional:

| Field | Class | Include | PO? |
|-------|-------|---------|-----|
| `desc` | PUBLIC | Yes | No |
| `images[]` | PUBLIC | Yes ordered | No |
| `propertyValues[]` | CONDITIONAL | **Yes** default on detail | **Yes** (PII/category sensitivity) |
| `viewCount` | CONDITIONAL | **No** default | **Yes** |
| `id` | CONDITIONAL | **No** | **Yes** |

### 3.3 Nested DTOs

| Type | Allowed fields | Excluded (evidence) |
|------|----------------|---------------------|
| `PublicAdvertTypeRef` | `title` | `id`, `advertSet` — `AdvertType.java` |
| `PublicCategoryRef` | `title`, `slug`, `icon` | `builtIn`, `seq`, `active`, collections |
| `PublicGeoRef` | `name` | `id`, audit, `plateNumber`, parent graphs |
| `PublicImageRef` | `id`, `featured`, `imageUrl` | raw `path`, `name`, `type` storage |
| `PublicPropertyValue` | `keyName`, `value` | FK ids — fixes TASK-014 orphan `value` |

### Architecture recommendation

**Option B** with tables above as **default allow-list** (CONDITIONAL fields off until PO opts in).

### PO decision required

`viewCount`, public `id`, `propertyValues` on detail, optional list teaser fields.

### Engineering detail

MapStruct `PUBLIC` profile; integration tests assert deny-list.

---

## 4. Visibility policy V1–V4

| Rule | Predicate | Risk |
|------|-----------|------|
| **V1** AS-IS | `isActive=true` | PENDING/REJECTED may leak |
| **V2** | `advertStatus=ACTIVATED` | Ignores soft-delete flag |
| **V3** | `ACTIVATED ∧ isActive ∧ ¬deleted` | Matches public intent |
| **V4** | V3 + scheduling | Post-M1 |

**List/detail:** Same repository predicate for P1 and P2.

### Architecture recommendation

**V3** for M1 public catalog queries.

### PO decision required

Formal alignment with **DEC-006** (does not auto-approve DEC-006).

### Engineering detail

New repository methods; manager workflows unchanged for `getAll`.

---

## 5. Image delivery — Model 2

**Pattern:** `GET /api/v1.0/public/images/{imageId}`; JSON uses `imageUrl` pointing to this path.

| Topic | Behavior (proposal) |
|-------|---------------------|
| Resolve | Load `Image` by id; join `Advert`; apply **same visibility predicate as V3** |
| Unpublished advert | **404** (not 403) to avoid enumeration signal difference |
| Deleted image row | **404** |
| ID guessing | Sequential `Long` ids — **enumerate risk**; mitigate with rate limit + no directory listing |
| Cache | `Cache-Control: public, max-age=86400` for stable published assets; revalidate on advert unpublish |
| Errors | 404 generic; 500 `FriendlyMessage` |
| CDN migration | Keep `imageUrl` as path or configurable absolute base — storage backend swappable |

### Architecture recommendation

**Model 2** for M1; **reject** raw `Image.path` in public JSON.

### PO decision required

Confirm Model 2 vs CDN-first (Model 4 later).

### Engineering detail

Implement in **TASK-004** / image epic; byte source from disk/DB TBD in implementation.

---

## 6. Slug and identifiers

| Topic | Recommendation | Owner |
|-------|----------------|-------|
| DB uniqueness | `UNIQUE` index on `adverts.slug` | Engineering |
| App validation | Reject duplicate on create/update | Engineering |
| Stability | Slug change = new URL; **301** policy post-M1 | PO later |
| Duplicate read | 0 → 404; >1 → 500 + alert | Engineering |
| Public `id` | Omit | PO override only |
| `viewCount` | Omit unless PO approves; increment via `?incrementView=true` on P2 | PO |

**TASK-014** owns slug GET implementation.

---

## 7. Security whitelist (anonymous GET only)

| Pattern | Purpose |
|---------|---------|
| `GET /api/v1.0/adverts/{language}/catalog` | P1 listing |
| `GET /api/v1.0/adverts/{language}/slug/*` | P2 detail |
| `GET /api/v1.0/public/images/*` | Model 2 binary |

**Excluded:** all writes; `contact-messages/**` POST; `getAll`; admin; geo/category CRUD; A02–A04; swagger remains as today.

**Security architecture:** **Option A** — `permitAll` on named GET matchers + **service-layer V3 filter**.

### PO decision required

Approve whitelist as M1 anonymous surface (TASK-001).

---

## 8. Rate limiting

| Tier | Proposal |
|------|----------|
| **M1 minimum** | Reverse-proxy or Spring filter coarse limit per IP on P1/P2 (e.g. 120 req/min) — **no Redis required** |
| **Recommended later** | Distributed limit, bot detection |
| **Not mandatory** | API gateway, Redis — unless ops already provides |

### Architecture recommendation

**Basic IP throttling before production** — implementation choice by engineering.

### PO decision required

“Public catalog must not go live without rate limiting” yes/no.

---

## 9. PO decision checklist (worksheet)

See [PO_DECISION_WORKSHEET.md](PO_DECISION_WORKSHEET.md) §**DEC-001-M1-ARCHITECTURE** (TASK-016 recommendations pre-printed).

| # | Topic | Architecture default |
|---|--------|----------------------|
| 1 | Listing path | **P1** |
| 2 | Public DTO | **B** + §3 allow-list |
| 3 | Visibility | **V3** |
| 4 | Images | **Model 2** |
| 5 | `viewCount` / `id` | **Omit** |
| 6 | Whitelist | §7 (3 patterns) |
| 7 | Rate limit before prod | **Yes** (engineering implements minimum) |

**ADR-002** **approved** via TASK-001 (2026-11-13).

---

## 10. Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | TASK-016 architecture decision package |
| 0.2.0 | 2026-11-13 | TASK-001 PO approvals recorded; ADR-002 approved |
