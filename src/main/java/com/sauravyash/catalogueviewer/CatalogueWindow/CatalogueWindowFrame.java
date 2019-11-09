/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import com.sauravyash.catalogueviewer.AudioPlayer;
import com.sauravyash.catalogueviewer.CatalogueViewer; 
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Yash
 */
public class CatalogueWindowFrame extends javax.swing.JFrame {

    private final CardLayout cardlayout;
    public static AudioPlayer menuplayer;
    
    /**
     * Instantiates a new Catalogue Window Frame
     */
    public CatalogueWindowFrame() {
        this.cardlayout = new CardLayout();
        CatalogueWindowFrame.menuplayer = new AudioPlayer(CatalogueViewer.Catalogue.audioFile);
        Init();
    }
    
/**
     * The layout manager for the frame.
     *
     * Uses a card system where only one component (JPanel) is shown at a time.
     * Used to alternate between
     */
        /**
     * Initializes and loads the content of the frame.
     */
    private void Init(){
        menuplayer.start();
        initComponents();
        this.setBackground(CatalogueViewer.Catalogue.Theme_Colour);
        this.pack();
        LoadMainMenu();
        setLayout(cardlayout);
    }
    
    /**
     * Destroys all active instances of CatalogueWindowFrame
     */
    public static void DestroySelf(){
        for (Window w: CatalogueWindowFrame.getWindows()){
            w.dispose();
        }
    }
    
    /**
     * Loads the Main Menu Panel as the first card in the Layout.
     */
    private void LoadMainMenu() {
        JPanel MainMenu = new JPanel();
        ItemListPanel itemListPanel = new ItemListPanel();
        JPanel NavBarPanel= new NavigationBarPanel();
        
        JComboBox sortingSelector = new JComboBox<>(itemListPanel.SortBy);
        sortingSelector.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("it beed runned");
                itemListPanel.Sort(sortingSelector.getSelectedItem().toString());
            } 
        });
        
        MainMenu.setLayout(new GridBagLayout());
        
        GridBagConstraints NavBarPanelGridBagConstraints;
        NavBarPanelGridBagConstraints = new GridBagConstraints();
        NavBarPanelGridBagConstraints.gridx = 0;
        NavBarPanelGridBagConstraints.gridy = 0;
        NavBarPanelGridBagConstraints.fill = GridBagConstraints.BOTH;
        NavBarPanelGridBagConstraints.ipadx = 48;
        NavBarPanelGridBagConstraints.anchor = GridBagConstraints.PAGE_START;
        MainMenu.add(NavBarPanel, NavBarPanelGridBagConstraints);
        
        GridBagConstraints SortingSelectorGridBagConstraints;
        SortingSelectorGridBagConstraints = new GridBagConstraints();
        SortingSelectorGridBagConstraints.gridx = 0;
        SortingSelectorGridBagConstraints.gridy = 1;
        SortingSelectorGridBagConstraints.ipadx = 36;
        SortingSelectorGridBagConstraints.insets = new Insets(0, 0, 0, 48);
//        SortingSelectorGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        SortingSelectorGridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        MainMenu.add(sortingSelector, SortingSelectorGridBagConstraints);
        
        
        GridBagConstraints ItemListPanelGridBagConstraints;
        ItemListPanelGridBagConstraints = new GridBagConstraints();
        ItemListPanelGridBagConstraints.gridx = 0;
        ItemListPanelGridBagConstraints.gridy = 2;
        ItemListPanelGridBagConstraints.fill = GridBagConstraints.BOTH;
        ItemListPanelGridBagConstraints.weightx = 0.5;
        ItemListPanelGridBagConstraints.weighty = 1;
        MainMenu.add(itemListPanel, ItemListPanelGridBagConstraints);
        
        add(MainMenu);
        setExtendedState( this.getExtendedState() | JFrame.MAXIMIZED_BOTH );
    }
    
    /**
     * This creates a full screen view for viewing an item, 
     * including the ability to rate.
     * 
     * Basically an instance of RatingPanel
     * @param item The item to display
     */
    public void LoadItemCard(CatalogueItem item) {
        menuplayer.togglePause();
        ItemCardPanel itemcard = new ItemCardPanel(item);
        itemcard.setName("ItemCard");
        add(itemcard, "ItemCard");
        cardlayout.next(this.getContentPane());
        System.out.println("Card Loaded"); 
        
    }
    
    /**
     * Removes any item cards and then shows the main menu.
     */
    public static void ShowMainMenu() {
        // destroy all Item Card Panels
        try {
            for (Frame f: CatalogueWindowFrame.getFrames()) {
            CatalogueWindowFrame cwf = (CatalogueWindowFrame) f;
            cwf.cardlayout.next(cwf.getContentPane());
            for (Component c : f.getComponents()){
                try {
                    ItemCardPanel comp = (ItemCardPanel) c;
                    if (comp.getName() == "ItemCard") {
                        f.remove(comp);
                    } 
                } 
                //ignore if frame isn't a Item Card Panel
                catch (Exception e){ }
            } 
        }
        } catch (java.lang.ClassCastException e) {
            //ignore if frame isn't a Catalogue Window Frame
        } 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
