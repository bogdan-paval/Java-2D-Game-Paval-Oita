package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    public HUD() {
    }

    public void draw(Graphics2D g, int hpCurent, int hpMaxim, int oiteSalvate, int totalOite, int lupiUcisi, int totalLupi) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRoundRect(20, 20, 380, 210, 15, 15);
        g.setColor(Color.WHITE);
        g.drawRoundRect(20, 20, 380, 210, 15, 15);

        g.setFont(new Font("Arial", Font.BOLD, 18));

        if (Assets.bogo_viata_stari != null && hpCurent > 0) {
            int indexImagine = Math.min(hpCurent - 1, 5);
            BufferedImage imagineCurenta = Assets.bogo_viata_stari[indexImagine];

            int latimeHUD = 200;
            int inaltimeHUD = (int) (((double)imagineCurenta.getHeight() / imagineCurenta.getWidth()) * latimeHUD);

            g.drawImage(imagineCurenta, 20, 10, latimeHUD, inaltimeHUD, null);
        }

        g.setColor(Color.WHITE);
        g.drawString(Math.max(0, hpCurent) + " / " + hpMaxim, 230, 75);

        if (Assets.oaie_cap != null) {
            g.drawImage(Assets.oaie_cap, 40, 115, 40, 40, null);
        }
        g.drawString("Oițe: " + oiteSalvate + " / " + totalOite, 95, 140);

        if (Assets.lup_cap != null) {
            g.drawImage(Assets.lup_cap, 40, 170, 40, 40, null);
        }
        g.drawString("Lupi: " + lupiUcisi + " / " + totalLupi, 95, 195);
    }
}