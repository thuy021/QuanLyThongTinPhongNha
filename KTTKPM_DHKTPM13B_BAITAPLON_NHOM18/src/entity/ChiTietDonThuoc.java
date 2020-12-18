package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ChiTietDonThuoc
 *
 */
@Entity
@IdClass(ChiTietDonThuoc_PK.class)
@Table(name = "ChiTietDonThuoc")
@NamedQuery(name = "getAllCTDonThuoc", query ="select k from ChiTietDonThuoc k")
/*
 * @NamedQuery(name = "getCTDTByMaDonThuoc", query
 * ="select k from ChiTietDonThuoc k where k.maDonThuoc=maDonThuoc")
 */
public class ChiTietDonThuoc implements Serializable {
	@Id
	@ManyToOne
	@JoinColumn(name = "maDonThuoc", nullable = false)
	private DonThuoc donThuoc;

	@Id
	@ManyToOne
	@JoinColumn(name = "maThuoc", nullable = false)
	private Thuoc thuoc;

	@Override
	public String toString() {
		return "ChiTietDonThuoc [donThuoc=" + donThuoc + ", thuoc=" + thuoc + ", soLuong=" + soLuong + "]";
	}

	private int soLuong;

	public DonThuoc getDonThuoc() {
		return donThuoc;
	}

	public void setDonThuoc(DonThuoc donThuoc) {
		this.donThuoc = donThuoc;
	}

	public Thuoc getThuoc() {
		return thuoc;
	}

	public void setThuoc(Thuoc thuoc) {
		this.thuoc = thuoc;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ChiTietDonThuoc(DonThuoc donThuoc, Thuoc thuoc, int soLuong) {
		super();
		this.donThuoc = donThuoc;
		this.thuoc = thuoc;
		this.soLuong = soLuong;
	}

	private static final long serialVersionUID = 1L;

	public ChiTietDonThuoc() {
		super();
	}

}
