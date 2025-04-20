/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.KhachHangDAL;
import DTO.KhachHangDTO;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author OS
 */
public class KhachHangBLL {
    private KhachHangDAL khachHangDAL;
    
    public KhachHangBLL() {
        khachHangDAL = new KhachHangDAL();
    }
    
    /**
     * Lấy danh sách tất cả khách hàng
     * @return ArrayList chứa danh sách khách hàng
     */
    public ArrayList<KhachHangDTO> getKhachHangList() {
        return khachHangDAL.getKhachHangList();
    }
    
    /**
     * Lấy thông tin khách hàng theo mã
     * @param maKH Mã khách hàng
     * @return Đối tượng khách hàng, null nếu không tìm thấy
     */
    public KhachHangDTO getKhachHangById(String maKH) {
        return khachHangDAL.getKhachHangById(maKH);
    }
    
    /**
     * Tìm kiếm khách hàng theo tên
     * @param ten Tên khách hàng cần tìm
     * @return ArrayList chứa các khách hàng tìm thấy
     */
    public ArrayList<KhachHangDTO> searchKhachHangByName(String ten) {
        return khachHangDAL.searchKhachHangByName(ten);
    }
    
    /**
     * Tìm kiếm khách hàng theo loại
     * @param loai Loại khách hàng cần tìm
     * @return ArrayList chứa các khách hàng tìm thấy
     */
    public ArrayList<KhachHangDTO> getKhachHangByType(String loai) {
        return khachHangDAL.getKhachHangByType(loai);
    }
    
    /**
     * Thêm một khách hàng mới
     * @param khachHang Đối tượng khách hàng cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertKhachHang(KhachHangDTO khachHang) {
        // Kiểm tra dữ liệu khách hàng
        if (khachHang == null) {
            return false;
        }
        
        // Kiểm tra mã khách hàng đã tồn tại chưa
        if (khachHangDAL.checkKhachHangExists(khachHang.getStrMaKH())) {
            return false;
        }
        
        // Kiểm tra email hợp lệ
        if (!isValidEmail(khachHang.getStrEmail())) {
            return false;
        }
        
        // Kiểm tra email đã tồn tại chưa
        if (khachHangDAL.checkEmailExists(khachHang.getStrEmail())) {
            return false;
        }
        
        return khachHangDAL.insertKhachHang(khachHang);
    }
    
    /**
     * Cập nhật thông tin khách hàng
     * @param khachHang Đối tượng khách hàng cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateKhachHang(KhachHangDTO khachHang) {
        // Kiểm tra dữ liệu khách hàng
        if (khachHang == null) {
            return false;
        }
        
        // Kiểm tra mã khách hàng có tồn tại không
        if (!khachHangDAL.checkKhachHangExists(khachHang.getStrMaKH())) {
            return false;
        }
        
        // Kiểm tra email hợp lệ
        if (!isValidEmail(khachHang.getStrEmail())) {
            return false;
        }
        
        // Lấy thông tin khách hàng hiện tại
        KhachHangDTO currentKH = khachHangDAL.getKhachHangById(khachHang.getStrMaKH());
        
        // Kiểm tra nếu email đã thay đổi và email mới đã tồn tại
        if (!khachHang.getStrEmail().equals(currentKH.getStrEmail()) && 
            khachHangDAL.checkEmailExists(khachHang.getStrEmail())) {
            return false;
        }
        
        return khachHangDAL.updateKhachHang(khachHang);
    }
    
    /**
     * Cập nhật tổng chi tiêu của khách hàng
     * @param maKH Mã khách hàng
     * @param tongChiTieu Tổng chi tiêu mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTongChiTieu(String maKH, double tongChiTieu) {
        // Kiểm tra mã khách hàng có tồn tại không
        if (!khachHangDAL.checkKhachHangExists(maKH)) {
            return false;
        }
        
        return khachHangDAL.updateTongChiTieu(maKH, tongChiTieu);
    }
    
    /**
     * Xóa một khách hàng khỏi hệ thống
     * @param maKH Mã khách hàng cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteKhachHang(String maKH) {
        // Kiểm tra mã khách hàng có tồn tại không
        if (!khachHangDAL.checkKhachHangExists(maKH)) {
            return false;
        }
        
        return khachHangDAL.deleteKhachHang(maKH);
    }
    
    /**
     * Kiểm tra sự tồn tại của khách hàng
     * @param maKH Mã khách hàng cần kiểm tra
     * @return true nếu khách hàng tồn tại, false nếu không tồn tại
     */
    public boolean checkKhachHangExists(String maKH) {
        return khachHangDAL.checkKhachHangExists(maKH);
    }
    
    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email Email cần kiểm tra
     * @return true nếu email đã tồn tại, false nếu chưa tồn tại
     */
    public boolean checkEmailExists(String email) {
        return khachHangDAL.checkEmailExists(email);
    }
    
    /**
     * Kiểm tra email có hợp lệ không
     * @param email Email cần kiểm tra
     * @return true nếu email hợp lệ, false nếu không hợp lệ
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
     public ArrayList<KhachHangDTO> searchKhachHangByHoTen(String ten) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO nv : getKhachHangList()) {
            if ((nv.getStrHo()+" "+nv.getStrTen()).toLowerCase().contains(ten.toLowerCase())) {
                result.add(nv);
            }
        }
        return result;
    }
}