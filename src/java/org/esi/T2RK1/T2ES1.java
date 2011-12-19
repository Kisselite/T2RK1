/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

import java.sql.*;

/**
 *
 * @author Shaz
 */
public class T2ES1 {

    static dbConnection connection = new dbConnection();

    public static String sendBalanceInvoice(String date, int value) throws InterruptedException {
        com.supplier.ElectricitySupplier_Service service = new com.supplier.ElectricitySupplier_Service();
        com.supplier.ElectricitySupplier port = service.getElectricitySupplierPort();

        com.supplier.BalanceInvoiceMessage balanceInvoiceMessage = new com.supplier.BalanceInvoiceMessage();

        balanceInvoiceMessage.setDate(date);
        balanceInvoiceMessage.setValue(value);

        Boolean dataSent = false;

        try {
            port.sendBalanceInvoice(balanceInvoiceMessage);
            return "Success: Balance Invoice has been sent successfully. No Confirmation received though.";
        } catch (Exception e) {
            Thread.sleep(15000);
        }

        if (!dataSent) {
            try {
                port.sendBalanceInvoice(balanceInvoiceMessage);
                return "Success: Balance Invoice has been sent successfully on second trial (15 seconds wait). No Confirmation received though.";
            } catch (Exception e) {
                Thread.sleep(15000);
            }
        }

        if (!dataSent) {
            try {
                port.sendBalanceInvoice(balanceInvoiceMessage);
                return "Success: Balance Invoice has been sent successfully on third trial (30 seconds wait). No Confirmation received though.";
            } catch (Exception e) {
                return "Failure: Balance Invoice has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2ES1.";
            }
        }

        return null;
    }

    public static String sendConsumptionProfile(String supplierID) throws SQLException {
        com.supplier.ElectricitySupplier_Service service = new com.supplier.ElectricitySupplier_Service();
        com.supplier.ElectricitySupplier port = service.getElectricitySupplierPort();

        com.supplier.ConsumptionProfileMessage consumptionProfileMessage = new com.supplier.ConsumptionProfileMessage();

        //Check if Supplier Exists
        String message = null;
        Statement statement = null;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM dailymetereddata where supplierID = '" + supplierID + "'";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            if (rs == null) {
                return "Failure: Specified Supplier ID doesn't exist.";
            }
            else if(rs.next()) {
                if(rs.getInt(1) == 0)
                    return "Failure: Specified Supplier ID doesn't exist.";
            }

            //Calculate and Get Consumed Energy from DB - for supplier per area per month
            String query = "select supplierID, startDate, endDate, sum(consumedEnergy) as consumption from " + connection.getDbName() + ".dailymetereddata group by supplierID, startDate, endDate";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                consumptionProfileMessage.setDate(resultSet.getDate("startDate").toString());
                consumptionProfileMessage.setValue((int) resultSet.getFloat("consumption"));

                Boolean dataSent = false;

                try {
                    port.sendConsumptionProfile(consumptionProfileMessage);
                    message = "Success: Consumption Profile has been sent successfully. No Confirmation received though.";
                    dataSent = true;
                } catch (Exception e) {
                    Thread.sleep(15000);
                }

                if (!dataSent) {
                    try {
                        port.sendConsumptionProfile(consumptionProfileMessage);
                        message = "Success: Consumption Profile has been sent successfully on second trial (15 seconds wait). No Confirmation received though.";
                        dataSent = true;
                    } catch (Exception e) {
                        Thread.sleep(15000);
                    }
                }

                if (!dataSent) {
                    try {
                        port.sendConsumptionProfile(consumptionProfileMessage);
                        message = "Success: Consumption Profile has been sent successfully on third trial (30 seconds wait). No Confirmation received though.";
                        dataSent = true;
                    } catch (Exception e) {
                       message = "Failure: Consumption Profile has not been sent after waiting for 30 seconds and three trials. T2RK1 is giving up on T2ES1.";
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
