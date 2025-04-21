/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DTO.ThuongHieuDTO;
import DAL.ThuongHieuDAL;
import java.util.ArrayList;

public class ThuongHieuBLL {

    private ArrayList<ThuongHieuDTO> list_ThuongHieu;
    private ThuongHieuDAL th;

    public ThuongHieuBLL() {
        list_ThuongHieu = new ArrayList<>();
        th = new ThuongHieuDAL();
        list_ThuongHieu = th.getListThuongHieu();
    }

    public ArrayList<ThuongHieuDTO> getList_ThuongHieu() {
        return list_ThuongHieu;
    }

    public boolean addThuongHieu(ThuongHieuDTO thDTO) {
        if (th.InsertThuongHieu(thDTO)) {
            list_ThuongHieu.add(thDTO);
            return true;
        }
        return false;
    }

    public Boolean UpdateThuongHieu(ThuongHieuDTO thDTO) {
        if (th.UpdateThuongHieu(thDTO)) {

            // duyệt từng phẩn tử
            for (ThuongHieuDTO thdto : list_ThuongHieu) {
                if (thDTO.getStrMathuonghieu().equals(thdto.getStrMathuonghieu())) {
                    thdto.setStrDiachi(thDTO.getStrDiachi());
                    thdto.setStrEmail(thDTO.getStrEmail());
                    thdto.setStrTenthuonghieu(thDTO.getStrTenthuonghieu());
                    return true;
                }
            }
        }

        return false;
    }

    public Boolean deleteThuongHieu(ThuongHieuDTO thDTO) {
        if (th.DeleteThuongHieu(thDTO)) {

            // duyệt từng phẩn tử
            for (ThuongHieuDTO thieu : list_ThuongHieu) {
                if (thieu.getStrMathuonghieu().equals(thDTO.getStrMathuonghieu())) {
                    list_ThuongHieu.remove(thieu);
                    return true;
                }
            }
        }

        return false;
    }
    public boolean deleteThuongHieu(String Mathuonghieu) {
        return th.deleteThuongHieu(Mathuonghieu);
    }

    public String getTenTHfromMaTH(String math) {
        for (ThuongHieuDTO thdto : list_ThuongHieu) {
            if (thdto.getStrMathuonghieu().equals(math)) {
                return thdto.getStrTenthuonghieu();
            }
        }
        return null;
    }

    public String getMaTHfromTenTH(String tenth) {
        for (ThuongHieuDTO thdto : list_ThuongHieu) {
            if (thdto.getStrTenthuonghieu().equals(tenth)) {
                return thdto.getStrMathuonghieu();
            }
        }
        return null;
    }

    public ArrayList<String> searchMaTHfromTenTH(String tenth) {
        ArrayList<String> list=new ArrayList<>();
        for (ThuongHieuDTO thdto : list_ThuongHieu) {
            if(thdto.getStrTenthuonghieu().toLowerCase().contains(tenth.toLowerCase())){
                list.add(thdto.getStrMathuonghieu());
            }
        }
        return list;
    }
}
