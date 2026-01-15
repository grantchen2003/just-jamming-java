package io.github.grantchen2003.gateway;

import com.sun.net.httpserver.HttpServer;
import io.github.grantchen2003.handlers.GetHandler;
import io.github.grantchen2003.handlers.PutHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Gateway {
    public static void main(String[] args) throws IOException {
        final List<String> shardIps = List.of(System.getenv("SHARD_IPS").split(","));

        final HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);

        server.createContext("/get", new GetHandler(shardIps));
        server.createContext("/put", new PutHandler(shardIps));

        server.setExecutor(null);
        server.start();

        System.out.println("Key-value store gateway started on port 8083");
    }
}
