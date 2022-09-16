
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ong58
 */
public class memberDriver {

    public static void main(String[] args) {
        menu();

    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int selection;
        do {
            System.out.println("Member Submenu");
            System.out.println("====================");
            System.out.println("1.Add Member");
            System.out.println("2.Edit Member");
            System.out.println("3.Delete Member");
            System.out.println("4.Search Member");
            System.out.println("5.View Member");
            System.out.println("0.Exit function");
            System.out.println("====================");
            System.out.print("Enter your selection: ");
            selection = sc.nextInt();

            switch (selection) {
                case 1:
                    //Add Member
                    break;
                case 2:
                    //Edit Member
                    break;
                case 3:
                    //Delete()
                    break;
                case 4:
                    //Search()
                    break;
                case 5:
                    //View()
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid selection please try again");
                    break;
            }
        } while (selection > 5 || selection < 0);
    }

    public static void add(){
        Scanner sc = new Scanner(System.in);
        String id;
        String name;
        String ic;
        int[] birthdate = new int[3];
        String phoneNum;
        String address;
        
        System.out.print("Name: ");
        
        
        
        System.out.print("ic: ");
        
        System.out.println("birthdate: ");
        System.out.print("day: ");
        System.out.print("month: ");
        System.out.print("year: ");
        
        
        
        System.out.print("address: ");
        
        
        
    
    
    }
    

}
