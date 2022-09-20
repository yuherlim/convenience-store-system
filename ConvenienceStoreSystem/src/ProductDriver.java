import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        
//        Product p1 = new Product();
        
//        ProductDriver.addProduct(products, p1);

        
        ProductDriver.addProduct(products);
        
//        products = readFile(Product.fileName, products);
//        
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
    
//    //method that adds new category into a specified txt file.
//    public static void addCategory(String fileName) {
//        ArrayList<String> categoryList = new ArrayList<>();
//        //read and store all the existing categories into categoryList
//        try {
//            FileReader reader = new FileReader("src\\" + fileName);
//            BufferedReader bufferedReader = new BufferedReader(reader);
// 
//            String line;
// 
//            while ((line = bufferedReader.readLine()) != null) {
//                categoryList.add(line);
//            }
//            reader.close();
// 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        boolean loop;
//        do {
//            loop = true;
//            System.out.println("Existing categories: ");
//            for (String category: categoryList) {
//                System.out.println(category);
//            }
//
//            System.out.println("");
//            String category = General.stringInput("Please enter new category name : ", "Invalid category name, please try again.").toUpperCase();
//
//            if (Product.addCategory(fileName, categoryList, category)) {
//                System.out.println("New category : " + category + " successfully added.");
//                loop = false;
//            } else 
//                System.out.println("New category not added.\n");
//        } while(loop == true);
//    }
    
    //Method to add a new product
    public static void addProduct(ArrayList<Product> products) {
        
        //Read the current product list
        products = readFile(Product.fileName, products);
        
        //Used when reading records available from stock details
        ArrayList<StockDetails> currentStockDetails = new ArrayList<>();
        //Read the current stock details list
        currentStockDetails = StockDetailsDriver.readFile(StockDetails.fileName, currentStockDetails);
        
//        //Read the current stock details list and store a copy of it in currentStockDetails
//        ArrayList<StockDetails> currentStockDetails = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, stockDetails).clone();
        
        //Loop for user input and adding products.
        char cont = 'Y';
        do {
            //User input

            //Set current product ID: get product code of last element, take the back part, convert to integer, increment it, convert back to string to store. 
            String currentProductCode = "P" + String.format("%04d", Integer.parseInt(products.get(products.size() - 1).getCode().substring(1, 5)) + 1);
            
            System.out.println("Product code: " + currentProductCode);
            
            //Ask for product name
            //Validate product name to prevent duplicate products to be created.
            String name;
            boolean validName;
            do {
                validName = true;
                name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getName().equals(name)) {
                        System.out.println("This product name has already existed, please try another product name.");
                        validName = false;
                        break;
                    } 
                }
                
            } while (validName == false);
            
            //Ask for product current cost price
            //Input validation for current cost price.
            double currentCostPrice;
            boolean validCostPrice;
            do {
                validCostPrice = false;
                currentCostPrice = General.doubleInput("Enter current cost price: ", "Invalid cost price, please try again.");
                if (currentCostPrice > 0)
                    validCostPrice = true;
                else 
                    System.out.println("Product current cost price cannot be a negative number. Please try again.");
            } while(validCostPrice == false);
            
            //Ask for product current selling price
            //input validation for current selling price
            double currentSellingPrice;
            boolean validSellingPrice;
            do {
                validSellingPrice = false;
                currentSellingPrice = General.doubleInput("Enter current selling price: ", "Invalid selling price, please try again.");
                if (currentSellingPrice > currentCostPrice)
                    validSellingPrice = true;
                else 
                    System.out.println("Product current selling price cannot be smaller than the cost price. Please try again.");
            } while(validSellingPrice == false);
            
            //set default stockQty for newly created product as 0
            int stockQty = 0;
            
            //Ask for minimum stock reorder quantity
            int minReorderQty;
            boolean validMinReorderQty;
            do {
                validMinReorderQty = false;
                minReorderQty = General.intInput("Enter minimum reorder quantity: ", "Invalid input, please ensure you have entered an integer.");
                if (minReorderQty < 0)
                    System.out.println("Invalid, you have entered a negative number, please try again.");
                else
                    validMinReorderQty = true;
            } while(validMinReorderQty == false);
            
//            String category = General.stringInput("Enter category of product: ", "Invalid category name, please try again.").toUpperCase();
            
            ArrayList<String> categoryList = new ArrayList<>();
            
            //read and store all the existing categories into categoryList
            try {
                FileReader reader = new FileReader("src\\" + "productCategory.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    categoryList.add(line);
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            
            //Ask for the category of this product.
            //Validate the category of this product if it does not exist ask if they want to create it.
            String category;
            boolean validCategory;
            do {
                validCategory = false;
                System.out.println("Existing categories: ");
                for (String ct: categoryList) {
                    System.out.println(ct);
                }

                System.out.println("");
                category = General.stringInput("Enter category of product: ", "Invalid category name, please try again.").toUpperCase();

                for (String ct: categoryList) {
                    if (ct.equals(category)) {
                        validCategory = true;
                        break;
                    }
                }
                
                if (validCategory == false) 
                    if (General.yesNoInput("This product category does not exist, do you want to add it? (Y)es/(N)o : ", "Invalid input, please enter Y or N.") == 'Y') {
                        Product.addCategory("productCategory.txt", category);
                        validCategory = true;
                    }
            } while(validCategory == false);
                
                
            
            
            
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
            
            

            //set current product's quantity
//            int prodQty = 0;
//            for(int i = 0; i < currentStockDetails.size(); i++) {
//                if (currentStockDetails.get(i).getProductCode().equals(p1.getCode())) {
//                    if (currentStockDetails.get(i).getInvNo().charAt(0) == 'I')
//                        prodQty += currentStockDetails.get(i).getQty();
//                    else
//                        prodQty -= currentStockDetails.get(i).getQty();
//                }
//            }
//            p1.setStockQty(prodQty);
            
//            //Show current product's quantity
//            System.out.printf("Current product's stock quantity: %d\n", p1.getStockQty());
//            System.out.print("Enter product minimum reorder quantity: ");
//            p1.setMinReorderQty(sc.nextInt());
//
//            sc.nextLine();      //Clear newline
//
//            System.out.print("Enter product category: ");
//            p1.setCategory(sc.nextLine().toUpperCase());

//            //Set current product ID: get product code of last element, take the back part, convert to integer, increment it, convert back to string to store. 
//            String currentProductCode = "P" + String.format("%04d", Integer.parseInt(products.get(products.size() - 1).getCode().substring(1, 5)) + 1);
//            p1.setCode(currentProductCode);

            
//            //read from stockDetails.txt and create a copy of stock details records.
//            ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, currentStockDetails).clone();
//            currentStockDetails.clear();
//            //Add elements of stock details that is associated with this product code.
//            for (StockDetails sd: allSD) {
//                if (sd.getProductCode().equals(p1.getCode())) 
//                    currentStockDetails.add(sd);
//            }
            
            
//            //set stockDetails associated with this product.
//            p1.setStockDetails(currentStockDetails);
            
//            //set transactionDetails array list to null cause there is no transaction yet with this new product.
//            p1.setTransactionDetails(null);
            
            //Create product object to be added to array list. 
            //StockDetails and TransactionDetails array list is set to null because newly created product does not have any stock or is in any transaction yet.
            Product p1 = new Product(currentProductCode, name, currentSellingPrice, currentCostPrice, stockQty, minReorderQty, category, null, null);
            
            //confirmation of adding of product.
            char confirmation = 'N';
            confirmation = General.yesNoInput("Confirm add product? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
//            confirmation = Character.toUpperCase(sc.next().charAt(0));
            if (confirmation == 'Y') {
                products.add(p1);
                System.out.println("Product added successfully.");
                //Write to file after finish adding product details.
                Product.add(Product.fileName, products);
            } else {
                System.out.println("Product is not added.");
            }

            //Check whether user wants to continue adding new products
            cont = General.yesNoInput("Continue adding new product? (Y/N) : ", "Invalid input, please enter Y or N.");
        } while(cont == 'Y'); 
        
    }
}
