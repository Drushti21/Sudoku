import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ScorePage extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:sudoku.db";

    public ScorePage() {
        setTitle("Sudoku Scores");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Date & Time");
        model.addColumn("Time Taken (mm:ss)");

        // Fetch scores from the database
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT date, time_taken FROM Score ORDER BY time_taken ASC")) {

            while (rs.next()) {
                String date = rs.getString("date");
                int time = rs.getInt("time_taken");
                model.addRow(new Object[] { date, String.format("%02d:%02d", time / 60, time % 60) });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading scores: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table));

        setVisible(true);
    }

    public static void main(String[] args) {
        new ScorePage();
    }
}
