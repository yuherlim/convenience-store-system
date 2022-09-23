/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author New User
 */
public class Inventory {

    public static void main(String[] args) {
        
        int selection;

        do {
            System.out.println("======================");
            System.out.println(" Inventory Management ");
            System.out.println("======================");
            System.out.println("1. Stock In");
            System.out.println("2. Stock Return");
            System.out.println("3. Product Reorder Report");
            System.out.println("4. Summary Report");

            selection = General.intInput("Enter your selection (0-4)", "  Please input a number only.");

            switch (selection) {
                case 1 -> {
                    SupplierInvoiceDriver.main(args);
                }
                case 2 -> {
                    CreditNoteDriver.main(args);
                }
                case 3 -> {
                    Report.productReorderReport();
                }
                case 4 -> {
                    //Report.monthlyStockInReport();
                }
                case 0 -> {
                    FrontDriver.main(args);
                }
                default ->
                    System.out.println("Invalid input! Please enter 0-4 only." + '\n');
            }
        } while (selection != 0);

    }
}
