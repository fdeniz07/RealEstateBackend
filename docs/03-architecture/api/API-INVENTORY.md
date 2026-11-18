---
document_id: RE-API-INV-001
title: REST API Inventory
version: 0.2.0
status: draft
language: en
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-18
source_references:
  - SRC-005
  - SRC-006
---

# REST API Inventory (AS-IS)

**Conventions and TO-BE contract:** [API-ARCHITECTURE.md](API-ARCHITECTURE.md). **Public catalog (approved ADR-002):** [PUBLIC-CATALOG-API-DESIGN.md](PUBLIC-CATALOG-API-DESIGN.md). **Errors:** [ERROR_HANDLING.md](../../04-development/ERROR_HANDLING.md).

**M1 public catalog implementation:** design approved via ADR-002; Java endpoints **not fully implemented** AS-IS.

Base path pattern: `http://{host}:8080/api/v1.0/...` (APPROVED long-term target: `/api/v1/` — see [API_VERSIONING.md](../../04-development/API_VERSIONING.md))

**Default auth:** JWT required except `POST /api/v1.0/auth/**`, limited `POST /api/v1.0/users`, whitelist static/swagger/actuator (see security doc).

Swagger reference screenshots: `assets/screenshots/`.

## Endpoint summary by controller

| Controller | Base path | Mapped methods | Notes |
|------------|-----------|----------------|-------|
| AuthController | `/auth` | 2 | signUp, login — **public** |
| UserController | `/users` | 6 | Profile CRUD + image |
| AdminController | `/admins` | 8 | User admin |
| RoleController | `/roles` | 5 | Role CRUD |
| CountryController | `/countries` | 5 | CRUD |
| CityController | `/cities` | 5 | CRUD |
| DistrictController | `/districts` | 5 | CRUD |
| CategoryController | `/categories` | 10 | Category + properties |
| AdvertController | `/adverts` | 5 | List/create/update/delete |
| ContactController | `/contact-messages` | 3 | List/get/add |
| MessageController | `/messages` | 12 | Mailbox features |
| FavoriteController | `/favorites` | 3 | Partial; user fav POST commented |
| TourRequestController | `/tour-requests` | **0** | Empty |
| AdvertTypeController | `/advert-types` | **0** | Empty |
| ImageController | `/images` | **0** | Empty |
| LogController | `/logs` | **0** | Empty |

## Detailed inventory (verified mappings)

### Authentication — `api/v1.0/auth`

| Method | Path | Auth |
|--------|------|------|
| POST | `/{language}/signUp` | Public |
| POST | `/{language}/login` | Public |

### Users — `api/v1.0/users`

| Method | Path |
|--------|------|
| GET | `/{language}/` |
| GET | `/{language}/getUser/{userId}` |
| PUT | `/{language}/update/{userId}` |
| PUT | `/{language}/update/profile-image/{userId}` (duplicate mapping — **TBD** review) |
| DELETE | `/{language}/delete/{userId}` |

### Admins — `api/v1.0/admins`

| Method | Path |
|--------|------|
| GET | `/{language}/getAllUsers` |
| GET | `/{language}/getAllUsersByActive` |
| GET | `/{language}/getAllUsersByInactive` |
| GET | `/{language}/getAllManagers` |
| GET | `/{language}/getAllCustomers` |
| POST | `/{language}/addUser` |
| PUT | `/{language}/changeUserRole/{userId}` |
| PATCH | `/{language}/changeUserStatus/{userId}` |

### Roles — `api/v1.0/roles`

| Method | Path |
|--------|------|
| GET | `/{language}/roles` |
| GET | `/{language}/getById/{roleId}` |
| POST | `/{language}/addRole` |
| PUT | `/{language}/updateRole/{roleId}` |
| DELETE | `/{language}/deleteRole/{roleId}` |

### Countries / Cities / Districts

Standard CRUD templates: `getAll` or list GET, `get/{id}`, `add`, `update/{id}`, `delete/{id}` under respective bases.

### Categories — `api/v1.0/categories`

| Method | Path |
|--------|------|
| GET | `/{language}/categories` |
| GET | `/{language}/categories/admin` |
| GET | `/{language}/get/{categoryId}` |
| POST | `/{language}/add` |
| PUT | `/{language}/update/{categoryId}` |
| DELETE | `/{language}/delete/{categoryId}` |
| GET | `/{language}/properties/{id}` |
| POST | `/{language}/properties/{id}` |
| PUT | `/{language}/properties/{id}` |
| DELETE | `/{language}/properties/{id}` |

### Adverts — `api/v1.0/adverts`

| Method | Path |
|--------|------|
| GET | `/{language}/getAll` |
| GET | `/{language}/getListWithActive` |
| POST | `/{language}/add` |
| PUT | `/{language}/update/{advertId}` |
| DELETE | `/{language}/delete/{advertId}` |

### Contact messages — `api/v1.0/contact-messages`

| Method | Path |
|--------|------|
| GET | `/{language}/contacts` |
| GET | `/{language}/get/{contactId}` |
| POST | `/{language}/add` |

### Messages — `api/v1.0/messages`

Includes inbox, sent, read/unread, draft, trash, important, spam lists; get by id; send; update; delete.

### Favorites — `api/v1.0/favorites`

| Method | Path | Notes |
|--------|------|-------|
| GET | `/admin/{id}` | User favorites by user id |
| DELETE | `ad` | Delete all — path typo todo |
| DELETE | `/{id}` | Delete one |

## Error model (AS-IS)

`GlobalExceptionHandler` produces `InternalApiResponse` and `ApiErrorResponse` (validation) with `FriendlyMessage` / `FriendlyMessageCodes` — see [ERROR_HANDLING.md](../../04-development/ERROR_HANDLING.md). **TO-BE:** RFC 9457 Problem Details per [API-ARCHITECTURE.md](API-ARCHITECTURE.md).

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Initial inventory |
| 0.2.0 | 2026-11-18 | Links to API architecture; error envelopes; v1.0 note |

## API versioning proposal (TO-BE)

| Topic | Proposal |
|-------|----------|
| Current | URL prefix `v1.0` |
| Future | Header `Accept-Version` or `/v2` for breaking changes |
| OpenAPI | Single springdoc group per version |

See [API_VERSIONING.md](../../04-development/API_VERSIONING.md).

## Figures

![Auth & User Swagger](../../assets/screenshots/Swagger%20UI%20-%20Auth%20&%20User.png)

**Figure RE-FIG-API-001** — Existing Swagger grouping for Auth/User (screenshot evidence).

---

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-13 | Full grep-verified inventory |
