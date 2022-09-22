
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//import java.util.Arrays;

public class SupplierInvoice {

    private String invNo;
    private String invDate;
    private String staffName;
    private String supplierName;
    private ArrayList<StockDetails> stockDetails;
    private double amount;
    private String tag;
    private static int numOfInv;
    public static String fileName = "invoice.txt";

    public SupplierInvoice() {
        this.invNo = "";
        this.staffName = "";
        this.supplierName = "";
        this.amount = 0d;
    }

    public SupplierInvoice(String invNo, String invDate, String staffName, String supplierName, ArrayList<StockDetails> stockDetails, double amount, String tag) {
        this.invNo = invNo;
        this.invDate = invDate;
        this.staffName = staffName;
        this.supplierName = supplierName;
        this.stockDetails = stockDetails;
        this.amount = amount;
        this.tag = tag;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ArrayList<StockDetails> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(ArrayList<StockDetails> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static int getNumOfInv() {
        return numOfInv;
    }

    public static void setNumOfInv(int numOfInv) {
        SupplierInvoice.numOfInv = numOfInv;
    }

    public static void writeFile(String fileName, ArrayList<SupplierInvoice> invoice) {
        String line;
            //Create FileWriter set to write mode
            try ( FileWriter writer = new FileWriter("src\\" + fileName, false)) {

                for (int i = 0; i < invoice.size(); i++) {
                    //Create a new record to be written
                    line = String.format("%s|%s|%s|%s%%.2lf\n", invoice.get(i).getInvNo(), invoice.get(i).getInvDate(), invoice.get(i).getStaffName(), invoice.get(i).getSupplierName(), invoice.get(i).getAmount());
                    //Writes the record to the file.
                    writer.write(line);
                }

        } catch (IOException e) {
            e.getStackTrace();
    }
}

    @Override
    public String toString() {
        return '\n' + "Invoice No.: " + invNo + '\n'
                + "Invoice Date: " + invDate + '\n'
                + "Person Incharge: " + staffName + '\n'
                + "Supplier: " + supplierName + '\n'
                + "Total Amount: " + amount + '\n';
    }
    
    
}
