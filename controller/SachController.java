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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PhieuMuon;
import model.Sach;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class SachController {

    public ArrayList<Sach> danhSachTatCaSach() {
        ArrayList<Sach> lists = new ArrayList<Sach>();
        try {
            String sql = "SELECT * FROM Sach";
            try (Connection con = ConnectDB.connectSQLServer(); Statement stm = con.createStatement();) {
                ResultSet rs = stm.executeQuery(sql);
                while (rs.next()) {
                    Sach sv = new Sach();
                    sv.setMaSach(rs.getString(1));
                    sv.setTenSach(rs.getString(2));
                    sv.setMaTheLoai(rs.getString(3));
                    sv.setTacGia(rs.getString(4));
                    sv.setSoLuong(rs.getString(5));
                    sv.setMaNXB(rs.getString(6));
                    sv.setNgay(rs.getString(7));
                    sv.setNoiDung(rs.getString(8));
                    lists.add(sv);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    public int insert(Sach sach) {
        int result = 0;

        try {

            String sql = "Insert into sach values (?,?,?,?,?,?,?)";

            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                
                
                ps.setString(1, sach.getTenSach());
                ps.setString(2, sach.getMaTheLoai());
                ps.setString(3, sach.getTacGia());
                ps.setString(4, sach.getSoLuong());
                ps.setString(5, sach.getMaNXB());
                ps.setString(6, sach.getNgay());
                ps.setString(7, sach.getNoiDung());
                 result = ps.executeUpdate();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(Sach sach) {

        int result = 0;
        try {
            String sql = "update Sach set tensach=? , matheloai=?,tacgia=?,soluong=?,manxb=?,ngaynhap=?,ndtt=? where masach=?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, sach.getTenSach());
                ps.setString(2, sach.getMaTheLoai());
                ps.setString(3, sach.getTacGia());
                ps.setString(4, sach.getSoLuong());
                ps.setString(5, sach.getMaNXB());
                ps.setString(6, sach.getNgay());
                ps.setString(7, sach.getNoiDung());
                ps.setString(8, sach.getMaSach());
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(String masach) {
        int result = 0;
        try {

            String sql = "delete from sach where masach=?";
            try (Connection con = ConnectDB.connectSQLServer(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, masach);
                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public String layTenTheoMa(String maSach) {
        String result = null;
        try {
            String sql = "select tensach form sach where masach = " + maSach + "";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<Sach> searchSach(String tensach) {
        ArrayList<Sach> list = new ArrayList<>();
        try {
            String sql = "Select * from sach where tensach like N'%" + tensach + "%' or matheloai like '%" + tensach + "%' or masach like '%" + tensach + "%' ";
            try (Connection con = ConnectDB.connectSQLServer()) {
                Statement sttm = con.createStatement();
                ResultSet rs = sttm.executeQuery(sql);

                while (rs.next()) {
                    Sach sach = new Sach();
                    sach.setMaSach(rs.getString(1));
                    sach.setTenSach(rs.getString(2));
                    sach.setMaTheLoai(rs.getString(3));
                    sach.setTacGia(rs.getString(4));
                    sach.setSoLuong(rs.getString(5));
                    sach.setMaNXB(rs.getString(6));
                    sach.setNgay(rs.getString(7));
                    sach.setNoiDung(rs.getString(8));

                    list.add(sach);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public ArrayList<Sach> searchSachTheoTen(String tensach) {
        ArrayList<Sach> list = new ArrayList<>();
        try {
            String sql = "Select * from sach where tenhsach like N'%" + tensach + "%'";
            try (Connection con = ConnectDB.connectSQLServer()) {
                Statement sttm = con.createStatement();
                ResultSet rs = sttm.executeQuery(sql);

                while (rs.next()) {
                    Sach sach = new Sach();
                    sach.setMaSach(rs.getString(1));
                    sach.setTenSach(rs.getString(2));
                    sach.setMaTheLoai(rs.getString(3));
                    sach.setTacGia(rs.getString(4));
                    sach.setSoLuong(rs.getString(5));
                    sach.setMaNXB(rs.getString(6));
                    sach.setNgay(rs.getString(7));
                    sach.setNoiDung(rs.getString(8));
                    list.add(sach);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public Sach laySachTheoMa(String maSach) {
        Sach result = null;
        try {
            String sql = "select * from sach where masach = " + maSach + "";
            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                result = new Sach(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7), rs.getString(6), rs.getString(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

//    public int bienDongSach(String maPm) {
//        int result = 0;
//        try {
//            String sql = "SELECT phieumuon.SoLuong as 'soluongPM', Sach.MaSach as 'maSach',datra FROM PhieuMuon LEFT JOIN Sach ON PhieuMuon.MaSach = sach.MaSach where PhieuMuon.maphieumuon = " + maPm + "";
//            Connection conn = util.ConnectDB.connectSQLServer();
//            Statement stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            PhieuMuonController pmc = new PhieuMuonController();
//            PhieuMuon pm = pmc.layPMTheoMaPM(maPm);
//            Sach sach = laySachTheoMa(pm.getMaSach());
//
//            String soLuong = null;
//
//            while (rs.next()) {
//                if (rs.getBoolean(3)) {
//                    int slPM = Integer.parseInt(rs.getString(1));
//                    int slSach = Integer.parseInt(sach.getSoLuong());
//                    soLuong = String.valueOf(slPM + slSach);
//                    System.out.println("btsl = " + soLuong);
//
//                    result = 1;
//                } else {
//                    int slPM = Integer.parseInt(rs.getString(1));
//                    int slSach = Integer.parseInt(sach.getSoLuong());
//                    soLuong = String.valueOf(slSach - slPM);
//                    System.out.println("btsl 2: "+soLuong );
//
//                    result = -1;
//                }
//                sach.setSoLuong(soLuong);
//                update(sach);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
}
