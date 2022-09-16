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
    private static String currentId;
    
    //Constructor

    public Member(String name, String ic, String phoneNum, String address) {
        super(name, ic, phoneNum, address);       
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

    public int[] getBirthdate() {
        return birthdate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
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

    public void setBirthdate(int[] birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
    
    
}
