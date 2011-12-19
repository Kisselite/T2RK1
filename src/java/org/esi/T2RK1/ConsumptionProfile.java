/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

/**
 *
 * @author Shaz
 */
public class ConsumptionProfile {
    Integer profileCalculationAreaID;
    Integer consumedEnergy;

    public ConsumptionProfile() {

    }

    /**
     * @return the profileCalculationAreaID
     */
    public Integer getProfileCalculationAreaID() {
        return profileCalculationAreaID;
    }

    /**
     * @param profileCalculationAreaID the profileCalculationAreaID to set
     */
    public void setProfileCalculationAreaID(Integer profileCalculationAreaID) {
        this.profileCalculationAreaID = profileCalculationAreaID;
    }

    /**
     * @return the consumedEnergy
     */
    public Integer getConsumedEnergy() {
        return consumedEnergy;
    }

    /**
     * @param consumedEnergy the consumedEnergy to set
     */
    public void setConsumedEnergy(Integer consumedEnergy) {
        this.consumedEnergy = consumedEnergy;
    }


}
