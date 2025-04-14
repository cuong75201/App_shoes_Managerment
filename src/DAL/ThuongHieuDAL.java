/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DAL.MySQLHelper.MySQLHelpers;
import DTO.ThuongHieuDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author cuong
 */
public class ThuongHieuDAL {

    public ArrayList<ThuongHieuDTO> getListThuongHieu() {
        ArrayList<ThuongHieuDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblthuonghieu");
        try {
            while (result.next()) {
                list.add(new ThuongHieuDTO(
                        result.getString("Mathuonghieu"),
                        result.getString("Tenthuonghieu"),
                        result.getString("Diachi"),
                        result.getString("Email")
                ));
                result.close();
                helper.closeConnect();
                        return list;

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public boolean InsertThuongHieu(ThuongHieuDTO th) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblthuonghieu");
        params.put("FIELD", "Mathuonghieu,Tenthuonghieu,Diachi,Email");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(th.getStrMathuonghieu());
        values.add(th.getStrTenthuonghieu());
        values.add(th.getStrDiachi());
        values.add(th.getStrEmail());
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean UpdateThuongHieu(ThuongHieuDTO th) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblthuonghieu");
        params.put("WHERE", "Mathuonghieu = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> UpdateValue = new HashMap<>();
        UpdateValue.put("Mathuonghieu", th.getStrMathuonghieu());
        UpdateValue.put("Tenthuonghieu", th.getStrTenthuonghieu());
        UpdateValue.put("Diachi", th.getStrDiachi());
        UpdateValue.put("Email", th.getStrEmail());

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(th.getStrMathuonghieu());
        boolean success = helper.updateData(UpdateValue, valueCondition);
        helper.closeConnect();
        return success;
    }
    public boolean DeleteThuongHieu(ThuongHieuDTO th){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String,String> params=new HashMap<>();
        params.put("TABLE","tblthuonghieu");
        params.put("WHERE","Mathuonghieu = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> value=new ArrayList<>();
        value.add(th.getStrMathuonghieu());
        boolean success=helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}
