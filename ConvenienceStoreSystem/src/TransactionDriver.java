
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
        int selection;
        do {

            System.out.println("---------------");
            System.out.println("| Transaction |");
            System.out.println("---------------");
            //System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');
            Staff.updateNumberOfStaff();
            System.out.println("1 - Add transaction");
            System.out.println("2 - Display Staff");
            System.out.println("3 - Search transaction");
            System.out.println("0 - Main Menu");
            do {
                selection = General.intInput("Selection: ", "  Please only select 0~4.");
                switch (selection) {
                    case 1 ->
                        add();
                    case 2 ->
                        add();
                    case 3 ->
                        Transaction.search();
                    case 0 -> {
                        System.out.println("****************************");
                        System.out.println("Return to Main Menu.");
                        General.systemPause();
                    }
                    default ->
                        System.out.println("  Please only select 0~3.");
                }
            } while (selection < 0 || selection > 4);
            General.clearScreen();
        } while (selection != 0);
    }

    public static void add() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
        TransactionDriver.readFile(Transaction.FILE_NAME, transactions);
        MemberDriver.readFile(Member.FILE_NAME, members);

        Transaction addTransaction = new Transaction();
        TransactionDetails addTransactionDetails = new TransactionDetails();
        Product addProduct = new Product();
        Member member = new Member();
        Payment payment = new Payment();
        int loop;
        char charSelection;
        int intSelection;
        int orderQty;
        String ic;

        do {
            System.out.println("===================");
            System.out.println("| Add transaction |");
            System.out.println("===================");

            addTransaction.setDateTime(General.getCurrentDateTime("dateTime"));

            addTransaction.setId("T" + General.getCurrentDateTime("yymmdd") + String.format("-%04d", (Transaction.getNumOfTransaction() + 1)));

            addTransaction.setStaff(Staff.searchAllStaff("Cuurent Staff", "Staff ID"));

            System.out.println("Transaction ID                     : " + addTransaction.getId());
            loop1:
            do {
                loop = 0;
                addTransactionDetails.setProductCode(ProductDriver.codeInput());

                addProduct = Product.search("productCode", addTransactionDetails.getProductCode()).get(0);

                if (addProduct == null) {
                    System.out.println("  Product not found");
                    charSelection = General.yesNoInput("Add product (Y/N) : ", "  Invalid Selection");
                    continue;
                } else {
                    if (addProduct.getStatus().equals("ACTIVE") == false) {
                        System.out.println("  Product not active");
                        charSelection = General.yesNoInput("Add product (Y/N) : ", "  Invalid Selection");
                        continue;
                    } else {
                        if (addProduct.getStockQty() == 0) {
                            System.out.println("  Product sold out");
                            charSelection = General.yesNoInput("Add product (Y/N) : ", "  Invalid Selection");
                            continue;
                        }
                    }
                }
                loop:
                for (int i = 0; i < transactionDetails.size(); i++) {
                    if (transactionDetails.get(i).getProductCode().equals(addProduct.getCode())) {
                        do {
                            System.out.println("1.Modfity product quantity");
                            System.out.println("2.Remove product");
                            System.out.println("0.Exit");
                            intSelection = General.intInput("Product selection: ", "  Invalid Selection");

                            switch (intSelection) {

                                case 1:
                                    do {
                                        loop = 0;
                                        orderQty = General.intInput("Product quantity (0 - to cancel) : ", "  Invalid quantity input");

                                        if (orderQty < 0) {
                                            System.out.println("  Invalid order quantity");
                                            loop = 1;
                                        } else if (orderQty == 0) {
                                            if (transactionDetails.size() > 1) {
                                                transactionDetails.remove(i);
                                                break loop;
                                            } else {
                                                transactionDetails.remove(i);
                                                charSelection = 'Y';
                                                continue loop1;
                                            }
                                        } else if (orderQty > addProduct.getStockQty()) {
                                            System.out.println("Product stock not enough");
                                            System.out.println("  Stock Qty : " + addProduct.getStockQty());
                                            loop = 1;
                                        } else {
                                            transactionDetails.get(i).setQty(orderQty);
                                        }
                                    } while (loop == 1);
                                    break;
                                case 2:
                                    if (transactionDetails.size() > 1) {
                                        transactionDetails.remove(i);
                                    } else {
                                        transactionDetails.remove(i);
                                        charSelection = 'Y';
                                        continue loop1;
                                    }
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("  Invalid Selection");
                                    loop = 1;
                                    break;
                            }
                        } while (loop == 1);
                    } else {
                        do {
                            loop = 0;
                            orderQty = General.intInput("Product quantity : ", "  Invalid quantity input");
                            if (orderQty < 0) {
                                System.out.println("  Invalid order quantity");
                                loop = 1;
                            } else if (orderQty == 0) {
                              
                            } else if (orderQty > addProduct.getStockQty()) {
                                System.out.println("Product stock not enough");
                                System.out.println("  Stock Qty : " + addProduct.getStockQty());
                                loop = 1;
                            } else {
                                transactionDetails.add(new TransactionDetails(orderQty, addTransaction.getId(), addProduct.getCode(), addProduct.getCurrentSellingPrice()));
                                break loop;
                            }
                        } while (loop == 1);
                    }
                }

                if (transactionDetails.size() == 0) {
                    do {
                        loop = 0;
                        orderQty = General.intInput("Product quantity : ", "  Invalid quantity input");
                        if (orderQty < 0) {
                            System.out.println("  Invalid order quantity");
                            loop = 1;
                        } else if (orderQty == 0) {
                            charSelection = 'Y';
                            continue loop1;
                        } else if (orderQty > addProduct.getStockQty()) {
                            System.out.println("Product stock not enough");
                            System.out.println("  Stock Qty : " + addProduct.getStockQty());
                            loop = 1;
                        } else {
                            transactionDetails.add(new TransactionDetails(orderQty, addTransaction.getId(), addProduct.getCode(), addProduct.getCurrentSellingPrice()));
                        }

                    } while (loop == 1);
                }

                Transaction.displayTransactionDetails(transactionDetails);
                charSelection = General.yesNoInput("Add product (Y/N) : ", "  Invalid Selection");

            } while (charSelection
                    == 'Y');

            confirm:
            do {
                charSelection = Character.toUpperCase(General.charInput("Confirm transaction (Y/N) X - Cancel: ", "  Invalid Selection"));

                switch (charSelection) {
                    case 'Y':
                        do {
                            loop = 0;

                            charSelection = General.yesNoInput("Membership (Y/N) : ", "  Invalid Selection");
                            if (charSelection == 'N') {
                                addTransaction.setMember(new Member());
                                payment = Payment.pay(transactionDetails, "No", addTransaction.getDateTime());
                                continue;
                            }

                            ic = General.stringNullCheckingInput("Member IC                     : ", "  Invalid Member IC");
                            if (General.icValidation(ic) == false) {
                                loop = 1;
                            } else {
                                if (Member.searchObj(ic, "IC", "All", members).size() != 0) {
                                    member = Member.searchObj(ic, "IC", "All", members).get(0);
                                    if (member.getStatus().equals("Active")) {
                                        addTransaction.setMember(new Member(member));
                                        payment = Payment.pay(transactionDetails, "Yes", addTransaction.getDateTime());
                                    } else {
                                        System.out.println("  Member inactive");
                                        loop = 1;
                                    }
                                } else {
                                    System.out.println("  Member not found");
                                    loop = 1;
                                }

                            }
                        } while (loop == 1);
                        addTransaction.setPayment(new Payment(payment));
                        addTransaction.setTransactionDetails((ArrayList<TransactionDetails>) transactionDetails.clone());
                        transactions.add(new Transaction(addTransaction));
                        Transaction.setNumOfTransaction(Transaction.getNumOfTransaction() + 1);
                        TransactionDetails.add(transactionDetails);
                        Product.updateQuantity();

                        break;
                    case 'N':
                        do {
                            loop = 0;
                            Transaction.displayTransactionDetails(transactionDetails);
                            intSelection = General.intInput("Product selection : ", "  Invalid selection input");
                            if (intSelection <= 0 || intSelection > transactionDetails.size()) {
                                System.out.println("  Invalid selection");
                                loop = 1;
                            } else {
                                do {
                                    addProduct = Product.search("productCode", transactionDetails.get(intSelection - 1).getProductCode()).get(0);
                                    System.out.println("1.Modfity product quantity");
                                    System.out.println("2.Remove product");
                                    System.out.println("0.Exit");
                                    intSelection = General.intInput("Selection: ", "  Invalid Selection");

                                    switch (intSelection) {

                                        case 1:
                                            do {
                                                loop = 0;
                                                orderQty = General.intInput("Product quantity : ", "  Invalid quantity input");

                                                if (orderQty < 0) {
                                                    System.out.println("  Invalid order quantity");
                                                    loop = 1;
                                                } else if (orderQty == 0) {
                                                    if (transactionDetails.size() > 1) {
                                                        transactionDetails.remove(intSelection - 1);
                                                        Transaction.displayTransactionDetails(transactionDetails);
                                                        loop = 1;
                                                        continue confirm;
                                                    } else {
                                                        System.out.println("Transaction cancelled");
                                                    }
                                                } else if (orderQty > addProduct.getStockQty()) {
                                                    System.out.println("Product stock not enough");
                                                    System.out.println("  Stock Qty : " + addProduct.getStockQty());
                                                    loop = 1;
                                                } else {
                                                    transactionDetails.get(intSelection - 1).setQty(orderQty);
                                                    Transaction.displayTransactionDetails(transactionDetails);
                                                    loop = 1;
                                                    continue confirm;
                                                }
                                            } while (loop == 1);
                                            break;
                                        case 2:
                                            if (transactionDetails.size() > 1) {
                                                transactionDetails.remove(intSelection - 1);
                                                Transaction.displayTransactionDetails(transactionDetails);
                                                loop = 1;
                                                continue confirm;
                                            } else {
                                                System.out.println("Transaction cancelled");
                                            }
                                            break;
                                        case 0:
                                            loop = 1;
                                            Transaction.displayTransactionDetails(transactionDetails);
                                            continue confirm;
                                        default:
                                            System.out.println("  Invalid Selection");
                                            loop = 1;
                                            break;
                                    }
                                } while (loop == 1);
                            }
                        } while (loop == 1);
                        break;
                    case 'X':
                        System.out.println("Transaction cancelled");
                        break;
                    default:
                        loop = 1;
                        System.out.println("  Invalid selection");
                        break;
                }
            } while (loop == 1);

            charSelection = General.yesNoInput("Add transaction (Y/N) : ", "  Invalid Selection");

            transactionDetails.clear();

        } while (charSelection == 'Y');

        Transaction.writeFile(Transaction.FILE_NAME, transactions);

    }

    //Read file function
    public static void readFile(String fileName, ArrayList<Transaction> transactions) {
        try {
            ArrayList<TransactionDetails> transactionDetails;
            ArrayList<TransactionDetails> newTransactionDetails = new ArrayList<>();
            ArrayList<Member> members = new ArrayList<>();
            MemberDriver.readFile(Member.FILE_NAME, members);
            transactionDetails = TransactionDetails.readFile(TransactionDetails.FILE_NAME);

            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            Transaction.setNumOfTransaction(0);

            while ((line = bufferedReader.readLine()) != null) {

                String[] buffer = line.split("\\|");
                String transactionId = buffer[0];
                String staffId = buffer[1];
                String memberId = buffer[2];
                String paymentId = buffer[3];
                String dateTime = buffer[4];
                newTransactionDetails.clear();
                for (int i = 0; i < transactionDetails.size(); i++) {
                    if (transactionDetails.get(i).getTransactionId().equals(transactionId)) {
                        newTransactionDetails.add(new TransactionDetails(transactionDetails.get(i)));
                    }

                }

                if (transactionId.substring(1, 7).equals(General.getCurrentDateTime("yymmdd"))) {
                    Transaction.setNumOfTransaction(Transaction.getNumOfTransaction() + 1);
                }

                if (memberId.equals(" ")) {
                    transactions.add(new Transaction(transactionId, Staff.searchAllStaff(staffId, "Staff ID"), new Member(), Payment.search(paymentId), (ArrayList<TransactionDetails>) newTransactionDetails.clone(), dateTime));
                } else {
                    transactions.add(new Transaction(transactionId, Staff.searchAllStaff(staffId, "Staff ID"), Member.searchObj(memberId, "ID", "All", members).get(0), Payment.search(paymentId), (ArrayList<TransactionDetails>) newTransactionDetails.clone(), dateTime));

                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
