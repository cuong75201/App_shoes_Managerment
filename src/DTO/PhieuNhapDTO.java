/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class PhieuNhapDTO {
    String strMaPN, strMaNCC, strMaNV, strNgayNhap;
    double tongTien;
    private int trangthai;

    public PhieuNhapDTO() {
    }

    
    
    public PhieuNhapDTO(String strMaPN, String strMaNCC, String strMaNV, String strNgayNhap, double tongTien) {
        this.strMaPN = strMaPN;
        this.strMaNCC = strMaNCC;
        this.strMaNV = strMaNV;
        this.strNgayNhap = strNgayNhap;
        this.tongTien = tongTien;
        this.trangthai = 1;
    }

    public String getStrMaPN() {
        return strMaPN;
    }

    public void setStrMaPN(String strMaPN) {
        this.strMaPN = strMaPN;
    }

    public String getStrMaNCC() {
        return strMaNCC;
    }

    public void setStrMaNCC(String strMaNCC) {
        this.strMaNCC = strMaNCC;
    }

    public String getStrMaNV() {
        return strMaNV;
    }

    public void setStrMaNV(String strMaNV) {
        this.strMaNV = strMaNV;
    }

    public String getStrNgayNhap() {
        return strNgayNhap;
    }

    public void setStrNgayNhap(String strNgayNhap) {
        this.strNgayNhap = strNgayNhap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" + "strMaPN=" + strMaPN + ", strMaNCC=" + strMaNCC + ", strMaNV=" + strMaNV + ", strNgayNhap=" + strNgayNhap + ", tongTien=" + tongTien + ", trangthai=" + trangthai +  '}';
    }
}
