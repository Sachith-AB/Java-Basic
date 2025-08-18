package controller;

import application.user.UserService;
import com.sun.net.httpserver.HttpExchange;
import domain.user.User;

import java.nio.charset.StandardCharsets;

public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public User createUser(HttpExchange ex) throws Exception{
        String requestBody = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        User userToCreate = parseJsonToUser(requestBody);
        User createdUser = service.createUser(userToCreate);
        return createdUser;
    }

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
}
