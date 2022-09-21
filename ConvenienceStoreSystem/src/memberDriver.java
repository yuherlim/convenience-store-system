
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
public class MemberDriver {

    public static void main(String[] args) {
        General.clearScreen();
        menu();

    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int selection;
        int loop;
        do {
            loop = 0;
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
                    add();
                    loop = 1;
                    break;
                case 2:
                    edit();
                    loop = 1;
                    break;
                case 3:
                    delete();
                    loop = 1;
                    break;
                case 4:
                    Member.searchMember();
                    loop = 1;
                    break;
                case 5:
                    Member.display();
                    loop = 1;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid selection please try again");
                    loop = 1;
                    break;
            }
        } while (loop == 1);
    }

    public static void add() {
        ArrayList<Member> members = new ArrayList<Member>();
        Scanner sc = new Scanner(System.in);
        Member addMember = new Member();
        char charSelection;
        int intSelection;
        int loop;

        readFile("member", members);

        do {
            addMember.setId("M" + General.getCurrentDateTime("yymm") + String.format("%04d", (Member.getNumOfCustomer() + 1)));

            addMember.setName(General.stringInput("Name :", "Invalid Name format"));

            addMember.setIc(General.icInput("ic : "));

            addMember.setBirthdate(General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", addMember.getIc()));
            if (General.ageCalc(addMember.getBirthdate()) < 18) {
                System.out.println("Member is under age");
                do {
                    loop = 0;
                    charSelection = Character.toUpperCase(General.charInput("add another member(Y/N): ", "Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
                continue;
            }

            addMember.setPhoneNum(General.phoneInput("Phone Number : "));

            addMember.setAddress(General.stringNullCheckingInput("Address : ", "Invalid Address"));

            addMember.setAddress("Active");

            Member.displayDetails(addMember);

            do {
                loop = 0;
                charSelection = Character.toUpperCase(General.charInput("Confirm (Y/N) X-Cancel : ", ("Invalid input")));

                switch (charSelection) {
                    case 'N':
                        do {
                            loop = 0;
                            System.out.println("Edit menu");
                            System.out.println("1.Name ");
                            System.out.println("2.IC and Birthdate");
                            System.out.println("3.PhoneNum ");
                            System.out.println("4.Address ");
                            intSelection = General.intInput("Selection : ", "Invalid selection input");

                            switch (intSelection) {
                                case 1:
                                    addMember.setName(General.stringInput("Name :", "Invalid Name format"));
                                    break;
                                case 2:
                                    do {
                                        loop = 0;
                                        addMember.setIc(General.icInput("ic : "));
                                        addMember.setBirthdate(General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", addMember.getIc()));
                                        if (General.ageCalc(addMember.getBirthdate()) < 18) {
                                            System.out.println("The birthDate entered is invalid");
                                            loop = 1;
                                        }
                                    } while (loop == 1);
                                    break;
                                case 3:
                                     addMember.setPhoneNum(General.phoneInput("Phone Number : "));
                                    break;
                                case 4:
                                    addMember.setAddress(General.stringNullCheckingInput("Address : ", "Invalid Address"));
                                    break;
                                default:
                                    System.out.println("Invalid selection");
                                    loop = 1;
                                    break;
                            }

                        } while (loop == 1);
                        Member.displayDetails(addMember);
                        loop = 1;
                        break;

                    case 'Y':
                        System.out.println("Member sucessfully added");
                        Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
                        members.add(addMember);
                        break;

                    case 'X':
                        break;

                    default:
                        System.out.println("Invalid selection");
                        loop = 1;
                        break;
                }

            } while (loop == 1);

            if (charSelection == 'Y') {
                do {
                    loop = 0;
                    charSelection = Character.toUpperCase(General.charInput("add another member(Y/N): ", "Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
            }
        } while (charSelection == 'Y');

        Member.add(members);
    }

    public static void edit() {
        ArrayList<Member> members = new ArrayList<>();

        char charSelection;
        int intSelection;
        int loop;
        int indexSelection = 0;
        String[] inputMode;

        readFile("member", members);

        do {
            loop = 0;
            inputMode = Member.searchMenu();
            indexSelection = Member.searchDisplay(inputMode[0],inputMode[1],members);
            
            if(indexSelection == -1){
             charSelection = General.yesNoInput("Edit another member (Y/N) : ", "Invalid selection");
             continue;
            }
           
            do {
                charSelection = General.yesNoInput("Edit? (Y/N)", "Invalid selection");

                switch (charSelection) {
                    case 'Y':
                        do {
                            loop = 0;
                            System.out.println("Edit menu");
                            System.out.println("1.Name ");
                            System.out.println("2.IC and Birthdate");
                            System.out.println("3.PhoneNum ");
                            System.out.println("4.Address ");
                            intSelection = General.intInput("Selection : ", "Invalid selection input");

                            switch (intSelection) {
                                case 1:
                                    members.get(indexSelection).setName(General.stringInput("Name :", "Invalid Name format"));
                                    break;
                                case 2:
                                    do {
                                        loop = 0;
                                        members.get(indexSelection).setIc(General.icInput("ic : "));
                                        members.get(indexSelection).setBirthdate(General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", members.get(indexSelection).getIc()));
                                        if (General.ageCalc(members.get(indexSelection).getBirthdate()) < 18) {
                                            System.out.println("The birthDate entered is invalid");
                                            loop = 1;
                                        }
                                    } while (loop == 1);
                                    break;
                                case 3:
                                     members.get(indexSelection).setPhoneNum(General.phoneInput("Phone Number : "));
                                    break;
                                case 4:
                                    members.get(indexSelection).setAddress(General.stringNullCheckingInput("Address : ", "Invalid Address"));
                                    break;
                                default:
                                    System.out.println("Invalid selection");
                                    loop = 1;
                                    break;
                            }

                        } while (loop == 1);
                        Member.displayDetails(members.get(indexSelection));
                        loop = 1;
                        break;

                    case 'N':
                        break;

                    default:
                        break;
                }

            } while (loop == 1);

            charSelection = General.yesNoInput("Edit another member : ", "Invalid selection");

        } while (charSelection == 'Y');

        Member.edit(members);
    }

    public static void delete() {
        ArrayList<Member> members = new ArrayList<Member>();

        char charSelection;
        int indexSelection = 0;
        String[] inputMode;
        readFile("member", members);
        do{
        inputMode = Member.searchMenu();
        indexSelection = Member.searchDisplay(inputMode[0],inputMode[1],members);
        if(indexSelection == -1){
             charSelection = General.yesNoInput("Edit another member (Y/N) : ", "Invalid selection");
             continue;
            }
                charSelection = General.yesNoInput("Edit? (Y/N)", "Invalid selection");
                
                switch (charSelection) {
                    case 'Y':
                        System.out.println("Member ID :" + members.get(indexSelection).getId() + " succesfully deactivated.");
                        members.get(indexSelection).setStatus("Inactive");
                        break;
                    case 'N':
                        break;

                    default:
                        break;
                }
                
                 charSelection = General.yesNoInput("Delete another member (Y/N) : ", "Invalid selection");
                
          } while (charSelection == 'Y');
        Member.delete(members);
    }

    public static void readFile(String fileName, ArrayList<Member> members) {
        try {
            FileReader reader = new FileReader("src\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            Member.setNumOfCustomer(0);

            while ((line = bufferedReader.readLine()) != null) {
                String[] buffer = line.split("\\|");
                String memberId = buffer[0];
                String name = buffer[1];
                String ic = buffer[2];
                String birthdate = buffer[3];
                String phoneNum = buffer[4];
                String address = buffer[5];
                String status = buffer[6];
                if (memberId.substring(1, 5).equals(General.getCurrentDateTime("yymm"))) {
                    Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
                }
                members.add(new Member(memberId, name, ic, birthdate, phoneNum, address, status));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
