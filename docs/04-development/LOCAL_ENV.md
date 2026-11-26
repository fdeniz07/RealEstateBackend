---
document_id: RE-DEV-LOCAL-ENV-001
title: Local Environment and Datasource Contract
version: 0.4.1
status: draft
language: en
created_at: 2026-11-18
updated_at: 2026-11-26
source_references:
  - DB-INIT-P1
  - RE-TEST-ARCH-001
---

# Local environment and datasource contract

**Canonical test architecture:** [TESTING-ARCHITECTURE.md](../03-architecture/testing/TESTING-ARCHITECTURE.md) §15.

This document defines the **approved environment contract** (DB-INIT-P1). Local and CI integration tests share Spring profile **`test`** and canonical `SPRING_DATASOURCE_*` variables; only the JDBC database name and host port differ (`real_estate_test_db` vs `real_estate_ci_test_db`). **`PostgresIntegrationTestSupport`** centralizes test datasource binding; **`TestReferenceDataFixture`** (`@Profile("test")`) seeds roles, built-in admin, and minimal TR geography. **`DataInitializer`** runs only under profile **`localhost`**.

---

## 1. Environment matrix

| Environment | Spring profile | Database | Host port (JDBC) | Notes |
|-------------|----------------|----------|------------------|--------|
| **Development** | `localhost` | `real_estate_db` | **5433** (mapped from Compose) | Persistent Docker volume; do not delete for test isolation |
| **Local test** | `test` | `real_estate_test_db` | **5433** | Same Postgres instance as dev, **separate database name** (Phase 1+) |
| **GitHub Actions** | `test` | `real_estate_ci_test_db` | **5432** | Ephemeral Postgres 16 (`backend-ci.yml`); **not** `real_estate_db` |

Local development and local tests share the **host port 5433** when using the default Compose mapping (`127.0.0.1:${POSTGRES_PORT}:5432`). CI uses port **5432** on the runner host.

---

## 2. Canonical environment variables

Use these names everywhere (local shell, IDE, CI). Spring Boot **relaxed binding** maps them to `spring.datasource.*` and `spring.profiles.active`.

| Variable | Purpose |
|----------|---------|
| `SPRING_PROFILES_ACTIVE` | `localhost` (dev app) or `test` (local `mvn test` and GitHub Actions) |
| `SPRING_DATASOURCE_URL` | Full JDBC URL including database name |
| `SPRING_DATASOURCE_USERNAME` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | Database password (never commit real values) |
| `JWT_SECRET` | JWT signing — **development** uses gitignored `.env`; **integration tests** require an explicit export (no Java fallback); **CI** maps from repository secret `CI_TEST_JWT_SECRET` |

**JWT separation:** Use a **dedicated integration-test JWT** for profile `test` / `mvn test`. Do **not** reuse the production or localhost development JWT from `.env` unless you deliberately choose the same value locally. Never commit test or production JWT values.

**Precedence:** When set, environment variables override values in `application.yaml`. Integration tests extending `PostgresIntegrationTestSupport` bind datasource and JWT via `@DynamicPropertySource` (fail-fast if `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, or database name is invalid).

### Example values (placeholders only)

**Development (`localhost`):**

```text
SPRING_PROFILES_ACTIVE=localhost
SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5433/real_estate_db
SPRING_DATASOURCE_USERNAME=db_user
SPRING_DATASOURCE_PASSWORD=<your-local-password>
JWT_SECRET=<base64-encoded-secret-min-32-bytes>
```

**Local test (`test`):**

```text
SPRING_PROFILES_ACTIVE=test
SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5433/real_estate_test_db
SPRING_DATASOURCE_USERNAME=db_user
SPRING_DATASOURCE_PASSWORD=<your-local-password>
JWT_SECRET=<dedicated-integration-test-base64-secret>
```

**GitHub Actions (`test` — no secrets in repo):**

| Source | Maps to |
|--------|---------|
| Repository secret **`CI_TEST_JWT_SECRET`** | Job env `JWT_SECRET` |

Workflow **`.github/workflows/backend-ci.yml`** also sets `SPRING_*` and validates that `JWT_SECRET` is non-blank before `mvn test`. The value must be a **non-production** integration-test JWT (same team convention as local test runs). The Postgres service creates **`real_estate_ci_test_db`**; health check waits with `pg_isready` before Maven runs.

---

## 3. Docker Compose vs Maven

| Tool | Reads `.env`? | Typical use |
|------|---------------|-------------|
| **Docker Compose** | **Yes** (project root `.env`) | `POSTGRES_*` for container bootstrap |
| **`mvn test` / `mvn spring-boot:run`** | **No** | Must receive variables via shell, IDE, or a wrapper script |

Copy `.env.example` to `.env` (gitignored). Compose uses it; **export canonical variables before Maven** or use the examples below.

**Do not** commit `.env` or real passwords. `.gitignore` excludes `.env` and `.env.*` except `.env.example`.

### Legacy Compose variables (still used by `compose.yaml`)

| Variable | Role |
|----------|------|
| `POSTGRES_DB` | Initial database created by the Postgres image (development DB name) |
| `POSTGRES_USER` / `POSTGRES_PASSWORD` | Superuser credentials for the container |
| `POSTGRES_PORT` | Host port bind (`.env.example` default **5433**; `compose.yaml` maps `127.0.0.1:${POSTGRES_PORT}:5432`) |

Align `SPRING_DATASOURCE_*` with these values when running locally.

---

## 4. Spring profiles and configuration files (Phase 1)

| Profile | Config file | Datasource |
|---------|-------------|------------|
| `localhost` | `application.yaml` (profile section) | `SPRING_DATASOURCE_*` with **development-only** `POSTGRES_*` fallbacks |
| `docker` | `application-docker.yaml` | `SPRING_DATASOURCE_*` — default host **`postgres:5432`** inside Compose network |
| `test` | `application-test.yaml` | **Only** `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` (no fallback) |

Activate local test configuration:

```text
SPRING_PROFILES_ACTIVE=test
SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5433/real_estate_test_db
SPRING_DATASOURCE_USERNAME=db_user
SPRING_DATASOURCE_PASSWORD=<your-local-password>
JWT_SECRET=<base64-secret>
```

If any required variable is missing under profile `test`, the context **fails to start** (no silent connection to `real_estate_db`).

Flyway and Hibernate `validate` are enabled under `test` (same migration classpath as development).

---

## 5. Create `real_estate_test_db` safely (existing Docker volume)

The Compose file creates only `POSTGRES_DB` (`real_estate_db`) on **first** volume initialization. Entrypoint init scripts **do not run** on an already-initialized volume.

**Never** use `docker compose down -v`, drop `real_estate_db`, or delete the physical volume `realestate-backend_realestate_pg_data` (Compose logical key `realestate_pg_data`).

### Verify database exists

Prefer Compose service exec (container names are generated for project `re`):

```powershell
docker compose exec postgres psql -U db_user -d real_estate_db -tAc "SELECT datname FROM pg_database WHERE datname = 'real_estate_test_db'"
```

Empty output means the database is missing.

### Create database (one-time, idempotent)

Use the same `db_user` / password as in your local `.env` (not committed):

**PowerShell / Docker (recommended):**

```powershell
docker compose exec postgres psql -U db_user -d postgres -c "CREATE DATABASE real_estate_test_db OWNER db_user ENCODING 'UTF8'"
```

If the database already exists, Postgres returns an error — safe to ignore.

**psql from host (port 5433):**

```powershell
psql -h 127.0.0.1 -p 5433 -U db_user -d postgres -f docs/04-development/postgres/create-real-estate-test-db.sql
```

**Fresh Compose volumes only (future installs):** an init script could be mounted under `docker-entrypoint-initdb.d`; that does **not** run against this developer’s existing volume unless recreated (not recommended).

### Distinguish databases

| Database | Purpose |
|----------|---------|
| `real_estate_db` | Development data (`localhost` profile) — **preserve** |
| `real_estate_test_db` | Automated local tests (`test` profile) — Flyway may migrate on first Spring boot against this DB |

---

## 6. Loading environment for local tests (recommended)

**Preferred (least surprise):** set the five canonical variables explicitly, then run Maven.

**PowerShell (example — replace placeholders):**

```powershell
$env:SPRING_PROFILES_ACTIVE = "test"
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://127.0.0.1:5433/real_estate_test_db"
$env:SPRING_DATASOURCE_USERNAME = "db_user"
$env:SPRING_DATASOURCE_PASSWORD = "change_me_local_only"
$env:JWT_SECRET = "replace_with_base64_secret"
.\mvnw.cmd test
```

**Bash (example — replace placeholders):**

```bash
export SPRING_PROFILES_ACTIVE=test
export SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5433/real_estate_test_db
export SPRING_DATASOURCE_USERNAME=db_user
export SPRING_DATASOURCE_PASSWORD=change_me_local_only
export JWT_SECRET=replace_with_base64_secret
./mvnw test
```

**IDE:** Duplicate the same five variables in the test Run Configuration.

**Future (Phase 2+):** Optional `scripts/run-tests.ps1` / `scripts/run-tests.sh` may read `.env` and export canonical names before invoking Maven.

---

## 7. Flyway and seed data (contract)

| Owner | Responsibility |
|-------|----------------|
| **Flyway V1–V4** | Schema and versioned reference data (e.g. `languages` in V3). **Immutable** — never edit applied migrations. |
| **DataInitializer** | Profiles **`localhost`** and **`docker`** — full enum geography and built-in admin for development containers. |
| **TestReferenceDataFixture** | Profile **`test` only** — idempotent roles, `admin@mail.com`, minimal TR (country / city / district); no bulk country enum load. |
| **LanguageRegistryInitializer** | All profiles — loads language snapshot after `ApplicationReadyEvent`. |

Automated tests must use `real_estate_test_db` (local) or `real_estate_ci_test_db` (CI); see [TESTING-ARCHITECTURE.md](../03-architecture/testing/TESTING-ARCHITECTURE.md) §15.

---

## 8. Docker Compose (Phase 1 — project `re`, services `postgres` + `api`)

**Diagrams and topology:** [DOCKER-COMPOSE-ARCHITECTURE.md](../03-architecture/infrastructure/assets/DOCKER-COMPOSE-ARCHITECTURE.md).

| Mode | Command | JDBC target |
|------|---------|-------------|
| **Compose dev stack** | `docker compose up -d --build` | `api` → `jdbc:postgresql://postgres:5432/real_estate_db` |
| **Host API (legacy)** | `mvnw spring-boot:run` + profile `localhost` | `127.0.0.1:5433/real_estate_db` |
| **Compose integration tests** | `docker compose --profile test run --rm test-runner` | `postgres:5432/real_estate_test_db` |
| **Host integration tests** | export `test` profile env + `mvn test` | `127.0.0.1:5433/real_estate_test_db` |

**Naming:** Compose project `name: re`. Physical volume pin `realestate-backend_realestate_pg_data`. Default API host port **`BACKEND_PORT=8081`**.

**Safe transition from project `realestate-backend`:** If containers `realestate-postgres` / `realestate-backend` are running, **stop that project first** (`docker compose -p realestate-backend stop`). Only one Postgres may mount the pinned volume. Never `down -v`.

**Fail-fast env (Compose `${VAR:?…}`):** `POSTGRES_*`, `JWT_SECRET`. For profile `test`: also `JWT_SECRET_TEST`. For profile `tools`: also `PGADMIN_DEFAULT_EMAIL` / `PGADMIN_DEFAULT_PASSWORD`. Copy placeholders from `.env.example` — do not commit real secrets.

**JWT separation in `.env`:**

| Variable | Use |
|----------|-----|
| `JWT_SECRET` | Development API (`localhost` / Compose `api`) |
| `JWT_SECRET_TEST` | Compose `test-runner` only (mapped to `JWT_SECRET` inside the runner) |

**DataInitializer (profiles `localhost` and `docker`):** Seeds roles, geography, and built-in admin **only when the related tables are empty**. Does **not** overwrite existing rows. Documented in source `DataInitializer.java`.

**pgAdmin (optional):** `docker compose --profile tools up -d pgadmin` → `http://127.0.0.1:${PGADMIN_PORT:-5050}` (credentials from `.env`). Register server host **`postgres`**, port **5432**.

**Test databases on existing volumes:** Init script `docker/postgres/init/01-create-test-databases.sh` runs **only** when the data directory is empty. On an **existing** pinned volume, follow §5 — **do not** recreate the volume.

---

## 9. Safety rules

- **Do not** delete `real_estate_db` or run `docker compose down -v` to “reset tests” (destroys the development volume).
- **Do not** put production credentials in Git or in `.env.example`.
- Use `real_estate_test_db` (local) or `real_estate_ci_test_db` (CI) for automated tests after database separation is implemented.

---

## Change history

| Version | Date | Summary |
|---------|------|---------|
| 0.1.0 | 2026-11-18 | DB-INIT-P1 Phase 0 — environment contract |
| 0.2.0 | 2026-11-18 | Phase 1 — `application-test.yaml`, test DB provisioning |
| 0.3.0 | 2026-11-18 | Phase 4 — CI `backend-ci.yml` contract, fixture / initializer split documented |
| 0.3.1 | 2026-11-18 | GitGuardian — `CI_TEST_JWT_SECRET`, explicit local `JWT_SECRET`, no Java JWT fallback |
| 0.4.0 | 2026-11-26 | Docker Compose Phase 1 — `docker` profile, backend container, test-runner profile, JWT_SECRET_TEST |
| 0.4.1 | 2026-11-26 | Project `re`, service `api`, volume pin, BACKEND_PORT 8081, fail-fast env, compose exec docs |