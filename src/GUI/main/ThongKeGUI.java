/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import GUI.component.CustomButton;

import Utils.CreateComponent;

import GUI.ThongKe.TongQuan;
import GUI.ThongKe.DoanhThu;
import GUI.ThongKe.NhaCungCap;
import GUI.ThongKe.KhachHang;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ThongKeGUI extends JPanel {

    private int width = 1116, height = 800;
    private JScrollPane scrollPane;
    private CustomButton btnTongQuan, btnDoanhThu, btnNcc, btnKhachHang;
    private JPanel pnButton;
    private CardLayout cardLayout;
    private JPanel pnMain;

    public ThongKeGUI() {
        cardLayout = new CardLayout();
        pnMain = new JPanel(cardLayout);
        init();
    }
//    public CustomButton CreatBtn(String img,String lbtop,S){
//        
//    }

    public void init() {
        
        TongQuan tongQuan =new TongQuan();
        pnMain.add(tongQuan,"TongQuan");
        DoanhThu doanhthu=new DoanhThu();
        pnMain.add(doanhthu,"DoanhThu");
        NhaCungCap ncc=new NhaCungCap();
        pnMain.add(ncc,"NhaCungCap");
        KhachHang kh=new KhachHang();
        pnMain.add(kh,"KhachHang");
        pnMain.setBounds(0, 150, width, 600);
        
        btnTongQuan = new CreateComponent().createBtn("tongquan.png", "Tông quan");
        btnTongQuan.addActionListener(e -> cardLayout.show(pnMain, "TongQuan"));

        btnDoanhThu = new CreateComponent().createBtn("pngegg.png", "Doanh Thu");
        btnDoanhThu.addActionListener(e -> cardLayout.show(pnMain, "DoanhThu"));

        btnNcc = new CreateComponent().createBtn("supplier.png", "Nhà cung cấp");
        btnNcc.addActionListener(e -> cardLayout.show(pnMain, "NhaCungCap"));

        btnKhachHang = new CreateComponent().createBtn("client.png", "Khách hàng");
        btnKhachHang.addActionListener(e -> cardLayout.show(pnMain,"KhachHang"));

        btnTongQuan.setBounds(0, 10, 110, 100);
        btnDoanhThu.setBounds(150, 10, 110, 100);
        btnNcc.setBounds(300, 10, 130, 100);
        btnKhachHang.setBounds(450, 10, 130, 100);

        pnButton = new JPanel(null);
        pnButton.setBackground(Color.WHITE);
        pnButton.add(btnTongQuan);
        pnButton.add(btnDoanhThu);
        pnButton.add(btnKhachHang);
        pnButton.add(btnNcc);
        pnButton.setBounds(width / 2 - 300, 10, 600, 120);
        

        this.setLayout(null);
        this.add(pnButton);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
        this.add(pnMain);

    }

}
