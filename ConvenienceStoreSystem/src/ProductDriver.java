import java.io.BufferedReader;
import java.io.FileReader;
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
    public static void main(String[] args, Staff staffLogin) {
        int selection;
        do {
            selection = productMenu(staffLogin);
            switch(selection) {
                case 1 -> addProduct(staffLogin);
                case 2 -> modifyProduct(staffLogin);
                case 3 -> editProductStatus(staffLogin);
                case 4 -> searchProduct(staffLogin);
                case 5 -> viewProduct(staffLogin);
                case 0 -> {
                    System.out.println("Returning to main menu...");
                    General.systemPause();
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-5).");
                    General.systemPause();
                }
            }
        } while(selection != 0);
    }
    
    
    //Method to add a new product
    public static void addProduct(Staff staffLogin) {
        //Give user a choice to return to product menu.
        int selection;
        do {
            General.clearScreen();
            printHeader("addProduct", staffLogin);
            System.out.println("Available choices: ");
            System.out.println("1. Start adding product(s)");
            System.out.println("");
            System.out.println("0. Return to product menu");
            System.out.println("");
            selection = General.intInput("Enter your selection (0-1) : ", "Invalid input, please enter an integer.");
            switch(selection) {
                case 1 -> {
                }
                case 0 -> {
                    System.out.println("Returning to product menu...");
                    General.systemPause();
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-1).");
                    General.systemPause();
                }
            }
        } while(selection != 0 && selection != 1);
        
        if (selection == 0)
            return;
        
        //Read the current product list into an array list
        ArrayList<Product> products = Product.readFile(Product.FILE_NAME);
        
        //Loop for user input and adding products.
        char cont;
        do {
            General.clearScreen();
            
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
            
            //Create product object to be added to array list. 
            //StockDetails and TransactionDetails array list is set to null because newly created product does not have any stock or is in any transaction yet.
            Product p1 = new Product(currentProductCode, name, currentSellingPrice, currentCostPrice, stockQty, minReorderQty, category, null, null, status);
            
            //confirmation of adding of product.
            char confirmation;
            confirmation = General.yesNoInput("Confirm add product? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
            if (confirmation == 'Y') {
                products.add(p1);
                System.out.println("Product added successfully.");
                //Write to file after finish adding product details.
                Product.add(Product.FILE_NAME, products);
            } else {
                System.out.println("Product is not added.");
            }

            System.out.println("");
            //Check whether user wants to continue adding new products
            cont = General.yesNoInput("Continue adding new product? (Y/N) : ", "Invalid input, please enter Y or N.");
        } while(cont == 'Y'); 
        
    }
    
    //method to search for products and print the search results.
    public static void searchProduct(Staff staffLogin) {
        //Array list to store the returned search results
        ArrayList<Product> productSearchResults;
        
        //search menu
        int printCount;
        int selection;
        do {
            General.clearScreen();
            printHeader("searchProduct", staffLogin);
            selection = searchMenu("search");
            switch(selection) {
                case 1 -> {
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    printCount = 0;
                    
                    General.clearScreen();
                    
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader", staffLogin);
                    productSearchResults = Product.search("productCode", code);
                    
                    if (productSearchResults != null) {
                        printCount = Product.display(productSearchResults);
                    } else {
                        System.out.println("Product code entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    General.systemPause();
                }
                case 2 -> {
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    printCount = 0;
                    
                    General.clearScreen();
                    
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader", staffLogin);
                    productSearchResults = Product.search("productName", name);
                    
                    if (productSearchResults != null) {
                        printCount = Product.display(productSearchResults);
                    } else {
                        System.out.println("Product name entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    General.systemPause();
                }
                case 3 -> {
                    //Ask for product category and search for the product details with the product category.
                    String category = General.stringInput("Enter category of product: ", "Invalid category name, please try again.").toUpperCase();
                    printCount = 0;
                    
                    General.clearScreen();
                    
                    productSearchResults = Product.search("productCategory", category);
                    
                    if (productSearchResults == null) {
                        System.out.println("Product category entered does not exist.");
                        General.systemPause();
                        continue;
                    }
                    
                    //Array list to store all, active or inactive products
                    ArrayList<Product> categoryProductSearchResults = new ArrayList<>();
                    //Ask whether user want to view all, active, or inactive category of products.
                    int activeOrInactiveSelection;
                    do {
                        General.clearScreen();
                        activeOrInactiveSelection = searchMenu("activeOrInactive");
                        switch(activeOrInactiveSelection) {
                            case 1 -> {
                                for (Product product: productSearchResults) {
                                    categoryProductSearchResults.add(new Product(product));
                                }
                            }
                            case 2 -> {
                                for (Product product: productSearchResults) {
                                    if (product.getStatus().equals("ACTIVE"))
                                        categoryProductSearchResults.add(new Product(product));
                                }
                            }
                            case 3 -> {
                                for (Product product: productSearchResults) {
                                    if (product.getStatus().equals("INACTIVE"))
                                        categoryProductSearchResults.add(new Product(product));
                                }
                            }
                            case 0 -> {
                                System.out.println("Returning to product menu...");
                                General.systemPause();
                            }
                            default -> {
                                System.out.println("Please ensure your selection is (0-3).");
                                General.systemPause();
                            }
                        }
                    } while(activeOrInactiveSelection != 0 && activeOrInactiveSelection != 1 && activeOrInactiveSelection != 2 && activeOrInactiveSelection != 3);
                    
                    if (activeOrInactiveSelection == 0) {
                        selection = 0;
                        break;
                    }
                    
                    General.clearScreen();
                    
                    //Print out the search results.
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader", staffLogin);
                    if (!categoryProductSearchResults.isEmpty()) {
                        printCount = Product.display(categoryProductSearchResults);
                    } else {
                        System.out.println("No product record found.");
                    }
  
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    General.systemPause();
                }
                case 0 -> {
                    System.out.println("Returning to product menu...");
                    General.systemPause();
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-3).");
                    General.systemPause();
                }
            }
            
        } while(selection != 0);
    }
    
    //method to modify product details.
    public static void modifyProduct(Staff staffLogin) {
        //Read the current product records into an array list.
        ArrayList<Product> products = Product.readFile(Product.FILE_NAME);
        
        //Product array list to store the search results
        ArrayList<Product> searchResults;
        
        //Product object to store search result.
        Product productSearchResult = new Product();
        
        //modify menu 1: search for product record to edit.
        int selection;
        do {
            General.clearScreen();
            printHeader("modifyProduct", staffLogin);
            
            selection = modifyMenu("search");
            switch(selection) {
                case 1 -> {
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    searchResults = Product.search("productCode", code);
                    //If there are search results, store the first element of the array list as a Product object. (Product code is unique)
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                }
                case 2 -> {
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    searchResults = Product.search("productName", name);
                    //If there are search results, store the first element of the array list as a Product object. (Product name is unique)
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                }
                case 0 -> {
                    System.out.println("Returning to product menu...");
                    General.systemPause();
                    continue;
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-2).");
                    General.systemPause();
                    continue;
                }
            }
            
            General.clearScreen();
            
            //if product does not exist prompt user.
            if (searchResults == null) {
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader", staffLogin);
                System.out.println("Product does not exist.");
                General.systemPause();
                continue;
            }
            
            //prevent inactive products to be modified.
            if (productSearchResult.getStatus().equals("INACTIVE")) {
                System.out.println("The product you searched for is currently inactive. Only active products can be modified.");
                General.systemPause();
                continue;
            }
            
            //modify menu 2: ask for what field to edit
            int modifyFieldSelection;
            do {
                General.clearScreen();
                //print search results
                System.out.println("Search Results: ");
                printHeader("searchTableHeader", staffLogin);
                System.out.println(productSearchResult);
                System.out.println("");
                
                modifyFieldSelection = modifyMenu("modifyField"); 
                switch(modifyFieldSelection) {
                    case 1 -> {
                        //Ask for new product name.
                        String name = nameInput();
                        productSearchResult.setName(name);
                    }
                    case 2 -> {
                        //Ask for new product selling price.
                        double currentSellingPrice = currentSellingPriceInput(productSearchResult.getCurrentCostPrice());
                        productSearchResult.setCurrentSellingPrice(currentSellingPrice);
                    }
                    case 3 -> {
                        //Ask for new category
                        String category = categoryInput();
                        productSearchResult.setCategory(category);
                    }
                    case 4 -> {
                        //Ask for new product reorder quantity.
                        int minReorderQty = minReorderQtyInput();
                        productSearchResult.setMinReorderQty(minReorderQty);
                    }
                    case 0 -> {
                        System.out.println("Returning to modify product menu...");
                        General.systemPause();
                        continue;
                    } 
                    default -> {
                        System.out.println("Please ensure your selection is (0-4).");
                        General.systemPause();
                        continue;
                    }
                }
                
                
                //ask for confirmation.
                char confirmation;
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
                    Product.modify(Product.FILE_NAME, products);
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
                
                General.systemPause();
            } while (modifyFieldSelection != 0);
        } while(selection != 0);
        
    }
    
    //method to edit the product status either become active or inactive
    public static void editProductStatus(Staff staffLogin) {
        //Read the current product list into an array list
        ArrayList<Product> products = Product.readFile(Product.FILE_NAME);
        
        //Product array list to store the search results
        ArrayList<Product> searchResults = new ArrayList<>();
        
        //Product object to store search result.
        Product productSearchResult = new Product();
        
        //search for product to activate/deactivate
        int searchSelection;
        do {
            General.clearScreen();
            printHeader("editProductStatus", staffLogin);
            
            searchSelection = editProductStatusMenu();
            switch(searchSelection) {
                case 1 -> {
                    //Ask for product code and search for the product details with the product code.
                    String code = codeInput();
                    searchResults = Product.search("productCode", code);
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                }
                case 2 -> {
                    //Ask for product name and search for the product details with the product name.
                    String name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
                    searchResults = Product.search("productName", name);
                    if (searchResults != null)
                        productSearchResult = searchResults.get(0);
                }
                case 0 -> {
                    System.out.println("Returning to product menu...");
                    General.systemPause();
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-2).");
                    General.systemPause();
                    continue;
                }
            }
            
            if (searchSelection == 0)
                break;
            
            General.clearScreen();
            
            //if product does not exist prompt user.
            if (searchResults == null) {
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader", staffLogin);
                System.out.println("Product does not exist.");
                General.systemPause();
                continue;
            }
            
            //print search results
            System.out.println("");
            System.out.println("Search Results: ");
            printHeader("searchTableHeader", staffLogin);
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
                    Product.editProductStatus(Product.FILE_NAME, products);
                    System.out.println("Product status successfully updated.");
                } else {
                    System.out.println("Product status not updated.");
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
                    Product.editProductStatus(Product.FILE_NAME, products);
                    System.out.println("Product status successfully updated.");
                } else {
                    System.out.println("Product status not updated.");
                }
            }
            
            General.systemPause();
        } while(searchSelection != 0);
    }
    
    //method to view products
    public static void viewProduct(Staff staffLogin) {
        
        
        //Read the current product records into an array list.
        ArrayList<Product> products = Product.readFile(Product.FILE_NAME);
        
        //Product array list to store the desired type (active/inactive) product to view
        ArrayList<Product> selectedProducts = new ArrayList<>();
        
        //to keep track of the number of records printed.
        int printCount;
        
        //Let user choose whether they want to view active products or inactive products.
        int selection;
        do {
            General.clearScreen();
            printHeader("viewProduct", staffLogin);
            
            //clear the array list for subsequent loops to store the results.
            selectedProducts.clear();
            //clear the printCount for subsequent loops
            printCount = 0;
            selection = viewProductMenu();
            switch(selection) {
                case 1 -> {
                    for (Product product: products) {
                        if (product.getStatus().equals("ACTIVE"))
                            selectedProducts.add(new Product(product));
                    }
                }
                case 2 -> {
                    for (Product product: products) {
                        if (product.getStatus().equals("INACTIVE"))
                            selectedProducts.add(new Product(product));
                    }
                }
                case 0 -> {
                    System.out.println("Returning to product menu...");
                    General.systemPause();
                }
                default -> {
                    System.out.println("Please ensure your selection is (0-2).");
                    General.systemPause();
                    continue;
                }
            }
            
            if (selection == 0) {
                break;
            }
            
            General.clearScreen();
            
            //print out the chosen results.
            System.out.println("Products: ");
            printHeader("searchTableHeader", staffLogin);
            if (!selectedProducts.isEmpty()) {
                printCount = Product.display(selectedProducts);
            } else {
                System.out.println("No product record found.");
            }
            
            System.out.println("");
            System.out.printf("< %d record(s) >\n", printCount);
            System.out.println("");
            
            General.systemPause();
            
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
                General.systemPause();
                System.out.println("");
            }
            
        } while(validProductCode == false);
        

        return productCode;
    }
    
    //Validation for product name
    public static String nameInput() {
        //read current product list in to array list
        ArrayList<Product> products = Product.readFile(Product.FILE_NAME);

        //Validate product name to prevent duplicate products to be created.
        String name;
        boolean validName;
        do {
            validName = true;
            name = General.stringInput("Enter product name: ", "Invalid product name, please try again").toUpperCase();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().equals(name)) {
                    System.out.println("This product name has already existed, please try another product name.");
                    General.systemPause();
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
            General.clearScreen();
            
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
                General.systemPause();
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
                General.systemPause();
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
                General.systemPause();
                System.out.println("");
            }
                
        } while(validSellingPrice == false);
        return currentSellingPrice;
    }
    
    //method to print headers.
    public static void printHeader(String headerType, Staff staffLogin) {
        switch(headerType) {
            case "productMenu" -> {
                System.out.println("------------------");
                System.out.println(" | Product Menu | ");
                System.out.println("------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "addProduct" -> {
                System.out.println("------------------------");
                System.out.println(" | Add new product(s) | ");
                System.out.println("------------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "modifyProduct" -> {
                System.out.println("-----------------------");
                System.out.println(" | Modify product(s) | ");
                System.out.println("-----------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "searchProduct" -> {
                System.out.println("-----------------------");
                System.out.println(" | Search product(s) | ");
                System.out.println("-----------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "editProductStatus" -> {
                System.out.println("----------------------------");
                System.out.println(" | Edit product(s) status | ");
                System.out.println("----------------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "viewProduct" -> {
                System.out.println("---------------------");
                System.out.println(" | View product(s) | ");
                System.out.println("---------------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');        //add login name and position
                System.out.println("");
            }
            case "searchTableHeader" -> {
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("| Code  | Name                 | Category             | Selling price (RM) | Cost Price (RM) | Quantity | Reorder Quantity | Status   |");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            }
            default -> System.out.println("Header type does not exist.");
        }
    }
    
    //Display the product menu and return selection.
    public static int productMenu(Staff staffLogin) {
        General.clearScreen();
        printHeader("productMenu", staffLogin);
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
            case "search" -> {
                System.out.println("Which field do you want to search by?");
                System.out.println("Available choices: ");
                System.out.println("1. Product Code");
                System.out.println("2. Product Name");
                System.out.println("3. Category");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");
                
                selection = General.intInput("Enter selection (0-3) : ", "Invalid input, please enter an integer.");
            }
            case "activeOrInactive" -> {
                System.out.println("All, active or inactive category of products?");
                System.out.println("Available choices: ");
                System.out.println("1. All");
                System.out.println("2. Active");
                System.out.println("3. Inactive");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");
                
                selection = General.intInput("Enter selection (0-3) : ", "Invalid input, please enter an integer.");
            }
            default -> System.out.println("Menu type does not exist.");
        }
        
        
        return selection;
    }
    
    //Displays the menus used in modifyProduct method and returns selection
    public static int modifyMenu(String menuType) {
        int selection = 0;
        switch(menuType) {
            case "search" -> {
                System.out.println("Which field do you want to search by?");
                System.out.println("Available choices: ");
                System.out.println("1. Product Code");
                System.out.println("2. Product Name");
                System.out.println("");
                System.out.println("0. Return to product menu");
                System.out.println("");

                selection = General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
            }
            case "modifyField" -> {
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
            }
            default -> System.out.println("Menu type does not exist.");
        }
        
        return selection;
    }
}

