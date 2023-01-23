package main;

import java.awt.event.*;

import javax.swing.AbstractAction;

public class KeyHandler {
    public boolean upArrowPressed, downArrowPressed, leftArrowPressed, rightArrowPressed, enterPressed, escapePressed;
	public String lastKeyPressed;
	public boolean item1Pressed = true, item2Pressed, item3Pressed, item4Pressed, item5Pressed, item6Pressed, item7Pressed, item8Pressed, item9Pressed;

	//Menu Interaction
    public class UpArrowPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = true;
		}		
	}
    public class UpArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = false;
		}		
	}
	public class DownArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = true;
		}		
	}
    public class DownArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = false;
		}		
	}
    public class LeftArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = true;
		}		
	}
    public class LeftArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = false;
		}		
	}
    public class RightArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = true;
		}		
	}
    public class RightArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = false;
		}		
	}
    public class EnterPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            enterPressed = true;
		}		
	}
    public class EnterReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            enterPressed = false;
		}		
	}
    public class EscapePressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            escapePressed = true;
		}		
	}
    public class EscapeReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            escapePressed = false;
		}		
	}

	//Selecting element
	public class Equip1PressedAction extends AbstractAction{
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
	public class Equip2PressedAction extends AbstractAction{
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
	public class Equip3PressedAction extends AbstractAction{
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
	public class Equip4PressedAction extends AbstractAction{
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
	public class Equip5PressedAction extends AbstractAction{
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
	public class Equip6PressedAction extends AbstractAction{
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
	public class Equip7PressedAction extends AbstractAction{
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
	public class Equip8PressedAction extends AbstractAction{
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
	public class Equip9PressedAction extends AbstractAction{
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
}	
