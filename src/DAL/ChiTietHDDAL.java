package DAL;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAL.MySQLHelper.MySQLHelpers;
import DTO.ChiTietHDDTO;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class ChiTietHDDAL {
    public ArrayList<ChiTietHDDTO> getDSChiTietHoaDon() throws SQLException {
        ArrayList<ChiTietHDDTO> list = new ArrayList<>();
        MySQLHelpers helper = new MySQLHelpers();
        ResultSet result = helper.selectAllFromTable("tblchitiethd");
        try {
            while (result.next()) {
                list.add(new ChiTietHDDTO(
                        result.getString("Magiay"),
                        result.getString("MaHD"),
                        result.getInt("SoLuong"),
                        result.getInt("GiaBan")
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
 public boolean themChiTietHoaDon(ChiTietHDDTO chitiet_hd){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitiethd");
        params.put("FIELD", "Magiay, MaHD, SoLuong, GiaBan");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_hd.getStrMaGiay());
        values.add(chitiet_hd.getStrMaHD());
        values.add(chitiet_hd.getiSoLuong());
        values.add(chitiet_hd.getiGiaBan());
        boolean success = helper.insertData(values);
        if(success)
            return true;
        return false;
 }
 public boolean xoaChiTietHoaDon(ChiTietHDDTO chitiet_hd){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitiethd");
        params.put("WHERE", " Magiay=? AND MaHD=?"); 
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(chitiet_hd.getStrMaGiay());
        values.add(chitiet_hd.getStrMaHD());
        boolean success = helper.deleteData(values);
        if(success)
            return true;
        return false;
 }
 public boolean suaChiTietHoaDon(ChiTietHDDTO chitiet_hd){
        MySQLHelpers helper = new MySQLHelpers();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "tblchitiethd");
        params.put("WHERE", "Magiay=? AND MaHD=?");
        helper.buildingQueryParam(params);
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("SoLuong", chitiet_hd.getiSoLuong());
        updateValues.put("GiaBan", chitiet_hd.getiGiaBan());
        ArrayList<Object> conditionValues = new ArrayList<>();  
        conditionValues.add(chitiet_hd.getStrMaGiay()); 
        conditionValues.add(chitiet_hd.getStrMaHD()); 
        boolean success = helper.updateData(updateValues, conditionValues);
        if(success)
            return true;
        return false;
 }
//public static void main(String[] args) throws SQLException{
//    ChiTietHDDAL temp=new ChiTietHDDAL();
//    ArrayList<ChiTietHDDTO> list=temp.getDSChiTietHoaDon();
//    if(list==null)
//        System.out.print("Lỗi");
//    for(ChiTietHDDTO i : list)
//       System.out.println(i.getStrMaGiay());
////    ChiTietHDDTO tmp=new ChiTietHDDTO("SP1", "HD008", 1, 10);
////    temp.themChiTietHoaDon(tmp);
////    temp.xoaChiTietHoaDon(tmp);
//    ChiTietHDDTO tmp_2=new ChiTietHDDTO("SP1", "HD001", 3, 5);
//    temp.suaChiTietHoaDon(tmp_2);
//}
}

