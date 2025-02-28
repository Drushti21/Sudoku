import java.sql.*;

public class FetchScores {
    private static final String DB_URL = "jdbc:sqlite:sudoku.db";

    public static void displayScores() {
        String query = "SELECT date, time_taken FROM Score ORDER BY time_taken ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
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
