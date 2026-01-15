package io.github.grantchen2003.handlers;

import com.sun.net.httpserver.HttpExchange;
import io.github.grantchen2003.routing.ShardRouter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GetHandler extends AbstractShardHandler {
    public GetHandler(ShardRouter router) {
        super(router);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        final String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("key=")) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        final String key = query.substring(4);
        final String shardIp = shardRouter.getShardIp(key);

        final String encodedKey = java.net.URLEncoder.encode(key, StandardCharsets.UTF_8);
        final URI shardUri = URI.create("http://" + shardIp + "/get?key=" + encodedKey);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(shardUri)
                .GET()
                .build();

        try {
            final HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            exchange.sendResponseHeaders(response.statusCode(), response.body().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.body());
            }

            System.out.println("GET " + request.uri() + " | Response: " + response.statusCode());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            exchange.sendResponseHeaders(500, -1);
        } catch (IOException e) {
            exchange.sendResponseHeaders(500, -1);
        }
    }
}
