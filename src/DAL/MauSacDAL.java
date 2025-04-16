/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.MauSacDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class MauSacDAL {

    public ArrayList<MauSacDTO> getListMauSac() {
        ArrayList<MauSacDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblmausac");
        try {
            while (result.next()) {
                list.add(new MauSacDTO(
                        result.getString("Mamau"),
                        result.getString("Tenmau")
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

    public boolean InsertMauSac(MauSacDTO mausac) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblmausac");
        params.put("FIELD", "mamau,tenmau");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(mausac.getStrMamau());
        values.add(mausac.getStrTenmau());
        boolean success = helper.insertData(values);
        helper.closeConnect();
        return success;
    }

    public boolean UpdateMauSac(MauSacDTO mausac) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblmausac");
        params.put("WHERE", "mamau = ?");
        helper.buildingQueryParam(params);
        Map<String, Object> UpdateValue = new HashMap<>();
        UpdateValue.put("mamau", mausac.getStrMamau());
        UpdateValue.put("tenmau", mausac.getStrTenmau());
        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(mausac.getStrMamau());
        boolean success = helper.updateData(UpdateValue, valueCondition);
        helper.closeConnect();
        return success;
    }

    public boolean DeleteMauSac(MauSacDTO mausac) {
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblmausac");
        params.put("WHERE", "mamau = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> value = new ArrayList<>();
        value.add(mausac.getStrMamau());
        boolean success = helper.deleteData(value);
        helper.closeConnect();
        return success;
    }
}
