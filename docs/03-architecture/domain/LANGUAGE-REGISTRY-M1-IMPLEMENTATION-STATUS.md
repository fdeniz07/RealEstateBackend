---
document_id: RE-ARCH-LREG-M1-001
title: Language Registry — M1 Implementation Status
version: 1.0.0
status: draft
language: en
owner: Engineering
created_at: 2026-11-15
related_documents:
  - LANGUAGE-REGISTRY-DESIGN.md
  - PRODUCT_DECISIONS.md
  - I18N_STRATEGY.md
---

# Language Registry — M1 Implementation Status

**Purpose:** Record **completed** M1 Language Registry engineering (IMPL-LREG-01 … 04.5.1).

**Authority:** [PRODUCT_DECISIONS.md](../../08-project-management/PRODUCT_DECISIONS.md) §8 (DEC-007).
**Design reference:** [LANGUAGE-REGISTRY-DESIGN.md](LANGUAGE-REGISTRY-DESIGN.md) (ARCH-2 proposal — §13 WBS numbering differs from delivery packages below).
**Multilingual architecture overview:** [I18N-L10N-ARCHITECTURE.md](../i18n/I18N-L10N-ARCHITECTURE.md) (summarizes registry role — **does not** replace this status doc).

**Out of M1 scope (not implemented):** `UiLanguageAdapter`, public P1/P2 content resolution, `SlugNormalizer` registry integration, admin CRUD, Redis/Spring Cache, legacy controller/route changes.

---

## 1. Completed work packages

| Package | Deliverable | Location (summary) |
|---------|-------------|-------------------|
| **IMPL-LREG-01** | Flyway `languages` table + seed `de`, `tr`, `en` | `src/main/resources/db/migration/V3__languages_registry.sql` |
| **IMPL-LREG-02** | JPA `Language` entity, `TextDirection`, mapping tests | `com.prettier.entity.concretes.Language` |
| **IMPL-LREG-03** | `LanguageRepository` + ordering/query methods | `com.prettier.repository.LanguageRepository` |
| **IMPL-LREG-04.1** | Canonicalizer, syntax validator, descriptors, exceptions | `com.prettier.shared.i18n.registry` (foundation types) |
| **IMPL-LREG-04.2** | `LanguageRegistrySnapshot`, `LanguageRegistrySnapshotBuilder` | same package |
| **IMPL-LREG-04.3** | `LanguageRegistryService` / `Impl`, `LanguageRegistrySnapshotLoader`, `AtomicReference` snapshot | same package |
| **IMPL-LREG-04.3.1** | `reloadSnapshot()` propagates `RuntimeException` (no swallow) | `LanguageRegistryServiceImpl` |
| **IMPL-LREG-04.4** | Startup load via `ApplicationReadyEvent`; startup fails on reload error | `LanguageRegistryInitializer` |
| **IMPL-LREG-04.4.1** | Empty DB snapshot rejected in loader (`IllegalStateException`) | `LanguageRegistrySnapshotLoader` |
| **IMPL-LREG-04.5** | PostgreSQL acceptance tests (9 scenarios) | `LanguageRegistryPostgreSqlAcceptanceTest` |
| **IMPL-LREG-04.5.1** | Test isolation: `@Transactional` rollback + post-test `reloadSnapshot()` | same test class |

---

## 2. Database schema and seed (M1)

**Migration:** `V3__languages_registry.sql` (additive; does not alter `adverts` / V2 slug index).

| Column | Notes |
|--------|--------|
| `code` | PK, varchar(35) |
| `parent_code` | FK → `languages(code)`, optional |
| `name`, `native_name` | varchar(100) |
| `is_active`, `is_ui_supported`, `is_content_supported` | boolean |
| `text_direction` | `LTR` \| `RTL` |
| `sort_order` | smallint |

**Seed (Flyway):** `de` (10), `tr` (20), `en` (30) — all active, UI + content supported, LTR.

---

## 3. Runtime behavior (as implemented)

### 3.1 Lifecycle

```text
Flyway migrate → Spring context refresh → CommandLineRunner (DataInitializer) → ApplicationReadyEvent
  → LanguageRegistryInitializer → LanguageRegistryService.reloadSnapshot()
```

- **First load:** synchronous `ApplicationReadyEvent` listener; **no** `@Async`, **no** constructor/static load.
- **Failure:** exception from `reloadSnapshot()` fails application startup (verified in `LanguageRegistryInitializerTest`).
- **Empty registry:** loader throws `IllegalStateException` if zero rows; snapshot `AtomicReference` is **not** updated on failure.

### 3.2 In-memory snapshot (PO deviation from ARCH-2 §7 draft)

- **Implemented:** single `AtomicReference<LanguageRegistrySnapshot>` on `LanguageRegistryServiceImpl`, replaced only after successful load.
- **Not implemented:** `spring-boot-starter-cache` / `ConcurrentMapCacheManager` (explicit PO: no Spring Cache M1).

### 3.3 Service API semantics

| Method | Behavior |
|--------|----------|
| `canonicalizeCode` | Trim, lowercase subtags, BCP-47-style syntax validation; **no** registry lookup |
| `findByCode` | Exact match on canonical code after canonicalization; **no** parent fallback |
| `requireActive` / `supports` | Syntax → registry → active → capability (`UI_MESSAGE` / `ADVERT_CONTENT`) |
| `listActive(capability)` | Derived from snapshot; deterministic snapshot order |
| `reloadSnapshot()` | Load via loader; on failure log + rethrow; keep previous snapshot |

**Capabilities (M1):** `UI_MESSAGE` (`is_ui_supported`), `ADVERT_CONTENT` (`is_content_supported`).

### 3.4 Loader

- `@Transactional(readOnly = true)` + `LanguageRepository.findAll(sortOrder, code)`.
- Builds immutable `LanguageRegistrySnapshot`; rejects empty result set before return.

---

## 4. Test strategy and results

| Layer | Classes |
|-------|---------|
| Unit / component | `LanguageCodeCanonicalizerTest`, `LanguageTagSyntaxValidatorTest`, `RegistryFoundationTypesTest`, `LanguageRegistrySnapshotTest`, `LanguageRegistrySnapshotBuilderTest`, `LanguageRegistrySnapshotLoaderTest`, `LanguageRegistryServiceImplTest`, `LanguageRegistryInitializerTest` |
| JPA / Flyway | `LanguageEntityMappingTest`, `LanguageRepositoryTest` |
| Full context | `RealEstateApplicationTests` |
| PostgreSQL acceptance | `LanguageRegistryPostgreSqlAcceptanceTest` (9 tests) |

**Configuration:** `@DynamicPropertySource` → `SPRING_DATASOURCE_*` or default `jdbc:postgresql://localhost:5433/real_estate_db`, `flyway.enabled=true`, `ddl-auto=validate`.

**Acceptance scenarios (04.5):** Flyway V1–V3, seed presence, repository ordering/parent, descriptor mapping, empty rejection, capabilities, initialized snapshot, DataInitializer coexistence.

**Isolation (04.5.1):** acceptance class `@Transactional` + `@AfterEach` rollback and `reloadSnapshot()`; **no** manual `deleteAll` backup/restore.

**Last verified full suite:** `mvn test` — **136** tests, **0** failures/errors/skipped (Docker Maven + PostgreSQL via `host.docker.internal:5433`, 2026-11-15).

---

## 5. Known limitations (M1)

| Topic | Status |
|-------|--------|
| Legacy `Language` enum + `{language}` controllers | Unchanged |
| Public catalog / `AdvertTranslation` | Not integrated |
| `SlugNormalizer` registry gate | Not integrated |
| Runtime registry admin / hot reload API | Not implemented (restart for Flyway-only seed changes) |
| Multi-instance cache coherence | N/A (single JVM; snapshot not distributed) |
| `AI_TRANSLATION_TARGET` / `is_translation_supported` | M2 (IMPL-LREG-07 in design doc) |

---

## 6. Acceptance criteria mapping (ARCH-2 §14)

| Criterion | M1 status |
|-----------|-----------|
| Flyway seed + Hibernate `validate` | Met (V3 + integration tests) |
| `listActive(ADVERT_CONTENT)` includes seeded languages | Met (acceptance + unit tests) |
| Unknown / inactive codes fail closed in service API | Met (`UnsupportedLanguageException` / empty optional) |
| `canonicalizeCode(" TR ")` → `tr` | Met (unit tests) |
| No Java enum change for new languages | Met (data-driven table) |
| Snapshot + validation tests | Met (see §4) |
| No public content fallback | Met (registry only; no public route wiring yet) |

---

## 7. Change history

| Version | Date | Summary |
|---------|------|---------|
| 1.0.0 | 2026-11-15 | M1 implementation record for PO review |