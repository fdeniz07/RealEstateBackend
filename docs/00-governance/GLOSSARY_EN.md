---
document_id: RE-GOV-004
title: Glossary (English)
version: 0.1.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-13
---

# Glossary — English

| Term | Definition | Evidence |
|------|------------|----------|
| **Advert / Listing** | A property listing offered on the platform | Entity `Advert` (SRC-016) |
| **Advert type** | Classification of listing (e.g. sale/rent) | Entity `AdvertType` |
| **Anonymous user** | Visitor without authentication | LEGACY role poster (SRC-002) |
| **Customer** | Registered user who can create listings | `RoleType.CUSTOMER` (SRC-016) |
| **Manager** | Operational user managing catalogs and messages | LEGACY + admin APIs (SRC-005) |
| **Admin** | Full platform administrator | `RoleType.ADMIN` |
| **Tour request** | Appointment to view a property | Entity `TourRequest`; REST **TBD** |
| **Contact message** | Inquiry submitted via contact channel | Entity `Contact` |
| **Category property key** | Dynamic attribute definition for categories | `CategoryPropertyKey` |
| **JWT** | JSON Web Token for stateless API auth | SRC-006 |
| **Language path param** | `EN`, `TR`, etc. in URL segments | Controllers `/{language}/...` |
| **AS-IS / LEGACY / TO-BE / TBD** | Evidence classification labels | SOURCE_REGISTER |

See Turkish equivalents: [GLOSSARY_TR.md](GLOSSARY_TR.md).
