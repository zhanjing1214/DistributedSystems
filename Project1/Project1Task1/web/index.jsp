<!-- this jsp asks the user to enter a string of text data, 
    and to make a choice of two hash functions using radio buttons. 
    The hash function choices should be MD5 and SHA-1, 
    with MD5 being the default. 
    When the submit button is pressed a servlet is executed. -->

<%-- 
    Document   : index
    Created on : 2016-9-20, 17:18:09
    Author     : zhanjing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DS Clicker</title>
    </head>
    <body>
        <form action="getComputeHashes" method="GET">
            <br>Please enter a string of text data:
            <input type="text" name="rawText"><br><br>
            <input type="radio" name="function" value="MD5" checked> MD5<br>
            <input type="radio" name="function" value="SHA-1"> SHA-1<br>
            <br><input type="submit" value="Submit">
        </form>
        
        <%--This requests the correct hashes from servlet--%>
        <% 
            if(request.getParameter("rawText")!= null) { 
                out.println("Hashes of the string \"" + request.getParameter("rawText") + "\""); %> <br>
        <%
            out.println(request.getAttribute("function") + "(HEX): " + request.getAttribute("hex")); %> <br>
        <%        
                out.println(request.getAttribute("function") + "(BASE 64): " + request.getAttribute("base"));
            }
        %> 
    </body>
</html>
