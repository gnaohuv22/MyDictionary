/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Word;

/**
 *
 * @author hoang
 */
public class WordDAO extends DBContext {

    public ArrayList<Word> getListWord() {
        String SQL = "SELECT * FROM [Word]";
        ArrayList<Word> data = new ArrayList<>();

        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String word = rs.getString(2);
                String dictId = String.valueOf(rs.getString(3));

                Word w = new Word(id, word, dictId);
                data.add(w);
            }
            return data;
        } catch (SQLException e) {
            System.out.println("getListWord: " + e.getMessage());
        }
        return null;
    }

    private int getMaxId() {
        String SQL = "SELECT MAX(id) FROM Word";

        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getMaxId: " + e.getMessage());
        }
        return -1;
    }

    public int addNewWord(Word w) {
        String SQL = "INSERT INTO Word VALUES (?, ?, ?)";

        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, this.getMaxId() + 1);
            pstm.setString(2, w.getWord());
            pstm.setInt(3, Integer.parseInt(w.getDictId()));

            pstm.execute();
            return this.getMaxId();
        } catch (SQLException e) {
            System.out.println("addNewWord: " + e.getMessage());
        }
        return -1;
    }

    void deleteAssociatedWord(String dictId) {
        String SQL = "DELETE FROM [Word] WHERE dictId = ?";
        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(dictId));

            pstm.execute();
        } catch (Exception e) {
            System.out.println("deleteAssociatedWord: " + e.getMessage());
        }
    }

    public ArrayList<Word> getWordsById(String dictId) {
        String SQL = "SELECT * FROM [Word] WHERE dictId = ?";
        ArrayList<Word> data = new ArrayList<>();

        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(dictId));
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String word = rs.getString(2);
                String dId = String.valueOf(rs.getString(3));

                Word w = new Word(id, word, dId);
                data.add(w);
            }
            return data;
        } catch (SQLException e) {
            System.out.println("getWordById: " + e.getMessage());
        }
        return null;
    }

    void deleteMeaning(String id) {
        ArrayList<Word> data = this.getWordsById(id);
        MeaningDAO md = new MeaningDAO();
        for (Word w : data) {
            md.deleteAssociatedMeaning(w);
        }
    }

    public Word getWordById(String wordId) {
        String SQL = "SELECT * FROM [Word] WHERE id = ?";

        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(wordId));
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String word = rs.getString(2);
                String dictId = String.valueOf(rs.getString(3));

                Word w = new Word(id, word, dictId);
                return w;
            }
        } catch (SQLException e) {
            System.out.println("getWordById: " + e.getMessage());
        }
        return null;
    }

    public void deleteWordById(String id) {
        MeaningDAO md = new MeaningDAO();
        md.deleteAssociatedMeaning(this.getWordById(id));
        String SQL = "DELETE FROM [Word] WHERE id = ?";
        try ( PreparedStatement pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, Integer.parseInt(id));

            pstm.execute();
        } catch (SQLException e) {
            System.out.println("deleteWordById: " + e.getMessage());
        }
    }

    public boolean isDuplicated(Word w) {
        String SQL = "SELECT * FROM [Word] WHERE word = ? AND dictId = ?";
        try ( PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, w.getWord().trim());
            ps.setString(2, w.getDictId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("isDuplicated: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Word> getWordByPattern(String pattern) {

        if (pattern.equals(".")) {
            pattern = "";
        }
        String SQL = "SELECT * FROM [Word] WHERE word LIKE '%' + ? + '%'";
        ArrayList<Word> data = new ArrayList<>();

        try ( PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, pattern);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String word = rs.getString(2);
                String dictId = String.valueOf(rs.getString(3));

                Word w = new Word(id, word, dictId);
                data.add(w);
            }
            return data;
        } catch (SQLException e) {
            System.out.println("getWordByPattern: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Word> sortDictionaryByType(String typeOfSort, ArrayList<Word> data) {
        switch ((Integer.parseInt(typeOfSort))) {
            case 0:
                return data;

            case 1:
                Collections.sort(data, Word.WordComparatorASC);
                return data;

            case 2:
                Collections.sort(data, Word.WordComparatorDESC);
                return data;

            default:
                throw new AssertionError();
        }
    }
}
