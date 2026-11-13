---
document_id: RE-ROADMAP-001
title: Modernization Roadmap
version: 0.1.0
status: draft
---

# ROADMAP

```mermaid
gantt
  title RealEstate Modernization (proposal)
  dateFormat YYYY-MM
  section Foundation
  Documentation v0.1     :done, 2026-11, 2026-11
  section M1 Parity
  Public read API ADR    :2026-12, 2027-01
  Tour/Favorite/Image API:2027-01, 2027-03
  section M2 Quality
  Flyway baseline        :2027-02, 2027-03
  Testcontainers suite   :2027-03, 2027-05
  section M3 Identity
  OAuth2/OIDC spike      :2027-05, 2027-06
  section M4 Platform
  Boot 4 migration       :2027-06, 2027-09
```

## Milestones

| ID | Goal | Exit criteria |
|----|------|---------------|
| M0 | Documentation foundation | This deliverable |
| M1 | LEGACY API parity gaps closed | FR-TOUR, FR-FAV, FR-MED AS-IS |
| M2 | Schema + tests | Flyway + 60% coverage goal |
| M3 | Hardening security | RBAC enforced, CORS fixed |
| M4 | Stack upgrade | Boot 4 on Java 25 in CI |

Dependencies: [MILESTONES.md](MILESTONES.md). Risks: [RISKS.md](RISKS.md).
