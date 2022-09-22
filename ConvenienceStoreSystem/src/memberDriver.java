
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        menu();

    }

    //Member main menu
    public static void menu() {
        int selection;
        int loop;
        do {
            loop = 0;
            General.clearScreen();
            System.out.println("================");
            System.out.println("|Member Submenu|");
            System.out.println("================");
            System.out.println("1.Add Member");
            System.out.println("2.Edit Member");
            System.out.println("3.Delete Member");
            System.out.println("4.Search Member");
            System.out.println("5.View Member");
            System.out.println("0.Exit function");
            System.out.println("================");
            selection = General.intInput("Enter your selection : ", "  Invalid selection input");

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
                    System.out.println("  Invalid selection");
                    loop = 1;
                    break;
            }
        } while (loop == 1);
    }

    //Member add function
    public static void add() {
        ArrayList<Member> members = new ArrayList<Member>();
        ArrayList<Member> memberSearch;
        Member addMember = new Member();
        String ic;
        String birthdate;
        char charSelection;
        int intSelection;
        int loop;

        //Read member txt file
        readFile("member", members);

        do {
            General.clearScreen();
            System.out.println("============");
            System.out.println("|Add Member|");
            System.out.println("============");

            //Prompt and get member details
            addMember.setId("M" + General.getCurrentDateTime("yymm") + String.format("%03d", (Member.getNumOfCustomer() + 1)));

            System.out.println("Member ID                     : " + addMember.getId());

            addMember.setName(General.stringInput("Member name                   : ", "  Invalid Name format"));

            do {
                loop = 0;
                ic = General.icInput("Member IC                     : ");
                memberSearch = Member.searchObj(ic, "IC", "All", members);
                if (memberSearch.isEmpty() == false) {
                    System.out.println("  IC registered");
                    loop = 1;
                } else {
                    addMember.setIc(ic);
                }
            } while (loop == 1);

            addMember.setBirthdate(General.birthDateInput("Member birthdate (dd/mm/yyyy) : ", "  Invalid birthdate format", addMember.getIc()));
            if (General.ageCalc(addMember.getBirthdate()) < 18) {
                System.out.println("  Member is under age");
                do {
                    loop = 0;
                    charSelection = Character.toUpperCase(General.charInput("Add another member(Y/N): ", "  Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("  Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
                continue;
            }

            addMember.setPhoneNum(General.phoneInput("Member phone number           : "));

            addMember.setAddress(General.stringNullCheckingInput("Member Address                : ", "  Invalid Address"));

            addMember.setStatus("Active");
            General.clearScreen();
            System.out.println("============");
            System.out.println("|Add Member|");
            System.out.println("============");
            Member.displayDetails(addMember);

            //Prompt and get user selection on editing member details
            do {
                loop = 0;
                charSelection = Character.toUpperCase(General.charInput("Confirm (Y/N) X-Cancel : ", ("  Invalid input")));

                switch (charSelection) {
                    case 'N':
                        do {
                            loop = 0;
                            System.out.println("===========");
                            System.out.println("|Edit menu|");
                            System.out.println("===========");
                            System.out.println("1.Name ");
                            System.out.println("2.IC and Birthdate");
                            System.out.println("3.PhoneNum ");
                            System.out.println("4.Address ");
                            System.out.println("===========");
                            intSelection = General.intInput("Selection : ", "  Invalid selection input");

                            switch (intSelection) {
                                case 1:
                                    addMember.setName(General.stringInput("Member name                   : ", "  Invalid Name format"));
                                    break;
                                case 2:
                                    do {
                                        loop = 0;
                                        ic = General.icInput("Member IC                     : ");
                                        memberSearch = Member.searchObj(ic, "IC", "All", members);
                                        if (memberSearch.isEmpty() == false) {
                                            System.out.println("  IC registered");
                                            loop = 1;
                                            continue;
                                        }
                                        birthdate = General.birthDateInput("Member birthdate (dd/mm/yyyy) : ", "  Invalid birthdate format", ic);
                                        if (General.ageCalc(birthdate) < 18) {
                                            System.out.println("  The birthDate entered is invalid");
                                            loop = 1;
                                        } else {
                                            addMember.setIc(ic);
                                            addMember.setBirthdate(birthdate);
                                        }
                                    } while (loop == 1);
                                    break;
                                case 3:
                                    addMember.setPhoneNum(General.phoneInput("Member phone number           : "));
                                    break;
                                case 4:
                                    addMember.setAddress(General.stringNullCheckingInput("Member Address                : ", "  Invalid Address"));
                                    break;
                                default:
                                    System.out.println("  Invalid selection");
                                    loop = 1;
                                    break;
                            }

                        } while (loop == 1);
                        General.clearScreen();
                        System.out.println("============");
                        System.out.println("|Add Member|");
                        System.out.println("============");
                        Member.displayDetails(addMember);
                        loop = 1;
                        break;

                    //Create member object and add inside member object arraylist
                    case 'Y':
                        System.out.println("Member sucessfully added");
                        Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
                        members.add(new Member(addMember.getId(), addMember.getName(), addMember.getIc(), addMember.getBirthdate(), addMember.getPhoneNum(), addMember.getAddress(), addMember.getStatus()));
                        break;

                    case 'X':
                        break;

                    default:
                        System.out.println("  Invalid selection");
                        loop = 1;
                        break;
                }

            } while (loop == 1);

            //Ask user whether add another member
            if (charSelection == 'Y') {
                do {
                    loop = 0;
                    charSelection = Character.toUpperCase(General.charInput("add another member(Y/N): ", "  Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("  Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
            }
        } while (charSelection == 'Y');

        //Write the arraylist into member txt file
        Member.add(members);
    }

    public static void edit() {
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<Member> memberSearch;

        String ic;
        String birthdate;
        char charSelection;
        int intSelection;
        int loop;
        int indexSelection = 0;
        String[] inputMode;

        //Read Member txt file
        readFile("member", members);

        do {
            General.clearScreen();
            System.out.println("=============");
            System.out.println("|Edit Member|");
            System.out.println("=============");
            loop = 0;

            //Search existing member and display its detail
            inputMode = Member.searchMenu();
            indexSelection = Member.searchDisplay(inputMode[0], inputMode[1], "All", members);

            if (indexSelection == -1) {
                charSelection = General.yesNoInput("Edit another member (Y/N) : ", "  Invalid selection");
                continue;
            }

            //Prompt and get user selection on editing member details
            do {
                charSelection = General.yesNoInput("Edit (Y/N) : ", "  Invalid selection");

                switch (charSelection) {
                    case 'Y':
                        do {
                            loop = 0;
                            System.out.println("===========");
                            System.out.println("|Edit menu|");
                            System.out.println("===========");
                            System.out.println("1.Name ");
                            System.out.println("2.IC and Birthdate");
                            System.out.println("3.PhoneNum ");
                            System.out.println("4.Address ");
                            System.out.println("===========");
                            intSelection = General.intInput("Selection : ", "  Invalid selection input");

                            switch (intSelection) {
                                case 1:
                                    members.get(indexSelection).setName(General.stringInput("Member ID                     : ", "  Invalid Name format"));
                                    break;
                                case 2:
                                    do {
                                        loop = 0;
                                        ic = General.icInput("Member IC                     : ");
                                        memberSearch = Member.searchObj(ic, "IC", "All", members);
                                        if (memberSearch.isEmpty() == false) {
                                            System.out.println("  IC registered");
                                            loop = 1;
                                            continue;
                                        }
                                        birthdate = General.birthDateInput("Member birthdate (dd/mm/yyyy) : ", "  Invalid birthdate format", ic);
                                        if (General.ageCalc(birthdate) < 18) {
                                            System.out.println("  The birthDate entered is invalid");
                                            loop = 1;
                                        } else {
                                            members.get(indexSelection).setIc(ic);
                                            members.get(indexSelection).setBirthdate(birthdate);
                                        }
                                    } while (loop == 1);
                                    break;
                                case 3:
                                    members.get(indexSelection).setPhoneNum(General.phoneInput("Member phone number           : "));
                                    break;
                                case 4:
                                    members.get(indexSelection).setAddress(General.stringNullCheckingInput("Member Address                : ", "  Invalid Address"));
                                    break;
                                default:
                                    System.out.println("  Invalid selection");
                                    loop = 1;
                                    break;
                            }

                        } while (loop == 1);
                        General.clearScreen();
                        System.out.println("=============");
                        System.out.println("|Edit Member|");
                        System.out.println("=============");
                        Member.displayDetails(members.get(indexSelection));
                        loop = 1;
                        break;

                    case 'N':
                        break;

                    default:
                        break;
                }

            } while (loop == 1);

            charSelection = General.yesNoInput("Edit another member (Y/N)) : ", "  Invalid selection");

        } while (charSelection == 'Y');

        //Write file
        Member.edit(members);
    }

    public static void delete() {
        ArrayList<Member> members = new ArrayList<Member>();

        char charSelection;
        int indexSelection = 0;
        String[] inputMode;

        //Read Member txt file
        readFile("member", members);

        do {
            General.clearScreen();
            System.out.println("===============");
            System.out.println("|Delete Member|");
            System.out.println("===============");

            //Search existing member and display its detail
            inputMode = Member.searchMenu();
            indexSelection = Member.searchDisplay(inputMode[0], inputMode[1], "All", members);
            if (indexSelection == -1) {
                charSelection = General.yesNoInput("Edit another member (Y/N) : ", "  Invalid selection");
                continue;
            }
            //Prompt and get user selection on whether to delete member
            charSelection = General.yesNoInput("Delete (Y/N) : ", "  Invalid selection");

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

            charSelection = General.yesNoInput("Delete another member (Y/N) : ", "  Invalid selection");

        } while (charSelection == 'Y');
        //Write file
        Member.delete(members);
    }

    //Read file method
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
