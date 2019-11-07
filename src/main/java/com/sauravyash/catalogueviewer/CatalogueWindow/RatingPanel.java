/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import com.sauravyash.catalogueviewer.userdatabase.NoRatingsException;
import com.sauravyash.catalogueviewer.userdatabase.Rating;
import com.sauravyash.catalogueviewer.userdatabase.RatingDB;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.simple.parser.ParseException;

/**
 * The panel used for rating.
 * 
 * Users can select Thumbs up or Thumbs Down and the choice is stored 
 * in a database.
 * @author Yash
 */
public class RatingPanel extends JPanel {
    /**
     * The location of the empty thumbs up image.
     */
    private File ThumbsUpImage;
    
    /**
     * The location of the active thumbs up image.
     */
    private File ActiveThumbsUpImage;
    
    /**
     * The location of the empty thumbs down image.
     */
    private File ThumbsDownImage;
    
    /**
     * The location of the active thumbs down image.
     */
    private File ActiveThumbsDownImage;
    
    /**
     * The current rating.
     * Rating: Thumbs Down = 1, Thumbs Up = 2, No Rating = 0
     */
    private int rating;
    
    /**
     * The item that is being rated.
     */
    private CatalogueItem item;
    
    /**
     * The label that holds the Thumbs Up Image.
     */
    private JLabel ThumbsUpLabel;
    
    /**
     * The label that holds the Thumbs Down Image.
     */
    private JLabel ThumbsDownLabel;
    
    /**
     * The Constructor for the rating panel.
     * @param item The item that is being rated
     */
    public RatingPanel(CatalogueItem item) {
        this.ActiveThumbsDownImage = new File(CatalogueViewer.ResourcesFolder, "active-thumbs-down.png");
        this.ThumbsDownImage = new File(CatalogueViewer.ResourcesFolder, "thumbs-down.png");
        this.ActiveThumbsUpImage = new File(CatalogueViewer.ResourcesFolder, "active-thumbs-up.png");
        this.ThumbsUpImage = new File(CatalogueViewer.ResourcesFolder, "thumbs-up.png");
        setLayout(new GridLayout(1, 2));
        setSize(100,50);
        this.item = item;
        try {
            rating = RatingDB.GetUserRating(item.title, CatalogueViewer.Catalogue.Title, CatalogueViewer.UserData.Username);
        } catch(NoRatingsException ex) {
            rating = 0;
        } catch (IOException | ParseException ex) {
            Logger.getLogger(RatingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        InitButtons();
    }
    
    /**
     * Initializes the the thumbs buttons.
     */
    private void InitButtons() {
        ThumbsUpLabel = new JLabel();
        ThumbsDownLabel = new JLabel();
        
        ThumbsUpLabel.setSize(50,50);
        ThumbsDownLabel.setSize(50,50);
        
        LoadThumbImages();
        
        ThumbsUpLabel.addMouseListener(new MouseAdapter() {   
            @Override
            public void mouseEntered(MouseEvent me) {
                Utilities.LoadImageInJLabel(ThumbsUpLabel, ActiveThumbsUpImage);
                Utilities.LoadImageInJLabel(ThumbsDownLabel, ThumbsDownImage);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                LoadThumbImages();
            }

            @Override
            public void mouseClicked(MouseEvent me) {
                rating = 2;
                try {
                    RatingDB.AddRating(new Rating(item, 2));
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(RatingPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                LoadThumbImages();
            }
        });
        
        ThumbsDownLabel.addMouseListener(new MouseAdapter() {   
            @Override
            public void mouseEntered(MouseEvent me) {
                Utilities.LoadImageInJLabel(ThumbsUpLabel, ThumbsUpImage);
                Utilities.LoadImageInJLabel(ThumbsDownLabel, ActiveThumbsDownImage);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                LoadThumbImages();
            }

            @Override
            public void mouseClicked(MouseEvent me) {
                rating = 1;
                try {
                    RatingDB.AddRating(new Rating(item, 1));
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(RatingPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                LoadThumbImages();
            }
        });
        
        add(ThumbsUpLabel);
        add(ThumbsDownLabel);
    }
    
    /**
     * Reloads the Labels with the appropriate image (active/empty).
     */
    private void LoadThumbImages() {
        Utilities.LoadImageInJLabel(ThumbsUpLabel, rating == 2 ? ActiveThumbsUpImage : ThumbsUpImage);
        Utilities.LoadImageInJLabel(ThumbsDownLabel, rating == 1 ? ActiveThumbsDownImage : ThumbsDownImage);
    }
}
