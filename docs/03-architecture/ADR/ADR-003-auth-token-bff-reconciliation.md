---
document_id: ADR-003
title: Auth Token Transport — BFF Reconciliation for Decisions 14 and 23
version: 0.1.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-18
updated_at: 2026-11-18
---

# ADR-003 — BFF Reconciliation (Decisions 14 & 23)

## Status

**draft** — **PROPOSED**. Does **not** amend approval status of Decisions 14 or 23 until explicitly accepted by the authorized decision owner.

## Context

Architecture programme decisions (authoritative):

- **Decision 14:** Short-lived access token; refresh token protected by HttpOnly/Secure/SameSite cookie; CSRF; strict CORS.
- **Decision 23:** Keycloak as IdP; Authorization Code + PKCE; Spring Security OAuth2 Resource Server on the API.

Taken literally, Decision 14 can imply the **browser** holds access tokens while refresh uses HttpOnly cookies. Decision 23 places **Keycloak** as token issuer and the **API** as Resource Server. A **Next.js** hybrid frontend benefits from server-side OIDC handling.

**Repository:** Decisions 14 and 23 are **not** individually listed in `docs/08-project-management/DECISION_LOG.md`; this ADR records the **proposed reconciliation** for governance traceability.

## Decision (PROPOSED)

Adopt a **Next.js BFF** as the recommended TO-BE pattern:

1. Browser holds an **opaque HttpOnly session cookie** only (not Keycloak tokens in JS storage).
2. BFF performs Authorization Code + **PKCE** with Keycloak and stores access/refresh tokens **server-side**.
3. BFF sends **Bearer** access tokens to the Spring Boot API.
4. API validates JWTs as OAuth2 Resource Server; **no** browser cookie authentication at API.

This **reconciles** Decision 14 by moving token custody server-side while still using HttpOnly cookies for the browser session, rather than exposing Keycloak refresh tokens to JavaScript.

## Consequences (if approved)

| Benefit | Trade-off |
|---------|-----------|
| Aligns with HttpOnly session security goals | BFF operational complexity |
| API remains stateless Bearer-only | Extra hop; BFF availability critical |
| Fits Next.js SSR/hybrid | Formal amendment to literal Decision 14 wording may be required |

## Alternatives

| Option | Notes |
|--------|--------|
| Direct SPA + PKCE to API | Access token in memory; HttpOnly refresh **not** automatic; stricter XSS discipline |
| AS-IS custom JWT | Remains until migration; no Keycloak |

## Implementation status

**Not implemented.** Keycloak, BFF, and Resource Server are **FUTURE**.

## Related

- [SECURITY-ARCHITECTURE.md](../security/SECURITY-ARCHITECTURE.md)
- [INTEGRATIONS.md](../../05-infrastructure/INTEGRATIONS.md)

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-18 | Initial PROPOSED BFF reconciliation |