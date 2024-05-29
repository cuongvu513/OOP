package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
public class Fish extends Entity {
    private static File is = new File("res/src_fish.png");
    private int aniTick, aniIndex,aniSpeed = 20;
    private int fishAction = 0;
    private int size;
    private int id;
    private BufferedImage[][] animations;
    private float speedFish =0.4f;
    Random random = new Random();
    public Fish(float x,float y){
        super(x, y);
        this.size = random.nextInt(20)+50;
        loadAnimations();
    }
    public void setId(int id){
        this.id = id;
    }
    private boolean isRight(){
        if (this.id % 6 < 3 ) return false;
        return true; 
    }
    private void loadAnimations() {
        
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
    public void resetFish(){
        this.size = random.nextInt(20)+50;
        this.id = random.nextInt(72);
        int fishY = random.nextInt(100,800);
        int fishX;
        int id = random.nextInt(72);
        if (id % 6 <3) fishX = 1300;
            else fishX  = -50;
        this.x = fishX;
        this.y = fishY;
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
    
    private void updatePos() {
        if (isRight())
            x += speedFish;
        else x-= speedFish;
        if (x < -50 || x> 1300) resetFish();
    }
    public void update(int i){
        updateAnimationTick();
        updatePos();
        setAnimation();
    } 
    private void setAnimation() {
        fishAction = id;

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
}
