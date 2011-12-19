/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

import esi.t2no1.ws.service.LoadProfileOutput;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Shaz
 */
public class T2NO1 {

    static dbConnection connection = new dbConnection();

    public static String submitLoadProfile(String areaID, int lossConsumption) throws InterruptedException, SQLException {
        esi.t2no1.ws.impl.MeteringServiceImplService service = new esi.t2no1.ws.impl.MeteringServiceImplService();
        esi.t2no1.ws.impl.MeteringService port = service.getMeteringServiceImplPort();

        esi.t2no1.ws.service.LoadProfileInput loadProfileInput = new esi.t2no1.ws.service.LoadProfileInput();
        esi.t2no1.ws.service.LoadProfile loadProfile = new esi.t2no1.ws.service.LoadProfile();

        //Check if Supplier Exists
        String message = null;
        Statement statement = null;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM loadprofileshare where profileSettlementAreaID = '" + areaID + "'";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            if (rs == null) {
                return "Failure: Specified Area ID doesn't exist.";
            } else if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return "Failure: Specified Area ID doesn't exist.";
                }
            }

            //Retrieve as much data as you can and take the rest from interface
            String query = "select loadprofileshare.supplierID, profileSettlementAreaID, supplyMonth, highLoadShare, lowLoadShare, plannedConsumption from " + connection.getDbName() + ".loadprofileshare, " + connection.getDbName() + ".ConsumptionPlan where loadprofileshare.supplierID = ConsumptionPlan.supplierID";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                loadProfile.setCalculationAreaId(resultSet.getString("profileSettlementAreaID"));
                loadProfile.setSupplierId(resultSet.getString("supplierID"));

                GregorianCalendar startDateCalendar = new GregorianCalendar();
                startDateCalendar.setTime(resultSet.getDate("supplyMonth"));
                XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateCalendar);

                loadProfile.setProfileStartDate(startDate);

                GregorianCalendar endDateCalendar = new GregorianCalendar();
                endDateCalendar.setTime(resultSet.getDate("supplyMonth"));
                XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateCalendar);

                loadProfile.setProfileEndDate(endDate);

                //Total Area Input comes from Planned Consumption because Planned Consumption is production
                //and production should be area input
                loadProfile.setTotalAreaInput((long) resultSet.getFloat("plannedConsumption"));

                //Take value from Interface as nothing potentially helpful was saved in DB
                loadProfile.setNetworkLossConsumption((long) lossConsumption);

                //(High Load + Low Load)/2
                Long average = (long) (resultSet.getFloat("highLoadShare") + resultSet.getFloat("lowLoadShare")) / 2;
                loadProfile.setProfileSettledConsumption(average);

                loadProfileInput.setMessageId("0");
                loadProfileInput.setMessageType("God Knows");
                loadProfileInput.setLoadProfile(loadProfile);

                LoadProfileOutput output;
                Boolean dataSent = false;

                try {
                    output = port.submitLoadProfile(loadProfileInput);
                    message = "Success: Load Profile has been sent successfully. Null received as Confirmation.";
                    dataSent = true;
                } catch (Exception e) {
                    Thread.sleep(15000);
                }

                if (!dataSent) {
                    try {
                        output = port.submitLoadProfile(loadProfileInput);
                        message = "Success: Load Profile has been sent successfully on second trial (15 seconds wait). Null received as Confirmation.";
                        dataSent = true;
                    } catch (Exception e) {
                        Thread.sleep(15000);
                    }
                }

                if (!dataSent) {
                    try {
                        output = port.submitLoadProfile(loadProfileInput);
                        message = "Success: Load Profile has been sent successfully on third trial (30 seconds wait). Null received as Confirmation.";
                        dataSent = true;
                    } catch (Exception e) {
                        message = "Failure: Load Profile has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2NO1.";
                    }
                }
            }

            resultSet.close();

            connection.closeDBConnection();
        } catch (SQLException s) {
        } catch (Exception e) {
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return message;
    }

    public static String submitQualitySeries(int seriesID, String date, Float value) throws DatatypeConfigurationException, InterruptedException, ParseException {
        esi.t2no1.ws.impl.MeteringServiceImplService service = new esi.t2no1.ws.impl.MeteringServiceImplService();
        esi.t2no1.ws.impl.MeteringService port = service.getMeteringServiceImplPort();

        esi.t2no1.ws.service.QualitySeriesInput qualitySeriesInput = new esi.t2no1.ws.service.QualitySeriesInput();

        esi.t2no1.ws.service.QualitySeriesInput.QualitySeries series = new esi.t2no1.ws.service.QualitySeriesInput.QualitySeries();
        esi.t2no1.ws.service.QualitySeriesInput.QualitySeries.Entry entry = new esi.t2no1.ws.service.QualitySeriesInput.QualitySeries.Entry();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        GregorianCalendar keyCalendar = new GregorianCalendar();
        keyCalendar.setTime(formatter.parse(date));
        XMLGregorianCalendar key = DatatypeFactory.newInstance().newXMLGregorianCalendar(keyCalendar);

        entry.setKey(key);
        entry.setValue(value);

        series.getEntry().add(entry);

        qualitySeriesInput.setMessageId("0");
        qualitySeriesInput.setMessageType("God Knows");
        qualitySeriesInput.setSeriesId(seriesID);
        qualitySeriesInput.setQualitySeries(series);
        qualitySeriesInput.setSenderId("T2RK1");

        Boolean output = null;
        Boolean dataSent = false;

        try {
            output = port.submitQualitySeries(qualitySeriesInput);
            if(output)
                return "Success: Quality Series has been sent successfully. True received as Confirmation.";
            return "Failure: Quality Series wasn't welcomed at T2NO1. False received as Confirmation.";
        } catch (Exception e) {
            Thread.sleep(15000);
        }

        if (!dataSent) {
            try {
                output = port.submitQualitySeries(qualitySeriesInput);
                if(output)
                    return "Success: Quality Series has been sent successfully on second trial (15 seconds wait). True received as Confirmation.";
                return "Failure: Quality Series wasn't welcomed at T2NO1. False received as Confirmation.";
            } catch (Exception e) {
                Thread.sleep(15000);
            }
        }

        if (!dataSent) {
            try {
                output = port.submitQualitySeries(qualitySeriesInput);
                if(output)
                    return "Success: Quality Series has been sent successfully on third trial (30 seconds wait). True received as Confirmation.";
                return "Failure: Quality Series wasn't welcomed at T2NO1. False received as Confirmation.";
            } catch (Exception e) {
                return "Failure: Quality Series has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2NO1.";
            }
        }

        return null;
    }
}
