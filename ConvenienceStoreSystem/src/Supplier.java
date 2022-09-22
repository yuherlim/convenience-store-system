
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
    private String address;
    private String industry;
    
    //fileName containing supplier details.
    public static String fileName = "supplier.txt";

    public Supplier() {
        this("","","","","");
    }
    
    public Supplier(String id, String name, String phoneNo, String address, String industry) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        this.industry = industry;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public static void add() {
        
    }
    
    public static void search() {
        
    }
    
    public static void modify() {
        
    }
    
    public static void display() {
        
    }
    
    public static void addIndustry() {
        
    }
    
    public static void readFile() {
        
    }
    
    public static void writeFile() {
        
    }
    
    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + ", address=" + address + ", industry=" + industry + '}';
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
