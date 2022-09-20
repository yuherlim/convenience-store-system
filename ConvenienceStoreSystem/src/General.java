
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String stringInput(String promptMsg, String errorMsg) {
        String input;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.print(promptMsg);
            input = sc.nextLine();
            for (int i = 0; i < input.length(); i++) {
                if (Character.isLetter(input.charAt(i)) == false && input.charAt(i) != ' ') {
                    System.out.println(errorMsg);
                    loop = 1;
                    break;
                }
            }
        } while (loop == 1);
        return input;
    }

    public static int intInput(String promptMsg, String errorMsg) {
        int input = 0;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.print(promptMsg);
            try {
                input = sc.nextInt();
                sc.nextLine();      //get rid of the newline
            } catch (Exception e) {
                System.out.println(errorMsg);
                loop = 1;
                sc.nextLine();      //get rid of the newline if string is inputted.
            }
        } while (loop == 1);
        return input;
    }

    public static double doubleInput(String promptMsg, String errorMsg) {
        double input = 0.00;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            
            loop = 0;
            System.out.print(promptMsg);
            try {
                input = sc.nextDouble();
                sc.nextLine();      //get rid of the newline
            } catch (Exception e) {
                System.out.println(errorMsg);
                loop = 1;
                sc.nextLine();      //get rid of the newline if string is inputted.
            }
            
        } while (loop == 1);
        return input;
    }

    public static char charInput(String promptMsg, String errorMsg) {
        String input;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.print(promptMsg);
            input = sc.next();
            if ((Character.isLetter(input.charAt(0)) != true) && (input.charAt(0) != ' ' && input.charAt(1) == '\n')) {
                System.out.println(errorMsg);
                loop = 1;
            }
        } while (loop == 1);
        return input.charAt(0);
    }
    
    public static char yesNoInput(String promptMsg, String errorMsg) {
        char input;
        int loop;
        do {
            input = Character.toUpperCase(charInput(promptMsg, errorMsg));
            loop = 0;
            if (input != 'Y' && input != 'N') {
                System.out.println("Invalid input, please enter Y or N.");
                loop = 1;
            }
               
        } while (loop == 1);
        return input;
    }

    public static String phoneInput() {
        Scanner sc = new Scanner(System.in);
        String phoneNum;
        int loop = 0;

        do {
            System.out.print("phoneNum: ");
            phoneNum = sc.next();

        } while (phoneNumValidation(phoneNum) == false);
        return phoneNum;
    }

    public static boolean phoneNumValidation(String phoneNum) {
        if (phoneNum.length() > 11 || phoneNum.length() < 10) {
            System.out.println("Invalid length of phone number");
            return false;
        } else {
            for (int i = 0; i < phoneNum.length(); i++) {
                if (Character.isDigit(phoneNum.charAt(i)) == false) {
                    System.out.println("Invalid phone number format");
                    return false;
                }
            }
        }
        return true;
    }

    public static String icInput(String birthDate) {
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

        } while (icValidation(ic) == false || loop == 1);
        return ic;
    }

    public static boolean icValidation(String ic) {
        if (ic.length() != 12) {
            System.out.println("Invalid length of ic");
            return false;
        } else {
            for (int i = 0; i < ic.length(); i++) {
                if (Character.isDigit(ic.charAt(i)) == false) {
                    System.out.println("Invalid ic number format");
                    return false;
                }
            }
        }
        return true;
    }

    public static String dateInput(String promptMsg, String errorMsg) {
        Scanner sc = new Scanner(System.in);
        String input;
        int loop;
        do {
            loop = 0;
            System.out.print(promptMsg);
            input = sc.next();
            if (dateChecking(input) == false) {
                System.out.println(errorMsg);
                loop = 1;
            }

        } while (loop == 1);
        return input;
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

    public static String getCurrentDateTime(String mode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        if ("dateTime".equals(mode)) {
            return dtf.format(now);
        } else if ("time".equals(mode)) {
            return dtf.format(now).substring(11);
        } else if ("date".equals(mode)) {
            return dtf.format(now).substring(0, 10);
        } else if ("yy".equals(mode)) {
            return dtf.format(now).substring(2, 4);
        } else if ("yyyy".equals(mode)) {
            return dtf.format(now).substring(0, 4);
        } else if ("mm".equals(mode)) {
            return dtf.format(now).substring(5, 7);
        } else if ("dd".equals(mode)) {
            return dtf.format(now).substring(8, 10);
        } else if ("yymm".equals(mode)) {
            return dtf.format(now).substring(2, 4) + dtf.format(now).substring(5, 7);
        } else {
            System.out.println("Invalid mode selection");
            return "Invalid mode selection";
        }
    }
}

