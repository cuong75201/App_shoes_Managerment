/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.HoaDonDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;


/**
 *
 * @author OS
 */

public class HoaDonDAL {

    public ArrayList<HoaDonDTO> getHoaDonList() {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblhoadon");
        try {
            while (result.next()) {
                list.add(new HoaDonDTO(
                        result.getString("MaHD"),
                        result.getString("MaNV"),
                        result.getString("MaKH"),
                        result.getString("MaKM"),
                        result.getString("NgayBan"),
                        result.getDouble("TongTien")
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
    
    public ArrayList<HoaDonDTO> getHoaDonByCustomerId(String maKH) {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaKH = ?");
        params.put("OTHER", "ORDER BY NgayBan DESC");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maKH);
        
        ResultSet result = helper.querywithParam(values);
        try {
            while (result.next()) {
                list.add(new HoaDonDTO(
                        result.getString("MaHD"),
                        result.getString("MaNV"),
                        result.getString("MaKH"),
                        result.getString("MaKM"),
                        result.getString("NgayBan"),
                        result.getDouble("TongTien")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    public ArrayList<HoaDonDTO> getHoaDonByDateRange(String startDate, String endDate) {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "NgayBan BETWEEN ? AND ?");
        params.put("OTHER", "ORDER BY NgayBan DESC");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(startDate);
        values.add(endDate);
        
        ResultSet result = helper.querywithParam(values);
        try {
            while (result.next()) {
                list.add(new HoaDonDTO(
                        result.getString("MaHD"),
                        result.getString("MaNV"),
                        result.getString("MaKH"),
                        result.getString("MaKM"),
                        result.getString("NgayBan"),
                        result.getDouble("TongTien")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public boolean insertHoaDon(HoaDonDTO hoaDon) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("FIELD", "MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(hoaDon.getStrMaHD());
        values.add(hoaDon.getStrMaNV());
        values.add(hoaDon.getStrMaKH());
        values.add(hoaDon.getStrMaKM());
        values.add(hoaDon.getStrNgayBan());
        values.add(hoaDon.getTongTien());
        
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("MaNV", hoaDon.getStrMaNV());
        updateValues.put("MaKH", hoaDon.getStrMaKH());
        updateValues.put("MaKM", hoaDon.getStrMaKM());
        updateValues.put("NgayBan", hoaDon.getStrNgayBan());
        updateValues.put("TongTien", hoaDon.getTongTien());

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(hoaDon.getStrMaHD());
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    public boolean updateTongTien(String maHD, double tongTien) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ?");
        helper.buildingQueryParam(params);
        
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("TongTien", tongTien);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maHD);
        
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }

    public boolean deleteHoaDon(String maHD) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ?");
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maHD);
        
        boolean success = helper.deleteData(values);
        helper.closeConnect();
        return success;
    }
    
    public boolean checkHoaDonExists(String maHD) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "COUNT(*) as count");
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ?");
        
        helper.buildingQueryParam(params);
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(maHD);
        
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