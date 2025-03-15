import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AfterLogin {
    private Scanner scanner = new Scanner(System.in);
    private List<Movie> movies;
    private String recentBooking = null;

    public AfterLogin() {
        this.movies = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        try (Connection conn = db.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Movies");
            while (rs.next()) {
                int id = rs.getInt("movieID");
                String MovieTitle = rs.getString("MovieTitle");
                String Genre = rs.getString("Genre");
                String Rating = rs.getString("Rating");
                String Synopsis = rs.getString("Synopsis");
                String showTimes = rs.getString("ShowTimes");
                movies.add(new Movie(id, MovieTitle, Genre, Rating, Synopsis, showTimes));
            }
        } catch (Exception e) {
            System.out.println("Error loading movies from database: " + e.getMessage());
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n1. Movies");
            System.out.println("2. Buy Ticket");
            System.out.println("3. View Booking");
            System.out.println("4. Update Profile");
            System.out.println("5. Notification");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showMovies();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    viewBooking();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMovies() {
        movies.clear(); // Clear the current list to avoid duplicates
        String url = "jdbc:mysql://localhost:3306/moviedb";
        String user = "root";
        String password = "Xe2233nN#";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {

            while (rs.next()) {
                int moveID = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String rating = rs.getString("rating");
                String synopsis = rs.getString("synopsis");
                String showTimes = rs.getString("show_times");

                movies.add(new Movie(moveID, title, genre, rating, synopsis, showTimes));
            }
        } catch (Exception e) {
            System.out.println("Error loading movies from database: " + e.getMessage());
        }
    }

    private void buyTicket() {
        showMovies(); // Show the list of movies first
        System.out.print("What movie do you want to watch? (select number): ");
        int movieID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Movie selectedMovie = null;
        for (Movie movie : movies) {
            if (movie.getmovieID() == movieID) {
                selectedMovie = movie;
                break;
            }
        }

        if (selectedMovie != null) {
            System.out.println("You have selected: " + selectedMovie.getMovieTitle());
            showSeats(selectedMovie);
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    private void showSeats(Movie selectedMovie) {
        System.out.println("Select a seat:");
        String[] rows = {"A", "B"};
        int seatsPerRow = 5;
        for (String row : rows) {
            System.out.print("Row" + row + ": ");
            for (int i = 1; i <= seatsPerRow; i++) {
                System.out.print("(" + i + row + ") ");
            }
            System.out.println();
        }
        System.out.print("Choose a seat (e.g., 1A): ");
        String seatSelection = scanner.nextLine();

        if (isValidSeatSelection(seatSelection, rows, seatsPerRow)) {
            System.out.println("You have selected seat: " + seatSelection);
            System.out.println("So you have chosen: " + selectedMovie.getMovieTitle() + ", " + selectedMovie.getshowTimes() + ", seat: " + seatSelection);
            System.out.println("Total: $7");
            System.out.print("Pay now? (yes/no): ");
            String payNow = scanner.nextLine();

            if (payNow.equalsIgnoreCase("yes")) {
                System.out.println("Payment successful. Enjoy your movie!");
                recentBooking = "Movie: " + selectedMovie.getMovieTitle() + ", Time: " + selectedMovie.getshowTimes() + ", Seat: " + seatSelection;
            } else {
                System.out.println("Payment cancelled.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }

    private boolean isValidSeatSelection(String seatSelection, String[] rows, int seatsPerRow) {
        if (seatSelection.length() < 2) {
            return false;
        }
        String row = seatSelection.substring(seatSelection.length() - 1);
        String seatNumberStr = seatSelection.substring(0, seatSelection.length() - 1);
        try {
            int seatNumber = Integer.parseInt(seatNumberStr);
            for (String r : rows) {
                if (r.equals(row) && seatNumber >= 1 && seatNumber <= seatsPerRow) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private void viewBooking() {
        if (recentBooking != null) {
            System.out.println("Recent Booking: " + recentBooking);
        } else {
            System.out.println("No recent bookings.");
        }
    }

    private void loadMoviesFromCSV(String filePath) {
        movies.clear(); // Clear the current list to avoid duplicates
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 6) {
                    int id = Integer.parseInt(values[0]);
                    movies.add(new Movie(id, values[1], values[2], values[3], values[4], values[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading movies from CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AfterLogin afterLogin = new AfterLogin();
        afterLogin.menu();
    }
}

// Reuse the Movie class from Admin.java
class Movie {
    int movieID;
    String MovieTitle, Genre, Rating, Synopsis, showTimes, availableSeats;

    public Movie(int movieID, String MovieTitle, String Genre, String Rating, String Synopsis, String showTimes) {
        this.movieID = movieID;
        this.MovieTitle = MovieTitle;
        this.Genre = Genre;
        this.Rating = Rating;
        this.Synopsis = Synopsis;
        this.showTimes = showTimes;
        this.availableSeats = availableSeats;
    }

    public int getmovieID() {
        return movieID;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String MovieTitle) {
        this.MovieTitle = MovieTitle;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String Synopsis) {
        this.Synopsis = Synopsis;
    }

    public String getshowTimes() {
        return showTimes;
    }

    public void setshowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    @Override
    public String toString() {
        return movieID + ". " + "Movie Title: " + MovieTitle + ", Genre: " + Genre + ", Rating: " + Rating + ", Synopsis: " + Synopsis + ", Show times: " + showTimes;
    }
}