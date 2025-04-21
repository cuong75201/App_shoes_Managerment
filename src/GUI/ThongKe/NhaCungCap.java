/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThongKe;

import GUI.component.CustomTable;
import GUI.component.CustomButton;

import Utils.XuatExcel;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import BLL.NhaCungCapBLL;
import BLL.PhieuNhapBLL;

import DTO.NhaCungCapDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
public class NhaCungCap extends JPanel{
        private int width=1116,height=600;
        private JScrollPane scrollPane;
        private CustomTable tblncc;
        private NhaCungCapBLL ncc;
        private PhieuNhapBLL pn;
        private CustomButton btnReset,btnXuatExcel;
    public NhaCungCap(){
        ncc=new NhaCungCapBLL();
        pn=new PhieuNhapBLL();
        init();
    }
    public void init(){
        btnReset=new CustomButton("Làm mới");
        btnReset.setBackground(Color.decode("#f4a261"));
        btnReset.setForeground(Color.WHITE);
        btnReset.setBorderColor(btnReset.getBackground());
        btnReset.setBounds(20,0,100,40);
        
        btnXuatExcel=new CustomButton("Xuất Excel");
        btnXuatExcel.setBackground(Color.decode("#A5D6A7"));
        btnXuatExcel.setForeground(Color.WHITE);
        btnXuatExcel.setBorderColor(btnXuatExcel.getBackground());
        btnXuatExcel.setBounds(140,0,100,40);
       
        
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
        scrollPane.setBounds(20,50,width-50,height-50);
        
         btnReset.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                model.setRowCount(0);
                for(NhaCungCapDTO nccdto:ncc.getListNhaCungCap()){
            Object[] rowData={
              nccdto.getStrMaNCC(),
              nccdto.getStrTenNCC(),
              pn.getTongTienByMaNCC(nccdto.getStrMaNCC())
            };
            model.addRow(rowData);
        }
            }
        });
         btnXuatExcel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
               XuatExcel.exportTableToExcel(tblncc);
            }
        });
        
          this.setLayout(null);
          this.add(scrollPane);
          this.add(btnReset);
          this.add(btnXuatExcel);
        this.setBackground(Color.decode("#F0F7FA"));
    }
}
