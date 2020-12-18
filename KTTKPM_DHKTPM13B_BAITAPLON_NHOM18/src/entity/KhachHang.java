package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: KhachHang
 *
 */
@Entity
@Table(name = "KhachHang")
@NamedQueries({
	@NamedQuery(name = "getKHByCMND", query ="select k from KhachHang k where k.soCMND = :soCMND"),
	@NamedQuery(name = "getAllKH", query ="select k from KhachHang k")
	
})
public class KhachHang implements Serializable {

	@Id
	private String maKhachHang;
	private String tenKhachHang;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String soDienThoai;
	private String soCMND;
	private String diaChi;
	
	
	public KhachHang(String maKhachHang, String tenKhachHang, boolean gioiTinh, Date ngaySinh, String soDienThoai,
			String soCMND, String diaChi) {
		super();
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.soCMND = soCMND;
		this.diaChi = diaChi;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
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

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
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

	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", gioiTinh=" + gioiTinh
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", soCMND=" + soCMND + ", diaChi="
				+ diaChi + "]";
	}

	private static final long serialVersionUID = 1L;

	public KhachHang() {
		super();
	}
   
}
