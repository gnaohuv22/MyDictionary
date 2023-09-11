/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Meaning;
import model.Word;

/**
 *
 * @author hoang
 */
public class MeaningDAO extends DBContext {
    public ArrayList<Meaning> getListMeaning() {
        String SQL = "SELECT * FROM [Meaning]";
        ArrayList<Meaning> data = new ArrayList<>();
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String type = rs.getString(2);
                String define = rs.getString(3);
                String wordId = String.valueOf(rs.getInt(4));
                
                Meaning m = new Meaning(id, type, define, wordId);
                data.add(m);
            }
            return data;
            
        } catch (SQLException e) {
            System.out.println("getListMeaning: " + e.getMessage());
        }
        return null;
    }
    
    private int getMaxId() {
        String SQL = "SELECT MAX(id) FROM Meaning";
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getMaxId: " + e.getMessage());
        }
        return -1;
    }
    
    public boolean addNewMeaning(Meaning m) {
        String SQL = "INSERT INTO Meaning VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, this.getMaxId() + 1);
            pstm.setString(2, m.getType());
            pstm.setString(3, m.getDefine());
            pstm.setString(4, String.valueOf(m.getWordId()));
            
            pstm.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("addNewMeaning: " + e.getMessage());
        }
        return false;
    }

    void deleteAssociatedMeaning(Word w) {
        String SQL = "DELETE FROM [Meaning] WHERE wordId = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(w.getId()));
            
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("deleteAssociatedMeaning: " + e.getMessage());
        }
    }
    
    public void deleteMeaningById(String id) {
        String SQL = "DELETE FROM [Meaning] WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(id));
            
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("deleteMeaningById: " + e.getMessage());
        }
    }

    public void updateMeaning(Meaning m) {
        String SQL = "UPDATE [Meaning] SET typeId = ?, definition = ? WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, m.getType());
            ps.setString(2, m.getDefine());
            ps.setString(3, m.getId());
            
            ps.execute();
        } catch (SQLException e) {
            System.out.println("updateMeaning: " + e.getMessage());
        }
    }
}
