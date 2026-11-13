---
document_id: ADR-002
title: Public Read API Policy
version: 0.5.0
status: approved
language: en
task: TASK-001
related_decisions:
  - DEC-001
  - DEC-006
approved_at: 2026-11-13
---

# ADR-002 — Public Catalog Read Endpoints

## Status

**approved** (Product Owner, **TASK-001**, 2026-11-13).

This ADR records **seven explicit PO approvals** for the **M1 public catalog** (read paths, DTO policy, visibility, images, identifiers, anonymous whitelist, rate limiting). It does **not** authorize Java implementation by itself; execution follows [M1-IMPLEMENTATION-PLAN.md](../../08-project-management/M1-IMPLEMENTATION-PLAN.md).

**Approval scope boundary — not approved by TASK-001:**

- Per-field DTO allow-lists beyond the policies below (engineering derives defaults from [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](../../08-project-management/M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md) §3 and [DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md](../../08-project-management/DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md); PO did not approve every column name).
- Numeric rate-limit thresholds, window sizes, or filter technology.
- Slug uniqueness migration, duplicate cleanup scripts, or DB constraint shape.
- Image storage provider, CDN, or filesystem layout.
- Individual `propertyValues` keys not on the engineering allow-list.
- Option B vs C for security **pattern**: **Option A** (`permitAll` on named GET matchers + service-layer enforcement) is **adopted** as the M1 security pattern (implicit in whitelist decision).

Turkish summary: [ADR-002-public-read-api-TR.md](ADR-002-public-read-api-TR.md).

---

## Approved decisions (TASK-001)

| # | Topic | PO decision |
|---|--------|-------------|
| 1 | **Listing endpoint** | **P1** — `GET /api/v1.0/adverts/{language}/catalog` (not P1b `getListWithActive` for anonymous public catalog). |
| 2 | **Public DTO** | **Option B** — `PublicAdvertListItem`, `PublicAdvertDetail`, explicit nested public reference DTOs. **Prohibit** direct JPA entity serialization on public responses. |
| 3 | **Visibility** | **V3** — public only if `AdvertStatus = ACTIVATED` **AND** `isActive = true` **AND** `deleted = false`. Same predicate on **listing**, **detail**, and **image** access. Recorded under **DEC-006** M1 public gate (§ below). |
| 4 | **Image delivery** | **Model 2** — `GET /api/v1.0/public/images/{imageId}`; every request verifies parent advert satisfies **V3**. Predictable image IDs are **not** authorization. |
| 5 | **Identifiers & counters** | Public DB **`id` excluded**; **`slug`** for navigation/detail; **`viewCount` excluded** from M1 public DTOs; **`propertyValues`** — only **explicitly allow-listed** properties may appear in public JSON (no blanket entity dump). |
| 6 | **Anonymous whitelist** | **Only** these anonymous **GET** endpoints: catalog (P1), `.../slug/{slug}` (P2), public images. All other endpoints **authenticated by default**. **Excluded:** A02, A03, A04, contact POST (DEC-001-J02), `getAll`, manager/admin routes, anonymous writes. |
| 7 | **Rate limiting** | **Mandatory before production** for anonymous catalog endpoints; **IP-based**; **Redis not mandatory**; thresholds = engineering decision. |

### Preserved prior approvals (unchanged)

| ID | Statement |
|----|-----------|
| **DEC-001-J02** | Contact-message POST requires authentication. |
| **DEC-001-M1-A02-A04** | A02, A03, A04 deferred beyond M1. |
| **DEC-001-ANONYMOUS-CATALOG** | Anonymous read-only listing + detail required. |

---

## Context

| Source | Finding |
|--------|---------|
| SRC-008-PDF | **47+** GET codes allow **ANONYMOUS +** (broader than M1) |
| AS-IS `SecurityFilterChainConfig` | `anyRequest().authenticated()` except auth/signup whitelist |
| FR-ADV-001 | Documents conflict — **M1 narrow whitelist approved** above |
| Frontend | Browse not integrated; FE alignment remains TASK-010 |
| PRODUCT_DECISIONS DEC-001 | Workshop + TASK-001 M1 baseline |

Full workshop: [PRODUCT_DECISIONS.md](../../08-project-management/PRODUCT_DECISIONS.md) §2.

## DEC-006 — M1 public visibility (approved subset)

For **anonymous public catalog** reads only, PO approved **V3** as the publication gate:

```text
AdvertStatus.ACTIVATED ∧ isActive = true ∧ deleted = false
```

Apply identically in repository/service queries for **P1**, **P2**, and **Model 2** image resolution.

**Not decided by TASK-001:** Draft/Archived enum extensions, owner-only views of `REJECTED`, customer edit → `PENDING` workflows — remain open in PRODUCT_DECISIONS §7.

## DTO boundaries (approved policy)

**Reject** anonymous use of `AdvertResponse`.

**Adopt Option B** with PO identifier policy (§ approved #5). Default field shapes for engineering planning: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](../../08-project-management/M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md) §3 — **proposal tables remain engineering reference**, not additional PO sign-off per field.

## M1 anonymous GET whitelist (approved)

| Endpoint | Verbs | Role |
|----------|-------|------|
| `/api/v1.0/adverts/{language}/catalog` | GET | P1 listing |
| `/api/v1.0/adverts/{language}/slug/{slug}` | GET | P2 detail — implement **TASK-014** |
| `/api/v1.0/public/images/{imageId}` | GET | Model 2 |

**Exclude:** all writes; `contact-messages` POST; `getAll`; `getListWithActive` for anonymous catalog (use P1); admin/manager routes; A02–A04; broad legacy geo/category anonymous GET.

## Security pattern

**Option A** — `SecurityFilterChainConfig` `permitAll` only on the three GET patterns above; **service-layer V3** on every public read path; default authenticated elsewhere.

Contact POST: **DEC-001-J02** (authenticated). AS-IS whitelist typo `contact-Messages/**` cleanup remains engineering **TBD**.

## Rate limiting (approved policy)

IP-based throttling **required before production** on P1/P2/public images. Implementation mechanism and numeric limits are **engineering decisions** (in-process filter, reverse proxy, etc.).

## Consequences

- Implementation authorized per [M1-IMPLEMENTATION-PLAN.md](../../08-project-management/M1-IMPLEMENTATION-PLAN.md) (`IMPL-010` …).
- DTO/mapper + TEST-SEC-01, TEST-ADV-public-* required.
- RBAC on mutating routes still **TASK-005**.
- FE anonymous browse unblocked for **contract design**; wiring remains TASK-010.

## Related traceability

- BR-011, DEC-001, DEC-006 (M1 public gate), [PRODUCT_DECISIONS.md](../../08-project-management/PRODUCT_DECISIONS.md)
- FR-ADV-001; FR-CNT-001: **DEC-001-J02**
- TASK-001, TASK-014, TASK-015, TASK-016, TASK-017-M1
- [PUBLIC-CATALOG-API-DESIGN.md](../api/PUBLIC-CATALOG-API-DESIGN.md) (design reference)

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Initial stub |
| 0.2.0 | 2026-11-13 | TASK-008 decision analysis |
| 0.3.0 | 2026-11-13 | TASK-010 workshop |
| 0.3.1–0.3.3 | 2026-11-13 | DEC-001-J02, M1-A02-A04, ANONYMOUS-CATALOG refs |
| 0.4.0 | 2026-11-13 | TASK-016 M1 baseline proposal (draft) |
| 0.5.0 | 2026-11-13 | **TASK-001: PO approved seven M1 decisions; status → approved** |
