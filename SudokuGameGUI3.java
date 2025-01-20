import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGameGUI3 {
    private static final int SIZE = 9;
    private int[][] board = {
            { 3, 0, 0, 0, 0, 7, 4, 0, 8 },
            { 0, 8, 0, 0, 0, 2, 0, 0, 7 },
            { 5, 0, 0, 4, 0, 8, 0, 0, 0 },
            { 0, 0, 4, 0, 0, 1, 8, 0, 0 },
            { 0, 9, 0, 0, 0, 0, 7, 3, 1 },
            { 7, 1, 0, 0, 0, 0, 2, 0, 6 },
            { 0, 0, 0, 0, 0, 0, 6, 8, 5 },
            { 9, 0, 7, 0, 8, 0, 1, 0, 4 },
            { 6, 5, 8, 0, 0, 0, 0, 0, 3 }
    };

    private int[][] solution = {
            { 3, 6, 9, 1, 5, 7, 4, 2, 8 },
            { 4, 8, 1, 9, 6, 2, 5, 3, 7 },
            { 5, 7, 2, 4, 3, 8, 9, 6, 1 },
            { 2, 3, 4, 7, 9, 1, 8, 5, 6 },
            { 8, 9, 5, 6, 2, 4, 7, 3, 1 },
            { 7, 1, 6, 8, 4, 5, 2, 9, 6 },
            { 1, 4, 3, 2, 7, 9, 6, 8, 5 },
            { 9, 2, 7, 5, 8, 6, 1, 4, 3 },
            { 6, 5, 8, 3, 1, 2, 7, 4, 9 }
    };

    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int elapsedTime = 0;
    private Timer timer;
    private JLabel timerLabel;
    private JButton startButton, resetButton, hintButton;
    private Random random = new Random();
    private boolean isTimerRunning = false;

    private static final Color LIGHT_BLUE = new Color(173, 216, 230);
    private static final Color LIGHT_RED = new Color(255, 182, 193);
    private static final Color LIGHT_GRAY = new Color(211, 211, 211);

    public SudokuGameGUI3() {
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
                    final int r = row, c = col;
                    cells[row][col].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String text = cells[r][c].getText();
                            if (text.matches("[1-9]")) {
                                int num = Integer.parseInt(text);
                                if (!isValidMove(r, c, num)) {
                                    cells[r][c].setBackground(LIGHT_RED);
                                } else {
                                    cells[r][c].setBackground(LIGHT_BLUE);
                                }
                            } else {
                                cells[r][c].setBackground(LIGHT_BLUE);
                            }

                            if (!isTimerRunning) {
                                startGame();
                            }
                        }

                        @Override
                        public void keyTyped(KeyEvent e) {
                            char input = e.getKeyChar();
                            if (!Character.isDigit(input) || input == '0') {
                                e.consume();
                            }
                        }
                    });
                }

                if ((row % 3 == 2 && col % 3 == 2) || (row % 3 == 2 && col == SIZE - 1)
                        || (col % 3 == 2 && row == SIZE - 1)) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, Color.BLACK));
                } else if (row % 3 == 2) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK));
                } else if (col % 3 == 2) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.BLACK));
                } else {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
                }

                gridPanel.add(cells[row][col]);
            }
        }

        timerLabel = new JLabel("Elapsed Time: 0s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        startButton = new JButton("Start Game");
        startButton.setEnabled(false); // Timer starts with first input
        startButton.addActionListener(e -> startGame());

        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());

        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());

        hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> giveHint());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startButton);
        bottomPanel.add(checkButton);
        bottomPanel.add(resetButton);
        bottomPanel.add(hintButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void startGame() {
        if (!isTimerRunning) {
            timer = new Timer(1000, e -> {
                elapsedTime++;
                timerLabel.setText("Elapsed Time: " + elapsedTime + "s");
            });
            timer.start();
            isTimerRunning = true;
        }
    }

    private void resetGame() {
        if (timer != null) {
            timer.stop();
        }
        elapsedTime = 0;
        timerLabel.setText("Elapsed Time: 0s");
        isTimerRunning = false;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    cells[row][col].setText("");
                    cells[row][col].setBackground(LIGHT_BLUE);
                    cells[row][col].setEditable(true);
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
                    if (num != solution[row][col]) {
                        isCorrect = false;
                        cells[row][col].setBackground(LIGHT_RED);
                    } else {
                        cells[row][col].setBackground(LIGHT_BLUE);
                    }
                }
            }
        }

        if (isCorrect) {
            // Stop the timer when the solution is correct
            if (timer != null) {
                timer.stop();
            }

            JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
            showRatingDialog();
        } else {
            JOptionPane.showMessageDialog(null, "Some numbers are incorrect. Try again!");
        }
    }

    private void giveHint() {
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (cells[row][col].getText().length() > 0 || board[row][col] != 0);

        int correctNumber = solution[row][col];
        cells[row][col].setText(String.valueOf(correctNumber));
        cells[row][col].setBackground(LIGHT_GRAY);
        cells[row][col].setEditable(false);
    }

    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col && Integer.toString(num).equals(cells[row][i].getText()))
                return false;
            if (i != row && Integer.toString(num).equals(cells[i][col].getText()))
                return false;
        }

        int startRow = (row / 3) * 3, startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (Integer.toString(num).equals(cells[i][j].getText()))
                    return false;
            }
        }
        return true;
    }

    private void showRatingDialog() {
        JDialog ratingDialog = new JDialog();
        ratingDialog.setTitle("Rate the Game");
        ratingDialog.setLayout(new BorderLayout());
        ratingDialog.setSize(400, 200);
        ratingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel promptLabel = new JLabel("Please rate our game:", JLabel.CENTER);
        promptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ratingDialog.add(promptLabel, BorderLayout.NORTH);

        JPanel starsPanel = new JPanel();
        ButtonGroup starGroup = new ButtonGroup(); // Use ButtonGroup in Swing

        // Store buttons in a list for later iteration
        JToggleButton[] starButtons = new JToggleButton[5];

        for (int i = 0; i < 5; i++) {
            starButtons[i] = new JToggleButton(new StarIcon());
            starButtons[i].setActionCommand(String.valueOf(i + 1)); // Star rating 1 to 5
            starGroup.add(starButtons[i]); // Add the button to the group
            starsPanel.add(starButtons[i]);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Check if any star is selected
            boolean selected = false;
            for (JToggleButton button : starButtons) {
                if (button.isSelected()) {
                    System.out.println("User rated: " + button.getActionCommand() + " stars");
                    selected = true;
                    break;
                }
            }
            if (selected) {
                System.out.println("Thank you for your feedback!");
                ratingDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(ratingDialog, "Please select a star rating.");
            }
        });

        ratingDialog.add(starsPanel, BorderLayout.CENTER);
        ratingDialog.add(submitButton, BorderLayout.SOUTH);
        ratingDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGameGUI::new);
    }
}