/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThongKe;

import GUI.component.CustomTable;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import BLL.NhaCungCapBLL;
import BLL.PhieuNhapBLL;

import DTO.NhaCungCapDTO;
import javax.swing.table.DefaultTableModel;
public class NhaCungCap extends JPanel{
        private int width=1116,height=600;
        private JScrollPane scrollPane;
        private CustomTable tblncc;
        private NhaCungCapBLL ncc;
        private PhieuNhapBLL pn;
    public NhaCungCap(){
        ncc=new NhaCungCapBLL();
        pn=new PhieuNhapBLL();
        init();
    }
    public void init(){
        
        DefaultTableModel model =new DefaultTableModel(new String[]{"Mã nhà cung cấp","Tên nhà cung cấp","Tổng số tiền"},0);
        for(NhaCungCapDTO nccdto:ncc.getListNhaCungCap()){
            Object[] rowData={
              nccdto.getStrMaNCC(),
              nccdto.getStrTenNCC(),
              pn.getTongTienByMaNCC(nccdto.getStrMaNCC())
            };
            model.addRow(rowData);
        }
        tblncc=new CustomTable(model);
        scrollPane=new JScrollPane(tblncc);
        scrollPane.setBounds(20,0,width-50,height);
          this.setLayout(null);
          this.add(scrollPane);
        this.setBackground(Color.decode("#F0F7FA"));
    }
}
