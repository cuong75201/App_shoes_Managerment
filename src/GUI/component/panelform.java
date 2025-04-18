/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.component.customTextField;
import GUI.component.CustomComboBox;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author cuong
 */
public class panelform extends JPanel {

    public JLabel lb = new JLabel();
    public customTextField field;
    public CustomComboBox cb;

    public panelform(String lbtext) {
        JLabel lb = new JLabel(lbtext);
        lb.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lb.setBounds(0, 0, 100, 20);

        this.setLayout(null);
        this.setSize(200, 100);
        this.add(lb);

        this.setBackground(Color.WHITE);
    }

    public void setField() {
        this.field = new customTextField();
        field.setBounds(0, 30, 200, 30);
        this.add(field);
    }
    public void setComboBox(CustomComboBox cb){
        this.cb=cb;
        cb.setBounds(0, 30, 200, 30);
        this.add(cb);
    }
}
