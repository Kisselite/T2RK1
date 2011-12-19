package org.esi.T2RK1;

import java.util.Date;

/**
 * 
 */

/**
 * @author KK
 *
 */
public class LoadProfile {
    /*
    The total of all the consumption within a profile calculation area which is not
    metered and balance settled per hour and which is thus to be profile settled.
    Calculated for each hour and constituting the total of all input in the area minus
    the consumption that is metered and balance settled per hour.

    At present, the network losses are included in the load profile.  The load profile is
    thus the same as what is referred to as unspecified consumption.
    */

    Integer loadProfile;
    Date date;
    Date hour;
    Float hourlyLoad;
    NetworkOwner networkOwner;

    public LoadProfile() {
	this.networkOwner = new NetworkOwner();
    }

    /**
     * @return the loadProfile
     */
    public Integer getLoadProfile() {
        return loadProfile;
    }

    /**
     * @param loadProfile the loadProfile to set
     */
    public void setLoadProfile(Integer loadProfile) {
        this.loadProfile = loadProfile;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the hour
     */
    public Date getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(Date hour) {
        this.hour = hour;
    }

    /**
     * @return the hourlyLoad
     */
    public Float getHourlyLoad() {
        return hourlyLoad;
    }

    /**
     * @param hourlyLoad the hourlyLoad to set
     */
    public void setHourlyLoad(Float hourlyLoad) {
        this.hourlyLoad = hourlyLoad;
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
}
