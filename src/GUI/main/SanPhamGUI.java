/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.main;

import DTO.SanPhamDTO;
import DTO.ThuongHieuDTO;

import GUI.component.CustomComboBox;
import GUI.component.customTextField;
import GUI.component.CustomButton;
import GUI.component.PanelFunction;

import BLL.SanPhamBLL;
import BLL.ThuongHieuBLL;

import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import Utils.CreateComponent;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SanPhamGUI extends JPanel {

    ArrayList<SanPhamDTO> list_sp;
    ArrayList<ThuongHieuDTO> list_th;
    SanPhamBLL sp;
    ThuongHieuBLL th;

    private PanelFunction pnButton;
    private CustomComboBox cbfilter;
    private int width = 1116, height = 800;

    public SanPhamGUI() {
        list_sp = new ArrayList<>();
        list_th = new ArrayList<>();
        th = new ThuongHieuBLL();
        sp = new SanPhamBLL();
        list_th = th.getList_ThuongHieu();
        list_sp = sp.getListProduct();

        init();
    }

    public void init() {
        
        pnButton = new PanelFunction();
       cbfilter = new CustomComboBox();
        cbfilter.addItem("Tất cả");
        for (ThuongHieuDTO thdto : list_th) {
            cbfilter.addItem(thdto.getStrTenthuonghieu());
        }

        pnButton.setCbfilter(cbfilter);
        this.setLayout(null);
        this.add(pnButton);
        this.setBounds(250, 0, width, height);
        this.setBackground(Color.decode("#F0F7FA"));

    }
}
