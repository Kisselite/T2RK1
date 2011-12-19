<%-- 
    Document   : t2es2
    Created on : 11.12.2011, 20:16:00
    Author     : Shaz
--%>

<%@page import="org.esi.T2RK1.T2ES2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Talk to T2ES2</title>
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

            <form action="t2es2.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Annual Consumption Report:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Message ID*</label></td>
                        <td><input type="text" name="messageID" value="1"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Name*</label></td>
                        <td><input type="text" name="name" value="Dummy User"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Phone*</label></td>
                        <td><input type="text" name="phone" value="00372110"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Postal Address*</label></td>
                        <td><input type="text" name="postalAddress" value="Raatuse 22 Lobby Tartu"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Start Date*</label></td>
                        <td><input type="text" name="startDate" value="2011-12-10 10:55:32"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">End Date*</label></td>
                        <td><input type="text" name="endDate" value="2011-12-10 10:55:32"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
            <br><br>
            <form action="t2es2.jsp" method="post">
                <table border="1">
                    <tr bgcolor=#AAAAAA>
                        <td colspan="2">Send Balance Invoice:</td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Message ID*</label></td>
                        <td><input type="text" name="messageID" value="1"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Recipient*</label></td>
                        <td><input type="text" name="recipient"/></td>
                    </tr>
                    <tr>
                        <td><label for="keyword">Date*</label></td>
                        <td><input type="text" name="dated" value="2011-12-10 10:55:32"/></td>
                    </tr>
                    
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>

            <%
                        if (request != null) {
                            T2ES2 t2es2 = new T2ES2();

                            if (((request.getParameter("messageID") != null)
                                    && (!request.getParameter("messageID").isEmpty()))
                                    && ((request.getParameter("phone") != null)
                                    && (!request.getParameter("phone").isEmpty()))
                                    && ((request.getParameter("postalAddress") != null)
                                    && (!request.getParameter("postalAddress").isEmpty()))
                                    && ((request.getParameter("name") != null)
                                    && (!request.getParameter("name").isEmpty()))
                                    && ((request.getParameter("startDate") != null)
                                    && (!request.getParameter("startDate").isEmpty()))
                                    && ((request.getParameter("endDate") != null)
                                    && (!request.getParameter("endDate").isEmpty()))) {
                                out.println("<br>" + t2es2.submitAnnualConsumptionReport(request.getParameter("messageID"), request.getParameter("phone"), request.getParameter("postalAddress"), request.getParameter("name"), request.getParameter("startDate"), request.getParameter("endDate")) + "<br>");
                            }
                            /*else {
                                out.println("Failure: Please specify Supplier ID first!<br>");
                            }*/

                            if (((request.getParameter("dated") != null)
                                    && (!request.getParameter("dated").isEmpty()))
                                    && ((request.getParameter("messageID") != null)
                                    && (!request.getParameter("messageID").isEmpty()))
                                    && ((request.getParameter("recipient") != null)
                                    && (!request.getParameter("recipient").isEmpty()))) {
                                out.println("<br>" + t2es2.submitBalanceInvoice(request.getParameter("messageID"), request.getParameter("recipient"), request.getParameter("dated")) + "<br>");
                            }
                            /*else {
                                out.println("Failure: Missing Value(s). Please specify Date and Value first!<br>");
                            }*/
                        }
            %>
        </div>
    </body>
</html>
