package Utilities;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

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
    Clip clip;
    Clip clipMusic;
    Clip clipESound;
    private boolean isMusic = false;
    private boolean endMusic = false;
    private boolean songPlayed = false;
    private boolean endESound = false;
    private boolean soundEPlayed = false;
    private boolean isESound = false;

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
            case "EndMusic":
            endMusic = true;
            filepath = "Global-Warning/res/audio/Nothing.wav";
            break;
            case "EndSound":
            endESound = true;
            filepath = "Global-Warning/res/audio/Nothing.wav";
            break;
            case "Music":
            filepath = "Global-Warning/res/audio/Music.wav";
            isMusic = true;
            break;
            case "Boss":
            filepath = "Global-Warning/res/audio/Bossn.wav";
            isMusic = true;
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
            case "Wind":
            filepath = "Global-Warning/res/audio/Wind.wav";
            isESound = true;
            break;
            case "Checkpoint":
            filepath = "Global-Warning/res/audio/Checkpoint.wav";
            break;
            case "Fireball":
            filepath = "Global-Warning/res/audio/Fire.wav";
            break;
            default:
            filepath = "";
            break;
            
        }
        musicPath = new File(filepath);
        try {
     

    /*setter*/   AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        if (endMusic == true) {
            clipMusic.stop();
            clip.stop();
            songPlayed = false;
        }
        if (isMusic == true && endMusic == false) {
            if (songPlayed == true){
            clipMusic.stop();
            songPlayed = false;
            }
            clipMusic = AudioSystem.getClip();
            clipMusic.open(audioInput);
            clipMusic.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusic.start();
            songPlayed = true;
        }
        if (endESound == true && soundEPlayed == true) {
            clipESound.stop();
            soundEPlayed = false;
        }
        if (isESound == true && endESound == false) {
            if (soundEPlayed == true){
            clipESound.stop();
            soundEPlayed = false;
            }
            clipESound = AudioSystem.getClip();
            clipESound.open(audioInput);
            clipESound.loop(Clip.LOOP_CONTINUOUSLY);
            clipESound.start();
            soundEPlayed = true;
        }
        else if (isMusic == false && isESound == false){
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        endMusic = false;
        isMusic = false;
        endESound = false;
        isESound = false;
    }
    }

    public void setMute(Boolean b) {
        this.mute = b;
    }
}
