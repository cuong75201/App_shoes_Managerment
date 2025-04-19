/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.component;

import DTO.ThuongHieuDTO;
import javax.swing.JPanel;

import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;

import Utils.CreateComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;

public class PanelFunction extends JPanel {

    public JButton btnThem, btnXoa, btnSua, btnChiTiet, btnXuatExcel,btnReset;

    public CustomComboBox cbfilter;
    public customTextField fieldSearch;
    public CustomButton btnSearch;
    public int width = 1116;
    public int width_pos=220;

    public PanelFunction() {
        cbfilter = new CustomComboBox();
        init();
    }

    public void setCbfilter(CustomComboBox cbfilter) {
        this.cbfilter = cbfilter;
        this.cbfilter.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        this.cbfilter.setBounds(600, 45, 130, 35);
        this.add(cbfilter);
    }

    public void init() {
        btnSearch = new CustomButton("Search");
        btnSearch.setBackground(Color.decode("#2ECC71"));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBounds(980, 45, 80, 35);
        btnSearch.setBorderColor(btnSearch.getBackground());

        fieldSearch = new customTextField();
        fieldSearch.setText("Tìm kiếm...");
        fieldSearch.setBorderColor(Color.decode("#E1E1E1"));
        fieldSearch.setBounds(760, 45, 200, 35);

        fieldSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = fieldSearch.getText();
                if (text.equals("Tìm kiếm...")) {
                    fieldSearch.setText("");
                    fieldSearch.setBorderColor(Color.BLUE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = fieldSearch.getText();
                if (text.isEmpty()) {
                    fieldSearch.setText("Tìm kiếm...");

                }
                fieldSearch.setBorderColor(Color.decode("#E1E1E1"));

            }

        });

        btnThem = CreateComponent.createBtn("add_icon.png", "Thêm");
        btnThem.setBounds(0, 10, 110, 100);

        btnSua = CreateComponent.createBtn("icon-update.png", "Sửa");
        btnSua.setBounds(110, 10, 110, 100);

        btnXoa = CreateComponent.createBtn("icon_delete.png", "Xóa");
        btnXoa.setBounds(220, 10, 110, 100);

       
        this.setLayout(null);
        this.setBounds(20, 10, width - 50, 120);
        this.setBackground(Color.WHITE);
        this.add(btnThem);
        this.add(btnXoa);
        this.add(btnSua);
        this.add(cbfilter);
        this.add(fieldSearch);
        this.add(btnSearch);

    }
    public void setBtnChitiet(){
         btnChiTiet = CreateComponent.createBtn("info.png", "Chi tiết");
         width_pos+=110;
        btnChiTiet.setBounds(width_pos, 10, 110, 100);
         this.add(btnChiTiet);
    }
    public void setBtnReset(){
       btnReset = CreateComponent.createBtn("refresh_icon.png", "Tải lại");
       width_pos+=110;
        btnReset.setBounds(width_pos, 10, 110, 100);
         this.add(btnReset);
    }
}