/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThongKe;

import javax.swing.JPanel;

import BLL.SanPhamBLL;
import BLL.KhachHangBLL;
import BLL.NhanVienBLL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TongQuan extends JPanel {
    JPanel pnSanPham,pnKhachHang,pnNhanVien;
    SanPhamBLL sp;
    KhachHangBLL kh;
    NhanVienBLL nv;
    private int width=1116,height=600;
    public TongQuan() {
        sp=new SanPhamBLL();
        kh=new KhachHangBLL();
        nv=new NhanVienBLL();
        init();
    }

    public void init() {
        pnSanPham=taoThongKePanel(System.getProperty("user.dir") + "/src/Assets/shoes_thongke.png",sp.CountSP() + "","Sản phẩm có trong kho");
        pnSanPham.setBounds(width / 2 - 300,50,600,120);
        
        pnKhachHang=taoThongKePanel(System.getProperty("user.dir") + "/src/Assets/icon_func/client.png",kh.getKhachHangList().size()+"","Khách hàng từ trước đến nay");
       pnKhachHang.setBounds(width / 2 - 300,200,600,120);
        
       pnNhanVien=taoThongKePanel(System.getProperty("user.dir") + "/src/Assets/nhanvien.png",nv.getListNhanVien().size()+"","Số lượng nhân viên trong cửa hàng");
       pnNhanVien.setBounds(width / 2 - 300,350,600,120);
        this.setLayout(null);
        this.add(pnSanPham);
        this.add(pnKhachHang);
        this.add(pnNhanVien);
        this.setBackground(Color.decode("#F0F7FA"));
    }

    public JPanel taoThongKePanel(String iconPath, String labelTop, String labelBottom) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));

        // Load và resize icon
        ImageIcon rawIcon = new ImageIcon(iconPath);
        Image scaledImage = rawIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel iconLabel = new JLabel(resizedIcon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel topLabel = new JLabel(labelTop);
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        topLabel.setForeground(new Color(86, 101, 115));
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bottomLabel = new JLabel(labelBottom);
        bottomLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        bottomLabel.setForeground(new Color(86, 101, 115));
        bottomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(topLabel);
        textPanel.add(bottomLabel);

        // Thêm vào panel chính
        panel.add(iconLabel);
        panel.add(textPanel);

        return panel;
    }
}
