/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;

/**
 *
 * @author MSII
 */
public class AdminController {

    public int updateAdmin(Admin ad, String userName) {
        int result = 0;

        try {
            String sql = "update admin set username = '" + ad.getUsername() + "', password = '" + ad.getPassword() + "',ten = '" + ad.getTen() + "' where username = '" + userName + "'";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            result = stm.executeUpdate(sql);
            System.out.println(result);
            conn.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public Admin layAdminTuUserName(String userName) {
        Admin result = null;
        try {
            String sql = "select * from admin where username = '" + userName + "'";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                Admin admin = new Admin(rs.getString(1), rs.getString(2), rs.getString(3));
                result = admin;
                System.out.println(admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
