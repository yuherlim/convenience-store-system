/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ong58
 */
public class Member extends Person {
    private String id;
    private static int NumOfCustomer;
    
    //Constructor

    public Member(String id,String name, String ic, String birthdate, String phoneNum, String address) {
        super(name, ic, birthdate, phoneNum, address);
        this.id = id;
    }
    //Setter and getter

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIc() {
        return ic;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public static int getNumOfCustomer() {
        return NumOfCustomer;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void setNumOfCustomer(int NumOfCustomer) {
        Member.NumOfCustomer = NumOfCustomer;
    }
   
    @Override
    public String toString() {
        return  super.toString() + ",id=" + id;
    }
   
    
    
    
    
    
}
