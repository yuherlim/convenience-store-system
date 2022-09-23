
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SupplierInvoice {

    private String invNo;
    private String invDate;
    private String staffName;
    private String supplierName;
    private ArrayList<StockDetails> stockDetails;
    private double amount;
    private String tag;
    private static int numOfInv;
    public static final String FILE_NAME = "invoice.txt";

    public SupplierInvoice() {
        this.invNo = "";
        this.invDate = "";
        this.staffName = "";
        this.supplierName = "";
        this.amount = 0d;
    }

    public SupplierInvoice(SupplierInvoice si) {
        this.invNo = si.invNo;
        this.invDate = si.invDate;
        this.staffName = si.staffName;
        this.supplierName = si.supplierName;
        this.stockDetails = si.stockDetails;
        this.amount = si.amount;
        this.tag = si.tag;
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

    public static ArrayList<SupplierInvoice> readFile(String fileName, ArrayList<SupplierInvoice> supplierInvoice, ArrayList<StockDetails> stockDetails) {

        try ( FileReader reader = new FileReader("src\\" + fileName)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");

                String invNo = string1[0];
                String invDate = string1[1];
                String staffName = string1[2];
                String supplierName = string1[3];
                String tag = buffer[2];

                //Convert string to double for total amount
                double amount = Double.parseDouble(buffer[1]);

                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetails.readFile(StockDetails.FILE_NAME).clone();
                stockDetails.clear();

                //Add elements of stock details that is associated with this invoice number.
                for (StockDetails sd : allSD) {
                    if (sd.getInvNo().equals(invNo)) {
                        stockDetails.add(sd);
                    }
                }

                //store cloned versions of stockDetails as it will be used again in subsequent loops
                supplierInvoice.add(new SupplierInvoice(invNo, invDate, staffName, supplierName, (ArrayList<StockDetails>) stockDetails.clone(), amount, tag));

            }
        } catch (IOException e) {
        }

        return supplierInvoice;
    }

    public static void writeFile(String fileName, ArrayList<SupplierInvoice> invoice) {
        String line;
        //Create FileWriter set to write mode
        try ( FileWriter writer = new FileWriter("src\\" + fileName, false)) {

            for (int i = 0; i < invoice.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s%%%.2f%%%s\n", invoice.get(i).getInvNo(), invoice.get(i).getInvDate(), invoice.get(i).getStaffName(), invoice.get(i).getSupplierName(), invoice.get(i).getAmount(), invoice.get(i).getTag());
                //Writes the record to the file.
                writer.write(line);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    //METHOD OVERLOADING
    //Display invoice with specify invoice number
    public static int printInvoice(ArrayList<SupplierInvoice> supplierInvoice, int index, ArrayList<StockDetails> stockDetails) {

        int invalid = 0;
        double totalAmount = 0d;
        double subtotal;

        //Used to store the stock details with the current invoice no.
        ArrayList<StockDetails> currentInvoiceStockDetails = new ArrayList<>();

        //If it is valid, then print, else print invaalid message
        if (!supplierInvoice.get(index).getTag().equals("Invalid")) {
            System.out.println();
            System.out.printf("Staff Incharge: %-36s Invoice No.: %7s\n", supplierInvoice.get(index).getStaffName(), supplierInvoice.get(index).getInvNo());
            System.out.printf("Supplier: %-38s Invoice Date: %10s\n", supplierInvoice.get(index).getSupplierName(), supplierInvoice.get(index).getInvDate());
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Item                Cost Price               Qty               Total(RM)");
            System.out.println("-------------------------------------------------------------------------");

            //Loop for compare the same invNo.
            for (int i = 0; i < stockDetails.size(); i++) {
                if (supplierInvoice.get(index).getInvNo().equals(stockDetails.get(i).getInvNo())) {
                    //store the stock details with the current invoice number.
                    currentInvoiceStockDetails.add(new StockDetails(stockDetails.get(i)));
                }
            }

            //Loop for display stock details
            for (int i = 0; i < currentInvoiceStockDetails.size(); i++) {
                subtotal = currentInvoiceStockDetails.get(i).getCostPrice() * currentInvoiceStockDetails.get(i).getQty();
                System.out.printf("%-5s               %7.2f                %4d                %6.2f\n",
                        currentInvoiceStockDetails.get(i).getProductCode(), currentInvoiceStockDetails.get(i).getCostPrice(), currentInvoiceStockDetails.get(i).getQty(), subtotal);
                totalAmount += subtotal;
            }
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("Total Amount(RM)%53.2f \n\n", totalAmount);
        } else {
            System.out.println("Invoice has been cancelled.");
            return ++invalid;
        }

        return invalid;
    }

    //Display invoice when adding new invoice
    public static double printInvoice(SupplierInvoice si, ArrayList<StockDetails> newStockDetails) {

        double totalAmount = 0d;
        double subtotal;

        System.out.println();
        System.out.printf("Staff Incharge: %-36s Invoice No.: %7s\n", si.getStaffName(), si.getInvNo());
        System.out.printf("Supplier: %-38s Invoice Date: %10s\n", si.getSupplierName(), si.getInvDate());
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Item                Cost Price               Qty               Total(RM)");
        System.out.println("-------------------------------------------------------------------------");

        //Loop for display the stock details
        for (int i = 0; i < newStockDetails.size(); i++) {
            subtotal = newStockDetails.get(i).getCostPrice() * newStockDetails.get(i).getQty();
            System.out.printf("%-5s               %7.2f                %4d                %6.2f\n",
                    newStockDetails.get(i).getProductCode(), newStockDetails.get(i).getCostPrice(), newStockDetails.get(i).getQty(), subtotal);
            totalAmount += subtotal;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Total Amount(RM)%53.2f \n\n", totalAmount);

        return totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice Number: " + invNo
                + " Invoice Date: " + invDate
                + " Staff Incharge: " + staffName
                + " Supplier: " + supplierName
                + " Aamount:" + amount + '\n';
    }
}
