package game;

import java.awt.Graphics2D;

public class Entity {
    Game game = null;
    public int health = 100;
    public double x = 0, y = 0, vx = 0, vy = 0;
    
    public void update() {};

    public void draw(Graphics2D g2) {};
}
