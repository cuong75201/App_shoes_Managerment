/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.NhaCungCapDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import DAL.MySQLHelper.MySQLHelpers;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class NhaCungCapDAL {

    public ArrayList<NhaCungCapDTO> getListAccount() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblnhacungcap");
        try {
            while (result.next()) {
                list.add(new NhaCungCapDTO(
                        result.getString("MaNCC"),
                        result.getString("TenNCC"),
                        result.getString("DiaChi"),
                        result.getString("Email")
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

    public boolean InsertNCC(NhaCungCapDTO NCC) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhacungcap");
        params.put("FIELD", "MaNCC,TenNCC,DiaChi,Email");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(NCC.getStrMaNCC());
        values.add(NCC.getStrTenNCC());
        values.add(NCC.getStrDiaChi());
        values.add(NCC.getStrEmail());
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean UpdateNCC(NhaCungCapDTO NCC) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhacungcap");
        params.put("WHERE", "MaNCC = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> UpdateValue = new HashMap<>();
        UpdateValue.put("MaNCC", NCC.getStrMaNCC());
        UpdateValue.put("TenNCC", NCC.getStrTenNCC());
        UpdateValue.put("DiaChi", NCC.getStrDiaChi());
        UpdateValue.put("Email", NCC.getStrEmail());
        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(NCC.getStrMaNCC());
        boolean success = helper.updateData(UpdateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean DeleteNCC(NhaCungCapDTO NCC) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblnhacungcap");
        params.put("WHERE", "MaNCC = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> value = new ArrayList<>();
        value.add(NCC.getStrMaNCC());
        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}
