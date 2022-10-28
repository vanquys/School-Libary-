drop database QLTV
go 
create database QLTV
go 
use QLTV
create table Admin (
	Username varchar(50) primary key,
	Password varchar(50),
	Ten nvarchar(50)
)


Create table Lop(
	MaLop varchar(10) primary key ,
	Ten varchar(30)
)
Create table SinhVien(
	MaSV varchar(10) primary key,
	Password varchar(50),
	MaLop varchar(10),
	HoTen nvarchar(50),
	NgaySinh date,
	GioiTinh bit,
	DiaChi nvarchar(50),
	SDT varchar(11),
	Email nvarchar(50)
)
Create table TheLoaiSach(
	MaTheLoai varchar(10) primary key,
	TenTheLoai nvarchar(50),
	ViTri nvarchar(50)
)

Create table PhieuMuon(
	MaPhieuMuon varchar(10) primary key,
	MaSV varchar(10),
	MaSach varchar(10),
	SoLuong int,
	NgayMuon date DEFAULT GETDATE(),
	NgayHenTra date DEFAULT (GETDATE() + 10),
	DaTra bit
	
)



Create table Sach(
	MaSach varchar(10) primary key,
	TenSach nvarchar(50),
	MaTheLoai varchar(10),
	TacGia nvarchar(50),
	SoLuong int,
	MaNXB varchar(10) ,
	NgayNhap date,
	NDTT nvarchar(100),
)
