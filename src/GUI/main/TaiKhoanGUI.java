/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import javax.swing.JPanel;

import GUI.component.PanelFunction;

import GUI.component.CustomTable;

import BLL.TaiKhoanBLL;
import DTO.TaiKhoanDTO;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TaiKhoanGUI extends JPanel {

    public ArrayList<TaiKhoanDTO> list_tk;
    public TaiKhoanBLL tk;
    private int width = 1116, height = 800;
    PanelFunction pnButton;
    private JScrollPane scrollpane;
    private CustomTable tbltaikhoan;

    public TaiKhoanGUI() {
        pnButton = new PanelFunction();
        list_tk = new ArrayList<>();
        tk = new TaiKhoanBLL();
        list_tk = tk.getListAccount();
        init();
    }

    public void init() {
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên đăng nhập", "Quyền", "Trạng thái"}, 0);
        for (TaiKhoanDTO tkdto : list_tk){
            Object[] rowData={
                tkdto.getStrTenDangNhap(),
            };
        }
        tbltaikhoan = new CustomTable(model);
        scrollpane = new JScrollPane(tbltaikhoan);
        scrollpane.setBounds(20, 150, width - 50, 600);

        this.setLayout(null);
        this.add(pnButton);
        this.add(scrollpane);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
    }
}
