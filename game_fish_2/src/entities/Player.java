package entities;

import java.awt.Color;
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
    private int size ;
    private int level;
    private int currentExp = 0; // Kinh nghiệm hiện tại
    private int maxExp = 10;     // Kinh nghiệm cần thiết để lên cấp
    public Player(float x ,float y){
        super(x,y);
        loadAnimations();
        this.size = 80;
        this.level = 1;

    }
    public void update(){
        updateAnimationTick();
        updatePos();
        setAnimation();
    }
    // Phương thức để thêm kinh nghiệm
    public void gainExp(int exp) {
        currentExp += exp;
        if (currentExp >= maxExp) {
            levelUp();
        }
    }

    // Phương thức để lên cấp
    private void levelUp() {
        size +=10;
        level++;
        currentExp = 0;
        maxExp *= 1.5; // Tăng lượng kinh nghiệm cần thiết để lên cấp tiếp theo
    }
    private void renderlevel(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Level: " + level, (int)x +8 ,(int) y - 6);
        g.drawString("Level: " + level,(int) x + 10,(int) y - 6);
        g.drawString("Level: " + level, (int)x+9,(int) y - 7);
        g.drawString("Level: " + level, (int)x+9,(int) y - 5);
        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, (int)x+9, (int)y - 6);
    }
    private void renderExpbar(Graphics g){
        int barWidth = 200; // Chiều rộng của thanh kinh nghiệm
        int barHeight = 30; // Chiều cao của thanh kinh nghiệm
        int expBarX = 0;   // Vị trí x của thanh kinh nghiệm
        int expBarY = 0; // Vị trí y của thanh kinh nghiệm

        g.setColor(Color.BLACK);
        g.drawRect(expBarX - 1, expBarY - 1, barWidth + 2, barHeight + 2);

        float expPercentage = (float) currentExp / maxExp;
        int expBarFilledWidth = (int) (barWidth * expPercentage);

        g.setColor(Color.GREEN);
        g.fillRect(expBarX, expBarY, expBarFilledWidth, barHeight);

    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)x,(int)y,size,size,null);
        renderlevel(g);
        renderExpbar(g);
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
        float sFish = fish.getSize();
        int lv = this.level - fish.getLevel();
        if (Math.abs(x+size/2-xFish- sFish) <= 30 && Math.abs(y+size/2-yFish-sFish)<=30){
            if (lv>=0){
                System.out.println("EATTTTT");
                gainExp(fish.getLevel());
                return true;
            }
            // viet game over
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
    public int getLevel(){
        return this.level;
    }

}
