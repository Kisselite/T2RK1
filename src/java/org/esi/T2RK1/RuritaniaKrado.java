/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Shaz
 */
@WebService()
@Stateless()
public class RuritaniaKrado {
    dbConnection connection = new dbConnection();
    
    //Receive Daily Metered Data from NO
    @WebMethod(operationName = "receiveDailyMeteredData")
    public String receiveDailyMeteredData( @WebParam(name = "networkOwnerId")
    String networkOwnerId, @WebParam(name = "constraintAreaId")
    String constraintAreaId, @WebParam(name = "startDate")
    String startDate, @WebParam(name = "endDate")
    String endDate, @WebParam(name = "electricityUserId")
    String electricityUserId, @WebParam(name = "consumedEnergy")
    String consumedEnergy, @WebParam(name = "electricitySupplierId")
    String electricitySupplierId, @WebParam(name = "suppliedEnergy")
    String suppliedEnergy) {
        String message = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Parse Input Parameters
        NetworkOwner owner = new NetworkOwner();
        owner.setNetworkOwnerId(networkOwnerId);
        owner.setConstraintAreaId(constraintAreaId);

        ElectricitySupplier supplier = new ElectricitySupplier();
        supplier.setElectricitySupplierID(electricitySupplierId);

        MeteredData data = new MeteredData();

        try {
            data.setStartDate(formatter.parse(startDate));
        }
        catch(ParseException e) {
            return "Failure [102]: Invalid Date. Start Date should be Date.";
        }

        try {
            data.setEndDate(formatter.parse(endDate));
        }
        catch(ParseException e) {
            return "Failure [103]: Invalid Date. End Date should be Date.";
        }

        data.setNetworkOwner(owner);

        data.setSuppliedEnergy(new EntryPoint());
        data.getSuppliedEnergy().setElectricitySupplier(supplier);
        data.getSuppliedEnergy().setSuppliedEnergy(new Float(suppliedEnergy));

        data.setConsumedEnergy(new ExitPoint());
        data.getConsumedEnergy().setElectricityUserID(electricityUserId);
        data.getConsumedEnergy().setEnergyConsumed(new Float(consumedEnergy));

        //Store in Database
        try {
            connection.openDBConnection();

            PreparedStatement statement = connection.getConn().prepareStatement("insert dailymetereddata"
                    + "(ownerID, startDate, endDate, userID, consumedEnergy,  supplierID, suppliedEnergy) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, owner.getNetworkOwnerId());
            statement.setDate(2, new Date(data.getStartDate().getTime()));
            statement.setDate(3, new Date(data.getStartDate().getTime()));
            statement.setString(4, data.getConsumedEnergy().getElectricityUserID());
            statement.setFloat(5, data.getConsumedEnergy().getEnergyConsumed());
            statement.setString(6, data.getSuppliedEnergy().getElectricitySupplier().getElectricitySupplierID());
            statement.setFloat(7, data.getSuppliedEnergy().getSuppliedEnergy());
            
            statement.executeUpdate();
            
            message = "Success: Daily Metered Data has been successfully received and saved.";

            connection.closeDBConnection();
        }
        catch (SQLException s){
            s.printStackTrace();
            return "Failure [104]: SQL Exception. Daily Metered Data wasn't saved successfully.";
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return message;
    }
    
    /*@WebMethod(operationName = "takeDailyMeteredData")
    @Oneway
    public void takeDailyMeteredData(@WebParam(name = "meteredData")
    MeteredData meteredData) {
        
    }*/

    //Receive Consumption Forecast and Plan from ES
    @WebMethod(operationName = "receiveConsumptionForecast")
    public String receiveConsumptionForecast(@WebParam(name = "electricitySupplierId")
    String electricitySupplierId, @WebParam(name = "date") String date, @WebParam(name = "forecastedConsumption")
    String forecastedConsumption) {
        String message = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ConsumptionForecast forecast = new ConsumptionForecast();

        forecast.setForecastedConsumption(new Float(forecastedConsumption));
        try {
            forecast.setDated(formatter.parse(date));
        }
        catch(ParseException e){
            return "Failure [202]: Invalid Date. Please provide Date in desired format.";
        }

        ElectricitySupplier supplier = new ElectricitySupplier();
        supplier.setElectricitySupplierID(electricitySupplierId);
        forecast.setElectricitySupplier(supplier);

        //Store in Database
        try {
            connection.openDBConnection();

            PreparedStatement statement = connection.getConn().prepareStatement("insert consumptionforecast"
                    + "(supplierID, dated, forecastedConsumption) "
                    + "VALUES(?, ?, ?)");
            statement.setString(1, forecast.getElectricitySupplier().getElectricitySupplierID());
            statement.setDate(2, new Date(forecast.getDated().getTime()));
            statement.setFloat(3, forecast.getForecastedConsumption());

            statement.executeUpdate();

            message = "Success: Consumption Forecast has been successfully received and saved.";

            connection.closeDBConnection();
        }
        catch (SQLException s){
            return "Failure [203]: SQL Exception. Consumption Forecast wasn't saved successfully.";
        }
        catch(Exception e) {
        }

        return message;
    }

    @WebMethod(operationName = "receiveConsumptionPlan")
    public String receiveConsumptionPlan(@WebParam(name = "electricitySupplierId")
    String electricitySupplierId, @WebParam(name = "date") String date, @WebParam(name = "plannedConsumption")
    String plannedConsumption) {
        String message = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ConsumptionPlan plan = new ConsumptionPlan();

        plan.setPlannedConsumption(new Float(plannedConsumption));
        try{
            plan.setDated(formatter.parse(date));
        }
        catch(ParseException e){
            return "Failure [302]: Invalid Date. Please provide Date in desired format.";
        }

        ElectricitySupplier supplier = new ElectricitySupplier();
        supplier.setElectricitySupplierID(electricitySupplierId);
        plan.setElectricitySupplier(supplier);

        //Store in Database
        try {
            connection.openDBConnection();

            PreparedStatement statement = connection.getConn().prepareStatement("insert consumptionplan"
                    + "(supplierID, dated, plannedConsumption) "
                    + "VALUES(?, ?, ?)");
            statement.setString(1, plan.getElectricitySupplier().getElectricitySupplierID());
            statement.setDate(2, new Date(plan.getDated().getTime()));
            statement.setFloat(3, plan.getPlannedConsumption());

            statement.executeUpdate();

            message = "Success: Consumption Plan has been successfully received and saved.";

            connection.closeDBConnection();
        }
        catch (SQLException s){
            return "Failure [303]: SQL Exception. Consumption Plan wasn't saved successfully.";
        }
        catch(Exception e) {
        }

        return message;
    }

    /*@WebMethod(operationName = "takeConsumptionForecastAndPlan")
    @Oneway
    public void takeConsumptionForecastAndPlan(@WebParam(name = "forecast")
    ConsumptionForecast forecast, @WebParam(name = "plan")
    ConsumptionPlan plan) {
    }*/

    //Receive Load Profile from NO
    @WebMethod(operationName = "receiveLoadProfile")
    public String receiveLoadProfile(@WebParam(name = "networkOwnerId")
    String networkOwnerId, @WebParam(name = "date")
    String date, @WebParam(name = "hour")
    String hour, @WebParam(name = "hourlyLoad")
    String hourlyLoad) {
        String message = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        LoadProfile loadProfile = new LoadProfile();

        NetworkOwner owner = new NetworkOwner();
        owner.setNetworkOwnerId(networkOwnerId);

        loadProfile.setNetworkOwner(owner);
        loadProfile.setHourlyLoad(new Float(hourlyLoad));

        try {
            loadProfile.setHour(formatter.parse(hour));
        }
        catch(ParseException e){
            return "Failure [403]: Invalid Time. Please provide Hour in desired format.";
        }

        try {
            loadProfile.setDate(formatter.parse(date));
        }
        catch(ParseException e){
            return "Failure [402]: Invalid Date. Please provide Date in desired format.";
        }

        //Store in Database
        try {
            connection.openDBConnection();

            PreparedStatement statement = connection.getConn().prepareStatement("insert loadprofile"
                    + "(ownerID, dated, hour, hourlyload) "
                    + "VALUES(?, ?, ?, ?)");
            statement.setString(1, loadProfile.getNetworkOwner().getNetworkOwnerId());
            statement.setDate(2, new Date(loadProfile.getDate().getTime()));
            statement.setDate(3, new Date(loadProfile.getHour().getTime()));
            statement.setFloat(4, loadProfile.getHourlyLoad());

            statement.executeUpdate();

            message = "Success: Load Profile has been successfully received and saved.";

            connection.closeDBConnection();
        }
        catch (SQLException s){
            return "Failure [404]: SQL Exception. Load Profile wasn't saved successfully.";
        }
        catch(Exception e) {
        }

        return message;
    }

    /*@WebMethod(operationName = "takeLoadProfile")
    @Oneway
    public void takeLoadProfile(@WebParam(name = "loadProfile")
    LoadProfile loadProfile) {
    }*/

    //Receive Preliminary Load Profile Shares per ES from NO
    //Receive Preliminary Load Profile Shares per BP from NO
    @WebMethod(operationName = "receiveLoadProfileShare")
    public String receiveLoadProfileShare(@WebParam(name = "networkOwnerId")
    String networkOwnerId, @WebParam(name = "electricitySupplierId")
    String electricitySupplierId, @WebParam(name = "balanceProviderId")
    String balanceProviderId, @WebParam(name = "profileSettlementAreaId")
    String profileSettlementAreaId, @WebParam(name = "calculationStage")
    String calculationStage, @WebParam(name = "supplyMonth")
    String supplyMonth, @WebParam(name = "highLoadProfileShare")
    String highLoadProfileShare, @WebParam(name = "lowLoadProfileShare")
    String lowLoadProfileShare) {
        String message = null;
        
        if((electricitySupplierId != null) && (balanceProviderId != null))
            return "Failure [502]: T2RK1 is incapable of handling Load Profile Share for the provided Electricity Supplier ID and Balance Provider ID in one go.";

        if((!calculationStage.toLowerCase().equals("preliminary")) && (!calculationStage.toLowerCase().equals("final")))
            return "Failure [503]: Invalid Value. Calculation Stage can only be either preliminary or final.";
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        LoadProfileShare loadProfileShare = new LoadProfileShare();

        NetworkOwner owner = new NetworkOwner();
        owner.setNetworkOwnerId(networkOwnerId);

        loadProfileShare.setNetworkOwner(owner);

        ElectricitySupplier supplier = new ElectricitySupplier();
        supplier.setElectricitySupplierID(electricitySupplierId);

        loadProfileShare.setElectricitySupplier(supplier);

        BalanceProvider provider = new BalanceProvider();
        provider.setBalanceProviderID(balanceProviderId);

        loadProfileShare.setBalanceProvider(provider);

        loadProfileShare.setProfileSettlementAreaID(profileSettlementAreaId);
        loadProfileShare.setCalculationStage(calculationStage.toLowerCase());

        try {
            loadProfileShare.setSupplyMonth(formatter.parse(supplyMonth));
        }
        catch (ParseException e) {
            return "Failure [504]: Invalid Value. Supply Month should be Date.";
        }

        loadProfileShare.setHighLoadProfileShare(new Float(highLoadProfileShare));
        loadProfileShare.setLowLoadProfileShare(new Float(lowLoadProfileShare));

        //Store in Database
        try {
            connection.openDBConnection();

            PreparedStatement statement = null;

            if(electricitySupplierId != null){
                statement = connection.getConn().prepareStatement("insert loadprofileshare"
                    + "(ownerID, supplierID, profileSettlementAreaID, calculationStage, supplyMonth, highLoadShare, lowLoadShare) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, loadProfileShare.getNetworkOwner().getNetworkOwnerId());
                statement.setString(2, loadProfileShare.getElectricitySupplier().getElectricitySupplierID());
                statement.setString(3, loadProfileShare.getProfileSettlementAreaID());
                statement.setString(4, loadProfileShare.getCalculationStage());
                statement.setDate(5, new Date(loadProfileShare.getSupplyMonth().getTime()));
                statement.setFloat(6, loadProfileShare.getHighLoadProfileShare());
                statement.setFloat(7, loadProfileShare.getLowLoadProfileShare());
            }
            else if(balanceProviderId != null)
            {
                statement = connection.getConn().prepareStatement("insert loadprofileshare"
                    + "(ownerID, providerID, profileSettlementAreaID, calculationStage, supplyMonth, highLoadShare, lowLoadShare) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, loadProfileShare.getNetworkOwner().getNetworkOwnerId());
                statement.setString(2, loadProfileShare.getBalanceProvider().getBalanceProviderID());
                statement.setString(3, loadProfileShare.getProfileSettlementAreaID());
                statement.setString(4, loadProfileShare.getCalculationStage());
                statement.setDate(5, new Date(loadProfileShare.getSupplyMonth().getTime()));
                statement.setFloat(6, loadProfileShare.getHighLoadProfileShare());
                statement.setFloat(7, loadProfileShare.getLowLoadProfileShare());
            }

            statement.executeUpdate();

            message = "Success: Load Profile Share has been successfully received and saved.";

            connection.closeDBConnection();
        }
        catch (SQLException s){
            return "Failure [505]: SQL Exception. Load Profile Share wasn't saved successfully.";
        }
        catch(Exception e) {
        }

        return message;
    }

    /*@WebMethod(operationName = "takePreliminaryLoadProfileShare")
    @Oneway
    public void takePreliminaryLoadProfileShare(@WebParam(name = "loadProfileShare")
    LoadProfileShare loadProfileShare) {
    }
    */
}

