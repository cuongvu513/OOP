package entities.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import entities.Entity;
import java.util.Random;

public class Reverse extends Entity {
    private int aniTick, aniIndex, aniSpeed = 10;
    private int reverseAction = 0;
    private BufferedImage[][] animations;
    private float speedReverse = 0.6f;
    private Random random = new Random();
    private int size;
    
    public Reverse(float x, float y) {
        super(x, y);
        this.size = 50; 
        loadAnimations();
    }

    private void loadAnimations() {
        File is = new File("res/reverse.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[1][1]; 
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 512, j * 512, 512, 512); // tạo mảng hình ảnh động 1
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[reverseAction][aniIndex], (int) x, (int) y, size, size, null); // Vẽ bom với kích thước là size x size
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations[reverseAction].length) {
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        if (!exploded) {
            x += speedReverse;
            if (x > 1280) { 
                resetRev();
            }
        } else {
            size = 0;
        }
    }
    

public void resetRev(){
    this.x = -1000;
    this.size = 50;
    this.y = random.nextInt(100,600);
}
public void update() {
    updateAnimationTick();
    updatePos();
}

public float getX() {
    return this.x;
}

public float getY() {
    return this.y;
}
private boolean exploded = false;
public void setX(float x){
    this.x = x;
}
public void setY(float y){
    this.y = y;
}
public boolean isExploded() {
    return exploded;
}
    
public void setSize(int size){
    this.size = size;
}


}
