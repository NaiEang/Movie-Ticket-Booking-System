import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter {
    // Database connection details (adjust these according to your setup)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/amittdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Xe2233nN#";

    public static void main(String[] args) {
        String csvFilePath = "Movies.csv"; // Replace with your CSV file path
        
        try {
            // Read and list CSV data
            List<String[]> csvData = readCSV(csvFilePath);
            
            // Print all data
            System.out.println("CSV Contents:");
            for (String[] row : csvData) {
                for (String field : row) {
                    System.out.print(field + " | ");
                }
                System.out.println();
            }
            
            // Optional: Import to database
            importToDatabase(csvData);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to read CSV file
    private static List<String[]> readCSV(String filePath) throws Exception {
        List<String[]> data = new ArrayList<>();
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Split by comma (assumes simple CSV without commas in quoted fields)
                String[] row = line.split(",");
                data.add(row);
            }
        }
        return data;
    }

    // Method to import data to database
    private static void importToDatabase(List<String[]> csvData) {
        String sql = "INSERT INTO your_table (column1, column2, column3) VALUES (?, ?, ?)"; // Adjust columns
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (String[] row : csvData) {
                // Adjust these based on your CSV structure and table columns
                pstmt.setString(1, row[0]); // First column
                pstmt.setString(2, row[1]); // Second column
                pstmt.setString(3, row[2]); // Third column
                pstmt.executeUpdate();
            }
            System.out.println("Data imported successfully!");
            
        } catch (SQLException e) {
            System.out.println("Error importing to database: " + e.getMessage());
        }
    }
}