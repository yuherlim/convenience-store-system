import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

        //Used to store the current products array list

        ArrayList<Product> products = new ArrayList<>();
        
        Product p1 = new Product();
        
//        ProductDriver.addProduct(products, p1);
        
        products = readFile(Product.fileName, products);
        
//        for (Product p: products) {
//            System.out.println("Product:");
//            System.out.println(p);
//            System.out.println("Product's Transaction Details: ");
//            for (TransactionDetails transDets: p.getTransactionDetails())
//                System.out.println(transDets);
//            System.out.println("Product's Stock Details");
//            for (StockDetails stockDets: p.getStockDetails())
//                System.out.println(stockDets);
//            System.out.println();
//        }

    }
    
    public static ArrayList<Product> readFile(String fileName, ArrayList<Product> products) {
        try {
            //Array lists used when reading from transaction details and stock details text files
            ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");
                String[] string2 = buffer[1].split("\\|");
                String[] string3 = buffer[2].split("\\|");
                
                //Store the read data into their respective variables to be used later to create product object.
                String category = buffer[3];
                String code = string1[0];
                String name = string1[1];
                
                //Convert string to double for currentSellingPrice and currentCostPrice
                //element 1 is currentSellingPrice and element 2 is currentCostPrice
                double[] doubleArr = new double[2];
                for (int i = 0; i < string2.length; i++) {
                    doubleArr[i] = Double.parseDouble(string2[i]);
                }
                
                //Convert string to integer for stockQty and minReorderQty
                //element 1 is stockQty and element 2 is minReorderQty
                int[] intArr = new int[2];
                for (int i = 0; i < string3.length; i++) {
                    intArr[i] = Integer.parseInt(string3[i]);
                }

                //read from transactionDetails.txt and create a copy of transactionDetails records
                ArrayList<TransactionDetails> allTD = (ArrayList<TransactionDetails>) TransactionDetailsDriver.readFile(TransactionDetails.fileName, transactionDetails).clone();
                transactionDetails.clear();
                //Add elements of transaction details that is associated with this product code.
                for (TransactionDetails td: allTD) {
                    if (td.getProductCode().equals(code))
                        transactionDetails.add(td);
                }
                
                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, stockDetails).clone();
                stockDetails.clear();
                //Add elements of stock details that is associated with this product code.
                for (StockDetails sd: allSD) {
                    if(sd.getProductCode().equals(code))
                        stockDetails.add(sd);
                }
                
                //store cloned versions of transactionDetails and stockDetails as they will be used again in subsequent loops
                products.add(new Product(code, name, doubleArr[0], doubleArr[1], intArr[0], intArr[1], category, (ArrayList<TransactionDetails>)transactionDetails.clone(), (ArrayList<StockDetails>)stockDetails.clone()));
                
            
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public static void writeFile(String fileName, ArrayList<Product> products) {
        String line;
        try {
            //Create FileWriter set to write mode for second parameter (false)
            FileWriter writer = new FileWriter("src\\" + fileName, false);
  
            for (int i = 0; i < products.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%%.2f|%.2f|%%%d|%d%%%s\n", products.get(i).getCode(), products.get(i).getName(), products.get(i).getCurrentSellingPrice(), products.get(i).getCurrentCostPrice(), products.get(i).getStockQty(), products.get(i).getMinReorderQty(), products.get(i).getCategory());
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
    
    //Method to add a new product
    public static void addProduct(ArrayList<Product> products, Product p1) {
        
        //Read the current product list
        products = readFile(Product.fileName, products);
        
        //Used when reading records available from stock details
        ArrayList<StockDetails> currentStockDetails = new ArrayList<>();
        //Read the current stock details list
        currentStockDetails = StockDetailsDriver.readFile(StockDetails.fileName, currentStockDetails);
        
//        //Read the current stock details list and store a copy of it in currentStockDetails
//        ArrayList<StockDetails> currentStockDetails = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, stockDetails).clone();
        
        char cont = 'Y';
        do {
            Scanner sc = new Scanner(System.in);
            //User input

            System.out.print("Enter product name: ");
            General.stringInput("Enter product name: ", "Invalid product name, try again");
            
//            //Array list to store the stock available to create product from.
//            ArrayList<String> productName = new ArrayList<>();

//            //for each product, loop through the stock details to show available stocks to create product from.
//            for (Product p: products) {
//                for(StockDetails sd: currentStockDetails) {
//                    if (!p.getName().equals(sd.getProductName()))
//                        if (!productName.contains(sd.getProductName()))
//                             productName.add(sd.getProductName());
//                }
//                
//            }
            
//            for (int i = 0; i < currentStockDetails.size(); i++) {
//                if (!products.get(i).getName().equals(currentStockDetails.get(i).getProductName())) {
//                     //if the product name does not exist in the product records, store it into productName array list.
//                    //This if statement is to prevent storing duplicate product names.
//                    if (!productName.isEmpty())
//                        if (!productName.contains(currentStockDetails.get(i).getProductName()))
//                             productName.add(currentStockDetails.get(i).getProductName());
//                }
//            }

//            //prints out the available stocks to create product name from.
//            System.out.println("Stock available: ");
//            for (int i = 0; i < productName.size(); i++) {
//                System.out.printf("%2d. %s\n", i + 1, productName.get(i).toUpperCase());
//            }
            
//            //input validation for product name.
//            boolean validName = false;
//            do {
//                System.out.printf("Choose product name (Enter string of product name): ");
//                String name = sc.nextLine().toUpperCase();
//
//                for (String p: productName) {
//                    if (p.equals(name)) {
//                        validName = true;
//                        p1.setName(name);
//                        break;
//                    }
//                }
//                if (validName == false)
//                    System.out.println("Invalid product name. Try again.");
//            } while (validName == false);

//            //loop calculate the sum of the cost prices for this product.
//            double sumCostPrice = 0d;
//            int costPriceCount = 0;
//            for (int i = 0; i < stockDetails.size(); i++) {
//                if (p1.getName().equals(stockDetails.get(i).getProductName())) {
//                    sumCostPrice += stockDetails.get(i).getCostPrice();
//                    costPriceCount++;
//                }
//            }

//             //calculate average cost price for this product
//            double averageCostPrice = sumCostPrice / costPriceCount;

//            //set current product's average cost price.
//            p1.setAverageCostPrice(averageCostPrice);

//            System.out.printf("Current average stock cost price: RM %.2f\n", p1.getAverageCostPrice());
            
            //input validation for current selling price
            boolean validSellingPrice = false;
            do {
                System.out.print("Enter product current selling price: ");
                double currentSellingPrice = sc.nextDouble();
                if (currentSellingPrice > p1.getCurrentCostPrice()) {
                    p1.setCurrentSellingPrice(currentSellingPrice);
                    validSellingPrice = true;
                } else 
                    System.out.println("Product current selling price cannot be smaller than the average cost price. Try again.");
            } while(validSellingPrice == false);

            //set current product's quantity
            int prodQty = 0;
            for(int i = 0; i < currentStockDetails.size(); i++) {
                if (currentStockDetails.get(i).getProductCode().equals(p1.getCode())) {
                    if (currentStockDetails.get(i).getInvNo().charAt(0) == 'I')
                        prodQty += currentStockDetails.get(i).getQty();
                    else
                        prodQty -= currentStockDetails.get(i).getQty();
                }
            }
            p1.setStockQty(prodQty);
            
            //Show current product's quantity
            System.out.printf("Current product's stock quantity: %d\n", p1.getStockQty());
            System.out.print("Enter product minimum reorder quantity: ");
            p1.setMinReorderQty(sc.nextInt());

            sc.nextLine();      //Clear newline

            System.out.print("Enter product category: ");
            p1.setCategory(sc.nextLine().toUpperCase());

            //Set current product ID: get product code of last element, take the back part, convert to integer, increment it, convert back to string to store. 
            String currentProductCode = "P" + String.format("%04d", Integer.parseInt(products.get(products.size() - 1).getCode().substring(1, 5)) + 1);
            p1.setCode(currentProductCode);

            
            //read from stockDetails.txt and create a copy of stock details records.
            ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, currentStockDetails).clone();
            currentStockDetails.clear();
            //Add elements of stock details that is associated with this product code.
            for (StockDetails sd: allSD) {
                if (sd.getProductCode().equals(p1.getCode())) 
                    currentStockDetails.add(sd);
            }
            
            
            //set stockDetails associated with this product.
            p1.setStockDetails(currentStockDetails);
            
            //set transactionDetails array list to null cause there is no transaction yet with this new product.
            p1.setTransactionDetails(null);
            
            //confirmation of adding of product.
            char confirmation = 'N';
            System.out.print("Confirm add product? (Y/N) : ");
            confirmation = Character.toUpperCase(sc.next().charAt(0));
            if (confirmation == 'Y') {
                products.add(p1);
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Product is not added.");
            }

            System.out.println("Continue adding new product? (Y/N)");
            cont = Character.toUpperCase(sc.next().charAt(0));
        } while(cont == 'Y'); 
        //Write to file after finish adding products
        Product.add(Product.fileName, products);
    }
}
