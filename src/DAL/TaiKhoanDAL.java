/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.TaiKhoanDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import DAL.MySQLHelper.MySQLHelpers;
import com.mysql.cj.xdevapi.Result;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TaiKhoanDAL {

    public ArrayList<TaiKhoanDTO> getListAccount() {
        ArrayList<TaiKhoanDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tbltaikhoan");
        try {
            while (result.next()) {
                list.add(new TaiKhoanDTO(
                        result.getString("tendangnhap"),
                        result.getString("matkhau"),
                        result.getInt("capbac")
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

    public boolean InsertAccount(TaiKhoanDTO tk) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tbltaikhoan");
        params.put("FIELD", "tendangnhap,matkhau,capbac");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(tk.getStrTenDangNhap());
        values.add(tk.getStrMatKhau());
        values.add(tk.getiCapBac());
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean UpdateAccount(TaiKhoanDTO tk) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tbltaikhoan");
        params.put("WHERE", "tendangnhap = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> UpdateValue = new HashMap<>();
        UpdateValue.put("tendangnhap", tk.getStrTenDangNhap());
        UpdateValue.put("matkhau", tk.getStrMatKhau());
        UpdateValue.put("capbac", tk.getiCapBac());
        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(tk.getStrTenDangNhap());
        boolean success = helper.updateData(UpdateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean DeleteAccount(TaiKhoanDTO tk) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tbltaikhoan");
        params.put("WHERE", "tendangnhap = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> value = new ArrayList<>();
        value.add(tk.getStrTenDangNhap());
        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}
