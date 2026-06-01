/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sokoban.filehandling;

import java.io.*;
import sokoban.model.Player;
/**
 *
 * @author gpopo
 */
public class UserFileManager extends FileManager {
    
    private static final String USERS_FOLDER = "users/";

    @Override
    public void save(Object obj, String path) throws IOException {
        createFolder(ROOT_FOLDER + USERS_FOLDER);
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(path))) {
            oos.writeObject(obj);
        }

    }

    @Override
    public Object load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(path))) {
            return ois.readObject();
        }
    }

    @Override
    public boolean delete(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    @Override
    public boolean exists(String path) {
        return new File(path).exists();
    }
    
    public void savePlayer(Player player) throws IOException {
        String folder = ROOT_FOLDER + USERS_FOLDER + player.getUsername();
        createFolder(folder);
        String path = buildPath(USERS_FOLDER + player.getUsername(), "profile.dat");
        save(player, path);
    }
    
    public Player loadPlayer(String username) throws IOException, ClassNotFoundException {
        String path = buildPath(USERS_FOLDER + username, "profile.dat");
        if(!exists(path)){
            return null;
        }
        return (Player) load(path);
    }
    
    public boolean deletePlayer(String username) {
        String path = buildPath(USERS_FOLDER + username, "profile.dat");
        return delete(path);
    }
    
    public boolean playerExists(String username) {
        String path = buildPath(USERS_FOLDER + username, "profile.dat");
        return exists(path);
    }
    
}
