/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.LoaiDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 *
 * @author OS
 */
public class LoaiDAL {
    public ArrayList<LoaiDTO> getLoaiList() {
        ArrayList<LoaiDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblloai");
        try {
            while (result.next()) {
                list.add(new LoaiDTO(
                        result.getString("Maloai"),
                        result.getString("Tenloai")
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
     * Thêm một loại sản phẩm mới
     * @param loai Đối tượng loại sản phẩm cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertLoai(LoaiDTO loai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblloai");
        params.put("FIELD", "Maloai, Tenloai");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(loai.getStrMaloai());
        values.add(loai.getStrTenloai());
        
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Cập nhật thông tin loại sản phẩm
     * @param loai Đối tượng loại sản phẩm cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateLoai(LoaiDTO loai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblloai");
        params.put("WHERE", "Maloai = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Tenloai", loai.getStrTenloai());
        
        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(loai.getStrMaloai());
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Xóa một loại sản phẩm khỏi hệ thống
     * @param maLoai Mã loại cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteLoai(String maLoai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblloai");
        params.put("WHERE", "Maloai = ?");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maLoai);
        
        boolean success = helper.deleteData(values);
        helper.closeConnect();
        return success;
    }
}
