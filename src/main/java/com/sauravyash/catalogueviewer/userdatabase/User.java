/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

import org.json.simple.JSONObject;

/**
 *
 * @author Yash
 */
public class User {

    /**
     *
     */
    public String FirstName;

    /**
     *
     */
    public String LastName;

    /**
     *
     */
    public String Username;

    /**
     *
     */
    public String Email;
    
    /**
     *
     * @param obj
     */
    public User(Object obj){
        JSONObject UserData = (JSONObject) obj;
        FirstName = UserData.get("FirstName").toString();
        LastName = UserData.get("LastName").toString();
        Username = UserData.get("UserName").toString();
        Email = UserData.get("Email").toString();
        
    }
    
    /**
     *
     * @param fn
     * @param ln
     * @param usrname
     * @param email
     */
    public User(String fn, String ln,String usrname,String email){
        FirstName = fn;
        LastName = ln;
        Username = usrname;
        Email = email;
    }
    
    /**
     *
     * @return
     */
    public String FullName() {
        return FirstName + " " + LastName;
    }
}
