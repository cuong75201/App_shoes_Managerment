package DAL;

import DTO.PhieuNhapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import DAL.MySQLHelper.MySQLHelpers;

public class PhieuNhapDAL {

    public ArrayList<PhieuNhapDTO> getListPhieuNhap() {
        ArrayList<PhieuNhapDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("SELECT", "*");
        params.put("TABLE", "tblphieunhap");
        params.put("WHERE", "Trangthai = 1");
        helper.buildingQueryParam(params);
        ResultSet result = helper.executeQuery();

        try {
            while (result.next()) {
                list.add(new PhieuNhapDTO(
                        result.getString("MaPN"),
                        result.getString("MaNCC"),
                        result.getString("MaNV"),
                        result.getString("NgayNhap"),
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

    public boolean insertPhieuNhap(PhieuNhapDTO pn) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblphieunhap");
        params.put("FIELD", "MaPN,MaNCC,MaNV,NgayNhap,TongTien");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(pn.getStrMaPN());
        values.add(pn.getStrMaNCC());
        values.add(pn.getStrMaNV());
        values.add(pn.getStrNgayNhap());
        values.add(pn.getTongTien());

        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean updatePhieuNhap(PhieuNhapDTO pn) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblphieunhap");
        params.put("WHERE", "MaPN = ?");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put("MaNCC", pn.getStrMaNCC());
        updateValue.put("MaNV", pn.getStrMaNV());
        updateValue.put("NgayNhap", pn.getStrNgayNhap());
        updateValue.put("TongTien", pn.getTongTien());

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(pn.getStrMaPN());

        boolean success = helper.updateData(updateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean deletePhieuNhap(PhieuNhapDTO pn) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblphieunhap");
        params.put("WHERE", "MaPN = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> value = new ArrayList<>();
        value.add(pn.getStrMaPN());

        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }

    public boolean deletePhieuNhap(String maPN) {
        // Thay vì xóa thật sự, ta chỉ cập nhật trạng thái
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblphieunhap");
        params.put("WHERE", "MaPN = ? AND Trangthai = 1");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("Trangthai", 0);

        ArrayList<Object> conditionValue = new ArrayList<>();
        conditionValue.add(maPN);
        boolean success = helper.updateData(updateValues, conditionValue);
        helper.closeConnect();
        return success;
    }
}
