package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OverMenu extends JPanel {
    private BufferedImage backgroundImage;
    private BufferedImage iconImage;
    private Rectangle iconArea;

    public OverMenu(GameMenu gameFrame) {
        setLayout(new BorderLayout());

        // Tải hình ảnh nền
        try {
            backgroundImage = ImageIO.read(new File("res/gameover.png"));
            iconImage = ImageIO.read(new File("res/iconover.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Thiết lập vị trí và kích thước
        int iconX = 520; 
        int iconY = 500; 
        int iconWidth = 220; 
        int iconHeight = 220; 
        iconArea = new Rectangle(iconX, iconY, iconWidth, iconHeight);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (iconArea.contains(e.getPoint())) {
                    gameFrame.showMainMenu();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        
        if (iconImage != null) {
            g.drawImage(iconImage, iconArea.x, iconArea.y, iconArea.width, iconArea.height, this);
        }
    }
}
