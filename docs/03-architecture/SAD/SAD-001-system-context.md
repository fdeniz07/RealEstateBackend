---
document_id: RE-SAD-001
title: System Context (C4 Level 1)
version: 0.1.0
status: draft
language: en
---

# Solution Architecture — System Context

## C4 Context diagram

```mermaid
C4Context
  title RealEstate System Context
  Person(seeker, "Property seeker", "Browses listings LEGACY")
  Person(customer, "Customer", "Publishes listings")
  Person(staff, "Manager/Admin", "Operates catalog and users")
  System(realestate, "RealEstate Backend", "Spring Boot API AS-IS")
  System_Ext(fe, "React Frontend", "Partial CRA client READ-ONLY")
  System_Ext(pg, "PostgreSQL", "Primary datastore")
  System_Ext(mail, "Email provider", "LEGACY activation TBD")
  Rel(seeker, fe, "Uses")
  Rel(customer, fe, "Uses")
  Rel(staff, fe, "Uses")
  Rel(fe, realestate, "HTTPS JSON JWT")
  Rel(realestate, pg, "JDBC/JPA")
  Rel(realestate, mail, "SMTP TO-BE")
```

**Figure RE-FIG-SAD-001** — C4 context (draft).

SVG companion: [../../assets/diagrams/c4-context.svg](../../assets/diagrams/c4-context.svg).

## Container view (summary)

See [HLD-001-containers.md](../HLD/HLD-001-containers.md).

## Integration boundaries (future)

Catalogued in [INTEGRATIONS.md](../../05-infrastructure/INTEGRATIONS.md).
