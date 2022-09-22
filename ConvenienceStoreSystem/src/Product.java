
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Yu
 */
public class Product {

    private String code;
    private String name;
    private double currentSellingPrice;
    private double currentCostPrice;
    private int stockQty;
    private int minReorderQty;
    private String category;
    private ArrayList<TransactionDetails> transactionDetails;
    private ArrayList<StockDetails> stockDetails;
    private String status;

    //File name to store product records.
    public static String fileName = "products.txt";

    //Constructor
    public Product() {
        this.code = "";
        this.name = "";
        this.category = "";
        this.status = "";
    }

    public Product(Product p) {
        this.code = p.code;
        this.name = p.name;
        this.currentSellingPrice = p.currentSellingPrice;
        this.currentCostPrice = p.currentCostPrice;
        this.stockQty = p.stockQty;
        this.minReorderQty = p.minReorderQty;
        this.category = p.category;
        this.transactionDetails = p.transactionDetails;
        this.stockDetails = p.stockDetails;
        this.status = p.status;
    }

    public Product(String code, String name, double currentSellingPrice, double currentCostPrice, int stockQty, int minReorderQty, String category, ArrayList<TransactionDetails> transactionDetails, ArrayList<StockDetails> stockDetails, String status) {
        this.code = code;
        this.name = name;
        this.currentSellingPrice = currentSellingPrice;
        this.currentCostPrice = currentCostPrice;
        this.stockQty = stockQty;
        this.minReorderQty = minReorderQty;
        this.category = category;
        this.transactionDetails = transactionDetails;
        this.stockDetails = stockDetails;
        this.status = status;
    }

    //getter and setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentSellingPrice() {
        return currentSellingPrice;
    }

    public void setCurrentSellingPrice(double currentSellingPrice) {
        this.currentSellingPrice = currentSellingPrice;
    }

    public double getCurrentCostPrice() {
        return currentCostPrice;
    }

    public void setCurrentCostPrice(double currentCostPrice) {
        this.currentCostPrice = currentCostPrice;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getMinReorderQty() {
        return minReorderQty;
    }

    public void setMinReorderQty(int minReorderQty) {
        this.minReorderQty = minReorderQty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public ArrayList<StockDetails> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(ArrayList<StockDetails> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //method that updates the quantity, selling price, and cost price to be used when creating invoices and credit notes.
    //it will update the product details based on latest information in stockDetails.txt
    public static void updateProduct() {
        //Read current products into an array list.
        ArrayList<Product> products = readFile(Product.fileName);

        //Read current stockDetails into an array list.
        ArrayList<StockDetails> stockDetails = StockDetails.readFile(StockDetails.fileName);

        double sumCostPrice;
        int costPriceCount;
        double averageCostPrice;
        int prodQty = 0;
        double currentSellingPrice;
        boolean productInvoiceExist;

        for (Product p : products) {
            sumCostPrice = 0d;
            costPriceCount = 0;
            averageCostPrice = 0d;
            prodQty = 0;
            currentSellingPrice = 0d;
            productInvoiceExist = false;
            //loop calculate the sum of the cost prices for this product and update the product's current quantity.
            for (int i = 0; i < stockDetails.size(); i++) {
                if (p.getCode().equals(stockDetails.get(i).getProductCode())) {
                    productInvoiceExist = true;
                    //check whether invoice no string is empty, if it is, then it is a credit note
                    if (!stockDetails.get(i).getInvNo().equals("")) {
                        sumCostPrice += stockDetails.get(i).getCostPrice();
                        costPriceCount++;
                        prodQty += stockDetails.get(i).getQty();
                    } else {
                        prodQty -= stockDetails.get(i).getQty();
                    }
                }
            }
            //Only update product details if there are invoices associated with the product.
            if (productInvoiceExist) {
                //calculate average cost price for this product
                averageCostPrice = sumCostPrice / costPriceCount;

                //set current product's cost price to the average cost price of this product in all invoices containing this product.
                p.setCurrentCostPrice(averageCostPrice);

                //set the current product's selling price by marking up the cost price by 50%
                currentSellingPrice = averageCostPrice * 1.5;
                p.setCurrentSellingPrice(currentSellingPrice);

                //set the updated stock quantity of this product.
                p.setStockQty(prodQty);
            }

        }

        //update the edited product details
        modify(Product.fileName, products);

    }

    //method that updates the quantity when a transaction is made.
    //it will update the product details based on latest information in transactionDetails.txt
    public static void updateQuantity() {
        //Read current products into an array list.
        ArrayList<Product> products = readFile(Product.fileName);

        //Read the current transaction details into an array list.
        ArrayList<TransactionDetails> transactionDetails = TransactionDetails.readFile(TransactionDetails.fileName);

        //Deduct the quantity of products in transactions from the total quantity that a product has.
        for (Product p : products) {
            for (TransactionDetails td : transactionDetails) {
                if (p.getCode().equals(td.getProductCode())) {
                    p.setStockQty(p.getStockQty() - td.getQty());
                }
            }
        }

        //update the edited product details
        modify(Product.fileName, products);
    }

    //Accepts the file name and a product array list with additional new products and writes it to a file.
    public static void add(String fileName, ArrayList<Product> products) {
        Product.writeFile(fileName, products);
    }

    //accepts two string as arguments, one for searchType and a searchString, and returns the Product array list containing the product searched.
    //if the product does not exist, return null
    public static ArrayList<Product> search(String searchType, String searchString) {

        //Read product details and store it into an array list
        ArrayList<Product> products = readFile(Product.fileName);

        //Array list to store the search results for products
        ArrayList<Product> searchResultsProducts = new ArrayList<>();

        //to keep track of the searchCount
        int searchCount = 0;

        switch (searchType) {
            case "productCode":
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getCode().equals(searchString)) {
                        searchResultsProducts.add(new Product(products.get(i)));
                        searchCount++;
                        break;
                    }
                }
                break;
            case "productName":
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getName().equals(searchString)) {
                        searchResultsProducts.add(new Product(products.get(i)));
                        searchCount++;
                        break;
                    }
                }
                break;
            //No break added in if statement because there can be more than 1 search results in productCategory.
            case "productCategory":
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getCategory().equals(searchString)) {
                        searchResultsProducts.add(new Product(products.get(i)));
                        searchCount++;
                    }
                }
                break;
            default:
                System.out.println("Invalid searchType.");
        }
        if (searchCount == 0) {
            return null;
        }

        return (ArrayList<Product>) searchResultsProducts.clone();

    }

    //Accepts the file name and a modified product array list and writes it to a file.
    public static void modify(String fileName, ArrayList<Product> products) {
        Product.writeFile(fileName, products);
    }

    //Accepts a Product array list, displays the products, and return the print count.
    public static int display(ArrayList<Product> products) {
        int printCount = 0;
        for (Product p : products) {
            System.out.println(p);
            printCount++;
        }
        return printCount;
    }

    //Accepts the file name and a product array list with the updated status and writes it to a file.
    public static void editProductStatus(String fileName, ArrayList<Product> products) {
        Product.writeFile(fileName, products);
    }

    //Add a new product category in the specified txt file
    public static void addCategory(String fileName, String category) {
        try {
            //Create FileWriter set to append mode (second parameter true) 
            FileWriter writer = new FileWriter("src\\" + fileName, true);

            //Write the new category into the specified fileName.
            writer.write(category + "\n");
            // Closes the writer
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //method to read a specified file into a Product array list and return it
    public static ArrayList<Product> readFile(String fileName) {
        //Array list to store the product records
        ArrayList<Product> products = new ArrayList<>();
        try {
            //Array lists used when reading from transaction details and stock details text files
            ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
            ArrayList<StockDetails> stockDetails = new ArrayList<>();

            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\%");
                String[] string1 = buffer[0].split("\\|");
                String[] string2 = buffer[1].split("\\|");
                String[] string3 = buffer[2].split("\\|");
                String[] string4 = buffer[3].split("\\|");

                //Store the read data into their respective variables to be used later to create product object.
                String category = string4[0];
                String code = string1[0];
                String name = string1[1];
                String status = string4[1];

                //Convert string to double for currentSellingPrice and currentCostPrice
                //element 1 is currentSellingPrice and element 2 is currentCostPrice
                double[] doubleArr = new double[2];
                for (int i = 0; i < string2.length; i++) {
                    doubleArr[i] = Double.parseDouble(string2[i]);
                }

                //Convert string to integer for stockQty and minReorderQty
                //element 1 is stockQty and element 2 is minReorderQty
                int[] intArr = new int[2];
                for (int i = 0; i < string3.length; i++) {
                    intArr[i] = Integer.parseInt(string3[i]);
                }

                //read from transactionDetails.txt and create a copy of transactionDetails records
                ArrayList<TransactionDetails> allTD = (ArrayList<TransactionDetails>) TransactionDetails.readFile(TransactionDetails.fileName).clone();
                transactionDetails.clear();
                //Add elements of transaction details that is associated with this product code.
                for (TransactionDetails td : allTD) {
                    if (td.getProductCode().equals(code)) {
                        transactionDetails.add(td);
                    }
                }

                //read from stockDetails.txt and create a copy of stock details records.
                ArrayList<StockDetails> allSD = (ArrayList<StockDetails>) StockDetails.readFile(StockDetails.fileName).clone();
                stockDetails.clear();
                //Add elements of stock details that is associated with this product code.
                for (StockDetails sd : allSD) {
                    if (sd.getProductCode().equals(code)) {
                        stockDetails.add(sd);
                    }
                }

                //store cloned versions of transactionDetails and stockDetails as they will be used again in subsequent loops
                products.add(new Product(code, name, doubleArr[0], doubleArr[1], intArr[0], intArr[1], category, (ArrayList<TransactionDetails>) transactionDetails.clone(), (ArrayList<StockDetails>) stockDetails.clone(), status));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    //method to write the data of a product array list into a specified file name.
    public static void writeFile(String fileName, ArrayList<Product> products) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < products.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s%%%.2f|%.2f%%%d|%d%%%s|%s\n", products.get(i).getCode(), products.get(i).getName(), products.get(i).getCurrentSellingPrice(), products.get(i).getCurrentCostPrice(), products.get(i).getStockQty(), products.get(i).getMinReorderQty(), products.get(i).getCategory(), products.get(i).getStatus());
                //Writes the record to the file.
                writer.write(line);
            }

            // Closes the writer
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String toString() {
//        return "Product code: " + code + ", Product name: " + name + ", Current selling price: " + currentSellingPrice + ", Current cost price: " + currentCostPrice + ", Stock quantity: " + stockQty + ", Product minimum reorder quantity: " + minReorderQty +  ", Category: " + category;        
        return String.format("| %-5s | %-20s | %-20s | %18.2f | %15.2f | %8d | %16d | %-8s |", code, name, category, currentSellingPrice, currentCostPrice, stockQty, minReorderQty, status);
    }

    //Override equals method to compare objects using their product codes.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.code, other.code);
    }

}
