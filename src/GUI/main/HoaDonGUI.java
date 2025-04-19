/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import BLL.HoaDonBLL;
import DTO.HoaDonDTO;
import GUI.component.PanelFunction;
import GUI.component.CustomButton;
import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.customPasswordField;

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
public class HoaDonGUI extends JPanel {
    
    // Components
    private PanelFunction panelFunction;
    private JTable tblHoaDon;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Dialog components
    private JDialog dialogHoaDon;
    private customTextField txtMaHD, txtMaNV, txtMaKH, txtMaKM, txtNgayBan, txtTongTien;
    private CustomButton btnLuu, btnHuy;
    
    // BLL
    private HoaDonBLL hoaDonBLL;
    
    // Data
    private ArrayList<HoaDonDTO> listHoaDon;
    private int selectedRow = -1;
    private boolean isAdding = false;
    
    public HoaDonGUI() {
        hoaDonBLL = new HoaDonBLL();
        init();
        loadData();
        addEvents();
    }
    
    private void init() {
        // Set up panel
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, 1116, 700);
        
        // Panel Function
        panelFunction = new PanelFunction();
        panelFunction.setBtnChitiet();
        panelFunction.setBtnReset(); // Thêm nút Reset
        this.add(panelFunction);
        
        // Set filter options
        CustomComboBox cbFilter = new CustomComboBox();
        cbFilter.addItem("Tất cả");
        cbFilter.addItem("Mã HD");
        cbFilter.addItem("Mã NV");
        cbFilter.addItem("Mã KH");
        cbFilter.addItem("Ngày bán");
        panelFunction.setCbfilter(cbFilter);
        
        // Table
        createTable();
    }
    
    private void createTable() {
        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã HD");
        tableModel.addColumn("Mã NV");
        tableModel.addColumn("Mã KH");
        tableModel.addColumn("Mã KM");
        tableModel.addColumn("Ngày bán");
        tableModel.addColumn("Tổng tiền");
        
        // Create table
        tblHoaDon = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        
        // Table settings
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        tblHoaDon.getTableHeader().setFont(new Font("Sans-serif", Font.BOLD, 14));
        tblHoaDon.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblHoaDon.setRowHeight(30);
        
        // Add table to scroll pane
        scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.setBounds(20, 150, 1076, 500);
        this.add(scrollPane);
    }
    
    private void loadData() {
        // Clear table
        tableModel.setRowCount(0);
        
        // Get data from BLL
        listHoaDon = hoaDonBLL.getHoaDonList();
        
        // Add data to table
        for (HoaDonDTO hoaDon : listHoaDon) {
            Vector<Object> row = new Vector<>();
            row.add(hoaDon.getStrMaHD());
            row.add(hoaDon.getStrMaNV());
            row.add(hoaDon.getStrMaKH());
            row.add(hoaDon.getStrMaKM());
            row.add(hoaDon.getStrNgayBan());
            row.add(String.format("%,.0f", hoaDon.getTongTien()));
            tableModel.addRow(row);
        }
    }
    
    private void addEvents() {
        // Table row selection
        tblHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = tblHoaDon.getSelectedRow();
                
                // Double-click to show details
                if (e.getClickCount() == 2 && selectedRow != -1) {
                    showHoaDonDetails();
                }
            }
        });
        
        // Button Add
        panelFunction.btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdding = true;
                showHoaDonDialog(null);
            }
        });
        
        // Button Edit
        panelFunction.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    isAdding = false;
                    showHoaDonDialog(listHoaDon.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // Button Delete
        panelFunction.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteHoaDon();
            }
        });
        
        // Button Details
        panelFunction.btnChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHoaDonDetails();
            }
        });
        
        // Button Reset
        panelFunction.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        
        // Button Search
        panelFunction.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchHoaDon();
            }
        });
    }
    
    private void showHoaDonDialog(HoaDonDTO hoaDon) {
        // Create dialog
        dialogHoaDon = new JDialog();
        dialogHoaDon.setTitle(isAdding ? "Thêm hóa đơn" : "Sửa hóa đơn");
        dialogHoaDon.setSize(400, 400);
        dialogHoaDon.setLayout(null);
        dialogHoaDon.setLocationRelativeTo(null);
        dialogHoaDon.setModal(true);
        dialogHoaDon.setResizable(false);
        
        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;
        
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblMaHD);
        
        txtMaHD = new customTextField();
        txtMaHD.setBounds(130, y, 230, height);
        txtMaHD.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtMaHD);
        
        y += gap;
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblMaNV);
        
        txtMaNV = new customTextField();
        txtMaNV.setBounds(130, y, 230, height);
        txtMaNV.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtMaNV);
        
        y += gap;
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblMaKH);
        
        txtMaKH = new customTextField();
        txtMaKH.setBounds(130, y, 230, height);
        txtMaKH.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtMaKH);
        
        y += gap;
        JLabel lblMaKM = new JLabel("Mã khuyến mãi:");
        lblMaKM.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblMaKM);
        
        txtMaKM = new customTextField();
        txtMaKM.setBounds(130, y, 230, height);
        txtMaKM.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtMaKM);
        
        y += gap;
        JLabel lblNgayBan = new JLabel("Ngày bán:");
        lblNgayBan.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblNgayBan);
        
        txtNgayBan = new customTextField();
        txtNgayBan.setBounds(130, y, 230, height);
        txtNgayBan.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtNgayBan);
        
        y += gap;
        JLabel lblTongTien = new JLabel("Tổng tiền:");
        lblTongTien.setBounds(20, y, 100, height);
        dialogHoaDon.add(lblTongTien);
        
        txtTongTien = new customTextField();
        txtTongTien.setBounds(130, y, 230, height);
        txtTongTien.setBorderColor(Color.decode("#E1E1E1"));
        dialogHoaDon.add(txtTongTien);
        
        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 320, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogHoaDon.add(btnLuu);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(220, 320, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogHoaDon.add(btnHuy);
        
        // Fill data if editing
        if (!isAdding && hoaDon != null) {
            txtMaHD.setText(hoaDon.getStrMaHD());
            txtMaHD.setEditable(false);
            txtMaNV.setText(hoaDon.getStrMaNV());
            txtMaKH.setText(hoaDon.getStrMaKH());
            txtMaKM.setText(hoaDon.getStrMaKM());
            txtNgayBan.setText(hoaDon.getStrNgayBan());
            txtTongTien.setText(String.valueOf(hoaDon.getTongTien()));
        }
        
        // Button events
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHoaDon();
            }
        });
        
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogHoaDon.dispose();
            }
        });
        
        // Show dialog
        dialogHoaDon.setVisible(true);
    }
    
    private void saveHoaDon() {
        // Validate inputs
        if (txtMaHD.getText().trim().isEmpty() || 
            txtMaNV.getText().trim().isEmpty() || 
            txtMaKH.getText().trim().isEmpty() || 
            txtNgayBan.getText().trim().isEmpty() || 
            txtTongTien.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(dialogHoaDon, 
                "Vui lòng nhập đầy đủ thông tin!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate number format
        double tongTien;
        try {
            tongTien = Double.parseDouble(txtTongTien.getText().trim());
            if (tongTien < 0) {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Tổng tiền không được âm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogHoaDon, 
                "Tổng tiền phải là số!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(txtNgayBan.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(dialogHoaDon, 
                "Ngày bán phải có định dạng YYYY-MM-DD!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create HoaDonDTO object
        HoaDonDTO hoaDon = new HoaDonDTO(
            txtMaHD.getText().trim(),
            txtMaNV.getText().trim(),
            txtMaKH.getText().trim(),
            txtMaKM.getText().trim(),
            txtNgayBan.getText().trim(),
            tongTien
        );
        
        boolean success;
        
        // Add or update
        if (isAdding) {
            // Check if invoice ID already exists
            if (hoaDonBLL.checkHoaDonExists(hoaDon.getStrMaHD())) {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Mã hóa đơn đã tồn tại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            success = hoaDonBLL.insertHoaDon(hoaDon);
            if (success) {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Thêm hóa đơn thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Thêm hóa đơn thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = hoaDonBLL.updateHoaDon(hoaDon);
            if (success) {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Cập nhật hóa đơn thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogHoaDon, 
                    "Cập nhật hóa đơn thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Close dialog and refresh data
        dialogHoaDon.dispose();
        loadData();
    }
    
    private void deleteHoaDon() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn hóa đơn cần xóa!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa hóa đơn này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String maHD = listHoaDon.get(selectedRow).getStrMaHD();
            boolean success = hoaDonBLL.deleteHoaDon(maHD);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Xóa hóa đơn thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                selectedRow = -1;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Xóa hóa đơn thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchHoaDon() {
        String keyword = panelFunction.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        
        String filterOption = panelFunction.cbfilter.getSelectedItem().toString();
        
        // Filter table based on selected filter option
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblHoaDon.setRowSorter(sorter);
        
        if (filterOption.equals("Tất cả")) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        } else {
            int column = -1;
            switch (filterOption) {
                case "Mã HD":
                    column = 0;
                    break;
                case "Mã NV":
                    column = 1;
                    break;
                case "Mã KH":
                    column = 2;
                    break;
                case "Ngày bán":
                    column = 4;
                    break;
            }
            
            if (column != -1) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, column));
            }
        }
    }
    
    private void showHoaDonDetails() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn hóa đơn để xem chi tiết!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Lấy dữ liệu của hóa đơn đã chọn
            HoaDonDTO hoaDon = listHoaDon.get(selectedRow);
            
            // Mở form chi tiết hóa đơn
            ChiTietHoaDonView chiTietView = new ChiTietHoaDonView(hoaDon);
            chiTietView.setVisible(true);
            
            // Làm mới dữ liệu sau khi đóng form chi tiết
            loadData();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi hiển thị chi tiết hóa đơn: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetData() {
        // Làm mới dữ liệu
        loadData();
        
        // Xóa bộ lọc nếu đang áp dụng
        if (tblHoaDon.getRowSorter() != null) {
            tblHoaDon.setRowSorter(null);
        }
        
        // Reset các trường tìm kiếm
        panelFunction.fieldSearch.setText("Tìm kiếm...");
        panelFunction.cbfilter.setSelectedIndex(0);
        
        // Xóa dòng đang chọn
        selectedRow = -1;
        tblHoaDon.clearSelection();
        
        JOptionPane.showMessageDialog(this, 
            "Đã làm mới dữ liệu thành công!", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}