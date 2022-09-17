
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ong58
 */
public class General {

    public static int intValidation(String promptMsg, String errorMsg) {
        int input = 0;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.println(promptMsg);
            try {
                input = sc.nextInt();
            } catch (Exception e) {
                System.out.println(errorMsg);
                loop = 1;
            }
        }while(loop == 1);
        return input;
    }

    public static void phoneNumValidation() {
        Scanner sc = new Scanner(System.in);
        String phoneNum;
        int loop = 0;

        do {
            loop = 0;
            System.out.print("phoneNum: ");
            phoneNum = sc.next();

            if (phoneNum.length() > 11 || phoneNum.length() < 10) {
                System.out.println("Invalid length of phone number");
                loop = 1;
            } else {
                for (int i = 0; i < phoneNum.length(); i++) {
                    if (Character.isDigit(phoneNum.charAt(i)) == false) {
                        System.out.println("Invalid phone number format");
                        loop = 1;
                        break;
                    }
                }
            }
        } while (loop == 1);
    }

    public static void icValidation(String birthDate) {
        Scanner sc = new Scanner(System.in);
        String ic;
        int loop = 0;

        do {
            loop = 0;
            System.out.print("ic: ");
            ic = sc.next();

            if (ic.charAt(0) != birthDate.charAt(8) || ic.charAt(1) != birthDate.charAt(9)) {
                System.out.println("Invalid ic number format");
                loop = 1;
                continue;
            }

            if (ic.charAt(2) != birthDate.charAt(3) || ic.charAt(3) != birthDate.charAt(4)) {
                System.out.println("Invalid ic number format");
                loop = 1;
                continue;
            }

            if (ic.charAt(4) != birthDate.charAt(0) || ic.charAt(5) != birthDate.charAt(1)) {
                System.out.println("Invalid ic number format");
                loop = 1;
                continue;
            }

            if (ic.length() != 12) {
                System.out.println("Invalid length of ic");
                loop = 1;
            } else {
                for (int i = 0; i < ic.length(); i++) {
                    if (Character.isDigit(ic.charAt(i)) == false) {
                        System.out.println("Invalid ic number format");
                        loop = 1;
                        break;
                    }
                }
            }
        } while (loop == 1);
    }

    public static boolean dateChecking(String dateStr) {
        DateFormat date = new SimpleDateFormat("dd/mm/yyyy");
        date.setLenient(false);
        try {
            date.parse(dateStr);
        } catch (ParseException e) {
            return false;

        }
        return true;
    }
}
