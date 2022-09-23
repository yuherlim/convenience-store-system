
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
    static String fileName = "transaction.txt";

    //Constuctor
    public Transaction(){};
    
    public Transaction(Transaction t){
        this.id = t.getId();
        this.staff = t.getStaff();
        this.member = t.getMember();
        this.payment = t.getPayment();
        this.transactionDetails = t.getTransactionDetails();
        this.dateTime = t.dateTime;
    };
    
    public Transaction(String id, Staff staff, Member member, Payment payment, ArrayList<TransactionDetails> transactionDetails,String dateTime) {

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
    
    public void search(){
   
    
    
    }
    

    public static double displayTransactionDetails(ArrayList<TransactionDetails> transactionDetails){
        Product printProduct = new Product();
        double subtotal ;
        double total = 0 ;
        
        System.out.println("====================================================================");
        System.out.println("| No  | Code  | Product Name         | Price | Quantity | Subtotal |");
        System.out.println("====================================================================");
        
        for(int i = 0 ; i< transactionDetails.size() ; i ++){
        
        printProduct =  Product.search("productCode", transactionDetails.get(i).getProductCode()).get(0);
            
        subtotal =  printProduct.getCurrentSellingPrice()* transactionDetails.get(i).getQty();
        System.out.printf("| %03d | %-5s | %-20s | %5.2f | %8d | %8.2f |\n",(i+1),printProduct.getCode(),printProduct.getName(),printProduct.getCurrentSellingPrice(),transactionDetails.get(i).getQty(),subtotal);
        
        total += subtotal;
        }
         System.out.println("====================================================================");
         System.out.printf("                                                    Total = %7.2f\n",total);
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
                line = String.format("%s|%s|%s|%s|%s\n", transactions.get(i).getId(), transactions.get(i).getStaff().getStaffID(), transactions.get(i).getMember().getId(), transactions.get(i).getPayment().getPaymentId(),transactions.get(i).getDateTime());
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
