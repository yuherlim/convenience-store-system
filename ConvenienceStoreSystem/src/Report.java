import java.util.ArrayList;

/**
 *
 * @author JiaQing
 */
public class Report {
    private String reportTitle;
    private String dateGenerated;
    private ArrayList<Transaction> transactions;
    private ArrayList<Product> products;
    
    //Constructor
    public Report(String reportTitle, String dateGenerated, ArrayList<Transaction> transactions, ArrayList<Product> products){
        this.reportTitle = reportTitle;
        this.dateGenerated = dateGenerated;
        this.transactions = transactions;
        this.products = products;
    }
    
    //getter and setter
    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    public static void dailyReport(){
        
    }
    
    public static void monthlyReport(){
        
    }
    
    public static void productReorderReport(){
        Report reorderReport = new Report("Reorder Report", General.getCurrentDateTime());
        ArrayList<Product> products = Product.readFile(Product.fileName);
        
        
    }
}
