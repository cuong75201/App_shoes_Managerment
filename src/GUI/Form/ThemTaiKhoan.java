/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

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
import GUI.component.customPasswordField;

import BLL.TaiKhoanBLL;
import GUI.component.customPasswordField;

import java.awt.Color;

public class ThemTaiKhoan extends JFrame {

    TaiKhoanBLL tk;
    int width = 490, height = 800;
    JPanel panelTop, pnContent;
    JLabel lbtitle;
    customTextField faccount;
    customPasswordField fpass;
    JLabel lbaccount, lbpass, lbcapbac, lbhoatdong;
    CustomComboBox cbcapbac, cbhoatdong;
    CustomButton btnSave,btnCancel;
    public ThemTaiKhoan() {
        tk = new TaiKhoanBLL();
        init();
    }

    public JLabel CreateLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        return label;
    }

    public customTextField CreateField() {
        customTextField field = new customTextField();
        field.setSize(width - 70, 50);
        return field;
    }
      public customPasswordField CreateFieldPassword() {
        customPasswordField field = new customPasswordField();
        field.setSize(width - 70, 50);
        return field;
    }

    public void init() {
        lbtitle = new JLabel("THÊM TÀI KHOẢN MỚI");
        lbtitle.setForeground(Color.WHITE);
        lbtitle.setFont(new Font("SansSerif", Font.BOLD, 30));

        panelTop = new JPanel(new GridBagLayout());
        panelTop.setBackground(Color.decode("#167AC6"));
        panelTop.setBounds(0, 0, width, 50);
        panelTop.add(lbtitle);

        lbaccount = CreateLabel("Account");
        lbaccount.setBounds(30, 20, 100, 50);
        faccount = CreateField();
        faccount.setLocation(30, 70);
        faccount.setEditable(false);
        faccount.setFocusable(false);

        lbpass = CreateLabel("Password");
        lbpass.setBounds(30, 150, 100, 50);
        fpass = CreateFieldPassword();
        fpass.setLocation(30, 200);

        lbcapbac = CreateLabel("Chức vụ");
        lbcapbac.setBounds(30, 280, 100, 50);
        cbcapbac = new CustomComboBox();
        for (int i = 0; i < 6; i++) {
            cbcapbac.addItem(tk.CapBactoChucVu(i + 1));
        }
        cbcapbac.setBounds(30, 330, width - 70, 50);

        lbhoatdong = CreateLabel("Trạng thái");
        lbhoatdong.setBounds(30, 410, 100, 50);
        cbhoatdong = new CustomComboBox();
        cbhoatdong.addItem("Hoạt động");
        cbhoatdong.addItem("Khóa");
        cbhoatdong.setBounds(30, 460, width - 70, 50);

        btnSave=new CustomButton("Save");
        btnSave.setBackground(Color.decode("#389FD6"));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBorderColor(btnSave.getBackground());
        btnSave.setBounds(110,600,100,40);
        
        btnCancel=new CustomButton("Cancel");
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorderColor(btnCancel.getBackground());
        btnCancel.setBounds(220,600,100,40);
        
        pnContent = new JPanel(null);
        pnContent.setBackground(Color.WHITE);
        pnContent.setBounds(0, 50, width, height - 50);
        pnContent.add(lbaccount);
        pnContent.add(faccount);
        pnContent.add(lbpass);
        pnContent.add(fpass);
        pnContent.add(lbcapbac);
        pnContent.add(cbcapbac);
        pnContent.add(lbhoatdong);
        pnContent.add(cbhoatdong);
        pnContent.add(btnSave);
        pnContent.add(btnCancel);

        this.setSize(width, height);
        this.add(panelTop);
        this.add(pnContent);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ThemTaiKhoan();
    }

}
