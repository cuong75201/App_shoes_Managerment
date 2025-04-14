/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class NhanVienDTO {
      private String strMaNV;
    private String strHo;
    private String strTen;
    private String strGioiTinh;
    private String strDiaChi;
    private String strEmail;
    private String strChucVu;
    private String iDienThoai;
    private int iLuong;
    private String strAnh;

    public NhanVienDTO() {
    }

    public NhanVienDTO(String strMaNV, String strHo, String strTen, String strGioiTinh, String strDiaChi, String strEmail, String strChucVu, String iDienThoai, int iLuong, String strAnh) {
        this.strMaNV = strMaNV;
        this.strHo = strHo;
        this.strTen = strTen;
        this.strGioiTinh = strGioiTinh;
        this.strDiaChi = strDiaChi;
        this.strEmail = strEmail;
        this.strChucVu = strChucVu;
        this.iDienThoai = iDienThoai;
        this.iLuong = iLuong;
        this.strAnh = strAnh;
    }

    public String getStrChucVu() {
        return strChucVu;
    }

    public void setStrChucVu(String strChucVu) {
        this.strChucVu = strChucVu;
    }
    
    public String getstrMaNV() {
        return strMaNV;
    }

    public void setstrMaNV(String strMaNV) {
        this.strMaNV = strMaNV;
    }

    public String getStrHo() {
        return strHo;
    }

    public void setStrHo(String strHo) {
        this.strHo = strHo;
    }

    public String getStrTen() {
        return strTen;
    }

    public void setStrTen(String strTen) {
        this.strTen = strTen;
    }

    public String getStrGioiTinh() {
        return strGioiTinh;
    }

    public void setStrGioiTinh(String strGioiTinh) {
        this.strGioiTinh = strGioiTinh;
    }

    public String getStrDiaChi() {
        return strDiaChi;
    }

    public void setStrDiaChi(String strDiaChi) {
        this.strDiaChi = strDiaChi;
    }

    public String getiDienThoai() {
        return iDienThoai;
    }

    public void setiDienThoai(String iDienThoai) {
        this.iDienThoai = iDienThoai;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public int getiLuong() {
        return iLuong;
    }

    public void setiLuong(int iLuong) {
        this.iLuong = iLuong;
    }

    public String getStrAnh() {
        return strAnh;
    }

    public void setStrAnh(String strAnh) {
        this.strAnh = strAnh;
    }
    
}
