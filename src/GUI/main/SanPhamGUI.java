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
import Utils.CreateComponent;
import java.awt.Color;

public class SanPhamGUI extends JPanel {

    ArrayList<SanPhamDTO> list_sp;
    SanPhamBLL sp;
    private JButton btnThem, btnXoa, btnSua, btnChiTiet, btnXuatExcel;
    private JPanel pnButton;

    public SanPhamGUI() {
        list_sp = new ArrayList<>();
        sp = new SanPhamBLL();
        list_sp = sp.getListProduct();
        init();
    }

    public void init() {

        btnThem = CreateComponent.createBtn("icon_delete.png", "Xoa");
        btnThem.setBounds(30, 0, 100, 75);

        pnButton = new JPanel(null);
        pnButton.add(btnThem);
        pnButton.setBounds(0, 0, 300, 300);
        pnButton.setBackground(Color.WHITE);


        this.setLayout(null);
        this.add(pnButton);
        this.setBounds(250, 0, 1116, 800);
        this.setBackground(Color.WHITE);

    }
}
