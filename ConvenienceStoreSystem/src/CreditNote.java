

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
    private static String fileName = "creditNote.txt";
    
    public CreditNote(){
        this.cnNo = "";
        this.cnDate = "";
        this.amount = 0d;
    }
    
    public CreditNote(String cnNo, String cnDate, String staffName, String supplierName, StockDetails[] stockDetails, double amount) {
        this.cnNo = cnNo;
        this.cnDate = cnDate;
        this.staffName = staffName;
        this.supplierName = supplierName;
        this.stockDetails = new ArrayList<>(Arrays.asList(stockDetails));
        this.amount = amount;
    }

    public String getCnNo() {
        return cnNo;
    }

    //still figure auto-increment for cnNo
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

    public static String getFileName() {
        return fileName;
    }

    public static void writeFile(String fileName, ArrayList<CreditNote> creditNote) {
        String line;
        try {
                //Create FileWriter set to write mode
            try (FileWriter writer = new FileWriter("src\\" + fileName, false)) {

                for (int i = 0; i < creditNote.size(); i++) {
                    //Create a new record to be written
                    line = String.format("%s|%s|%s|%s%%.2lf\n", creditNote.get(i).getCnNo(), creditNote.get(i).getCnDate(), creditNote.get(i).getStaffName(), creditNote.get(i).getSupplierName(), creditNote.get(i).getAmount());
                    //Writes the record to the file.
                    writer.write(line);
                }
                // Closes the writer
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
