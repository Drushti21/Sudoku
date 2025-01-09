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
        frame.setSize(500, 600);  // Increased frame height to accommodate timer label
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));

        // Initialize the board and Sudoku grid
        generateNewBoard();
        initializeGrid(gridPanel);

        // Timer label setup
        timerLabel = new JLabel("Time Left: 30s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        // Start Game Button setup
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();  // Start the new game when clicked
            }
        });

        // Reset Timer Button setup
        resetButton = new JButton("Reset Timer");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();  // Reset the timer when clicked
            }
        });

        // Pause Timer Button setup
        pauseButton = new JButton("Pause Timer");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();  // Pause or resume the timer
            }
        });

        // "Check Solution" button
        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());

        // Add components to frame
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(pauseButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(checkButton, BorderLayout.SOUTH);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Generate a new Sudoku board (you can implement any board generation logic)
    private void generateNewBoard() {
        Random rand = new Random();
        // For simplicity, just generating a random board for demonstration.
        // You can replace this with a more sophisticated Sudoku generator.
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = rand.nextInt(10);  // Random numbers 0-9 for simplicity
            }
        }

        // Optionally, you can pre-fill some numbers as fixed
        board[0][0] = 5; // Example fixed number
        board[1][1] = 3; // Example fixed number
    }

    // Initialize the grid with the current board
    private void initializeGrid(JPanel gridPanel) {
        gridPanel.removeAll();  // Clear previous cells

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

        gridPanel.revalidate();  // Revalidate layout after clearing and adding components
        gridPanel.repaint();
    }

    // Start the game and the timer
    private void startGame() {
        startButton.setEnabled(false);  // Disable Start button once clicked

        // Reset the game state and generate a new board
        generateNewBoard();
        initializeGrid((JPanel) ((JPanel) startButton.getParent()).getComponent(0));

        // Start the countdown timer
        timeLeft = 30;  // Reset to 30 seconds
        timerLabel.setText("Time Left: " + timeLeft + "s");

        // Start the countdown timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0 && !isPaused) {
                    timeLeft--;
                    timerLabel.setText("Time Left: " + timeLeft + "s");
                } else if (timeLeft == 0) {
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

    // Reset the timer to 30 seconds
    private void resetTimer() {
        timeLeft = 30;  // Reset to 30 seconds
        timerLabel.setText("Time Left: " + timeLeft + "s");
        if (timer != null) {
            timer.stop();  // Stop the current timer
        }
    }

    // Pause or Resume the timer
    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            pauseButton.setText("Resume Timer");  // Change button text to "Resume Timer"
        } else {
            pauseButton.setText("Pause Timer");  // Change button text back to "Pause Timer"
        }
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
