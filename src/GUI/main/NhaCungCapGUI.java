package GUI.main;
import DTO.NhaCungCapDTO;
import BLL.NhaCungCapBLL;
import DTO.SanPhamDTO;
import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.PanelFunction;
import GUI.component.CustomComboBox;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
public class NhaCungCapGUI extends JPanel {
    private ArrayList<NhaCungCapDTO> list_ncc;
    private NhaCungCapBLL ncc;
    private JTable tblnhacungcap;
    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private JScrollPane scrollPane;
    private JDialog dialogNhaCungCap;
    private boolean isAdding = false;
    private customTextField txtMaNCC, txtTenNCC, txtDiaChi, txtEmail;
    private CustomButton btnLuu, btnHuy;
    private int width = 1116, height = 800;
    public NhaCungCapGUI(){
        list_ncc=new ArrayList<>();
        ncc=new NhaCungCapBLL();
        list_ncc=ncc.getListNhaCungCap();
        initComponent();
        addEvents();
    }
    
    private void initComponent() {
        pnButton=new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã NCC");
        cbfilter.addItem("Tên nhà cung cấp");
        cbfilter.addItem("Địa chỉ");
        cbfilter.addItem("Email");
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Email"}, 0);
        for (var temp : list_ncc){
            Object[] rowData = {
                temp.getStrMaNCC(),
                temp.getStrTenNCC(),
                temp.getStrDiaChi(),
                temp.getStrEmail(),
            };
            model.addRow(rowData);
    }
        tblnhacungcap=new CustomTable(model);
        tblnhacungcap.getTableHeader().setReorderingAllowed(false);
        tblnhacungcap.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblnhacungcap.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane(tblnhacungcap);
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
                showNhaCungCapDialog(null);
            }
        });
        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=-1;
                selectedRow=tblnhacungcap.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showNhaCungCapDialog(list_ncc.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNhaCungCap();
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
                tblnhacungcap.setRowSorter(null);
            }
        });
    }
    
    private void deleteNhaCungCap(){
        int selectedRow=-1;
        selectedRow=tblnhacungcap.getSelectedRow();    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhà cung cấp cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhà cung cấp này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {            
            boolean success = ncc.deleteNhaCungCap(list_ncc.get(selectedRow).getStrMaNCC());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa nhà cung cấp thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa nhà cung cấp thất bại!",
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
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tblnhacungcap.getModel());
        tblnhacungcap.setRowSorter(sorter);
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
    
        private void showNhaCungCapDialog(NhaCungCapDTO ncc) {
        dialogNhaCungCap = new JDialog();
        dialogNhaCungCap.setTitle(isAdding ? "Thêm nhà cung cấp" : "Sửa nhà cung cấp");
        dialogNhaCungCap.setSize(400, 300);
        dialogNhaCungCap.setLayout(null);
        dialogNhaCungCap.setLocationRelativeTo(null);
        dialogNhaCungCap.setModal(true);
        dialogNhaCungCap.setResizable(false);

        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;

        JLabel lblMaNCC = new JLabel("Mã NCC:");
        lblMaNCC.setBounds(20, y, 100, height);
        dialogNhaCungCap.add(lblMaNCC);

        txtMaNCC = new customTextField();
        txtMaNCC.setBounds(130, y, 230, height);
        txtMaNCC.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhaCungCap.add(txtMaNCC);

        y += gap;
        JLabel lblTenNCC = new JLabel("Tên NCC:");
        lblTenNCC.setBounds(20, y, 100, height);
        dialogNhaCungCap.add(lblTenNCC);

        txtTenNCC = new customTextField();
        txtTenNCC.setBounds(130, y, 230, height);
        txtTenNCC.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhaCungCap.add(txtTenNCC);

        y += gap;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(20, y, 100, height);
        dialogNhaCungCap.add(lblDiaChi);

        txtDiaChi = new customTextField();
        txtDiaChi.setBounds(130, y, 230, height);
        txtDiaChi.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhaCungCap.add(txtDiaChi);

        y += gap;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, y, 100, height);
        dialogNhaCungCap.add(lblEmail);

        txtEmail = new customTextField();
        txtEmail.setBounds(130, y, 230, height);
        txtEmail.setBorderColor(Color.decode("#E1E1E1"));
        dialogNhaCungCap.add(txtEmail);

        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 220, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogNhaCungCap.add(btnLuu);

        btnHuy = new CustomButton("Hủy bỏ");
        btnHuy.setBounds(220, 220, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogNhaCungCap.add(btnHuy);

        // Fill data if editing
        if (!isAdding && ncc != null) {
            txtMaNCC.setText(ncc.getStrMaNCC());
            txtMaNCC.setEditable(false);
            txtTenNCC.setText(ncc.getStrTenNCC());
            txtDiaChi.setText(ncc.getStrDiaChi());
            txtEmail.setText(ncc.getStrEmail());
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
                dialogNhaCungCap.dispose();
            }
        });
        dialogNhaCungCap.setVisible(true);
        }
  
    private void saveNhaCungCap() {
        if (txtMaNCC.getText().trim().isEmpty() ||
                txtTenNCC.getText().trim().isEmpty() ||
                txtDiaChi.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogNhaCungCap,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email format
        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(dialogNhaCungCap,
                    "Email không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create NhaCungCapDTO object
        NhaCungCapDTO temp = new NhaCungCapDTO(
                txtMaNCC.getText().trim(),
                txtTenNCC.getText().trim(),
                txtDiaChi.getText().trim(),
                txtEmail.getText().trim()
        );
        boolean success;
        // Add or update
        if (isAdding) {
            // Check if supplier ID already exists
            for(var tmp : ncc.getListNhaCungCap())
                if(tmp.getStrMaNCC().equals(temp.getStrMaNCC())){
                    JOptionPane.showMessageDialog(dialogNhaCungCap,
                        "Mã nhà cung cấp đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            success = ncc.addNhaCungCap(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogNhaCungCap,
                        "Thêm nhà cung cấp thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogNhaCungCap,
                        "Thêm nhà cung cấp thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = ncc.updateNhaCungCap(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogNhaCungCap,
                        "Cập nhật nhà cung cấp thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogNhaCungCap,
                        "Cập nhật nhà cung cấp thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        dialogNhaCungCap.dispose();
        loadData();
    }
        
    private void loadData(){
        DefaultTableModel model = (DefaultTableModel) tblnhacungcap.getModel();
        model.setRowCount(0);
        list_ncc = ncc.getListNhaCungCap();
        for (var temp : list_ncc) {
            Object[] row = {
                    temp.getStrMaNCC(),
                    temp.getStrTenNCC(),
                    temp.getStrDiaChi(),
                    temp.getStrEmail()
            };
            model.addRow(row);
        }
        tblnhacungcap.setRowSorter(null);
    }
}

