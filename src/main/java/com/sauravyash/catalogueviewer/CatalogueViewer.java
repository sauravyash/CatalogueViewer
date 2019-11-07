/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer;

import com.sauravyash.catalogueviewer.CatalogueWindow.CatalogueFile; 
import com.sauravyash.catalogueviewer.CatalogueWindow.CatalogueWindowFrame;
import com.sauravyash.catalogueviewer.ZipSelector.CatalogueSelectorWindowFrame;
import com.sauravyash.catalogueviewer.userdatabase.UserDatabase;
import com.sauravyash.catalogueviewer.LoginPanel.LoginWindowFrame;
import com.sauravyash.catalogueviewer.userdatabase.RatingDB;
import com.sauravyash.catalogueviewer.userdatabase.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import org.json.simple.parser.ParseException;

/**
 * This Class is the main thread that runs the entire program. It
 * manages all of the windows and global variables that are used dynamically
 * throughout the program (resource folder, working directory etc.).
 * 
 * @author Yash
 */
public class CatalogueViewer {

    /**
     * The relative directory for all of the application data to be stored.
     * It differs between operating systems.
     * 
     * Windows: C:\Users\${current_user_name}
     * Linux & Mac: ~/ (User Directory)
     */
    public static Path WorkingDIR = Paths.get(System.getProperty("user.home"), ".CatalogueViewer");

    /**
     * The details of the user currently logged in. 
     */
    public static User UserData;

    /**
     * The entire catalogue in the abstract CatalogueFile format.
     */
    public static CatalogueFile Catalogue;

    /**
     * The path of the folder with the images and manifest file of the catalogue.
     */
    public static File CataloguePath;

    /**
     * The relative path to the folder with all of the application's resources.
     */
    public static File ResourcesFolder = new File(System.getProperty("user.dir"), "src/main/resources/");
    
    /**
     * The main process for the entire application.
     * @param args (not used)
     */
    public static void main(String[] args){
        System.out.println("Running...");
        UserDatabase.InitializeDB();
        RatingDB.InitializeDB();
        
        /* Create and display the form */
        OpenLoginWindow();
        
        // code to skip the catalogue opening
//        try {
//            CataloguePath = new File("/Users/Yash/.CatalogueViewer/ApolloCatalogue/");
//            Catalogue = new CatalogueFile(new File("/Users/Yash/.CatalogueViewer/ApolloCatalogue/manifest.json"));
//            UserData = new User("yash","agasti","sauravyash","yash@yash.com");
//        } catch (IOException | ParseException ex) {
//            Logger.getLogger(CatalogueViewer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        OpenCatalogueWindow();
    }
    
    /**
     * This will open an instance of the login pane.
     */
    public static void OpenLoginWindow() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindowFrame().setVisible(true); 
        });
    }
    
    /**
     * This will open an instance of the ZIP file selection pane.
     */
    public static void OpenSelectorWindow() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CatalogueSelectorWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatalogueSelectorWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatalogueSelectorWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatalogueSelectorWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CatalogueSelectorWindowFrame().setVisible(true);
        });
    }
    
    /**
     * This will open an instance of the catalogue viewer.
     */
    public static void OpenCatalogueWindow() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CatalogueWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatalogueWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatalogueWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatalogueWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CatalogueWindowFrame().setVisible(true);
        });
    }

    /**
     * This will open an instance of the login pane and 
     * clear the current user data.
     */
    public static void LogOut() {
        UserData = null;
        OpenLoginWindow();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This will clean up all of the catalogues that were opened 
     * in the session and then quit the program
     */
    public static void ShutDown(){
        File[] Open_Catalogues = WorkingDIR.toFile().listFiles(File::isDirectory);
        // delete all open catalogues
        for(File catalogue: Open_Catalogues){
            catalogue.delete();
            try {
                Files.walk(catalogue.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ex) {
                Logger.getLogger(CatalogueViewer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.exit(0);
    }
}
