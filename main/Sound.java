package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    URL soundURL[] = new URL[30];
    Clip clip;
    public int volume = 50;
    FloatControl fc;

    public Sound() {
        //soundURL[0] = getClass().getResource("/res/audio/bruh.wav");
        //soundURL[1] = getClass().getResource("/res/audio/enemy_death.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            updateVolume();
        } catch(Exception e) {}
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    void updateVolume() {
        fc.setValue((float) (Math.log10(Math.pow(10, volume))/100*86-80));
    }

    public void setVolume(int vol) {
        volume = vol;
        updateVolume();
    }
}
