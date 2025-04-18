/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.panelform;

/**
 *
 * @author cuong
 */
public class ThemSanPham extends JFrame {

    private int width = 1250, height = 600;
    private JPanel panelTop, panelBottom;
    private JLabel lbtitle;
    public panelform pnMasp, pnTensp, pnSoluong, pnGia, pnSize, pnDoituong, pnChatlieu, pnloai, pnxx, pnmau, pnthuonghieu;
    public CustomComboBox cbloai, cbxx, cbmau, cbthuonghieu;
    public CustomButton btnSave, btnCancel;

    public ThemSanPham() {
        cbloai = new CustomComboBox();
        cbmau = new CustomComboBox();
        cbxx = new CustomComboBox();
        cbthuonghieu = new CustomComboBox();
        init();
    }
    public ThemSanPham(CustomComboBox cbloai,CustomComboBox cbmau, CustomComboBox cbxx,CustomComboBox cbthuonghieu){
        this.cbloai=cbloai;
        this.cbmau=cbmau;
        this.cbxx=cbxx;
        this.cbthuonghieu=cbthuonghieu;
        init();
    }

    public void init() {

        lbtitle = new JLabel("THÊM SẢN PHẨM MỚI");
        lbtitle.setForeground(Color.WHITE);
        lbtitle.setFont(new Font("SansSerif", Font.BOLD, 30));

        panelTop = new JPanel(new GridBagLayout());
        panelTop.setBackground(Color.decode("#167AC6"));
        panelTop.setBounds(0, 0, width, 100);
        panelTop.add(lbtitle);

        pnMasp = new panelform("Mã giày:");
        pnMasp.setLocation(30, 150);
        pnMasp.setField();
        pnMasp.field.setEditable(false);
        pnMasp.field.setFocusable(false);

        pnTensp = new panelform("Tên giày: ");
        pnTensp.setLocation(30, 250);
        pnTensp.setField();

        pnSoluong = new panelform("Số lượng: ");
        pnSoluong.setLocation(30, 350);
        pnSoluong.setField();

        pnGia = new panelform("Giá: ");
        pnGia.setLocation(350, 150);
        pnGia.setField();

        pnSize = new panelform("Size: ");
        pnSize.setLocation(350, 250);
        pnSize.setField();

        pnDoituong = new panelform("Đối tượng");
        pnDoituong.setLocation(350, 350);
        pnDoituong.setField();

        pnChatlieu = new panelform("Chất liệu");
        pnChatlieu.setLocation(670, 150);
        pnChatlieu.setField();

        pnloai = new panelform("Loại");
        pnloai.setLocation(670, 250);
        pnloai.setComboBox(cbloai);

        pnmau = new panelform("Màu sắc");
        pnmau.setLocation(670, 350);
        pnmau.setComboBox(cbmau);

        pnxx = new panelform("Xuất Xứ");
        pnxx.setLocation(990, 150);
        pnxx.setComboBox(cbxx);

        pnthuonghieu = new panelform("Thương hiệu");
        pnthuonghieu.setLocation(990, 250);
        pnthuonghieu.setComboBox(cbthuonghieu);

        btnSave = new CustomButton("Lưu");
        btnSave.setBackground(Color.decode("#2ECC71"));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBorderColor(btnSave.getBackground());
        btnSave.setBounds(550, 20, 70, 40);

        btnCancel = new CustomButton("Hủy bỏ");
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorderColor(btnCancel.getBackground());
        btnCancel.setBounds(630, 20, 100, 40);

        panelBottom = new JPanel(null);
        panelBottom.setBackground(Color.WHITE);
        panelBottom.setBounds(0, 450, width, 100);
        panelBottom.add(btnSave);
        panelBottom.add(btnCancel);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLayout(null);
        this.add(panelTop);
        this.add(pnMasp);
        this.add(pnTensp);
        this.add(pnSoluong);
        this.add(pnGia);
        this.add(pnSize);
        this.add(pnDoituong);
        this.add(pnChatlieu);
        this.add(pnloai);
        this.add(pnmau);
        this.add(pnxx);
        this.add(pnthuonghieu);
        this.add(panelBottom);
//        this.setVisible(true);

    }

    public static void main(String[] args) {
        new ThemSanPham();
    }
}
