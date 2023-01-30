package game;

import java.awt.Color;

public class Element {
    public String ID;
    public Color color;
    public boolean hasUpdated = false,
    saturated = false;
    public int x, y, timeSinceLastMove = 0;
    public double VX = 0, VY = 1, viscosity = 1;
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
                color = new Color((int) (Math.random()*11+70), (int) (Math.random()*11+70), (int) (Math.random()*11+70));
                state = State.STATIC;
                break;
            case "sponge":
                color = new Color((int) (Math.random()*41+100), (int) (Math.random()*41+100), (int) (Math.random()*21+10));
                state = State.STATIC;
                break;
            case "sand":    
                color = new Color((int) (Math.random()*41+180), (int) (Math.random()*41+180), 0);
                VY = -5;
                VX = -5;
                state = State.FALLING;
                break;
            case "gp":    
                color = new Color((int) (Math.random()*21+105), (int) (Math.random()*21+105), (int) (Math.random()*21+90));
                VY = -5;
                VX = 5;
                state = State.FALLING;
                break;
            case "water":
                color = new Color(0, 0, (int) (Math.random()*11+215));
                viscosity = 0.5;
                state = State.LIQUID;
                break;
            case "lava":
                color = new Color((int) (Math.random()*21+190), (int) (Math.random()*41+30), 0);
                viscosity = 0.05;
                state = State.LIQUID;
                break;
            case "acid":
                color = new Color(0, (int) (Math.random()*11+215), 0);
                viscosity = 0.75;
                state = State.LIQUID;
                break;
            case "steam":
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*11+235), (int) (Math.random()*11+235));
                state = State.GAS;
                break;
            case "flame":
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*101+30), (int) (Math.random()*11));
                state = State.GAS;
                break;
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

    public boolean checkIfMoving() {
        return getState() != 1 && (Math.abs(VX) >= 1 || (int) VY != 1);
    }

    public void setDeltaX(double newX) {
        VX = newX;
        if (VX > 10) {
            VX = 10;
        }
        else if (VX < -10) {
            VX = -10;
        }
    }

    public void changeDeltaX(double xChange) {
        VX += xChange;
        if (VX > 10) {
            VX = 10;
        }
        else if (VX < -10) {
            VX = -10;
        }
    }

    public void setDeltaY(double newY) {
        VY = newY;
        if (VY > 10) {
            VY = 10;
        }
        else if (VY < -10) {
            VY = -10;
        }
    }

    public void changeDeltaY(double yChange) {
        VY += yChange;
        if (VY > 10) {
            VY = 10;
        }
        else if (VY < -10) {
            VY = -10;
        }
    }

    public void decreaseVelocity() {
        if (VX > 0) {
            if (VX > 6)
                changeDeltaX(-0.5);
            else if (VX > 4)
                changeDeltaX(-0.25);
            else if (VX > 2)
                changeDeltaX(-0.1);
            else
                changeDeltaX(-0.01);
        }
        else {
            if (VX < -6)
                changeDeltaX(0.5);
            else if (VX < -4)
                changeDeltaX(0.25);
            else if (VX < -2)
                changeDeltaX(0.1);
            else
                changeDeltaX(0.01);
        }
        if (VY > 1.1) {
            changeDeltaY(-0.1);
        }
        else {
            if (VY < -2)
                changeDeltaY(0.5);
            else if (VY < 0)
                changeDeltaY(0.25);
            else
                changeDeltaY(0.1);
        }
    }
}
