/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.LoaiDAL;
import DTO.LoaiDTO;
import java.util.ArrayList;

/**
 *
 * @author OS
 */
public class LoaiBLL {
    private LoaiDAL loaiDAL;
    private ArrayList<LoaiDTO> list_loai;
    public LoaiBLL() {
        list_loai=new ArrayList<>();
        loaiDAL = new LoaiDAL();
        list_loai=loaiDAL.getLoaiList();
    }
    
    /**
     * Lấy danh sách tất cả loại sản phẩm
     * @return ArrayList chứa danh sách loại sản phẩm
     */
    public ArrayList<LoaiDTO> getLoaiList() {
        return loaiDAL.getLoaiList();
    }
    
    /**
     * Thêm một loại sản phẩm mới
     * @param loai Đối tượng loại sản phẩm cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertLoai(LoaiDTO loai) {
        // Kiểm tra dữ liệu loại sản phẩm
        if (loai == null) {
            return false;
        }
        
        // Kiểm tra mã loại đã tồn tại chưa
        if (isLoaiExists(loai.getStrMaloai())) {
            return false;
        }
        
        // Kiểm tra tên loại đã tồn tại chưa
        if (isLoaiNameExists(loai.getStrTenloai())) {
            return false;
        }
        
        return loaiDAL.insertLoai(loai);
    }
    
    /**
     * Cập nhật thông tin loại sản phẩm
     * @param loai Đối tượng loại sản phẩm cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateLoai(LoaiDTO loai) {
        // Kiểm tra dữ liệu loại sản phẩm
        if (loai == null) {
            return false;
        }
        
        // Kiểm tra mã loại có tồn tại không
        if (!isLoaiExists(loai.getStrMaloai())) {
            return false;
        }
        
        // Lấy tên loại hiện tại
        String currentName = getLoaiNameById(loai.getStrMaloai());
        
        // Kiểm tra nếu tên loại đã thay đổi và tên mới đã tồn tại
        if (!loai.getStrTenloai().equals(currentName) && isLoaiNameExists(loai.getStrTenloai())) {
            return false;
        }
        
        return loaiDAL.updateLoai(loai);
    }
    
    /**
     * Xóa một loại sản phẩm khỏi hệ thống
     * @param maLoai Mã loại cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteLoai(String maLoai) {
        // Kiểm tra mã loại có tồn tại không
        if (!isLoaiExists(maLoai)) {
            return false;
        }
        
        // Kiểm tra loại đã được sử dụng trong sản phẩm chưa
        if (isLoaiUsedInProduct(maLoai)) {
            return false;
        }
        
        return loaiDAL.deleteLoai(maLoai);
    }
    
    /**
     * Kiểm tra sự tồn tại của loại sản phẩm theo mã
     * @param maLoai Mã loại cần kiểm tra
     * @return true nếu tồn tại, false nếu không tồn tại
     */
    public boolean isLoaiExists(String maLoai) {
        ArrayList<LoaiDTO> loaiList = loaiDAL.getLoaiList();
        
        for (LoaiDTO loai : loaiList) {
            if (loai.getStrMaloai().equals(maLoai)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Kiểm tra sự tồn tại của tên loại sản phẩm
     * @param tenLoai Tên loại cần kiểm tra
     * @return true nếu tồn tại, false nếu không tồn tại
     */
    public boolean isLoaiNameExists(String tenLoai) {
        ArrayList<LoaiDTO> loaiList = loaiDAL.getLoaiList();
        
        for (LoaiDTO loai : loaiList) {
            if (loai.getStrTenloai().equalsIgnoreCase(tenLoai)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Lấy tên loại sản phẩm theo mã loại
     * @param maLoai Mã loại cần tìm
     * @return Tên loại sản phẩm, null nếu không tìm thấy
     */
    public String getLoaiNameById(String maLoai) {
        ArrayList<LoaiDTO> loaiList = loaiDAL.getLoaiList();
        
        for (LoaiDTO loai : loaiList) {
            if (loai.getStrMaloai().equals(maLoai)) {
                return loai.getStrTenloai();
            }
        }
        
        return null;
    }
    
    /**
     * Lấy đối tượng loại sản phẩm theo mã
     * @param maLoai Mã loại cần tìm
     * @return Đối tượng loại sản phẩm, null nếu không tìm thấy
     */
    public LoaiDTO getLoaiById(String maLoai) {
        ArrayList<LoaiDTO> loaiList = loaiDAL.getLoaiList();
        
        for (LoaiDTO loai : loaiList) {
            if (loai.getStrMaloai().equals(maLoai)) {
                return loai;
            }
        }
        
        return null;
    }
    
    /**
     * Kiểm tra loại sản phẩm đã được sử dụng trong bảng sản phẩm chưa
     * @param maLoai Mã loại cần kiểm tra
     * @return true nếu đã được sử dụng, false nếu chưa được sử dụng
     */
    public boolean isLoaiUsedInProduct(String maLoai) {
        // Phương thức này sẽ cần tích hợp với SanPhamBLL để kiểm tra
        // Tạm thời trả về false để cho phép xóa
        return false;
    }
    public String getIdbyLoainame(String name){
        for (LoaiDTO loaidto:list_loai){
            if(loaidto.getStrTenloai().equals(name)){
                return loaidto.getStrMaloai();
            }
        }
        return null;
    }
      public ArrayList<String> searchMaloaifromTenloai(String tenloai) {
        ArrayList<String> list=new ArrayList<>();
        for (LoaiDTO loaidto : list_loai) {
            if(loaidto.getStrTenloai().toLowerCase().contains(tenloai.toLowerCase())){
                list.add(loaidto.getStrMaloai());
            }
        }
        return list;
    }
}