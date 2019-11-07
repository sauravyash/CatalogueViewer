/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sauravyash.catalogueviewer.ZipSelector;

/**
 *
 * @author Yash
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
 * This class unzips a normal ZIP file
 * @author Yash
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
        try {
            Files.walk(new File(unzipDir, zipFileName.substring(0, zipFileName.length() - 4)).toPath())
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
                    InputStream is = zipFile.getInputStream(entry); // get the input stream
                    OutputStream os = new java.io.FileOutputStream(destPath);
                    byte[] buf = new byte[4096];
                    int r;
                    while ((r = is.read(buf)) != -1) {
                      os.write(buf, 0, r);
                    }
                    os.close();
                    is.close();
                    
                    System.out.println("file : " + entry.getName() + " => " + destPath);
                }
            }
        } catch(IOException e){
            throw new IOException("Error unzipping file: " + zipFilePath, e);
        }
        String path = zipFilePath.substring(0, zipFilePath.length() - 4);
        
        return path;
    }
}