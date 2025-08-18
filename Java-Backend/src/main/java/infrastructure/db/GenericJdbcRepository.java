package infrastructure.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Generic JDBC repository that can work with any domain entity
 * @param <T> The domain entity type
 * @param <ID> The ID type (typically Long)
 */
public abstract class GenericJdbcRepository<T, ID> {
    protected final DbConfig dbConfig;
    protected final String tableName;

    public GenericJdbcRepository(DbConfig dbConfig, String tableName) {
        this.dbConfig = dbConfig;
        this.tableName = tableName;
    }

    /**
     * Find all entities from the table
     */
    public List<T> findAll() {
        String query = "SELECT * FROM " + tableName;
        List<T> entities = new ArrayList<>();
        
        try (Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                entities.add(mapRowToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all entities from " + tableName, e);
        }
        
        return entities;
    }

    /**
     * Find entity by ID
     */
    public Optional<T> findById(ID id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        
        try (Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            setIdParameter(statement, 1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return Optional.of(mapRowToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding entity by id: " + id + " in " + tableName, e);
        }
        
        return Optional.empty();
    }

    /**
     * Save entity (insert if new, update if exists)
     */
    public T save(T entity) {
        if (isNewEntity(entity)) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    /**
     * Delete entity by ID
     */
    public boolean deleteById(ID id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            setIdParameter(statement, 1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting entity by id: " + id + " from " + tableName, e);
        }
    }

    /**
     * Execute custom query and return list of entities
     */
    public List<T> findByCustomQuery(String whereClause, Object... parameters) {
        String query = "SELECT * FROM " + tableName + " WHERE " + whereClause;
        List<T> entities = new ArrayList<>();
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(mapRowToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing custom query on " + tableName, e);
        }
        
        return entities;
    }

    /**
     * Execute custom query and return single entity
     */
    public Optional<T> findOneByCustomQuery(String whereClause, Object... parameters) {
        List<T> results = findByCustomQuery(whereClause, parameters);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // Abstract methods that subclasses must implement
    protected abstract T mapRowToEntity(ResultSet resultSet) throws SQLException;
    protected abstract String getInsertQuery();
    protected abstract String getUpdateQuery();
    protected abstract void setInsertParameters(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement statement, T entity) throws SQLException;
    protected abstract boolean isNewEntity(T entity);
    protected abstract void setIdParameter(PreparedStatement statement, int parameterIndex, ID id) throws SQLException;
    protected abstract ID getGeneratedId(ResultSet resultSet) throws SQLException;

    private T insert(T entity) {
        String query = getInsertQuery();
        System.out.println("=== Insert Operation Debug ===");
        System.out.println("Table: " + tableName);
        System.out.println("Query: " + query);
        System.out.println("Entity before insert: " + entity);

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            setInsertParameters(statement, entity);
            
            int affectedRows = statement.executeUpdate();
            System.out.println("Affected rows: " + affectedRows);

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to insert entity into " + tableName);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ID generatedId = getGeneratedId(generatedKeys);
                    System.out.println("Generated ID: " + generatedId);

                    T resultEntity = updateEntityWithId(entity, generatedId);
                    System.out.println("Entity after insert: " + resultEntity);
                    System.out.println("=== Insert Success ===");

                    return resultEntity;
                } else {
                    throw new RuntimeException("Failed to get generated ID for " + tableName);
                }
            }
        } catch (SQLException e) {
            System.err.println("=== SQL Exception during insert ===");
            System.err.println("Table: " + tableName);
            System.err.println("Query: " + query);
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            System.err.println("===================================");
            e.printStackTrace();
            throw new RuntimeException("Error inserting entity into " + tableName + ": " + e.getMessage(), e);
        }
    }

    private T update(T entity) {
        String query = getUpdateQuery();
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            setUpdateParameters(statement, entity);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Entity not found for update in " + tableName);
            }
            
            return entity;
        } catch (SQLException e) {
            System.err.println("=== SQL Exception during update ===");
            System.err.println("Table: " + tableName);
            System.err.println("Query: " + query);
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            System.err.println("===================================");
            e.printStackTrace();
            throw new RuntimeException("Error updating entity in " + tableName + ": " + e.getMessage(), e);
        }
    }

    // Abstract method to update entity with generated ID
    protected abstract T updateEntityWithId(T entity, ID id);
}
