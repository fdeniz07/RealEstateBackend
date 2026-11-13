---
document_id: RE-PM-M1-ARCH-001-TR
title: M1 Herkese Acik Katalog - Mimari Karar Paketi
version: 0.2.0
status: approved
language: tr
prepared_by: TASK-016
equivalent_en: M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md
---

# M1 Herkese Acik Katalog - Mimari Karar Paketi (TASK-016)

TASK-013 … TASK-016 kanitlari. **TASK-001** M1 baz cizgisini onayladi (§0.1). §3 alan tablolari **muhendislik referansi**dir.

English: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md](M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS.md).

**ADR:** [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) — **onaylandi** (v0.5.0).

## 0. Mevcut onaylar

DEC-001-J02, DEC-001-M1-A02-A04, DEC-001-ANONYMOUS-CATALOG — degismedi.

### 0.1 PO onayli M1 baz cizgisi (TASK-001)

P1 liste; DTO B; V3 gorunurluk (DEC-006 §7.5); Model 2 gorsel; id/viewCount yok, slug, allow-list propertyValues; uc yollu whitelist; canli oncesi IP rate limit.

Tam metin: [ADR-002-public-read-api-TR.md](../03-architecture/ADR/ADR-002-public-read-api-TR.md).

## 1. Katmanlar

| Katman | Anlam |
|--------|--------|
| PO onayli | §0.1, ADR-002 |
| Muhendislik referansi | §3 alan tablolari |
| Muhendislik detayi | Esikler, migration, depolama |

## 2. P1 vs P1b

**Onay:** **P1** `GET /api/v1.0/adverts/{language}/catalog`.

## 3. Public DTO B

**Onay:** PublicAdvert* tipleri; public yanitta JPA entity yasak. Alan varsayilanlari paket §3 ve TASK-014-DATA — alan bazli ek PO onayi yok.

## 4. Gorunurluk

**Onay:** **V3** — ACTIVATED + isActive + deleted=false; liste, detay, gorsel.

## 5. Gorsel Model 2

**Onay:** `GET /api/v1.0/public/images/{imageId}`; V3 zorunlu; tahmin edilebilir ID yetki degil.

## 6–8. Whitelist ve rate limit

**Onay:** Uc GET; digerleri kimlik dogrulamali; canli oncesi IP rate limit zorunlu.

## 10. Degisiklik gecmisi

| Surum | Tarih | Ozet |
|-------|-------|------|
| 0.1.0 | 2026-11-13 | TASK-016 paket |
| 0.2.0 | 2026-11-13 | TASK-001 onaylari |
