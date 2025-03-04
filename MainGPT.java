package movie2;

import java.util.Scanner;

import movie.User;

public class MainGPT {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserGPT user = null;

        System.out.println("===== Welcome to Movie Booking System =====");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose Option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    user = UserGPT.register(sc);
                    break;

                case 2:
                    user = UserGPT.loadFromFile(sc);
                    if (user != null) {
                        user.displayProfile();
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

