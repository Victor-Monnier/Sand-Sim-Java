package main;

import javafx.scene.shape.Rectangle;

public class CollisionChecker {
    public static boolean checkCollision (Rectangle a, Rectangle b) {
        return a.intersects(b.getBoundsInLocal());
    }
}