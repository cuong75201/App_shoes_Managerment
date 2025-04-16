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
import javax.swing.JButton;
/**
 *
 * @author cuong
 */
public class CreateComponent {
    public static CustomButton createBtn(String Icon,String Text){
        CustomButton btn=new CustomButton(Text);
        btn.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir")+"/src/Assets/icon_func/" +Icon).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH)));
        btn.setVerticalTextPosition(JButton.BOTTOM);
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setForeground(Color.decode("#39BCD6"));
        btn.setFont(new Font("Sans-serif",Font.BOLD,15));
        btn.setBorderColor(Color.WHITE);
        return btn;
    }
}
