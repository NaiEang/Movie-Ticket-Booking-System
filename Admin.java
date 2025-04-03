import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

class Admin {
    private Scanner scanner = new Scanner(System.in);

    public void addMovie() {
        System.out.println("Fill in movie information");
        System.out.print("Movie Title: ");
        String movietitle = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Rating: ");
        String rating = scanner.nextLine();
        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();
        System.out.print("Show Times: ");
        String showTimes = scanner.nextLine();
        System.out.print("Hall: ");
        String hall = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Movies (MovieTitle, Genre, Rating, Synopsis, ShowTimes, Hall) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, movietitle);
            stmt.setString(2, genre);
            stmt.setString(3, rating);
            stmt.setString(4, synopsis);
            stmt.setString(5, showTimes);
            stmt.setString(6, hall);
            stmt.executeUpdate();
            System.out.println("Movie added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
    }

    public void updateMovie() {
        System.out.print("Enter Movie ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new title (Press Enter to keep the same): ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new genre: ");
        String newGenre = scanner.nextLine();
        System.out.print("Enter new rating: ");
        String newRating = scanner.nextLine();
        System.out.print("Enter new synopsis: ");
        String newSynopsis = scanner.nextLine();
        System.out.print("Enter new show times: ");
        String newShowtimes = scanner.nextLine();
        System.out.print("Enter new hall: ");
        String newHall = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Movies SET MovieTitle=?, Genre=?, Rating=?, Synopsis=?, ShowTimes=? WHERE movieID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newTitle);
            stmt.setString(2, newGenre);
            stmt.setString(3, newRating);
            stmt.setString(4, newSynopsis);
            stmt.setString(5, newShowtimes);
            stmt.setString(6, newHall);
            stmt.setInt(7, id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie updated successfully!");
            } else {
                System.out.println("Movie not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating movie: " + e.getMessage());
        }
    }

    public void deleteMovie() {
        System.out.print("Enter Movie ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Movieso WHERE movieID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Movie deleted successfully!");
            } else {
                System.out.println("Movie not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting movie: " + e.getMessage());
        }
    }

    public void displayMovies() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Movies";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("No movies available.");
                return;
            }

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("movieID") +
                    ", Title: " + rs.getString("MovieTitle") +
                    ", Genre: " + rs.getString("Genre") +
                    ", Rating: " + rs.getString("Rating") +
                    ", Synopsis: " + rs.getString("Synopsis") +
                    ", Show Times: " + rs.getString("ShowTimes")+
                    ", Hall: " + rs.getString("Hall")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching movies: " + e.getMessage());
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("\n1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movies");
            System.out.println("4. View Movies");
            System.out.println("5. Exit");
            System.out.println("=======================");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: addMovie(); break;
                case 2: updateMovie(); break;
                case 3: deleteMovie(); break;
                case 4: displayMovies(); break;
                case 5: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.menu();
    }
}
