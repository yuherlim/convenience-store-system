
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
            if (input.length() == 0) {
                System.out.println(errorMsg);
                loop = 1;
                continue;
            }
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

    public static String stringNullCheckingInput(String promptMsg, String errorMsg) {
        String input;
        int loop;
        Scanner sc = new Scanner(System.in);
        do {
            loop = 0;
            System.out.print(promptMsg);
            input = sc.nextLine();
            if (input.length() == 0) {
                loop = 1;
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
                sc.nextLine();
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
                sc.nextLine();
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
            if (Character.isLetter(input.charAt(0)) != true && input.length() != 1) {

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
                System.out.println("  Invalid input, please enter Y or N.");
                loop = 1;
            }
               
        } while (loop == 1);
        return input;
    }
    
    public static String phoneInput(String promptMsg) {
        Scanner sc = new Scanner(System.in);
        String phoneNum;

        do {
            System.out.print(promptMsg);
            phoneNum = sc.next();

        } while (phoneNumValidation(phoneNum) == false);
        return phoneNum;
    }

    public static boolean phoneNumValidation(String phoneNum) {
        if (phoneNum.length() > 11 || phoneNum.length() < 10) {
            System.out.println("  Invalid length of phone number");
            return false;
        } else {
            for (int i = 0; i < phoneNum.length(); i++) {
                if (Character.isDigit(phoneNum.charAt(i)) == false) {
                    System.out.println("  Invalid phone number format");
                    return false;
                }
            }
        }
        return true;
    }

    public static String icInput(String promptMsg) {
        Scanner sc = new Scanner(System.in);
        String ic;
        int loop = 0;

        do {
            loop = 0;
            System.out.print(promptMsg);
            ic = sc.next();

        } while (icValidation(ic) == false || loop == 1);
        return ic;
    }

    public static boolean icValidation(String ic) {
        if (ic.length() != 12) {
            System.out.println("  Invalid length of ic");
            return false;
        } else {

            if (Integer.parseInt(ic.substring(0, 2)) <= 20) {
                if (dateChecking(ic.substring(4, 6) + "/" + ic.substring(2, 4) + "/20" + ic.substring(0, 2)) == false) {
                    System.out.println("  Invalid ic birthdate format");
                    return false;
                }
            } else {
                if (dateChecking(ic.substring(4, 6) + "/" + ic.substring(2, 4) + "/19" + ic.substring(0, 2)) == false) {
                    System.out.println("  Invalid ic birthdate format");
                    return false;
                }
            }

            for (int i = 0; i < ic.length(); i++) {
                if (Character.isDigit(ic.charAt(i)) == false) {
                    System.out.println("  Invalid ic number format");
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

    public static String birthDateInput(String promptMsg, String errorMsg, String ic) {
        Scanner sc = new Scanner(System.in);
        String input;
        int loop;
        do {
            loop = 0;

            input = dateInput(promptMsg, errorMsg);

            if (ic.charAt(0) != input.charAt(8) || ic.charAt(1) != input.charAt(9)) {
                System.out.println("  Birthdate didnt same as IC");
                loop = 1;
                continue;
            }

            if (ic.charAt(2) != input.charAt(3) || ic.charAt(3) != input.charAt(4)) {
                System.out.println("  Birthdate didnt same as IC");
                loop = 1;
                continue;
            }

            if (ic.charAt(4) != input.charAt(0) || ic.charAt(5) != input.charAt(1)) {
                System.out.println("  Birthdate didnt same as IC");
                loop = 1;
                continue;
            }

        } while (loop == 1);
        return input;
    }

    public static boolean dateChecking(String dateStr) {
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        date.setLenient(false);
        try {
            date.parse(dateStr);
        } catch (ParseException e) {
            return false;

        }
        return true;
    }

    public static String getCurrentDateTime(String mode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        switch (mode) {
            case "dateTime":
                return dtf.format(now);
            case "time":
                return dtf.format(now).substring(11);
            case "date":
                return dtf.format(now).substring(0, 10);
            case "yy":
                return dtf.format(now).substring(8, 10);
            case "yyyy":
                return dtf.format(now).substring(6, 10);
            case "mm":
                return dtf.format(now).substring(3, 5);
            case "dd":
                return dtf.format(now).substring(0, 2);
            case "yymm":
                return dtf.format(now).substring(8, 10) + dtf.format(now).substring(3, 5);
            default:
                System.out.println("  Invalid mode selection");
                
                break;
        }
                return "Invalid mode selection";
    }

    public static int ageCalc(String birthDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long difference_In_Years = 0;
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(birthDate);
            Date d2 = sdf.parse(getCurrentDateTime("date"));

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in seconds,
            // minutes, hours, years, and days
            difference_In_Years = TimeUnit.MILLISECONDS
                    .toDays(difference_In_Time)
                    / 365l;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) difference_In_Years;
    }

    public static void clearScreen() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex) {
        }
    }

    public static void systemPause() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Press <Enter> to continue...");
        sc.nextLine();

    }

}
