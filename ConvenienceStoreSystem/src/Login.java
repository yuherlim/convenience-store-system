
/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
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
                System.out.print("Staff ID: ");
                String staffIdEnter = scanner.nextLine().toUpperCase();

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
                    System.out.println("staff.txt open failed.");
                }

                if (!staffIdExist) {
                    System.out.println("  >Staff ID not exist.");
                }

            } while (!staffIdExist);

            if ("Active".equals(staffLogin.getAccountStatus())) {
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
                                System.out.print("Press any key to continue...");
                                scanner.nextLine();
                            }
                        }
                    }
                }
            } else if ("Inactive".equals(staffLogin.getAccountStatus())) {
                loop++;
            } else {
                System.out.println("");
            }
        } while (loop == 0);
    }
}
