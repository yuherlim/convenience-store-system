
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class Supplier {
    private String id;
    private String name;
    private String phoneNo;
    private String email;
    private String address;
    
    //fileName containing supplier details.
    public static String fileName = "supplier.txt";

    public Supplier() {
        this("","","","","");
    }
    
    public Supplier(Supplier sp) {
        this.id = sp.id;
        this.name = sp.name;
        this.phoneNo = sp.phoneNo;
        this.email = sp.email;
        this.address = sp.address;
    }

    public Supplier(String id, String name, String phoneNo, String email, String address) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //Accepts the file name and a supplier array list with additional new suppliers and writes it to a file.
    public static void add(String fileName, ArrayList<Supplier> suppliers) {
        Supplier.writeFile(fileName, suppliers);
    }
    
     //accepts two string as arguments, one for searchType and a searchString, and returns the Supplier object containing the supplier searched.
    //If the supplier does not exist, returns null
    public static Supplier search(String searchType, String searchString) {
        
        //Read supplier details and store it into an array list
        ArrayList<Supplier> suppliers = readFile(Supplier.fileName);
        
        
        //Array list to store the search results for suppliers
        ArrayList<Supplier> searchResultsSuppliers = new ArrayList<>();
        
        //to keep track of the searchCount
        int searchCount = 0;
        
        //break when record is found because there supplierId and supplierNames are unique.
        switch(searchType) {
            case "supplierId":
                for (int i = 0; i < suppliers.size(); i++) {
                    if (suppliers.get(i).getId().equals(searchString)) {
                        searchResultsSuppliers.add(new Supplier(suppliers.get(i)));
                        searchCount++;
                        break;
                    } 
                }
                break;
            case "supplierName":
                for (int i = 0; i < suppliers.size(); i++) {
                    if (suppliers.get(i).getName().equals(searchString)) {
                        searchResultsSuppliers.add(new Supplier(suppliers.get(i)));
                        searchCount++;
                        break;
                    } 
                }
                break;
            default:
                System.out.println("Invalid searchType.");
        }
        if (searchCount == 0)
            return null;
        
        return new Supplier(searchResultsSuppliers.get(0));
        
    }
    
    public static void modify() {
        
    }
    
    public static void display() {
        
    }
    
    //reads file and returns Supplier array list.
    public static ArrayList<Supplier> readFile(String fileName) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] buffer = line.split("\\|");
                String supplierId = buffer[0];
                String supplierName = buffer[1];
                String supplierPhoneNum = buffer[2];
                String supplierEmail = buffer[3];
                String supplierAddress = buffer[4];
                
                suppliers.add(new Supplier(supplierId, supplierName, supplierPhoneNum, supplierEmail, supplierAddress));
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return suppliers;
    }
    
    //method to write the data of a Supplier array list into a specified file name.
    public static void writeFile(String fileName, ArrayList<Supplier> suppliers) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);
  
            for (int i = 0; i < suppliers.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s|%s\n", suppliers.get(i).getId(), suppliers.get(i).getName(), suppliers.get(i).getPhoneNo(), suppliers.get(i).getEmail(), suppliers.get(i).getAddress());
                //Writes the record to the file.
                writer.write(line);
            }
  
            // Closes the writer
            writer.close();
        }
  
        catch (Exception e) {
            e.getStackTrace();
        }
    }
    
    
    //Needs to be changed to displaying in table form.
    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + ", email=" + email + ", address=" + address + '}';
    }

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
        final Supplier other = (Supplier) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
