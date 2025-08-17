package infrastructure.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Example of how to create a repository for any domain using GenericJdbcRepository
 * This example shows a Product domain
 */
public class ProductRepository extends GenericJdbcRepository<Product, Long> {
    
    public ProductRepository(DbConfig dbConfig) {
        super(dbConfig, "products"); // Default table name
    }
    
    public ProductRepository(DbConfig dbConfig, String tableName) {
        super(dbConfig, tableName); // Custom table name
    }

    // Domain-specific methods
    public List<Product> findByCategory(String category) {
        return findByCustomQuery("category = ?", category);
    }

    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return findByCustomQuery("price BETWEEN ? AND ?", minPrice, maxPrice);
    }

    public Optional<Product> findByName(String name) {
        return findOneByCustomQuery("name = ?", name);
    }

    // Implementation of abstract methods
    @Override
    protected Product mapRowToEntity(ResultSet resultSet) throws SQLException {
        return new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getDouble("price"),
            resultSet.getString("category")
        );
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO " + tableName + " (name, description, price, category) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName + " SET name = ?, description = ?, price = ?, category = ? WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.name());
        statement.setString(2, product.description());
        statement.setDouble(3, product.price());
        statement.setString(4, product.category());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.name());
        statement.setString(2, product.description());
        statement.setDouble(3, product.price());
        statement.setString(4, product.category());
        statement.setLong(5, product.id());
    }

    @Override
    protected boolean isNewEntity(Product product) {
        return product.id() == 0;
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
    protected Product updateEntityWithId(Product product, Long id) {
        return new Product(id, product.name(), product.description(), product.price(), product.category());
    }
}

// Example Product record
record Product(long id, String name, String description, double price, String category) {
}
