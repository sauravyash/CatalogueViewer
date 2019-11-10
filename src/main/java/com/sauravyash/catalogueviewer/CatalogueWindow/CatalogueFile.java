/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.CatalogueWindow;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Yash
 */
public class CatalogueFile {

    /**
     * The title of the catalogue.
     */
    public String Title;

    /**
     * The logo/icon image of the catalogue. (Displayed at the top left of the menu page)
     */
    public File Icon;

    /**
     * Not Implemented Yet
     * This will be the image of the catalogue displayed at the top of the main menu.
     */
    public File Banner;

    /**
     * The primary color of the catalogue.
     */
    public Color Theme_Colour;
    
    /**
     * The primary color of the catalogue.
     */
    public File audio;
    
    /**
     * The items in the catalogue.
     * They populate the main menu
     */
    public ArrayList<CatalogueItem> items;
    
    /**
     * This constructor is used to instantiate directly from the manifest file.
     * @param manifestJSON The location of the manifest to process.
     * @throws IOException Thrown if file of image is not found
     * @throws ParseException Thrown if file of image is corrupted/not readable
     */
    public CatalogueFile(File manifestJSON) throws IOException, ParseException{
        items = new ArrayList<CatalogueItem>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(manifestJSON.toString())) {
            // Read JSON file
            JSONObject manifest = (JSONObject) jsonParser.parse(reader);
            Title = manifest.get("title").toString();
            Icon = new File(CatalogueViewer.CataloguePath, manifest.get("icon").toString());
            Banner = new File(CatalogueViewer.CataloguePath, manifest.get("banner").toString());
            
            Theme_Colour = HexStringToColor(manifest.get("theme_color").toString());
            JSONArray itemlist = (JSONArray) manifest.get("items");
            for (Object item: itemlist) {
//                System.out.println("Item: " + item);
                items.add(new CatalogueItem(item));
            }
            
            try{
                audio = new File(CatalogueViewer.CataloguePath, manifest.get("audio").toString());
            }
            catch(NullPointerException e) {/*ignore */ }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Converts a Hex String to a Swing Color Object.
     * Since the color system in swing is RGB, this function returns a 
     * swing compatible Color object.
     * @param hex The HEX string to decode (Format: "#000000")
     * @return Color The swing color object
     */
    private Color HexStringToColor(String hex){
        String r = "0x" + hex.substring(1, 3);
        String g = "0x" + hex.substring(3, 5);
        String b = "0x" + hex.substring(5, 7);
        return new Color(Integer.decode(r), Integer.decode(g), Integer.decode(b));
    }
}
