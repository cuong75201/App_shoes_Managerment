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

    public MauSacBLL() {
        dal = new MauSacDAL();
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
}

