/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class Staff extends Person {

    private String staffID;
    private String position;
    private String password;
    private double salary;
    private String accountStatus;
    private static int numOfStaff;
    public static final String FILE_NAME = "staff.txt";
    public static final String TEMP_FILE_NAME = "staffTemp.txt";

    //Constructor
    public Staff() {
        super("", "", "", "", "");
        this.staffID = "";
        this.position = "";
        this.password = "";
        this.salary = 0.0;
    }

    public Staff(String staffID, String name, String ic, String birthdate, String phoneNum, String address,
            String position, String password, double salary, String accountStatus) {
        super(name, ic, birthdate, phoneNum, address);
        this.staffID = staffID;
        this.position = position;
        this.password = password;
        this.salary = salary;
        this.accountStatus = accountStatus;
    }

    //Getter and setter
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public static int getNumOfStaff() {
        return numOfStaff;
    }

    public static void setNumOfStaff(int numOfStaff) {
        Staff.numOfStaff = numOfStaff;
    }

    @Override
    public String toString() {
        int age = General.ageCalc(birthdate);
        return "Staff ID              : " + staffID + "\n"
                + "Name                  : " + name + "\n"
                + "IC                    : " + ic + "\n"
                + "BirthDate(DD/MM/YYYY) : " + birthdate + "\n"
                + "Age                   : " + age + "\n"
                + "Phone Number          : " + phoneNum + "\n"
                + "Address               : " + address + "\n"
                + //format address to 3 lines
                "Position              : " + position + "\n"
                + "Account Status        : " + accountStatus;
    }

    public String toStringAdmin(String str) {
        if (str.equals("salary")) {
            return "Salary                : RM " + String.format("%.2f", salary);
        } else {
            return "Password              : " + password;
        }
    }

    //staffID validation ST-999
    public static String staffIDInput(String promptMsg, String errorMsg) {
        Scanner scanner = new Scanner(System.in);
        String staffID;
        int loop;
        do {
            loop = 0;
            System.out.print(promptMsg);
            staffID = scanner.nextLine().toUpperCase();

            if (staffID.length() == 6) {
                for (int i = 0; i < staffID.length(); i++) {
                    if (!staffID.substring(0, 3).equals("ST-")) {
                        System.out.println(errorMsg);
                        loop++;
                        break;
                    }
                }
            } else {
                System.out.println(errorMsg);
                loop++;
            }
        } while (loop == 1);
        return staffID;
    }

    //password validation
    public static String createPassword() {
        String password;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.print("New password: ");
            password = sc.nextLine();
            if (password.length() <= 8 || password.length() >= 32) {
                System.out.println("> Password length should be 8-32 characters/numbers.");
                loop = 1;
            } else {
                for (int i = 0; i < password.length(); i++) {
                    if (password.charAt(i) == ' ') {
                        System.out.println("> Password cannot contain space.");
                        loop = 1;
                        break;
                    }
                }
            }
        } while (loop == 1);
        return password;
    }

    //update number of staff
    public static void updateNumberOfStaff() {
        try ( FileReader reader = new FileReader("src\\" + FILE_NAME)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            numOfStaff = -1;
            while (bufferedReader.readLine() != null) {
                numOfStaff++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("staff.txt open failed.");
        }
    }

    //append one line staffInfo into file
    public static void appendStaff(String FILE_NAME, Staff staffInfo) {
        try ( FileWriter writer = new FileWriter("src\\" + FILE_NAME, true)) {
            String line = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%f|%s\n",
                    staffInfo.staffID, staffInfo.name, staffInfo.ic, staffInfo.birthdate,
                    staffInfo.phoneNum, staffInfo.address, staffInfo.position,
                    staffInfo.password, staffInfo.salary, staffInfo.accountStatus);

            writer.write(line);
            writer.close();

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    //output: all the staff detail
    //        all field "" = no result
    //parameter: name OR staffID OR ic
    //           "Name" OR "Staff ID" OR "IC"  >>search by
    public static Staff searchAllStaff(String searcher, String condition) {
        Staff staffResult = new Staff();
        boolean staffExist = false;

        //read file
        try ( FileReader reader = new FileReader("src\\" + FILE_NAME)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            OUTER:
            while ((line = bufferedReader.readLine()) != null) {
                String[] staffDetail = line.split("\\|");
                staffResult = new Staff(staffDetail[0], staffDetail[1], staffDetail[2],
                        staffDetail[3], staffDetail[4], staffDetail[5], staffDetail[6],
                        staffDetail[7], Double.parseDouble(staffDetail[8]), staffDetail[9]);
                switch (condition) {
                    case "Name" -> {
                        if (staffResult.getName().equals(searcher)) {
                            staffExist = true;
                            break OUTER;
                        }
                    }
                    case "Staff ID" -> {
                        if (staffResult.getStaffID().equals(searcher)) {
                            staffExist = true;
                            break OUTER;
                        }
                    }
                    case "IC" -> {
                        if (staffResult.getIc().equals(searcher)) {
                            staffExist = true;
                            break OUTER;
                        }
                    }
                    default ->
                        System.out.println("Error type. Check parameter.");
                }
            }
            if (!staffExist) {
                staffResult = new Staff();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("staff.txt open failed.");
        }

        return staffResult;
    }

    //create edit staff menu depend on (admin :true , non-admin :false)
    //password, salary, position, address, phone number, status, name, ic + birthdate
    public static int infoEditMenu(boolean isAdmin) {
        int selection, loop = 0;

        System.out.println("Modify field: - ");
        System.out.println("1 - Address");
        System.out.println("2 - Phone Number");
        System.out.println("3 - Password");
        if (isAdmin) {
            System.out.println("4 - Name");
            System.out.println("5 - IC & Birthdate");
            System.out.println("6 - Salary");
            System.out.println("7 - Position");
            System.out.println("8 - Account Status");
        }

        System.out.println("");
        System.out.println("0 - Comfirm");
        System.out.println("9 - Cancelled all changes");

        do {
            selection = General.intInput("  Selection: ", "Enter Integer only.");

            if (!isAdmin) {
                switch (selection) {
                    case 0, 1, 2, 3, 9 -> {
                        loop = 0;
                    }
                    default -> {
                        System.out.println("Select 0-3 or 9 only.");
                        loop++;
                    }
                }
            } else {
                if (selection >= 0 && selection <= 9) {
                    loop = 0;
                } else {
                    System.out.println("Select 0-9 only.");
                    loop++;
                }
            }
        } while (loop != 0);
        return selection;
    }

    public static Staff staffEditDetail(Staff editedStaff, boolean isAdmin, boolean isOther) {
        int selection2;
        do {
            selection2 = Staff.infoEditMenu(isAdmin);
            if (isAdmin) {
                switch (selection2) {
                    case 1 -> {
                        editedStaff.setAddress(General.stringNullCheckingInput("Address: ", "  Address cannot be empty."));
                    }
                    case 2 -> {
                        editedStaff.setPhoneNum(General.phoneInput("Phone Number(without '-'): "));
                    }
                    case 3 -> {
                        editedStaff.setPassword(Staff.createPassword());
                    }

                    case 4 -> {
                        editedStaff.setName(General.stringNullCheckingInput("Name: ", "  Name cannot be empty."));
                    }
                    case 5 -> {
                        editedStaff.setBirthdate(General.dateInput("Birthdate(DD/MM/YYYY): ", "  Wrong date format. Enter again."));
                        editedStaff.setIc(General.icInput("IC                   : "));
                    }
                    case 6 -> {
                        editedStaff.setSalary(General.doubleInput("Salary: RM ", "Only numbers requried."));
                    }
                    case 7 -> {
                        System.out.println("Position List:- ");
                        System.out.println("  1 - Normal Worker");
                        System.out.println("  2 - Human Resource");
                        System.out.println("  3 - Manager");
                        System.out.println("  4 - Program Admin");
                        int selectionP;
                        do {
                            selectionP = General.intInput("Select Position: ", "  Selection only Interger.");
                            switch (selectionP) {
                                case 1 ->
                                    editedStaff.setPosition("Normal Worker");
                                case 2 ->
                                    editedStaff.setPosition("Human Resource");
                                case 3 ->
                                    editedStaff.setPosition("Manager");
                                case 4 ->
                                    editedStaff.setPosition("Program Admin");
                                default ->
                                    System.out.println("  Selection only 1-4.");
                            }
                        } while (selectionP < 1 || selectionP > 4);
                        System.out.println("Position: " + editedStaff.getPosition());
                    }
                    case 8 -> {
                        if (!isOther) {
                            System.out.println("  Resign self not allowed.");
                        } else if(editedStaff.staffID.equals("ST-000")){
                            System.out.println("  Cannot resign program admin account.");
                        }
                        else {
                            if (editedStaff.getAccountStatus().equals("Active") || editedStaff.getAccountStatus().equals("Inactive")) {
                                editedStaff.setAccountStatus("Resign");
                            } else {
                                editedStaff.setAccountStatus("Inactive");
                            }
                        }
                    }
                    case 0 -> {
                        Staff.editStaffFile(editedStaff);
                        System.out.println("Edit complete.");
                        System.out.println(editedStaff.toString());
                        System.out.println(editedStaff.toStringAdmin("salary"));
                        System.out.println(editedStaff.toStringAdmin("password"));
                    }
                    case 9 -> {
                        System.out.println("Modify cancelled. Back to main menu.");
                    }
                }
            } else {
                switch (selection2) {
                    case 1 -> {
                        editedStaff.setAddress(General.stringNullCheckingInput("Address: ", "  Address cannot be empty."));
                    }
                    case 2 -> {
                        editedStaff.setPhoneNum(General.phoneInput("Phone Number(without '-'): "));
                    }
                    case 3 -> {
                        editedStaff.setPassword(Staff.createPassword());
                    }
                    case 0 -> {
                        Staff.editStaffFile(editedStaff);
                        System.out.println("Edit complete.");
                        System.out.println(editedStaff.toString());
                        System.out.println(editedStaff.toStringAdmin("salary"));
                        System.out.println(editedStaff.toStringAdmin("password"));
                    }
                    case 9 -> {
                        System.out.println("Modify cancelled. Back to main menu.");
                    }
                }
            }
        } while (selection2 != 9 && selection2 != 0);

        return editedStaff;
    }

    public static void editStaffFile(Staff editedStaff) {
        Staff staffResult;
        String lineFile, lineEdit;
        try ( FileReader reader = new FileReader("src\\" + FILE_NAME)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] staffDetail = line.split("\\|");
                staffResult = new Staff(staffDetail[0], staffDetail[1], staffDetail[2],
                        staffDetail[3], staffDetail[4], staffDetail[5], staffDetail[6],
                        staffDetail[7], Double.parseDouble(staffDetail[8]), staffDetail[9]);
                if (staffDetail[0].equals(editedStaff.getStaffID())) {
                    try ( FileWriter writer = new FileWriter("src\\" + TEMP_FILE_NAME, true)) {
                        lineEdit = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%f|%s\n",
                                editedStaff.staffID, editedStaff.name, editedStaff.ic, editedStaff.birthdate,
                                editedStaff.phoneNum, editedStaff.address, editedStaff.position,
                                editedStaff.password, editedStaff.salary, editedStaff.accountStatus);

                        writer.write(lineEdit);
                        writer.close();
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                } else {
                    try ( FileWriter writer = new FileWriter("src\\" + TEMP_FILE_NAME, true)) {
                        lineFile = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%f|%s\n",
                                staffResult.staffID, staffResult.name, staffResult.ic, staffResult.birthdate,
                                staffResult.phoneNum, staffResult.address, staffResult.position,
                                staffResult.password, staffResult.salary, staffResult.accountStatus);

                        writer.write(lineFile);
                        writer.close();
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                }
            }
            reader.close();
            //delete src\\staff.txt
            File deleteFile = new File("src\\" + FILE_NAME);
            if (!deleteFile.delete()) {
                System.out.println("Failed to delete the file \"src\\" + FILE_NAME + "\".");
            }
            //reneme src\\staffTemp.txt to src\\staff.txt
            File rename = new File("src\\" + TEMP_FILE_NAME);
            File renameResult = new File("src\\" + FILE_NAME);
            if (!rename.renameTo(renameResult)) {
                System.out.println("Rename Failed");
            }
        } catch (IOException e) {
            System.out.println(FILE_NAME + "open failed.");
        }

    }
}
