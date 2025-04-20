package BLL;
import DAL.ChiTietKhuyenMaiDAL;
import DTO.ChiTietKMDTO;
import java.sql.SQLException;
import java.util.ArrayList;
public class ChiTietKhuyenMaiBLL {
    private ArrayList<ChiTietKMDTO> list;
    private ChiTietKhuyenMaiDAL ctkhuyenmai;
    public ChiTietKhuyenMaiBLL() throws SQLException{
        list=new ArrayList<>();
        ctkhuyenmai=new ChiTietKhuyenMaiDAL();
        this.list=ctkhuyenmai.getDSChiTietKM();
}
    public ArrayList<ChiTietKMDTO> getListChiTietKhuyenMai() {
        return list;
    }
    public void setListChiTietKhuyenMai(ArrayList<ChiTietKMDTO> list){
        this.list=list;
    }
    public boolean addChiTietKhuyenMai(ChiTietKMDTO temp){
        if(ctkhuyenmai.themChiTietKhuyenMai(temp)){
            list.add(temp);
            return true;
        }
        return false;
    }
    public boolean deleteChiTietKhuyenMai(ChiTietKMDTO temp){
        if(ctkhuyenmai.xoaChiTietKhuyenMai(temp)){
            for(var tmp : list)
                if(tmp.getStrMaGiay().equals(temp.getStrMaGiay())&&tmp.getStrMaKM().equals(temp.getStrMaKM()))
                    list.remove(tmp);
                    return true;
                }
        return false;
    }
    public boolean updateChiTietKhuyenMai(ChiTietKMDTO temp){
        if(ctkhuyenmai.suaChiTietKhuyenMai(temp))
            for(var tmp : list)
                if(tmp.getStrMaGiay().equals(temp.getStrMaGiay())&&tmp.getStrMaKM().equals(temp.getStrMaKM())){
                    tmp.setTiLeKM(temp.getTiLeKM());
                    return true;
                }
        return false;
    }
//        public static void main(String[] args) throws SQLException{
//        ChiTietKhuyenMaiBLL temp=new ChiTietKhuyenMaiBLL();
//        ChiTietKMDTO tmp=new ChiTietKMDTO("KM4", "SP1", 0.45);
////        temp.addChiTietKhuyenMai(tmp);
////        temp.deleteChiTietKhuyenMai(tmp);
//        ChiTietKMDTO tmp_2=new ChiTietKMDTO("KM1", "SP1", 0.43);
//        temp.updateChiTietKhuyenMai(tmp_2);
//    }
}
