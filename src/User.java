import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(hashPassword(password)); // Compare hashed passwords
            }
        }
        return false;
    }

    public void changePassword(String username, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hashPassword(newPassword)); // Hash the new password
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }

    private String hashPassword(String password) {
        // For simplicity, return plain password. Use proper hashing (e.g., BCrypt) in production.
        return password;
    }
}
