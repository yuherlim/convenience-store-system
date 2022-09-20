
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class Product {
    private String code;
    private String name;
    private double currentSellingPrice;
    private double currentCostPrice;
    private int stockQty;
    private int minReorderQty;
    private String category;
    private ArrayList<TransactionDetails> transactionDetails;
    private ArrayList<StockDetails> stockDetails;
    
    //File name to store product records.
    public static String fileName = "products.txt";
    
//    private static int nextCode = 0;     //Read from file to get nextCode

    public Product() {
        this.code = "";
        this.name = "";
        this.category = "";
//        code = "P" + String.format("%04d", nextCode);
//        nextCode++;
    }

    public Product(String code, String name, double currentSellingPrice, double currentCostPrice, int stockQty, int minReorderQty, String category, ArrayList<TransactionDetails> transactionDetails, ArrayList<StockDetails> stockDetails) {
//        code = "P" + nextCode;
        this.code = code;
        this.name = name;
        this.currentSellingPrice = currentSellingPrice;
        this.currentCostPrice = currentCostPrice;
        this.stockQty = stockQty;
        this.minReorderQty = minReorderQty;
        this.category = category;
        this.transactionDetails = transactionDetails;
        this.stockDetails = stockDetails;
//        nextCode++;
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentSellingPrice() {
        return currentSellingPrice;
    }

    public void setCurrentSellingPrice(double currentSellingPrice) {
        this.currentSellingPrice = currentSellingPrice;
    }

    public double getCurrentCostPrice() {
        return currentCostPrice;
    }

    public void setCurrentCostPrice(double currentCostPrice) {
        this.currentCostPrice = currentCostPrice;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getMinReorderQty() {
        return minReorderQty;
    }

    public void setMinReorderQty(int minReorderQty) {
        this.minReorderQty = minReorderQty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public ArrayList<StockDetails> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(ArrayList<StockDetails> stockDetails) {
        this.stockDetails = stockDetails;
    }
    
    public static void add(String fileName, ArrayList<Product> products) {
        Product.writeFile(fileName, products);
    }
    
    public void search() {
        System.out.println("add function body");
    }
    
    public void modify() {
        System.out.println("add function body");
    }
    
    public void display() {
        System.out.println("add function body");
    }
    
    //Add a new product category in the specified txt file if it has not existed before.
    public static void addCategory(String fileName, String category) {
//        boolean exist = false;
        
        try {
            //Create FileWriter set to append mode (second parameter true) 
            FileWriter writer = new FileWriter("src\\" + fileName, true);
  
//            for (int i = 0; i < categoryList.size(); i++) {
//                //Check whether the category existed before or not.
//                if (categoryList.get(i).equals(category)) {
//                    System.out.println("This category already exists.");
//                    exist = true;
//                    break;
//                }
//            }
//            //Writes the record to the file if the category has not existed before.
//            char confirmation;
//            if (!exist) {
//                confirmation = General.yesNoInput("Confirm add category? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
//                if (confirmation == 'Y')
//                    writer.write(category + "\n");
//                else 
//                    exist = true;
//            }
            //Write the new category into the specified fileName.
            writer.write(category + "\n");    
            // Closes the writer
            writer.close();
        }
  
        catch (Exception e) {
            e.getStackTrace();
        }
//        return (!exist);
    }
    
    public static void writeFile(String fileName, ArrayList<Product> products) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);
  
            for (int i = 0; i < products.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%%.2f|%.2f%%%d|%d%%%s\n", products.get(i).getCode(), products.get(i).getName(), products.get(i).getCurrentSellingPrice(), products.get(i).getCurrentCostPrice(), products.get(i).getStockQty(), products.get(i).getMinReorderQty(), products.get(i).getCategory());
                //Writes the record to the file.
                writer.write(line);
            }
  
            // Closes the writer
            writer.close();
        }
  
        catch (Exception e) {
            e.getStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "Product code: " + code + ", Product name: " + name + ", Current selling price: " + currentSellingPrice + ", Current cost price: " + currentCostPrice + ", Stock quantity: " + stockQty + ", Product minimum reorder quantity: " + minReorderQty +  ", Category: " + category;        
    }
}
