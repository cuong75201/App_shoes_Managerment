/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.KhuyenMaiDAL;
import DTO.KhuyenMaiDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author OS
 */
public class KhuyenMaiBLL {
    private KhuyenMaiDAL khuyenMaiDAL;
    
    public KhuyenMaiBLL() {
        khuyenMaiDAL = new KhuyenMaiDAL();
    }
    
    /**
     * Lấy danh sách tất cả khuyến mãi
     * @return ArrayList chứa danh sách khuyến mãi
     */
    public ArrayList<KhuyenMaiDTO> getKhuyenMaiList() {
        return khuyenMaiDAL.getKhuyenMaiList();
    }
    
    /**
     * Thêm một khuyến mãi mới
     * @param khuyenMai Đối tượng khuyến mãi cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertKhuyenMai(KhuyenMaiDTO khuyenMai) {
        // Kiểm tra dữ liệu khuyến mãi
        if (khuyenMai == null) {
            return false;
        }
        
        // Kiểm tra tính hợp lệ của ngày bắt đầu và ngày kết thúc
        if (!isValidDateRange(khuyenMai.getStrNgayBatDau(), khuyenMai.getStrNgayKetThuc())) {
            return false;
        }
        
        return khuyenMaiDAL.insertKhuyenMai(khuyenMai);
    }
    
    /**
     * Cập nhật thông tin khuyến mãi
     * @param khuyenMai Đối tượng khuyến mãi cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateKhuyenMai(KhuyenMaiDTO khuyenMai) {
        // Kiểm tra dữ liệu khuyến mãi
        if (khuyenMai == null) {
            return false;
        }
        
        // Kiểm tra tính hợp lệ của ngày bắt đầu và ngày kết thúc
        if (!isValidDateRange(khuyenMai.getStrNgayBatDau(), khuyenMai.getStrNgayKetThuc())) {
            return false;
        }
        
        return khuyenMaiDAL.updateKhuyenMai(khuyenMai);
    }
    
    /**
     * Xóa một khuyến mãi khỏi hệ thống
     * @param maKM Mã khuyến mãi cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteKhuyenMai(String maKM) {
        return khuyenMaiDAL.deleteKhuyenMai(maKM);
    }
    
    /**
     * Lấy danh sách khuyến mãi đang hoạt động (ngày hiện tại nằm trong khoảng ngày bắt đầu và ngày kết thúc)
     * @return ArrayList chứa danh sách khuyến mãi đang hoạt động
     */
    public ArrayList<KhuyenMaiDTO> getActiveKhuyenMaiList() {
        ArrayList<KhuyenMaiDTO> allKhuyenMai = khuyenMaiDAL.getKhuyenMaiList();
        ArrayList<KhuyenMaiDTO> activeKhuyenMai = new ArrayList<>();
        
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        for (KhuyenMaiDTO km : allKhuyenMai) {
            try {
                Date startDate = dateFormat.parse(km.getStrNgayBatDau());
                Date endDate = dateFormat.parse(km.getStrNgayKetThuc());
                
                if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                    activeKhuyenMai.add(km);
                }
            } catch (ParseException e) {
                // Bỏ qua khuyến mãi có định dạng ngày không hợp lệ
            }
        }
        
        return activeKhuyenMai;
    }
    
    /**
     * Kiểm tra tính hợp lệ của khoảng thời gian khuyến mãi
     * @param startDateStr Ngày bắt đầu
     * @param endDateStr Ngày kết thúc
     * @return true nếu hợp lệ, false nếu không hợp lệ
     */
    private boolean isValidDateRange(String startDateStr, String endDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            
            // Kiểm tra ngày bắt đầu phải trước hoặc bằng ngày kết thúc
            return !startDate.after(endDate);
        } catch (ParseException e) {
            return false;
        }
    }
}