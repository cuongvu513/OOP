package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import entities.item.Boom;
import entities.item.Heart;
import entities.item.Reverse;
import entities.item.Rocket;
import entities.item.Stun;
import main.BackGroundMusic;
import main.GameMenu;

public class Player extends Entity {
    // Các hằng số cho hành động của người chơi
    private static final int REST_R = 0;
    private static final int REST_L = 1;
    private static final int SWIM_R = 2;
    private static final int SWIM_L = 3;

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = REST_R;
    private boolean left, up, right, down, dir = true;
    private boolean moving = false;
    private float speed = 1f;
    private int size;
    private int level;
    private int currentExp = 0; // Kinh nghiệm hiện tại
    private int maxExp = 10; // Kinh nghiệm cần thiết để lên cấp
    private GameMenu gameMenu;
    public boolean check = false;
    private boolean isReversed = false;
    public Timer reverseTime;
    private boolean isStunned = false;
    public Timer stunTime;
    private Object reverseTimer;
    private Object stunTimer;
    private long stunEndTime;
    private long reverseEndTime;
    private BackGroundMusic SoundEat = new BackGroundMusic("res/eat.wav");
    private BackGroundMusic SoundIteam = new BackGroundMusic("res/iteam.wav");
    private BackGroundMusic SoundRocket = new BackGroundMusic("res/iteamrocket.wav");
    private BackGroundMusic SoundTun = new BackGroundMusic("res/iteamtun.wav");

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
        this.size = 60;
        this.level = 1;
    }

    @Override
    public void update() {
        updateAnimationTick();
        updatePos();
        setAnimation();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, size, size, null);
        renderLevel(g);
        renderExpBar(g);
    }

    private void renderLevel(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Level: " + level, (int)x + 8, (int) y - 6);
        g.drawString("Level: " + level, (int) x + 10, (int) y - 6);
        g.drawString("Level: " + level, (int)x + 9, (int) y - 7);
        g.drawString("Level: " + level, (int)x + 9, (int) y - 5);
        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, (int)x + 9, (int)y - 6);
    }

    private void renderExpBar(Graphics g) {
        int barWidth = 200; // Chiều rộng của thanh kinh nghiệm
        int barHeight = 30; // Chiều cao của thanh kinh nghiệm
        int expBarX = 0; // Vị trí x của thanh kinh nghiệm
        int expBarY = 0; // Vị trí y của thanh kinh nghiệm

        g.setColor(Color.BLACK);
        g.drawRect(expBarX - 1, expBarY - 1, barWidth + 2, barHeight + 2);

        float expPercentage = (float) currentExp / maxExp;
        int expBarFilledWidth = (int) (barWidth * expPercentage);

        g.setColor(Color.GREEN);
        g.fillRect(expBarX, expBarY, expBarFilledWidth, barHeight);
    }

    // Phương thức để thêm kinh nghiệm
    public void gainExp(int exp) {
        currentExp += exp + 1;
        if (currentExp >= maxExp) {
            levelUp();
        }
    }

    // Phương thức để lên cấp
    private void levelUp() {
        size += 10;
        speed = 1.0f + level / 10.0f;
        level++;
        currentExp = 0;
        maxExp *= 1.5; // Tăng lượng kinh nghiệm cần thiết để lên cấp tiếp theo
    }

    // Cập nhật tiến trình hoạt hình
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }

    // Trả về số lượng khung hình cho hành động người chơi
    private int getSpriteAmount(int playerAction) {
        return 6;
    }

    // Đặt hoạt hình cho người chơi
    private void setAnimation() {
        if (moving) {
            if (right || dir) playerAction = SWIM_R;
            else if (left || !dir) playerAction = SWIM_L;
        } else {
            if (dir) playerAction = REST_R;
            else playerAction = REST_L;
        }
    }

    // Cập nhật vị trí của người chơi
    private void updatePos() {
        moving = false;

        if (isReversed) {
            if (right && !left) {
                x -= speed; // Đảo ngược hướng di chuyển
                if (x < 0) x = 0;
                moving = true;
                dir = false;
            } else if (left && !right) {
                x += speed; // Đảo ngược hướng di chuyển
                if (x > 1200) x = 1200;
                moving = true;
                dir = true;
            }

            if (down && !up) {
                y -= speed;
                if (y < 0) y = 0;
                moving = true;
            } else if (up && !down) {
                y += speed;
                if (y > 730) y = 730;
                moving = true;
            }
        } else {
            if (left && !right) {
                x -= speed;
                if (x < 0) x = 0;
                moving = true;
                dir = false;
            } else if (right && !left) {
                x += speed;
                if (x > 1200) x = 1200;
                moving = true;
                dir = true;
            }

            if (up && !down) {
                y -= speed;
                if (y < 0) y = 0;
                moving = true;
            } else if (down && !up) {
                y += speed;
                if (y > 730) y = 730;
                moving = true;
            }
        }
    }

    // Đặt trạng thái đảo ngược
    public void setReversed(boolean isReversed) {
        this.isReversed = isReversed;
        if (isReversed) {
            reverseEndTime = System.currentTimeMillis() + 7000;
            if (reverseTimer != null) {
                ((Timer) reverseTimer).cancel();
            }
            reverseTimer = new Timer();
            ((Timer) reverseTimer).schedule(new TimerTask() {
                @Override
                public void run() {
                    setReversed(false);
                }
            }, 6000);
        }
    }

    // Đặt trạng thái bị choáng
    public void setStunned(boolean isStunned) {
        this.isStunned = isStunned;
        if (isStunned) {
            speed = 0;
            stunEndTime = System.currentTimeMillis() + 7000;
            if (stunTimer != null) {
                ((Timer) stunTimer).cancel();
            }
            stunTimer = new Timer();
            ((Timer) stunTimer).schedule(new TimerTask() {
                @Override
                public void run() {
                    setStunned(false);
                }
            }, 6000);
        } else {
            speed = 1.5f;
        }
    }

    // Kiểm tra trạng thái đảo ngược
    public boolean isReversed() {
        return isReversed;
    }

    // Kiểm tra trạng thái bị choáng
    public boolean isStunned() {
        return isStunned;
    }

    // Kiểm tra ăn tim
    public boolean eat(Heart heart) {
        float xHeart = heart.getX();
        float yHeart = heart.getY();
        if (Math.abs(x + size / 2 - xHeart - 12) <= 15 && Math.abs(y + size / 2 - yHeart - 12) <= 15) {
            SoundIteam.playone();
            System.out.println("them 1 mang");
            return true;
        }
        return false;
    }

public boolean eat(Rocket rocket){
    float xRocket = rocket.getX();
    float yRocket = rocket.getY();
    if (Math.abs(x+size/2-xRocket-32) <=40 && Math.abs(y+size/2-yRocket-72)<=40){
        SoundRocket.playone();
        return true;
    }
    return false;
}
    public boolean eat(Stun stun){
    float xStun = stun.getX();
    float yStun = stun.getY();
    if (Math.abs(x+size/2-xStun-25) <=30 && Math.abs(y+size/2-yStun-25)<=30){
        SoundTun.playone();
        System.out.println(" bi choang' 5 giay^");
        return true;
    }
    return false;
}
    public boolean eat(Reverse reverse){
    float xReverse = reverse.getX();
    float yReverse = reverse.getY();
    if (Math.abs(x+size/2-xReverse-25) <=30 && Math.abs(y+size/2-yReverse-25)<=30){
        SoundIteam.playone();
        System.out.println("dao? nguoc di chuyen");
        return true;
    }
    return false;
}
    public boolean eat(Boom boom) {
    float xBoom = boom.getX();
    float yBoom = boom.getY();
    if (Math.abs(x+size/2 - xBoom-25) <= 30 && Math.abs(y+size/2 - yBoom-25) <= 30) {
        SoundRocket.playone();
        return true;
    }
    return false;
}   


// end eat 
// set time 
//tính thời gian choáng còn lại
public int getStunTimeLeft() {
    long timeLeft = stunEndTime - System.currentTimeMillis();
    return timeLeft > 0 ? (int) timeLeft : 0;
}
public int getReverseTimeLeft() {
    long timeLeft = reverseEndTime - System.currentTimeMillis();
    return timeLeft > 0 ? (int) timeLeft : 0;
}
//end time 
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
    public void GameOver(){
        gameMenu = new GameMenu();
        gameMenu.OverGame();
        this.x = -1000;
        this.y = -1000;
        speed=0;
        check = false;
    }
    public boolean eat(Fish fish){
        float xFish = fish.getX();
        float yFish = fish.getY();
        float sFish = fish.getSize()/2;
        int lv = this.level - fish.getLevel();
        if (Math.abs(x+size/2-xFish- sFish) <= 30+this.level*2 && Math.abs(y+size/2-yFish-sFish)<=30+this.level*2 ){
            if (lv>=0){
                SoundEat.playone();
                System.out.println("EATTTTT");
                gainExp(fish.getLevel());
                return true;
            }
            // viet game over
            else {
                check = true;
                x = 200;
                y = 20;
                speed =1f;
            }
            
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
