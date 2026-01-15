package io.github.grantchen2003.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetHandler implements HttpHandler {
    final List<String> shardIps;

    public GetHandler(List<String> shardIps) {
        this.shardIps = shardIps;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        final String query = exchange.getRequestURI().getQuery();

        if (!query.startsWith("key=")) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        final String key = query.substring(4);
        final String shardIp = getShardIp(key);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + shardIp + "/get?key=" + java.net.URLEncoder.encode(key, StandardCharsets.UTF_8)))
                .GET()
                .build();

        try (final HttpClient client = HttpClient.newHttpClient()){
            final HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            final byte[] responseBody = response.body();

            final int statusCode = response.statusCode();
            System.out.println("GET " + request.uri() + " | Response: " + statusCode);

            if (statusCode == 200) {
                exchange.sendResponseHeaders(200, responseBody.length);
                final OutputStream os = exchange.getResponseBody();
                os.write(responseBody);
                os.close();
            } else {
                exchange.sendResponseHeaders(statusCode, -1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            exchange.sendResponseHeaders(500, -1);
        } catch (IOException e) {
            exchange.sendResponseHeaders(500, -1);
        }
    }

    private String getShardIp(String key) {
        final int keyHash = Math.abs(key.hashCode());
        final int shardIndex = keyHash % shardIps.size();
        return shardIps.get(shardIndex);
    }
}
