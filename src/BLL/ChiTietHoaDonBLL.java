package BLL;
import DAL.ChiTietHDDAL;
import DTO.ChiTietHDDTO;
import java.sql.SQLException;
import java.util.ArrayList;
public class ChiTietHoaDonBLL {
    private ArrayList<ChiTietHDDTO> list;
    private ChiTietHDDAL cthoadon;
    public ChiTietHoaDonBLL() throws SQLException{
        list=new ArrayList<>();
        cthoadon=new ChiTietHDDAL();
        this.list=cthoadon.getDSChiTietHoaDon();
}
    public ArrayList<ChiTietHDDTO> getListChiTietHoaDon() {
        return list;
    }
    public void setListChiTietHoaDon(ArrayList<ChiTietHDDTO> list){
        this.list=list;
    }
    public boolean addChiTietHoaDon(ChiTietHDDTO temp){
        if(cthoadon.themChiTietHoaDon(temp)){
            list.add(temp);
            return true;
        }
        return false;
    }
    public boolean deleteChiTietHoaDon(ChiTietHDDTO temp){
        if(cthoadon.xoaChiTietHoaDon(temp)){
            for (int i = 0; i < list.size(); i++) {
                ChiTietHDDTO tmp = list.get(i);
                // Sửa == thành equals() để so sánh chuỗi đúng cách
                if (tmp.getStrMaGiay().equals(temp.getStrMaGiay()) && 
                    tmp.getStrMaHD().equals(temp.getStrMaHD())) {
                    list.remove(i);
                    return true;
                }    
            }
        }
        return false;
    }
    public boolean updateChiTietHoaDon(ChiTietHDDTO temp) {
    if (cthoadon.suaChiTietHoaDon(temp)) {
        for (int i = 0; i < list.size(); i++) {
            ChiTietHDDTO tmp = list.get(i);
            // Sửa == thành equals() để so sánh chuỗi đúng cách
            if (tmp.getStrMaGiay().equals(temp.getStrMaGiay()) && 
                tmp.getStrMaHD().equals(temp.getStrMaHD())) {
                tmp.setiGiaBan(temp.getiGiaBan());
                tmp.setiSoLuong(temp.getiSoLuong());
                return true;
            }
        }
    }
    return false;
}
//    public static void main(String[] args) throws SQLException{
//        ChiTietHoaDonBLL temp=new ChiTietHoaDonBLL();
//        ChiTietHDDTO tmp=new ChiTietHDDTO("SP1", "HD003", 10, 10);
////        temp.addChiTietHoaDon(tmp);
////        temp.deleteChiTietHoaDon(tmp);
//        ChiTietHDDTO tmp_2=new ChiTietHDDTO("SP1", "HD001", 3, 4);
//        temp.updateChiTietHoaDon(tmp_2);
//    }
}
