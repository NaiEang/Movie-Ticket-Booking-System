import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserAuthentication user = null;

        System.out.println("");
        System.out.print("===== Welcome to Movie Booking System =====");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("============================================");
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
                        //Check for admin credential
                        if(user.getName().equals("admin") && user.getPassword().equals("##2233##")){
                            Admin admin = new Admin();
                            admin.menu();
                        }else{
                        user.displayProfile();
                        HomePage afterUserAuthentication = new HomePage();
                        afterUserAuthentication.menu();
                    }
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


