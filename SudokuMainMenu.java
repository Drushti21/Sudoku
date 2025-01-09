import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokuMainMenu extends JFrame {
    private Image backgroundImage;

    public SudokuMainMenu() {
        setTitle("Sudoku Game");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load the background image
        backgroundImage = new ImageIcon("bg.png").getImage(); // Ensure "bg.png" is in your project directory

        // Create a panel with custom painting for the background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("SUDOKU", JLabel.CENTER);
        titleLabel.setFont(new Font("Agbalumo", Font.BOLD, 70));
        titleLabel.setForeground(Color.BLACK);

        JLabel subTitleLabel = new JLabel("Select Your Level", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Agbalumo", Font.PLAIN, 30));
        subTitleLabel.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);

        JButton easyButton = createStyledButton("Easy Level");
        JButton mediumButton = createStyledButton("Medium Level");
        JButton hardButton = createStyledButton("Hard Level");

        easyButton.addActionListener(e -> {
            new SudokuGameGUI(); // Launch Easy Level
            dispose();
        });

        mediumButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Medium Level not yet implemented!");
        });

        hardButton.addActionListener(e -> {
            new SudokuGameGUI2(); // Launch Hard Level
            dispose();
        });

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        mainPanel.add(subTitleLabel, gbc);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 60));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(4, 160, 222)); // Default Blue Background (#04a0de)
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder());
        
        // Stadium shape (Rounded corners)
        button.setBorder(BorderFactory.createLineBorder(new Color(4, 160, 222), 4, true));
        button.setOpaque(true);

        // Multi-color hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 71, 111), 4, true),   // Red Border
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(255, 209, 102), 4, true),  // Yellow Border
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(new Color(6, 214, 160), 4, true),  // Green Border
                                        BorderFactory.createLineBorder(new Color(17, 138, 178), 4, true)  // Blue Border
                                )
                        )
                ));
                button.setBackground(new Color(7, 59, 76)); // Darker Background (#073b4c)
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(4, 160, 222)); // Reset to Original Blue
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(new Color(4, 160, 222), 4, true));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new SudokuMainMenu();
    }
}
