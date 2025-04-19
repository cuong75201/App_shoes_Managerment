package DAL;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.SanPhamDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class SanPhamDAL {

    public ArrayList<SanPhamDTO> getProductList() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblsanpham");
        try {
            while (result.next()) {
                list.add(new SanPhamDTO(
                        result.getString("Magiay"),
                        result.getString("Tengiay"),
                        result.getString("Doituongsd"),
                        result.getString("Chatlieu"),
                        result.getString("Maloai"),
                        result.getString("Maxx"),
                        result.getString("Mamau"),
                        result.getString("Mathuonghieu"),
                        result.getInt("Soluong"),
                        result.getInt("Gia"),
                        result.getInt("Size"),
                        result.getInt("trangthai")
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

    public boolean InsertProduct(SanPhamDTO sanpham) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblsanpham");
        params.put("FIELD", "Magiay,Tengiay,Soluong,Gia,Size,Doituongsd,Chatlieu,Maloai,Maxx,MaMau,Mathuonghieu,trangthai");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(sanpham.getStrMaGiay());
        values.add(sanpham.getStrTenGiay());
        values.add(sanpham.getiSoLuong());
        values.add(sanpham.getiGia());
        values.add(sanpham.getiSize());
        values.add(sanpham.getStrDoiTuongSD());
        values.add(sanpham.getStrChatLieu());
        values.add(sanpham.getStrMaLoai());
        values.add(sanpham.getStrMaxx());
        values.add(sanpham.getStrMaMau());
        values.add(sanpham.getStrMaThuongHieu());
        values.add(sanpham.getiTrangthai());
        return helper.insertData(values);
    }

    public boolean UpdateProduct(SanPhamDTO sanpham) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Magiay", sanpham.getStrMaGiay());
        updateValues.put("Tengiay", sanpham.getStrTenGiay());
        updateValues.put("Soluong", sanpham.getiSoLuong());
        updateValues.put("Gia", sanpham.getiGia());
        updateValues.put("Size", sanpham.getiSize());
        updateValues.put("Doituongsd", sanpham.getStrDoiTuongSD());
        updateValues.put("Chatlieu", sanpham.getStrChatLieu());
        updateValues.put("Maloai", sanpham.getStrMaLoai());
        updateValues.put("Maxx", sanpham.getStrMaxx());
        updateValues.put("MaMau", sanpham.getStrMaMau());
        updateValues.put("Mathuonghieu", sanpham.getStrMaThuongHieu());
        updateValues.put("trangthai", sanpham.getiTrangthai());
        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(sanpham.getStrMaGiay());
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }

    public boolean DeleteProduct(SanPhamDTO sanpham) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(sanpham.getStrMaGiay());
        boolean success = helper.deleteData(values);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Kiểm tra xem một sản phẩm có tồn tại và đang hoạt động trong cơ sở dữ liệu hay không
     * @param maGiay Mã giày cần kiểm tra
     * @return true nếu tồn tại và đang hoạt động, false nếu không tồn tại hoặc đã bị vô hiệu hóa
     */
    public boolean checkSanPhamExists(String maGiay) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "COUNT(*) as count");
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ? AND Trangthai = 1");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(maGiay);

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
     * Lấy thông tin sản phẩm theo mã giày
     * @param maGiay Mã giày cần lấy thông tin
     * @return SanPhamDTO chứa thông tin sản phẩm, null nếu không tìm thấy hoặc đã bị vô hiệu hóa
     */
    public SanPhamDTO getSanPhamByMa(String maGiay) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ? AND Trangthai = 1");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(maGiay);

        ResultSet rs = helper.querywithParam(values);
        try {
            if (rs != null && rs.next()) {
                SanPhamDTO sanPham = new SanPhamDTO(
                        rs.getString("Magiay"),
                        rs.getString("Tengiay"),
                        rs.getString("Doituongsd"),
                        rs.getString("Chatlieu"),
                        rs.getString("Maloai"),
                        rs.getString("Maxx"),
                        rs.getString("Mamau"),
                        rs.getString("Mathuonghieu"),
                        rs.getInt("Soluong"),
                        rs.getInt("Gia"),
                        rs.getInt("Size"),
                        rs.getInt("Trangthai")
                );
                rs.close();
                helper.closeConnect();
                return sanPham;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        helper.closeConnect();
        return null;
    }
    
    /**
     * Cập nhật số lượng tồn kho của sản phẩm
     * @param maGiay Mã giày cần cập nhật
     * @param soLuong Số lượng mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateSoluong(String maGiay, int soLuong) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ? AND Trangthai = 1");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Soluong", soLuong);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maGiay);

        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Cập nhật trạng thái của sản phẩm (ẩn/hiện)
     * @param maGiay Mã giày cần cập nhật
     * @param trangThai Trạng thái mới (1 = hiện, 0 = ẩn)
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTrangThai(String maGiay, int trangThai) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Magiay = ?");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Trangthai", trangThai);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maGiay);

        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
    
    /**
     * Lấy danh sách tất cả sản phẩm đang hoạt động
     * @return ArrayList chứa danh sách sản phẩm đang hoạt động
     */
    public ArrayList<SanPhamDTO> getDSSanPham() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblsanpham");
        params.put("WHERE", "Trangthai = 1");
        
        helper.buildingQueryParam(params);
        
        ResultSet rs = helper.executeQuery();
        try {
            while (rs != null && rs.next()) {
                list.add(new SanPhamDTO(
                        rs.getString("Magiay"),
                        rs.getString("Tengiay"),
                        rs.getString("Doituongsd"),
                        rs.getString("Chatlieu"),
                        rs.getString("Maloai"),
                        rs.getString("Maxx"),
                        rs.getString("Mamau"),
                        rs.getString("Mathuonghieu"),
                        rs.getInt("Soluong"),
                        rs.getInt("Gia"),
                        rs.getInt("Size"),
                        rs.getInt("Trangthai")
                ));
            }
            if (rs != null) {
                rs.close();
            }
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

}
