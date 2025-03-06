import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class User{
    String name, password, email,phone;
    public User(String name, String password, String email, String phone){
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public boolean validate(String passwordString){
        return this.password.equals(passwordString);
    }

    public static void Register(){  
        Scanner sc = new Scanner(System.in);
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
        if(password.equals(confirmPassword)){
            User user = new User(name, password, email, phone);
            System.out.println("User created successfully!");
            user.displayProfile(user.getName(), user.getEmail(), user.getPhone());
        }else{
            System.out.println("Passwords do not match!");
        }
        sc.close();
    }
    public boolean login(String password){
        return this.password.equals(password);
    }
    void updateProfile(){
    
    }
    void viewBooking(){
        
    }
    void displayProfile(String name, String email, String phone){
        System.out.println("Username: "+name);
        System.out.println("Email: "+email);
        System.out.println("Phone: "+phone);
    }
}
