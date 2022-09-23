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
    
    public Report() {
        
    }
    
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
    
    //displays the reorder report for products that needs to be restocked.
    public static void productReorderReport(){
        General.clearScreen();
        
        //Create a new report obj
        Report reorderReport = new Report("Reorder Report", General.getCurrentDateTime("date"), null, Product.readFile(Product.FILE_NAME));
       
        int printCount = 0;
        
        //Printing report header
        System.out.println("==============");
        System.out.println(reorderReport.getReportTitle());
        System.out.println("==============");
        System.out.println("Date generated: " + reorderReport.getDateGenerated());
        System.out.println("The following products need to be reordered:");
        System.out.println("--------------------------------------------------------------");
        System.out.println("| Code  | Name                 | Quantity | Reorder Quantity |");
        System.out.println("--------------------------------------------------------------");
        for (Product p: reorderReport.products) {
            if (p.getStockQty() < p.getMinReorderQty()) {
                if (p.getStatus().equals("ACTIVE")) {
                    System.out.printf("| %5s | %20s | %8d | %16d |\n", p.getCode(), p.getName(), p.getStockQty(), p.getMinReorderQty());
                    printCount++;
                }
            }
        }
        
        if (printCount == 0) {
            System.out.println("There are no products that needs to be reordered.");
        }
        

        System.out.println("");
        System.out.printf("< %d record(s) >\n", printCount);
        System.out.println("");
        General.systemPause();
    }
}
