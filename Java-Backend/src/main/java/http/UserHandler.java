// http/UsersHandler.java
package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import application.user.UserService;
import controller.UserController;
import domain.user.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserHandler implements HttpHandler {
    private final UserService service;
    private final UserController userController;

    public UserHandler(UserService service, UserController userController) {
        this.service = service;
        this.userController = userController;
    }

    @Override public void handle(HttpExchange ex) throws IOException {
        try {
            String method = ex.getRequestMethod();
            ex.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            if ("GET".equals(method)) {
                List<User> users = service.getAllUsers();
                String json = toJson(users); // manual JSON (pure)
                byte[] body = json.getBytes(StandardCharsets.UTF_8);
                ex.sendResponseHeaders(200, body.length);
                ex.getResponseBody().write(body);
            } else if ("POST".equals(method)) {
                // Create user through controller and get the result
                User createdUser = userController.createUser(ex);

                // Convert to JSON and send response
                String json = toJson(List.of(createdUser));
                byte[] body = json.getBytes(StandardCharsets.UTF_8);
                ex.sendResponseHeaders(201, body.length);
                ex.getResponseBody().write(body);
            } else {
                byte[] body = "{\"error\":\"Method Not Allowed\"}".getBytes(StandardCharsets.UTF_8);
                ex.sendResponseHeaders(405, body.length);
                ex.getResponseBody().write(body);
            }
        } catch (Exception e) {
            String err = "{\"error\":\"" + e.getMessage().replace("\"","\\\"") + "\"}";
            byte[] body = err.getBytes(StandardCharsets.UTF_8);
            ex.sendResponseHeaders(500, body.length);
            ex.getResponseBody().write(body);
        } finally {
            ex.close();
        }
    }

    // Parse JSON string to User object (simple manual parsing)
    private User parseJsonToUser(String json) {
        // Remove whitespace and braces
        json = json.trim().replaceAll("^\\{|\\}$", "");

        String name = "";
        String email = "";

        // Split by comma and parse key-value pairs
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");

                if ("name".equals(key)) {
                    name = value;
                } else if ("email".equals(key)) {
                    email = value;
                }
            }
        }

        return new User(0, name, email); // ID will be auto-generated
    }

    // Very basic JSON – replace with Jackson if you allow one library
    private String toJson(List<User> users) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            sb.append("{\"id\":").append(u.id())
                    .append(",\"name\":\"").append(escape(u.name()))
                    .append("\",\"email\":\"").append(escape(u.email())).append("\"}");
            if (i < users.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    private String escape(String s){ return s == null ? "" : s.replace("\"","\\\""); }
}
