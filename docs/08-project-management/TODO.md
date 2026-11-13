# TODO (v0.2.0 traceability)

| Task ID | Description | Priority | Status | Related | Acceptance |
|---------|-------------|----------|--------|---------|------------|
| TASK-001 | Record M1 public catalog PO decisions (ADR-002) | high | **done** | ADR-002 v0.5.0 **approved**, worksheet §DEC-001-M1-ARCHITECTURE | Seven decisions logged |
| TASK-002 | Implement TourRequest REST | high | backlog | FR-TOUR-001/002, S01–S10 | OpenAPI + TEST-TOUR-* |
| TASK-003 | Restore Favorite user endpoints | medium | backlog | FR-FAV-001, K01–K06 | UC tested |
| TASK-004 | AdvertType/Image/Log REST | medium | backlog | FR-CAT-002, FR-MED-001 | Endpoints in inventory |
| TASK-005 | Enable RBAC | high | backlog | FR-ADM-*, NFR-SEC-005 | TEST-SEC-01 |
| TASK-006 | Flyway baseline | high | backlog | ERD-AS-IS | ddl-auto validate |
| TASK-007 | PDF requirements extraction | low | **done** | SRC-008-PDF | LEGACY-REQUIREMENTS-ANALYSIS |
| TASK-008 | Requirements reconciliation doc | high | **done** | FRD 0.2.0, matrix | This task deliverables |
| TASK-009 | springdoc duplicate cleanup | low | ready | TECH | Single starter |
| TASK-010 | Product decision workshop (PRODUCT_DECISIONS) + FE/API public browse alignment | high | **workshop done** / BE impl unblocked | PRODUCT_DECISIONS, FR-ADV-001, ADR-002 | FE E2E wiring still open |
| TASK-011-PO | PO decision review package (DEC-001–007) | high | **done** | PO_DECISION_REVIEW, worksheets | EN/TR review + validation TASK-011 |
| TASK-011 | Advert aggregation APIs A02/A03 | medium | backlog | FR-ADV-004 | City/category stats |
| TASK-012-SCOPE | DEC-001 A02/A03/A04 M1 scope review | medium | **done** | DEC-001-A02-A04-SCOPE-REVIEW | PO scope package EN/TR |
| TASK-013-ACCESS | DEC-001 M1 public catalog access review | high | **done** | DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW | Policy package EN/TR |
| TASK-014-DATA | DEC-001 public DTO and data boundary review (doc) | high | **done** | DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW | EN/TR + diagrams dec-014-*; **not** slug API |
| TASK-015 | M1 public catalog API design | high | **done** | PUBLIC-CATALOG-API-DESIGN | Proposal EN/TR + dec-015-* |
| TASK-016 | ADR-002 and M1 architecture decision finalization | high | **done** | M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS, ADR-002 | Package EN/TR; ADR **approved** via TASK-001 |
| TASK-017-M1 | M1 public catalog implementation plan (TASK-017 pre-coding) | high | **done** | M1-IMPLEMENTATION-PLAN, dec-017-* | WBS IMPL-* / EN/TR; **not** password TASK-017 |
| TASK-012 | Popular adverts A04 | medium | backlog | FR-ADV-005 | Formula PP |
| TASK-013 | Advert list/detail parity A05–A10 | medium | backlog | FR-ADV-006–010 | Contract diff |
| TASK-014 | Slug detail endpoint A07 (implementation) | medium | backlog | FR-ADV-008 | GET by slug; **distinct from TASK-014-DATA** |
| TASK-015 | Advert status/built_in rules | medium | backlog | FR-ADV-002 | LEG-BR-002 |
| TASK-016 | Geo read vs admin CRUD policy | medium | backlog | FR-GEO-001/002 | ADR or FR update |
| TASK-017 | Forgot/reset password F03/F04 | medium | backlog | FR-AUTH-003/004 | Security review |
| TASK-018 | User password patch F07 | low | backlog | FR-USER-002 | PATCH endpoint |
| TASK-019 | Reporting G01–G05 | low | backlog | FR-RPT-* | Manager dashboards |
| TASK-020 | Advert activity log API | low | backlog | FR-LOG-001 | LogController |
