/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Shaz
 */
public class T2RK1 {

    dbConnection connection = new dbConnection();

    public LoadProfile[] getLoadProfileForDisplay() throws SQLException {
        Statement statement = null;
        LoadProfile[] loadProfile = null;

        int rowCount = 0;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM loadProfile";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            while (rs.next()) {
                rowCount = rs.getInt(1);
            }

            loadProfile = new LoadProfile[rowCount];

            String query = "select ownerId, dated, hour, hourlyLoad from " + connection.getDbName()
                    + ".loadProfile";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;

            while (resultSet.next()) {
                LoadProfile lp = new LoadProfile();

                lp.getNetworkOwner().setNetworkOwnerId(resultSet.getString("ownerId"));
                lp.setDate(resultSet.getDate("dated"));
                lp.setHour(resultSet.getDate("hour"));
                lp.setHourlyLoad(resultSet.getFloat("hourlyLoad"));

                loadProfile[index] = lp;

                index++;
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

        return loadProfile;
    }

    public LoadProfileShare[] getLoadProfileShareForDisplay() throws SQLException {
        Statement statement = null;
        LoadProfileShare[] loadProfileShare = null;

        int rowCount = 0;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM loadprofileshare";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            while (rs.next()) {
                rowCount = rs.getInt(1);
            }

            loadProfileShare = new LoadProfileShare[rowCount];

            String query = "select ownerID, supplierID, providerID, profileSettlementAreaID, calculationStage, supplyMonth, highLoadShare, lowLoadShare from " + connection.getDbName() + ".loadprofileshare";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;

            while (resultSet.next()) {
                LoadProfileShare lps = new LoadProfileShare();

                lps.getNetworkOwner().setNetworkOwnerId(resultSet.getString("ownerID"));
                lps.getElectricitySupplier().setElectricitySupplierID(resultSet.getString("supplierID"));
                lps.getBalanceProvider().setBalanceProviderID(resultSet.getString("providerID"));
                lps.setProfileSettlementAreaID(resultSet.getString("profileSettlementAreaID"));
                lps.setCalculationStage(resultSet.getString("calculationStage"));
                lps.setSupplyMonth(resultSet.getDate("supplyMonth"));
                lps.setHighLoadProfileShare(resultSet.getFloat("highLoadShare"));
                lps.setLowLoadProfileShare(resultSet.getFloat("lowLoadShare"));

                loadProfileShare[index] = lps;

                index++;
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

        return loadProfileShare;
    }

    public ConsumptionPlan[] getConsumptionPlanForDisplay() throws SQLException {
        Statement statement = null;
        ConsumptionPlan[] plannedConsumption = null;

        int rowCount = 0;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM consumptionplan";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            while (rs.next()) {
                rowCount = rs.getInt(1);
            }

            plannedConsumption = new ConsumptionPlan[rowCount];

            String query = "select supplierID, dated, plannedConsumption from " + connection.getDbName() + ".consumptionplan";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;

            while (resultSet.next()) {
                ConsumptionPlan cp = new ConsumptionPlan();

                cp.getElectricitySupplier().setElectricitySupplierID(resultSet.getString("supplierID"));
                cp.setDated(resultSet.getDate("dated"));
                cp.setPlannedConsumption(resultSet.getFloat("plannedConsumption"));


                plannedConsumption[index] = cp;

                index++;
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

        return plannedConsumption;
    }

    public ConsumptionForecast[] getConsumptionForecastForDisplay() throws SQLException {
        Statement statement = null;
        ConsumptionForecast[] forecastedConsumption = null;

        int rowCount = 0;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM consumptionforecast";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            while (rs.next()) {
                rowCount = rs.getInt(1);
            }

            forecastedConsumption = new ConsumptionForecast[rowCount];

            String query = "select supplierID, dated, forecastedConsumption from " + connection.getDbName() + ".consumptionforecast";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;

            while (resultSet.next()) {
                ConsumptionForecast cf = new ConsumptionForecast();

                cf.getElectricitySupplier().setElectricitySupplierID(resultSet.getString("supplierID"));
                cf.setDated(resultSet.getDate("dated"));
                cf.setForecastedConsumption(resultSet.getFloat("forecastedConsumption"));


                forecastedConsumption[index] = cf;

                index++;
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

        return forecastedConsumption;
    }

    public MeteredData[] getDailyMeteredDataForDisplay() throws SQLException {
        Statement statement = null;
        MeteredData[] meteredData = null;

        int rowCount = 0;

        try {
            connection.openDBConnection();

            String sqlString = "SELECT COUNT(*) FROM dailymetereddata";

            PreparedStatement preStatement = (PreparedStatement) connection.getConn().prepareStatement(sqlString);
            ResultSet rs = preStatement.executeQuery();

            while (rs.next()) {
                rowCount = rs.getInt(1);
            }

            meteredData = new MeteredData[rowCount];

            String query = "select ownerID, startDate, endDate, userID, consumedEnergy,  supplierID, suppliedEnergy from " + connection.getDbName() + ".dailymetereddata";
            statement = (Statement) connection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int index = 0;

            while (resultSet.next()) {
                MeteredData md = new MeteredData();

                md.getNetworkOwner().setNetworkOwnerId(resultSet.getString("ownerID"));
                md.setStartDate(resultSet.getDate("startDate"));
                md.setEndDate(resultSet.getDate("endDate"));

                ExitPoint exitPoint = new ExitPoint();
                exitPoint.setElectricityUserID(resultSet.getString("supplierID"));
                exitPoint.setEnergyConsumed(resultSet.getFloat("consumedEnergy"));
                md.setConsumedEnergy(exitPoint);

                EntryPoint entryPoint = new EntryPoint();
                entryPoint.getElectricitySupplier().setElectricitySupplierID(resultSet.getString("userID"));
                entryPoint.setSuppliedEnergy(resultSet.getFloat("suppliedEnergy"));
                md.setSuppliedEnergy(entryPoint);

                meteredData[index] = md;

                index++;
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


        return meteredData;
    }
}
