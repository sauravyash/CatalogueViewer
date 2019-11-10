/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.ZipSelector;

/**
 * 
 * @author Yash Agasti
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class unzips a normal ZIP file.
 * 
 * Usage: String unzipped_folder_location =  new FileUnzipper(zipFile,unzipDir).unzip();
 * The first parameter (zipFile) must be a File or a String (file works better). 
 * The second parameter (unzipDir) must be a String.
 * 
 * You will need to surround it in a "try{} catch(Exception e){}" block
 * 
 * @author Yash Agasti
 */
public class FileUnzipper {

    private String zipFileDir  = null;
    private String zipFileName = null;
    private String unzipDir    = null;
    
    /**
     * Constructor that initilizes the zip file.
     * @param zipFile the source file to unzip as a File
     * @param unzipDir the parent directory of the destination as a string
     * @throws IOException if file doesn't exist, it is thrown
     */
    public FileUnzipper(File zipFile, String unzipDir) throws IOException {
        this.zipFileName = zipFile.getName();
        this.zipFileDir  = zipFile.getParentFile().getCanonicalPath();
        this.unzipDir    = unzipDir;
    }
    
    /**
     * Alternate Constructor that initilizes the zip file
     * @param zipFile the source file to unzip as a string
     * @param unzipDir the parent directory of the destination as a string
     * @throws IOException 
     */
    public FileUnzipper(String zipFile, String unzipDir) throws IOException {
        this.zipFileName = new File(zipFile).getName();
        this.zipFileDir  = new File(zipFile).getParentFile().getCanonicalPath();
        this.unzipDir    = unzipDir;
    }

    /**
     * Starts the unzip process.
     * 
     * It also removes any duplicates.
     * 
     * @return the directory of the new unzipped folder
     * @throws IOException
     */
    public String unzip() throws IOException {
        String zipFilePath = new File(this.zipFileDir,this.zipFileName).getCanonicalPath();
        String finalPath = zipFilePath.substring(0, zipFilePath.length() - 4);
        try {
            // delete folder if it already exists
            if (new File(finalPath).exists()){
                Files.walk(new File(unzipDir, zipFileName.substring(0, zipFileName.length() - 4)).toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        
        // unzip
        try {
            System.out.println("zipFilePath = " + zipFilePath);
            ZipFile zipFile = new ZipFile(zipFilePath);
            
            /*
                Creates a special enumerable list of all of the files/folders 
                in the zip
            */
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            // runs until it has unzipped everything
            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String destPath = new File(this.unzipDir, entry.getName()).toString();
                if(entry.isDirectory()){
                    System.out.print("dir  : " + entry.getName());
                    System.out.println(" => " + destPath);
                    File file = new File(destPath);
                    file.mkdirs();
                } else {
                    // retrieve the input stream & output stream
                    try (InputStream is = zipFile.getInputStream(entry); 
                            OutputStream os = new FileOutputStream(destPath)) {
                        // create byte array buffer 
                        byte[] buf = new byte[4096];
                        int r;
                        // write from the buffer to the file
                        while ((r = is.read(buf)) != -1) {
                            os.write(buf, 0, r);
                        }
                    }
                    
                    System.out.println("file : " + entry.getName() + " => " + destPath);
                }
            }
        } catch(IOException e){
            throw new IOException("Error unzipping file: " + zipFilePath, e);
        }
        
        return finalPath;
    }
}
