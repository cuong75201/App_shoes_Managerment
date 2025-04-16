/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author cuong
 */
public class CustomComboBox extends JComboBox<String> {

    private Color bgColor = Color.WHITE;
    private Color borderColor = Color.decode("#E1E1E1");
    private Color foregroundColor = Color.BLACK;
    private int border = 1;

    public CustomComboBox() {
        init();
    }

    public void init() {
        setUI(new RoundedComBoBoxUI());
        setOpaque(false);
        setBackground(bgColor);
        setForeground(foregroundColor);

        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (isSelected) {
                    label.setBackground(new Color(30, 144, 255)); // Màu xanh khi hover/chọn
                    label.setForeground(Color.WHITE);             // Chữ trắng
                } else {
                    label.setBackground(Color.WHITE);             // Màu nền thường
                    label.setForeground(Color.BLACK);             // Chữ đen
                }

                label.setOpaque(true); // QUAN TRỌNG để thấy màu nền
                label.setHorizontalAlignment(SwingConstants.CENTER); // Nếu muốn canh giữa
                label.setFont(getFont());

                return label;
            }
        });
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getborder() {
        return border;
    }

    public void setborder(int border) {
        this.border = border;
    }

    private class RoundedComBoBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton("▼");
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setForeground(foregroundColor);
            button.setBackground(bgColor);
            return button;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = c.getWidth();
            int height = c.getHeight();
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, width, height, 10, 10);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(border));
            g2.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);
            super.paint(g, c);
        }
    }

}
