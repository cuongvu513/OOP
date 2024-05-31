package main.Menu;

import java.awt.event.KeyEvent;

public class InputManger {
    public void processKeyPressed(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP:
                System.out.println("You press UP");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("You press DOWN");
                break;
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_ENTER:
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_A:
                break;
        }
    }
    public void processKeyReleased(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP:
                System.out.println("You released UP");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("You released DOWN");
                break;
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_ENTER:
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_A:
                break;
        }
    }
}
