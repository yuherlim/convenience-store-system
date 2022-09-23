
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
    
    //METHOD OVERLOADING
    //Display invoice with specify invoice number
    public static int printCreditNote(ArrayList<CreditNote> creditNote, int index, ArrayList<StockDetails> stockDetails) {

        int invalid = 0;
        double totalAmount = 0d;
        double subtotal;

        //Used to store the stock details with the current invoice no.
        ArrayList<StockDetails> currentCreditNoteStockDetails = new ArrayList<>();

        if (!creditNote.get(index).getTag().equals("Invalid")) {
            System.out.println();
            System.out.printf("Staff Incharge: %-36s Invoice No.: %7s\n", creditNote.get(index).getStaffName(), creditNote.get(index).getCnNo());
            System.out.printf("Supplier: %-38s Invoice Date: %10s\n", creditNote.get(index).getSupplierName(), creditNote.get(index).getCnDate());
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Item                Cost Price               Qty               Total(RM)");
            System.out.println("-------------------------------------------------------------------------");

            //Loop for compare the same invNo.
            for (int i = 0; i < stockDetails.size(); i++) {
                if (creditNote.get(index).getCnNo().equals(stockDetails.get(i).getCnNo())) {
                    //store the stock details with the current invoice number.
                    currentCreditNoteStockDetails.add(new StockDetails(stockDetails.get(i)));
                }
            }

            //Loop for display stock details
            for (int i = 0; i < currentCreditNoteStockDetails.size(); i++) {
                subtotal = currentCreditNoteStockDetails.get(i).getCostPrice() * currentCreditNoteStockDetails.get(i).getQty();
                System.out.printf("%-5s             %7.2f                   %3d                %6.2f\n",
                        currentCreditNoteStockDetails.get(i).getProductCode(), currentCreditNoteStockDetails.get(i).getCostPrice(), currentCreditNoteStockDetails.get(i).getQty(), subtotal);
                totalAmount += subtotal;
            }
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("Total Amount(RM)%53.2f \n\n", totalAmount);
        } else {
            System.out.println("Credit Note has been cancelled.");
            return ++invalid;
        }
        return invalid;
    }
    
    public static double printCreditNote(CreditNote cn, ArrayList<StockDetails> newStockDetails) {
        
        double totalAmount = 0d;
        double subtotal;

        System.out.println();
        System.out.printf("Staff Incharge: %-36s Invoice No.: %7s\n", cn.getStaffName(), cn.getCnNo());
        System.out.printf("Supplier: %-38s Invoice Date: %10s\n", cn.getSupplierName(), cn.getCnDate());
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Item                Cost Price               Qty               Total(RM)");
        System.out.println("-------------------------------------------------------------------------");

        //Loop for display the stock details
        for (int i = 0; i < newStockDetails.size(); i++) {
            subtotal = newStockDetails.get(i).getCostPrice() * newStockDetails.get(i).getQty();
            System.out.printf("%-5s               %7.2f               %3d               %6.2f\n",
                    newStockDetails.get(i).getProductCode(), newStockDetails.get(i).getCostPrice(), newStockDetails.get(i).getQty(), subtotal);
            totalAmount += subtotal;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Total Amount(RM)%53.2f \n\n", totalAmount);

        return totalAmount;
    }
    
    public static ArrayList<CreditNote> readFile(String fileName, ArrayList<CreditNote> creditNote, ArrayList<StockDetails> stockDetails) {
        try ( FileReader reader = new FileReader("src\\" + fileName)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");

                String cnNo = string1[0];
                String cnDate = string1[1];
                String staffName = string1[2];
                String supplierName = string1[3];
                String tag = buffer[2];

                //Convert string to double for total amount
                double amount = Double.parseDouble(buffer[1]);

                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetails.readFile(StockDetails.FILE_NAME).clone();
                stockDetails.clear();

                //Add elements of stock details that is associated with this credit number.
                for (StockDetails sd : allSD) {
                    if (sd.getCnNo().equals(cnNo)) {
                        stockDetails.add(sd);
                    }
                }

                //store cloned versions of stockDetails as it will be used again in subsequent loops
                creditNote.add(new CreditNote(cnNo, cnDate, staffName, supplierName, (ArrayList<StockDetails>) stockDetails.clone(), amount, tag));
            }
        } catch (IOException e) {
        }

        return creditNote;
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
