package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity {
    public int width = 10, height = 10;

    public Player(Game game, int x, int y, int health) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public Player(Game game, String stats) {
        //will parse string for stats
    }

    public boolean checkIfCellIntersects(int x, int y) {
        return (x >= (int) this.x-width/2 && x < (int) (this.x+width/2)) && (y+1 > (int) (this.y-height/2) && y < (int) (this.y+height/2));
    }

    @Override
    public void update() {
        vy += 0.05;
        vx *= 0.95;

        if (game.keyH.upPressed) {
            if (onGround)
                vy = -2;
            if (underWater || inWater && game.time % 20 == 0) {
                vy = -0.5;
            }
        }
        if (game.keyH.downPressed)
            vy += 0.05;
        if (game.keyH.leftPressed)
            vx -= 0.05;
        if (game.keyH.rightPressed)
            vx += 0.05;
        if (!game.keyH.leftPressed && !game.keyH.rightPressed && Math.abs(vx) < 0.15)
            vx = 0;

        if (vx > 10)
            vx = 10;
        else if (vx < -10)
            vx = -10;
        if (vy > 10)
            vy = 10;
        else if (vy < -10)
            vy = -10;
        if (inWater) {
            vx *= 0.9;
            vy *= 0.9;
            if (vy > 1)
                vy /= 2;
        }

        onGround = false;
        inWater = false;
        underWater = false;

        for (int i = 0; i < 10; i++) {
            y += vy/10;

            if (vy > 0) {
                for (int col = (int) (x-width/2); col < (int) (x+width/2); col++) {
                    if (col < 0 || col >= game.width)
                        continue;
                    if ((int) (y+height/2-1) >= 0 && (int) (y+height/2-1) < game.height) {
                        int tempState = game.grid[col][(int) (y+height/2-1)].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            y = (int) (y-1);
                            vy = 0;
                            continue;
                        }
                    }
                    if ((int) (y+height/2) >=  0 && (int) (y+height/2) < game.height) {
                        int tempState = game.grid[col][(int) (y+height/2)].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            y = (int) (y);
                            vy = 0;
                            onGround = true;
                        }
                    }
                }
            }
            else if (vy < 0) {
                for (int col = (int) (x-width/2); col < (int) (x+width/2); col++) {
                    if (col < 0 || col >= game.width)
                        continue;
                    if ((int) (y-height/2+1) >= 0 && (int) (y-height/2+1) < game.height) {
                        int tempState = game.grid[col][(int) (y-height/2+1)].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            y = (int) (y+1.5);
                            vy = 0.1;
                            continue;
                        }
                    }
                    if ((int) (y-height/2) >= 0 && (int) (y-height/2) < game.height) {
                        int tempState = game.grid[col][(int) (y-height/2)].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            y = (int) (y+0.5);
                            vy = 0.1;
                        }
                    }
                }
            }

            //Checking if in/under water
            if (y+height/2-1 >= 0 && y+height/2-1 < game.height) {
                if (x-width/2 >= 0 && x-width/2 < game.width && game.grid[(int) (x-width/2)][(int) (y+height/2-1)].element.getState() == 3)
                    inWater = true;
                if (x+width/2-1 >= 0 && x+width/2-1 < game.width && game.grid[(int) (x+width/2-1)][(int) (y+height/2-1)].element.getState() == 3)
                    inWater = true;
            }
            if (y-height/2 >= 0 && y-height/2 < game.height) {
                if (x-width/2 >= 0 && x-width/2 < game.width && game.grid[(int) (x-width/2)][(int) (y-height/2)].element.getState() == 3)
                    underWater = true;
                if (x+width/2-1 >= 0 && x+width/2-1 < game.width && game.grid[(int) (x+width/2-1)][(int) (y-height/2)].element.getState() == 3)
                    underWater = true;
            }

            x += vx/10;
            if (vx > 0) {
                for (int row = (int) (y-height/2); row < (int) (y+height/2); row++) {
                    if (row < 0 || row >= game.height)
                        continue;
                    if ((int) (x+width/2-1) >= 0 && (int) (x+width/2-1) < game.width) {
                        int tempState = game.grid[(int) (x+width/2-1)][row].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            x = (int) (x-1);
                            vx = 0;
                            continue;
                        }
                    }
                    if ((int) (x+width/2) >= 0 && (int) (x+width/2) < game.width) {
                        int tempState = game.grid[(int) (x+width/2)][row].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            if (row-1 > 0) {
                                tempState = game.grid[(int) (x+width/2)][row-1].element.getState();
                                if (vy <= 0 && (row-1 >= 0 && row == (int) (y+height/2-1) && (tempState != 1 && tempState != 2))) {
                                    y = (int) (y-1);
                                    vy -= 0.25;
                                    continue;
                                }
                            }
                            x = (int) (x);
                            vx /= 2;
                        }
                    }
                }
            }
            else if (vx < 0) {
                for (int row = (int) (y-height/2); row < (int) (y+height/2); row++) {
                    if (row < 0 || row >= game.height)
                        continue;
                    if ((int) (x-width/2+1) >= 0 && (int) (x-width/2+1) < game.width) {
                        int tempState = game.grid[(int) (x-width/2+1)][row].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            x = (int) (x+1.5);
                            vx = 0;
                            continue;
                        }
                    }
                    if ((int) (x-width/2) >= 0 && (int) (x-width/2) < game.width) {
                        int tempState = game.grid[(int) (x-width/2)][row].element.getState();
                        if (tempState == 1 || tempState == 2) {
                            if (row-1 > 0) {
                                tempState = game.grid[(int) (x-width/2)][row-1].element.getState();
                                if (vy <= 0 && (row-1 >= 0 && row == (int) (y+height/2-1) && (tempState != 1 && tempState != 2))) {
                                    y = (int) (y-1);
                                    vy -= 0.25;
                                    continue;
                                }
                            }
                            x = (int) (x+0.5);
                            vx /= 2;
                        }
                    }
                }
            }
        }
    }   
    
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.magenta);
        g2.fillRect((int) ((game.drawnWidth/2-width/2)*game.pixelsPerSlot), (int) ((game.drawnHeight/2-height/2)*game.pixelsPerSlot), (int) (width*game.pixelsPerSlot), (int) (height*game.pixelsPerSlot));
        /*g2.setColor(Color.YELLOW);
        for (int col = (int) (x-width/2); col < (int) (x+width/2); col++) {
            if ((int) (y+height/2) < 0 || (int) (y+height/2) >= game.height)
                break;
            if (col < 0 || col >= game.width)
                continue;
            
            g2.drawRect((int) ((game.drawnWidth/2)*game.pixelsPerSlot), (int) (game.drawnHeight/2*game.pixelsPerSlot), (int) (game.pixelsPerSlot), (int) (game.pixelsPerSlot));
        }*/
    }
}
