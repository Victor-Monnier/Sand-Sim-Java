package game;

import java.awt.Color;

public class Element {
    public enum Type {MPTY, STNE, SPNG, SAND, GUNP, WATR, LAVA, ACID, VAPR, FIRE}
    public enum State {EMPTY, STATIC, FALLING, LIQUID, GAS};
    public Type ID;
    public Color color;
    public boolean hasUpdated = false,
    saturated = false;
    public int timeSinceLastMove = 0;
    public double x, y, vx = 0, vy = 1, viscosity = 1;
    State state;

    public Element(int x, int y, Type ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;

        switch (ID) {
            case MPTY:
                color = new Color(16, 16, 16);
                state = State.EMPTY;
                break;
            case STNE:
                color = new Color((int) (Math.random()*11+70), (int) (Math.random()*11+70), (int) (Math.random()*11+70));
                state = State.STATIC;
                break;
            case SPNG:
                color = new Color((int) (Math.random()*41+100), (int) (Math.random()*41+100), (int) (Math.random()*21+10));
                state = State.STATIC;
                break;
            case SAND:    
                color = new Color((int) (Math.random()*41+180), (int) (Math.random()*41+180), 0);
                state = State.FALLING;
                break;
            case GUNP:    
                color = new Color((int) (Math.random()*21+105), (int) (Math.random()*21+105), (int) (Math.random()*21+90));
                state = State.FALLING;
                break;
            case WATR:
                color = new Color(0, 0, (int) (Math.random()*11+215));
                viscosity = 0.5;
                state = State.LIQUID;
                break;
            case LAVA:
                color = new Color((int) (Math.random()*21+190), (int) (Math.random()*41+30), 0);
                viscosity = 0.05;
                state = State.LIQUID;
                break;
            case ACID:
                color = new Color(0, (int) (Math.random()*11+215), 0);
                viscosity = 0.75;
                state = State.LIQUID;
                break;
            case VAPR:
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*11+235), (int) (Math.random()*11+235));
                state = State.GAS;
                break;
            case FIRE:
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*101+30), (int) (Math.random()*11));
                state = State.GAS;
                break;
        }
    }

    public Element(int x, int y, String ID) {
        this.x = x;
        this.y = y;

        switch (ID) {
            case "MPTY":
                color = new Color(16, 16, 16);
                this.ID = Type.MPTY;
                state = State.EMPTY;
                break;
            case "STNE":
                color = new Color((int) (Math.random()*11+70), (int) (Math.random()*11+70), (int) (Math.random()*11+70));
                this.ID = Type.STNE;
                state = State.STATIC;
                break;
            case "SPNG":
                color = new Color((int) (Math.random()*41+100), (int) (Math.random()*41+100), (int) (Math.random()*21+10));
                this.ID = Type.SPNG;
                state = State.STATIC;
                break;
            case "SAND":    
                color = new Color((int) (Math.random()*41+180), (int) (Math.random()*41+180), 0);
                this.ID = Type.SAND;
                state = State.FALLING;
                break;
            case "GUNP":    
                color = new Color((int) (Math.random()*21+105), (int) (Math.random()*21+105), (int) (Math.random()*21+90));
                this.ID = Type.GUNP;
                state = State.FALLING;
                break;
            case "WATR":
                color = new Color(0, 0, (int) (Math.random()*11+215));
                viscosity = 0.5;
                this.ID = Type.WATR;
                state = State.LIQUID;
                break;
            case "LAVA":
                color = new Color((int) (Math.random()*21+190), (int) (Math.random()*41+30), 0);
                viscosity = 0.05;
                this.ID = Type.LAVA;
                state = State.LIQUID;
                break;
            case "ACID":
                color = new Color(0, (int) (Math.random()*11+215), 0);
                viscosity = 0.75;
                this.ID = Type.ACID;
                state = State.LIQUID;
                break;
            case "VAPR":
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*11+235), (int) (Math.random()*11+235));
                this.ID = Type.VAPR;
                state = State.GAS;
                break;
            case "FIRE":
                color = new Color((int) (Math.random()*11+235), (int) (Math.random()*101+30), (int) (Math.random()*11));
                this.ID = Type.FIRE;
                state = State.GAS;
                break;
        }
    }

    public int getState() {
        switch (state) {
            case EMPTY:
                return 0;
            case STATIC:
                return 1;
            case FALLING:
                return 2;
            case LIQUID:
                return 3;
            case GAS:
                return 4;
        }
        return 0;
    }

    public boolean checkIfMoving() {
        return getState() != 1 && (Math.abs(vx) >= 0.1 || vy == 1);
    }

    public void setVX(double newX) {
        vx = newX;
        if (vx > 10) {
            vx = 10;
        }
        else if (vx < -10) {
            vx = -10;
        }
    }

    public void changeVX(double xChange) {
        vx += xChange;
        if (vx > 10) {
            vx = 10;
        }
        else if (vx < -10) {
            vx = -10;
        }
    }

    public void setVY(double newY) {
        vy = newY;
        if (vy > 10) {
            vy = 10;
        }
        else if (vy < -10) {
            vy = -10;
        }
    }

    public void changeVY(double yChange) {
        vy += yChange;
        if (vy > 10) {
            vy = 10;
        }
        else if (vy < -10) {
            vy = -10;
        }
    }

    public void decreaseVelocity() {
        vy *= 0.98;
        changeVY(0.05);
        vx *= 0.98;
        if (Math.abs(vx) < 0.1) {
            vx = 0;
        }
        if (vy >= 1 && vy < 2) {
            vy = 1;
        }
        if (vy >= 2) {
            changeVY(-0.05);
        }
    }
}
