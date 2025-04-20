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
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuNhapView extends JDialog{
    private JLabel lblTitle, lblMaPN, lblMaNCC, lblMaNV, lblNgayNhap, lblTongTien;
    private CustomTable tblChiTietPhieuNhap;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private CustomButton btnDong, btnInPhieuNhap, btnThemSanPham, btnXoaSanPham, btnLuuChiTiet;
    
    private JPanel pnlThemPhieuNhap;
    private JLabel lblMaSP, lblSoLuong, lblGiaNhap;
    private customTextField txtMaSP, txtSoLuong, txtGiaNhap;
    private CustomButton btnThem, btnHuy;
    
    private PhieuNhapDTO pn;
    private ArrayList<ChiTietPNDTO> list_ctpn;
    private ChiTietPhieuNhapBLL ctpn;
    
    public ChiTietPhieuNhapView(PhieuNhapDTO pn){
        super();
        setTitle("Chi tiết phiếu nhập");
        setModal(true);
        this.pn = pn;
        try {
            this.ctpn = new ChiTietPhieuNhapBLL();
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
        
        lblTitle = new JLabel("CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 900, 30);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
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
        
        lblMaPN = new JLabel("Mã phiếu nhập: " + pn.getStrMaPN());
        lblMaPN.setBounds(20, y, 350, 20);
        lblMaPN.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaPN);
        
        lblNgayNhap = new JLabel("Ngày nhập: " + pn.getStrNgayNhap());
        lblNgayNhap.setBounds(400, y, 350, 20);
        lblNgayNhap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblNgayNhap);
        
        y += 25;
        
        lblMaNCC = new JLabel("Nhà cung cấp: " + pn.getStrMaNCC());
        lblMaNCC.setBounds(20, y, 350, 20);
        lblMaNCC.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaNCC);
        
        lblMaNV = new JLabel("Nhân viên: " + pn.getStrMaNV());
        lblMaNV.setBounds(400, y, 350, 20);
        lblMaNV.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblMaNV);
        
        y += 25;
        
        lblTongTien = new JLabel("Tổng tiền: " + String.format("%,.0f VND", pn.getTongTien()));
        lblTongTien.setBounds(20, y, 350, 20);
        lblTongTien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlInfo.add(lblTongTien);
        
        createTable();
        createAddPhieuNhapPanel();
        
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
        tableModel.addColumn("Mã giày");
        tableModel.addColumn("Mã phiếu nhập");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Giá nhập");
        tblChiTietPhieuNhap = new CustomTable(tableModel);
        scrollPane = new JScrollPane(tblChiTietPhieuNhap);
        scrollPane.setBounds(20, 200, 860, 240);
        this.add(scrollPane);
        }
    
    private void updateTable() {
    tableModel.setRowCount(0);
    double tong = 0;
    for (var temp : list_ctpn) {
        Vector<Object> row = new Vector<>();
        row.add(temp.getStrMaGiay());
        row.add(temp.getStrMaPN());
        row.add(temp.getiSoLuong());
        row.add(String.format("%,d", temp.getiGiaNhap()));
        tableModel.addRow(row);
        tong += temp.getiSoLuong() * temp.getiGiaNhap();
    }
    lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tong));
    pn.setTongTien(tong);
}
    
    private void createAddPhieuNhapPanel(){
        pnlThemPhieuNhap=new JPanel();
        pnlThemPhieuNhap.setLayout(null);
        pnlThemPhieuNhap.setBounds(20, 450, 860, 150);
        pnlThemPhieuNhap.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.decode("#E1E1E1")), 
            "Thêm sản phẩm"));
        pnlThemPhieuNhap.setBackground(Color.WHITE);
        this.add(pnlThemPhieuNhap);
        pnlThemPhieuNhap.setVisible(false); // Ẩn ban đầu
        
        int y = 30;
        
        lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(20, y, 100, 25);
        lblMaSP.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemPhieuNhap.add(lblMaSP);
        
        txtMaSP = new customTextField();
        txtMaSP.setBounds(120, y, 150, 25);
        txtMaSP.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemPhieuNhap.add(txtMaSP);
        
        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(300, y, 100, 25);
        lblSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemPhieuNhap.add(lblSoLuong);
        
        txtSoLuong = new customTextField();
        txtSoLuong.setBounds(400, y, 100, 25);
        txtSoLuong.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemPhieuNhap.add(txtSoLuong);
        
        lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setBounds(520, y, 100, 25);
        lblGiaNhap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pnlThemPhieuNhap.add(lblGiaNhap);
        
        txtGiaNhap = new customTextField();
        txtGiaNhap.setBounds(620, y, 100, 25);
        txtGiaNhap.setBorderColor(Color.decode("#E1E1E1"));
        pnlThemPhieuNhap.add(txtGiaNhap);
        
        y += 40;
        
        // Thêm và Hủy
        btnThem = new CustomButton("Thêm");
        btnThem.setBounds(300, y, 100, 30);
        btnThem.setBackground(Color.decode("#2ECC71"));
        btnThem.setForeground(Color.WHITE);
        btnThem.setBorderColor(btnThem.getBackground());
        pnlThemPhieuNhap.add(btnThem);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(420, y, 100, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        pnlThemPhieuNhap.add(btnHuy);
    }
    private void loadData(){
        list_ctpn=new ArrayList<>();
        for(var temp : ctpn.getListChiTietPhieuNhap())
            if(temp.getStrMaPN().equals(pn.getStrMaPN()))
                list_ctpn.add(temp);
        tableModel.setRowCount(0);
        double tong=0;
        for(var temp : list_ctpn){
            Vector<Object> row = new Vector<>();
            row.add(temp.getStrMaGiay());
            row.add(temp.getStrMaPN());
            row.add(temp.getiSoLuong());
            row.add(String.format("%,d", temp.getiGiaNhap()));
            tableModel.addRow(row);
            tong=tong+temp.getiSoLuong()*temp.getiGiaNhap();
        }
        lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tong));
    }
    private void addEvents(){
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
                addPhieuNhap();
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
        pnlThemPhieuNhap.setVisible(show);
        if (show) {
            txtMaSP.setText("");
            txtSoLuong.setText("");
            txtGiaNhap.setText("");
            txtMaSP.requestFocus();
        }
    }
    private void addPhieuNhap(){
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
        SanPhamDAL sanpham = new SanPhamDAL();
        SanPhamDTO sp = sanpham.getSanPhamByMa(maSP);
        if (sp == null) {
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
        for (var chiTiet : list_ctpn) 
            if (chiTiet.getStrMaGiay().equals(maSP)) {
                soLuongDaCoTrongPN = chiTiet.getiSoLuong();
                chiTietHienTai = chiTiet;
                break;
            }

        if (chiTietHienTai != null) {
            int option = JOptionPane.showConfirmDialog(this, 
                "Sản phẩm đã tồn tại trong phiếu nhập! Bạn có muốn cập nhật số lượng và giá nhập không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                chiTietHienTai.setiSoLuong(soLuong);
                chiTietHienTai.setiGiaNhap(giaNhap);
                updateTable();
                toggleAddProductPanel(false);
            }
            JOptionPane.showMessageDialog(this, 
            "Cập nhật thành công! Nhấn 'Lưu thay đổi' để cập nhật vào database.", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ChiTietPNDTO newPN = new ChiTietPNDTO(
            pn.getStrMaPN(), 
            maSP, 
            soLuong, 
            giaNhap
        );
        list_ctpn.add(newPN);
        updateTable();
        toggleAddProductPanel(false);
        JOptionPane.showMessageDialog(this, 
            "Thêm sản phẩm thành công! Nhấn 'Lưu thay đổi' để cập nhật vào database.", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
}       
    private void saveChanges(){
                try {
            // Lấy danh sách chi tiết phiếu nhập hiện tại từ database
            ArrayList<ChiTietPNDTO> chiTietPNHienTai = new ArrayList<>();
            for (ChiTietPNDTO chiTiet : ctpn.getListChiTietPhieuNhap()) {
                if (chiTiet.getStrMaPN().equals(pn.getStrMaPN())) {
                    chiTietPNHienTai.add(chiTiet);
                }
            }
            
            // Kiểm tra số lượng tồn kho
            SanPhamDAL sanPhamDAL = new SanPhamDAL();
            
            // Danh sách chi tiết cần xóa (có trong DB nhưng không còn trong GUI)
            ArrayList<ChiTietPNDTO> chiTietCanXoa = new ArrayList<>();
            for (ChiTietPNDTO chiTietHienTai : chiTietPNHienTai) {
                boolean timThay = false;
                for (ChiTietPNDTO chiTietMoi : list_ctpn) {
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
            ArrayList<ChiTietPNDTO> chiTietCanThem = new ArrayList<>();
            // Danh sách chi tiết cần cập nhật (có cả trong GUI và DB nhưng thông tin đã thay đổi)
            ArrayList<ChiTietPNDTO> chiTietCanCapNhat = new ArrayList<>();
            
            for (ChiTietPNDTO chiTietMoi : list_ctpn) {
                boolean timThay = false;
                for (ChiTietPNDTO chiTietHienTai : chiTietPNHienTai) {
                    if (chiTietMoi.getStrMaGiay().equals(chiTietHienTai.getStrMaGiay())) {
                        timThay = true;
                        // Kiểm tra xem thông tin có thay đổi không
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
            
            // Thực hiện xóa các chi tiết không còn
            for (ChiTietPNDTO chiTiet : chiTietCanXoa) {
                if (!ctpn.deleteChiTietPhieuNhap(chiTiet.getStrMaPN(), chiTiet.getStrMaGiay())) {
                    throw new Exception("Không thể xóa chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                // Giảm số lượng trong tồn kho
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int soLuongMoi = sanPham.getiSoLuong() - chiTiet.getiSoLuong();
                    sanPham.setiSoLuong(soLuongMoi);
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            // Thực hiện thêm các chi tiết mới
            for (ChiTietPNDTO chiTiet : chiTietCanThem) {
                if (!ctpn.addChiTietPhieuNhap(chiTiet)) {
                    throw new Exception("Không thể thêm chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                // Tăng số lượng trong tồn kho
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int soLuongMoi = sanPham.getiSoLuong() + chiTiet.getiSoLuong();
                    sanPham.setiSoLuong(soLuongMoi);
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            // Thực hiện cập nhật các chi tiết đã thay đổi
            for (ChiTietPNDTO chiTiet : chiTietCanCapNhat) {
                if (!ctpn.updateChiTietPhieuNhap(chiTiet)) {
                    throw new Exception("Không thể cập nhật chi tiết phiếu nhập: " + chiTiet.getStrMaGiay());
                }
                
                // Tìm số lượng cũ
                int soLuongCu = 0;
                for (ChiTietPNDTO chiTietCu : chiTietPNHienTai) {
                    if (chiTietCu.getStrMaGiay().equals(chiTiet.getStrMaGiay())) {
                        soLuongCu = chiTietCu.getiSoLuong();
                        break;
                    }
                }
                
                // Cập nhật số lượng trong tồn kho
                SanPhamDTO sanPham = sanPhamDAL.getSanPhamByMa(chiTiet.getStrMaGiay());
                if (sanPham != null) {
                    int chenhLech = chiTiet.getiSoLuong() - soLuongCu;
                    int soLuongMoi = sanPham.getiSoLuong() + chenhLech;
                    sanPham.setiSoLuong(soLuongMoi);
                    sanPhamDAL.updateSoluong(sanPham.getStrMaGiay(), soLuongMoi);
                }
            }
            
            // Tính tổng tiền mới
            double tongtien = 0;
            for (ChiTietPNDTO chiTiet : list_ctpn) {
                tongtien += chiTiet.getiSoLuong() * chiTiet.getiGiaNhap();
            }
            
            // Cập nhật tổng tiền của phiếu nhập trong database
            PhieuNhapBLL phieuNhapBLL = new PhieuNhapBLL();
            if (!phieuNhapBLL.updateTongTien(pn.getStrMaPN(), tongtien)) {
                throw new Exception("Không thể cập nhật tổng tiền phiếu nhập");
            }
            
            // Cập nhật lại giao diện
            pn.setTongTien(tongtien);
            lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VND", tongtien));
            updateTable();
            
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
        int selectedRow=-1;
        selectedRow=tblChiTietPhieuNhap.getSelectedRow();
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
            // Lấy mã sản phẩm từ bảng
            String maSP = tblChiTietPhieuNhap.getValueAt(selectedRow, 0).toString();
            
            // Tìm và xóa khỏi danh sách
            for (int i = 0; i < list_ctpn.size(); i++) {
                if (list_ctpn.get(i).getStrMaGiay().equals(maSP)) {
                    list_ctpn.remove(i);
                    break;
                }
            }
            // Cập nhật lại dữ liệu hiển thị
            updateTable();
            JOptionPane.showMessageDialog(this, 
                "Xóa sản phẩm thành công! Nhấn 'Lưu thay đổi' để cập nhật vào database.", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
