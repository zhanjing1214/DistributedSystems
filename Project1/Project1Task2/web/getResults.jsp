<%-- 
    Document   : getResults
    Created on : 2016-9-21, 22:35:02
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
        <%= request.getAttribute("result") %><br>
    </body>
</html>
