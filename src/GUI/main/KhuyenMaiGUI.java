/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import BLL.KhuyenMaiBLL;
import DTO.KhuyenMaiDTO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author cuong
 */
public class KhuyenMaiGUI extends JPanel {
    
    // Components
    private PanelFunction panelFunction;
    private CustomTable tblKhuyenMai;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Dialog components
    private JDialog dialogKhuyenMai;
    private customTextField txtMaKM, txtTenChuongTrinh, txtDieuKien, txtNgayBatDau, txtNgayKetThuc;
    private CustomComboBox cbLoaiChuongTrinh;
    private CustomButton btnLuu, btnHuy;
    
    // BLL
    private KhuyenMaiBLL khuyenMaiBLL;
    
    // Data
    private ArrayList<KhuyenMaiDTO> listKhuyenMai;
    private int selectedRow = -1;
    private boolean isAdding = false;
    
    public KhuyenMaiGUI() {
        khuyenMaiBLL = new KhuyenMaiBLL();
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
        cbFilter.addItem("Mã KM");
        cbFilter.addItem("Tên chương trình");
        cbFilter.addItem("Loại chương trình");
        cbFilter.addItem("Trạng thái");
        panelFunction.setCbfilter(cbFilter);
        
        // Tạo bảng
        createTable();
    }
    
    private void createTable() {
        // Tạo model của bảng
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã KM");
        tableModel.addColumn("Tên chương trình");
        tableModel.addColumn("Loại chương trình");
        tableModel.addColumn("Điều kiện");
        tableModel.addColumn("Ngày bắt đầu");
        tableModel.addColumn("Ngày kết thúc");
        tableModel.addColumn("Trạng thái");
        
        // Tạo bảng
        tblKhuyenMai = new CustomTable(tableModel);
        
        // Thêm bảng vào thanh cuộn
        scrollPane = new JScrollPane(tblKhuyenMai);
        scrollPane.setBounds(20, 150, 1076, 500);
        this.add(scrollPane);
    }
    
    private void loadData() {
        // Xóa dữ liệu hiện tại của bảng
        tableModel.setRowCount(0);
        
        // Lấy dữ liệu từ BLL
        listKhuyenMai = khuyenMaiBLL.getKhuyenMaiList();
        
        // Ngày hiện tại để kiểm tra trạng thái
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        // Thêm dữ liệu vào bảng
        for (KhuyenMaiDTO khuyenMai : listKhuyenMai) {
            Vector<Object> row = new Vector<>();
            row.add(khuyenMai.getStrMaKM());
            row.add(khuyenMai.getStrTenChuongTrinh());
            row.add(khuyenMai.getStrLoaiChuongTrinh());
            row.add(khuyenMai.getStrDieuKien());
            row.add(khuyenMai.getStrNgayBatDau());
            row.add(khuyenMai.getStrNgayKetThuc());
            
            // Kiểm tra trạng thái khuyến mãi
            String status = "Không hoạt động";
            try {
                Date startDate = dateFormat.parse(khuyenMai.getStrNgayBatDau());
                Date endDate = dateFormat.parse(khuyenMai.getStrNgayKetThuc());
                
                if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                    status = "Đang hoạt động";
                } else if (currentDate.before(startDate)) {
                    status = "Chưa diễn ra";
                } else {
                    status = "Đã kết thúc";
                }
            } catch (ParseException e) {
                status = "Lỗi ngày tháng";
            }
            
            row.add(status);
            tableModel.addRow(row);
        }
    }
    
    private void addEvents() {
        // Sự kiện khi chọn dòng trong bảng
        tblKhuyenMai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = tblKhuyenMai.getSelectedRow();
            }
        });
        
        // Nút Thêm
        panelFunction.btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdding = true;
                showKhuyenMaiDialog(null);
            }
        });
        
        // Nút Sửa
        panelFunction.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    isAdding = false;
                    int modelRow = tblKhuyenMai.convertRowIndexToModel(selectedRow);
                    showKhuyenMaiDialog(listKhuyenMai.get(modelRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khuyến mãi cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // Nút Xóa
        panelFunction.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKhuyenMai();
            }
        });
        
        // Nút Chi tiết
        panelFunction.btnChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showKhuyenMaiDetails();
            }
        });
        
        // Nút Tải lại
        panelFunction.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        
        // Nút Tìm kiếm
        panelFunction.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchKhuyenMai();
            }
        });
        
        
    }
    
    private void showKhuyenMaiDialog(KhuyenMaiDTO khuyenMai) {
        // Tạo dialog
        dialogKhuyenMai = new JDialog();
        dialogKhuyenMai.setTitle(isAdding ? "Thêm khuyến mãi" : "Sửa khuyến mãi");
        dialogKhuyenMai.setSize(500, 400);
        dialogKhuyenMai.setLayout(null);
        dialogKhuyenMai.setLocationRelativeTo(null);
        dialogKhuyenMai.setModal(true);
        dialogKhuyenMai.setResizable(false);
        
        // Tạo các components
        int y = 20;
        int height = 35;
        int gap = 45;
        
        JLabel lblMaKM = new JLabel("Mã khuyến mãi:");
        lblMaKM.setBounds(20, y, 150, height);
        lblMaKM.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblMaKM);
        
        txtMaKM = new customTextField();
        txtMaKM.setBounds(170, y, 290, height);
        txtMaKM.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhuyenMai.add(txtMaKM);
        
        y += gap;
        JLabel lblTenChuongTrinh = new JLabel("Tên chương trình:");
        lblTenChuongTrinh.setBounds(20, y, 150, height);
        lblTenChuongTrinh.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblTenChuongTrinh);
        
        txtTenChuongTrinh = new customTextField();
        txtTenChuongTrinh.setBounds(170, y, 290, height);
        txtTenChuongTrinh.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhuyenMai.add(txtTenChuongTrinh);
        
        y += gap;
        JLabel lblLoaiChuongTrinh = new JLabel("Loại chương trình:");
        lblLoaiChuongTrinh.setBounds(20, y, 150, height);
        lblLoaiChuongTrinh.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblLoaiChuongTrinh);
        
        cbLoaiChuongTrinh = new CustomComboBox();
        cbLoaiChuongTrinh.addItem("Giảm giá phần trăm");
        cbLoaiChuongTrinh.addItem("Giảm giá trực tiếp");
        cbLoaiChuongTrinh.addItem("Tặng quà");
        cbLoaiChuongTrinh.addItem("Mua X tặng Y");
        cbLoaiChuongTrinh.setBounds(170, y, 290, height);
        dialogKhuyenMai.add(cbLoaiChuongTrinh);
        
        y += gap;
        JLabel lblDieuKien = new JLabel("Điều kiện:");
        lblDieuKien.setBounds(20, y, 150, height);
        lblDieuKien.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblDieuKien);
        
        txtDieuKien = new customTextField();
        txtDieuKien.setBounds(170, y, 290, height);
        txtDieuKien.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhuyenMai.add(txtDieuKien);
        
        y += gap;
        JLabel lblNgayBatDau = new JLabel("Ngày bắt đầu:");
        lblNgayBatDau.setBounds(20, y, 150, height);
        lblNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblNgayBatDau);
        
        txtNgayBatDau = new customTextField();
        txtNgayBatDau.setBounds(170, y, 290, height);
        txtNgayBatDau.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhuyenMai.add(txtNgayBatDau);
        
        y += gap;
        JLabel lblNgayKetThuc = new JLabel("Ngày kết thúc:");
        lblNgayKetThuc.setBounds(20, y, 150, height);
        lblNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dialogKhuyenMai.add(lblNgayKetThuc);
        
        txtNgayKetThuc = new customTextField();
        txtNgayKetThuc.setBounds(170, y, 290, height);
        txtNgayKetThuc.setBorderColor(Color.decode("#E1E1E1"));
        dialogKhuyenMai.add(txtNgayKetThuc);
        
        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(170, 320, 100, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogKhuyenMai.add(btnLuu);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(290, 320, 100, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogKhuyenMai.add(btnHuy);
        
        // Thêm ghi chú về định dạng ngày
        JLabel lblNote = new JLabel("Định dạng ngày: dd MMM yyyy (VD: 01 Jan 2023)");
        lblNote.setBounds(20, 270, 400, 20);
        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblNote.setForeground(Color.GRAY);
        dialogKhuyenMai.add(lblNote);
        
        // Nếu là sửa, điền dữ liệu sẵn có
        if (!isAdding && khuyenMai != null) {
            txtMaKM.setText(khuyenMai.getStrMaKM());
            txtMaKM.setEditable(false);
            txtTenChuongTrinh.setText(khuyenMai.getStrTenChuongTrinh());
            
            String loai = khuyenMai.getStrLoaiChuongTrinh();
            for (int i = 0; i < cbLoaiChuongTrinh.getItemCount(); i++) {
                if (cbLoaiChuongTrinh.getItemAt(i).equals(loai)) {
                    cbLoaiChuongTrinh.setSelectedIndex(i);
                    break;
                }
            }
            
            txtDieuKien.setText(khuyenMai.getStrDieuKien());
            txtNgayBatDau.setText(khuyenMai.getStrNgayBatDau());
            txtNgayKetThuc.setText(khuyenMai.getStrNgayKetThuc());
        }
        
        // Sự kiện nút
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveKhuyenMai();
            }
        });
        
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogKhuyenMai.dispose();
            }
        });
        
        // Hiển thị dialog
        dialogKhuyenMai.setVisible(true);
    }
    
    private void saveKhuyenMai() {
        // Kiểm tra dữ liệu đầu vào
        if (txtMaKM.getText().trim().isEmpty() || 
            txtTenChuongTrinh.getText().trim().isEmpty() || 
            txtDieuKien.getText().trim().isEmpty() || 
            txtNgayBatDau.getText().trim().isEmpty() || 
            txtNgayKetThuc.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(dialogKhuyenMai, 
                "Vui lòng nhập đầy đủ thông tin!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        dateFormat.setLenient(false);
        
        try {
            Date startDate = dateFormat.parse(txtNgayBatDau.getText().trim());
            Date endDate = dateFormat.parse(txtNgayKetThuc.getText().trim());
            
            // Kiểm tra ngày bắt đầu phải trước hoặc bằng ngày kết thúc
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(dialogKhuyenMai, 
                    "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(dialogKhuyenMai, 
                "Định dạng ngày không hợp lệ! Vui lòng sử dụng định dạng: dd MMM yyyy (VD: 01 Jan 2023)", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Tạo đối tượng KhuyenMaiDTO
        KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(
            txtMaKM.getText().trim(),
            txtTenChuongTrinh.getText().trim(),
            cbLoaiChuongTrinh.getSelectedItem().toString(),
            txtDieuKien.getText().trim(),
            txtNgayBatDau.getText().trim(),
            txtNgayKetThuc.getText().trim()
        );
        
        boolean success;
        
        // Thêm hoặc cập nhật
        if (isAdding) {
            success = khuyenMaiBLL.insertKhuyenMai(khuyenMai);
            if (success) {
                JOptionPane.showMessageDialog(dialogKhuyenMai, 
                    "Thêm khuyến mãi thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogKhuyenMai, 
                    "Thêm khuyến mãi thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = khuyenMaiBLL.updateKhuyenMai(khuyenMai);
            if (success) {
                JOptionPane.showMessageDialog(dialogKhuyenMai, 
                    "Cập nhật khuyến mãi thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogKhuyenMai, 
                    "Cập nhật khuyến mãi thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Đóng dialog và làm mới dữ liệu
        dialogKhuyenMai.dispose();
        loadData();
    }
    
    private void deleteKhuyenMai() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khuyến mãi cần xóa!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa khuyến mãi này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int modelRow = tblKhuyenMai.convertRowIndexToModel(selectedRow);
            String maKM = listKhuyenMai.get(modelRow).getStrMaKM();
            boolean success = khuyenMaiBLL.deleteKhuyenMai(maKM);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khuyến mãi thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                selectedRow = -1;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa khuyến mãi thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchKhuyenMai() {
        String keyword = panelFunction.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        
        String filterOption = panelFunction.cbfilter.getSelectedItem().toString();
        
        // Lọc bảng dựa trên tùy chọn bộ lọc đã chọn
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblKhuyenMai.setRowSorter(sorter);
        
        if (filterOption.equals("Tất cả")) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        } else {
            int column = -1;
            switch (filterOption) {
                case "Mã KM":
                    column = 0;
                    break;
                case "Tên chương trình":
                    column = 1;
                    break;
                case "Loại chương trình":
                    column = 2;
                    break;
                case "Trạng thái":
                    column = 6;
                    break;
            }
            
            if (column != -1) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, column));
            }
        }
    }
    
    private void showKhuyenMaiDetails() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khuyến mãi để xem chi tiết!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Lấy dữ liệu của dòng đã chọn
            int modelRow = tblKhuyenMai.convertRowIndexToModel(selectedRow);
            KhuyenMaiDTO khuyenMai = listKhuyenMai.get(modelRow);
            
            // Mở form chi tiết khuyến mãi
            ChiTietKhuyenMaiView chiTietView = new ChiTietKhuyenMaiView(null, khuyenMai);
            chiTietView.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi hiển thị chi tiết khuyến mãi: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void resetData() {
        // Làm mới dữ liệu
        loadData();
        
        // Xóa bộ lọc nếu đang áp dụng
        if (tblKhuyenMai.getRowSorter() != null) {
            tblKhuyenMai.setRowSorter(null);
        }
        
        // Reset các trường tìm kiếm
        panelFunction.fieldSearch.setText("Tìm kiếm...");
        panelFunction.cbfilter.setSelectedIndex(0);
        
        // Xóa dòng đang chọn
        selectedRow = -1;
        tblKhuyenMai.clearSelection();
        
        JOptionPane.showMessageDialog(this, 
            "Đã làm mới dữ liệu thành công!", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}