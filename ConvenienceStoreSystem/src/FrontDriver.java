
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
                            TransactionDriver.main(args);
                        }
                        case 2 -> {
                            Inventory.main(args);
                        }
                        case 3 -> {
                            ProductDriver.main(args, staffLogin);
                        }
                        case 4 -> {
                            MemberDriver.main(args);
                        }
                        case 5 -> {
                            SupplierDriver.main(args, staffLogin);
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
