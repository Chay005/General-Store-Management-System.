import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Billing {
    public double generateBill(int[] productIds, int[] quantities, Inventory inventory) throws SQLException {
        double total = 0;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            for (int i = 0; i < productIds.length; i++) {
                Product product = inventory.getProduct(productIds[i]);
                if (quantities[i] > product.getQuantity()) {
                    throw new IllegalArgumentException("Insufficient stock for " + product.getName());
                }

                double itemTotal = product.getPrice() * quantities[i];
                total += itemTotal;

                inventory.updateStock(productIds[i], -quantities[i]);

                // Insert sale record
                String saleQuery = "INSERT INTO sales (product_id, quantity_sold, total_price) VALUES (?, ?, ?)";
                try (PreparedStatement saleStatement = connection.prepareStatement(saleQuery)) {
                    saleStatement.setInt(1, productIds[i]);
                    saleStatement.setInt(2, quantities[i]);
                    saleStatement.setDouble(3, itemTotal);
                    saleStatement.executeUpdate();
                }
            }

            connection.commit(); // Commit transaction
        }

        return total;
    }
}
