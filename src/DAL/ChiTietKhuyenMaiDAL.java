package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.ChiTietKMDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
public class ChiTietKhuyenMaiDAL {
    public ArrayList<ChiTietKMDTO> getDSChiTietKM() throws SQLException{
        ArrayList<ChiTietKMDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblchitietkm");
        try {
            while (result.next()) {
                list.add(new ChiTietKMDTO(
                        result.getString("MaKM"),
                        result.getString("MaGiay"),
                        result.getDouble("TiLeKM")
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
    public boolean themChiTietKhuyenMai(ChiTietKMDTO chitiet_km){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietkm");
        params.put("FIELD", "MaGiay, MaKM, TiLeKM");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_km.getStrMaGiay());
        values.add(chitiet_km.getStrMaKM());
        values.add(chitiet_km.getTiLeKM());
        boolean success = helper.insertData(values);
        if(success)
            return true;
        return false;
 }
    public boolean xoaChiTietKhuyenMai(ChiTietKMDTO chitiet_km){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietkm");
        params.put("WHERE", "MaGiay=? AND MaKM=?"); 
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_km.getStrMaGiay());
        values.add(chitiet_km.getStrMaKM());
        boolean success = helper.deleteData(values);
        if(success)
            return true;
        return false;
    }
    public boolean suaChiTietKhuyenMai(ChiTietKMDTO chitiet_km){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitietkm");
        params.put("WHERE", "MaGiay=? AND MaKM=?"); 
        helper.buildingQueryParam(params);
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("TiLeKM", chitiet_km.getTiLeKM());
        ArrayList<Object> conditionValues = new ArrayList<>();  
        conditionValues.add(chitiet_km.getStrMaGiay()); 
        conditionValues.add(chitiet_km.getStrMaKM()); 
        boolean success = helper.updateData(updateValues, conditionValues);
        if(success)
            return true;
        return false;
    }
//public static void main(String[] args) throws SQLException{
//    ChiTietKhuyenMaiDAL temp=new ChiTietKhuyenMaiDAL();
//    ArrayList<ChiTietKMDTO> list=temp.getDSChiTietKM();
//    if(list==null)
//        System.out.print("Lỗi");
//    for(ChiTietKMDTO i : list)
//       System.out.println(i.getStrMaKM());
//    ChiTietKMDTO tmp=new ChiTietKMDTO("KM3", "SP1", 0.3);
////    temp.themChiTietKhuyenMai(tmp);
////    temp.xoaChiTietKhuyenMai(tmp);
//    ChiTietKMDTO tmp_2=new ChiTietKMDTO("KM1", "SP1", 0.4);
//    temp.suaChiTietKhuyenMai(tmp_2);
//}
}
