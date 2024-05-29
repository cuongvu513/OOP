package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

// Cua so hien thi 
public class GameWindow extends JFrame{
    private JFrame jframe; // tao khung
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack(); // can frame theo panel
        jframe.setVisible(true);
    }
}