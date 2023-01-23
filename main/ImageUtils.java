package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    //Rotates an image around a point
    public static void drawRotatedImage(Graphics2D g2, BufferedImage image, int degrees, int x, int y, int width, int height, int pivotX, int pivotY, double GS) {
        g2.rotate(Math.toRadians(degrees), (int) (pivotX*GS), (int) (pivotY*GS));
        g2.drawImage(image, (int) (x*GS), (int) (y*GS), (int) (width*GS), (int) (height*GS), null);
        g2.rotate(Math.toRadians(-degrees), (int) (pivotX*GS), (int) (pivotY*GS));
    }
}
