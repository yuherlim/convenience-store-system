
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierInvoiceDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection;
        
        SupplierInvoice si = new SupplierInvoice();
        StockDetails sd = new StockDetails();
        ArrayList<SupplierInvoice> invoice = new ArrayList<>();
        ArrayList<StockDetails> stockDetails = new ArrayList<>();
        
        do{
        //Suplier Invoice Menu
        System.out.println("==================");
        System.out.println(" Supllier Invoice");
        System.out.println("==================");
        System.out.println("1 - Add new Invoice");
        System.out.println("2 - Edit Invoice");
        System.out.println("3 - Search Invoice");
        System.out.println("4 - Cancel Invoice" + '\n');
        System.out.println("0 - Back to Main Menu" + '\n');
        
        System.out.print("Enter your selection (0-4): ");
        selection = sc.nextInt();
        
        switch(selection){
            case 1 -> {
                SupplierInvoiceDriver.addInvoice(invoice, si, stockDetails, sd);
            }
            case 2 -> {
                        //SupplierInvoiceDriver.editInvoice(invoice, si, stockDetails, sd);
            }
            case 3 -> {
                        //SupplierInvoiceDriver.searchInvoice(invoice, si, stockDetails, sd);
            }
            case 4 -> {
                        //SupplierInvoiceDriver.cancelInvoice(invoice, si, stockDetails, sd);
            }
            case 0 -> {
                        //ConvenienceStore.main();
            }
            default -> System.out.println("Invalid input! Please try again..." + '\n');
        }
        }while(selection < 0 || selection > 4);

    }

    public static ArrayList<SupplierInvoice> readFile(String fileName, ArrayList<SupplierInvoice> invoice, ArrayList<StockDetails> stockDetails) {
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

                //Convert string to double for total amount
                double doubleArr = Double.parseDouble(buffer[1]);

                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetailsDriver.readFile("stock details.txt", stockDetails).clone();
                stockDetails.clear();

                //invoice.add(new SupplierInvoice(invNo, invDate, staffName, supplierName, stockDetails, doubleArr));
            }

        } catch (IOException e) {
        }

        return invoice;
    }

    //Method to add a new invoice / stock in
    public static void addInvoice(ArrayList<SupplierInvoice> invoice, SupplierInvoice si, ArrayList<StockDetails> stockDetails, StockDetails sd) {
        Scanner sc = new Scanner(System.in);
        char cont;
        String staffName;
        String supplierName;

        do {
            System.out.println("-------------------");
            System.out.println("| Add New Invoice |");
            System.out.println("-------------------");

            si.setInvNo(String.format("INV-%04d", SupplierInvoice.getNumOfInv() + 1));
            System.out.println("Invoice No.: " + si.getInvNo());

            System.out.print("Enter invoice date: ");
            si.setInvDate(sc.nextLine());

            //need compare to Staff class
            do {
                System.out.print("Enter staff name: ");
                staffName = sc.nextLine();
                if (Staff.seacrhStaff(staffName) == false) {
                    System.out.println("Invalid staff name! Please try again..");
                }
            } while (Staff.seacrhStaff(staffName) == false);

            //need compare to Supplier class
            do {
                System.out.print("Enter Suplier name: ");
                supplierName = sc.nextLine();
                if (Supplier.seacrhSupplier(supplierName) == false) {
                    System.out.println("Invalid supplier name! Please try again..");
                }
            } while (Supplier.seacrhSupplier(supplierName) == false);

            do {
                System.out.print("Enter the product code: ");
                sd.setProductCode(sc.nextLine());

                System.out.print("Enter the quantity: ");
                sd.setQty(sc.nextInt());

                System.out.print("Enter the cost price: ");
                sd.setCostPrice(sc.nextDouble());

                System.out.print("Continue? (Y/N) > ");
                cont = sc.next().charAt(0);
            } while (cont == 'Y' || cont == 'y');

            //get invoice no. from supplier invoice class and store in stock details for reference 
            sd.setInvNo(si.getInvNo());

            System.out.print("Enter total amount of invoice: ");
            si.setAmount(sc.nextDouble());

            invoice.add(si);
            stockDetails.add(sd);

            System.out.println("Continue add another invoice? (Y/N) > ");
            cont = sc.next().charAt(0);
        } while (cont == 'Y' || cont == 'y');
    }
}
