/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

import com.sauravyash.catalogueviewer.CatalogueViewer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Yash
 */
public class RatingDB {

    /**
     *
     */
    public static Path RatingDBFile = Paths.get(CatalogueViewer.WorkingDIR.toString(), "RatingDB.json");
    
    /**
     *
     */
    public static void InitializeDB() {
        File dbFile = new File(RatingDBFile.toString());
        boolean doesfileExists = dbFile.exists();
        if (doesfileExists) return;
        
        //create file
        try {
            Files.createFile(RatingDBFile);
        } catch(IOException e) {
            System.out.println("Database Initialization Error: " + e);
        }
        
        // write empty json array to file
        try (FileOutputStream outputStream = new FileOutputStream(RatingDBFile.toString());) {
            String arrayJSON = "[]";
            
            byte[] strToBytes = arrayJSON.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch(IOException e) {
            System.out.println("Database Initialization File Writing Error : " + e);
        }
    }
    
    /**
     *
     * @param newRating
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public static void AddRating(Rating newRating) throws FileNotFoundException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject JSONrating = newRating.toJSONObject();
        
        try (FileReader reader = new FileReader(RatingDBFile.toString())) {
            //Read JSON file
            JSONArray DB = (JSONArray) jsonParser.parse(reader);
            
            Predicate<JSONObject> duplicate = rating -> (
                newRating.ItemTitle.equals(rating.get("ItemTitle").toString()) 
                && newRating.UserName.equals(rating.get("UserName").toString())
            );
            
            DB.removeIf(duplicate);
            
//            DB.forEach(ratingObject -> {
//                JSONObject rating = (JSONObject) ratingObject;
//                if (newRating.ItemTitle.equals(rating.get("ItemTitle").toString()) 
//                        && newRating.UserName.equals(rating.get("UserName").toString())) {
//                    DB.remove(ratingObject);
//                    
//                }
//                
//            });
            
            // add user to list
            DB.add(JSONrating);
            
            // write to JSON file
            try(PrintWriter prw = new PrintWriter(RatingDBFile.toString()))
            {
                prw.println(DB.toString());          
                prw.close();
            } catch (FileNotFoundException e) {
                InitializeDB();
                System.out.println("File Not Found Exception: " + e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Exception: " + e);
            InitializeDB();
            throw new FileNotFoundException("Could Not Add User: Database Not Initialised");
        } catch (IOException e) {
            System.out.println("IO Exception: " + e);
            throw new IOException("Could Not Add User: ");
        } catch (ParseException e) {
            System.out.println("Parse Exception (File Corrupted???): " + e);
            throw new ParseException(e.getErrorType());
        }
    }
    
    /**
     *
     * @param ItemTitle
     * @param CatalogueTitle
     * @return
     * @throws NoRatingsException
     * @throws ParseException
     * @throws IOException
     */
    public static int GetPercentRating(String ItemTitle, String CatalogueTitle) throws NoRatingsException, ParseException, IOException {
        ArrayList<Rating> ratings = GetRatings(ItemTitle, CatalogueTitle);
        
        if (ratings.size() < 1) throw new NoRatingsException();
        int total = 0;
        
        total = ratings.stream().filter((rating) -> (rating.RatingNumber == 2)).map((_item) -> 1).reduce(total, Integer::sum);
        
        int percent = (total * 100) / ratings.size();
        return percent;
    }
    
    /**
     *
     * @param ItemTitle
     * @param CatalogueTitle
     * @param UserName
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws NoRatingsException
     */
    public static int GetUserRating(String ItemTitle, String CatalogueTitle, String UserName) throws IOException, ParseException, NoRatingsException {
        ArrayList<Rating> ratings = GetRatings(ItemTitle, CatalogueTitle);
        
        for(Rating rating: ratings){
            if (rating.ItemTitle.equals(ItemTitle)
              && rating.CatalogueTitle.equals(CatalogueTitle)
              && rating.UserName.equals(UserName)) 
            {
                System.out.println("User Rating Found");
                return rating.RatingNumber;
            }  
        }
        // if rating isn't found
        throw new NoRatingsException();
    }
    
    /**
     *
     * @param ItemTitle
     * @param CatalogueTitle
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<Rating> GetRatings(String ItemTitle, String CatalogueTitle) throws IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        ArrayList<Rating> ratings = new ArrayList();
        try (FileReader reader = new FileReader(RatingDBFile.toString())) {
            // Read JSON file
            JSONArray DB = (JSONArray) jsonParser.parse(reader);
            
            DB.forEach((ratingObject) -> {
                JSONObject rating = (JSONObject) ratingObject;
                if (rating.get("ItemTitle").toString().equals(ItemTitle) 
                        && rating.get("CatalogueTitle").toString().equals(CatalogueTitle))
                {
                    ratings.add(new Rating(rating));
                }    
            });
        } catch (IOException e) {
            System.out.println("IO Exception: " + e);
            throw new IOException("IO Exception: " + e);
        } catch (ParseException e) {
            System.out.println("Parse Exception (File Corrupted???): " + e);
            throw new ParseException(e.getErrorType());
        }
        return ratings;
    }
}
