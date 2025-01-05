import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.google.gson.Gson;

public class SudokuServlet extends HttpServlet {
    private SudokuGenerator sudokuGenerator = new SudokuGenerator();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Gson gson = new Gson();
        response.setContentType("application/json");

        if ("newGame".equals(action)) {
            int difficulty = Integer.parseInt(request.getParameter("difficulty"));
            int[][] puzzle = sudokuGenerator.generatePuzzle(difficulty);
            response.getWriter().write(gson.toJson(puzzle));
        }
    }
}
