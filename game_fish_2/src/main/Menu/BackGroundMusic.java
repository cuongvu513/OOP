package main.Menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackGroundMusic {
    private Clip clip;
    public BackGroundMusic(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void play() {
        if (clip != null) {
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}