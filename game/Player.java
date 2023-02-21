package game;
public class Player extends Entity {
    public Player(Game game, int x, int y, int health) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public Player(Game game, String stats) {
        //will parse string for stats
    }

    @Override
    public void update() {

    }   
    
    @Override
    public void draw() {

    }
}
