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
public class MeteredData {
    Date startDate;
    Date endDate;
    
    NetworkOwner networkOwner;
    EntryPoint[] suppliedEnergy;
    ExitPoint[] consumedEnergy;

    //To keep things simple: ExchangePoint[] exchangedEnergy;

    MeteredData(){

    }

    
}
