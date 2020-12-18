package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NhanVien
 *
 */
@Entity
@Table(name = "NhanVien")
@NamedQueries({
	@NamedQuery(name = "getNVByMa", query ="select k from NhanVien k where k.maNhanVien = :maNhanVien"),
	@NamedQuery(name = "getAllNV", query ="select k from NhanVien k"),
	@NamedQuery(name = "getNVByUser", query = "select n from NhanVien n where n.taiKhoan=:taiKhoan")
	
})
public class NhanVien implements Serializable {
	@Id
	private String maNhanVien;
	private String tenNhanVien;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String soDienThoai;
	private String soCMND;
	private String diaChi;
	private Date ngayVaoLam;
	private String loaiTaiKhoan;
	private String taiKhoan;
	private String matKhau;
	
	
	public NhanVien(String maNhanVien, String tenNhanVien, boolean gioiTinh, Date ngaySinh, String soDienThoai,
			String soCMND, String diaChi, Date ngayVaoLam, String loaiTaiKhoan, String taiKhoan, String matKhau) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.soCMND = soCMND;
		this.diaChi = diaChi;
		this.ngayVaoLam = ngayVaoLam;
		this.loaiTaiKhoan = loaiTaiKhoan;
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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

	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}

	public void setLoaiTaiKhoan(String loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
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

	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", gioiTinh=" + gioiTinh
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", soCMND=" + soCMND + ", diaChi="
				+ diaChi + ", ngayVaoLam=" + ngayVaoLam + ", loaiTaiKhoan=" + loaiTaiKhoan + ", taiKhoan=" + taiKhoan
				+ ", matKhau=" + matKhau + "]";
	}

	private static final long serialVersionUID = 1L;

	public NhanVien() {
		super();
	}
   
}
