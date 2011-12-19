/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author Shaz
 */
public class T2ES2RESTful {
    static String balanceInvoiceURL = "http://t2es2.mcrlabs.net/balance/invoice";
    static String consumptionInfoURL = "http://t2es2.mcrlabs.net/balance/consumption_info";

    public static String submitAnnualConsumptionReport(String startDate, String endDate, int amount) {
        String message = null;
        
         try {
             // Construct data
             String data = URLEncoder.encode("startDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8");
             data += "&" + URLEncoder.encode("endDate", "UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8");
             data += "&" + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(amount), "UTF-8");

             // Send data
             URL url = new URL(consumptionInfoURL);
             URLConnection conn = url.openConnection();
             conn.setDoOutput(true);
             OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
             wr.write(data);
             wr.flush();

             // Get the response
             if (conn != null && conn.getInputStream() != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader rd = new BufferedReader(inputStreamReader);

                 String line;

                 while ((line = rd.readLine()) != null) {
                     message = line;
                 }

                 rd.close();
             }
             else {
                 message = "Input Stream from T2ES2 is null!";
             }

             wr.close();
         }
         catch (IOException e) {
             message = "An attempt to open the file denoted by pathname - http://t2es2.mcrlabs.net/balance/consumption_info (specified in T2ES2's emailed documentation) has failed. ";
         }
         catch (Exception e) {
             e.printStackTrace();
         }
        
        return message;
    }

     public static String submitBalanceInvoice(String startDate, String endDate, int amount) {
         String message = null;

         try {
             // Construct data
             String data = URLEncoder.encode("startDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8");
             data += "&" + URLEncoder.encode("endDate", "UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8");
             data += "&" + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(amount), "UTF-8");

             // Send data
             URL url = new URL(balanceInvoiceURL);
             URLConnection conn = url.openConnection();
             conn.setDoOutput(true);
             OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
             wr.write(data);
             wr.flush();

             // Get the response
             if (conn != null && conn.getInputStream() != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader rd = new BufferedReader(inputStreamReader);

                 String line;

                 while ((line = rd.readLine()) != null) {
                     message = line;
                 }
                 
                 rd.close();
             }
             else {
                 message = "Input Stream from T2ES2 is null!";
             }

             wr.close();
         }
         catch (IOException e) {
             message = "An attempt to open the file denoted by pathname - http://t2es2.mcrlabs.net/balance/invoice (specified in T2ES2's emailed documentation) has failed. ";
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         
         return message;
     }
}
