public class Product {
    private int productId;
    private String name;
    private double price;
    private int quantity;

    // Constructor
    public Product(int productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ID: " + productId + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity;
    }
}
