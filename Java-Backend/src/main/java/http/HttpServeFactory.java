package http;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServeFactory {
    public static HttpServer create(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newFixedThreadPool(
                Math.max(4, Runtime.getRuntime().availableProcessors() * 2)
        ));

        return  server;
    }
}
