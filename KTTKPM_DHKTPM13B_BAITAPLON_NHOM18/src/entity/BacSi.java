package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: BacSi
 *
 */
@Entity
@Table(name = "BacSi")
@NamedQueries({
	@NamedQuery(name = "getBSByCMND", query ="select k from BacSi k where k.soCMND = :soCMND"),
	@NamedQuery(name = "getBSByTK", query ="select k from BacSi k where k.taiKhoan = :taiKhoan"),
	@NamedQuery(name = "getAllBS", query ="select k from BacSi k"),
	@NamedQuery(name = "getBacSiByTaiKhoan", query = "select k from BacSi k where k.taiKhoan = :taiKhoan")
	
})
public class BacSi implements Serializable {
	@Id
	private String maBacSi;
	private String tenBacSi;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String chuyenMon;
	private String soCMND;
	private String diaChi;
	private Date ngayVaoLam;
	private String taiKhoan;
	private String matKhau;
	
	public String getMaBacSi() {
		return maBacSi;
	}

	public void setMaBacSi(String maBacSi) {
		this.maBacSi = maBacSi;
	}

	public String getTenBacSi() {
		return tenBacSi;
	}

	public void setTenBacSi(String tenBacSi) {
		this.tenBacSi = tenBacSi;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getChuyenMon() {
		return chuyenMon;
	}

	public void setChuyenMon(String chuyenMon) {
		this.chuyenMon = chuyenMon;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public BacSi(String maBacSi, String tenBacSi, boolean gioiTinh, Date ngaySinh, String chuyenMon, String soCMND,
			String diaChi, Date ngayVaoLam, String taiKhoan, String matKhau) {
		super();
		this.maBacSi = maBacSi;
		this.tenBacSi = tenBacSi;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.chuyenMon = chuyenMon;
		this.soCMND = soCMND;
		this.diaChi = diaChi;
		this.ngayVaoLam = ngayVaoLam;
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
	}

	private static final long serialVersionUID = 1L;

	public BacSi() {
		super();
	}
   
}
