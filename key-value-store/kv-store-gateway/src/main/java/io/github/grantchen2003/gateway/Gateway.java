package io.github.grantchen2003.gateway;

import com.sun.net.httpserver.HttpServer;
import io.github.grantchen2003.handlers.DeleteHandler;
import io.github.grantchen2003.handlers.GetHandler;
import io.github.grantchen2003.handlers.PutHandler;
import io.github.grantchen2003.routing.ShardRouter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

public class Gateway {
    public static void main(String[] args) throws IOException {
        final List<String> shardIps = List.of(System.getenv("SHARD_IPS").split(","));
        final ShardRouter shardRouter = new ShardRouter(shardIps);

        final HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);

        server.createContext("/get", new GetHandler(shardRouter));
        server.createContext("/put", new PutHandler(shardRouter));
        server.createContext("/delete", new DeleteHandler(shardRouter));

        final int cores = Runtime.getRuntime().availableProcessors();
        server.setExecutor(Executors.newFixedThreadPool(cores * 2));

        server.start();
        System.out.println("Key-value store gateway started on port 8083");
    }
}
