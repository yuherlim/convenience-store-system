
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
public class StockDetailsDriver {
    
    
    //reads file and returns StockDetails array list.
    public static ArrayList<StockDetails> readFile(String fileName, ArrayList<StockDetails> stockDetails) {
        try {
            FileReader reader = new FileReader("src\\stockDetails.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] buffer = line.split("\\%");
                //first string is product name, second string is either invoice number or credit note number
                String[] string1 = buffer[0].split("\\|");
                String string2 = buffer[1];
                String string3 = buffer[2];   
                
                //Convert string to integer for transaction details quantity.
                int qty = Integer.parseInt(string2);
                
                //Convert string to double for transaction details selling price
                double costPrice = Double.parseDouble(string3);
                
                if (string1[1].substring(0,1).equals("I"))
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, "", string1[1]));
                else
                    stockDetails.add(new StockDetails(string1[0], qty, costPrice, string1[1], ""));
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return stockDetails;
    }
}
