---
document_id: RE-GOV-006
title: Documentation Changelog
version: 0.1.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-13
---

# Changelog

## [0.2.0] — 2026-11-13

### Added

- [LEGACY_API_TO_FR_MATRIX.md](../02-requirements/FRD/LEGACY_API_TO_FR_MATRIX.md) (70 codes).

### Added (TASK-013)

- [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md](../08-project-management/DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW.md) / TR; diagrams `dec-013-*.svg`.
- Worksheet §DEC-001-PUBLIC-CATALOG (blank).

### Added (TASK-001)

- [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) v0.5.0 **approved** — seven M1 public catalog PO decisions; DEC-006 M1 public gate V3.
- PRODUCT_DECISIONS §2.5.1, §7.5; worksheets §DEC-001-M1-ARCHITECTURE recorded; M1-IMPLEMENTATION-PLAN v0.2.0 (IMPL-000 satisfied).

### Added (TASK-017-M1)

- [M1-IMPLEMENTATION-PLAN.md](../08-project-management/M1-IMPLEMENTATION-PLAN.md) / TR; diagram `dec-017-m1-impl-critical-path.svg`.
- Incremental WBS `IMPL-000` … `IMPL-090`, test matrix T-01…T-19, Java/Spring migration assessment (Option A recommended).

### Added (TASK-016)

- [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](../08-project-management/M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md) / TR; [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) v0.4.0 + ADR-002-TR summary.
- Worksheet §DEC-001-M1-ARCHITECTURE (TASK-016 recommendations).

### Added (TASK-015)

- [PUBLIC-CATALOG-API-DESIGN.md](../03-architecture/api/PUBLIC-CATALOG-API-DESIGN.md) / TR; diagrams `dec-015-*.svg`.
- Worksheet §DEC-001-PUBLIC-CATALOG-API (blank PO fields).

### Added (TASK-014-DATA)

- [DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md](../08-project-management/DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW.md) / TR; diagrams `dec-014-*.svg`.
- Worksheet §DEC-001-PUBLIC-DATA-BOUNDARY (blank).

### Changed (TASK-013-A)

- PO approved **DEC-001-ANONYMOUS-CATALOG** (M1 listing + detail anonymous product requirement).
- Updated PRODUCT_DECISIONS, PO review/worksheet EN/TR, DECISION_LOG, access review v0.1.1; ADR-002 v0.3.3 reference only (**draft**).

### Added (TASK-012)

- [DEC-001-A02-A04-SCOPE-REVIEW.md](../08-project-management/DEC-001-A02-A04-SCOPE-REVIEW.md) / TR — M1 scope for A02–A04.
- Diagrams `dec-012-*.svg`; worksheet section DEC-001-M1-A02–A04.

### Added (TASK-011)

- [PO_DECISION_REVIEW.md](../08-project-management/PO_DECISION_REVIEW.md), [PO_DECISION_REVIEW-TR.md](../08-project-management/PO_DECISION_REVIEW-TR.md)
- [PO_DECISION_WORKSHEET.md](../08-project-management/PO_DECISION_WORKSHEET.md), [PO_DECISION_WORKSHEET-TR.md](../08-project-management/PO_DECISION_WORKSHEET-TR.md)
- Diagrams: `dec-003-messaging-interaction.svg`, `dec-004-reporting-dependencies.svg`, `dec-007-multilingual-layers.svg`

### Added (TASK-010)

- [PRODUCT_DECISIONS.md](../08-project-management/PRODUCT_DECISIONS.md) — DEC-001 … DEC-007 workshop package.
- Decision diagrams under `assets/diagrams/dec-00*.svg`.

### Changed (TASK-010)

- [ADR-002-public-read-api.md](../03-architecture/ADR/ADR-002-public-read-api.md) v0.3.0 — DTO boundary, FE findings, Option A proposal.
- [DECISION_LOG.md](../08-project-management/DECISION_LOG.md), [TODO.md](../08-project-management/TODO.md) — workshop traceability.

### Changed (TASK-009)

- [BRD-EN.md](../01-business/BRD/BRD-EN.md) / [BRD-TR.md](../01-business/BRD/BRD-TR.md) v0.2.0 — 18 BRs, BRL rules, FR links.
- [ROLE_PERMISSION_MATRIX.md](../01-business/role-permission-matrix/ROLE_PERMISSION_MATRIX.md) v0.2.0.
- [ERD-LEGACY.md](../03-architecture/database/ERD-LEGACY.md).
- [UC-ADV-01.md](../02-requirements/use-cases/UC-ADV-01.md).

### Changed

- FRD-EN / FRD-TR expanded to **35** FRs with LEGACY cross-refs (TASK-008).
- TRACEABILITY_MATRIX corrected (no phantom ADRs).
- ADR-002 decision analysis expanded (still draft).
- TODO aligned with matrix tasks.

## [0.1.0] — 2026-11-13

### Added

- Initial `docs/` tree (governance, business, requirements, architecture, development, infrastructure, operations, education, project management).
- BRD/FRD EN/TR (draft), NFR catalogue, role-permission matrix.
- SAD/HLD drafts, API inventory, security and database documentation.
- Technology baseline (TO-BE targets with official Spring references).
- Education README and Level 1 EN/TR.
- ROADMAP, TODO, RISKS, RELEASE_PLAN, MILESTONES, DECISION_LOG.
- Visual assets: Mermaid diagrams, copied Swagger screenshots, legacy Roles image.

### Notes

- No backend source, build, or git commits performed (per execution boundaries).
