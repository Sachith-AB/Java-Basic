package http;

import annotation.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import domain.user.User;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnotationBasedRouter implements HttpHandler {
    private final Object controller;
    private final String basePath;

    public AnnotationBasedRouter(Object controller) {
        this.controller = controller;
        
        // Get base path from @RequestMapping annotation
        RequestMapping requestMapping = controller.getClass().getAnnotation(RequestMapping.class);
        this.basePath = (requestMapping != null) ? requestMapping.value() : "";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            
            // Remove base path
            if (path.startsWith(basePath)) {
                path = path.substring(basePath.length());
            }
            
            // Set response headers
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            
            // Handle OPTIONS request (CORS preflight)
            if ("OPTIONS".equals(method)) {
                exchange.sendResponseHeaders(200, 0);
                return;
            }

            // Find matching method
            Method matchedMethod = findMatchingMethod(method, path);
            if (matchedMethod == null) {
                sendErrorResponse(exchange, 404, "Not Found");
                return;
            }

            // Invoke method and handle response
            Object result = invokeMethod(matchedMethod, exchange, path);
            sendSuccessResponse(exchange, result, "POST".equals(method) ? 201 : 200);

        } catch (IllegalArgumentException e) {
            sendErrorResponse(exchange, 400, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private Method findMatchingMethod(String httpMethod, String path) {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (matchesHttpMethod(method, httpMethod) && matchesPath(method, path)) {
                return method;
            }
        }
        return null;
    }

    private boolean matchesHttpMethod(Method method, String httpMethod) {
        return (method.isAnnotationPresent(GetMapping.class) && "GET".equals(httpMethod)) ||
               (method.isAnnotationPresent(PostMapping.class) && "POST".equals(httpMethod));
    }

    private boolean matchesPath(Method method, String path) {
        String methodPath = getMethodPath(method);
        
        // Exact match for simple paths
        if (methodPath.equals(path) || (methodPath.isEmpty() && path.equals("/"))) {
            return true;
        }
        
        // Pattern match for path variables
        String pattern = methodPath.replaceAll("\\{[^}]+\\}", "([^/]+)");
        return path.matches(pattern);
    }

    private String getMethodPath(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return method.getAnnotation(GetMapping.class).value();
        }
        if (method.isAnnotationPresent(PostMapping.class)) {
            return method.getAnnotation(PostMapping.class).value();
        }
        return "";
    }

    private Object invokeMethod(Method method, HttpExchange exchange, String path) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            
            if (param.isAnnotationPresent(PathVariable.class)) {
                args[i] = extractPathVariable(method, path, param);
            } else if (param.isAnnotationPresent(RequestBody.class)) {
                args[i] = parseRequestBody(exchange, param.getType());
            }
        }

        return method.invoke(controller, args);
    }

    private Object extractPathVariable(Method method, String path, Parameter param) {
        String methodPath = getMethodPath(method);
        String pattern = methodPath.replaceAll("\\{([^}]+)\\}", "([^/]+)");
        
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(path);
        
        if (matcher.matches()) {
            String value = matcher.group(1);
            Class<?> paramType = param.getType();
            
            if (paramType == long.class || paramType == Long.class) {
                return Long.parseLong(value);
            } else if (paramType == int.class || paramType == Integer.class) {
                return Integer.parseInt(value);
            } else {
                return value;
            }
        }
        
        return null;
    }

    private Object parseRequestBody(HttpExchange exchange, Class<?> targetType) throws Exception {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        
        if (targetType == User.class) {
            return parseJsonToUser(requestBody);
        } else if (targetType.getSimpleName().equals("CreateUserRequest")) {
            return parseJsonToCreateUserRequest(requestBody);
        }
        
        return null;
    }

    private User parseJsonToUser(String json) {
        json = json.trim().replaceAll("^\\{|\\}$", "");
        String name = "";
        String email = "";
        long id = 0;

        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");

                switch (key) {
                    case "id" -> id = value.isEmpty() ? 0 : Long.parseLong(value);
                    case "name" -> name = value;
                    case "email" -> email = value;
                }
            }
        }

        return new User(id, name, email);
    }

    private Object parseJsonToCreateUserRequest(String json) {
        json = json.trim().replaceAll("^\\{|\\}$", "");
        String name = "";
        String email = "";

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

        try {
            Class<?> requestClass = Class.forName("controller.UserController$CreateUserRequest");
            return requestClass.getConstructors()[0].newInstance(name, email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create CreateUserRequest", e);
        }
    }

    private void sendSuccessResponse(HttpExchange exchange, Object result, int statusCode) throws IOException {
        String json = toJson(result);
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, body.length);
        exchange.getResponseBody().write(body);
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        String json = "{\"error\":\"" + message.replace("\"", "\\\"") + "\"}";
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, body.length);
        exchange.getResponseBody().write(body);
    }

    private String toJson(Object obj) {
        if (obj instanceof List<?> list) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(toJson(list.get(i)));
            }
            sb.append("]");
            return sb.toString();
        } else if (obj instanceof User user) {
            return String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\"}", 
                user.id(), user.name(), user.email());
        } else if (obj instanceof Optional<?> opt) {
            if (opt.isPresent()) {
                return toJson(opt.get());
            } else {
                return "null";
            }
        } else {
            return "\"" + obj.toString() + "\"";
        }
    }
}
