
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class TransactionDetails {
    private int qty;
    private String transactionId;
    private String productCode;
    private double sellingPrice;


    public static final String FILE_NAME = "transactionDetails.txt";
    
    public TransactionDetails() {
        transactionId = "";
        productCode = "";
    }
    
    public TransactionDetails (TransactionDetails obj){
        this.qty = obj.qty;
        this.transactionId = obj.transactionId;
        this.productCode = obj.productCode;
        this.sellingPrice = obj.sellingPrice;
    }
    
    public TransactionDetails(int qty, String transactionId, String productCode, double sellingPrice) {
        this.qty = qty;
        this.transactionId = transactionId;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    public static void add(ArrayList<TransactionDetails> addTransactionDetails){
        ArrayList<TransactionDetails> transactionDetails = readFile(FILE_NAME);
        
        for(int i = 0; i <addTransactionDetails.size(); i++){
                transactionDetails.add(addTransactionDetails.get(i));
                }
        
        writeFile(FILE_NAME,transactionDetails);
        
    }

    //reads file and returns TransactionDetails array list.
    public static ArrayList<TransactionDetails> readFile(String fileName) {
        ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                String[] codes = buffer[0].split("\\|");
                String string2 = buffer[1];
                String string3 = buffer[2];   
                
                //Convert string to integer for transaction details quantity.
                int qty = Integer.parseInt(string2);
                
                //Convert string to double for transaction details selling price
                double sellingPrice = Double.parseDouble(string3);
                
                transactionDetails.add(new TransactionDetails(qty, codes[1], codes[0], sellingPrice));
            }
            reader.close();
 
        } catch (IOException e) {
            System.out.println("  " + fileName + " could not be opened.");
        }
        
        return transactionDetails;
    }
    
    //method to write the data of a transaction details array list into a specified file name.
    public static void writeFile(String fileName, ArrayList<TransactionDetails> transactionDetails) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);
  
            for (int i = 0; i < transactionDetails.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%%d%%%.2f\n", transactionDetails.get(i).getProductCode(), transactionDetails.get(i).getTransactionId(), transactionDetails.get(i).getQty(), transactionDetails.get(i).getSellingPrice());
                //Writes the record to the file.
                writer.write(line);
            }
  
            // Closes the writer
            writer.close();
        }
  
        catch (IOException e) {
            System.out.println("  " + fileName + " could not be opened.");
        }
    }
    
    @Override
    public String toString() {
        return "TransactionDetails{" + "qty=" + qty + ", transactionId=" + transactionId + ", productCode=" + productCode + ", sellingPrice=" + sellingPrice + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionDetails other = (TransactionDetails) obj;
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        return Objects.equals(this.productCode, other.productCode);
    }
    
    
}
