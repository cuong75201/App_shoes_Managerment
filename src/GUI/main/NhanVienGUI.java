package GUI.main;

import DTO.NhanVienDTO;
import BLL.NhanVienBLL;
import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.PanelFunction;
import GUI.component.CustomTable;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class NhanVienGUI extends JPanel {
    private ArrayList<NhanVienDTO> list_nv;
    private NhanVienBLL nvBLL;
    private CustomTable tblNhanVien;
    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private JScrollPane scrollPane;
    private JDialog dialogNhanVien;
    private boolean isAdding = false;
    private customTextField txtMaNV, txtHo, txtTen, txtGioiTinh, txtDiaChi, txtDienThoai, txtEmail, txtLuong, txtChucVu;
    private CustomButton btnLuu, btnHuy;
    private int width = 1116, height = 800;

    public NhanVienGUI() {
        list_nv = new ArrayList<>();
        nvBLL = new NhanVienBLL();
        list_nv = nvBLL.getListNhanVien();
        initComponent();
        addEvents();
    }

    private void initComponent() {
        pnButton = new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã NV");
        cbfilter.addItem("Tên");
        cbfilter.addItem("Điện thoại");
        cbfilter.addItem("Email");

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã NV", "Họ", "Tên", "Giới tính", "Địa chỉ", "Điện thoại", "Email", "Lương", "Chức vụ"}, 0
        );
        for (var temp : list_nv) {
            Object[] rowData = {
                temp.getstrMaNV(),
                temp.getStrHo(),
                temp.getStrTen(),
                temp.getStrGioiTinh(),
                temp.getStrDiaChi(),
                temp.getiDienThoai(), // DienThoai là String
                temp.getStrEmail(),
                temp.getiLuong(),
                temp.getStrChucVu()
            };
            model.addRow(rowData);
        }

        tblNhanVien = new CustomTable(model);
        tblNhanVien.getTableHeader().setReorderingAllowed(false);
        tblNhanVien.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblNhanVien.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));

        scrollPane = new JScrollPane(tblNhanVien);
        scrollPane.setBounds(20, 150, width - 50, 600);

        pnButton.setCbfilter(cbfilter);
        pnButton.setBtnReset();
        this.setLayout(null);
        this.add(pnButton);
        this.add(scrollPane);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
    }

    private void addEvents() {
        pnButton.btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isAdding = true;
                showNhanVienDialog(null);
            }
        });

        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblNhanVien.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showNhanVienDialog(list_nv.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNhanVien();
            }
        });

        pnButton.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchNhanVien();
            }
        });

        pnButton.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                pnButton.fieldSearch.setText("");
                cbfilter.setSelectedIndex(0);
                tblNhanVien.setRowSorter(null);
            }
        });
    }

    private void deleteNhanVien() {
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = nvBLL.deleteNhanVien(list_nv.get(selectedRow).getstrMaNV());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa nhân viên thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa nhân viên thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchNhanVien() {
        String keyword = pnButton.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        String filterOption = cbfilter.getSelectedItem().toString();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tblNhanVien.getModel());
        tblNhanVien.setRowSorter(sorter);
        int column = -1;
        switch (filterOption) {
            case "Mã NV":
                column = 0;
                break;
            case "Tên":
                column = 2;
                break;
            case "Điện thoại":
                column = 5;
                break;
            case "Email":
                column = 6;
                break;
        }
        if (column != -1) {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + keyword, column));
        }
    }

    private void showNhanVienDialog(NhanVienDTO nv) {
        dialogNhanVien = new JDialog();
        dialogNhanVien.setTitle(isAdding ? "Thêm nhân viên" : "Sửa nhân viên");
        dialogNhanVien.setSize(400, 550);
        dialogNhanVien.setLayout(null);
        dialogNhanVien.setLocationRelativeTo(null);
        dialogNhanVien.setModal(true);
        dialogNhanVien.setResizable(false);

        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;

        JLabel lblMaNV = new JLabel("Mã NV:");
        lblMaNV.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblMaNV);

        txtMaNV = new customTextField();
        txtMaNV.setBounds(130, y, 230, height);
        txtMaNV.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtMaNV);

        y += gap;
        JLabel lblHo = new JLabel("Họ:");
        lblHo.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblHo);

        txtHo = new customTextField();
        txtHo.setBounds(130, y, 230, height);
        txtHo.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtHo);

        y += gap;
        JLabel lblTen = new JLabel("Tên:");
        lblTen.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblTen);

        txtTen = new customTextField();
        txtTen.setBounds(130, y, 230, height);
        txtTen.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtTen);

        y += gap;
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblGioiTinh);

        txtGioiTinh = new customTextField();
        txtGioiTinh.setBounds(130, y, 230, height);
        txtGioiTinh.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtGioiTinh);

        y += gap;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblDiaChi);

        txtDiaChi = new customTextField();
        txtDiaChi.setBounds(130, y, 230, height);
        txtDiaChi.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtDiaChi);

        y += gap;
        JLabel lblDienThoai = new JLabel("Điện thoại:");
        lblDienThoai.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblDienThoai);

        txtDienThoai = new customTextField();
        txtDienThoai.setBounds(130, y, 230, height);
        txtDienThoai.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtDienThoai);

        y += gap;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblEmail);

        txtEmail = new customTextField();
        txtEmail.setBounds(130, y, 230, height);
        txtEmail.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtEmail);

        y += gap;
        JLabel lblLuong = new JLabel("Lương:");
        lblLuong.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblLuong);

        txtLuong = new customTextField();
        txtLuong.setBounds(130, y, 230, height);
        txtLuong.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtLuong);

        y += gap;
        JLabel lblChucVu = new JLabel("Chức vụ:");
        lblChucVu.setBounds(20, y, 100, height);
        dialogNhanVien.add(lblChucVu);

        txtChucVu = new customTextField();
        txtChucVu.setBounds(130, y, 230, height);
        txtChucVu.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhanVien.add(txtChucVu);

        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, y + 50, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogNhanVien.add(btnLuu);

        btnHuy = new CustomButton("Hủy bỏ");
        btnHuy.setBounds(220, y + 50, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogNhanVien.add(btnHuy);

        // Fill data if editing
        if (!isAdding && nv != null) {
            txtMaNV.setText(nv.getstrMaNV());
            txtMaNV.setEditable(false);
            txtHo.setText(nv.getStrHo());
            txtTen.setText(nv.getStrTen());
            txtGioiTinh.setText(nv.getStrGioiTinh());
            txtDiaChi.setText(nv.getStrDiaChi());
            txtDienThoai.setText(nv.getiDienThoai()); // iDienThoai là String
            txtEmail.setText(nv.getStrEmail());
            txtLuong.setText(String.valueOf(nv.getiLuong()));
            txtChucVu.setText(nv.getStrChucVu());
        }

        // Button events
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNhanVien();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogNhanVien.dispose();
            }
        });

        dialogNhanVien.setVisible(true);
    }

    private void saveNhanVien() {
        // Validate input
        if (txtMaNV.getText().trim().isEmpty() ||
            txtHo.getText().trim().isEmpty() ||
            txtTen.getText().trim().isEmpty() ||
            txtGioiTinh.getText().trim().isEmpty() ||
            txtDiaChi.getText().trim().isEmpty() ||
            txtDienThoai.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtLuong.getText().trim().isEmpty() ||
            txtChucVu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogNhanVien,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(dialogNhanVien,
                    "Email không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number
        String dienThoai = txtDienThoai.getText().trim();
        if (!dienThoai.matches("\\d+")) {
            JOptionPane.showMessageDialog(dialogNhanVien,
                    "Số điện thoại phải là số!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate salary
        String luongStr = txtLuong.getText().trim();
        if (!luongStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(dialogNhanVien,
                    "Lương phải là số!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create NhanVienDTO object
        try {
            NhanVienDTO temp = new NhanVienDTO(
                txtMaNV.getText().trim(),
                txtHo.getText().trim(),
                txtTen.getText().trim(),
                txtGioiTinh.getText().trim(),
                txtDiaChi.getText().trim(),
                txtEmail.getText().trim(),
                txtChucVu.getText().trim(),
                dienThoai, // iDienThoai là String
                Integer.parseInt(luongStr),
                "" // Anh field, set empty as it's not used in dialog
            );

            boolean success;
            // Add or update
            if (isAdding) {
                // Check if MaNV already exists
                for (var tmp : nvBLL.getListNhanVien()) {
                    if (tmp.getstrMaNV().equals(temp.getstrMaNV())) {
                        JOptionPane.showMessageDialog(dialogNhanVien,
                                "Mã nhân viên đã tồn tại!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                success = nvBLL.addNhanVien(temp);
                if (success) {
                    JOptionPane.showMessageDialog(dialogNhanVien,
                            "Thêm nhân viên thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialogNhanVien,
                            "Thêm nhân viên thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                success = nvBLL.updateNhanVien(temp);
                if (success) {
                    JOptionPane.showMessageDialog(dialogNhanVien,
                            "Cập nhật nhân viên thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialogNhanVien,
                            "Cập nhật nhân viên thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            dialogNhanVien.dispose();
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogNhanVien,
                    "Lương không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        list_nv = nvBLL.getListNhanVien();
        for (var temp : list_nv) {
            Object[] row = {
                temp.getstrMaNV(),
                temp.getStrHo(),
                temp.getStrTen(),
                temp.getStrGioiTinh(),
                temp.getStrDiaChi(),
                temp.getiDienThoai(), // iDienThoai là String
                temp.getStrEmail(),
                temp.getiLuong(),
                temp.getStrChucVu()
            };
            model.addRow(row);
        }
        tblNhanVien.setRowSorter(null);
    }
}
