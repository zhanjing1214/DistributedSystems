<%-- 
    Document   : login
    Created on : 2016-11-11, 9:11:05
    Author     : zhanjing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Baby</title>
    </head>
    <body>
        <style>
            .main{
                text-align: center;
            }
        </style>
        <div class="main">
        <br>
        ################################################
        <h3>Currency converter</h3>
        ################################################
        <br><br>
        <form action="MyAppServlet" method="POST">
            <label for="letter">Amount</label>
            <input type="text" name="searchAmount" value="" /><br>
            <br>
            <label for="letter">From</label>
            <select name="searchFrom">
                <option value ="USD">USD</option>
                <option value ="CNY">CNY</option>
                <option value="EUR">EUR</option>
                <option value="AUD">AUD</option>
                <option value="INR">INR</option>
                <option value="JPY">JPY</option>
                <option value="HKD">HKD</option>
            </select>
            <label for="letter">To</label>
            <select name="searchTo">
                <option value ="USD">USD</option>
                <option value ="CNY">CNY</option>
                <option value="EUR">EUR</option>
                <option value="AUD">AUD</option>
                <option value="INR">INR</option>
                <option value="JPY">JPY</option>
                <option value="HKD">HKD</option>
            </select>
            <br><br>
            <input type="submit" name="convertSubmit" value="Convert" /><br>
        </form> 
        <h4> <%= request.getAttribute("result")== null? "" : request.getAttribute("result")%></h4>
           
        <br><br><br><br> 
        #################################################
        <h3>Dashboard Login</h3>
        #################################################
        <br>
        <form action="MyAppServlet" method="GET">
            <br>
            <label for="letter">ID:</label>(jzhan1)
            <input type="text" name="id" value="" /><br><br> 
            <label for="letter">Password:</label> (1a2b1c4d)
            <input type="text" name="pw" value="" /><br><br>
            <input type="submit" name="dashboardSubmit" value="Login" /><br><br>
        </form>
        <h4> <%= request.getAttribute("error")== null? "" : request.getAttribute("error")%></h4>
        </div>
    </body>
</html>
