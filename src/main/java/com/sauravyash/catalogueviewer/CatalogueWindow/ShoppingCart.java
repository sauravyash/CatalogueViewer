/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * The Shopping Cart Component with the drop down menu.
 * @author Yash
 */
public class ShoppingCart {
    /**
     * The menu bar component used.
     */
    private final JMenuBar CartMenuBar;
    
    /**
     * The menu component.
     */
    private final JMenu menu;
    
    /**
     * The list of items in the cart.
     */
    private final ArrayList<ShoppingCartItem> CartItems;
    
    /**
     * The constructor for the Cart
     */
    public ShoppingCart(){
        CartItems = new ArrayList();
        CartMenuBar = new JMenuBar();
        menu = new JMenu("Cart");
        CartMenuBar.add(menu);
        LoadItems();
    }
    
    /**
     * Adds an item to the cart.
     * @param item The item to be added
     */
    public void AddItem(CatalogueItem item) {
        for (ShoppingCartItem cartitem : CartItems) {
            if (cartitem.Item.ID == item.ID) {
                cartitem.IncreaseItemCount();
                LoadItems();
                return;
            }
        }
        CartItems.add(new ShoppingCartItem(item));
        LoadItems();
    }
    
    /**
     * Adds the menu bar to the specified panel.
     * @param panel THe specified panel
     */
    public void AttachTo(JPanel panel){
        panel.add(CartMenuBar);
    }
    
    /**
     * Removes the item from the shopping cart.
     * @param ID 
     */
    private void RemoveItem(String ID) {
        CartItems.removeIf(item -> (item.Item.ID.equals(ID)));
        LoadItems();
    }

    /**
     * Reloads all of the shopping cart items into the menu bar 
     * with styling.
     */
    private void LoadItems() {
        menu.removeAll();
        
        JMenuItem spacer = new JMenuItem(" ");
        spacer.setEnabled(false);
        
        JMenuItem TitleMenuItem = new JMenuItem("Cart");
        TitleMenuItem.setEnabled(false);
        menu.add(TitleMenuItem);
        
        menu.add(spacer);
        menu.add(spacer);
        
        if (CartItems.isEmpty()){
            JMenuItem EmptyMenuItem = new JMenuItem("There are no items in the cart.");
            menu.add(EmptyMenuItem);
        } else {
            CartItems.forEach((ShoppingCartItem CartItem) -> {
                JMenuItem mb = CartItem.getMenuItem();
                mb.addActionListener((ActionEvent ae) -> {
                    RemoveItem(CartItem.Item.ID);
                });
                mb.setToolTipText("Click here to delete.");
                menu.add(mb);
            });
        }
        
        menu.add(spacer);
        
        float total = 0f;
        total = CartItems.stream().map((cartitem) -> cartitem.Item.price * cartitem.NumberOfItems).reduce(total, (accumulator, _item) -> accumulator + _item);
        
        JMenuItem TotalMenuItem = new JMenuItem("Total: $" + String.format("%.2f", total).trim());
        TotalMenuItem.setEnabled(false);
        menu.add(TotalMenuItem);
        
        
    }
    
}
