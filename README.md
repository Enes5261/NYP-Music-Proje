Melodix - Müzik Uygulaması 🎵 

Marmara Üniversitesi Bilgisayar Mühendisliği Nesne Yönelimli Programlama dersi kapsamında geliştirilen,
modern ve kullanıcı dostu bir masaüstü müzik çalar platformudur.


📖 Proje Hakkında

Melodix, kullanıcıların yerel müzik dosyalarını yönetebildiği, kişiselleştirilmiş çalma listeleri oluşturabildiği ve modern bir arayüz 
üzerinden müzik dinleyebildiği bir uygulamadır.Projenin temel amacı, karmaşık yazılım sistemlerinde Nesne Yönelimli Programlama (OOP) 
prensiplerini ve tasarım kalıplarını uygulayarak sürdürülebilir bir mimari oluşturmaktır.


✨ Özellikler

Gelişmiş Müzik Kontrolü: JavaFX Media API ile oynatma, duraklatma, parça değiştirme ve dinamik ses seviyesi kontrolü.
Kimlik Doğrulama Sistemi: Kullanıcı kayıt ve giriş özelliklerine sahip, güvenli bir oturum yönetimi.
Kişiselleştirilmiş Çalma Listeleri: Kullanıcıya özel favori listeleri oluşturma, adlandırma ve yönetme imkanı.
Reaktif Kullanıcı Arayüzü: Property Binding mekanizması sayesinde şarkı değiştiğinde anlık olarak güncellenen arayüz elemanları.
Veri Kalıcılığı: Kullanıcı ve çalma listesi verilerinin uygulama kapatıldığında kaybolmaması için JSON tabanlı depolama.


🛠️ Kullanılan Teknolojiler

Dil: Java 21 
Arayüz: JavaFX & Scene Builder 
Veri Yönetimi: Google GSON (JSON Serialization/Deserialization) 
Proje Yönetimi: Maven 


🏗️ Mimari ve OOP Prensipleri 

Uygulama, sorumlulukların ayrıştırıldığı MVC (Model-View-Controller) ve katmanlı mimari üzerine inşa edilmiştir.
Singleton Pattern: AuthService ve MusicPlayerService sınıflarında veri tutarlılığını sağlamak için kullanılmıştır.
Kapsülleme (Encapsulation): Veri güvenliği için tüm model alanları private tanımlanmış ve kontrollü erişim için Getter/Setter metodları uygulanmıştır.
Kalıtım (Inheritance): Projeye özgü MelodixException sınıfı ile merkezi ve anlamlı bir hata yönetimi yapısı kurulmuştur.
Polimorfizm: Metot overloading (farklı constructor yapıları) ve overriding (toString, equals gibi temel metodların özelleştirilmesi) teknikleri kullanılmıştır.


🚀 Kurulum

Bu depoyu bilgisayarınıza klonlayın.
Projenizi IntelliJ IDEA veya Eclipse gibi bir IDE ile "Maven Projesi" olarak açın.
Maven bağımlılıklarının yüklenmesini bekleyin.
Main.java sınıfını çalıştırarak uygulamayı başlatın.


👥 Geliştiriciler 

Melih Enes Karmil – 170423017 
Mhd Dıaa Alsebaı – 170423954 
Ömer Musab Çiçek – 170420510


📸 Uygulama Ekran Görüntüleri

<img width="1202" height="733" alt="image" src="https://github.com/user-attachments/assets/fcd9a85c-b0a2-4310-aa46-b9936536e66c" />
<img width="1201" height="731" alt="image" src="https://github.com/user-attachments/assets/3e58b3a7-b9c8-4be8-9855-112a7f1cef61" />
<img width="505" height="731" alt="image" src="https://github.com/user-attachments/assets/cbb736ba-61d2-406a-8347-e1ac4475ce70" />
<img width="1202" height="729" alt="image" src="https://github.com/user-attachments/assets/5adf82f6-940a-41bc-a786-0d00fdfea7ca" />


