/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import BLL.ChiTietKhuyenMaiBLL;
import DTO.ChiTietKMDTO;
import DTO.KhuyenMaiDTO;
import GUI.component.CustomButton;
import GUI.component.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author cuong
 */
public class ChiTietKhuyenMaiView extends JDialog {
    
    // Components
    private JLabel lblTitle, lblMaKM, lblTenChuongTrinh, lblLoaiChuongTrinh, lblDieuKien;
    private JLabel lblNgayBatDau, lblNgayKetThuc, lblTrangThai;
    private CustomTable tblChiTietKhuyenMai;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private CustomButton btnDong, btnInDanhSach;
    
    // Data
    private KhuyenMaiDTO khuyenMai;
    private ArrayList<ChiTietKMDTO> listChiTietKM;
    private ChiTietKhuyenMaiBLL chiTietKhuyenMaiBLL;
    
    public ChiTietKhuyenMaiView(HoaDonGUI parent, KhuyenMaiDTO khuyenMai) {
        super();
        setTitle("Chi tiết khuyến mãi");
        setModal(true);
        this.khuyenMai = khuyenMai;
        
        try {
            this.chiTietKhuyenMaiBLL = new ChiTietKhuyenMaiBLL();
            init();
            loadData();
            addEvents();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void init() {
        this.setSize(800, 600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        
        // Title
        lblTitle = new JLabel("CHI TIẾT KHUYẾN MÃI", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 800, 30);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(lblTitle);
        
        // Thông tin khuyến mãi
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(null);
        pnlInfo.setBounds(20, 60, 760, 160);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#E1E1E1")), 
            "Thông tin khuyến mãi"));
        pnlInfo.setBackground(Color.WHITE);
        this.add(pnlInfo);
        
        int y = 25;
        
        lblMaKM = new JLabel("Mã khuyến mãi: " + khuyenMai.getStrMaKM());
        lblMaKM.setBounds(20, y, 350, 20);
        lblMaKM.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaKM);
        
        lblTenChuongTrinh = new JLabel("Tên chương trình: " + khuyenMai.getStrTenChuongTrinh());
        lblTenChuongTrinh.setBounds(400, y, 350, 20);
        lblTenChuongTrinh.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblTenChuongTrinh);
        
        y += 25;
        
        lblLoaiChuongTrinh = new JLabel("Loại chương trình: " + khuyenMai.getStrLoaiChuongTrinh());
        lblLoaiChuongTrinh.setBounds(20, y, 350, 20);
        lblLoaiChuongTrinh.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblLoaiChuongTrinh);
        
        lblDieuKien = new JLabel("Điều kiện: " + khuyenMai.getStrDieuKien());
        lblDieuKien.setBounds(400, y, 350, 20);
        lblDieuKien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblDieuKien);
        
        y += 25;
        
        lblNgayBatDau = new JLabel("Ngày bắt đầu: " + khuyenMai.getStrNgayBatDau());
        lblNgayBatDau.setBounds(20, y, 350, 20);
        lblNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNgayBatDau);
        
        lblNgayKetThuc = new JLabel("Ngày kết thúc: " + khuyenMai.getStrNgayKetThuc());
        lblNgayKetThuc.setBounds(400, y, 350, 20);
        lblNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNgayKetThuc);
        
        y += 25;
        
        // Kiểm tra trạng thái khuyến mãi
        String status = tinhTrangThaiKhuyenMai(khuyenMai);
        
        lblTrangThai = new JLabel("Trạng thái: " + status);
        lblTrangThai.setBounds(20, y, 350, 20);
        lblTrangThai.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblTrangThai);
        
        // Tạo bảng chi tiết
        createTable();
        
        // Buttons
        btnDong = new CustomButton("Đóng");
        btnDong.setBounds(570, 520, 100, 30);
        btnDong.setBackground(Color.decode("#E74C3C"));
        btnDong.setForeground(Color.WHITE);
        btnDong.setBorderColor(btnDong.getBackground());
        this.add(btnDong);
        
        btnInDanhSach = new CustomButton("In danh sách");
        btnInDanhSach.setBounds(450, 520, 100, 30);
        btnInDanhSach.setBackground(Color.decode("#3498DB"));
        btnInDanhSach.setForeground(Color.WHITE);
        btnInDanhSach.setBorderColor(btnInDanhSach.getBackground());
        this.add(btnInDanhSach);
    }
    
    private String tinhTrangThaiKhuyenMai(KhuyenMaiDTO km) {
        try {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMM yyyy");
            java.util.Date currentDate = new java.util.Date();
            java.util.Date startDate = dateFormat.parse(km.getStrNgayBatDau());
            java.util.Date endDate = dateFormat.parse(km.getStrNgayKetThuc());
            
            if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                return "Đang hoạt động";
            } else if (currentDate.before(startDate)) {
                return "Chưa diễn ra";
            } else {
                return "Đã kết thúc";
            }
        } catch (Exception e) {
            return "Không xác định";
        }
    }
    
    private void createTable() {
        // Tạo model của bảng
        tableModel = new DefaultTableModel();
        tableModel.addColumn("STT");
        tableModel.addColumn("Mã giày");
        tableModel.addColumn("Tên sản phẩm");
        tableModel.addColumn("Tỉ lệ khuyến mãi");
        tableModel.addColumn("Giảm giá");
        
        // Tạo bảng
        tblChiTietKhuyenMai = new CustomTable(tableModel);
        
        // Thêm bảng vào thanh cuộn
        scrollPane = new JScrollPane(tblChiTietKhuyenMai);
        scrollPane.setBounds(20, 240, 760, 260);
        this.add(scrollPane);
    }
    
    private void loadData() {
        // Lấy dữ liệu chi tiết khuyến mãi
        listChiTietKM = new ArrayList<>();
        
        // Lọc các chi tiết của khuyến mãi hiện tại
        for (ChiTietKMDTO chiTiet : chiTietKhuyenMaiBLL.getListChiTietKhuyenMai()) {
            if (chiTiet.getStrMaKM().equals(khuyenMai.getStrMaKM())) {
                listChiTietKM.add(chiTiet);
            }
        }
        
        // Xóa dữ liệu hiện tại của bảng
        tableModel.setRowCount(0);
        
        // Thêm dữ liệu vào bảng
        int stt = 1;
        
        for (ChiTietKMDTO chiTiet : listChiTietKM) {
            Vector<Object> row = new Vector<>();
            row.add(stt++);
            row.add(chiTiet.getStrMaGiay());
            
            // Thêm tên sản phẩm (thực tế cần lấy từ database)
            row.add("Sản phẩm " + chiTiet.getStrMaGiay()); // Placeholder, cần thay thế với tên thực tế
            
            double tiLe = chiTiet.getTiLeKM();
            
            row.add(String.format("%.2f", tiLe));
            row.add(String.format("%.0f%%", tiLe * 100));
            
            tableModel.addRow(row);
        }
    }
    
    private void addEvents() {
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnInDanhSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ChiTietKhuyenMaiView.this, 
                    "Chức năng in danh sách đang được phát triển.", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}