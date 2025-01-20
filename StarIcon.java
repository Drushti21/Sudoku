import java.awt.*;
import javax.swing.*;

public class StarIcon implements Icon {
    private int width;
    private int height;

    public StarIcon() {
        this.width = 30; // Width of the icon
        this.height = 30; // Height of the icon
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        int[] xPoints = { x + 15, x + 20, x + 30, x + 23, x + 25, x + 15, x + 5, x + 7, x + 0, x + 10 };
        int[] yPoints = { y, y + 10, y + 10, y + 15, y + 25, y + 20, y + 25, y + 15, y + 10, y + 10 };
        g.fillPolygon(xPoints, yPoints, 10); // Draw a star shape
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
