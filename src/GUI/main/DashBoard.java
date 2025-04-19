/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import DTO.TaiKhoanDTO;
import DTO.NhanVienDTO;

import BLL.TaiKhoanBLL;
import BLL.NhanVienBLL;

import GUI.component.CustomButton;
import GUI.main.SanPhamGUI;
import GUI.main.TaiKhoanGUI;
import GUI.main.HoaDonGUI;
import GUI.main.KhachHangGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.CardLayout;

import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DashBoard extends JFrame {

    private Timer timer;

    private ArrayList<String> pathImg;
    private TaiKhoanDTO tk;
    private NhanVienDTO nv;
    private NhanVienBLL nvBLL;
    private CardLayout cardLayout;
    private JPanel pnButton, pnInfor, pnContent, pnMain;
    private JButton btnDashBoard, btnSanpham, btnNhaCungCap, btnNhanVien, btnBill, btnKhachHang, btnThuocTinh, btnPhieuNhap, btnKhuyenMai, btnTaiKhoan, btnThongKe, btnPhanQuyen, btnLogout;
    private JLabel lbinfor, lbicon, lbroles, lbhomecontent, lbhomebanner, lbtitle;

    public DashBoard(TaiKhoanDTO tk) {
        nvBLL = new NhanVienBLL();
        this.nv = nvBLL.searchNhanVienByMa(tk.getStrTenDangNhap());
        this.init();
    }

    public void init() {
        lbhomecontent = new JLabel("SHOES MANAGEMENT APP");
        lbhomecontent.setFont(new Font("Sans-serif", Font.BOLD, 30));
        lbhomecontent.setForeground(Color.decode("#0295B1"));
        lbhomecontent.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/shoes_icon.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
        lbhomecontent.setBounds(300, 30, 500, 35);

        lbtitle = new JLabel("HỆ THỐNG QUẢN LÝ GIÀY HÀNG ĐẦU ĐẤT NƯỚC");
        lbtitle.setBounds(200, 70, 800, 30);
        lbtitle.setForeground(Color.decode("#0295B1"));
        lbtitle.setFont(new Font("Sans-serif", Font.BOLD, 30));

        lbhomebanner = new JLabel();
        lbhomebanner.setBounds(150, 150, 800, 550);
        lbhomebanner.setIcon(new ImageIcon(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/shoes-sale-social-media-post-square-banner-template-design_505751-4894.jpg").getImage().getScaledInstance(lbhomebanner.getWidth(), lbhomebanner.getHeight(), Image.SCALE_SMOOTH)
        ));
        SlideShow();
        timer.start();

        pnContent = new JPanel(null);
        pnContent.setBounds(250, 0, 1116, 800);
        pnContent.setBackground(Color.decode("#F0F7FA"));
        pnContent.add(lbhomecontent);
        pnContent.add(lbhomebanner);
        pnContent.add(lbtitle);

        cardLayout = new CardLayout();
        pnMain = new JPanel(cardLayout);
        pnMain.setBounds(250, 0, 1116, 800);
        pnMain.add(pnContent, "DashBoard");
        SanPhamGUI pnSanPham = new SanPhamGUI();
        pnMain.add(pnSanPham, "SanPham");
        HoaDonGUI pnHoaDon = new HoaDonGUI();
        pnMain.add(pnHoaDon, "Bill");
        TaiKhoanGUI pnTaikhoan = new TaiKhoanGUI();
        pnMain.add(pnTaikhoan,"TaiKhoan");
        KhachHangGUI pnKhachHang = new KhachHangGUI();
        pnMain.add(pnKhachHang, "KhachHang");
        KhuyenMaiGUI pnKhuyenMai = new KhuyenMaiGUI();
        pnMain.add(pnKhuyenMai, "KhuyenMai");

        pnInfor = new JPanel();
        pnInfor.setLayout(null);
        pnInfor.setBounds(0, 0, 250, 100);
        pnInfor.setBackground(Color.WHITE);
        lbinfor = new JLabel();
        lbinfor.setBounds(80, 20, 150, 20);
        lbinfor.setText(nv.getStrHo() + " " + nv.getStrTen());
        lbinfor.setFont(new Font("Sans-serif", Font.BOLD, 15));

        lbicon = new JLabel();
        lbicon.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/ImgNhanVien/" + nv.getStrAnh()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        lbicon.setBounds(10, 20, 60, 60);

        lbroles = new JLabel(nv.getStrChucVu());
        lbroles.setFont(new Font("Sans-serif", Font.ITALIC, 13));
        lbroles.setBounds(80, 40, 150, 30);

        pnInfor.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#CED8DC")));

        pnInfor.add(lbinfor);
        pnInfor.add(lbicon);
        pnInfor.add(lbroles);
        pnButton = new JPanel();
        pnButton.setLayout(null);
        btnDashBoard = createButton("home_24dp_000000.png", "Trang chủ");
        btnDashBoard.setBounds(10, 110, 230, 50);
        btnDashBoard.addActionListener(e -> cardLayout.show(pnMain, "DashBoard"));

        btnSanpham = createButton("product-29-32.png", "Sản phẩm");
        btnSanpham.setBounds(10, 160, 230, 50);
        btnSanpham.addActionListener(e -> cardLayout.show(pnMain, "SanPham"));

        btnNhaCungCap = createButton("supplier-15-32.png", "Nhà cung cấp");
        btnNhaCungCap.setBounds(10, 210, 230, 50);
        btnNhaCungCap.addActionListener(e -> cardLayout.show(pnMain, "NhaCungCap"));

        btnNhanVien = createButton("staff-7-32.png", "Nhân viên");
        btnNhanVien.setBounds(10, 260, 230, 50);
        btnNhanVien.addActionListener(e -> cardLayout.show(pnMain, "NhanVien"));

        btnBill = createButton("bill-3-32.png", "Hóa đơn");
        btnBill.setBounds(10, 310, 230, 50);
        btnBill.addActionListener(e -> cardLayout.show(pnMain, "Bill"));

        btnKhachHang = createButton("my-client-32.png", "Khách hàng");
        btnKhachHang.setBounds(10, 360, 230, 50);
        btnKhachHang.addActionListener(e -> cardLayout.show(pnMain, "KhachHang"));

        btnThuocTinh = createButton("attribute-1-32.png", "Thuộc tính");
        btnThuocTinh.setBounds(10, 410, 230, 50);
        btnThuocTinh.addActionListener(e -> cardLayout.show(pnMain, "ThuocTinh"));

        btnPhieuNhap = createButton("bill-25-32.png", "Phiếu nhập");
        btnPhieuNhap.setBounds(10, 460, 230, 50);
        btnPhieuNhap.addActionListener(e -> cardLayout.show(pnMain, "PhieuNhap"));

        btnKhuyenMai = createButton("promotion-8-32.png", "Khuyến mãi");
        btnKhuyenMai.setBounds(10, 510, 230, 50);
        btnKhuyenMai.addActionListener(e -> cardLayout.show(pnMain, "KhuyenMai"));

        btnTaiKhoan = createButton("account-3-32.png", "Tài khoản");
        btnTaiKhoan.setBounds(10, 560, 230, 50);
        btnTaiKhoan.addActionListener(e -> cardLayout.show(pnMain, "TaiKhoan"));

        btnThongKe = createButton("statistical-table-32.png", "Thống kê");
        btnThongKe.setBounds(10, 610, 230, 50);
        btnThongKe.addActionListener(e -> cardLayout.show(pnMain, "ThongKe"));


        btnLogout = createButton("red-circle-logout-arrow-20586.png", "Đăng xuất");
        btnLogout.setBounds(10, 710, 230, 50);

        pnButton.add(btnDashBoard);
        pnButton.add(btnSanpham);
        pnButton.add(btnNhaCungCap);
        pnButton.add(btnNhanVien);
        pnButton.add(btnBill);
        pnButton.add(btnKhachHang);
        pnButton.add(btnThuocTinh);
        pnButton.add(btnPhieuNhap);
        pnButton.add(btnKhuyenMai);
        pnButton.add(btnTaiKhoan);
        pnButton.add(btnThongKe);
        pnButton.add(btnLogout);

        pnButton.setBackground(Color.WHITE);
        pnButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.decode("#CED8DC")));

        pnButton.setBounds(0, 0, 250, 800);

        this.add(pnInfor);
        this.add(pnButton);
        this.add(pnMain);

        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/shoes_icon.png").getImage());
        this.setTitle("Hệ thống quản lý bán giày");
        this.setLayout(null);
        this.setSize(1366, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void SlideShow() {
        pathImg = new ArrayList<>();
        pathImg.add("shoes-sale-social-media-post-square-banner-template-design_505751-2856.jpg");
        pathImg.add("shoes-sale-social-media-post-square-banner-template-design_505751-3735.jpg");
        pathImg.add("shoes-sale-social-media-post-square-banner-template-design_505751-4894.jpg");
        int[] currentIndex = {0};
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex[0] = (currentIndex[0] + 1) % pathImg.size();
                String img = pathImg.get(currentIndex[0]);
                ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/src/Assets/" + img);
                Image image = icon.getImage().getScaledInstance(lbhomebanner.getWidth(), lbhomebanner.getHeight(), Image.SCALE_SMOOTH);
                lbhomebanner.setIcon(new ImageIcon(image));
            }
        });
    }

    private CustomButton createButton(String imageIcon, String textIcon) {
        int target = 15;
        int lengthText = textIcon.length();
        for (int i = 0; i < target - lengthText; i++) {
            textIcon = " " + textIcon;
        }
        CustomButton button = new CustomButton(textIcon);
        button.setIcon(new ImageIcon(
                new ImageIcon(System.getProperty("user.dir") + "/src/Assets/" + imageIcon).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)
        ));
        button.setBorderColor(button.getBackground());
        button.setFont(new Font("Sans-serif", Font.BOLD, 15));
        button.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                button.setBackground(Color.decode("#BBDEFB"));
                button.setBorderColor(button.getBackground());

            }

            @Override
            public void focusLost(FocusEvent e) {
                button.setBackground(Color.WHITE);
                button.setBorderColor(button.getBackground());

            }
        });
        return button;
    }

    public static void main(String[] args) {

        new DashBoard(new TaiKhoanDTO("admin", "21232f297a57a5a743894a0e4a801fc3", 1));
    }
}
