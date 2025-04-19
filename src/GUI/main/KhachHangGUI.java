/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import BLL.KhachHangBLL;
import DTO.KhachHangDTO;
import GUI.component.PanelFunction;
import GUI.component.CustomButton;
import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author bactran
 */
public class KhachHangGUI extends JPanel {
    
    // Components
    private PanelFunction panelFunction;
    private CustomTable tblKhachHang;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Dialog components
    private JDialog dialogKhachHang;
    private customTextField txtMaKH, txtHo, txtTen, txtDiaChi, txtEmail, txtTongChiTieu;
    private CustomComboBox cbGioiTinh, cbLoai;
    private CustomButton btnLuu, btnHuy;
    
    // BLL
    private KhachHangBLL khachHangBLL;
    
    // Data
    private ArrayList<KhachHangDTO> listKhachHang;
    private int selectedRow = -1;
    private boolean isAdding = false;
    
    public KhachHangGUI() {
        khachHangBLL = new KhachHangBLL();
        init();
        loadData();
        addEvents();
    }
    
    private void init() {
        // Thiết lập panel
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, 1116, 700);
        
        // Panel Function
        panelFunction = new PanelFunction();
        panelFunction.setBtnChitiet();
        panelFunction.setBtnReset();
        this.add(panelFunction);
        
        // Thiết lập các tùy chọn bộ lọc
        CustomComboBox cbFilter = new CustomComboBox();
        cbFilter.addItem("Tất cả");
        cbFilter.addItem("Mã KH");
        cbFilter.addItem("Họ tên");
        cbFilter.addItem("Giới tính");
        cbFilter.addItem("Loại KH");
        panelFunction.setCbfilter(cbFilter);
        
        // Tạo bảng
        createTable();
        
        // Điều chỉnh giao diện
        panelFunction.btnXuatExcel = new JButton("Xuất Excel");
        panelFunction.btnXuatExcel.setBounds(440, 10, 110, 100);
        panelFunction.add(panelFunction.btnXuatExcel);
    }
    
    private void createTable() {
        // Tạo model của bảng
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã KH");
        tableModel.addColumn("Họ");
        tableModel.addColumn("Tên");
        tableModel.addColumn("Giới tính");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Email");
        tableModel.addColumn("Loại KH");
        tableModel.addColumn("Tổng chi tiêu");
        
        // Tạo bảng
        tblKhachHang = new CustomTable(tableModel);
        
        // Thêm bảng vào thanh cuộn
        scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setBounds(20, 150, 1076, 500);
        this.add(scrollPane);
    }
    
    private void loadData() {
        // Xóa dữ liệu hiện tại của bảng
        tableModel.setRowCount(0);
        
        // Lấy dữ liệu từ BLL
        listKhachHang = khachHangBLL.getKhachHangList();
        
        // Thêm dữ liệu vào bảng
        for (KhachHangDTO khachHang : listKhachHang) {
            Vector<Object> row = new Vector<>();
            row.add(khachHang.getStrMaKH());
            row.add(khachHang.getStrHo());
            row.add(khachHang.getStrTen());
            row.add(khachHang.getStrGioiTinh());
            row.add(khachHang.getStrDiaChi());
            row.add(khachHang.getStrEmail());
            row.add(khachHang.getStrLoai());
            row.add(String.format("%,.0f", khachHang.getiTongChiTieu()));
            tableModel.addRow(row);
        }
    }
    
    private void addEvents() {
        // Sự kiện khi chọn dòng trong bảng
        tblKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = tblKhachHang.getSelectedRow();
            }
        });
        
        // Nút Thêm
        panelFunction.btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdding = true;
                showKhachHangDialog(null);
            }
        });
        
        // Nút Sửa
        panelFunction.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    isAdding = false;
                    showKhachHangDialog(listKhachHang.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // Nút Xóa
        panelFunction.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKhachHang();
            }
        });
        
        // Nút Chi tiết
        panelFunction.btnChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showKhachHangDetails();
            }
        });
        
        // Nút Xuất Excel
        panelFunction.btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        
        // Nút Tải lại
        panelFunction.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                panelFunction.fieldSearch.setText("Tìm kiếm...");
                panelFunction.cbfilter.setSelectedIndex(0);
                if (tblKhachHang.getRowSorter() != null) {
                    tblKhachHang.setRowSorter(null);
                }
            }
        });
        
        // Nút Tìm kiếm
        panelFunction.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchKhachHang();
            }
        });
    }
    
    private void showKhachHangDialog(KhachHangDTO khachHang) {
        // Tạo dialog
        dialogKhachHang = new JDialog();
        dialogKhachHang.setTitle(isAdding ? "Thêm khách hàng" : "Sửa khách hàng");
        dialogKhachHang.setSize(500, 450);
        dialogKhachHang.setLayout(null);
        dialogKhachHang.setLocationRelativeTo(null);
        dialogKhachHang.setModal(true);
        dialogKhachHang.setResizable(false);
        
        // Tạo các components
        int y = 20;
        int height = 35;
        int gap = 45;
        
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(20, y, 120, height);
        lblMaKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblMaKH);
        
        txtMaKH = new customTextField();
        txtMaKH.setBounds(150, y, 300, height);
        txtMaKH.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtMaKH);
        
        y += gap;
        JLabel lblHo = new JLabel("Họ:");
        lblHo.setBounds(20, y, 120, height);
        lblHo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblHo);
        
        txtHo = new customTextField();
        txtHo.setBounds(150, y, 300, height);
        txtHo.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtHo);
        
        y += gap;
        JLabel lblTen = new JLabel("Tên:");
        lblTen.setBounds(20, y, 120, height);
        lblTen.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblTen);
        
        txtTen = new customTextField();
        txtTen.setBounds(150, y, 300, height);
        txtTen.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtTen);
        
        y += gap;
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(20, y, 120, height);
        lblGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblGioiTinh);
        
        cbGioiTinh = new CustomComboBox();
        cbGioiTinh.addItem("Nam");
        cbGioiTinh.addItem("Nữ");
        cbGioiTinh.setBounds(150, y, 300, height);
        dialogKhachHang.add(cbGioiTinh);
        
        y += gap;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(20, y, 120, height);
        lblDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblDiaChi);
        
        txtDiaChi = new customTextField();
        txtDiaChi.setBounds(150, y, 300, height);
        txtDiaChi.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtDiaChi);
        
        y += gap;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, y, 120, height);
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblEmail);
        
        txtEmail = new customTextField();
        txtEmail.setBounds(150, y, 300, height);
        txtEmail.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtEmail);
        
        y += gap;
        JLabel lblLoai = new JLabel("Loại khách hàng:");
        lblLoai.setBounds(20, y, 120, height);
        lblLoai.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblLoai);
        
        cbLoai = new CustomComboBox();
        cbLoai.addItem("Thường");
        cbLoai.addItem("VIP");
        cbLoai.addItem("Thân thiết");
        cbLoai.setBounds(150, y, 300, height);
        dialogKhachHang.add(cbLoai);
        
        y += gap;
        JLabel lblTongChiTieu = new JLabel("Tổng chi tiêu:");
        lblTongChiTieu.setBounds(20, y, 120, height);
        lblTongChiTieu.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhachHang.add(lblTongChiTieu);
        
        txtTongChiTieu = new customTextField();
        txtTongChiTieu.setBounds(150, y, 300, height);
        txtTongChiTieu.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhachHang.add(txtTongChiTieu);
        
        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(150, 380, 100, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogKhachHang.add(btnLuu);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(280, 380, 100, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogKhachHang.add(btnHuy);
        
        // Nếu là sửa, điền dữ liệu sẵn có
        if (!isAdding && khachHang != null) {
            txtMaKH.setText(khachHang.getStrMaKH());
            txtMaKH.setEditable(false);
            txtHo.setText(khachHang.getStrHo());
            txtTen.setText(khachHang.getStrTen());
            if (khachHang.getStrGioiTinh().equalsIgnoreCase("Nam")) {
                cbGioiTinh.setSelectedIndex(0);
            } else {
                cbGioiTinh.setSelectedIndex(1);
            }
            txtDiaChi.setText(khachHang.getStrDiaChi());
            txtEmail.setText(khachHang.getStrEmail());
            
            String loai = khachHang.getStrLoai();
            if (loai.equalsIgnoreCase("Thường")) {
                cbLoai.setSelectedIndex(0);
            } else if (loai.equalsIgnoreCase("VIP")) {
                cbLoai.setSelectedIndex(1);
            } else {
                cbLoai.setSelectedIndex(2);
            }
            
            txtTongChiTieu.setText(String.valueOf(khachHang.getiTongChiTieu()));
        } else {
            // Đặt giá trị mặc định cho tổng chi tiêu khi thêm mới
            txtTongChiTieu.setText("0");
        }
        
        // Sự kiện nút
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveKhachHang();
            }
        });
        
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogKhachHang.dispose();
            }
        });
        
        // Hiển thị dialog
        dialogKhachHang.setVisible(true);
    }
    
    private void saveKhachHang() {
        // Kiểm tra dữ liệu đầu vào
        if (txtMaKH.getText().trim().isEmpty() || 
            txtHo.getText().trim().isEmpty() || 
            txtTen.getText().trim().isEmpty() || 
            txtDiaChi.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() || 
            txtTongChiTieu.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(dialogKhachHang, 
                "Vui lòng nhập đầy đủ thông tin!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra định dạng số
        double tongChiTieu;
        try {
            tongChiTieu = Double.parseDouble(txtTongChiTieu.getText().trim());
            if (tongChiTieu < 0) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Tổng chi tiêu không được âm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogKhachHang, 
                "Tổng chi tiêu phải là số!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Tạo đối tượng KhachHangDTO
        KhachHangDTO khachHang = new KhachHangDTO(
            txtMaKH.getText().trim(),
            txtHo.getText().trim(),
            txtTen.getText().trim(),
            cbGioiTinh.getSelectedItem().toString(),
            txtDiaChi.getText().trim(),
            txtEmail.getText().trim(),
            cbLoai.getSelectedItem().toString(),
            tongChiTieu
        );
        
        boolean success;
        
        // Thêm hoặc cập nhật
        if (isAdding) {
            // Kiểm tra mã khách hàng đã tồn tại chưa
            if (khachHangBLL.checkKhachHangExists(khachHang.getStrMaKH())) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Mã khách hàng đã tồn tại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Kiểm tra email đã tồn tại chưa
            if (khachHangBLL.checkEmailExists(khachHang.getStrEmail())) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Email đã được sử dụng!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            success = khachHangBLL.insertKhachHang(khachHang);
            if (success) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Thêm khách hàng thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Thêm khách hàng thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // Kiểm tra nếu email đã thay đổi và email mới đã tồn tại
            KhachHangDTO currentKH = khachHangBLL.getKhachHangById(khachHang.getStrMaKH());
            if (!khachHang.getStrEmail().equals(currentKH.getStrEmail()) && 
                khachHangBLL.checkEmailExists(khachHang.getStrEmail())) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Email đã được sử dụng!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            success = khachHangBLL.updateKhachHang(khachHang);
            if (success) {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Cập nhật khách hàng thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogKhachHang, 
                    "Cập nhật khách hàng thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Đóng dialog và làm mới dữ liệu
        dialogKhachHang.dispose();
        loadData();
    }
    
    private void deleteKhachHang() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khách hàng cần xóa!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa khách hàng này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String maKH = listKhachHang.get(selectedRow).getStrMaKH();
            boolean success = khachHangBLL.deleteKhachHang(maKH);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khách hàng thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                selectedRow = -1;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khách hàng thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchKhachHang() {
        String keyword = panelFunction.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        
        String filterOption = panelFunction.cbfilter.getSelectedItem().toString();
        
        // Lọc bảng dựa trên tùy chọn bộ lọc đã chọn
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblKhachHang.setRowSorter(sorter);
        
        if (filterOption.equals("Tất cả")) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        } else {
            int column = -1;
            switch (filterOption) {
                case "Mã KH":
                    column = 0;
                    break;
                case "Họ tên":
                    // Tìm kiếm cả trong cột Họ và Tên
                    RowFilter<DefaultTableModel, Object> hoFilter = RowFilter.regexFilter("(?i)" + keyword, 1);
                    RowFilter<DefaultTableModel, Object> tenFilter = RowFilter.regexFilter("(?i)" + keyword, 2);
                    ArrayList<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
                    filters.add(hoFilter);
                    filters.add(tenFilter);
                    sorter.setRowFilter(RowFilter.orFilter(filters));
                    return;
                case "Giới tính":
                    column = 3;
                    break;
                case "Loại KH":
                    column = 6;
                    break;
            }
            
            if (column != -1) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, column));
            }
        }
    }
    
    private void showKhachHangDetails() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khách hàng để xem chi tiết!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Trong ứng dụng thực tế, đây sẽ mở một giao diện chi tiết hiển thị thông tin chi tiết của khách hàng
        // Hiện tại, chỉ hiển thị thông tin cơ bản trong hộp thoại thông báo
        KhachHangDTO khachHang = listKhachHang.get(selectedRow);
        
        StringBuilder details = new StringBuilder();
        details.append("CHI TIẾT KHÁCH HÀNG\n\n");
        details.append("Mã khách hàng: ").append(khachHang.getStrMaKH()).append("\n");
        details.append("Họ và tên: ").append(khachHang.getStrHo()).append(" ").append(khachHang.getStrTen()).append("\n");
        details.append("Giới tính: ").append(khachHang.getStrGioiTinh()).append("\n");
        details.append("Địa chỉ: ").append(khachHang.getStrDiaChi()).append("\n");
        details.append("Email: ").append(khachHang.getStrEmail()).append("\n");
        details.append("Loại khách hàng: ").append(khachHang.getStrLoai()).append("\n");
        details.append("Tổng chi tiêu: ").append(String.format("%,.0f VND", khachHang.getiTongChiTieu()));
        
        JOptionPane.showMessageDialog(this, 
            details.toString(), 
            "Chi tiết khách hàng", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToExcel() {
        JOptionPane.showMessageDialog(this, 
            "Chức năng xuất Excel đang được phát triển.", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}