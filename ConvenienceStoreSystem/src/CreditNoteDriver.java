
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CreditNoteDriver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection;

        CreditNote cn = new CreditNote();
        StockDetails sd = new StockDetails();

        ArrayList<CreditNote> creditNote = new ArrayList<>();
        ArrayList<StockDetails> stockDetails = new ArrayList<>();

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
                    CreditNoteDriver.addCreditNote(creditNote, cn, stockDetails, sd);
                }
                case 2 -> {
                    //CreditNoteDriver.editCreditNote(creditNote, cn, stockDetails, sd);
                }
                case 3 -> {
                    //CreditNoteDriver.searchCreditNote(creditNote, cn, stockDetails, sd);
                }
                case 4 -> {
                    //CreditNoteDriver.cancelCreditNote(creditNote, cn, stockDetails, sd);
                }
                case 0 -> {
                    //ConvenienceStore.main();
                }
                default ->
                    System.out.println("Invalid input! Please try again..." + '\n');
            }
        } while (selection < 0 || selection > 4);
    }

    public static ArrayList<CreditNote> readFile(String fileName, ArrayList<CreditNote> creditNote, ArrayList<StockDetails> stockDetails) {
        try ( FileReader reader = new FileReader("src\\" + fileName)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");

                String cnNo = string1[0];
                String cnDate = string1[1];
                String staffName = string1[2];
                String supplierName = string1[3];

               
                
                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetails.readFile(StockDetails.fileName).clone();
                stockDetails.clear();
                //Add elements of stock details that is associated with this credit number.
                for (StockDetails sd: allSD) {
                    if(sd.getCnNo().equals(cnNo))
                        stockDetails.add(sd);
                }

                //Convert string to double for total amount
                double doubleArr = Double.parseDouble(buffer[1]);

                creditNote.add(new CreditNote(cnNo, cnDate, staffName, supplierName, (ArrayList<StockDetails>)stockDetails.clone(), doubleArr));
            }
        } catch (IOException e) {
        }

        return creditNote;
    }

    //Method to add a new creditNote / stock return
    public static void addCreditNote(ArrayList<CreditNote> creditNote, CreditNote cn, ArrayList<StockDetails> stockDetails, StockDetails sd) {
        Scanner sc = new Scanner(System.in);
        char cont;
        String staffName;
        String supplierName;

        do {
            System.out.println("-----------------------");
            System.out.println("| Add New Credit Note |");
            System.out.println("-----------------------");

            cn.setCnNo(String.format("CRN-%04d", creditNote.size() + 1));
            System.out.println("Credit Note No.: " + cn.getCnNo());

            System.out.print("Enter credit note date: ");
            cn.setCnDate(sc.nextLine());

            //need compare to Staff class
//            do {
//                System.out.print("Enter staff name: ");
//                staffName = sc.nextLine();
//                if (Staff.seacrhStaff(staffName) == false) {
//                    System.out.println("Invalid staff name! Please try again..");
//                } else {
//                    cn.setStaffName(staffName);
//                }
//            } while (Staff.seacrhStaff(staffName) == false);

            //need compare to Supplier class
//            do {
//                System.out.print("Enter Suplier name: ");
//                supplierName = sc.nextLine();
//                if (Supplier.seacrhSupplier(supplierName) == false) {
//                    System.out.println("Invalid supplier name! Please try again..");
//                } else {
//                    cn.setSupplierName(supplierName);
//                }
//            } while (Supplier.seacrhSupplier(supplierName) == false);

            do {
                System.out.print("Enter the product code: ");
                sd.setProductCode(sc.nextLine());

                System.out.print("Enter the quantity: ");
                sd.setQty(sc.nextInt());

                System.out.print("Enter the cost price: ");
                sd.setCostPrice(sc.nextDouble());

                System.out.print("Continue add item? (Y/N) > ");
                cont = sc.next().charAt(0);
            } while (cont == 'Y' || cont == 'y');

            //get invoice no. from supplier invoice class and store in stock details for reference 
            sd.setInvNo(cn.getCnNo());

            System.out.print("Enter total amount of credit note: ");
            cn.setAmount(sc.nextDouble());

            creditNote.add(cn);
            stockDetails.add(sd);

            System.out.println("Continue add another credit note? (Y/N) > ");
            cont = sc.next().charAt(0);
        } while (cont == 'Y' || cont == 'y');
    }

    public static void editCreditNote() {
        
    }

    public void searchCreditNote() {

    }

    public static void cancelCreditNote() {

    }

}
