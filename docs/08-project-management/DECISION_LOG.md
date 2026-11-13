# DECISION_LOG

| Date | Decision | Status | ADR / Doc |
|------|----------|--------|-----------|
| 2026-11-13 | Modular monolith preferred over microservices | draft | ADR-001 |
| 2026-11-13 | Proposed public read catalog API | superseded | ADR-002 v0.5.0 **approved** (TASK-001) |
| 2026-11-13 | Documentation-only; no stack upgrade | active | — |
| 2026-11-13 | DEC-001 public catalog Option A + public DTO (workshop) | **proposal draft** | ADR-002, PRODUCT_DECISIONS |
| 2026-11-13 | DEC-002 role responsibilities align to LEGACY + TASK-005 | **proposal draft** | PRODUCT_DECISIONS |
| 2026-11-13 | DEC-003 retain internal messaging (R1) pending PO | **proposal draft** | PRODUCT_DECISIONS |
| 2026-11-13 | DEC-004 implement G01–G05 when prioritized | **proposal draft** | PRODUCT_DECISIONS |
| 2026-11-13 | DEC-005 password recovery F03/F04 with LEG-NFR-008 | **proposal draft** | PRODUCT_DECISIONS |
| 2026-11-13 | DEC-006 listing states LEGACY enum; no Draft until PO | **partial** | PRODUCT_DECISIONS §7.4 open |
| 2026-11-13 | **DEC-006 M1 public visibility V3** (ACTIVATED + isActive + not deleted) | **approved** (TASK-001) | ADR-002, PRODUCT_DECISIONS §7.5 |
| 2026-11-13 | DEC-007 defer multilingual DB model | **proposal draft** | PRODUCT_DECISIONS |

Workshop package: [PRODUCT_DECISIONS.md](PRODUCT_DECISIONS.md) (TASK-010). **No PO approval recorded.**

| 2026-11-13 | PO review package prepared for DEC-001 … DEC-007 (TASK-011) | **PO review required** | [PO_DECISION_REVIEW.md](PO_DECISION_REVIEW.md), worksheets EN/TR |
| 2026-11-13 | **DEC-001-J02:** J02 POST requires authentication; anonymous not permitted (TASK-011-A) | **approved** | FR-CNT-001 / contact-messages; **not** ADR-002 approval |
| 2026-11-13 | **DEC-001-M1-A02-A04:** Option D — A02/A03/A04 excluded from M1; deferred later (TASK-012-A) | **approved** | FR-ADV-004/005; M1 scope only — **not** access policy or ADR-002 |
| 2026-11-13 | M1 public catalog access review prepared (TASK-013) | **PO review required** | [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md](DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md) — technical policy open |
| 2026-11-13 | **DEC-001-ANONYMOUS-CATALOG:** M1 listing + detail must be reachable anonymously (TASK-013-A) | **approved** | Product requirement — **not** ADR-002, whitelist, DTO, visibility, rate limits, A02–A04 |
| 2026-11-13 | Public DTO and data boundary review prepared (TASK-014-DATA) | **PO review required** | [DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md](DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md) — field/DTO policy open |
| 2026-11-13 | M1 public catalog API design prepared (TASK-015) | **proposal — PO review required** | [PUBLIC-CATALOG-API-DESIGN.md](../03-architecture/api/PUBLIC-CATALOG-API-DESIGN.md) — not ADR-002 approval |
| 2026-11-13 | M1 architecture finalization package (TASK-016) | **proposal — PO review required** | [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md); ADR-002 v0.4.0 **draft** |
| 2026-11-13 | M1 implementation delivery plan (TASK-017-M1) | **approved baseline for execution** | [M1-IMPLEMENTATION-PLAN.md](M1-IMPLEMENTATION-PLAN.md) |
| 2026-11-13 | **TASK-001:** M1 public catalog — seven PO decisions (P1, DTO B, V3, Model 2, identifiers, whitelist, rate limit) | **approved** | [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) v0.5.0; IMPL-000 satisfied |
