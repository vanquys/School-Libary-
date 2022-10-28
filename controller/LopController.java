/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSII
 */
public class LopController {

    public String layTenTheoMaLop(String maLop) {
        
        String tenLop = null;
        String sql = "select ten from lop where malop=?";
        try {
            Connection conn = util.ConnectDB.connectSQLServer();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, maLop);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                tenLop = rs.getString(1);
            }
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tenLop;
    }

    public String layMaLopTheoTen(String tenLop) {
        String maLop = null;
        String sql = "select malop from lop where ten=?";
        try {
            ResultSet rs;
            try (Connection conn = util.ConnectDB.connectSQLServer(); PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, tenLop);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    maLop = rs.getString(1);
                } 
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maLop;
    }
}
