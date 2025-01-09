import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGameGUI {
    private static final int SIZE = 9;
    private int[][] board = new int[SIZE][SIZE];
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int timeLeft = 30; // 30 seconds countdown timer
    private Timer timer;
    private JLabel timerLabel;
    private JButton startButton, resetButton, pauseButton;
    private boolean isPaused = false;

    public SudokuGameGUI() {
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600); // Increased frame height for timer label
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));

        generateNewBoard();
        initializeGrid(gridPanel);

        // Timer label setup
        timerLabel = new JLabel("Time Left: 30s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        // Start Game Button setup
        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());

        // Reset Timer Button setup
        resetButton = new JButton("Reset Timer");
        resetButton.addActionListener(e -> resetTimer());

        // Pause Timer Button setup
        pauseButton = new JButton("Pause Timer");
        pauseButton.addActionListener(e -> togglePause());

        // "Check Solution" button
        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());

        // Add components to frame
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(pauseButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(checkButton, BorderLayout.SOUTH);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void generateNewBoard() {
        Random rand = new Random();
        // This should be replaced with a valid Sudoku generator
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = rand.nextInt(10);
            }
        }
        board[0][0] = 5; // Example fixed number
        board[1][1] = 3; // Example fixed number
    }

    private void initializeGrid(JPanel gridPanel) {
        gridPanel.removeAll();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("MONSTERRAT", Font.BOLD, 20));

                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                }

                final int r = row, c = col;
                cells[row][col].addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (!(Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '0')) {
                            e.consume();
                        }
                    }
                });

                gridPanel.add(cells[row][col]);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void startGame() {
        startButton.setEnabled(false);
        generateNewBoard();
        initializeGrid((JPanel) ((JPanel) startButton.getParent()).getComponent(0));

        timeLeft = 30;
        timerLabel.setText("Time Left: " + timeLeft + "s");

        timer = new Timer(1000, e -> {
            if (timeLeft > 0 && !isPaused) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft + "s");
            } else if (timeLeft == 0) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "Time's up! Game Over.");
                endGame();
            }
        });
        timer.start();
    }

    private void endGame() {
        startButton.setEnabled(true);
        timeLeft = 30;
        timerLabel.setText("Time Left: 30s");
        if (timer != null) {
            timer.stop();
        }
    }

    private void resetTimer() {
        timeLeft = 30;
        timerLabel.setText("Time Left: " + timeLeft + "s");
        if (timer != null) {
            timer.stop();
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume Timer" : "Pause Timer");
    }

    private void checkSolution() {
        boolean isCorrect = true;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty() || !isValidMove(row, col, Integer.parseInt(text))) {
                    isCorrect = false;
                    cells[row][col].setBackground(Color.RED);
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                }
            }
        }

        JOptionPane.showMessageDialog(null, isCorrect ? "Congratulations! You solved the puzzle!" : "Some numbers are incorrect. Try again!");
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
