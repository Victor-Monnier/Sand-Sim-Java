package main;

import java.awt.event.*;

import javax.swing.AbstractAction;

public class KeyHandler {
    public boolean upArrowPressed, downArrowPressed, leftArrowPressed, rightArrowPressed, enterPressed, escapePressed;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public String lastKeyPressed;
	public boolean item1Pressed = true, item2Pressed, item3Pressed, item4Pressed, item5Pressed, item6Pressed, item7Pressed, item8Pressed, item9Pressed,
	increaseGameSpeedPressed, decreaseGameSpeedPressed, pauseGameSpeedPressed;

	//Menu Interaction
    public class UpArrowPressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = true;
		}		
	}
    public class UpArrowReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = false;
		}		
	}
	public class DownArrowPressedAction extends AbstractAction {
        @Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = true;
		}		
	}
    public class DownArrowReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = false;
		}		
	}
    public class LeftArrowPressedAction extends AbstractAction {
        @Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = true;
		}		
	}
    public class LeftArrowReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = false;
		}		
	}
    public class RightArrowPressedAction extends AbstractAction {
        @Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = true;
		}		
	}
    public class RightArrowReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = false;
		}		
	}
    public class EnterPressedAction extends AbstractAction {
        @Override public void actionPerformed(ActionEvent e) {
            enterPressed = true;
		}		
	}
    public class EnterReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            enterPressed = false;
		}		
	}
    public class EscapePressedAction extends AbstractAction {
        @Override public void actionPerformed(ActionEvent e) {
            escapePressed = true;
		}		
	}
    public class EscapeReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            escapePressed = false;
		}		
	}

	//Movement
	public class UpPressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			upPressed = true;
		}
	}
	public class UpReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			upPressed = false;
		}
	}
	public class DownPressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			downPressed = true;
		}
	}
	public class DownReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			downPressed = false;
		}
	}
	public class LeftPressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			leftPressed = true;
		}
	}
	public class LeftReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			leftPressed = false;
		}
	}
	public class RightPressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			rightPressed = true;
		}
	}
	public class RightReleasedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			rightPressed = false;
		}
	}

	//Selecting element
	public class Equip1PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = true;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("1 PRESSED");
		}		
	}
	public class Equip2PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = true;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("2 PRESSED");
		}		
	}
	public class Equip3PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = true;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("3 PRESSED");
		}		
	}
	public class Equip4PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = true;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("4 PRESSED");
		}		
	}
	public class Equip5PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = true;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("5 PRESSED");
		}		
	}
	public class Equip6PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = true;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("6 PRESSED");
		}		
	}
	public class Equip7PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = true;
			item8Pressed = false;
			item9Pressed = false;
			System.out.println("7 PRESSED");
		}		
	}
	public class Equip8PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = true;
			item9Pressed = false;
			System.out.println("8 PRESSED");
		}		
	}
	public class Equip9PressedAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
            item1Pressed = false;
			item2Pressed = false;
			item3Pressed = false;
			item4Pressed = false;
			item5Pressed = false;
			item6Pressed = false;
			item7Pressed = false;
			item8Pressed = false;
			item9Pressed = true;
			System.out.println("9 PRESSED");
		}		
	}

	//Game speed
	public class IncreaseGameSpeedPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            increaseGameSpeedPressed = true;
		}		
	}
	public class IncreaseGameSpeedReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            increaseGameSpeedPressed = false;
		}		
	}
	public class DecreaseGameSpeedPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            decreaseGameSpeedPressed = true;
		}		
	}
	public class DecreaseGameSpeedReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            decreaseGameSpeedPressed = false;
		}		
	}
	public class PauseGameSpeedPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            pauseGameSpeedPressed = !pauseGameSpeedPressed;
		}		
	}
}	
