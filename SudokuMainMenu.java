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

        backgroundImage = new ImageIcon("bg2.png").getImage();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("SUDOKU", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif Bold Italic", Font.BOLD, 70));
        titleLabel.setForeground(Color.BLACK);

        JLabel subTitleLabel = new JLabel("Select Your Level", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Agbalumo", Font.PLAIN, 30));
        subTitleLabel.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 20, 20));
        buttonPanel.setOpaque(false);

        JButton easyButton = createStyledButton("Easy Level");
        JButton mediumButton = createStyledButton("Medium Level");
        JButton hardButton = createStyledButton("Hard Level");
        JButton scoreButton = createStyledButton("Scores");

        easyButton.addActionListener(e -> {
            new SudokuGameGUI();
            dispose();
        });

        mediumButton.addActionListener(e -> {
            new SudokuGameGUI3();
            dispose();
        });

        hardButton.addActionListener(e -> {
            new SudokuGameGUI2();
            dispose();
        });

        scoreButton.addActionListener(e -> new ScorePage());

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(scoreButton);

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
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 30, 30);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(200, 60));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(4, 160, 222));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(7, 59, 76));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 71, 111), 3, true),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(255, 209, 102), 3, true),
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(new Color(6, 214, 160), 3, true),
                                        BorderFactory.createLineBorder(new Color(17, 138, 178), 3, true))))) ;
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(4, 160, 222));
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(new Color(4, 160, 222), 3, true));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new SudokuMainMenu();
    }
}
