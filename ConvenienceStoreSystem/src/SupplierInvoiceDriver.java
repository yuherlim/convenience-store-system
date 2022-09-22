
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierInvoiceDriver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
            System.out.println("0 - Back to Main Menu" + '\n');

            System.out.print("Enter your selection (0-4): ");
            selection = sc.nextInt();

            switch (selection) {
                case 1 -> {
                    SupplierInvoiceDriver.addInvoice();
                }
                case 2 -> {
                    SupplierInvoiceDriver.editInvoice();
                }
                case 3 -> {
                    SupplierInvoiceDriver.searchInvoice();
                }
                case 4 -> {
                    SupplierInvoiceDriver.cancelInvoice();
                }
                case 0 -> {
                    //ConvenienceStore.main();
                }
                default ->
                    System.out.println("Invalid input! Please try again..." + '\n');
            }
        } while (selection < 0 || selection > 4);

    }

    public static ArrayList<SupplierInvoice> readFile(String fileName, ArrayList<SupplierInvoice> supplierInvoice, ArrayList<StockDetails> stockDetails) {

        try ( FileReader reader = new FileReader("src\\" + fileName)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");

                String invNo = string1[0];
                String invDate = string1[1];
                String staffName = string1[2];
                String supplierName = string1[3];
                String tag = buffer[2];

                //Convert string to double for total amount
                double doubleArr = Double.parseDouble(buffer[1]);

                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetails.readFile(StockDetails.fileName).clone();
                stockDetails.clear();

                //Add elements of stock details that is associated with this invoice number.
                for (StockDetails sd : allSD) {
                    if (sd.getInvNo().equals(invNo)) {
                        stockDetails.add(sd);
                    }
                }

                //store cloned versions of stockDetails as it will be used again in subsequent loops
                supplierInvoice.add(new SupplierInvoice(invNo, invDate, staffName, supplierName, (ArrayList<StockDetails>) stockDetails.clone(), doubleArr, tag));

            }
        } catch (IOException e) {
        }

        return supplierInvoice;
    }

    //Add new Invoice function
    public static void addInvoice() {
        Scanner sc = new Scanner(System.in);
        char cont;
        char confirm;
        String staffName;
        String supplierName;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();
            StockDetails sd = new StockDetails();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Call readfile to read the invoice.txt
            SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);

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
            si.setInvDate(General.dateInput("Enter invoice date: ", "Invalid date! Please try again.."));

            //need compare to Staff class
            do {
                System.out.print("Enter staff name: ");
                staffName = sc.nextLine();
                if (Staff.searchAllStaff(staffName, "Name").getName().equals(staffName)) {
                    si.setStaffName(staffName);
                } else {
                    System.out.println("Invalid staff name! Please try again..");
                }
            } while (!Staff.searchAllStaff(staffName, "Name").getName().equals(staffName));

            //need compare to Supplier class
            do {
                supplierName = General.stringNullCheckingInput("Enter a new supplier name: ", "  Input field cannot be empty, please enter again.");
                if (Supplier.search("supplierName", supplierName).getName().equals(supplierName)) {
                    si.setSupplierName(supplierName);
                } else {
                    System.out.println("Invalid staff name! Please try again..");
                }
            } while (!Supplier.search(supplierName, "supplierName").getName().equals(supplierName));

            do {
                System.out.print("Enter the product code: ");
                sd.setProductCode(sc.next());

                System.out.print("Enter the quantity: ");
                sd.setQty(sc.nextInt());

                System.out.print("Enter the cost price: ");
                sd.setCostPrice(sc.nextDouble());

                //set invoice no. in StockDetails for reference
                sd.setInvNo(si.getInvNo());

                //add stock details to stockDetails arrayList
                stockDetails.add(sd);

                cont = General.yesNoInput("Continue to add item? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

            } while (cont == 'Y');

            //add supplier invoice details to supplierInvoice arrayList
            supplierInvoice.add(si);

            //display the whole invoice details
            //set the total amount that return
            si.setAmount(SupplierInvoiceDriver.printInvoice(si, stockDetails));

            //set the tag to 'Valid'
            si.setTag("Valid");

            confirm = General.yesNoInput("Continue add this invoice? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                SupplierInvoice.writeFile(SupplierInvoice.fileName, supplierInvoice);
                StockDetails.writeFile(StockDetails.fileName, stockDetails);
            }

            cont = General.yesNoInput("Continue add again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y' || cont == 'y');
    }

    //Search Function, only search by invoice number
    public static void searchInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont;

        do {
            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            supplierInvoice = SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.fileName);

            do {
                General.clearScreen();
                System.out.println("------------------");
                System.out.println("| Search Invoice |");
                System.out.println("------------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

            } while (searchInvoice(supplierInvoice, invNo, stockDetails) == -1);

            cont = General.yesNoInput("Continue search again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');

        //Back to Stock In menu
        //SupplierInvoiceDriver.main(args);
    }

    public static int searchInvoice(ArrayList<SupplierInvoice> supplierInvoice, String invNo, ArrayList<StockDetails> stockDetails) {

        int index;

        //loop for find the same inv no.
        for (int i = 0; i < supplierInvoice.size(); i++) {
            //if same, then print out result
            if (supplierInvoice.get(i).getInvNo().equals(invNo)) {
                SupplierInvoiceDriver.printInvoice(supplierInvoice, i, stockDetails);
                return index = i;
            }
        }
        System.out.println("Invalid Invoice Number. Please try again...");
        General.systemPause();
        return index = -1;
    }

    //Edit Invoice function, edit specify field
    public static void editInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        String input;
        char cont;
        int selection;
        int index;

        do {
            //Create an object to hold the current invoice
            SupplierInvoice si = new SupplierInvoice();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read the file and store into ArrayList
            supplierInvoice = SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.fileName);

            do {
                General.clearScreen();
                System.out.println("----------------");
                System.out.println("| Edit Invoice |");
                System.out.println("----------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

                //search and display the invoice result
                index = searchInvoice(supplierInvoice, invNo, stockDetails);

                si = supplierInvoice.get(index);

            } while (index == -1);

            do {
                System.out.println('\n');
                System.out.println("Editable Field: ");
                System.out.println("1. Invoice Date" + '\n' + "2. Staff Incharge" + '\n' + "3. Supplier Name"
                        + '\n' + "4. Item Details (Item code/quantity/cost price)");

                selection = General.intInput("Select a field to edit (1-4): ", "Invalid Input! Please enter number only.");

                switch (selection) {
                    case 1 -> {
                        input = General.dateInput("Enter invoice date(DD/MM/YYYY): ", "Please enter date with format DD/MM/YYYY");
                        si.setInvDate(input);
                        supplierInvoice.set(index, si);
                        break;
                    }
                    case 2 -> {
                        do {
                            input = General.stringNullCheckingInput("Enter a new staff name: ", "  Input field cannot be empty, please enter again.");
                            if (Staff.searchAllStaff(input, "Name").getName().equals(input)) {
                                si.setStaffName(input);
                                supplierInvoice.set(index, si);
                            } else {
                                System.out.println("Invalid staff name! Please try again..");
                            }
                        } while (!Staff.searchAllStaff(input, "Name").getName().equals(input));
                        break;
                    }
                    case 3 -> {
                        do {
                            input = General.stringNullCheckingInput("Enter a new supplier name: ", "  Input field cannot be empty, please enter again.");
                            if (Supplier.search(input, "supplierName").getName().equals(input)) {
                                si.setSupplierName(input);
                                supplierInvoice.set(index, si);
                            } else {
                                System.out.println("Invalid staff name! Please try again..");
                            }
                        } while (!Supplier.search(input, "supplierName").getName().equals(input));
                        break;
                    }
                    case 4 -> {
                        editInvoiceItem(supplierInvoice, si, stockDetails, index);
                        break;
                    }
                    default -> {
                        if (selection < 1 || selection > 6) {
                            System.out.println("Invalid selection! Please enter number 1-4 only.");
                            General.systemPause();
                            searchInvoice(supplierInvoice, invNo, stockDetails);
                        }
                    }
                }
            } while (selection < 1 || selection > 6);

            cont = General.yesNoInput("Continue edit again? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

        } while (cont == 'Y');
    }

    //Delete Invoice function, set the tag from 'Valid' to 'Invalid'
    public static void cancelInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont = 'Y';
        char confirm;
        int index = 0;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();
            StockDetails sd = new StockDetails();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            //Read and store in arrayList
            supplierInvoice = SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);
            stockDetails = StockDetails.readFile(StockDetails.fileName);

            do {
                General.clearScreen();
                System.out.println("------------------");
                System.out.println("| Cancel Invoice |");
                System.out.println("------------------");

                System.out.print("Enter Invoice Number (eg: INV0001): ");
                invNo = sc.nextLine().toUpperCase();

                index = searchInvoice(supplierInvoice, invNo, stockDetails);

            } while (index == -1);

            confirm = General.yesNoInput("Confirm to cancel this invoice? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");

            if (confirm == 'Y') {
                supplierInvoice.set(index, si);
                System.out.println("Invoice canceled successfully.");
                cont = General.yesNoInput("Continue to cancel another invoice? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");
            } else if (confirm == 'N') {
                System.out.println("Process terminated...");
                cont = General.yesNoInput("Continue to cancel another invoice? (Y/N) > ", "Invalid input! Please enter 'Y' or 'N' only.");
            }

        } while (cont == 'Y');
    }

    public static void printInvoice(ArrayList<SupplierInvoice> supplierInvoice, int index, ArrayList<StockDetails> stockDetails) {

        double totalAmount = 0d;
        double subtotal;

        //Used to store the stock details with the current invoice no.
        ArrayList<StockDetails> currentInvoiceStockDetails = new ArrayList<>();

        if (!supplierInvoice.get(index).getTag().equals("Invalid")) {
            System.out.printf("Invoice No.: %s                          Invoice Date: %s \n", supplierInvoice.get(index).getInvNo(), supplierInvoice.get(index).getInvDate());
            System.out.printf("Supplier: %s                                Staff Incharge: %s \n", supplierInvoice.get(index).getSupplierName(), supplierInvoice.get(index).getStaffName());
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Item               Cost Price          Qty                 Total(RM)");
            System.out.println("---------------------------------------------------------------------");

            //Loop for compare the same invNo.
            for (int i = 0; i < stockDetails.size(); i++) {
                if (supplierInvoice.get(index).getInvNo().equals(stockDetails.get(i).getInvNo())) {
                    //store the stock details with the current invoice number.
                    currentInvoiceStockDetails.add(new StockDetails(stockDetails.get(i)));
                }
            }

            //Loop for display stock details
            for (int i = 0; i < currentInvoiceStockDetails.size(); i++) {
                subtotal = currentInvoiceStockDetails.get(i).getCostPrice() * currentInvoiceStockDetails.get(i).getQty();
                System.out.printf("%s             %.2f              %d                   %8.2f\n",
                        currentInvoiceStockDetails.get(i).getProductCode(), currentInvoiceStockDetails.get(i).getCostPrice(), currentInvoiceStockDetails.get(i).getQty(), subtotal);
                totalAmount += subtotal;
            }
            System.out.println("---------------------------------------------------------------------");
            System.out.printf("Total Amount(RM)                                          %8.2f \n \n", totalAmount);
        } else {
            System.out.println("Invoice has been canceled.");
        }
    }

    public static double printInvoice(SupplierInvoice si, ArrayList<StockDetails> stockDetails) {

        double totalAmount = 0d;
        double subtotal;

        System.out.printf("Invoice No.: %s                             Invoice Date: %s \n", si.getInvNo(), si.getInvDate());
        System.out.printf("Supplier: %s                                Staff Incharge: %s \n", si.getSupplierName(), si.getStaffName());
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Item               Cost Price          Qty                 Total(RM)");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < stockDetails.size(); i++) {
            subtotal = stockDetails.get(i).getCostPrice() * stockDetails.get(i).getQty();
            System.out.printf("%s                    %.2f             %d                    %.2f \n",
                    stockDetails.get(i).getProductCode(), stockDetails.get(i).getCostPrice(), stockDetails.get(i).getQty(), subtotal);
            totalAmount += subtotal;
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("Total Amount(RM)                                             %.2f \n", totalAmount);

        return totalAmount;
    }

    public static void editInvoiceItem(ArrayList<SupplierInvoice> supplierInvoice, SupplierInvoice si, ArrayList<StockDetails> stockDetails, int index) {
        String productCode;
        double costPrice;
        int quantity;

        double subtotal;
        int num = 1;
        int item;
        int field;

        General.clearScreen();

        //Used to store the stock details with the current invoice no.
        ArrayList<StockDetails> currentInvoiceStockDetails = new ArrayList<>();

        System.out.println("<Edit Invoice Item>");

        //Loop for compare the same invNo.
        for (int i = 0; i < stockDetails.size(); i++) {
            if (supplierInvoice.get(index).getInvNo().equals(stockDetails.get(i).getInvNo())) {
                //store the stock details with the current invoice number.
                currentInvoiceStockDetails.add(new StockDetails(stockDetails.get(i)));
            }
        }

        System.out.println("    ---------------------------------------------------------------------");
        System.out.println("    Item               Cost Price          Qty                 Total(RM)");
        System.out.println("    ---------------------------------------------------------------------");

        //Loop for display stock details
        for (int i = 0; i < currentInvoiceStockDetails.size(); i++) {
            subtotal = currentInvoiceStockDetails.get(i).getCostPrice() * currentInvoiceStockDetails.get(i).getQty();
            System.out.printf("%d  %s                %.2f              %d                   %8.2f\n",
                    num, currentInvoiceStockDetails.get(i).getProductCode(), currentInvoiceStockDetails.get(i).getCostPrice(), currentInvoiceStockDetails.get(i).getQty(), subtotal);
            num++;
        }

        do {
            item = General.intInput("Which item did you want to edit? : ", "  Please input a number.");
            if (item < num || item > num) {
                System.out.printf("Please input 1-%d : ", num);
            }
        } while (item < num || item > num);

        do {
            System.out.println('\n');
            System.out.println("1. Product Code" + '\n' + "2. Cost Price" + '\n' + "3. Quantity");
            System.out.print("Please select a field to edit the item (1-3): ");
            field = General.intInput("Please select a field to edit the item (1-3): ", "  Please input a number.");
            if (field < 1 || field > 3) {
                System.out.println("Please input 1-3 : ");
            }
        } while (field < 1 || field > 3);

        switch (field) {
            case 1 -> {
                System.out.print("Enter product code: ");
            }
        }

    }
}
