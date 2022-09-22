
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
public class Payment {

    private String paymentId;
    private String paymentType;
    private double amount;
    private double amountPaid;
    private final static double sstRate = 0.06;
    private final static double discountRate = 0.10;

    //Constructor
    public Payment(String paymentId, String paymentType, double amount, double amountPaid) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.amount = amount;
        this.amountPaid = amountPaid;
    }

    //Setter and getter
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public static void pay() {
        //Pass in transaction object and subtotal return payment object
        //Ask user on which payment type to pay
        //Ask user to enter amount paid
        //Display receipt
        //write file
    }

    public static void receipt() {
        //Display receipt
    }

    public static Payment search(String paymentId) {
        ArrayList<Payment> payments = new ArrayList<>();
        readFile("payment", payments);
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentId().equals(paymentId)) {
                return payments.get(i);
            }
        }
        return null;
    }

    public static void delete(String paymentId) {
    }

    //Write file function
    public static void writeFile(String fileName, ArrayList<Payment> payments) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < payments.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%.2f|%.2f\n", payments.get(i).getPaymentId(), payments.get(i).getPaymentType(), payments.get(i).getAmount(), payments.get(i).getAmountPaid());
                //Writes the record to the file.
                writer.write(line);
            }

            // Closes the writer
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //Read file function
    public static void readFile(String fileName, ArrayList<Payment> payments) {
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\|");
                String paymentId = buffer[0];
                String paymentType = buffer[1];
                String amount = buffer[2];
                String amountPaid = buffer[3];

                payments.add(new Payment(paymentId, paymentType, Double.parseDouble(amount), Double.parseDouble(amountPaid)));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
