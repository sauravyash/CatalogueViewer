/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import java.io.File;
import java.util.UUID;
import org.json.simple.JSONObject;

/**
 * This is an abstract object that encapsulates all of the 
 * data for an item in the manifest file as a Java object.
 * @author Yash
 */
public class CatalogueItem {
    // The ID is only permanent for the current application session 
    // (the ID would need to be inside the manifest file for impermeability)

    /**
     * This is a temporary identifier for the session as 
     * there isn't any ID in the manifest file.
     */
    public String ID;

    /**
     * This is the title of the item.
     * It is used in conjunction with the Catalogue's title as a permanent ID
     */
    public String title;

    /**
     * This is the subtitle of the item.
     * It supplies the item with additional critical information for the user to  
     * identify the item.
     */
    public String subtitle;

    /**
     * This is the description of the item.
     * This is a long string that provides details of the item.
     */
    public String description;

    /**
     * This is the price of the item.
     * It is a number representing the item's cost.
     */
    public float price;

    /**
     * This is a visual aid for the user to identify the item.
     */
    public File image;

    /**
     * This is an auditory aid for the user to identify the item.
     */
    public File audio;
    
    /**
     * This Constructor used directly with the manifest parser in order
     * to quickly instantiate an item from the JSON file.
     * @param rawItem the information used to instantiate the item (technically a JSONObject)
     */
    public CatalogueItem(Object rawItem){ 
        ID = UUID.randomUUID().toString();
        
        JSONObject item = (JSONObject) rawItem;
        
        title = item.get("title").toString();
        subtitle = item.get("subtitle").toString();
        description = item.get("description").toString();
        price = Float.parseFloat(item.get("price").toString());
       
        try{
            image = new File(CatalogueViewer.CataloguePath, item.get("image").toString()); 
        }
        catch(NullPointerException e) {/*ignore */ }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            audio = new File(CatalogueViewer.CataloguePath, item.get("audio").toString());
        }
        catch(NullPointerException e) {/*ignore */ }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
