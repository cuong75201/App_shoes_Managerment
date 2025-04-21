-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: qlcuahanggiaydb
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tblchitiethd`
--

DROP TABLE IF EXISTS `tblchitiethd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblchitiethd` (
  `Magiay` varchar(20) NOT NULL,
  `MaHD` varchar(20) NOT NULL,
  `SoLuong` int NOT NULL,
  `GiaBan` int NOT NULL,
  `trangthai` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblchitiethd`
--

LOCK TABLES `tblchitiethd` WRITE;
/*!40000 ALTER TABLE `tblchitiethd` DISABLE KEYS */;
INSERT INTO `tblchitiethd` VALUES ('SP1','HD001',1,100,1),('SP1','HD004',1,100,1),('SP1','HD007',8,100,1),('SP1','HD010',1,150,1),('SP1','HD012',1,150,1),('SP2','HD003',1,150,1),('SP2','HD004',1,150,1),('SP2','HD007',8,150,1),('SP2','HD012',1,200,1),('SP3','HD001',1,200,1),('SP3','HD003',1,200,1),('SP3','HD004',2,200,1),('SP3','HD007',6,200,1),('SP4','HD002',1,250,1),('SP4','HD003',1,250,1),('SP4','HD004',2,250,1),('SP4','HD006',1,250,1),('SP4','HD007',5,250,1),('SP5','HD002',1,300,1),('SP5','HD006',3,100,1),('SP5','HD007',6,300,1),('SP5','HD008',3,350,1),('SP5','HD011',1,350,1),('SP6','HD005',2,350,1),('SP6','HD007',8,350,1),('SP4','HD001',1,1,1);
/*!40000 ALTER TABLE `tblchitiethd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblchitietkm`
--

DROP TABLE IF EXISTS `tblchitietkm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblchitietkm` (
  `MaGiay` varchar(20) NOT NULL,
  `MaKM` varchar(20) NOT NULL,
  `TiLeKM` double NOT NULL,
  `trangthai` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblchitietkm`
--

LOCK TABLES `tblchitietkm` WRITE;
/*!40000 ALTER TABLE `tblchitietkm` DISABLE KEYS */;
INSERT INTO `tblchitietkm` VALUES ('SP1','KM1',0.1,1),('SP1','KM5',0.5,1),('SP1','KM6',0.5,1),('SP2','KM1',0.1,1),('SP2','KM2',0.2,1),('SP2','KM5',0.5,1),('SP2','KM6',0.5,1),('SP3','KM1',0.1,1),('SP3','KM4',0.4,1),('SP3','KM5',0.5,1),('SP3','KM6',0.3,1),('SP4','KM3',0.3,1),('SP4','KM5',0.5,1),('SP5','KM4',0.4,1),('SP5','KM5',0.5,1);
/*!40000 ALTER TABLE `tblchitietkm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblchitietpn`
--

DROP TABLE IF EXISTS `tblchitietpn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblchitietpn` (
  `MaGiay` varchar(20) NOT NULL,
  `MaPN` varchar(20) NOT NULL,
  `SoLuong` int NOT NULL,
  `GiaNhap` int NOT NULL,
  `trangthai` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblchitietpn`
--

LOCK TABLES `tblchitietpn` WRITE;
/*!40000 ALTER TABLE `tblchitietpn` DISABLE KEYS */;
INSERT INTO `tblchitietpn` VALUES ('SP1','PN001',10,50,1),('SP2','PN001',10,100,1),('SP3','PN001',10,150,1),('SP4','PN001',10,200,1),('SP5','PN001',10,250,1),('SP6','PN001',10,300,1),('SP5','PN002',10,300,1),('SP1','PN003',3,100,1),('SP2','PN003',3,150,1),('SP3','PN003',3,200,1),('SP4','PN003',3,250,1);
/*!40000 ALTER TABLE `tblchitietpn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblhoadon`
--

DROP TABLE IF EXISTS `tblhoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblhoadon` (
  `MaHD` varchar(20) NOT NULL,
  `MaNV` varchar(20) NOT NULL,
  `MaKH` varchar(20) NOT NULL,
  `MaKM` varchar(20) NOT NULL,
  `NgayBan` varchar(20) NOT NULL,
  `TongTien` double NOT NULL,
  `Trangthai` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblhoadon`
--

LOCK TABLES `tblhoadon` WRITE;
/*!40000 ALTER TABLE `tblhoadon` DISABLE KEYS */;
INSERT INTO `tblhoadon` VALUES ('HD001','admin','KH1','KM2','08 Jun 2020',351,1),('HD002','admin','KH2','KM6','08 Jun 2020',550,1),('HD003','admin','KH5','KM4','08 Jun 2020',520,1),('HD004','admin','KH6','KM3','08 Jun 2020',1000,1),('HD005','admin','KH3','KM3','08 Jun 2020',700,1),('HD006','admin','KH1','KM2','08 Jun 2020',550,1),('HD007','admin','KH2','KM6','08 Jun 2020',7690,1),('HD008','admin','KH1','KM6','08 Jun 2020',1050,1),('HD010','admin','KH6','KM6','13 Jun 2020',75,1),('HD011','admin','KH6','KM6','13 Jun 2020',350,1),('HD012','admin','KH4','KM6','14 Jun 2020',175,1);
/*!40000 ALTER TABLE `tblhoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblkhachhang`
--

DROP TABLE IF EXISTS `tblkhachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblkhachhang` (
  `MaKH` varchar(20) NOT NULL,
  `Ho` varchar(20) NOT NULL,
  `Ten` varchar(20) NOT NULL,
  `GioiTinh` varchar(20) NOT NULL,
  `DiaChi` varchar(40) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `Loai` varchar(20) NOT NULL,
  `TongChiTieu` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblkhachhang`
--

LOCK TABLES `tblkhachhang` WRITE;
/*!40000 ALTER TABLE `tblkhachhang` DISABLE KEYS */;
INSERT INTO `tblkhachhang` VALUES ('KH1','Nguyễn Tuấn','Anh','Nam','123321123321 địa chỉ','nguyenanh@gmail.com','vip',58939.3),('KH2','Tăng Chí','Chung','Nam','12332 địa chỉ','chungtang@gmail.com','đặc biệt',0),('KH3','Võ Văn Gia','Bảo','Nam','13321 địa chỉ','baovo@gmail.com','vip',98495),('KH5','Trần Lê Anh','Đào','Nữ','23213 địa chỉ','daotran@gmail.com','bình thường',1150),('KH6','Nguyễn Văn','An','Nam','địa chỉ','abc@gmail.com','bình thường',6740);
/*!40000 ALTER TABLE `tblkhachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblkhuyenmai`
--

DROP TABLE IF EXISTS `tblkhuyenmai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblkhuyenmai` (
  `MaKM` varchar(20) NOT NULL,
  `TenChuongTrinh` varchar(40) NOT NULL,
  `LoaiChuongTrinh` varchar(20) NOT NULL,
  `DieuKien` varchar(20) NOT NULL,
  `NgayBatDau` varchar(20) NOT NULL,
  `NgayKetThuc` varchar(20) NOT NULL,
  `trangthai` int DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblkhuyenmai`
--

LOCK TABLES `tblkhuyenmai` WRITE;
/*!40000 ALTER TABLE `tblkhuyenmai` DISABLE KEYS */;
INSERT INTO `tblkhuyenmai` VALUES ('KM1','Mùa hè xanh','Loại 1','vip','13 May 2020','15 May 2020',1),('KM2','Ngày vàng','Loại 3','đặc biệt','01 Jun 2020','08 Jun 2020',1),('KM3','Ngày trở về','Loại 2','vip','08 Jun 2020','17 Jun 2020',1),('KM4','Mùa đông lạnh giá','Loại 3','bình thường','01 Jun 2020','17 Jun 2020',1),('KM5','Ngày mới','Loại 3','bình thường','01 Jan 2020','01 Jan 2030',1),('KM6','admin','Loại 1','bình thường','1 Jun 2020','30 Jun 2020',1);
/*!40000 ALTER TABLE `tblkhuyenmai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblloai`
--

DROP TABLE IF EXISTS `tblloai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblloai` (
  `Maloai` varchar(20) NOT NULL,
  `Tenloai` varchar(20) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`Maloai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblloai`
--

LOCK TABLES `tblloai` WRITE;
/*!40000 ALTER TABLE `tblloai` DISABLE KEYS */;
INSERT INTO `tblloai` VALUES ('1','sneaker',1),('2','Running',1),('3','Fashion',1),('4','Basketball',1),('5','Soccer',1);
/*!40000 ALTER TABLE `tblloai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmausac`
--

DROP TABLE IF EXISTS `tblmausac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblmausac` (
  `Mamau` varchar(20) NOT NULL,
  `Tenmau` varchar(20) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`Mamau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmausac`
--

LOCK TABLES `tblmausac` WRITE;
/*!40000 ALTER TABLE `tblmausac` DISABLE KEYS */;
INSERT INTO `tblmausac` VALUES ('BLK','Black',1),('BLU','Blue',1),('GR','Green',1),('ORG','Orange',1),('WT','White',1);
/*!40000 ALTER TABLE `tblmausac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblnhacungcap`
--

DROP TABLE IF EXISTS `tblnhacungcap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblnhacungcap` (
  `MaNCC` varchar(20) NOT NULL,
  `TenNCC` varchar(20) NOT NULL,
  `DiaChi` varchar(40) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`MaNCC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnhacungcap`
--

LOCK TABLES `tblnhacungcap` WRITE;
/*!40000 ALTER TABLE `tblnhacungcap` DISABLE KEYS */;
INSERT INTO `tblnhacungcap` VALUES ('NCC1','Rồng Thiên','123 Ra đường bị la,P ABC,TP HN','rongthien619@gmail.com',1),('NCC2','Trời Đỏ','456 kế bên nhà 123','sunred916@gmail.com',1),('NCC3','Rực lửa','135 là số lẻ không phải số lẽ','246@gmail.com',1),('NCC4','Bước Nhảy','246 không phải là số chẳn mà là số chẵn','jumpandjump@gmail.com',1),('NCC5','Tầm xa','3 là số nguyên tố','357@gmail.com',0),('NCC6','Đại học Sài Gòn','220 An Dương Vương','sgu@edu.vn',1);
/*!40000 ALTER TABLE `tblnhacungcap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblnhanvien`
--

DROP TABLE IF EXISTS `tblnhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblnhanvien` (
  `MaNV` varchar(20) NOT NULL,
  `Ho` varchar(30) NOT NULL,
  `Ten` varchar(10) NOT NULL,
  `GioiTinh` varchar(10) NOT NULL,
  `DiaChi` varchar(50) NOT NULL,
  `DienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(20) NOT NULL,
  `Luong` int NOT NULL,
  `Anh` varchar(100) DEFAULT NULL,
  `Trangthai` int DEFAULT NULL,
  PRIMARY KEY (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblnhanvien`
--

LOCK TABLES `tblnhanvien` WRITE;
/*!40000 ALTER TABLE `tblnhanvien` DISABLE KEYS */;
INSERT INTO `tblnhanvien` VALUES ('admin','Quản trị','Viên','Nam','123 Đi la cà,P đi,Q Ăn, TPHCM','1001011101','cuong75201@gmail.com',999999,'boy4.jpg',1),('id1','Nguyễn Thị','Thư','Nữ','123 Nguyện Văn A,P.A, Q.A,TPHCM','1234567890','nguyenvanan@gamil.co',1000,'boy4.jpg',1),('id2','Tàu Văn','Phà','Nam','123 Lạc Văn Biển,P.A, Q.A,TPHCM','1239876540','taunhobien@gamil.com',800,'boy4.jpg',1),('id3','Trần','Dần','Nam','123 A,P LA,Q DHA,TpHCM','2147483647','trandan@gmail.com',300,'boy4.jpg',1),('id4','Lê Văn','Hoàng','Nữ','3 ABCD,P LA,Q DHA,TpHCM','41234111','levanhoang@gmail.com',8000,'boy4.jpg',1),('id5','Nguyễn Thị Lê','Kim','Nữ','13 An Mà,P LA,Q DHA,TpHCM','123456789','nguyenthilekim@gmail',900,'boy4.jpg',1),('id6','Pham Van','Tri','Nam','11 AV, VC, CV ,CCV','1900152211','quantri@gmail.com',1000,NULL,1);
/*!40000 ALTER TABLE `tblnhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblphieunhap`
--

DROP TABLE IF EXISTS `tblphieunhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblphieunhap` (
  `MaPN` varchar(20) NOT NULL,
  `MaNCC` varchar(20) NOT NULL,
  `MaNV` varchar(20) NOT NULL,
  `NgayNhap` varchar(20) NOT NULL,
  `TongTien` double NOT NULL,
  `Trangthai` int DEFAULT '1',
  PRIMARY KEY (`MaPN`),
  KEY `FKMaNCC` (`MaNCC`),
  KEY `FKMaNV` (`MaNV`),
  CONSTRAINT `FKMaNCC` FOREIGN KEY (`MaNCC`) REFERENCES `tblnhacungcap` (`MaNCC`),
  CONSTRAINT `FKMaNV` FOREIGN KEY (`MaNV`) REFERENCES `tblnhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblphieunhap`
--

LOCK TABLES `tblphieunhap` WRITE;
/*!40000 ALTER TABLE `tblphieunhap` DISABLE KEYS */;
INSERT INTO `tblphieunhap` VALUES ('PN001','NCC1','admin','08 Jun 2020',10500,1),('PN002','NCC2','admin','08 Jun 2020',3000,1),('PN003','NCC3','admin','11 Jun 2020',2100,1);
/*!40000 ALTER TABLE `tblphieunhap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsanpham`
--

DROP TABLE IF EXISTS `tblsanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblsanpham` (
  `Magiay` varchar(20) NOT NULL,
  `Tengiay` varchar(20) NOT NULL,
  `Soluong` int NOT NULL,
  `Gia` int NOT NULL,
  `Size` int NOT NULL,
  `Doituongsd` varchar(20) NOT NULL,
  `Chatlieu` varchar(20) NOT NULL,
  `Maloai` varchar(20) NOT NULL,
  `Maxx` varchar(20) NOT NULL,
  `Mamau` varchar(20) NOT NULL,
  `Mathuonghieu` varchar(20) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`Magiay`),
  KEY `FKMaXuatXu` (`Maxx`),
  KEY `FKMaThuonghieu` (`Mathuonghieu`),
  KEY `FKMaMau` (`Mamau`),
  KEY `FKMaLoai` (`Maloai`),
  CONSTRAINT `FKMaLoai` FOREIGN KEY (`Maloai`) REFERENCES `tblloai` (`Maloai`),
  CONSTRAINT `FKMaMau` FOREIGN KEY (`Mamau`) REFERENCES `tblmausac` (`Mamau`),
  CONSTRAINT `FKMaThuonghieu` FOREIGN KEY (`Mathuonghieu`) REFERENCES `tblthuonghieu` (`Mathuonghieu`),
  CONSTRAINT `FKMaXuatXu` FOREIGN KEY (`Maxx`) REFERENCES `tblxuatxu` (`Maxx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsanpham`
--

LOCK TABLES `tblsanpham` WRITE;
/*!40000 ALTER TABLE `tblsanpham` DISABLE KEYS */;
INSERT INTO `tblsanpham` VALUES ('SP001','Sneaker 1',10,1500000,42,'Nam','Vải','1','VN','BLK','AD',1),('SP002','Sneaker 2',12,1550000,41,'Nữ','Da','1','CN','WT','BT',1),('SP003','Sneaker 3',14,1600000,43,'Nam','Vải','1','UK','GR','NK',1),('SP004','Sneaker 4',11,1650000,40,'Nữ','Da','1','US','BLU','TO',1),('SP005','Sneaker 5',13,1700000,42,'Nam','Vải','1','ID','ORG','AD',1),('SP006','Running 1',9,1450000,41,'Nam','Vải','2','VN','GR','BT',1),('SP007','Running 2',15,1500000,42,'Nữ','Da','2','CN','BLK','NK',1),('SP008','Running 3',12,1550000,40,'Nam','Vải','2','UK','WT','TO',1),('SP009','Running 4',16,1600000,43,'Nữ','Da','2','US','BLU','AD',1),('SP010','Running 5',10,1650000,41,'Nam','Vải','2','ID','ORG','BT',1),('SP011','Fashion 1',8,1800000,40,'Nữ','Vải','3','VN','BLK','NK',1),('SP012','Fashion 2',10,1850000,41,'Nam','Da','3','CN','WT','TO',1),('SP013','Fashion 3',11,1900000,42,'Nữ','Vải','3','UK','GR','AD',1),('SP014','Fashion 4',13,1950000,43,'Nam','Da','3','US','BLU','BT',1),('SP015','Fashion 5',12,2000000,40,'Nữ','Vải','3','ID','ORG','NK',1),('SP016','Basketball 1',9,2100000,44,'Nam','Da','4','VN','BLK','TO',1),('SP017','Basketball 2',14,2150000,43,'Nam','Vải','4','CN','WT','AD',1),('SP018','Basketball 3',11,2200000,42,'Nam','Da','4','UK','GR','BT',1),('SP019','Basketball 4',10,2250000,41,'Nữ','Vải','4','US','BLU','NK',1),('SP020','Basketball 5',12,2300000,44,'Nam','Da','4','ID','ORG','TO',1),('SP021','Soccer 1',13,1750000,42,'Nam','Da','5','VN','BLK','AD',1),('SP022','Soccer 2',11,1800000,43,'Nữ','Vải','5','CN','WT','BT',1),('SP023','Soccer 3',14,1850000,41,'Nam','Da','5','UK','GR','NK',1),('SP024','Soccer 4',12,1900000,40,'Nữ','Vải','5','US','BLU','TO',1),('SP025','Soccer 5',15,1950000,42,'Nam','Da','5','ID','ORG','AD',1),('SP026','Sneaker 6',10,1550000,43,'Nam','Vải','1','VN','GR','BT',1),('SP027','Sneaker 7',12,1600000,44,'Nữ','Da','1','CN','BLK','NK',1),('SP028','Sneaker 8',14,1650000,42,'Nam','Vải','1','UK','WT','TO',1),('SP029','Sneaker 9',11,1700000,41,'Nữ','Da','1','US','BLU','AD',1),('SP030','Sneaker 10',13,1750000,42,'Nam','Vải','1','ID','ORG','BT',1),('SP031','Running 6',9,1500000,40,'Nam','Vải','2','VN','GR','NK',1),('SP032','Running 7',15,1550000,41,'Nữ','Da','2','CN','BLK','TO',1),('SP033','Running 8',12,1600000,43,'Nam','Vải','2','UK','WT','AD',1),('SP034','Running 9',16,1650000,40,'Nữ','Da','2','US','BLU','BT',1),('SP035','Running 10',10,1700000,41,'Nam','Vải','2','ID','ORG','NK',1),('SP036','Fashion 6',8,1800000,42,'Nữ','Vải','3','VN','BLK','TO',1),('SP037','Fashion 7',10,1850000,43,'Nam','Da','3','CN','WT','AD',1),('SP038','Fashion 8',11,1900000,44,'Nữ','Vải','3','UK','GR','BT',1),('SP039','Fashion 9',13,1950000,40,'Nam','Da','3','US','BLU','NK',1),('SP040','Fashion 10',12,2000000,42,'Nữ','Vải','3','ID','ORG','TO',1);
/*!40000 ALTER TABLE `tblsanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltaikhoan`
--

DROP TABLE IF EXISTS `tbltaikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbltaikhoan` (
  `tendangnhap` varchar(10) NOT NULL,
  `matkhau` varchar(64) DEFAULT NULL,
  `capbac` int NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`tendangnhap`),
  CONSTRAINT `FKMaNVTK` FOREIGN KEY (`tendangnhap`) REFERENCES `tblnhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltaikhoan`
--

LOCK TABLES `tbltaikhoan` WRITE;
/*!40000 ALTER TABLE `tbltaikhoan` DISABLE KEYS */;
INSERT INTO `tbltaikhoan` VALUES ('admin','202cb962ac59075b964b07152d234b70',1,1),('id1','202cb962ac59075b964b07152d234b70',2,1),('id2','202cb962ac59075b964b07152d234b70',3,1),('id3','202cb962ac59075b964b07152d234b70',4,1),('id4','202cb962ac59075b964b07152d234b70',5,1),('id5','202cb962ac59075b964b07152d234b70',6,1);
/*!40000 ALTER TABLE `tbltaikhoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblthuonghieu`
--

DROP TABLE IF EXISTS `tblthuonghieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblthuonghieu` (
  `Mathuonghieu` varchar(20) NOT NULL,
  `Tenthuonghieu` varchar(30) NOT NULL,
  `Diachi` varchar(40) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`Mathuonghieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblthuonghieu`
--

LOCK TABLES `tblthuonghieu` WRITE;
/*!40000 ALTER TABLE `tblthuonghieu` DISABLE KEYS */;
INSERT INTO `tblthuonghieu` VALUES ('AD','Adidas','USA','adidas@gmail.com',1),('BT','Bitis','VietNam','bitis@gmial.com',1),('NK','Nike','USA','nike@gmail.com',1),('TO','ToOng','VietNam','toong@gmial.com',1);
/*!40000 ALTER TABLE `tblthuonghieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblxuatxu`
--

DROP TABLE IF EXISTS `tblxuatxu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblxuatxu` (
  `Maxx` varchar(20) NOT NULL,
  `Tennuoc` varchar(20) NOT NULL,
  `trangthai` int DEFAULT '1',
  PRIMARY KEY (`Maxx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblxuatxu`
--

LOCK TABLES `tblxuatxu` WRITE;
/*!40000 ALTER TABLE `tblxuatxu` DISABLE KEYS */;
INSERT INTO `tblxuatxu` VALUES ('CN','China',1),('ID','Indonesia',1),('UK','United Kingdom',1),('US','USA',1),('VN','VietNam',1);
/*!40000 ALTER TABLE `tblxuatxu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-21 22:40:50
