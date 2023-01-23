package game;

import java.awt.Color;

public class Element {
    public String ID;
    public Color color;
    public boolean hasUpdated = false;
    public int x, y;
    public double xOffset, yOffset, deltaX = 0, deltaY = 0, viscosity = 1;
    public enum State {STATIC, FALLING, LIQUID, GAS, EMPTY};
    State state;

    public Element(int x, int y, String ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;

        switch (ID) {
            case "empty":
                color = new Color(16, 16, 16);
                state = State.EMPTY;
                break;
            case "stone":
                color = new Color((int) (Math.random()*20+90), (int) (Math.random()*20+90), (int) (Math.random()*20+90));
                state = State.STATIC;
                break;
            case "sand":    
                color = new Color((int) (Math.random()*41+180), (int) (Math.random()*41+180), 0);
                yOffset = 1;
                state = State.FALLING;
                break;
            case "water":
                color = new Color(0, 0, (int) (Math.random()*10+215));
                viscosity = 0.5;
                state = State.LIQUID;
                break;
            case "lava":
                color = new Color((int) (Math.random()*20+190), (int) (Math.random()*40+30), 0);
                viscosity = 0.05;
                state = State.LIQUID;
                break;
            case "acid":
                color = new Color(0, (int) (Math.random()*10+215), 0);
                viscosity = 0.25;
                state = State.LIQUID;
                break;
            case "steam":
                color = new Color((int) (Math.random()*10+235), (int) (Math.random()*10+235), (int) (Math.random()*10+235));
                state = State.GAS;
                break;
        }
    }

    public void setDeltaX(double DX) {
        deltaY = DX;
        if (deltaX > 1) {
            deltaX = 1;
        }
        else if (deltaX < -1) {
            deltaX = -1;
        }
    }

    public void changeDeltaX(double DX) {
        deltaX += DX;
        if (deltaX > 1) {
            deltaX = 1;
        }
        else if (deltaX < -1) {
            deltaX = -1;
        }
    }

    public int getState() {
        switch (state) {
            case STATIC:
                return 1;
            case FALLING:
                return 2;
            case LIQUID:
                return 3;
            case GAS:
                return 4;
            case EMPTY:
                return 5;
        }
        return 0;
    }

    public void setDeltaY(double DY) {
        deltaY = DY;
        if (deltaY > 1) {
            deltaY = 1;
        }
        else if (deltaY < -1) {
            deltaY = -1;
        }
    }

    public void changeDeltaY(double DY) {
        deltaY += DY;
        if (deltaY > 1) {
            deltaY = 1;
        }
        else if (deltaY < -1) {
            deltaY = -1;
        }
    }

    public void updatePosition() {
        xOffset += deltaX;
        yOffset += deltaY;
    }
}
