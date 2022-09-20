
/**
 *
 * @author JiaQing
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

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
        return "Staff ID              : " + staffID + "\n"
                + "Name                  : " + name + "\n"
                + "IC                    : " + ic + "\n"
                + "BirthDate(DD/MM/YYYY) : " + birthdate + "\n"
                + "Phone Number          : " + phoneNum + "\n"
                + "Address               : " + address + "\n"
                + //format address to 3 lines
                "Position              : " + position + "\n"
                + "Salary                : RM " + String.format("%.2f", salary) + "\n"
                + "Account Status        : " + accountStatus;
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

    //equal
}
