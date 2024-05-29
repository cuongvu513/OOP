package main;
import java.awt.Graphics;

import entities.Player;
import entities.Fish;
import java.util.Random;
//  Khoi tao object game
public class Game implements Runnable{
    private GameWindow  gameWindow;
    private GamePanel   gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    public Fish[] fish;
    private int quantityFish ;
    Random random = new Random();
    public Game(){
        initClasses();
        gamePanel   = new GamePanel(this);
        gameWindow  = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
        
    }
    private void initClasses() {
        player = new Player(200,200);
        quantityFish = random.nextInt(6)+3;
        fish = new Fish[quantityFish];
        for (int i=0;i<quantityFish;i++){
            int fishX = random.nextInt(1200);
            int fishY = random.nextInt(800);
            fish[i] = new Fish(fishX,fishY);
        }
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        player.update();
        //fish.update(0);
        for (int i=0;i<quantityFish;i++){
            fish[i].update(i);
            if (player.eat(fish[i])) fish[i].setSize(0);
        }
    }
    public void render(Graphics g){
        player.render(g);
        // fish.render(g);
        // fish2.render(g);
        for (int i=0;i<quantityFish;i++){
            fish[i].render(g);
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        double timePerFrame = 1000000000.0/ FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames =0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        // tao loop game
        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime)/ timePerUpdate;
            deltaF += (currentTime - previousTime)/ timePerFrame;
            previousTime = currentTime;
            if (deltaU >=1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1){
                gamePanel.repaint();
                deltaF--;
                frames++;
            }
            // if (System.nanoTime()- lastFrame >= timePerFrame){
            //     gamePanel.repaint();
            //     lastFrame = now;
            //     frames++;
            // }
            
            if (System.currentTimeMillis() - lastCheck >=1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: "+ frames + "| UPS: "+ updates);
                frames =0;
                updates = 0;
            }  
        }
    }

    public Player getPlayer() {
        return player;
    }
}
