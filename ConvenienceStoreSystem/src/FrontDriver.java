
/**
 *
 * @author JiaQing
 */
public class FrontDriver {

    public static void main(String[] args) {
        int selectMain, selectHome;

        do {
            General.clearScreen();
            selectHome = Front.homePage();
            General.clearScreen();

            if (selectHome == 1) {
                Staff staffLogin = Front.loginPage();
                do {
                    General.clearScreen();
                    selectMain = Front.mainMenu(staffLogin);
                    if (selectMain != 0) {
                        General.clearScreen();
                    }
                    switch (selectMain) {
                        case 1 -> {
                            //Sales
                        }
                        case 2 -> {
                            //Inventory Management
                        }
                        case 3 -> {
                            ProductDriver.main(args);
                        }
                        case 4 -> {
                            //Membership
                        }
                        case 5 -> {
                            SupplierDriver.main(args);
                        }
                        case 6 -> {
                            StaffDriver.main(args, staffLogin);
                        }
                        case 0 -> {
                            System.out.println("  *** Log Out. Back to Home Page. ***");
                            General.systemPause();
                        }
                    }
                } while (selectMain != 0);
            } else {
                System.out.println("Thanks for using Convenience Store POS System...");
                General.systemPause();
            }
        } while (selectHome == 1);
    }
}
