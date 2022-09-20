
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
        ArrayList<Member> members = new ArrayList<Member>();
        readFile("member", members);

        menu();
        display(members);

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
                    searchMember();
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
                    charSelection = Character.toUpperCase(General.CharInput("add another member(Y/N): ", "Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
                continue;
            }

            phoneNum = General.phoneInput("Phone Number : ");

            System.out.print("Address : ");
            address = sc.nextLine();

            System.out.printf("Name : %s \nIC : %s \nBirthdate : %s \nPhoneNum : %s \nAddress : %s\n", id, name, ic, birthdate, phoneNum, address);

            do {
                loop = 0;
                charSelection = Character.toUpperCase(General.CharInput("Confirm (Y/N) X-Cancel : ", ("Invalid input")));

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
                        members.add(new Member(id, name, ic, birthdate, phoneNum, address));
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
                    charSelection = Character.toUpperCase(General.CharInput("add another member(Y/N): ", "Invalid selection input"));
                    if (charSelection != 'N' && charSelection != 'Y') {
                        System.out.println("Invalid selection");
                        loop = 1;
                    }
                } while (loop == 1);
            }
        } while (charSelection == 'Y');

        writeFile("member", members);
    }

    public static ArrayList<Member> searchObj(String input, String mode, ArrayList<Member> members) {
        ArrayList<Member> member = new ArrayList<>();

        if (mode == "IC") {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getIc().equals(input)) {
                    member.add(members.get(i));
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getId().equals(input)) {
                    member.add(members.get(i));
                }
            }
        } else if ("name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(input)) {
                    member.add(members.get(i));
                }
            }
        } else if ("birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                    member.add(members.get(i));
                }
            }
        }
        return member;
    }

    public static ArrayList<Integer> searchIndex(String input, String mode, ArrayList<Member> members) {
        ArrayList<Integer> index = new ArrayList<>();
        if ("IC".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getIc().equals(input)) {
                    index.add(i);
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getId().equals(input)) {
                    index.add(i);
                }
            }
        } else if ("name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(input)) {
                    index.add(i);
                }
            }
        } else if ("birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                    index.add(i);
                }
            }
        }
        return index;
    }

    public static void searchMember() {
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();
        String input = " ";
        String mode = " ";
        int intInput;
        int loop;
        int selection;
        char charSelection;

        readFile("member", members);

        do {
            do {
                loop = 0;
                System.out.println("================");
                System.out.println("Search Member");
                System.out.println("================");
                System.out.println("Search by :");
                System.out.println("1 - ID");
                System.out.println("2 - IC");
                System.out.println("3 - Name");
                System.out.println("4 - Birth month");
                selection = General.intInput("Selection : ", "Invalid selection");

                switch (selection) {
                    case 1:
                        mode = "ID";
                        do {
                            loop = 0;
                            input = General.stringInput("Member id : ", "Invalid Member id");
                            if (memberIdValidation(input) == false) {
                                System.out.println("Invalid member id");
                                loop = 1;
                            }
                        } while (loop == 1);
                        break;
                    case 2:
                        mode = "IC";
                        do {
                            loop = 0;
                            input = General.stringInput("Member ic : ", "Invalid Member ic");
                            if (General.icValidation(input) == false) {
                                System.out.println("Invalid member ic");
                                loop = 1;
                            }
                        } while (loop == 1);
                        break;
                    case 3:
                        mode = "name";
                        input = General.stringInput("Member name : ", "Invalid Member name");
                        break;
                    case 4:
                        mode = "birth month";
                        do {
                            loop = 0;
                            intInput = General.intInput("Member birth month (1-12) : ", "Invalid Member month");
                            if (intInput < 1 || intInput > 12) {
                                System.out.println("Invalid Member month");
                                loop = 1;
                            }
                        } while (loop == 1);
                        input = String.valueOf(intInput);
                        break;
                    default:
                        System.out.println("Invalid selection");
                        loop = 1;
                        break;
                }

            } while (loop == 1);
            members = searchObj(input, mode, members);
            if (members.size() < 1) {
                System.out.println("Member not found");
            } else {
                display(members);
            }

            do {
                loop = 0;
                charSelection = Character.toUpperCase(General.CharInput("Search another member(Y/N): ", "Invalid selection input"));
                if (charSelection != 'N' && charSelection != 'Y') {
                    System.out.println("Invalid selection");
                    loop = 1;
                }
            } while (loop == 1);

        } while (charSelection == 'Y');

    }

    public static void edit() {
        ArrayList<Member> members = new ArrayList<Member>();
        ArrayList<Member> memberSearch = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String id;
        String name;
        String ic;
        String birthdate;
        String phoneNum;
        String address;
        String mode = " ";
        String input = " ";
        char charSelection;
        int intSelection;
        int selection;
        int loop;
        int intInput;

        readFile("member", members);

        do {
            loop = 0;

            do {
                do {
                    loop = 0;
                    System.out.println("================");
                    System.out.println("Search Member");
                    System.out.println("================");
                    System.out.println("Search by :");
                    System.out.println("1 - ID");
                    System.out.println("2 - IC");

                    selection = General.intInput("Selection : ", "Invalid selection");

                    switch (selection) {
                        case 1:
                            mode = "ID";
                            do {
                                loop = 0;
                                input = General.stringInput("Member id : ", "Invalid Member id");
                                if (memberIdValidation(input) == false) {
                                    System.out.println("Invalid member id");
                                    loop = 1;
                                }
                            } while (loop == 1);
                            break;
                        case 2:
                            mode = "IC";
                            do {
                                loop = 0;
                                input = General.stringInput("Member ic : ", "Invalid Member ic");
                                if (General.icValidation(input) == false) {
                                    System.out.println("Invalid member ic");
                                    loop = 1;
                                }
                            } while (loop == 1);
                            break;
                        case 3:
                            mode = "name";
                            input = General.stringInput("Member name : ", "Invalid Member name");
                            break;
                        case 4:
                            mode = "birth month";
                            do {
                                loop = 0;
                                intInput = General.intInput("Member birth month (1-12) : ", "Invalid Member month");
                                if (intInput < 1 || intInput > 12) {
                                    System.out.println("Invalid Member month");
                                    loop = 1;
                                }
                            } while (loop == 1);
                            input = String.valueOf(intInput);
                            break;
                        default:
                            System.out.println("Invalid selection");
                            loop = 1;
                            break;
                    }

                } while (loop == 1);

                memberSearch = searchObj(input, mode, members);

                index = searchIndex(input, mode, members);

                charSelection = Character.toUpperCase(General.CharInput("Confirm (Y/N) X-Cancel : ", ("Invalid input")));

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
                        members.add(new Member(id, name, ic, birthdate, phoneNum, address));
                        break;

                    default:
                        System.out.println("Invalid selection");
                        loop = 1;
                        break;
                }

            } while (loop == 1);

        } while (charSelection == 'Y');
    }

    public static void display(ArrayList<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            System.out.printf("| %04d | %-8s | %-30s | %-12s | %-11s | %-60s |", i, members.get(i).getId(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getPhoneNum(), members.get(i).getAddress());
        }
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
                if (memberId.substring(1, 5).equals(General.getCurrentDateTime("yymm"))) {
                    Member.setNumOfCustomer(Member.getNumOfCustomer() + 1);
                }
                members.add(new Member(memberId, name, ic, birthdate, phoneNum, address));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName, ArrayList<Member> members) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < members.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s|%s|%s\n", members.get(i).getId(), members.get(i).getName(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getBirthdate(), members.get(i).getAddress());
                //Writes the record to the file.
                writer.write(line);
            }

            // Closes the writer
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static boolean memberIdValidation(String id) {
        if (id.length() == 8) {
            if (id.charAt(0) != 'M') {
                return false;
            }
            if (Integer.parseInt(id.substring(3, 5)) > 12) {
                return false;
            }
            for (int i = 1; i < id.length(); i++) {
                if (Character.isDigit(id.charAt(i)) != true) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
