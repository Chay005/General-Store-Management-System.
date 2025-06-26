

public interface InventoryInterface {
    void addProduct(Product product) throws Exception;
    void updateStock(int productId, int quantity) throws Exception;
    Product getProduct(int productId) throws Exception;
}
