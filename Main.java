import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserAuthentication user = null;

        System.out.println("===== Welcome to Movie Booking System =====");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. UserAuthentication");
            System.out.println("3. Exit");
            System.out.print("Choose Option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    user = UserAuthentication.register(sc);
                    break;

                case 2:
                    user = UserAuthentication.loadFromDatabase(sc);
                    if (user != null) {
                        user.displayProfile();
                        HomePage afterUserAuthentication = new HomePage();
                        afterUserAuthentication.menu();
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using Movie Booking System!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }
    }
}


