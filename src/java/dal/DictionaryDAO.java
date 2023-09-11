/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Dictionary;
import model.Word;

/**
 *
 * @author hoang
 */
public class DictionaryDAO extends DBContext {
    public ArrayList<Dictionary> getListDictionary() {
        String SQL = "SELECT * FROM [Dictionary]";
        ArrayList<Dictionary> data = new ArrayList<>();
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String title = rs.getString(2);
                String description = rs.getString(3);
                String author = rs.getString(4);
                
                Dictionary d = new Dictionary(id, title, description, author);
                data.add(d);
            }
            
            return data;
        } catch (Exception e) {
            System.out.println("getListDictionary: " + e.getMessage());
        }
        return null;
    }
    
    public int getNumberOfDictionary() {
        String SQL = "SELECT MAX(id) FROM [Dictionary]";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getNumberOfDictionary: " + e.getMessage());
        }
        return -1;
    }
    
    public boolean addNewDictionary(Dictionary d) {
        String SQL = "INSERT INTO [Dictionary] VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(d.getId()));
            pstm.setString(2, d.getTitle());
            pstm.setString(3, d.getDescription());
            pstm.setString(4, d.getAuthor());
            
            pstm.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("addNewDictionary: " + e.getMessage());
        }
        return false;
    }
    
    public Dictionary getDictionaryById(String id) {
        String SQL = "SELECT * FROM [Dictionary] WHERE id = ?";
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(id));
            
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                String did = String.valueOf(rs.getInt(1));
                String title = rs.getString(2);
                String description = rs.getString(3);
                String author = rs.getString(4);
                
                Dictionary d = new Dictionary(did, title, description, author);
                return d;
            }
        } catch (Exception e) {
            System.out.println("getDictionaryById: " + e.getMessage());
        }
        return null;
    }

    public boolean deleteDictionary(String id) {
        WordDAO wd = new WordDAO();
        
        wd.deleteMeaning(id);
        wd.deleteAssociatedWord(id);
        
        String SQL = "DELETE FROM [Dictionary] WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(id));
            
            pstm.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("deleteDictionary: " + e.getMessage());
        }
        return false;
    }
    
    public boolean updateDictionary (Dictionary d) {
        String SQL = "UPDATE [Dictionary] SET title = ?, description = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, d.getTitle());
            ps.setString(2, d.getDescription());
            ps.setString(3, d.getId());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("updateDictionary: " + e.getMessage());
        }
        return false;
    }
    
    public boolean isDuplicated(Dictionary d) {
        String SQL = "SELECT * FROM [Dictionary] WHERE title = ? AND author = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, d.getTitle().trim());
            ps.setString(2, d.getAuthor());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("isDuplicated: " + e.getMessage());
        }
        return false;
    }
    
    public int getNumberOfDuplicated(Dictionary d) {
        String SQL = "SELECT COUNT(*) FROM [Dictionary] WHERE title = ? AND author = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, d.getTitle().trim());
            ps.setString(2, d.getAuthor());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getNumberOfDuplicated: " + e.getMessage());
        }
        return 0;
    }
}
