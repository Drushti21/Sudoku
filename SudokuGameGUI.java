import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGameGUI {
    private static final int SIZE = 9;
    private int[][] board = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int timeLeft = 30; // 30 seconds countdown timer
    private Timer timer;
    private JLabel timerLabel;
    private JButton startButton;

    public SudokuGameGUI() {
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);  // Increased frame height to accommodate timer label
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));

        // Create Sudoku grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("MONSTERRAT", Font.BOLD, 20));

                // Set predefined numbers as uneditable
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                }

                // Restrict input to numbers only
                final int r = row, c = col;
                cells[row][col].addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char ch = e.getKeyChar();
                        if (!(ch >= '1' && ch <= '9')) {
                            e.consume(); // Ignore invalid input
                        }
                    }
                });

                gridPanel.add(cells[row][col]);
            }
        }

        // Timer label setup
        timerLabel = new JLabel("Time Left: 30s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        // Start Game Button setup
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // "Check Solution" button
        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());

        // Add components to frame
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(startButton, BorderLayout.WEST);
        bottomPanel.add(checkButton, BorderLayout.CENTER);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Start the game and the timer
    private void startGame() {
        startButton.setEnabled(false);  // Disable Start button once clicked

        // Start the countdown timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time Left: " + timeLeft + "s");
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when time is up
                    JOptionPane.showMessageDialog(null, "Time's up! Game Over.");
                    endGame();  // Call the end game method
                }
            }
        });
        timer.start();  // Start the timer
    }

    // End the game (reset and enable the Start button again)
    private void endGame() {
        startButton.setEnabled(true);  // Re-enable the Start button
        timeLeft = 30;  // Reset the timer
        timerLabel.setText("Time Left: 30s");
    }

    // Check if the Sudoku solution is correct
    private void checkSolution() {
        boolean isCorrect = true;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) {
                    isCorrect = false;
                } else {
                    int num = Integer.parseInt(text);
                    if (!isValidMove(row, col, num)) {
                        isCorrect = false;
                        cells[row][col].setBackground(Color.RED);
                    } else {
                        cells[row][col].setBackground(Color.WHITE);
                    }
                }
            }
        }

        // Show message
        if (isCorrect) {
            JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
        } else {
            JOptionPane.showMessageDialog(null, "Some numbers are incorrect. Try again!");
        }
    }

    // Validate move (your previous logic)
    private boolean isValidMove(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < SIZE; i++) {
            if (i != col && Integer.toString(num).equals(cells[row][i].getText())) return false;
            if (i != row && Integer.toString(num).equals(cells[i][col].getText())) return false;
        }

        // Check 3x3 grid
        int startRow = (row / 3) * 3, startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((startRow + i != row || startCol + j != col) &&
                    Integer.toString(num).equals(cells[startRow + i][startCol + j].getText())) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        new SudokuGameGUI();
    }
}

