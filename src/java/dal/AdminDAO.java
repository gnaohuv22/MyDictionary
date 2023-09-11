/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Admin;
import model.Dictionary;

/**
 *
 * @author hoang
 */
public class AdminDAO extends DBContext {
    public boolean checkLogin(String username, String password) {
        String SQL = "SELECT * FROM [Admin] WHERE username = ? AND password = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        }
        return false;
    }
    
    public boolean deleteUser (String username) {
        String SQL = "DELETE FROM [User] WHERE username =?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
        DictionaryDAO dd = new DictionaryDAO();
        ArrayList<Dictionary> dictionarys = dd.getListDictionary();
        for (Dictionary d : dictionarys) {
            if (d.getAuthor().equals(username)) {
                dd.deleteDictionary(d.getId());
            }
        }
            ps.setString(1, username);
            
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("deleteUser: " + e.getMessage());
        }
        return false;
    }
    
    public boolean deleteDictionary(String dictId) {
        DictionaryDAO dd = new DictionaryDAO();
        return dd.deleteDictionary(dictId);
    }
    
    public Admin getById(String username) {
        String SQL = "SELECT * FROM [Admin] WHERE username = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String password = rs.getString(2);
                String displayName = rs.getString(3);
                
                Admin a = new Admin(username, password, displayName);
                return a;
            }
        } catch (Exception e) {
            System.out.println("getById: " + e.getMessage());
        }
        return null;
    }
}
