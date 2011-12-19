package org.esi.T2RK1;

import java.util.Date;

/**
 * 
 */

/**
 * @author KK
 *
 */
public class LoadProfileShare {
    /*
    The share of a load profile that is calculated prior to the supply month.
    Preliminary load profile shares for the supply month are based on:

    • historical metered data for the load profile
    • annual consumption by the electricity user to be profile settled
    • the electricity user/supplier relationships in force during the supply month.
    */

    NetworkOwner networkOwner;
    ElectricitySupplier electricitySupplier;
    BalanceProvider balanceProvider;
    String profileSettlementAreaID;
    String calculationStage;
    Date supplyMonth;
    Float lowLoadProfileShare;
    Float highLoadProfileShare;

    public LoadProfileShare() {
	this.networkOwner = new NetworkOwner();
        this.electricitySupplier = new ElectricitySupplier();
        this.balanceProvider = new BalanceProvider();
    }

    /**
     * @return the networkOwner
     */
    public NetworkOwner getNetworkOwner() {
        return networkOwner;
    }

    /**
     * @param networkOwner the networkOwner to set
     */
    public void setNetworkOwner(NetworkOwner networkOwner) {
        this.networkOwner = networkOwner;
    }

    /**
     * @return the electricitySupplier
     */
    public ElectricitySupplier getElectricitySupplier() {
        return electricitySupplier;
    }

    /**
     * @param electricitySupplier the electricitySupplier to set
     */
    public void setElectricitySupplier(ElectricitySupplier electricitySupplier) {
        this.electricitySupplier = electricitySupplier;
    }

    /**
     * @return the balanceProvider
     */
    public BalanceProvider getBalanceProvider() {
        return balanceProvider;
    }

    /**
     * @param balanceProvider the balanceProvider to set
     */
    public void setBalanceProvider(BalanceProvider balanceProvider) {
        this.balanceProvider = balanceProvider;
    }

    /**
     * @return the calculationStage
     */
    public String getCalculationStage() {
        return calculationStage;
    }

    /**
     * @param calculationStage the calculationStage to set
     */
    public void setCalculationStage(String calculationStage) {
        this.calculationStage = calculationStage;
    }

    /**
     * @return the supplyMonth
     */
    public Date getSupplyMonth() {
        return supplyMonth;
    }

    /**
     * @param supplyMonth the supplyMonth to set
     */
    public void setSupplyMonth(Date supplyMonth) {
        this.supplyMonth = supplyMonth;
    }

    /**
     * @return the lowLoadProfileShare
     */
    public Float getLowLoadProfileShare() {
        return lowLoadProfileShare;
    }

    /**
     * @param lowLoadProfileShare the lowLoadProfileShare to set
     */
    public void setLowLoadProfileShare(Float lowLoadProfileShare) {
        this.lowLoadProfileShare = lowLoadProfileShare;
    }

    /**
     * @return the highloadProfileShare
     */
    public Float getHighLoadProfileShare() {
        return highLoadProfileShare;
    }

    /**
     * @param highloadProfileShare the highloadProfileShare to set
     */
    public void setHighLoadProfileShare(Float highloadProfileShare) {
        this.highLoadProfileShare = highloadProfileShare;
    }

    /**
     * @return the profileSettlementAreaID
     */
    public String getProfileSettlementAreaID() {
        return profileSettlementAreaID;
    }

    /**
     * @param profileSettlementAreaID the profileSettlementAreaID to set
     */
    public void setProfileSettlementAreaID(String profileSettlementAreaID) {
        this.profileSettlementAreaID = profileSettlementAreaID;
    }
	
}
