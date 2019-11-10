/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A set of helper utility functions to assist with the program.
 * @author Yash
 */
public class Utilities {

    /**
     * Loads the specified image into a Label as an Icon
     * @param label The label to insert the image within
     * @param ImgLocation The location of the the image on the system
     */
    public static void LoadImageInJLabel(JLabel label, File ImgLocation) {
        // System.out.println(ImgLocation.getAbsolutePath());
        try {
            BufferedImage img = ImageIO.read(ImgLocation);
            Image ScaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(ScaledImg);
            label.setIcon(imageIcon);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads the specified image into a Label as an Icon with the specified height and width.
     * @param label The label to insert the image within
     * @param ImgLocation The location of the the image on the system
     * @param height
     * @param width
     */
    public static void LoadImageInJLabel(JLabel label, File ImgLocation, int height, int width) {
        // System.out.println(ImgLocation.getAbsolutePath());
        try {
            BufferedImage img = ImageIO.read(ImgLocation);
            Image ScaledImg = img.getScaledInstance(height, width, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(ScaledImg);
            label.setIcon(imageIcon);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads the specified image into a Button as an Icon.
     * @param button The button to insert the image within
     * @param ImgLocation The location of the the image on the system
     */
    public static void LoadImageInJButton(JButton button, File ImgLocation) {
        try {
            BufferedImage img = ImageIO.read(ImgLocation);
            Image ScaledImg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(ScaledImg);
            button.setIcon(imageIcon);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    /**
     * Asynchronously runs a function after the specified delay. 
     * @param runnable The function to run after the delay.
     * @param delay the length of the delay in seconds.
     */
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (InterruptedException e){
                System.err.println(e);
            }
        }).start();
    }
    
    /**
     * Centers the window in the middle of the screen.
     * @param frame the frame to center
     */
    public static void CenterWindow(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
            (dim.width - frame.getSize().width) / 2, //width
            (dim.height - frame.getSize().height) / 2 //height
        );
    }
}
