
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
    private ArrayList<Product> products;

    public Transaction(String id, Staff staff, Member member, Payment payment, ArrayList<TransactionDetails> transactionDetails, ArrayList<Product> products) {
        this.id = id;
        this.staff = staff;
        this.member = member;
        this.payment = payment;
        this.transactionDetails = transactionDetails;
        this.products = products;
    }
    
    
    
}
