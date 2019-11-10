/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

/**
 * The Abstract object of the Shopping Cart Item.
 * It holds the item type and number in cart.
 * @author Yash
 */
public class ShoppingCartItem {

    /**
     * The represented item.
     */
    public CatalogueItem Item;

    /**
     * The number of the item in the cart.
     */
    public int NumberOfItems = 1;
    
    /**
     * Constructor for the object
     * @param item .
     */
    ShoppingCartItem(CatalogueItem item) {
        this.Item = item;
    }
    
    /**
     * Increases the number of the item in the cart.
     */
    public void IncreaseItemCount() {
        NumberOfItems += 1;
    }
    
    /**
     * sets the number of the item in the cart to i.
     * @param i
     */
    public void IncreaseItemCount(int i) {
        NumberOfItems += i;
    }
    
    /**
     * Decreases the number of the item in the cart.
     */
    public void DecreaseItemCount() {
        NumberOfItems -= 1;
        if (NumberOfItems < 1) {
            NumberOfItems = 1;
        }
    }
    
    /**
     * This retrieves an instance of the menu item as a swing component.
     * @return
     */
    public JMenuItem getMenuItem() {
        JMenuItem menuitem = new JMenuItem(Item.title + " ($" + Item.price + ") [x" + NumberOfItems + "]");
        menuitem.setToolTipText("Click here to delete.");
        try {
            BufferedImage img;
            img = ImageIO.read(Item.image);
            Image ScaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(ScaledImg);
            menuitem.setIcon(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return menuitem;
    }
}
