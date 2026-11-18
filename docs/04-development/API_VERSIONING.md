---
document_id: RE-DEV-APIVER-001
title: API Versioning
version: 0.2.0
status: draft
language: en
owner: RealEstate Project
updated_at: 2026-11-18
---

# API Versioning

**API conventions:** [API-ARCHITECTURE.md](../03-architecture/api/API-ARCHITECTURE.md).

## AS-IS

| Item | Value |
|------|--------|
| Path prefix | **`/api/v1.0/`** on all REST controllers (e.g. `@RequestMapping("api/v1.0/adverts")`) |
| Breaking changes | No formal deprecation process documented in code |

## APPROVED policy (TO-BE)

| Rule | Detail |
|------|--------|
| Version segment | **`/api/v1/`** as architectural target (may alias or migrate from `v1.0` — **TBD** migration) |
| Within v1 | Prefer **additive**, backward-compatible changes (new fields optional, new endpoints) |
| Breaking change | Requires new major path (`/api/v2/`) or negotiated client update |
| Deprecation | Communicate sunset; maintain previous version for agreed window — **TBD** governance |
| No premature v2 | Do **not** introduce `/api/v2/` without documented breaking requirement |

## Relationship to public catalog

Product-approved public routes are defined under current `v1.0` paths in [PUBLIC-CATALOG-API-DESIGN.md](../03-architecture/api/PUBLIC-CATALOG-API-DESIGN.md). Version migration must preserve or explicitly deprecate those URLs.

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | (undated) | Initial short note |
| 0.2.0 | 2026-11-18 | AS-IS v1.0 vs APPROVED v1 target; deprecation policy outline |