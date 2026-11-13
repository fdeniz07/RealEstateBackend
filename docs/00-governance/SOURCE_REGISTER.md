---
document_id: RE-GOV-003
title: Source Register
version: 0.1.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-13
extraction_note: SRC-008-PDF fully text-extracted 2026-11-13 (TASK-007, 103 pages)
---

# Source Register

Inventory of sources used for RealEstate documentation. Reliability: **High** (code/config), **Medium** (README/screenshots), **Low** (unverified legacy narrative).

| Source ID | Filename / location | Type | Version / date | Description | Reliability | Related requirements | Documentation refs |
|-----------|---------------------|------|----------------|-------------|-------------|----------------------|-------------------|
| SRC-001 | `Backend/README.md` | README | repo HEAD | Technology list, infra screenshot links | Medium | BR-001, NFR-* | Hub README, TECHNOLOGY_BASELINE |
| SRC-002 | `files/Roles.JPG` | Image | 2023-11-07 area | Role hierarchy (Anonymous→Admin) | Medium | BR-002, FR-AUTH-* | BRD, role matrix |
| SRC-003 | `Backend/pom.xml` | Build | 1.0.0 | Dependencies, Java 17, Boot 3.0.0 | High | NFR-COMPAT | TECHNOLOGY_BASELINE |
| SRC-004 | `Backend/src/main/resources/application.yaml` | Config | active profile localhost | Datasource, JPA, CORS, JWT props (redacted in docs) | High | NFR-SEC, NFR-DEPLOY | Security, ENVIRONMENT_CONFIGURATION |
| SRC-005 | `Backend/src/main/java/.../controller/**` | Source | repo HEAD | REST API surface | High | FR-*, API-* | API-INVENTORY, FRD |
| SRC-006 | `Backend/.../security/config/SecurityFilterChainConfig.java` | Source | repo HEAD | JWT filter chain, permit rules | High | FR-AUTH, NFR-SEC | Security architecture |
| SRC-007 | `Backend/src/test/.../RealEstateApplicationTests.java` | Test | repo HEAD | Sole automated test | High | TEST-*, NFR-TEST | TEST_STRATEGY |
| SRC-008 | `files/Documentation-20231107.zip` | Archive | 2023-11-07 | Contains `real-state-project.pdf` (unchanged) | High (post-extraction) | LEG-* catalogue | BRD background |
| SRC-008-PDF | `real-state-project.pdf` (in archive) | PDF / LEGACY spec | 2023-11-06 | 103 pp.; 70 API codes; PowerPoint export | High | A01–K06, F01–F12, G01–G05 | [LEGACY-REQUIREMENTS-ANALYSIS.md](../01-business/source-analysis/LEGACY-REQUIREMENTS-ANALYSIS.md) |
| SRC-009 | `files/Design Files-20231107.zip` | Archive | 2023-11-07 | UI/design assets (not fully extracted in Stage 0) | Low–Medium | UI LEGACY | TBD UI docs |
| SRC-010 | `files/Logo-20231107.zip` | Archive | 2023-11-07 | Brand logos | Medium | — | assets (optional) |
| SRC-011 | `files/Other Files-20231107.zip` | Archive | 2023-11-07 | Banner/error images | Low | — | assets/screenshots overlap |
| SRC-012 | `Frontend/Frontend/package.json` | Manifest | 0.1.0 | React 18, CRA, i18next, antd | High | FR-I18N UI | I18N_STRATEGY |
| SRC-013 | `Frontend/Frontend/src/router/router.js` | Source | repo HEAD | Client routes (partial app) | Medium | FR-* UI | SAD context |
| SRC-014 | `Backend/img/*.png` | Screenshot | repo | Swagger UI captures | High | API-* | assets/screenshots |
| SRC-015 | Official Spring Boot docs | External | Boot 4.0+ / 4.1.x | System requirements, Java 17–25/27 | High | TO-BE stack | TECHNOLOGY_BASELINE |
| SRC-016 | `Backend/entity/**` | Source | repo HEAD | JPA domain model | High | FR-*, DB-* | database ERD |
| SRC-017 | `Backup/real-estate.zip` | Backup | unknown | Full project backup | Low | TBD | Not inspected in Stage 0 |

## Missing or limited sources

| Item | Status |
|------|--------|
| Extracted text from `real-state-project.pdf` | **Done** — TASK-007; see SRC-008-PDF and LEGACY-REQUIREMENTS-ANALYSIS |
| Flyway migrations | **None found** — schema via `ddl-auto: update` (AS-IS) |
| OpenAPI exported spec | **TBD** — runtime generation via springdoc only |
| CI/CD configuration | **Not found** in Backend repo root |

## Conflict log (sample)

| Topic | LEGACY (SRC-002) | AS-IS (SRC-005, SRC-006) | Resolution |
|-------|------------------|---------------------------|------------|
| Anonymous listing browse | Allowed without login | Most `GET` APIs require JWT except auth/signup whitelist | Document gap; TO-BE: public read endpoints (FR-ADV-001) |
| Tour requests REST API | Customer can request tours | `TourRequestController` has no mappings | LEGACY capability; implementation **TBD** |
| Favorites CRUD for customer | Add/remove favorites | Endpoints commented out; limited admin GET | Partial AS-IS |
| Role PERSONAL | Not in role poster | `RoleType.PERSONAL` in code | AS-IS extra role; document in matrix |
| Geo endpoints | PDF GET-only U01–U03, p.4 | Full CRUD on countries/cities/districts | AS-IS expanded scope vs LEGACY |
| Reports G01–G05 | PDF pp. 89–93 | No `/report` controllers | LEGACY not implemented |
| Internal messaging | Not in PDF | `MessageController` | AS-IS-only feature |
| API path prefix | `/adverts`, `/login` | `api/v1.0/.../{language}/...` | Contract migration TO-BE |

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Initial register after Stage 0 discovery |
| 0.1.1 | 2026-11-13 | TASK-007 PDF extraction; SRC-008-PDF; conflict log extended |
| 0.1.2 | 2026-11-13 | TASK-008 LEGACY_API_TO_FR_MATRIX; FRD 0.2.0 |
