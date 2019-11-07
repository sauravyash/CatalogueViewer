/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;
import com.sauravyash.catalogueviewer.CatalogueViewer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author Yash
 */
public class UserDatabase {

    /**
     *
     */
    public static Path UserDBFile = Paths.get(CatalogueViewer.WorkingDIR.toString(), "UserDB.json");
    
    /**
     *
     */
    public static void InitializeDB() {
        File dbFile = new File(UserDBFile.toString());
        boolean doesfileExists = dbFile.exists();
        if (doesfileExists) return;
        
        //create file
        try {
            Files.createDirectory(CatalogueViewer.WorkingDIR);
            Files.createFile(UserDBFile);   
        } catch(IOException e) {
            System.out.println("Database Initialization Error: " + e);
        }
        
        // write empty json array to file
        try (FileOutputStream outputStream = new FileOutputStream(UserDBFile.toString());) {
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
     * @param fn
     * @param ln
     * @param email
     * @param username
     * @param pwd
     * @throws UserAlreadyExistsException
     * @throws Exception
     */
    public static void AddUser(String fn, String ln, String email, String username, String pwd) throws UserAlreadyExistsException, Exception {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONObject user = new JSONObject();
        
        user.put("FirstName", fn);
        user.put("LastName", ln);
        user.put("Email", email);
        user.put("UserName", username);
        user.put("Password", pwd);
        
        try (FileReader reader = new FileReader(UserDBFile.toString())) {
            //Read JSON file
            JSONArray DB = (JSONArray) jsonParser.parse(reader);
            
            for (Object UserDetails : DB) {
                JSONObject UserCreds = (JSONObject) UserDetails;
                if(UserCreds.get("UserName").equals(username)) {
                    throw new UserAlreadyExistsException();
                }
            }
            
            // add user to list
            DB.add(user);
            
            // write to JSON file
            try(PrintWriter prw = new PrintWriter(UserDBFile.toString()))
            {
                prw.println(DB.toString());          
                prw.close();
            } catch (FileNotFoundException e) {
                InitializeDB();
                System.out.println("File Not Found Exception: " + e);
            }
            
            // System.out.println(DB);
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
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException("Username Already Exists. Cannot Create User...");
        }
    }
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static boolean IsUserValid(String username, String password) {
        boolean isValid = false;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(UserDBFile.toString())) {
            // Read JSON file
            JSONArray DB = (JSONArray) jsonParser.parse(reader);
            
            for(Object user : DB) {
                JSONObject UserData = (JSONObject) user;
                System.out.println("userDB: " + UserData.get("UserName") + " Given: " + username);
                if (UserData.get("UserName").equals(username)) {
                    System.out.println("username found");
                    if (UserData.get("Password").equals(password)) {
                        isValid = true;
                        System.out.println("password found");
                        CatalogueViewer.UserData = new User(UserData);
                        break;
                    }
                        
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Exception: " + e);
            InitializeDB();
        } catch (IOException e) {
            System.out.println("IO Exception: " + e);
        } catch (ParseException e) {
            System.out.println("Parse Exception (File Corrupted???): " + e);
        }
       return isValid;
    }
    
    /**
     *
     */
    protected static void RemoveUser(){
        
    }
}
