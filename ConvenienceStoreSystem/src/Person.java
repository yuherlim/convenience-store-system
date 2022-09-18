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
    protected String birthdate;
    protected String phoneNum;
    protected String address;
    
    //Constructor
    
    
    //Setter & getter

    public Person(String name, String ic, String birthdate, String phoneNum, String address) {
        this.name = name;
        this.ic = ic;
        this.birthdate = birthdate;
        this.phoneNum = phoneNum;
        this.address = address;
    }
    
}
