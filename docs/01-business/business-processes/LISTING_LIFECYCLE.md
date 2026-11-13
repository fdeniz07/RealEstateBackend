---
document_id: RE-BIZ-PROC-001
title: Listing Lifecycle Process
version: 0.1.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-13
---

# Listing Lifecycle (BPMN-style)

```mermaid
flowchart LR
  subgraph Customer
    A[Create advert POST] --> B{Valid data?}
  end
  subgraph System
    B -->|yes| C[Persist PENDING]
    B -->|no| D[Validation error]
    C --> E[AdvertStatus enum]
  end
  subgraph Manager_LEGACY
    E --> F[Review approve TO-BE]
    F --> G[ACTIVE via isActive flag]
  end
  subgraph Public
    G --> H[getListWithActive]
    H --> I[Favorites / Tours LEGACY]
  end
```

**Figure RE-FIG-PROC-001** — Listing lifecycle aligned to `Advert` + `AdvertStatus` (AS-IS) with LEGACY moderation (**TBD**).

| Step | AS-IS | Source |
|------|-------|--------|
| Create | `POST /api/v1.0/adverts/{language}/add` | SRC-005 |
| Status field | `AdvertStatus.PENDING` default | SRC-016 |
| Active filter | `getListWithActive` | SRC-005 |
| Manager approval workflow | Not verified | TBD |
