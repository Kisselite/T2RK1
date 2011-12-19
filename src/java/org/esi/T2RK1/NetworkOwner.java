package org.esi.T2RK1;


/**
 * 
 * @author �������
 *
 */

public class NetworkOwner {
    public String networkOwnerId;
    public String constraintAreaId;
    
    public NetworkOwner(){

    }

    /**
     * @return the networkOwnerId
     */
    public String getNetworkOwnerId() {
        return networkOwnerId;
    }

    /**
     * @param networkOwnerId the networkOwnerId to set
     */
    public void setNetworkOwnerId(String networkOwnerId) {
        this.networkOwnerId = networkOwnerId;
    }

    /**
     * @return the constraintAreaId
     */
    public String getConstraintAreaId() {
        return constraintAreaId;
    }

    /**
     * @param constraintAreaId the constraintAreaId to set
     */
    public void setConstraintAreaId(String constraintAreaId) {
        this.constraintAreaId = constraintAreaId;
    }
}
