/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.NhanVienDAL;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBLL {

    private NhanVienDAL dal;

    public NhanVienBLL() {
        dal = new NhanVienDAL();
    }

    public ArrayList<NhanVienDTO> getListNhanVien() {
        return dal.getListNhanVien();
    }

    public ArrayList<String> getAllMaNV() {
        return dal.getAllMaNV();
    }

    public boolean addNhanVien(NhanVienDTO nv) {
        return dal.insertNhanVien(nv);
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        return dal.updateNhanVien(nv);
    }

    public boolean deleteNhanVien(NhanVienDTO nv) {
        return dal.deleteNhanVien(nv);
    }

    public boolean deleteNhanVien(String maNV) {
        return dal.deleteNhanVien(maNV);
    }

    // Tìm theo mã nhân viên
    public NhanVienDTO searchNhanVienByMa(String maNV) {
        for (NhanVienDTO nv : dal.getListNhanVien()) {
            if (nv.getstrMaNV().equalsIgnoreCase(maNV)) {
                return nv;
            }
        }
        return null;
    }

    // Tìm theo tên
    public ArrayList<NhanVienDTO> searchNhanVienByTen(String ten) {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : dal.getListNhanVien()) {
            if (nv.getStrTen().toLowerCase().contains(ten.toLowerCase())) {
                result.add(nv);
            }
        }
        return result;
    }

    // Tìm theo giới tính
    public ArrayList<NhanVienDTO> searchNhanVienByGioiTinh(String gioiTinh) {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : dal.getListNhanVien()) {
            if (nv.getStrGioiTinh().equalsIgnoreCase(gioiTinh)) {
                result.add(nv);
            }
        }
        return result;
    }

    // Tìm theo khoảng lương
    public ArrayList<NhanVienDTO> searchNhanVienByLuong(int minLuong, int maxLuong) {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : dal.getListNhanVien()) {
            int luong = nv.getiLuong();
            if (luong >= minLuong && luong <= maxLuong) {
                result.add(nv);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> searchNhanVienByHoTen(String ten) {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : dal.getListNhanVien()) {
            if ((nv.getStrHo() + " " + nv.getStrTen()).toLowerCase().contains(ten.toLowerCase())) {
                result.add(nv);
            }
        }
        return result;
    }

    public boolean isNhanVienExist(String maNV) {
        for (NhanVienDTO nv : getListNhanVien()) {
            if (nv.getstrMaNV().equalsIgnoreCase(maNV)) {
                return true;
            }
        }
        return false;
    }
    public String searchEmailfromManv(String manv){
        for (NhanVienDTO nvdto:getListNhanVien()){
            if(nvdto.getstrMaNV().equals(manv)){
                return nvdto.getStrEmail();
            }
        }
        return "";
    }
}
