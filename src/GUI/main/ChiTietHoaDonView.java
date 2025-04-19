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
                JOptionPane.showMessageDialog(ChiTietHoaDonView.this, 
                    "Chức năng in hóa đơn đang được phát triển.", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
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
        // Lấy mã sản phẩm từ bảng
        String maSP = tblChiTietHoaDon.getValueAt(selectedRow, 1).toString();
        
        // Tìm và xóa khỏi danh sách
        for (int i = 0; i < listChiTietHD.size(); i++) {
            if (listChiTietHD.get(i).getStrMaGiay().equals(maSP)) {
                listChiTietHD.remove(i);
                break;
            }
        }
        
        // Cập nhật lại dữ liệu hiển thị
        loadData();
        
        // Reset selected row
        selectedRow = -1;
        
        JOptionPane.showMessageDialog(this, 
            "Xóa sản phẩm thành công! Nhấn 'Lưu thay đổi' để cập nhật vào database.", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
}