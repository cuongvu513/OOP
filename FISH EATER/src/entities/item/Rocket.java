package entities.item;

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
    public int id;
    private BufferedImage[] animations;
    private float speedFire =0.5f;
    public Color textColor;
    private int level;
    public float xRocket;
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
    @Override
    public void render(Graphics g){
        g.drawImage(animations[aniIndex], (int)x,(int)y,64,144,null);

    }
    public void resetRocket(float x){
        this.x =x;
        this.size = 50;
        this. y = -random.nextInt(1000);
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
        if (y > 1000) resetRocket(random.nextInt(100,1100));
    }
    @Override
    public void update(){
        updateAnimationTick();
        updatePos();
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
