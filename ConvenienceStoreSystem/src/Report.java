
import java.util.ArrayList;

/**
 *
 * @author JiaQing
 */
public class Report {

    private String reportTitle;
    private String dateGenerated;
    private String timeGenerated;
    private ArrayList<Transaction> transactions;
    private ArrayList<Product> products;

    public Report() {

    }

    //Constructor
    public Report(String reportTitle, String dateGenerated, ArrayList<Transaction> transactions, ArrayList<Product> products) {
        this.reportTitle = reportTitle;
        this.dateGenerated = dateGenerated;
        this.transactions = transactions;
        this.products = products;
    }

    public Report(String reportTitle, String dateGenerated, String timeGenerated) {
        this.reportTitle = reportTitle;
        this.dateGenerated = dateGenerated;
        this.timeGenerated = timeGenerated;
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

    public String getTimeGenerated() {
        return timeGenerated;
    }

    public void setTimeGenerated(String timeGenerated) {
        this.timeGenerated = timeGenerated;
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
    
    public static void dailyReport() {
        General.clearScreen();
        
        Report dailyReport = new Report("Daily Report", General.getCurrentDateTime("date"), General.getCurrentDateTime("time"));
        int transactionCount = 0;

        //Prompt and get date
        String checkDate = General.dateInput("Date of report to generate (DD/MM/YYYY): ", "  Invalid date format.");

        //get all transaction into arraylist
        ArrayList<Transaction> transactions = new ArrayList<>();
        TransactionDriver.readFile("transaction.txt", transactions);

        //print report header
        System.out.println("==============");
        System.out.println(' ' + dailyReport.getReportTitle());
        System.out.println("==============");

        //System.out.println("Staff Generated: " + staffLogin.getName());
        System.out.println("Date Generated: " + dailyReport.getDateGenerated() + "    Time Generated: " + dailyReport.getTimeGenerated());
        System.out.println('\n' + "Report Date: " + checkDate);
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("| Transaction ID | Member ID |  Payment ID  | Amount(RM) |    Staff Name    |");
        System.out.println("-----------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getDateTime().substring(0, 10).equals(checkDate)) {
                System.out.printf("|  %12s  | %9s | %12s | %10.2f | %-16s | \n", transaction.getId(), transaction.getMember().getId(), transaction.getPayment().getPaymentId(), transaction.getPayment().getAmount(), transaction.getStaff().getName());
                transactionCount++;
            }
        }
        if (transactionCount == 0) {
            System.out.println("| There are no transaction in " + checkDate + String.format("%36s", " ") + '|');
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("");
        System.out.printf("< %d record(s) >\n", transactionCount);
        System.out.println("");
        General.systemPause();
    }

    public static void monthlyReport() {

    }

    //displays the reorder report for products that needs to be restocked.
    public static void productReorderReport() {
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
        for (Product p : reorderReport.products) {
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
