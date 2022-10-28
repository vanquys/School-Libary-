/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ADMIN
 */
public class NhaXuatBan {
    String maNXB;
    String ten;
    String diaChi;
    String email;

    public NhaXuatBan() {
    }

    public NhaXuatBan(String maNXB, String ten, String diaChi, String email) {
        this.maNXB = maNXB;
        this.ten = ten;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
