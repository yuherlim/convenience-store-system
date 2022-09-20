
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<Member> members = new ArrayList<Member>();
        readFile("member", members);

        Member.displayMember(members);
        General.clearScreen();
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
                    add();
                    break;
                case 2:
                    //Edit
                    break;
                case 3:
                    //Delete()
                    break;
                case 4:
                    Member.searchMember();
                    break;
                case 5:
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
        ArrayList<Member> members = new ArrayList<Member>();
        Scanner sc = new Scanner(System.in);
        String id;
        String name;
        String ic;
        String birthdate;
        String phoneNum;
        String address;
        String status;
        char charSelection;
        int intSelection;
        int loop;

        readFile("member", members);

        do {
            id = "M" + General.getCurrentDateTime("yymm") + String.format("%04d", (Member.getNumOfCustomer() + 1));

            name = General.stringInput("Name :", "Invalid Name format");

            ic = General.icInput("ic : ");

            birthdate = General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", ic);
            if (General.ageCalc(birthdate) < 18) {
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

            phoneNum = General.phoneInput("Phone Number : ");

            System.out.print("Address : ");
            address = General.stringNullCheckingInput("Address : ", "Invalid Address");

            status = "Active";

            System.out.printf("Name : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n", id, name, ic, birthdate, phoneNum, address);

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
                                    name = General.stringInput("Name :", "Invalid Name format");
                                    break;
                                case 2:
                                    do {
                                        loop = 0;
                                        ic = General.icInput("ic : ");
                                        birthdate = General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", ic);
                                        if (General.ageCalc(birthdate) < 18) {
                                            System.out.println("The birthDate entered is invalid");
                                            loop = 1;
                                        }
                                    } while (loop == 1);
                                    break;
                                case 3:
                                    phoneNum = General.phoneInput("Phone Number : ");
                                    break;
                                case 4:
                                    System.out.print("Address : ");
                                    address = sc.nextLine();
                                    break;
                                default:
                                    System.out.println("Invalid selection");
                                    loop = 1;
                                    break;
                            }

                        } while (loop == 1);
                        System.out.printf("Name : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n", id, name, ic, birthdate, phoneNum, address);
                        loop = 1;
                        break;

                    case 'Y':
                        System.out.println("Member sucessfully added");
                        Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
                        members.add(new Member(id, name, ic, birthdate, phoneNum, address, status));
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

//    public static void edit() {
//        ArrayList<Member> members = new ArrayList<Member>();
//        ArrayList<Member> memberSearch = new ArrayList<>();
//        ArrayList<Integer> index = new ArrayList<>();
//
//        Scanner sc = new Scanner(System.in);
//        String id;
//        String name;
//        String ic;
//        String birthdate;
//        String phoneNum;
//        String address;
//        String mode = " ";
//        String input = " ";
//        char charSelection;
//        int intSelection;
//        int selection;
//        int loop;
//        int intInput;
//
//        readFile("member", members);
//
//        do {
//            loop = 0;
//
//            do {
//                do {
//                    loop = 0;
//                    System.out.println("================");
//                    System.out.println("Search Member");
//                    System.out.println("================");
//                    System.out.println("Search by :");
//                    System.out.println("1 - ID");
//                    System.out.println("2 - IC");
//
//                    selection = General.intInput("Selection : ", "Invalid selection");
//
//                    switch (selection) {
//                        case 1:
//                            mode = "ID";
//                            do {
//                                loop = 0;
//                                input = General.stringInput("Member id : ", "Invalid Member id");
//                                if (Member.memberIdValidation(input) == false) {
//                                    System.out.println("Invalid member id");
//                                    loop = 1;
//                                }
//                            } while (loop == 1);
//                            break;
//                        case 2:
//                            mode = "IC";
//                            do {
//                                loop = 0;
//                                input = General.stringInput("Member ic : ", "Invalid Member ic");
//                                if (General.icValidation(input) == false) {
//                                    System.out.println("Invalid member ic");
//                                    loop = 1;
//                                }
//                            } while (loop == 1);
//                            break;
//                        case 3:
//                            mode = "name";
//                            input = General.stringInput("Member name : ", "Invalid Member name");
//                            break;
//                        case 4:
//                            mode = "birth month";
//                            do {
//                                loop = 0;
//                                intInput = General.intInput("Member birth month (1-12) : ", "Invalid Member month");
//                                if (intInput < 1 || intInput > 12) {
//                                    System.out.println("Invalid Member month");
//                                    loop = 1;
//                                }
//                            } while (loop == 1);
//                            input = String.valueOf(intInput);
//                            break;
//                        default:
//                            System.out.println("Invalid selection");
//                            loop = 1;
//                            break;
//                    }
//
//                } while (loop == 1);
//
//                memberSearch = Member.searchObj(input, mode, members);
//
//                index = Member.searchIndex(input, mode, members);
//
//                if (index.size() == 0) {
//                    System.out.println("Member not found");
//                    //ASk user whether to continue
//                } else if (index.size() == 1) {
//                    System.out.printf("ID : %s \nName : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n", memberSearch.get(0).getId(), memberSearch.get(0).getName(), memberSearch.get(0).getIc(), memberSearch.get(0).getBirthdate(), memberSearch.get(0).getPhoneNum(), memberSearch.get(0).getAddress());
//
//                }
//
//                charSelection = Character.toUpperCase(General.charInput("Confirm (Y/N) X-Cancel : ", ("Invalid input")));
//
//                switch (charSelection) {
//                    case 'N':
//                        do {
//                            loop = 0;
//                            System.out.println("Edit menu");
//                            System.out.println("1.Name ");
//                            System.out.println("2.IC and Birthdate");
//                            System.out.println("3.PhoneNum ");
//                            System.out.println("4.Address ");
//                            intSelection = General.intInput("Selection : ", "Invalid selection input");
//
//                            switch (intSelection) {
//                                case 1:
//                                    name = General.stringInput("Name :", "Invalid Name format");
//                                    break;
//                                case 2:
//                                    do {
//                                        loop = 0;
//                                        ic = General.icInput("ic : ");
//                                        birthdate = General.birthDateInput("Birthdate (dd/mm/yyyy)   :", "Invalid birthdate format", ic);
//                                        if (General.ageCalc(birthdate) < 18) {
//                                            System.out.println("The birthDate entered is invalid");
//                                            loop = 1;
//                                        }
//                                    } while (loop == 1);
//                                    break;
//                                case 3:
//                                    phoneNum = General.phoneInput("Phone Number : ");
//                                    break;
//                                case 4:
//                                    System.out.print("Address : ");
//                                    address = sc.nextLine();
//                                    break;
//                                default:
//                                    System.out.println("Invalid selection");
//                                    loop = 1;
//                                    break;
//                            }
//
//                        } while (loop == 1);
//                        System.out.printf("Name : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n", id, name, ic, birthdate, phoneNum, address);
//                        loop = 1;
//                        break;
//
//                    case 'Y':
//                        System.out.println("Member sucessfully added");
//                        Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
//                        members.add(new Member(id, name, ic, birthdate, phoneNum, address));
//                        break;
//
//                    default:
//                        System.out.println("Invalid selection");
//                        loop = 1;
//                        break;
//                }
//
//            } while (loop == 1);
//
//        } while (charSelection == 'Y');
//
//        Member.edit(members);
//    }

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
