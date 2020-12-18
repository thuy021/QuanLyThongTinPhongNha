package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Thuoc
 *
 */
@Entity
@Table(name = "Thuoc")
@NamedQueries({
	@NamedQuery(name = "getThuocByTenThuoc", query ="select k from Thuoc k where k.tenThuoc = :tenThuoc"),
	@NamedQuery(name = "getAllThuoc", query ="select k from Thuoc k")
})
public class Thuoc implements Serializable {

	@Id
	private String maThuoc;
	private String tenThuoc;
	private String donVi;
	private double donGia;
	
	public Thuoc(String maThuoc, String tenThuoc, String donVi, double donGia) {
		super();
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.donVi = donVi;
		this.donGia = donGia;
	}

	public String getMaThuoc() {
		return maThuoc;
	}

	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}

	public String getTenThuoc() {
		return tenThuoc;
	}

	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", donVi=" + donVi + ", donGia=" + donGia + "]";
	}

	private static final long serialVersionUID = 1L;

	public Thuoc() {
		super();
	}
   
}
