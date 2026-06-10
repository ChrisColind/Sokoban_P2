/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sokoban.filehandling;

import java.io.IOException;
/**
 *
 * @author gpopo
 */
public abstract class FileManager {
    
    protected static final String ROOT_FOLDER = "data/";
    
    public abstract void save(Object obj, String path) throws IOException;
    public abstract Object load(String path) throws IOException, ClassNotFoundException;
    public abstract boolean delete(String path);
    public abstract boolean exists(String path);
    
    protected String buildPath(String folder, String filename) {
        return ROOT_FOLDER + folder + "/" + filename;
    }
    
    protected boolean createFolder(String folderPath){
        java.io.File folder = new java.io.File(folderPath);
        if(!folder.exists()) {
            return folder.mkdirs();
        }
        return true;
    }

}
