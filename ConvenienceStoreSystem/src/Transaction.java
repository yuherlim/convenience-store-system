
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
    private Staff staff;
    private Member member;
    private Payment payment;
    private ArrayList<TransactionDetails> transactionDetails;
    private static int numOfTransaction;
    private String status;

    //Constuctor
    public Transaction(){};
    
    public Transaction(Transaction t){
        this.id = t.getId();
        this.staff = t.getStaff();
        this.member = t.getMember();
        this.payment = t.getPayment();
        this.transactionDetails = t.getTransactionDetails();
        this.status = t.getStatus() ;
    };
    
    public Transaction(String id, Staff staff, Member member, Payment payment, ArrayList<TransactionDetails> transactionDetails,String status) {
        this.id = id;
        this.staff = staff;
        this.member = member;
        this.payment = payment;
        this.transactionDetails = transactionDetails;
        this.status = status;
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

    public static int getNumOfTransaction() {
        return numOfTransaction;
    }

    public static void setNumOfTransaction(int numOfTransaction) {
        Transaction.numOfTransaction = numOfTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
    
    public static void cancel(){
    
    
    }
    
     //Write file function
    public static void writeFile(String fileName, ArrayList<Transaction> transactions) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < transactions.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s|%s\n", transactions.get(i).getId(), transactions.get(i).getStaff().getStaffID(), transactions.get(i).getMember().getId(), transactions.get(i).getPayment().getPaymentId(),transactions.get(i).getStatus());
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
