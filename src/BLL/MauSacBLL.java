/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.MauSacDAL;
import DTO.MauSacDTO;
import java.util.ArrayList;

public class MauSacBLL {
    private MauSacDAL dal;
    private ArrayList<MauSacDTO> list_mau;

    public MauSacBLL() {
        dal = new MauSacDAL();
        list_mau=new ArrayList<>();
        list_mau=dal.getListMauSac();
    }

    // Lấy danh sách màu sắc
    public ArrayList<MauSacDTO> getListMauSac() {
        return dal.getListMauSac();
    }

    // Thêm màu sắc
    public boolean addMauSac(MauSacDTO mausac) {
        return dal.InsertMauSac(mausac);
    }

    // Cập nhật màu sắc
    public boolean updateMauSac(MauSacDTO mausac) {
        return dal.UpdateMauSac(mausac);
    }

    // Xoá màu sắc
    public boolean deleteMauSac(MauSacDTO mausac) {
        return dal.DeleteMauSac(mausac);
    }
    public boolean deleteMauSac(String Mamau) {
        return dal.deleteMauSac(Mamau);
    }

    // Tìm kiếm màu sắc theo mã hoặc tên
    public ArrayList<MauSacDTO> searchMauSac(String keyword) {
        ArrayList<MauSacDTO> list = dal.getListMauSac();
        ArrayList<MauSacDTO> result = new ArrayList<>();

        for (MauSacDTO mausac : list) {
            if (mausac.getStrMamau().toLowerCase().contains(keyword.toLowerCase()) ||
                mausac.getStrTenmau().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(mausac);
            }
        }

        return result;
    }
        public String getTenmaufromMaMau(String mamau){
        for (MauSacDTO maudto:list_mau){
            if(maudto.getStrMamau().equals(mamau)){
                return maudto.getStrTenmau();
            }
        }
        return null;
    }
           public String getMamaufromTenMau(String tenmau){
        for (MauSacDTO maudto:list_mau){
            if(maudto.getStrTenmau().equals(tenmau)){
                return maudto.getStrMamau();
            }
        }
        return null;
    }
}

