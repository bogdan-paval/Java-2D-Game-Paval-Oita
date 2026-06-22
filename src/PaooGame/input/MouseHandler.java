package PaooGame.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private boolean rightClicked = false;
    private boolean leftClicked = false;
    private int mouseX = 0;
    private int mouseY = 0;

    public boolean isRightClicked() { return rightClicked; }
    public void setRightClicked(boolean rightClicked) { this.rightClicked = rightClicked; }

    public boolean isLeftClicked() { return leftClicked; }
    public void setLeftClicked(boolean leftClicked) { this.leftClicked = leftClicked; }

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    @Override
    public void mousePressed(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e)) rightClicked = true;
        if (javax.swing.SwingUtilities.isLeftMouseButton(e)) leftClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e)) rightClicked = false;
        if (javax.swing.SwingUtilities.isLeftMouseButton(e)) leftClicked = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}