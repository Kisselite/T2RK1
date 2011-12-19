<%-- 
    Document   : t2es2R
    Created on : 11.12.2011, 20:16:00
    Author     : Shaz
--%>

<%@page import="org.esi.T2RK1.T2ES2RESTful"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Talk to T2ES2 RESTful</title>
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

            <form action="t2es2R.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Consumption Info:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Start Date*</label></td>
                        <td><input type="text" name="cStartDate" value="01-01-2012"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">End Date*</label></td>
                        <td><input type="text" name="cEndDate" value="01-01-2012"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Amount*</label></td>
                        <td><input type="text" name="cAmount" value="100"/></td>
                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
            <br><br>
            <form action="t2es2R.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Balance Invoice:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Start Date*</label></td>
                        <td><input type="text" name="startDate" value="01-01-2012"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">End Date*</label></td>
                        <td><input type="text" name="endDate" value="01-01-2012"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Amount*</label></td>
                        <td><input type="text" name="amount" value="100"/></td>
                    </tr>
                    
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>

            <%
                        if (request != null) {
                            T2ES2RESTful t2es2 = new T2ES2RESTful();

                            if (((request.getParameter("cStartDate") != null)
                                    && (!request.getParameter("cStartDate").isEmpty()))
                                    && ((request.getParameter("cEndDate") != null)
                                    && (!request.getParameter("cEndDate").isEmpty()))
                                    && ((request.getParameter("cAmount") != null)
                                    && (!request.getParameter("cAmount").isEmpty()))) {
                                out.println("<br>" + t2es2.submitAnnualConsumptionReport(request.getParameter("cStartDate"), request.getParameter("cEndDate"), Integer.parseInt(request.getParameter("cAmount"))) + "<br>");
                            }
                            /*else {
                                out.println("Failure: Please specify Supplier ID first!<br>");
                            }*/

                            if (((request.getParameter("startDate") != null)
                                    && (!request.getParameter("startDate").isEmpty()))
                                    && ((request.getParameter("endDate") != null)
                                    && (!request.getParameter("endDate").isEmpty()))
                                    && ((request.getParameter("amount") != null)
                                    && (!request.getParameter("amount").isEmpty()))) {
                                out.println("<br>" + t2es2.submitBalanceInvoice(request.getParameter("startDate"), request.getParameter("endDate"), Integer.parseInt(request.getParameter("amount"))) + "<br>");
                            }
                            /*else {
                                out.println("Failure: Missing Value(s). Please specify Date and Value first!<br>");
                            }*/
                        }
            %>
        </div>
    </body>
</html>
