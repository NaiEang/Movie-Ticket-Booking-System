import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.Console;

public class UserAuthentication {
    private String name, password, email, phone;

    public UserAuthentication(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    // Save user details to database
    public void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/moviedb";	
        String user = "root";
        String pass = "Xe2233nN#";

        String query = "INSERT INTO Users (Name, Password, Email, Phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }
    //login method
    // Load user from the database based on email and password
    public static UserAuthentication loadFromDatabase(Scanner sc) {
        System.out.print("Enter your username: ");
        String inputname= sc.nextLine();

        String inputPassword;
        Console console = System.console();
        if (console == null) {
            //this will hide pw
            char[] passwordArray = console.readPassword("Enter your password: ");
            inputPassword = new String(passwordArray);
            //clear password array
            java.util.Arrays.fill(passwordArray, ' ');
        } else {
            //if console is null, use Scanner to read password
            System.out.print("Enter your password: ");
            inputPassword = sc.nextLine();
        }

        String url = "jdbc:mysql://localhost:3306/moviedb";
        String user = "root";
        String password = "Xe2233nN#";

        String query = "SELECT * FROM Users WHERE name = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, inputname);
            stmt.setString(2, inputPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                System.out.println("Login Successful!");
                return new UserAuthentication(name, inputPassword, email, phone);
            } else {
                System.out.println("Invalid username or password!");
            }
        } catch (SQLException e) {
            System.out.println("Error loading user from database: " + e.getMessage());
        }
        return null;
    }

    // Register a new user and save to the database
    public static UserAuthentication register(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your phone: ");
        String phone = sc.nextLine();

        String password, confirmpw;
        Console console = System.console();
        if(console == null) {
            //this will hide pw
            char[] passwordArray = console.readPassword("Enter your password: ");
            password = new String(passwordArray);
            //clear password array
            java.util.Arrays.fill(passwordArray, ' ');

            char[] confirmPasswordArray = console.readPassword("Confirm your password: ");
            confirmpw = new String(confirmPasswordArray);
            //clear password array
            java.util.Arrays.fill(confirmPasswordArray, ' ');
        } else {
            //if console is null, use Scanner to read password
            System.out.print("Enter your password: ");
            password = sc.nextLine();
            System.out.print("Confirm your password: ");
            confirmpw = sc.nextLine();

        }

        if (!password.equals(confirmpw)) {
            System.out.println("Passwords do not match!");
            return null;
        }
        System.out.println("Registration Successful!");

        UserAuthentication user = new UserAuthentication(name, password, email, phone);
        user.saveToDatabase();
        return user;
    }

    // Display user profile information
    public void displayProfile() {
        System.out.println("\nUser Profile:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}