
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class ProductDriver {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList();
        Product p1 = new Product();
        addProduct(products, p1);
        
        System.out.println(products.get(0));
    }
    
    //Method to add a new product
    public static void addProduct(ArrayList<Product> products, Product p1) {
        Scanner sc = new Scanner(System.in);
        //User input
        System.out.print("Enter product name: ");
        p1.setName(sc.nextLine());
        System.out.print("Enter product current selling price: ");
        p1.setCurrentSellingPrice(sc.nextDouble());
        System.out.print("Enter product current cost price: ");
        p1.setCurrentCostPrice(sc.nextDouble());
        System.out.print("Enter product stock quantity: ");
        p1.setStockQty(sc.nextInt());
        System.out.print("Enter product minimum reorder quantity: ");
        p1.setMinReorderQty(sc.nextInt());
        sc.nextLine();      //Clear newline
        System.out.print("Enter product category: ");
        p1.setCategory(sc.nextLine());
        
        products.add(p1);
        
        
    }
}
