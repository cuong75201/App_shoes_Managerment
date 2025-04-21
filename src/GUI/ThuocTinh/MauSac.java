/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThuocTinh;
import BLL.MauSacBLL;
import DTO.MauSacDTO;
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
/**
 *
 * @author ADMIN
 */public class MauSac extends JPanel {
    private ArrayList<MauSacDTO> list_ms;
    private MauSacBLL ms;
    private JTable tblmausac;
    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private JScrollPane scrollPane;
    private JDialog dialogMauSac;
    private boolean isAdding = false;
    private customTextField txtMamau, txtTenmau;
    private CustomButton btnLuu, btnHuy;
    private int width = 1116, height = 800;
    public MauSac(){
        list_ms=new ArrayList<>();
        ms=new MauSacBLL();
        list_ms=ms.getListMauSac();
        initComponent();
        addEvents();
    }
    
    private void initComponent() {
        pnButton=new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã màu");
        cbfilter.addItem("Tên màu");
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã màu", "Tên màu"}, 0);
        for (var temp : list_ms){
            Object[] rowData = {
                temp.getStrMamau(),
                temp.getStrTenmau(),
            };
            model.addRow(rowData);
    }
        tblmausac=new CustomTable(model);
        tblmausac.getTableHeader().setReorderingAllowed(false);
        tblmausac.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblmausac.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane(tblmausac);
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
                showMauSacDialog(null);
            }
        });
        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=-1;
                selectedRow=tblmausac.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showMauSacDialog(list_ms.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn màu sắc cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMauSac();
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
                tblmausac.setRowSorter(null);
            }
        });
    }
    
    private void deleteMauSac(){
        int selectedRow=-1;
        selectedRow=tblmausac.getSelectedRow();    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn màu sắc cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa màu sắc này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {            
            boolean success = ms.deleteMauSac(ms.get(selectedRow).getStrMamau());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa màu sắc thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa màu sắc thất bại!",
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
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tblmausac.getModel());
        tblmausac.setRowSorter(sorter);
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
    
        private void showMauSacDialog(MauSacDTO ms) {
        dialogMauSac= new JDialog();
     dialogMauSac.setTitle(isAdding ? "Thêm màu sắc" : "Sửa màu sắc");
       dialogMauSac.setSize(400, 300);
       dialogMauSac.setLayout(null);
      dialogMauSac.setLocationRelativeTo(null);
        dialogMauSac.setModal(true);
         dialogMauSac.setResizable(false);

        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;

        JLabel lblMamau = new JLabel("Mã màu sắc:");
        lblMamau.setBounds(20, y, 100, height);
        dialogMauSac.add(lblMamau);

        txtMamau= new customTextField();
        txtMamau.setBounds(130, y, 230, height);
        txtMamau.setBorderColor(Color.decode("#E1E1E1"));
        dialogMauSac.add(txtMamau);

        y += gap;
        JLabel lblTenmau = new JLabel("Tên màu:");
        lblTenmau.setBounds(20, y, 100, height);
        dialogMauSac.add(lblTenmau);

        txtTenmau = new customTextField();
        txtTenmau.setBounds(130, y, 230, height);
        txtTenmau.setBorderColor(Color.decode("#E1E1E1"));
        dialogMauSac.add(txtTenmau);

    

        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 220, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogMauSac.add(btnLuu);

        btnHuy = new CustomButton("Hủy bỏ");
        btnHuy.setBounds(220, 220, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogMauSac.add(btnHuy);

        // Fill data if editing
        if (!isAdding && ms != null) {
            txtMamau.setText(ms.getStrMamau());
            txtMamau.setEditable(false);
            txtTenmau.setText(ms.getStrTenmau());
        }
        // Button events
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMauSac();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogMauSac.dispose();
            }
        });
         dialogMauSac.setVisible(true);
        }
  
    private void saveMauSac() {
        if (txtMamau.getText().trim().isEmpty() ||
                txtTenmau.getText().trim().isEmpty() ||
            JOptionPane.showMessageDialog(dialogMauSac,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
      

        // Create NhaCungCapDTO object
        MauSacDTO temp = new MauSacDTO(
                txtMamau.getText().trim(),
                txtTenmau.getText().trim()
        );
        boolean success;
        // Add or update
        if (isAdding) {
            // Check if supplier ID already exists
            for(var tmp : ms.getList_ThuongHieu())
                if(tmp.getStrMathuonghieu().equals(temp.getStrMamau())){
                    JOptionPane.showMessageDialog(dialogMauSac,
                        "Mã màu sắc đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            success = ms.addMauSac(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogMauSac,
                        "Thêm màu sắc thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogMauSac,
                        "Thêm màu sắc thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = ms.UpdateMauSac(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogMauSac,
                        "Cập nhật màu sắc thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogMauSac,
                        "Cập nhật màu sắc thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        dialogMauSac.dispose();
        loadData();
    }
        
    private void loadData(){
        DefaultTableModel model = (DefaultTableModel) tblmausac.getModel();
        model.setRowCount(0);
        list_ms = ms.getListMauSac();
        for (var temp : list_ms) {
            Object[] row = {
                    temp.getStrMamau(),
                    temp.getStrTenmau()
            };
            model.addRow(row);
        }
        tblmausac.setRowSorter(null);
    }
}



