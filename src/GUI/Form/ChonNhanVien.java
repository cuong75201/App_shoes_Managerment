/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.Form.ThemTaiKhoan;

import BLL.TaiKhoanBLL;
import BLL.NhanVienBLL;

import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class ChonNhanVien extends JFrame {

    private int width = 1000, height = 600;
    private JPanel PanelSerch;
    private JScrollPane scrollPane;
    private customTextField fieldSearch;
    private CustomButton btnSearch;
    private CustomTable tblnhanvien;
    private DefaultTableModel model;
    private NhanVienBLL nv;
    private TaiKhoanBLL tk;
    private ArrayList<NhanVienDTO> list_nv;
    private ThemTaiKhoan tkform;

    public ChonNhanVien() {
        tk = new TaiKhoanBLL();
        nv = new NhanVienBLL();
        list_nv = nv.getListNhanVien();
        init();
    }

    public void EventKey() {
        fieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (fieldSearch.getText().isEmpty()) {
                    tblnhanvien.setmodel(model);
                    return;
                }
                DefaultTableModel modelfilter = new DefaultTableModel(new String[]{"MaNV", "Họ Tên", "Địa chỉ", "Điện thoại"}, 0);
                ArrayList<NhanVienDTO> nvlist = nv.searchNhanVienByHoTen(fieldSearch.getText());
                for (NhanVienDTO nvdto : nvlist) {
                    if (tk.TestMaNV(nvdto.getstrMaNV())) {
                        continue;
                    }
                    Object[] rowData = {
                        nvdto.getstrMaNV(),
                        nvdto.getStrHo() + " " + nvdto.getStrTen(),
                        nvdto.getStrDiaChi(),
                        nvdto.getiDienThoai()
                    };
                    modelfilter.addRow(rowData);
                }
                tblnhanvien.setmodel(modelfilter);
            }
        });
    }

    public void EventMouse() {
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = tblnhanvien.getSelectedRow();
                if (rowSelected == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                tkform = new ThemTaiKhoan();

                tkform.setVisible(true);
                dispose();
                tkform.faccount.setText(tblnhanvien.getValueAt(rowSelected, 0).toString());
                tkform.btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String password = new String(tkform.fpass.getPassword());

                        if (password.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Password không được để trống", "Warning", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        TaiKhoanDTO tkdto = new TaiKhoanDTO(tkform.faccount.getText(), tk.hashMD5(password), tk.ChucVutoCapBac(tkform.cbcapbac.getSelectedItem().toString()), tk.trangThaiToInt(tkform.cbhoatdong.getSelectedItem().toString()));
                        if (tk.addAccount(tkdto)) {
                            tkform.dispose();

                            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
            }
        });
    }

    public void init() {
        model = new DefaultTableModel(new String[]{"MaNV", "Họ Tên", "Địa chỉ", "Điện thoại"}, 0);
        for (NhanVienDTO nvdto : list_nv) {
            if (tk.TestMaNV(nvdto.getstrMaNV())) {
                continue;
            }
            Object[] rowData = {
                nvdto.getstrMaNV(),
                nvdto.getStrHo() + " " + nvdto.getStrTen(),
                nvdto.getStrDiaChi(),
                nvdto.getiDienThoai()
            };
            model.addRow(rowData);
        }

        tblnhanvien = new CustomTable(model);
        scrollPane = new JScrollPane(tblnhanvien);
        scrollPane.setBounds(0, 100, width, height - 100);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        fieldSearch = new customTextField();
        fieldSearch.setText("Tìm kiếm nhân viên");
        fieldSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = fieldSearch.getText();
                if (text.equals("Tìm kiếm nhân viên")) {
                    fieldSearch.setText("");
                    fieldSearch.setBorderColor(Color.BLUE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = fieldSearch.getText();
                if (text.isEmpty()) {
                    fieldSearch.setText("Tìm kiếm nhân viên");

                }
                fieldSearch.setBorderColor(Color.decode("#E1E1E1"));

            }
        });
        fieldSearch.setBounds(30, 30, 800, 40);

        btnSearch = new CustomButton("Chọn");
        btnSearch.setBackground(Color.decode("#389FD6"));
        btnSearch.setBorderColor(btnSearch.getBackground());
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBounds(860, 30, 100, 40);

        PanelSerch = new JPanel(null);
        PanelSerch.setBounds(0, 0, width, 100);
        PanelSerch.add(fieldSearch);
        PanelSerch.add(btnSearch);
        PanelSerch.setBackground(Color.decode("#F2F2F2"));

        EventKey();
        EventMouse();

        this.add(PanelSerch);
        this.add(scrollPane);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChonNhanVien();

    }
}
