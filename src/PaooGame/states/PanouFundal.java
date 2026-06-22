package PaooGame.states;

import PaooGame.Graphics.ImageLoader;
import PaooGame.exceptions.ResourceLoadException;

import javax.swing.*;
import java.awt.*;

class PanouFundal extends JPanel {
    private Image imagineFundal;

    public PanouFundal(String caleaCatreImagine) {
        try {
            imagineFundal = ImageLoader.LoadImage(caleaCatreImagine);
        } catch (ResourceLoadException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagineFundal != null) {
            g.drawImage(imagineFundal, 0, 0, getWidth(), getHeight(), this);
        }
    }
}