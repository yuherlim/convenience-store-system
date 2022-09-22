
import java.io.FileWriter;
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

    // Member search function
    public static void searchMember() {
        ArrayList<Member> members = new ArrayList<>();
        char charSelection;
        String[] inputMode;
        MemberDriver.readFile("member", members);

        do {
            General.clearScreen();
            System.out.println("=============");
            System.out.println("|Search Member|");
            System.out.println("=============");
            inputMode = searchMenu();
            searchDisplay(inputMode[0], inputMode[1], "All", members);

            charSelection = General.yesNoInput("Search another Member (Y/N)", "  Invalid Selection");

        } while (charSelection == 'Y');

    }

    // Member search mode menu
    public static String[] searchMenu() {
        int selection;
        int loop;
        int intInput;
        String[] inputMode = new String[2];

        do {
            loop = 0;
            System.out.println("Search Member by :");
            System.out.println("1 - ID");
            System.out.println("2 - IC");
            System.out.println("3 - Name");
            System.out.println("4 - Birth month");
            System.out.println("5 - All Member");
            System.out.println("===============");
            selection = General.intInput("Selection : ", "  Invalid selection");

            switch (selection) {
                case 1:
                    inputMode[1] = "ID";
                    do {
                        loop = 0;
                        inputMode[0] = General.stringNullCheckingInput("Member ID                     : ", "s").toUpperCase();
                        if (memberIdValidation(inputMode[0]) == false) {
                            System.out.println("  Invalid member id");
                            loop = 1;
                        }
                    } while (loop == 1);
                    break;
                case 2:
                    inputMode[1] = "IC";
                    do {
                        loop = 0;
                        inputMode[0] = General.stringNullCheckingInput("Member IC                     : ", "  Invalid Member IC");
                        if (General.icValidation(inputMode[0]) == false) {
                            System.out.println("  Invalid member IC");
                            loop = 1;
                        }
                    } while (loop == 1);
                    break;
                case 3:
                    inputMode[1] = "Name";
                    inputMode[0] = General.stringInput("Member name                   : ", "  Invalid Member name");
                    break;
                case 4:
                    inputMode[1] = "Birth month";
                    do {
                        loop = 0;
                        intInput = General.intInput("Member birth month (1-12)      : ", "  Invalid Member month");
                        if (intInput < 1 || intInput > 12) {
                            System.out.println("  Invalid Member month");
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
                    System.out.println("  Invalid selection");
                    loop = 1;
                    break;
            }

        } while (loop == 1);
        return inputMode;
    }

    //Member search function for displaying the member details searched
    public static int searchDisplay(String input, String mode, String status, ArrayList<Member> members) {
        int loop;
        int indexSelection = -1;
        int intSelection;

        ArrayList<Member> memberSearch = Member.searchObj(input, mode, status, members);

        ArrayList<Integer> index = Member.searchIndex(input, mode, status, members);

        if (index.isEmpty()) {
            System.out.println("  Member not found");
            return -1;
        } else if (index.size() == 1) {
            if (memberSearch.get(0).getStatus().equals("Active")) {
                Member.displayDetails(memberSearch.get(0));
                return 0;
            } else {
                System.out.println("  Member Inactive");
                return -1;
            }

        } else {
            do {
                loop = 0;
                Member.displayMember(memberSearch);
                intSelection = General.intInput("Selection : ", "  Invalid selection input");
                if (intSelection <= 0 || intSelection > index.size()) {
                    System.out.println("  Invalid selection");
                    loop = 1;
                } else {
                    indexSelection = index.get(intSelection - 1);
                    if (memberSearch.get(intSelection - 1).getStatus().equals("Active")) {
                        Member.displayDetails(memberSearch.get(intSelection - 1));
                    } else {
                        System.out.println("  Member Inactive");
                        return -1;
                    }

                }
            } while (loop == 1);

        }
        return indexSelection;
    }

    //Search function for returning the object of the matched object in the arraylist by passing in the criterions for searching
    public static ArrayList<Member> searchObj(String input, String mode, String status, ArrayList<Member> members) {
        ArrayList<Member> member = new ArrayList<>();

        if ("IC".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getIc().equals(input)) {
                        member.add(members.get(i));
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getIc().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getId().equals(input)) {
                        member.add(members.get(i));
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getId().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getName().equals(input)) {
                        member.add(members.get(i));
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getName().equals(input)) {
                        member.add(members.get(i));
                    }
                }
            }
        } else if ("Birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                        member.add(members.get(i));
                    } else if (members.get(i).getStatus().equals(status)) {
                        if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                            member.add(members.get(i));
                        }
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
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        member.add(members.get(i));
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        member.add(members.get(i));
                    }
                }
            }

        } else if ("Middle aged adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            member.add(members.get(i));
                        }
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            member.add(members.get(i));
                        }
                    }
                }
            }
        } else if ("Old adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 45) {
                        member.add(members.get(i));
                    }

                } else if (members.get(i).getStatus().equals(status)) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 45) {
                        member.add(members.get(i));
                    }
                }
            }
        }

        return member;
    }

    //Search function for returning the index of the matched object in the arraylist by passing in the criterions for searching
    public static ArrayList<Integer> searchIndex(String input, String mode, String status, ArrayList<Member> members) {
        ArrayList<Integer> index = new ArrayList<>();

        if ("IC".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getIc().equals(input)) {
                        index.add(i);
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getIc().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("ID".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getId().equals(input)) {
                        index.add(i);
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getId().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("Name".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getName().equals(input)) {
                        index.add(i);
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (members.get(i).getName().equals(input)) {
                        index.add(i);
                    }
                }
            }
        } else if ("Birth month".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                        index.add(i);
                    } else if (members.get(i).getStatus().equals(status)) {
                        if (members.get(i).getBirthdate().substring(3, 5).equals(input)) {
                            index.add(i);
                        }
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
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        index.add(i);
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (General.ageCalc(members.get(i).getBirthdate()) <= 30) {
                        index.add(i);
                    }
                }
            }

        } else if ("Middle aged adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            index.add(i);
                        }
                    }
                } else if (members.get(i).getStatus().equals(status)) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 30) {
                        if (General.ageCalc(members.get(i).getBirthdate()) <= 45) {
                            index.add(i);
                        }
                    }
                }
            }
        } else if ("Old adult".equals(mode)) {
            for (int i = 0; i < members.size(); i++) {
                if (status.equals("All")) {
                    if (General.ageCalc(members.get(i).getBirthdate()) > 45) {
                        index.add(i);
                    }

                } else if (members.get(i).getStatus().equals(status)) {
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
        char charSelection;
        int selection;
        Scanner sc = new Scanner(System.in);
        int loop;
        //Read member txt file
        MemberDriver.readFile("member", members);
        do {
            do {
                loop = 0;

                //Prompt and get user selection on which type of member record needed to be displayed
                General.clearScreen();
                System.out.println("================");
                System.out.println("|Member Display|");
                System.out.println("================");
                System.out.println("1.Active Member");
                System.out.println("2.Inactive member");
                System.out.println("3.Member in current birth month");
                System.out.println("4.Young adult customer");
                System.out.println("5.Middle aged customer");
                System.out.println("6.Old adult customer");
                System.out.println("7.Display all member");
                System.out.println("===============");
                System.out.print("Enter your selection: ");
                selection = sc.nextInt();

                switch (selection) {
                    case 1:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj("", "Active", "All", members));
                        System.out.println("=====================================================================================");
                        break;
                    case 2:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj("", "Inactive", "All", members));
                        System.out.println("=====================================================================================");
                        break;
                    case 3:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj(General.getCurrentDateTime("mm"), "Birth month", "Active", members));
                        System.out.println("=====================================================================================");
                        break;
                    case 4:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj("", "Young adult", "Active", members));
                        System.out.println("=====================================================================================");
                        break;
                    case 5:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("======================================================================================");
                        displayMember(searchObj("", "Middle aged adult", "Active", members));
                        System.out.println("======================================================================================");
                        break;
                    case 6:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj("", "Old adult", "Active", members));
                        System.out.println("=====================================================================================");
                        break;
                    case 7:
                        System.out.println("======================");
                        System.out.println("|Active Member record|");
                        System.out.println("======================");
                        System.out.println(" ");
                        System.out.println("=====================================================================================");
                        displayMember(searchObj("", "All", "Active", members));
                        System.out.println("=====================================================================================");
                        break;
                    default:
                        System.out.println("  Invalid selection");
                        loop = 1;
                        break;

                }
            } while (loop == 1);

            charSelection = General.yesNoInput("Display another member (Y/N) : ", "  Invalid Selection");
        } while (charSelection == 'Y');
    }

    public static void displayMember(ArrayList<Member> members) {
        System.out.println("========================================================================================================================================================================");
        for (int i = 0; i < members.size(); i++) {
            System.out.printf("| %04d | %-8s | %-30s | %-12s | %-10s | %-11s | %-60s | %-8s |\n", (i + 1), members.get(i).getId(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getBirthdate(), members.get(i).getPhoneNum(), members.get(i).getAddress(), members.get(i).getStatus());
            System.out.println("========================================================================================================================================================================");
            if ((i + 1) % 20 == 0) {
                General.systemPause();
            }
        }
    }

    public static void displayMember(Member members) {
        //Display Member details
        System.out.printf("| %-8s | %-30s | %-12s | %-10s | %-11s | %-60s | \n", members.getId(), members.getName(), members.getIc(), members.getBirthdate(), members.getPhoneNum(), members.getAddress());

    }

    public static void displayDetails(Member members) {
        //Display Member details{
        System.out.println(members.toString());

    }

    //Write file function
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

    //Member ID validation
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
