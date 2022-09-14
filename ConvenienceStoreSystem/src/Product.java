
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class Product implements FileFunctions{
    private String code;
    private String name;
    private double currentSellingPrice;
    private double currentCostPrice;
    private int stockQty;
    private int minReorderQty;
    private String category;
    private ArrayList<TransactionDetails> transactionDetails;
    private ArrayList<StockDetails> stockDetails;
    
    private static int nextCode = 0;     //Read from file to get nextCode

    public Product() {
        code = "P" + String.format("%04d", nextCode);
        nextCode++;
    }
    
    public Product(String code, String name, double currentSellingPrice, double currentCostPrice, int stockQty, int minReorderQty, String category) {
        code = "P" + nextCode;
        this.name = name;
        this.currentSellingPrice = currentSellingPrice;
        this.currentCostPrice = currentCostPrice;
        this.stockQty = stockQty;
        this.minReorderQty = minReorderQty;
        this.category = category;
        nextCode++;
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
    
    @Override
    public void add() {
        System.out.println("add function body");
    }
    
    @Override
    public void search() {
        System.out.println("add function body");
    }
    
    @Override
    public void modify() {
        System.out.println("add function body");
    }
    
    @Override
    public void display() {
        System.out.println("add function body");
    }
    
    @Override
    public String toString() {
        return "Product code: " + code + ", Product name: " + name + ", Current selling price: " + currentSellingPrice + ", Current cost price: " + currentCostPrice + ", Stock quantity: " + stockQty + ", Product minimum reorder quantity: " + minReorderQty +  ", Category: " + category;        
    }
}
