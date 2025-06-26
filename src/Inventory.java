import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    // Method to add a product to the database
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.executeUpdate();
        }
    }

    // Method to update the stock (quantity) of a product in the database
    public void updateStock(int productId, int quantity) throws SQLException {
        String query = "UPDATE products SET quantity = quantity + ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
    }

    // Method to fetch a single product by its ID
    public Product getProduct(int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity")
                );
            } else {
                throw new IllegalArgumentException("Product not found!");
            }
        }
    }

    // Method to fetch all products from the database
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity")
                ));
            }
        }
        return products;
    }

    // New Method: Get the next product ID
    public int getNextProductId() throws SQLException {
        String query = "SELECT MAX(id) AS max_id FROM products";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("max_id") + 1;  // Increment the highest ID by 1
            } else {
                return 1;  // If no products exist, return 1 as the first product ID
            }
        }
    }
}
