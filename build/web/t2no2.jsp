<%-- 
    Document   : t2no2
    Created on : 11.12.2011, 20:16:11
    Author     : Shaz
--%>

<%@page import="org.esi.T2RK1.T2NO2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Talk to T2NO2</title>
    </head>
    <body>
        <div align="center">
            <h3>Welcome to Ruritania’s Electricity Market</h3>
            <a href="index.jsp"> Home Page </a> |
            <a href="t2es1.jsp"> Talk to T2ES1 </a> |
            <a href="t2no1.jsp"> Talk to T2NO1 </a> |
            <a href="t2es2R.jsp"> Talk to T2ES2 </a> |
            <a href="t2no2.jsp"> Talk to T2NO2 </a>
            <br><br><br>
            <form action="t2no2.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Load Profile:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Supplier ID*</label></td>
                        <td><input type="text" name="supplierID"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
            <br><br>
            <form action="t2no2.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Quality Series:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Date*</label></td>
                        <td><input type="text" name="date" value ="2011-12-10 10:55:32"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Value*</label></td>
                        <td><input type="text" name="value"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
            <%
                        if (request != null) {
                            T2NO2 t2no2 = new T2NO2();

                            if (request.getParameter("supplierID") != null && !request.getParameter("supplierID").isEmpty()) {
                                out.println("<br>" + t2no2.loadProfile(request.getParameter("supplierID")) + "<br>");
                            }
                            
                            if (((request.getParameter("date") != null)
                                    && (!request.getParameter("date").isEmpty()))
                                    && ((request.getParameter("value") != null)
                                    && (!request.getParameter("value").isEmpty()))) {
                                out.println("<br>" + t2no2.qualitySeries(request.getParameter("date"), Integer.parseInt(request.getParameter("value"))) + "<br>");
                            }
                        }
            %>
        </div>
    </body>
</html>
