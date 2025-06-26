// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;

// public class LoginScreen {
//     private JFrame frame;
//     private JTextField usernameField;
//     private JPasswordField passwordField;
    
//     public LoginScreen() {
//         frame = new JFrame("Login - Store Management System");
//         frame.setSize(400, 300);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new GridLayout(4, 1));

//         JLabel usernameLabel = new JLabel("Username:");
//         usernameField = new JTextField(20);
//         JLabel passwordLabel = new JLabel("Password:");
//         passwordField = new JPasswordField(20);

//         JButton loginButton = new JButton("Login");
//         loginButton.addActionListener(new LoginAction());

//         frame.add(usernameLabel);
//         frame.add(usernameField);
//         frame.add(passwordLabel);
//         frame.add(passwordField);
//         frame.add(loginButton);

//         frame.setVisible(true);
//     }

//     private class LoginAction implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             String username = usernameField.getText();
//             String password = new String(passwordField.getPassword());

//             if (authenticate(username, password)) {
//                 frame.dispose(); // Close login screen
//                 new StoreManagementGUI(); // Launch main GUI
//             } else {
//                 JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
//             }
//         }

//         private boolean authenticate(String username, String password) {
//             String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
//             try (Connection connection = DatabaseConnection.getConnection();
//                  PreparedStatement statement = connection.prepareStatement(query)) {
//                 statement.setString(1, username);
//                 statement.setString(2, password);

//                 ResultSet resultSet = statement.executeQuery();
//                 return resultSet.next(); // Return true if a record is found
//             } catch (Exception ex) {
//                 ex.printStackTrace();
//                 JOptionPane.showMessageDialog(frame, "Database Error", "Error", JOptionPane.ERROR_MESSAGE);
//                 return false;
//             }
//         }
//     }

//     public static void main(String[] args) {
//         new LoginScreen(); // Show login screen first
//     }
// }
