/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sokoban.auth;

import java.io.IOException;
import sokoban.filehandling.UserFileManager;
import sokoban.model.Authenticable;
import sokoban.model.Language;
import sokoban.model.Player;

/**
 *
 * @author gpopo
 */
public class AuthManager implements Authenticable {
    
    private UserFileManager userFileManager;
    private Player currentPlayer;
    
    public AuthManager() {
        this.userFileManager = new UserFileManager();
        this.currentPlayer = null;
    }

    @Override
    public boolean register(String username, String password, String fullName) {
        try {
            if (userFileManager.playerExists(username)) {
                System.out.println("El usuario ya existe");
                return false;
            }
            
            if(!validatePassword(password)) {
                System.out.println("La contrasena no cumple los requisitos");
                return false;
            }
            
            Player newPlayer = new Player(username, password, fullName);
            userFileManager.savePlayer(newPlayer);
            System.out.println("Usuario registrado exitosamente");
            return true;
        } catch(IOException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean login(String username, String password) {
        try{
            if(!userFileManager.playerExists(username)) {
                System.out.println("El usuario no existe");
                return false;
            }
            Player player = userFileManager.loadPlayer(username);
            if (player != null && player.getPassword().equals(password)) {
                this.currentPlayer = player;
                System.out.println("Login exitoso. Bienvenido " + player.getFullName());
                return true;
            }
            System.out.println("Contrasena incorrecta");
            return false;
        }catch(IOException | ClassNotFoundException e) {
            System.out.println("Error al iniciar sesion: " + e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean logout(String username) {
        if (currentPlayer != null && currentPlayer.getUsername().equals(username)){
            currentPlayer = null;
            System.out.println("Sesion cerrada");
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        try{
            Player player = userFileManager.loadPlayer(username);
            if (player == null) return false;
            if (!player.getPassword().equals(oldPassword)) {
                System.out.println("Contrasena actual incorrecta");
                return false;
            }
            if(!validatePassword(newPassword));
            userFileManager.savePlayer(player);
            return true;
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cambiar contrasena: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validatePassword(String password) {
        if (password.length()<8) return false;
        if(!password.matches(".*[A-Z]*")) return false;
        if(!password.matches(".*[0-9]*.")) return false;
        if(!password.matches(".*[!@#$%^&*()].*")) return false;
        return true;
    }
    
    public Player getCurrentPlayer() 
    {
        return currentPlayer;
    }
    
    public boolean isLoggedIn(){
        return currentPlayer != null;
    }
    
}
