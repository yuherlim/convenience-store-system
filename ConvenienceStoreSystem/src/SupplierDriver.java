
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class SupplierDriver {
    
    public static void main(String[] args) {
        int selection;
        do {
            selection = supplierMenu();
            switch(selection) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    modifySupplier();
                    break;
                case 3:
                    searchSupplier();
                    break;
                case 4:
                    viewSupplier();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    General.systemPause();
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-4).");
                    General.systemPause();
            }
        } while(selection != 0);
    }
    
    //Method to add a new suplier
    public static void addSupplier() {
        //Give user a choice to return to supplier menu.
        int selection;
        do {
            General.clearScreen();
            printHeader("addSupplier");
            System.out.println("Available choices: ");
            System.out.println("1. Start adding supplier(s)");
            System.out.println("");
            System.out.println("0. Return to supplier menu");
            System.out.println("");
            selection = General.intInput("Enter your selection (0-1) : ", "Invalid input, please enter an integer.");
            switch(selection) {
                case 1:
                    break;
                case 0:
                    System.out.println("Returning to supplier menu...");
                    General.systemPause();
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-1).");
                    General.systemPause();
            }
        } while(selection != 0 && selection != 1);
        
        if (selection == 0)
            return;
        
        //Read the current supplier list into an array list
        ArrayList<Supplier> suppliers = Supplier.readFile(Supplier.fileName);
        
        
        //Loop for user input and adding suppliers.
        char cont = 'Y';
        do {
            General.clearScreen();
            
            //User input

            //Set current supplier ID: get supplier ID of last element, take the back part, convert to integer, increment it, convert back to string to store. 
            String currentSupplierId = "SP" + String.format("%04d", Integer.parseInt(suppliers.get(suppliers.size() - 1).getId().substring(2, 6)) + 1);
            
            System.out.println("Supplier code: " + currentSupplierId);
            
            System.out.println("");
            
            //Ask for supplier name
            String name = nameInput();
            
            System.out.println("");
            
            //Ask for phone number
            String phoneNo = General.phoneInput("Enter phone number : ");
            
            System.out.println("");
            
            //Ask for email
            String email = emailInput();
            
            System.out.println("");
            
            //Ask for address
            String address = General.stringNullCheckingInput("Enter address: ", "Empty input detected, please try again.").toUpperCase();
            
            System.out.println("");
            
            //create supplier object
            Supplier sp = new Supplier(currentSupplierId, name, phoneNo, email, address);
            
            //confirmation of adding of supplier.
            char confirmation = 'N';
            confirmation = General.yesNoInput("Confirm add supplier? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
            if (confirmation == 'Y') {
                suppliers.add(sp);
                System.out.println("Supplier added successfully.");
                //Write to file after finish adding supplier details.
                Supplier.add(Supplier.fileName, suppliers);
            } else {
                System.out.println("Supplier is not added.");
            }
            
            System.out.println("");
            
            //Check whether user wants to continue adding new suppliers
            cont = General.yesNoInput("Continue adding new supplier? (Y/N) : ", "Invalid input, please enter Y or N.");
        } while(cont == 'Y'); 
        
    }
    
    //method to modify supplier details.
    public static void modifySupplier() {
        //Read the current supplier records into an array list.
        ArrayList<Supplier> suppliers = Supplier.readFile(Supplier.fileName);
        
        //Supplier object to store search result.
        Supplier supplierSearchResult;
        
        //modify menu 1: search for supplier record to edit.
        int selection;
        do {
            General.clearScreen();
            
            printHeader("modifySupplier");
            
            selection = modifyMenu("search");
            switch(selection) {
                case 1:
                    //Ask for supplier ID and search for the supplier details with the supplier ID.
                    String supplierId = idInput();
                    supplierSearchResult = Supplier.search("supplierId", supplierId);
                    break;
                case 2:
                    //Ask for supplier name and search for the supplier details with the supplier name.
                    String name = General.stringInput("Enter supplier name: ", "Invalid supplier name, please try again").toUpperCase();
                    supplierSearchResult = Supplier.search("supplierName", name);
                    break;
                case 0:
                    System.out.println("Returning to supplier menu...");
                    General.systemPause();
                    continue;
                default:
                    System.out.println("Please ensure your selection is (0-2).");
                    General.systemPause();
                    continue;
            }
            
            General.clearScreen();
            
            //if supplier does not exist prompt user.
            if (supplierSearchResult == null) {
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader");
                System.out.println("Supplier does not exist.");
                General.systemPause();
                continue;
            }
            
            //modify menu 2: ask for what field to edit
            int modifyFieldSelection;
            do {
                General.clearScreen();
                
                //print search results
                System.out.println("");
                System.out.println("Search Results: ");
                printHeader("searchTableHeader");
                System.out.println(supplierSearchResult);
                System.out.println("");
                
                modifyFieldSelection = modifyMenu("modifyField"); 
                switch(modifyFieldSelection) {
                    case 1:
                        //Ask for new supplier name.
                        String name = nameInput();
                        supplierSearchResult.setName(name);
                        break;
                    case 2:
                        //Ask for new phone number.
                        String phoneNo = General.phoneInput("Enter phone number : ");
                        supplierSearchResult.setPhoneNo(phoneNo);
                        break;
                    case 3:
                        //Ask for new email
                        String email = emailInput();
                        supplierSearchResult.setEmail(email);
                        break;
                    case 4:
                        //Ask for new address.
                        String address = General.stringNullCheckingInput("Enter address: ", "Empty input detected, please try again.").toUpperCase();
                        supplierSearchResult.setAddress(address);
                        break;
                    case 0:
                        System.out.println("Returning to modify supplier menu...");
                        General.systemPause();
                        continue;
                    default: 
                        System.out.println("Please ensure your selection is (0-4).");
                        General.systemPause();
                        continue;
                }
                
                
                //ask for confirmation.
                char confirmation = 'N';
                confirmation = General.yesNoInput("Confirm modify supplier details? (Y)es/(N)o : ", "Invalid input, please enter Y or N.");
                
                if (confirmation == 'Y') {
                    //loop through the suppliers array list and find the supplier to be edited.
                    for (int i = 0; i < suppliers.size(); i++) {
                        if (suppliers.get(i).equals(supplierSearchResult)) {
                            suppliers.set(i, new Supplier(supplierSearchResult));
                            break;
                        }
                    }
                    
                    //modify the supplier list with the edited details.
                    Supplier.modify(Supplier.fileName, suppliers);
                    System.out.println("Supplier details successfully modified.");
                } else {
                    //loop through the suppliers array list and find the supplier that was not edited.
                    //revert the supplierSearchResult
                    for (int i = 0; i < suppliers.size(); i++) {
                        if (suppliers.get(i).equals(supplierSearchResult)) {
                            supplierSearchResult = new Supplier(suppliers.get(i));
                            break;
                        }
                    }
                    System.out.println("Supplier details is not modified.");
                }
                
                General.systemPause();
            } while (modifyFieldSelection != 0);
        } while(selection != 0);
        
    }
    
    //method to search for suppliers and print the search results.
    public static void searchSupplier() {
        //object to store the returned search results
        Supplier supplierSearchResult;
        //Array list to store search results to be printed
        ArrayList<Supplier> searchResultList = new ArrayList<>();
        
        //search menu
        int printCount;
        int selection;
        do {
            General.clearScreen();
            printHeader("searchSupplier");
            
            //clear the searchResultList for subsequent loops
            searchResultList.clear();
            selection = searchMenu();
            switch(selection) {
                case 1:
                    //Ask for supplier ID and search for the supplier details with the supplier ID.
                    String supplierId = idInput();
                    printCount = 0;
                    
                    General.clearScreen();
                    
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader");
                    supplierSearchResult = Supplier.search("supplierId", supplierId);
                    
                    if (supplierSearchResult != null) {
                        //Add the search result to a supplier array list to be printed using the supplier display method.
                        searchResultList.add(supplierSearchResult);
                        printCount = Supplier.display(searchResultList);
                    } else {
                        System.out.println("Supplier ID entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    General.systemPause();
                    break;
                case 2:
                    //Ask for supplier name and search for the supplier details with the supplier name.
                    String name = General.stringInput("Enter supplier name: ", "Invalid supplier name, please try again").toUpperCase();
                    printCount = 0;
                    
                    General.clearScreen();
                    
                    System.out.println("Search results: ");
                    printHeader("searchTableHeader");
                    supplierSearchResult = Supplier.search("supplierName", name);
                    
                    if (supplierSearchResult != null) {
                        //Add the search result to a supplier array list to be printed using the supplier display method.
                        searchResultList.add(supplierSearchResult);
                        printCount = Supplier.display(searchResultList);
                    } else {
                        System.out.println("Supplier name entered does not exist.");
                    }
                    
                    System.out.println("");
                    System.out.printf("< %d record(s) >\n", printCount);
                    System.out.println("");
                    General.systemPause();
                    break;
                case 0:
                    System.out.println("Returning to supplier menu...");
                    General.systemPause();
                    break;
                default:
                    System.out.println("Please ensure your selection is (0-2).");
                    General.systemPause();
            }
            
        } while(selection != 0);
    }
    
    //method to view suppliers
    public static void viewSupplier() {
        General.clearScreen();
        
        printHeader("viewSupplier");
        
        //Read the current supplier records into an array list.
        ArrayList<Supplier> suppliers = Supplier.readFile(Supplier.fileName);
        
        //to keep track of the number of records printed.
        int printCount = 0;
        
        //print out all the suppliers
        System.out.println("Suppliers: ");
        printHeader("searchTableHeader");
        
        if (!suppliers.isEmpty()) {
            printCount = Supplier.display(suppliers);
        } else {
            System.out.println("No supplier record found.");
        }
        
        System.out.println("");
        System.out.printf("< %d record(s) >\n", printCount);
        System.out.println("");
        
        General.systemPause();
    }
    
    //method to print headers.
    public static void printHeader(String headerType) {
        switch(headerType) {
            case "supplierMenu":
                System.out.println("-------------------");
                System.out.println(" | Supplier Menu | ");
                System.out.println("-------------------");
                System.out.println("");
                break;
            case "addSupplier":
                System.out.println("-------------------------");
                System.out.println(" | Add new supplier(s) | ");
                System.out.println("-------------------------");
                System.out.println("");
                break;
            case "modifySupplier":
                System.out.println("------------------------");
                System.out.println(" | Modify supplier(s) | ");
                System.out.println("------------------------");
                System.out.println("");
                break;
            case "searchSupplier":
                System.out.println("------------------------");
                System.out.println(" | Search supplier(s) | ");
                System.out.println("------------------------");
                System.out.println("");
                break;
            case "viewSupplier":
                System.out.println("----------------------");
                System.out.println(" | View supplier(s) | ");
                System.out.println("----------------------");
                System.out.println("");
                break;
            case "searchTableHeader":
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("| Code   | Name                      | Phone number | Email                                    | Address                                                                |");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                break;
            default:
                System.out.println("Header type does not exist.");
        }
    }
    
    //Validation for supplier name
    public static String nameInput() {
        //read current supplier list in to array list
        ArrayList<Supplier> suppliers = Supplier.readFile(Supplier.fileName);
        
        //Validate supplier name to prevent duplicate suppliers to be created.
        String name;
        boolean validName;
        do {
            validName = true;
            name = General.stringNullCheckingInput("Enter supplier name: ", "Invalid supplier name, please try again").toUpperCase();
            for (int i = 0; i < suppliers.size(); i++) {
                if (suppliers.get(i).getName().equals(name)) {
                    System.out.println("This supplier name has already existed, please try another supplier name.");
                    System.out.println("");
                    validName = false;
                    break;
                } 
            }

        } while (validName == false);
        
        return name;
    }
    
    //Validation for email input.
    public static String emailInput() {
        //Regular Expression   
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";  
        //Compile regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regex);  
        
        String email;
        boolean validEmail;
        do {
            validEmail = false;
            email = General.stringNullCheckingInput("Enter email : ", "Empty input detected, please try again.");
            //Create instance of matcher 
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                validEmail = true;
            }
            if (!validEmail) {
                System.out.println("Invalid email inputted, please try again.");
                System.out.println("");
            }
            
        } while(validEmail == false);
        
        return email;
    }
    
    //Validation for supplier id.
    public static String idInput() {
        String supplierId;
        boolean validSupplierId;
        do {
            validSupplierId = true;
            
            supplierId = General.stringNullCheckingInput("Enter supplier ID (Eg: SP0001) : ", "Empty input detected. Please ensure that you have inputted something.").toUpperCase();
            
            if (supplierId.length() != 6) {
                validSupplierId = false;
            } else if (supplierId.charAt(0) != 'S' && supplierId.charAt(1) != 'P') {
                validSupplierId = false;
            } else {
                for (int i = 2; i < supplierId.length(); i++) {
                    if (!Character.isDigit(supplierId.charAt(i))) {
                        validSupplierId = false;
                        break;
                    }
                }
            }
            
            if (validSupplierId == false) {
                System.out.println("Invalid supplier ID inputted. Please try again.");
                System.out.println("");
            }
            
        } while(validSupplierId == false);
        
        return supplierId;
    }
    
    //Display the supplier menu and return selection.
    public static int supplierMenu() {
        General.clearScreen();
        printHeader("supplierMenu");
        System.out.println("1. Add new supplier(s)");
        System.out.println("2. Modify supplier(s)");
        System.out.println("3. Search supplier(s)");
        System.out.println("4. View supplier(s)");
        System.out.println("");
        System.out.println("0. Return to main menu");
        System.out.println("");
        
        return General.intInput("Enter selection (0-4) : ", "Invalid input, please enter an integer.");
    }
    
    //Displays the search menu used in searchSupplier method and returns selection.
    public static int searchMenu() {
        System.out.println("Which field do you want to search by?");
        System.out.println("Available choices: ");
        System.out.println("1. Supplier ID");
        System.out.println("2. Supplier Name");
        System.out.println("");
        System.out.println("0. Return to product menu");
        System.out.println("");

        return General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
    }
    
    //Displays the menus used in modifySupplier method and returns selection
    public static int modifyMenu(String menuType) {
        int selection = 0;
        switch(menuType) {
            case "search":
                System.out.println("Which field do you want to search by?");
                System.out.println("Available choices: ");
                System.out.println("1. Supplier ID");
                System.out.println("2. Supplier Name");
                System.out.println("");
                System.out.println("0. Return to supplier menu");
                System.out.println("");

                selection = General.intInput("Enter selection (0-2) : ", "Invalid input, please enter an integer.");
                break;
            case "modifyField":
                System.out.println("Which field do you want to modify?");
                System.out.println("Available choices: ");
                System.out.println("1. Supplier Name");
                System.out.println("2. Phone Number");
                System.out.println("3. Email");
                System.out.println("4. Address");
                System.out.println("");
                System.out.println("0. Return to modify supplier menu");
                System.out.println("");

                selection = General.intInput("Enter selection (0-4) : ", "Invalid input, please enter an integer.");
                break;
            default:
                System.out.println("Menu type does not exist.");
        }
        
        return selection;
    }
}
