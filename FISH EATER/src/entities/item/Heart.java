package entities.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import entities.Entity;
import java.util.Random;

public class Heart extends Entity {
    private int aniTick, aniIndex, aniSpeed = 20;
    private int heartAction = 0;
    private BufferedImage[][] animations;
    private float speedHeart = 0.5f;
    private Random random = new Random();
    private int size;
    
    public Heart(float x, float y) {
        super(x, y);
        this.size = 35; 
        loadAnimations();
    }

    private void loadAnimations() {
        File is = new File("res/heart.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[1][1]; 
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 2000, j * 2000,2550, 2370); // tạo mảng hình ảnh động 1
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(animations[heartAction][aniIndex], (int) x, (int) y, size, size, null); // Vẽ bom với kích thước là size x size
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations[heartAction].length) {
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        if (!exploded) {
            x += speedHeart;
            if (x > 1280) { // Assuming screen width is 1280
                resetHeart();
            }
        } else {
            size = 0;
        }
    }
    

    @Override
    public void update() {
        updateAnimationTick();
        updatePos();
    }

   
    public void resetHeart(){
        this.size = 35;
        this.x = -10000;
        this.y = random.nextInt(100,600);
    }
  
 
    private boolean exploded = false;

    public boolean isExploded() {
    return exploded;
}
    public void setSize(int size){
    this.size = size;
}
}

