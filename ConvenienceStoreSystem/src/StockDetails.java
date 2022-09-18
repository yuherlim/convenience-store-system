/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class StockDetails {
    private String productName;
    private int qty;
    private double costPrice;
    private String cnNo;
    private String invNo;

    public static String fileName = "stockDetails.txt";
    
    public StockDetails() {
        
    }
    
    public StockDetails(String productName, int qty, double costPrice, String cnNo, String invNo) {
        this.productName = productName;
        this.qty = qty;
        this.costPrice = costPrice;
        this.cnNo = cnNo;
        this.invNo = invNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    @Override
    public String toString() {
        return "StockDetails{" + "productName=" + productName + ", qty=" + qty + ", costPrice=" + costPrice + ", cnNo=" + cnNo + ", invNo=" + invNo + '}';
    }
    
    
}
