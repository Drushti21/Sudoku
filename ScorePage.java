import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class ScorePage extends JFrame {

    public ScorePage() {
        setTitle("Sudoku Scores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = { "Date", "Time Taken" };
        Object[][] data = fetchScores();

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    private Object[][] fetchScores() {
        String url = "jdbc:sqlite:sudoku.db";
        String query = "SELECT date, time_taken FROM scores ORDER BY time_taken ASC";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] data = new Object[rowCount][2];
            int i = 0;

            while (rs.next()) {
                data[i][0] = rs.getString("date");
                data[i][1] = formatTime(rs.getInt("time_taken"));
                i++;
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row == 0) {
                c.setBackground(Color.YELLOW); // Highlight the best score
            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScorePage::new);
    }
}
