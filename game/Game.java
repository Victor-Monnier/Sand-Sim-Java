package game;

import main.KeyHandler;
import main.MouseHandler;

import java.awt.Graphics2D;

public class Game {
    public Cell[][] grid;
    public int width, height;
    public long updates, time;
    double speed = 0.05, pixelsPerSlot;
    KeyHandler keyH;
    MouseHandler mouseH;

    public Game(KeyHandler keyH, MouseHandler mouseH) {
        this.keyH = keyH;
        this.mouseH = mouseH;
        width = 160;
        height = 90;
        
        if (1600.0/width < 900.0/height)
            pixelsPerSlot = 1600.0/width;
        else
            pixelsPerSlot = 900.0/height;

        grid = new Cell[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                grid[col][row] = new Cell(this, col, row);
            }
        }
    }

    boolean checkInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void setElement(int x, int y, String ID) {
        if (checkInBounds(x, y) && !grid[x][y].element.ID.equals(ID)) {
            grid[x][y].element = new Element(x, y, ID);
        }
    }

    public void setElement(int x, int y, Element element) {
        if (checkInBounds(x, y)) {
            element.x = x;
            element.y = y;
            element.hasUpdated = true;
            grid[x][y].element = element;
        }
    }

    public void update() {        
        updates++;
        if (keyH.pauseGameSpeedPressed) {
            speed = 0.05;
        }
        else if (updates % (int) (100/(100*speed+0.01)+0.5) == 0) {
            time++;
            //Resets elements
            for (int col = width-1; col >= 0; col--) {
                for (int row = height-1; row >= 0; row--) {
                    grid[col][row].element.hasUpdated = false;
                }
            }

            //Starts right if time is even, otherwise from left
            if (time % 10 < 5) {
                for (int col = width-1; col >= 0; col--) {
                    for (int row = height-1; row >= 0; row--) {
                        grid[col][row].update();
                    }
                }
            }
            else {
                for (int col = 0; col < width; col++) {
                    for (int row = height-1; row >= 0; row--) {
                        grid[col][row].update();
                    }
                }
            }
        }

        //Player controls
        //Changing game speed
        if (keyH.increaseGameSpeedPressed && updates % 50 == 0) {
            speed += 0.01;
            if (speed > 1) {
                speed = 1;
            }
        }
        else if (keyH.decreaseGameSpeedPressed && updates % 50 == 0) {
            speed -= 0.01;
            if (speed < 0) {
                speed = 0;
            }
        }

        //"Painting" elements
        String selectedElement;
        if (keyH.item1Pressed)
            selectedElement = "stone";
        else if (keyH.item2Pressed)
            selectedElement = "sand";
        else if (keyH.item3Pressed)
            selectedElement = "water";
        else if (keyH.item4Pressed)
            selectedElement = "lava";
        else if (keyH.item5Pressed)
            selectedElement = "acid";
        else if (keyH.item6Pressed)
            selectedElement = "sponge";
        else if (keyH.item7Pressed)
            selectedElement = "steam";
        else if (keyH.item8Pressed)
            selectedElement = "empty";
        else if (keyH.item9Pressed)
            selectedElement = "empty";
        else
            selectedElement = "stone";

        if (mouseH.mouseLeftPressed) {
            setElement((int) (mouseH.mouseX/pixelsPerSlot), (int) (mouseH.mouseY/pixelsPerSlot-1), selectedElement);
            setElement((int) (mouseH.mouseX/pixelsPerSlot-1), (int) (mouseH.mouseY/pixelsPerSlot), selectedElement);
            setElement((int) (mouseH.mouseX/pixelsPerSlot), (int) (mouseH.mouseY/pixelsPerSlot), selectedElement);
            setElement((int) (mouseH.mouseX/pixelsPerSlot+1), (int) (mouseH.mouseY/pixelsPerSlot), selectedElement);
            setElement((int) (mouseH.mouseX/pixelsPerSlot), (int) (mouseH.mouseY/pixelsPerSlot+1), selectedElement);
        }
        if (mouseH.mouseRightPressed) {
            setElement((int) (mouseH.mouseX/pixelsPerSlot), (int) (mouseH.mouseY/pixelsPerSlot), "empty");
        }
    }

    public void draw(Graphics2D g2) {
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                g2.setColor(grid[col][row].element.color);
                g2.fillRect((int) (col*pixelsPerSlot+0.5), (int) (row*pixelsPerSlot+0.5), (int) (pixelsPerSlot+0.5), (int) (pixelsPerSlot+0.5));
            }
        }
    }
}
