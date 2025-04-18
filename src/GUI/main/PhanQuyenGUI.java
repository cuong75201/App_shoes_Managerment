/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import javax.swing.JPanel;

import BLL.TaiKhoanBLL;

import DTO.TaiKhoanDTO;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class PhanQuyenGUI extends JPanel{
    public TaiKhoanBLL tk;
    ArrayList<TaiKhoanDTO> list_tk;
    public PhanQuyenGUI(){
        list_tk=new ArrayList<>();
        tk=new TaiKhoanBLL();
        list_tk=tk.getListAccount();
    }
}
