import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate; // import the LocalDate class
import java.util.Arrays;

public class Invoice {

    private String invNo;
    private LocalDate invDate; // Create a date object; //= LocalDate.now();
    private Staff staff;
    private Supplier supplier;
    private double amount;
    private ArrayList<StockDetails> stockDetails;
    private static String fileName = "invoice.txt";
    
    public Invoice(String invNo, LocalDate invDate, Staff staff, Supplier supplier, StockDetails[] stockDetails, double amount) {
        this.invNo = invNo;
        this.invDate = invDate;
        this.staff = staff;
        this.supplier = supplier;
        this.stockDetails = new ArrayList<>(Arrays.asList(stockDetails));
        this.amount = amount;
    }

    public String getInvNo() {
        return invNo;
    }

    //still figure auto-increment for invNo
//    public void setInvNo(String invNo) {
//        this.invNo = invNo;
//    }
    

    public LocalDate getInvDate() {
        return invDate;
    }

    public void setInvDate(LocalDate invDate) {
        this.invDate = invDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public static String getFileName() {
        return fileName;
    }
    
    public static void readFile(String fileName, ArrayList<Invoice> invoice) {
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);

                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");
                String[] string2 = buffer[1].split("\\|");
                String[] string3 = buffer[2].split("\\|");
                String[] string4 = buffer[3].split("\\|");

                int size = string4.length;
                Double[] double1 = new Double[size];
                for (int i = 0; i < size; i++) {
                    double1[i] = Double.parseDouble(string2[i]);
                }
                
                for (int i = 0; i < buffer.length; i++) {
                    System.out.println(buffer[i]);
                }
                for (int i = 0; i < string1.length; i++) {
                    System.out.println(string1[i]);
                }
                
                //because of the arraylist, i dont know how does it work with initialize the things to the constructor
                //invoice.add(new Invoice(string2, string3, string4, double1));

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}