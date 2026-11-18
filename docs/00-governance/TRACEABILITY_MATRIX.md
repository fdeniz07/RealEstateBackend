---
document_id: RE-GOV-007
title: Requirements Traceability Matrix
version: 0.2.1
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-18
---

# Requirements Traceability Matrix (v0.2.0)

Corrected in **TASK-008**. Mapping matrix: [LEGACY_API_TO_FR_MATRIX.md](../02-requirements/FRD/LEGACY_API_TO_FR_MATRIX.md).

## Coverage summary (verified)

| Layer | Count | Source |
|-------|------:|--------|
| Business requirements (BRD) | **18** | BR-001 … BR-018 (v0.2.0) |
| Functional requirements | **36** | [FRD-EN.md](../02-requirements/FRD/FRD-EN.md) |
| LEGACY API codes | **70** | SRC-008-PDF |
| Architecture decision files | **2** | ADR-001 (draft), [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) (**approved** v0.6.0, TASK-001) |
| Documented use cases | 2 | UC-TOUR-01, [UC-ADV-01](../02-requirements/use-cases/UC-ADV-01.md) |
| Proposed test IDs | 35+ | Referenced in FRD; not executed |

```mermaid
flowchart TB
  BR[BR BRD] --> FR[FR 36]
  LEG[LEGACY 70 codes] --> FR
  FR --> UC[Use cases]
  FR --> API[AS-IS API inventory]
  FR --> ADR[ADR-001 ADR-002]
  FR --> TEST[TEST proposals]
  ADR --> TASK[TASK backlog]
```

**Figure RE-FIG-TRACE-002** — Traceability chain (corrected counts).

## Example — Anonymous catalog browse (LEGACY vs AS-IS)

| BR | FR | UC | LEGACY | AS-IS API | ADR | TEST | TASK | Status |
|----|----|----|--------|-----------|-----|------|------|--------|
| BR-011 | FR-ADV-001 | UC-ADV-01 | A01, A07, C01 (pp. 25–31) | `GET .../adverts/...` (JWT) | ADR-002 (**approved** v0.6.0) | TEST-SEC-01 | TASK-010 | LEGACY / **conflict** (AS-IS JWT vs ADR-002 policy) |
| BR-003 | FR-ADV-002 | UC-ADV-01 | A10 (p. 34) | `POST .../add` | ADR-001 | TEST-ADV-02 | TASK-015 | Partial AS-IS |
| BR-004 | FR-TOUR-001 | UC-TOUR-01 | S05 (p. 69) | No mapping | ADR-001 | TEST-TOUR-01 | TASK-002 | LEGACY gap |

**Figure RE-FIG-TRACE-001** — Corrected browse vs tour traces (no phantom ADRs).

## Example — Role hierarchy (corrected BR mapping)

| BR | FR | UC | Notes |
|----|----|----|-------|
| BR-002 | FR-ADM-001, FR-ADM-002 | — | Role/user governance; **not** FR-AUTH-001 |
| BR-002 | FR-ADV-001 | UC-ADV-01 | Anonymous permissions per PDF matrix |

## ADR references (actual documents only)

| ADR | Topic | Linked FR |
|-----|-------|-----------|
| ADR-001 | Modular monolith | All implementation TASKs |
| ADR-002 | Public read API (**approved** v0.6.0, TASK-001; DEC-007 amendment 2026-11-14) | FR-ADV-001, FR-GEO-001, FR-CNT-001 |

**Removed:** phantom ADR-003/ADR-004 from v0.1.0 matrix.

## Product decision traceability — D-06 (owner-confirmed)

| Product decision | FR | BR (evidence) | TASK | Status |
|------------------|-----|---------------|------|--------|
| **D-06** | FR-FAV-001 | BR-007 ([BRD-EN.md](../01-business/BRD/BRD-EN.md) §7.3 / FR-FAV-001) | TASK-003 | LEGACY/partial AS-IS; **D-06 rules not verified** in implementation |
| **D-06** | FR-FOLLOW-001 | — (no dedicated BR in BRD v0.2.0) | — | Post-M1; **pending** |

**Note:** **DEC-006** (listing lifecycle) is separate; coordinate eligibility (D-06 OQ-1) by cross-reference only.

## TASK identifier alignment

| TASK | FR / LEGACY | Purpose |
|------|-------------|---------|
| TASK-002 | FR-TOUR-* | Tour REST |
| TASK-003 | FR-FAV-001 | Favorites |
| TASK-004 | FR-MED-001, FR-CAT-002 | Image / advert type |
| TASK-005 | FR-ADM-*, NFR-SEC | RBAC |
| TASK-010 | FR-ADV-001 | Public read / ADR-002 |
| TASK-011–020 | See [TODO.md](../08-project-management/TODO.md) | PDF parity backlog |

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Initial (incorrect counts) |
| 0.2.0 | 2026-11-13 | TASK-008 correction |
| 0.2.1 | 2026-11-18 | **D-06** traceability; FR count 36; FR-FOLLOW-001 pending |
| 0.2.2 | 2026-11-18 | **F-001:** ADR-002 status references aligned to **approved** v0.6.0 (not draft) |
