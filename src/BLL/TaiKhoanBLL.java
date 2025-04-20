/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DTO.TaiKhoanDTO;
import DAL.TaiKhoanDAL;
import java.security.MessageDigest;
import java.util.ArrayList;

public class TaiKhoanBLL {

    private ArrayList<TaiKhoanDTO> ListAccount;
    private TaiKhoanDAL taikhoan;

    public TaiKhoanBLL() {
        ListAccount = new ArrayList<>();
        taikhoan = new TaiKhoanDAL();
        this.ListAccount = taikhoan.getListAccount();
    }

    public ArrayList<TaiKhoanDTO> getListAccount() {
        return ListAccount;
    }

    public boolean addAccount(TaiKhoanDTO tk) {
        if (taikhoan.InsertAccount(tk)) {
            ListAccount.add(tk);
            return true;
        }
        return false;
    }

    public boolean TestAccount(TaiKhoanDTO tk) {
        for (TaiKhoanDTO taiKhoanDTO : ListAccount) {
            if (taiKhoanDTO.getStrTenDangNhap().equals(tk.getStrTenDangNhap()) && taiKhoanDTO.getStrMatKhau().equals(tk.getStrMatKhau())) {
                return true;
            }
        }
        return false;
    }

    public Boolean UpdateAccount(TaiKhoanDTO tk) {
        if (taikhoan.UpdateAccount(tk)) {

            // duyệt từng phẩn tử
            for (TaiKhoanDTO taikhoanDTO : ListAccount) {
                if (taikhoanDTO.getStrTenDangNhap().equals(tk.getStrTenDangNhap())) {
                    taikhoanDTO.setStrMatKhau(tk.getStrMatKhau());
                    taikhoanDTO.setiCapBac(tk.getiCapBac());
                    return true;
                }
            }
        }

        return false;
    }

    public Boolean deleteAccount(TaiKhoanDTO tk) {
        if (taikhoan.DeleteAccount(tk)) {

            // duyệt từng phẩn tử
            for (TaiKhoanDTO taikhoanDTO : ListAccount) {
                if (taikhoanDTO.getStrTenDangNhap().equals(tk.getStrTenDangNhap())) {
                    ListAccount.remove(taikhoan);
                    return true;
                }
            }
        }

        return false;
    }

    public static String hashMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));  // chuyển từng byte sang hex
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String CapBactoChucVu(int capbac) {
        switch (capbac) {
            case 1:
                return "Quản trị viên";
            case 2:
                return "Quản lý hàng hóa"; // Xem, sửa xóa danh sách hàng hóa
            case 3:
                return "Quản lý khuyến mãi"; // Xem sản phẩm, tạo khuyến mãi
            case 4:
                return "Quản lý hóa đơn"; //Xem, sửa, xóa hóa đơn 
            case 5:
                return "Quản lý nhập hàng"; // Xem, thêm danh sách hàng hóa , phiếu nhập, nhà cung cấp
            case 6:
                return "Thống kê doanh thu"; //
            default:
                return "Không tồn tại";
        }
    }

    public int ChucVutoCapBac(String chucVu) {
        switch (chucVu) {
            case "Quản trị viên":
                return 1;
            case "Quản lý hàng hóa":
                return 2;
            case "Quản lý khuyến mãi":
                return 3;
            case "Quản lý hóa đơn":
                return 4;
            case "Quản lý nhập hàng":
                return 5;
            case "Thống kê doanh thu":
                return 6;
            default:
                return -1; // hoặc throw exception nếu cần
        }
    }

    public boolean TestMaNV(String manv) {
        for (TaiKhoanDTO tkdto : ListAccount) {
            if (tkdto.getStrTenDangNhap().equals(manv)) {
                return true;
            }
        }
        return false;
    }

    public int trangThaiToInt(String trangThai) {
        if (trangThai.equalsIgnoreCase("hoạt động")) {
            return 1;
        } else if (trangThai.equalsIgnoreCase("khóa")) {
            return 0;
        } else {
            return -1; // Trạng thái không hợp lệ
        }
    }
    public TaiKhoanDTO searchTKbyMa(String ma){
        for (TaiKhoanDTO tkdto:ListAccount){
            if(tkdto.getStrTenDangNhap().equalsIgnoreCase(ma)){
                return tkdto;
            }
        }
            return null;
    }
    public String intToTrangThai(int trangThai) {
    switch (trangThai) {
        case 1:
            return "Hoạt động";
        case 0:
            return "Khóa";
        default:
            return "không xác định"; // Hoặc đại ca muốn để rỗng, hoặc throw exception cũng được
    }
}
}
