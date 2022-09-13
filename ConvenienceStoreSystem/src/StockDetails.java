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

    public StockDetails() {
        
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
    
    
}
