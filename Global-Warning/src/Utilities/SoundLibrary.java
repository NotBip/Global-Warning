package Utilities;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import GameStates.Playing;

    /**
	***********************************************
	* @Author : Bobby Walden
	* @Originally made : 21 JAN, 2024
	* @Last Modified: 22 JAN, 2024
	* @Description: Manages the sound files for the game session.
	***********************************************
	*/

public class SoundLibrary {

    // Variables
    String filepath;
    File musicPath;
    Playing playing;
    Boolean mute = false;

    // Intitialize Sound Library
    public SoundLibrary(Playing playing) {
        this.playing = playing;
    }
    
    /**
	@Method Name: playSound
	@Author: Bobby Walden
	@Creation Date: 21 JAN, 2024
	@Modified Date: 22 JAN, 2024
	@Description: Manages and plays the audio files.
	@Parameters: String sound
	@Returns: N/A
	@Dependencies: N/A
	@Throws/Exceptions: Exceptions e
	*/
    public void playSound(String sound) {
        if (mute == false) {
        switch (sound) {
            case "Music":
            filepath = "Global-Warning/res/audio/Music.wav";
            break;
            case "Boss":
            filepath = "Global-Warning/res/audio/Boss.wav";
            break;
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
            case "Dash":
            filepath = "Global-Warning/res/audio/Dash.wav";
            break;
            case "Jump":
            filepath = "Global-Warning/res/audio/Jump.wav";
            break;
            case "Land":
            filepath = "Global-Warning/res/audio/land.wav";
            break;
            case "Walk":
            filepath = "Global-Warning/res/audio/Walk.wav";
            break;
            case "Damage":
            filepath = "Global-Warning/res/audio/Damage.wav";
            break;
            case "Unlock":
            filepath = "Global-Warning/res/audio/Chest.wav";
            break;
            case "Locked":
            filepath = "Global-Warning/res/audio/Locked.wav";
            break;
            case "Hit":
            filepath = "Global-Warning/res/audio/Hit.wav";
            break;
            case "Thunder":
            filepath = "Global-Warning/res/audio/Lightning.wav";
            break;
            case "Throw":
            filepath = "Global-Warning/res/audio/Throw.wav";
            break;
            case "Explode":
            filepath = "Global-Warning/res/audio/Explode.wav";
            break;
            case "Heal":
            filepath = "Global-Warning/res/audio/Heal.wav";
            break;
            case "Denied":
            filepath = "Global-Warning/res/audio/Denied.wav";
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
    }

    public void setMute(Boolean b) {
        this.mute = b;
    }
}
