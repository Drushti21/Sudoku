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
    private int timeLeft = 30;
    private Timer timer;
    private JLabel timerLabel;
    private JButton startButton, resetButton;
    private int selectedNumber = 0; // Stores selected number

    private static final Color LIGHT_BLUE = new Color(173, 216, 230); // Light Blue for editable cells
    private static final Color LIGHT_RED = new Color(255, 182, 193);  // Light Red for errors
    private static final Color LIGHT_GRAY = new Color(211, 211, 211); // Light Gray for pre-filled cells

    public SudokuGameGUI() {
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
                }

                final int r = row, c = col;
                cells[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedNumber != 0 && cells[r][c].isEditable()) {
                            cells[r][c].setText(String.valueOf(selectedNumber));
                            validateCell(r, c);
                        }
                    }
                });

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
        
        JPanel numberPanel = new JPanel(new GridLayout(1, 9, 5, 5));
        for (int i = 1; i <= 9; i++) {
            JButton numButton = new JButton(String.valueOf(i));
            numButton.setFont(new Font("Arial", Font.BOLD, 20));
            numButton.addActionListener(e -> selectedNumber = Integer.parseInt(numButton.getText()));
            numberPanel.add(numButton);
        }
        
        frame.add(numberPanel, BorderLayout.NORTH);

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
        selectedNumber = 0;
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

    private void validateCell(int row, int col) {
        String text = cells[row][col].getText();
        if (!text.isEmpty()) {
            int num = Integer.parseInt(text);
            if (!isValidMove(row, col, num)) {
                cells[row][col].setBackground(LIGHT_RED);
            } else {
                cells[row][col].setBackground(LIGHT_BLUE);
            }
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
        new SudokuGameGUI();
    }
}
