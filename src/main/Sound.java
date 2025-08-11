/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Branislav Vujanov
 */
public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[5];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/Theme-Song.wav");
        soundURL[1] = getClass().getResource("/sound/EndEffect.wav");
        soundURL[2] = getClass().getResource("/sound/CollisionEffect.wav");
        soundURL[3] = getClass().getResource("/sound/WinEffect.wav");
    }
    
    public void setFile(int i){
        try {
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void play(){
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
}
