---
document_id: RE-PM-PO-WS-001
title: Product Owner Decision Worksheet
version: 0.1.7
status: draft
language: en
task: TASK-011
---

# Product Owner Decision Worksheet

**Instructions:** For each DEC, select **Accept**, **Reject**, **Modify**, or **Defer**. Record your decision in the **My decision** field. Leave **Approval date** blank until formal sign-off. **Do not** treat pre-filled text as approved.

**Related review:** [PO_DECISION_REVIEW.md](PO_DECISION_REVIEW.md) · Turkish: [PO_DECISION_WORKSHEET-TR.md](PO_DECISION_WORKSHEET-TR.md)

---

## DEC-001 — Public catalog and anonymous read access

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ A permitAll + public DTO · ☐ B public facade · ☐ C guest JWT · ☐ D gateway · ☐ Other: ___ |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. M1 path list, rate limits) — **J02 recorded under DEC-001-J02 below** |
| **Approval date** | |
| **Approval status** | Not approved |

### DEC-001-J02 — Contact message POST (J02)

| Field | Recorded value (PO, TASK-011-A) |
|-------|----------------------------------|
| **Sub-decision** | J02 `POST .../contact-messages/{language}/add` |
| **Selected option** | **Authenticated only** (anonymous **not** permitted) |
| **Disposition** | **Accept** |
| **My decision / modification** | J02 POST requires authentication. Anonymous users must not submit contact messages. |
| **Conditions and constraints** | — |
| **Approval date** | 2026-11-13 |
| **Approval status** | **Approved** |

### DEC-001-M1-A02–A04 — M1 product scope (A02 / A03 / A04)

*Evidence: [DEC-001-A02-A04-SCOPE-REVIEW.md](DEC-001-A02-A04-SCOPE-REVIEW.md).*

| Field | Recorded value (PO, TASK-012-A) |
|-------|----------------------------------|
| **M1 scope option** | **D — Minimum viable user journey** |
| **Disposition** | **Accept** |
| **My decision / modification** | A02 (Cities), A03 (Categories), and A04 (Popular Advertisements) are **excluded from M1** and **deferred to a later release**. Reason: prioritize completing the fundamental property listing and property detail experience; A02–A04 must not expand initial release scope. |
| **Access note (separate from scope)** | Anonymous vs JWT for A02–A04 when implemented: **unresolved** (not decided by this approval) |
| **Approval date** | 2026-11-13 |
| **Approval status** | **Approved** (M1 product scope only) |

### DEC-001-ANONYMOUS-CATALOG — M1 listing and detail (product requirement)

*Evidence: [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md](DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md); [PRODUCT_DECISIONS.md](PRODUCT_DECISIONS.md) §2.5.*

| Field | Recorded value (PO, TASK-013-A) |
|-------|----------------------------------|
| **Product requirement** | Anonymous visitors **must** browse the **M1 property listing** and **open M1 property details** without authentication (read-only). |
| **Disposition** | **Accept** |
| **My decision / modification** | Authentication is **not** required for these two read-only user journeys. |
| **Scope limit (not approved by this row)** | ADR-002; `permitAll`; endpoint whitelist; Public DTO; visibility rule; `isActive`/status/image path exposure; rate limits; anonymous writes; A02/A03/A04 anonymous access; change to DEC-001-J02 |
| **Approval date** | 2026-11-13 |
| **Approval status** | **Approved** (product requirement only) |

### DEC-001-PUBLIC-CATALOG — M1 technical access policy

*Evidence: [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md](DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md). **Product requirement recorded under DEC-001-ANONYMOUS-CATALOG above; technical fields below remain open.***

| Field | Your input |
|-------|------------|
| **Architecture option** | ☐ A permitAll · ☐ B public facade · ☐ C JWT-only catalog · ☐ D hybrid · ☐ Defer |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Visibility rule** | ☐ A isActive only · ☐ B ACTIVATED · ☐ C both · ☐ D other |
| **Public DTO** | ☐ Required · ☐ Reuse AdvertResponse · ☐ TBD |
| **Anonymous endpoints approved** | (list paths explicitly, e.g. getListWithActive, slug detail, geo GET) |
| **Rate limiting before launch** | ☐ Yes · ☐ No · ☐ TBD |
| **Conditions and constraints** | |
| **Approval date** | |
| **Approval status** | Not approved |

### DEC-001-PUBLIC-DATA-BOUNDARY — Public DTO and field exposure (TASK-014-DATA)

*Evidence: [DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md](DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md). **No decision pre-recorded.***

| Field | Your input |
|-------|------------|
| **Public DTO strategy** | ☐ A Reuse AdvertResponse · ☐ B PublicAdvertResponse · ☐ C Public read model · ☐ Defer |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Public field allow-list** | (e.g. slug, viewCount, geo shape, strip isActive/audit) |
| **Image delivery model** | ☐ 1 Direct URL · ☐ 2 App public GET · ☐ 3 Protected · ☐ 4 CDN/object · ☐ TBD |
| **Nested geo/category shape** | |
| **Category property values policy** | |
| **Conditions and constraints** | (align with DEC-006 visibility — separate decision) |
| **Approval date** | |
| **Approval status** | **Superseded** — DTO **Option B** + allow-list policy via §DEC-001-M1-ARCHITECTURE (TASK-001) |

### DEC-001-PUBLIC-CATALOG-API — M1 API design package (TASK-015)

*Evidence: [PUBLIC-CATALOG-API-DESIGN.md](../03-architecture/api/PUBLIC-CATALOG-API-DESIGN.md). **Endpoints/DTO/visibility recorded under §DEC-001-M1-ARCHITECTURE (TASK-001).***

| Field | Your input |
|-------|------------|
| **Listing path** | ☐ P1 `/catalog` · ☐ P1b `getListWithActive` · ☐ Defer |
| **Detail path** | ☐ P2 `/slug/{slug}` · ☐ Other: ___ |
| **Public DTO** | ☐ Option B (recommended in design) · ☐ A · ☐ C · ☐ Defer |
| **Visibility rule** | ☐ V1 · ☐ V2 · ☐ V3 · ☐ V4 · ☐ Defer (DEC-006) |
| **Image delivery** | ☐ Model 2 app GET · ☐ Model 1 direct path · ☐ Model 4 CDN · ☐ TBD |
| **Expose `viewCount` / `id`** | |
| **Security whitelist** | (confirm paths for permitAll — TASK-001 / ADR-002) |
| **Disposition** | ☐ Accept design · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Approval date** | |
| **Approval status** | **Superseded** — TASK-001 §DEC-001-M1-ARCHITECTURE |

### DEC-001-M1-ARCHITECTURE — Finalization package (TASK-016 / **TASK-001 recorded**)

*Evidence: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md), [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) v0.5.0 (**approved**).*

| Field | Recorded PO decision (TASK-001) |
|-------|--------------------------------|
| **Listing path** | **P1** `GET /api/v1.0/adverts/{language}/catalog` |
| **Public DTO** | **Option B** — dedicated types; no JPA on public JSON |
| **Visibility** | **V3** — `ACTIVATED` + `isActive` + `deleted=false` (DEC-006 §7.5) |
| **Images** | **Model 2** `GET /api/v1.0/public/images/{imageId}` + V3 on every request |
| **`viewCount` / `id`** | **Excluded** from M1 public DTOs; **slug** for navigation |
| **`propertyValues`** | Only **explicitly allow-listed** properties in public JSON |
| **Anonymous whitelist** | P1 catalog, P2 slug, public images GET only |
| **Rate limit before prod** | **Yes** — IP-based; Redis not required |
| **Disposition (TASK-001 / ADR-002)** | **Accept baseline** |
| **Approval date** | 2026-11-13 |
| **Approval status** | **Approved** |

**Implementation sequencing (TASK-017-M1):** execute per [M1-IMPLEMENTATION-PLAN.md](M1-IMPLEMENTATION-PLAN.md) (`IMPL-010` … `IMPL-090`). **IMPL-000 satisfied** by TASK-001.

---

## DEC-002 — Customer, Manager and Admin responsibilities

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ A LEGACY matrix · ☐ B JWT-only minimal · ☐ C Custom matrix · ☐ Defer RBAC |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. LEG-BR-007, PERSONAL role, approval authority) |
| **Approval date** | |
| **Approval status** | Not approved |

---

## DEC-003 — Internal messaging

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ R1 Retain · ☐ R2 Deprecate · ☐ R3 Merge with contact · ☐ Other: ___ |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. actors, retention, GDPR) |
| **Approval date** | |
| **Approval status** | Not approved |

---

## DEC-004 — Reporting G01–G05

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ Full G01–G05 · ☐ MVP: ___ · ☐ Defer reports · ☐ External BI |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. export format, Manager scope on G04) |
| **Approval date** | |
| **Approval status** | Not approved |

---

## DEC-005 — Password recovery

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ Implement F03/F04 · ☐ Admin-only reset · ☐ Defer · ☐ Other: ___ |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. email provider, TTL, CAPTCHA) |
| **Approval date** | |
| **Approval status** | Not approved |

---

## DEC-006 — Listing lifecycle

| Field | Your input |
|-------|------------|
| **M1 public catalog gate (TASK-001)** | **V3 approved** — see PRODUCT_DECISIONS §7.5 / ADR-002 |
| **Selected option** (full lifecycle) | ☐ TO-BE A LEGACY enum strict · ☐ TO-BE B isActive only · ☐ TO-BE C add Draft/Archive · ☐ Other |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. rejection visibility, Draft vs Pending) |
| **Approval date** | 2026-11-13 (M1 public gate only) |
| **Approval status** | **Partial** — M1 public V3 **approved**; remainder **not approved** |

---

## DEC-007 — Multilingual property content

| Field | Your input |
|-------|------------|
| **Selected option** | ☐ M1 single column · ☐ M2 parallel columns · ☐ M3 translation table · ☐ M4 JSONB · ☐ Defer schema |
| **Disposition** | ☐ Accept · ☐ Reject · ☐ Modify · ☐ Defer |
| **My decision / modification** | |
| **Conditions and constraints** | (e.g. EN/TR required, slug policy, fallback locale) |
| **Approval date** | |
| **Approval status** | Not approved |

---

## Sign-off block (optional)

| Field | Value |
|-------|-------|
| Product Owner name | |
| Review session date | |
| Notes | |

**Reminder:** ADR-002 approval is **TASK-001** separately; completing this worksheet does not approve ADR-002.
