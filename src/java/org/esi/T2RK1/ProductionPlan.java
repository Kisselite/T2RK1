/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

/**
 *
 * @author Shaz
 */
public class ProductionPlan {
    ElectricitySupplier electricitySupplier;
    Integer plannedProduction;

    public ProductionPlan(){
        this.electricitySupplier = new ElectricitySupplier();
    }
}
