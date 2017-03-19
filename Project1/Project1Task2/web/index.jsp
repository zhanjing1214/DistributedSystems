<%-- 
    Document   : index
    Created on : 2016-9-21, 21:44:26
    Author     : zhanjing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DS Clicker</title>
    </head>
    <body>
        <h1>Distributed Systems Class Clicker</h1><br>
        Submit your answer to the current question:<br>
        
        <form action="surveyServlet" method="POST"><br>
            <input type="radio" name="answer" value="A">A<br>
            <input type="radio" name="answer" value="B">B<br>
            <input type="radio" name="answer" value="C">C<br>
            <input type="radio" name="answer" value="D">D<br><br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
