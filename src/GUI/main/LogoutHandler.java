/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import GUI.component.CustomButton;
import DTO.TaiKhoanDTO;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class xử lý chức năng đăng xuất của hệ thống
 */
public class LogoutHandler {
    
    private JFrame parentFrame;
    private TaiKhoanDTO currentAccount;
    
    /**
     * Constructor của LogoutHandler
     * 
     * @param parentFrame Frame hiện tại đang hiển thị (thường là DashBoard)
     * @param currentAccount Tài khoản hiện tại đang đăng nhập
     */
    public LogoutHandler(JFrame parentFrame, TaiKhoanDTO currentAccount) {
        this.parentFrame = parentFrame;
        this.currentAccount = currentAccount;
    }
    
    /**
     * Tạo nút đăng xuất với các thuộc tính và sự kiện
     * 
     * @return CustomButton nút đăng xuất đã được cấu hình
     */
    public CustomButton createLogoutButton() {
        CustomButton btnLogout = new CustomButton("Đăng xuất");
        btnLogout.setFont(new Font("Serif", Font.BOLD, 15));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setBorderColor(Color.decode("#FF3333"));
        btnLogout.setForeground(Color.decode("#FF3333"));
        
        btnLogout.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogout.setBackground(Color.decode("#FF3333"));
                btnLogout.setBorderColor(Color.decode("#FF3333"));
                btnLogout.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogout.setBackground(Color.WHITE);
                btnLogout.setBorderColor(Color.decode("#FF3333"));
                btnLogout.setForeground(Color.decode("#FF3333"));
            }
        });
        
        return btnLogout;
    }
    
    /**
     * Thực hiện chức năng đăng xuất
     * - Hiển thị dialog xác nhận
     * - Nếu xác nhận, đóng frame hiện tại và mở LoginScreen
     */
    public void logout() {
        int option = JOptionPane.showConfirmDialog(
                parentFrame, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (option == JOptionPane.YES_OPTION) {
            // Ghi log đăng xuất (nếu cần)
            System.out.println("Người dùng " + currentAccount.getStrTenDangNhap() + " đã đăng xuất");
            
            // Đóng frame hiện tại
            parentFrame.dispose();
            
            // Mở lại màn hình đăng nhập
            new LoginScreen();
        }
    }
    
    /**
     * Phương thức tĩnh để đăng xuất trực tiếp không cần tạo đối tượng
     * 
     * @param parentFrame Frame hiện tại
     * @param currentAccount Tài khoản hiện tại
     */
    public static void logoutSystem(JFrame parentFrame, TaiKhoanDTO currentAccount) {
        LogoutHandler handler = new LogoutHandler(parentFrame, currentAccount);
        handler.logout();
    }
}