package io.github.grantchen2003.server;

import com.sun.net.httpserver.HttpServer;
import io.github.grantchen2003.handlers.DeleteHandler;
import io.github.grantchen2003.handlers.GetHandler;
import io.github.grantchen2003.handlers.HealthHandler;
import io.github.grantchen2003.handlers.LoggingHandler;
import io.github.grantchen2003.handlers.PutHandler;
import io.github.grantchen2003.store.InMemoryStore;
import io.github.grantchen2003.store.Store;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        final Store store = new InMemoryStore();

        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/get", new LoggingHandler(new GetHandler(store)));
        server.createContext("/put", new LoggingHandler(new PutHandler(store)));
        server.createContext("/delete", new LoggingHandler(new DeleteHandler(store)));
        server.createContext("/health", new LoggingHandler(new HealthHandler(store)));

        server.setExecutor(null);
        server.start();

        System.out.println("Key-value store server started on port 8080");
    }
}
