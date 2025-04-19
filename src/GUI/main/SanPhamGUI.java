/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import DTO.SanPhamDTO;
import DTO.ThuongHieuDTO;
import DTO.LoaiDTO;
import DTO.MauSacDTO;
import DTO.XuatXuDTO;

import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.PanelFunction;
import GUI.component.CustomTable;
import GUI.Form.ThemSanPham;

import BLL.SanPhamBLL;
import BLL.ThuongHieuBLL;
import BLL.LoaiBLL;
import BLL.MauSacBLL;
import BLL.XuatXuBLL;

import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import Utils.CreateComponent;
import Utils.XuatExcel;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SanPhamGUI extends JPanel {

    ArrayList<SanPhamDTO> list_sp;
    ArrayList<ThuongHieuDTO> list_th;
    ArrayList<MauSacDTO> list_mau;
    ArrayList<LoaiDTO> list_loai;
    ArrayList<XuatXuDTO> list_xx;
    SanPhamBLL sp;
    ThuongHieuBLL th;
    LoaiBLL loai;
    MauSacBLL mau;
    XuatXuBLL xx;

    private PanelFunction pnButton;
    private ThemSanPham addSanPham;
    private CustomComboBox cbfilter, cbmau, cbxx, cbloai, cbth;
    private CustomTable tblsanpham;
    private JScrollPane scrollPane;
    private int width = 1116, height = 800;

    public SanPhamGUI() {

        list_sp = new ArrayList<>();
        list_th = new ArrayList<>();
        list_mau = new ArrayList<>();
        list_loai = new ArrayList<>();
        list_xx = new ArrayList<>();
        th = new ThuongHieuBLL();
        sp = new SanPhamBLL();
        loai = new LoaiBLL();
        mau = new MauSacBLL();
        xx = new XuatXuBLL();
        list_th = th.getList_ThuongHieu();
        list_sp = sp.getListProduct();
        list_mau = mau.getListMauSac();
        list_xx = xx.getList_XuatXu();
        list_loai = loai.getLoaiList();

        init();
    }

    public void init() {
        cbloai = new CustomComboBox();
        cbmau = new CustomComboBox();
        cbth = new CustomComboBox();
        cbxx = new CustomComboBox();
        for (LoaiDTO loaidto : list_loai) {
            cbloai.addItem(loaidto.getStrTenloai());
        }
        for (ThuongHieuDTO thdto : list_th) {
            cbth.addItem(thdto.getStrTenthuonghieu());
        }
        for (MauSacDTO maudto : list_mau) {
            cbmau.addItem(maudto.getStrTenmau());
        }
        for (XuatXuDTO xxdto : list_xx) {
            cbxx.addItem(xxdto.getStrTennuoc());
        }
        addSanPham = new ThemSanPham(cbloai, cbmau, cbxx, cbth);

        addSanPham.pnMasp.field.setText(sp.getDefaultMasp());

        pnButton = new PanelFunction();
        cbfilter = new CustomComboBox();
        cbfilter.addItem("Mã giày");
        cbfilter.addItem("Tên giày");
        cbfilter.addItem("Thương hiệu");
        cbfilter.addItem("Loại giày");

        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã giày", "Tên giày", "Số lượng", "Giá", "Size", "Đối tượng", "Chất liệu", "Loại giày", "Xuất xứ", "Màu sắc", "Thương Hiệu"}, 0);
        for (SanPhamDTO spdto : list_sp) {
            if (spdto.getiTrangthai() == 0) {
                continue;
            }
            Object[] rowData = {
                spdto.getStrMaGiay(),
                spdto.getStrTenGiay(),
                spdto.getiSoLuong(),
                spdto.getiGia(),
                spdto.getiSize(),
                spdto.getStrDoiTuongSD(),
                spdto.getStrChatLieu(),
                loai.getLoaiNameById(spdto.getStrMaLoai()),
                xx.getTennuocfromMaxx(spdto.getStrMaxx()),
                mau.getTenmaufromMaMau(spdto.getStrMaMau()),
                th.getTenTHfromMaTH(spdto.getStrMaThuongHieu())
            };
//            System.out.println(spdto.getStrMaMau()+" "+ mau.getTenmaufromMaMau(spdto.getStrMaMau()));
            model.addRow(rowData);
        }

        tblsanpham = new CustomTable(model);
        tblsanpham.getTableHeader().setReorderingAllowed(false);
        tblsanpham.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tblsanpham.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        scrollPane = new JScrollPane(tblsanpham);

        scrollPane.setBounds(
                20, 150, width - 50, 600);
        pnButton.setCbfilter(cbfilter);
        pnButton.setBtnReset();
        pnButton.setBtnExcel();
        this.setLayout(
                null);

        this.add(pnButton);

        this.add(scrollPane);

        this.setBounds(
                250, 0, width, height);

        this.setBackground(Color.decode("#F0F7FA"));

        pnButton.btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addSanPham.setVisible(true);
                addSanPham.btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        addSanPham.setVisible(false);
                    }
                });
                addSanPham.btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SanPhamDTO spdto = new SanPhamDTO();
                        if (addSanPham.pnSoluong.field.getText().trim().isEmpty() || addSanPham.pnGia.field.getText().trim().isEmpty() || addSanPham.pnSize.field.getText().trim().isEmpty() || addSanPham.pnTensp.field.getText().trim().isEmpty() || addSanPham.pnDoituong.field.getText().trim().isEmpty() || addSanPham.pnChatlieu.field.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Các trường không được để trống", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnSoluong.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnGia.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Giá không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnSize.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Size giày không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        spdto.setStrMaGiay(addSanPham.pnMasp.field.getText());
                        spdto.setStrTenGiay(addSanPham.pnTensp.field.getText());
                        spdto.setStrDoiTuongSD(addSanPham.pnDoituong.field.getText());
                        spdto.setiSoLuong(Integer.parseInt(addSanPham.pnSoluong.field.getText()));
                        spdto.setiGia(Integer.parseInt(addSanPham.pnGia.field.getText()));
                        spdto.setiSize(Integer.parseInt(addSanPham.pnSize.field.getText()));
                        spdto.setStrChatLieu(addSanPham.pnChatlieu.field.getText());
                        spdto.setStrMaLoai(loai.getIdbyLoainame(addSanPham.pnloai.cb.getSelectedItem().toString()));
                        spdto.setStrMaMau(mau.getMamaufromTenMau(addSanPham.pnmau.cb.getSelectedItem().toString()));
                        spdto.setStrMaxx(xx.getMaxxfromTennuoc(addSanPham.pnxx.cb.getSelectedItem().toString()));
                        spdto.setStrMaThuongHieu(th.getMaTHfromTenTH(addSanPham.pnthuonghieu.cb.getSelectedItem().toString()));
                        if (sp.addProduct(spdto)) {
                            Object[] rowData = {
                                spdto.getStrMaGiay(),
                                spdto.getStrTenGiay(),
                                spdto.getiSoLuong(),
                                spdto.getiGia(),
                                spdto.getiSize(),
                                spdto.getStrDoiTuongSD(),
                                spdto.getStrChatLieu(),
                                loai.getLoaiNameById(spdto.getStrMaLoai()),
                                xx.getTennuocfromMaxx(spdto.getStrMaxx()),
                                mau.getTenmaufromMaMau(spdto.getStrMaMau()),
                                th.getTenTHfromMaTH(spdto.getStrMaThuongHieu())
                            };
//            System.out.println(spdto.getStrMaMau()+" "+ mau.getTenmaufromMaMau(spdto.getStrMaMau()));
                            model.addRow(rowData);
                            addSanPham.setVisible(false);
                        };
                    }
                });
            }
        });

        pnButton.btnSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblsanpham.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addSanPham.setVisible(true);
                addSanPham.pnMasp.field.setText(tblsanpham.getValueAt(selectedRow, 0).toString());
                addSanPham.pnTensp.field.setText(tblsanpham.getValueAt(selectedRow, 1).toString());
                addSanPham.pnSoluong.field.setText(tblsanpham.getValueAt(selectedRow, 2).toString());
                addSanPham.pnGia.field.setText(tblsanpham.getValueAt(selectedRow, 3).toString());
                addSanPham.pnSize.field.setText(tblsanpham.getValueAt(selectedRow, 4).toString());
                addSanPham.pnDoituong.field.setText(tblsanpham.getValueAt(selectedRow, 5).toString());
                addSanPham.pnChatlieu.field.setText(tblsanpham.getValueAt(selectedRow, 6).toString());
                addSanPham.pnloai.cb.setSelectedItem(tblsanpham.getValueAt(selectedRow, 7).toString());
                addSanPham.pnxx.cb.setSelectedItem(tblsanpham.getValueAt(selectedRow, 8).toString());
                addSanPham.pnmau.cb.setSelectedItem(tblsanpham.getValueAt(selectedRow, 9).toString());
                addSanPham.pnthuonghieu.cb.setSelectedItem(tblsanpham.getValueAt(selectedRow, 10).toString());

                addSanPham.btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        addSanPham.setVisible(false);
                    }
                });
                addSanPham.btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SanPhamDTO spdto = new SanPhamDTO();
                        if (addSanPham.pnSoluong.field.getText().trim().isEmpty() || addSanPham.pnGia.field.getText().trim().isEmpty() || addSanPham.pnSize.field.getText().trim().isEmpty() || addSanPham.pnTensp.field.getText().trim().isEmpty() || addSanPham.pnDoituong.field.getText().trim().isEmpty() || addSanPham.pnChatlieu.field.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Các trường không được để trống", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnSoluong.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnGia.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Giá không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!isInteger(addSanPham.pnSize.field.getText())) {
                            JOptionPane.showMessageDialog(null, "Size giày không hợp lệ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        spdto.setStrMaGiay(addSanPham.pnMasp.field.getText());
                        spdto.setStrTenGiay(addSanPham.pnTensp.field.getText());
                        spdto.setStrDoiTuongSD(addSanPham.pnDoituong.field.getText());
                        spdto.setiSoLuong(Integer.parseInt(addSanPham.pnSoluong.field.getText()));
                        spdto.setiGia(Integer.parseInt(addSanPham.pnGia.field.getText()));
                        spdto.setiSize(Integer.parseInt(addSanPham.pnSize.field.getText()));
                        spdto.setStrChatLieu(addSanPham.pnChatlieu.field.getText());
                        spdto.setStrMaLoai(loai.getIdbyLoainame(addSanPham.pnloai.cb.getSelectedItem().toString()));
                        spdto.setStrMaMau(mau.getMamaufromTenMau(addSanPham.pnmau.cb.getSelectedItem().toString()));
                        spdto.setStrMaxx(xx.getMaxxfromTennuoc(addSanPham.pnxx.cb.getSelectedItem().toString()));
                        spdto.setStrMaThuongHieu(th.getMaTHfromTenTH(addSanPham.pnthuonghieu.cb.getSelectedItem().toString()));
                        if (sp.updateProduct(spdto)) {
                            model.setValueAt(spdto.getStrMaGiay(), selectedRow, 0);
                            model.setValueAt(spdto.getStrTenGiay(), selectedRow, 1);
                            model.setValueAt(spdto.getiSoLuong(), selectedRow, 2);
                            model.setValueAt(spdto.getiGia(), selectedRow, 3);
                            model.setValueAt(spdto.getiSize(), selectedRow, 4);
                            model.setValueAt(spdto.getStrDoiTuongSD(), selectedRow, 5);
                            model.setValueAt(spdto.getStrChatLieu(), selectedRow, 6);
                            model.setValueAt(loai.getLoaiNameById(spdto.getStrMaLoai()), selectedRow, 7);
                            model.setValueAt(xx.getTennuocfromMaxx(spdto.getStrMaxx()), selectedRow, 8);
                            model.setValueAt(mau.getTenmaufromMaMau(spdto.getStrMaMau()), selectedRow, 9);
                            model.setValueAt(th.getTenTHfromMaTH(spdto.getStrMaThuongHieu()), selectedRow, 10);

                            addSanPham.setVisible(false);
                        };
                    }
                });

            }
        });
        pnButton.btnXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblsanpham.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                System.out.println(xx.getMaxxfromTennuoc(tblsanpham.getValueAt(selectedRow, 8).toString()));
                if (result == JOptionPane.YES_OPTION) {
                    SanPhamDTO spdto = new SanPhamDTO();
                    spdto.setStrMaGiay(tblsanpham.getValueAt(selectedRow, 0).toString());
                    spdto.setStrTenGiay(tblsanpham.getValueAt(selectedRow, 1).toString());
                    spdto.setStrDoiTuongSD(tblsanpham.getValueAt(selectedRow, 5).toString());
                    spdto.setiSoLuong(Integer.parseInt(tblsanpham.getValueAt(selectedRow, 2).toString()));
                    spdto.setiGia(Integer.parseInt(tblsanpham.getValueAt(selectedRow, 3).toString()));
                    spdto.setiSize(Integer.parseInt(tblsanpham.getValueAt(selectedRow, 4).toString()));
                    spdto.setStrChatLieu(tblsanpham.getValueAt(selectedRow, 6).toString());
                    spdto.setStrMaLoai(loai.getIdbyLoainame(tblsanpham.getValueAt(selectedRow, 7).toString()));
                    spdto.setStrMaMau(mau.getMamaufromTenMau(tblsanpham.getValueAt(selectedRow, 9).toString()));
                    spdto.setStrMaxx(xx.getMaxxfromTennuoc(tblsanpham.getValueAt(selectedRow, 8).toString()));
                    spdto.setStrMaThuongHieu(th.getMaTHfromTenTH(tblsanpham.getValueAt(selectedRow, 10).toString()));
                    spdto.setiTrangthai(0);
                    if (sp.updateProduct(spdto)) {
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        pnButton.btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String filter = pnButton.cbfilter.getSelectedItem().toString();
                String search = pnButton.fieldSearch.getText();
                ArrayList<SanPhamDTO> spham = new ArrayList<>();
                if (filter.equals("Mã giày")) {
                    spham = sp.SearchbyMasp(search);
                } else if (filter.equals("Tên giày")) {
                    spham = sp.SearchbyTensp(search);
                } else if (filter.equals("Thương hiệu")) {
                    if (th.searchMaTHfromTenTH(search).isEmpty()) {
                        spham = new ArrayList<>();
                    } else {
                        spham = sp.SearchbyMaThuongHieu(th.searchMaTHfromTenTH(search));
                    }
                } else {
                    if (loai.searchMaloaifromTenloai(search).isEmpty()) {
                        spham = new ArrayList<>();
                    } else {
                        spham = sp.SearchbyMaLoaiSP(loai.searchMaloaifromTenloai(search));
                    }
                }
                DefaultTableModel modelfilter = new DefaultTableModel(new String[]{"Mã giày", "Tên giày", "Số lượng", "Giá", "Size", "Đối tượng", "Chất liệu", "Loại giày", "Xuất xứ", "Màu sắc", "Thương Hiệu"}, 0);
                for (SanPhamDTO spdto : spham) {
                    if (spdto.getiTrangthai() == 0) {
                        continue;
                    }
                    Object[] rowData = {
                        spdto.getStrMaGiay(),
                        spdto.getStrTenGiay(),
                        spdto.getiSoLuong(),
                        spdto.getiGia(),
                        spdto.getiSize(),
                        spdto.getStrDoiTuongSD(),
                        spdto.getStrChatLieu(),
                        loai.getLoaiNameById(spdto.getStrMaLoai()),
                        xx.getTennuocfromMaxx(spdto.getStrMaxx()),
                        mau.getTenmaufromMaMau(spdto.getStrMaMau()),
                        th.getTenTHfromMaTH(spdto.getStrMaThuongHieu())
                    };
                    System.out.println(loai.getLoaiNameById(spdto.getStrMaLoai())
);

                    modelfilter.addRow(rowData);
                }

                tblsanpham.setmodel(modelfilter);
//                tblsanpham.getTableHeader().setReorderingAllowed(false);
//                tblsanpham.setFont(new Font("Sans-serif", Font.PLAIN, 14));
//                tblsanpham.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
            }

        });
        pnButton.btnReset.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                 tblsanpham.setmodel(model);
            }
        });
        pnButton.btnXuatExcel.addMouseListener(new MouseAdapter(){
            @Override
              public void mouseClicked(MouseEvent e){
              XuatExcel export =new XuatExcel();
              export.exportTableToExcel(tblsanpham);
            }
        });
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true; // Chuyển được
        } catch (NumberFormatException e) {
            return false; // Không chuyển được
        }
    }
}
