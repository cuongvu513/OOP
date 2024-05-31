package main.Menu;

import javax.swing.*;

import java.awt.*;
public class GameMenu extends JFrame {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MainMenu mainMenuPanel;
    private GamePanel gamePanel;
    private OverMenu gameOverPanel;
    private BackGroundMusic backGroundMusic;
    
    public GameMenu() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        backGroundMusic = new BackGroundMusic("res/nuocbien.wav");
        backGroundMusic.play();
        mainMenuPanel = new MainMenu(this);
        gamePanel = new GamePanel(this);
        gameOverPanel = new OverMenu(this);

        mainPanel.add(mainMenuPanel, "MainMenu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(gameOverPanel, "GameOver");

        add(mainPanel);
        this.addKeyListener(gamePanel);
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
        mainMenuPanel.requestFocusInWindow();  // Đảm bảo MainMenuPanel nhận focus khi hiển thị
    }

    public void showGame() {
        cardLayout.show(mainPanel, "Game");
        gamePanel.startGame();
        this.setVisible(false);
    }

    public void showGameOver() {
        cardLayout.show(mainPanel, "GameOver");
        gameOverPanel.requestFocusInWindow();  // Đảm bảo GameOverPanel nhận focus khi hiển thị
    }

    public void runGame() {
        GameMenu gameFrame = new GameMenu();
        gameFrame.setVisible(true);
        gameFrame.showMainMenu();
    }
    public void OverGame() {
        GameMenu gameFrame = new GameMenu();
        gameFrame.setVisible(true);
        gameFrame.showGameOver();
    }
}
