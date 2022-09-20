
/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
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
        try ( FileReader reader = new FileReader("src\\staff.txt")) {
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
    public static void appendStaff(String fileName, Staff staffInfo) {
        try ( FileWriter writer = new FileWriter("src\\" + fileName, true)) {
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
        try ( FileReader reader = new FileReader("src\\staff.txt")) {
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
                        if (staffResult.getStaffID().equals(searcher)) {
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
}
