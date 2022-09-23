
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
public class StockDetails {
    private String productCode;
    private int qty;
    private double costPrice;
    private String cnNo;
    private String invNo;

    public static final String FILE_NAME = "stockDetails.txt";
    
    public StockDetails() {
        productCode = "";
        cnNo = "";
        invNo = "";
    }
    
    public StockDetails(StockDetails sd) {
        this.productCode = sd.productCode;
        this.qty = sd.qty;
        this.costPrice = sd.costPrice;
        this.cnNo = sd.cnNo;
        this.invNo = sd.invNo;
    }
    
    public StockDetails(String productCode, int qty, double costPrice, String cnNo, String invNo) {
        this.productCode = productCode;
        this.qty = qty;
        this.costPrice = costPrice;
        this.cnNo = cnNo;
        this.invNo = invNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getCnNo() {
        return cnNo;
    }

    public void setCnNo(String cnNo) {
        this.cnNo = cnNo;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    //reads file and returns StockDetails array list.
    public static ArrayList<StockDetails> readFile(String fileName) {  
        ArrayList<StockDetails> stockDetails = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                //first string is product code, second string is either invoice number or credit note number
                String[] string1 = buffer[0].split("\\|");
                String string2 = buffer[1];
                String string3 = buffer[2];   
                
                //Convert string to integer for stock details quantity.
                int qty = Integer.parseInt(string2);
                
                //Convert string to double for stock details cost price
                double costPrice = Double.parseDouble(string3);
                
                //Check whether the current stock detail object has a invoice no or a credit no
                if (string1[1].substring(0,1).equals("I"))
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, "", string1[1]));
                else
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, string1[1], ""));
            }
            reader.close();
 
        } catch (IOException e) {
            System.out.println("  " + fileName + " could not be opened.");
        }
        
        return stockDetails;
    }
    
    //method to write the data of a transaction details array list into a specified file name.
    public static void writeFile(String fileName, ArrayList<StockDetails> stockDetails) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);
  
            for (int i = 0; i < stockDetails.size(); i++) {
                //Create a new record to be written
                if (stockDetails.get(i).getInvNo().equals(""))
                    line = String.format("%s|%s%%%d%%%.2f\n", stockDetails.get(i).getProductCode(), stockDetails.get(i).getCnNo(), stockDetails.get(i).getQty(), stockDetails.get(i).getCostPrice());
                else
                    line = String.format("%s|%s%%%d%%%.2f\n", stockDetails.get(i).getProductCode(), stockDetails.get(i).getInvNo(), stockDetails.get(i).getQty(), stockDetails.get(i).getCostPrice());
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
        return "StockDetails{" + "productCode=" + productCode + ", qty=" + qty + ", costPrice=" + costPrice + ", cnNo=" + cnNo + ", invNo=" + invNo + '}';
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
        final StockDetails other = (StockDetails) obj;
        if (!Objects.equals(this.productCode, other.productCode)) {
            return false;
        }
        if (!Objects.equals(this.cnNo, other.cnNo)) {
            return false;
        }
        return Objects.equals(this.invNo, other.invNo);
    }
    
}
