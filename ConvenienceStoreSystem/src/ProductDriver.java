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

        int selection;
        do {
            selection = productMenu();
            switch(selection) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    modifyProduct();
                    break;
                case 3:
                    editProductStatus();
                    break;
                case 4:
                    searchProduct();
                    break;
                case 5:
                    viewProduct();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-5).");
            }
        } while(selection != 0);
        
//        Product p1 = new Product();
        
//        ProductDriver.addProduct(products, p1);

        
//        ProductDriver.addProduct();
        
//        ProductDriver.searchProduct();

//        ProductDriver.modifyProduct();

//        ProductDriver.editProductStatus();

//        ProductDriver.viewProduct();
        
//        ProductDriver.modifyProduct(products);
        
        //Used to store the current products array list

//        ArrayList<Product> products = Product.readFile(Product.fileName);
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
    
    
    //Method to add a new product
    public static void addProduct() {
        
        //Give user a choice to return to product menu.
        int selection;
        do {
            printHeader("addProduct");
            System.out.println("Available choices: ");
            System.out.println("1. Start adding product(s)");
            System.out.println("");
            System.out.println("0. Return to product menu");
            System.out.println("");
            selection = General.intInput("Enter your selection (0-1) : ", "Invalid input, please enter an integer.");
            switch(selection) {
                case 1:
                    break;
                case 0:
                    System.out.println("Returning to product menu...");
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-1).");
            }
        } while(selection != 0 && selection != 1);
        
        if (selection == 0)
            return;
        
        //Read the current product list into an array list
        ArrayList<Product> products = Product.readFile(Product.fileName);
        
//        //Used when reading records available from stock details
//        ArrayList<StockDetails> currentStockDetails = new ArrayList<>();
//        //Read the current stock details list
//        currentStockDetails = StockDetailsDriver.readFile(StockDetails.fileName, currentStockDetails);
        
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
            String name = nameInput();
            
            //Ask for product current cost price
            double currentCostPrice = currentCostPriceInput();
            
            
            //Ask for product current selling price
            double currentSellingPrice = currentSellingPriceInput(currentCostPrice);
            
            //set default stockQty for newly created product as 0
            int stockQty = 0;
            
            //Ask for minimum stock reorder quantity
            int minReorderQty = minReorderQtyInput();
            
            //Ask for the category of this product.
            String category = categoryInput();
            
            //Set default product status
            String status = "ACTIVE";
            
            
            
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
            Product p1 = new Product(currentProductCode, name, currentSellingPrice, currentCostPrice, stockQty, minReorderQty, category, null, null, status);
            
            //confirmation of adding of product.
            char confirmation = 'N';
            confirmation = General.yesNoInput("Confirm add product? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
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
    
    //method to search for products and print the search results.
    public static void searchProduct() {
        printHeader("searchProduct");
        
        //Array list to store the returned search results
        ArrayList<Product> productSearchResults;
        
        //search menu
        int printCount;
        int selection;
        do {
            selection = searchMenu("search");
            switch(selection) {
                case 1:
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    printCount = 0;
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader");
                    productSearchResults = Product.search("productCode", code);
                    
                    if (productSearchResults != null) {
                        printCount = Product.display(productSearchResults);
                    } else {
                        System.out.println("Product code entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    break;
                case 2:
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    printCount = 0;
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader");
                    productSearchResults = Product.search("productName", name);
                    
                    if (productSearchResults != null) {
                        printCount = Product.display(productSearchResults);
                    } else {
                        System.out.println("Product name entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    break;
                case 3:
                    
                    //Ask for product category and search for the product details with the product category.
                    String category = General.stringInput("Enter category of product: ", "Invalid category name, please try again.").toUpperCase();
                    printCount = 0;
                    
                    productSearchResults = Product.search("productCategory", category);
                    
                    if (productSearchResults == null) {
                        System.out.println("Product category entered does not exist.");
                        System.out.println("");
                        continue;
                    }
                    
                    //Array list to store all, active or inactive products
                    ArrayList<Product> categoryProductSearchResults = new ArrayList<>();
                    //Ask whether user want to view all, active, or inactive category of products.
                    int activeOrInactiveSelection;
                    do {
                        
                        activeOrInactiveSelection = searchMenu("activeOrInactive");
                        switch(activeOrInactiveSelection) {
                            case 1:
                                for (Product product: productSearchResults) {
                                    categoryProductSearchResults.add(new Product(product));
                                }
                                break;
                            case 2:
                                for (Product product: productSearchResults) {
                                    if (product.getStatus().equals("ACTIVE"))
                                        categoryProductSearchResults.add(new Product(product));
                                }
                                break;
                            case 3:
                                for (Product product: productSearchResults) {
                                    if (product.getStatus().equals("INACTIVE"))
                                        categoryProductSearchResults.add(new Product(product));
                                }
                                break;
                            case 0:
                                System.out.println("Returning to product menu...");
                                break;
                            default:
                                System.out.println("Please ensure your selection is (0-2).");
                        }
                    } while(activeOrInactiveSelection != 0 && activeOrInactiveSelection != 1 && activeOrInactiveSelection != 2 && activeOrInactiveSelection != 3);
                    
                    if (activeOrInactiveSelection == 0) {
                        selection = 0;
                        break;
                    }
                    
                    //Print out the search results.
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader");
                    if (!categoryProductSearchResults.isEmpty()) {
                        printCount = Product.display(categoryProductSearchResults);
                    } else {
                        System.out.println("No product record found.");
                    }
  
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    break;
                case 0:
                    System.out.println("Returning to product menu...");
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-3).");
            }
            
        } while(selection != 0);
    }
    
    //method to modify product details.
    public static void modifyProduct() {
        //Read the current product records into an array list.
        ArrayList<Product> products = Product.readFile(Product.fileName);
        
        //Product array list to store the search results
        ArrayList<Product> searchResults = new ArrayList<>();
        
        //Product object to store search result.
        Product productSearchResult = new Product();
        
        //modify menu 1: search for product record to edit.
        int selection;
        do {
            printHeader("modifyProduct");
            
            selection = modifyMenu("search");
            switch(selection) {
                case 1:
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    searchResults = Product.search("productCode", code);
                    //If there are search results, store the first element of the array list as a Product object. (Product code is unique)
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                    break;
                case 2:
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    searchResults = Product.search("productName", name);
                    //If there are search results, store the first element of the array list as a Product object. (Product name is unique)
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                    break;
                case 0:
                    System.out.println("Returning to product menu...");
                    continue;
                default:
                    System.out.println("Please ensure your selection is (0-2).");
                    continue;
            }
            
            //if product does not exist prompt user.
            if (searchResults == null) {
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader");
                System.out.println("Product does not exist.");
                System.out.println("");
                continue;
            }
            
            //prevent inactive products to be modified.
            if (productSearchResult.getStatus().equals("INACTIVE")) {
                System.out.println("The product you searched for is currently inactive. Only active products can be modified.");
                System.out.println("");
                continue;
            }
            
            //modify menu 2: ask for what field to edit
            int modifyFieldSelection;
            do {
                //print search results
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader");
                System.out.println(productSearchResult);
                System.out.println("");
                
                modifyFieldSelection = modifyMenu("modifyField"); 
                switch(modifyFieldSelection) {
                    case 1:
                        //Ask for new product name.
                        String name = nameInput();
                        productSearchResult.setName(name);
                        break;
                    case 2:
                        //Ask for new product selling price.
                        double currentSellingPrice = currentSellingPriceInput(productSearchResult.getCurrentCostPrice());
                        productSearchResult.setCurrentSellingPrice(currentSellingPrice);
                        break;
                    case 3:
                        //Ask for new category
                        String category = categoryInput();
                        productSearchResult.setCategory(category);
                        break;
                    case 4:
                        //Ask for new product reorder quantity.
                        int minReorderQty = minReorderQtyInput();
                        productSearchResult.setMinReorderQty(minReorderQty);
                        break;
                    case 0:
                        System.out.println("Returning to modify product menu...");
                        continue;
                    default: 
                        System.out.println("Please ensure your selection is (0-4).");
                        continue;
                }
                
                
                //ask for confirmation.
                char confirmation = 'N';
                confirmation = General.yesNoInput("Confirm modify product details? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
                
                if (confirmation == 'Y') {
                    //loop through the products array list and find the product to be edited.
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).equals(productSearchResult)) {
                            products.set(i, new Product(productSearchResult));
                            break;
                        }
                    }
                    
                    //modify the product list with the edited details.
                    Product.modify(Product.fileName, products);
                    System.out.println("Product details successfully modified.");
                } else {
                    //loop through the products array list and find the product that was not edited.
                    //revert the productSearchResult
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).equals(productSearchResult)) {
                            productSearchResult = new Product(products.get(i));
                            break;
                        }
                    }
                    System.out.println("Product details is not modified.");
                }
                
                System.out.println("");
            } while (modifyFieldSelection != 0);
        } while(selection != 0);
        
    }
    
    //method to edit the product status either become active or inactive
    public static void editProductStatus() {
        //Read the current product list into an array list
        ArrayList<Product> products = Product.readFile(Product.fileName);
        
        //Product array list to store the search results
        ArrayList<Product> searchResults = new ArrayList<>();
        
        //Product object to store search result.
        Product productSearchResult = new Product();
        
        //search for product to activate/deactivate
        int searchSelection;
        do {
            printHeader("editProductStatus");
            searchSelection = editProductStatusMenu();
            switch(searchSelection) {
                case 1:
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    searchResults = Product.search("productCode", code);
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                    break;
                case 2:
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    searchResults = Product.search("productName", name);
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                    break;
                case 0:
                    System.out.println("Returning to product menu...");
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-2).");
                    continue;
            }
            
            if (searchSelection == 0)
                break;
            
            //if product does not exist prompt user.
            if (searchResults == null) {
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader");
                System.out.println("Product does not exist.");
                System.out.println("");
                continue;
            }
            
            //print search results
            System.out.println("");
            System.out.println("Search Results: ");
            printHeader("searchTableHeader");
            System.out.println(productSearchResult);
            System.out.println("");
            
            char confirmation;
            if (productSearchResult.getStatus().equals("ACTIVE")) {
                System.out.println("Product status: ACTIVE");
                System.out.println("");
                confirmation = General.yesNoInput("Do you want to deactivate this product? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
                if (confirmation == 'Y') {
                    //loop through the products array list and find the product to be deactivated.
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).equals(productSearchResult)) {
                            products.get(i).setStatus("INACTIVE");
                            break;
                        }
                    }
                    
                    //update the product list with the new status.
                    Product.editProductStatus(Product.fileName, products);
                    System.out.println("Product status successfully updated.");
                }
            } else {
                System.out.println("Product status: INACTIVE");
                System.out.println("");
                confirmation = General.yesNoInput("Do you want to reactivate this product? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
                if (confirmation == 'Y') {
                    //loop through the products array list and find the product to be reactivated.
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).equals(productSearchResult)) {
                            products.get(i).setStatus("ACTIVE");
                            break;
                        }
                    }
                    
                    //update the product list with the new status.
                    Product.editProductStatus(Product.fileName, products);
                    System.out.println("Product status successfully updated.");
                }
            }
        } while(searchSelection != 0);
    }
    
    //method to view products
    public static void viewProduct() {
        printHeader("viewProduct");
        
        //Read the current product records into an array list.
        ArrayList<Product> products = Product.readFile(Product.fileName);
        
        //Product array list to store the desired type (active/inactive) product to view
        ArrayList<Product> selectedProducts = new ArrayList<>();
        
        //to keep track of the number of records printed.
        int printCount;
        
        //Let user choose whether they want to view active products or inactive products.
        int selection;
        do {
            //clear the array list for subsequent loops to store the results.
            selectedProducts.clear();
            //clear the printCount for subsequent loops
            printCount = 0;
            selection = viewProductMenu();
            switch(selection) {
                case 1:
                    for (Product product: products) {
                        if (product.getStatus().equals("ACTIVE"))
                            selectedProducts.add(new Product(product));
                    }
                    break;
                case 2:
                    for (Product product: products) {
                        if (product.getStatus().equals("INACTIVE"))
                            selectedProducts.add(new Product(product));
                    }
                    break;
                case 0:
                    System.out.println("Returning to product menu...");
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-2).");
            }
            
            if (selection == 0) {
                break;
            }
            
            //print out the chosen results.
            System.out.println("Products: ");
            printHeader("searchTableHeader");
            if (!selectedProducts.isEmpty()) {
                printCount = Product.display(selectedProducts);
            } else {
                System.out.println("No product record found.");
            }
            
            System.out.println("");
            System.out.printf("< %d record(s) >\n", printCount);
            System.out.println("");
            
        } while(selection != 0);
    }
    
    //Validation for product code input.
    public static String codeInput() {
        String productCode;
        boolean validProductCode;
        do {
            validProductCode = true;
            
            productCode = General.stringNullCheckingInput("Enter product code (Eg: P0001) : ", "Empty input detected. Please ensure that you have inputted something.").toUpperCase();
            
            if (productCode.length() != 5) {
                validProductCode = false;
            } else if (productCode.charAt(0) != 'P') {
                validProductCode = false;
            } else {
                for (int i = 1; i < productCode.length(); i++) {
                    if (!Character.isDigit(productCode.charAt(i))) {
                        validProductCode = false;
                        break;
                    }
                }
            }
            
            if (validProductCode == false) {
                System.out.println("Invalid product code inputted. Please try again.");
                System.out.println("");
            }
            
        } while(validProductCode == false);
        

        return productCode;
    }
    
    //Validation for product name
    public static String nameInput() {
        //read current product list in to array list
        ArrayList<Product> products = new ArrayList<>();
        products = Product.readFile(Product.fileName);
        //Validate product name to prevent duplicate products to be created.
        String name;
        boolean validName;
        do {
            validName = true;
            name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().equals(name)) {
                    System.out.println("This product name has already existed, please try another product name.");
                    System.out.println("");
                    validName = false;
                    break;
                } 
            }

        } while (validName == false);
        
        return name;
    }
    
    //Validation for category input.
    public static String categoryInput() {
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
        return category;
    }
    
    //validation for minimum reorder quantity
    public static int minReorderQtyInput() {
        //Ask for minimum stock reorder quantity
        int minReorderQty;
        boolean validMinReorderQty;
        do {
            validMinReorderQty = false;
            minReorderQty = General.intInput("Enter minimum reorder quantity: ", "Invalid input, please ensure you have entered an integer.");
            if (minReorderQty < 0) {
                System.out.println("Invalid, you have entered a negative number, please try again.");
                System.out.println("");
            } else
                validMinReorderQty = true;
        } while(validMinReorderQty == false);
        
        return minReorderQty;
    }
    
    //Validation for current price input
    public static double currentCostPriceInput() {
        //Ask for product current cost price
        //Input validation for current cost price.
        double currentCostPrice;
        boolean validCostPrice;
        do {
            validCostPrice = false;
            currentCostPrice = General.doubleInput("Enter current cost price: ", "Invalid cost price, please try again.");
            if (currentCostPrice > 0)
                validCostPrice = true;
            else {
                System.out.println("Product current cost price cannot be a negative number. Please try again.");
                System.out.println("");
            }
                
        } while(validCostPrice == false);
        return currentCostPrice;
    }
    
    //Validation for selling price input
    public static double currentSellingPriceInput(double currentCostPrice) {
        //Ask for product current selling price
        //input validation for current selling price
        double currentSellingPrice;
        boolean validSellingPrice;
        do {
            validSellingPrice = false;
            currentSellingPrice = General.doubleInput("Enter current selling price: ", "Invalid selling price, please try again.");
            if (currentSellingPrice > currentCostPrice)
                validSellingPrice = true;
            else {
                System.out.println("Product current selling price cannot be smaller than the cost price. Please try again.");
                System.out.println("");
            }
                
        } while(validSellingPrice == false);
        return currentSellingPrice;
    }
    
    //method to print headers.
    public static void printHeader(String headerType) {
        switch(headerType) {
            case "productMenu":
                System.out.println("------------------");
                System.out.println(" | Product Menu | ");
                System.out.println("------------------");
                System.out.println("");
                break;
            case "addProduct":
                System.out.println("------------------------");
                System.out.println(" | Add new product(s) | ");
                System.out.println("------------------------");
                System.out.println("");
                break;
            case "modifyProduct":
                System.out.println("-----------------------");
                System.out.println(" | Modify product(s) | ");
                System.out.println("-----------------------");
                System.out.println("");
                break;
            case "searchProduct":
                System.out.println("-----------------------");
                System.out.println(" | Search product(s) | ");
                System.out.println("-----------------------");
                System.out.println("");
                break;
            case "editProductStatus":
                System.out.println("----------------------------");
                System.out.println(" | Edit product(s) status | ");
                System.out.println("----------------------------");
                System.out.println("");
                break;
            case "viewProduct":
                System.out.println("---------------------");
                System.out.println(" | View product(s) | ");
                System.out.println("---------------------");
                System.out.println("");
                break;
            case "searchTableHeader":
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("| Code  | Name                 | Category             | Selling price (RM) | Cost Price (RM) | Quantity | Reorder Quantity | Status   |");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
                break;
            default:
                System.out.println("Header type does not exist.");
        }
    }
    
    //Display the product menu and return selection.
    public static int productMenu() {
        printHeader("productMenu");
        System.out.println("1. Add new product(s)");
        System.out.println("2. Modify product(s)");
        System.out.println("3. Edit product(s) status");
        System.out.println("4. Search product(s)");
        System.out.println("5. View product(s)");
        System.out.println("");
        System.out.println("0. Return to main menu");
        System.out.println("");
        
        return General.intInput("Enter selection (0-5) : ", "Invalid input, please enter an integer.");
    }
    
    //Display the menu used in viewProduct method and return selection
    public static int viewProductMenu() {
        System.out.println("Which type of products you want to view?");
        System.out.println("Available choices: ");
        System.out.println("1. Active");
        System.out.println("2. Inactive");
        System.out.println("");
        System.out.println("0. Return to product menu");
        System.out.println("");

        return General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
    }
    
    //Displays the menu used in editProductStatus method and return selection
    public static int editProductStatusMenu() {
        System.out.println("Which field do you want to search by?");
        System.out.println("Available choices: ");
        System.out.println("1. Product Code");
        System.out.println("2. Product Name");
        System.out.println("");
        System.out.println("0. Return to product menu");
        System.out.println("");

        return General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
    }
    
    //Displays the search menu used in searchProduct method and returns selection.
    public static int searchMenu(String menuType) {
        int selection = 0;
        switch(menuType) {
            case "search":
                System.out.println("Which field do you want to search by?");
                System.out.println("Available choices: ");
                System.out.println("1. Product Code");
                System.out.println("2. Product Name");
                System.out.println("3. Category");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");
                
                selection = General.intInput("Enter selection (0-3) : ", "Invalid input, please enter an integer.");
                break;
            case "activeOrInactive":
                System.out.println("All, active or inactive category of products?");
                System.out.println("Available choices: ");
                System.out.println("1. All");
                System.out.println("2. Active");
                System.out.println("3. Inactive");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");
                
                selection = General.intInput("Enter selection (0-3) : ", "Invalid input, please enter an integer.");
                break;
            default:
                System.out.println("Menu type does not exist.");
        }
        
        
        return selection;
    }
    
    //Displays the menus used in modifyProduct method and returns selection
    public static int modifyMenu(String menuType) {
        int selection = 0;
        switch(menuType) {
            case "search":
                System.out.println("Which field do you want to search by?");
                System.out.println("Available choices: ");
                System.out.println("1. Product Code");
                System.out.println("2. Product Name");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");

                selection = General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
                break;
            case "modifyField":
                System.out.println("Which field do you want to modify?");
                System.out.println("Available choices: ");
                System.out.println("1. Product Name");
                System.out.println("2. Product Selling Price");
                System.out.println("3. Product Category");
                System.out.println("4. Product Reorder Quantity");
                System.out.println("");
                System.out.println("0. Return to modify product menu");
                System.out.println("");

                selection = General.intInput("Enter selection (0-4) : ", "Invalid input, please enter an integer.");
                break;
            default:
                System.out.println("Menu type does not exist.");
        }
        
        return selection;
    }
}

