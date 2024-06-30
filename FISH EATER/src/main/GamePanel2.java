package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import inputs.*;

public class GamePanel2 extends JPanel implements Runnable, KeyListener {
    public Thread thread;
    private boolean isRunning;
    private KeyboardInput inputManger;
    private GameMenu gameFrame;
    private BackGroundMusic bgMusic;
    BufferedImage image;

    public GamePanel2(GameMenu gameFrame) {
        inputManger = new KeyboardInput(null);
        try {
            image = ImageIO.read(new File("res/Map.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        bgMusic = new BackGroundMusic("res/ocean-waves-112906.wav");
        bgMusic.play();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0, 0, GameMenu.SCREEN_WIDTH, GameMenu.SCREEN_HEIGHT);
        g.drawImage(image, 0 , 0, this);
    }

    public void startGame() {
        thread = new Thread(this);
        while(isRunning==false)
        {
            isRunning=true;
            new Game();
        }
    }

    @Override
    public void run() {
        long FPS = 80;
        long period = 1000 * 1000000 / FPS;
        long beginTime = System.nanoTime();
        while (isRunning) {
            long deltaTime = System.nanoTime() - beginTime;
            long sleepTime = period - deltaTime;
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else Thread.sleep(period / 2000000);
            } catch (InterruptedException ex) {
            }
            beginTime = System.nanoTime();
            // Giả lập kết thúc game sau một khoảng thời gian
            isRunning = false;
            gameFrame.showGameOver();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        inputManger.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManger.keyReleased(e);
    }
}
