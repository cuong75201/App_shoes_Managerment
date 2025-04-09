package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.ChiTietPNDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
public class ChiTietPhieuNhapDAL {
    public ArrayList<ChiTietPNDTO> getDSChiTietPN() throws SQLException{
        ArrayList<ChiTietPNDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblchitietpn");
        try {
            while (result.next()) {
                list.add(new ChiTietPNDTO(
                        result.getString("MaPN"),
                        result.getString("MaGiay"),
                        result.getInt("SoLuong"),
                        result.getInt("GiaNhap")
                ));
            }
            result.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    public boolean themChiTietPhieuNhap(ChiTietPNDTO chitiet_pn){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietpn");
        params.put("FIELD", "MaGiay, MaPN, SoLuong, GiaNhap");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_pn.getStrMaGiay());
        values.add(chitiet_pn.getStrMaPN());
        values.add(chitiet_pn.getiSoLuong());
        values.add(chitiet_pn.getiGiaNhap());
        boolean success = helper.insertData(values);
        if(success)
            return true;
        return false;
 }
    public boolean xoaChiTietPhieuNhap(ChiTietPNDTO chitiet_pn){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietpn");
        params.put("WHERE", "MaGiay=? AND MaPN=?"); 
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_pn.getStrMaGiay());
        values.add(chitiet_pn.getStrMaPN());
        boolean success = helper.deleteData(values);
        if(success)
            return true;
        return false;
    }
    public boolean suaChiTietPhieuNhap(ChiTietPNDTO chitiet_pn){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietpn");
        params.put("WHERE", "MaGiay=? AND MaPN=?"); 
        helper.buildingQueryParam(params);
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("SoLuong", chitiet_pn.getiSoLuong());
        updateValues.put("GiaNhap", chitiet_pn.getiGiaNhap());
        ArrayList<Object> conditionValues = new ArrayList<>();  
        conditionValues.add(chitiet_pn.getStrMaGiay()); 
        conditionValues.add(chitiet_pn.getStrMaPN()); 
        boolean success = helper.updateData(updateValues, conditionValues);
        if(success)
            return true;
        return false;
    }
//public static void main(String[] args) throws SQLException{
//    ChiTietPhieuNhapDAL temp=new ChiTietPhieuNhapDAL();
//    ArrayList<ChiTietPNDTO> list=temp.getDSChiTietPN();
//    if(list==null)
//        System.out.print("Lỗi");
//    for(ChiTietPNDTO i : list)
//       System.out.println(i.getStrMaPN());
////    ChiTietPNDTO tmp=new ChiTietPNDTO("PN002", "SP6", 10, 10);
////    temp.themChiTietPhieuNhap(tmp);
////    temp.xoaChiTietPhieuNhap(tmp);
//    ChiTietPNDTO tmp_2=new ChiTietPNDTO("PN001", "SP1", 12, 60);
//    temp.suaChiTietPhieuNhap(tmp_2);
//}
}
