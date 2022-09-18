
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        ArrayList<Member> members = new ArrayList<Member>();

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

    public static void add() {
        Scanner sc = new Scanner(System.in);
        int numOfTotal;
        String id;
        String name;
        String ic;
        String birthdate;
        String phoneNum;
        String address;
        char selection;
        
        do{
            
        name = General.stringInput("Name :", "Invalid Name format");

        birthdate = General.dateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format");

        ic = General.icInput(birthdate);

        phoneNum = General.phoneInput();

        System.out.print("Address : ");
        address = sc.nextLine();
        
        System.out.printf("Name : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n",id,name,ic,birthdate,phoneNum,address  );
        
        selection = Character.toUpperCase(General.CharInput("Confirm (Y/N) : ", ("Invalid input : ")));
        
        }while(selection == 'Y');
    }

    public static ArrayList<Member> readFile(String fileName, ArrayList<Member> members) {
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] buffer = line.split("\\|");
                String memberId = buffer[0];
                String name = buffer[1];
                String ic = buffer[2];
                String birthdate = buffer[3];
                String phoneNum = buffer[4];
                String address = buffer[5];

                members.add(new Member(memberId, name, ic, birthdate, phoneNum, address));

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return members;
    }

    
    
}
