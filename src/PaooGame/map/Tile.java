package PaooGame.map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static Tile[] tiles = new Tile[5000];

    protected BufferedImage img;
    protected final int id;
    protected boolean solid = false;

    public static final int TILE_WIDTH  = 64;
    public static final int TILE_HEIGHT = 64;

    public Tile(BufferedImage texture, int id) {
        this.img = texture;
        this.id = id;
        tiles[id] = this;
    }

    public void Draw(Graphics g, int x, int y) {

        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean IsSolid() {
        return solid;
    }
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}