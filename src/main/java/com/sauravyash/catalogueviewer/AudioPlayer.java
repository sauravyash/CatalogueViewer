/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer;

import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * @author Yash
 */
public class AudioPlayer extends Thread{
     
    /**
     * this flag indicates whether the playback completes or not.
     */
    
    private boolean isPlaying = false;
    private boolean isPaused = false;
    
    boolean playCompleted;
    File audioFile;
    
    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;
    
    
    public AudioPlayer(String audioFilePath) {
        audioFile = new File(audioFilePath);
    }
    
    public AudioPlayer(File audioFile) {
        this.audioFile = audioFile;
    }
    
     
    /**
     * Play a given audio file.
     * @throws NullPointerException 
     */
    public void playTrack() throws NullPointerException{
        try {
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
                AudioFormat format = audioStream.getFormat();
                
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                
                try (SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info)) {
                    audioLine.open(format);
                    
                    audioLine.start();
                    
                    System.out.println("Playback started.");
                    
                    byte[] bytesBuffer = new byte[BUFFER_SIZE];
                    int bytesRead = -1;
                    isPlaying = true;
                    
                    while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                        if (!isPlaying) break;
                        if (isPaused) {
                            try {
                                isPaused = false;
                                this.wait();
                            } 
                            catch (InterruptedException e) {
                                System.err.println(e);
                                break;
                            }
                        }
                        else {
                            audioLine.write(bytesBuffer, 0, bytesRead);
                        }
                    }
                    
                    audioLine.drain();
                }
            }
             
            System.out.println("Playback completed.");
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            System.err.println(ex);
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            System.err.println(ex);
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            System.err.println(ex);
        }  catch (NullPointerException e){
            System.out.println("File (" + audioFile.getAbsolutePath() + ") Not Found");
            System.err.println(e);
        }
    }
    
    /**
     * Start the Audio Track as a new thread.
     * Required to run as a new thread due to multitasking.
     */
    @Override
    public void run() {
        this.playTrack();
    }
    
    /**
     * Stop the audio.
     * Stops the thread safely.
     */
    public void StopPlaying() {
        isPlaying = false;
    }
    
    /**
     * pause the thread
     */
    public void togglePause() {
        isPaused = !isPaused;
    }
}