# UlasimApp - Java Tabanlı Ulaşım Rota Planlama Uygulaması

## ✨ Genel Tanıtım

**UlasimApp**, Java Swing ile geliştirilmiş, harita mantığı arkaplanda olan bir rota planlama uygulamasıdır. Kullanıcılar, başlangıç ve bitiş noktalarını arayüzden seçerek, en uygun rotayı **süre, ücret ve mesafe** açısından karşılaştırmalı olarak görebilir.

> Bu proje, **Kocaeli Üniversitesi Bilgisayar Mühendisliği 2. sınıf 2. dönem Prolab I dersi** kapsamında gerçekleştirilmiştir. Öğrencilere yazılım mühendisliği ilkelerini uygulama imkânı sağlayan bu ilk proje, aynı zamanda takım çalışması, tasarım prensipleri ve yazılım mimarisi açısından da önemli deneyimler sunmuştur.

Uygulama, **SOLID prensipleri** ışığında tasarlanmış olup geliştirilmeye ve genişletilmeye uygun mimarisi sayesinde gelecekte daha büyük sistemlere entegre edilebilir.

---

## 📚 Öne Çıkan Özellikler

- Harita paneli üzerinden rota noktaları belirleme (graf yapısı mantığı)
- En uygun rota analizleri: ⏱️ En kısa süreli / 💸 En ucuz / 📏 En kısa mesafe
- Ödeme tipleri: 💳 Kredi Kartı, 🪙 Nakit, 🟦 KentKart
- Kullanıcı profilleri: 👵 Yaşlı, 👨‍🏫 Öğretmen, 🧑‍🎓 Öğrenci, 👤 Genel, ♿ Engelli
- Ödeme türüne özel sesli bildirim desteği
- Haversine ve Manhattan gibi algoritmalarla mesafe stratejisi
- JSON formatında durak ve bağlantı verisi yükleme
- GUI destekli menüler: Ayarlar, Profil, Ödeme, Yardım

---

## 📁 Proje Yapısı

```plaintext
src/
 ├── main/App.java                         # Ana uygulama giriş noktası
 ├── ui/UlasimArayuzu.java                # Ana Swing kullanıcı arayüzü
 ├── ui/HaritaPanel.java                  # Harita paneli ve durak çizimi
 ├── ui/dialog/                           # Yardımcı ayar diyalogları
 │   ├── AboutDialog.java
 │   ├── FAQDialog.java
 │   ├── PaymentDialog.java
 │   ├── PreferencesDialog.java
 │   └── ProfileDialog.java
 ├── service/                              # Uygulama mantığı servisleri
 │   ├── RouteService.java
 │   ├── RouteCalculator.java
 │   ├── SoundPlayer.java
 │   ├── VeriYukleyici.java
 │   ├── HaversineHesaplayici.java
 │   ├── ManhattanHesaplayici.java
 │   └── IMesafeHesaplayici.java
 ├── model/                                # Veri modeli ve soyutlamalar
 │   ├── vehicle/                          # Araçlar
 │   │   ├── Arac.java
 │   │   ├── Otobus.java
 │   │   ├── Taksi.java
 │   │   └── Tramvay.java
 │   ├── payment/                          # Ödeme yöntemleri
 │   │   ├── PaymentMethod.java
 │   │   ├── KentKart.java
 │   │   ├── KrediKarti.java
 │   │   └── Nakit.java
 │   ├── passanger/                        # Yolcu türleri
 │   │   ├── IYolcu.java
 │   │   ├── GenelYolcu.java
 │   │   ├── Yasli.java
 │   │   ├── Engelli.java
 │   │   ├── Ogrenci.java
 │   │   └── Ogretmen.java
 │   └── routing/                          # Duraklar ve bağlantılar
 │       ├── Durak.java
 │       ├── DurakBaglanti.java
 │       └── DurakTransfer.java
```

---

## 🌐 SOLID Prensipleri

**S - Single Responsibility Principle**
> Her sınıf yalnızca bir sorumluluğa sahiptir. Örn: `SoundPlayer` yalnızca ses çalar, `VeriYukleyici` yalnızca veriyi yükler.

**O - Open/Closed Principle**
> Mevcut kodu bozmadan yeni ödeme türü veya yolcu tipi kolayca eklenebilir.

**L - Liskov Substitution Principle**
> `Yolcu`, `Öğretmen` ya da `Yaşlı` türleri birbirinin yerine sorunsuz geçebilir.

**I - Interface Segregation Principle**
> `IYolcu` ve `IMesafeHesaplayici` yalnızca ilgili işlemleri tanımlar.

**D - Dependency Inversion Principle**
> `RouteService` gibi sınıflar, `IMesafeHesaplayici` gibi soyutlamalar üzerinden çalışır.

---

## 🚀 Projeyi Çalıştırma

### `.bat` Dosyasıyla (Windows)
```bat
@echo off
cd /d "%~dp0"
if not exist "bin" mkdir bin
javac -cp "lib/json-20210307.jar" -d bin src/main/*.java src/model/routing/*.java src/model/passanger/*.java  src/model/payment/*.java src/model/vehicle/*.java   src/service/*.java src/ui/*.java src/ui/dialog/*.java 
if %errorlevel% neq 0 exit /b %errorlevel%
java -cp "bin;lib/json-20210307.jar" main.App
pause
```

> Bu `.bat` dosyasını opsiyonel olarak `.exe` veya `.jar` formatına dönüştürerek çalıştırabilirsiniz.

---

## 👨‍💻 Geliştirici Ekibi

- Sadık Gölpek – [github.com/sadikgolpek](https://github.com/sadikgolpek)
- Ali Kılınç – [github.com/aliklnc](https://github.com/aliklnc)

> Bu proje, Java ile nesne yönelimli programlamaya dair güçlü örnekler sunar. SOLID prensiplerine göre inşa edilerek sürdürülebilir ve genişletilebilir bir yapıya kavuşmuştur.

---

## 📌 Notlar

- Harita görüntüsü kullanılmamıştır, sadece grafik olarak bağlantılar arkaplanda gösterilir.
- Proje genişletmeye uygun şekilde yazılmıştır.
- Diyaloglar (`About`, `Payment`, `Preferences`, vs.) arayüz etkileşimini artırmak için modüler yapıda düşünülmüştür.

---

## 📜 Lisans

MIT Lisansı – Özgürce kullanılabilir, değiştirilebilir.

> ⭐ Projeyi faydalı bulduysanız GitHub sayfamıza yıldız bırakmayı unutmayın!

