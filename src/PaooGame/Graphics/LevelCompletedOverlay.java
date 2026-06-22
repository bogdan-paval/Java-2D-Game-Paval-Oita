package PaooGame.Graphics;

import java.awt.*;

public class LevelCompletedOverlay {

    public LevelCompletedOverlay() {
    }

    public void draw(Graphics g, int screenWidth, int screenHeight, int oiteSalvate) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.setColor(Color.YELLOW);
        g.drawString("NIVEL COMPLETAT!", screenWidth / 2 - 300, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Oițe salvate: " + oiteSalvate, screenWidth / 2 - 150, 450);
        g.drawString("Scor Nivel: " + (oiteSalvate * 100) + " puncte", screenWidth / 2 - 170, 520);
        g.setColor(Color.ORANGE);
        g.drawString("Apasă W, A, S sau D pentru a continua", screenWidth / 2 - 300, 650);
    }
}