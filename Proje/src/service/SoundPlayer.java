package service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    /**
     * Belirtilen dosya yolundaki ses dosyasını çalar.
     * 
     * @param soundFilePath Çalınacak ses dosyasının yolu
     */
    public static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}
