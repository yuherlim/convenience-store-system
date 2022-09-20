
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierInvoiceDriver {

    public static void main(String[] args) {
        
        SupplierInvoice si = new SupplierInvoice();
        StockDetails sd = new StockDetails();
        ArrayList<SupplierInvoice> invoice = new ArrayList<>();
        ArrayList<StockDetails> stockDetails = new ArrayList<>();

        SupplierInvoiceDriver.addInvoice(invoice, si, stockDetails, sd);

//        //Read and Print File
//        invoice = readFile("invoice.txt", invoice, stockDetails);
//
//        for (SupplierInvoice si : invoice) {
//            System.out.println(si);
//            for (StockDetails stockDets : si.getStockDetails()) {
//                System.out.println(stockDets);
//            }
//            System.out.println();
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
