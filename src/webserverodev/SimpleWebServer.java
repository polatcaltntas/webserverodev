package webserverodev;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SimpleWebServer {
    // --- KULLANCI BİLGİLERİNİZİ BURAYA YAZIN ---
    private static final String AD_SOYAD = "ADI SOYADI";
    private static final String OGR_NO   = "OGRENCI_NO";
    // -------------------------------------------

    // HTML içeriği -- burayı istediğiniz gibi genişletebilirsiniz (CSS inline kullanıldı).
    private static final String HTML_PAGE = "<!doctype html>\n" +
            "<html lang=\"tr\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "  <title>Final Değerlendirme</title>\n" +
            "  <style>\n" +
            "    body { font-family: 'Arial', sans-serif; background: linear-gradient(135deg,#f8f9fb,#eef2f7); padding: 40px; }\n" +
            "    .card { max-width: 800px; margin: auto; background: white; border-radius: 12px; box-shadow: 0 6px 18px rgba(0,0,0,0.08); padding: 30px; }\n" +
            "    h1 { font-size: 2.2em; color: #0b5394; margin-bottom: 0.1em; }\n" +
            "    h2 { font-size: 1.4em; color: #073763; margin-top: 0.2em; }\n" +
            "    p.bio { font-size: 1.05em; line-height: 1.6; color: #333333; }\n" +
            "    .meta { font-size: 0.9em; color: #666; margin-top: 12px; }\n" +
            "  </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "  <div class=\"card\">\n" +
            "    <h1>" + AD_SOYAD + "</h1>\n" +
            "    <h2>" + OGR_NO + "</h2>\n" +
            "    <div class=\"meta\">Basit Java Socket tabanlı web sunucu örneği (port 1989)</div>\n" +
            "    <hr />\n" +
            "    <p class=\"bio\">\n" +
            "      Merhaba — bu kısa biyografide kendinizi tanıtabilirsiniz. Bu bölüm renk, font ve basit stil ile biçimlendirilmiştir. " +
            "Buraya eğitim, ilgi alanları veya proje hakkında birkaç cümle ekleyin. Örnek: Bilgisayar mühendisliği öğrencisiyim, " +
            "Java ile socket programlama ve ağ uygulamaları üzerinde çalışıyorum. Bu sayfa, ödevin sunumu için özelleştirilebilir.\n" +
            "    </p>\n" +
            "  </div>\n" +
            "</body>\n" +
            "</html>";

    public static void main(String[] args) {
        final int PORT = 1989;
        System.out.println("SimpleWebServer başlatılıyor... Port: " + PORT);
        ExecutorService pool = Executors.newFixedThreadPool(8); // aynı anda 8 istemciyi basitçe işleyebilir

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            System.out.println("Sunucu hazır. Tarayıcıdan http://localhost:" + PORT + " adresine gidin.");
            while (true) {
                Socket client = serverSocket.accept();
                pool.submit(() -> handleClient(client));
            }
        } catch (IOException e) {
            System.err.println("Sunucu hatası: " + e.getMessage());
            e.printStackTrace();
            pool.shutdown();
        }
    }

    private static void handleClient(Socket client) {
        String clientInfo = client.getRemoteSocketAddress().toString();
        try (
            Socket socket = client;
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))
        ) {
            // HTTP isteğini okuyalım (başlık satırlarını)
            String line;
            // İlk satır isteğin başlangıcı (örn: GET / HTTP/1.1)
            String requestLine = reader.readLine();
            if (requestLine == null) return;
            System.out.println("İstek: " + requestLine + " from " + clientInfo);

            // Header'ları geç (boş satıra kadar)
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // header'ları loglamak isterseniz burada yapabilirsiniz
            }

            byte[] bodyBytes = HTML_PAGE.getBytes(StandardCharsets.UTF_8);
            // HTTP yanıtı gönder
            writer.write("HTTP/1.1 200 OK\r\n");
            writer.write("Content-Type: text/html; charset=utf-8\r\n");
            writer.write("Content-Length: " + bodyBytes.length + "\r\n");
            writer.write("Connection: close\r\n");
            writer.write("\r\n");
            writer.flush();

            // Body'yi ham olarak yaz
            out.write(bodyBytes);
            out.flush();

            System.out.println("Yanıt gönderildi -> " + clientInfo);
        } catch (IOException ex) {
            System.err.println("İstemci işleme hatası (" + clientInfo + "): " + ex.getMessage());
        }
    }
}
