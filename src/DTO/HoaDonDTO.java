package DTO;

public class HoaDonDTO {
    private String strMaHD;
    private String strMaNV;
    private String strMaKH;
    private String strMaKM;
    private String strNgayBan;
    private double tongTien;
    private int trangthai; // Thêm trường trạng thái

    public HoaDonDTO() {
    }

    // Constructor cũ
    public HoaDonDTO(String strMaHD, String strMaNV, String strMaKH, String strMaKM, String strNgayBan, double tongTien) {
        this.strMaHD = strMaHD;
        this.strMaNV = strMaNV;
        this.strMaKH = strMaKH;
        this.strMaKM = strMaKM;
        this.strNgayBan = strNgayBan;
        this.tongTien = tongTien;
        this.trangthai = 1; // Mặc định là 1 - đang hoạt động
    }
    
    // Constructor mới có thêm trạng thái
    public HoaDonDTO(String strMaHD, String strMaNV, String strMaKH, String strMaKM, String strNgayBan, double tongTien, int trangthai) {
        this.strMaHD = strMaHD;
        this.strMaNV = strMaNV;
        this.strMaKH = strMaKH;
        this.strMaKM = strMaKM;
        this.strNgayBan = strNgayBan;
        this.tongTien = tongTien;
        this.trangthai = trangthai;
    }

    public String getStrMaHD() {
        return strMaHD;
    }

    public void setStrMaHD(String strMaHD) {
        this.strMaHD = strMaHD;
    }

    public String getStrMaNV() {
        return strMaNV;
    }

    public void setStrMaNV(String strMaNV) {
        this.strMaNV = strMaNV;
    }

    public String getStrMaKH() {
        return strMaKH;
    }

    public void setStrMaKH(String strMaKH) {
        this.strMaKH = strMaKH;
    }

    public String getStrMaKM() {
        return strMaKM;
    }

    public void setStrMaKM(String strMaKM) {
        this.strMaKM = strMaKM;
    }

    public String getStrNgayBan() {
        return strNgayBan;
    }

    public void setStrNgayBan(String strNgayBan) {
        this.strNgayBan = strNgayBan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public int getTrangthai() {
        return trangthai;
    }
    
    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
    
    @Override
    public String toString() {
        return "HoaDonDTO{" + "strMaHD=" + strMaHD + ", strMaNV=" + strMaNV + ", strMaKH=" + strMaKH + ", strMaKM=" + strMaKM + ", strNgayBan=" + strNgayBan + ", tongTien=" + tongTien + ", trangthai=" + trangthai + '}';
    }
}