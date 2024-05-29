package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
public class Fish extends Entity {
    private int aniTick, aniIndex,aniSpeed = 20;
    private int fishAction = 0;
    private int size;
    private BufferedImage[][] animations;
    private float speedFish =0.4f;
    Random random = new Random();
    public Fish(float x,float y){
        super(x, y);
        this.size = random.nextInt(20)+50;
        loadAnimations();
    }
    private void loadAnimations() {
        File is = new File("res/fish.png");
        // tranh loi doc anh 
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[72][4];
            for (int j=0;j <animations.length;j++)
                for(int i=0; i<animations[j].length;i++){
                    animations[j][i] = img.getSubimage(i*498,j*327, 498, 327); // tao mang hinh anh dong 1
                } 
        } catch (IOException e) {
            // in ra loi
            e.printStackTrace();
        }
    }
    public void render(Graphics g){
        g.drawImage(animations[fishAction][aniIndex], (int)x,(int)y,size,size,null);
    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >=aniSpeed){
            aniTick = 0;
            aniIndex ++;
            if (aniIndex>= 4)
                aniIndex = 0;
        }
    }
    
    private void updatePos(int i) {
        if (i%2==0)
            x += speedFish;
        else x-= speedFish;
    }
    public void update(int i){
        updateAnimationTick();
        updatePos(i);
        setAnimation();
    }
    private void setAnimation() {
        fishAction = 1;
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
}
