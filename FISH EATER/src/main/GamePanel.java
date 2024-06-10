package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInput;
import inputs.MouseInput;

// tao nen cho window
public class GamePanel extends JPanel{

    private MouseInput mouseinput;
    private Game game;
    private BufferedImage backgroundImage;
    public GamePanel(Game game){
        mouseinput = new MouseInput(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseinput);
        addMouseMotionListener(mouseinput);
        loadBackgroundImage("res/map_2.png");
    }
    private void loadBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // tao dien tich game 
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    
    // ve ca 
    @Override
    public void paintComponent(Graphics g){  
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0,1280,800, null);
        } else {
            // Optional: Set a default background color if image loading fails
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        game.render(g);


        // Phan Dung 
        String lifeText = "LIFE: " + game.getLife()  ;
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString(lifeText, 1100, 40);


        if (game.getPlayer().isStunned()){
            paintCD_Stun(g);

        }
        if (game.getPlayer().isReversed()){
            paintCD_Reverse(g);
        }
    }
    
    private void paintCD_Stun(Graphics g) {
        int stunTimeLeft = game.getPlayer().getStunTimeLeft() / 1000; 
        if (stunTimeLeft > 0) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String stunText = "Thời gian bị choáng: " + stunTimeLeft + " giây!";
            g.drawString(stunText, 480, 20);
        }
    }
    private void paintCD_Reverse(Graphics g) {
        int reverseTimeLeft = game.getPlayer().getReverseTimeLeft() / 1000; 
        if (reverseTimeLeft > 0) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String reverseText = "Thời gian bị đảo ngược nút di chuyển " + reverseTimeLeft + " giây!";
            g.drawString(reverseText, 400, 45);
        }
    }
     

    public Game getGame(){
        return game;
    }
}
