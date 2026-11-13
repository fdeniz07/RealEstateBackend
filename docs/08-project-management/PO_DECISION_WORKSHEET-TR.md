---
document_id: RE-PM-PO-WS-001-TR
title: Ürün Sahibi Karar Çalışma Sayfası
version: 0.1.7
status: draft
language: tr
task: TASK-011
equivalent_en: PO_DECISION_WORKSHEET.md
---

# Ürün Sahibi Karar Çalışma Sayfası

**Talimat:** Her DEC için **Kabul**, **Red**, **Değiştir** veya **Ertele** seçin. Kararınızı **Kararım** alanına yazın. **Onay tarihi** resmi imza olana kadar boş kalsın. Önceden doldurulmuş metin **onay sayılmaz**.

**İnceleme belgesi:** [PO_DECISION_REVIEW-TR.md](PO_DECISION_REVIEW-TR.md) · English: [PO_DECISION_WORKSHEET.md](PO_DECISION_WORKSHEET.md)

---

## DEC-001 — Herkese açık katalog ve anonim okuma

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ A permitAll + public DTO · ☐ B public facade · ☐ C guest JWT · ☐ D gateway · ☐ Diğer: ___ |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. M1 yol listesi, rate limit) — **J02 için DEC-001-J02 aşağıda** |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

### DEC-001-J02 — İletişim mesajı POST (J02)

| Alan | Kayıtlı değer (PO, TASK-011-A) |
|------|--------------------------------|
| **Alt karar** | J02 `POST .../contact-messages/{language}/add` |
| **Seçilen seçenek** | **Yalnızca kimlik doğrulamalı** (anonim **yok**) |
| **İşlem** | **Kabul** |
| **Kararım / değişikliğim** | J02 POST kimlik doğrulaması gerektirir. Anonim kullanıcılar iletişim mesajı gönderemez. |
| **Koşullar ve kısıtlar** | — |
| **Onay tarihi** | 2026-11-13 |
| **Onay durumu** | **Onaylandı** |

### DEC-001-M1-A02–A04 — M1 ürün kapsamı (A02 / A03 / A04)

*Kanıt: [DEC-001-A02-A04-SCOPE-REVIEW-TR.md](DEC-001-A02-A04-SCOPE-REVIEW-TR.md).*

| Alan | Kayıtlı değer (PO, TASK-012-A) |
|------|--------------------------------|
| **M1 kapsam seçeneği** | **D — Minimum uygulanabilir kullanıcı yolculuğu** |
| **İşlem** | **Kabul** |
| **Kararım / değişikliğim** | A02 (Şehirler), A03 (Kategoriler) ve A04 (Popüler ilanlar) **M1 dışında** bırakılır ve **sonraki bir sürüme ertelenir**. Gerekçe: temel **ilan listeleme ve ilan detayı** deneyimini tamamlamak; A02–A04 ilk sürüm kapsamını genişletmemeli. |
| **Erişim notu (kapsamdan ayrı)** | Uygulandığında A02–A04 anonim vs JWT: **çözülmedi** (bu onay erişim politikası tanımlamaz) |
| **Onay tarihi** | 2026-11-13 |
| **Onay durumu** | **Onaylandı** (yalnızca M1 ürün kapsamı) |

### DEC-001-ANONYMOUS-CATALOG — M1 liste ve detay (ürün gereksinimi)

*Kanıt: [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW-TR.md](DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW-TR.md); [PRODUCT_DECISIONS.md](PRODUCT_DECISIONS.md) §2.5.*

| Alan | Kayıtlı değer (PO, TASK-013-A) |
|------|--------------------------------|
| **Ürün gereksinimi** | Anonim ziyaretçiler **M1 emlak listesine göz atabilmeli** ve **M1 ilan detayını kimlik doğrulaması olmadan açabilmelidir** (salt okunur). |
| **İşlem** | **Kabul** |
| **Kararım / değişikliğim** | Bu iki salt okunur kullanıcı yolculuğu için kimlik doğrulama **gerekli değildir**. |
| **Kapsam sınırı (bu satır onaylamaz)** | ADR-002; `permitAll`; uç whitelist’i; Public DTO; görünürlük kuralı; `isActive`/durum/görsel yolu; rate limit; anonim yazma; A02/A03/A04 anonim erişimi; DEC-001-J02 değişikliği |
| **Onay tarihi** | 2026-11-13 |
| **Onay durumu** | **Onaylandı** (yalnızca ürün gereksinimi) |

### DEC-001-PUBLIC-CATALOG — M1 teknik erişim politikası

*Kanıt: [DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW-TR.md](DEC-001-PUBLIC-CATALOG-ACCESS-REVIEW-TR.md). **Ürün gereksinimi DEC-001-ANONYMOUS-CATALOG altında kayıtlı; aşağıdaki teknik alanlar açık.***

| Alan | Girdiniz |
|------|----------|
| **Mimari seçenek** | ☐ A permitAll · ☐ B public facade · ☐ C yalnızca JWT katalog · ☐ D hibrit · ☐ Ertele |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Görünürlük kuralı** | ☐ A isActive · ☐ B ACTIVATED · ☐ C ikisi · ☐ D diğer |
| **Public DTO** | ☐ Zorunlu · ☐ AdvertResponse yeniden kullan · ☐ TBD |
| **Onaylı anonim uçlar** | (yolları açıkça yazın) |
| **Canlı öncesi rate limiting** | ☐ Evet · ☐ Hayır · ☐ TBD |
| **Koşullar ve kısıtlar** | |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

### DEC-001-PUBLIC-DATA-BOUNDARY — Public DTO ve alan sızıntısı (TASK-014-DATA)

*Kanıt: [DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW-TR.md](DEC-001-PUBLIC-DATA-BOUNDARY-REVIEW-TR.md). **Önceden kayıtlı karar yok.***

| Alan | Girdiniz |
|------|----------|
| **Public DTO stratejisi** | ☐ A AdvertResponse yeniden kullan · ☐ B PublicAdvertResponse · ☐ C Okuma modeli · ☐ Ertele |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Public alan allow-list** | (ör. slug, viewCount, geo şekli, isActive/audit çıkarımı) |
| **Görsel dağıtım modeli** | ☐ 1 Doğrudan URL · ☐ 2 Uygulama public GET · ☐ 3 Korumalı · ☐ 4 CDN/nesne · ☐ TBD |
| **İç içe geo/kategori şekli** | |
| **Kategori özellik değerleri politikası** | |
| **Koşullar ve kısıtlar** | (DEC-006 görünürlük ile hizala — ayrı karar) |
| **Onay tarihi** | |
| **Onay durumu** | **Yerini aldı** — DTO B + allow-list: §DEC-001-M1-ARCHITECTURE (TASK-001) |

### DEC-001-PUBLIC-CATALOG-API — M1 API tasarim paketi (TASK-015)

*Kanıt: [PUBLIC-CATALOG-API-DESIGN-TR.md](../03-architecture/api/PUBLIC-CATALOG-API-DESIGN-TR.md). **Kararlar §DEC-001-M1-ARCHITECTURE (TASK-001) altında kayıtlı.***

| Alan | Girdiniz |
|------|----------|
| **Liste yolu** | ☐ P1 `/catalog` · ☐ P1b `getListWithActive` · ☐ Ertele |
| **Detay yolu** | ☐ P2 `/slug/{slug}` · ☐ Diğer: ___ |
| **Public DTO** | ☐ B (tasarımda önerilen) · ☐ A · ☐ C · ☐ Ertele |
| **Görünürlük kuralı** | ☐ V1 · ☐ V2 · ☐ V3 · ☐ V4 · ☐ Ertele (DEC-006) |
| **Görsel dağıtım** | ☐ Model 2 uygulama GET · ☐ Model 1 doğrudan path · ☐ Model 4 CDN · ☐ TBD |
| **`viewCount` / `id` açılımı** | |
| **Güvenlik whitelist** | (permitAll yolları — TASK-001 / ADR-002) |
| **İşlem** | ☐ Tasarımı kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Onay tarihi** | |
| **Onay durumu** | **Yerini aldı** — TASK-001 |

### DEC-001-M1-ARCHITECTURE — Sonlandirma paketi (TASK-016 / **TASK-001 kayıtlı**)

*Kanıt: [M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md](M1-PUBLIC-CATALOG-ARCHITECTURE-DECISIONS-TR.md), [ADR-002](../03-architecture/ADR/ADR-002-public-read-api.md) v0.5.0 (**onaylı**).*

| Alan | Kayıtlı PO kararı (TASK-001) |
|------|------------------------------|
| **Liste yolu** | **P1** `GET /api/v1.0/adverts/{language}/catalog` |
| **Public DTO** | **B** — özel tipler; public JSON'da JPA yok |
| **Görünürlük** | **V3** — DEC-006 §7.5 |
| **Görseller** | **Model 2** + her istekte V3 |
| **`viewCount` / `id`** | **Hariç**; **slug** navigasyon |
| **`propertyValues`** | Yalnız **allow-list** özellikler |
| **Anonim whitelist** | P1, P2 slug, public images |
| **Canlı öncesi rate limit** | **Evet** — IP; Redis zorunlu değil |
| **İşlem** | **Baz çizgiyi kabul** |
| **Onay tarihi** | 2026-11-13 |
| **Onay durumu** | **Onaylandı** |

**Uygulama (TASK-017-M1):** [M1-IMPLEMENTATION-PLAN-TR.md](M1-IMPLEMENTATION-PLAN-TR.md). **IMPL-000** TASK-001 ile karşılandı.

---

## DEC-002 — Müşteri, Manager ve Admin sorumlulukları

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ A LEGACY matris · ☐ B minimal JWT · ☐ C özel matris · ☐ RBAC ertele |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. LEG-BR-007, PERSONAL rolü, onay yetkisi) |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

---

## DEC-003 — Dahili mesajlaşma

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ R1 Tut · ☐ R2 Kaldır · ☐ R3 İletişimle birleştir · ☐ Diğer: ___ |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. aktörler, saklama, GDPR) |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

---

## DEC-004 — Raporlama G01–G05

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ Tam G01–G05 · ☐ MVP: ___ · ☐ Raporları ertele · ☐ Harici BI |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. export formatı, G04 Manager kapsamı) |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

---

## DEC-005 — Şifre kurtarma

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ F03/F04 uygula · ☐ Yalnız admin sıfırlama · ☐ Ertele · ☐ Diğer: ___ |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. e-posta sağlayıcısı, TTL, CAPTCHA) |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

---

## DEC-006 — İlan yaşam döngüsü

| Alan | Girdiniz |
|------|----------|
| **M1 public kapı (TASK-001)** | **V3 onaylandı** — PRODUCT_DECISIONS §7.5 / ADR-002 |
| **Seçilen seçenek** (tam döngü) | ☐ TO-BE A · ☐ TO-BE B · ☐ TO-BE C · ☐ Diğer |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. red görünürlüğü, Draft vs Pending) |
| **Onay tarihi** | 2026-11-13 (yalnız M1 public kapı) |
| **Onay durumu** | **Kısmi** — V3 **onaylı**; geri kalan **onaysız** |

---

## DEC-007 — Çok dilli ilan içeriği

| Alan | Girdiniz |
|------|----------|
| **Seçilen seçenek** | ☐ M1 tek sütun · ☐ M2 paralel sütunlar · ☐ M3 çeviri tablosu · ☐ M4 JSONB · ☐ Şema ertele |
| **İşlem** | ☐ Kabul · ☐ Red · ☐ Değiştir · ☐ Ertele |
| **Kararım / değişikliğim** | |
| **Koşullar ve kısıtlar** | (ör. EN/TR zorunlu, slug, fallback dili) |
| **Onay tarihi** | |
| **Onay durumu** | Onaylanmadı |

---

## İmza bloğu (isteğe bağlı)

| Alan | Değer |
|------|-------|
| Ürün Sahibi adı | |
| İnceleme oturumu tarihi | |
| Notlar | |

**Hatırlatma:** ADR-002 onayı **TASK-001** ile ayrıdır; bu sayfayı doldurmak ADR-002’yi onaylamaz.
