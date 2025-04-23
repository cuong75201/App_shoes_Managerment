/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import BLL.ChiTietHoaDonBLL;
import BLL.HoaDonBLL;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import DAL.SanPhamDAL;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.customTextField;
import GUI.component.CustomComboBox;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 *
 * @author cuong
 */
public class ChiTietHoaDonView extends JDialog {
    
    // Components
    private JLabel lblTitle, lblMaHD, lblNgayBan, lblNhanVien, lblKhachHang, lblKhuyenMai, lblTongTien;
    private CustomTable tblChiTietHoaDon;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private CustomButton btnDong, btnInHoaDon, btnThemSanPham, btnXoaSanPham, btnLuuChiTiet;
    
    // Panel thêm sản phẩm
    private JPanel pnlThemSanPham;
    private JLabel lblMaSP, lblSoLuong, lblDonGia, lblGiamGia;
    private customTextField txtMaSP, txtSoLuong, txtDonGia, txtGiamGia;
    private CustomComboBox cbSanPham;
    private CustomButton btnThem, btnHuy;
    
    // Data
    private HoaDonDTO hoaDon;
    private ArrayList<ChiTietHDDTO> listChiTietHD;
    private ChiTietHoaDonBLL chiTietHoaDonBLL;
    private int selectedRow = -1;
    private boolean isAddingProduct = false;
    
    public ChiTietHoaDonView(HoaDonDTO hoaDon) {
        super();
        setTitle("Chi tiết hóa đơn");
        setModal(true);
        this.hoaDon = hoaDon;
        
        try {
            this.chiTietHoaDonBLL = new ChiTietHoaDonBLL();
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
        this.setSize(900, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        
        // Title
        lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 900, 30);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(lblTitle);
        
        // Thông tin hóa đơn
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(null);
        pnlInfo.setBounds(20, 60, 860, 120);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#E1E1E1")), 
            "Thông tin hóa đơn"));
        pnlInfo.setBackground(Color.WHITE);
        this.add(pnlInfo);
        
        int y = 25;
        
        lblMaHD = new JLabel("Mã hóa đơn: " + hoaDon.getStrMaHD());
        lblMaHD.setBounds(20, y, 350, 20);
        lblMaHD.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaHD);
        
        lblNgayBan = new JLabel("Ngày bán: " + hoaDon.getStrNgayBan());
        lblNgayBan.setBounds(400, y, 350, 20);
        lblNgayBan.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNgayBan);
        
        y += 25;
        
        lblNhanVien = new JLabel("Nhân viên: " + hoaDon.getStrMaNV());
        lblNhanVien.setBounds(20, y, 350, 20);
        lblNhanVien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNhanVien);
        
        lblKhachHang = new JLabel("Khách hàng: " + hoaDon.getStrMaKH());
        lblKhachHang.setBounds(400, y, 350, 20);
        lblKhachHang.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblKhachHang);
        
        y += 25;
        
        lblKhuyenMai = new JLabel("Khuyến mãi: " + (hoaDon.getStrMaKM().isEmpty() ? "Không có" : hoaDon.getStrMaKM()));
        lblKhuyenMai.setBounds(20, y, 350, 20);
        lblKhuyenMai.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblKhuyenMai);
        
        lblTongTien = new JLabel("Tổng tiền: " + String.format("%,.0f VND", hoaDon.getTongTien()));
        lblTongTien.setBounds(400, y, 350, 20);
        lblTongTien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblTongTien);
        
        // Tạo bảng chi tiết
        createTable();
        
        // Panel Thêm sản phẩm
        createAddProductPanel();
        
        // Buttons
        btnDong = new CustomButton("Đóng");
        btnDong.setBounds(780, 620, 100, 30);
        btnDong.setBackground(Color.decode("#E74C3C"));
        btnDong.setForeground(Color.WHITE);
        btnDong.setBorderColor(btnDong.getBackground());
        this.add(btnDong);
        
        btnInHoaDon = new CustomButton("In hóa đơn");
        btnInHoaDon.setBounds(660, 620, 100, 30);
        btnInHoaDon.setBackground(Color.decode("#3498DB"));
        btnInHoaDon.setForeground(Color.WHITE);
        btnInHoaDon.setBorderColor(btnInHoaDon.getBackground());
        this.add(btnInHoaDon);
        
        btnThemSanPham = new CustomButton("Thêm sản phẩm");
        btnThemSanPham.setBounds(20, 620, 120, 30);
        btnThemSanPham.setBackground(Color.decode("#2ECC71"));
        btnThemSanPham.setForeground(Color.WHITE);
        btnThemSanPham.setBorderColor(btnThemSanPham.getBackground());
        this.add(btnThemSanPham);
        
        btnXoaSanPham = new CustomButton("Xóa sản phẩm");
        btnXoaSanPham.setBounds(160, 620, 120, 30);
        btnXoaSanPham.setBackground(Color.decode("#E67E22"));
        btnXoaSanPham.setForeground(Color.WHITE);
        btnXoaSanPham.setBorderColor(btnXoaSanPham.getBackground());
        this.add(btnXoaSanPham);
        
        btnLuuChiTiet = new CustomButton("Lưu thay đổi");
        btnLuuChiTiet.setBounds(300, 620, 120, 30);
        btnLuuChiTiet.setBackground(Color.decode("#1ABC9C"));
        btnLuuChiTiet.setForeground(Color.WHITE);
        btnLuuChiTiet.setBorderColor(btnLuuChiTiet.getBackground());
        this.add(btnLuuChiTiet);
    }
    
    private void createTable() {
        // Tạo model của bảng
        tableModel = new DefaultTableModel();
        tableModel.addColumn("STT");
        tableModel.addColumn("Mã giày");
        tableModel.addColumn("Tên sản phẩm");
        tableModel.addColumn("Số lượng");
        
        
        // Tạo bảng
        tblChiTietHoaDon = new CustomTable(tableModel);
        
        // Thêm bảng vào thanh cuộn
        scrollPane = new JScrollPane(tblChiTietHoaDon);
        scrollPane.setBounds(20, 200, 860, 240);
        this.add(scrollPane);
    }
    
    private void createAddProductPanel() {
        pnlThemSanPham = new JPanel();
        pnlThemSanPham.setLayout(null);
        pnlThemSanPham.setBounds(20, 450, 860, 150);
        pnlThemSanPham.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#E1E1E1")), 
            "Thêm sản phẩm"));
        pnlThemSanPham.setBackground(Color.WHITE);
        this.add(pnlThemSanPham);
        pnlThemSanPham.setVisible(false); // Ẩn ban đầu
        
        int y = 30;
        
        // Sản phẩm
        lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(20, y, 100, 25);
        lblMaSP.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblMaSP);
        
        txtMaSP = new customTextField();
        txtMaSP.setBounds(120, y, 150, 25);
        txtMaSP.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtMaSP);
        
        // Số lượng
        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(300, y, 100, 25);
        lblSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblSoLuong);
        
        txtSoLuong = new customTextField();
        txtSoLuong.setBounds(400, y, 100, 25);
        txtSoLuong.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtSoLuong);
        
        // Đơn giá
        lblDonGia = new JLabel("Đơn giá:");
        lblDonGia.setBounds(520, y, 100, 25);
        lblDonGia.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblDonGia);

        txtDonGia = new customTextField();
        txtDonGia.setBounds(620, y, 100, 25);
        txtDonGia.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtDonGia);
        
        
        y += 40;
        
        // Thêm và Hủy
        btnThem = new CustomButton("Thêm");
        btnThem.setBounds(300, y, 100, 30);
        btnThem.setBackground(Color.decode("#2ECC71"));
        btnThem.setForeground(Color.WHITE);
        btnThem.setBorderColor(btnThem.getBackground());
        pnlThemSanPham.add(btnThem);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(420, y, 100, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        pnlThemSanPham.add(btnHuy);
    }
    
    private void loadData() {
        
    // Lấy dữ liệu chi tiết hóa đơn
    listChiTietHD = new ArrayList<>();
    
    // Lọc các chi tiết của hóa đơn hiện tại
    for (ChiTietHDDTO chiTiet : chiTietHoaDonBLL.getListChiTietHoaDon()) {
        if (chiTiet.getStrMaHD().equals(hoaDon.getStrMaHD())) {
            listChiTietHD.add(chiTiet);
        }
    }
    
    // Khởi tạo đối tượng SanPhamDAL để lấy thông tin sản phẩm
    SanPhamDAL sanPhamDAL = new SanPhamDAL();
    
    // Xóa dữ liệu hiện tại của bảng
    tableModel.setRowCount(0);
    
    // Thêm dữ liệu vào bảng
    int stt = 1;
    double tongTien = 0;
    
    for (ChiTietHDDTO chiTiet : listChiTietHD) {
        Vector<Object> row = new Vector<>();
        row.add(stt++);
        row.add(chiTiet.getStrMaGiay());
        
        // Lấy tên sản phẩm từ database
        String tenSanPham = "Sản phẩm " + chiTiet.getStrMaGiay(); // Giá trị mặc định
        SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
        if (sanPham != null) {
            tenSanPham = sanPham.getStrTenGiay();
        }
        row.add(tenSanPham);
        
        int soLuong = chiTiet.getiSoLuong();
        
        
        row.add(soLuong);
        
        
        tableModel.addRow(row);
        tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaBan();
    }
    
    // Cập nhật tổng tiền
    lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
}
    
    private void addEvents() {
        // Sự kiện khi chọn dòng trong bảng
        tblChiTietHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = tblChiTietHoaDon.getSelectedRow();
            }
        });
        
        // Nút Đóng
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Nút In hóa đơn
        btnInHoaDon.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            printInvoice(); // Thay thế dòng thông báo cũ bằng lệnh gọi phương thức in
        }
        });
        
        // Nút Thêm sản phẩm
        btnThemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAddProductPanel(true);
            }
        });
        
        // Nút Xóa sản phẩm
        btnXoaSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        
        // Nút Lưu thay đổi
        btnLuuChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        
        // Nút Thêm (trong panel thêm sản phẩm)
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        
        // Nút Hủy (trong panel thêm sản phẩm)
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAddProductPanel(false);
            }
        });
    }
    
    private void toggleAddProductPanel(boolean show) {
        pnlThemSanPham.setVisible(show);
        
        if (show) {
            // Clear các trường nhập liệu
            txtMaSP.setText("");
            txtSoLuong.setText("");
            txtDonGia.setText("");
            txtMaSP.requestFocus();
        }
    }
    
    private void addProduct() {
    // Kiểm tra dữ liệu đầu vào
    String maSP = txtMaSP.getText().trim();
    String soLuongStr = txtSoLuong.getText().trim();
    String donGiaStr = txtDonGia.getText().trim();
    
    if (maSP.isEmpty() || soLuongStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Vui lòng nhập đầy đủ thông tin!", 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Kiểm tra sản phẩm có tồn tại trong database không
    SanPhamDAL sanPhamDAL = new SanPhamDAL();
    SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(maSP);
    if (sanPham == null) {
        JOptionPane.showMessageDialog(this, 
            "Sản phẩm với mã " + maSP + " không tồn tại trong cơ sở dữ liệu!", 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Chuyển đổi số lượng và đơn giá
    int soLuong;
    try {
        soLuong = Integer.parseInt(soLuongStr);
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Số lượng phải lớn hơn 0!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Số lượng phải là số nguyên!", 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    int donGia = sanPham.getiGia();
    if (!donGiaStr.isEmpty()) {
        try {
            donGia = Integer.parseInt(donGiaStr);
            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Đơn giá phải lớn hơn 0!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Đơn giá phải là số nguyên!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    // Kiểm tra số lượng tồn kho
    int soLuongTonKho = sanPham.getiSoLuong();
    int soLuongDaCoTrongHD = 0;
    ChiTietHDDTO chiTietHienTai = null;
    
    // Kiểm tra xem sản phẩm đã tồn tại trong hóa đơn chưa và lấy số lượng hiện tại
    for (ChiTietHDDTO chiTiet : listChiTietHD) {
        if (chiTiet.getStrMaGiay().equals(maSP)) {
            soLuongDaCoTrongHD = chiTiet.getiSoLuong();
            chiTietHienTai = chiTiet;
            break;
        }
    }
    
    // Nếu đã có sản phẩm trong hóa đơn
    if (chiTietHienTai != null) {
        // Nếu số lượng mới lớn hơn số lượng hiện tại, kiểm tra tồn kho
        if (soLuong > soLuongDaCoTrongHD) {
            int soLuongThem = soLuong - soLuongDaCoTrongHD;
            if (soLuongThem > soLuongTonKho) {
                JOptionPane.showMessageDialog(this, 
                    "Số lượng tồn kho không đủ! Hiện có: " + soLuongTonKho + " sản phẩm.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Sản phẩm đã tồn tại trong hóa đơn! Bạn có muốn cập nhật số lượng không?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            // Cập nhật số lượng và đơn giá
            chiTietHienTai.setiSoLuong(soLuong);
            chiTietHienTai.setiGiaBan(donGia);
            
            // Cập nhật lại dữ liệu
            loadData();
            
            // Ẩn panel thêm sản phẩm
            toggleAddProductPanel(false);
        }
        return;
    } else {
        // Sản phẩm chưa có trong hóa đơn, kiểm tra số lượng tồn kho
        if (soLuong > soLuongTonKho) {
            JOptionPane.showMessageDialog(this, 
                "Số lượng tồn kho không đủ! Hiện có: " + soLuongTonKho + " sản phẩm.", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Tạo chi tiết hóa đơn mới
        ChiTietHDDTO chiTietMoi = new ChiTietHDDTO(maSP, hoaDon.getStrMaHD(), soLuong, donGia);
        
        // Thêm vào danh sách hiển thị và database
        boolean added = chiTietHoaDonBLL.addChiTietHoaDon(chiTietMoi);
        System.out.println("Đã thêm sản phẩm vào database: " + added);
        System.out.println("Mã SP: " + maSP + ", Mã HD: " + hoaDon.getStrMaHD() + 
            ", Số lượng: " + soLuong + ", Đơn giá: " + donGia);
        
    // Nếu thêm thành công
    if (added) {
        // Giảm số lượng tồn kho
        int soLuongMoi = sanPham.getiSoLuong() - soLuong;
        boolean updated = sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
        System.out.println("Đã cập nhật số lượng tồn kho: " + updated + 
            ", Số lượng mới: " + soLuongMoi);

        // Cập nhật lại dữ liệu hiển thị
        loadData();

        // Ẩn panel thêm sản phẩm
        toggleAddProductPanel(false);

        JOptionPane.showMessageDialog(this, 
            "Thêm sản phẩm thành công!", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, 
            "Thêm sản phẩm thất bại!", 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
    }
        
}
    }
    
    private void saveChanges() {
    try {
        // Lấy danh sách chi tiết hóa đơn hiện tại từ database
        ArrayList<ChiTietHDDTO> chiTietHDHienTai = new ArrayList<>();
        for (ChiTietHDDTO chiTiet : chiTietHoaDonBLL.getListChiTietHoaDon()) {
            if (chiTiet.getStrMaHD().equals(hoaDon.getStrMaHD())) {
                chiTietHDHienTai.add(chiTiet);
            }
        }
        
        // Kiểm tra số lượng tồn kho
        SanPhamDAL sanPhamDAL = new SanPhamDAL();
        Map<String, Integer> sanPhamCanThem = new HashMap<>();
        
        // Kiểm tra các chi tiết mới hoặc được cập nhật
        for (ChiTietHDDTO chiTietMoi : listChiTietHD) {
            boolean timThay = false;
            int soLuongCu = 0;
            
            // Kiểm tra xem sản phẩm đã có trong hóa đơn cũ chưa
            for (ChiTietHDDTO chiTietCu : chiTietHDHienTai) {
                if (chiTietMoi.getStrMaGiay().equals(chiTietCu.getStrMaGiay())) {
                    timThay = true;
                    soLuongCu = chiTietCu.getiSoLuong();
                    break;
                }
            }
            
            // Nếu là sản phẩm mới hoặc có tăng số lượng
            int soLuongThem = 0;
            if (!timThay) {
                // Sản phẩm mới
                soLuongThem = chiTietMoi.getiSoLuong();
            } else if (chiTietMoi.getiSoLuong() > soLuongCu) {
                // Sản phẩm cũ nhưng tăng số lượng
                soLuongThem = chiTietMoi.getiSoLuong() - soLuongCu;
            }
            
            if (soLuongThem > 0) {
                // Kiểm tra số lượng tồn kho
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTietMoi.getStrMaGiay());
                if (sanPham == null) {
                    throw new Exception("Không tìm thấy sản phẩm: " + chiTietMoi.getStrMaGiay());
                }
                
                if (soLuongThem > sanPham.getiSoLuong()) {
                    throw new Exception("Sản phẩm " + sanPham.getStrTenGiay() + " không đủ số lượng! Hiện có: " + sanPham.getiSoLuong());
                }
                
                // Lưu lại để cập nhật số lượng tồn kho sau
                sanPhamCanThem.put(chiTietMoi.getStrMaGiay(), soLuongThem);
            }
        }
        
        // Danh sách chi tiết cần xóa (có trong DB nhưng không còn trong GUI)
        ArrayList<ChiTietHDDTO> chiTietCanXoa = new ArrayList<>();
        for (ChiTietHDDTO chiTietHienTai : chiTietHDHienTai) {
            boolean timThay = false;
            for (ChiTietHDDTO chiTietMoi : listChiTietHD) {
                if (chiTietHienTai.getStrMaGiay().equals(chiTietMoi.getStrMaGiay())) {
                    timThay = true;
                    break;
                }
            }
            if (!timThay) {
                chiTietCanXoa.add(chiTietHienTai);
            }
        }
        
        // Danh sách chi tiết cần thêm mới (có trong GUI nhưng chưa có trong DB)
        ArrayList<ChiTietHDDTO> chiTietCanThem = new ArrayList<>();
        // Danh sách chi tiết cần cập nhật (có cả trong GUI và DB nhưng thông tin đã thay đổi)
        ArrayList<ChiTietHDDTO> chiTietCanCapNhat = new ArrayList<>();
        
        for (ChiTietHDDTO chiTietMoi : listChiTietHD) {
            boolean timThay = false;
            for (ChiTietHDDTO chiTietHienTai : chiTietHDHienTai) {
                if (chiTietMoi.getStrMaGiay().equals(chiTietHienTai.getStrMaGiay())) {
                    timThay = true;
                    // Kiểm tra xem thông tin có thay đổi không
                    if (chiTietMoi.getiSoLuong() != chiTietHienTai.getiSoLuong() || 
                        chiTietMoi.getiGiaBan() != chiTietHienTai.getiGiaBan()) {
                        chiTietCanCapNhat.add(chiTietMoi);
                    }
                    break;
                }
            }
            if (!timThay) {
                chiTietCanThem.add(chiTietMoi);
            }
        }
        
        // Thực hiện xóa các chi tiết không còn
        for (ChiTietHDDTO chiTiet : chiTietCanXoa) {
            if (!chiTietHoaDonBLL.deleteChiTietHoaDon(chiTiet)) {
                throw new Exception("Không thể xóa chi tiết hóa đơn: " + chiTiet.getStrMaGiay());
            }
            
            // Hoàn trả số lượng vào tồn kho
            SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
            if (sanPham != null) {
                int soLuongMoi = sanPham.getiSoLuong() + chiTiet.getiSoLuong();
                sanPham.setiSoLuong(soLuongMoi);
                sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
            }
        }
        
        // Thực hiện thêm các chi tiết mới
        for (ChiTietHDDTO chiTiet : chiTietCanThem) {
            if (!chiTietHoaDonBLL.addChiTietHoaDon(chiTiet)) {
                throw new Exception("Không thể thêm chi tiết hóa đơn: " + chiTiet.getStrMaGiay());
            }
            
            // Giảm số lượng trong tồn kho
            SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
            if (sanPham != null) {
                int soLuongMoi = sanPham.getiSoLuong() - chiTiet.getiSoLuong();
                sanPham.setiSoLuong(soLuongMoi);
                sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
            }
        }
        
        // Thực hiện cập nhật các chi tiết đã thay đổi
        for (ChiTietHDDTO chiTiet : chiTietCanCapNhat) {
            if (!chiTietHoaDonBLL.updateChiTietHoaDon(chiTiet)) {
                throw new Exception("Không thể cập nhật chi tiết hóa đơn: " + chiTiet.getStrMaGiay());
            }
            
            // Tìm số lượng cũ
            int soLuongCu = 0;
            for (ChiTietHDDTO chiTietCu : chiTietHDHienTai) {
                if (chiTietCu.getStrMaGiay().equals(chiTiet.getStrMaGiay())) {
                    soLuongCu = chiTietCu.getiSoLuong();
                    break;
                }
            }
            
            // Cập nhật số lượng trong tồn kho
            SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
            if (sanPham != null) {
                int chenhLech = soLuongCu - chiTiet.getiSoLuong();
                int soLuongMoi = sanPham.getiSoLuong() + chenhLech;
                sanPham.setiSoLuong(soLuongMoi);
                sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
            }
        }
        
        // Tính tổng tiền mới
        double tongTien = 0;
        for (ChiTietHDDTO chiTiet : listChiTietHD) {
            tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaBan();
        }
        
        // Cập nhật tổng tiền của hóa đơn trong database
        HoaDonBLL hoaDonBLL = new HoaDonBLL();
        if (!hoaDonBLL.updateTongTien(hoaDon.getStrMaHD(), tongTien)) {
            throw new Exception("Không thể cập nhật tổng tiền hóa đơn");
        }
        
        // Cập nhật lại giao diện
        hoaDon.setTongTien(tongTien);
        lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
        
        JOptionPane.showMessageDialog(this, 
            "Lưu thay đổi thành công!", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Lỗi khi lưu thay đổi: " + e.getMessage(), 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void deleteProduct() {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, 
            "Vui lòng chọn sản phẩm cần xóa!", 
            "Thông báo", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc chắn muốn xóa sản phẩm này khỏi hóa đơn?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION);
        
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            // Lấy mã sản phẩm từ bảng
            String maSP = tblChiTietHoaDon.getValueAt(selectedRow, 1).toString();
            
            // Tìm chi tiết hóa đơn cần xóa
            ChiTietHDDTO chiTietCanXoa = null;
            for (ChiTietHDDTO chiTiet : listChiTietHD) {
                if (chiTiet.getStrMaGiay().equals(maSP)) {
                    chiTietCanXoa = chiTiet;
                    break;
                }
            }
            
            if (chiTietCanXoa == null) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy thông tin sản phẩm trong danh sách!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Xóa chi tiết hóa đơn từ cơ sở dữ liệu trước
            boolean success = chiTietHoaDonBLL.deleteChiTietHoaDon(chiTietCanXoa);
            
            if (success) {
                // Hoàn trả số lượng vào tồn kho
                SanPhamDAL sanPhamDAL = new SanPhamDAL();
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTietCanXoa.getStrMaGiay());
                
                if (sanPham != null) {
                    int soLuongMoi = sanPham.getiSoLuong() + chiTietCanXoa.getiSoLuong();
                    boolean updateStock = sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                    
                    if (!updateStock) {
                        JOptionPane.showMessageDialog(this, 
                            "Cập nhật số lượng tồn kho thất bại!", 
                            "Cảnh báo", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
                
                // Xóa khỏi danh sách hiển thị
                listChiTietHD.remove(chiTietCanXoa);
                
                // Cập nhật lại tổng tiền
                double tongTien = 0;
                for (ChiTietHDDTO chiTiet : listChiTietHD) {
                    tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaBan();
                }
                
                // Cập nhật tổng tiền vào cơ sở dữ liệu
                HoaDonBLL hoaDonBLL = new HoaDonBLL();
                boolean updateTotal = hoaDonBLL.updateTongTien(hoaDon.getStrMaHD(), tongTien);
                
                if (!updateTotal) {
                    JOptionPane.showMessageDialog(this, 
                        "Cập nhật tổng tiền hóa đơn thất bại!", 
                        "Cảnh báo", 
                        JOptionPane.WARNING_MESSAGE);
                }
                
                // Cập nhật tổng tiền trên giao diện
                hoaDon.setTongTien(tongTien);
                lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
                
                // Cập nhật lại dữ liệu hiển thị
                loadData();
                
                // Reset selected row
                selectedRow = -1;
                
                JOptionPane.showMessageDialog(this, 
                    "Xóa sản phẩm thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa sản phẩm thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xóa sản phẩm: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
    private void printInvoice() {
    // Hiển thị hộp thoại xác nhận trước khi in
    int confirm = JOptionPane.showConfirmDialog(this,
        "Bạn có muốn xem trước hóa đơn trước khi in không?",
        "Xác nhận",
        JOptionPane.YES_NO_CANCEL_OPTION);
        
    if (confirm == JOptionPane.CANCEL_OPTION) {
        return; // Người dùng hủy thao tác in
    }
    
    if (confirm == JOptionPane.YES_OPTION) {
        // Tạo cửa sổ xem trước khi in
        JDialog previewDialog = new JDialog(this, "Xem trước hóa đơn", true);
        previewDialog.setSize(600, 800);
        previewDialog.setLocationRelativeTo(this);
        
        // Tạo panel để hiển thị bản xem trước
        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Vẽ hóa đơn lên panel
                try {
                    new InvoicePrintable().print(g2d, new PageFormat(), 0);
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
            }
        };
        
        previewPanel.setPreferredSize(new Dimension(550, 750));
        
        JScrollPane scrollPane = new JScrollPane(previewPanel);
        previewDialog.add(scrollPane, BorderLayout.CENTER);
        
        // Panel chứa các nút điều khiển
        JPanel buttonPanel = new JPanel();
        CustomButton btnPrint = new CustomButton("In hóa đơn");
        btnPrint.setBackground(Color.decode("#3498DB"));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setBorderColor(btnPrint.getBackground());
        
        CustomButton btnCancel = new CustomButton("Hủy");
        btnCancel.setBackground(Color.decode("#E74C3C"));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorderColor(btnCancel.getBackground());
        
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnCancel);
        previewDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Thêm sự kiện cho nút In
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewDialog.dispose();
                printActualInvoice();
            }
        });
        
        // Thêm sự kiện cho nút Hủy
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewDialog.dispose();
            }
        });
        
        previewDialog.setVisible(true);
    } else {
        // In ngay không xem trước
        printActualInvoice();
    }
}

private void printActualInvoice() {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(new InvoicePrintable());
    
    if (job.printDialog()) {
        try {
            job.print();
            JOptionPane.showMessageDialog(this, 
                "In hóa đơn thành công!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
                
            // Lưu lịch sử in hóa đơn
            saveInvoicePrintHistory();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi in: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void saveInvoicePrintHistory() {
    // Phương thức này có thể được mở rộng để lưu lịch sử in hóa đơn vào cơ sở dữ liệu
    // Ví dụ: thời gian in, người in, trạng thái in, v.v.
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String printTime = dateFormat.format(new Date());
    
    System.out.println("Đã in hóa đơn " + hoaDon.getStrMaHD() + " vào lúc " + printTime);
    
    // Ghi chú: Trong phiên bản hoàn chỉnh, bạn có thể muốn thêm một bảng trong cơ sở dữ liệu
    // để lưu trữ lịch sử in hóa đơn và thêm mã ở đây để lưu vào cơ sở dữ liệu
}

// Lớp định dạng in hóa đơn được cải thiện
private class InvoicePrintable implements Printable {
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        
        // Thiết lập chất lượng vẽ cao hơn
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Thiết lập font
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);
        Font headerFont = new Font("SansSerif", Font.BOLD, 12);
        Font normalFont = new Font("SansSerif", Font.PLAIN, 12);
        
        int y = 20; // Vị trí bắt đầu
        int lineHeight = 15; // Độ cao mỗi dòng
        int leftMargin = 50; // Lề trái
        int width = (int) pageFormat.getImageableWidth();
        
        // Vẽ viền trang
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(5, 5, width - 10, (int)pageFormat.getImageableHeight() - 10);
        g2d.setColor(Color.BLACK);
        
        // Vẽ tiêu đề
        g2d.setFont(titleFont);
        String title = "HÓA ĐƠN BÁN HÀNG";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, y);
        y += 30;
        
        // Vẽ thông tin cửa hàng
        g2d.setFont(headerFont);
        String shopName = "SHOES SHOP";
        g2d.drawString(shopName, leftMargin, y);
        
        // Vẽ đường gạch chân dưới tên cửa hàng
        int shopNameWidth = g2d.getFontMetrics().stringWidth(shopName);
        g2d.drawLine(leftMargin, y + 2, leftMargin + shopNameWidth, y + 2);
        
        y += lineHeight + 5;
        
        g2d.setFont(normalFont);
        g2d.drawString("Địa chỉ: 123 Đường Nguyễn Huệ, Quận 1, TP.HCM", leftMargin, y);
        y += lineHeight;
        g2d.drawString("Điện thoại: 0123456789", leftMargin, y);
        y += lineHeight;
        g2d.drawString("Email: contact@shoesshop.com", leftMargin, y);
        y += lineHeight * 2;
        
        // Vẽ đường ngăn cách
        g2d.drawLine(leftMargin, y - 5, width - leftMargin, y - 5);
        
        // Vẽ thông tin hóa đơn
        g2d.setFont(headerFont);
        g2d.drawString("THÔNG TIN HÓA ĐƠN", leftMargin, y + 15);
        y += lineHeight + 20;
        
        // Tạo bảng thông tin với 2 cột
        g2d.setFont(normalFont);
        int col2X = width / 2;
        
        g2d.drawString("Mã hóa đơn:", leftMargin, y);
        g2d.drawString(hoaDon.getStrMaHD(), leftMargin + 100, y);
        
        g2d.drawString("Ngày bán:", col2X, y);
        g2d.drawString(hoaDon.getStrNgayBan(), col2X + 100, y);
        y += lineHeight + 5;
        
        g2d.drawString("Nhân viên:", leftMargin, y);
        g2d.drawString(hoaDon.getStrMaNV(), leftMargin + 100, y);
        
        g2d.drawString("Khách hàng:", col2X, y);
        g2d.drawString(hoaDon.getStrMaKH(), col2X + 100, y);
        y += lineHeight + 5;
        
        g2d.drawString("Khuyến mãi:", leftMargin, y);
        g2d.drawString((hoaDon.getStrMaKM().isEmpty() ? "Không có" : hoaDon.getStrMaKM()), leftMargin + 100, y);
        
        y += lineHeight * 2;
        
        // Vẽ đường ngăn cách
        g2d.drawLine(leftMargin, y - 5, width - leftMargin, y - 5);
        
        // Vẽ tiêu đề bảng chi tiết
        g2d.setFont(headerFont);
        g2d.drawString("CHI TIẾT HÓA ĐƠN", leftMargin, y + 15);
        y += lineHeight + 20;
        
        // Vẽ header của bảng với đường viền
        int tableWidth = width - 2 * leftMargin;
        int[] colWidths = {tableWidth / 10, 3 * tableWidth / 10, 4 * tableWidth / 10, 2 * tableWidth / 10};
        String[] headers = {"STT", "Mã giày", "Tên sản phẩm", "Số lượng"};
        
        // Vẽ nền cho header
        g2d.setColor(new Color(230, 230, 230));
        g2d.fillRect(leftMargin, y - 15, tableWidth, 20);
        g2d.setColor(Color.BLACK);
        
        // Vẽ viền bảng
        g2d.drawRect(leftMargin, y - 15, tableWidth, 20);
        
        // Vẽ các cột
        int startX = leftMargin;
        for (int i = 0; i < colWidths.length - 1; i++) {
            startX += colWidths[i];
            g2d.drawLine(startX, y - 15, startX, y + 5);
        }
        
        // Vẽ header text
        g2d.setFont(headerFont);
        startX = leftMargin;
        for (int i = 0; i < headers.length; i++) {
            int textWidth = g2d.getFontMetrics().stringWidth(headers[i]);
            g2d.drawString(headers[i], startX + (colWidths[i] - textWidth) / 2, y);
            startX += colWidths[i];
        }
        y += 10;
        
        // Vẽ nội dung của bảng
        g2d.setFont(normalFont);
        SanPhamDAL sanPhamDAL = new SanPhamDAL();
        
        int rowHeight = lineHeight + 5;
        int tableStartY = y;
        
        for (int i = 0; i < listChiTietHD.size(); i++) {
            ChiTietHDDTO chiTiet = listChiTietHD.get(i);
            int rowY = y + i * rowHeight;
            
            // Vẽ đường viền hàng
            g2d.drawRect(leftMargin, rowY, tableWidth, rowHeight);
            
            // Vẽ các đường phân cách cột
            startX = leftMargin;
            for (int j = 0; j < colWidths.length - 1; j++) {
                startX += colWidths[j];
                g2d.drawLine(startX, rowY, startX, rowY + rowHeight);
            }
            
            // Vẽ nội dung hàng
            startX = leftMargin;
            
            // STT
            String stt = String.valueOf(i + 1);
            int textWidth = g2d.getFontMetrics().stringWidth(stt);
            g2d.drawString(stt, startX + (colWidths[0] - textWidth) / 2, rowY + rowHeight - 5);
            startX += colWidths[0];
            
            // Mã giày
            g2d.drawString(chiTiet.getStrMaGiay(), startX + 5, rowY + rowHeight - 5);
            startX += colWidths[1];
            
            // Tên sản phẩm
            String tenSanPham = "Sản phẩm " + chiTiet.getStrMaGiay();
            SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
            if (sanPham != null) {
                tenSanPham = sanPham.getStrTenGiay();
            }
            g2d.drawString(tenSanPham, startX + 5, rowY + rowHeight - 5);
            startX += colWidths[2];
            
            // Số lượng
            String soLuong = String.valueOf(chiTiet.getiSoLuong());
            textWidth = g2d.getFontMetrics().stringWidth(soLuong);
            g2d.drawString(soLuong, startX + (colWidths[3] - textWidth) / 2, rowY + rowHeight - 5);
        }
        
        // Cập nhật y sau khi vẽ bảng
        y += listChiTietHD.size() * rowHeight + lineHeight;
        
        // Vẽ tổng tiền
        g2d.setFont(headerFont);
        String tongTienStr = "Tổng tiền: " + String.format("%,.0f VND", hoaDon.getTongTien());
        int tongTienWidth = g2d.getFontMetrics().stringWidth(tongTienStr);
        g2d.drawString(tongTienStr, width - leftMargin - tongTienWidth, y);
        y += lineHeight * 2;
        
        // Vẽ đường ngăn cách
        g2d.drawLine(leftMargin, y - 5, width - leftMargin, y - 5);
        
        // Vẽ thông tin chân trang
        g2d.setFont(normalFont);
        
        // Thời gian in
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String printDate = "Thời gian in: " + dateFormat.format(new Date());
        g2d.drawString(printDate, leftMargin, y + 10);
        y += lineHeight * 3;
        
        // Chữ ký
        int signatureX1 = leftMargin + width / 4 - 30;
        int signatureX2 = leftMargin + 3 * width / 4 - 30;
        
        g2d.drawString("Người bán hàng", signatureX1, y);
        g2d.drawString("Khách hàng", signatureX2, y);
        y += lineHeight;
        g2d.drawString("(Ký, ghi rõ họ tên)", signatureX1 - 10, y);
        g2d.drawString("(Ký, ghi rõ họ tên)", signatureX2 - 10, y);
        
        return PAGE_EXISTS;
    }
}

}
