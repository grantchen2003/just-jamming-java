package io.github.grantchen2003.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

        final String shardUrlStr = "http://" + shardIp + "/get?key=" + java.net.URLEncoder.encode(key, StandardCharsets.UTF_8);
        final URL shardUrl;
        try {
            shardUrl = new URI(shardUrlStr).toURL();
        } catch (URISyntaxException e) {
            exchange.sendResponseHeaders(500, -1);
            return;
        }

        HttpURLConnection conn = (HttpURLConnection) shardUrl.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("GET " + shardUrl + " | Response: " + responseCode);
        if (responseCode == 200) {
            final String response = new String(conn.getInputStream().readAllBytes());
            exchange.sendResponseHeaders(200, response.getBytes().length);
            final OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else if (responseCode == 404) {
            exchange.sendResponseHeaders(404, -1);
        }

        conn.disconnect();
    }

    private String getShardIp(String key) {
        final int keyHash = Math.abs(key.hashCode());
        final int shardIndex = keyHash % shardIps.size();
        return shardIps.get(shardIndex);
    }
}
