/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author New User
 */
public class Sales {
    
    public static void main(String[] args) {
        
        int selection;

        do {
            System.out.println("===================");
            System.out.println(" Sales Transaction ");
            System.out.println("===================");
            System.out.println("1 - Add new Transaction");
            System.out.println("2 - Cancel Transaction");
            System.out.println("3 - Search Transaction");
            System.out.println("4 - View Transaction");
            System.out.println("5 - Daily Transaction Report" + '\n');
            System.out.println("0 - Back to Main Menu" + '\n');

            selection = General.intInput("Enter your selection (0-5)", "  Please input a number only.");

            switch (selection) {
                case 1 -> {
                    TransactionDriver.add();
                }
                case 2 -> {
                    //TransactionDriver.cancel();
                }
                case 3 -> {
                    //TransactionDriver.search();
                }
                case 4 -> {
                    //TransactionDriver.view();
                }
                case 5 -> {
                    //Report.dailyTransactionReport();
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
