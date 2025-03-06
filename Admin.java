//Add movie, Update movie, delete movie
import  java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;

class Admin{
    private ArrayList<Movie> movies = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int movieID = 1;
    private static final String FILE_NAME = "Movies.csv";

    public Admin(){
        loadMovies();
    }
    public void addMovie(){
        System.out.println("Fill in movie information");
        System.out.print("Movie Title: ");
        String movietitle  = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Rating: ");
        String rating = scanner.nextLine();
        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();
        System.out.print("Show Times: ");
        String showtimes = scanner.nextLine();
        movies.add(new Movie(movieID++, movietitle,genre,rating, synopsis, showtimes));
        saveMovies();
        System.out.println("Movie added successfully!");
    }

    void updateMovie(){
        System.out.println("Enter Movie ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Movie movie: movies){
            if (movie.getmovieID() == id){
                System.out.println("Press Enter to keep the info the same.");
                System.out.print("Enter new title: ");
                movie.setMovieTitle(scanner.nextLine());
                String newTitle= scanner.nextLine();
                if (!newTitle.trim().isEmpty()){
                    movie.setMovieTitle(MovieTitle);
                }
                System.out.print("Enter new genre: ");
                movie.setGenre(scanner.nextLine());
                System.out.print("Enter new rating: ");
                movie.setRating(scanner.nextLine());
                System.out.print("Enter new synopsis: ");
                movie.setSynopsis(scanner.nextLine());
                System.out.print("Enter new show times: ");
                movie.setshowTimes(scanner.nextLine());
                saveMovies();
                System.out.println("Movie updated successfully!");
                return;
            }
        }
        System.out.println("Movie not found");
    }

    void deleteMovie(){
        System.out.print("Enter movie ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Movie movie: movies){
            if(movie.getmovieID() == id){
                movies.remove(movie);
                saveMovies();
                System.out.println("Movie deleted.");
                return;
            }
        }
        System.out.println("Movie not found");
        return;
    }

    void displayMovie(){
        loadMovies();
        if (movies.isEmpty()){
            System.out.println("No movies available.");
        }else{
            for(Movie movie: movies){
                System.out.println(movie);
            }
        }
    }

    public void saveMovies(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))){
            for(Movie movie: movies){
                writer.println(movie.getmovieID()+ ","+movie.getMovieTitle()+","+movie.getGenre()+","+movie.getRating()+","+movie.getSynopsis()+","+movie.getshowTimes());
            }
        }catch (IOException e){
            System.out.println("Error saving movies: "+ e.getMessage());
        }
    }

    private void loadMovies(){
    movies.clear();
        File file = new File(FILE_NAME);
        
        if(!file.exists()){
            System.out.println("No existing movie found.");
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 6){
                    int id = Integer.parseInt(parts[0]);
                    movies.add(new Movie(id, parts[1], parts[2], parts[3],parts[4],parts[5]));
                    movieID = Math.max(movieID, id + 1);
                }
            }
        }catch(IOException e){
            System.out.println("No exisiting movie file found, starting fresh.");
        }
    }

    public void menu(){
        while(true){
            System.out.println("\n1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movies");
            System.out.println("4. View Movies");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1: addMovie();break;
                case 2 : updateMovie();break;
                case 3: deleteMovie();break;
                case 4: displayMovie();break;
                case 5: System.out.println("Exiting...");return;
                default : System.out.println("Invalid option, try again.");
            }
        }
    }
    public static void main(String[] args){
        Admin admin = new Admin();
        admin.menu();
    }
}
class Movie{
    int movieID;
    String MovieTitle, Genre, Rating, Synopsis, showTimes, availableSeats;
    public Movie(int movieID, String MovieTitle, String Genre, String Rating, String Synopsis, String showTimes){
        this.movieID = movieID;
        this.MovieTitle = MovieTitle;
        this.Genre = Genre;
        this.Rating = Rating;
        this.Synopsis = Synopsis;
        this.showTimes = showTimes;
        this.availableSeats = availableSeats;
    }
    public int getmovieID(){
        return movieID;
    }
    public String getMovieTitle(){
        return MovieTitle;
    }
    public void setMovieTitle(String MovieTitle){
        this.MovieTitle = MovieTitle;
    }
    public String getGenre(){
        return Genre;
    }
    public void setGenre(String Genre){
        this.Genre = Genre;
    }
    public String getRating(){
        return Rating;
    }
    public void setRating(String Rating){
        this.Rating = Rating;
    }
    public String getSynopsis(){
        return Synopsis;
    }
    public void setSynopsis(String Synopsis){
        this.Synopsis = Synopsis;
    }
    public String getshowTimes(){
        return showTimes;
    }
    public void setshowTimes(String showTimes)
    {
        this.showTimes = showTimes;
    }

    @Override
    public String toString(){
        return movieID+". "+"Movie Title: " + MovieTitle +", Genre: "+Genre+", Rating: "+ Rating+", Synopsis: "+Synopsis+", Show times: "+ showTimes;
    }
}