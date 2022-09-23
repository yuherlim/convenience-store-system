
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierInvoiceDriver {

    public static void main(String[] args) {
        
        int selection;

        do {
            //Suplier Invoice Menu
            System.out.println("==================");
            System.out.println(" Supplier Invoice ");
            System.out.println("==================");
            System.out.println("1 - Add new Invoice");
            System.out.println("2 - Edit Invoice");
            System.out.println("3 - Search Invoice");
            System.out.println("4 - Cancel Invoice" + '\n');
            System.out.println("0 - Back to Inventory Menu" + '\n');

            //get and validate input
            selection = General.intInput("Enter your selection (0-4): ", "  Please input a number only.");

            switch (selection) {
                case 1 -> {
                    General.clearScreen();
                    SupplierInvoiceDriver.addInvoice();
                }
                case 2 -> {
                    General.clearScreen();
                    SupplierInvoiceDriver.editInvoice();
                }
                case 3 -> {
                    General.clearScreen();
                    SupplierInvoiceDriver.searchInvoice();
                }
                case 4 -> {
                    General.clearScreen();
                    SupplierInvoiceDriver.cancelInvoice();
                }
                case 0 -> {
                    General.clearScreen();
                    Inventory.main(args);
                }
                default ->
                    System.out.println("Invalid input! Please input 0-4 only." + '\n');
            }
        } while (selection != 0);

    }

    //Add new Invoice function / stock in
    public static void addInvoice() {
        Scanner sc = new Scanner(System.in);
        char cont;
        char confirm;

        String staffName;
        String supplierName;
        String productCode;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            ArrayList<StockDetails> newStockDetails = new ArrayList<>();

            //Read and store into ArrayList
            supplierInvoice = SupplierInvoice.readFile(SupplierInvoice.FILE_NAME, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            //Set the num of invoice as arraylist size
            SupplierInvoice.setNumOfInv(supplierInvoice.size());

            General.clearScreen();
            System.out.println("-------------------");
            System.out.println("| Add New Invoice |");
            System.out.println("-------------------");

            //Set the invNo. = (num of inv) + 1
            si.setInvNo(String.format("INV%04d", SupplierInvoice.getNumOfInv() + 1));
            System.out.println("Invoice No.: " + si.getInvNo());

            //get and validate date input
            si.setInvDate(General.dateInput("Enter invoice date: ", "  Invalid date! Please try again.."));

            //get and compare staffname from Staff class
            do {
                staffName = General.stringNullCheckingInput("Enter staff name: ", "  Input field cannot be empty, please enter again.");
                if (Staff.searchAllStaff(staffName, "Name").getName().equals(staffName)) {
                    si.setStaffName(staffName);
                } else {
                    System.out.println("  Invalid staff name! Please try again..");
                }
            } while (!Staff.searchAllStaff(staffName, "Name").getName().equals(staffName));

            //get and compare supplierName from Supplier class
            do {
                supplierName = General.stringNullCheckingInput("Enter supplier name: ", "  Input field cannot be empty, please enter again.");
                if (Supplier.search("supplierName", supplierName.toUpperCase()) != null) {
                    si.setSupplierName(supplierName);
                } else {
                    System.out.println("  Invalid supplier name! Please try again..");
                }
            } while (Supplier.search("supplierName", supplierName.toUpperCase()) == null);

            //get item list of the invoice
            do {
                StockDetails sd = new StockDetails();
                ArrayList<Product> productSearchResult;     
                do {
                    productCode = ProductDriver.codeInput();
                    productSearchResult = Product.search("productCode", productCode);
                    if (productSearchResult != null) {
                        if (productSearchResult.get(0).getStatus().equals("ACTIVE"))
                            sd.setProductCode(productCode);
                        else
                            productSearchResult = null;
                    }
                } while (productSearchResult == null);
                
//                do {
//                    productCode = ProductDriver.codeInput();
//                    if (Product.search("productCode", productCode) != null) {
//                        sd.setProductCode(productCode);
//                    }
//                } while (Product.search("productCode", productCode) == null);

                System.out.print("Enter the quantity: ");
                sd.setQty(sc.nextInt());

                System.out.print("Enter the cost price: ");
                sd.setCostPrice(sc.nextDouble());

                //set invoice no. in StockDetails for reference
                sd.setInvNo(si.getInvNo());

                //add stock details to stockDetails arrayList
                newStockDetails.add(sd);

                cont = General.yesNoInput("Continue to add item? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

            } while (cont == 'Y');

            //display the whole invoice details
            //set the total amount that return
            si.setAmount(SupplierInvoice.printInvoice(si, newStockDetails));

            //set the tag to 'Valid'
            si.setTag("Valid");

            confirm = General.yesNoInput("Confirm add this invoice? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                //add new supplier invoice details to supplierInvoice arrayList
                supplierInvoice.add(si);

                //add new stock details to stockDetails arrayList
                for (int i = 0; i < newStockDetails.size(); i++) {
                    stockDetails.add(newStockDetails.get(i));
                }

                //Updated the file information
                SupplierInvoice.writeFile(SupplierInvoice.FILE_NAME, supplierInvoice);
                StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);
                Product.updateProduct();
            }

            cont = General.yesNoInput("Continue add again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y' || cont == 'y');
        
        General.clearScreen();
    }

    //METHOD OVERLOADING
    //Search Function
    public static void searchInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont;

        do {
            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            supplierInvoice = SupplierInvoice.readFile(SupplierInvoice.FILE_NAME, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("------------------");
                System.out.println("| Search Invoice |");
                System.out.println("------------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

            } while (searchInvoice(supplierInvoice, invNo, stockDetails) == -1);

            cont = General.yesNoInput("Continue search again? (Y/N) > ", "  Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');

        General.clearScreen();
    }
    
    //Search with parameter and will return the index of invoice that in ArrayList found
    public static int searchInvoice(ArrayList<SupplierInvoice> supplierInvoice, String invNo, ArrayList<StockDetails> stockDetails) {

        int index;
        int invalid = 0;

        //loop for find the same inv no.
        for (int i = 0; i < supplierInvoice.size(); i++) {
            //if same, then print out result
            if (supplierInvoice.get(i).getInvNo().equals(invNo)) {
                invalid = SupplierInvoice.printInvoice(supplierInvoice, i, stockDetails);
                if (invalid > 0) {
                    return index = -2;
                }
                return index = i;
            }
        }

        System.out.println("Invalid invoice number. Please try again...");
        General.systemPause();
        
        return index = -1;
    }

    //Edit Invoice function, edit specify field in the invoice
    public static void editInvoice() {
        Scanner sc = new Scanner(System.in);

        String invNo;
        String input;
        char cont;
        char confirm;
        int selection;
        int index;

        double totalAmount;

        do {
            //Create an object to hold the current invoice
            SupplierInvoice si = new SupplierInvoice();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            supplierInvoice = SupplierInvoice.readFile(SupplierInvoice.FILE_NAME, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("----------------");
                System.out.println("| Edit Invoice |");
                System.out.println("----------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

                //search and display the invoice result
                index = searchInvoice(supplierInvoice, invNo, stockDetails);

                if (index == -2) {
                    break;
                }
                
                //store the result into an object
                si = supplierInvoice.get(index);

            } while (index == -1);

            if (index == -2) {
                break;
            }

            do {
                System.out.println('\n');
                System.out.println("Editable Field: ");
                System.out.println("1. Invoice Date" + '\n' + "2. Staff Incharge" + '\n' + "3. Supplier Name"
                        + '\n' + "4. Item Details (Item code/quantity/cost price)");

                selection = General.intInput("Select a field to edit (1-4): ", "  Invalid Input! Please enter number only.");

                switch (selection) {
                    case 1 -> {
                        input = General.dateInput("Enter invoice date(DD/MM/YYYY): ", "  Please enter date with format DD/MM/YYYY");
                        confirm = General.yesNoInput("Confirm to change? (Y/N) > ", "  Please enter 'Y' or 'N' only.");
                        if (confirm == 'Y') {
                            si.setInvDate(input);
                        }
                        break;
                    }
                    case 2 -> {
                        do {
                            input = General.stringNullCheckingInput("Enter a new staff name: ", "  Input field cannot be empty, please enter again.");
                            if (Staff.searchAllStaff(input, "Name").getName().equals(input)) {
                                confirm = General.yesNoInput("Confirm to change? (Y/N) > ", "  Please enter 'Y' or 'N' only.");
                                if (confirm == 'Y') {
                                    si.setStaffName(input);
                                }
                            } else {
                                System.out.println("  Invalid staff name! Please try again..");
                            }
                        } while (!Staff.searchAllStaff(input, "Name").getName().equals(input));
                        break;
                    }
                    case 3 -> {
                        do {
                            input = General.stringNullCheckingInput("Enter a new supplier name: ", "  Input field cannot be empty, please enter again.");
                            if (Supplier.search(input, "supplierName") != null) {
                                confirm = General.yesNoInput("Confirm to change? (Y/N) > ", "  Please enter 'Y' or 'N' only.");
                                if (confirm == 'Y') {
                                    si.setSupplierName(input);
                                }
                            } else {
                                System.out.println("  Invalid supplier name! Please try again..");
                            }
                        } while (Supplier.search(input, "supplierName") == null);
                        break;
                    }
                    case 4 -> {
                        totalAmount = editInvoiceItem(supplierInvoice, si, index);
                        si.setAmount(totalAmount);
                        break;
                    }
                    default -> {
                        if (selection < 1 || selection > 6) {
                            System.out.println("  Invalid selection! Please enter number 1-4 only.");
                            General.systemPause();
                            searchInvoice(supplierInvoice, invNo, stockDetails);
                        }
                    }
                }
            } while (selection < 1 || selection > 6);

            if (index == -2) {
                break;
            }

            //Update Supplier Invoice
            supplierInvoice.set(index, si);
            SupplierInvoice.writeFile(SupplierInvoice.FILE_NAME, supplierInvoice);

            cont = General.yesNoInput("Continue edit again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');
        
        General.clearScreen();
    }

    //Edit specify field of item list in the invoice
    public static double editInvoiceItem(ArrayList<SupplierInvoice> supplierInvoice, SupplierInvoice si, int index) {
        String productCode;
        double costPrice;
        int quantity;

        double subtotal;
        double totalAmount = 0d;
        int num = 0;
        int item;
        int field;
        char confirm;

        //Create empty arrayList and use it to store value after read file
        ArrayList<StockDetails> stockDetails;
        stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

        //Used to store the stock details with the current invoice no.
        ArrayList<StockDetails> currentInvoiceStockDetails = new ArrayList<>();

        //Create an object to hold current item details
        StockDetails sd;

        //Array to store the index of stockDetails
        int indexNum = 0;
        int[] stockDetailsIndex = new int[stockDetails.size()];

        General.clearScreen();
        System.out.println("<Edit Invoice Item>");

        //Loop for compare the same invNo.
        for (int i = 0; i < stockDetails.size(); i++) {
            if (supplierInvoice.get(index).getInvNo().equals(stockDetails.get(i).getInvNo())) {
                //store the stock details with the current invoice number.
                currentInvoiceStockDetails.add(stockDetails.get(i));
                //store stockDetails index
                stockDetailsIndex[indexNum] = i;
                indexNum++;
            }
        }

        System.out.println("    ---------------------------------------------------------------------");
        System.out.println("    Item               Cost Price          Qty                 Total(RM)");
        System.out.println("    ---------------------------------------------------------------------");

        //Loop for display stock details
        for (int i = 0; i < currentInvoiceStockDetails.size(); i++) {
            subtotal = currentInvoiceStockDetails.get(i).getCostPrice() * currentInvoiceStockDetails.get(i).getQty();
            num++;
            System.out.printf("%d  %s                %.2f                  %d                   %8.2f\n",
                    num, currentInvoiceStockDetails.get(i).getProductCode(), currentInvoiceStockDetails.get(i).getCostPrice(), currentInvoiceStockDetails.get(i).getQty(), subtotal);
        }

        do {
            System.out.println();
            item = General.intInput("Which item did you want to edit? : ", "  Please input a number.");
            if (item < num || item > num) {
                System.out.printf("Please input 1-%d : ", num);
            }
        } while (item < num || item > num);

        --item;

        sd = currentInvoiceStockDetails.get(item);

        do {
            System.out.println();
            System.out.println("1. Product Code" + '\n' + "2. Cost Price" + '\n' + "3. Quantity");
            field = General.intInput("Please select a field to edit the item (1-3): ", "  Please input a number.");

            switch (field) {
                case 1 -> {
                    do {
                        productCode = ProductDriver.codeInput();
                        if (Product.search("productCode", productCode) != null) {
                            confirm = General.yesNoInput("Confirm change? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");
                            if (confirm == 'Y') {
                                sd.setProductCode(productCode);
                                currentInvoiceStockDetails.set(item, sd);
                            }
                        }
                    } while (Product.search("productCode", productCode) == null);
                    break;
                }
                case 2 -> {
                    costPrice = General.doubleInput("Enter cost price: ", "  Please enter a value with decimal place");
                    confirm = General.yesNoInput("Confirm change? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");
                    if (confirm == 'Y') {
                        sd.setCostPrice(costPrice);
                        currentInvoiceStockDetails.set(item, sd);
                    }
                    break;
                }
                case 3 -> {
                    quantity = General.intInput("Enter quantity: ", "Please enter a integer quantity");
                    confirm = General.yesNoInput("Confirm change? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");
                    if (confirm == 'Y') {
                        sd.setQty(quantity);
                        currentInvoiceStockDetails.set(item, sd);
                    }
                    break;
                }
                default -> {
                    if (field < 1 || field > 3) {
                        System.out.println("Please input 1-3 : ");
                    }
                }
            }
        } while (field < 1 || field > 3);
        
        //loop the arrayList and replace the specify element
        for (int i = 0; i < stockDetails.size(); i++) {
            if(stockDetails.get(i).equals(sd)){
                stockDetails.set(i, sd);

            }
        }

        //Update Stock Details
        StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);

        //Update product
        Product.updateProduct();

        //calculate totalAmount and return back
        for (int i = 0; i < currentInvoiceStockDetails.size(); i++) {
            subtotal = currentInvoiceStockDetails.get(i).getCostPrice() * currentInvoiceStockDetails.get(i).getQty();
            totalAmount += subtotal;
        }
        
        return totalAmount;
    }

    //Delete Invoice function, set the tag from 'Valid' to 'Invalid' to indicate the invoice has been cancelled
    public static void cancelInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont;
        char confirm;
        int index = 0;

        do {
            //Create object to hold current invoice
            SupplierInvoice si;

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read and store in arrayList
            supplierInvoice = SupplierInvoice.readFile(SupplierInvoice.FILE_NAME, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("------------------");
                System.out.println("| Cancel Invoice |");
                System.out.println("------------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

                index = searchInvoice(supplierInvoice, invNo, stockDetails);

                if (index == -2) {
                    break;
                }

            } while (index == -1);

            if (index == -2) {
                break;
            }

            si = supplierInvoice.get(index);

            confirm = General.yesNoInput("Confirm to cancel this invoice? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                si.setTag("Invalid");
                supplierInvoice.set(index, si);
                //Loop for compare the same invNo.
                for (int i = 0; i < stockDetails.size(); i++) {
                    if (supplierInvoice.get(index).getInvNo().equals(stockDetails.get(i).getInvNo())) {
                        //remove the stock details related to this invoice
                        stockDetails.remove(i);
                    }
                }

                //Update the file
                StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);
                SupplierInvoice.writeFile(SupplierInvoice.FILE_NAME, supplierInvoice);

                //Update product info
                Product.updateProduct();

                System.out.println("Invoice cancelled successfully.");

            } else if (confirm == 'N') {
                System.out.println("Process cancelled...");
            }

            cont = General.yesNoInput("Continue to cancel another invoice? (Y/N) > ", " Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');
        
        General.clearScreen();
    }

}
