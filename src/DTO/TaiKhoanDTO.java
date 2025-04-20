/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class TaiKhoanDTO {
     private String strTenDangNhap;
    private String strMatKhau;
    private int iCapBac;
    private int iTrangThai;
    
    public TaiKhoanDTO() {
    }

    public TaiKhoanDTO(String strTenDangNhap, String strMatKhau, int iCapBac,int iTrangThai) {
        this.strTenDangNhap = strTenDangNhap;
        this.strMatKhau = strMatKhau;
        this.iCapBac = iCapBac;
        this.iTrangThai=iTrangThai;
    }

    public int getiTrangThai() {
        return iTrangThai;
    }

    public void setiTrangThai(int iTrangThai) {
        this.iTrangThai = iTrangThai;
    }

    public String getStrTenDangNhap() {
        return strTenDangNhap;
    }

    public void setStrTenDangNhap(String strTenDangNhap) {
        this.strTenDangNhap = strTenDangNhap;
    }

    public String getStrMatKhau() {
        return strMatKhau;
    }

    public void setStrMatKhau(String strMatKhau) {
        this.strMatKhau = strMatKhau;
    }

    public int getiCapBac() {
        return iCapBac;
    }

    public void setiCapBac(int iCapBac) {
        this.iCapBac = iCapBac;
    }
}
