/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomTable extends JTable {

    public CustomTable() {
        init();
    }

    public CustomTable(DefaultTableModel model) {
        this.setModel(model);
        init();
    }
    public void setmodel(DefaultTableModel model){
        this.setModel(model);
        init();
    }
    public void init() {
        // Cài đặt các thuộc tính cơ bản của bảng
        this.setShowVerticalLines(false);  // Ẩn các đường phân cách dọc
        this.setShowHorizontalLines(true); // Hiển thị các đường phân cách ngang
        this.setBackground(Color.WHITE);   // Màu nền của bảng
        this.setRowHeight(40);
        JTableHeader header = this.getTableHeader(); //Tạo đối tượng TableHeader
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        // Cài đặt màu nền của thanh tiêu đề
        this.getTableHeader().setBackground(Color.decode("#F2F2F2"));
        this.getTableHeader().setForeground(Color.BLACK);  // Màu chữ của tiêu đề 

        // Cài đặt màu nền cho các hàng trong bảng
        this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                cell.setBackground(Color.WHITE);  // Màu nền hàng chẵn

                return cell;
            }
        });
        this.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());  // Ẩn phân cách dọc
                comp.setBackground(Color.decode("#F2F2F2")); // Màu nền tiêu đề
                comp.setForeground(Color.BLACK);
                comp.setFont(new Font("SansSerif", Font.BOLD, 15));
                return comp;
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // ❌ Chặn hết mọi ô
    }
}
