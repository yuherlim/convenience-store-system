
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ong58
 */
public class Member extends Person {

    private String id;
    private String status;
    private static int NumOfCustomer;

    //Constructor
    public Member() {
        super();
    }

    public Member(String id, String name, String ic, String birthdate, String phoneNum, String address, String status) {
        super(name, ic, birthdate, phoneNum, address);
        this.id = id;
        this.status = status;
    }

    //Setter and getter
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public static int getNumOfCustomer() {
        return NumOfCustomer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void setNumOfCustomer(int NumOfCustomer) {
        Member.NumOfCustomer = NumOfCustomer;
    }

    public static void add(ArrayList<Member> members) {
        writeFile("member", members);

    }

    public static void searchMember() {
        ArrayList<Member> members = new ArrayList<>();
        char charSelection;
        String[] inputMode;
        MemberDriver.readFile("member", members);

        do {
            inputMode = searchMenu();
            searchDisplay(inputMode[0], inputMode[1], members);

            charSelection = General.yesNoInput("Search another Member (Y/N)", "Invalid Selection");

        } while (charSelection == 'Y');

    }

    public static String[] searchMenu() {
        int selection;
        int loop;
        int intInput;
        String[] inputMode = new String[2];

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
            System.out.println("5 - All Member");
            selection = General.intInput("Selection : ", "Invalid selection");

            switch (selection) {
                case 1:
                    inputMode[1] = "ID";
                    do {
                        loop = 0;
                        inputMode[0] = General.stringNullCheckingInput("Member id : ", "s").toUpperCase();
                        if (memberIdValidation(inputMode[0]) == false) {
                            System.out.println("Invalid member id");
                            loop = 1;
                        }
                    } while (loop == 1);
                    break;
                case 2:
                    inputMode[1] = "IC";
                    do {
                        loop = 0;
                        inputMode[0] = General.stringNullCheckingInput("Member ic : ", "Invalid Member ic");
                        if (General.icValidation(inputMode[0]) == false) {
                            System.out.println("Invalid member ic");
                            loop = 1;
                        }
                    } while (loop == 1);
                    break;
                case 3:
                    inputMode[1] = "Name";
                    inputMode[0] = General.stringInput("Member name : ", "Invalid Member name");
                    break;
                case 4:
                    inputMode[1] = "Birth month";
                    do {
                        loop = 0;
                        intInput = General.intInput("Member birth month (1-12) : ", "Invalid Member month");
                        if (intInput < 1 || intInput > 12) {
                            System.out.println("Invalid Member month");
                            loop = 1;
                        }
                    } while (loop == 1);
                    inputMode[0] = String.format("%02d", intInput);
                    break;
                case 5:
                    inputMode[1] = "Active";
                    inputMode[0] = " ";
                    break;
                default:
                    System.out.println("Invalid selection");
                    loop = 1;
                    break;
            }

        } while (loop == 1);
        return inputMode;
    }

    public static int searchDisplay(String input, String mode, ArrayList<Member> members) {
        int loop;
        int indexSelection = -1;
        int intSelection;

        ArrayList<Member> memberSearch = Member.searchObj(input, mode, members);

        ArrayList<Integer> index = Member.searchIndex(input, mode, members);

        if (index.isEmpty()) {
            System.out.println("Member not found");
            return -1;
        } else if (index.size() == 1) {
            Member.displayDetails(memberSearch.get(0));
            return 0;
        } else {
            do {
                loop = 0;
                Member.displayMember(memberSearch);
                intSelection = General.intInput("Selection : ", "Invalid selection input");
                if (intSelection <= 0 || intSelection > index.size()) {
                    System.out.println("Invalid selection");
                    loop = 1;
                } else {
                    indexSelection = index.get(intSelection - 1);
                    Member.displayDetails(memberSearch.get(intSelection - 1));
                }
            } while (loop == 1);

        }
        return indexSelection;
    }

    public static ArrayList<Member> searchObj(String input, String mode, ArrayList<Member> members) {
        ArrayList<Member> member = new ArrayList<>();

        if ("IcChecking".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getIc().equals(input)) {
                    member.add(members.get(i));
                }
            }
        } else if ("IC".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getIc().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getId().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getName().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Active".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    member.add(members.get(i));
                }
            }
        } else if ("Inactive".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Inactive")) {
                    member.add(members.get(i));
                }
            }
        } else if ("All".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                member.add(members.get(i));

            }

        } else if ("Young adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Middle aged adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            member.add(members.get(i));
                        }
                    }
                }
            }
        } else if ("Old adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 45) {
                        member.add(members.get(i));
                    }
                }

            }
        }

        return member;
    }

    public static ArrayList<Integer> searchIndex(String input, String mode, ArrayList<Member> members) {
        ArrayList<Integer> index = new ArrayList<>();
        if ("IcChecking".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getIc().equals(input)) {
                    index.add(i);
                }
            }
        }else if ("IC".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getIc().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getId().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("Name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getName().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("Birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("Active".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    index.add(i);
                }
            }
        } else if ("Inactive".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Inactive")) {
                    index.add(i);
                }
            }
        } else if ("All".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                index.add(i);

            }

        } else if ("Young adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        index.add(i);
                    }
                }
            }
        } else if ("Middle aged adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            index.add(i);
                        }
                    }
                }
            }
        } else if ("Old adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getStatus().equals("Active")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 45) {
                        index.add(i);
                    }
                }

            }
        }
        return index;
    }

    public static void edit(ArrayList<Member> members) {
        writeFile("member", members);

    }

    public static void delete(ArrayList<Member> members) {
        writeFile("member", members);

    }

    public static void display() {
        ArrayList<Member> members = new ArrayList<>();
        int selection;
        Scanner sc = new Scanner(System.in);

        do {
            MemberDriver.readFile("member", members);
            System.out.println("Member Submenu");
            System.out.println("====================");
            System.out.println("1.Active Member");
            System.out.println("2.Inactive member");
            System.out.println("3.Member in current birth month");
            System.out.println("4.Young adult customer");
            System.out.println("5.Middle aged customer");
            System.out.println("6.Old adult customer");
            System.out.println("7.Display all member");
            System.out.println("====================");
            System.out.print("Enter your selection: ");
            selection = sc.nextInt();

            switch (selection) {
                case 1:
                    displayMember(searchObj("", "Active", members));
                    break;
                case 2:
                    displayMember(searchObj("", "Inactive", members));
                    break;
                case 3:
                    displayMember(searchObj(General.getCurrentDateTime("mm"), "Birth month", members));
                    break;
                case 4:
                    displayMember(searchObj("", "Young adult", members));
                    break;
                case 5:
                    displayMember(searchObj("", "Middle aged adult", members));
                    break;
                case 6:
                    displayMember(searchObj("", "Old adult", members));
                    break;
                case 7:
                    displayMember(searchObj("", "All", members));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid selection please try again");
                    break;
            }
        } while (selection > 5 || selection < 0);

    }

    public static void displayMember(ArrayList<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            System.out.printf("| %04d | %-8s | %-30s | %-12s | %-10s | %-11s | %-60s | \n", (i + 1), members.get(i).getId(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getBirthdate(), members.get(i).getPhoneNum(), members.get(i).getAddress());
        }
    }

    public static void displayMember(Member members) {
        System.out.printf("| %-8s | %-30s | %-12s | %-10s | %-11s | %-60s | \n", members.getId(), members.getName(), members.getIc(), members.getBirthdate(), members.getPhoneNum(), members.getAddress());

    }

    public static void displayDetails(Member members) {
        System.out.println(members.toString());

    }

    public static void writeFile(String fileName, ArrayList<Member> members) {
        String line;
        try {
            //Create FileWriter set to write mode
            FileWriter writer = new FileWriter("src\\" + fileName, false);

            for (int i = 0; i < members.size(); i++) {
                //Create a new record to be written
                line = String.format("%s|%s|%s|%s|%s|%s|%s\n", members.get(i).getId(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getBirthdate(), members.get(i).getPhoneNum(), members.get(i).getAddress(), members.get(i).getStatus());
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

    @Override
    public String toString() {
        return String.format("Member ID           : %s \nMember name         : %s \nMember IC           : %s \nMember birthdate    : %s \nMember phone number : %s \nMember Address      : %s\n", id, name, ic, birthdate, phoneNum, address);
    }
}
