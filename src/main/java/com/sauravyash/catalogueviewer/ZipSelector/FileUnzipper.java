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
     * This Constructor initilizes the zip file
     * @param zipFile
     * @param unzipDir
     * @throws java.io.IOException 
     */
    public FileUnzipper(File zipFile, String unzipDir) throws IOException {
        this.zipFileName = zipFile.getName();
        this.zipFileDir  = zipFile.getParentFile().getCanonicalPath();
        this.unzipDir    = unzipDir;
    }
    
    public FileUnzipper(String zipFile, String unzipDir) throws IOException {
        this.zipFileName = new File(zipFile).getName();
        this.zipFileDir  = new File(zipFile).getParentFile().getCanonicalPath();
        this.unzipDir    = unzipDir;
    }
    
    /**
     * This Constructor ensures that 
     * @param zipFileDir
     * @param zipFileName
     * @param unzipDir
     */
    public FileUnzipper(String zipFileDir, String zipFileName, String unzipDir) {
        this.zipFileDir = zipFileDir;
        this.zipFileName = zipFileName;
        this.unzipDir = unzipDir;
    }

    /**
     *
     * @return
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        // unzip
        try {
            System.out.println("zipFilePath = " + zipFilePath);
            ZipFile zipFile = new ZipFile(zipFilePath);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String destPath = this.unzipDir + File.separator + entry.getName();
                if(entry.isDirectory()){
                    System.out.print("dir  : " + entry.getName());
                    System.out.println(" => " + destPath);
                    File file = new File(destPath);
                    file.mkdirs();
                } else {
                     // get the input stream
                    try (InputStream is = zipFile.getInputStream(entry); 
                            OutputStream os = new FileOutputStream(destPath)) {
                        byte[] buf = new byte[4096];
                        int r;
                        while ((r = is.read(buf)) != -1) {
                            os.write(buf, 0, r);
                        }
                    }
                    
                    System.out.println("file : " + entry.getName() + " => " + destPath);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
            throw new IOException("Error unzipping file: " + zipFilePath, e);
        }
        
        return finalPath;
    }
}
