import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveScore {
    private static final String DB_URL = "jdbc:sqlite:sudoku.db"; // Ensure correct path

    public static void saveScore(int timeTaken) {
        String insertQuery = "INSERT INTO Score (date, time_taken) VALUES (datetime('now'), ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, timeTaken);
            pstmt.executeUpdate();
            System.out.println("Score saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        saveScore(120); // Example: Save 120 seconds as score
    }
}
