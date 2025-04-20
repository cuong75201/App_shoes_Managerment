/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import javax.swing.JPanel;

import GUI.component.PanelFunction;
import GUI.component.CustomComboBox;
import GUI.component.CustomTable;
import GUI.Form.ChonNhanVien;
import GUI.Form.SuaTaiKhoan;

import BLL.TaiKhoanBLL;

import DTO.TaiKhoanDTO;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TaiKhoanGUI extends JPanel {

    public ArrayList<TaiKhoanDTO> list_tk;
    public TaiKhoanBLL tk;
    private int width = 1116, height = 800;
    PanelFunction pnButton;
    private JScrollPane scrollpane;
    private CustomTable tbltaikhoan;
    private CustomComboBox cbfilter;
    private ChonNhanVien changeNV;
    private SuaTaiKhoan suatk;

    public TaiKhoanGUI() {
        pnButton = new PanelFunction();
        list_tk = new ArrayList<>();
        tk = new TaiKhoanBLL();
        list_tk = tk.getListAccount();
        init();
    }

    public void init() {

        cbfilter = new CustomComboBox();
        cbfilter.addItem("Username");
        pnButton.setCbfilter(cbfilter);
        pnButton.setBtnReset();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên đăng nhập", "Quyền", "Trạng thái"}, 0);
        for (TaiKhoanDTO tkdto : list_tk) {
            Object[] rowData = {
                tkdto.getStrTenDangNhap(),
                tk.CapBactoChucVu(tkdto.getiCapBac()),
                tk.intToTrangThai(tkdto.getiTrangThai())
            };
            model.addRow(rowData);
        }
        tbltaikhoan = new CustomTable(model);
        scrollpane = new JScrollPane(tbltaikhoan);
        scrollpane.setBounds(20, 150, width - 50, 600);

        // Xu ly sk them
        pnButton.btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeNV = new ChonNhanVien();
                changeNV.setVisible(true);
            }
        });

        pnButton.btnSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = tbltaikhoan.getSelectedRow();
                if (rowSelected == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                suatk = new SuaTaiKhoan();
                suatk.setVisible(true);
                suatk.faccount.setText(tbltaikhoan.getValueAt(rowSelected, 0).toString());
                suatk.btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        suatk.dispose();
                    }
                });
                suatk.btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("aa");
                        TaiKhoanDTO tkdto = new TaiKhoanDTO();
                        tkdto.setStrTenDangNhap(suatk.faccount.getText());
                        tkdto.setiCapBac(tk.ChucVutoCapBac(suatk.cbcapbac.getSelectedItem().toString()));
                        tkdto.setiTrangThai(tk.trangThaiToInt(suatk.cbhoatdong.getSelectedItem().toString()));
                        if (tk.UpdateAccount(tkdto)) {
                            tbltaikhoan.setValueAt(suatk.cbcapbac.getSelectedItem(), rowSelected, 1);
                            tbltaikhoan.setValueAt(suatk.cbhoatdong.getSelectedItem(), rowSelected, 2);

                            suatk.dispose();
                        }

                    }
                });
            }
        });
        pnButton.btnXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = tbltaikhoan.getSelectedRow();
                if (rowSelected == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) {
                    return;
                }
                if (tk.deleteAccount(tk.searchTKbyMa(tbltaikhoan.getValueAt(rowSelected, 0).toString()))) {
                    model.removeRow(rowSelected);
                }
            }
        });
        pnButton.btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String search = pnButton.fieldSearch.getText();
                DefaultTableModel modelfilter = new DefaultTableModel(new String[]{"Tên đăng nhập", "Quyền", "Trạng thái"}, 0);
                for (TaiKhoanDTO tkdto : list_tk) {
                    if (tkdto.getStrTenDangNhap().toLowerCase().contains(search.toLowerCase())) {

                        Object[] rowData = {
                            tkdto.getStrTenDangNhap(),
                            tk.CapBactoChucVu(tkdto.getiCapBac()),
                            tk.intToTrangThai(tkdto.getiTrangThai())
                        };
                        modelfilter.addRow(rowData);
                    }
                    tbltaikhoan.setmodel(modelfilter);
                    
                }
            }
        });
         pnButton.btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tbltaikhoan.setmodel(model);
            }
        });
        this.setLayout(null);
        this.add(pnButton);
        this.add(scrollpane);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));
    }
}
