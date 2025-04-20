package BLL;
import DAL.ChiTietPhieuNhapDAL;
import DTO.ChiTietPNDTO;
import java.sql.SQLException;
import java.util.ArrayList;
public class ChiTietPhieuNhapBLL {
    private ArrayList<ChiTietPNDTO> list;
    private ChiTietPhieuNhapDAL ctphieunhap;
    public ChiTietPhieuNhapBLL() throws SQLException{
        list=new ArrayList<>();
        ctphieunhap=new ChiTietPhieuNhapDAL();
        this.list=ctphieunhap.getDSChiTietPN();
}
    public ArrayList<ChiTietPNDTO> getListChiTietPhieuNhap() {
        return list;
    }
    public void setListChiTietPhieuNhap(ArrayList<ChiTietPNDTO> list){
        this.list=list;
    }
    public boolean addChiTietPhieuNhap(ChiTietPNDTO temp){
        if(ctphieunhap.themChiTietPhieuNhap(temp)){
            list.add(temp);
            return true;
        }
        return false;
    }
    public boolean deleteChiTietPhieuNhap(ChiTietPNDTO temp){
        if(ctphieunhap.xoaChiTietPhieuNhap(temp))
            for(var tmp : list)
                if(tmp.getStrMaGiay().equals(temp.getStrMaGiay())&&tmp.getStrMaPN().equals(temp.getStrMaPN())){
                    list.remove(tmp);
                    return true;
                }
        return false;
    }

    public boolean deleteChiTietPhieuNhap(String maPN, String maSP) {
        if(ctphieunhap.deleteChiTietPhieuNhap(maPN, maSP))
            for(var tmp : list)
                if(tmp.getStrMaGiay().equals(maSP)&&tmp.getStrMaPN().equals(maSP)){
                    list.remove(tmp);
                    return true;
                }
        return false;
    }
    
    public boolean updateChiTietPhieuNhap(ChiTietPNDTO temp){
        if(ctphieunhap.suaChiTietPhieuNhap(temp))
            for(var tmp : list)
                if(tmp.getStrMaGiay().equals(temp.getStrMaGiay())&&tmp.getStrMaPN().equals(temp.getStrMaPN())){
                    tmp.setiGiaNhap(temp.getiGiaNhap());
                    tmp.setiSoLuong(temp.getiSoLuong());
                    return true;
                }
        return false;
    }
//            public static void main(String[] args) throws SQLException{
//        ChiTietPhieuNhapBLL temp=new ChiTietPhieuNhapBLL();
//        ChiTietPNDTO tmp=new ChiTietPNDTO("PN002", "SP4", 45, 60);
////        temp.addChiTietPhieuNhap(tmp);
////        temp.deleteChiTietPhieuNhap(tmp);
//        ChiTietPNDTO tmp_2=new ChiTietPNDTO("PN001", "SP1", 8, 4);
//        temp.updateChiTietPhieuNhap(tmp_2);
//    }
}
