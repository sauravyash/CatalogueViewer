/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

import org.json.simple.JSONObject;

/**
 * The abstract User object.
 * @author Yash
 */
public class User {

    /**
     * The first name of the user.
     */
    public String FirstName;

    /**
     * The last name of the user.
     */
    public String LastName;

    /**
     * The user name of the user.
     * The unique identifier of the user.
     */
    public String Username;

    /**
     * The email provided by the user for contact.
     */
    public String Email;
    
    /**
     * Constructs an user object from a JSON Object.
     * @param obj The JSON object to parse
     */
    public User(Object obj){
        JSONObject UserData = (JSONObject) obj;
        FirstName = UserData.get("FirstName").toString();
        LastName = UserData.get("LastName").toString();
        Username = UserData.get("UserName").toString();
        Email = UserData.get("Email").toString();
        
    }
    
    /**
     * Constructs an User.
     * Used by the register panel of the login panel to create a new user. Also 
     * used for creating a user manually for debugging purposes.
     * Password not stored for security reasons (and not required anywhere).
     * 
     * @param fn The First Name of the User
     * @param ln The Last Name of the User
     * @param username The username of the User
     * @param email  The Email of the User
     */
    public User(String fn, String ln,String username,String email){
        FirstName = fn;
        LastName = ln;
        Username = username;
        Email = email;
    }
    
    /**
     * Retrieves the full name of the user
     * @return the full name as a string.
     */
    public String FullName() {
        return FirstName + " " + LastName;
    }
}
