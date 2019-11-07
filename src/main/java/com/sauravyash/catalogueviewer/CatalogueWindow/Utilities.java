/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Yash
 */
public class Utilities {

    /**
     *
     * @param label
     * @param ImgLocation
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
     *
     * @param label
     * @param ImgLocation
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
     *
     * @param button
     * @param ImgLocation
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
     *
     * @param runnable
     * @param delay
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
}
