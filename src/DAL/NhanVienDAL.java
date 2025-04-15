
package DAL;

import DTO.NhanVienDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import DAL.MySQLHelper.MySQLHelpers;

public class NhanVienDAL {

    public ArrayList<NhanVienDTO> getListNhanVien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblnhanvien");

        try {
            while (result.next()) {
                list.add(new NhanVienDTO(
                        result.getString("MaNV"),
                        result.getString("Ho"),
                        result.getString("Ten"),
                        result.getString("GioiTinh"),
                        result.getString("DiaChi"),
                        result.getString("Email"),
                        result.getString("ChucVu"),
                        result.getString("DienThoai"),
                        result.getInt("Luong"),
                        result.getString("Anh")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public boolean insertNhanVien(NhanVienDTO nv) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhanvien");
        params.put("FIELD", "MaNV,Ho,Ten,GioiTinh,DiaChi,Email,ChucVu,DienThoai,Luong,Anh");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(nv.getstrMaNV());
        values.add(nv.getStrHo());
        values.add(nv.getStrTen());
        values.add(nv.getStrGioiTinh());
        values.add(nv.getStrDiaChi());
        values.add(nv.getStrEmail());
        values.add(nv.getStrChucVu());
        values.add(nv.getiDienThoai());
        values.add(nv.getiLuong());
        values.add(nv.getStrAnh());

        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhanvien");
        params.put("WHERE", "MaNV = ?");
        helper.buildingQueryParam(params);

        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put("Ho", nv.getStrHo());
        updateValue.put("Ten", nv.getStrTen());
        updateValue.put("GioiTinh", nv.getStrGioiTinh());
        updateValue.put("DiaChi", nv.getStrDiaChi());
        updateValue.put("Email", nv.getStrEmail());
        updateValue.put("ChucVu", nv.getStrChucVu());
        updateValue.put("DienThoai", nv.getiDienThoai());
        updateValue.put("Luong", nv.getiLuong());
        updateValue.put("Anh", nv.getStrAnh());

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(nv.getstrMaNV());

        boolean success = helper.updateData(updateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean deleteNhanVien(NhanVienDTO nv) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhanvien");
        params.put("WHERE", "MaNV = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> value = new ArrayList<>();
        value.add(nv.getstrMaNV());

        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}


