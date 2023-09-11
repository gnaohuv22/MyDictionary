/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DictionaryDAO;
import dal.MeaningDAO;
import dal.MeaningTypeDAO;
import dal.UserDAO;
import dal.WordDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Dictionary;
import model.Meaning;
import model.MeaningType;
import model.User;
import model.Word;

/**
 *
 * @author hoang
 */
public class DictionaryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String userInfo = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    userInfo = cookie.getValue();
                    break;
                }
            }
        }
        User u = null;

        if (userInfo != null) {
            UserDAO ud = new UserDAO();
            u = ud.getUserByUsername(userInfo);
        }
        request.setAttribute("u", u);

        if (request.getParameter("id") != null) {
            String id = request.getParameter("id");

            DictionaryDAO dd = new DictionaryDAO();
            Dictionary d = dd.getDictionaryById(id);
            if (d == null) {
                request.getRequestDispatcher("notfound.jsp").forward(request, response);
            } else {
                if (u.getUsername().equals(d.getAuthor())) {
                    WordDAO wd = new WordDAO();
                    ArrayList<Word> wordData = wd.getListWord();

                    MeaningDAO md = new MeaningDAO();
                    ArrayList<Meaning> meaningData = md.getListMeaning();

                    MeaningTypeDAO mtd = new MeaningTypeDAO();
                    ArrayList<MeaningType> meaningType = mtd.getListMeaningType();

                    request.setAttribute("d", d);
                    request.setAttribute("wordList", wordData);
                    request.setAttribute("meaningList", meaningData);
                    request.setAttribute("meaningType", meaningType);
                    request.getRequestDispatcher("/WEB-INF/viewDictionary.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("forbidden.jsp").forward(request, response);
                }
            }

        } else if (request.getParameter("add-new-word") != null) { //Add a new word to current dictionary
            String id = request.getParameter("dictId");

            DictionaryDAO dd = new DictionaryDAO();
            Dictionary d = dd.getDictionaryById(id);
            ArrayList<Dictionary> dictionaryList = dd.getListDictionary();

            WordDAO wd = new WordDAO();
            ArrayList<Word> wordData = wd.getListWord();

            MeaningDAO md = new MeaningDAO();
            ArrayList<Meaning> meaningData = md.getListMeaning();

            MeaningTypeDAO mtd = new MeaningTypeDAO();
            ArrayList<MeaningType> meaningType = mtd.getListMeaningType();
            request.setAttribute("d", d);
            request.setAttribute("dictionaryList", dictionaryList);
            request.setAttribute("wordList", wordData);
            request.setAttribute("meaningList", meaningData);
            request.setAttribute("meaningType", meaningType);
            request.getRequestDispatcher("/WEB-INF/addWord.jsp").forward(request, response);
        } else if (request.getParameter("edit-dict") != null) { //Change the dictionary name
            String id = request.getParameter("dictId");
            DictionaryDAO dd = new DictionaryDAO();
            Dictionary d = dd.getDictionaryById(id);
            request.setAttribute("d", d);
            request.getRequestDispatcher("/WEB-INF/editDictionary.jsp").forward(request, response);
        } else if (request.getParameter("modify") != null) { //Modify a word in dictionary
            String dictId = request.getParameter("dictId");
            String wordId = request.getParameter("wordId");
            WordDAO wd = new WordDAO();
            Word w = wd.getWordById(wordId);
            MeaningDAO md = new MeaningDAO();
            MeaningTypeDAO mtd = new MeaningTypeDAO();
            ArrayList<Meaning> meanings = md.getListMeaning();
            ArrayList<MeaningType> meaningType = mtd.getListMeaningType();

            request.setAttribute("dictId", dictId);
            request.setAttribute("w", w);
            request.setAttribute("meanings", meanings);
            request.setAttribute("meaningType", meaningType);
            request.getRequestDispatcher("/WEB-INF/modifyWord.jsp").forward(request, response);
        } else if (request.getParameter("search") != null) {
            String pattern = request.getParameter("search-box");
            String dictId = request.getParameter("dictId");
            WordDAO wd = new WordDAO();
            DictionaryDAO dd = new DictionaryDAO();
            HttpSession session = request.getSession();
            session.removeAttribute("d");
            session.removeAttribute("words");
            session.removeAttribute("searched");
            
            session.setAttribute("d", dd.getDictionaryById(dictId));
            session.setAttribute("words", wd.getWordByPattern(pattern));
            session.setAttribute("searched", "1");

            response.sendRedirect("home?dictId=" + dictId);
        } else if (request.getParameter("sort") != null) {
            String dictId = request.getParameter("dictId");
            String[] ids = request.getParameterValues("ids");
            String[] words = request.getParameterValues("words");

            ArrayList<Word> data = new ArrayList<>();
            HttpSession session = request.getSession();
            String typeOfSort = request.getParameter("sort");
            WordDAO wd = new WordDAO();
            for (int i = 0; i < ids.length; ++i) {
                Word w = new Word(ids[i], words[i], dictId);
                data.add(w);
            }
            session.removeAttribute("words");
            session.removeAttribute("wordList");
            request.removeAttribute("wordList");
            request.removeAttribute("words");
            request.removeAttribute("sorted");
           
            session.setAttribute("sorted", "1");
            session.setAttribute("words", wd.sortDictionaryByType(typeOfSort, data));
            response.sendRedirect("home?dictId=" + dictId);
        } else {
            request.getRequestDispatcher("/WEB-INF/addDictionary.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("add") != null) {
            DictionaryDAO dd = new DictionaryDAO();
            String id = String.valueOf(dd.getNumberOfDictionary() + 1);
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String author = request.getParameter("author");

            Dictionary d = new Dictionary(id, title, description, author);
            if (!dd.isDuplicated(d)) {
                if (dd.addNewDictionary(d)) {
                    //Display a successful message and auto close the dialog box
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Dictionary added successfully!');");
                    out.println("location='home';");
                    out.println("</script>");

                } else {
                    request.setAttribute("title", title);
                    request.setAttribute("description", description);
                    request.setAttribute("author", author);
                    request.setAttribute("errorMessage", "Add dictionary not successful. Please check the information and try again!");

                    request.getRequestDispatcher("/WEB-INF/addDictionary.jsp").forward(request, response);
                }
            } else {
                int numberOfName = dd.getNumberOfDuplicated(d);
                d = new Dictionary(id, title + " (" + numberOfName + ")", description, author);

                dd.addNewDictionary(d);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Dictionary added successfully!');");
                out.println("location='home';");
                out.println("</script>");
            }
        } else if (request.getParameter("add-word") != null) { //Add a new word to current dictionary
            //Get data from user's input
            String dictId = request.getParameter("dictId");
            String word = request.getParameter("word");
            String[] types = request.getParameterValues("meaning-type");
            String[] definitions = request.getParameterValues("definition");

            //Create a new Word object
            Word w = new Word("", word, dictId);
            WordDAO wd = new WordDAO();

            if (!wd.isDuplicated(w)) {
                int wordId = wd.addNewWord(w);

                //Add the meanings to database
                for (int i = 0; i < types.length; ++i) {
                    String meaningType = types[i];
                    String definition = definitions[i];
                    Meaning m = new Meaning("", meaningType, definition, String.valueOf(wordId));

                    MeaningDAO md = new MeaningDAO();
                    md.addNewMeaning(m);
                }
                //Redirect to viewDictionary.jsp page
                response.sendRedirect("home?dictId=" + dictId);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('This word has already existed.');");
                out.println("window.location.href = 'home?dictId=" + dictId + "';");
                out.println("</script>");
            }

        } else if (request.getParameter("save") != null) { // save changes to current dictionary
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String author = request.getParameter("author");

            Dictionary d = new Dictionary(id, title, description, author);
            DictionaryDAO dd = new DictionaryDAO();
            if (dd.updateDictionary(d)) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Dictionary updated successfully.');");
                out.println("location='home';");
                out.println("</script>");
            }

        } else if (request.getParameter("delete-dict") != null) { //Delete the current dictionary
            String id = request.getParameter("dictId");
            DictionaryDAO dd = new DictionaryDAO();
            if (dd.deleteDictionary(id)) {
                //Display a successfully message as a notification (not an alert) to the screen and let it close automatically after 3 seconds
                //Then back to 'home' page (home.jsp)
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Dictionary deleted successfully.');");
                out.println("location='home'");
                out.println("</script>");
            }
        } else if (request.getParameter("word-change") != null) { //Save the change about meanings of the word
            String[] removeMeaningIds = request.getParameterValues("removeMeaning");
            MeaningDAO md = new MeaningDAO();

            if (removeMeaningIds != null) {
                for (String id : removeMeaningIds) {
                    md.deleteMeaningById(id);
                }
            }

            String[] ids = request.getParameterValues("meaningId");
            String[] types = request.getParameterValues("meaning-type");
            String[] definitions = request.getParameterValues("definition");
            String wordId = request.getParameter("wordId");
            String dictId = request.getParameter("dictId");
            if (types != null || ids != null || definitions != null) {
                for (int i = 0; i < types.length; ++i) {
                    String type = types[i];
                    String definition = definitions[i];
                    String id = null;
                    if (i < ids.length) {
                        id = ids[i];
                    }

                    if (id == null) {
                        Meaning m = new Meaning("", type, definition, String.valueOf(wordId));
                        md.addNewMeaning(m);
                    } else {
                        Meaning m = new Meaning(id, type, definition, wordId);
                        md.updateMeaning(m);
                    }
                }
            } else {
                try ( PrintWriter out = response.getWriter()) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('An error occured.');");
                    out.println("location='home'");
                    out.println("</script>");
                }
            }
            response.sendRedirect("home?dictId=" + dictId);
        } else if (request.getParameter("delete") != null) { //Delete a word in dictionary
            String dictId = request.getParameter("dictId");
            String id = request.getParameter("wordId");
            WordDAO wd = new WordDAO();
            wd.deleteWordById(id);

            response.sendRedirect("home?dictId=" + dictId);
        } else {
            request.getRequestDispatcher("notfound.jsp").forward(request, response);
        }
    }
}
