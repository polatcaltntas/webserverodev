# Java Web Sunucusu

## Genel Bakış
Bu proje, **yalnızca Java’nın standart kütüphaneleri** (`java.net` ve `java.io`) kullanılarak, herhangi bir üçüncü parti bileşen olmadan oluşturulmuş basit bir web sunucusudur.  
Sunucu **1989 numaralı portu** dinler ve herhangi bir web tarayıcısından gelen isteğe küçük bir HTML sayfası ile yanıt verir. Bu sayfa şunları içerir:

- `<h1>`: Ad Soyad  
- `<h2>`: Öğrenci Numarası  
- Renk ve yazı tipi biçimlendirmesi içeren kısa bir biyografi bölümü

---

## Çalışma Prensibi
1. `ServerSocket` nesnesi **1989** portunu dinlemeye başlar.  
2. Bir istemci bağlandığında, yeni bir `Socket` nesnesi bu bağlantıyı yönetir.  
3. Sunucu gelen HTTP isteğini `BufferedReader` ile okur.  
4. `BufferedWriter` kullanarak tarayıcıya geçerli bir HTTP yanıtı ve HTML içeriği gönderir.  
5. Yanıt, tarayıcıda kişisel bilgileri içeren bir web sayfası olarak görüntülenir.

---

## Çalıştırma Adımları
```bash
javac SimpleWebServer.java
java SimpleWebServer
```

Daha sonra tarayıcıdan şu adrese gidin:
```
http://localhost:1989
```

---

## Temel Kavramlar
- **IP Adresi:** Cihazın ağ üzerindeki kimliğidir.  
- **Port:** Aynı IP üzerinde çalışan farklı servisleri ayırt eder (bu projede 1989).  
- **Socket:** IP ve port bilgisini birleştirerek ağ iletişimini sağlar.

---

## Kullanılan Araçlar
- Java SE (ServerSocket, Socket, IO sınıfları)  
- ChatGPT (kod açıklamaları ve dokümantasyon desteği için)  
- Eclipse (geliştirme ortamı)
