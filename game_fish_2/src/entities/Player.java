package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;
import entities.Fish.*;
public class Player extends Entity {
    
    private BufferedImage[][] animations;
    private int aniTick, aniIndex,aniSpeed = 15;
    private int playerAction = REST_R;
    private boolean left,up,right,down,dir = true;
    private boolean moving = false;
    private float speed = 1.5f;
    public Player(float x ,float y){
        super(x,y);
        loadAnimations();

    }
    public void update(){
        updateAnimationTick();
        updatePos();
        setAnimation();
    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)x,(int)y,80,80,null);
    }


    // update vi tri hinh anh
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >=aniSpeed){
            aniTick = 0;
            aniIndex ++;
            if (aniIndex>= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }
    private void setAnimation() {
        if (moving){
            if (right || dir) playerAction = SWIM_R;
            else if (left || !dir) playerAction = SWIM_L;
            
        }
        else {
            if (dir) playerAction = REST_R;
            else playerAction =REST_L;
        }
    }
    // di chuyen
    private void updatePos() {
        moving = false;
        if (left && !right){
            x -= speed;
            if (x< 0) x= 0;
            moving = true;
            dir = false;
        }
        else if (right && !left){
            x+= speed;
            if (x > 1200) x = 1200;

            moving = true;
            dir = true;
        }
        if (up && !down){
            y -= speed;

            if (y< 0) y= 0;
            moving = true;
        }
        else if (down && !up){
            y += speed;
            if (y> 730) y= 730;
            moving = true; 
        }
    }
    private void loadAnimations() {
        File is = new File("res/spritesheet.png");
        // tranh loi doc anh 
        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[4][6];
            for (int j=0;j <animations.length;j++)
                for(int i=0; i<animations[j].length;i++){
                    animations[j][i] = img.getSubimage(i*256,j*256, 256, 256); // tao mang hinh anh dong 1
                } 
        } catch (IOException e) {
            // in ra loi
            e.printStackTrace();
        }
    }
    public boolean eat(Fish fish){
        float xFish = fish.getX();
        float yFish = fish.getY();
        if (Math.abs(x-xFish) <=10 && Math.abs(y-yFish)<=10){
            System.out.println("EATTTTT");
            return true;
        }
        return false;
    }
    public boolean isUp(){
        return up;
    }
    public void setUp(boolean up){
        this.up = up;
    }
    public boolean isLeft(){
        return left;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public boolean isRight(){
        return right;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public boolean isDown(){
        return down;
    }
    public void setDown(boolean down){
        this.down = down;
    }

}
