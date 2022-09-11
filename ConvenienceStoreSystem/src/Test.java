
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yu
 */
public class Test implements FileFunctions { 
    public static void main(String[] args) {
        System.out.println("Test output.");
    }
    
    @Override
    public void readFile(ArrayList<Object> obj, String filename) {
        System.out.println("add function body");
    }
    
    @Override
    public void add() {
        System.out.println("add function body");
    }
    
    @Override
    public void search() {
        System.out.println("add function body");
    }
    
    @Override
    public void modify() {
        System.out.println("add function body");
    }
    
    @Override
    public void display() {
        System.out.println("add function body");
    }
}
