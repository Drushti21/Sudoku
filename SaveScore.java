import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveScore {
    public static void saveGameTime(int elapsedTime) {
        String url = "jdbc:sqlite:sudoku.db"; // Database file

        String insertSQL = "INSERT INTO scores (date, time_taken) VALUES (datetime('now'), ?)";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, elapsedTime); // Time taken in seconds
            pstmt.executeUpdate();
            System.out.println("Score saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
