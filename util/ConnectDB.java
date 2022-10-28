/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thaond
 */
public class ConnectDB {

    static String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String DB_URL = "jdbc:sqlserver://localhost;";
    static String DATABASENAME = "databaseName=QLTV;";
    static String USER = "user=sa;";
    static String PASS = "password=123";

    public static Connection connectSQLServer() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
