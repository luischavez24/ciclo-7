<%-- 
    Document   : index
    Created on : 02/04/2018, 09:17:53 PM
    Author     : guis
--%>
<%! int cuenta = 10; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <table>
            <h3>Holi boli :3 bbita</h3>
        <%
            for (int i=0; i<cuenta; i++){
                out.println("<tr>" );
                out.println("<td>" + i + "</td>" );
                out.println("</tr>" );
            }
        %>
        </table>
    </center>
    </body>
</html>
