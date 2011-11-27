/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author Shaz
 */
@WebService()
@Stateless()
public class RuritaniaKrado {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getQualitySeries")
    public QualitySeries sendQualitySeries() {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getBalanceInvoice")
    public BalanceInvoice getBalanceInvoice(@WebParam(name = "supplierID")
    int supplierID) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getConsumptionProfile")
    public ConsumptionProfile getConsumptionProfile(@WebParam(name = "supplierID")
    int supplierID) {
        //TODO write your implementation code here:
        return null;
    }

}
