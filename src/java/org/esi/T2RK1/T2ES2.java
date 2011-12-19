/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

import java.sql.*;
import com.mysql.jdbc.Statement;
import ee.ut.cs.esi.t2es2.services.AnnualConsumptionResponse;
import ee.ut.cs.esi.t2es2.services.BalanceInvoiceResponse;
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
public class T2ES2 {

    static dbConnection connection = new dbConnection();

    public static String submitBalanceInvoice(String messageID, String recipient, String date) throws ParseException, DatatypeConfigurationException, InterruptedException {
        ee.ut.cs.esi.t2es2.services.ElectricitySupplierServiceImplService service = new ee.ut.cs.esi.t2es2.services.ElectricitySupplierServiceImplService();
        ee.ut.cs.esi.t2es2.services.ElectricitySupplierService port = service.getElectricitySupplierServiceImplPort();

        ee.ut.cs.esi.t2es2.services.BalanceInvoice balanceInvoice = new ee.ut.cs.esi.t2es2.services.BalanceInvoice();

        balanceInvoice.setMessageId(messageID);
        balanceInvoice.setMessageRecipient(recipient);
        balanceInvoice.setMessageSender("T2RK1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        GregorianCalendar timeStampCalendar = new GregorianCalendar();
        timeStampCalendar.setTime(formatter.parse(date));
        XMLGregorianCalendar timeStamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeStampCalendar);

        balanceInvoice.setSendTime(timeStamp);

        Boolean dataSent = false;

        try {
            BalanceInvoiceResponse response = port.submitBalanceInvoice(balanceInvoice);
            return "Success: Balance Invoice has been sent successfully. Null received as Confirmation.";
        } catch (Exception e) {
            Thread.sleep(15000);
        }

        if (!dataSent) {
            try {
                BalanceInvoiceResponse response = port.submitBalanceInvoice(balanceInvoice);
                return "Success: Balance Invoice has been sent successfully on second trial (15 seconds wait). Null received as Confirmation.";
            } catch (Exception e) {
                Thread.sleep(15000);
            }
        }

        if (!dataSent) {
            try {
                BalanceInvoiceResponse response = port.submitBalanceInvoice(balanceInvoice);
                return "Success: Balance Invoice has been sent successfully on third trial (30 seconds wait). Null received as Confirmation.";
            } catch (Exception e) {
                return "Failure: Balance Invoice has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2ES2.";
            }
        }

        return null;
    }

    public static String submitAnnualConsumptionReport(String messageID, String name, String phone, String postalAddress, String startDate, String endDate) throws SQLException {
        ee.ut.cs.esi.t2es2.services.ElectricitySupplierServiceImplService service = new ee.ut.cs.esi.t2es2.services.ElectricitySupplierServiceImplService();
        ee.ut.cs.esi.t2es2.services.ElectricitySupplierService port = service.getElectricitySupplierServiceImplPort();

        ee.ut.cs.esi.t2es2.services.AnnualConsumption annualConsumption = new ee.ut.cs.esi.t2es2.services.AnnualConsumption();
        ee.ut.cs.esi.t2es2.services.Client client = new ee.ut.cs.esi.t2es2.services.Client();

        //Check if Supplier Exists
        String message = null;
        Statement statement = null;

        AnnualConsumptionResponse response;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            connection.openDBConnection();

            //Calculate and Get Consumed Energy from DB - for supplier per area per month
            String query = "select ID, startDate, endDate, userID, sum(consumedEnergy) as consumption from " + connection.getDbName() + ".dailymetereddata group by year(startDate)";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                try {
                    client.setId(Integer.parseInt(resultSet.getString("userID")));
                }
                catch(Exception e){
                    client.setId(401);
                }

                client.setName(name);
                client.setPhone(phone);
                client.setPostalAddress(postalAddress);

                GregorianCalendar startDateCalendar = new GregorianCalendar();
                startDateCalendar.setTime(formatter.parse(startDate));
                XMLGregorianCalendar xmlStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateCalendar);

                client.setStartDate(xmlStartDate);

                GregorianCalendar endDateCalendar = new GregorianCalendar();
                endDateCalendar.setTime(formatter.parse(endDate));
                XMLGregorianCalendar xmlEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateCalendar);

                client.setEndDate(xmlEndDate);

                annualConsumption.setMessageId(messageID);
                annualConsumption.setRelatedMessageId(String.valueOf(resultSet.getInt("ID")));

                GregorianCalendar sendTimeCalendar = new GregorianCalendar();
                sendTimeCalendar.setTime(resultSet.getDate("startDate"));
                XMLGregorianCalendar sendTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(sendTimeCalendar);

                annualConsumption.setSendTime(sendTime);

                annualConsumption.setClient(client);
                annualConsumption.setMessageRecipient("T2ES2");
                annualConsumption.setMessageSender("T2RK1");
                annualConsumption.setAnnualConsumption((double) resultSet.getFloat("consumption"));

                Boolean dataSent = false;

                try {
                    response = port.submitAnnualConsumptionReport(annualConsumption);
                    message = "Success: Annual Consumption Report has been sent successfully. Null received as Confirmation.";
                    dataSent = true;
                } catch (Exception e) {
                    Thread.sleep(15000);
                }

                if (!dataSent) {
                    try {
                        response = port.submitAnnualConsumptionReport(annualConsumption);
                        message = "Success: Annual Consumption Report has been sent successfully on second trial (15 seconds wait). Null received as Confirmation.";
                        dataSent = true;
                    } catch (Exception e) {
                        Thread.sleep(15000);
                    }
                }

                if (!dataSent) {
                    try {
                        response = port.submitAnnualConsumptionReport(annualConsumption);
                        message = "Success: Annual Consumption Report has been sent successfully on third trial (30 seconds wait). Null received as Confirmation.";
                        dataSent = true;
                    } catch (Exception e) {
                        message = "Failure: Annual Consumption Report has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2ES2.";
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
