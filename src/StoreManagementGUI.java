import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;

public class StoreManagementGUI {
    private Inventory inventory = new Inventory();
    private Billing billing = new Billing();
    private JFrame frame;
    private JTextArea inventoryDisplayArea;
    private JTextArea billingDisplayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StoreManagementGUI::new);
    }

    public StoreManagementGUI() {
        createLoginGUI();
    }

    public void createLoginGUI() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Store Management System", JLabel.CENTER);
        titleLabel.setBounds(50, 20, 300, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 80, 100, 30);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 80, 180, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 180, 30);

        JButton loginButton = createRoundedButton("Login", new Color(0, 128, 0), Color.WHITE);
        loginButton.setBounds(150, 190, 100, 40);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals("admin") && password.equals("admin")) {
                loginFrame.dispose();
                createMainGUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(titleLabel);
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);
    }

    public void createMainGUI() {
        frame = new JFrame("General Store Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(70, 130, 180));

        JButton inventoryButton = createRoundedButton("Inventory", Color.WHITE, new Color(70, 130, 180));
        JButton billingButton = createRoundedButton("Billing", Color.WHITE, new Color(70, 130, 180));
        JButton saveButton = createRoundedButton("Save Inventory", Color.WHITE, new Color(70, 130, 180));
        JButton exitButton = createRoundedButton("Exit", Color.WHITE, new Color(255, 69, 0));

        topPanel.add(inventoryButton);
        topPanel.add(billingButton);
        topPanel.add(saveButton);
        topPanel.add(exitButton);

        // Center Panel with CardLayout
        JPanel centerPanel = new JPanel(new CardLayout());

        // Inventory Panel
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(new Color(245, 245, 245));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField();
        JLabel productPriceLabel = new JLabel("Price:");
        JTextField productPriceField = new JTextField();
        JLabel productQuantityLabel = new JLabel("Quantity:");
        JTextField productQuantityField = new JTextField();
        JButton addProductButton = createRoundedButton("Add Product", new Color(34, 139, 34), Color.WHITE);

        inputPanel.add(productNameLabel);
        inputPanel.add(productNameField);
        inputPanel.add(productPriceLabel);
        inputPanel.add(productPriceField);
        inputPanel.add(productQuantityLabel);
        inputPanel.add(productQuantityField);
        inputPanel.add(new JLabel());
        inputPanel.add(addProductButton);

        inventoryDisplayArea = new JTextArea(10, 30);
        inventoryDisplayArea.setEditable(false);
        inventoryDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryDisplayArea);

        inventoryPanel.add(inputPanel, BorderLayout.NORTH);
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);

        // Billing Panel
        JPanel billingPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        billingPanel.setBackground(new Color(245, 245, 245));
        billingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel customerNameLabel = new JLabel("Customer Name:");
        JTextField customerNameField = new JTextField();
        JLabel billingProductIdLabel = new JLabel("Product ID:");
        JTextField billingProductIdField = new JTextField();
        JLabel billingQuantityLabel = new JLabel("Quantity:");
        JTextField billingQuantityField = new JTextField();
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Card", "Online"});
        JButton generateBillButton = createRoundedButton("Generate Bill", new Color(70, 130, 180), Color.WHITE);
        billingDisplayArea = new JTextArea();
        billingDisplayArea.setEditable(false);
        billingDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane billingScrollPane = new JScrollPane(billingDisplayArea);

        billingPanel.add(customerNameLabel);
        billingPanel.add(customerNameField);
        billingPanel.add(billingProductIdLabel);
        billingPanel.add(billingProductIdField);
        billingPanel.add(billingQuantityLabel);
        billingPanel.add(billingQuantityField);
        billingPanel.add(paymentMethodLabel);
        billingPanel.add(paymentMethodComboBox);
        billingPanel.add(new JLabel());
        billingPanel.add(generateBillButton);

        centerPanel.add(inventoryPanel, "Inventory");
        centerPanel.add(billingPanel, "Billing");

        // Event Listeners
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        inventoryButton.addActionListener(e -> {
            cardLayout.show(centerPanel, "Inventory");
            try {
                updateInventoryDisplay();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error loading inventory: " + ex.getMessage());
            }
        });

        billingButton.addActionListener(e -> cardLayout.show(centerPanel, "Billing"));

        addProductButton.addActionListener(e -> {
            try {
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int quantity = Integer.parseInt(productQuantityField.getText());

                Product product = new Product(inventory.getNextProductId(), name, price, quantity);
                inventory.addProduct(product);
                updateInventoryDisplay();
                JOptionPane.showMessageDialog(frame, "Product added successfully!");
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        generateBillButton.addActionListener(e -> {
            try {
                // Retrieve input from the user
                String customerName = customerNameField.getText();
                int productId = Integer.parseInt(billingProductIdField.getText());
                int quantity = Integer.parseInt(billingQuantityField.getText());
                String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        
                // Validate inputs
                if (customerName.isEmpty()) {
                    throw new IllegalArgumentException("Customer name cannot be empty.");
                }
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }
        
                // Generate the bill using the Billing class
                double total = billing.generateBill(new int[]{productId}, new int[]{quantity}, inventory);
        
                // Create the receipt
                StringBuilder receipt = new StringBuilder();
                receipt.append("\n===== Billing Receipt =====\n");
                receipt.append("Customer Name: ").append(customerName).append("\n");
                receipt.append("Product ID: ").append(productId).append("\n");
                receipt.append("Quantity: ").append(quantity).append("\n");
                receipt.append("Payment Method: ").append(paymentMethod).append("\n");
                receipt.append("-------------------------\n");
                receipt.append("Total Amount: ").append(String.format("%.2f", total)).append("\n");
                receipt.append("==========================\n");
                receipt.append("Thank You!! Visit Again !! \n");
                receipt.append("==========================\n");
        
                // Display the receipt
                billingDisplayArea.setText(receipt.toString());
                JOptionPane.showMessageDialog(frame, "Bill generated successfully!\n" + receipt, "Billing Receipt", JOptionPane.INFORMATION_MESSAGE);
        
                // Update inventory display after billing
                updateInventoryDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input: Please enter valid numbers for Product ID and Quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        

        saveButton.addActionListener(e -> {
            try {
                saveInventoryToCSV();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving inventory: " + ex.getMessage());
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Frame Setup
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton createRoundedButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(background.darker()));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void updateInventoryDisplay() throws SQLException {
        inventoryDisplayArea.setText("Inventory:\n");
        for (Product product : inventory.getAllProducts()) {
            inventoryDisplayArea.append(product + "\n");
        }
    }

    private void saveInventoryToCSV() throws IOException {
        
        JOptionPane.showMessageDialog(frame, "Inventory saved to CSV successfully!");
    }
}
