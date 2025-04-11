/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.KhuyenMaiDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author OS
 */
public class KhuyenMaiDAL {
     /**
     * Lấy danh sách tất cả khuyến mãi từ cơ sở dữ liệu
     * @return ArrayList chứa tất cả khuyến mãi
     */
    public ArrayList<KhuyenMaiDTO> getKhuyenMaiList() {
        ArrayList<KhuyenMaiDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblkhuyenmai");
        try {
            while (result.next()) {
                list.add(new KhuyenMaiDTO(
                        result.getString("MaKM"),
                        result.getString("TenChuongTrinh"),
                        result.getString("LoaiChuongTrinh"),
                        result.getString("DieuKien"),
                        result.getString("NgayBatDau"),
                        result.getString("NgayKetThuc")
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
     * Thêm một khuyến mãi mới
     * @param khuyenMai Đối tượng khuyến mãi cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertKhuyenMai(KhuyenMaiDTO khuyenMai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhuyenmai");
        params.put("FIELD", "MaKM, TenChuongTrinh, LoaiChuongTrinh, DieuKien, NgayBatDau, NgayKetThuc");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(khuyenMai.getStrMaKM());
        values.add(khuyenMai.getStrTenChuongTrinh());
        values.add(khuyenMai.getStrLoaiChuongTrinh());
        values.add(khuyenMai.getStrDieuKien());
        values.add(khuyenMai.getStrNgayBatDau());
        values.add(khuyenMai.getStrNgayKetThuc());
        
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Cập nhật thông tin khuyến mãi
     * @param khuyenMai Đối tượng khuyến mãi cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateKhuyenMai(KhuyenMaiDTO khuyenMai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhuyenmai");
        params.put("WHERE", "MaKM = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("TenChuongTrinh", khuyenMai.getStrTenChuongTrinh());
        updateValues.put("LoaiChuongTrinh", khuyenMai.getStrLoaiChuongTrinh());
        updateValues.put("DieuKien", khuyenMai.getStrDieuKien());
        updateValues.put("NgayBatDau", khuyenMai.getStrNgayBatDau());
        updateValues.put("NgayKetThuc", khuyenMai.getStrNgayKetThuc());
        
        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(khuyenMai.getStrMaKM());
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Xóa một khuyến mãi khỏi hệ thống
     * @param maKM Mã khuyến mãi cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteKhuyenMai(String maKM) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblkhuyenmai");
        params.put("WHERE", "MaKM = ?");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maKM);
        
        boolean success = helper.deleteData(values);
        helper.closeConnect();
        return success;
    }
}
