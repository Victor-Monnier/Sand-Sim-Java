package main;

import java.awt.event.*;

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseWheelListener, MouseMotionListener {
    public boolean mouseLeftPressed, mouseLeftClicked, mouseRightPressed, mouseRightClicked, mouseScrolled;
    public int mouseX, mouseY, mouseScrollAmount;
    GamePanel gp;
    double GS;

    public MouseHandler(GamePanel gp, double GS) {
        this.gp = gp;
        this.GS = GS;
    }
    
    @Override 
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseLeftPressed = true;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            mouseRightPressed = true;
        }
    }

    @Override 
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseLeftPressed = false;
            mouseLeftClicked = true;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            mouseRightPressed = false;
            mouseRightClicked = true;
        }
    }

    @Override 
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX()/GS);
        mouseY = (int) (e.getY()/GS);
        //THIS ONLY WORKS IF 1600x900 RES
        //System.out.println("X: "+(gp.player.x-gp.player.onScreenX+mouseX)+"   Y: "+(gp.player.y-gp.player.onScreenY+mouseY));
    }

    @Override 
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX()/GS);
        mouseY = (int) (e.getY()/GS);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseScrolled = true;
        mouseScrollAmount = e.getWheelRotation();
        System.out.println(mouseScrollAmount);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}
}
