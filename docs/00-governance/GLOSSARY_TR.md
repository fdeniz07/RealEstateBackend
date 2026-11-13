---
document_id: RE-GOV-005
title: Sözlük (Türkçe)
version: 0.1.0
status: draft
language: tr
owner: RealEstate Project
created_at: 2026-11-13
updated_at: 2026-11-13
---

# Sözlük — Türkçe

| Terim | Tanım | Kanıt |
|-------|-------|-------|
| **İlan (Advert)** | Platformda sunulan gayrimenkul ilanı | `Advert` varlığı (SRC-016) |
| **İlan tipi** | İlanın sınıflandırması (satılık/kiralık vb.) | `AdvertType` |
| **Anonim kullanıcı** | Kimlik doğrulaması olmayan ziyaretçi | LEGACY rol posteri (SRC-002) |
| **Müşteri (Customer)** | Kayıtlı kullanıcı; ilan verebilir | `RoleType.CUSTOMER` |
| **Yönetici (Manager)** | Katalog, mesaj ve operasyon yönetimi | LEGACY + admin API’leri |
| **Admin** | Tam yetkili platform yöneticisi | `RoleType.ADMIN` |
| **Tur / randevu talebi** | Mülk gezisi için talep | `TourRequest`; REST **TBD** |
| **İletişim mesajı** | İletişim kanalından gelen başvuru | `Contact` |
| **Kategori özellik anahtarı** | Kategoriye özel dinamik alan tanımı | `CategoryPropertyKey` |
| **JWT** | Durumsuz API kimlik doğrulama token’ı | SRC-006 |
| **Dil yolu parametresi** | URL’de `/{language}/...` segmenti | Denetleyiciler |
| **AS-IS / LEGACY / TO-BE / TBD** | Kanıt sınıflandırma etiketleri | SOURCE_REGISTER |

İngilizce karşılıklar: [GLOSSARY_EN.md](GLOSSARY_EN.md).
