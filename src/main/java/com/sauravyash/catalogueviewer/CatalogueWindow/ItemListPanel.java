/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * This is the viewer for the list of items with pagination for navigation.
 * @author Yash
 */
public class ItemListPanel extends javax.swing.JPanel {
    ArrayList<CatalogueItem> items = CatalogueViewer.Catalogue.items; 
    private int Page = 1;
    private int NumberOfPages;
    public String[] SortBy;
    
    
    /**
     * Constructor for the Item List JPanel
     */
    public ItemListPanel() {
        this.SortBy = new String[]{"Choose An Option: ", "Name (Ascending)", "Name (Descending)", "Price (Ascending)", "Price (Descending)"};
        initComponents();
        LoadList();
        initPagination();
    }
    
    /**
     * Creates the pagination components.
     */
    private void initPagination(){
        NumberOfPages = (int) Math.ceil((double) (items.size()) / 8.0);
        JButton BackButton = new JButton("<");
        JButton ForwardButton = new JButton(">");
        
        BackButton.addActionListener((ActionEvent e) -> {
            PreviousPage();
        });
        
        ForwardButton.addActionListener((ActionEvent e) -> {
            NextPage();
        });
        
        PageButtons.add(BackButton);
        for (int i = 1; i < (NumberOfPages + 1); i++){
            JButton b = new JButton((String.valueOf(i)));
            
            // variable must be final to be set in inner class 
            // if i is used, error: "local variables referenced from an inner class must be final or effectively final"
            final int j = i;
            b.addActionListener((ActionEvent e) -> {
                SetPage(j);
            });
            // b.setBorder(BorderFactory.createLineBorder(Color.red));
            PageButtons.add(b);
        }
        PageButtons.add(ForwardButton);
        
    }
    
    /**
     * Loads all of the items into the panel.
     */
    private void LoadList() {
        System.out.println("Page: " + Page);
        // Destroy all panels in ItemDisplayPanel
        for (Component c : ItemDisplayPanel.getComponents()){
            ItemDisplayPanel.remove(c);
        }
        // Load new images into the ItemDisplayPanel
        int low = 8 * (Page - 1);
        int high = (8 * (Page - 1)) + 8;
        if (high >= items.size()) high = items.size();
        System.out.println("items: " + low + " to " + high);
        for (CatalogueItem item : items.subList(low, high)){
            ItemPanel itempanel = new ItemPanel(item);
            ItemDisplayPanel.add(itempanel);
        }
        revalidate();
        repaint();
    }
    
    /**
     * Navigates the pagination to the previous page (if there is one).
     */
    public void PreviousPage(){
        if (Page > 1) Page--;
        LoadList();
    }
    
    /**
     * Navigates the pagination to the next page (if there is one).
     */
    public void NextPage() {
        if (Page < NumberOfPages) Page++;
        LoadList();
    }
    
    /**
     * Navigates the pagination to the specified page (if there is one).
     * @param num The page to navigate
     * @throws IllegalArgumentException Thrown if the page doesn't exist
     */
    public void SetPage(int num) throws IllegalArgumentException{
        if (Page > 0 && Page <= NumberOfPages) Page = num;
        else throw new IllegalArgumentException("Number Out Of Bounds");
        
        LoadList();
    }
    
    /**
     * Sorts the item array and then displays them.
     * 
     * String can be any of the cases specified below
     * @param sortby 
     */
    public void Sort(String sortby){
        System.out.println(sortby);
        switch(sortby.trim()){
            case "Name (Ascending)":
                items.sort((o1, o2) -> {
                    return o1.title.compareToIgnoreCase(o2.title);
                });
                break;
            case "Name (Descending)":
                items.sort((o1, o2) -> {
                    return o2.title.compareToIgnoreCase(o1.title);
                });
                break;
            case "Price (Ascending)":
                items.sort((o1, o2) -> {
                    if (o1.price > o2.price) return 1;
                    else if (o1.price < o2.price) return -1;
                    else return 0;
                });
                break;
            case "Price (Descending)":
                items.sort((o1, o2) -> {
                    if (o1.price < o2.price) return 1;
                    else if (o1.price > o2.price) return -1;
                    else return 0;
                });
                break;
            default: 
                System.out.println("You have sorted default style boiii!");
                items.sort((o1, o2) -> {
                    return o1.ID.compareToIgnoreCase(o2.ID);
                });
                break;
        }
        SetPage(1);
        LoadList(); 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ItemDisplayPanel = new javax.swing.JPanel();
        PageButtons = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        ItemDisplayPanel.setLayout(new java.awt.GridLayout(2, 4, 1, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.8;
        add(ItemDisplayPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.2;
        add(PageButtons, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ItemDisplayPanel;
    private javax.swing.JPanel PageButtons;
    // End of variables declaration//GEN-END:variables
}
