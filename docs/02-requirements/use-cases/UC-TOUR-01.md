---
document_id: RE-UC-TOUR-01
title: UC Tour Request
version: 0.1.0
status: draft
language: en
---

# UC-TOUR-01 — Request property tour

| Field | Value |
|-------|-------|
| **Actors** | Customer (primary), Manager (LEGACY) |
| **Preconditions** | User authenticated (AS-IS); active advert exists |
| **Main success** | Customer submits tour request → Manager sees queue → status updated |
| **Classification** | LEGACY flow; API **TBD** |
| **Related FR** | FR-TOUR-001 |
| **Traceability** | [TRACEABILITY_MATRIX.md](../../00-governance/TRACEABILITY_MATRIX.md) |

```mermaid
sequenceDiagram
  participant C as Customer
  participant API as API TBD
  participant M as Manager
  C->>API: POST tour-request
  API-->>C: 201 Created
  M->>API: GET pending tours
  M->>API: PATCH status APPROVED
```

**Figure RE-FIG-UC-TOUR-01** — Target use case (TO-BE completion).
