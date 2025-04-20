/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class SanPhamDTO {
    String strMaGiay, strTenGiay, strDoiTuongSD, strChatLieu,strMaLoai
            , strMaxx, strMaMau, strMaThuongHieu;
    int iSoLuong, iGia, iSize,iTrangthai;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String strMaGiay, String strTenGiay, String strDoiTuongSD, String strChatLieu, String strMaLoai, String strMaxx, String strMaMau, String strMaThuongHieu, int iSoLuong, int iGia, int iSize,int itrangthai) {
        this.strMaGiay = strMaGiay;
        this.strTenGiay = strTenGiay;
        this.strDoiTuongSD = strDoiTuongSD;
        this.strChatLieu = strChatLieu;
        this.strMaLoai = strMaLoai;
        this.strMaxx = strMaxx;
        this.strMaMau = strMaMau;
        this.strMaThuongHieu = strMaThuongHieu;
        this.iSoLuong = iSoLuong;
        this.iGia = iGia;
        this.iSize = iSize;
        this.iTrangthai=itrangthai;
    }

    public int getiTrangthai() {
        return iTrangthai;
    }

    public void setiTrangthai(int iTrangthai) {
        this.iTrangthai = iTrangthai;
    }

    public String getStrMaGiay() {
        return strMaGiay;
    }

    public void setStrMaGiay(String strMaGiay) {
        this.strMaGiay = strMaGiay;
    }

    public String getStrTenGiay() {
        return strTenGiay;
    }

    public void setStrTenGiay(String strTenGiay) {
        this.strTenGiay = strTenGiay;
    }

    public String getStrDoiTuongSD() {
        return strDoiTuongSD;
    }

    public void setStrDoiTuongSD(String strDoiTuongSD) {
        this.strDoiTuongSD = strDoiTuongSD;
    }

    public String getStrChatLieu() {
        return strChatLieu;
    }

    public void setStrChatLieu(String strChatLieu) {
        this.strChatLieu = strChatLieu;
    }

    public String getStrMaLoai() {
        return strMaLoai;
    }

    public void setStrMaLoai(String strMaLoai) {
        this.strMaLoai = strMaLoai;
    }

    public String getStrMaxx() {
        return strMaxx;
    }

    public void setStrMaxx(String strMaxx) {
        this.strMaxx = strMaxx;
    }

    public String getStrMaMau() {
        return strMaMau;
    }

    public void setStrMaMau(String strMaMau) {
        this.strMaMau = strMaMau;
    }

    public String getStrMaThuongHieu() {
        return strMaThuongHieu;
    }

    public void setStrMaThuongHieu(String strMaThuongHieu) {
        this.strMaThuongHieu = strMaThuongHieu;
    }

    public int getiSoLuong() {
        return iSoLuong;
    }

    public void setiSoLuong(int iSoLuong) {
        this.iSoLuong = iSoLuong;
    }

    public int getiGia() {
        return iGia;
    }

    public void setiGia(int iGia) {
        this.iGia = iGia;
    }

    public int getiSize() {
        return iSize;
    }

    public void setiSize(int iSize) {
        this.iSize = iSize;
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" + "strMaGiay=" + strMaGiay + ", strTenGiay=" + strTenGiay + ", strDoiTuongSD=" + strDoiTuongSD + ", strChatLieu=" + strChatLieu + ", strMaLoai=" + strMaLoai + ", strMaxx=" + strMaxx + ", strMaMau=" + strMaMau + ", strMaThuongHieu=" + strMaThuongHieu + ", iSoLuong=" + iSoLuong + ", iGia=" + iGia + ", iSize=" + iSize + ", iTrangthai=" + iTrangthai + '}';
    }


    
    
}
