/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThuocTinh;
import BLL.ThuongHieuBLL;
import DTO.ThuongHieuDTO;
import GUI.component.CustomButton;
import GUI.component.CustomComboBox;
import GUI.component.CustomTable;
import GUI.component.PanelFunction;
import GUI.component.customTextField;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ThuongHieu extends JPanel {
    private ArrayList<ThuongHieuDTO> list_ThuongHieu;
    private ThuongHieuBLL th;
    private JTable tblthuonghieu;
    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private JScrollPane scrollPane;
    private JDialog dialogThuongHieu;
    private boolean isAdding = false;
    private customTextField txtMathuonghieu, txtTenthuonghieu, txtDiachi, txtEmail;
    private CustomButton btnLuu, btnHuy;
    private int width = 1116, height = 800;
    public ThuongHieu(){
        list_ThuongHieu=new ArrayList<>();
        th=new ThuongHieuBLL();
        list_ThuongHieu=th.getList_ThuongHieu();
        initComponent();
        addEvents();
    }
    
    private void initComponent() {
        pnButton=new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã thương hiệu");
        cbfilter.addItem("Tên thương hiệu");
        cbfilter.addItem("Địa chỉ");
        cbfilter.addItem("Email");
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã thương hiệu", "Tên thương hiệu", "Địa chỉ", "Email"}, 0);
        for (var temp : list_ThuongHieu){
            Object[] rowData = {
                temp.getStrMathuonghieu(),
                temp.getStrTenthuonghieu(),
                temp.getStrDiachi(),
                temp.getStrEmail(),
            };
            model.addRow(rowData);
    }
        tblthuonghieu=new CustomTable(model);
        tblthuonghieu.getTableHeader().setReorderingAllowed(false);
        tblthuonghieu.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblthuonghieu.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane(tblthuonghieu);
        scrollPane.setBounds(20, 150, width - 50, 600);
        pnButton.setCbfilter(cbfilter);
        pnButton.setBtnReset();
        this.setLayout(null);
        this.add(pnButton);
        this.add(scrollPane);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
    }
    
    private void addEvents(){
        pnButton.btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isAdding = true;
                showThuongHieuDialog(null);
            }
        });
        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=-1;
                selectedRow=tblthuonghieu.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showThuongHieuDialog(list_ThuongHieu.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn thương hiệu cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteThuongHieu();
            }
        });
        pnButton.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchNhaCungCap();
            }
        });
        pnButton.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                pnButton.fieldSearch.setText("");
                cbfilter.setSelectedIndex(0);
                tblthuonghieu.setRowSorter(null);
            }
        });
    }
    
    private void deleteThuongHieu(){
        int selectedRow=-1;
        selectedRow=tblthuonghieu.getSelectedRow();    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn thương hiệu cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa thương hiệu này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {            
            boolean success = th.deleteThuongHieu(list_ThuongHieu.get(selectedRow).getStrMathuonghieu());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa thương hiệu thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa thương hiệu thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchNhaCungCap(){
        String keyword = pnButton.fieldSearch.getText().trim();
        if (keyword.equals("") || keyword.equals("Tìm kiếm...")) {
            loadData();
            return;
        }
        String filterOption = cbfilter.getSelectedItem().toString();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tblthuonghieu.getModel());
        tblthuonghieu.setRowSorter(sorter);
        int column = -1;
        switch (filterOption) {
            case "Mã NCC":
                column = 0;
                break;
            case "Tên nhà cung cấp":
                column = 1;
                break;
            case "Địa chỉ":
                column = 2;
                break;
            case "Email":
                column = 3;
                break;
        }
        if (column != -1) {
                sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + keyword, column));
        }
    }
    
        private void showThuongHieuDialog(ThuongHieuDTO th) {
        dialogThuongHieu = new JDialog();
       dialogThuongHieu.setTitle(isAdding ? "Thêm thương hiệu" : "Sửa thương hiệu");
        dialogThuongHieu.setSize(400, 300);
        dialogThuongHieu.setLayout(null);
       dialogThuongHieu.setLocationRelativeTo(null);
        dialogThuongHieu.setModal(true);
        dialogThuongHieu.setResizable(false);

        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;

        JLabel lblMathuonghieu = new JLabel("Mã thương hiệu:");
        lblMathuonghieu.setBounds(20, y, 100, height);
        dialogThuongHieu.add(lblMathuonghieu);

        txtMathuonghieu = new customTextField();
        txtMathuonghieu.setBounds(130, y, 230, height);
        txtMathuonghieu.setBorderColor(Color.decode("#E1E1E1"));
        dialogThuongHieu.add(txtMathuonghieu);

        y += gap;
        JLabel lblTenthuonghieu = new JLabel("Tên thương hiệu:");
        lblTenthuonghieu.setBounds(20, y, 100, height);
        dialogThuongHieu.add(lblTenthuonghieu);

        txtTenthuonghieu = new customTextField();
        txtTenthuonghieu.setBounds(130, y, 230, height);
        txtTenthuonghieu.setBorderColor(Color.decode("#E1E1E1"));
        dialogThuongHieu.add(txtTenthuonghieu);

        y += gap;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(20, y, 100, height);
        dialogThuongHieu.add(lblDiaChi);

        txtDiachi = new customTextField();
        txtDiachi.setBounds(130, y, 230, height);
        txtDiachi.setBorderColor(Color.decode("#E1E1E1"));
        dialogThuongHieu.add(txtDiachi);

        y += gap;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, y, 100, height);
        dialogThuongHieu.add(lblEmail);

        txtEmail = new customTextField();
        txtEmail.setBounds(130, y, 230, height);
        txtEmail.setBorderColor(Color.decode("#E1E1E1"));
        dialogThuongHieu.add(txtEmail);

        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 220, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogThuongHieu.add(btnLuu);

        btnHuy = new CustomButton("Hủy bỏ");
        btnHuy.setBounds(220, 220, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogThuongHieu.add(btnHuy);

        // Fill data if editing
        if (!isAdding && th != null) {
            txtMathuonghieu.setText(th.getStrMathuonghieu());
            txtMathuonghieu.setEditable(false);
            txtTenthuonghieu.setText(th.getStrTenthuonghieu());
            txtDiachi.setText(th.getStrDiachi());
            txtEmail.setText(th.getStrEmail());
        }
        // Button events
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNhaCungCap();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogThuongHieu.dispose();
            }
        });
         dialogThuongHieu.setVisible(true);
        }
  
    private void saveNhaCungCap() {
        if (txtMathuonghieu.getText().trim().isEmpty() ||
                txtTenthuonghieu.getText().trim().isEmpty() ||
                txtDiachi.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogThuongHieu,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email format
        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(dialogThuongHieu,
                    "Email không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create NhaCungCapDTO object
        ThuongHieuDTO temp = new ThuongHieuDTO(
                txtMathuonghieu.getText().trim(),
                txtTenthuonghieu.getText().trim(),
                txtDiachi.getText().trim(),
                txtEmail.getText().trim()
        );
        boolean success;
        // Add or update
        if (isAdding) {
            // Check if supplier ID already exists
            for(var tmp : th.getList_ThuongHieu())
                if(tmp.getStrMathuonghieu().equals(temp.getStrMathuonghieu())){
                    JOptionPane.showMessageDialog(dialogThuongHieu,
                        "Mã thương hiệu đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            success = th.addThuongHieu(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogThuongHieu,
                        "Thêm thương hiệu thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogThuongHieu,
                        "Thêm thương hiệu thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = th.UpdateThuongHieu(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogThuongHieu,
                        "Cập nhật thương hiệu thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogThuongHieu,
                        "Cập nhật thương hiệu thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        dialogThuongHieu.dispose();
        loadData();
    }
        
    private void loadData(){
        DefaultTableModel model = (DefaultTableModel) tblthuonghieu.getModel();
        model.setRowCount(0);
        list_ThuongHieu= th.getList_ThuongHieu();
        for (var temp : list_ThuongHieu) {
            Object[] row = {
                    temp.getStrMathuonghieu(),
                    temp.getStrTenthuonghieu(),
                    temp.getStrDiachi(),
                    temp.getStrEmail()
            };
            model.addRow(row);
        }
        tblthuonghieu.setRowSorter(null);
    }
}
/**
 *
 * @author ADMIN
 */


