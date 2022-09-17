
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
        add();

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
        String birthdate;
        String phoneNum;
        String address;
        int loop = 0;
        boolean dateFormat;
        
        System.out.print("Name: ");
        name = sc.nextLine();
        sc.
        do{
        loop = 0;    
        System.out.println("birthdate (yyyy/mm/dd): ");
        birthdate = sc.next();
        dateFormat = General.dateChecking(birthdate); 
        }while(loop == 1);
        
        
        
        General.icValidation(birthdate);
        
        
        
        System.out.print("address: ");
        name = sc.nextLine();
        
        System.out.print("phone number: ");
        phoneNum = sc.next();
        
        
    Scanner scanner = new Scanner(System.in);
    try {
      int i = scanner.nextInt();
    } catch (Exception e) {
      System.out.println("Something went wrong.");
    } finally {
      System.out.println("The 'try catch' is finished.");
    }
        
    
    
    }
    

}
