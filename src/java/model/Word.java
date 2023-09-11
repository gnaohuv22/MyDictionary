/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Comparator;

/**
 *
 * @author hoang
 */
public class Word {
    private String id, word, dictId;

    public Word() {
    }

    public Word(String id, String word, String dictId) {
        this.id = id;
        this.word = word;
        this.dictId = dictId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }
    
    public static Comparator<Word> WordComparatorASC = new Comparator<Word>() {

        @Override
	public int compare(Word s1, Word s2) {
	   String word1 = s1.getWord().toLowerCase();
	   String word2 = s2.getWord().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};
    
    public static Comparator<Word> WordComparatorDESC = new Comparator<Word>() {

        @Override
	public int compare(Word s1, Word s2) {
	   String word1 = s1.getWord().toLowerCase();
	   String word2 = s2.getWord().toLowerCase();

	   //ascending order
	   return word2.compareTo(word1);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};
}
