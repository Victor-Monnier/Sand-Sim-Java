package main;

//import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    //Game Globals
    public boolean isFullScreen = false,
    mouseLeftClicked = false, mouseLeftPressed = false, mouseRightClicked = false, mouseRightPressed = false, mouseScrolled = false;
    public int menuID = 0, 
    mouseX = 0, mouseY = 0, mouseScrollAmount;
    public enum GameState {PLAYING, INMENU, PAUSED, INGAMEMENU}
    public GameState gameState = GameState.PLAYING;

    //Settings
    public int 
    screenWidth = 1600, /* 16:9 Aspect Ratio (fixed) */
    screenHeight = 900,
    FPS = 50, /* Amount of graphical updates per second */
    volume = 50;
    public double GS = screenHeight/900.0; /* graphics scaling: all graphics are same relative size and position regardless of screen size */

    //Preloads commonly used sprites. This only works when a limited amount of graphics
    /*public BufferedImage    
    public void getCommonImages() {
        try {
           // main_menu_background = ImageIO.read(getClass().getResourceAsStream("/res/background/menu/main_menu.jpg"));
           System.out.println("loading images");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //Keyboard
    public KeyHandler keyH = new KeyHandler();
    //Keys for menu interaction
    Action upArrowPressedAction = keyH.new UpArrowPressedAction();
    Action upArrowReleasedAction = keyH.new UpArrowReleasedAction();
    Action downArrowPressedAction = keyH.new DownArrowPressedAction();
    Action downArrowReleasedAction = keyH.new DownArrowReleasedAction();
    Action leftArrowPressedAction = keyH.new LeftArrowPressedAction();
    Action leftArrowReleasedAction = keyH.new LeftArrowReleasedAction();
    Action rightArrowPressedAction = keyH.new RightArrowPressedAction();
    Action rightArrowReleasedAction = keyH.new RightArrowReleasedAction();
    Action enterPressedAction = keyH.new EnterPressedAction();
    Action enterReleasedAction = keyH.new EnterReleasedAction();
    Action escapePressedAction = keyH.new EscapePressedAction();
    Action escapeReleasedAction = keyH.new EscapeReleasedAction();
    //Movement
    Action upPressedAction = keyH.new UpPressedAction();
    Action upReleasedAction = keyH.new UpReleasedAction();
    Action downPressedAction = keyH.new DownPressedAction();
    Action downReleasedAction = keyH.new DownReleasedAction();
    Action leftPressedAction = keyH.new LeftPressedAction();
    Action leftReleasedAction = keyH.new LeftReleasedAction();
    Action rightPressedAction = keyH.new RightPressedAction();
    Action rightReleasedAction = keyH.new RightReleasedAction();
    //Selecting elements
    Action equip1PressedAction = keyH.new Equip1PressedAction();
    Action equip2PressedAction = keyH.new Equip2PressedAction();
    Action equip3PressedAction = keyH.new Equip3PressedAction();
    Action equip4PressedAction = keyH.new Equip4PressedAction();
    Action equip5PressedAction = keyH.new Equip5PressedAction();
    Action equip6PressedAction = keyH.new Equip6PressedAction();
    Action equip7PressedAction = keyH.new Equip7PressedAction();
    Action equip8PressedAction = keyH.new Equip8PressedAction();
    Action equip9PressedAction = keyH.new Equip9PressedAction();
    //Game speed
    Action increaseGameSpeedPressedAction = keyH.new IncreaseGameSpeedPressedAction();
    Action increaseGameSpeedReleasedAction = keyH.new IncreaseGameSpeedReleasedAction();
    Action decreaseGameSpeedPressedAction = keyH.new DecreaseGameSpeedPressedAction();
    Action decreaseGameSpeedReleasedAction = keyH.new DecreaseGameSpeedReleasedAction();
    Action pauseGameSpeedPressedAction = keyH.new PauseGameSpeedPressedAction();
    //JComponent that the keybinds are added to
    JComponent thisWindow = Main.window.getRootPane();
    public void updateKeyBindings() {
        //Keys for menu interaction
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upArrowPressed");
		thisWindow.getActionMap().put("upArrowPressed", upArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "upArrowReleased");
		thisWindow.getActionMap().put("upArrowReleased", upArrowReleasedAction);
		thisWindow.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downArrowPressed");
		thisWindow.getActionMap().put("downArrowPressed", downArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "downArrowReleased");
		thisWindow.getActionMap().put("downArrowReleased", downArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftArrowPressed");
		thisWindow.getActionMap().put("leftArrowPressed", leftArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "leftArrowReleased");
		thisWindow.getActionMap().put("leftArrowReleased", leftArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightArrowPressed");
		thisWindow.getActionMap().put("rightArrowPressed", rightArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rightArrowReleased");
		thisWindow.getActionMap().put("rightArrowReleased", rightArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
		thisWindow.getActionMap().put("enterPressed", enterPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released ENTER"), "enterReleased");
		thisWindow.getActionMap().put("enterReleased", enterReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapePressed");
		thisWindow.getActionMap().put("escapePressed", escapePressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released ESCAPE"), "escapeReleased");
		thisWindow.getActionMap().put("escapeReleased", escapeReleasedAction);
        //Movement
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("W"), "upPressed");
		thisWindow.getActionMap().put("upPressed", upPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released W"), "upReleased");
		thisWindow.getActionMap().put("upReleased", upReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("S"), "downPressed");
		thisWindow.getActionMap().put("downPressed", downPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released S"), "downReleased");
		thisWindow.getActionMap().put("downReleased", downReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("A"), "leftPressed");
		thisWindow.getActionMap().put("leftPressed", leftPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released A"), "leftReleased");
		thisWindow.getActionMap().put("leftReleased", leftReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("D"), "rightPressed");
		thisWindow.getActionMap().put("rightPressed", rightPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released D"), "rightReleased");
		thisWindow.getActionMap().put("rightReleased", rightReleasedAction);
        //Selecting elements
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("1"), "equip1Pressed");
		thisWindow.getActionMap().put("equip1Pressed", equip1PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("2"), "equip2Pressed");
		thisWindow.getActionMap().put("equip2Pressed", equip2PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("3"), "equip3Pressed");
		thisWindow.getActionMap().put("equip3Pressed", equip3PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("4"), "equip4Pressed");
		thisWindow.getActionMap().put("equip4Pressed", equip4PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("5"), "equip5Pressed");
		thisWindow.getActionMap().put("equip5Pressed", equip5PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("6"), "equip6Pressed");
		thisWindow.getActionMap().put("equip6Pressed", equip6PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("7"), "equip7Pressed");
		thisWindow.getActionMap().put("equip7Pressed", equip7PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("8"), "equip8Pressed");
		thisWindow.getActionMap().put("equip8Pressed", equip8PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("9"), "equip9Pressed");
		thisWindow.getActionMap().put("equip9Pressed", equip9PressedAction);
        //Game speed
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("UP"), "incSpeedPressed");
		thisWindow.getActionMap().put("incSpeedPressed", increaseGameSpeedPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "incSpeedReleased");
		thisWindow.getActionMap().put("incSpeedReleased", increaseGameSpeedReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "incSpeedPressed");
		thisWindow.getActionMap().put("incSpeedPressed", increaseGameSpeedPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "incSpeedReleased");
		thisWindow.getActionMap().put("incSpeedReleased", increaseGameSpeedReleasedAction);
		thisWindow.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "decSpeedReleased");
		thisWindow.getActionMap().put("decSpeedReleased", decreaseGameSpeedPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "decSpeedPressed");
		thisWindow.getActionMap().put("decSpeedPressed", decreaseGameSpeedReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "decSpeedReleased");
		thisWindow.getActionMap().put("decSpeedReleased", decreaseGameSpeedPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "decSpeedPressed");
		thisWindow.getActionMap().put("decSpeedPressed", decreaseGameSpeedReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "pausePressed");
		thisWindow.getActionMap().put("pausePressed", pauseGameSpeedPressedAction);
    }

    //Mouse
    MouseHandler mouseH = new MouseHandler(this, GS);
    game.Game game = new game.Game(keyH, mouseH);
    Sound sound = new Sound();
    Thread gameThread;

    //Used in other classes where GameState can't be accessed
    public void setGameState(String thisGameState) {
        switch (thisGameState) {
            case "PLAYING":
                gameState = GameState.PLAYING;
                break;
            case "INMENU":
                gameState = GameState.INMENU;
                break;
            case "PAUSED":
                gameState = GameState.PAUSED;
                break;
            case "INGAMEMENU":
                gameState = GameState.INGAMEMENU;
                break;
        }
    }
    //Used in other classes where GameState can't be accessed
    public String getGameState() {
        switch (gameState) {
            case PLAYING:
                return "PLAYING";
            case INMENU:
                return "INMENU";
            case PAUSED:
                return "PAUSED";
            case INGAMEMENU:
                return "INGAMEMENU";
        }
        return("ERROR: FAULTY GAMESTATE");
    }

    public GamePanel() {
        //System stuff
        //getCommonImages();

        //playSF(0);
        updateKeyBindings();
        
        //Scaling for fullscreen requires additional information, so it comes last
        if (isFullScreen)
            setFullScreen();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addMouseWheelListener(mouseH);
        this.setFocusable(true);
    }

    public void setFullScreen() {
        //Getting the width and height of RL screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //Setting the JFrame to have the proper resolution
        screenWidth = 2560;//ge.getMaximumWindowBounds().width;
        screenHeight = 1440;//ge.getMaximumWindowBounds().height;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        GS = screenHeight/900.0;
        //Resets the mouse handler
        mouseH.GS = GS;

        //Setting the JFrame to be full screen
        gd.setFullScreenWindow(Main.window);

        update();
        repaint();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game loop
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double drawDelta = 0;
        double updateInterval = 1000000000/1000;
        double updateDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long drawTimer = 0;
        long updateTimer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            drawDelta += (currentTime-lastTime)/drawInterval;
            updateDelta += (currentTime-lastTime)/updateInterval;
            drawTimer += (currentTime-lastTime);
            updateTimer += (currentTime-lastTime);
            lastTime = currentTime;

            //Logical updates
            if (updateDelta >= 1) {
                update();
                updateDelta--;
            }

            //Graphical updates
            if (drawDelta >= 1) {
                repaint();
                drawDelta--;
                drawCount++;
            }

            if (drawTimer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                drawTimer = 0;
            }
            if (updateTimer >= 1000000000) {
                updateTimer = 0;
            }
        }
    }

    //Logcial updates
    public void update() {
        switch (gameState) {
            case INMENU:
            case PAUSED:
                break;
            case INGAMEMENU:
            case PLAYING:
                game.update();
                break;
        }

        //Updates mouse variables
        mouseLeftPressed = mouseH.mouseLeftPressed;
        mouseLeftClicked = mouseH.mouseLeftClicked;
        mouseRightPressed = mouseH.mouseRightPressed;
        mouseRightClicked = mouseH.mouseRightClicked;
        mouseScrolled = mouseH.mouseScrolled;
        mouseX = mouseH.mouseX;
        mouseY = mouseH.mouseY;
        mouseScrollAmount = mouseH.mouseScrollAmount;

        //Resets variables that only last for one update
        mouseH.mouseLeftClicked = false;
        mouseH.mouseRightClicked = false;
        mouseH.mouseScrolled = false;
        mouseH.mouseScrollAmount = 0;
        keyH.escapePressed = false;
        keyH.enterPressed = false;
        keyH.lastKeyPressed = null;
    }

    //Graphical updates
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //Anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 

        game.draw(g2);        

        g2.dispose();
    }

    //All sound related stuff
    public void playSF(int i) {
        sound.setFile(i);
        setSFVolume(volume);
        sound.play();
    }
    public void setSFVolume(int SFVolume) {
        volume = SFVolume;
        if (volume > 100)
            volume = 100;
        else if (volume < 0)
            volume = 0;
        sound.setVolume(volume);
    }
}
