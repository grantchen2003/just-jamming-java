package io.github.grantchen2003.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PutHandler implements HttpHandler {
    final List<String> shardIps;

    public PutHandler(List<String> shardIps) {
        this.shardIps = shardIps;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        final InputStream inputStream = exchange.getRequestBody();
        final String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        final JSONObject jsonObject = new JSONObject(body);

        final String key, value;
        try {
            key = jsonObject.getString("key");
            value = jsonObject.getString("value");
        } catch (JSONException e) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        final String shardIp = getShardIp(key);

        final JSONObject payload = new JSONObject();
        payload.put("key", key);
        payload.put("value", value);

        try (final HttpClient client = HttpClient.newHttpClient()) {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + shardIp + "/put"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("POST " + request.uri() + " | Response: " + response.statusCode());
            exchange.sendResponseHeaders(response.statusCode(), -1);
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
