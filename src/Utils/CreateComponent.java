/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import GUI.component.CustomButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author cuong
 */
public class CreateComponent {

    public static CustomButton createBtn(String Icon, String Text) {
        CustomButton btn = new CustomButton(Text);
        btn.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/icon_func/" + Icon).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        btn.setVerticalTextPosition(JButton.BOTTOM);
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setForeground(Color.decode("#39BCD6"));
        btn.setFont(new Font("Sans-serif", Font.BOLD, 15));
        btn.setBorderColor(btn.getBackground());
        btn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
         

            }

            @Override
            public void mouseReleased(MouseEvent e) {
              

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Color.decode("#BBDEFB"));
                btn.setBorderColor(btn.getBackground());

            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setBorderColor(btn.getBackground());

            }
        });
        return btn;
    }
}
