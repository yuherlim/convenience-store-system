
/**
 *
 * @author JiaQing
 */
import java.util.Scanner;

public class StaffDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int selection;
        //Staff submenu
        do {
            do {
                System.out.println("---------");
                System.out.println("| Staff |");
                System.out.println("---------");
                System.out.println("<" + "LOGINNAME" + "> | " + "POSITION");        //add login name and position
                Staff.updateNumberOfStaff();
                System.out.println("1 - Add Staff");
                System.out.println("2 - Display Staff");
                System.out.println("3 - Search Staff");
                System.out.println("4 - Edit Staff" + '\n');
                System.out.println("0 - Main Menu");
                selection = General.intInput("  Selection: ", "  Please only select 0~4.");
            } while (selection < 0 || selection > 4);

            switch (selection) {
                case 1 ->
                    StaffDriver.addStaff();
                case 2 -> 
                    StaffDriver.displayStaff();
                case 3 -> {
                }
                case 4 -> {
                }
                case 0 -> {
                    System.out.println("Return to Main Menu. Press any key to continue...");
                    scanner.nextLine();
                }
                default ->
                    System.out.println("  Please only select 0~4.");
            }
        } while (selection != 0);

    }

    public static void addStaff() {
        Scanner scanner = new Scanner(System.in);
        Staff addStaff = new Staff();

        System.out.println("-------------");
        System.out.println("| Add Staff |");
        System.out.println("-------------");
        System.out.println("<" + "LOGINNAME" + "> | " + "POSITION");        //add login name and position

        System.out.print("Name(same as IC): ");
        addStaff.setName(scanner.nextLine());

        do {
            System.out.print("IC(without '-'): ");
            addStaff.setIc(scanner.nextLine());
        } while (!General.icValidation(addStaff.getIc()));

        addStaff.setBirthdate(General.dateInput("Birthdate(DD/MM/YYYY): ", "Wrong date format. Enter again."));

        addStaff.setPhoneNum(General.phoneInput("Phone Number(without '-'): "));

        System.out.print("Address: ");
        addStaff.setAddress(scanner.nextLine());

        System.out.println("Position List:- ");
        System.out.println("  1 - Normal Worker");
        System.out.println("  2 - Human Resource");
        System.out.println("  3 - Manager");
        System.out.println("  4 - Program Admin");
        int selection;
        do {
            System.out.print("Select Position: ");
            selection = scanner.nextInt();
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

        addStaff.setSalary(General.doubleInput("Salary: RM ", "Only numbers requried."));

        addStaff.setStaffID(String.format("ST-%03d", Staff.getNumOfStaff() + 1));
        addStaff.setAccountStatus("Inactive");

        System.out.println(" New Staff Information");
        System.out.println("***********************");
        System.out.println(addStaff.toString());
        System.out.println("***********************");
        char yOrN;
        do {
            System.out.print("Comfirm to add new staff <" + addStaff.getStaffID() + ">?(Y)es/(N)o: ");
            yOrN = Character.toUpperCase(scanner.next().charAt(0));
            switch (yOrN) {
                case 'Y' -> {
                    //append file
                    Staff.appendStaff("staff.txt", addStaff);

                    Staff.setNumOfStaff(Staff.getNumOfStaff() + 1);
                    System.out.println("    == Staff Added ==");
                }
                case 'N' ->
                    System.out.println("    == Staff Add Cancelled ==");
                default ->
                    System.out.println("Only enter Y or N.");
            }
        } while (yOrN != 'Y' && yOrN != 'N');
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }

    public static void displayStaff() {
        System.out.println("-----------------");
        System.out.println("| Display Staff |");
        System.out.println("-----------------");
        System.out.println("<" + "LOGINNAME" + "> | " + "POSITION");        //add login name and position

        System.out.println("Staff ID  Name                            IC            Age  Phone Num    Position");
        System.out.println("--------  ------------------------------  ------------  ---  -----------  --------------");
        //read file
        //store in Staff
        //print all detail in one line
        //loop
        System.out.println();

    }
}
