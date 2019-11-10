/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

/**
 * The custom exception if there is already another user detected with the same 
 * username in the database.
 * @author Yash
 */
public class UserAlreadyExistsException extends Exception {
    /**
     * Constructs an exception identical on the Exception class.
     */
    UserAlreadyExistsException(){
        super();
    }
    
    /**
     * Constructs an exception identical on the Exception class but with a 
     * message.
     */
    UserAlreadyExistsException(String msg){
        super(msg);
    }
}
