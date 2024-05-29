package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInput;
import inputs.MouseInput;
import java.util.*;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

// tao nen cho window
public class GamePanel extends JPanel{

    private MouseInput mouseinput;
    private Game game;
    public GamePanel(Game game){
        mouseinput = new MouseInput(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseinput);
        addMouseMotionListener(mouseinput);
    }


    // tao dien tich game 
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
// thay doi toa do 
    public void updateGame(){

    }
    
    // ve ca 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }
}
