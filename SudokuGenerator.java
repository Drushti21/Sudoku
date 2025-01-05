import java.util.Random;

public class SudokuGenerator {
    private int[][] grid = new int[9][9];
    private Random random = new Random();

    public int[][] generateSolution() {
        fillGrid(0, 0);
        return grid;
    }

    public int[][] generatePuzzle(int difficulty) {
        generateSolution();
        int cellsToRemove = difficulty == 1 ? 20 : (difficulty == 2 ? 40 : 60);
        for (int i = 0; i < cellsToRemove; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            grid[row][col] = 0;
        }
        return grid;
    }

    private boolean fillGrid(int row, int col) {
        if (row == 9) return true; // Puzzle complete
        if (col == 9) return fillGrid(row + 1, 0);

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                if (fillGrid(row, col + 1)) return true;
                grid[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
        }

        int subGridRow = (row / 3) * 3, subGridCol = (col / 3) * 3;
        for (int i = subGridRow; i < subGridRow + 3; i++) {
            for (int j = subGridCol; j < subGridCol + 3; j++) {
                if (grid[i][j] == num) return false;
            }
        }
        return true;
    }
}
