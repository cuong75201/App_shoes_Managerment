/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import GUI.component.customPasswordField;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.Form.QuenMatKhau;

import Utils.SendEmail;

import DTO.TaiKhoanDTO;
import BLL.TaiKhoanBLL;
import BLL.NhanVienBLL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoginScreen extends JFrame {
    
    QuenMatKhau qmk;
    
    private TaiKhoanBLL list_tk;

    public LoginScreen() {
        list_tk = new TaiKhoanBLL();
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(1000, 500);
        this.setTitle("Đăng nhập");
        System.out.println(System.getProperty("user.dir"));
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/shoes_icon.png").getImage());
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        JLabel lefticon = new JLabel();
        Image imageicon = new ImageIcon(System.getProperty("user.dir") + "/src/Assets/login.jpg").getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        lefticon.setIcon(new ImageIcon(imageicon));
        lefticon.setBounds(0, 0, 500, 500);

        JLabel lbUsername = new JLabel("Username");
        lbUsername.setBounds(0, 100, 500, 50);
        lbUsername.setFont(new Font("Serif", Font.BOLD, 18));

        customTextField usernameField = new customTextField("Enter your username");
        usernameField.setBounds(10, 150, 400, 50);
        usernameField.setFont(new Font("Serif", Font.BOLD, 15));
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = usernameField.getText();
                if (text.equals("Enter your username")) {
                    usernameField.setText("");
                    usernameField.setBorderColor(Color.BLUE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = usernameField.getText();
                if (text.isEmpty()) {
                    usernameField.setText("Enter your username");

                }
                usernameField.setBorderColor(Color.BLACK);

            }

        });

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setBounds(0, 200, 500, 50);
        lbPassword.setFont(new Font("Serif", Font.BOLD, 18));

        customPasswordField passwordField = new customPasswordField("Enter your password");
        passwordField.setBounds(10, 250, 400, 50);
        passwordField.setFont(new Font("Serif", Font.BOLD, 15));
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = new String(passwordField.getPassword());
                if (text.equals("Enter your password")) {
                    passwordField.setText("");
                    passwordField.setBorderColor(Color.BLUE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = new String(passwordField.getPassword());
                if (text.isEmpty()) {
                    passwordField.setText("Enter your password");

                }
                passwordField.setBorderColor(Color.BLACK);

            }

        });

        CustomButton ButtonLogin = new CustomButton("Đăng nhập");
        ButtonLogin.setBounds(10, 330, 400, 50);
        ButtonLogin.setFont(new Font("Serif", Font.BOLD, 15));
        ButtonLogin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setStrTenDangNhap(username);
                tk.setStrMatKhau(list_tk.hashMD5(password));
                if (list_tk.TestAccount(tk)) {
                    tk = list_tk.getCapBacfromMa(tk.getStrTenDangNhap());
                    System.out.println(tk);
                    dispose();
                    new DashBoard(tk);
                } else {
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không chính xác", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ButtonLogin.setBackground(Color.decode("#303F9F"));
                ButtonLogin.setBorderColor(Color.decode("#303F9F"));
                ButtonLogin.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ButtonLogin.setBackground(Color.WHITE);
                ButtonLogin.setBorderColor(Color.decode("#303F9F"));
                ButtonLogin.setForeground(Color.decode("#303F9F"));
            }

        });

        

        JLabel lbForgotPassword = new JLabel("Quên mật khẩu");
        lbForgotPassword.setSize(150, 50);
        lbForgotPassword.setFont(new Font("Serif", Font.ITALIC, 18));
        lbForgotPassword.setLocation(300, 380);
        lbForgotPassword.addMouseListener(new MouseAdapter() {

        });

        lbForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                qmk=new QuenMatKhau();
                qmk.setVisible(true);
              
            }
        });

        JLabel lbTitle = new JLabel("Đăng nhập hệ thống quản lý");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTitle.setBackground(Color.WHITE);
        pnTitle.add(lbTitle);
        pnTitle.setBounds(0, 50, 500, 50);
        leftPanel.setLayout(null);
        leftPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        leftPanel.setBounds(0, 0, 500, 500);
        leftPanel.add(lefticon);

        rightPanel.setBounds(500, 0, 500, 500);
        rightPanel.add(pnTitle);
        rightPanel.add(lbUsername);
        rightPanel.add(usernameField);

        rightPanel.add(lbPassword);
        rightPanel.add(passwordField);

        rightPanel.add(ButtonLogin);
        rightPanel.add(lbForgotPassword);
        panel.add(leftPanel);
        panel.add(rightPanel);
        this.add(panel);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
