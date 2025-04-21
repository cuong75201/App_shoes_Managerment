/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import GUI.component.CustomTable;
import java.io.File;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;
public class XuatExcel {
     public static void exportTableToExcel(CustomTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách");

        TableModel model = table.getModel();

        // Ghi header
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(model.getColumnName(i));
        }

        // Ghi dữ liệu
        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object value = model.getValueAt(i, j);
                cell.setCellValue(value != null ? value.toString() : "");
            }
        }

        // Ghi ra file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel!");
        }
    }
     public static void exportTableToExcel(CustomTable table) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
    fileChooser.setSelectedFile(new File("du-lieu.xlsx"));

    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        // Thêm đuôi .xlsx nếu thiếu
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        // Gọi lại hàm cũ để xuất file
        exportTableToExcel(table, filePath);
    }
}
}
