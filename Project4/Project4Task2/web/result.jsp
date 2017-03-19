<%-- 
    Document   : result
    Created on : 2016-11-11, 11:04:50
    Author     : zhanjing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Surprise Baby</title>
    </head>

    <body>
        <style>
            .main{
                text-align: center;
            }
        </style>


        <div class="main">
        ####################################################
        <h2>MyApp Log Analysis </h2>
        ####################################################
        <br><br>
        ____________________________________________________
        <h4>Number of records in the database: <br><br><%= request.getAttribute("a1")%></h4>
        ____________________________________________________
        <h4>Last time request: <br><br><%= request.getAttribute("a2")%></h4>
        ____________________________________________________
        <h4>Number of records cenvert to USD: <br><br><%= request.getAttribute("a3")%></h4>
        ____________________________________________________
        <h4>Number of convert amount > 10: <br><br> <%= request.getAttribute("a4")%></h4>
        ____________________________________________________
        <h4>Number of visits from : <br><br><%= request.getAttribute("a5")%></h4>
        ___________________________________________________________________________________________________________
        <h5>All records in the database: <br><br> <%= request.getAttribute("all")%></h5>
        </div>
    </body>
</html>
