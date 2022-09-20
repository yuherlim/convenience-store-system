
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CreditNoteDriver {
    public static void main(String[] args) {
       ArrayList<CreditNote> creditNote = new ArrayList<>();
       ArrayList<StockDetails> stockDetails = new ArrayList<>(); 
       
       //Read and Print File
        creditNote = readFile("creditNote.txt", creditNote, stockDetails);

        for (CreditNote cn : creditNote) {
            System.out.println(cn);
            for (StockDetails stockDets : cn.getStockDetails()) {
                System.out.println(stockDets);
            }
            System.out.println();
        }
    }
    
    public static ArrayList<CreditNote> readFile(String fileName, ArrayList<CreditNote> creditNote, ArrayList<StockDetails> stockDetails) {
        try {
            try (FileReader reader = new FileReader("src\\" + fileName)) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                
                String line;
                
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    String[] buffer = line.split("\\%");
                    String[] string1 = buffer[0].split("\\|");
                    String[] string2 = buffer[1].split("\\|");
                    
                    String cnNo = string1[0];
                    String cnDate = string1[1];
                    String staffName = string1[2];
                    String supplierName = string1[3];
                    
                    //Convert string to double for total amount
                    double[] doubleArr = new double[1];
                    for (int i = 0; i < string2.length; i++) {
                        doubleArr[i] = Double.parseDouble(string2[i]);
                    }
                    
//                //Convert string to integer for stockQty and minReorderQty
//                int[] intArr = new int[2];
//                for (int i = 0; i < string3.length; i++) {
//                    intArr[i] = Integer.parseInt(string3[i]);
//                }
//                //read from stockDetails.txt
//                stockDetails = StockDetailsDriver.readFile("stockDetails.txt", stockDetails);
//
//                //remove array list elements that do not contain the current product ID
//                for (int i = 0; i < stockDetails.size(); i++) {
//                    if (!stockDetails.get(i).getProductCode().equals(invNo)) {
//                        stockDetails.remove(i);
//                    }
//                }
//invoice.add(new SupplierInvoice(invNo, invDate, staffName, supplierName, stockDetails, doubleArr[1]));
                }
            }

        } catch (IOException e) {
        }

        return creditNote;
    }
    
}

//    //Method to add a new invoice
//    public static void addProduct(ArrayList<SupplierInvoice> invoice, SupplierInvoice si) {
//        Scanner sc = new Scanner(System.in);
//        //User input
//        System.out.print("Enter Credit Note date: ");
//        si.setInvDate(sc.nextLine());
//
//        System.out.print("Enter staff name: ");
////        si.setStaffName(sc.nextLine());
//
//        System.out.print("Enter Suplier name: ");
////        si.setSupplierName(sc.next(Supplier));
//
//        System.out.print("Enter total amount of credit note: ");
//        si.setAmount(sc.nextDouble());
//
//        invoice.add(si);
//    }
//}
