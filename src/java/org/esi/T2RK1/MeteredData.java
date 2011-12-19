/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import java.util.Date;

/**
 *
 * @author Shaz
 */
public class MeteredData {
    Date startDate;
    Date endDate;
    
    NetworkOwner networkOwner;
    EntryPoint suppliedEnergy;
    ExitPoint consumedEnergy;

    //To keep things simple: ExchangePoint exchangedEnergy;

    public MeteredData(){
        this.networkOwner = new NetworkOwner();
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * @return the suppliedEnergy
     */
    public EntryPoint getSuppliedEnergy() {
        return suppliedEnergy;
    }

    /**
     * @param suppliedEnergy the suppliedEnergy to set
     */
    public void setSuppliedEnergy(EntryPoint suppliedEnergy) {
        this.suppliedEnergy = suppliedEnergy;
    }

    /**
     * @return the consumedEnergy
     */
    public ExitPoint getConsumedEnergy() {
        return consumedEnergy;
    }

    /**
     * @param consumedEnergy the consumedEnergy to set
     */
    public void setConsumedEnergy(ExitPoint consumedEnergy) {
        this.consumedEnergy = consumedEnergy;
    }
}
