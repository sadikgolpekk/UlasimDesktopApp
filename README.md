# UlasimApp - Java Tabanlı Ulaşım Rota Planlama Uygulaması

![Image](https://github.com/user-attachments/assets/ce40b013-e381-4c70-8995-8597d71a80d9)


## ✨ Genel Tanıtım

**UlasimApp**, Java Swing ile geliştirilmiş, harita mantığı arkaplanda olan bir rota planlama uygulamasıdır. Kullanıcılar, başlangıç ve bitiş noktalarını arayüzden seçerek, en uygun rotayı **süre, ücret ve mesafe** açısından karşılaştırmalı olarak görebilir.

> Bu proje, **Kocaeli Üniversitesi Bilgisayar Mühendisliği 2. sınıf Prolab dersi** kapsamında gerçekleştirilmiştir. Öğrencilere yazılım mühendisliği ilkelerini uygulama imkânı sağlayan bu proje, aynı zamanda takım çalışması, tasarım prensipleri ve yazılım mimarisi açısından da önemli deneyimler sunmuştur.

Uygulama, **SOLID prensipleri** ışığında tasarlanmış olup geliştirilmeye ve genişletilmeye uygun mimarisi sayesinde gelecekte daha büyük sistemlere entegre edilebilir.

---

![Image](https://github.com/user-attachments/assets/bdcfc00d-8343-4010-9032-6dc21131c166)

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


![Image](https://github.com/user-attachments/assets/15b036eb-2b29-47e4-932c-55716ad2817c)

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

- Sadık Gölpek – [github.com/sadikgolpekk](https://github.com/sadikgolpekk)
- Ali Kılınç – [github.com/aliiklnc](https://github.com/aliiklnc)

> Bu proje, Java ile nesne yönelimli programlamaya dair güçlü örnekler sunar. SOLID prensiplerine göre inşa edilerek sürdürülebilir ve genişletilebilir bir yapıya kavuşmuştur.

---

## 📌 Notlar

- Harita görüntüsü kullanılmamıştır, sadece grafik olarak bağlantılar arkaplanda gösterilir.
- Proje genişletmeye uygun şekilde yazılmıştır.
- Diyaloglar (`About`, `Payment`, `Preferences`, vs.) arayüz etkileşimini artırmak için modüler yapıda düşünülmüştür.

  ![Image](https://github.com/user-attachments/assets/6ac9595f-71ab-4cd4-80a7-dbe24b546e6d)
  ![Image](https://github.com/user-attachments/assets/6551f6de-d8c4-4fc8-a710-c99b52e824fe)
---

## 📜 Lisans

 Özgürce kullanılabilir, değiştirilebilir.

> ⭐ Projeyi faydalı bulduysanız GitHub sayfamıza yıldız bırakmayı unutmayın!

