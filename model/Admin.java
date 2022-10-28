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
public class Admin {
//    Username varchar(50) primary key,
//	Password varchar(50),
//	Ten nvarchar(50)
    
    String username;
    String password;
    String ten;

    public Admin(String username, String password, String ten) {
        this.username = username;
        this.password = password;
        this.ten = ten;
    }

    public Admin() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    
}
