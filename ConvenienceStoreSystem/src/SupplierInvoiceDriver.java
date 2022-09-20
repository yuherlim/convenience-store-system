
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierInvoiceDriver extends General {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection;

        SupplierInvoice si = new SupplierInvoice();
        StockDetails sd = new StockDetails();
        ArrayList<SupplierInvoice> invoice = new ArrayList<>();
        ArrayList<StockDetails> stockDetails = new ArrayList<>();

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
                System.out.println(line);
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");

                String invNo = string1[0];
                String invDate = string1[1];
                String staffName = string1[2];
                String supplierName = string1[3];
                String tag = buffer[2];

                //Convert string to double for total amount
                double doubleArr = Double.parseDouble(buffer[1]);

//                //read from stockDetails.txt and create a copy of stock details records.
//                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetailsDriver.readFile(StockDetails.fileName, stockDetails).clone();
//                stockDetails.clear();
                supplierInvoice.add(new SupplierInvoice(invNo, invDate, staffName, supplierName, stockDetails, doubleArr, tag));

            }

        } catch (IOException e) {
        }

        return supplierInvoice;
    }

    //Add new Invoice function
    public static void addInvoice() {
        Scanner sc = new Scanner(System.in);
        char cont;
        String staffName;
        String supplierName;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();
            StockDetails sd = new StockDetails();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            ArrayList<Double> subTotal = new ArrayList<>();

            //Call readfile to read the invoice.txt
            SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);

            //Set the num of invoice as arraylist size
            SupplierInvoice.setNumOfInv(supplierInvoice.size());

            do {
                System.out.println("-------------------");
                System.out.println("| Add New Invoice |");
                System.out.println("-------------------");

                //Set the invNo. = (num of inv) + 1
                si.setInvNo(String.format("INV-%04d", SupplierInvoice.getNumOfInv() + 1));
                System.out.println("Invoice No.: " + si.getInvNo());

                //get and validate date input
                si.setInvDate(dateInput("Enter invoice date: ", "Invalid date! Please try again.."));

                //need compare to Staff class
                String nameGetted;
                do {
                    System.out.print("Enter staff name: ");
                    staffName = sc.nextLine();
                    nameGetted = Staff.searchAllStaff(staffName, "Name").getName();
                    if (!nameGetted.equals("")) {
                        si.setStaffName(staffName);
                    } else {
                        System.out.println("Invalid staff name! Please try again..");
                    }
                } while (!nameGetted.equals(""));

                //need compare to Supplier class
                do {
                    System.out.print("Enter Suplier name: ");
                    supplierName = sc.nextLine();
                    if (Supplier.seacrhSupplier(supplierName) == false) {
                        System.out.println("Invalid supplier name! Please try again..");
                    } else {
                        si.setSupplierName(supplierName);
                    }
                } while (Supplier.seacrhSupplier(supplierName) == false);

                do {
                    System.out.print("Enter the product code: ");
                    sd.setProductCode(sc.nextLine());

                    System.out.print("Enter the quantity: ");
                    sd.setQty(sc.nextInt());

                    System.out.print("Enter the cost price: ");
                    sd.setCostPrice(sc.nextDouble());

                    //get invoice no. from supplier invoice class and store in stock details for reference
                    sd.setInvNo(si.getInvNo());

                    stockDetails.add(sd);

                    System.out.print("Continue add item? (Y/N) > ");
                    cont = sc.next().charAt(0);

                } while (cont == 'Y' || cont == 'y');

                supplierInvoice.add(si);

                //calculate the subtotal of each product add in
                SupplierInvoiceDriver.calcSubTotal(stockDetails);

                //display the whole invoice details
                SupplierInvoiceDriver.printInvoice(si, stockDetails, subTotal);

                System.out.println("Continue add another invoice? (Y/N) > ");
                cont = sc.next().charAt(0);
            } while (cont == 'Y' || cont == 'y');

            SupplierInvoice.writeFile(SupplierInvoice.fileName, supplierInvoice);
//            StockDetails.writeFile(StockDetails.fileName, stockDetails);

        } while (cont == 'Y' || cont == 'y');
    }

    //Search Function, only search by invoice number
    public static void searchInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();
            StockDetails sd = new StockDetails();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            ArrayList<Double> subTotal = new ArrayList<>();

            //Call readfile to read the invoice.txt
            SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);

            //Set the num of invoice as arraylist size
            SupplierInvoice.setNumOfInv(supplierInvoice.size());

            do {
                System.out.println("------------------");
                System.out.println("| Search Invoice |");
                System.out.println("------------------");

                System.out.print("Enter Invoice No. to search: ");
                invNo = sc.nextLine().toUpperCase();

                for (int i = 0; i < supplierInvoice.size(); i++) {
                    if (invNo.equals(supplierInvoice.get(i).getInvNo())) {
                        SupplierInvoiceDriver.printInvoice(si, stockDetails, subTotal);
                    }
                }

                System.out.println("Continue to search another invoice? (Y/N) > ");
                cont = sc.next().charAt(0);

            } while (cont == 'Y' || cont == 'y');

        } while (cont == 'Y' || cont == 'y');
    }

    //Edit Invoice function, edit specify field
    public static void editInvoice() {

    }

    //Delete Invoice function, change the tag 'Valid' to 'Invalid'
    public static void cancelInvoice() {
        Scanner sc = new Scanner(System.in);
        String invNo;
        char cont;

        do {
            //Create object
            SupplierInvoice si = new SupplierInvoice();
            StockDetails sd = new StockDetails();

            //Create empty arraylist to store value
            ArrayList<SupplierInvoice> supplierInvoice = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();
            ArrayList<Double> subTotal = new ArrayList<>();

            //Call readfile to read the invoice.txt
            SupplierInvoiceDriver.readFile(SupplierInvoice.fileName, supplierInvoice, stockDetails);

            //Set the num of invoice as arraylist size
            SupplierInvoice.setNumOfInv(supplierInvoice.size());

            do {
                System.out.println("------------------");
                System.out.println("| Cancel Invoice |");
                System.out.println("------------------");

                do {
                    System.out.print("Enter Invoice No.: ");
                    invNo = sc.nextLine().toUpperCase();

                    for (int i = 0; i < supplierInvoice.size(); i++) {
                        if (!invNo.equals(supplierInvoice.get(i).getInvNo())) {
                            System.out.println("Invalid Invoice Number. Please try again...");
                        } else {
                            SupplierInvoiceDriver.printInvoice(si, stockDetails, subTotal);
                        }
                    }
                } while (!invNo.equals(supplierInvoice.get(i).getInvNo()));

                System.out.println("Confirm to cancel this invoice? (Y/N) > ");
                cont = sc.next().charAt(0);

            } while (cont == 'Y' || cont == 'y');

        } while (cont == 'Y' || cont == 'y');
    }

    public static void printInvoice(SupplierInvoice si, ArrayList<StockDetails> stockDetails, ArrayList<Double> subTotal) {

        double totalAmount = 0d;

        System.out.printf("Invoice No.: %s                             Invoice Date: %s \n", si.getInvNo(), si.getInvDate());
        System.out.printf("Supplier: %s                                Staff Incharge: %s \n", si.getSupplierName(), si.getStaffName());
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Item               Cost Price          Qty                 Total(RM)");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < stockDetails.size(); i++) {
            System.out.printf("%s                    %.2f             %d                    %.2f \n",
                    stockDetails.get(i).getProductCode(), stockDetails.get(i).getCostPrice(), stockDetails.get(i).getQty(), subTotal.get(i));
            totalAmount += subTotal.get(i);
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("Total Amount(RM)                                             %.2f \n", totalAmount);
    }

    public static ArrayList<Double> calcSubTotal(ArrayList<StockDetails> stockDetails) {
        ArrayList<Double> subTotal = new ArrayList<>();

        for (int i = 0; i < stockDetails.size(); i++) {
            subTotal.add(i, stockDetails.get(i).getCostPrice() * stockDetails.get(i).getQty());
        }

        return subTotal;
    }
}
