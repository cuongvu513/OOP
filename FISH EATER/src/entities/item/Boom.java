package entities.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import entities.Entity;
import java.util.Random;

public class Boom extends Entity {
    private int aniTick, aniIndex, aniSpeed = 10;
    private int boomAction = 0;
    private BufferedImage[][] animations;
    private float speedBoom = 1f;
    private Random random = new Random();
    private int size;
    
    public Boom(float x, float y) {
        super(x, y);
        this.size = 50; 
        loadAnimations();
    }

    private void loadAnimations() {
        File is = new File("res/boom.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[3][3]; 
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 130, j * 130, 130, 130); // tạo mảng hình ảnh động 1
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(animations[boomAction][aniIndex], (int) x, (int) y, size, size, null); // Vẽ bom với kích thước là size x size
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations[boomAction].length) {
                aniIndex = 0;
                boomAction += 1;
                boomAction %=3;
            }
        }
    }

    private void updatePos() {
        if (!exploded) {
            x += speedBoom;
            if (x > 1280) { 
               resetBoom();
            }
        } else {
            size = 0;
        }
    }
    public void resetBoom(){
        this.size = 50;
        this.x = -3000;
        this.y = random.nextInt(100,600);
    }
    @Override
    public void update() {
        updateAnimationTick();
        updatePos();
    }

   
    private boolean exploded = false;
    public boolean isExploded() {
        return exploded;
    }
    
    public void setSize(int size){
        this.size = size;
    }


}
