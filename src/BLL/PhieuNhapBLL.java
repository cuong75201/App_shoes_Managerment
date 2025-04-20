/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.PhieuNhapDAL;
import DTO.PhieuNhapDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            if (pn.getStrMaPN().toLowerCase().contains(keyword)
                    || pn.getStrMaNCC().toLowerCase().contains(keyword)
                    || pn.getStrMaNV().toLowerCase().contains(keyword)) {
                ketQua.add(pn);
            }
        }
        return ketQua;
    }

    public int getTongTientheoNam(int namCanLay) {
        int tong = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        for (PhieuNhapDTO pn : getListPhieuNhap()) {
            try {
                LocalDate date = LocalDate.parse(pn.getStrNgayNhap(), formatter);
                int nam = date.getYear();

                if (nam == namCanLay) {
                    tong += pn.getTongTien();
                }

            } catch (Exception e) {
                System.out.println("Lỗi định dạng ngày cho mã phiếu: " + pn.getStrMaPN());
            }
        }

        return tong;

    }

    public int getTongTienTheoThang(int thang, int nam) {
        double tong = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();

        for (PhieuNhapDTO pn : danhSachPhieuNhap) {
            try {
                Date date = sdf.parse(pn.getStrNgayNhap());
                cal.setTime(date);
                int thangGD = cal.get(Calendar.MONTH) + 1;
                int namGD = cal.get(Calendar.YEAR);

                if (thangGD == thang && namGD == nam) {
                    tong += pn.getTongTien();
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày: " + pn.getStrNgayNhap());
            }
        }

        return (int) tong; // ép kiểu về int
    }

    public int getTongTienTheoNgay(String ngayCanTinh) {
       SimpleDateFormat sdfInput = new SimpleDateFormat("d/M/yyyy");        // Format ngày người dùng nhập
        SimpleDateFormat sdfData  = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH); // Format trong dữ liệu

        double tong = 0;

        try {
            Date dateCanTinh = sdfInput.parse(ngayCanTinh);

            for (PhieuNhapDTO pn : danhSachPhieuNhap) {
                Date datePhieu = sdfData.parse(pn.getStrNgayNhap());

                if (datePhieu.equals(dateCanTinh)) {
                    tong += pn.getTongTien();
                }
            }

        } catch (ParseException e) {
            System.out.println("❌ Lỗi định dạng ngày: " + e.getMessage());
        }

        return (int) tong;
    }
    public int getTongTienByMaNCC(String Ma){
        double sum=0;
        for(PhieuNhapDTO pndto:danhSachPhieuNhap){
            if(pndto.getStrMaNCC().equals(Ma)){
                sum+=pndto.getTongTien();
            }
        }
        return (int) sum;
    }
}
