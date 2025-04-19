/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class ChiTietPNDTO {
    String strMaPN, strMaGiay;
    int iSoLuong, iGiaNhap;
    private int trangthai;

    public ChiTietPNDTO(String strMaPN, String strMaGiay, int iSoLuong, int iGiaNhap) {
        this.strMaPN = strMaPN;
        this.strMaGiay = strMaGiay;
        this.iSoLuong = iSoLuong;
        this.iGiaNhap = iGiaNhap;
        this.trangthai = 1;
    }

    public ChiTietPNDTO() {
    }

    @Override
    public String toString() {
        return "ChiTietPNDTO{" + "strMaPN=" + strMaPN + ", strMaGiay=" + strMaGiay + ", iSoLuong=" + iSoLuong + ", iGiaNhap=" + iGiaNhap + '}';
    }

    public String getStrMaPN() {
        return strMaPN;
    }

    public void setStrMaPN(String strMaPN) {
        this.strMaPN = strMaPN;
    }

    public String getStrMaGiay() {
        return strMaGiay;
    }

    public void setStrMaGiay(String strMaGiay) {
        this.strMaGiay = strMaGiay;
    }

    public int getiSoLuong() {
        return iSoLuong;
    }

    public void setiSoLuong(int iSoLuong) {
        this.iSoLuong = iSoLuong;
    }

    public int getiGiaNhap() {
        return iGiaNhap;
    }

    public void setiGiaNhap(int iGiaNhap) {
        this.iGiaNhap = iGiaNhap;
    }
    
    public static int maSPTangdan(ChiTietPNDTO a, ChiTietPNDTO b){
        return a.getStrMaGiay().compareTo(b.getStrMaGiay());
    }
}
