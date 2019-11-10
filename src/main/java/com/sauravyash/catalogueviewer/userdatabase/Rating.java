/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import com.sauravyash.catalogueviewer.CatalogueWindow.CatalogueItem;
import org.json.simple.JSONObject;

/**
 * The abstract rating object.
 * 
 * @author Yash
 */
public class Rating {

    /**
     * The name of the catalogue from the rating
     */
    public String CatalogueTitle;

    /**
     * The name of the item being rating
     */
    public String ItemTitle;

    /**
     * The rating of the item
     * 0 = none, 1 = thumbs down, 2 = thumbs up
     */
    public int RatingNumber;

    /**
     * the unique identifier for the user to ensure duplicate ratings don't form
     */
    public String UserName;
    
    /**
     * Constructs a rating.
     * This constructor is mainly used by the program to add a new rating by the
     * user.
     * 
     * @param item
     * @param rating
     */
    public Rating(CatalogueItem item, int rating){
        RatingNumber = rating;
        ItemTitle = item.title;
        CatalogueTitle = CatalogueViewer.Catalogue.Title;
        UserName = CatalogueViewer.UserData.Username;
    }

    /**
     * Constructs a rating.
     * Creates a rating manually for debugging purposes.
     * 
     * @param catalogue_title
     * @param item_title
     * @param rating
     * @param username
     */
    protected Rating(
            String catalogue_title, 
            String item_title, 
            int rating, 
            String username
    ){
        RatingNumber = rating;
        ItemTitle = item_title;
        CatalogueTitle = catalogue_title;
        UserName = username;
    }
    
    /**
     * Converts the abstract JSON object ratings in the rating database to the 
     * normal rating object.
     * This constructor is mainly used by the database manager class.
     * 
     * @param rating 
     */
    protected Rating(JSONObject rating) {
        RatingNumber = Integer.parseInt(rating.get("RatingNumber").toString());
        ItemTitle = rating.get("ItemTitle").toString();
        CatalogueTitle = rating.get("CatalogueTitle").toString();
        UserName = rating.get("UserName").toString();
    }
    
    /**
     * Converts a rating to a JSON compatible object for file storage.
     * @return the converted JSON object
     */
    public JSONObject toJSONObject() {
        JSONObject ratingObject = new JSONObject();
        
        ratingObject.put("CatalogueTitle", CatalogueTitle);
        ratingObject.put("ItemTitle", ItemTitle);
        ratingObject.put("RatingNumber", RatingNumber);
        ratingObject.put("UserName", UserName);
        
        return ratingObject;
    }
}
