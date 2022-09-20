
import java.io.FileWriter;
import java.util.ArrayList;

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
    public Member(String id, String name, String ic, String birthdate, String phoneNum, String address) {
        super(name, ic, birthdate, phoneNum, address);
        this.id = id;
    }
    //Setter and getter

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIc() {
        return ic;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public static int getNumOfCustomer() {
        return NumOfCustomer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void setNumOfCustomer(int NumOfCustomer) {
        Member.NumOfCustomer = NumOfCustomer;
    }

    @Override
    public String toString() {
        return super.toString() + ",id=" + id;
    }

    public static void add(ArrayList<Member> members) {

        writeFile("member", members);

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

        MemberDriver.readFile("member", members);

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
                charSelection = Character.toUpperCase(General.charInput("Search another member(Y/N): ", "Invalid selection input"));
                if (charSelection != 'N' && charSelection != 'Y') {
                    System.out.println("Invalid selection");
                    loop = 1;
                }
            } while (loop == 1);

        } while (charSelection == 'Y');

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

    public static void edit(ArrayList<Member> members) {

        writeFile("member", members);

    }

    public static void delete(ArrayList<Member> members) {

        writeFile("member", members);

    }

    public static void display(ArrayList<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            System.out.printf("| %04d | %-8s | %-30s | %-12s | %-11s | %-60s |", i, members.get(i).getId(), members.get(i).getName(), members.get(i).getIc(), members.get(i).getPhoneNum(), members.get(i).getAddress());
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
