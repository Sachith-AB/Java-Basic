package infrastructure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {
    private final String url, user, password;

    public DbConfig(String url, String user, String password) {
        this.url = url; this.user = user; this.password = password;
    }

    public Connection getConnection() throws SQLException {
        // For production prefer a pool (HikariCP). This is the “pure” approach.
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        return DriverManager.getConnection(url, props);
    }
}
