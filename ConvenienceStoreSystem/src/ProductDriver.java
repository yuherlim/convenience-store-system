
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
        ArrayList<StockDetails> stockDetails = new ArrayList<>();
//        Product p1 = new Product();
//        addProduct(products, p1);
        
        products = readFile("products.txt", products, transactionDetails, stockDetails);
        
        for (Product p: products) {
            System.out.println(p);
            for (TransactionDetails transDets: p.getTransactionDetails())
                System.out.println(transDets);
            for (StockDetails stockDets: p.getStockDetails())
                System.out.println(stockDets);
            System.out.println();
        }
    }
    
    public static ArrayList<Product> readFile(String fileName, ArrayList<Product> products, ArrayList<TransactionDetails> transactionDetails, ArrayList<StockDetails> stockDetails) {
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
                
                String category = buffer[3];
                String code = string1[0];
                String name = string1[1];
                
                //Convert string to double for currentSellingPrice and currentCostPrice
                double[] doubleArr = new double[2];
                for (int i = 0; i < string2.length; i++) {
                    doubleArr[i] = Double.parseDouble(string2[i]);
                }
                
                //Convert string to integer for stockQty and minReorderQty
                int[] intArr = new int[2];
                for (int i = 0; i < string3.length; i++) {
                    intArr[i] = Integer.parseInt(string3[i]);
                }
                
                //read from transactionDetails.txt
                transactionDetails = TransactionDetailsDriver.readFile("transactionDetails.txt", transactionDetails);
                
                //remove array list elements that do not contain the current product ID
                for(int i = 0; i < transactionDetails.size(); i++) {
                    if (!transactionDetails.get(i).getProductCode().equals(code)) 
                        transactionDetails.remove(i);
                }
                
                //read from stockDetails.txt
                stockDetails = StockDetailsDriver.readFile("stockDetails.txt", stockDetails);

                //remove array list elements that do not contain the current product ID
                for (int i = 0; i < stockDetails.size(); i++) {
                    if (!stockDetails.get(i).getProductCode().equals(code)) 
                        stockDetails.remove(i);
                }
                
                products.add(new Product(code, name, doubleArr[0], doubleArr[1], intArr[0], intArr[1], category, transactionDetails, stockDetails));
                
            
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return products;
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
