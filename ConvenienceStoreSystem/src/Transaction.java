
import java.io.FileWriter;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Yu
 */
public class Transaction {

    private String id;
    private String dateTime;

    private Staff staff;
    private Member member;
    private Payment payment;
    private ArrayList<TransactionDetails> transactionDetails;
    private static int numOfTransaction;
    public static final String FILE_NAME = "transaction.txt";

    //Constuctor
    public Transaction() {
    }

    public Transaction(Transaction t) {
        this.id = t.getId();
        this.staff = t.getStaff();
        this.member = t.getMember();
        this.payment = t.getPayment();
        this.transactionDetails = t.getTransactionDetails();
        this.dateTime = t.dateTime;
    }

    public Transaction(String id, Staff staff, Member member, Payment payment, ArrayList<TransactionDetails> transactionDetails, String dateTime) {

        this.id = id;
        this.staff = staff;
        this.member = member;
        this.payment = payment;
        this.transactionDetails = transactionDetails;
        this.dateTime = dateTime;
    }

    //Setter and getter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;

    }

    public static int getNumOfTransaction() {
        return numOfTransaction;
    }

    public static void setNumOfTransaction(int numOfTransaction) {
        Transaction.numOfTransaction = numOfTransaction;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public static void search() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        TransactionDriver.readFile(Transaction.FILE_NAME, transactions);
        Transaction transaction = new Transaction();
        String transactionID;
        char charSelection;
        int loop;

        do {

            loop = 0;
            do {
                loop = 0;
                transactionID = General.stringNullCheckingInput("Transaction ID                     : ", "  Invalid transaction ID").toUpperCase();

                if (transactionID.length() == 12) {
                    if (transactionID.charAt(0) != 'T') {
                        System.out.println("  Invalid transaction id format");
                        loop = 1;
                        continue;
                    }
                    if (General.dateChecking(String.format("%s/%s/19%s", transactionID.substring(5, 7), transactionID.substring(3, 5), transactionID.substring(1, 3))) == false) {
                        System.out.println("  Invalid transaction id date format");
                        loop = 1;
                        continue;
                    }

                    if (transactionID.charAt(7) != '-') {
                        System.out.println("  Invalid transaction id format");
                        loop = 1;
                        continue;
                    }

                    for (int i = 8; i < transactionID.length(); i++) {
                        if (Character.isDigit(transactionID.charAt(i)) != true) {
                            System.out.println("  Invalid transaction id format");
                            loop = 1;
                        }
                    }

                } else {
                    System.out.println("  Invalid transaction id format");
                    loop = 1;
                }

            } while (loop == 1);

            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getId().equals(transactionID)) {
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.println("| Transaction ID | Member ID |  Payment ID  | Amount(RM) |                Staff Name                | ");
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.printf("|  %12s  | %9s | %12s | %7.2f    | %-40s |\n", transactions.get(i).getId(), transactions.get(i).getMember().getId(), transactions.get(i).getPayment().getPaymentId(), (transactions.get(i).getPayment().getAmount() + transactions.get(i).getPayment().getRounding() + transactions.get(i).getPayment().getSst() - transactions.get(i).getPayment().getDiscount()), transactions.get(i).getStaff().getStaffID());
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    charSelection = Character.toUpperCase(General.yesNoInput("Print receipt (Y/N) : ", "  Invalid Input"));

                    if (charSelection == 'Y') {

                        Payment.receipt(transactions.get(i).payment, transactions.get(i).transactionDetails, transactions.get(i).dateTime);

                    }
                    loop = 0;
                    break;
                } else {
                    loop = 1;
                }
            }

            if (loop == 1) {
                System.out.println(" Transaction ID not found");
            }

            charSelection = General.yesNoInput("Search transaction (Y/N) : ", "  Invalid Input");

        } while (charSelection == 'Y');
    }

    public static double displayTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        Product printProduct = new Product();
        double subtotal;
        double total = 0;

        System.out.println("====================================================================");
        System.out.println("| No  | Code  | Product Name         | Price | Quantity | Subtotal |");
        System.out.println("====================================================================");

        for (int i = 0; i < transactionDetails.size(); i++) {

            printProduct = Product.search("productCode", transactionDetails.get(i).getProductCode()).get(0);

            subtotal = printProduct.getCurrentSellingPrice() * transactionDetails.get(i).getQty();
            System.out.printf("| %03d | %-5s | %-20s | %5.2f | %8d | %8.2f |\n", (i + 1), printProduct.getCode(), printProduct.getName(), printProduct.getCurrentSellingPrice(), transactionDetails.get(i).getQty(), subtotal);

            total += subtotal;
        }
        System.out.println("====================================================================");
        System.out.printf("                                                    Total = %7.2f\n", total);
        System.out.println("====================================================================");

        return total;
    }

    //Write file function
    public static void writeFile(String fileName, ArrayList<Transaction> transactions) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < transactions.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s|%s\n", transactions.get(i).getId(), transactions.get(i).getStaff().getStaffID(), transactions.get(i).getMember().getId(), transactions.get(i).getPayment().getPaymentId(), transactions.get(i).getDateTime());
                //Writes the record to the file.
                writer.write(line);
            }

            // Closes the writer
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
