import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesFileHandler {
    
    // File path to store sales data
    private static final String SALES_FILE_PATH = "sales.csv";
    
    // Save the sales data to a CSV file
    public static void saveSaleToFile(String customerName, int productId, int quantity, double total) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SALES_FILE_PATH, true));
        
        // Add sale details to the file with a timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        writer.write(timestamp + "," + customerName + "," + productId + "," + quantity + "," + total);
        writer.newLine();
        
        writer.close();
    }
}
