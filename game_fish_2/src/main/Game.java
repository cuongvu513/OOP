package main;
import java.awt.Graphics;
import java.awt.Font;
import entities.Player;
import entities.item.Rocket;
import entities.Fish;
import java.util.Random;
import entities.item.Rocket;
//  Khoi tao object game
public class Game implements Runnable{
    private GameWindow  gameWindow;
    private GamePanel   gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    public Fish[] fish;
    private Font font;
    private int quantityFish ;
    private Rocket rocket;
    Random random = new Random();
    public Game(){
        initClasses();
        font = new Font("Arial", Font.PLAIN, 12);
        gamePanel   = new GamePanel(this);
        gameWindow  = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
        
    }
    private void initClasses() {
        player = new Player(200,200);
        quantityFish = 10;
        rocket = new Rocket(random.nextInt(200,1000),-100);
        fish = new Fish[quantityFish];
        for (int i=0;i<quantityFish;i++){
            int fishX;
            int fishY = random.nextInt(100,700);
            int id = random.nextInt(72);
            fishX = random.nextInt(1280);
            fish[i] = new Fish(fishX,fishY);
            fish[i].setId(id);
            fish[i].setLF(random.nextInt(3));
        }
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        player.update();
        //fish.update(0);
        rocket.update();
        if (player.check) {
            player.GameOver();
            gameWindow.set();
        }
        for (int i=0;i<quantityFish;i++){
            fish[i].update(i);
            if (player.eat(fish[i])) {
                fish[i].setSize(0);
                fish[i].resetFish();
                int levelPlayer = player.getLevel();
                fish[i].setLF(levelPlayer + random.nextInt(levelPlayer) -random.nextInt(levelPlayer));
            }
        }

    }
    public void render(Graphics g){
        player.render(g);
        // fish.render(g);
        // fish2.render(g);
        rocket.render(g);
        g.setFont(font);
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
