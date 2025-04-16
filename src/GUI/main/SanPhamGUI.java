/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import DTO.SanPhamDTO;
import BLL.SanPhamBLL;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
public class SanPhamGUI extends JPanel {
    ArrayList<SanPhamDTO> list_sp;
    SanPhamBLL sp;
    private JButton btnThem,btnXoa,btnSua,btnChiTiet,btnXuatExcel;
    private JPanel pnButton;
    public SanPhamGUI(){
        list_sp=new ArrayList<>();
        sp=new SanPhamBLL();
        list_sp=sp.getListProduct();
        init();
    }
    public void init(){
        pnButton=new JPanel(null);
        
        
        this.add(pnButton);
    }
}
