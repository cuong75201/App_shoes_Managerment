/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import GUI.component.CustomButton;

import Utils.CreateComponent;
import GUI.ThuoctTinh.ThuongHieu;
import GUI.ThuoctTinh.MauSac;
import GUI.ThuoctTinh.XuatXu;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ThuocTinhGUI extends JPanel {

    private int width = 1116, height = 800;
    private JScrollPane scrollPane;
    private CustomButton btnThuongHieu,btnMauSac,btnXuatXu;
    private JPanel pnButton;
    private CardLayout cardLayout;
    private JPanel pnMain;

    public ThuocTinhGUI() {
        cardLayout = new CardLayout();
        pnMain = new JPanel(cardLayout);
        init();
    }
//    public CustomButton CreatBtn(String img,String lbtop,S){
//        
//    }

    public void init() {
        
        ThuongHieu thuonghieu =new ThuongHieu();
        pnMain.add(thuonghieu,"ThuongHieu");
         MauSac mausac=new MauSac();
        pnMain.add(mausac,"MauSac");
        XuatXu xuatxu=new XuatXu();
        pnMain.add(xuatxu,"XuatXu");
    
        pnMain.setBounds(0, 150, width, 600);
        
        btnThuongHieu = new CreateComponent().createBtn("pngtree-nike-brand-shoe-png-image_19854449.png", "ThuongHieu");
        btnThuongHieu.addActionListener(e -> cardLayout.show(pnMain, "ThuongHieu"));
         btnMauSac = new CreateComponent().createBtn("pngtree-the-adidas-shoe-illustration-is-depicting-the-brand-and-logo-vector-png-image_6925001.png", "MauSac");
        btnMauSac.addActionListener(e -> cardLayout.show(pnMain, "MauSac"));
        btnXuatXu = new CreateComponent().createBtn("image(20444).png", "XuatXu");
        btnXuatXu.addActionListener(e -> cardLayout.show(pnMain, "XuatXu"));

      
        btnThuongHieu.setBounds(0, 10, 110, 100);
          btnMauSac.setBounds(150, 10, 110, 100);
        btnXuatXu.setBounds(300, 10, 130, 100);

        pnButton = new JPanel(null);
        pnButton.setBackground(Color.WHITE);
        pnButton.add(btnThuongHieu);
          pnButton.add(btnMauSac);
        pnButton.add(btnXuatXu);
        pnButton.setBounds(width / 2 - 300, 10, 600, 120);
        

        this.setLayout(null);
        this.add(pnButton);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
        this.add(pnMain);

    }

}