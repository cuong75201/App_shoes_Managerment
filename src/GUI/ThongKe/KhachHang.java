package GUI.ThongKe;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import BLL.KhachHangBLL;
import BLL.HoaDonBLL;

import DTO.KhachHangDTO;

import GUI.component.CustomTable;
import GUI.component.customTextField;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class KhachHang extends JPanel {

    private int width = 1116, height = 600;
    private JScrollPane scrollPane;
    private JPanel pnSearch;
    private CustomTable tblkhachhang;
    private customTextField fieldSearch;
    private KhachHangBLL kh;
    private HoaDonBLL hd;

    public KhachHang() {
        kh = new KhachHangBLL();
        hd = new HoaDonBLL();
        init();
    }

    public void init() {
        pnSearch=new JPanel(null);
        fieldSearch=new customTextField();
        pnSearch.setBounds(20,0,width-50,70);
        pnSearch.add(fieldSearch);
        pnSearch.setBackground(Color.decode("#F0F7FA"));
        fieldSearch.setBounds(260,20,600,40);
        fieldSearch.setText("Tìm kiếm...");
        fieldSearch.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if(fieldSearch.getText().equals("Tìm kiếm...")){
                    fieldSearch.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(fieldSearch.getText().isEmpty()){
                    fieldSearch.setText("Tìm kiếm...");
                }
            }
            
        });
       
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã khách hàng", "Tên khách hàng", "Tổng số tiền"}, 0);
        for (KhachHangDTO khdto : kh.getKhachHangList()) {
            Object[] rowData = {
                khdto.getStrMaKH(),
               khdto.getStrHo()+" "+ khdto.getStrTen(),
                hd.getTongTienByMaHD(khdto.getStrMaKH())
            };
            model.addRow(rowData);
        }
        tblkhachhang = new CustomTable(model);
        scrollPane = new JScrollPane(tblkhachhang);
        scrollPane.setBounds(20, 80, width - 50, height);
        
         fieldSearch.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                if (fieldSearch.getText().isEmpty()) {
                    tblkhachhang.setmodel(model);
                    return;
                }
                DefaultTableModel modelfilter = new DefaultTableModel(new String[]{"Mã khách hàng", "Tên khách hàng", "Tổng số tiền"}, 0);
                ArrayList<KhachHangDTO> khlist = kh.searchKhachHangByHoTen(fieldSearch.getText());
        for (KhachHangDTO khdto : khlist) {
            Object[] rowData = {
                khdto.getStrMaKH(),
               khdto.getStrHo()+" "+ khdto.getStrTen(),
                hd.getTongTienByMaHD(khdto.getStrMaKH())
            };
            modelfilter.addRow(rowData);
        }
        tblkhachhang.setmodel(modelfilter);
            }
            
        });
        
        this.add(scrollPane);
        this.add(pnSearch);

        this.setLayout(null);
        this.setBackground(Color.decode("#F0F7FA"));
    }
}
