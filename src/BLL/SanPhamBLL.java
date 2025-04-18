/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.SanPhamDAL;
import DTO.SanPhamDTO;
import java.util.ArrayList;

public class SanPhamBLL {

    private ArrayList<SanPhamDTO> listProduct;
    private SanPhamDAL sanpham;

    public SanPhamBLL() {
        listProduct = new ArrayList<>();
        sanpham = new SanPhamDAL();
        this.listProduct = sanpham.getProductList();
    }

    public ArrayList<SanPhamDTO> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<SanPhamDTO> listProduct) {
        this.listProduct = listProduct;
    }

    public boolean addProduct(SanPhamDTO sp) {
        if (sanpham.InsertProduct(sp)) {
            listProduct.add(sp);
            return true;
        }
        return false;
    }

    public boolean updateProduct(SanPhamDTO sp) {
        if (sanpham.UpdateProduct(sp)) {
            for (SanPhamDTO spdto : listProduct) {
                if (spdto.getStrMaGiay().equals(sp.getStrMaGiay())) {
                    spdto.setStrMaGiay(sp.getStrMaGiay());
                    spdto.setStrChatLieu(sp.getStrChatLieu());
                    spdto.setStrDoiTuongSD(sp.getStrDoiTuongSD());
                    spdto.setStrMaLoai(sp.getStrMaLoai());
                    spdto.setStrMaMau(sp.getStrMaMau());
                    spdto.setStrMaThuongHieu(sp.getStrMaThuongHieu());
                    spdto.setStrMaxx(sp.getStrMaxx());
                    spdto.setStrTenGiay(sp.getStrTenGiay());
                    spdto.setiGia(sp.getiGia());
                    spdto.setiSize(sp.getiSize());
                    spdto.setiSoLuong(sp.getiSoLuong());
                    spdto.setiTrangthai(sp.getiTrangthai());
                    return true;
                }
            }

        }
        return false;
    }

    public boolean deleteProduct(SanPhamDTO sp) {
        if (sanpham.DeleteProduct(sp)) {
            for (SanPhamDTO spdto : listProduct) {
                if (spdto.getStrMaGiay().equals(sp.getStrMaGiay())) {
                    listProduct.remove(spdto);
                    return true;
                }

            }
        }
        return false;
    }

    public String getDefaultMasp() {
        if (listProduct.size() == 0) {
            return "SP1";
        }
        int i = 0;
        String s = "SP";
        for (SanPhamDTO sp : listProduct) {
            int masp = Integer.parseInt(sp.getStrMaGiay().split("SP")[1]);
            i = masp + 1;
        }
        s += i;
        return s;
    }

    public ArrayList<SanPhamDTO> SearchbyMasp(String ma) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();

        for (SanPhamDTO sp : listProduct) { // danhSachSanPham là ArrayList<SanPhamDTO> chứa toàn bộ sản phẩm
            if (sp.getStrMaGiay().toLowerCase().contains(ma.toLowerCase())) {
                result.add(sp);
            }
        }

        return result;
    }

    public ArrayList<SanPhamDTO> SearchbyTensp(String ten) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();

        for (SanPhamDTO sp : listProduct) { // danhSachSanPham là ArrayList<SanPhamDTO> chứa toàn bộ sản phẩm
            if (sp.getStrTenGiay().toLowerCase().contains(ten.toLowerCase())) {
                result.add(sp);
            }
        }

        return result;
    }

    public ArrayList<SanPhamDTO> SearchbyMaThuongHieu(ArrayList<String> th) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (String math : th) {
            for (SanPhamDTO sp : listProduct) { // danhSachSanPham là ArrayList<SanPhamDTO> chứa toàn bộ sản phẩm
                if (sp.getStrMaThuongHieu().contains(math)) {
                    result.add(sp);
                }
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> SearchbyMaLoaiSP(ArrayList<String> loai) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();

        for (String maloai : loai) {
            for (SanPhamDTO sp : listProduct) { // danhSachSanPham là ArrayList<SanPhamDTO> chứa toàn bộ sản phẩm
                if (sp.getStrMaLoai().contains(maloai)) {
                    result.add(sp);
                }
            }
        }
        return result;

    }
}
