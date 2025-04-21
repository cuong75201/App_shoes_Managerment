/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Form;

import BLL.NhanVienBLL;
import BLL.TaiKhoanBLL;

import java.util.Random;

import DTO.TaiKhoanDTO;

import GUI.component.*;
import Utils.SendEmail;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuenMatKhau extends JFrame {

    private NhanVienBLL nv;
    private TaiKhoanBLL tk;
    private CustomButton btnXacnhan;
    private customTextField field;
    private CustomButton btnConfirm;

    public QuenMatKhau() {
        nv = new NhanVienBLL();
        tk = new TaiKhoanBLL();
        init();
    }

    public void init() {
        JLabel label = new JLabel("Nhập tên tài khoản của bạn:");
        label.setBounds(230, 50, 300, 40);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        btnXacnhan = new CustomButton("Xác nhận");
        btnXacnhan.setBackground(Color.decode("#303F9F"));
        btnXacnhan.setForeground(Color.WHITE);
        btnXacnhan.setBorderColor(btnXacnhan.getBackground());
        btnXacnhan.setBounds(230, 150, 250, 40);

        field = new customTextField();
        field.setBounds(230, 100, 250, 40);

        JPanel pnMain = new JPanel(null);
        pnMain.add(label);
        pnMain.add(field);
        pnMain.add(btnXacnhan);
        pnMain.setBackground(Color.WHITE);

        this.add(pnMain);

        btnXacnhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String account = field.getText();
                if (tk.searchTKbyMa(account) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy tên tài khoản", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pnMain.removeAll();
                pnMain.revalidate();
                pnMain.repaint();
                JLabel otp, newpass;
                customTextField fotp = new customTextField();
                customPasswordField pass = new customPasswordField();
                otp = new JLabel("Nhập mã otp vừa được gửi qua email:");
                newpass = new JLabel("Nhập mật khẩu mới:");
                otp.setBounds(50, 50, 330, 40);
                otp.setFont(new Font("Arial", Font.PLAIN, 18));
                newpass.setBounds(50, 120, 200, 40);
                newpass.setFont(new Font("Arial", Font.PLAIN, 18));

                btnConfirm = new CustomButton("Xác nhận");
                btnConfirm.setBackground(Color.decode("#303F9F"));
                btnConfirm.setForeground(Color.WHITE);
                btnConfirm.setBorderColor(btnXacnhan.getBackground());
                btnConfirm.setBounds(50,220,590,40);
                
                fotp.setBounds(390, 50, 200, 40);
                pass.setBounds(390, 120, 200, 40);
                pnMain.add(otp);
                pnMain.add(newpass);
                pnMain.add(fotp);
                pnMain.add(pass);
                pnMain.add(btnConfirm);
                
                 String title ="[Shoes Management App] Mã xác thực (OTP) để đặt lại mật khẩu";
                int ma = (int)(Math.random() * 90000) + 10000;
                SendEmail.sendEmail(nv.searchEmailfromManv(account), title,ma+"" );
                
                btnConfirm.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        if(!fotp.getText().equals(ma+"")){
                            JOptionPane.showMessageDialog(null,"Mã OTP không đúng!","Lỗi",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String password=new String(pass.getPassword());
                        if(password.isEmpty()){
                            JOptionPane.showMessageDialog(null,"Mật khẩu không được để trống!","Lỗi",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        TaiKhoanDTO tkdto=tk.searchTKbyMa(account);
                        tkdto.setStrMatKhau(tk.hashMD5(password));
                        System.out.println(tkdto);
                        if(tk.UpdateAccount(tkdto)){
                            System.out.println(tk.getListAccount());
                            JOptionPane.showMessageDialog(null,"Đổi mật khẩu thành công","Thành công",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    }
                });
            }
        });

        this.setTitle("Quên mật khẩu");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(730, 320);
    }
}
