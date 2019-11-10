/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.userdatabase;

/**
 * The custom exception if there isn't any records of a rating.
 * @author Yash
 */
public class NoRatingsException extends Exception {

    /**
     * Constructs an exception identical on the Exception class.
     */
    public NoRatingsException() {
        super();
    }
}
