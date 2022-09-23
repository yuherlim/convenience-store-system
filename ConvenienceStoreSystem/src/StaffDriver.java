
/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StaffDriver {

    public static void main(String[] args, Staff staffLogin) {
        int selection;
        do {

            System.out.println("---------");
            System.out.println("| Staff |");
            System.out.println("---------");
            System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');
            Staff.updateNumberOfStaff();
            System.out.println("1 - Add Staff");
            System.out.println("2 - Display Staff");
            System.out.println("3 - Search Staff");
            System.out.println("4 - Modify Staff" + '\n');
            System.out.println("0 - Main Menu");
            do {
                selection = General.intInput("Selection: ", "  Please only select 0~4.");
                switch (selection) {
                    case 1 -> {
                        StaffDriver.addStaff(staffLogin);
                        General.clearScreen();
                    }
                    case 2 -> {
                        StaffDriver.displayStaff(staffLogin);
                        General.clearScreen();
                    }
                    case 3 -> {
                        StaffDriver.searchStaff(staffLogin);
                        General.clearScreen();
                    }
                    case 4 -> {
                        StaffDriver.editStaff(staffLogin);
                        General.clearScreen();
                    }
                    case 0 -> {
                        System.out.println("****************************");
                        System.out.println("Return to Main Menu.");
                        General.systemPause();
                    }
                    default ->
                        System.out.println("  Please only select 0~4.");
                }
            } while (selection < 0 || selection > 4);
            General.clearScreen();
        } while (selection != 0);
    }

    //add new staff and write into txt file
    public static void addStaff(Staff staffLogin) {
        Scanner scanner = new Scanner(System.in);
        Staff addStaff = new Staff();
        
        General.clearScreen();
        System.out.println("-------------");
        System.out.println("| Add Staff |");
        System.out.println("-------------");
        System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');

        addStaff.setName(General.stringNullCheckingInput("Name(same as IC): ", "  Name cannot be empty."));
        addStaff.setBirthdate(General.dateInput("Birthdate(DD/MM/YYYY): ", "  Wrong date format. Enter again."));
        addStaff.setIc(General.icInput("IC(without '-'): "));
        addStaff.setPhoneNum(General.phoneInput("Phone Number(without '-'): "));
        addStaff.setAddress(General.stringNullCheckingInput("Address: ", "  Address cannot be empty."));

        System.out.println("Position List:- ");
        System.out.println("  1 - Normal Worker");
        System.out.println("  2 - Human Resource");
        System.out.println("  3 - Manager");
        System.out.println("  4 - Program Admin");
        int selection;
        do {
            selection = General.intInput("Select Position: ", "  Selection only Interger.");
            switch (selection) {
                case 1 ->
                    addStaff.setPosition("Normal Worker");
                case 2 ->
                    addStaff.setPosition("Human Resource");
                case 3 ->
                    addStaff.setPosition("Manager");
                case 4 ->
                    addStaff.setPosition("Program Admin");
                default ->
                    System.out.println("  Selection only 1-4.");
            }
        } while (selection < 1 || selection > 4);
        System.out.println("Position: " + addStaff.getPosition());

        addStaff.setSalary(General.doubleInput("Salary: RM ", "  Only numbers requried."));

        addStaff.setStaffID(String.format("ST-%03d", Staff.getNumOfStaff() + 1));
        addStaff.setAccountStatus("Inactive");

        System.out.println("-----------------------");
        System.out.println(" New Staff Information");
        System.out.println("**********************************************");
        System.out.println(addStaff.toString());
        System.out.println(addStaff.toStringAdmin("salary"));
        System.out.println("**********************************************");
        char yOrN;
        do {
            System.out.print("Comfirm to add new staff <" + addStaff.getStaffID() + ">?(Y)es/(N)o: ");
            yOrN = Character.toUpperCase(scanner.next().charAt(0));
            scanner.nextLine();
            switch (yOrN) {
                case 'Y' -> {
                    //append file
                    Staff.appendStaff(Staff.FILE_NAME, addStaff);

                    Staff.setNumOfStaff(Staff.getNumOfStaff() + 1);
                    System.out.println("    == Staff Added ==");
                }
                case 'N' ->
                    System.out.println("    == Staff Add Cancelled ==");
                default ->
                    System.out.println("  Only enter Y or N.");
            }
        } while (yOrN != 'Y' && yOrN != 'N');
        General.systemPause();
        General.clearScreen();
    }

    //display all staff info or active+inactive staff info
    public static void displayStaff(Staff staffLogin) {
        Staff displayStaff;
        int age;
        
        General.clearScreen();
        System.out.println("-----------------");
        System.out.println("| Display Staff |");
        System.out.println("-----------------");
        System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');

        if (staffLogin.getPosition().equals("Normal Worker")) {
            System.out.println("Staff ID  Name                            IC            Age  Phone Num    Position        Account Status");
            System.out.println("--------  ------------------------------  ------------  ---  -----------  --------------  --------------");

            try ( FileReader reader = new FileReader("src\\" + Staff.FILE_NAME)) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] staffDetail = line.split("\\|");
                    displayStaff = new Staff(staffDetail[0], staffDetail[1], staffDetail[2],
                            staffDetail[3], staffDetail[4], staffDetail[5], staffDetail[6],
                            staffDetail[7], Double.parseDouble(staffDetail[8]), staffDetail[9]);
                    //Calculate age
                    age = General.ageCalc(displayStaff.getBirthdate());
                    System.out.printf(" %s   %-30s  %s  %3d  %-11s  %-14s  %-14s\n",
                            displayStaff.getStaffID(), displayStaff.getName(), displayStaff.getIc(), age,
                            displayStaff.getPhoneNum(), displayStaff.getPosition(),
                            displayStaff.getAccountStatus());
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("  staff.txt open failed.");
            }
        } else {
            System.out.println("Staff ID  Name                            IC            Age  Phone Num    Position        Account Status  Salary");
            System.out.println("--------  ------------------------------  ------------  ---  -----------  --------------  --------------  ---------");

            try ( FileReader reader = new FileReader("src\\" + Staff.FILE_NAME)) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] staffDetail = line.split("\\|");
                    displayStaff = new Staff(staffDetail[0], staffDetail[1], staffDetail[2],
                            staffDetail[3], staffDetail[4], staffDetail[5], staffDetail[6],
                            staffDetail[7], Double.parseDouble(staffDetail[8]), staffDetail[9]);
                    //Calculate age
                    age = General.ageCalc(displayStaff.getBirthdate());
                    System.out.printf(" %s   %-30s  %s  %3d  %-11s  %-14s  %-14s  %5.2f\n",
                            displayStaff.getStaffID(), displayStaff.getName(), displayStaff.getIc(), age,
                            displayStaff.getPhoneNum(), displayStaff.getPosition(),
                            displayStaff.getAccountStatus(), displayStaff.getSalary());
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("  staff.txt open failed.");
            }
        }
        General.systemPause();
        General.clearScreen();
    }

    //Search staff by Staff id / name / IC
    //search by name function in Staff class
    public static void searchStaff(Staff staffLogin) {
        Staff searchStaff;
        int selection;
        boolean isAdmin = !staffLogin.getPosition().equals("Normal Worker");
        
        do {
            do {
                General.clearScreen();
                System.out.println("----------------");
                System.out.println("| Search Staff |");
                System.out.println("----------------");
                System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');

                System.out.println("Search staff by: -");
                System.out.println("1 - Staff ID");
                System.out.println("2 - Name");
                System.out.println("3 - IC" + '\n');
                System.out.println("0 - Staff Menu");

                selection = General.intInput("  Selection: ", "  Please only select 0~4.");
            } while (selection < 0 || selection > 3);

            switch (selection) {
                case 1 -> {
                    String staffIDSearch = Staff.staffIDInput("Staff ID : ", "  Invalid Staff ID.");
                    //read file
                    searchStaff = Staff.searchAllStaff(staffIDSearch, "Staff ID");

                    if (!searchStaff.getName().equals("")) {
                        if (isAdmin) {
                            System.out.println(searchStaff.toString() + '\n'
                                    + searchStaff.toStringAdmin("salary") + '\n'
                                    + searchStaff.toStringAdmin("password"));
                        } else {
                            System.out.println(searchStaff.toString());
                        }
                        break;
                    } else {
                        System.out.println("  Staff not exist.");
                    }
                }
                case 2 -> {
                    String nameSearch = General.stringNullCheckingInput("Name : ", "  Name cannot be empty.");
                    //read file
                    searchStaff = Staff.searchAllStaff(nameSearch, "Name");

                    if (!searchStaff.getName().equals("")) {
                        if (isAdmin) {
                            System.out.println(searchStaff.toString() + '\n'
                                    + searchStaff.toStringAdmin("salary") + '\n'
                                    + searchStaff.toStringAdmin("password"));
                        } else {
                            System.out.println(searchStaff.toString());
                        }
                        break;
                    } else {
                        System.out.println("  Staff not exist.");
                    }
                }
                case 3 -> {
                    String icSearch = General.icInput("IC : ");
                    //read file
                    searchStaff = Staff.searchAllStaff(icSearch, "IC");

                    if (!searchStaff.getIc().equals("")) {
                        if (isAdmin) {
                            System.out.println(searchStaff.toString() + '\n'
                                    + searchStaff.toStringAdmin("salary") + '\n'
                                    + searchStaff.toStringAdmin("password"));
                        } else {
                            System.out.println(searchStaff.toString());
                        }
                        break;
                    } else {
                        System.out.println("  Staff not exist.");
                    }
                }
                case 0 ->
                    System.out.println("Back to Staff Menu.");
                default ->
                    System.out.println("  Please only select 0~4.");
            }
            General.systemPause();
            General.clearScreen();
        } while (selection != 0);
    }

    //edit info
    //admin : 
    //        access : all (search by - name, staff id, ic)
    //        edit   : password, salary, position, address, phone number, status, name, ic + birthdate
    //none admin:
    //        access : own
    //        edit   : password, address, phone number
    public static void editStaff(Staff staffLogin) {
        boolean isAdmin = !staffLogin.getPosition().equals("Normal Worker");
        boolean isOther = isAdmin;
        int selection;
        Staff editedStaff = staffLogin;
        
        General.clearScreen();
        System.out.println("----------------");
        System.out.println("| Modify Staff |");
        System.out.println("----------------");
        System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');

        if (isAdmin) {
            System.out.println("Modify Information of: -");
            System.out.println("1 - Own");
            System.out.println("2 - Other Staff");
            System.out.println("");
            System.out.println("0 - Back to Staff Menu");
            do {
                selection = General.intInput("Selection: ", "  Enter Integer only.");
                System.out.println("");

                switch (selection) {
                    case 1 -> {
                        isOther = false;
                        editedStaff = staffLogin;
                        editedStaff = Staff.staffEditDetail(editedStaff, isAdmin, isOther);
                    }
                    case 2 -> {
                        isOther = true;
                        System.out.println("Enter Staff ID to be edited.");
                        String staffIDSearch = Staff.staffIDInput("Staff ID : ", "  Invalid Staff ID.");
                        //read file
                        editedStaff = Staff.searchAllStaff(staffIDSearch, "Staff ID");

                        if (!editedStaff.getStaffID().equals("")) {
                            editedStaff = Staff.staffEditDetail(editedStaff, isAdmin, isOther);
                        } else {
                            System.out.println("  Staff not exist.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Back to Staff Menu...");
                    }
                    default -> {
                        System.out.println("  Enter 0-2 only.");
                    }
                }
            } while (selection != 0);
        } else {
            editedStaff = staffLogin;
            editedStaff = Staff.staffEditDetail(editedStaff, isAdmin, isOther);
        }
        //write editedStaff into file
        Staff.editStaffFile(editedStaff);
        System.out.println("Staff " + editedStaff.getStaffID() + " edited successful.");
        General.systemPause();
        General.clearScreen();
    }
}
