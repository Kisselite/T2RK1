/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import java.text.ParseException;

/**
 *
 * @author Shaz
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        RuritaniaKrado rk = new RuritaniaKrado();

        rk.receiveDailyMeteredData("12", "12", "2011-12-10 10:55:32", "2011-12-10 10:55:32", "U12", "12.5", "S15", "15.8");
    }

}
