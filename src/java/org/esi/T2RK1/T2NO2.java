/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import t2no2api.LoadProfileResponse;
import t2no2api.QualitySeriesResponse;

/**
 *
 * @author Shaz
 */
public class T2NO2 {

    static dbConnection connection = new dbConnection();
    
    public static String qualitySeries(String date, int value) throws ParseException, DatatypeConfigurationException, InterruptedException {
        t2no2api.T2NO2ApiService service = new t2no2api.T2NO2ApiService();
        t2no2api.T2NO2ApiPortType port = service.getT2NO2ApiPort();

        t2no2api.QualitySeries qualitySeriesRequest = new t2no2api.QualitySeries();

        qualitySeriesRequest.setFrom("T2RK1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        GregorianCalendar timeStampCalendar = new GregorianCalendar();
        timeStampCalendar.setTime(formatter.parse(date));
        XMLGregorianCalendar timeStamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeStampCalendar);

        qualitySeriesRequest.setTimestamp(timeStamp);
        qualitySeriesRequest.setValue(value);

        QualitySeriesResponse response;
        Boolean dataSent = false;

        try {
            response = port.qualitySeries(qualitySeriesRequest);
            if (response.getResultCode() == 0) {
                return "Success: Quality Series has been sent successfully. Zero received as Confirmation.";
            }
            return "Failure: Quality Series wasn't welcomed at T2NO2. Non-Zero Value received as Confirmation.";
        } catch (Exception e) {
            Thread.sleep(15000);
        }

        if (!dataSent) {
            try {
                response = port.qualitySeries(qualitySeriesRequest);
                if (response.getResultCode() == 0) {
                    return "Success: Quality Series has been sent successfully on second trial (15 seconds wait). Zero received as Confirmation.";
                }
                return "Failure: Quality Series wasn't welcomed at T2NO2. Non-Zero Value received as Confirmation.";
            } catch (Exception e) {
                Thread.sleep(15000);
            }
        }

        if (!dataSent) {
            try {
                response = port.qualitySeries(qualitySeriesRequest);
                if (response.getResultCode() == 0) {
                    return "Success: Quality Series has been sent successfully on third trial (30 seconds wait). Zero received as Confirmation.";
                }
                return "Failure: Quality Series wasn't welcomed at T2NO2. Non-Zero Value received as Confirmation.";
            } catch (Exception e) {
                return "Failure: Quality Series has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2NO2.";
            }
        }

        return null;
    }

    public static String loadProfile(String supplierID) throws SQLException {
        t2no2api.T2NO2ApiService service = new t2no2api.T2NO2ApiService();
        t2no2api.T2NO2ApiPortType port = service.getT2NO2ApiPort();

        t2no2api.LoadProfile loadProfileRequest = new t2no2api.LoadProfile();

        //Check if Supplier Exists
        String message = null;
        Statement statement = null;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM loadprofileshare where supplierID = '" + supplierID + "'";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            if (rs == null) {
                return "Failure: Specified Supplier ID doesn't exist.";
            } else if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return "Failure: Specified Supplier ID doesn't exist.";
                }
            }

            //Retrieve as much data as you can and take the rest from interface
            String query = "select supplierID, profileSettlementAreaID, supplyMonth, sum(highLoadShare) as high, sum(lowLoadShare) as low from " + connection.getDbName() + ".loadprofileshare where loadprofileshare.supplierID = '" + supplierID + "'";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                t2no2api.ES es = new t2no2api.ES();
                es.setId(resultSet.getString("supplierID"));
                es.setName(resultSet.getString("supplierID"));

                loadProfileRequest.setES(es);

                t2no2api.TimePeriod timePeriod = new t2no2api.TimePeriod();

                GregorianCalendar timePeriodCalendar = new GregorianCalendar();
                timePeriodCalendar.setTime(resultSet.getDate("supplyMonth"));
                XMLGregorianCalendar timePeriodDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(timePeriodCalendar);
                
                timePeriod.setStart(timePeriodDate);
                timePeriod.setEnd(timePeriodDate);

                loadProfileRequest.setPeriod(timePeriod);

                Long value = (long) (resultSet.getFloat("high") + resultSet.getFloat("low"))/2;
                loadProfileRequest.setAmount(value);

                LoadProfileResponse response;
                Boolean dataSent = false;

                try {
                    response = port.loadProfile(loadProfileRequest);

                    if(response.getResultCode() == 101)
                        message = "Failure: Invalid Client ID Format. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 102)
                        message = "Failure: Invalid No ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 103)
                        message = "Failure: Invalid Area ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 104)
                        message = "Failure: Invalid Facility ID (Integer Expected). Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 201)
                        message = "Failure: Unknown ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 202)
                        message = "Failure: Old ES same as New ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 203)
                        message = "Failure: Client already receives electricity from 'new ES'. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 0)
                        message = "Success: Load Profile has been sent successfully. Zero received as Confirmation.";

                    dataSent = true;
                } catch (Exception e) {
                    Thread.sleep(15000);
                }

                if (!dataSent) {
                    try {
                        response = port.loadProfile(loadProfileRequest);

                    if(response.getResultCode() == 101)
                        message = "Failure: Invalid Client ID Format. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 102)
                        message = "Failure: Invalid No ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 103)
                        message = "Failure: Invalid Area ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 104)
                        message = "Failure: Invalid Facility ID (Integer Expected). Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 201)
                        message = "Failure: Unknown ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 202)
                        message = "Failure: Old ES same as New ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 203)
                        message = "Failure: Client already receives electricity from 'new ES'. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 0)
                        message = "Success: Load Profile has been sent successfully. Zero received as Confirmation.";

                    dataSent = true;
                    } catch (Exception e) {
                        Thread.sleep(15000);
                    }
                }

                if (!dataSent) {
                    try {
                        response = port.loadProfile(loadProfileRequest);

                    if(response.getResultCode() == 101)
                        message = "Failure: Invalid Client ID Format. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 102)
                        message = "Failure: Invalid No ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 103)
                        message = "Failure: Invalid Area ID. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 104)
                        message = "Failure: Invalid Facility ID (Integer Expected). Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 201)
                        message = "Failure: Unknown ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 202)
                        message = "Failure: Old ES same as New ES. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 203)
                        message = "Failure: Client already receives electricity from 'new ES'. Sent Load Profile wasn't saved by T2NO2.";
                    else if(response.getResultCode() == 0)
                        message = "Success: Load Profile has been sent successfully. Zero received as Confirmation.";

                    dataSent = true;
                    } catch (Exception e) {
                        message = "Failure: Load Profile has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2NO2.";
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
}
