/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change xxis license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit xxis template
 */
package BLL;

import DTO.XuatXuDTO;
import DAL.XuatXuDAL;
import java.util.ArrayList;

public class XuatXuBLL {

    private ArrayList<XuatXuDTO> list_XuatXu;
    private XuatXuDAL xx;

    public XuatXuBLL() {
        list_XuatXu = new ArrayList<>();
        xx = new XuatXuDAL();
        list_XuatXu = xx.getListXuatXu();
    }

    public ArrayList<XuatXuDTO> getList_XuatXu() {
        return list_XuatXu;
    }

    public boolean addXuatXu(XuatXuDTO xxDTO) {
        if (xx.InsertXuatXu(xxDTO)) {
            list_XuatXu.add(xxDTO);
            return true;
        }
        return false;
    }

    public Boolean UpdateXuatXu(XuatXuDTO xxDTO) {
        if (xx.UpdateXuatXu(xxDTO)) {

            // duyệt từng phẩn tử
            for (XuatXuDTO xxdto : list_XuatXu) {
                if (xxDTO.getStrMaxuatxu().equals(xxdto.getStrMaxuatxu())) {
                    xxdto.setStrTennuoc(xxDTO.getStrTennuoc());
                    return true;
                }
            }
        }

        return false;
    }

    public Boolean deleteXuatXu(XuatXuDTO xxDTO) {
        if (xx.DeleteXuatXu(xxDTO)) {

            // duyệt từng phẩn tử
            for (XuatXuDTO xxu : list_XuatXu) {
                if (xxu.getStrMaxuatxu().equals(xxDTO.getStrMaxuatxu())) {
                    list_XuatXu.remove(xxu);
                    return true;
                }
            }
        }

        return false;
    }
}
