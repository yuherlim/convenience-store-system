
/**
 *
 * @author JiaQing
 */
public class FrontDriver {

    public static void main(String[] args) {
        int selectMain, selectHome;
        
        do {
            selectHome = Front.homePage();

            if (selectHome == 1) {
                Staff staffLogin = Front.loginPage();
                selectMain = Front.mainMenu(staffLogin);
                switch (selectMain) {
                    case 1 -> {
                        //Sales
                    }
                    case 2 -> {
                        //Inventory Management
                    }
                    case 3 -> {
                        //Product
                    }
                    case 4 -> {
                        //Membership
                    }
                    case 5 -> {
                        //Supplier
                    }
                    case 6 -> {
                        StaffDriver.main(args, staffLogin);
                    }
                    case 0 -> {
                        System.out.println("  *** Log Out. Back to Home Page. ***");
                        General.systemPause();
                        General.clearScreen();
                    }
                }
            } else {
                General.clearScreen();
                System.out.println("Thanks for using Convenience Store POS System...");
                General.systemPause();
            }
        } while (selectHome == 1);
    }
}
