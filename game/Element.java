package game;

import java.awt.Color;

public class Element {
    public String ID;
    public Color color;
    public boolean hasUpdated = false,
    saturated = false;
    public int x, y;
    public double deltaX = 0, deltaY = 0, viscosity = 1;
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
            case "sponge":
                color = new Color((int) (Math.random()*41+100), (int) (Math.random()*41+100), (int) (Math.random()*21+10));
                state = State.STATIC;
                break;
            case "sand":    
                color = new Color((int) (Math.random()*41+180), (int) (Math.random()*41+180), 0);
                deltaY = 1;
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

    public void decreaseVelocity() {
        if (deltaX < 0) {
            deltaX += 0.005;
        }
        if (deltaX > 0) {
            deltaX -= 0.005;
        }
    }

    public void update(Game game) {
        if (getState() == 2) {
            decreaseVelocity();
            if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 5) {
                if (deltaY < 0.05)
                    changeDeltaY(0.05);
                else if (deltaY < 0.25)
                    changeDeltaY(0.005);
                else if (deltaY < 0.5)
                    changeDeltaY(0.001);
            }
            else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 5) {
                if (deltaY < 0.025)
                    changeDeltaY(0.025);
                else if (deltaY < 0.1)
                    changeDeltaY(0.0025);
                else if (deltaY < 0.5)
                    changeDeltaY(0.0005);
                if (deltaX < 0.05)
                    changeDeltaY(0.05);
                if (deltaX < 0.25)
                    changeDeltaX(0.005);
                else if (deltaX < 0.5)
                    changeDeltaX(0.001);
            }
            else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 5) {
                if (deltaY < 0.025)
                    changeDeltaY(0.025);
                else if (deltaY < 0.1)
                    changeDeltaY(0.0025);
                else if (deltaY < 0.5)
                    changeDeltaY(0.0005);
                if (deltaX > -0.05)
                    changeDeltaY(-0.05);
                else if (deltaX > -0.25)
                    changeDeltaX(-0.005);
                else if (deltaX > -0.5)
                    changeDeltaX(-0.001);
            }
            else {
                deltaY = 0;
                decreaseVelocity();
            }
        }


    }
}
