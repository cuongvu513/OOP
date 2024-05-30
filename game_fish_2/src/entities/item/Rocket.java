package entities.item;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.Entity;
import java.util.*;

import java.awt.Color;
public class Rocket extends Entity {
    private static File is = new File("res/fireball.png");
    private int aniTick, aniIndex,aniSpeed = 20;
    private int size;
    private int id;
    private BufferedImage[] animations;
    private float speedFire =0.5f;
    private Color textColor;
    private int level;
    private float xRocket;
    private Random random = new Random();
    public Rocket(float x,float y){
        super(x, y);
        //this.size = random.nextInt(20)+50;
        this.textColor = Color.RED;
        loadAnimations(); 
    }

    private void loadAnimations() {
        // tranh loi doc anh 
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[6];
            for (int j=0;j <animations.length;j++){
                    animations[j] = img.getSubimage(0,j*576, 256, 576); // tao mang hinh anh dong 1
                } 
        } catch (IOException e) {
            // in ra loi
            e.printStackTrace();
        }
    }
    public void render(Graphics g){
        g.drawImage(animations[aniIndex], (int)x,(int)y,64,144,null);

    }
    
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >=aniSpeed){
            aniTick = 0;
            aniIndex ++;
            if (aniIndex>= (6))
                aniIndex = 0;
        }
    }
    
    private void updatePos() {
        y += speedFire;
    }
    public void update(){
        updateAnimationTick();
        updatePos();
    } 

    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public int getLevel(){
        return this.level;
    }
}
