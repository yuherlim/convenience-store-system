
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ong58
 */
public class TransactionDriver {

    public static void main(String[] args) {

    }

    public static void add() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<TransactionDetails> transactionDetails;
        ArrayList<Product> products;
        products = Product.readFile(Product.fileName);
        TransactionDriver.readFile("transaction", transactions);
        MemberDriver.readFile("member", members);

        
        Transaction addTransaction = new Transaction();
        TransactionDetails addTransactionDetails = new TransactionDetails();
        Product addProduct = new Product();
        int loop;

        System.out.println("=================");
        System.out.println("|Add transaction|");
        System.out.println("=================");

        addTransaction.setId("T" + General.getCurrentDateTime("yymmdd") + String.format("-%04d", (Member.getNumOfCustomer() + 1)));

        System.out.println("Transaction ID                     : " + addTransaction.getId());
        
        do{
        loop = 0;
        addTransactionDetails.setProductCode(ProductDriver.codeInput());
        for{int = 0; i < products.size(); i++}
        
        
        
        }while(loop == 1);
        
        
        

    }

    //Read file function
    public static void readFile(String fileName, ArrayList<Transaction> transactions) {
        try {
            ArrayList<TransactionDetails> transactionDetails;
            ArrayList<Member> members = new ArrayList<>();
            MemberDriver.readFile("member", members);

            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            Transaction.setNumOfTransaction(0);

            while ((line = bufferedReader.readLine()) != null) {
                transactionDetails = TransactionDetails.readFile("transactionDetails");

                String[] buffer = line.split("\\|");
                String transactionId = buffer[0];
                String staffId = buffer[1];
                String memberId = buffer[2];
                String paymentId = buffer[3];
                String transactionStatus = buffer[3];

                for (int i = 0; i < transactionDetails.size(); i++) {
                    if (!transactionDetails.get(i).getTransactionId().equals(transactionId)) {
                        transactionDetails.remove(i);
                    }

                }

                if (transactionId.substring(1, 7).equals(General.getCurrentDateTime("yymmdd"))) {
                    Transaction.setNumOfTransaction(Transaction.getNumOfTransaction() + 1);
                }

                transactions.add(new Transaction(transactionId, Staff.searchAllStaff(staffId, "Staff ID"), Member.searchObj(memberId, "ID", "All", members).get(0), Payment.search(paymentId), (ArrayList<TransactionDetails>)transactionDetails.clone(), transactionStatus));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
