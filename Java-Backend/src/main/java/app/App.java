package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import controller.UserController;
import infrastructure.db.DbConfig;
import infrastructure.db.JdbcUserRepository;
import application.user.UserService;
import application.user.UserServiceImpl;
import http.HttpServeFactory;
import http.UserHandler;
import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws Exception {
        Properties cfg = loadProps();
        int port = Integer.parseInt(cfg.getProperty("server.port", "8080"));

        DbConfig db = new DbConfig(
                cfg.getProperty("db.url"),
                cfg.getProperty("db.user"),
                cfg.getProperty("db.password")
        );

        var userRepo = new JdbcUserRepository(db);
        UserService userService = new UserServiceImpl(userRepo);
        UserController userController = new UserController(userService);

        // Create and start HTTP server
        HttpServer server = HttpServeFactory.create(port);
        server.createContext("/users", new UserHandler(userService, userController));

        server.start();
        System.out.println("Server started on port " + port);
    }

    private static Properties loadProps() throws IOException {
        Properties p = new Properties();
        try (InputStream in = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if(in != null) p.load(in);
        }
        return p;
    }
}
