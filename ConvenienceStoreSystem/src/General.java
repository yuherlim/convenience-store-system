
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
        }while(loop == 1);
    }
    
    public boolean dateChecking(String dateStr) {
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
    
    


