/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PhieuMuon;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class PhieuMuonController {

    public int capNhatPM(PhieuMuon pm) {
        int result = 0;
        try {
            String sql = "Update PhieuMuon SET MaSV = ?,masach =?,soluong =?,  NgayMuon = ?, NgayHenTra  = ? , datra = ? WHERE MaPhieuMuon = ?";
            try (Connection con = ConnectDB.connectSQLServer(); CallableStatement ps = con.prepareCall(sql)) {
                ps.setString(1, pm.getMaSinhVien());
                ps.setString(2, pm.getMaSach());
                ps.setString(3, pm.getSoLuong());
                ps.setString(4, pm.getNgayMuon());
                ps.setString(5, pm.getNgayHetHan());
                ps.setBoolean(6, pm.isDaTra());
                ps.setString(7, pm.getMaPhieuMuon());
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int xoaPhieuMuon(String maPhieuMuon) {
        int result = 0;
        try {
            String sql = "DELETE FROM PhieuMuon WHERE MaPhieuMuon=?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maPhieuMuon);
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList listPhieuMuon() {
        String sql = "select * from PhieuMuon";
        ArrayList list = new ArrayList();
        try {
            Connection connection = util.ConnectDB.connectSQLServer();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new PhieuMuon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean themMoiPhieuMuon(PhieuMuon phieuMuon) {
        String sql = "Insert into PhieuMuon values(?,?,?,?,?,?)";
        try (Connection connection = util.ConnectDB.connectSQLServer(); PreparedStatement preparedStatement = connection.prepareCall(sql)) {
            preparedStatement.setString(1, phieuMuon.getMaSinhVien());
            preparedStatement.setString(2, phieuMuon.getMaSach());
            preparedStatement.setString(3, phieuMuon.getSoLuong());
            preparedStatement.setString(4, phieuMuon.getNgayMuon());
            preparedStatement.setString(5, phieuMuon.getNgayHetHan());
            preparedStatement.setBoolean(6, phieuMuon.isDaTra());

            int row = preparedStatement.executeUpdate();
            if (row != 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<PhieuMuon> layPMTheoMaSV(String maSv) {
        String sql = "select * from phieumuon where masv = '" + maSv + "' ";
        ArrayList<PhieuMuon> phieuMuons = new ArrayList<>();
        try {
            try (Connection conn = util.ConnectDB.connectSQLServer(); Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                while (rs.next()) {
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieuMuon(rs.getString(1));
                    pm.setMaSinhVien(rs.getString(2));
                    pm.setMaSach(rs.getString(3));
                    pm.setSoLuong(rs.getString(4));
                    pm.setNgayMuon(rs.getString(5));
                    pm.setNgayHetHan(rs.getString(6));
                    pm.setDaTra(rs.getBoolean(7));
                    phieuMuons.add(pm);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phieuMuons;
    }

    public ArrayList<PhieuMuon> searchPhieuMuon(String txtSearch) {

        ArrayList<PhieuMuon> list = new ArrayList<>();
        try {
            String sql = "Select * from phieumuon where maphieumuon like '%" + txtSearch + "%' or masv like '%" + txtSearch + "%' or masach like '%" + txtSearch + "%' ";
            try (Connection con = ConnectDB.connectSQLServer()) {
                Statement sttm = con.createStatement();
                ResultSet rs = sttm.executeQuery(sql);
                while (rs.next()) {
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieuMuon(rs.getString(1));
                    pm.setMaSinhVien(rs.getString(2));
                    pm.setMaSach(rs.getString(3));
                    pm.setSoLuong(rs.getString(4));
                    pm.setNgayMuon(rs.getString(5));
                    pm.setNgayHetHan(rs.getString(6));
                    pm.setDaTra(rs.getBoolean(7));
                    list.add(pm);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public PhieuMuon layPMTheoMaPM(String maPM) {
        String sql = "select * from phieumuon where maphieumuon = '" + maPM + "' ";
        PhieuMuon pm = new PhieuMuon();
        try {
            try (Connection conn = util.ConnectDB.connectSQLServer(); Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    pm.setMaPhieuMuon(rs.getString(1));
                    pm.setMaSinhVien(rs.getString(2));
                    pm.setMaSach(rs.getString(3));
                    pm.setSoLuong(rs.getString(4));
                    pm.setNgayMuon(rs.getString(5));
                    pm.setNgayHetHan(rs.getString(6));
                    pm.setDaTra(rs.getBoolean(7));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pm;
    }

    public String layMaPMCuoiCung() {
        String maPm = null;
        try {
            String sql = "SELECT maphieumuon from phieumuon where maphieumuon = ALL (SELECT MAX(maphieumuon) from phieumuon)";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                maPm = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maPm;
    }

    public int laySoSachDaMuonChuaTra(String maSach) {
        int result = 0;
        try {
            String sql = "select Soluong from phieumuon where masach = " + maSach + " and datra = 'false' ";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {                
                result += Integer.parseInt(rs.getString(1));
            }
          
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
}
