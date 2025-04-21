package GUI.main;

import BLL.ChiTietPhieuNhapBLL;
import BLL.PhieuNhapBLL;
import DTO.ChiTietPNDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import DAL.SanPhamDAL;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.customTextField;
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

public class ChiTietPhieuNhapView extends JDialog {
    
    // Components
    private JLabel lblTitle, lblMaPN, lblNgayNhap, lblNhaCungCap, lblNhanVien, lblTongTien;
    private CustomTable tblChiTietPhieuNhap;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private CustomButton btnDong, btnInPhieuNhap, btnThemSanPham, btnXoaSanPham, btnLuuChiTiet;
    
    // Panel thêm sản phẩm
    private JPanel pnlThemSanPham;
    private JLabel lblMaSP, lblSoLuong, lblGiaNhap;
    private customTextField txtMaSP, txtSoLuong, txtGiaNhap;
    private CustomButton btnThem, btnHuy;
    
    // Data
    private PhieuNhapDTO phieuNhap;
    private ArrayList<ChiTietPNDTO> listChiTietPN;
    private ChiTietPhieuNhapBLL chiTietPhieuNhapBLL;
    private int selectedRow = -1;
    
    public ChiTietPhieuNhapView(PhieuNhapDTO phieuNhap) {
        super();
        setTitle("Chi tiết phiếu nhập");
        setModal(true);
        this.phieuNhap = phieuNhap;
        
        try {
            this.chiTietPhieuNhapBLL = new ChiTietPhieuNhapBLL();
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
        lblTitle = new JLabel("CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 900, 30);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(lblTitle);
        
        // Thông tin phiếu nhập
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(null);
        pnlInfo.setBounds(20, 60, 860, 120);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#E1E1E1")), 
            "Thông tin phiếu nhập"));
        pnlInfo.setBackground(Color.WHITE);
        this.add(pnlInfo);
        
        int y = 25;
        
        lblMaPN = new JLabel("Mã phiếu nhập: " + phieuNhap.getStrMaPN());
        lblMaPN.setBounds(20, y, 350, 20);
        lblMaPN.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaPN);
        
        lblNgayNhap = new JLabel("Ngày nhập: " + phieuNhap.getStrNgayNhap());
        lblNgayNhap.setBounds(400, y, 350, 20);
        lblNgayNhap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNgayNhap);
        
        y += 25;
        
        lblNhaCungCap = new JLabel("Nhà cung cấp: " + phieuNhap.getStrMaNCC());
        lblNhaCungCap.setBounds(20, y, 350, 20);
        lblNhaCungCap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNhaCungCap);
        
        lblNhanVien = new JLabel("Nhân viên: " + phieuNhap.getStrMaNV());
        lblNhanVien.setBounds(400, y, 350, 20);
        lblNhanVien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNhanVien);
        
        y += 25;
        
        lblTongTien = new JLabel("Tổng tiền: " + String.format("%,.0f VND", phieuNhap.getTongTien()));
        lblTongTien.setBounds(20, y, 350, 20);
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
        
        btnInPhieuNhap = new CustomButton("In phiếu nhập");
        btnInPhieuNhap.setBounds(660, 620, 100, 30);
        btnInPhieuNhap.setBackground(Color.decode("#3498DB"));
        btnInPhieuNhap.setForeground(Color.WHITE);
        btnInPhieuNhap.setBorderColor(btnInPhieuNhap.getBackground());
        this.add(btnInPhieuNhap);
        
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
        tableModel = new DefaultTableModel();
        tableModel.addColumn("STT");
        tableModel.addColumn("Mã giày");
        tableModel.addColumn("Tên sản phẩm");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Giá nhập");
        
        tblChiTietPhieuNhap = new CustomTable(tableModel);
        scrollPane = new JScrollPane(tblChiTietPhieuNhap);
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
        pnlThemSanPham.setVisible(false);
        
        int y = 30;
        
        lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(20, y, 100, 25);
        lblMaSP.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblMaSP);
        
        txtMaSP = new customTextField();
        txtMaSP.setBounds(120, y, 150, 25);
        txtMaSP.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtMaSP);
        
        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(300, y, 100, 25);
        lblSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblSoLuong);
        
        txtSoLuong = new customTextField();
        txtSoLuong.setBounds(400, y, 100, 25);
        txtSoLuong.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtSoLuong);
        
        lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setBounds(520, y, 100, 25);
        lblGiaNhap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemSanPham.add(lblGiaNhap);
        
        txtGiaNhap = new customTextField();
        txtGiaNhap.setBounds(620, y, 100, 25);
        txtGiaNhap.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemSanPham.add(txtGiaNhap);
        
        y += 40;
        
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
        // Tải dữ liệu trực tiếp từ database
        listChiTietPN = new ArrayList<>();
        ArrayList<ChiTietPNDTO> allChiTiet = chiTietPhieuNhapBLL.getListChiTietPhieuNhap();
        for (ChiTietPNDTO chiTiet : allChiTiet) {
            if (chiTiet.getStrMaPN().equals(phieuNhap.getStrMaPN())) {
                listChiTietPN.add(chiTiet);
            }
        }

        SanPhamDAL sanPhamDAL = new SanPhamDAL();
        tableModel.setRowCount(0);
        
        int stt = 1;
        double tongTien = 0;
        for (ChiTietPNDTO chiTiet : listChiTietPN) {
            Vector<Object> row = new Vector<>();
            row.add(stt++);
            row.add(chiTiet.getStrMaGiay());
            
            String tenSanPham = "Sản phẩm " + chiTiet.getStrMaGiay();
            SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
            if (sanPham != null) {
                tenSanPham = sanPham.getStrTenGiay();
            }
            row.add(tenSanPham);
            row.add(chiTiet.getiSoLuong());
            row.add(String.format("%,d", chiTiet.getiGiaNhap()));
            
            tableModel.addRow(row);
            tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaNhap();
        }
        
        lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
        phieuNhap.setTongTien(tongTien);
    }
    
    private void addEvents() {
        tblChiTietPhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = tblChiTietPhieuNhap.getSelectedRow();
            }
        });
        
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnInPhieuNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ChiTietPhieuNhapView.this, 
                    "Chức năng in phiếu nhập đang được phát triển.", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnThemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAddProductPanel(true);
            }
        });
        
        btnXoaSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        
        btnLuuChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        
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
            txtMaSP.setText("");
            txtSoLuong.setText("");
            txtGiaNhap.setText("");
            txtMaSP.requestFocus();
        }
    }
    
    private void addProduct() {
        String maSP = txtMaSP.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String giaNhapStr = txtGiaNhap.getText().trim();
        
        if (maSP.isEmpty() || soLuongStr.isEmpty() || giaNhapStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ thông tin!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SanPhamDAL sanPhamDAL = new SanPhamDAL();
        SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(maSP);
        if (sanPham == null) {
            JOptionPane.showMessageDialog(this, 
                "Sản phẩm với mã " + maSP + " không tồn tại trong cơ sở dữ liệu!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int soLuong, giaNhap;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            giaNhap = Integer.parseInt(giaNhapStr);
            if (soLuong <= 0 || giaNhap <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Số lượng và giá nhập phải lớn hơn 0!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Số lượng và giá nhập phải là số nguyên!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int soLuongDaCoTrongPN = 0;
        ChiTietPNDTO chiTietHienTai = null;
        for (ChiTietPNDTO chiTiet : listChiTietPN) {
            if (chiTiet.getStrMaGiay().equals(maSP)) {
                soLuongDaCoTrongPN = chiTiet.getiSoLuong();
                chiTietHienTai = chiTiet;
                break;
            }
        }
        
        if (chiTietHienTai != null) {
            int option = JOptionPane.showConfirmDialog(this, 
                "Sản phẩm đã tồn tại trong phiếu nhập! Bạn có muốn cập nhật số lượng và giá nhập không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Cập nhật trực tiếp vào database
                chiTietHienTai.setiSoLuong(soLuong);
                chiTietHienTai.setiGiaNhap(giaNhap);
                boolean updated = chiTietPhieuNhapBLL.updateChiTietPhieuNhap(chiTietHienTai);
                if (updated) {
                    // Cập nhật số lượng tồn kho
                    int chenhLech = soLuong - soLuongDaCoTrongPN;
                    int soLuongMoi = sanPham.getiSoLuong() + chenhLech;
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                    
                    // Tải lại dữ liệu từ database
                    loadData();
                    toggleAddProductPanel(false);
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật sản phẩm thành công!",
                            "Thông báo", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật sản phẩm thất bại!",
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            return;
        }
        
        ChiTietPNDTO chiTietMoi = new ChiTietPNDTO(phieuNhap.getStrMaPN(), maSP, soLuong, giaNhap);
        boolean added = chiTietPhieuNhapBLL.addChiTietPhieuNhap(chiTietMoi);
        if (added) {
            int soLuongMoi = sanPham.getiSoLuong() + soLuong;
            sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
            loadData();
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
    
    private void saveChanges() {
        try {
            ArrayList<ChiTietPNDTO> chiTietPNHienTai = new ArrayList<>();
            for (ChiTietPNDTO chiTiet : chiTietPhieuNhapBLL.getListChiTietPhieuNhap()) {
                if (chiTiet.getStrMaPN().equals(phieuNhap.getStrMaPN())) {
                    chiTietPNHienTai.add(chiTiet);
                }
            }
            
            SanPhamDAL sanPhamDAL = new SanPhamDAL();
            
            ArrayList<ChiTietPNDTO> chiTietCanXoa = new ArrayList<>();
            for (ChiTietPNDTO chiTietHienTai : chiTietPNHienTai) {
                boolean timThay = false;
                for (ChiTietPNDTO chiTietMoi : listChiTietPN) {
                    if (chiTietHienTai.getStrMaGiay().equals(chiTietMoi.getStrMaGiay())) {
                        timThay = true;
                        break;
                    }
                }
                if (!timThay) {
                    chiTietCanXoa.add(chiTietHienTai);
                }
            }
            
            ArrayList<ChiTietPNDTO> chiTietCanThem = new ArrayList<>();
            ArrayList<ChiTietPNDTO> chiTietCanCapNhat = new ArrayList<>();
            
            for (ChiTietPNDTO chiTietMoi : listChiTietPN) {
                boolean timThay = false;
                for (ChiTietPNDTO chiTietHienTai : chiTietPNHienTai) {
                    if (chiTietMoi.getStrMaGiay().equals(chiTietHienTai.getStrMaGiay())) {
                        timThay = true;
                        if (chiTietMoi.getiSoLuong() != chiTietHienTai.getiSoLuong() || 
                            chiTietMoi.getiGiaNhap() != chiTietHienTai.getiGiaNhap()) {
                            chiTietCanCapNhat.add(chiTietMoi);
                        }
                        break;
                    }
                }
                if (!timThay) {
                    chiTietCanThem.add(chiTietMoi);
                }
            }
            
            for (ChiTietPNDTO chiTiet : chiTietCanXoa) {
                if (!chiTietPhieuNhapBLL.deleteChiTietPhieuNhap(chiTiet)) {
                    throw new Exception("Không thể xóa chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int soLuongMoi = sanPham.getiSoLuong() - chiTiet.getiSoLuong();
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            for (ChiTietPNDTO chiTiet : chiTietCanThem) {
                if (!chiTietPhieuNhapBLL.addChiTietPhieuNhap(chiTiet)) {
                    throw new Exception("Không thể thêm chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int soLuongMoi = sanPham.getiSoLuong() + chiTiet.getiSoLuong();
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            for (ChiTietPNDTO chiTiet : chiTietCanCapNhat) {
                if (!chiTietPhieuNhapBLL.updateChiTietPhieuNhap(chiTiet)) {
                    throw new Exception("Không thể cập nhật chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                int soLuongCu = 0;
                for (ChiTietPNDTO chiTietCu : chiTietPNHienTai) {
                    if (chiTietCu.getStrMaGiay().equals(chiTiet.getStrMaGiay())) {
                        soLuongCu = chiTietCu.getiSoLuong();
                        break;
                    }
                }
                
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int chenhLech = chiTiet.getiSoLuong() - soLuongCu;
                    int soLuongMoi = sanPham.getiSoLuong() + chenhLech;
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            double tongTien = 0;
            for (ChiTietPNDTO chiTiet : listChiTietPN) {
                tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaNhap();
            }
            
            PhieuNhapBLL phieuNhapBLL = new PhieuNhapBLL();
            if (!phieuNhapBLL.updateTongTien(phieuNhap.getStrMaPN(), tongTien)) {
                throw new Exception("Không thể cập nhật tổng tiền phiếu nhập");
            }
            
            phieuNhap.setTongTien(tongTien);
            lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
            
            // Tải lại dữ liệu từ database để đồng bộ listChiTietPN
            loadData();
            
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
            "Bạn có chắc chắn muốn xóa sản phẩm này khỏi phiếu nhập?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String maSP = tblChiTietPhieuNhap.getValueAt(selectedRow, 1).toString();
            ChiTietPNDTO chiTietXoa = null;
            
            // Tìm chi tiết phiếu nhập cần xóa
            for (ChiTietPNDTO chiTiet : listChiTietPN) {
                if (chiTiet.getStrMaGiay().equals(maSP)) {
                    chiTietXoa = chiTiet;
                    break;
                }
            }
            
            if (chiTietXoa != null) {
                // Xóa trực tiếp khỏi database
                boolean deleted = chiTietPhieuNhapBLL.deleteChiTietPhieuNhap(chiTietXoa);
                if (deleted) {
                    // Cập nhật số lượng tồn kho
                    SanPhamDAL sanPhamDAL = new SanPhamDAL();
                    SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTietXoa.getStrMaGiay());
                    if (sanPham != null) {
                        int soLuongMoi = sanPham.getiSoLuong() - chiTietXoa.getiSoLuong();
                        sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                    }
                    
                    // Cập nhật tổng tiền
                    double tongTien = 0;
                    for (ChiTietPNDTO chiTiet : listChiTietPN) {
                        if (!chiTiet.getStrMaGiay().equals(maSP)) {
                            tongTien += chiTiet.getiSoLuong() * chiTiet.getiGiaNhap();
                        }
                    }
                    PhieuNhapBLL phieuNhapBLL = new PhieuNhapBLL();
                    phieuNhapBLL.updateTongTien(phieuNhap.getStrMaPN(), tongTien);
                    phieuNhap.setTongTien(tongTien);
                    lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongTien));
                    
                    // Tải lại dữ liệu từ database
                    loadData();
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
            }
        }
    }
}
