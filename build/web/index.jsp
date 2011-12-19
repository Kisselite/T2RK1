<%-- 
    Document   : index
    Created on : 10.12.2011, 19:48:07
    Author     : Shaz
--%>

<%@page import="org.esi.T2RK1.LoadProfileShare"%>
<%@page import="org.esi.T2RK1.LoadProfile"%>
<%@page import="org.esi.T2RK1.ConsumptionPlan"%>
<%@page import="org.esi.T2RK1.ConsumptionForecast"%>
<%@page import="org.esi.T2RK1.MeteredData"%>
<%@page import="org.esi.T2RK1.T2RK1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ESI Semester Project T2RK1</title>
    </head>
    <body>
        <div align="center">
            <h3>Welcome to Ruritania’s Electricity Market</h3>
            <a href="index.jsp"> Home Page </a> |
            <a href="t2es1.jsp"> Talk to T2ES1 </a> |
            <a href="t2no1.jsp"> Talk to T2NO1 </a> |
            <a href="t2es2R.jsp"> Talk to T2ES2 </a> |
            <a href="t2no2.jsp"> Talk to T2NO2 </a>
            <br>
            <a href="#DailyMeteredData">Daily Metered Data</a> |
            <a href="#ConsumptionForecast">Consumption Forecast</a> |
            <a href="#ConsumptionPlan">Consumption Plan</a> |
            <a href="#LoadProfile">Load Profile</a> |
            <a href="#LoadProfileShare">Load Profile Share</a>
            <br><br><br>
            <%
                        T2RK1 t2rk1 = new T2RK1();
                        MeteredData[] meteredData = t2rk1.getDailyMeteredDataForDisplay();

                        out.println("<table border=1>");
                        out.println("<tr colspan=7><a name=DailyMeteredData>Received Daily Metered Data</a></tr>");
                        out.println("<tr bgcolor=#AAAAAA>");
                        out.println("<td> Network Owner ID </td>");
                        out.println("<td> Start Date </td>");
                        out.println("<td> End Date </td>");
                        out.println("<td> Electricity User ID </td>");
                        out.println("<td> Consumed Energy </td>");
                        out.println("<td> Supplier ID </td>");
                        out.println("<td> Supplied Energy </td>");
                        out.println("</tr>");

                        if (meteredData != null && meteredData.length != 0) {

                            for (int i = 0; i < meteredData.length; i++) {
                                out.println("<tr>");
                                out.println("<td>" + meteredData[i].getNetworkOwner().getNetworkOwnerId() + "</td>");
                                out.println("<td>" + meteredData[i].getStartDate() + "</td>");
                                out.println("<td>" + meteredData[i].getEndDate() + "</td>");
                                out.println("<td>" + meteredData[i].getConsumedEnergy().getElectricityUserID() + "</td>");
                                out.println("<td>" + meteredData[i].getConsumedEnergy().getEnergyConsumed() + "</td>");
                                out.println("<td>" + meteredData[i].getSuppliedEnergy().getElectricitySupplier().getElectricitySupplierID() + "</td>");
                                out.println("<td>" + meteredData[i].getSuppliedEnergy().getSuppliedEnergy() + "</td>");
                                out.println("</tr>");
                            }
                        }

                        out.println("</table><br><br>");

                        ConsumptionForecast[] forecastedConsumption = t2rk1.getConsumptionForecastForDisplay();

                        out.println("<table border=1>");
                        out.println("<tr colspan=7><a name=ConsumptionForecast>Received Consumption Forecasts</a></tr>");
                        out.println("<tr bgcolor=#AAAAAA>");
                        out.println("<td> Supplier ID </td>");
                        out.println("<td> Date </td>");
                        out.println("<td> Forecasted Consumption </td>");
                        out.println("</tr>");

                        if (forecastedConsumption != null && forecastedConsumption.length != 0) {

                            for (int i = 0; i < forecastedConsumption.length; i++) {
                                out.println("<tr>");
                                out.println("<td>" + forecastedConsumption[i].getElectricitySupplier().getElectricitySupplierID() + "</td>");
                                out.println("<td>" + forecastedConsumption[i].getDated() + "</td>");
                                out.println("<td>" + forecastedConsumption[i].getForecastedConsumption() + "</td>");
                                out.println("</tr>");
                            }
                        }

                        out.println("</table><br><br>");

                        ConsumptionPlan[] plannedConsumption = t2rk1.getConsumptionPlanForDisplay();

                        out.println("<table border=1>");
                        out.println("<tr colspan=7><a name=ConsumptionPlan>Received Consumption Plans</a></tr>");
                        out.println("<tr bgcolor=#AAAAAA>");
                        out.println("<td> Supplier ID </td>");
                        out.println("<td> Date </td>");
                        out.println("<td> Planned Consumption </td>");
                        out.println("</tr>");

                        if (plannedConsumption != null && plannedConsumption.length != 0) {

                            for (int i = 0; i < plannedConsumption.length; i++) {
                                out.println("<tr>");
                                out.println("<td>" + plannedConsumption[i].getElectricitySupplier().getElectricitySupplierID() + "</td>");
                                out.println("<td>" + plannedConsumption[i].getDated() + "</td>");
                                out.println("<td>" + plannedConsumption[i].getPlannedConsumption() + "</td>");
                                out.println("</tr>");
                            }
                        }

                        out.println("</table><br><br>");

                        LoadProfile[] loadProfile = t2rk1.getLoadProfileForDisplay();

                        out.println("<table border=1>");
                        out.println("<tr colspan=7><a name=LoadProfile>Received Load Profiles</a></tr>");
                        out.println("<tr bgcolor=#AAAAAA>");
                        out.println("<td> Network Owner ID </td>");
                        out.println("<td> Date </td>");
                        out.println("<td> Hour </td>");
                        out.println("<td> Hourly Load </td>");
                        out.println("</tr>");

                        if (loadProfile != null && loadProfile.length != 0) {

                            for (int i = 0; i < loadProfile.length; i++) {
                                out.println("<tr>");
                                out.println("<td>" + loadProfile[i].getNetworkOwner().getNetworkOwnerId() + "</td>");
                                out.println("<td>" + loadProfile[i].getDate() + "</td>");
                                out.println("<td>" + loadProfile[i].getHour() + "</td>");
                                out.println("<td>" + loadProfile[i].getHourlyLoad() + "</td>");
                                out.println("</tr>");
                            }
                        }

                        out.println("</table><br><br>");

                        LoadProfileShare[] loadProfileShare = t2rk1.getLoadProfileShareForDisplay();

                        out.println("<table border=1>");
                        out.println("<tr colspan=7><a name=LoadProfileShare>Received Load Profile Shares</a></tr>");
                        out.println("<tr bgcolor=#AAAAAA>");
                        out.println("<td> Network Owner ID </td>");
                        out.println("<td> Supplier ID </td>");
                        out.println("<td> Provider ID </td");
                        out.println("<td> Profile Settlement Area ID </td>");
                        out.println("<td> Calculation Stage </td>");
                        out.println("<td> Supply Month </td>");
                        out.println("<td> High Load Profile Share </td>");
                        out.println("<td> Low Load Profile Share </td>");
                        out.println("</tr>");

                        if (loadProfileShare != null && loadProfileShare.length != 0) {

                            for (int i = 0; i < loadProfileShare.length; i++) {
                                out.println("<tr>");
                                out.println("<td>" + loadProfileShare[i].getNetworkOwner().getNetworkOwnerId() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getElectricitySupplier().getElectricitySupplierID() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getBalanceProvider().getBalanceProviderID() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getProfileSettlementAreaID() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getCalculationStage() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getSupplyMonth() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getHighLoadProfileShare() + "</td>");
                                out.println("<td>" + loadProfileShare[i].getLowLoadProfileShare() + "</td>");
                                out.println("</tr>");
                            }
                        }

                        out.println("</table><br><br>");

            %>
        </div>
    </body>
</html>
