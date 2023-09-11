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
import model.MeaningType;

/**
 *
 * @author hoang
 */
public class MeaningTypeDAO extends DBContext {
    public ArrayList<MeaningType> getListMeaningType() {
        ArrayList<MeaningType> data = new ArrayList<>();
        String SQL = "SELECT * FROM [MeaningType]";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString(1);
                String type = rs.getString(2);
                
                MeaningType mt = new MeaningType(id, type);
                data.add(mt);
            }
            return data;
            
        } catch (SQLException e) {
            System.out.println("getListMeaningType: " + e.getMessage());
        }
        return null;
    }
}
