package GUI.main;

import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;

import BLL.PhieuNhapBLL;
import BLL.NhaCungCapBLL;
import BLL.NhanVienBLL;
import BLL.SanPhamBLL;

import GUI.component.PanelFunction;
import GUI.component.CustomButton;
import GUI.component.CustomComboBox;
import GUI.component.CustomTable;
import GUI.component.customTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PhieuNhapGUI extends JPanel {
    private ArrayList<PhieuNhapDTO> list_pn;
    private ArrayList<SanPhamDTO> list_sp;
    private ArrayList<NhaCungCapDTO> list_ncc;
    private ArrayList<NhanVienDTO> list_nv;
    
    private PhieuNhapBLL pn;
    private SanPhamBLL sp;
    private NhaCungCapBLL ncc;
    private NhanVienBLL nv;
    
    private PanelFunction pnButton;
    private CustomComboBox cbfilter, cbsp, cbncc, cbnv;
    private customTextField txtMaPN, txtNgayNhap, txtTongTien;
    private CustomTable tblphieunhap;
    private CustomButton btnLuu, btnHuy;

    private JScrollPane scrollPane;
    private JDialog dialogphieunhap;
    
    private boolean isAdding = false;
    
    private int width = 1116, height = 800;

    public PhieuNhapGUI() {
        list_pn = new ArrayList<>();
        list_sp = new ArrayList<>();
        list_ncc = new ArrayList<>();
        list_nv = new ArrayList<>();
        pn = new PhieuNhapBLL();
        sp = new SanPhamBLL();
        ncc = new NhaCungCapBLL();
        nv = new NhanVienBLL();
        list_pn = pn.getListPhieuNhap();
        list_sp = sp.getListProduct();
        list_ncc = ncc.getListNhaCungCap();
        list_nv = nv.getListNhanVien();
        initComponent();
        addEvents();
    }
    
    private void initComponent() {
        pnButton = new PanelFunction();
        pnButton.setBtnChitiet();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã phiếu nhập");
        cbfilter.addItem("Mã nhà cung cấp");
        cbfilter.addItem("Mã nhân viên");
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Phiếu Nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày nhập", "Tổng tiền"}, 0);
        for (var temp : list_pn) {
            Object[] rowData = {
                temp.getStrMaPN(),
                temp.getStrMaNCC(),
                temp.getStrMaNV(),
                temp.getStrNgayNhap(),
                temp.getTongTien()
            };
            model.addRow(rowData);
        }
        tblphieunhap = new CustomTable(model);
        tblphieunhap.getTableHeader().setReorderingAllowed(false);
        tblphieunhap.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblphieunhap.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane(tblphieunhap);
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
        tblphieunhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblphieunhap.getSelectedRow();
                if (e.getClickCount() == 2 && selectedRow != -1) {
//                    showPhieuNhapDetails();
                }
            }
        });
        pnButton.btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdding = true;
                showPhieuNhapDialog(null);
            }
        });
        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblphieunhap.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showPhieuNhapDialog(list_pn.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePhieuNhap();
            }
        });
        pnButton.btnChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                showPhieuNhapDetails();
            }
        });
        pnButton.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                pnButton.fieldSearch.setText("");
                if (cbfilter.getItemCount() > 0) {
                    cbfilter.setSelectedIndex(0);
                }
                tblphieunhap.setRowSorter(null);
            }
        });
        pnButton.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPhieuNhap();
            }
        });
    }
    
    private void showPhieuNhapDialog(PhieuNhapDTO phieunhap) {
        dialogphieunhap = new JDialog();
        dialogphieunhap.setTitle(isAdding ? "Thêm Phiếu Nhập Mới" : "Chỉnh Sửa Phiếu Nhập");
        dialogphieunhap.setSize(400, 350);
        dialogphieunhap.setLayout(null);
        dialogphieunhap.setLocationRelativeTo(null);
        dialogphieunhap.setModal(true);
        dialogphieunhap.setResizable(false);
        
        cbsp = new CustomComboBox();
        cbncc = new CustomComboBox();
        cbnv = new CustomComboBox();
        for (var temp : list_sp)
            cbsp.addItem(temp.getStrMaGiay());
        for (var temp : list_ncc)
            cbncc.addItem(temp.getStrMaNCC());
        for (var temp : list_nv)
            cbnv.addItem(temp.getstrMaNV());
        
        int y = 20;
        int height = 35;
        int gap = 50;
        
        JLabel lblMaPN = new JLabel("Mã phiếu nhập:");
        lblMaPN.setBounds(20, y, 100, height);
        dialogphieunhap.add(lblMaPN);
        
        txtMaPN = new customTextField();
        txtMaPN.setBounds(130, y, 230, height);
        txtMaPN.setBorderColor(Color.decode("#E1E1E1"));
        dialogphieunhap.add(txtMaPN);
        
        y += gap;
        JLabel lblMaNCC = new JLabel("Mã nhà cung cấp:");
        lblMaNCC.setBounds(20, y, 100, height);
        dialogphieunhap.add(lblMaNCC);
        cbncc.setBounds(130, y, 230, height);
        dialogphieunhap.add(cbncc);
        
        y += gap;
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, y, 100, height);
        dialogphieunhap.add(lblMaNV);
        cbnv.setBounds(130, y, 230, height);
        dialogphieunhap.add(cbnv);
        
        y += gap;
        JLabel lblNgayNhap = new JLabel("Ngày nhập:");
        lblNgayNhap.setBounds(20, y, 100, height);
        dialogphieunhap.add(lblNgayNhap);
        
        txtNgayNhap = new customTextField();
        txtNgayNhap.setBounds(130, y, 230, height);
        txtNgayNhap.setBorderColor(Color.decode("#E1E1E1"));
        dialogphieunhap.add(txtNgayNhap);
        
        y += gap;
        JLabel lblTongTien = new JLabel("Tổng tiền:");
        lblTongTien.setBounds(20, y, 100, height);
        dialogphieunhap.add(lblTongTien);
        
        txtTongTien = new customTextField();
        txtTongTien.setBounds(130, y, 230, height);
        txtTongTien.setBorderColor(Color.decode("#E1E1E1"));
        dialogphieunhap.add(txtTongTien);
        
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 270, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogphieunhap.add(btnLuu);
        
        btnHuy = new CustomButton("Hủy");
        btnHuy.setBounds(220, 270, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogphieunhap.add(btnHuy);
        
        if (!isAdding && phieunhap != null) {
            txtMaPN.setText(phieunhap.getStrMaPN());
            txtMaPN.setEditable(false);
            cbncc.setSelectedItem(phieunhap.getStrMaNCC());
            cbnv.setSelectedItem(phieunhap.getStrMaNV());
            txtNgayNhap.setText(phieunhap.getStrNgayNhap());
            txtTongTien.setText(String.valueOf(phieunhap.getTongTien()));
        }
        
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePhieuNhap();
            }
        });
        
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogphieunhap.dispose();
            }
        });
        
        dialogphieunhap.setVisible(true);
    }
    
    private void savePhieuNhap() {
        if (txtMaPN.getText().trim().isEmpty() ||
            cbncc.getSelectedItem() == null ||
            cbnv.getSelectedItem() == null ||
            txtNgayNhap.getText().trim().isEmpty() ||
            txtTongTien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogphieunhap,
                "Vui lòng điền đầy đủ tất cả các trường thông tin!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (list_ncc.isEmpty() || list_nv.isEmpty()) {
            JOptionPane.showMessageDialog(dialogphieunhap,
                "Danh sách nhà cung cấp hoặc nhân viên rỗng!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double tongTien;
        try {
            tongTien = Double.parseDouble(txtTongTien.getText().trim());
            if (tongTien < 0) {
                JOptionPane.showMessageDialog(dialogphieunhap,
                    "Tổng tiền phải là số không âm!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogphieunhap,
                "Tổng tiền phải là một số hợp lệ!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(txtNgayNhap.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(dialogphieunhap,
                "Ngày nhập phải đúng định dạng YYYY-MM-DD!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        PhieuNhapDTO temp = new PhieuNhapDTO(
            txtMaPN.getText().trim(),
            cbncc.getSelectedItem().toString(),
            cbnv.getSelectedItem().toString(),
            txtNgayNhap.getText().trim(),
            tongTien
        );
        
        boolean success;
        if (isAdding) {
            for (var tmp : pn.getListPhieuNhap())
                if (tmp.getStrMaPN().equals(temp.getStrMaPN())) {
                    JOptionPane.showMessageDialog(dialogphieunhap,
                        "Mã phiếu nhập này đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            success = pn.addPhieuNhap(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogphieunhap,
                    "Thêm phiếu nhập thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogphieunhap,
                    "Thêm phiếu nhập không thành công!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = pn.updatePhieuNhap(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogphieunhap,
                    "Cập nhật phiếu nhập thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogphieunhap,
                    "Cập nhật phiếu nhập không thành công!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        dialogphieunhap.dispose();
        loadData();
    }
    
    private void deletePhieuNhap() {
        int selectedRow = tblphieunhap.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu nhập để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa phiếu nhập này không?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maPN = list_pn.get(selectedRow).getStrMaPN();
            boolean success = pn.deletePhieuNhap(maPN);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Xóa phiếu nhập thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Xóa phiếu nhập không thành công!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchPhieuNhap() {
        String keyword = pnButton.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        String filterOption = cbfilter.getSelectedItem().toString();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tblphieunhap.getModel());
        tblphieunhap.setRowSorter(sorter);
        int column = -1;
        switch (filterOption) {
            case "Mã phiếu nhập":
                column = 0;
                break;
            case "Mã nhà cung cấp":
                column = 1;
                break;
            case "Mã nhân viên":
                column = 2;
                break;
        }
        if (column != -1) {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + keyword, column));
        }
    }

    private void showPhieuNhapDetails() {
        int selectedRow = tblphieunhap.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu nhập để xem chi tiết!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            PhieuNhapDTO temp = list_pn.get(selectedRow);
            ChiTietPhieuNhapView tmp = new ChiTietPhieuNhapView(temp);
            tmp.setVisible(true);
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Không thể hiển thị chi tiết phiếu nhập: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblphieunhap.getModel();
        model.setRowCount(0);
        list_pn = pn.getListPhieuNhap();
        for (var temp : list_pn) {
            Object[] row = {
                temp.getStrMaPN(),
                temp.getStrMaNCC(),
                temp.getStrMaNV(),
                temp.getStrNgayNhap(),
                temp.getTongTien()
            };
            model.addRow(row);
        }
        tblphieunhap.setRowSorter(null);
    }
}
