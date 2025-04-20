/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.HoaDonDAL;
import DTO.HoaDonDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author OS
 */
public class HoaDonBLL {

    private HoaDonDAL hoaDonDAL;

    public HoaDonBLL() {
        hoaDonDAL = new HoaDonDAL();
    }

    /**
     * Lấy danh sách tất cả hóa đơn
     *
     * @return ArrayList chứa danh sách hóa đơn
     */
    public ArrayList<HoaDonDTO> getHoaDonList() {
        return hoaDonDAL.getHoaDonList();
    }

    /**
     * Lấy danh sách hóa đơn theo mã khách hàng
     *
     * @param maKH Mã khách hàng
     * @return ArrayList chứa danh sách hóa đơn của khách hàng
     */
    public ArrayList<HoaDonDTO> getHoaDonByCustomerId(String maKH) {
        return hoaDonDAL.getHoaDonByCustomerId(maKH);
    }

    /**
     * Lấy danh sách hóa đơn trong khoảng thời gian
     *
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return ArrayList chứa danh sách hóa đơn
     */
    public ArrayList<HoaDonDTO> getHoaDonByDateRange(String startDate, String endDate) {
        return hoaDonDAL.getHoaDonByDateRange(startDate, endDate);
    }

    /**
     * Thêm một hóa đơn mới
     *
     * @param hoaDon Đối tượng hóa đơn cần thêm
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean insertHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hóa đơn
        if (hoaDon == null) {
            return false;
        }

        // Kiểm tra mã hóa đơn đã tồn tại chưa
        if (hoaDonDAL.checkHoaDonExists(hoaDon.getStrMaHD())) {
            return false;
        }

        return hoaDonDAL.insertHoaDon(hoaDon);
    }

    /**
     * Cập nhật thông tin hóa đơn
     *
     * @param hoaDon Đối tượng hóa đơn cần cập nhật
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hóa đơn
        if (hoaDon == null) {
            return false;
        }

        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(hoaDon.getStrMaHD())) {
            return false;
        }

        return hoaDonDAL.updateHoaDon(hoaDon);
    }

    /**
     * Cập nhật tổng tiền của hóa đơn
     *
     * @param maHD Mã hóa đơn
     * @param tongTien Tổng tiền mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTongTien(String maHD, double tongTien) {
        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(maHD)) {
            return false;
        }

        return hoaDonDAL.updateTongTien(maHD, tongTien);
    }

    /**
     * Xóa một hóa đơn khỏi hệ thống
     *
     * @param maHD Mã hóa đơn cần xóa
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteHoaDon(String maHD) {
        // Kiểm tra mã hóa đơn có tồn tại không
        if (!hoaDonDAL.checkHoaDonExists(maHD)) {
            return false;
        }

        return hoaDonDAL.deleteHoaDon(maHD);
    }

    /**
     * Kiểm tra sự tồn tại của hóa đơn
     *
     * @param maHD Mã hóa đơn cần kiểm tra
     * @return true nếu hóa đơn tồn tại, false nếu không tồn tại
     */
    public boolean checkHoaDonExists(String maHD) {
        return hoaDonDAL.checkHoaDonExists(maHD);
    }

    public int getTongTientheoNam(int namCanLay) {
        int tong = 0;

        // 2 định dạng ngày có thể xảy ra
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("dd MMM yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        };

        for (HoaDonDTO hd : getHoaDonList()) {
            boolean parsed = false;

            for (DateTimeFormatter formatter : formatters) {
                try {
                    LocalDate date = LocalDate.parse(hd.getStrNgayBan().trim(), formatter);
                    if (date.getYear() == namCanLay) {
                        tong += hd.getTongTien();
                    }
                    parsed = true;
                    break;
                } catch (DateTimeParseException ignored) {
                }
            }

            if (!parsed) {
                System.out.println("❗ Không đọc được ngày: " + hd.getStrNgayBan() + " (Mã HĐ: " + hd.getStrMaHD() + ")");
            }
        }

        return tong;
    }
     public int getTongTienTheoThang(int thang, int nam) {
        double tong = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();

        for (HoaDonDTO pn : getHoaDonList()) {
            try {
                Date date = sdf.parse(pn.getStrNgayBan());
                cal.setTime(date);  
                int thangGD = cal.get(Calendar.MONTH) + 1;
                int namGD = cal.get(Calendar.YEAR);

                if (thangGD == thang && namGD == nam) {
                    tong += pn.getTongTien();
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày: " + pn.getStrNgayBan());
            }
        }

        return (int) tong; 

    }
      public int getTongTienTheoNgay(String ngayCanTinh) {
       SimpleDateFormat sdfInput = new SimpleDateFormat("d/M/yyyy");        // Format ngày người dùng nhập
        SimpleDateFormat sdfData  = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH); // Format trong dữ liệu

        double tong = 0;

        try {
            Date dateCanTinh = sdfInput.parse(ngayCanTinh);

            for (HoaDonDTO pn : getHoaDonList()) {
                Date datePhieu = sdfData.parse(pn.getStrNgayBan());

                if (datePhieu.equals(dateCanTinh)) {
                    tong += pn.getTongTien();
                }
            }

        } catch (ParseException e) {
            System.out.println("❌ Lỗi định dạng ngày: " + e.getMessage());
        }

        return (int) tong;
    }
      public int getTongTienByMaHD(String ma){
          double sum = 0;
          for(HoaDonDTO hddto:getHoaDonList()){
              if(hddto.getStrMaKH().equals(ma)){
                  sum+=hddto.getTongTien();
              }
          }
          return (int) sum;
      }
}
