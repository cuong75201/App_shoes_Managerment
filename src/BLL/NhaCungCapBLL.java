/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BLL;

import DAL.NhaCungCapDAL;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBLL {
    private NhaCungCapDAL dal;

    public NhaCungCapBLL() {
        dal = new NhaCungCapDAL();
    }

    // Lấy danh sách nhà cung cấp
    public ArrayList<NhaCungCapDTO> getListNhaCungCap() {
        return dal.getListAccount();
    }

    public ArrayList<String> getAllMaNCC() {
    return dal.getAllMaNCC();
}

    // Thêm nhà cung cấp
    public boolean addNhaCungCap(NhaCungCapDTO ncc) {
        return dal.InsertNCC(ncc);
    }

    // Cập nhật nhà cung cấp
    public boolean updateNhaCungCap(NhaCungCapDTO ncc) {
        return dal.UpdateNCC(ncc);
    }

    // Xóa nhà cung cấp
    public boolean deleteNhaCungCap(NhaCungCapDTO ncc) {
        return dal.DeleteNCC(ncc);
    }

    public boolean deleteNhaCungCap(String maNCC) {
        return dal.deleteNhaCungCap(maNCC);
    }

    // Tìm kiếm nhà cung cấp theo nhiều tiêu chí: mã, tên, địa chỉ, email
    public ArrayList<NhaCungCapDTO> searchNhaCungCap(String keyword) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> list = getListNhaCungCap();

        for (NhaCungCapDTO ncc : list) {
            if (ncc.getStrMaNCC().toLowerCase().contains(keyword.toLowerCase())
                || ncc.getStrTenNCC().toLowerCase().contains(keyword.toLowerCase())
                || ncc.getStrDiaChi().toLowerCase().contains(keyword.toLowerCase())
                || ncc.getStrEmail().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(ncc);
            }
        }

        return result;
    }
}
