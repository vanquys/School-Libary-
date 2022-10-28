/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class SinhVien {
//    MaSV varchar(10) primary key,
//	Password varchar(50),
//	MaLop varchar(10),
//	HoTen nvarchar(50),
//	NgaySinh date,
//	GioiTinh bit,
//	DiaChi nvarchar(50),
//	SDT varchar(11),
//	Email nvarchar(50)
    String maSv;
    String password;
    String maLop;
    String hoTen;
    String ngaySinh;
    Boolean gioiTinh;
    String diaChi;
    String soDienThoai;
    String email;

    public SinhVien() {
    }

    public SinhVien(String maSv, String password, String maLop, String hoTen, String ngaySinh, Boolean gioiTinh, String diaChi, String soDienThoai, String email) {
        this.maSv = maSv;
        this.password = password;
        this.maLop = maLop;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public String getMaSv() {
        return maSv;
    }

    public void setMaSv(String maSv) {
        this.maSv = maSv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
