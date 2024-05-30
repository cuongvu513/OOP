package main.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OverMenu extends JPanel {
    private BufferedImage backgroundImage;

    public OverMenu(GameMenu gameFrame) {
        setLayout(new BorderLayout());

        // Tải hình ảnh nền
        try {
            backgroundImage = ImageIO.read(new File("res/Gameover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel("", JLabel.CENTER);
        label.setForeground(Color.WHITE); // Để văn bản dễ đọc hơn trên nền hình ảnh
        add(label, BorderLayout.CENTER);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> gameFrame.showMainMenu());
        add(mainMenuButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
