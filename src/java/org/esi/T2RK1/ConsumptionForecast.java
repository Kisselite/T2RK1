package org.esi.T2RK1;

import java.util.Date;

/**
 * 
 */

/**
 * @author KK
 *
 */
public class ConsumptionForecast {
	Float forecastedConsumption;
        Date dated;
	ElectricitySupplier electricitySupplier;
	
	public ConsumptionForecast () {
		electricitySupplier = new ElectricitySupplier();
	}

    /**
     * @return the forecastedConsumption
     */
    public Float getForecastedConsumption() {
        return forecastedConsumption;
    }

    /**
     * @param forecastedConsumption the forecastedConsumption to set
     */
    public void setForecastedConsumption(Float forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
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
}
