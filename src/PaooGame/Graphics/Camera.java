package PaooGame.Graphics;

import PaooGame.entity.Entity;

public class Camera {
    private int x;
    private int y;
    private final int offsetCentrare = 72;

    public Camera(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update(Entity target, int viewportWidth, int viewportHeight, int mapWidth, int mapHeight) {
        x = target.getWorldX() - (viewportWidth / 2) + offsetCentrare;
        y = target.getWorldY() - (viewportHeight / 2) + offsetCentrare;

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x > mapWidth - viewportWidth) {
            x = mapWidth - viewportWidth;
        }
        if (y > mapHeight - viewportHeight) {
            y = mapHeight - viewportHeight;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}