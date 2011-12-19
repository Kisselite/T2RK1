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
public class ConsumptionPlan {
    ElectricitySupplier electricitySupplier;
    Date dated;
    Float plannedConsumption;

    public ConsumptionPlan() {
        electricitySupplier = new ElectricitySupplier();
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
     * @return the dated
     */
    public Date getDated() {
        return dated;
    }

    /**
     * @param dated the dated to set
     */
    public void setDated(Date dated) {
        this.dated = dated;
    }

    /**
     * @return the plannedConsumption
     */
    public Float getPlannedConsumption() {
        return plannedConsumption;
    }

    /**
     * @param plannedConsumption the plannedConsumption to set
     */
    public void setPlannedConsumption(Float plannedConsumption) {
        this.plannedConsumption = plannedConsumption;
    }
}
