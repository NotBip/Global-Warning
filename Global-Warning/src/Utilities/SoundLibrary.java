package Utilities;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import GameStates.Playing;

public class SoundLibrary {
    
    String filepath;
    File musicPath;
    Playing playing;

    public SoundLibrary(Playing playing) {
        this.playing = playing;
    }
    
    public void playSound(String sound) {
        switch (sound) {
            case "Select":
            filepath = "Global-Warning/res/audio/button1.wav";
            break;
            case "Deselect":
            filepath = "Global-Warning/res/audio/button2.wav";
            break;
            case "On":
            filepath = "Global-Warning/res/audio/on.wav";
            break;
            case "Off":
            filepath = "Global-Warning/res/audio/off.wav";
            break;
            case "Begin":
            filepath = "Global-Warning/res/audio/complete.wav";
            break;
            case "Shoot":
            filepath = "Global-Warning/res/audio/Gun.wav";
            break;
            default:
            filepath = "";
            break;
            
        }
        musicPath = new File(filepath);
        try {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void loopMusic(){

    }
}
