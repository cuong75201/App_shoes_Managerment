package DAL;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.HoaDonDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class HoaDonDAL {

    public ArrayList<HoaDonDTO> getHoaDonList() {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "Trangthai = 1");
        
        helper.buildingQueryParam(params);
        ResultSet result = helper.executeQuery();
        
        try {
            while (result.next()) {
                list.add(new HoaDonDTO(
                        result.getString("MaHD"),
                        result.getString("MaNV"),
                        result.getString("MaKH"),
                        result.getString("MaKM"),
                        result.getString("NgayBan"),
                        result.getDouble("TongTien"),
                        result.getInt("Trangthai")
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
        params.put("WHERE", "MaKH = ? AND Trangthai = 1");
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
                        result.getDouble("TongTien"),
                        result.getInt("Trangthai")
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
        params.put("WHERE", "NgayBan BETWEEN ? AND ? AND Trangthai = 1");
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
                        result.getDouble("TongTien"),
                        result.getInt("Trangthai")
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
        params.put("FIELD", "MaHD, MaNV, MaKH, MaKM, NgayBan, TongTien, Trangthai");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(hoaDon.getStrMaHD());
        values.add(hoaDon.getStrMaNV());
        values.add(hoaDon.getStrMaKH());
        values.add(hoaDon.getStrMaKM());
        values.add(hoaDon.getStrNgayBan());
        values.add(hoaDon.getTongTien());
        values.add(1); // Mặc định trạng thái là 1 (đang hoạt động)

        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ? AND Trangthai = 1");
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
        params.put("WHERE", "MaHD = ? AND Trangthai = 1");
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
        // Thay vì xóa thật sự, ta chỉ cập nhật trạng thái
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ? AND Trangthai = 1");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Trangthai", 0);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maHD);

        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    // Phương thức xóa thật sự nếu cần
    public boolean hardDeleteHoaDon(String maHD) {
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
        params.put("WHERE", "MaHD = ? AND Trangthai = 1");

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
    
    /**
     * Khôi phục hóa đơn đã xóa (soft delete)
     * @param maHD Mã hóa đơn cần khôi phục
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean restoreHoaDon(String maHD) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "MaHD = ? AND Trangthai = 0");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Trangthai", 1);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maHD);

        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Lấy danh sách hóa đơn đã xóa (soft delete)
     * @return ArrayList chứa danh sách hóa đơn đã xóa
     */
    public ArrayList<HoaDonDTO> getDeletedHoaDonList() {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblhoadon");
        params.put("WHERE", "Trangthai = 0");
        params.put("OTHER", "ORDER BY NgayBan DESC");
        
        helper.buildingQueryParam(params);
        ResultSet result = helper.executeQuery();
        
        try {
            while (result.next()) {
                list.add(new HoaDonDTO(
                        result.getString("MaHD"),
                        result.getString("MaNV"),
                        result.getString("MaKH"),
                        result.getString("MaKM"),
                        result.getString("NgayBan"),
                        result.getDouble("TongTien"),
                        result.getInt("Trangthai")
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
}