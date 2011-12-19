<%-- 
    Document   : t2no1
    Created on : 11.12.2011, 20:15:49
    Author     : Shaz
--%>

<%@page import="java.sql.Date"%>
<%@page import="org.esi.T2RK1.T2NO1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Talk to T2NO1</title>
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
            <form action="t2no1.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Load Profile:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Area ID*</label></td>
                        <td><input type="text" name="areaID"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Network Loss Consumption*</label></td>
                        <td><input type="text" name="lossConsumption"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
            <br><br>
            <form action="t2no1.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Quality Series:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Series ID*</label></td>
                        <td><input type="text" name="seriesID"/></td>
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
                            T2NO1 t2no1 = new T2NO1();

                            if (request.getParameter("areaID") != null && !request.getParameter("areaID").isEmpty()) {
                                out.println("<br>" + t2no1.submitLoadProfile(request.getParameter("areaID"), Integer.parseInt(request.getParameter("lossConsumption"))) + "<br>");
                            }



                            if (((request.getParameter("seriesID") != null)
                                    && (!request.getParameter("seriesID").isEmpty()))
                                    && ((request.getParameter("date") != null)
                                    && (!request.getParameter("date").isEmpty()))
                                    && ((request.getParameter("value") != null)
                                    && (!request.getParameter("value").isEmpty()))) {
                                out.println("<br>" + t2no1.submitQualitySeries(Integer.parseInt(request.getParameter("seriesID")), request.getParameter("date"), Float.parseFloat(request.getParameter("value"))) + "<br>");
                            }
                        }
            %>
        </div>
    </body>
</html>
