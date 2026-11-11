import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServidorPortfolio {

    public static void main(String[] args) throws IOException {
        int porta = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

        // Rota principal que serve HTML, CSS, JS e Imagens
        server.createContext("/", new StaticFileHandler());

        // Rota de API em Java para receber mensagens do formulário de contato
        server.createContext("/api/contato", new ContatoHandler());

        server.setExecutor(null);
        System.out.println("======================================================");
        System.out.println("Servidor Java do Portfólio da Profa. Conceição ativo!");
        System.out.println("Acesse no navegador: http://localhost:" + porta);
        System.out.println("======================================================");
        server.start();
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uriPath = exchange.getRequestURI().getPath();
            if (uriPath.equals("/")) {
                uriPath = "/index.html";
            }

            // Busca o arquivo na raiz do projeto
            Path filePath = Paths.get("." + uriPath).normalize();
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = getContentType(filePath.toString());
                byte[] bytes = Files.readAllBytes(filePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                String notFound = "404 - Arquivo nao encontrado";
                exchange.sendResponseHeaders(404, notFound.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(notFound.getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html; charset=UTF-8";
            if (path.endsWith(".css")) return "text/css; charset=UTF-8";
            if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            return "application/octet-stream";
        }
    }

    static class ContatoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                String decoded = URLDecoder.decode(body, StandardCharsets.UTF_8);

                System.out.println("[Nova Mensagem Recebida para Profa. Conceição]: " + decoded);

                String resposta = "Obrigado! Sua mensagem foi enviada com sucesso para a Profa. Conceição.";
                byte[] respBytes = resposta.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(200, respBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(respBytes);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}
