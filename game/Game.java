package game;

import main.KeyHandler;
import main.MouseHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
            element.timeSinceLastMove = 0;
            grid[x][y].element = element;
        }
    }

    public Element getElement(int x, int y) {
        if (checkInBounds(x, y)) {
            return grid[x][y].element;
        }
        else {
            return null;
        }
    }

    public ArrayList<int[]> getPointsInCircle(int centerX, int centerY, double radius) {
        ArrayList<int[]> validPoints = new ArrayList<int[]>();
        for (double x = centerX-radius+0.5; x <= centerX+radius; x++) {
            int maxYDifference = (int) (Math.sqrt(radius*radius-(x-centerX)*(x-centerX))+0.5);
            for (int y = (int) (centerY-radius); y < centerY+radius; y++) {
                if (y-centerY >= 0 && y-centerY < maxYDifference || y-centerY < 0 && y-centerY >= -maxYDifference) {
                    int[] point = {(int) (x), y};
                    validPoints.add(point);
                }
            }
        }
        return validPoints;
    }

    public void explode(int x, int y, double size, double power) {
        double centerX = x;
        double centerY = y;
        if (size % 2 == 0) {
            centerX -= 0.5;
            centerY -= 0.5;
        }
        for (int[] point : getPointsInCircle(x, y, size)) {
            double pointX = point[0]-centerX, pointY = point[1]-centerY;
            if (!checkInBounds(point[0], point[1]))
                continue;
            //System.out.println("AMOGUS???");
            /*if (Math.random() < 0.5 || grid[point[0]][point[1]].element.getState() != 2 && grid[point[0]][point[1]].element.getState() != 3false) {
                setElement(point[0], point[1], "empty");
                System.out.println("??????");
                continue;
            }*/
            //If right of center
            if (pointX > 0) {
                double angle = Math.atan(pointX/pointY);
                System.out.print((pointX/pointY)+"\t"+pointX+"/"+pointY+" \t");
                System.out.println(angle*180/Math.PI);
                //getElement(x, y).setDeltaX((size-pointX));
            }
            //If left of center
            else if (pointX < 0) {
                double angle = Math.atan(pointX/pointY)+180;
                System.out.print((pointX/pointY)+"\t"+pointX+"/"+pointY+" \t");
                System.out.println(angle*180/Math.PI);
                //getElement(x, y).setDeltaX(-size-pointX);
            }
            //If same x as center
            else {
                //System.out.println("TEST");
                System.out.println("NO ANGLE");
                if (pointX == 0) {
                    //getElement(x, y).setDeltaX(0);
                    //getElement(x, y).setDeltaY(-size-pointX);
                }
            }
        }
    }

    public void update() {  
        updates++;
        if (keyH.pauseGameSpeedPressed) {
            System.out.println(Math.atan(1.0)*180/Math.PI);
            speed = 0.05;
        }
        else if (updates % (int) (100/(100*speed+0.01)+0.5) == 0) {
            //Time at start of update
            long startTime = System.currentTimeMillis();      

            time++;
            //Resets elements
            for (int col = width-1; col >= 0; col--) {
                for (int row = height-1; row >= 0; row--) {
                    grid[col][row].element.hasUpdated = false;
                    grid[col][row].element.timeSinceLastMove++;
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

            //Prints out how long this update cycle took
            if (updates % 500 == 0)
                System.out.println("Frame time: "+(System.currentTimeMillis()-startTime)+"ms");
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
            selectedElement = "gp";
        else if (keyH.item7Pressed)
            selectedElement = "flame";
        else if (keyH.item8Pressed)
            selectedElement = "sponge";
        else if (keyH.item9Pressed)
            selectedElement = "empty";
        else
            selectedElement = "stone";

        //TEMP
        if (keyH.enterPressed) {
            explode(mouseH.mouseX/10, mouseH.mouseY/10, 2, 0);
        }

        if (mouseH.mouseLeftPressed) {
            //System.out.println((mouseH.mouseX+pixelsPerSlot/2.0)/10+", "+(mouseH.mouseY+pixelsPerSlot/2.0)/10);
            for (int[] point : getPointsInCircle((int) (mouseH.mouseX+pixelsPerSlot/2.0)/10, (int) (mouseH.mouseY+pixelsPerSlot/2.0)/10, 2)) {
                setElement(point[0], point[1], selectedElement);
            }
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
