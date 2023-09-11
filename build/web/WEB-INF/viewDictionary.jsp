<%-- 
    Document   : viewDictionary
    Created on : Jul 12, 2023, 11:56:47 AM
    Author     : hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon-16x16.png">
        <link rel="stylesheet" href="CSS/viewDictionary.css">
    </head>
    <body>
        <div class="dict-view-box">
            <div class="interact-butt">
                <form id="interact-form" action="dictionary" method="GET">
                    <input type="hidden" name="dictId" value="${d.getId()}">
                    <c:choose>
                        <c:when test="${not empty searched or not empty sorted}">
                            <c:forEach items="${words}" var="w">
                                <c:if test="${w.getDictId() eq d.getId()}">
                                    <input type="hidden" name="ids" value="${w.getId()}">
                                    <input type="hidden" name="words" value="${w.getWord()}">
                                </c:if>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <c:forEach items="${wordList}" var="w">
                                <c:if test="${w.getDictId() eq d.getId()}">
                                    <input type="hidden" name="ids" value="${w.getId()}">
                                    <input type="hidden" name="words" value="${w.getWord()}">
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                    <button type="submit" class="add-word" name="add-new-word">Add New Word</button>
                    <button type="submit" class="edit-dict" name="edit-dict">Edit</button>
                    <input type="text" class="search-box" name="search-box"> <input type="submit" name="search" value="Search">
                    <select name="sort">
                        <option value="0">Default</option>
                        <option value="1">From A to Z</option>
                        <option value="2">From Z to A</option>
                    </select>
                    <input type="submit" name="sort-by" value="Sort"> 
                </form>

                <form id="interact-form" action="dictionary" method="POST">
                    <input type="hidden" name="dictId" value="${d.getId()}">
                    <button class="delete-button" name="delete-dict" onclick="return confirm('Are you sure to delete this dictionary?');">Delete</button>
                </form>
            </div>

            <div class="dict-info">
                <h2 name="title">${d.getTitle()}</h2>
                <p name="description">${d.getDescription()}</p>
                <!--Refer a link to their profile-->
                <a href="profile?username=${u.getUsername()}"> 
                    <span class="author">Author: ${d.getAuthor()}</span>
                </a>
            </div>

            <div class="list-word">
                <c:choose>
                    <c:when test="${not empty searched or not empty sorted}">
                        <c:forEach items="${words}" var="w">
                            <c:if test="${d.getId() eq w.getDictId()}">
                                <div class="word">
                                    <span>${w.getWord()}</span>
                                    <a href="dictionary?modify=1&dictId=${d.getId()}&wordId=${w.getId()}">Modify</a>

                                    <c:forEach items="${meaningList}" var="m">
                                        <c:if test="${m.getWordId() eq w.getId()}">
                                            <c:forEach items="${meaningType}" var="mt">
                                                <c:if test="${m.getType() eq mt.getId()}">
                                                    <h5>${mt.getType()}</h5>
                                                </c:if>
                                            </c:forEach>
                                            <p>${m.getDefine()}</p>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${wordList}" var="w">
                            <c:if test="${d.getId() eq w.getDictId()}">
                                <div class="word">
                                    <span>${w.getWord()}</span>
                                    <a href="dictionary?modify=1&dictId=${d.getId()}&wordId=${w.getId()}">Modify</a>

                                    <c:forEach items="${meaningList}" var="m">
                                        <c:if test="${m.getWordId() eq w.getId()}">
                                            <c:forEach items="${meaningType}" var="mt">
                                                <c:if test="${m.getType() eq mt.getId()}">
                                                    <h5>${mt.getType()}</h5>
                                                </c:if>
                                            </c:forEach>
                                            <p>${m.getDefine()}</p>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
