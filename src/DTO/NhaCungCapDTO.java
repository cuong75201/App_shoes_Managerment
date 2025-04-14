/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cuong
 */
public class NhaCungCapDTO {
     String strMaNCC, strTeNCC, strDiaChi, strEmail;

    public NhaCungCapDTO() {
    }

    public NhaCungCapDTO(String strMaNCC, String strTeNCC, String strDiaChi, String strEmail) {
        this.strMaNCC = strMaNCC;
        this.strTeNCC = strTeNCC;
        this.strDiaChi = strDiaChi;
        this.strEmail = strEmail;
    }

    public String getStrMaNCC() {
        return strMaNCC;
    }

    public void setStrMaNCC(String strMaNCC) {
        this.strMaNCC = strMaNCC;
    }

    public String getStrTenNCC() {
        return strTenNCC;
    }

    public void setStrTeNCC(String strTeNCC) {
        this.strTeNCC = strTeNCC;
    }

    public String getStrDiaChi() {
        return strDiaChi;
    }

    public void setStrDiaChi(String strDiaChi) {
        this.strDiaChi = strDiaChi;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    @Override
    public String toString() {
        return "NhaCungCapDTO{" + "strMaNCC=" + strMaNCC + ", strTenNCC=" + strTenNCC + ", strDiaChi=" + strDiaChi + ", strEmail=" + strEmail + '}';
    }
    
}
