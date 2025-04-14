/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.KhachHangDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;


/**
 *
 * @author OS
 */
public class KhachHangDAL {
     public ArrayList<KhachHangDTO> getKhachHangList() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblkhachhang");
        try {
            while (result.next()) {
                list.add(new KhachHangDTO(
                        result.getString("MaKH"),
                        result.getString("Ho"),
                        result.getString("Ten"),
                        result.getString("GioiTinh"),
                        result.getString("DiaChi"),
                        result.getString("Email"),
                        result.getString("Loai"),
                        result.getDouble("TongChiTieu")
                ));
            }
            result.close();
            helper.closeConnect();
                    return list;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    /**
     * Lấy khách hàng theo mã
     * @param maKH Mã khách hàng
     * @return KhachHangDTO đối tượng khách hàng nếu tìm thấy, null nếu không tìm thấy
     */
    public KhachHangDTO getKhachHangById(String maKH) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "MaKH = ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maKH);
        
        ResultSet result = helper.querywithParam(values);
        try {
            if (result.next()) {
                KhachHangDTO khachHang = new KhachHangDTO(
                        result.getString("MaKH"),
                        result.getString("Ho"),
                        result.getString("Ten"),
                        result.getString("GioiTinh"),
                        result.getString("DiaChi"),
                        result.getString("Email"),
                        result.getString("Loai"),
                        result.getDouble("TongChiTieu")
                );
                result.close();
                helper.closeConnect();
                return khachHang;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        helper.closeConnect();
        return null;
    }
    
    /**
     * Tìm kiếm khách hàng theo tên
     * @param ten Tên khách hàng cần tìm
     * @return ArrayList chứa các khách hàng tìm thấy
     */
    public ArrayList<KhachHangDTO> searchKhachHangByName(String ten) {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "Ten LIKE ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add("%" + ten + "%");
        
        ResultSet result = helper.querywithParam(values);
        try {
            while (result.next()) {
                list.add(new KhachHangDTO(
                        result.getString("MaKH"),
                        result.getString("Ho"),
                        result.getString("Ten"),
                        result.getString("GioiTinh"),
                        result.getString("DiaChi"),
                        result.getString("Email"),
                        result.getString("Loai"),
                        result.getDouble("TongChiTieu")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    /**
     * Tìm kiếm khách hàng theo loại
     * @param loai Loại khách hàng cần tìm
     * @return ArrayList chứa các khách hàng tìm thấy
     */
    public ArrayList<KhachHangDTO> getKhachHangByType(String loai) {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "Loai = ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(loai);
        
        ResultSet result = helper.querywithParam(values);
        try {
            while (result.next()) {
                list.add(new KhachHangDTO(
                        result.getString("MaKH"),
                        result.getString("Ho"),
                        result.getString("Ten"),
                        result.getString("GioiTinh"),
                        result.getString("DiaChi"),
                        result.getString("Email"),
                        result.getString("Loai"),
                        result.getDouble("TongChiTieu")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    /**
     * Thêm một khách hàng mới
     * @param khachHang ĐốI tượng khách hàng cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertKhachHang(KhachHangDTO khachHang) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhachhang");
        params.put("FIELD", "MaKH, Ho, Ten, GioiTinh, DiaChi, Email, Loai, TongChiTieu");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(khachHang.getStrMaKH());
        values.add(khachHang.getStrHo());
        values.add(khachHang.getStrTen());
        values.add(khachHang.getStrGioiTinh());
        values.add(khachHang.getStrDiaChi());
        values.add(khachHang.getStrEmail());
        values.add(khachHang.getStrLoai());
        values.add(khachHang.getiTongChiTieu());
        
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Cập nhật thông tin khách hàng
     * @param khachHang Đối tượng khách hàng cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateKhachHang(KhachHangDTO khachHang) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "MaKH = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Ho", khachHang.getStrHo());
        updateValues.put("Ten", khachHang.getStrTen());
        updateValues.put("GioiTinh", khachHang.getStrGioiTinh());
        updateValues.put("DiaChi", khachHang.getStrDiaChi());
        updateValues.put("Email", khachHang.getStrEmail());
        updateValues.put("Loai", khachHang.getStrLoai());
        updateValues.put("TongChiTieu", khachHang.getiTongChiTieu());
        
        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(khachHang.getStrMaKH());
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Cập nhật tổng chi tiêu của khách hàng
     * @param maKH Mã khách hàng
     * @param tongChiTieu Tổng chi tiêu mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTongChiTieu(String maKH, double tongChiTieu) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "MaKH = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("TongChiTieu", tongChiTieu);
        
        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maKH);
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Xóa một khách hàng khỏi hệ thống
     * @param maKH Mã khách hàng cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteKhachHang(String maKH) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "MaKH = ?");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maKH);
        
        boolean success = helper.deleteData(values);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Kiểm tra sự tồn tại của khách hàng
     * @param maKH Mã khách hàng cần kiểm tra
     * @return true nếu khách hàng tồn tại, false nếu không tồn tại
     */
    public boolean checkKhachHangExists(String maKH) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "COUNT(*) as count");
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "MaKH = ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maKH);
        
        ResultSet rs = helper.querywithParam(values);
        try {
            if (rs != null && rs.next()) {
                boolean exists = rs.getInt("count") > 0;
                rs.close();
                helper.closeConnect();
                return exists;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        helper.closeConnect();
        return false;
    }
    
    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email Email cần kiểm tra
     * @return true nếu email đã tồn tại, false nếu chưa tồn tại
     */
    public boolean checkEmailExists(String email) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "COUNT(*) as count");
        params.put("TABLE", "tblkhachhang");
        params.put("WHERE", "Email = ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(email);
        
        ResultSet rs = helper.querywithParam(values);
        try {
            if (rs != null && rs.next()) {
                boolean exists = rs.getInt("count") > 0;
                rs.close();
                helper.closeConnect();
                return exists;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        helper.closeConnect();
        return false;
    }
}
