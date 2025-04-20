/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.ThongKe;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.component.CustomTable;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.CustomComboBox;

import BLL.PhieuNhapBLL;
import BLL.HoaDonBLL;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DoanhThu extends JPanel {

    private JPanel pnContent;
    private int width = 1116, height = 600;
    private CustomComboBox cbfilter;
    private JLabel start, end;
    private customTextField fieldStart, fieldEnd;
    private CustomTable tblThongKe;
    private JScrollPane scrollPane;
    private CustomButton btnTK;
    private PhieuNhapBLL pn;
    private HoaDonBLL hd;

    public DoanhThu() {
        pn = new PhieuNhapBLL();
        hd = new HoaDonBLL();
        init();
    }

    public void init() {
        tblThongKe = new CustomTable();

        cbfilter = new CustomComboBox();
        cbfilter.addItem("Thống kê theo năm");
        cbfilter.addItem("Thống kê theo tháng");
        cbfilter.addItem("Thống kê theo ngày");
        cbfilter.setBounds(100, 20, 200, 30);

        pnContent = new JPanel(null);
        pnContent.setBackground(Color.WHITE);
        pnContent.setBounds(20, 0, width - 50, 550);
        pnContent.add(cbfilter);
        ThongKeTheoNam();
        cbfilter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                pnContent.removeAll();
                pnContent.add(cbfilter);
                pnContent.revalidate();
                pnContent.repaint();
                int index = cbfilter.getSelectedIndex();
                switch (index) {
                    case 0:
                        ThongKeTheoNam();
                        break;
                    case 1:
                        ThongKeTheoThang();
                        break;
                    case 2:
                        ThongKeTheoNgay();
                        break;
                    default:

                        System.out.println("Lựa chọn không hợp lệ!");
                        break;
                }
            }
        });

        this.add(pnContent);
        this.setLayout(null);
        this.setBackground(Color.decode("#F0F7FA"));
    }

    public void ThongKeTheoNam() {
        start = new JLabel("Từ năm:");
        start.setFont(new Font("Tahoma", Font.PLAIN, 16));
        fieldStart = new customTextField();
        start.setBounds(350, 20, 70, 30);
        fieldStart.setBounds(420, 20, 70, 30);

        end = new JLabel("đến năm:");
        end.setFont(new Font("Tahoma", Font.PLAIN, 16));
        fieldEnd = new customTextField();
        end.setBounds(500, 20, 70, 30);
        fieldEnd.setBounds(580, 20, 70, 30);

        btnTK = new CustomButton("Thống kê");
        btnTK.setBackground(Color.BLACK);
        btnTK.setForeground(Color.WHITE);
        btnTK.setBorderColor(btnTK.getBackground());
        btnTK.setBounds(660, 20, 100, 30);
        btnTK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (kiemTraNamHopLe(fieldStart.getText(), fieldEnd.getText())) {
                    if (scrollPane != null) {
                        pnContent.remove(scrollPane);
                    }
                    int start = Integer.parseInt(fieldStart.getText());
                    int end = Integer.parseInt(fieldEnd.getText());
                    DefaultTableModel model = new DefaultTableModel(new String[]{"Năm", "Vốn", "Doanh thu", "Lợi nhuận"}, 0);
                    for (int i = start; i <= end; i++) {
                        Object[] rowData = {
                            i,
                            pn.getTongTientheoNam(i),
                            hd.getTongTientheoNam(i),
                            hd.getTongTientheoNam(i) - pn.getTongTientheoNam(i)
                        };
                        model.addRow(rowData);
                    }
                    tblThongKe = new CustomTable(model);
                    scrollPane = new JScrollPane(tblThongKe);
                    scrollPane.setBounds(0, 60, width, 500);
                    pnContent.add(scrollPane);
                    pnContent.revalidate();
                    pnContent.repaint();
                }
            }
        });

        pnContent.add(start);
        pnContent.add(fieldStart);
        pnContent.add(end);
        pnContent.add(fieldEnd);
        pnContent.add(btnTK);
    }

    public void ThongKeTheoThang() {
        start = new JLabel("Nhập năm:");
        start.setFont(new Font("Tahoma", Font.PLAIN, 14));
        fieldStart = new customTextField();
        start.setBounds(350, 20, 70, 30);
        fieldStart.setBounds(440, 20, 70, 30);

        btnTK = new CustomButton("Thống kê");
        btnTK.setBackground(Color.BLACK);
        btnTK.setForeground(Color.WHITE);
        btnTK.setBorderColor(btnTK.getBackground());
        btnTK.setBounds(520, 20, 100, 30);
        btnTK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isValidYear(fieldStart.getText())) {
                    if (scrollPane != null) {
                        pnContent.remove(scrollPane);
                    }
                    int year = Integer.parseInt(fieldStart.getText());
                    DefaultTableModel model = new DefaultTableModel(new String[]{"Tháng", "Vốn", "Doanh thu", "Lợi nhuận"}, 0);
                    for (int i = 0; i < 12; i++) {
                        Object[] rowData = {
                            i + 1,
                            pn.getTongTienTheoThang(i + 1, year),
                            hd.getTongTienTheoThang(i + 1, year),
                            hd.getTongTienTheoThang(i + 1, year) - pn.getTongTienTheoThang(i + 1, year)
                        };
                        model.addRow(rowData);

                    }
                    tblThongKe = new CustomTable(model);
                    scrollPane = new JScrollPane(tblThongKe);
                    scrollPane.setBounds(0, 60, width, 500);
                    pnContent.add(scrollPane);
                    pnContent.revalidate();
                    pnContent.repaint();
                }
            }
        });

        pnContent.add(start);
        pnContent.add(fieldStart);
        pnContent.add(btnTK);
    }

    public void ThongKeTheoNgay() {
        start = new JLabel("Từ ngày:");
        start.setFont(new Font("Tahoma", Font.PLAIN, 16));
        fieldStart = new customTextField();
        start.setBounds(350, 20, 70, 30);
        fieldStart.setBounds(420, 20, 70, 30);

        end = new JLabel("đến ngày:");
        end.setFont(new Font("Tahoma", Font.PLAIN, 16));
        fieldEnd = new customTextField();
        end.setBounds(500, 20, 70, 30);
        fieldEnd.setBounds(580, 20, 70, 30);

        btnTK = new CustomButton("Thống kê");
        btnTK.setBackground(Color.BLACK);
        btnTK.setForeground(Color.WHITE);
        btnTK.setBorderColor(btnTK.getBackground());
        btnTK.setBounds(660, 20, 100, 30);
        btnTK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (kiemTraKhoangNgay(fieldStart.getText(), fieldEnd.getText())) {
                    if (scrollPane != null) {
                        pnContent.remove(scrollPane);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                    sdf.setLenient(false);
                    Date start = chuyenChuoiThanhNgay(fieldStart.getText());
                    Date end = chuyenChuoiThanhNgay(fieldEnd.getText());
                    DefaultTableModel model = new DefaultTableModel(new String[]{"Ngày", "Vốn", "Doanh thu", "Lợi nhuận"}, 0);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(start);
                    while (!cal.getTime().after(end)) {
                        Date currentDate = cal.getTime();
                        String currentStr = sdf.format(currentDate);
                        Object[] dataRow = {
                            currentStr,
                            pn.getTongTienTheoNgay(currentStr),
                            hd.getTongTienTheoNgay(currentStr),
                            hd.getTongTienTheoNgay(currentStr) - pn.getTongTienTheoNgay(currentStr)
                        };
                        model.addRow(dataRow);

                        cal.add(Calendar.DATE, 1); // Tăng thêm 1 ngày
                    }
                    tblThongKe = new CustomTable(model);
                    scrollPane = new JScrollPane(tblThongKe);
                    scrollPane.setBounds(0, 60, width, 500);
                    pnContent.add(scrollPane);
                    pnContent.revalidate();
                    pnContent.repaint();
                }
            }
        });

        pnContent.add(start);
        pnContent.add(fieldStart);
        pnContent.add(end);
        pnContent.add(fieldEnd);
        pnContent.add(btnTK);
    }

    public boolean kiemTraNamHopLe(String strNamBatDau, String strNamKetThuc) {
        try {
            int namBatDau = Integer.parseInt(strNamBatDau.trim());
            int namKetThuc = Integer.parseInt(strNamKetThuc.trim());

            if (namBatDau <= 0 || namKetThuc <= 0) {
                JOptionPane.showMessageDialog(null, "Năm không được âm hoặc bằng 0!");
                return false;
            }

            if (namBatDau > namKetThuc) {
                JOptionPane.showMessageDialog(null, "Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc!");
                return false;
            }

            if (namBatDau < 1900 || namKetThuc > 2100) {
                JOptionPane.showMessageDialog(null, "Năm phải nằm trong khoảng từ 1900 đến 2100!");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên hợp lệ cho năm!");
            return false;
        }
    }

    public static boolean isValidYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            if (year > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Năm không hợp lệ! Vui lòng nhập một năm dương.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Đầu vào không hợp lệ! Vui lòng nhập một số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean kiemTraKhoangNgay(String ngayBatDau, String ngayKetThuc) {
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        sdf.setLenient(false); // Không cho phép ngày sai như 32/1/2020

        try {
            Date dateStart = sdf.parse(ngayBatDau);
            Date dateEnd = sdf.parse(ngayKetThuc);

            if (dateStart.after(dateEnd)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ! (Đúng: d/M/yyyy)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Date chuyenChuoiThanhNgay(String chuoiNgay) {
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        sdf.setLenient(false); // Không cho phép ngày sai

        try {
            return sdf.parse(chuoiNgay);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ! (Đúng: d/M/yyyy)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
