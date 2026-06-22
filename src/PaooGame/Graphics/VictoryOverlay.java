package PaooGame.Graphics;

import java.awt.*;

public class VictoryOverlay {

    public VictoryOverlay() {
    }

    public void draw(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 80));
        g.drawString("VICTORIE FINALĂ!", screenWidth / 2 - 350, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Apasă ESC pentru Meniu", screenWidth / 2 - 200, 600);
    }
}