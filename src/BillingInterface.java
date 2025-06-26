
public interface BillingInterface {
    double generateBill(int[] productIds, int[] quantities, Inventory inventory) throws Exception;
}
