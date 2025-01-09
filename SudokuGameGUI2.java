import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGameGUI2 {
    private static final int SIZE = 9;
    private int[][] board = {
        {2, 0, 0, 0, 6, 0, 0, 0, 9},
        {0, 0, 0, 8, 0, 0, 0, 1, 0},
        {0, 0, 9, 0, 0, 0, 7, 5, 0},
        {0, 7, 0, 0, 5, 3, 0, 0, 8},
        {3, 0, 0, 0, 0, 9, 0, 0, 6},
        {0, 0, 8, 7, 0, 0, 0, 4, 0},
        {0, 5, 7, 0, 0, 0, 9, 0, 0},
        {1, 9, 0, 0, 0, 4, 0, 0, 0},
        {8, 0, 0, 0, 7, 0, 0, 0, 2}
    };

    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int timeLeft = 30;
    private Timer timer;
    private JLabel timerLabel;
    private JButton startButton, resetButton;

    private static final Color LIGHT_BLUE = new Color(173, 216, 230); // Light Blue for editable cells
    private static final Color LIGHT_RED = new Color(255, 182, 193);  // Light Red for errors
    private static final Color LIGHT_GRAY = new Color(211, 211, 211); // Light Gray for pre-filled cells

    public SudokuGameGUI2() {
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("MONSTERRAT", Font.BOLD, 20));

                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(LIGHT_GRAY);
                } else {
                    cells[row][col].setBackground(LIGHT_BLUE);

                    // Add KeyListener to validate input immediately
                    final int r = row, c = col;
                    cells[row][col].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String text = cells[r][c].getText();
                            if (text.matches("[1-9]")) {
                                int num = Integer.parseInt(text);
                                if (!isValidMove(r, c, num)) {
                                    cells[r][c].setBackground(LIGHT_RED); // Highlight incorrect move
                                } else {
                                    cells[r][c].setBackground(LIGHT_BLUE); // Restore correct color
                                }
                            } else {
                                cells[r][c].setBackground(LIGHT_BLUE); // Reset if input is deleted
                            }
                        }
                    });
                }
                gridPanel.add(cells[row][col]);
            }
        }

        timerLabel = new JLabel("Time Left: 30s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());

        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());

        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startButton);
        bottomPanel.add(checkButton);
        bottomPanel.add(resetButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void startGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timeLeft = 30;
        timerLabel.setText("Time Left: 30s");
        startButton.setEnabled(false);

        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft + "s");
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Time's up! Game Over.");
                startButton.setEnabled(true);
            }
        });
        timer.start();
    }

    private void resetGame() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = 30;
        timerLabel.setText("Time Left: 30s");
        startButton.setEnabled(true);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    cells[row][col].setText("");
                    cells[row][col].setBackground(LIGHT_BLUE);
                }
            }
        }
    }

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
                        cells[row][col].setBackground(LIGHT_RED);
                    } else {
                        cells[row][col].setBackground(LIGHT_BLUE);
                    }
                }
            }
        }

        if (isCorrect) {
            JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
        } else {
            JOptionPane.showMessageDialog(null, "Some numbers are incorrect. Try again!");
        }
    }

    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col && Integer.toString(num).equals(cells[row][i].getText())) return false;
            if (i != row && Integer.toString(num).equals(cells[i][col].getText())) return false;
        }

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
        new SudokuGameGUI2();
    }
}
