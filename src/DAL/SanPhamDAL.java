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
                        result.getInt("Size")
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
        params.put("FIELD", "Magiay,Tengiay,Soluong,Gia,Size,Doituongsd,Chatlieu,Maloai,Maxx,MaMau,Mathuonghieu");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(sanpham.getStrMaGiay());
        values.add(sanpham.getStrTenGiay());
        values.add(sanpham.getiSoLuong());
        values.add(sanpham.getiSize());
        values.add(sanpham.getiGia());
        values.add(sanpham.getiSize());
        values.add(sanpham.getStrDoiTuongSD());
        values.add(sanpham.getStrChatLieu());
        values.add(sanpham.getStrMaLoai());
        values.add(sanpham.getStrMaxx());
        values.add(sanpham.getStrMaMau());
        values.add(sanpham.getStrMaThuongHieu());
        helper.closeConnect();

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
}
