package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: DonThuoc
 *
 */
@Entity
@Table(name = "DonThuoc")
@NamedQuery(name = "getAllDonThuoc", query ="select k from DonThuoc k")
public class DonThuoc implements Serializable {
	@Id
	private String maDonThuoc;
	@ManyToOne
	@JoinColumn(name = "maKhachHang", referencedColumnName ="maKhachHang")
	private KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "maBacSi", referencedColumnName="maBacSi")
	private BacSi bacSi;

	@ManyToOne
	@JoinColumn(name = "maGoiDV", referencedColumnName ="maGoiDV")
	private GoiDichVu goiDV;
	
	@ManyToOne
	@JoinColumn(name = "maNhanVien",referencedColumnName ="maNhanVien")
	private NhanVien nhanVien;

	@OneToMany(mappedBy = "donThuoc", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ChiTietDonThuoc> listCTDT;
	private static final long serialVersionUID = 1L;

	private Date ngayLap;
	private double tongTien;



	public DonThuoc(String maDonThuoc, KhachHang khachHang, BacSi bacSi, GoiDichVu goiDV, NhanVien nhanVien,
			Date ngayLap, double tongTien) {
		super();
		this.maDonThuoc = maDonThuoc;
		this.khachHang = khachHang;
		this.bacSi = bacSi;
		this.goiDV = goiDV;
		this.nhanVien = nhanVien;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
	}

	public DonThuoc(String maDonThuoc, KhachHang khachHang, BacSi bacSi, GoiDichVu goiDV, NhanVien nhanVien,
			List<ChiTietDonThuoc> listCTDT, Date ngayLap, double tongTien) {
		super();
		this.maDonThuoc = maDonThuoc;
		this.khachHang = khachHang;
		this.bacSi = bacSi;
		this.goiDV = goiDV;
		this.nhanVien = nhanVien;
		this.listCTDT = listCTDT;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
	}

	
	public DonThuoc(String maDonThuoc, KhachHang khachHang, BacSi bacSi, GoiDichVu goiDV,
			List<ChiTietDonThuoc> listCTDT, Date ngayLap) {
		super();
		this.maDonThuoc = maDonThuoc;
		this.khachHang = khachHang;
		this.bacSi = bacSi;
		this.goiDV = goiDV;
		this.listCTDT = listCTDT;
		this.ngayLap = ngayLap;
	}

	public DonThuoc(String maDonThuoc) {
		super();
		this.maDonThuoc = maDonThuoc;
	}

	public String getMaDonThuoc() {
		return maDonThuoc;
	}

	public void setMaDonThuoc(String maDonThuoc) {
		this.maDonThuoc = maDonThuoc;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public BacSi getBacSi() {
		return bacSi;
	}

	public void setBacSi(BacSi bacSi) {
		this.bacSi = bacSi;
	}

	public GoiDichVu getGoiDV() {
		return goiDV;
	}

	public void setGoiDV(GoiDichVu goiDV) {
		this.goiDV = goiDV;
	}

	public List<ChiTietDonThuoc> getListCTDT() {
		return listCTDT;
	}

	public void setListCTDT(List<ChiTietDonThuoc> listCTDT) {
		this.listCTDT = listCTDT;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}


	@Override
	public String toString() {
		return "DonThuoc [maDonThuoc=" + maDonThuoc + ", khachHang=" + khachHang + ", bacSi=" + bacSi + ", goiDV="
				+ goiDV + ", nhanVien=" + nhanVien + ", listCTDT=" + listCTDT + ", ngayLap=" + ngayLap + ", tongTien="
				+ tongTien + "]";
	}

	public DonThuoc() {
		super();
	}

}
