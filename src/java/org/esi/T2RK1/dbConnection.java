/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esi.T2RK1;

import java.sql.*;

/**
 *
 * @author Shaz
 */
public class dbConnection {
    private Connection conn = null;
    
    private String url = "jdbc:mysql://ats.cs.ut.ee:3306/";
    private String dbName = "esi_shazia";
    private String driver = "com.mysql.jdbc.Driver";
    private String userName = "shazia";
    private String password = "f24XOtra";
    

    
    /*private String url = "jdbc:mysql://localhost:3307/";
    private String dbName = "t2rk1";
    private String driver = "com.mysql.jdbc.Driver";
    private String userName = "root";
    private String password = "root";
    */

    public dbConnection(){
    }

    public dbConnection(String url, String dbName, String userName, String password){
        this();

        this.url = url;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public void openDBConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
            try {
                Class.forName(this.getDriver()).newInstance();
                this.setConn(DriverManager.getConnection(this.getUrl() + this.getDbName(), this.getUserName(), this.getPassword()));
            }
            catch(Exception e ) {
                e.printStackTrace();
            }
    }

    public void closeDBConnection() throws SQLException{
        this.getConn().close();
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
