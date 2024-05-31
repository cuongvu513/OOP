package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.Color;
public class Fish extends Entity {
    private File is = new File("res/src_fish.png");
    private int aniTick, aniIndex,aniSpeed = 20;
    private int fishAction = 0;
    private int size;
    private int id;
    private BufferedImage[][] animations;
    private float speedFish =0.4f;
    private Color textColor;
    private int level;
    Random random = new Random();
    private int LP,levelPlayer;
    public void setLF(int LP){
        this.level = LP;
        this.size = LP*10+ 30;
    }
    public Fish(float x,float y){
        super(x, y);
        //this.size = random.nextInt(20)+50;
        this.textColor = Color.RED;
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
            animations = new BufferedImage[72+18][4];
            for (int j=0;j <72;j++)
                for(int i=0; i<4;i++){
                    animations[j][i] = img.getSubimage(i*498,j*327, 498, 327); // tao mang hinh anh dong 1
                } 
            //is = new File("res/src_fish_2");
            
        } catch (IOException e) {
            // in ra loi
            e.printStackTrace();
        }
    }
    public void render(Graphics g){
        g.drawImage(animations[fishAction][aniIndex], (int)x,(int)y,size,size,null);
        g.setColor(Color.BLACK);
        g.drawString("Level: " + level, (int)x +8 ,(int) y - 6);
        g.drawString("Level: " + level,(int) x + 10,(int) y - 6);
        g.drawString("Level: " + level, (int)x+9,(int) y - 7);
        g.drawString("Level: " + level, (int)x+9,(int) y - 5);
        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, (int)x+9, (int)y - 6);
        
    }
    public void resetFish(){
        this.id = random.nextInt(72);
        int fishY = random.nextInt(100,700);
        int fishX;
        int id = random.nextInt(0,72+18);
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
            if (this.id <72){
                if (aniIndex>= 4)
                 aniIndex = 0;
                }
            else 
            {
                if (aniIndex>= 3)
                aniIndex = 0;
            }
        }
    }
    
    private void updatePos() {
        if (isRight())
            x += speedFish+level/10.0;
        else x-= (speedFish+level/10.0);
        if (x < -50 || x> 1300) {
            resetFish();
            setLF(random.nextInt(7));
        }
        
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
    public int getLevel(){
        return this.level;
    }
}
