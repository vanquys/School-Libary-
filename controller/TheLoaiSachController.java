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
import model.TheLoaiSach;
import util.ConnectDB;

/**
 *
 * @author OS
 */
public class TheLoaiSachController {
    public String layTenTheoMa(String maTheLoai) {
        
        String tlSach = null;
        String sql = "select tentheloai from theloaisach where maTheLoai=?";
        try {
            Connection conn = util.ConnectDB.connectSQLServer();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, maTheLoai);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                tlSach = rs.getString(1);
            }
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tlSach;
    }
    public String layMaTheoTen(String tenTL) {
        
        String maTheLoai = null;
        String sql = "select maTheLoai from theloaisach where tenTheLoai=?";
        try {
            Connection conn = util.ConnectDB.connectSQLServer();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, tenTL);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                maTheLoai = rs.getString(1);
            }
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maTheLoai;
    }
    
    public ArrayList ListTheLoai(){
        String sql = "select * from TheLoaiSach";
        ArrayList list = new ArrayList();
        try{
            Connection connection = util.ConnectDB.connectSQLServer();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                list.add(new TheLoaiSach(rs.getString(1),rs.getString(2),rs.getString(3)));

            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    
    public int insert(TheLoaiSach tls)
    {
        try
        {
            String sql="Insert into TheLoaiSach values (?,?)";
            Connection con = util.ConnectDB.connectSQLServer();
            PreparedStatement ps =con.prepareStatement(sql);
                ps.setString(1, tls.getTenTheLoai());
                ps.setString(2, tls.getViTri());
             return ps.executeUpdate();
        }
        catch(Exception ex)
        {
            
        }
        return -1;
    }
    public int update(TheLoaiSach tls)
    {
        try
        {
            String sql="Update TheLoaiSach set TenTheLoai = ? , ViTri = ? Where MaTheLoai = ?";
            Connection con = util.ConnectDB.connectSQLServer();
            PreparedStatement ps =con.prepareStatement(sql);
                ps.setString(1, tls.getTenTheLoai());
                ps.setString(2, tls.getViTri());
                ps.setString(3, tls.getMaTheLoai());
             return ps.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return -1;
    }
    public boolean xoaDuLieuTheLoai(String maTheLoai){
        String sql = "delete from TheLoaiSach where MaTheLoai = '"+ maTheLoai+"'";
        try (Connection connection = util.ConnectDB.connectSQLServer(); Statement stmt = connection.createStatement()){
           int row= stmt.executeUpdate(sql);
            if(row !=0){
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return false;
    }
    public ArrayList<TheLoaiSach> SearchTen(String tenTheLoaiSach)
    {
        ArrayList<TheLoaiSach> lists = new ArrayList<TheLoaiSach>();
        try
        {
            String sql="SELECT * FROM THELOAISACH WHERE TenTheLoai like N'%" + tenTheLoaiSach +  "%' or MaTheLoai like '%" + tenTheLoaiSach + "%' or ViTri like N'%" + tenTheLoaiSach + "%'" ;
            Connection con = util.ConnectDB.connectSQLServer();
            Statement statement= con.createStatement();
            ResultSet rs =statement.executeQuery(sql);
          while(rs.next())
          {
            TheLoaiSach tls = new TheLoaiSach();
            tls.setMaTheLoai(rs.getString(1));
            tls.setTenTheLoai(rs.getString(2));
            tls.setViTri(rs.getString(3));
            lists.add(tls);          
          }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return lists;
    }

    
}
