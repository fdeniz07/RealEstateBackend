---
document_id: RE-DDD-DOM-001
title: Domain Overview
version: 0.1.0
status: draft
language: en
---

# Domain Model Overview

## AS-IS entity catalogue (SRC-016)

`User`, `Role`, `Advert`, `AdvertType`, `Category`, `CategoryPropertyKey`, `CategoryPropertyValue`, `Country`, `City`, `District`, `Favorite`, `TourRequest`, `Contact`, `Message`, `Image`, `AttachFile`, `Social`, `Log`.

## Advert aggregate (candidate)

```mermaid
classDiagram
  class Advert {
    +String title
    +Double price
    +AdvertStatus status
    +boolean isActive
  }
  class AdvertType
  class User
  class Country
  class City
  class District
  Advert --> AdvertType
  Advert --> User
  Advert --> Country
  Advert --> City
  Advert --> District
```

**Figure RE-FIG-DOM-001** — Advert associations (AS-IS JPA).

## Value object candidates (TO-BE)

| VO | Replaces |
|----|----------|
| `Money` | `Double price` |
| `Slug` | `String slug` |
| `EmailAddress` | user email field |
| `LocaleCode` | path `{language}` |

## Domain events (TO-BE)

- `AdvertPublished`, `TourRequestSubmitted`, `ContactMessageReceived`

Not implemented AS-IS.
