import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AfterLogin {
    private Scanner scanner = new Scanner(System.in);
    private List<Movie> movies;

    public AfterLogin() {
        this.movies = new ArrayList<>();
        loadMoviesFromCSV("Movies.csv");
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
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMovies() {
        loadMoviesFromCSV("Movies.csv"); // Reload movies to get the latest updates
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            System.out.println("Movies:");
            for (Movie movie : movies) {
                System.out.println(movie);
            }
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
            // Add further ticket buying logic here
        } else {
            System.out.println("Invalid movie selection.");
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