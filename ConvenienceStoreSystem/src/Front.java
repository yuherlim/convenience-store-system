
/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class Front {

    public static int homePage() {
        int selection;
        System.out.println("=====================");
        System.out.println("  Convenience Store");
        System.out.println("=====================");
        System.out.println("     POS system");
        System.out.println("");
        System.out.println("1 - Staff Login");
        System.out.println("0 - Exit Program");

        do {
            selection = General.intInput("Selection: ", "  Enter Integer only.");
            if (selection != 0 && selection != 1) {
                System.out.println("  Select 0 and 1 only.");
            }
        } while (selection != 0 && selection != 1);
        return selection;
    }

    public static Staff loginPage() {

        Scanner scanner = new Scanner(System.in);
        Staff staffLogin = new Staff();
        int loop;

        do {
            loop = 0;
            System.out.println("---------------");
            System.out.println("| Staff Login |");
            System.out.println("---------------");

            //staff id enter
            boolean staffIdExist = false;
            do {
                String staffIdEnter = Staff.staffIDInput("Staff ID: ", "Invalid Staff ID.");

                try ( FileReader reader = new FileReader("src\\staff.txt")) {
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] staffDetail = line.split("\\|");

                        if (staffDetail[0].equals(staffIdEnter)) {
                            staffLogin = new Staff(staffDetail[0], staffDetail[1], staffDetail[2],
                                    staffDetail[3], staffDetail[4], staffDetail[5], staffDetail[6],
                                    staffDetail[7], Double.parseDouble(staffDetail[8]), staffDetail[9]);
                            staffIdExist = true;
                            break;
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    System.out.println("  staff.txt open failed.");
                }

                if (!staffIdExist) {
                    System.out.println("  >Staff ID not exist.");
                }
            } while (!staffIdExist);

            switch (staffLogin.getAccountStatus()) {
                case "Active" -> {
                    //password enter 3 time at most
                    for (int i = 2; i >= 0; i--) {
                        System.out.print("Password: ");
                        String passwordEnter = scanner.nextLine();

                        if (passwordEnter.equals(staffLogin.getPassword())) {
                            System.out.println("*** Hi, " + staffLogin.getName() + ". ***");
                            loop++;
                            break;
                        } else {
                            switch (i) {
                                case 2 ->
                                    System.out.println("  >Wrong password. " + i + " tries left.");
                                case 1 ->
                                    System.out.println("  >Wrong password. " + i + " try left.");
                                default -> {
                                    System.out.println("  >Wrong password. Contact Human Resource or Manager to change password.");
                                }
                            }
                        }
                    }
                }
                case "Inactive" -> {
                    staffLogin.setPassword(Staff.createPassword());
                    staffLogin.setAccountStatus("Active");
                    Staff.editStaffFile(staffLogin);
                    System.out.println("*** Hi, " + staffLogin.getName() + ". ***");
                    loop++;
                }
                default ->
                    System.out.println("  >Staff not in company.");
            }
            General.systemPause();
            General.clearScreen();
        } while (loop == 0);
        return staffLogin;
    }
    
    public static int mainMenu(Staff staffLogin){
        int selection;

        System.out.println("===========");
        System.out.println(" Main Menu");
        System.out.println("===========");
        System.out.println("<" + staffLogin.getName() + "> | " + staffLogin.getPosition() + '\n');
        System.out.println("");
        System.out.println("1 - Sales");
        System.out.println("2 - Inventory Management");
        System.out.println("3 - Product");
        System.out.println("4 - Memberships");
        System.out.println("5 - Supplier");
        System.out.println("6 - Staff");
        System.out.println("");
        System.out.println("0 - Home Page");
        
        do {
            selection = General.intInput("Selection: ", "  Enter Integer only.");
            if (selection < 0 || selection > 6) {
                System.out.println("  Select 0 - 6 only.");
            }
        } while (selection < 0 || selection > 6);
        return selection;
    }

}
