import java.sql.*;

public class ScoreManager {
    private static final String DB_URL = "jdbc:sqlite:sudoku.db";

    public static void saveScore(int timeTaken) {
        String query = "INSERT INTO Score (date, time_taken) VALUES (datetime('now'), ?)";

        try {
            // ✅ Load SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(DB_URL);
                    PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, timeTaken);
                pstmt.executeUpdate();
                System.out.println("Score saved successfully!");

            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        saveScore(120); // Example: saving a score of 120 seconds (2 minutes)
    }
}
