/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.BaoMat;

/**
 *
 * @author ADMIN
 */
public class LoginController {

    public boolean dangNhapSinhVien(String sdt, String password) {
        boolean result = false;
        try {
            String sql = "select SDT, Password from sinhvien where sdt=? and password=?";
            try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
                prepareStatement.setString(1, sdt);
                util.BaoMat baomat = new BaoMat();
                prepareStatement.setString(2, baomat.encryptPass(password));
                try (ResultSet rs = prepareStatement.executeQuery()) {
                    if (rs.next()) {
                        result = true;
                    }
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return result;
    }
    
     public boolean dangNhapAdmin(String username, String password) {
        boolean result = false;
        try {
            String sql = "select username, Password from admin where username=? and password=?";
            try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
                prepareStatement.setString(1, username);
                util.BaoMat baomat = new BaoMat();
                prepareStatement.setString(2, baomat.encryptPass(password));
                try (ResultSet rs = prepareStatement.executeQuery()) {
                    if (rs.next()) {
                        result = true;
                    }
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return result;
    }

}
