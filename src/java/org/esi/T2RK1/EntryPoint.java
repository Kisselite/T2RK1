package org.esi.T2RK1;

/**
 * 
 */

/**
 * @author KK
 *
 */
public class EntryPoint {

	ElectricitySupplier electricitySupplier;
	Float suppliedEnergy;
	
	public EntryPoint() {
            this.electricitySupplier = new ElectricitySupplier();
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
     * @return the suppliedEnergy
     */
    public Float getSuppliedEnergy() {
        return suppliedEnergy;
    }

    /**
     * @param suppliedEnergy the suppliedEnergy to set
     */
    public void setSuppliedEnergy(Float suppliedEnergy) {
        this.suppliedEnergy = suppliedEnergy;
    }
}
