package main;

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
    private JButton startButton;

    public MainMenu(GameMenu gameFrame) {
        this.gameFrame = gameFrame;
        setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để căn giữa

        // Tải hình ảnh nền
        try {
            backgroundImage = ImageIO.read(new File("res/Giaodien.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tạo nút Start Game với icon
        try {
            ImageIcon startIcon = new ImageIcon(ImageIO.read(new File("res/starticon.png")));
            startButton = new JButton(startIcon);
        } catch (IOException e) {
            e.printStackTrace();
            startButton = new JButton("Start Game"); // fallback nếu không tải được icon
        }
        
        startButton.addActionListener(e -> {
            gameFrame.showGame();
            setFocusable(false); // Ngừng nhận sự kiện bàn phím khi vào game
        });

        // Đặt nút ở giữa màn hình
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);

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
            setFocusable(true); 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
