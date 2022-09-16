
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate; // import the LocalDate class
import java.util.Arrays;

public class CreditNote {
    private String cnNo;
    private LocalDate cnDate; // Create a date object; //= LocalDate.now();
    private Supplier supplier;
    private Staff staff;
    private ArrayList<StockDetails> stockDetails;
    private double amount;
    private static String fileName = "creditNote.txt";
    

    public CreditNote(String cnNo, LocalDate cnDate, Staff staff, Supplier supplier, StockDetails[] stockDetails, double amount) {
        this.cnNo = cnNo;
        this.cnDate = cnDate;
        this.staff = staff;
        this.supplier = supplier;
        this.stockDetails = new ArrayList<>(Arrays.asList(stockDetails));
        this.amount = amount;
    }

    public String getCnNo() {
        return cnNo;
    }

    //still figure auto-increment for cnNo
//    public void setCnNo(String cnNo) {
//        this.cnNo = cnNo;
//    }
    
    public LocalDate getCnDate() {
        return cnDate;
    }

    public void setCnDate(LocalDate cnDate) {
        this.cnDate = cnDate;
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
    
    public static void readFile(String fileName, ArrayList<CreditNote> creditNote) {
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
                //creditNote.add(new Invoice(string2, string3, string4, double1));

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
