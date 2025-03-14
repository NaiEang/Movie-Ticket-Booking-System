import java.io.*;
import java.util.Scanner;

public class UserGPT {
    private String name, password, email, phone;

    public UserGPT(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    // Save user details to a text file
    public void saveToFile() {
        try (FileWriter fw = new FileWriter("users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(name + "," + password + "," + email + "," + phone);
            System.out.println("User data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    // Load user from file based on email and password
    public static UserGPT loadFromFile(Scanner sc) {
        System.out.print("Enter your username to login: ");
        String inputname = sc.nextLine();
        System.out.print("Enter your password: ");
        String inputPassword = sc.nextLine();

        File file = new File("users.txt");
        if (!file.exists()) {
            System.out.println("No user data found. Please register first.");
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    String name = userData[0];
                    String password = userData[1];
                    String email = userData[2];
                    String phone = userData[3];

                    if (name.equals(inputname) && password.equals(inputPassword)) {
                        System.out.println("Login Successful!");
                        return new UserGPT(name, password, email, phone);
                    }
                }
            }
            System.out.println("Invalid username or password!");
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
        return null;
    }

    // Register a new user and save to file
    public static UserGPT register(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Confirm your password: ");
        String confirmPassword = sc.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return null;
        }

        UserGPT user = new UserGPT(name, password, email, phone);
        user.saveToFile();
        System.out.println("User registered successfully!");
        return user;
    }

    // Display user profile information
    public void displayProfile() {
        System.out.println("\nUser Profile:");
        System.out.println("Username: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}
