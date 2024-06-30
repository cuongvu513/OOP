package entities;

import java.awt.Graphics;

public abstract class Entity {
    protected float x, y;
    protected int size,level;
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Phương thức trừu tượng
    public abstract void update();

    public abstract void render(Graphics g);
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    
}
