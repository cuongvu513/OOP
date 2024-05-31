package main.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel implements KeyListener {
    private GameMenu gameFrame;
    private BufferedImage backgroundImage;

    public MainMenu(GameMenu gameFrame) {
        this.gameFrame = gameFrame;
        setLayout(new BorderLayout());

        // Tải hình ảnh nền
        try {
            backgroundImage = ImageIO.read(new File("res/map.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel("", JLabel.CENTER);
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> gameFrame.showGame());
        add(startButton, BorderLayout.SOUTH);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameFrame.showGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
