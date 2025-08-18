package infrastructure.db;

import domain.user.User;
import ports.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class JdbcUserRepository extends GenericJdbcRepository<User, Long> implements UserRepository {
    
    public JdbcUserRepository(DbConfig dbConfig) {
        super(dbConfig, "users"); // Default table name
    }
    
    public JdbcUserRepository(DbConfig dbConfig, String tableName) {
        super(dbConfig, tableName); // Custom table name
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<User> findBydId(long id) {
        return super.findById(id);
    }

    @Override
    public User save(User user) {
        return super.save(user);
    }

    // Find users by email
    public Optional<User> findByEmail(String email) {
        return findOneByCustomQuery("email = ?", email);
    }

    // Find users by name pattern
    public List<User> findByNameContaining(String namePattern) {
        return findByCustomQuery("name LIKE ?", "%" + namePattern + "%");
    }

    // Implementation of abstract methods from GenericJdbcRepository
    @Override
    protected User mapRowToEntity(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("email")
        );
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO " + tableName + " (name, email) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName + " SET name = ?, email = ? WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.name());
        statement.setString(2, user.email());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.name());
        statement.setString(2, user.email());
        statement.setLong(3, user.id());
    }

    @Override
    protected boolean isNewEntity(User user) {
        return user.id() == 0;
    }

    @Override
    protected void setIdParameter(PreparedStatement statement, int parameterIndex, Long id) throws SQLException {
        statement.setLong(parameterIndex, id);
    }

    @Override
    protected Long getGeneratedId(ResultSet resultSet) throws SQLException {
        return resultSet.getLong(1);
    }

    @Override
    protected User updateEntityWithId(User user, Long id) {
        return new User(id, user.name(), user.email());
    }
}
