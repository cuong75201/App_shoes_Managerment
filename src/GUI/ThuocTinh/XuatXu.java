package GUI.ThuocTinh;
import BLL.XuatXuBLL;
import DTO.XuatXuDTO;
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

public class XuatXu extends JPanel {
    private ArrayList<XuatXuDTO> list_XuatXu;
    private XuatXuBLL xx;
    private JTable tblxuatxu;
    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private JScrollPane scrollPane;
    private JDialog dialogXuatXu;
    private boolean isAdding = false;
    private customTextField txtMaxuatxu, txtTennuoc;
    private CustomButton btnLuu, btnHuy;
    private int width = 1116, height = 800;
    public XuatXu(){
        list_XuatXu=new ArrayList<>();
        xx=new XuatXuBLL();
        list_XuatXu=xx.getList_XuatXu();
        initComponent();
        addEvents();
    }
    
    private void initComponent() {
        pnButton=new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã xuất xứ");
        cbfilter.addItem("Tên nước");
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã xuất xứ", "Tên nước"}, 0);
        for (var temp : list_XuatXu){
            Object[] rowData = {
                temp.getStrMaxuatxu(),
                temp.getStrTennuoc(),
                
            };
            model.addRow(rowData);
    }
        tblxuatxu=new CustomTable(model);
         tblxuatxu.getTableHeader().setReorderingAllowed(false);
         tblxuatxu.setFont(new Font("Sans-serif", Font.PLAIN, 14));
       tblxuatxu.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane( tblxuatxu);
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
                showXuatXuDialog(null);
            }
        });
        pnButton.btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=-1;
                selectedRow= tblxuatxu.getSelectedRow();
                if (selectedRow != -1) {
                    isAdding = false;
                    showXuatXuDialog(list_XuatXu.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn xuất xứ cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pnButton.btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteXuatXu();
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
                 tblxuatxu.setRowSorter(null);
            }
        });
    }
    
    private void deleteXuatXu(){
        int selectedRow=-1;
        selectedRow= tblxuatxu.getSelectedRow();    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn xuất xứ cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa xuất xứ này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {            
            boolean success = xx.deleteXuatXu(list_XuatXu.get(selectedRow).getStrMaxuatxu());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa xuất xứ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa xuất xứ thất bại!",
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
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel)  tblxuatxu.getModel());
         tblxuatxu.setRowSorter(sorter);
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
    
        private void showXuatXuDialog(XuatXuDTO th) {
        dialogXuatXu = new JDialog();
        dialogXuatXu.setTitle(isAdding ? "Thêm xuất xứ" : "Sửa xuất xứ");
         dialogXuatXu.setSize(400, 300);
         dialogXuatXu.setLayout(null);
       dialogXuatXu.setLocationRelativeTo(null);
         dialogXuatXu.setModal(true);
         dialogXuatXu.setResizable(false);

        // Create components
        int y = 20;
        int height = 35;
        int gap = 50;

        JLabel lblMaxuatxu = new JLabel("Mã xuất xứ:");
          lblMaxuatxu.setBounds(20, y, 100, height);
        dialogXuatXu.add( lblMaxuatxu);

        txtMaxuatxu = new customTextField();
        txtMaxuatxu.setBounds(130, y, 230, height);
        txtMaxuatxu.setBorderColor(Color.decode("#E1E1E1"));
        dialogXuatXu.add(txtMaxuatxu);

        y += gap;
        JLabel lblTennuoc = new JLabel("Tên nước:");
        lblTennuoc.setBounds(20, y, 100, height);
        dialogXuatXu.add(lblTennuoc);

        txtTennuoc = new customTextField();
        txtTennuoc.setBounds(130, y, 230, height);
        txtTennuoc.setBorderColor(Color.decode("#E1E1E1"));
        dialogXuatXu.add(txtTennuoc);

     
        // Buttons
        btnLuu = new CustomButton("Lưu");
        btnLuu.setBounds(100, 220, 80, 30);
        btnLuu.setBackground(Color.decode("#2ECC71"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderColor(btnLuu.getBackground());
        dialogXuatXu.add(btnLuu);

        btnHuy = new CustomButton("Hủy bỏ");
        btnHuy.setBounds(220, 220, 80, 30);
        btnHuy.setBackground(Color.decode("#E74C3C"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setBorderColor(btnHuy.getBackground());
        dialogXuatXu.add(btnHuy);

        // Fill data if editing
        if (!isAdding && xx != null) {
            txtMaxuatxu.setText(th.getStrMaxuatxu());
            txtMaxuatxu.setEditable(false);
            txtTennuoc.setText(th.getStrTennuoc());
        }
        // Button events
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveXuatXu();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogXuatXu.dispose();
            }
        });
         dialogXuatXu.setVisible(true);
        }
  
    private void saveXuatXu() {
        if (txtMaxuatxu.getText().trim().isEmpty() ||
                txtTennuoc.getText().trim().isEmpty() ){
    //            txtDiachi.getText().trim().isEmpty() ||
      //          txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogXuatXu,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
  
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email format
    

        // Create NhaCungCapDTO object
        XuatXuDTO temp = new XuatXuDTO(
                txtMaxuatxu.getText().trim(),
                txtTennuoc.getText().trim()
        );
        boolean success;
        // Add or update
        if (isAdding) {
            // Check if supplier ID already exists
            for(var tmp : xx.getList_XuatXu())
                if(tmp.getStrMaxuatxu().equals(temp.getStrMaxuatxu())){
                    JOptionPane.showMessageDialog(dialogXuatXu,
                        "Mã xuất xứ  đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
                }
            success = xx.addXuatXu(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogXuatXu,
                        "Thêm xuất xứ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogXuatXu,
                        "Thêm xuất xứ  thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            success = xx.UpdateXuatXu(temp);
            if (success) {
                JOptionPane.showMessageDialog(dialogXuatXu,
                        "Cập nhật xuất xứ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialogXuatXu,
                        "Cập nhật xuất xứ thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        dialogXuatXu.dispose();
        loadData();
    }
        
    private void loadData(){
        DefaultTableModel model = (DefaultTableModel) tblxuatxu.getModel();
        model.setRowCount(0);
        list_XuatXu= xx.getList_XuatXu();
        for (var temp : list_XuatXu) {
            Object[] row = {
                    temp.getStrMaxuatxu(),
                    temp.getStrTennuoc()
            };
            model.addRow(row);
        }
        tblxuatxu.setRowSorter(null);
    }
}

/**
 *
 * @author ADMIN
 */



