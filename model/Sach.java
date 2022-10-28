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
public class Sach {
    String maSach;
    String tenSach;
    String maTheLoai;
    String tacGia;
    String soLuong;
    String ngay;
    String maNXB;
    String noiDung;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String maTheLoai, String tacGia, String soLuong, String ngay, String maNXB, String noiDung) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTheLoai = maTheLoai;
        this.tacGia = tacGia;
        this.soLuong = soLuong;
        this.ngay = ngay;
        this.maNXB = maNXB;
        this.noiDung = noiDung;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    
}
