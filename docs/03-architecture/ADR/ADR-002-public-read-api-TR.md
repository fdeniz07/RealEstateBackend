---
document_id: ADR-002-TR
title: Herkese Acik Okuma API Politikasi
version: 0.5.0
status: approved
language: tr
equivalent_en: ADR-002-public-read-api.md
task: TASK-001
approved_at: 2026-11-13
---

# ADR-002 — Herkese Acik Katalog Okuma Uclari

## Durum

**onaylandi** (Urun Sahibi, **TASK-001**, 2026-11-13).

Yedi acik M1 karari kapsar. Java implementasyonu tek basina bu ADR ile baslamaz; [M1-IMPLEMENTATION-PLAN-TR.md](../../08-project-management/M1-IMPLEMENTATION-PLAN-TR.md) izlenir.

**Onay kapsami disi:** alan bazli DTO listelerinin her satiri; rate limit sayilari; slug migration; depolama saglayicisi; allow-list disi property alanlari.

English: [ADR-002-public-read-api.md](ADR-002-public-read-api.md).

## Onaylanan yedi karar

| # | Konu | Karar |
|---|------|--------|
| 1 | Liste | **P1** `GET /api/v1.0/adverts/{language}/catalog` |
| 2 | DTO | **B** — PublicAdvert*; JPA entity JSON yasak |
| 3 | Gorunurluk | **V3** — ACTIVATED + isActive + deleted=false; liste/detay/gorsel |
| 4 | Gorsel | **Model 2** `GET /api/v1.0/public/images/{imageId}` + V3 |
| 5 | Kimlik | id ve viewCount yok; slug var; propertyValues yalniz allow-list |
| 6 | Whitelist | Uc GET; digerleri JWT; A02–A04, contact POST, getAll, yazma haric |
| 7 | Rate limit | Canli oncesi zorunlu, IP tabanli; Redis zorunlu degil |

## Korunan onaylar

DEC-001-J02, DEC-001-M1-A02-A04, DEC-001-ANONYMOUS-CATALOG — degismedi.

## DEC-006 (M1 public alt kümesi)

Anonim katalog icin **V3** onaylandi. Taslak/Arsiv ve is akisi sorulari acik.

Paket: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md](../../08-project-management/M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md).
