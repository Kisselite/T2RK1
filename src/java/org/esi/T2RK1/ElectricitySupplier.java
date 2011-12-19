/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esi.T2RK1;

/**
 *
 * @author Shaz
 */
public class ElectricitySupplier {

    String electricitySupplierID;
    String constraintAreaID;

    public ElectricitySupplier() {
    }

    /**
     * @return the electricitySupplierID
     */
    public String getElectricitySupplierID() {
        return electricitySupplierID;
    }

    /**
     * @param electricitySupplierID the electricitySupplierID to set
     */
    public void setElectricitySupplierID(String electricitySupplierID) {
        this.electricitySupplierID = electricitySupplierID;
    }

    /**
     * @return the constraintAreaID
     */
    public String getConstraintAreaID() {
        return constraintAreaID;
    }

    /**
     * @param constraintAreaID the constraintAreaID to set
     */
    public void setConstraintAreaID(String constraintAreaID) {
        this.constraintAreaID = constraintAreaID;
    }
}
