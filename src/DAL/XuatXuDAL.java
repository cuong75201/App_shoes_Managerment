/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DAL.MySQLHelper.MySQLHelpers;
import DTO.XuatXuDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class XuatXuDAL {

    public ArrayList<XuatXuDTO> getListXuatXu() {
        ArrayList<XuatXuDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblxuatxu");
        try {
            while (result.next()) {
                list.add(new XuatXuDTO(
                        result.getString("Maxx"),
                        result.getString("Tennuoc")
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

    public boolean InsertXuatXu(XuatXuDTO xx) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblxuatxu");
        params.put("FIELD", "Maxx,Tennuoc");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(xx.getStrMaxuatxu());
        values.add(xx.getStrTennuoc());
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean UpdateXuatXu(XuatXuDTO xx) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblxuatxu");
        params.put("WHERE", "Maxx = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> UpdateValue = new HashMap<>();
        UpdateValue.put("Maxx", xx.getStrMaxuatxu());
        UpdateValue.put("Tennuoc", xx.getStrTennuoc());

        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(xx.getStrMaxuatxu());
        boolean success = helper.updateData(UpdateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean DeleteXuatXu(XuatXuDTO xx) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblxuatxu");
        params.put("WHERE", "Maxx = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> value = new ArrayList<>();
        value.add(xx.getStrMaxuatxu());
        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}
