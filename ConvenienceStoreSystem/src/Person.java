/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ong58
 */
public abstract class Person {
    //Data field
    protected String name;
    protected String ic;
    protected int[] birthdate = new int[3];
    protected String phoneNum;
    protected String address;
    
    //Constructor
    public Person(String name, String ic, String phoneNum, String address) {
        this.name = name;
        this.ic = ic;
        this.phoneNum = phoneNum;
        this.address = address;
    }
    
    //Setter & getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    //not yet birthdate
    
    
    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
