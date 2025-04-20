/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.HoaDonDAL;
import DTO.HoaDonDTO;
import java.util.ArrayList;

/**
 *
 * @author OS
 */
public class HoaDonBLL {
    private HoaDonDAL hoaDonDAL;
    
    public HoaDonBLL() {
        hoaDonDAL = new HoaDonDAL();
    }
    
    /**
     * Lấy danh sách tất cả hóa đơn
     * @return ArrayList chứa danh sách hóa đơn
     */
    public ArrayList<HoaDonDTO> getHoaDonList() {
        return hoaDonDAL.getHoaDonList();
    }
    
    /**
     * Lấy danh sách hóa đơn theo mã khách hàng
     * @param maKH Mã khách hàng
     * @return ArrayList chứa danh sách hóa đơn của khách hàng
     */
    public ArrayList<HoaDonDTO> getHoaDonByCustomerId(String maKH) {
        return hoaDonDAL.getHoaDonByCustomerId(maKH);
    }
    
    /**
     * Lấy danh sách hóa đơn trong khoảng thời gian
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return ArrayList chứa danh sách hóa đơn
     */
    public ArrayList<HoaDonDTO> getHoaDonByDateRange(String startDate, String endDate) {
        return hoaDonDAL.getHoaDonByDateRange(startDate, endDate);
    }
    
    /**
     * Thêm một hóa đơn mới
     * @param hoaDon Đối tượng hóa đơn cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hóa đơn
        if (hoaDon == null) {
            return false;
        }
        
        // Kiểm tra mã hóa đơn đã tồn tại chưa
        if (hoaDonDAL.checkHoaDonExists(hoaDon.getStrMaHD())) {
            return false;
        }
        
        return hoaDonDAL.insertHoaDon(hoaDon);
    }
    
    /**
     * Cập nhật thông tin hóa đơn
     * @param hoaDon Đối tượng hóa đơn cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hóa đơn
        if (hoaDon == null) {
            return false;
        }
        
        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(hoaDon.getStrMaHD())) {
            return false;
        }
        
        return hoaDonDAL.updateHoaDon(hoaDon);
    }
    
    /**
     * Cập nhật tổng tiền của hóa đơn
     * @param maHD Mã hóa đơn
     * @param tongTien Tổng tiền mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTongTien(String maHD, double tongTien) {
        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(maHD)) {
            return false;
        }
        
        return hoaDonDAL.updateTongTien(maHD, tongTien);
    }
    
    /**
     * Xóa một hóa đơn khỏi hệ thống
     * @param maHD Mã hóa đơn cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteHoaDon(String maHD) {
        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(maHD)) {
            return false;
        }
        
        return hoaDonDAL.deleteHoaDon(maHD);
    }
    
    /**
     * Kiểm tra sự tồn tại của hóa đơn
     * @param maHD Mã hóa đơn cần kiểm tra
     * @return true nếu hóa đơn tồn tại, false nếu không tồn tại
     */
    public boolean checkHoaDonExists(String maHD) {
        return hoaDonDAL.checkHoaDonExists(maHD);
    }
    
    public String generateNewMaHD() {
    // Lấy danh sách hóa đơn hiện có
    ArrayList<HoaDonDTO> listHD = getHoaDonList();
    
    // Nếu không có hóa đơn nào, bắt đầu với HD001
    if (listHD.isEmpty()) {
        return "HD001";
    }
    
    // Tìm mã hóa đơn lớn nhất
    int maxId = 0;
    for (HoaDonDTO hd : listHD) {
        String maHD = hd.getStrMaHD();
        if (maHD.startsWith("HD")) {
            try {
                int id = Integer.parseInt(maHD.substring(2));
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException e) {
                // Bỏ qua nếu định dạng không phù hợp
            }
        }
    }
    
    // Tạo mã mới với số hiệu tiếp theo
    return String.format("HD%03d", maxId + 1);
}
}