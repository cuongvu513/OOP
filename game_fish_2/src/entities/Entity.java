package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
    
    protected float x,y;
    public Entity(float x, float y){
        this.x = x;
        this.y = y; 
    }
}
