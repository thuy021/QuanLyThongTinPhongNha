package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dao.BacSiDAO;
import dao.ChiTietDonThuocDAO;
import dao.DonThuocDAO;
import dao.GoiDichVuDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import entity.BacSi;
import entity.ChiTietDonThuoc;
import entity.DonThuoc;
import entity.GoiDichVu;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;

public class Testing {
	public static void main(String[] args) throws ParseException {
//		BacSiDAO bsDAO = new BacSiDAO();
//		DonThuocDAO dtDAO = new DonThuocDAO();
//		KhachHangDAO khDAO = new KhachHangDAO();
//		NhanVienDAO nvDAO = new NhanVienDAO();
//		GoiDichVuDAO dvDAO = new GoiDichVuDAO();
//		ChiTietDonThuocDAO ctDAO = new ChiTietDonThuocDAO();
//		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
//		BacSi b = new BacSi("BS01", "Phan Bach", true, df.parse("20201010") , "da khoa", "121212", "go vap", df.parse("20191010"), "bs1", "123");
//		KhachHang k = new KhachHang("KH01", "Thanh Thuy", true, df.parse("20201010"), "0992992", "121212", "Binh Thanh");
//		GoiDichVu dv = new GoiDichVu("DV1", "Boc rang", 150000);
//		Thuoc th = new Thuoc("T1", "abc", "vien", 12000);
//		NhanVien n= new NhanVien("NV01", "Ly Vy", false, df.parse("20201010"), "020022", "22222", "Quan 3", df.parse("20201010"), "NVLT", "nv1", "123");
//		DonThuoc dt = new DonThuoc("DT01", k, b, dv,n,  df.parse("20201010"), 12000);
//		List<ChiTietDonThuoc> ct = new ArrayList<ChiTietDonThuoc>();
//		ChiTietDonThuoc ct1 =new ChiTietDonThuoc(dt,th, 5);
//		ct.add(ct1);
//
//		dvDAO.persist(dv);
//		bsDAO.persist(b);
//		khDAO.persist(k);
//		nvDAO.persist(n);
//		dtDAO.persist(dt);
//		ctDAO.persist(ct1);
//
//	
//		System.out.println("\n"+b);
//		System.out.println(n);
//		System.out.println(ct1);
	}

}
