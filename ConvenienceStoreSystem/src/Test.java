
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
public class Test { 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        char inputtest = General.charInput("Please enter a character : ", "Invalid character.");
        
//        char inputtest = General.yesNoInput("Please enter a character : ", "Invalid character.");
//        System.out.println(Character.isLetter(' '));
        
//        String stringtest = General.stringInput("Please enter a string: ", "Not a string.");
//        System.out.println(stringtest);
        
//        double doubletest = General.doubleInput("Please enter a double: ", "Not a double");
//        
//        System.out.println(doubletest);
        
        int inttest = General.intInput("Please enter a int", "Not an int");
        
        System.out.println(inttest);
           

//          char input1 = sc.next().charAt(0);
//          
//          char input2 = sc.next().charAt(0);
//          
//          System.out.println(input1 + input2);
        
//        char input = General.yesNoInput("Please enter y/n : ", "Invalid input, please enter a character.");
//        System.out.println(input);
        
//        System.out.println("Test output.");
//        ArrayList<Product> products = new ArrayList<Product>();
//        //Used when reading record details for the products.
//        ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
//        ArrayList<StockDetails> stockDetails = new ArrayList<>();
//        //Read the current product list
//        products = ProductDriver.readFile(Product.fileName, products);
//        
//        ArrayList<StockDetails> test = new ArrayList<>();
//        test = readFile(StockDetails.fileName, test);
//        
//        for(Product p: products) {
//            System.out.println("Product: ");
//            System.out.println(p);
//            for(TransactionDetails td: p.getTransactionDetails()) {
//                System.out.println("Transaction Details: ");
//                System.out.println(td);
//            }
//            for(StockDetails sd: p.getStockDetails()) {
//                System.out.println("Stock Details: ");
//                System.out.println(sd);
//            }
//        }
//            
//        System.out.println("----------------------------------------------");
//        
//        for(StockDetails t: test) 
//            System.out.println(t);
            
    }
    
    public static ArrayList<StockDetails> readFile(String fileName, ArrayList<StockDetails> stockDetails) {
        try {
            FileReader reader = new FileReader("src\\stockDetails.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] buffer = line.split("\\%");
                //first string is product name, second string is either invoice number or credit note number
                String[] string1 = buffer[0].split("\\|");
                String string2 = buffer[1];
                String string3 = buffer[2];   
                
                //Convert string to integer for transaction details quantity.
                int qty = Integer.parseInt(string2);
                
                //Convert string to double for transaction details selling price
                double costPrice = Double.parseDouble(string3);
                
                if (string1[1].substring(0,1).equals("I"))
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, "", string1[1]));
                else
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, string1[1], ""));
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return stockDetails;
    }
    
    
}


