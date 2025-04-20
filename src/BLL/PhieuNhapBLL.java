/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.PhieuNhapDAL;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;

public class PhieuNhapBLL {
    private PhieuNhapDAL dal;
    private ArrayList<PhieuNhapDTO> danhSachPhieuNhap;

    public PhieuNhapBLL() {
        dal = new PhieuNhapDAL();
        danhSachPhieuNhap = dal.getListPhieuNhap();
    }

    // Lấy danh sách phiếu nhập
    public ArrayList<PhieuNhapDTO> getListPhieuNhap() {
        return danhSachPhieuNhap;
    }

    // Thêm phiếu nhập
    public boolean addPhieuNhap(PhieuNhapDTO pn) {
        if (dal.insertPhieuNhap(pn)) {
            danhSachPhieuNhap.add(pn); // Cập nhật danh sách tạm
            return true;
        }
        return false;
    }

    // Cập nhật phiếu nhập
    public boolean updatePhieuNhap(PhieuNhapDTO pn) {
        if (dal.updatePhieuNhap(pn)) {
            for (int i = 0; i < danhSachPhieuNhap.size(); i++) {
                if (danhSachPhieuNhap.get(i).getStrMaPN().equalsIgnoreCase(pn.getStrMaPN())) {
                    danhSachPhieuNhap.set(i, pn);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    // Xóa phiếu nhập
    public boolean deletePhieuNhap(PhieuNhapDTO pn) {
        if (dal.deletePhieuNhap(pn)) {
            danhSachPhieuNhap.removeIf(p -> p.getStrMaPN().equalsIgnoreCase(pn.getStrMaPN()));
            return true;
        }
        return false;
    }

    public boolean deletePhieuNhap(String maPN) {
        return dal.deletePhieuNhap(maPN);
    }

    // ✅ Tìm kiếm chính xác theo mã phiếu nhập
    public PhieuNhapDTO searchPhieuNhap(String maPN) {
        for (PhieuNhapDTO pn : danhSachPhieuNhap) {
            if (pn.getStrMaPN().equalsIgnoreCase(maPN)) {
                return pn;
            }
        }
        return null;
    }

    // ✅ Tìm kiếm nâng cao (từ khóa khớp mã phiếu, mã NCC hoặc mã NV)
    public ArrayList<PhieuNhapDTO> timKiemPhieuNhap(String keyword) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (PhieuNhapDTO pn : danhSachPhieuNhap) {
            if (pn.getStrMaPN().toLowerCase().contains(keyword) ||
                pn.getStrMaNCC().toLowerCase().contains(keyword) ||
                pn.getStrMaNV().toLowerCase().contains(keyword)) {
                ketQua.add(pn);
            }
        }
        return ketQua;
    }
    public boolean updateTongTien(String maPN, double tongtien){
        for(var tmp : danhSachPhieuNhap)
            if(tmp.getStrMaPN().equals(maPN)){
                tmp.setTongTien(tongtien);
                if(dal.updatePhieuNhap(tmp))
                    return true;
                else
                    return false;
            }
        return false;
    }
}
