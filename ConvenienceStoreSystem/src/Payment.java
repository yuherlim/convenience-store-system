
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
    private double discount;
    private double sst;
    private double rounding;
    private double amount;
    private double amountPaid;
    private final static double sstRate = 0.06;
    private final static double discountRate = 0.10;
    static String fileName = "payment";

    //Constructor
    public Payment() {
        this.paymentId = " ";
        this.paymentType = " ";
        this.discount = 0.0;
        this.sst = 0.0;
        this.rounding = 0.0;
        this.amount = 0.0;
        this.amountPaid = 0.0;
    }

    public Payment(Payment obj) {
        this.paymentId = obj.paymentId;
        this.paymentType = obj.paymentType;
        this.discount = obj.discount;
        this.sst = obj.sst;
        this.rounding = obj.rounding;
        this.amount = obj.amount;
        this.amountPaid = obj.amountPaid;

    }

    public Payment(String paymentId, String paymentType, double discount, double sst, double rounding, double amount, double amountPaid) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.discount = discount;
        this.sst = sst;
        this.rounding = rounding;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSst() {
        return sst;
    }

    public void setSst(double sst) {
        this.sst = sst;
    }

    public double getRounding() {
        return rounding;
    }

    public void setRounding(double rounding) {
        this.rounding = rounding;
    }

    public static Payment pay(ArrayList<TransactionDetails> transactionDetails, String discountYesOrNo) {
        ArrayList<Payment> payments = new ArrayList<>();
        Payment.readFile(fileName, payments);
        Payment payment = new Payment();
        double netTotal;
        int intSelection;
        int loop = 0;

        do {
            loop = 0;

            payment.amount = Transaction.displayTransactionDetails(transactionDetails);

            payment.paymentId = "P" + General.getCurrentDateTime("yymmdd") + String.format("-%04d", (Transaction.getNumOfTransaction() + 1));

            if (discountYesOrNo.equals("Yes")) {
                payment.discount = payment.amount * Payment.discountRate;
                payment.sst = (payment.amount - payment.discount) * Payment.sstRate;
                netTotal = Math.ceil(((payment.amount - payment.discount - payment.sst) * 20)) / 20;
                payment.rounding = (payment.amount - payment.discount - payment.sst) - netTotal;
                System.out.printf("                                        Discount (10%) = (%6.2f)\n", payment.discount);
                System.out.println("====================================================================");
                System.out.printf("                               Subtotal after discount = (%6.2f)\n", (payment.amount - payment.discount));
                System.out.printf("                                              SST (6%) = (%6.2f)\n", payment.sst);
                System.out.printf("                                         Rounding (RM) =  %6.2f\n", payment.rounding);
                System.out.println("====================================================================");
                System.out.printf("                                         NetTotal (RM) =  %6.2f\n", netTotal);
                System.out.println("====================================================================");
            } else {
                payment.discount = 0.00;
                payment.sst = (payment.amount - payment.discount) * Payment.sstRate;
                netTotal = Math.ceil(((payment.amount - payment.sst) * 20)) / 20;
                payment.rounding = (payment.amount - payment.sst) - netTotal;

                System.out.printf("                                              SST (6%) = (%6.2f)\n", payment.sst);
                System.out.printf("                                         Rounding (RM) =  %6.2f\n", payment.rounding);
                System.out.println("====================================================================");
                System.out.printf("                                         NetTotal (RM) =  %6.2f\n", netTotal);
                System.out.println("====================================================================");

            }

            System.out.println("Payment method");
            System.out.println("1.Cash");
            System.out.println("2.Bank transfer");
            System.out.println("3.QR pay");
            intSelection = General.intInput("Selection : ", "  Invalid selection");

            switch (intSelection) {
                case 1:
                    payment.setPaymentType("Cash");
                    do {
                        loop = 0;
                        payment.amountPaid = General.doubleInput("Amount Paid : ", "  Invalid input");

                        if (payment.amountPaid < netTotal) {
                            System.out.println("  Insufficient paid amount");
                            loop = 1;
                        } else {
                            System.out.printf("Amount Paid : %4.2f \n", payment.amountPaid);
                            System.out.printf("NetTotal    : %4.2f \n", netTotal);
                            System.out.printf("Change      : %4.2f \n", (payment.amountPaid - netTotal));
                        }
                    } while (loop == 1);

                    break;
                case 2:
                    payment.setPaymentType("Bank transfer");
                    break;
                case 3:
                    payment.setPaymentType("QR pay");
                    break;
                default:
                    loop = 1;
                    System.out.println("Invalid input");
                    break;
            }
        } while (loop == 1);

        if (discountYesOrNo.equals("Yes")) {
            receiptWithDiscount(payment, transactionDetails);
        } else {
            receiptWithNoDiscount(payment, transactionDetails);
        }
        
        payments.add(new Payment(payment));
        writeFile(fileName,payments);
        return payment;
    }

    public static void receiptWithDiscount(Payment payment, ArrayList<TransactionDetails> transactionDetails) {
        System.out.println("========================");
        System.out.println("Logo");
        System.out.println("========================");
        System.out.println("Address");

        System.out.println("Date Time  : " + General.getCurrentDateTime("DateTime"));
        System.out.println("Receipt ID : " + payment.paymentId);
        System.out.println("Cashier    : ");

        Transaction.displayTransactionDetails(transactionDetails);

        System.out.printf("                                        Discount (10%) = (%6.2f)\n", payment.discount);
        System.out.println("====================================================================");
        System.out.printf("                               Subtotal after discount = (%6.2f)\n", (payment.amount - payment.discount));
        System.out.printf("                                              SST (6%) = (%6.2f)\n", payment.sst);
        System.out.printf("                                         Rounding (RM) =  %6.2f\n", payment.rounding);
        System.out.println("====================================================================");
        System.out.printf("                                      NetTotal    (RM) =  %6.2f\n", (payment));
        System.out.printf("                                      Amount paid (RM) =  %6.2f\n", payment.amountPaid);
        System.out.printf("                                      Change      (RM) =  %6.2f\n", (payment.amount - payment.amountPaid));
        System.out.println("====================================================================");

    }

    public static void receiptWithNoDiscount(Payment payment, ArrayList<TransactionDetails> transactionDetails) {
        System.out.println("========================");
        System.out.println("Logo");
        System.out.println("========================");
        System.out.println("Address");

        System.out.println("Date Time  : " + General.getCurrentDateTime("DateTime"));
        System.out.println("Receipt ID : " + payment.paymentId);
        System.out.println("Cashier    : ");

        Transaction.displayTransactionDetails(transactionDetails);

        System.out.printf("                                              SST (6%) = (%6.2f)\n", payment.sst);
        System.out.printf("                                         Rounding (RM) =  %6.2f\n", payment.rounding);
        System.out.println("====================================================================");
        System.out.printf("                                      NetTotal    (RM) =  %6.2f\n", (payment));
        System.out.printf("                                      Amount paid (RM) =  %6.2f\n", payment.amountPaid);
        System.out.printf("                                      Change      (RM) =  %6.2f\n", (payment.amount - payment.amountPaid));
        System.out.println("====================================================================");

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

    //Write file function
    public static void writeFile(String fileName, ArrayList<Payment> payments) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < payments.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%.2f|%.2f|%.2f|%.2f|%.2f\n", payments.get(i).getPaymentId(), payments.get(i).getPaymentType(), payments.get(i).getDiscount(),payments.get(i).getSst(),payments.get(i).getRounding(),payments.get(i).getAmount(), payments.get(i).getAmountPaid());
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
                String[] buffer = line.split("\\%");
                String string1[] = buffer[0].split("\\|");
                String paymentId = string1[0];
                String paymentType = string1[1];
                
                String double1[] = buffer[1].split("\\|");
                String discount = double1[0];
                String sst = double1[1];
                String rounding = double1[2];
                String amount = double1[3];
                String amountPaid = double1[4];
          
                payments.add(new Payment(paymentId, paymentType, Double.parseDouble(discount),Double.parseDouble(sst),Double.parseDouble(rounding),Double.parseDouble(amount), Double.parseDouble(amountPaid)));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
