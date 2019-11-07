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
 *
 * @author Yash
 */
public class Rating {

    /**
     *
     */
    public String CatalogueTitle;

    /**
     *
     */
    public String ItemTitle;

    /**
     *
     */
    public int RatingNumber;

    /**
     *
     */
    public String UserName;
    
    // used for creating ratings made by the user for 
    // the first time

    /**
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
    
    // used for manual creations

    /**
     *
     * @param catalogue_title
     * @param item_title
     * @param rating
     * @param username
     */
    protected Rating(String catalogue_title, String item_title, int rating, String username){
        RatingNumber = rating;
        ItemTitle = item_title;
        CatalogueTitle = catalogue_title;
        UserName = username;
    }
    
    // used for converting the abstract JSON object ratings in 
    // the rating database to the normal rating object

    /**
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
     *
     * @return
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
