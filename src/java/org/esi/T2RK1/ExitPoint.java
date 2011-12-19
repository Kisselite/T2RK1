/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

/**
 *
 * @author Shaz
 */
public class ExitPoint {
    String electricityUserID;
    Float energyConsumed;

    public ExitPoint() {

    }

    /**
     * @return the electricityUserID
     */
    public String getElectricityUserID() {
        return electricityUserID;
    }

    /**
     * @param electricityUserID the electricityUserID to set
     */
    public void setElectricityUserID(String electricityUserID) {
        this.electricityUserID = electricityUserID;
    }

    /**
     * @return the energyConsumed
     */
    public Float getEnergyConsumed() {
        return energyConsumed;
    }

    /**
     * @param energyConsumed the energyConsumed to set
     */
    public void setEnergyConsumed(Float energyConsumed) {
        this.energyConsumed = energyConsumed;
    }
}
