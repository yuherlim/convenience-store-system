
import java.util.ArrayList;
import java.util.Scanner;

public class CreditNoteDriver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection;

        do {
            //Suplier Invoice Menu
            System.out.println("=============");
            System.out.println(" Credit Note ");
            System.out.println("=============");
            System.out.println("1 - Add new Credit Note");
            System.out.println("2 - Edit Credit Note");
            System.out.println("3 - Search Credit Note");
            System.out.println("4 - Cancel Credit Note" + '\n');
            System.out.println("0 - Back to Main Menu" + '\n');

            System.out.print("Enter your selection (0-4): ");
            selection = sc.nextInt();

            switch (selection) {
                case 1 -> {
                    General.clearScreen();
                    CreditNoteDriver.addCreditNote();
                }
                case 2 -> {
                    General.clearScreen();
                    CreditNoteDriver.editCreditNote();
                }
                case 3 -> {
                    General.clearScreen();
                    CreditNoteDriver.searchCreditNote();
                }
                case 4 -> {
                    General.clearScreen();
                    CreditNoteDriver.cancelCreditNote();
                }
                case 0 -> {
                    Inventory.main(args);
                }
                default ->
                    System.out.println("Invalid input! Please try again..." + '\n');
            }
        } while (selection != 0);
    }

    //Add new Credit Note / stock return
    public static void addCreditNote() {
        Scanner sc = new Scanner(System.in);
        char cont;
        char confirm;

        String staffName;
        String supplierName;
        String productCode;

        do {
            //Create Object
            CreditNote cn = new CreditNote();

            //Create empty arrayList to store value
            ArrayList<CreditNote> creditNote = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            ArrayList<StockDetails> newStockDetails = new ArrayList<>();

            //Read and store into ArrayList
            creditNote = CreditNote.readFile(CreditNote.FILE_NAME, creditNote, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            //Set the num of credit note as arraylist size
            CreditNote.setNumOfCrn(creditNote.size());

            General.clearScreen();
            System.out.println("-----------------------");
            System.out.println("| Add New Credit Note |");
            System.out.println("-----------------------");

            cn.setCnNo(String.format("CRN%04d", creditNote.size() + 1));
            System.out.println("Credit Note No.: " + cn.getCnNo());

            System.out.print("Enter credit note date: ");
            cn.setCnDate(sc.nextLine());

            //get and compare to Staff class
            do {
                staffName = General.stringNullCheckingInput("Enter staff name: ", "  Input field cannot be empty, please enter again.");
                if (Staff.searchAllStaff(staffName, "Name").getName().equals(staffName)) {
                    cn.setStaffName(staffName);
                } else {
                    System.out.println("  Invalid staff name! Please try again..");
                }
            } while (!Staff.searchAllStaff(staffName, "Name").getName().equals(staffName));

            //get and compare to Supplier class
            do {
                supplierName = General.stringNullCheckingInput("Enter supplier name: ", "  Input field cannot be empty, please enter again.");
                if (Supplier.search("supplierName", supplierName.toUpperCase()) != null) {
                    cn.setSupplierName(supplierName);
                } else {
                    System.out.println("  Invalid supplier name! Please try again..");
                }
            } while (Supplier.search("supplierName", supplierName.toUpperCase()) == null);

            //get item list of the credit note
            do {
                StockDetails sd = new StockDetails();
                do {
                    productCode = ProductDriver.codeInput();
                    if (Product.search("productCode", productCode) != null) {
                        sd.setProductCode(productCode);
                    }
                } while (Product.search("productCode", productCode) == null);

                System.out.print("Enter the quantity: ");
                sd.setQty(sc.nextInt());

                System.out.print("Enter the cost price: ");
                sd.setCostPrice(sc.nextDouble());

                //set creditNote no. in StockDetails for reference
                sd.setCnNo(cn.getCnNo());

                //add stock details to stockDetails arrayList
                newStockDetails.add(sd);

                cont = General.yesNoInput("Continue to add item? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

            } while (cont == 'Y' || cont == 'y');

            //display the whole invoice details
            //set the total amount that return
            cn.setAmount(CreditNote.printCreditNote(cn, newStockDetails));

            //set the tag to 'Valid'
            cn.setTag("Valid");

            confirm = General.yesNoInput("Confirm add this credit note? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                //add new credit note details to creditNote arrayList
                creditNote.add(cn);

                //add new stock details to stockDetails arrayList
                for (int i = 0; i < newStockDetails.size(); i++) {
                    stockDetails.add(newStockDetails.get(i));
                }

                //Updated the file information
                CreditNote.writeFile(CreditNote.FILE_NAME, creditNote);
                StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);
                Product.updateProduct();
            }

            cont = General.yesNoInput("Continue add again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");
        } while (cont == 'Y' || cont == 'y');
        
        General.clearScreen();
    }

    //METHOD OVERLOADING
    //Search function
    public static void searchCreditNote() {
        Scanner sc = new Scanner(System.in);
        String cnNo;
        char cont;

        do {
            //Create empty arraylist to store value
            ArrayList<CreditNote> creditNote = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            creditNote = CreditNote.readFile(CreditNote.FILE_NAME, creditNote, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("----------------------");
                System.out.println("| Search Credit Note |");
                System.out.println("----------------------");

                System.out.print("Enter Credit Note Number (eg: CRN0001): ");
                cnNo = sc.nextLine().toUpperCase();

            } while (searchCreditNote(creditNote, cnNo, stockDetails) == -1);

            cont = General.yesNoInput("Continue search again? (Y/N) > ", "  Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');

        General.clearScreen();
    }

    //Search with parameter and will return the index of credit note that in ArrayList found
    public static int searchCreditNote(ArrayList<CreditNote> creditNote, String cnNo, ArrayList<StockDetails> stockDetails) {

        int index;
        int invalid = 0;

        //loop for find the same inv no.
        for (int i = 0; i < creditNote.size(); i++) {
            //if same, then print out result
            if (creditNote.get(i).getCnNo().equals(cnNo)) {
                invalid = CreditNote.printCreditNote(creditNote, i, stockDetails);
                if (invalid > 0) {
                    return index = -2;
                }
                return index = i;
            }
        }

        System.out.println("Invalid credit note number. Please try again...");
        General.systemPause();

        return index = -1;
    }

    //Edit credit note function, edit specify field in the credit note
    public static void editCreditNote() {
        Scanner sc = new Scanner(System.in);

        String cnNo;
        String input;
        char cont;
        char confirm;
        int selection;
        int index;

        double totalAmount;

        do {
            //Create an object to hold the current invoice
            CreditNote cn = new CreditNote();

            //Create empty arraylist to store value
            ArrayList<CreditNote> creditNote = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            creditNote = CreditNote.readFile(CreditNote.FILE_NAME, creditNote, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("--------------------");
                System.out.println("| Edit Credit Note |");
                System.out.println("--------------------");

                System.out.print("Enter Credit Note Number (eg: CRN0001): ");
                cnNo = sc.nextLine().toUpperCase();

                //search and display the invoice result
                index = searchCreditNote(creditNote, cnNo, stockDetails);

                if (index == -2) {
                    break;
                }

                cn = creditNote.get(index);

            } while (index == -1);

            if (index == -2) {
                break;
            }

            do {
                System.out.println('\n');
                System.out.println("Editable Field: ");
                System.out.println("1. Credit Note Date" + '\n' + "2. Staff Incharge" + '\n' + "3. Supplier Name"
                        + '\n' + "4. Item Details (Item code/quantity/cost price)");

                selection = General.intInput("Select a field to edit (1-4): ", "  Invalid Input! Please enter number only.");

                switch (selection) {
                    case 1 -> {
                        input = General.dateInput("Enter credit note date(DD/MM/YYYY): ", "  Please enter date with format DD/MM/YYYY");
                        confirm = General.yesNoInput("Confirm to change? (Y/N) > ", "  Please enter 'Y' or 'N' only.");
                        if (confirm == 'Y') {
                            cn.setCnDate(input);
                        }
                        break;
                    }
                    case 2 -> {
                        do {
                            input = General.stringNullCheckingInput("Enter a new staff name: ", "  Input field cannot be empty, please enter again.");
                            if (Staff.searchAllStaff(input, "Name").getName().equals(input)) {
                                confirm = General.yesNoInput("Confirm to change? (Y/N) > ", "  Please enter 'Y' or 'N' only.");
                                if (confirm == 'Y') {
                                    cn.setStaffName(input);
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
                                    cn.setSupplierName(input);
                                }
                            } else {
                                System.out.println("  Invalid supplier name! Please try again..");
                            }
                        } while (Supplier.search(input, "supplierName") == null);
                        break;
                    }
                    case 4 -> {
                        totalAmount = editCreditNoteItem(creditNote, cn, index);
                        cn.setAmount(totalAmount);
                        break;
                    }
                    default -> {
                        if (selection < 1 || selection > 6) {
                            System.out.println("  Invalid selection! Please enter number 1-4 only.");
                            General.systemPause();
                            searchCreditNote(creditNote, cnNo, stockDetails);
                        }
                    }
                }
            } while (selection < 1 || selection > 6);

            if (index == -2) {
                break;
            }

            //Update Credit Note
            creditNote.set(index, cn);
            CreditNote.writeFile(SupplierInvoice.FILE_NAME, creditNote);

            cont = General.yesNoInput("Continue edit again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');
        
        General.clearScreen();
    }

    //Edit specify field of item list in the credit note
    public static double editCreditNoteItem(ArrayList<CreditNote> creditNote, CreditNote cn, int index) {
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
        ArrayList<StockDetails> currentCreditNoteStockDetails = new ArrayList<>();

        //Create an object to hold current item details
        StockDetails sd;

        //Array to store the index of stockDetails
        int indexNum = 0;
        int[] stockDetailsIndex = new int[stockDetails.size()];

        General.clearScreen();
        System.out.println("<Edit Credit Note Item>");

        //Loop for compare the same invNo.
        for (int i = 0; i < stockDetails.size(); i++) {
            if (creditNote.get(index).getCnNo().equals(stockDetails.get(i).getCnNo())) {
                //store the stock details with the current credit note number.
                currentCreditNoteStockDetails.add(stockDetails.get(i));
                //store stockDetails index
                stockDetailsIndex[indexNum] = i;
                indexNum++;
            }
        }

        System.out.println("   -------------------------------------------------------------------------");
        System.out.println("   Item                Cost Price               Qty               Total(RM)");
        System.out.println("   -------------------------------------------------------------------------");

        //Loop for display stock details
        for (int i = 0; i < currentCreditNoteStockDetails.size(); i++) {
            subtotal = currentCreditNoteStockDetails.get(i).getCostPrice() * currentCreditNoteStockDetails.get(i).getQty();
            num++;
            System.out.printf("%d. %-5s             %7.2f                   %3d                %6.2f\n",
                    num, currentCreditNoteStockDetails.get(i).getProductCode(), currentCreditNoteStockDetails.get(i).getCostPrice(), currentCreditNoteStockDetails.get(i).getQty(), subtotal);
        }

        do {
            item = General.intInput("Which item did you want to edit? : ", "  Please input a number.");
            if (item < num || item > num) {
                System.out.printf("Please input 1-%d : ", num);
            }
        } while (item < num || item > num);

        --item;

        sd = currentCreditNoteStockDetails.get(item);
        
        OUTER:
        do {
            System.out.println('\n');
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
                                currentCreditNoteStockDetails.set(item, sd);
                            }
                        }
                    } while (Product.search("productCode", productCode) == null);
                    break OUTER;
                }
                case 2 -> {
                    costPrice = General.doubleInput("Enter cost price: ", "  Please enter a value with decimal place");
                    confirm = General.yesNoInput("Confirm change? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");
                    if (confirm == 'Y') {
                        sd.setCostPrice(costPrice);
                        currentCreditNoteStockDetails.set(item, sd);
                    }
                    break OUTER;
                }
                case 3 -> {
                    quantity = General.intInput("Enter quantity: ", "Please enter a integer quantity");
                    confirm = General.yesNoInput("Confirm change? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");
                    if (confirm == 'Y') {
                        sd.setQty(quantity);
                        currentCreditNoteStockDetails.set(item, sd);
                    }
                    break OUTER;
                }
                default -> {
                    if (field < 1 || field > 3) {
                        System.out.println("Please input 1-3 : ");
                    }
                }
            }
        } while (field < 1 || field > 3);
        
        //loop the arrayList and replace the specify element
        for (int i = 0; i < stockDetailsIndex.length; i++) {
            stockDetails.set(stockDetailsIndex[i], currentCreditNoteStockDetails.get(i));
        }

        //Update Stock Details
        StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);

        //Update product
        Product.updateProduct();

        //calculate totalAmount and return back
        for (int i = 0; i < currentCreditNoteStockDetails.size(); i++) {
            subtotal = currentCreditNoteStockDetails.get(i).getCostPrice() * currentCreditNoteStockDetails.get(i).getQty();
            totalAmount += subtotal;
        }
        return totalAmount;
    }
    
    //Delete Invoice function, set the tag from 'Valid' to 'Invalid' to indicate the invoice has been cancelled
    public static void cancelCreditNote() {
        Scanner sc = new Scanner(System.in);
        String cnNo;
        char cont = 'Y';
        char confirm;
        int index = 0;

        do {
            //Create object to hold current invoice
            CreditNote cn;

            //Create empty arraylist to store value
            ArrayList<CreditNote> creditNote = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read and store in arrayList
            creditNote = CreditNote.readFile(CreditNote.FILE_NAME, creditNote, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.FILE_NAME);

            do {
                General.clearScreen();
                System.out.println("---------------------");
                System.out.println("| Cancel CreditNote |");
                System.out.println("---------------------");

                System.out.print("Enter Credit Note Number (eg: CRN0001): ");
                cnNo = sc.nextLine().toUpperCase();

                index = searchCreditNote(creditNote, cnNo, stockDetails);

                if (index == -2) {
                    break;
                }

            } while (index == -1);

            if (index == -2) {
                break;
            }

            cn = creditNote.get(index);

            confirm = General.yesNoInput("Confirm to cancel this credit note? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                cn.setTag("Invalid");
                creditNote.set(index, cn);
                //Loop for compare the same invNo.
                for (int i = 0; i < stockDetails.size(); i++) {
                    if (creditNote.get(index).getCnNo().equals(stockDetails.get(i).getCnNo())) {
                        //remove the stock details related to this invoice
                        stockDetails.remove(i);
                    }
                }

                //Update the file
                StockDetails.writeFile(StockDetails.FILE_NAME, stockDetails);
                CreditNote.writeFile(CreditNote.FILE_NAME, creditNote);

                //Update product info
                Product.updateProduct();

                System.out.println("Credit Note cancelled successfully.");
                

            } else if (confirm == 'N') {
                System.out.println("Process cancelled...");
            }
            
            cont = General.yesNoInput("Continue to cancel another credit note? (Y/N) > ", "  Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');
        
        General.clearScreen();
    }
}
