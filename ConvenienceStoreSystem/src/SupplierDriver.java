
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class SupplierDriver {
    //Method to add a new product
    //Method to add a new product
    public static void addProduct() {
        
        
        int selection;
        do {
            printHeader("addSupplier");
            System.out.println("Available choices: ");
            System.out.println("1. Start adding supplier(s)");
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
        
        //Read the current product list
        ArrayList<Supplier> suppliers = Supplier.readFile(Supplier.fileName);
        
        
        //Loop for user input and adding products.
        char cont = 'Y';
        do {
            //User input

            //Set current supplier ID: get supplier ID of last element, take the back part, convert to integer, increment it, convert back to string to store. 
            String currentSupplierCode = "SP" + String.format("%04d", Integer.parseInt(suppliers.get(suppliers.size() - 1).getId().substring(1, 5)) + 1);
            
            System.out.println("Supplier code: " + currentProductCode);
            
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
    
    //method to print headers.
    public static void printHeader(String headerType) {
        switch(headerType) {
            case "productMenu":
                System.out.println("------------------");
                System.out.println(" | Product Menu | ");
                System.out.println("------------------");
                System.out.println("");
                break;
            case "addSupplier":
                System.out.println("------------------------");
                System.out.println(" | Add new supplier(s) | ");
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
//            case "searchTableHeader":
//                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
//                System.out.println("| Code  | Name                 | Category             | Selling price (RM) | Cost Price (RM) | Quantity | Reorder Quantity | Status   |");
//                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
//                break;
            default:
                System.out.println("Header type does not exist.");
        }
    }
}
