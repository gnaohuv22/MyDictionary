/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author hoang
 */
public class UserDAO extends DBContext {

    public ArrayList<User> getListUser() {
        String SQL = "SELECT * FROM [User]";
        ArrayList<User> data = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String displayName = rs.getString(3);
                String email = rs.getString(4);
                String dob = String.valueOf(rs.getDate(5));
                String bio = rs.getString(6);
                String secretcode = String.valueOf(rs.getInt(7));

                User u = new User(username, password, displayName, email, dob, bio, secretcode);
                data.add(u);
            }
            return data;
        } catch (SQLException e) {
            System.out.println("getListUser: " + e.getMessage());
        }
        return null;
    }

    public boolean checkLogin(String username, String password) {
        String SQL = "SELECT * FROM [User] WHERE username = ? AND password = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("checkLogin" + e.getMessage());
        }
        return false;
    }

    public boolean isExisted(String username, String secretcode) {
        String SQL = "SELECT * FROM [User] WHERE username = ? AND secretcode = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, username);
            pstm.setInt(2, Integer.parseInt(secretcode));

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("isExisted: " + e.getMessage());
        }
        return false;
    }

    public void changePassword(String username, String password) {
        String SQL = "UPDATE [User] SET password = ? WHERE username = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, password);
            pstm.setString(2, username);

            pstm.execute();
        } catch (SQLException e) {
            System.out.println("changePassword: " + e.getMessage());
        }
    }

    public boolean registerUser(User u) {

        if (getUserByUsername(u.getUsername()) != null) {
            return false;
        }
        String SQL = "INSERT INTO [User] values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, u.getUsername());
            pstm.setString(2, u.getPassword());
            pstm.setString(3, u.getDisplayname());
            pstm.setString(4, u.getEmail());
            pstm.setDate(5, java.sql.Date.valueOf(u.getDob()));
            pstm.setString(6, u.getBio());
            pstm.setInt(7, Integer.parseInt(u.getSecretcode()));

            pstm.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("registerUser: " + e.getMessage());
            return false;
        }
    }

    public User getUserByUsername(String username) {

        String SQL = "SELECT * FROM [User] WHERE username = ?";
        try (PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, username);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User u = new User(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        String.valueOf(rs.getDate(5)),
                        rs.getString(6),
                        String.valueOf(rs.getInt(7))
                );

                return u;
            }
        } catch (SQLException e) {
            System.out.println("getUserByUsername: " + e.getMessage());
        }
        return null;
    }

    public void updateInfo(User u) {
        String SQL = "UPDATE [User] SET displayname = ?, email = ?, dob = ?, bio =? WHERE username = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, u.getDisplayname());
            ps.setString(2, u.getEmail());
            ps.setDate(3, java.sql.Date.valueOf(u.getDob()));
            ps.setString(4, u.getBio());
            ps.setString(5, u.getUsername());

            ps.execute();
        } catch (SQLException e) {
            System.out.println("updateInfo: " + e.getMessage());
        }
    }
}

