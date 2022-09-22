
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
 * @author Yu
 */
public class TransactionDetailsDriver {
    
    
    
//    //reads file and returns TransactionDetails array list.
//    public static ArrayList<TransactionDetails> readFile(String fileName, ArrayList<TransactionDetails> transactionDetails) {
//        try {
//            FileReader reader = new FileReader("src\\transactionDetails.txt");
//            BufferedReader bufferedReader = new BufferedReader(reader);
// 
//            String line;
// 
//            while ((line = bufferedReader.readLine()) != null) {
////                System.out.println(line);
//                String[] buffer = line.split("\\%");
//                String[] codes = buffer[0].split("\\|");
//                String string2 = buffer[1];
//                String string3 = buffer[2];   
//                
//                //Convert string to integer for transaction details quantity.
//                int qty = Integer.parseInt(string2);
//                
//                //Convert string to double for transaction details selling price
//                double sellingPrice = Double.parseDouble(string3);
//                
//                transactionDetails.add(new TransactionDetails(qty, codes[1], codes[0], sellingPrice));
//            }
//            reader.close();
// 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        return transactionDetails;
//    }
}
