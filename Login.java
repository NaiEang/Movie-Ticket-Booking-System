import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Login {
    private String name, password, email, phone;

    public Login(String name, String password, String email, String phone) {
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
    // Load user from the database based on email and password
    public static Login loadFromDatabase(Scanner sc) {
        System.out.print("Enter your username: ");
        String inputname= sc.nextLine();
        System.out.print("Enter your password: ");
        String inputPassword = sc.nextLine();

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
                return new Login(name, inputPassword, email, phone);
            } else {
                System.out.println("Invalid email or password!");
            }
        } catch (SQLException e) {
            System.out.println("Error loading user from database: " + e.getMessage());
        }
        return null;
    }

    // Register a new user and save to the database
    public static Login register(Scanner sc) {
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
        System.out.println("Registration Successful!");

        Login user = new Login(name, password, email, phone);
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