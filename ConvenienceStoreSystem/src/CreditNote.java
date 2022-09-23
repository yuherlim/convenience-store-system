

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//import java.time.LocalDate; // import the LocalDate class
import java.util.Arrays;

public class CreditNote {
    private String cnNo;
    private String cnDate;       //LocalDate date= LocalDate.now();
    private String staffName;
    private String supplierName;
    private ArrayList<StockDetails> stockDetails;
    private double amount;
    private String tag;
    private static int numOfCrn;
    public static final String FILE_NAME = "creditNote.txt";
    
    public CreditNote(){
        this.cnNo = "";
        this.cnDate = "";
        this.staffName = "";
        this.supplierName = "";
        this.amount = 0d;
        this.tag = "";
    }
    
    public CreditNote(CreditNote cn) {
        this.cnNo = cn.cnNo;
        this.cnDate = cn.cnDate;
        this.staffName = cn.staffName;
        this.supplierName = cn.supplierName;
        this.stockDetails = cn.stockDetails;
        this.amount = cn.amount;
        this.tag = cn.tag;
    }
    
    public CreditNote(String cnNo, String cnDate, String staffName, String supplierName, ArrayList<StockDetails> stockDetails, double amount, String tag) {
        this.cnNo = cnNo;
        this.cnDate = cnDate;
        this.staffName = staffName;
        this.supplierName = supplierName;
        this.stockDetails = stockDetails;
        this.amount = amount;
        this.tag = tag;
    }

    public String getCnNo() {
        return cnNo;
    }

    public void setCnNo(String cnNo) {
        this.cnNo = cnNo;
    }
    
    public String getCnDate() {
        return cnDate;
    }

    public void setCnDate(String cnDate) {
        this.cnDate = cnDate;
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

    public static int getNumOfCrn() {
        return numOfCrn;
    }

    public static void setNumOfCrn(int numOfCrn) {
        CreditNote.numOfCrn = numOfCrn;
    }

    public static void writeFile(String fileName, ArrayList<CreditNote> creditNote) {
        String line;
            //Create FileWriter set to write mode
            try (FileWriter writer = new FileWriter("src\\" + fileName, false)) {

                for (int i = 0; i < creditNote.size(); i++) {
                    //Create a new record to be written
                    line = String.format("%s|%s|%s|%s%%%.2f%%%s\n", creditNote.get(i).getCnNo(), creditNote.get(i).getCnDate(), creditNote.get(i).getStaffName(), creditNote.get(i).getSupplierName(), creditNote.get(i).getAmount(), creditNote.get(i).getTag());
                    //Writes the record to the file.
                    writer.write(line);
                }
                
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    
}
