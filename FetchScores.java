import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FetchScores {
    public static void displayScores() {
        String url = "jdbc:sqlite:sudoku.db"; // Database file

        String query = "SELECT date, time_taken FROM scores ORDER BY time_taken ASC";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Date & Time        | Time Taken");
            System.out.println("--------------------------------");

            while (rs.next()) {
                String date = rs.getString("date");
                int time = rs.getInt("time_taken");
                System.out.printf("%s  | %02d:%02d%n", date, time / 60, time % 60);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        displayScores();
    }
}
