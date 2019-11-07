/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

/**
 *
 * @author Yash
 */
public class UserAlreadyExistsException extends Exception {
    UserAlreadyExistsException(){
        super();
    }
    
    UserAlreadyExistsException(String msg){
        super(msg);
    }
}
